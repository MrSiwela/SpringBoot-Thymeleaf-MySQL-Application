package com.stan.thymeleafapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String getAllUsers(Model model){
        List<User> users = userService.findAllUser();
        model.addAttribute("userList",users);
        return "users";
    }

    @GetMapping("/users/new")
    public String showForm(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("pageTitle","ADD NEW USER");
        return "user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(User user, RedirectAttributes ra){
        userService.addUser(user);
        ra.addFlashAttribute("message","User Was Added Successfully.");
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String editForm(@PathVariable("id") int id,Model model, RedirectAttributes ra){
        try{
            User user = userService.getByID(id);
            model.addAttribute("user",user);
            model.addAttribute("pageTitle","Edit User ID : "+id);
            return "user_form";
        }catch(UserNotFoundException e){
            ra.addFlashAttribute("message",e.getMessage());
            return "redirect:/users";
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") int id,Model model, RedirectAttributes ra){
        try{
            userService.deleteUser(id);
            ra.addFlashAttribute("message","User ID : "+id+" Has Been Deleted.");
        }catch(UserNotFoundException e){
            ra.addFlashAttribute("message",e.getMessage());
        }
        return "redirect:/users";
    }

}
