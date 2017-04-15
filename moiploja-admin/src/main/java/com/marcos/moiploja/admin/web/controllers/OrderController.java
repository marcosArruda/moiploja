/**
 *
 */
package com.marcos.moiploja.admin.web.controllers;

import com.marcos.moiploja.MoiplojaException;
import com.marcos.moiploja.admin.security.SecurityUtil;
import com.marcos.moiploja.common.services.EmailService;
import com.marcos.moiploja.entities.Order;
import com.marcos.moiploja.orders.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;

/**
 * @author Marcos
 */
@Controller
@Secured(SecurityUtil.MANAGE_ORDERS)
public class OrderController extends MoiplojaAdminBaseController {
    private static final String viewPrefix = "orders/";

    @Autowired
    protected EmailService emailService;
    @Autowired
    protected OrderService orderService;
    @Autowired
    private TemplateEngine templateEngine;

    @Override
    protected String getHeaderTitle() {
        return "Manage Orders";
    }


    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public String listOrders(Model model) {
        List<Order> list = orderService.findAll();
        model.addAttribute("orders", list);
        return viewPrefix + "orders";
    }

    @RequestMapping(value = "/orders/{orderNumber}", method = RequestMethod.GET)
    public String editOrderForm(@PathVariable String orderNumber, Model model) {
        Order order = orderService.findOrder(orderNumber);
        model.addAttribute("order", order);
        return viewPrefix + "edit_order";
    }

    @RequestMapping(value = "/orders/{orderNumber}", method = RequestMethod.POST)
    public String updateOrder(@ModelAttribute("order") Order order, BindingResult result,
                              Model model, RedirectAttributes redirectAttributes) {
        Order persistedOrder = orderService.updateOrder(order);
        this.sendOrderStatusUpdateEmail(persistedOrder);
        logger.debug("Updated order with orderNumber : {}", persistedOrder.getOrderNumber());
        redirectAttributes.addFlashAttribute("info", "Order updated successfully");
        return "redirect:/orders";
    }

    protected void sendOrderStatusUpdateEmail(Order order) {
        try {

            // Prepare the evaluation context
            final Context ctx = new Context();
            ctx.setVariable("order", order);

            // Create the HTML body using Thymeleaf
            final String htmlContent = this.templateEngine.process("order-status-update-email", ctx);
        } catch (MoiplojaException e) {
            logger.error(e);
        }
    }
}
