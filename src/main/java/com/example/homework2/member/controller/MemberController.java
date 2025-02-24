package com.example.homework2.member.controller;

import com.example.homework2.common.consts.Const;
import com.example.homework2.member.dto.MemberResponseDto;
import com.example.homework2.member.dto.MemberSaveResponseDto;
import com.example.homework2.member.dto.MemberUpdateRequestDto;
import com.example.homework2.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

//    @PostMapping("/members")
//    public MemberSaveResponseDto save (@RequestBody MemberSaveResponseDto dto) {
//        return memberService.save(dto);
//    }

    @GetMapping("/members")
    public List<MemberResponseDto> getAll () {
        return memberService.findAll();
    }

    @GetMapping("/members/{memberId}")
    public MemberResponseDto getOne (@PathVariable Long memberId) {
        return memberService.findById(memberId);
    }

    @PutMapping("/members")
    public void update (
            @SessionAttribute(name = Const.LOGIN_MEMBER) Long memberId,
            @RequestBody MemberUpdateRequestDto dto) {
         memberService.update(memberId, dto);
    }

    @DeleteMapping("/members")
    public void delete (@SessionAttribute(name = Const.LOGIN_MEMBER) Long memberId) {
        memberService.deleteById(memberId);
    }
}
