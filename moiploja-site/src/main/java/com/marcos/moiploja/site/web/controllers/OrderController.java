/**
 *
 */
package com.marcos.moiploja.site.web.controllers;

import com.marcos.moiploja.MoiplojaException;
import com.marcos.moiploja.entities.Order;
import com.marcos.moiploja.entities.dto.Cart;
import com.marcos.moiploja.entities.dto.OrderDTO;
import com.marcos.moiploja.orders.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author Marcos
 */
@Controller
public class OrderController extends MoiplojaSiteBaseController {

    @Autowired
    protected OrderService orderService;

    @Override
    protected String getHeaderTitle() {
        return "Order";
    }

    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public String placeOrder(@Valid @ModelAttribute("order") OrderDTO order,
                             BindingResult result, Model model, HttpServletRequest request) {
        System.out.println(order.toString());
        Cart cart = getOrCreateCart(request);
        if (result.hasErrors()) {
            model.addAttribute("cart", cart);
            return "checkout";
        }
        Order createdOrder = null;
        try {
            createdOrder = orderService.processOrder(order, cart);
        } catch (MoiplojaException e) {
            e.printStackTrace();
            result.addError(new ObjectError("moipErrors", e.getMessage()));
            model.addAttribute("cart", cart);
            return "checkout";
        }

        request.getSession().removeAttribute("CART_KEY");
        return "redirect:orderconfirmation?orderNumber=" + createdOrder.getOrderNumber();
    }

    @RequestMapping(value = "/orderconfirmation", method = RequestMethod.GET)
    public String showOrderConfirmation(@RequestParam(value = "orderNumber") String orderNumber, Model model) {
        Order order = orderService.findOrder(orderNumber);
        model.addAttribute("order", order);
        model.addAttribute("paymentId", order.getPayment().getMoipId());
        return "orderconfirmation";
    }


    @RequestMapping(value = "/orders/{orderNumber}", method = RequestMethod.GET)
    public String viewOrder(@PathVariable(value = "orderNumber") String orderNumber, Model model) {
        Order order = orderService.findOrder(orderNumber);
        model.addAttribute("order", order);
        return "view_order";
    }
}
