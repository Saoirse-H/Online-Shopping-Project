package consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EntityScan("consumer.model")
public class ConsumerApplication {
    public static void main(String[] args) {
            SpringApplication.run(ConsumerApplication.class, args);
        }
}
