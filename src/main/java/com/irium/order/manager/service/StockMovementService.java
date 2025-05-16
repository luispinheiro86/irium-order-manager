package com.irium.order.manager.service;

import com.irium.order.manager.entity.Item;
import com.irium.order.manager.entity.StockMovement;
import com.irium.order.manager.exception.OrderManagerException;
import com.irium.order.manager.model.request.StockMovementRequest;
import com.irium.order.manager.repository.StockMovementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.transaction.Transactional;

@Service
public class StockMovementService {

  private static final Logger logger = LoggerFactory.getLogger(StockMovementService.class);

  private final StockMovementRepository stockMovementRepository;
  private final ItemService itemService;
  private final OrderService orderService;

  public StockMovementService(StockMovementRepository stockMovementRepository, ItemService itemService, OrderService orderService) {
	this.stockMovementRepository = stockMovementRepository;
	this.itemService = itemService;
	this.orderService = orderService;
  }

  @Transactional
  public StockMovement createStockMovement(StockMovementRequest request) {
	Item item = itemService.findItem(request.getItemId());
	StockMovement stockMovement = new StockMovement();
	stockMovement.setQuantity(request.getQuantity());
	stockMovement.setAvailable(request.getQuantity());
	stockMovement.setItem(item);
	CompletableFuture.runAsync(() -> orderService.handleStockMovement(item));
	logger.info("Stock movement created for item {}", item.getName());
	return stockMovementRepository.save(stockMovement);
  }

  public List<StockMovement> findAllStockMovements() {
	return stockMovementRepository.findAll();
  }

  public StockMovement findStockMovement(Long id) {
	logger.info("Getting Stock Movement {}", id);
	if (Objects.isNull(id)) {
	  throw new OrderManagerException(HttpStatus.BAD_REQUEST, "Invalid Stock Movement id");
	}
	return stockMovementRepository
		.findById(id)
		.orElseThrow(() -> new OrderManagerException(HttpStatus.NOT_FOUND, "Stock Movement " + id + " not found."));
  }

  @Transactional
  public StockMovement updateStockMovement(Long id, StockMovementRequest request) {
	logger.info("Update Stock Movement {}", id);
	StockMovement stockMovement = findStockMovement(id);
	stockMovement.setQuantity(request.getQuantity());
	Item item = itemService.findItem(request.getItemId());
	stockMovement.setItem(item);
	return stockMovementRepository.save(stockMovement);
  }

  public void deleteStockMovement(Long id) {
	logger.info("Delete Stock Movement {}", id);
	stockMovementRepository.deleteById(id);
  }

}
