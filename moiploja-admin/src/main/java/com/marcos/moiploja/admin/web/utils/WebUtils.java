/**
 *
 */
package com.marcos.moiploja.admin.web.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marcos
 */
public class WebUtils {
    public static final String IMAGES_PREFIX = "/products/images/";
    public static final String IMAGES_DIR = "/home/marcos/prog-apps/workspaces/workspace-moip/moiploja/moiploja-site/src/main/resources/static/assets/img/products/";
    private WebUtils() {
    }

    public static String getURLWithContextPath(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath();
    }


}
