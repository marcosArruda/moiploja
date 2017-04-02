/**
 *
 */
package com.marcos.moiploja.admin.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Marcos
 */
@Controller
public class HomeController extends MoiplojaAdminBaseController {
    @Override
    protected String getHeaderTitle() {
        return "Home";
    }

    @RequestMapping("/home")
    public String home(Model model) {
        return "home";
    }


}
