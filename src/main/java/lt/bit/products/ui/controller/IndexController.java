package lt.bit.products.ui.controller;

import lt.bit.products.ui.model.CartItem;
import lt.bit.products.ui.service.CartService;
import lt.bit.products.ui.service.ProductService;
import lt.bit.products.ui.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
class IndexController extends ControllerBase {

  private UserService userService;
  private ProductService productService;
  private CartService cartService;

  IndexController(UserService userService, ProductService productService, CartService cartService) {
    this.userService = userService;
    this.productService = productService;
    this.cartService = cartService;
  }

  @GetMapping("/")
  String index(Model model) {
    List<CartItem> cartItems = cartService.getCartItems();
    long totalItems = cartItems.stream().mapToInt(CartItem::getCount).sum();
    model.addAttribute("totalCartItems", totalItems);
    model.addAttribute("cartItems", cartItems);
    model.addAttribute("products", productService.getProducts());
    return "index";
  }

  @GetMapping(ADMIN_PATH)
  String admin(Model model) {
    if (!userService.isAuthenticated()) {
      return "login";
    }
    return "admin/index";
  }
}
