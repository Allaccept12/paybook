package com.example.paybook.member.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class TokenRepositoryTest {

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void initializeTable() {
        tokenRepository.deleteAll();
        memberRepository.deleteAll();
        Member member = Member.createMember(Email.of("ekdmd@naver.com"), Password.of("1234"));
        memberRepository.save(member);
        Token token = Token.of(member, "value", 1234L);
        tokenRepository.save(token);
    }

    @Test
    @DisplayName("유저데이터로 토큰정보 가져오기")
    void findByMember() {
        // given
        String email = "ekdmd@naver.com";
        Optional<Member> member = memberRepository.findByEmail(Email.of(email));
        // when
        Optional<Token> result = tokenRepository.findByMember(member.get());
        // then
        assertEquals("value",result.get().getValue());
    }

}