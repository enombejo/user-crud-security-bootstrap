package com.example.user_crud_spring_boot.controller;


import com.example.user_crud_spring_boot.model.Role;
import com.example.user_crud_spring_boot.model.User;
import com.example.user_crud_spring_boot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    UserService userService;

    @GetMapping("login")
    public String loginPage() {

        return "login";
    }


    @GetMapping("admin")
    public String getPage(ModelMap model, Principal principal) {
        model.addAttribute("principal", userService.getUserByName(principal.getName()));
        model.addAttribute("users", userService.listUser());
        return "admin";
    }

    @PostMapping("admin/delete")
    public String deleteUser(ModelMap model, @RequestParam(name = "id") long id, Principal principal) {
        System.out.println(id);
        if (id == 0) {
//            model.addAttribute("id2", "строка id пуста");
            model.addAttribute("users", userService.listUser());
            return "admin";
        }
        userService.deleteUser(id);
        model.addAttribute("principal", userService.getUserByName(principal.getName()));
        model.addAttribute("users", userService.listUser());
        return "admin";
    }

    @PostMapping("admin/add")
    public String addUser(ModelMap model, Principal principal,
                          @RequestParam(name = "email") String email,
                          @RequestParam(name = "password") String password,
                          @RequestParam(name = "lastName") String lastName,
                          @RequestParam(name = "firstName") String firstName,
                          @RequestParam(name = "ago") Integer ago,
                          @RequestParam(name = "roles",  required = false) Set<String> roles/**/) {
        User user = new User(email, password, firstName, lastName, ago);
        Set<Role> setRoles = new HashSet<>();
        if ((roles != null)) {
            roles.forEach(n -> setRoles.add(new Role(n, user)));
        } else {
            setRoles.add(new Role("ROLE_USER", user));
        }
        user.setRoles(setRoles);
        user.getRoles().forEach(n -> System.out.println(n.getRole()));
        userService.saveUser(user);
        model.addAttribute("principal", userService.getUserByName(principal.getName()));
        model.addAttribute("users", userService.listUser());
        return "admin";
    }

    @PostMapping("admin/update")
    public String updateUser(ModelMap model, Principal principal,
                             @RequestParam(name = "id") long id,
                             @RequestParam(name = "email") String email,
                             @RequestParam(name = "password") String password,
                             @RequestParam(name = "lastName") String lastName,
                             @RequestParam(name = "firstName") String firstName,
                             @RequestParam(name = "ago") Integer ago,
                             @RequestParam(name = "roles", required = false) Set<String> roles/**/) {
        User user = new User(email, password, firstName, lastName, ago);
        user.setId(id);
        System.out.println(user.toString());
        Set<Role> setRoles = new HashSet<>();
        if ((roles != null)) {
            roles.forEach(n -> setRoles.add(new Role(n, user)));
        } else {
            setRoles.add(new Role("ROLE_USER", user));
        }

        user.setRoles(setRoles);
        userService.updateUser(user);
        model.addAttribute("principal", userService.getUserByName(principal.getName()));
        model.addAttribute("users", userService.listUser());
        return "admin";
    }

    @GetMapping("user")
    public String getUser(ModelMap model, Principal userS) {
        User user = userService.getUserByName(userS.getName());
        model.addAttribute("principal", user);
        model.addAttribute("user", user);
        System.out.println(user.getRoles().contains("ROLE_ADMIN"));
        //boolean containsRole = false;
        //user.getRoles().forEach(n -> containsRole |= n.getRole().equals("ROLE_ADMIN"));
        if (user.getRoles().contains(new Role("ROLE_ADMIN", user))) return "user_admin";
        else return "user";
        //model.addAttribute("messages", user.toString());
    }

}


