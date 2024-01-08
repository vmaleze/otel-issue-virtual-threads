package com.example.otel.demo;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "products")
public class ProductEntity {

  @Id
  private String id;

  private String name;

  private String description;
}
