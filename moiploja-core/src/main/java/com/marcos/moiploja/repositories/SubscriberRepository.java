package com.marcos.moiploja.repositories;

import com.marcos.moiploja.entities.Order;
import com.marcos.moiploja.entities.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ive_marruda on 07/04/17.
 */
@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, String> {
}
