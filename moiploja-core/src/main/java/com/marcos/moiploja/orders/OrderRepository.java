/**
 *
 */
package com.marcos.moiploja.orders;

import com.marcos.moiploja.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Marcos
 */
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findByOrderNumber(String orderNumber);
}
