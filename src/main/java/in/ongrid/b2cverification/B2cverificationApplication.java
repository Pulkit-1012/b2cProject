
package in.ongrid.b2cverification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class B2cverificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(B2cverificationApplication.class, args);
	}

}
