package web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import model.*;
import response.*;
import web.UserSession;

import org.springframework.http.HttpEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
public class AdminController extends WebController {
    @Autowired
    private UserSession userSession;

    String aggregatorURL = "http://localhost:8080/";

    @ModelAttribute
    public void addAttributes(Model model){
        model.addAttribute("user", userSession.getUser());
    }

    @GetMapping("/admin")
    public String index(Model model) {
        //Check if user is admin, we can re-use this for anything admin required
        if (!urlAccessRights("seller"))
            return "redirect:/login";
        //model.addAttribute("message", "test");

        return "admin/adminIndex.html";
    }

    @GetMapping("/seller-hub")
    public String viewStock(Model model) {        
        if (!urlAccessRights("seller")) {
            return "redirect:/";
        }

        RestTemplate restTemplate = new RestTemplate();
        ItemList items = restTemplate.getForObject(aggregatorURL + "view-my-stock/{id}", ItemList.class, userSession.getUser().getId());
        model.addAttribute("items", items.getItems());
        return "admin/sellerHub.html";
    }

    @RequestMapping("/remove")
    public void remove(@RequestParam("id") int itemId, HttpServletResponse response) throws IOException {
        if (!urlAccessRights("seller")) {
            response.sendRedirect("/");
        }
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(aggregatorURL + "remove-from-stock/{sellerId}/{itemId}", null, Void.class, userSession.getUser().getId(), itemId);
        response.sendRedirect("/seller-hub");
    }

    @PostMapping("/add")
    public void add(String name, String category, String description, int quantity, double price, HttpServletResponse response) throws IOException {
        if (!urlAccessRights("seller")) {
            response.sendRedirect("/");
        }
        Item newItem = new Item(name, category, description, price, quantity, userSession.getUser().getId());
        newItem.setItemId(0);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Item> request = new HttpEntity<>(newItem);
        restTemplate.postForObject(aggregatorURL + "add-to-stock", request, Void.class);
        response.sendRedirect("/seller-hub");
        

    }
}
