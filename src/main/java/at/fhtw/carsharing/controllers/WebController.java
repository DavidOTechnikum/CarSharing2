package at.fhtw.carsharing.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class WebController {

    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("serverTime", new SimpleDateFormat().format(new Date()));
        return "hello";
    }

    @PostMapping("/hello")
    public String helloSubmit(Model model) {
        model.addAttribute("serverTime", new SimpleDateFormat().format(new Date()));
        return "hello";
    }
}
