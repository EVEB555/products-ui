package lt.bit.products.ui.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {
    private UUID id;
    private String name; // private neexposina laukelio, bet tuomet naudojami metodai
    private BigDecimal price;
    //private Integer quantity, int - primatyvus, Integer - objektinis tipas.
    // Jei norim irasyti null i duomenu baze su integer bus galima ja priskirti (null palaikomas)
    private double quantity;

    public Product(String name, BigDecimal price, double quantity) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
