package consumer.controller;

import consumer.model.Consumer;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@AutoConfigureMockMvc
@SpringBootTest(properties = {"eureka.client.enabled=false"})
class ConsumerControllerTest {

    protected static final int CONSUMER_ID = 1;
    protected static final String CONSUMER_USERNAME = "Sushi";
    protected static final String CONSUMER_PASSWORD = "Olive";

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ConsumerController consumerController;

    @Test
    public void contextLoads() {
        assertThat( consumerController ).isNotNull();
    }

    @Test
    void getUsers() throws Exception {
        List<Consumer> consumers = StreamSupport.stream(consumerController.getUsers().spliterator(), false)
                .collect(Collectors.toList());

        assertNotNull(consumers);

        Consumer consumer = consumers.get(0);
        assertEquals(CONSUMER_ID, consumer.getId());
        assertEquals(CONSUMER_USERNAME, consumer.getUsername());
        assertEquals(CONSUMER_PASSWORD, consumer.getPassword());

        mockMvc.perform(get("/all-consumers")
                .contentType(MediaType.APPLICATION_JSON ))
                .andExpect(status().isOk());
    }

    @Test
    void getUserByUserAndPass() {
        Consumer consumer = consumerController.getUserByUserAndPass(CONSUMER_USERNAME, CONSUMER_PASSWORD);
        assertNotNull(consumer);
        assertEquals(CONSUMER_USERNAME, consumer.getUsername());
        assertEquals(CONSUMER_PASSWORD, consumer.getPassword());
    }

    @Test
    void getUsername(){
        Consumer consumer = consumerController.getUsername(CONSUMER_USERNAME);
        assertNotNull(consumer);
        assertEquals(CONSUMER_USERNAME, consumer.getUsername());
    }


    @Test
    void getConsumer() throws Exception{
        Consumer consumer = consumerController.getConsumer(CONSUMER_ID);
        assertNotNull(consumer);
        assertEquals(CONSUMER_USERNAME, consumer.getUsername());
        assertEquals(CONSUMER_ID, consumer.getId());
    }
}