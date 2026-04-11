package com.gobb.repair.domain.item.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class BikeItemDtlUpdateRequest {

    private Long serialNumber;
    private String modifyIp;
    private String useBit;
    private Long bikeItemSerial;
    private String itemName;
    private String itemEnName;
    private String itemNickName;
    private String itemCode;
    private Integer buyCost;
    private Integer taxCost;
    private Integer agentCost;
    private Integer clientCost;
    private Integer stock;
    private String memo;
}