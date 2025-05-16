package com.irium.order.manager.repository;

import com.irium.order.manager.entity.Item;
import com.irium.order.manager.entity.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

  @Query("SELECT sm FROM StockMovement sm WHERE sm.item = :item AND sm.available > 0 " +
         "ORDER BY sm.creationDate ASC")
  List<StockMovement> findAvailableStockMovementsByItem(@Param("item") Item item);

}
