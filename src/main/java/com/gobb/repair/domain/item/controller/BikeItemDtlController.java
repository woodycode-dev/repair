package com.gobb.repair.domain.item.controller;

import com.gobb.repair.common.response.ApiResponse;
import com.gobb.repair.domain.item.dto.BikeItemDtlInsertRequest;
import com.gobb.repair.domain.item.dto.BikeItemDtlUpdateRequest;
import com.gobb.repair.domain.item.entity.BikeItemDtl;
import com.gobb.repair.domain.item.service.BikeItemDtlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "2-3. 부품상세", description = "부품상세 관련 API (CM_BIKE_ITEM_UNIT)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bike-item/{itemId}/units")
public class BikeItemDtlController {

    private final BikeItemDtlService bikeItemDtlService;

    @Operation(summary = "부품상세 목록 조회")
    @GetMapping
    public ApiResponse<List<BikeItemDtl>> findAll(@PathVariable Long itemId) {
        return ApiResponse.ok(bikeItemDtlService.findAllByItemId(itemId));
    }

    @Operation(summary = "부품상세 단건 조회")
    @GetMapping("/{serialNumber}")
    public ApiResponse<BikeItemDtl> findById(@PathVariable Long serialNumber) {
        return ApiResponse.ok(bikeItemDtlService.findById(serialNumber));
    }

    @Operation(summary = "부품상세 일괄 등록")
    @PostMapping("/batch")
    public ApiResponse<Void> insertBatch(@PathVariable Long itemId,
                                         @RequestBody List<BikeItemDtlInsertRequest> requests) {
        for (BikeItemDtlInsertRequest request : requests) {
            BikeItemDtl bikeItemDtl = BikeItemDtl.builder()
                    .registIp(request.getRegistIp())
                    .useBit(request.getUseBit())
                    .bikeItemSerial(itemId)
                    .itemName(request.getItemName())
                    .itemEnName(request.getItemEnName())
                    .itemNickName(request.getItemNickName())
                    .itemCode(request.getItemCode())
                    .buyCost(request.getBuyCost())
                    .taxCost(request.getTaxCost())
                    .agentCost(request.getAgentCost())
                    .clientCost(request.getClientCost())
                    .stock(request.getStock())
                    .memo(request.getMemo())
                    .build();
            bikeItemDtlService.insert(bikeItemDtl);
        }
        return ApiResponse.ok("등록되었습니다.", null);
    }

    @Operation(summary = "부품상세 일괄 수정", description = "변경된 항목만 리스트로 전달")
    @PutMapping("/batch")
    public ApiResponse<Void> updateBatch(@RequestBody List<BikeItemDtlUpdateRequest> requests) {
        for (BikeItemDtlUpdateRequest request : requests) {
            BikeItemDtl bikeItemDtl = BikeItemDtl.builder()
                    .serialNumber(request.getSerialNumber())
                    .modifyIp(request.getModifyIp())
                    .useBit(request.getUseBit())
                    .bikeItemSerial(request.getBikeItemSerial())
                    .itemName(request.getItemName())
                    .itemEnName(request.getItemEnName())
                    .itemNickName(request.getItemNickName())
                    .itemCode(request.getItemCode())
                    .buyCost(request.getBuyCost())
                    .taxCost(request.getTaxCost())
                    .agentCost(request.getAgentCost())
                    .clientCost(request.getClientCost())
                    .stock(request.getStock())
                    .memo(request.getMemo())
                    .build();
            bikeItemDtlService.update(bikeItemDtl);
        }
        return ApiResponse.ok("수정되었습니다.", null);
    }

    @Operation(summary = "부품상세 삭제")
    @DeleteMapping("/{serialNumber}")
    public ApiResponse<Void> delete(@PathVariable Long serialNumber) {
        bikeItemDtlService.delete(serialNumber);
        return ApiResponse.ok("삭제되었습니다.", null);
    }
}