package com.gobb.repair.domain.item.controller;

import com.gobb.repair.common.response.ApiResponse;
import com.gobb.repair.domain.item.dto.BikeItemCdRequest;
import com.gobb.repair.domain.item.entity.BikeItemCd;
import com.gobb.repair.domain.item.service.BikeItemCdService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "2-1. 부품분류", description = "부품분류 관련 API (CM_BIKE_ITEM_CD)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bike-item-cd")
public class BikeItemCdController {

    private final BikeItemCdService bikeItemCdService;

    @Operation(summary = "부품분류 목록 조회")
    @GetMapping
    public ApiResponse<List<BikeItemCd>> findAll(
            @RequestParam(required = false) String useBit) {
        return ApiResponse.ok(bikeItemCdService.findAll(useBit));
    }

    @Operation(summary = "부품분류 단건 조회")
    @GetMapping("/{serialNumber}")
    public ApiResponse<BikeItemCd> findById(@PathVariable Long serialNumber) {
        return ApiResponse.ok(bikeItemCdService.findById(serialNumber));
    }

    @Operation(summary = "부품분류 등록")
    @PostMapping
    public ApiResponse<Void> insert(@RequestBody BikeItemCdRequest request) {
        BikeItemCd bikeItemCd = BikeItemCd.builder()
                .registIp(request.getRegistIp())
                .useBit(request.getUseBit())
                .codeType(request.getCodeType())
                .subject(request.getSubject())
                .memo(request.getMemo())
                .build();
        bikeItemCdService.insert(bikeItemCd);
        return ApiResponse.ok("등록되었습니다.", null);
    }

    @Operation(summary = "부품분류 수정")
    @PutMapping("/{serialNumber}")
    public ApiResponse<Void> update(@PathVariable Long serialNumber, @RequestBody BikeItemCdRequest request) {
        BikeItemCd bikeItemCd = BikeItemCd.builder()
                .serialNumber(serialNumber)
                .modifyIp(request.getRegistIp())
                .useBit(request.getUseBit())
                .codeType(request.getCodeType())
                .subject(request.getSubject())
                .memo(request.getMemo())
                .build();
        bikeItemCdService.update(bikeItemCd);
        return ApiResponse.ok("수정되었습니다.", null);
    }

    @Operation(summary = "부품분류 삭제")
    @DeleteMapping("/{serialNumber}")
    public ApiResponse<Void> delete(@PathVariable Long serialNumber) {
        bikeItemCdService.delete(serialNumber);
        return ApiResponse.ok("삭제되었습니다.", null);
    }
}