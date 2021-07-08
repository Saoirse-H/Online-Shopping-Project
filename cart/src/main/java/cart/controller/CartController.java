package cart.controller;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cart.repository.CartRepository;
import model.Cart;
import response.CartList;

@RestController
public class CartController {
    @Autowired
    private CartRepository cartRepository;

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public Iterable<Cart> getCart() {
        return cartRepository.findAll();
    }

    @RequestMapping(value="/already-in-cart/{user}/{item}", method=RequestMethod.GET)
    public String alreadyInCart(@PathVariable("user") int userId, @PathVariable("item") int itemId) {
        Optional<Cart> cart = cartRepository.findByUserIdAndItemId(userId, itemId);
        return String.valueOf(cart.isPresent());
    }

    @RequestMapping(value="/get-cart/{user}", method=RequestMethod.GET) 
    public CartList getCart(@PathVariable("user") int userId) {
        List<Cart> userCarts = cartRepository.findByUserId(userId);
        CartList carts = new CartList(userCarts);
        return carts;
    }

    @RequestMapping(value="/get-cart/{user}/{item}", method=RequestMethod.GET)
    public Cart getCartByIds(@PathVariable("user") int userId, @PathVariable("item") int itemId) {
        return cartRepository.findByUserIdAndItemId(userId, itemId).orElse(null);
    }

    @RequestMapping(value="/add-to-cart", method=RequestMethod.POST)
    public void addToCart(@RequestBody Cart cart) {
        cartRepository.save(cart);
    }

    @RequestMapping(value="remove-from-cart", method = RequestMethod.DELETE)
    public void removeFromCart(@RequestBody Cart cart){
        cartRepository.delete(cart);
    }

    @RequestMapping(value="update-cart", method = RequestMethod.PUT)
    public void updateCart(@RequestBody Cart cart){
        cartRepository.save(cart);
    }

    // @RequestMapping(value = "/items/create", method = RequestMethod.POST)
    // public String addtoCart(@RequestBody Item item) {
    //     cartRepository.save(item);
    //     return "Added to cart";
    // }

    // @RequestMapping(value = "/items/remove", method = RequestMethod.POST)
    // public String removeFromCart(@RequestBody Item item) {
    //     cartRepository.delete(item);
    //     return "Removed from cart";
    // }
}
