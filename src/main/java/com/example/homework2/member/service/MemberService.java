package com.example.homework2.member.service;

import com.example.homework2.member.dto.MemberResponseDto;
import com.example.homework2.member.dto.MemberSaveResponseDto;
import com.example.homework2.member.dto.MemberUpdateRequestDto;
import com.example.homework2.member.entity.Member;
import com.example.homework2.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private  final MemberRepository memberRepository;

//    @Transactional
//    public MemberSaveResponseDto save(MemberSaveResponseDto dto) {
//        Member member = new Member(dto.getEmail());
//        Member savedMember = memberRepository.save(member);
//        return new MemberSaveResponseDto(savedMember.getId(), savedMember.getEmail());
//    }

    @Transactional(readOnly = true)
    public List<MemberResponseDto> findAll() {
        List<Member> members = memberRepository.findAll();

        List<MemberResponseDto> dtos = new ArrayList<>();

        for (Member member : members) {
            dtos.add(new MemberResponseDto(member.getId(), member.getEmail()));
        }
        return dtos;

        //stream 방식으로 작성시 코드
//        members.stream().map(
//                member -> {
//                    return new MemberResponseDto(member.getId(), member.getEmail());
//                }
//        )
    }

    @Transactional(readOnly = true)
    public MemberResponseDto findById(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalStateException("해당 id를 가진 회원이 존재하지 않습니다.")
        );
        return new MemberResponseDto(member.getId(), member.getEmail());
    }

    @Transactional
    public void update(Long memberId, MemberUpdateRequestDto dto) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalStateException("해당 id를 가진 회원이 존재하지 않습니다.")
        );
        member.update(dto.getEmail());
    }

    @Transactional
    public void deleteById(Long memberId) {

        memberRepository.deleteById(memberId);
    }
}
