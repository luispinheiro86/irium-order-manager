package com.irium.order.manager.repository;

import com.irium.order.manager.entity.Item;
import com.irium.order.manager.entity.Order;
import com.irium.order.manager.enumeration.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

  @Query("SELECT o FROM Order o WHERE o.item = :item and o.status = :status "
         + "ORDER BY o.creationDate ASC")
  List<Order> findAllOrdersByItemAndStatus(@Param("item") Item item, @Param("status") Status status);

}
