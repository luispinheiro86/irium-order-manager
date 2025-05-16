package com.irium.order.manager.service;

import com.irium.order.manager.entity.User;
import com.irium.order.manager.exception.OrderManagerException;
import com.irium.order.manager.model.request.UserRequest;
import com.irium.order.manager.model.response.UserResponse;
import com.irium.order.manager.repository.UserRepository;
import com.irium.order.manager.util.ManagerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import javax.transaction.Transactional;

@Service
public class UserService {

  private static final Logger logger = LoggerFactory.getLogger(UserService.class);
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
	this.userRepository = userRepository;
  }

  @Transactional
  public User createUser(UserRequest request) {
	User newUser = ManagerUtil.convertTo(request, User.class);
	return userRepository.save(newUser);
  }

  public List<User> findAllUsers() {
	return userRepository.findAll();
  }

  public User findUser(Long id) {
	logger.info("Getting user {}", id);
	if (Objects.isNull(id)) {
	  throw new OrderManagerException(HttpStatus.BAD_REQUEST, "Invalid user id");
	}
	return userRepository
		.findById(id).orElseThrow(() -> new OrderManagerException(HttpStatus.NOT_FOUND, "User " + id + " not found."));
  }

  @Transactional
  public User updateUser(Long id, UserRequest request) {
	logger.info("Update user {}", id);
	User user = findUser(id);
	user.setName(request.getName());
	user.setEmail(request.getEmail());
	return userRepository.save(user);
  }

  public void deleteUser(Long id) {
	logger.info("Delete user {}", id);
	userRepository.deleteById(id);
  }

}
