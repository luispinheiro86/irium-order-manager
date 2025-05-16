package com.irium.order.manager.enumeration;

public enum Status {

  PENDING("Pending"),
  COMPLETED("Completed");

  public final String description;

  private Status(String description) {
	this.description = description;
  }

}
