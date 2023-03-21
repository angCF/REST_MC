package javeriana.ms.restador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RestadorApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestadorApplication.class, args);
	}

}
