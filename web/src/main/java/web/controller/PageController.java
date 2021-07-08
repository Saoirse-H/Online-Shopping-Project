package web.controller;

import response.CategoryList;
import response.ItemList;
import web.UserSession;
import web.exceptions.*;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
public class PageController extends WebController {

    String aggregatorURL = "http://localhost:8080/";
    @Autowired
    private UserSession userSession;

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("error", null);
        model.addAttribute("user", userSession.getUser());
        if(userSession.isLoginFailed())
            model.addAttribute("error", "Username or Password not correct. Please try again.");

        if(userSession.isUsernameTaken())
            model.addAttribute("signUpError", "This username has already been taken. Please try another.");
    }

    @GetMapping("/")
    public String index(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        ItemList items = restTemplate.getForObject(aggregatorURL + "items", ItemList.class);
        model.addAttribute("items", items.getItems());
        CategoryList categoryList = restTemplate.getForObject(aggregatorURL + "stock-service/items/categories", CategoryList.class);
        model.addAttribute("categories", categoryList.getCategories());
        model.addAttribute("back", "false");
        return "index.html";
    }

    @GetMapping("/category/{category}")
    public String selectedCategory(Model model, @PathVariable String category){
        RestTemplate restTemplate = new RestTemplate();
        ItemList items = restTemplate.getForObject(aggregatorURL + "stock-service/items/"+ category, ItemList.class);
        model.addAttribute("items", items.getItems());
        model.addAttribute("categories", category);
        model.addAttribute("back", "true");
        return "index.html";
    }

    @GetMapping("/login")
    public String account() {

        return "login.html";
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        ItemList items = restTemplate.getForObject(aggregatorURL + "cart/{userId}", ItemList.class, userSession.getUser().getId());
        if(items.getItems() == null) {
            model.addAttribute("items", null);
        } else {
            model.addAttribute("items", items.getItems());
        }
        return "cart.html";
    }

    @RequestMapping(value="/add-to-cart-button")
    public void callAddToCart(@RequestParam("itemId") int itemId, HttpServletResponse response) throws IOException {
    
        if(!urlAccessRights("consumer")) {
            throw new IncorrectUserException("User is not a consumer");
        }

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(aggregatorURL + "add-to-cart/{user}/{item}", null, Void.class, userSession.getUser().getId() , itemId);
        response.sendRedirect("/");
    }

    @RequestMapping(value="/remove-from-cart-button")
    public void callRemoveFromCart(@RequestParam("itemId") int itemId, HttpServletResponse response) throws IOException {

        if(!urlAccessRights("consumer")) {
            throw new IncorrectUserException("User is not a consumer");
        }

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(aggregatorURL + "remove-from-cart/{user}/{item}", null, Void.class, userSession.getUser().getId() , itemId);
        response.sendRedirect("/cart");
    }
}