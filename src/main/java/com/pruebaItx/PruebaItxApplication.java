package com.pruebaItx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@SpringBootApplication
@EnableWebMvc
public class PruebaItxApplication {

  private static final Logger logger = LoggerFactory.getLogger(PruebaItxApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(PruebaItxApplication.class, args);
  }

  @EventListener(ApplicationReadyEvent.class)
  public void logApplicationStartup() {
    logger.info("Application started and ready to receive requests");
  }
}
