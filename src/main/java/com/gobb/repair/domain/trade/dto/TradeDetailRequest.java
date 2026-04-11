package com.gobb.repair.domain.trade.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class TradeDetailRequest {

    private Long productSerial;     // 부품 or 용품 FK
    private String productBit;      // P:부품, O:용품
    private String tradeItemType;   // 1:정품, 3:비품, 5:중고
    private Integer itemUnit;       // 주문수량
    private Integer orderCost;      // 주문금액
    private Integer buyCost;        // 매입금액
    private String memo;
}