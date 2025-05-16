package com.irium.order.manager.model.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class OrderRequest {

  @NotNull(message = "Item id is required")
  private Long itemId;

  @NotNull(message = "User id is required")
  private Long userId;

  @Positive(message = "Quantity is invalid")
  @NotNull(message = "Quantity is required")
  private Integer quantity;

  public Long getItemId() {
    return itemId;
  }

  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

}
