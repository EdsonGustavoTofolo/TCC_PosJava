package br.edu.utfpr.piloto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
