package seller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EntityScan("seller.model")
public class SellerApplication {
    public static void main(String[] args) {
            SpringApplication.run(SellerApplication.class, args);
        }
}
