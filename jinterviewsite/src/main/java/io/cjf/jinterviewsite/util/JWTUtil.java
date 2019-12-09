package io.cjf.jinterviewsite.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import io.cjf.jinterviewsite.vo.StudentLoginVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${jwt.valid.duration}")
    private Long jwtValidDuration;

    @Value("${jwt.issuer}")
    private String issuer;

    private Algorithm algorithm;

    public JWTUtil(@Value("${jwt.HS256.secret}") String jwtHS256Secret){
        logger.info("init jwt util");
        algorithm = Algorithm.HMAC256(jwtHS256Secret);
    }

    public String issueToken(Integer studentId, String openid){
        final Date now = new Date();
        final long nowTimestamp = now.getTime();
        final long expireTimestamp = nowTimestamp + jwtValidDuration*1000;
        final Date expireDate = new Date(expireTimestamp);


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

    public StudentLoginVO verifyToken(String token){
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .build();
        DecodedJWT jwt = verifier.verify(token);
        final StudentLoginVO studentLoginVO = new StudentLoginVO();
        studentLoginVO.setStudentId(Integer.parseInt(jwt.getSubject()));
        return studentLoginVO;
    }
}
