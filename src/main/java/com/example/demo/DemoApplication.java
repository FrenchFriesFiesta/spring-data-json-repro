package com.example.demo;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;


@SpringBootApplication
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}

@Entity
class Car {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@JdbcTypeCode(SqlTypes.JSON)
	private Config jsonField;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Config getJsonField() {
		return jsonField;
	}

	public void setJsonField(Config jsonField) {
		this.jsonField = jsonField;
	}
}

class Config {
	private int horsePower;
	private String manufacturer;
	private String brand;
	private Instant lastModifiedDate;

	public int getHorsePower() {
		return horsePower;
	}

	public void setHorsePower(int horsePower) {
		this.horsePower = horsePower;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Instant getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Instant lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
}

@RestController
@RequestMapping("/api")
class Api {

	private final Repo repo;

	Api(Repo repo) {
		this.repo = repo;
	}

	@GetMapping
	public ResponseEntity<?> get() {
		Car c = new Car();
		c.setName("a car");
		c.setJsonField(buildBasicConfig());
		c = repo.save(c);
		return ResponseEntity.ok().build();
	}

	private Config buildBasicConfig() {
		Config config = new Config();
		config.setBrand("bmw");
		config.setHorsePower(123);
		config.setManufacturer("bmw2");
		config.setLastModifiedDate(Instant.now());
		return config;
	}
}


interface Repo extends JpaRepository<Car, Long> {
}
