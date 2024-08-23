package com.eazybyte.loanservice;

import com.eazybyte.loanservice.dto.LoanContactInfo;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableConfigurationProperties(value={LoanContactInfo.class})
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title="Loan Microservice REST API Documentation",
				description = "Arvend's Bank Loan Microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name="Arvend",
						email="arvendrajan@gmail.com",
						url="https://www.arvend.com/"
				),
				license = @License(
						name ="MIT/Apache 2.0",
						url = "https://arvend.com/"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Arvend's Bank Loan Microservice REST Api Documentation",
				url="https://arvend.com"
		)
)
public class LoanServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanServiceApplication.class, args);
	}

}
