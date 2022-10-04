package com.example.paybook.financialledger.domain;

import com.example.paybook.member.domain.Email;
import com.example.paybook.member.domain.Member;
import com.example.paybook.member.domain.MemberRepository;
import com.example.paybook.member.domain.Password;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@ActiveProfiles("test")
class FinancialLedgerRepositoryTest {

    @Autowired
    FinancialLedgerRepository financialLedgerRepository;

    @Autowired
    MemberRepository memberRepository;



    String testEmail = "ekdmd@naver.com";

    @BeforeEach
    void initializeTable() {

        memberRepository.deleteAll();
        Member member = Member.createMember(Email.of(testEmail), Password.of("1234"));
        memberRepository.save(member);
        FinancialLedger financialLedger1 = FinancialLedger.createFinancialLedger(
                Memo.of("초코에몽 1개"), Money.of(1100), member
        );
        FinancialLedger financialLedgerDeleteFlagTrue = FinancialLedger.createFinancialLedger(
                Memo.of("딸기에몽 1개"), Money.of(1200), member
        );
        financialLedgerDeleteFlagTrue.updateDeleteFlag();
        financialLedgerRepository.save(financialLedger1);
        financialLedgerRepository.save(financialLedgerDeleteFlagTrue);


    }

    @Test
    @DisplayName("멤버 pk값으로 장부데이터 가져오기")
    void findByMemberId() {
        // given
        Optional<Member> member = memberRepository.findByEmail(Email.of(testEmail));
        // when
        List<FinancialLedger> result = financialLedgerRepository.findByMemberId(member.get().getId());
        // then
        assertEquals(1, result.size());
        assertEquals("딸기에몽 1개", result.get(1).getMemo().getMemo());
    }

    @Test
    @DisplayName("FinancialLedger엔티티의 @Where쿼리를 타지않는 native쿼리 - pk로 FinancialLedger엔티티 가졍오기")
    void findByMemberIdAtNativeQuery() {
        // given
        Long financialLedgerId = 2L;
        // when
        Optional<FinancialLedger> result = financialLedgerRepository.findByMemberIdAtNativeQuery(financialLedgerId);
        // then
        assertEquals(result.get().getId(), financialLedgerId);
        assertTrue(result.get().isDeleteFlag());
    }

}