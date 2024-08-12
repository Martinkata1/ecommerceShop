package com.ecommerce.admin.controller;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

/**
 *  This controller is responsible for the product management
 */
@Controller
@RequiredArgsConstructor
public class ProductController {
    /**
     * Product management service layer.
     */
    private final ProductService productService;
    /**
     * Service layer for category management.
     */
    private final CategoryService categoryService;


    /**
     * Makes GET requests to /products and displays a list of all products.
     * Checks if the user is authenticated. If not, redirects to login page.
     * Gets all products via productService.allProduct() and adds the information to the model.
     * @param model
     * @param principal
     * @return products
     */
    @GetMapping("/products")
    public String products(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        List<ProductDto> products = productService.allProduct();
        model.addAttribute("products", products);
        model.addAttribute("size", products.size());
        return "products";
    }

    /**
     * Makes GET requests to /products/{pageNo} with the pagination page number.
     * Checks authentication and calls productService.getAllProducts(pageNo) to get products paged.
     * Adds information about the products, the current page, and the total number of pages to the model.
     * @param pageNo
     * @param model
     * @param principal
     * @return page number of products
     */
    @GetMapping("/products/{pageNo}")
    public String allProducts(@PathVariable("pageNo") int pageNo, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Page<ProductDto> products = productService.getAllProducts(pageNo);
        model.addAttribute("title", "Manage Products");
        model.addAttribute("size", products.getSize());
        model.addAttribute("products", products);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", products.getTotalPages());
        return "products";
    }

    /**
     * Handles GET requests to search for products with a specified keyword term.
     * Checks authentication and uses productService.searchProducts(pageNo, keyword) to retrieve search results.
     * Adds search results and related information to the model.
     * @param pageNo
     * @param keyword
     * @param model
     * @param principal
     * @return result search products
     */
    @GetMapping("/search-products/{pageNo}")
    public String searchProduct(@PathVariable("pageNo") int pageNo,
                                @RequestParam(value = "keyword") String keyword,
                                Model model, Principal principal
    ) {
        if (principal == null) {
            return "redirect:/login";
        }
        Page<ProductDto> products = productService.searchProducts(pageNo, keyword);
        model.addAttribute("title", "Result Search Products");
        model.addAttribute("size", products.getSize());
        model.addAttribute("products", products);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", products.getTotalPages());
        return "product-result";

    }

    /**
     * Makes GET requests to /add-product to display the add new product form.
     * Checks authentication and gets a list of active categories via categoryService.findAllByActivatedTrue().
     * Adds the list of categories and a new ProductDto object to the model.
     * @param model
     * @param principal
     * @return add-product
     */
    @GetMapping("/add-product")
    public String addProductPage(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Add Product");
        List<Category> categories = categoryService.findAllByActivatedTrue();
        model.addAttribute("categories", categories);
        model.addAttribute("productDto", new ProductDto());
        return "add-product";
    }

    /**
     * Makes POST requests to /save-product to save a new product.
     * Checks authentication and calls productService.save(imageProduct, product) to save the product.
     * Adds a success or error message to RedirectAttributes.
     * @param product
     * @param imageProduct
     * @param redirectAttributes
     * @param principal
     * @return saved product on first page
     */
    @PostMapping("/save-product")
    public String saveProduct(@ModelAttribute("productDto") ProductDto product,
                              @RequestParam("imageProduct") MultipartFile imageProduct,
                              RedirectAttributes redirectAttributes, Principal principal) {
        try {
            if (principal == null) {
                return "redirect:/login";
            }
            productService.save(imageProduct, product);
            redirectAttributes.addFlashAttribute("success", "Add new product successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed to add new product!");
        }
        return "redirect:/products/0";
    }

    /**
     * Makes GET requests to /update-product/{id} to display the update form for a product with a specified ID.
     * Checks authentication and gets a list of active categories and product information via productService.getById(id).
     * Adds the information to the model.
     * @param id
     * @param model
     * @param principal
     * @return update the product if successful
     */
    @GetMapping("/update-product/{id}")
    public String updateProductForm(@PathVariable("id") Long id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        List<Category> categories = categoryService.findAllByActivatedTrue();
        ProductDto productDto = productService.getById(id);
        model.addAttribute("title", "Add Product");
        model.addAttribute("categories", categories);
        model.addAttribute("productDto", productDto);
        return "update-product";
    }

    /**
     * Makes POST requests to /update-product/{id} to update the product.
     * Checks authentication and calls productService.update(imageProduct, productDto) to update the product.
     * Adds a success or error message to RedirectAttributes.
     * @param productDto
     * @param imageProduct
     * @param redirectAttributes
     * @param principal
     * @return update the product
     */
    @PostMapping("/update-product/{id}")
    public String updateProduct(@ModelAttribute("productDto") ProductDto productDto,
                                @RequestParam("imageProduct") MultipartFile imageProduct,
                                RedirectAttributes redirectAttributes, Principal principal) {
        try {
            if (principal == null) {
                return "redirect:/login";
            }
            productService.update(imageProduct, productDto);
            redirectAttributes.addFlashAttribute("success", "Update successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error server, please try again!");
        }
        return "redirect:/products/0";
    }

    /**
     * Makes PUT and GET requests to /enable-product to enable a product with a specified ID.
     * Checks authentication and calls productService.enableById(id) to enable the product.
     * Adds a success or error message to RedirectAttributes.
     * @param id
     * @param redirectAttributes
     * @param principal
     * @return enable the product
     */
    @RequestMapping(value = "/enable-product", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enabledProduct(Long id, RedirectAttributes redirectAttributes, Principal principal) {
        try {
            if (principal == null) {
                return "redirect:/login";
            }
            productService.enableById(id);
            redirectAttributes.addFlashAttribute("success", "Enabled successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Enabled failed!");
        }
        return "redirect:/products/0";
    }

    /**
     * Makes PUT and GET requests to /delete-product to delete a product with a specified ID.
     * Checks authentication and calls productService.deleteById(id) to delete the product.
     * Adds a success or error message to RedirectAttributes.
     * @param id
     * @param redirectAttributes
     * @param principal
     * @return deleted product
     */
    @RequestMapping(value = "/delete-product", method = {RequestMethod.PUT, RequestMethod.GET})
    public String deletedProduct(Long id, RedirectAttributes redirectAttributes, Principal principal) {
        try {
            if (principal == null) {
                return "redirect:/login";
            }
            productService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Deleted failed!");
        }
        return "redirect:/products/0";
    }
}
