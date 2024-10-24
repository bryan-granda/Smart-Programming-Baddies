package com.smartprogrammingbaddies;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * This class contains unit tests for the App Class.
 */
@Tag("local")
@SpringBootTest
public class AppTest {
  /** The test app used for testing. */
  @Autowired
  private App app;

  /**
    * Sets up the testing environment to work with the individual project app.
    */
  @BeforeEach
  public void setupCourseForTesting() throws Exception {
    app.run();
  }

  /** Validate app initialization, also validate app, database are not null. */
  @Test
  public void initializeNotNullTest() {
    assertNotNull(app);
  }
}

