package stock.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Item;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import stock.repository.StockRepository;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@AutoConfigureMockMvc
@SpringBootTest(properties = {"eureka.client.enabled=false"})
public class StockControllerTest {

    protected static final int ITEM_ID = 1;
    protected static final int SELLER_ID = 1;
    protected static final String ITEM_NAME = "Swedish Sock";
    protected static final String ITEM_DESCRIPTION = "Yeety Swedish Socks";
    protected static final int ITEM_QUANTITY = 10;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StockController stockController;

    @Test
    public void contextLoads() {
        assertThat( stockController ).isNotNull();
    }

    @Test
    public void getItem() throws Exception{
        Item item = stockController.getItem(ITEM_ID);

        assertNotNull(item);
        assertEquals(ITEM_ID, item.getItemId());
        assertEquals(ITEM_NAME, item.getName());

        mockMvc.perform(get("/item/1")
                .contentType(MediaType.APPLICATION_JSON ))
                .andExpect(status().isOk());
    }

    @Test
    public void getItems() throws Exception{
        List<Item> items = stockController.getItems().getItems();
        assertNotNull(items);

        Item item = items.get(0);
        assertEquals(ITEM_ID, item.getItemId());
        assertEquals(ITEM_NAME, item.getName());

        mockMvc.perform(get("/items")
                .contentType(MediaType.APPLICATION_JSON ))
                .andExpect(status().isOk());

    }

    @Test
    public void boughtReturnItem() throws Exception{
        Item item = stockController.getItem(ITEM_ID);
        assertNotNull(item);
        assertEquals(ITEM_QUANTITY, item.getQuantity());

        stockController.boughtItem(ITEM_ID);

        item = stockController.getItem(ITEM_ID);

        assertEquals(ITEM_QUANTITY-1, item.getQuantity());

        stockController.returnItem(ITEM_ID);

        item = stockController.getItem(ITEM_ID);

        assertEquals(ITEM_QUANTITY, item.getQuantity());
    }

    @Test
    public void createItem() throws Exception{
        ObjectMapper mapper = new ObjectMapper();

        Item item = new Item("New", "New Category", "New Description", 10, 5, SELLER_ID);
        stockController.createItem(item);

        mockMvc.perform(
                post( "/items/create" )
                        .contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"New\",\"category\":\"New Category\",\"description\":\"New Description\",\"price\":10.0,\"quantity\":5,\"sellerId\":1}"))
                        .andExpect( status().isOk() )
                        .andExpect( content().string( containsString( "Item added" ) ) );

    }

    @Test
    public void changeItemDescription() throws Exception{

        Item item = stockController.getItem(1);
        assertEquals(ITEM_DESCRIPTION, item.getDescription());

        mockMvc.perform(
                put( "/items/modify/1" )
                        .contentType(MediaType.APPLICATION_JSON).content("{\"description\":\"New Description\"}"))
                .andExpect( status().isOk() );

        item = stockController.getItem(1);
        assertNotNull(item);
        assertEquals("New Description", item.getDescription());

    }

    @Test
    public void getSellerItem() throws Exception{
        mockMvc.perform(get("/seller-stock/1")
                .contentType(MediaType.APPLICATION_JSON ))
                .andExpect(status().isOk());
    }

}