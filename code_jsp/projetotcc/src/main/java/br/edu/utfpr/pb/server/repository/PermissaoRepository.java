package br.edu.utfpr.pb.server.repository;

import br.edu.utfpr.pb.server.model.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissaoRepository extends JpaRepository<Permissao, Long> {

	Permissao findByPermissao(String permissao);
	
}
