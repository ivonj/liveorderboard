package service;

import domain.Order;
import dto.OrderSummary;
import dto.OrderSummaryInformation;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class OrderService {

    private Map<Long, Order> liveOrderBoard = new HashMap<Long, Order>();

    private AtomicLong orderId = new AtomicLong(0);

    public Order registerOrder(Long userId, BigDecimal quantity, BigDecimal price, Order.Type orderType) {
        Order order = new Order(userId, quantity, price, orderType);
        order.setId(generateOrderId());
        liveOrderBoard.put(order.getId(), order);
        return order;
    }

    public void cancelOrder(Long orderId) {
        Order cancelledOrder = liveOrderBoard.remove(orderId);

        if (cancelledOrder == null) {
            throw new OrderNotFoundException("Order " + orderId + " was not found.");
        }
    }

    public List<String> getSummaryInformation() {
        OrderSummaryInformation orderSummaryInformation = new OrderSummaryInformation(getOrders());

        return orderSummaryInformation.getSummary().stream()
                .map(OrderSummary::toString)
                .collect(Collectors.toList());
    }

    List<Order> getOrders() {
        return new ArrayList(liveOrderBoard.values());
    }

    private long generateOrderId() {
        return orderId.incrementAndGet();
    }

}
