package com.ecommerce.admin.controller;

import com.ecommerce.library.model.Category;
import com.ecommerce.library.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

/**
 * WITH CRUD operations
 * This controller is responsible for managing categories in the application.
 */
@Controller
@RequiredArgsConstructor
public class CategoryController {
    /**
     * A service layer that takes care of business logic
     * related to categories. This service is used to
     * perform operations such as creating, updating, deleting and finding categories.
     */
    private final CategoryService categoryService;

    /**
     * Handles requests to the /categories URL, resulting in the category management page loading.
     * Checks if the user is authenticated. If not, it redirects him to the login page.
     * Adds a list of all categories as well as a new Category object to the model so that the form to add a new category can be filled.
     */
    @GetMapping("/categories")
    public String categories(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Manage Category");
        List<Category> categories = categoryService.findALl();
        model.addAttribute("categories", categories);
        model.addAttribute("size", categories.size());
        model.addAttribute("categoryNew", new Category());
        return "categories";
    }

    /**
     * Handles a POST request to create a new category.
     * Attempts to save the new category to the database.
     * If there is an error (for example duplicate category name),
     * returns the appropriate error messages and redirects
     * back to the categories page.
     * @param category
     * @param model
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/save-category")
    public String save(@ModelAttribute("categoryNew") Category category, Model model, RedirectAttributes redirectAttributes) {
        try {
            categoryService.save(category);
            model.addAttribute("categoryNew", category);
            redirectAttributes.addFlashAttribute("success", "Add successfully!");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Duplicate name of category, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            model.addAttribute("categoryNew", category);
            redirectAttributes.addFlashAttribute("error",
                    "Error server");
        }
        return "redirect:/categories";
    }

    /**
     * Handles a query to find a category by its ID.
     * @param id
     * @return Optional<Category> that can contain the found category or be empty if none exists
     */
    @RequestMapping(value = "/findById", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public Optional<Category> findById(Long id) {
        return categoryService.findById(id);
    }


    /**
     * Handles a request to update an existing category.
     * Trying to update the category in the database.
     * On success or error (for example duplicate name) redirects back to
     * category page and displays corresponding messages.
     * @param category
     * @param redirectAttributes
     * @return "redirect:/categories"
     */
    @GetMapping("/update-category")
    public String update(Category category, RedirectAttributes redirectAttributes) {
        try {
            categoryService.update(category);
            redirectAttributes.addFlashAttribute("success", "Update successfully!");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Duplicate name of category, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error from server or duplicate name of category, please check again!");
        }
        return "redirect:/categories";
    }


    /**
     * Handles a request to delete a category by its ID.
     * On successful deletion or error (for example server problem or duplicate name)
     * redirects back to category page and displays relevant messages.
     * @param id
     * @param redirectAttributes
     * @return "redirect:/categories"
     */
    @RequestMapping(value = "/delete-category", method = {RequestMethod.GET, RequestMethod.PUT})
    public String delete(Long id, RedirectAttributes redirectAttributes) {
        try {
            categoryService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Deleted successfully!");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Duplicate name of category, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error server");
        }
        return "redirect:/categories";
    }


    /**
     * Handles a category activation request (probably if it was previously disabled).
     * On successful activation or error redirects back
     * to the categories page and displays relevant messages.
     * @param id
     * @param redirectAttributes
     * @return "redirect:/categories"
     */
    @RequestMapping(value = "/enable-category", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enable(Long id, RedirectAttributes redirectAttributes) {
        try {
            categoryService.enableById(id);
            redirectAttributes.addFlashAttribute("success", "Enable successfully");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Duplicate name of category, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error server");
        }
        return "redirect:/categories";
    }


}
