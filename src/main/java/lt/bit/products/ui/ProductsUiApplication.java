package lt.bit.products.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@SpringBootApplication
public class ProductsUiApplication{

	public static void main(String[] args) {
		SpringApplication.run(ProductsUiApplication.class, args);
	}


}
