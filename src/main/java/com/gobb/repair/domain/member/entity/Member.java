package com.gobb.repair.domain.member.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    private Long serialNumber;          // 고유번호 (추가)
    private String userId;              // 로그인 아이디
    private LocalDateTime registDate;   // 등록일
    private String registIp;            // 등록IP
    private LocalDateTime modifyDate;   // 수정일
    private String modifyIp;            // 수정IP
    private String cancelBit;           // 삭제구분
    private String cancelReason;        // 삭제사유
    private String useBit;              // 사용여부
    private String serviceType;         // 서비스구분 C:공용, R:정비, L:리스
    private Long agentSerial;           // 에이전트 FK
    private Long clientSerial;          // 클라이언트 FK
    private String userType;            // 사용권한 1:운영자, 3:사용자
    private String userName;            // 사용자 성명
    private String password;            // 비밀번호
    private String email;               // 이메일
    private String telNo;               // 연락처
    private String refreshToken;        // Refresh Token
    private LocalDateTime refreshTokenExpire; // Refresh Token 만료일
    private Integer visit;              // 로그인 성공횟수
    private LocalDateTime loginDate;    // 로그인 성공일
    private LocalDateTime passwordDate; // 비밀번호 수정일
    private String memo;                // 메모
}