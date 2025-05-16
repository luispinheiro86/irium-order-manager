package com.irium.order.manager.service;

import static com.irium.order.manager.util.ManagerUtil.convertTo;
import com.irium.order.manager.entity.Item;
import com.irium.order.manager.exception.OrderManagerException;
import com.irium.order.manager.model.request.ItemRequest;
import com.irium.order.manager.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import javax.transaction.Transactional;

@Service
public class ItemService {

  private static final Logger logger = LoggerFactory.getLogger(ItemService.class);
  private final ItemRepository itemRepository;

  public ItemService(ItemRepository itemRepository) {
	this.itemRepository = itemRepository;
  }

  @Transactional
  public Item createItem(ItemRequest dto) {
	Item newItem = convertTo(dto, Item.class);
	return itemRepository.save(newItem);
  }

  public List<Item> findAllItems() {
	return itemRepository.findAll();
  }

  public Item findItem(Long id) {
	logger.info("Getting item {}", id);
	if (Objects.isNull(id)) {
	  throw new OrderManagerException(HttpStatus.BAD_REQUEST, "Invalid item id");
	}
	return itemRepository
		.findById(id)
		.orElseThrow(() -> new OrderManagerException(HttpStatus.NOT_FOUND, "Item " + id + " not found."));
  }

  @Transactional
  public Item updateItem(Long id, ItemRequest dto) {
	logger.info("Update item {}", id);
	Item item = findItem(id);
	item.setName(dto.getName());
	return itemRepository.save(item);
  }

  public void deleteItem(Long id) {
	logger.info("Delete item {}", id);
	itemRepository.deleteById(id);
  }

}
