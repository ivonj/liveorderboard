package dto;

import domain.Order;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderSummaryInformationTest {


    @Test
    public void testThatOneOrderIsConvertedToAnOrderSummary() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1L, BigDecimal.valueOf(3.5), BigDecimal.valueOf(306), Order.Type.SELL));

        OrderSummaryInformation orderSummaryInformation = new OrderSummaryInformation(orders);

        List<OrderSummary> summary = orderSummaryInformation.getSummary();
        Assert.assertEquals(1, summary.size());
        Assert.assertEquals(BigDecimal.valueOf(306), summary.get(0).getPrice());
        Assert.assertEquals(BigDecimal.valueOf(3.5), summary.get(0).getQuantity());
    }

    @Test
    public void testThatOrdersWithTheSamePriceAreGrouped() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1L, BigDecimal.valueOf(3.5), BigDecimal.valueOf(306), Order.Type.SELL));
        orders.add(new Order(2L, BigDecimal.valueOf(2.0), BigDecimal.valueOf(306), Order.Type.SELL));

        OrderSummaryInformation summaryInformation = new OrderSummaryInformation(orders);

        List<OrderSummary> summary = summaryInformation.getSummary();

        Assert.assertEquals(1, summary.size());
        Assert.assertEquals(BigDecimal.valueOf(306), summary.get(0).getPrice());
        Assert.assertEquals(BigDecimal.valueOf(5.5), summary.get(0).getQuantity());
    }

    @Test
    public void testThatSellOrdersAreOrderedByPriceInAscendingOrder() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1L, BigDecimal.valueOf(3.5), BigDecimal.valueOf(306), Order.Type.SELL));
        orders.add(new Order(2L, BigDecimal.valueOf(2.0), BigDecimal.valueOf(310), Order.Type.SELL));
        orders.add(new Order(3L, BigDecimal.valueOf(1.5), BigDecimal.valueOf(307), Order.Type.SELL));

        OrderSummaryInformation summaryInformation = new OrderSummaryInformation(orders);

        List<OrderSummary> summary = summaryInformation.getSummary();

        Assert.assertEquals(3, summary.size());
        Assert.assertEquals(BigDecimal.valueOf(306), summary.get(0).getPrice());
        Assert.assertEquals(BigDecimal.valueOf(3.5), summary.get(0).getQuantity());

        Assert.assertEquals(BigDecimal.valueOf(307), summary.get(1).getPrice());
        Assert.assertEquals(BigDecimal.valueOf(1.5), summary.get(1).getQuantity());

        Assert.assertEquals(BigDecimal.valueOf(310), summary.get(2).getPrice());
        Assert.assertEquals(BigDecimal.valueOf(2.0), summary.get(2).getQuantity());
    }

    @Test
    public void testThatBuyOrdersAreOrderedByPriceInDescendingOrder() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1L, BigDecimal.valueOf(3.5), BigDecimal.valueOf(306), Order.Type.BUY));
        orders.add(new Order(2L, BigDecimal.valueOf(2.0), BigDecimal.valueOf(310), Order.Type.BUY));
        orders.add(new Order(3L, BigDecimal.valueOf(1.5), BigDecimal.valueOf(307), Order.Type.BUY));

        OrderSummaryInformation summaryInformation = new OrderSummaryInformation(orders);

        List<OrderSummary> summary = summaryInformation.getSummary();

        Assert.assertEquals(3, summary.size());
        Assert.assertEquals(BigDecimal.valueOf(310), summary.get(0).getPrice());
        Assert.assertEquals(BigDecimal.valueOf(2.0), summary.get(0).getQuantity());

        Assert.assertEquals(BigDecimal.valueOf(307), summary.get(1).getPrice());
        Assert.assertEquals(BigDecimal.valueOf(1.5), summary.get(1).getQuantity());

        Assert.assertEquals(BigDecimal.valueOf(306), summary.get(2).getPrice());
        Assert.assertEquals(BigDecimal.valueOf(3.5), summary.get(2).getQuantity());
    }

}