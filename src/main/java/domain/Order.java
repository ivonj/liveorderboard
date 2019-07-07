package domain;

import java.math.BigDecimal;

public class Order {

    public enum Type {BUY, SELL}

    private Long id;
    private Long userId;
    private BigDecimal quantity;
    private BigDecimal price;
    private Type type;

    public Order(Long userId, BigDecimal quantity, BigDecimal price, Type type) {
         this.userId = userId;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Type getType() {
        return type;
    }
}
