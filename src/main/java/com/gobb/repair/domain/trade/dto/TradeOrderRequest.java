package com.gobb.repair.domain.trade.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TradeOrderRequest {

    private String registIp;
    private String productBit;          // P:부품, O:용품
    private Long fromRepairAgent;       // 주문 에이전트
    private Long fromRepairClient;      // 주문 클라이언트
    private Long toRepairAgent;         // 수신 에이전트
    private Long toRepairClient;        // 수신 클라이언트
    private String summary;             // 적요
    private String memo;
    private List<TradeDetailRequest> details; // 주문 항목
}