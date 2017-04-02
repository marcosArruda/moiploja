/**
 *
 */
package com.marcos.moiploja.customers;

import com.marcos.moiploja.entities.Customer;
import com.marcos.moiploja.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Marcos
 */
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findByEmail(String email);

    @Query("select o from Order o where o.customer.email=?1")
    List<Order> getCustomerOrders(String email);

}
