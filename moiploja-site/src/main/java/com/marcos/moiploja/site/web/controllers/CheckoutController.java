/**
 *
 */
package com.marcos.moiploja.site.web.controllers;

import com.marcos.moiploja.entities.dto.Cart;
import com.marcos.moiploja.entities.dto.OrderDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marcos
 */
@Controller
public class CheckoutController extends MoiplojaSiteBaseController {

    @Override
    protected String getHeaderTitle() {
        return "Checkout";
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.GET)
    public String checkout(HttpServletRequest request, Model model) {
        OrderDTO order = new OrderDTO();
        order.setCcNumber("5555666677778884");
        order.setCvv("123");
        model.addAttribute("order", order);
        Cart cart = getOrCreateCart(request);
        model.addAttribute("cart", cart);
        return "checkout";
    }
}
