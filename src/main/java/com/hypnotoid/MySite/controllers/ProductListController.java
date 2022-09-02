package com.hypnotoid.MySite.controllers;

import com.hypnotoid.MySite.models.Product;
import com.hypnotoid.MySite.services.ProductService;
import com.hypnotoid.MySite.validators.ProductValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;


@Controller
public class ProductListController {

    private final ProductService productService;
    private final ProductValidator validator;

    public ProductListController(ProductService productService, ProductValidator validator) {
        this.productService = productService;
        this.validator = validator;
    }

    @GetMapping("/productsList")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAll());
        if (model.getAttribute("org.springframework.validation.BindingResult.product") == null && model.asMap().get("product") == null) {
            model.addAttribute("product", productService.create());
        }
        return "shop/productsList";
    }

    @PostMapping("/productsListDelete")
    public RedirectView productsDelete(int id, RedirectAttributes attributes) {
//        if (productService.existOrdersByProductId(id)){
//            attributes.addFlashAttribute("deleteError","Существует пользователь, у которого заказан даный продукт");
//        } else {
        productService.deleteById(id);
//        }

        return new RedirectView("/productsList");
    }

    @PostMapping("/productsListSaveProcess")
    public RedirectView productsEditProcess(@Valid @ModelAttribute("product") Product product, BindingResult br, RedirectAttributes attributes) {
        if (br.hasErrors()) {
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.product", br);
            attributes.addFlashAttribute("product", product);
        } else if (product.getId() == 0) {
            productService.add(product);
        } else productService.edit(product);
        // attributes.addAttribute("product",productService.create());
        return new RedirectView("/productsList");
    }

    @PostMapping(value = "/productsListSave")
    public RedirectView productsEdit(RedirectAttributes attributes, int id) {
        attributes.addFlashAttribute("product", productService.getById(id));
        return new RedirectView("/productsList");
    }

    @PostMapping(value = "/productsListAdd20")
    public String productsListAdd20() {
        productService.add20RandomProducts();
        return "redirect:/productsList";
    }

    @GetMapping("/productsListFind")
    public String bugFix() {
        return ("redirect:/productsList");
    }

    @PostMapping(value = "/productsListFind")
    public String productsListFind(Model model, String find) {
        model.addAttribute("products", productService.find(find));
        return ("shop/productsFind");
    }

    // @Autowired
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }
}
