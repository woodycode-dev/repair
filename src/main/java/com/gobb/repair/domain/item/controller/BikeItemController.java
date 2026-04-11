package com.gobb.repair.domain.item.controller;

import com.gobb.repair.common.response.ApiResponse;
import com.gobb.repair.domain.item.dto.BikeItemRequest;
import com.gobb.repair.domain.item.dto.BikeItemInsertRequest;
import com.gobb.repair.domain.item.dto.BikeItemUpdateRequest;
import com.gobb.repair.domain.item.entity.BikeItem;
import com.gobb.repair.domain.item.service.BikeItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "2-2. 부품", description = "부품 관련 API (CM_BIKE_ITEM)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bike-item")
public class BikeItemController {

    private final BikeItemService bikeItemService;

    @Operation(summary = "부품 목록 조회")
    @GetMapping
    public ApiResponse<List<BikeItem>> findAll(BikeItemRequest request) {
        return ApiResponse.ok(bikeItemService.findAll(request));
    }

    @Operation(summary = "부품 단건 조회")
    @GetMapping("/{serialNumber}")
    public ApiResponse<BikeItem> findById(@PathVariable Long serialNumber) {
        return ApiResponse.ok(bikeItemService.findById(serialNumber));
    }

    @Operation(summary = "부품 등록")
    @PostMapping
    public ApiResponse<Void> insert(@RequestBody BikeItemInsertRequest request) {
        BikeItem bikeItem = BikeItem.builder()
                .registIp(request.getRegistIp())
                .useBit(request.getUseBit())
                .unitSerial(request.getUnitSerial())
                .makerType(request.getMakerType())
                .bikeSerial(request.getBikeSerial())
                .makerItemCode(request.getMakerItemCode())
                .itemName(request.getItemName())
                .itemEnName(request.getItemEnName())
                .itemNickName(request.getItemNickName())
                .memo(request.getMemo())
                .build();
        bikeItemService.insert(bikeItem);
        return ApiResponse.ok("등록되었습니다.", null);
    }

    @Operation(summary = "부품 수정")
    @PutMapping("/{serialNumber}")
    public ApiResponse<Void> update(@PathVariable Long serialNumber,
                                    @RequestBody BikeItemUpdateRequest request) {
        BikeItem bikeItem = BikeItem.builder()
                .serialNumber(serialNumber)
                .modifyIp(request.getModifyIp())
                .useBit(request.getUseBit())
                .unitSerial(request.getUnitSerial())
                .makerType(request.getMakerType())
                .bikeSerial(request.getBikeSerial())
                .makerItemCode(request.getMakerItemCode())
                .itemName(request.getItemName())
                .itemEnName(request.getItemEnName())
                .itemNickName(request.getItemNickName())
                .memo(request.getMemo())
                .build();
        bikeItemService.update(bikeItem);
        return ApiResponse.ok("수정되었습니다.", null);
    }

    @Operation(summary = "부품 삭제")
    @DeleteMapping("/{serialNumber}")
    public ApiResponse<Void> delete(@PathVariable Long serialNumber) {
        bikeItemService.delete(serialNumber);
        return ApiResponse.ok("삭제되었습니다.", null);
    }
}