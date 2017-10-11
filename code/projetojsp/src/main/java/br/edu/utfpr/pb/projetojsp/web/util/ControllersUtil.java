package br.edu.utfpr.pb.projetojsp.web.util;

import br.edu.utfpr.pb.projetojsp.model.Permissao;
import br.edu.utfpr.pb.projetojsp.model.Usuario;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Edson on 08/10/2017.
 */
public class ControllersUtil {
    public static Usuario getLoggedUser() {
        return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static Boolean hasLoggedUserRole(String role) {
        Usuario usuario = getLoggedUser();
        for (Permissao permissao : usuario.getPermissoes()) {
            if (permissao.getPermissao().equals(role)) {//se for do tipo aluno, retorna somente os requerimentos dele
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public static Boolean hasLoggedUserAnyRole(String...roles) {
        List<String> roleList = Arrays.asList(roles);
        Usuario usuario = getLoggedUser();
        return roleList.containsAll(usuario.getPermissoes());
    }
}
