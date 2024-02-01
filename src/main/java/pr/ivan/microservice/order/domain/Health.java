package pr.ivan.microservice.order.domain;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class Health {
    private HealthStatus status;
}
