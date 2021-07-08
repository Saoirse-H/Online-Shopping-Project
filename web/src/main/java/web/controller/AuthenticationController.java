package web.controller;

import web.UserSession;
import model.User;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@Controller
public class AuthenticationController{
    @Autowired
    private UserSession userSession;

    @PostMapping("/login")
    public void login(Model model, String username, String password, HttpServletResponse response) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        User consumer = restTemplate.getForObject("http://localhost:8080/consumer-service/exists?username="+username+"&password="+password, User.class);
        User seller = restTemplate.getForObject("http://localhost:8080/seller-service/exists?username="+username+"&password="+password, User.class);
        User user = consumer != null ? consumer : seller;

        if(user != null){
            userSession.setUser(user);
            userSession.setLoginFailed(false);
            userSession.setUsernameTaken(false);
            userSession.getUser().setId(user.getId());
            response.sendRedirect("/");
        } else {
            userSession.setLoginFailed(true);
            userSession.setUsernameTaken(false);
            response.sendRedirect("/login");
        }
    }

    @PostMapping("/signup")
    public String register(Model model, String username, String email, String password, Boolean isSeller) {
        RestTemplate restTemplate = new RestTemplate();
        User consumer = restTemplate.getForObject("http://localhost:8080/consumer-service/username-exists?username="+username, User.class);
        User seller = restTemplate.getForObject("http://localhost:8080/seller-service/username-exists?username="+username, User.class);

        if(consumer != null || seller != null) {
            userSession.setUsernameTaken(true);
            return "redirect:/login";
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("password", password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);

        if(isSeller != null) {
            User user = restTemplate.postForObject("http://localhost:8080/seller-service/add-seller", entity, User.class);

            userSession.setUser(user);
            userSession.setLoginFailed(false);
            userSession.setUsernameTaken(false);
            userSession.getUser().setId(user.getId());
        } else {
            User user = restTemplate.postForObject("http://localhost:8080/consumer-service/add-consumer", entity, User.class);
            
            userSession.setUser(user);
            userSession.setLoginFailed(false);
            userSession.setUsernameTaken(false);
            userSession.getUser().setId(user.getId());
        }
      
        return "redirect:/";
    }

    @GetMapping("/logout")
    public void logout(HttpServletResponse response) throws IOException {
        userSession.setUser(null);
        response.sendRedirect("/");
    }
}