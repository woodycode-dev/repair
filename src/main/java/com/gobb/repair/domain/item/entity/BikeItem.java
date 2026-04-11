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
public class BikeItem {

    private Long serialNumber;        // 고유번호
    private LocalDateTime registDate; // 등록일
    private String registIp;          // 등록IP
    private LocalDateTime modifyDate; // 수정일
    private String modifyIp;          // 수정IP
    private String cancelBit;         // 삭제구분
    private LocalDateTime cancelDate;  // 삭제일시
    private String cancelReason;      // 삭제사유
    private String useBit;            // 사용여부
    private Long unitSerial;          // 부품분류 FK
    private String makerType;         // 차량제조사 1:혼다, 3:SYM, 5:KR, 7:대림
    private Long bikeSerial;          // 차량 FK
    private String makerItemCode;     // 제조사 발행코드
    private String itemName;          // 부품명
    private String itemEnName;        // 부품명(영문)
    private String itemNickName;      // 부품명(필드)
    private Integer itemUnitSet;      // 부품 세부항목수
    private String realName;          // 이미지 실제파일명
    private String fileName;          // 이미지 저장파일명
    private Integer fileSize;         // 파일용량
    private String fileType;          // 파일타입
    private Integer imgWidth;         // 이미지 너비
    private Integer imgHeight;        // 이미지 높이
    private String memo;              // 메모
}