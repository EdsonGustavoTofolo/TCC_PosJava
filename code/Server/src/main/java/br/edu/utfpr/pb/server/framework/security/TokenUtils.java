package br.edu.utfpr.pb.server.framework.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import br.edu.utfpr.pb.posjava.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenUtils {
	
	private final String AUDIENCE_WEB = "web";
	private final String AUDIENCE_MOBILE = "mobile";
	private final String AUDIENCE_TABLET = "tablet";
	
	private String secret = "posjava";
	private Long expiration = 604800L; //EM MILISECONDS
	
	public String getUsernameFromToken(String token) {
		String username;
		try {
			final Claims claims = this.getClaimsFromToken(token);
			username = claims.getSubject();
		} catch(Exception e) {
			username = null;
		}
		return username;
	}

	private Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();					
		} catch(Exception e) {
			claims = null;
		}
		return claims;
	}
	
	public Date getCreatedDateFromToken(String token) {
		Date created;
		try {
			final Claims claims = this.getClaimsFromToken(token);
			created = new Date((Long) claims.get("created"));
		} catch (Exception e) {
			created = null;
		}
		return created;
	}
	
	public Date getExpirationDateFromToken(String token) {
		Date expiration;
		try {
			final Claims claims = this.getClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (Exception e) {
			expiration = null;
		}
		return expiration;
	}
	
	public String getAudienceFromToken(String token) {
		String audience;
		try {
			final Claims claims = this.getClaimsFromToken(token);
			audience = (String) claims.get("audience");
		} catch (Exception e) {
			audience = null;
		}
		return audience;
	}
	
	private Date generateCurrentDate() {
		return new Date(System.currentTimeMillis());
	}
	
	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + this.expiration * 1000);
	}
	
	private Boolean isTokenExpired(String token) {
		final Date expiration = this.getExpirationDateFromToken(token);
		return expiration.before(this.generateCurrentDate());
	}

	/**
	 * Serve para revogar um token quando o usuário alterar a senha
	 * @param created
	 * @param lastPasswordReset campo da entity Usuario, que é gravada quando o usuário altera a senha
	 * @return
	 */
	public Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
		return (lastPasswordReset != null && created.before(lastPasswordReset));
	}
	
	/**
	 * Ignora o token expirado se estiver acessando de um tablet ou mobile, neste caso
	 * @param token
	 * @return
	 */
	private Boolean ignoreTokenExpiration(String token) {
		String audience = this.getAudienceFromToken(token);
		return (this.AUDIENCE_TABLET.equals(audience) || this.AUDIENCE_MOBILE.equals(audience));
	}
	
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("sub", userDetails.getUsername());
		claims.put("audience", this.AUDIENCE_WEB);
		claims.put("created", this.generateCurrentDate());
		return this.generateToken(claims);
	}

	private String generateToken(Map<String, Object> claims) {		
		return Jwts.builder().setClaims(claims).setExpiration(this.generateExpirationDate()).signWith(SignatureAlgorithm.HS512, this.secret).compact();
	}
	
	public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
		final Date created = this.getCreatedDateFromToken(token);
		return (
				!(this.isCreatedBeforeLastPasswordReset(created, lastPasswordReset)) && 
				(!(this.isTokenExpired(token)) || this.ignoreTokenExpiration(token))
				);
	}
	
	public String refreshToken(String token) {
		String refreshed;
		try {
			final Claims claims = this.getClaimsFromToken(token);
			claims.put("created", this.generateCurrentDate());
			refreshed = this.generateToken(claims);
		} catch (Exception e) {
			refreshed = null;
		}
		return refreshed;
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		Usuario user = (Usuario) userDetails;
		final String username = this.getUsernameFromToken(token);
		final Date created = this.getCreatedDateFromToken(token);
		return (username.equals(user.getUsername()) && 
				!(this.isTokenExpired(token)) && 
				!(this.isCreatedBeforeLastPasswordReset(created, user.getLastPasswordReset())));
	}
}
