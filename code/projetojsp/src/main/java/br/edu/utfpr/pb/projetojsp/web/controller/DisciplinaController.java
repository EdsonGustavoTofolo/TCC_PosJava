package br.edu.utfpr.pb.projetojsp.web.controller;

import br.edu.utfpr.pb.projetojsp.model.Disciplina;
import br.edu.utfpr.pb.projetojsp.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Edson on 14/09/2017.
 */
@Controller
@RequestMapping("/disciplina")
public class DisciplinaController {
    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @GetMapping(value = "/findByCurso")
    @ResponseBody
    public List<Disciplina> findByCurso(HttpServletRequest request) {
        String curso = request.getParameter("curso");
        List<Disciplina> disciplinaList = disciplinaRepository.findByCursoId(Long.valueOf(curso));
        return disciplinaList;
    }
}
