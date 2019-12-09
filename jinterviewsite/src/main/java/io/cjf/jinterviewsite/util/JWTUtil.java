package io.cjf.jinterviewsite.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt.valid.duration}")
    private Long jwtValidDuration;

    @Value("${jwt.HS256.secret}")
    private String jwtHS256Secret;

    @Value("${jwt.issuer}")
    private String issuer;

    public String issueToken(Integer studentId, String openid){
        final Date now = new Date();
        final long nowTimestamp = now.getTime();
        final long expireTimestamp = nowTimestamp + jwtValidDuration;
        final Date expireDate = new Date(expireTimestamp);

        Algorithm algorithm = Algorithm.HMAC256(jwtHS256Secret);
        String token = JWT.create()
                .withIssuer(issuer)
                .withSubject(studentId.toString())
                .withIssuedAt(new Date())
                .withExpiresAt(expireDate)
                .withClaim("openid", openid)
                .withClaim("role", "student")
                .sign(algorithm);

        return token;
    }
}
