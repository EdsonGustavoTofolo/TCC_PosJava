package br.edu.utfpr.pb.projetojsp.repository;

import br.edu.utfpr.pb.projetojsp.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Usuario findByEmail(String email);
	Usuario findByCodigo(String codigo);
}
