package service.payment;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;

@ContextConfiguration(initializers = DatabaseSuite.Initializer.class)
public class DatabaseSuite {
  final static DockerImageName POSTGRES_IMAGE = DockerImageName.parse("postgres:13");
  private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>(POSTGRES_IMAGE);

  static class Initializer
      implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(@NonNull ConfigurableApplicationContext context) {
      Startables.deepStart(POSTGRES).join();

      TestPropertyValues.of(
              "spring.datasource.url=" + POSTGRES.getJdbcUrl(),
              "spring.datasource.username=" + POSTGRES.getUsername(),
              "spring.datasource.password=" + POSTGRES.getPassword())
          .applyTo(context);
    }
  }
}
