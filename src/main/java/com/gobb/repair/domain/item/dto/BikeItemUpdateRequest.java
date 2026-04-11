package com.gobb.repair.domain.item.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class BikeItemUpdateRequest {

    private String modifyIp;
    private String useBit;
    private Long unitSerial;
    private String makerType;
    private Long bikeSerial;
    private String makerItemCode;
    private String itemName;
    private String itemEnName;
    private String itemNickName;
    private String memo;
}