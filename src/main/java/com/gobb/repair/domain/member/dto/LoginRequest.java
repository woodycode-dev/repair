package com.gobb.repair.domain.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {

    private String userId;    // 로그인 아이디
    private String password;  // 비밀번호
}