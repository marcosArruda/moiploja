/**
 *
 */
package com.marcos.moiploja.site.web.controllers;

import com.marcos.moiploja.catalog.CatalogService;
import com.marcos.moiploja.entities.Category;
import com.marcos.moiploja.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

/**
 * @author Marcos
 */
@Controller
public class HomeController extends MoiplojaSiteBaseController {
    @Autowired
    private CatalogService catalogService;

    @Override
    protected String getHeaderTitle() {
        return "Home";
    }

    @RequestMapping("/home")
    public String home(Model model) {
        List<Category> previewCategories = new ArrayList<>();
        List<Category> categories = catalogService.getAllCategories();
        for (Category category : categories) {
            Set<Product> products = category.getProducts();
            Set<Product> previewProducts = new HashSet<>();
            int noOfProductsToDisplay = 4;
            if (products.size() > noOfProductsToDisplay) {
                Iterator<Product> iterator = products.iterator();
                for (int i = 0; i < noOfProductsToDisplay; i++) {
                    previewProducts.add(iterator.next());
                }
            } else {
                previewProducts.addAll(products);
            }
            category.setProducts(previewProducts);
            previewCategories.add(category);
        }
        model.addAttribute("categories", previewCategories);
        return "home";
    }

    @RequestMapping("/categories/{name}")
    public String category(@PathVariable String name, Model model) {
        Category category = catalogService.getCategoryByName(name);
        model.addAttribute("category", category);
        return "category";
    }

}
