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
public class TradeOrder {

    private Long serialNumber;          // 고유번호
    private LocalDateTime registDate;   // 등록일
    private String registIp;            // 등록IP
    private LocalDateTime modifyDate;   // 수정일
    private String modifyIp;            // 수정IP
    private String cancelBit;           // 삭제구분
    private String cancelReason;        // 삭제사유
    private String useBit;              // 사용여부
    private String productBit;          // 판매구분 P:부품, O:용품
    private Long fromRepairAgent;       // 주문 에이전트
    private Long fromRepairClient;      // 주문 클라이언트
    private Long toRepairAgent;         // 수신 에이전트
    private Long toRepairClient;        // 수신 클라이언트
    private String orderNo;             // 주문번호
    private LocalDateTime orderDate;    // 주문일자
    private Integer orderCost;          // 주문금액
    private Integer buyCost;            // 매입금액
    private String stateType;           // 처리상태 10:주문접수, 11:주문확정, 12:주문취소, 20:출고완료, 50:판매완료
    private String summary;             // 적요
    private String tradeType;           // 거래구분 S:판매, I:입고
    private Integer itemUnit;           // 주문수량
    private String memo;                // 메모
}