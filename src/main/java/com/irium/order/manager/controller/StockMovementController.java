package com.irium.order.manager.controller;

import static com.irium.order.manager.util.ManagerUtil.convertTo;
import static com.irium.order.manager.util.ManagerUtil.convertToList;
import com.irium.order.manager.entity.StockMovement;
import com.irium.order.manager.model.request.StockMovementRequest;
import com.irium.order.manager.model.response.StockMovementResponse;
import com.irium.order.manager.service.StockMovementService;
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
@RequestMapping("/api/stock-movement")
@Tag(name = "Stock-Movement", description = "Stock movement management")
public class StockMovementController {

  private final StockMovementService stockMovementService;

  public StockMovementController(StockMovementService stockMovementService) {
	this.stockMovementService = stockMovementService;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<StockMovementResponse> createStockMovement(@RequestBody @Valid StockMovementRequest request) {
	StockMovement stockMovement = stockMovementService.createStockMovement(request);
	return new ResponseEntity<>(convertTo(stockMovement, StockMovementResponse.class), HttpStatus.CREATED);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<StockMovementResponse> getStockMovement(@PathVariable Long id) {
	StockMovement stockMovement = stockMovementService.findStockMovement(id);
	return ResponseEntity.ok(convertTo(stockMovement, StockMovementResponse.class));
  }

  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<StockMovementResponse> updateStockMovement(@PathVariable Long id, @RequestBody @Valid StockMovementRequest request) {
	StockMovement stockMovement = stockMovementService.updateStockMovement(id, request);
	return ResponseEntity.ok(convertTo(stockMovement, StockMovementResponse.class));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteStockMovement(@PathVariable Long id) {
	stockMovementService.deleteStockMovement(id);
	return ResponseEntity.noContent().build();
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<StockMovementResponse>> getAllStockMovements() {
	List<StockMovement> stockMovements = stockMovementService.findAllStockMovements();
	return ResponseEntity.ok(convertToList(stockMovements, StockMovementResponse.class));
  }

}
