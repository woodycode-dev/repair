package com.gobb.repair.domain.item.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class BikeItemInsertRequest {

    private String registIp;
    private String useBit;
    private Long unitSerial;        // 부품분류 FK
    private String makerType;       // 제조사
    private Long bikeSerial;        // 차종 FK
    private String makerItemCode;   // 제조사 발행코드
    private String itemName;        // 부품명
    private String itemEnName;      // 부품명(영문)
    private String itemNickName;    // 부품명(필드)
    private String memo;
}