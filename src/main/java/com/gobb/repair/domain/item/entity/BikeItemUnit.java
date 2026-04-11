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
public class BikeItemUnit {

    private Long serialNumber;        // 고유번호
    private LocalDateTime registDate; // 등록일
    private String registIp;          // 등록IP
    private LocalDateTime modifyDate; // 수정일
    private String modifyIp;          // 수정IP
    private String cancelBit;         // 삭제구분
    private LocalDateTime cancelDate;  // 삭제일시
    private String cancelReason;      // 삭제사유
    private String useBit;            // 사용여부
    private Long bikeItemSerial;      // 부품 FK
    private String unitName;          // 부품상세명
    private String unitEnName;        // 부품상세명(영문)
    private String unitNickName;      // 부품상세명(필드)
    private String unitCode;          // 부품번호
    private Integer unitCost;         // 공장도가
    private Integer unitTax;          // 세액
    private Integer unitPrice;        // 공급가액
    private Integer unitLimit;        // 재고현황
    private String memo;              // 메모
}