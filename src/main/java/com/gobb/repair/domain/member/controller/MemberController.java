package com.gobb.repair.domain.member.controller;

import com.gobb.repair.common.response.ApiResponse;
import com.gobb.repair.domain.member.dto.LoginRequest;
import com.gobb.repair.domain.member.dto.LoginResponse;
import com.gobb.repair.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "인증", description = "로그인/로그아웃/토큰 재발급 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "로그인", description = "아이디/비밀번호로 로그인 후 Access Token + Refresh Token 발급")
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest request,
                                            HttpServletRequest httpRequest) {
        String registIp = httpRequest.getRemoteAddr();
        String userAgent = httpRequest.getHeader("User-Agent");
        return ApiResponse.ok(memberService.login(request, registIp, userAgent));
    }

    @Operation(summary = "Access Token 재발급", description = "Refresh Token으로 새 Access Token 발급")
    @PostMapping("/refresh")
    public ApiResponse<String> refresh(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshToken");
        return ApiResponse.ok(memberService.refreshAccessToken(refreshToken));
    }

    @Operation(summary = "로그아웃", description = "Refresh Token 삭제")
    @PostMapping("/logout")
    public ApiResponse<Void> logout(@AuthenticationPrincipal String userId) {
        memberService.logout(userId);
        return ApiResponse.ok("로그아웃되었습니다.", null);
    }
}