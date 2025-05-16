package com.irium.order.manager.controller;

import static com.irium.order.manager.util.ManagerUtil.convertTo;
import com.irium.order.manager.entity.Item;
import com.irium.order.manager.model.request.ItemRequest;
import com.irium.order.manager.model.response.ItemResponse;
import com.irium.order.manager.service.ItemService;
import com.irium.order.manager.util.ManagerUtil;
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
@RequestMapping("/api/items")
@Tag(name = "Items", description = "Item management")
public class ItemController {

  private final ItemService itemService;

  public ItemController(ItemService itemService) {
	this.itemService = itemService;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ItemResponse> createItem(@RequestBody @Valid ItemRequest request) {
	Item item = itemService.createItem(request);
	return new ResponseEntity<>(convertTo(item, ItemResponse.class), HttpStatus.CREATED);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ItemResponse> getItem(@PathVariable Long id) {
	Item item = itemService.findItem(id);
	return ResponseEntity.ok(convertTo(item, ItemResponse.class));
  }

  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ItemResponse> updateItem(@PathVariable Long id, @RequestBody @Valid ItemRequest request) {
	Item item = itemService.updateItem(id, request);
	return ResponseEntity.ok(convertTo(item, ItemResponse.class));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
	itemService.deleteItem(id);
	return ResponseEntity.noContent().build();
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<ItemResponse>> getAllItems() {
	List<Item> items = itemService.findAllItems();
	return ResponseEntity.ok(ManagerUtil.convertToList(items, ItemResponse.class));
  }

}
