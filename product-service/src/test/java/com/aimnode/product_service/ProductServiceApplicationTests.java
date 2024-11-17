package com.aimnode.product_service;

import com.aimnode.product_service.dto.ProductRequest;
import com.aimnode.product_service.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper; // Use this import

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get; // Correct import
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper; // Use Spring Boot's configured ObjectMapper

	@Autowired
	private ProductRepository productRepository;

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@BeforeEach
	void setUp() {
		// Ensure that the repository is clean before each test
		productRepository.deleteAll();
	}

	@Test
	void shouldGetAllProducts() throws Exception {
		// Create and save a product first so that we can retrieve it
		ProductRequest productRequest = getProductRequest();
		String productRequestString = objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(post("/api/product")
						.contentType(MediaType.APPLICATION_JSON)
						.content(productRequestString))
				.andExpect(status().isCreated()); // Create a product first

		// Verify that the product is created by checking the size of the repository
		Assertions.assertEquals(1, productRepository.findAll().size());  // Only one product should exist now

		// Now, perform the GET request to fetch all products
		mockMvc.perform(get("/api/product"))
				.andExpect(status().isOk()) // Expect a 200 OK status
				.andExpect(jsonPath("$").isArray()) // Check that the response is an array
				.andExpect(jsonPath("$[0].name").value("iphone 13")) // Check that the first product is "iphone 13"
				.andExpect(jsonPath("$[0].description").value("iphone 13"))
				.andExpect(jsonPath("$[0].price").value(1200)); // Check the product details
	}

	private ProductRequest getProductRequest() {
		return ProductRequest.builder()
				.name("iphone 13")
				.description("iphone 13")
				.price(BigDecimal.valueOf(1200))
				.build();
	}
}
