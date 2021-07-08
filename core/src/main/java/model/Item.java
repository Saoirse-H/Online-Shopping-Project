package model;

import javax.persistence.*;
import javax.validation.constraints.Min;

/**
 * Default item class to represent the product being sold. Extend to implement a specific item.
 * repository for item will be in stock service
 */
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Integer itemId;

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "description")
    private String description;

    @Min(value=0, message = "The price must be positive")
    @Column(name = "price")
    private double price;

    @Min(value=0, message = "The quantity must be positive")
    @Column(name = "quantity")
    private int quantity;

    @Min(value = 0, message= "The seller_id must be greater than or equal to 0")
    @Column(name="seller_id")
    private Integer sellerId;

/*    @Column(name ="seller_id")
    @ManyToOne
    @JoinColumn(name="user_id")
    private User sellerId;*/

    public Item() {
    }

    public Item(String name, String category, String description, @Min(value = 0, message = "The price must be positive") double price, @Min(value = 0, message = "The quantity must be positive") int quantity, @Min(value = 0, message= "The seller_id must be greater than or equal to 0") int sellerId) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.sellerId = sellerId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }
}
