package pr.ivan.microservice.order.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pr.ivan.microservice.order.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
