package br.edu.utfpr.pb.projetojsp.service;

import br.edu.utfpr.pb.projetojsp.model.Permissao;
import br.edu.utfpr.pb.projetojsp.model.Usuario;
import br.edu.utfpr.pb.projetojsp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	private UsuarioRepository usuarioRepository;

	@Autowired
	public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByEmail(username);
		if (usuario == null){
			throw new UsernameNotFoundException("Usuário não encontrado!");
		}
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		for (Permissao role : usuario.getPermissoes()){
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getPermissao()));
		}

		return new org.springframework.security.core.userdetails.User(usuario.getEmail(), usuario.getSenha(), grantedAuthorities);
	}

}
