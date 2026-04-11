package com.gobb.repair.domain.trade.controller;

import com.gobb.repair.common.response.ApiResponse;
import com.gobb.repair.domain.trade.entity.TradeDetail;
import com.gobb.repair.domain.trade.service.TradeDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "3-2. 거래주문항목", description = "거래주문항목 관련 API (TR_ORDER_DTL)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trade-order/{orderId}/detail")
public class TradeDetailController {

    private final TradeDetailService tradeDetailService;

    @Operation(summary = "주문항목 목록 조회")
    @GetMapping
    public ApiResponse<List<TradeDetail>> findAll(@PathVariable Long orderId) {
        return ApiResponse.ok(tradeDetailService.findAllByOrderId(orderId));
    }

    @Operation(summary = "주문항목 단건 조회")
    @GetMapping("/{serialNumber}")
    public ApiResponse<TradeDetail> findById(@PathVariable Long serialNumber) {
        return ApiResponse.ok(tradeDetailService.findById(serialNumber));
    }

    @Operation(summary = "주문항목 단건 상태 변경", description = "10→11(주문확정), 10→12(취소), 11→12(취소), 11→20(출고완료), 20→50(판매완료), 20→60(반품접수), 60→61(반품확정), 61→62(반품완료)")
    @PutMapping("/{serialNumber}/status")
    public ApiResponse<Void> updateStatus(
            @PathVariable Long serialNumber,
            @RequestParam String stateType,
            @RequestParam(required = false, defaultValue = "127.0.0.1") String modifyIp) {
        tradeDetailService.updateStatus(serialNumber, stateType, modifyIp);
        return ApiResponse.ok("상태가 변경되었습니다.", null);
    }
}