package br.edu.utfpr.pb.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * https://spring.io/guides/tutorials/bookmarks/
 * Created by Edson on 05/03/2017.
 */
@Controller
public class HelloController {
    @GetMapping("/hello")
    public String hello(Map<String, Object> model) {
        model.put("message", "HELLO JSP WORLD!");
        return "/";
    }
}
