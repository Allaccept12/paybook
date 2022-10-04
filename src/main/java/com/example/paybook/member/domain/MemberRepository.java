package com.example.paybook.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m.id from Member m where m.email =:email")
    Long existByEmail(@Param("email") Email email);

    Optional<Member> findByEmail(Email email);
}
