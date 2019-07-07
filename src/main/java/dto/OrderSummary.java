package dto;

import java.math.BigDecimal;

public class OrderSummary {

    private BigDecimal price;
    private BigDecimal quantity;

    public OrderSummary(BigDecimal price, BigDecimal quantity) {
        this.price = price;
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(quantity)
                .append(" kg for £")
                .append(price)
                .toString();
    }
}
