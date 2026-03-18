package org.trysol.Trysol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity

public class TrysolEnterpriseSuiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrysolEnterpriseSuiteApplication.class, args);
	}

}