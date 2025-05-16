package com.irium.order.manager.model.request;

import javax.validation.constraints.NotBlank;

public class ItemRequest {

  @NotBlank(message = "Name is required")
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
