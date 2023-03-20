package javeriana.ms.multiplicador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MultiplicadorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiplicadorApplication.class, args);
	}

}
