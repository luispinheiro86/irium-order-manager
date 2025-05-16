package com.irium.order.manager.model.response;

import com.irium.order.manager.enumeration.Status;

import java.time.LocalDateTime;

public class OrderResponse {

  private Long id;
  private UserResponse user;
  private ItemResponse item;
  private int quantity;
  private Status status;
  private LocalDateTime creationDate;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public UserResponse getUser() {
    return user;
  }

  public void setUser(UserResponse user) {
    this.user = user;
  }

  public ItemResponse getItem() {
    return item;
  }

  public void setItem(ItemResponse item) {
    this.item = item;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public LocalDateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
  }

}
