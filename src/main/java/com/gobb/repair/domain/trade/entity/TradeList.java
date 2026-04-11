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
public class TradeList {

    private Long serialNumber;              // 고유번호
    private LocalDateTime registDate;       // 등록일
    private String registIp;               // 등록IP
    private LocalDateTime modifyDate;       // 수정일
    private String modifyIp;               // 수정IP
    private String cancelBit;              // 삭제구분
    private String cancelReason;           // 삭제사유
    private String useBit;                 // 사용여부
    private String productBit;             // 판매구분 P:부품, O:용품
    private Long repairAgentSerial;        // 에이전트 FK
    private Long repairClientSerial;       // 클라이언트 FK
    private Long leaseContractSerial;      // 리스계약 FK
    private Long repairCheckItemSerial;    // 정비내역 FK
    private Long commonTradeOrderSerial;   // 거래주문서 FK
    private Long commonTradeDetailSerial;  // 거래주문항목 FK
    private String orderNo;               // 주문번호
    private String tradeBit;              // 입출구분 I:입고, O:출고
    private LocalDateTime tradeDate;      // 거래일자
    private Integer tradeCost;            // 거래금액
    private Integer buyCost;              // 매입금액
    private Integer itemUnit;             // 거래수량
    private String tradeItemType;         // 정품구분 1:정품, 3:비품, 5:중고
    private String tradeItemUse;          // 사용구분 S:판매, M:이동, R:정비, I:입고
    private Long repairCompanySerial;     // 매입거래처 FK
    private Long commonCustomerSerial;    // 고객 FK
    private String memo;                  // 메모
}