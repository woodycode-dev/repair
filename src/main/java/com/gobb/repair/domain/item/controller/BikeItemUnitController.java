package com.gobb.repair.domain.item.controller;

import com.gobb.repair.common.response.ApiResponse;
import com.gobb.repair.domain.item.entity.BikeItemUnit;
import com.gobb.repair.domain.item.service.BikeItemUnitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "부품상세", description = "부품상세 관련 API (CM_BIKE_ITEM_UNIT)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bike-item/{itemId}/units")
public class BikeItemUnitController {

    private final BikeItemUnitService bikeItemUnitService;

    @Operation(summary = "부품상세 목록 조회")
    @GetMapping
    public ApiResponse<List<BikeItemUnit>> findAll(@PathVariable Long itemId) {
        return ApiResponse.ok(bikeItemUnitService.findAllByItemId(itemId));
    }

    @Operation(summary = "부품상세 단건 조회")
    @GetMapping("/{serialNumber}")
    public ApiResponse<BikeItemUnit> findById(@PathVariable Long serialNumber) {
        return ApiResponse.ok(bikeItemUnitService.findById(serialNumber));
    }

    @Operation(summary = "부품상세 등록")
    @PostMapping
    public ApiResponse<Void> insert(@PathVariable Long itemId, @RequestBody BikeItemUnit bikeItemUnit) {
        bikeItemUnit.setBikeItemSerial(itemId);
        bikeItemUnitService.insert(bikeItemUnit);
        return ApiResponse.ok("등록되었습니다.", null);
    }

    @Operation(summary = "부품상세 수정")
    @PutMapping("/{serialNumber}")
    public ApiResponse<Void> update(@PathVariable Long serialNumber, @RequestBody BikeItemUnit bikeItemUnit) {
        bikeItemUnit.setSerialNumber(serialNumber);
        bikeItemUnitService.update(bikeItemUnit);
        return ApiResponse.ok("수정되었습니다.", null);
    }

    @Operation(summary = "부품상세 삭제")
    @DeleteMapping("/{serialNumber}")
    public ApiResponse<Void> delete(@PathVariable Long serialNumber) {
        bikeItemUnitService.delete(serialNumber);
        return ApiResponse.ok("삭제되었습니다.", null);
    }
}