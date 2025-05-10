package in.ongrid.b2cverification.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {


    private static final String SECRET_KEY = "a33187a802b62b2721d8d43392f112349992f0b690afc7de027a901493332415b5e263c857dfd0b45246176c181ae2a6a4d0bef9cfc406158c5bc51f318128b31788ebbb25ccc824793cb7c3f17bef1c4d77939f1e809277e745ac4e3d9989a1370c20809c6dfd21e40c935386fa292c847b3ee2c28dbd12ea06c76fa45f0a7308efb637d5b75d2f1717c02105fe1c89dfcaba4b0ebda13fc7cb89a3c8d471d10ee8b6d89cdab8d5804ce355620c69b8e226e8029d80d9dc98a2d903fff442b010c0ed491c3c962a4b3b8568f5a08740f3e83b7b2669b32a0bda8a5d1669595bc52a36498adda9e5c0770bf2f1bce1599bc75490986dab5051f7446599892aaf";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);//extracting username (email) here
    }



    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    //if i ned to generate token by just usimg the useremail
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }
    public boolean isTokenValid(String token,UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date(System.currentTimeMillis()));
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getSignInKey(),SignatureAlgorithm.HS512)
                .compact();
    }
    public Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
