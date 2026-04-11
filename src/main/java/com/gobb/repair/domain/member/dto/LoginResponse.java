package com.gobb.repair.domain.member.dto;

import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String accessToken;   // Access Token
    private String refreshToken;  // Refresh Token
    private String userId;        // 사용자 아이디
    private String userName;      // 사용자 성명
    private String userType;      // 사용권한
    private String serviceType;   // 서비스구분
}