package com.api.aggregator;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SwaggerConfig {

	@Bean
	public OpenAPI Api() {
		return new OpenAPI()
				.addSecurityItem(new SecurityRequirement()
						.addList("Bearer Authentication"))
				.components(new Components().addSecuritySchemes(
						"Bearer Authentication", createAPIKeyScheme()))
				.info(info());
	}

	private SecurityScheme createAPIKeyScheme() {
		return new SecurityScheme().type(SecurityScheme.Type.HTTP)
				.bearerFormat("JWT")
				.scheme("bearer");
	}

	private Info info() {

		Info info =  new Info();
		info.contact(this.contact());
		info.title("Project API Aggregator");
		info.description("Challenge Api Aggregator.");
		info.version("1.0");

		return info;
	}

	private Contact contact() {

		Contact contact =  new Contact();
		contact.setName("Thalles Jacobs Vieira");
		contact.setEmail("thalles_jacobs@hotmail.com");

		return contact;
	}
}
