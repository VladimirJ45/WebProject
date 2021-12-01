package com.Controller;

import com.AESEncryptDecrypt.AESEncryptionDecryption;
import com.DAO.*;
import com.Model.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.Objects;

@RequestMapping("")
@Controller
public class AppController {

    public AppController() throws SQLException, ClassNotFoundException {
    }
    private final UserDAO userDAO = new UserDAO();
    private final AccountDAO accountDAO = new AccountDAO();
    private final ItemDAO itemDAO = new ItemDAO();
    private final UserOrderDAO userOrderDAO = new UserOrderDAO();
    private final AESEncryptionDecryption aesEncryptionDecryption = new AESEncryptionDecryption();
    private final String secretKey = "epamwebprojectkey";

    @GetMapping(value = "/")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @PostMapping(value = "/login-process")
    public ModelAndView loginProcess(@RequestParam("username") String username, @RequestParam("password") String password){
        ModelAndView modelAndView = new ModelAndView();
        if(username == "" || password == ""){  //check input fields
            return new ModelAndView("redirect:/error");
        }
        if(userDAO.findByUsername(username) == null){  //check if user exists
            return new ModelAndView("redirect:/login?noSuchUser");
        }
        User user = userDAO.findByUsername(username);
        if(!Objects.equals(password, aesEncryptionDecryption.decrypt(user.getPassword(), secretKey))){ //check the password
            return new ModelAndView("redirect:/login?invalidPassword");
        }else if(Objects.equals(user.getRole().getName(), "user")){  //action for user with "user" role
            modelAndView.addObject("account", accountDAO.findById(user.getId()));
            modelAndView.setViewName("online_store");
            return modelAndView;
        }else if(Objects.equals(user.getRole().getName(), "admin")){  //action for user with "admin" role
            modelAndView.setViewName("admin");
            return modelAndView;
        }else{
            return new ModelAndView("redirect:/error");  //action for user without role
        }
    }

    @GetMapping(value = "/onlineStore")
    public ModelAndView onlineStore(@ModelAttribute("account") Account account){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("account", account);
        modelAndView.addObject("items", itemDAO.getAllItems());
        modelAndView.setViewName("online_store");
        return modelAndView;
    }

    @PostMapping(value = "/buyItem")
    public String buyItem(@ModelAttribute("item") Item item, @ModelAttribute("account") Account account){
        if(userOrderDAO.getUserItems(account).contains(item)){
            return "redirect:/online-store?alreadyBought";
        }
        UserOrder userOrder = new UserOrder();
        userOrder.setItem(item);
        userOrder.setAccount(account);
        userOrderDAO.createNewOrder(userOrder);
        return "redirect:/online-store?successfullyBought";
    }

    @PostMapping(value = "/userOrdersAndAccount")
    public ModelAndView userOrders(@ModelAttribute("account") Account account){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("account", account);
        modelAndView.addObject("userOrders", userOrderDAO.getUserItems(account));
        modelAndView.setViewName("user-info");
        return modelAndView;
    }

    @GetMapping(value = "/admin")
    public ModelAndView admin(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin");
        modelAndView.addObject(itemDAO.getAllItems());
        modelAndView.addObject(accountDAO.getAllAccounts());
        return modelAndView;
    }

    @PostMapping(value = "/viewUserOrdersByAdmin")
    public ModelAndView viewUser(@ModelAttribute("account") Account account){
        ModelAndView modelAndView = new ModelAndView();
        if(accountDAO.findById(account.getId()) == null){
            return new ModelAndView("redirect:/admin?accountDoesNotExists");
        }
        modelAndView.addObject("account", account);
        modelAndView.addObject("userOrders", userOrderDAO.getUserItems(account));
        modelAndView.setViewName("admin_view_account");
        return modelAndView;
    }

    @PostMapping(value = "/additem")
    public String addItem(@ModelAttribute("item") Item item){
        if(itemDAO.getItemByName(item.getName()) == null){
            return "redirect:/admin?itemAlreadyExists";
        }
        itemDAO.addItems(item);
        return "redirect:/admin?newItemAdded";
    }

    @PostMapping(value = "/removeItem")
    public String removeItem(@RequestParam("id") int id){
        if(itemDAO.getItemById(id) != null){
            itemDAO.deleteItemById(id);
            return "redirect:/admin?itemRemoved";
        }else{
            return "redirect:/admin?itemDoesNotExist";
        }
    }

    @PostMapping(value = "/updateItem")
    public String updateItem(@ModelAttribute Item item){
        if(itemDAO.getItemById(item.getId()) == null){
            return "redirect:/admin?itemDoesNotExist";
        }
        itemDAO.updateItem(item);
        return "redirect:/admin?itemUpdated";
    }

    @GetMapping(value = "/logout")
    public ModelAndView logout(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }
}