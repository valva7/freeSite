package com.web.freesite.service;

import com.web.freesite.domain.Member;
import com.web.freesite.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    // 사용자 정보를 저장하는 서비스나 레포지토리를 주입합니다.
    private final MemberRepository memberRepository;

    @Autowired
    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 사용자 이름(username)을 기반으로 데이터베이스에서 사용자 정보를 조회합니다.
        Member memberEntity = memberRepository.getMemberByEmail(email);

        if (memberEntity == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + email);
        }

        // User 클래스를 사용하여 UserDetails 객체를 생성합니다.
        return User.builder()
                .username(memberEntity.getEmail())
                .password(memberEntity.getAccessToken())
                .roles("USER") // 사용자의 권한을 설정합니다.
                .build();
    }
}