package com.example.homework2.member.dto;

import lombok.Getter;

@Getter
public class MemberSaveResponseDto {
    private final Long id;
    private final String email;

    public MemberSaveResponseDto(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
