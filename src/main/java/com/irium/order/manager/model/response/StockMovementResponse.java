package com.irium.order.manager.model.response;

import java.time.LocalDateTime;

public class StockMovementResponse {

  private Long id;
  private ItemResponse item;
  private Integer quantity;
  private LocalDateTime creationDate;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ItemResponse getItem() {
    return item;
  }

  public void setItem(ItemResponse item) {
    this.item = item;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public LocalDateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
  }

}
