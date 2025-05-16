package com.irium.order.manager.model.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class StockMovementRequest {

  @NotNull(message = "Item id is required")
  private Long itemId;

  @Positive(message = "Quantity is invalid")
  @NotNull(message = "Quantity is required")
  private Integer quantity;

  public Long getItemId() {
    return itemId;
  }

  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

}
