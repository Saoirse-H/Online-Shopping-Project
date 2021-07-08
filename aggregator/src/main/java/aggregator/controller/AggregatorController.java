package aggregator.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import aggregator.exceptions.*;

import model.*;
import response.*;

@RestController
public class AggregatorController {

    String aggregatorURL = "http://localhost:8080/";

    @RequestMapping(value = "/add-to-cart/{user}/{item}", method=RequestMethod.POST) 
    public void addToCart(@PathVariable("user") int userId, @PathVariable("item") int itemId) {
        RestTemplate restTemplate = new RestTemplate();
        //Get item from stock
        Item item = restTemplate.getForObject(aggregatorURL + "stock-service/item/{id}", Item.class, itemId);
        //update stock
        restTemplate.postForObject(aggregatorURL + "stock-service/bought/{id}", null, Void.class, itemId);
        //Make cart object
        String existingCart = restTemplate.getForObject(aggregatorURL + "cart-service/already-in-cart/{user}/{item}", String.class, userId, itemId);
        Cart cart = new Cart();
        
        if(Boolean.valueOf(existingCart)) {
            cart = restTemplate.getForObject(aggregatorURL + "cart-service/get-cart/{user}/{item}", Cart.class, userId, itemId);
            cart.setQuantity(cart.getQuantity()+1);
            cart.setPrice(cart.getPrice()+item.getPrice());
        }
        else {
            cart = new Cart(userId, item.getItemId(), item.getPrice());
        }
        //API call to cart
        HttpEntity<Cart> request = new HttpEntity<>(cart);
        restTemplate.postForObject(aggregatorURL + "cart-service/add-to-cart", request, Void.class);        
    }

    @RequestMapping(value = "/add-to-stock", method=RequestMethod.POST)
    public void addToStock(@RequestBody Item item) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Item> update = new HttpEntity<>(item);
        restTemplate.postForObject(aggregatorURL + "stock-service/items/create" , update, String.class);
    }

    @RequestMapping(value = "/remove-from-stock/{sellerId}/{itemId}", method=RequestMethod.POST)
    public void removeFromStock(@PathVariable("itemId") int itemId, @PathVariable("sellerId") int sellerId) {
        RestTemplate restTemplate = new RestTemplate();
        
        Item itemSeller = new Item();
        itemSeller = restTemplate.getForObject(aggregatorURL + "stock-service/item/{id}", Item.class, itemId);

        if (itemSeller.getSellerId() == sellerId) {
            restTemplate.postForObject(aggregatorURL + "stock-service/items/remove/{id}", null, String.class, itemId);
        } else {
            throw new SellerNotFoundException("You do not have permision to access stock of seller: " + sellerId);
        }
    }
    
    @RequestMapping(value="/items", method = RequestMethod.GET)
    public ItemList getItems() {
        RestTemplate restTemplate = new RestTemplate();
        ItemList items = restTemplate.getForObject(aggregatorURL + "stock-service/items", ItemList.class);
        return items;
    }

    @RequestMapping(value="/cart/{user}", method = RequestMethod.GET)
    public ItemList getCart(@PathVariable("user") int userId) {
        RestTemplate restTemplate = new RestTemplate();
        CartList carts = restTemplate.getForObject(aggregatorURL + "cart-service/get-cart/{user}", CartList.class, userId);
        ItemList items = new ItemList();
        if(carts.getCarts() != null) {
            List<Item> cartItems = new ArrayList<Item>();

            for (Cart c : carts.getCarts()) {
                Item stockItem = restTemplate.getForObject(aggregatorURL + "stock-service/item/{id}", Item.class, c.getItemId());
                Item cartItem = new Item(stockItem.getName(), stockItem.getCategory(), stockItem.getDescription(), c.getPrice(), c.getQuantity(), stockItem.getSellerId());
                cartItem.setItemId(stockItem.getItemId());
                cartItems.add(cartItem);
            }

            items.setItems(cartItems);
        }
        return items;
    }

    @RequestMapping(value="/view-my-stock/{id}", method=RequestMethod.GET)
    public ItemList getSellersItems(@PathVariable("id") int sellerId) {
        RestTemplate restTemplate = new RestTemplate();
        ItemList items = restTemplate.getForObject(aggregatorURL + "stock-service/seller-stock/{id}", ItemList.class, sellerId);
        return items;
    }

    @RequestMapping(value = "/remove-from-cart/{user}/{item}", method=RequestMethod.POST)
    public void removeFromCart(@PathVariable("user") int userId, @PathVariable("item") int itemId) {
        RestTemplate restTemplate = new RestTemplate();

        Item item = restTemplate.getForObject(aggregatorURL + "stock-service/item/{id}", Item.class, itemId);
        restTemplate.postForObject(aggregatorURL + "stock-service/return/{id}", null, Void.class, itemId);

        Cart cart = restTemplate.getForObject(aggregatorURL + "cart-service/get-cart/{user}/{item}", Cart.class, userId, itemId);
        cart.setQuantity(cart.getQuantity()-1);
        cart.setPrice(cart.getPrice()-item.getPrice());

        HttpEntity<Cart> request = new HttpEntity<>(cart);

        if(cart.getQuantity() == 0) {
            restTemplate.exchange(aggregatorURL + "cart-service/remove-from-cart", HttpMethod.DELETE, request, Cart.class);
        } else{
            restTemplate.exchange(aggregatorURL + "cart-service/update-cart", HttpMethod.PUT, request, Cart.class);
        }
    }
}
