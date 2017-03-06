package br.edu.utfpr.pb.server.controllers;

import br.edu.utfpr.pb.server.model.Pessoa;
import br.edu.utfpr.pb.server.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * https://spring.io/guides/tutorials/bookmarks/
 * http://websystique.com/springmvc/spring-mvc-4-restful-web-services-crud-example-resttemplate/
 * Created by Edson on 05/03/2017.
 */
@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    private final PessoaRepository pessoaRepository;

    @Autowired
    public PessoaController(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    /*@GetMapping(value = "/{id}")
    public Pessoa findOne(@PathVariable Long id) {
        return this.pessoaRepository.findOne(id);
    }*/

    @GetMapping(value = "/")
    public ResponseEntity<List<Pessoa>> listPeople() {
        List<Pessoa> people = this.pessoaRepository.findAll();
        if (people.isEmpty()) {
            return new ResponseEntity<List<Pessoa>>(HttpStatus.NOT_FOUND); //You many decide to return HttpStatus.NO_CONTENT
        }
        return new ResponseEntity<List<Pessoa>>(people, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}"/*, produces = MediaType.APPLICATION_JSON_VALUE*/)
    public ResponseEntity<Pessoa> getPerson(@PathVariable Long id) {
        Pessoa person = this.pessoaRepository.findOne(id);
        if (person == null) {
            return new ResponseEntity<Pessoa>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Pessoa>(person, HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<Void> createUser(@RequestBody Pessoa pessoa, UriComponentsBuilder ucBuilder) {
        this.pessoaRepository.save(pessoa);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/pessoa/{id}").buildAndExpand(pessoa.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Pessoa> updateUser(@PathVariable("id") Long id, @RequestBody Pessoa pessoa) {
        Pessoa currentUser = this.pessoaRepository.findOne(id);

        if (currentUser==null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<Pessoa>(HttpStatus.NOT_FOUND);
        }

        this.pessoaRepository.save(pessoa);
        return new ResponseEntity<Pessoa>(currentUser, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Pessoa> deleteUser(@PathVariable("id") Long id) {
        Pessoa user = this.pessoaRepository.findOne(id);
        if (user == null) {
            System.out.println("Unable to delete. User with id " + id + " not found");
            return new ResponseEntity<Pessoa>(HttpStatus.NOT_FOUND);
        }

        this.pessoaRepository.delete(id);
        return new ResponseEntity<Pessoa>(HttpStatus.NO_CONTENT);
    }
}
