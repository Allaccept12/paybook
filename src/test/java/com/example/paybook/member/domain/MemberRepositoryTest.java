package com.example.paybook.member.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void initializeTable() {
        memberRepository.deleteAll();
        Member member = Member.createMember(Email.of("ekdmd@naver.com"), Password.of("1234"));
        memberRepository.save(member);
    }
    @Test
    @DisplayName("존재하는 이메일인지 확인")
    void existByEmail() {
        //given
        String testEmail = "ekdmd@naver.com";
        // when
        Long result = memberRepository.existByEmail(Email.of(testEmail));
        // then
        assertEquals(1, result);
    }
    @Test
    @DisplayName("이메일로 유저 검색")
    void findByEmail() {
        // given
        String testEmail = "ekdmd@naver.com";
        // when
        Optional<Member> member = memberRepository.findByEmail(Email.of(testEmail));
        // then
        assertEquals(member.get().getEmail().getEmail(), testEmail);
    }
}