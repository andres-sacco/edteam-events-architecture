package com.edteam.reservations.messaging.util;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BaseITest {

    static DockerComposeContainer dockerComposeContainer = new DockerComposeContainer(
            new File("src/test/resources/docker/docker-compose.yml"))
                    .waitingFor("schema-registry",
                            Wait.forLogMessage(".*Server started, listening for requests.*\\n", 1))
                    .waitingFor("api-reservation-db",
                            Wait.forLogMessage(".*MySQL init process done. Ready for start up.*\\n", 1))
                    .waitingFor("kafka-init", Wait.forLogMessage(".*topic reservations-payments was create.*\\n", 1))
                    .withLocalCompose(true);

    @BeforeAll
    static void setUp() {
        dockerComposeContainer.start();
    }

    @AfterAll
    static void tearDown() {
        dockerComposeContainer.stop();
    }
}
