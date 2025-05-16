package com.irium.order.manager.controller;

import static com.irium.order.manager.util.ManagerUtil.convertTo;
import static com.irium.order.manager.util.ManagerUtil.convertToList;
import com.irium.order.manager.entity.User;
import com.irium.order.manager.model.request.UserRequest;
import com.irium.order.manager.model.response.UserResponse;
import com.irium.order.manager.service.UserService;
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
@RequestMapping("/api/users")
@Tag(name = "Users", description = "User management")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
	this.userService = userService;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest request) {
	User user = userService.createUser(request);
	return new ResponseEntity<>(convertTo(user, UserResponse.class), HttpStatus.CREATED);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
	User user = userService.findUser(id);
	return ResponseEntity.ok(convertTo(user, UserResponse.class));
  }

  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody @Valid UserRequest request) {
	User user = userService.updateUser(id, request);
	return ResponseEntity.ok(convertTo(user, UserResponse.class));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
	userService.deleteUser(id);
	return ResponseEntity.noContent().build();
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<UserResponse>> getAllUsers() {
	List<User> users = userService.findAllUsers();
	return ResponseEntity.ok(convertToList(users, UserResponse.class));
  }

}
