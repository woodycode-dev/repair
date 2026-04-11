package com.gobb.repair.domain.item.entity;

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
public class BikeItemCd {

    private Long serialNumber;       // 고유번호
    private LocalDateTime registDate; // 등록일
    private String registIp;          // 등록IP
    private LocalDateTime modifyDate; // 수정일
    private String modifyIp;          // 수정IP
    private String cancelBit;         // 삭제구분 Y:삭제, N:승인
    private LocalDateTime cancelDate;  // 삭제일시
    private String cancelReason;      // 삭제사유
    private String useBit;            // 사용여부 Y:사용, N:사용안함
    private String codeType;          // 대분류코드 1:차체, 3:전장, 5:엔진
    private String subject;           // 부품분류명
    private String memo;              // 메모
}