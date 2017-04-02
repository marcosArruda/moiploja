/**
 *
 */
package com.marcos.moiploja.admin.web.controllers;

import com.marcos.moiploja.admin.security.SecurityUtil;
import com.marcos.moiploja.entities.Permission;
import com.marcos.moiploja.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author Marcos
 */
@Controller
@Secured(SecurityUtil.MANAGE_PERMISSIONS)
public class PermissionController extends MoiplojaAdminBaseController {
    private static final String viewPrefix = "permissions/";

    @Autowired
    protected SecurityService securityService;

    @Override
    protected String getHeaderTitle() {
        return "Manage Permissions";
    }

    @RequestMapping(value = "/permissions", method = RequestMethod.GET)
    public String listPermissions(Model model) {
        List<Permission> list = securityService.getAllPermissions();
        model.addAttribute("permissions", list);
        return viewPrefix + "permissions";
    }

}
