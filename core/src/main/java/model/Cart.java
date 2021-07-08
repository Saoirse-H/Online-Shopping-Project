package model;

import javax.persistence.*;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="user_id")
    private int userId;

    @Column(name="item_id")
    private int itemId;

    @Column(name="price")
    private double price;

    @Column(name="quantity")
    private int quantity;

    public Cart() {}

    public Cart(int user_id, int item_id, double price) {
        this.userId = user_id;
        this.itemId = item_id;
        this.price = price;
        this.quantity = 1;
    }

    public int getId() {
        return id;
    }
	public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getItemId() {
        return this.itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
