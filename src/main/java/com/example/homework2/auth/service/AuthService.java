package com.example.homework2.auth.service;

import com.example.homework2.auth.dto.AuthLoginRequestDto;
import com.example.homework2.auth.dto.AuthLoginResponseDto;
import com.example.homework2.auth.dto.AuthSignupRequestDto;
import com.example.homework2.member.entity.Member;
import com.example.homework2.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;

    @Transactional
    public void signup(AuthSignupRequestDto dto) {
        Member member = new Member(dto.getEmail());
        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public AuthLoginResponseDto login(AuthLoginRequestDto dto) {
        Member member = memberRepository.findByEmail(dto.getEmail()).orElseThrow(
                ()-> new IllegalStateException("존재하지 않는 멤버 입니다.")
        );
        return new AuthLoginResponseDto(member.getId());
    }
}
