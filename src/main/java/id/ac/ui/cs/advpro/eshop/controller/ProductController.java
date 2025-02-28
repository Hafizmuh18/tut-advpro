package id.ac.ui.cs.advpro.eshop.controller;

import id.ac.ui.cs.advpro.eshop.model.Product;
import id.ac.ui.cs.advpro.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/product")
class ProductController {
    @Autowired
    ProductService service;

    @GetMapping("/create")
    public String createProductPage(Model model) {
        Product product = new Product();
        product.setProductId(UUID.randomUUID().toString());
        model.addAttribute("product", product);
        return "CreateProduct";
    }

    @PostMapping("/create")
    public String createProductPost(@ModelAttribute Product product, Model model) {
        service.create(product);
        return "redirect:list";
    }

    @GetMapping("/list")
    public String productListProducts(Model model) {
        List<Product> allProducts = service.findAll();
        model.addAttribute("products", allProducts);
        return "ProductList";
    }

    @PostMapping("/delete")
    public String deleteProduct(@RequestParam String productId) {
        service.delete(productId);
        return "redirect:list";
    }

    @GetMapping("/edit/{id}")
    public String updateProduct(@PathVariable String id, Model model) {
        List<Product> allProducts = service.findAll();
        for(Product product : allProducts){
            if(product.getProductId().equalsIgnoreCase(id)){
                model.addAttribute("product", product);
                return "UpdateProduct";
            }
        }
        return "redirect:list";
    }

    @PostMapping("/edit")
    public String updateProductPost(@ModelAttribute Product product, Model model) {
        service.update(product);
        return "redirect:list";
    }
}
