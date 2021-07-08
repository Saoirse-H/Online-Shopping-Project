package seller.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import seller.model.Seller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@AutoConfigureMockMvc
@SpringBootTest(properties = {"eureka.client.enabled=false"})
public class SellerControllerTest {

    protected static final int SELLER_ID = 1;
    protected static final String SELLER_USERNAME = "craftyGurl";
    protected static final String SELLER_PASSWORD = "Oscar";

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SellerController sellerController;

    @Test
    public void contextLoads() {
        assertThat( sellerController ).isNotNull();
    }

    @Test
    public void getSellers() throws Exception{
        List<Seller> sellers = StreamSupport.stream(sellerController.getSellers().spliterator(), false)
                .collect(Collectors.toList());

        assertNotNull(sellers);

        Seller seller = sellers.get(0);
        assertEquals(SELLER_ID, seller.getId());
        assertEquals(SELLER_USERNAME, seller.getUsername());
        assertEquals(SELLER_PASSWORD, seller.getPassword());

        mockMvc.perform(get("/all-sellers")
                .contentType(MediaType.APPLICATION_JSON ))
                .andExpect(status().isOk());
    }

    @Test
    public void getUserByUserAndPass() {
        Seller seller = sellerController.getUserByUserAndPass(SELLER_USERNAME, SELLER_PASSWORD);
        assertNotNull(seller);
        assertEquals(SELLER_USERNAME, seller.getUsername());
        assertEquals(SELLER_PASSWORD, seller.getPassword());
    }

    @Test
    public void getUsername(){
        Seller seller = sellerController.getUsername(SELLER_USERNAME);
        assertNotNull(seller);
        assertEquals(SELLER_USERNAME, seller.getUsername());
    }


    @Test
    public void getConsumer() throws Exception{
        Seller seller = sellerController.getSeller(SELLER_ID);
        assertNotNull(seller);
        assertEquals(SELLER_USERNAME, seller.getUsername());
        assertEquals(SELLER_ID, seller.getId());
    }
}