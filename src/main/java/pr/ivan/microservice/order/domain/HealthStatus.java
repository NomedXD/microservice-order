package pr.ivan.microservice.order.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public enum HealthStatus {
    UP("UP"),
    DOWN("DOWN");

    private final String status;
}
