package br.edu.utfpr.pb.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * https://spring.io/guides/tutorials/bookmarks/
 * Created by Edson on 05/03/2017.
 */
@Controller
public class HelloController {
    @RequestMapping("/hello")
    public String welcome(Map<String, Object> model) {
        model.put("message", "HELLO TO THE JSP WORLD!");
        return "/";
    }
}
