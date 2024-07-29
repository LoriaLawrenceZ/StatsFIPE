package br.com.zimbres.StatsFipe;

import br.com.zimbres.StatsFipe.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StatsFipeApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(StatsFipeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.menu();
	}
}
