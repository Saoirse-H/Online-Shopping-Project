package response;

import java.io.Serializable;
import java.util.List;
import model.Cart;

public class CartList implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Cart> carts;

    public CartList() { }

    public CartList(List<Cart> carts) {
        this.carts = carts;
    }

    public List<Cart> getCarts() {
        return this.carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    
}
