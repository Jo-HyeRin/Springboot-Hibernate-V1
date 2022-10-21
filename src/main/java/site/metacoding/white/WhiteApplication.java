package site.metacoding.white;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WhiteApplication {

	public static void main(String[] args) {
		System.out.println("메롱 : main()");
		SpringApplication.run(WhiteApplication.class, args);
	}

}
