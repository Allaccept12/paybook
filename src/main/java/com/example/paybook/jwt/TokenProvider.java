package com.example.paybook.jwt;

import com.example.paybook.common.model.ResponseDto;
import com.example.paybook.member.domain.Member;
import com.example.paybook.member.domain.Token;
import com.example.paybook.member.domain.TokenRepository;
import com.example.paybook.member.dto.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;
    private final Key key;
    private final TokenRepository tokenRepository;

    public TokenProvider(@Value("${jwt.secret}") String secretKey,TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDto generateTokenDto(Member member) {
        long now = (new Date().getTime());

        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder()
                .setSubject(member.getEmail().getEmail())
                .claim(AUTHORITIES_KEY, "ROLE_MEMBER")
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        Token token = Token.of(member,accessToken,accessTokenExpiresIn.getTime());
        tokenRepository.save(token);

        return new TokenDto(accessToken,accessTokenExpiresIn.getTime());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    public Token isPresentToken(Member member) {
        Optional<Token> optionalToken = tokenRepository.findByMember(member);
        return optionalToken.orElse(null);
    }

    @Transactional
    public ResponseDto<?> deleteToken(Member member) {
        Token token = isPresentToken(member);
        if (null == token) {
            throw new NotFoundTokenException();
        }
        tokenRepository.delete(token);
        return ResponseDto.success("success");
    }
}
