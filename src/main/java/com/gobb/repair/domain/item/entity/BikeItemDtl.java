package com.gobb.repair.domain.item.entity;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BikeItemDtl {

    private Long serialNumber;
    private LocalDateTime registDate;
    private String registIp;
    private LocalDateTime modifyDate;
    private String modifyIp;
    private String cancelBit;
    private String cancelReason;
    private LocalDateTime cancelDate;
    private String useBit;
    private Long bikeItemSerial;
    private String itemName;          // UNIT_NAME → ITEM_NAME
    private String itemEnName;        // UNIT_EN_NAME → ITEM_EN_NAME
    private String itemNickName;      // UNIT_NICK_NAME → ITEM_NICK_NAME
    private String itemCode;          // UNIT_CODE → ITEM_CODE
    private Integer buyCost;          // UNIT_COST → BUY_COST
    private Integer taxCost;          // UNIT_TAX → TAX_COST
    private Integer agentCost;        // UNIT_PRICE → AGENT_COST
    private Integer clientCost;       // 신규 추가
    private Integer stock;            // UNIT_LIMIT → STOCK
    private String memo;
}