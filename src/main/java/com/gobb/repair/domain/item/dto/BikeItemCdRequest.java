package com.gobb.repair.domain.item.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class BikeItemCdRequest {

    private String registIp;
    private String useBit;
    private String codeType;
    private String subject;
    private String memo;
}