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
public class MemberLog {

    private Long serialNumber;          // 고유번호
    private LocalDateTime registDate;   // 등록일
    private String registIp;            // 등록IP
    private Long membershipSerial;      // CM_MEMBERSHIP FK
    private String os;                  // 접속 OS
    private String browser;             // 접속 브라우저
    private String version;             // 브라우저 버전
    private String agent;               // 에이전트 정보
    private String stateBit;            // I:로그인, O:로그아웃
}