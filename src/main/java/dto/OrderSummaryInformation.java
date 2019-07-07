package dto;

import domain.Order;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class OrderSummaryInformation {

    private List<OrderSummary> sellOrderSummary;
    private List<OrderSummary> buyOrderSummary;

    public OrderSummaryInformation(List<Order> orders) {
        List<Order> sellOrders = collectOrdersByType(orders, Order.Type.SELL);
        List<Order> buyOrders = collectOrdersByType(orders, Order.Type.BUY);

        sellOrderSummary = toOrderSummary(sellOrders, Order.Type.SELL);
        buyOrderSummary = toOrderSummary(buyOrders, Order.Type.BUY);
    }

    private List<OrderSummary> toOrderSummary(Collection<Order> orders, Order.Type orderType) {

        Map<BigDecimal, List<Order>> groupedOrdersByPrice = orders
                .stream()
                .collect(Collectors.groupingBy(Order::getPrice));

        return groupedOrdersByPrice.entrySet().stream()
                .map(entry ->
                        new OrderSummary(entry.getKey(),
                                entry.getValue().stream()
                                        .map(Order::getQuantity)
                                        .reduce(BigDecimal.ZERO, (q1, q2) -> q1.add(q2))))
                .sorted(getSortingComparator(orderType))
                .collect(Collectors.toList());
    }

    private List<Order> collectOrdersByType(List<Order> orders, Order.Type type) {
        return orders.stream().
                filter(order -> order.getType().equals(type))
                .collect(Collectors.toList());
    }

    private Comparator<? super OrderSummary> getSortingComparator(Order.Type orderType) {
        return Order.Type.SELL.equals(orderType) ?
                Comparator.comparing(OrderSummary::getPrice) :
                Comparator.comparing(OrderSummary::getPrice).reversed();
    }

    public List<OrderSummary> getSummary() {
        List<OrderSummary> orderSummaryList = new ArrayList<>();
        orderSummaryList.addAll(sellOrderSummary);
        orderSummaryList.addAll(buyOrderSummary);
        return orderSummaryList;
    }

}
