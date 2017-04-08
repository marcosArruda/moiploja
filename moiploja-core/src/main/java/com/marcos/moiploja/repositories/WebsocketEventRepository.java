package com.marcos.moiploja.repositories;

import java.util.List;

import com.marcos.moiploja.entities.WebSocketEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ive_marruda on 07/04/17.
 */
@Repository
public interface WebsocketEventRepository extends JpaRepository<WebSocketEvent, Integer> {
    List<WebSocketEvent> findByPaymentId(String paymentId);
    Long deleteByPaymentId(String paymentId);
}
