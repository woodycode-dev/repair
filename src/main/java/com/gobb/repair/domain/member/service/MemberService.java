package com.gobb.repair.domain.member.service;

import com.gobb.repair.common.exception.BusinessException;
import com.gobb.repair.common.util.JwtUtil;
import com.gobb.repair.domain.member.dto.LoginRequest;
import com.gobb.repair.domain.member.dto.LoginResponse;
import com.gobb.repair.domain.member.entity.Member;
import com.gobb.repair.domain.member.entity.MemberLog;
import com.gobb.repair.domain.member.mapper.MemberLogMapper;
import com.gobb.repair.domain.member.mapper.MemberMapper;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;
    private final MemberLogMapper memberLogMapper;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    /**
     * 로그인
     * 아이디/비밀번호 검증 후 Access Token + Refresh Token 발급
     * 로그인 이력 저장
     */
    public LoginResponse login(LoginRequest request, String registIp, String userAgentStr) {
        Member member = memberMapper.findByUserId(request.getUserId());

        if (member == null) {
            throw new BusinessException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new BusinessException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        // Access Token 생성
        String accessToken = jwtUtil.generateToken(member.getUserId());

        // Refresh Token 생성 (UUID)
        String refreshToken = UUID.randomUUID().toString();

        // Refresh Token DB 저장
        member.setRefreshToken(refreshToken);
        member.setRefreshTokenExpire(LocalDateTime.now().plusDays(refreshExpiration));
        memberMapper.updateRefreshToken(member);

        // 로그인 정보 업데이트
        memberMapper.updateLoginInfo(member);

        // User-Agent 파싱
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentStr);
        String os = userAgent.getOperatingSystem().getName();
        String browser = userAgent.getBrowser().getName();
        String version = userAgent.getBrowserVersion() != null
                ? userAgent.getBrowserVersion().toString() : "";

        // 로그인 이력 저장
        MemberLog memberLog = MemberLog.builder()
                .registIp(registIp)
                .membershipSerial(member.getSerialNumber())
                .os(os)
                .browser(browser)
                .version(version)
                .agent(userAgentStr)
                .stateBit("I")
                .build();
        memberLogMapper.insert(memberLog);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(member.getUserId())
                .userName(member.getUserName())
                .userType(member.getUserType())
                .serviceType(member.getServiceType())
                .build();
    }

    /**
     * Access Token 재발급
     */
    public String refreshAccessToken(String refreshToken) {
        Member member = memberMapper.findByRefreshToken(refreshToken);

        if (member == null) {
            throw new BusinessException("유효하지 않은 Refresh Token입니다.");
        }

        if (member.getRefreshTokenExpire().isBefore(LocalDateTime.now())) {
            throw new BusinessException("Refresh Token이 만료되었습니다. 다시 로그인해주세요.");
        }

        return jwtUtil.generateToken(member.getUserId());
    }

    /**
     * 로그아웃
     * Refresh Token 삭제
     */
    public void logout(String userId) {
        Member member = memberMapper.findByUserId(userId);
        if (member == null) {
            throw new BusinessException("사용자를 찾을 수 없습니다.");
        }
        member.setRefreshToken(null);
        member.setRefreshTokenExpire(null);
        memberMapper.updateRefreshToken(member);
    }
}