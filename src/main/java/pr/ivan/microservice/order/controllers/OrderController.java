package pr.ivan.microservice.order.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pr.ivan.microservice.order.domain.Order;
import pr.ivan.microservice.order.repositories.OrderRepository;
import pr.ivan.microservice.order.services.OrderService;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class OrderController {
    @Value("${spring.application.name}")
    private String applicationName;
    private static final String ENTITY_NAME = "order";
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderRepository orderRepository, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    @Transactional
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) throws URISyntaxException {
        log.debug("REST request to save Order : {}", order);
        if (order.getId() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A new order cannot already have an ID");
        }
        final var result = orderRepository.save(order);
        orderService.createOrder(result);

        HttpHeaders headers = new HttpHeaders();
        String message = String.format("A new %s is created with identifier %d", ENTITY_NAME, result.getId());
        /*
          В июне 2012 года рекомендация по использованию префикса "X-" стала официальной как RFC 6648.
          НЕ СЛЕДУЕТ добавлять к именам параметров префикс "X-" или что-то подобное Конструкции.
          НЕ СЛЕДУЕТ запрещать параметры с префиксом "X-" или подобным конструкций из регистрации.
          НЕ ДОЛЖЕН указывать, что параметр с префиксом "X-" или Подобные конструкции следует понимать как нестандартизированные.
          НЕ ДОЛЖЕН указывать, что параметр без префикса "X-" или Подобные конструкции следует понимать как стандартизированные.
          Обратите внимание, что "SHOULD NOT" ("discouraged") - это не то же самое, что "MUST NOT"
          ("запрещено"), см. также RFC 2119 для другой спецификации по этим ключевым словам.
          Другими словами, вы можете продолжать использовать заголовки с префиксом "X-", но это больше
          не рекомендуется официально, и вы определенно не можете документировать их, как если бы они
          были общедоступным стандартом.
          официальная рекомендация - просто называть их разумно без префикса "X-"
          вы можете продолжать использовать заголовки с префиксом "X-", но это больше
          не рекомендуется официально, и вы определенно не можете документировать их, как
          если бы они были общедоступным стандартом
         */
        headers.add("X-" + applicationName + "-alert", message);
        headers.add("X-" + applicationName + "-params", result.getId().toString());
        return ResponseEntity.created(new URI("/api/orders/" + result.getId())).headers(headers).body(result);
    }
}
