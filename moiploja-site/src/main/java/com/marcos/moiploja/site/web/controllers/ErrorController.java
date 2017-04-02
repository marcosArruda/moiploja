/**
 *
 */
package com.marcos.moiploja.site.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Marcos
 */
@Controller
public class ErrorController extends MoiplojaSiteBaseController {
    private static final String viewPrefix = "error/";

    @Override
    protected String getHeaderTitle() {
        return "Error";
    }

    @RequestMapping("/403")
    public String accessDenied() {
        return viewPrefix + "accessDenied";
    }

	/*@RequestMapping("/error")
	public String error()
	{
		return viewPrefix+"error";
	}*/
}
