package com.irium.order.manager.service;

import com.irium.order.manager.entity.Item;
import com.irium.order.manager.entity.Order;
import com.irium.order.manager.entity.StockMovement;
import com.irium.order.manager.entity.User;
import com.irium.order.manager.enumeration.Status;
import com.irium.order.manager.exception.OrderManagerException;
import com.irium.order.manager.model.request.OrderRequest;
import com.irium.order.manager.repository.OrderRepository;
import com.irium.order.manager.repository.StockMovementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.transaction.Transactional;

@Service
public class OrderService {

  private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

  private final OrderRepository orderRepository;
  private final ItemService itemService;
  private final UserService userService;
  private final NotificationService notificationService;
  private final StockMovementRepository stockMovementRepository;

  public OrderService(OrderRepository orderRepository, ItemService itemService, UserService userService,
					  StockMovementRepository stockMovementRepository, NotificationService notificationService) {
	this.orderRepository = orderRepository;
	this.itemService = itemService;
	this.userService = userService;
	this.notificationService = notificationService;
	this.stockMovementRepository = stockMovementRepository;
  }

  @Transactional
  public Order createOrder(OrderRequest request) {
	Item item = itemService.findItem(request.getItemId());
	User user = userService.findUser(request.getUserId());
	Order order = buildOrder(user, item, request.getQuantity());
	Order savedOrder = orderRepository.save(order);
	logger.info("Order {} created.", savedOrder.getId());
	manageOrder(savedOrder);
	return savedOrder;
  }

  public List<Order> findAllOrders() {
	return orderRepository.findAll();
  }

  public Order findOrder(Long id) {
	logger.info("Getting order {}", id);
	if (Objects.isNull(id)) {
	  throw new OrderManagerException(HttpStatus.BAD_REQUEST, "Invalid order id");
	}
	return orderRepository
		.findById(id)
		.orElseThrow(() -> new OrderManagerException(HttpStatus.NOT_FOUND, "Order " + id + " not found."));
  }

  @Transactional
  public Order updateOrder(Long id, OrderRequest request) {
	logger.info("Update order {}", id);
	Order order = findOrder(id);
	if (order.getStatus().equals(Status.COMPLETED)) {
	  throw new OrderManagerException(HttpStatus.BAD_REQUEST, "Order id " + id + " is completed.");
	}
	Item item = itemService.findItem(request.getItemId());
	User user = userService.findUser(request.getUserId());
	order.setItem(item);
	order.setUser(user);
	order.setQuantity(request.getQuantity());
	return orderRepository.save(order);
  }

  public void deleteOrder(Long id) {
	logger.info("Delete order {}", id);
	orderRepository.deleteById(id);
  }

  private Order buildOrder(User user, Item item, Integer quantity) {
	Order order = new Order();
	order.setUser(user);
	order.setItem(item);
	order.setQuantity(quantity);
	order.setStatus(Status.PENDING);
	order.setStockMovements(new ArrayList<StockMovement>());
	return order;
  }

  private void manageOrder(Order order) {
	List<StockMovement> stockMovements = stockMovementRepository.findAvailableStockMovementsByItem(order.getItem());
	int totalAvailable = stockMovements.stream().map(StockMovement::getAvailable)
									   .mapToInt(Integer::intValue).sum();
	if (order.getQuantity() <= totalAvailable) {
	  int quantity = order.getQuantity();
	  for (StockMovement movement : stockMovements) {
		if (movement.getAvailable() >= quantity) {
		  movement.setAvailable(movement.getAvailable() - quantity);
		  order.getStockMovements().add(movement);
		  break;
		}
		quantity -= movement.getAvailable();
		movement.setAvailable(0);
		order.getStockMovements().add(movement);
	  }
	  order.setStatus(Status.COMPLETED);
	  orderRepository.save(order);
	  logger.info("Order {} is completed.", order.getId());
	  CompletableFuture.runAsync(() -> notificationService.sendEmail(order.getUser().getEmail(), order.getId()));
	}
  }

  @Transactional
  public void handleStockMovement(Item item) {
	logger.info("Handle stock movement for item {}", item.getName());
	List<Order> orders = orderRepository.findAllOrdersByItemAndStatus(item, Status.PENDING);
	for (Order order : orders) {
	  logger.info("Try to fulfill order {}", order.getId());
	  manageOrder(order);
	}
  }

}
