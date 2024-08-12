package com.ecommerce.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @EnableAutoConfiguration, and @ComponentScan.
 * It marks the main class of a Spring Boot application and triggers
 * auto-configuration and component scanning.
 * scanBasePackages: Specifies the base packages to scan for Spring
 * components. It includes both com.ecommerce.library and com.ecommerce.customer packages,
 * ensuring that Spring scans these packages for components like controllers, services, and repositories.
 */
@SpringBootApplication(scanBasePackages = {"com.ecommerce.library.*", "com.ecommerce.customer.*"})
/**
 * This annotation enables JPA repositories in your application.
 * It configures Spring Data JPA to scan the specified package for repository interfaces.
 * value: Defines the base package for scanning JPA repositories.
 * Here, it points to com.ecommerce.library.repository, where your repository
 * interfaces are located.
 */
@EnableJpaRepositories(value = "com.ecommerce.library.repository")
/**
 * Purpose: This annotation is used to specify the base package for scanning JPA entity classes. It helps Spring Data JPA to find your entity classes.
 * value: Defines the base package for scanning JPA entities. Here, it points to com.ecommerce.library.model, where your entity classes are located.
 */
@EntityScan(value = "com.ecommerce.library.model")
public class CustomerApplication {

    /**
     * SpringApplication.run(CustomerApplication.class, args):
     * This method is used to launch the Spring Boot application.
     * It creates an application context, performs the auto-configuration,
     * and starts the embedded server (e.g., Tomcat).
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }

}
