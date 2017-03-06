package br.edu.utfpr.pb.server.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * https://spring.io/guides/tutorials/bookmarks/
 * Created by Edson on 05/03/2017.
 */
@RestController
public class HelloController {
    @RequestMapping("/hello/{name}")
    String hello(@PathVariable String name) {
        return "Hi " + name + " !";
    }
}
