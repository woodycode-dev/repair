package com.gobb.repair.domain.log.entity;

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
public class DataLog {

    private Long serialNumber;        // 고유번호
    private LocalDateTime registDate; // 등록일
    private String registIp;          // 등록IP
    private String useBit;            // 사용여부
    private String tableName;         // 참조테이블명
    private Long fkSerial;            // 참조테이블 고유번호
    private String stateType;         // 처리상태 I:입력, U:수정, C:삭제, D:영구삭제
    private String userId;            // 로그인 회원아이디
    private String summary;           // 변경내역
}