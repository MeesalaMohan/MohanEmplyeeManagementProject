package com.test.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

	public String generatetoken(String username) {
		Map<String, Object> Cliams = new HashMap<>();
		return CreateToken(Cliams, username);
		// TODO Auto-generated method stub

	}

	private String CreateToken(Map<String, Object> cliams, String username) {
		// TODO Auto-generated method stub
		return Jwts.builder().setClaims(cliams).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
				.signWith(genkey(), SignatureAlgorithm.HS256).compact();
	}

	private Key genkey() {
		byte[] keybytes = Decoders.BASE64.decode("3273357638792F423F4528482B4D6251655368566D597133743677397A244326");
		// TODO Auto-generated method stub
		return Keys.hmacShaKeyFor(keybytes);
	}

	public String extractUsername(String token) {
		//cliams is class in this have  method get subject is gets  usernames 
		return extractCliams(token, Claims::getSubject);
		// TODO Auto-genera]ted method stub

	}

	private <T> T extractCliams(String token, Function<Claims, T> claimsResolver) {
		
		Claims cliams = extractAllClaims(token);
		//in this method functonal interface  have checks the cliams and token cliams is equal return username  
		return claimsResolver.apply(cliams);
	}

	private Claims extractAllClaims(String token) {

		return Jwts.parserBuilder().setSigningKey(genkey()).build().parseClaimsJws(token).getBody();
	}
// checking section  for token 
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		//boolean flag = username.equals(userDetails.getUsername()) && !isTokenExpired(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
//checks token expired or not
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	//checks expiration

	public Date extractExpiration(String token) {
		return extractCliams(token, Claims::getExpiration);
	}

}
