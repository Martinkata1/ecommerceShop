package com.ecommerce.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * This class represents the starting point of the Spring Boot
 * application and includes the following important annotations:
 * @SpringBootApplication:
 * This is the main annotation for Spring Boot applications.
 * It combines three other important annotations:
 * @Configuration: Marks the class as the source of configurations.
 * @EnableAutoConfiguration: Allows Spring Boot to automatically configure the
 * application based on the dependencies available on the classpath.
 * @ComponentScan: Scans for Spring components (such as controllers, services, etc.) in the specified packages.
 * In the case of scanBasePackages, Spring Boot will scan all components in the com.ecommerce.library.* and com.ecommerce.admin.* packages.
 * @EnableJpaRepositories:
 * Enables Spring Data JPA support and tells Spring in which package to look for
 * repository interfaces (database access interfaces). In this case, it's com.ecommerce.library.repository.
 * @EntityScan:
 * This annotation tells Spring in which package to look for JPA entities (models that are associated with tables in the database).
 * Here it is the com.ecommerce.library.model package.
 * The main method:
 * This is a standard Java method that serves as the program's entry point. In this case, it calls
 * SpringApplication.run, which starts the Spring Boot application.
 */
@SpringBootApplication(scanBasePackages = {"com.ecommerce.library.*", "com.ecommerce.admin.*"})
@EnableJpaRepositories(value = "com.ecommerce.library.repository")
@EntityScan(value = "com.ecommerce.library.model")
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}
