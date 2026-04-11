package com.gobb.repair.domain.trade.entity;

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
public class TradeDetail {

    private Long serialNumber;              // 고유번호
    private LocalDateTime registDate;       // 등록일
    private String registIp;               // 등록IP
    private LocalDateTime modifyDate;       // 수정일
    private String modifyIp;               // 수정IP
    private String cancelBit;              // 삭제구분
    private String cancelReason;           // 삭제사유
    private String useBit;                 // 사용여부
    private String productBit;             // 판매구분 P:부품, O:용품
    private Long productSerial;            // 상품정보 FK (부품 or 용품)
    private Long commonTradeOrderSerial;   // 거래주문서 FK
    private String orderNo;               // 주문번호
    private String stateType;             // 처리상태 10:주문접수, 11:주문확정, 12:주문취소, 20:출고완료, 50:판매완료, 60:반품접수
    private String tradeItemType;         // 정품구분 1:정품, 3:비품, 5:중고
    private Integer itemUnit;             // 주문수량
    private LocalDateTime orderDate;      // 주문일자
    private Integer orderCost;            // 주문금액
    private Integer buyCost;              // 매입금액
    private String memo;                  // 메모
}