package service;

import domain.Order;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class OrderServiceTest {

    private OrderService orderService;

    @Before
    public void setup(){
        orderService = new OrderService();
    }

    @Test
    public void testThatRegisterOrderStoresOrderInMemory() {
        Long userId = 1L;
        BigDecimal quantity = BigDecimal.valueOf(3.5);
        BigDecimal price = BigDecimal.valueOf(303);

        Order registeredOrder = orderService.registerOrder(userId, quantity, price, Order.Type.SELL);
        Assert.assertNotNull(registeredOrder);
        Assert.assertNotNull(registeredOrder.getId());

        List<Order> orders = orderService.getOrders();
        Assert.assertEquals(1, orders.size());

        Order order1 = orders.get(0);
        Assert.assertNotNull(order1.getId());
        Assert.assertEquals(userId, order1.getUserId());
        Assert.assertEquals(quantity, order1.getQuantity());
        Assert.assertEquals(price, order1.getPrice());
        Assert.assertEquals(Order.Type.SELL, order1.getType());
    }

    @Test(expected = OrderNotFoundException.class)
    public void testThatCancellingAnUnARegisteredOrderThrowsAnOrderNotFoundException(){
        Long unregisteredOrderId = 100L;
        orderService.cancelOrder(unregisteredOrderId);
    }

    @Test
    public void testThatCancellingARegisteredOrderRemovesTheOrderFromTheLiveOrderBoard(){
        Order registeredOrder = registerOrder(1L, BigDecimal.valueOf(3.5), BigDecimal.valueOf(303), Order.Type.SELL);
        orderService.cancelOrder(registeredOrder.getId());

        Assert.assertEquals(0, orderService.getOrders().size());
    }

    @Test
    public void testThatLiveOrdersSummaryForOnlyOneOrderShowsTheQuantityAndThePriceForThatOrder(){
        registerOrder(1L, BigDecimal.valueOf(3.5), BigDecimal.valueOf(303), Order.Type.SELL);
        List<String> liveOrdersSummary = orderService.getSummaryInformation();

        Assert.assertEquals(1, liveOrdersSummary.size());
        Assert.assertEquals("3.5 kg for £303", liveOrdersSummary.get(0));
    }

    @Test
    public void testThatLiveOrdersSummaryGroupsOrdersWithTheSamePriceAndDisplaysThemInDescendingOrderForOrderTypeSELL(){
        registerOrder(1L, BigDecimal.valueOf(3.5), BigDecimal.valueOf(306), Order.Type.SELL);
        registerOrder(2L, BigDecimal.valueOf(1.2), BigDecimal.valueOf(310), Order.Type.SELL);
        registerOrder(3L, BigDecimal.valueOf(1.5), BigDecimal.valueOf(307), Order.Type.SELL);
        registerOrder(4L, BigDecimal.valueOf(2.0), BigDecimal.valueOf(306), Order.Type.SELL);

        List<String> liveOrdersSummary = orderService.getSummaryInformation();
        Assert.assertEquals(3, liveOrdersSummary.size());

        Assert.assertEquals("5.5 kg for £306", liveOrdersSummary.get(0));
        Assert.assertEquals("1.5 kg for £307", liveOrdersSummary.get(1));
        Assert.assertEquals("1.2 kg for £310", liveOrdersSummary.get(2));
    }

    @Test
    public void testThatLiveOrdersSummaryDisplaysBUYOrdersInAscendingOrder(){
        registerOrder(1L, BigDecimal.valueOf(3.5), BigDecimal.valueOf(306), Order.Type.BUY);
        registerOrder(2L, BigDecimal.valueOf(1.2), BigDecimal.valueOf(310), Order.Type.BUY);
        registerOrder(3L, BigDecimal.valueOf(1.5), BigDecimal.valueOf(307), Order.Type.BUY);
        registerOrder(4L, BigDecimal.valueOf(2.0), BigDecimal.valueOf(306), Order.Type.BUY);

        List<String> liveOrdersSummary = orderService.getSummaryInformation();
        Assert.assertEquals(3, liveOrdersSummary.size());

        Assert.assertEquals("1.2 kg for £310", liveOrdersSummary.get(0));
        Assert.assertEquals("1.5 kg for £307", liveOrdersSummary.get(1));
        Assert.assertEquals("5.5 kg for £306", liveOrdersSummary.get(2));
    }

    private Order registerOrder(Long userId, BigDecimal quantity, BigDecimal price, Order.Type orderType) {
        return orderService.registerOrder(userId, quantity, price, orderType);
    }
}