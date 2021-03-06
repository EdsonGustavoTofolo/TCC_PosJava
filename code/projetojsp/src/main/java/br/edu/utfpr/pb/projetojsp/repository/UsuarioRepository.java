package br.edu.utfpr.pb.projetojsp.repository;

import br.edu.utfpr.pb.projetojsp.enumeration.TipoUsuarioEnum;
import br.edu.utfpr.pb.projetojsp.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Usuario findByUsername(String username);
	List<Usuario> findByTipo(TipoUsuarioEnum tipo);
}
