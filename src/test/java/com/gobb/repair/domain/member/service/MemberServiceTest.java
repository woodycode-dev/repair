package com.gobb.repair.domain.member.service;

import com.gobb.repair.common.exception.BusinessException;
import com.gobb.repair.common.util.JwtUtil;
import com.gobb.repair.domain.member.dto.LoginRequest;
import com.gobb.repair.domain.member.dto.LoginResponse;
import com.gobb.repair.domain.member.entity.Member;
import com.gobb.repair.domain.member.mapper.MemberLogMapper;
import com.gobb.repair.domain.member.mapper.MemberMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    private static final String USER_ID = "admin";
    private static final String RAW_PASSWORD = "test1234";
    private static final String USER_AGENT =
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0 Safari/537.36";

    @Mock
    private MemberMapper memberMapper;

    @Mock
    private MemberLogMapper memberLogMapper;

    @Mock
    private JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private MemberService memberService;

    @BeforeEach
    void setUp() {
        memberService = new MemberService(memberMapper, memberLogMapper, jwtUtil, passwordEncoder);
    }

    @Test
    void 해시된_비밀번호와_일치하면_토큰을_발급한다() {
        when(memberMapper.findByUserId(USER_ID)).thenReturn(member(passwordEncoder.encode(RAW_PASSWORD)));
        when(jwtUtil.generateToken(USER_ID)).thenReturn("access-token");

        LoginResponse response = memberService.login(request(RAW_PASSWORD), "127.0.0.1", USER_AGENT);

        assertThat(response.getAccessToken()).isEqualTo("access-token");
        assertThat(response.getRefreshToken()).isNotBlank();
        assertThat(response.getUserId()).isEqualTo(USER_ID);
        verify(memberMapper).updateRefreshToken(any(Member.class));
        verify(memberMapper).updateLoginInfo(any(Member.class));
        verify(memberLogMapper).insert(any());
    }

    @Test
    void 비밀번호가_틀리면_예외가_발생한다() {
        when(memberMapper.findByUserId(USER_ID)).thenReturn(member(passwordEncoder.encode(RAW_PASSWORD)));

        assertThatThrownBy(() -> memberService.login(request("wrong"), "127.0.0.1", USER_AGENT))
                .isInstanceOf(BusinessException.class)
                .hasMessage("아이디 또는 비밀번호가 올바르지 않습니다.");
        verify(jwtUtil, never()).generateToken(any());
    }

    @Test
    void DB에_평문_비밀번호가_남아있으면_로그인에_실패한다() {
        when(memberMapper.findByUserId(USER_ID)).thenReturn(member(RAW_PASSWORD));

        assertThatThrownBy(() -> memberService.login(request(RAW_PASSWORD), "127.0.0.1", USER_AGENT))
                .isInstanceOf(BusinessException.class)
                .hasMessage("아이디 또는 비밀번호가 올바르지 않습니다.");
        verify(jwtUtil, never()).generateToken(any());
    }

    @Test
    void 존재하지_않는_아이디면_예외가_발생한다() {
        when(memberMapper.findByUserId(USER_ID)).thenReturn(null);

        assertThatThrownBy(() -> memberService.login(request(RAW_PASSWORD), "127.0.0.1", USER_AGENT))
                .isInstanceOf(BusinessException.class)
                .hasMessage("아이디 또는 비밀번호가 올바르지 않습니다.");
    }

    private LoginRequest request(String password) {
        LoginRequest request = new LoginRequest();
        request.setUserId(USER_ID);
        request.setPassword(password);
        return request;
    }

    private Member member(String storedPassword) {
        return Member.builder()
                .serialNumber(1L)
                .userId(USER_ID)
                .userName("관리자")
                .password(storedPassword)
                .build();
    }
}
