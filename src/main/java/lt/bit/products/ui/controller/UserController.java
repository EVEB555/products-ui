package lt.bit.products.ui.controller;

import lt.bit.products.ui.service.UserService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Controller
public class UserController {

    private final UserService userService;
    private final MessageSource messages;

    public UserController(UserService userService, MessageSource messages) {
        this.userService = userService;
        this.messages = messages;
    }

    @GetMapping("/auth/login")
    String loginForm() {
        if(userService.isAuthenticated()) {
            return "redirect:/products";
        }
        return "login";
    }

    @PostMapping("/auth/login")
    String login(HttpServletRequest request, Model model) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username.equals("admin") && password.equals("admin1")) {
            userService.setAuthenticated(true); //isimena prisijungima
            return "redirect:/products";
        }
        model.addAttribute("errorMsg",
                messages.getMessage("login.error.INVALID_CREDENTIALS", null, Locale.getDefault()));
        return "login";
    }

    @GetMapping("/auth/logout")
    String logout() {
        userService.setAuthenticated(false);
        return "login";
    }

}
