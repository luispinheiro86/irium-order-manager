package com.irium.order.manager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.irium.order.manager.entity.Item;
import com.irium.order.manager.entity.StockMovement;
import com.irium.order.manager.exception.OrderManagerException;
import com.irium.order.manager.model.request.StockMovementRequest;
import com.irium.order.manager.repository.StockMovementRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class StockMovementServiceTest {

  @Mock
  private StockMovementRepository stockMovementRepository;

  @Mock
  private ItemService itemService;

  @Mock
  private OrderService orderService;

  @InjectMocks
  private StockMovementService stockMovementService;

  @Test
  void givenValidStockMovementRequest_whenCreateStockMovement_shouldReturnStockMovement() {
    StockMovementRequest request = new StockMovementRequest();
    request.setItemId(1L);
    request.setQuantity(10);
    Item item = Instancio.create(Item.class);
    when(itemService.findItem(1L)).thenReturn(item);
    when(stockMovementRepository.save(any(StockMovement.class))).thenAnswer(invocation -> invocation.getArgument(0));
    StockMovement saved = stockMovementService.createStockMovement(request);
    assertNotNull(saved);
    assertEquals(10, saved.getQuantity());
    assertEquals(item, saved.getItem());
    verify(orderService, times(1)).handleStockMovement(item);
    verify(stockMovementRepository, times(1)).save(any(StockMovement.class));
  }

  @Test
  void givenStockMovementId_whenFindStockMovement_shouldReturnStockMovement(){
    StockMovement stockMovement = Instancio.create(StockMovement.class);
    when(stockMovementRepository.findById(1L)).thenReturn(Optional.of(stockMovement));
    StockMovement result = stockMovementService.findStockMovement(1L);
    assertNotNull(result);
  }

  @Test
  void givenInvalidStockMovementId_whenFindStockMovement_shouldReturnException(){
    Exception exception = assertThrows(OrderManagerException.class, () -> stockMovementService.findStockMovement(null));
    assertTrue(exception.getMessage().contains("Invalid Stock Movement id"));
  }

}
