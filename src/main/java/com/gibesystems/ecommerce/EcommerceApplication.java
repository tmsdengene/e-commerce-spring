package com.gibesystems.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

	// Seed data for Category and Product if not present
	@org.springframework.context.annotation.Bean
	public org.springframework.boot.CommandLineRunner seedData(
			com.gibesystems.ecommerce.category.CategoryRepository categoryRepository,
			com.gibesystems.ecommerce.product.ProductRepository productRepository) {
		return args -> {
			// Seed Categories
			String[] categoryNames = {"Electronics", "Books", "Home Decor", "Toys", "Fashion"};
			java.util.Map<String, com.gibesystems.ecommerce.category.Category> categoryMap = new java.util.HashMap<>();
			for (String catName : categoryNames) {
				com.gibesystems.ecommerce.category.Category cat = categoryRepository.findAll().stream()
						.filter(c -> catName.equalsIgnoreCase(c.getName()))
						.findFirst()
						.orElseGet(() -> {
							com.gibesystems.ecommerce.category.Category newCat = new com.gibesystems.ecommerce.category.Category();
							newCat.setName(catName);
							return categoryRepository.save(newCat);
						});
				categoryMap.put(catName, cat);
			}

			// Seed Products
			Object[][] products = new Object[][]{
				{"Landscape Frame", "Electronics", 49.99, 100, "A beautiful landscape frame for your home or office.", "/uploads/products/landscape-frame.jpg"},
				{"Wireless Mouse", "Electronics", 19.99, 200, "Ergonomic wireless mouse with long battery life.", "/uploads/products/landscape-frame.jpg"},
				{"Java Programming", "Books", 29.99, 50, "Comprehensive guide to Java programming.", "/uploads/products/landscape-frame.jpg"},
				{"Modern Vase", "Home Decor", 24.99, 80, "Stylish vase for modern interiors.", "/uploads/products/landscape-frame.jpg"},
				{"Teddy Bear", "Toys", 14.99, 150, "Soft and cuddly teddy bear for kids.", "/uploads/products/landscape-frame.jpg"},
				{"Denim Jacket", "Fashion", 59.99, 60, "Trendy denim jacket for all seasons.", "/uploads/products/landscape-frame.jpg"}
			};
			for (Object[] prod : products) {
				String name = (String) prod[0];
				String catName = (String) prod[1];
				double price = (double) prod[2];
				int stock = (int) prod[3];
				String desc = (String) prod[4];
				String img = (String) prod[5];
				if (productRepository.findByName(name).isEmpty()) {
					com.gibesystems.ecommerce.product.Product product = new com.gibesystems.ecommerce.product.Product();
					product.setName(name);
					product.setProductUuid(java.util.UUID.randomUUID().toString());
					product.setCategory(categoryMap.get(catName));
					product.setPrice(java.math.BigDecimal.valueOf(price));
					product.setStockQuantity(stock);
					product.setDescription(desc);
					product.setImageUrl(img);
					product.setActive(true);
					product.setCreatedBy("seed");
					product.setCreatedAt(java.time.LocalDateTime.now());
					productRepository.save(product);
				}
			}
		};
	}

}
