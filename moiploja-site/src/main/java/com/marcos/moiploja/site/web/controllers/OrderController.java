/**
 *
 */
package com.marcos.moiploja.site.web.controllers;

import com.marcos.moiploja.MoiplojaException;
import com.marcos.moiploja.common.services.EmailService;
import com.marcos.moiploja.customers.CustomerService;
import com.marcos.moiploja.entities.*;
import com.marcos.moiploja.orders.OrderService;
import com.marcos.moiploja.site.web.models.Cart;
import com.marcos.moiploja.site.web.models.LineItem;
import com.marcos.moiploja.site.web.models.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Marcos
 */
@Controller
public class OrderController extends MoiplojaSiteBaseController {

    @Autowired
    protected OrderService orderService;
    @Autowired
    protected EmailService emailService;
    @Autowired
    private CustomerService customerService;

    @Override
    protected String getHeaderTitle() {
        return "Order";
    }

    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public String placeOrder(@Valid @ModelAttribute("order") OrderDTO order,
                             BindingResult result, Model model, HttpServletRequest request) {
        Cart cart = getOrCreateCart(request);
        if (result.hasErrors()) {
            model.addAttribute("cart", cart);
            return "checkout";
        }

        Order newOrder = new Order();

        String email = getCurrentUser().getCustomer().getEmail();
        Customer customer = customerService.getCustomerByEmail(email);
        newOrder.setCustomer(customer);
        Address address = new Address();
        address.setAddressLine1(order.getAddressLine1());
        address.setAddressLine2(order.getAddressLine2());
        address.setCity(order.getCity());
        address.setState(order.getState());
        address.setZipCode(order.getZipCode());
        address.setCountry(order.getCountry());

        newOrder.setDeliveryAddress(address);

        Address billingAddress = new Address();
        billingAddress.setAddressLine1(order.getAddressLine1());
        billingAddress.setAddressLine2(order.getAddressLine2());
        billingAddress.setCity(order.getCity());
        billingAddress.setState(order.getState());
        billingAddress.setZipCode(order.getZipCode());
        billingAddress.setCountry(order.getCountry());

        newOrder.setBillingAddress(billingAddress);

        Set<OrderItem> orderItems = new HashSet<OrderItem>();
        List<LineItem> lineItems = cart.getItems();
        for (LineItem lineItem : lineItems) {
            OrderItem item = new OrderItem();
            item.setProduct(lineItem.getProduct());
            item.setQuantity(lineItem.getQuantity());
            item.setPrice(lineItem.getProduct().getPrice());
            item.setOrder(newOrder);
            orderItems.add(item);
        }

        newOrder.setItems(orderItems);

        Payment payment = new Payment();
        payment.setCcNumber(order.getCcNumber());
        payment.setCvv(order.getCvv());

        newOrder.setPayment(payment);
        Order savedOrder = orderService.createOrder(newOrder);

        this.sendOrderConfirmationEmail(savedOrder);

        request.getSession().removeAttribute("CART_KEY");
        return "redirect:orderconfirmation?orderNumber=" + savedOrder.getOrderNumber();
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

    @RequestMapping(value = "/orderconfirmation", method = RequestMethod.GET)
    public String showOrderConfirmation(@RequestParam(value = "orderNumber") String orderNumber, Model model) {
        Order order = orderService.getOrder(orderNumber);
        model.addAttribute("order", order);
        return "orderconfirmation";
    }


    @RequestMapping(value = "/orders/{orderNumber}", method = RequestMethod.GET)
    public String viewOrder(@PathVariable(value = "orderNumber") String orderNumber, Model model) {
        Order order = orderService.getOrder(orderNumber);
        model.addAttribute("order", order);
        return "view_order";
    }
}
