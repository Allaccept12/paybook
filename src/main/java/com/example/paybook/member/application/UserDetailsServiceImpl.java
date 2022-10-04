package com.example.paybook.member.application;

import com.example.paybook.member.domain.Email;
import com.example.paybook.member.domain.Member;
import com.example.paybook.member.domain.MemberRepository;
import com.example.paybook.member.domain.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> member = memberRepository.findByEmail(Email.of(email));
        return member
                .map(UserDetailsImpl::new)
                .orElseThrow(MemberNotFoundException::new);
    }
}
