package com.irium.order.manager.controller;

import static com.irium.order.manager.util.ManagerUtil.convertTo;
import com.irium.order.manager.entity.Order;
import com.irium.order.manager.model.request.OrderRequest;
import com.irium.order.manager.model.response.OrderResponse;
import com.irium.order.manager.service.OrderService;
import com.irium.order.manager.util.ManagerUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Orders", description = "Order management")
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
	this.orderService = orderService;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<OrderResponse> createOrder(@RequestBody @Valid OrderRequest request) {
	Order order = orderService.createOrder(request);
	return new ResponseEntity<>(convertTo(order, OrderResponse.class), HttpStatus.CREATED);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id) {
	Order order = orderService.findOrder(id);
	return ResponseEntity.ok(convertTo(order, OrderResponse.class));
  }

  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<OrderResponse> updateOrder(@PathVariable Long id, @RequestBody @Valid OrderRequest request) {
	Order order = orderService.updateOrder(id, request);
	return ResponseEntity.ok(convertTo(order, OrderResponse.class));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
	orderService.deleteOrder(id);
	return ResponseEntity.noContent().build();
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<OrderResponse>> getAllOrders() {
	List<Order> orders = orderService.findAllOrders();
	return ResponseEntity.ok(ManagerUtil.convertToList(orders, OrderResponse.class));
  }

}
