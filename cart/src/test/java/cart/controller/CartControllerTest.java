package cart.controller;

import cart.repository.CartRepository;
import model.Cart;
import model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@AutoConfigureMockMvc
@SpringBootTest(properties = {"eureka.client.enabled=false"})
class CartControllerTest {

    protected static final int USER_ID = 1;
    protected static final int ITEM_ID = 1;
    protected static final double PRICE = 10;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CartController cartController;

    @BeforeEach
    void setUp() {
        cartController.addToCart(new Cart(USER_ID,ITEM_ID,PRICE));
    }

    @Test
    public void contextLoads() {
        assertThat( cartController ).isNotNull();
    }

    @Test
    void getCart() throws Exception {
        List<Cart> carts = StreamSupport.stream(cartController.getCart().spliterator(), false)
                .collect(Collectors.toList());
        assertNotNull(carts);

        mockMvc.perform(get("/cart")
                .contentType(MediaType.APPLICATION_JSON ))
                .andExpect(status().isOk());
    }

    @Test
    void addToCart_getCartsById() throws Exception{
        mockMvc.perform(get("/already-in-cart/1/1")
                .contentType(MediaType.APPLICATION_JSON ))
                .andExpect(status().isOk());

        List<Cart> carts = StreamSupport.stream(cartController.getCart().spliterator(), false)
                .collect(Collectors.toList());

        int size = carts.size();
        assertEquals(size, carts.size());
        cartController.addToCart(new Cart(USER_ID+1, ITEM_ID+1, PRICE));

        mockMvc.perform(get("/already-in-cart/1/1")
                .contentType(MediaType.APPLICATION_JSON ))
                .andExpect(status().isOk());

        List<Cart> carts1 = StreamSupport.stream(cartController.getCart().spliterator(), false)
                .collect(Collectors.toList());

        assertEquals(size+1, carts1.size());

        Cart cart = cartController.getCartByIds(USER_ID, ITEM_ID);
        assertNotNull(cart);
        assertEquals(ITEM_ID, cart.getItemId());
        assertEquals(USER_ID, cart.getUserId());

        mockMvc.perform(get("/get-cart/1/1")
                .contentType(MediaType.APPLICATION_JSON ))
                .andExpect(status().isOk());
    }

    @Test
    public void getCard() throws Exception{
        mockMvc.perform(get("/get-cart/1")
                .contentType(MediaType.APPLICATION_JSON ))
                .andExpect(status().isOk());
    }
}