package pr.ivan.microservice.order.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pr.ivan.microservice.order.domain.Health;
import pr.ivan.microservice.order.domain.HealthStatus;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class HealthController {
    @GetMapping("/health")
    public ResponseEntity<Health> getHealth() {
        log.debug("REST request to get the Health Status");
        Health health = Health.builder().status(HealthStatus.UP).build();
        return ResponseEntity.ok().body(health);
    }
}
