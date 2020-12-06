package net.playpit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

// TODO upload on github
// TODO upload docker container on dockerhub
// TODO try running inside kubernetes, create manifest (deployment)
// TODO pass environment variable to change startup time
// TODO create readiness probe
// TODO create liveness probe
@RestController
@SpringBootApplication
public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private static final String HEALTHY_TEXT = "Application is healthy :)";
    private static final String UNHEALTHY_TEXT = "Application is unhealthy :(";
    private static final String READY_TEXT = "Application is ready :)";
    private static final String NOT_READY_TEXT = "Application is not ready :)";

    @Value("${STARTUP_DELAY:0}")
    private int startupDelay;

    private boolean healthy = true;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @PostConstruct
    public void beforeStart() throws InterruptedException {
        if (startupDelay > 0) {
            log.info("Application startup paused on {} ms", startupDelay);
            Thread.sleep(startupDelay);
        }
    }

    @GetMapping("/health")
    @ResponseBody
    public ResponseEntity<String> health() {
        if (healthy) {
            return new ResponseEntity<>(HEALTHY_TEXT, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(UNHEALTHY_TEXT, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("/health")
    @ResponseBody
    public String makeUnhealthy() {
        healthy = false;
        log.info("Application is set to unhealthy");
        return UNHEALTHY_TEXT;
    }

    @PostMapping("/health")
    @ResponseBody
    public String makeHealthy() {
        healthy = true;
        log.info("Application is set to healthy");
        return HEALTHY_TEXT;
    }

    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<String> ready() {
        return new ResponseEntity<>(READY_TEXT, HttpStatus.OK);
    }
}