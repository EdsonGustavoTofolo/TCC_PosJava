package br.edu.utfpr.pb.projetojsp.service;

import br.edu.utfpr.pb.projetojsp.model.Usuario;
import br.edu.utfpr.pb.projetojsp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UsuarioService implements UserDetailsService{

	private UsuarioRepository usuarioRepository;
	
	@Autowired
	public UsuarioService(UsuarioRepository usuarioRepository){
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByEmail(email);
		if (usuario == null){
			throw new UsernameNotFoundException("Usuário não encontrado!");
		}
		
		return usuario;
	}

}
