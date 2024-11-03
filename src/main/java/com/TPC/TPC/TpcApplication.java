package com.TPC.TPC;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@Controller
@EnableCaching
@OpenAPIDefinition(
	info = @Info(
		title = "API do projeto LesSoft - TPC 2024",
		description = "Produto com foco no reconhecimento de consumo e na oferta de cashback.",
		contact = @Contact(name = "TPC", email = "thinkplancode@gmail.com"),
		version = "1.0.0"
	)
)
public class TpcApplication {

	public static void main(String[] args) {
		SpringApplication.run(TpcApplication.class, args);
	}

	@RequestMapping
	@ResponseBody
	public String home(){
		return "TPC - Challenge 2024 - API Rodando - Sprint 4";
	}
}
