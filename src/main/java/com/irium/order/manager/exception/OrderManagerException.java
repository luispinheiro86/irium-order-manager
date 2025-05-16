package com.irium.order.manager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class OrderManagerException extends ResponseStatusException {

  public OrderManagerException(HttpStatus status) {
	super(status);
  }

  public OrderManagerException(HttpStatus status, String message) {
	super(status, message);
  }

}
