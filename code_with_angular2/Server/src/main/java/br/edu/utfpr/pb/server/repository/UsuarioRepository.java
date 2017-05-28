package br.edu.utfpr.pb.server.repository;

import br.edu.utfpr.pb.server.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findByUsername(String username);
	
}
