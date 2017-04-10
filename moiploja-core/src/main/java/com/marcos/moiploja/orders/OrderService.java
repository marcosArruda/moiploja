/**
 *
 */
package com.marcos.moiploja.orders;

import br.com.moip.exception.MoipException;
import br.com.moip.exception.UnauthorizedException;
import br.com.moip.exception.UnexpectecException;
import br.com.moip.exception.ValidationException;
import com.marcos.moiploja.MoiplojaException;
import com.marcos.moiploja.common.services.EmailService;
import com.marcos.moiploja.common.services.MLLogger;
import com.marcos.moiploja.entities.*;
import com.marcos.moiploja.entities.dto.Cart;
import com.marcos.moiploja.entities.dto.OrderDTO;
import com.marcos.moiploja.services.MoipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Marcos
 */
@Service
@Transactional
public class OrderService {
    private static final MLLogger logger = MLLogger.getLogger(OrderService.class);

    @Autowired
    MoipService moipService;
    @Autowired
    EmailService emailService;
    @Autowired
    OrderRepository orderRepository;

    public Order processOrder(OrderDTO order, Cart cart) throws MoiplojaException {
        logger.info("Trying to process order processOrder()");
        final Order newOrder = Order.buildOrder(order, cart);

        newOrder.setOrderNumber(String.valueOf(System.currentTimeMillis()));
        orderRepository.save(newOrder);


        //doing MOIP interaction
        try {
            br.com.moip.resource.Order moipOrder = moipService.createOrder(newOrder);
            br.com.moip.resource.Payment moipPayment = moipService.createPayment(moipOrder, newOrder);
        }catch (UnauthorizedException unEx){
            throw new MoiplojaException("Movimentação MOIP não autorizada.", unEx);
        }catch (ValidationException valEx){
            throw new MoiplojaException("Dados enviados ao MOIP não validos.", valEx);
        }catch (UnexpectecException unexEx){
            throw new MoiplojaException("Erro 500 ao realizar a movimentação no MOIP.", unexEx);
        }catch (MoipException moipException){
            throw new MoiplojaException("Excessão genérica MOIP.", moipException);
        }

        this.sendOrderConfirmationEmail(newOrder);
        return newOrder;
    }

    public Order findOrder(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }

    public List<Order> findAll() {
        Sort sort = new Sort(Direction.DESC, "createdOn");
        return orderRepository.findAll(sort);
    }

    public Order updateOrder(Order order) {
        Order o = findOrder(order.getOrderNumber());
        o.setStatus(order.getStatus());
        Order savedOrder = orderRepository.save(o);
        return savedOrder;
    }

    protected void sendOrderConfirmationEmail(Order order) {
        try {
            emailService.sendEmail(order.getCustomer().getEmail(),
                    "QuilCartCart - Order Confirmation",
                    "Your order has been placed successfully.\n"
                            + "Order Number : " + order.getOrderNumber());
        } catch (MoiplojaException e) {
            logger.error(e);
        }
    }


}
