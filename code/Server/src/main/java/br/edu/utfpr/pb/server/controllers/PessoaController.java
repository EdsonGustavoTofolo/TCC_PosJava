package br.edu.utfpr.pb.server.controllers;

import br.edu.utfpr.pb.server.model.Pessoa;
import br.edu.utfpr.pb.server.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
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

    @GetMapping(value = "/{id}")
    public Pessoa findOne(@PathVariable Long id) {
        return this.pessoaRepository.findOne(id);
    }
}
