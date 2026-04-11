package com.gobb.repair.domain.trade.controller;

import com.gobb.repair.common.response.ApiResponse;
import com.gobb.repair.domain.trade.entity.TradeOrder;
import com.gobb.repair.domain.trade.service.TradeOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "거래주문", description = "거래주문 관련 API (TR_ORDER)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trade-order")
public class TradeOrderController {

    private final TradeOrderService tradeOrderService;

    @Operation(summary = "주문 목록 조회")
    @GetMapping
    public ApiResponse<List<TradeOrder>> findAll() {
        return ApiResponse.ok(tradeOrderService.findAll());
    }

    @Operation(summary = "주문 단건 조회")
    @GetMapping("/{serialNumber}")
    public ApiResponse<TradeOrder> findById(@PathVariable Long serialNumber) {
        return ApiResponse.ok(tradeOrderService.findById(serialNumber));
    }

    @Operation(summary = "주문 등록")
    @PostMapping
    public ApiResponse<Void> insert(@RequestBody TradeOrder tradeOrder) {
        tradeOrderService.insert(tradeOrder);
        return ApiResponse.ok("등록되었습니다.", null);
    }

    @Operation(summary = "주문 일괄 상태 변경", description = "같은 주문번호의 detail 상태가 모두 동일할 때만 처리 가능")
    @PutMapping("/{serialNumber}/status")
    public ApiResponse<Void> updateStatus(
            @PathVariable Long serialNumber,
            @RequestBody Map<String, String> body) {
        String stateType = body.get("stateType");
        String modifyIp = body.getOrDefault("modifyIp", "127.0.0.1");
        tradeOrderService.updateStatus(serialNumber, stateType, modifyIp);
        return ApiResponse.ok("상태가 변경되었습니다.", null);
    }

    @Operation(summary = "주문 삭제")
    @DeleteMapping("/{serialNumber}")
    public ApiResponse<Void> delete(@PathVariable Long serialNumber) {
        tradeOrderService.delete(serialNumber);
        return ApiResponse.ok("삭제되었습니다.", null);
    }
}