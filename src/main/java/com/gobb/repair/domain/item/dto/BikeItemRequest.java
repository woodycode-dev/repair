package com.gobb.repair.domain.item.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class BikeItemRequest {

    private String useBit;          // 사용여부
    private Long unitSerial;        // 부품분류 FK
    private String makerType;       // 제조사
    private Long bikeSerial;        // 차종 FK
    private String makerItemCode;   // 제조사 발행코드
    private String itemNickName;    // 부품명(필드)
    private String keywordType;     // 1:부품명, 2:부품명(영문)
    private String keyword;         // 검색어
}