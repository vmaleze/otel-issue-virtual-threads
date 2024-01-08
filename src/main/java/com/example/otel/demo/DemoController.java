package com.example.otel.demo;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class DemoController {

  private final ProductRepository productRepository;

  public DemoController(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @GetMapping
  public ResponseEntity<String> getTestData() {
    var ids = Arrays.asList("fc7cb0e6-6d6e-4254-923d-c91c8a5a61c9", "c847c28b-cfae-40d8-9e94-2563be777775");
    final ThreadFactory factory = Thread.ofVirtual().name("get-from-mysql-", 0).factory();
    try (var executor = Executors.newThreadPerTaskExecutor(factory)) {
      ids.forEach(id -> executor.submit(() -> {
        log.info("Get product with id [{}]",  id);
        try {
          productRepository.findById(id);
          log.info("FOUND IT");
        } catch (IllegalArgumentException e) {
          log.info("DID NO FOUND IT");
        }
      }));
    }
    return new ResponseEntity<>("Finished", HttpStatus.OK);
  }

}
