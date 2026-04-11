package com.gobb.repair.domain.item.service;

import com.gobb.repair.common.exception.BusinessException;
import com.gobb.repair.common.util.SummaryUtil;
import com.gobb.repair.domain.item.dto.BikeItemRequest;
import com.gobb.repair.domain.item.entity.BikeItem;
import com.gobb.repair.domain.item.mapper.BikeItemMapper;
import com.gobb.repair.domain.log.service.DataLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BikeItemService {

    private final BikeItemMapper bikeItemMapper;
    private final DataLogService dataLogService;

    /** 테이블명 */
    private static final String TABLE_NAME = "CM_BIKE_ITEM";
    /** 변경 이력 필드 라벨 - 수정 시 변경된 필드만 summary에 기록 */
    private static final Map<String, String> FIELD_LABELS = Map.of(
            "unitSerial", "부품분류",
            "makerType", "차량제조사",
            "bikeSerial", "차종",
            "makerItemCode", "제조사발행코드",
            "itemName", "부품명",
            "itemEnName", "부품명(영문)",
            "itemNickName", "부품명(필드)",
            "useBit", "사용여부",
            "memo", "메모"
    );

    /** 부품 등록/수정 유효성 검증 */
    private void validate(BikeItem bikeItem) {

        // 필수값 검증
        if (bikeItem.getUnitSerial() == null || bikeItem.getUnitSerial() == 0) {
            throw new BusinessException("부품분류는 필수입니다.");
        }
        if (bikeItem.getBikeSerial() == null || bikeItem.getBikeSerial() == 0) {
            throw new BusinessException("차종은 필수입니다.");
        }
        if (bikeItem.getMakerType() == null || bikeItem.getMakerType().trim().isEmpty()) {
            throw new BusinessException("제조사는 필수입니다.");
        }
        if (bikeItem.getMakerItemCode() == null || bikeItem.getMakerItemCode().trim().isEmpty()) {
            throw new BusinessException("제조사 발행코드는 필수입니다.");
        }
        if (bikeItem.getItemName() == null || bikeItem.getItemName().trim().isEmpty()) {
            throw new BusinessException("부품명은 필수입니다.");
        }
    }

    public List<BikeItem> findAll(BikeItemRequest request) {
        return bikeItemMapper.findAll(request);
    }

    public BikeItem findById(Long serialNumber) {
        BikeItem bikeItem = bikeItemMapper.findById(serialNumber);
        if (bikeItem == null) {
            throw new BusinessException("부품을 찾을 수 없습니다.");
        }
        return bikeItem;
    }

    public void insert(BikeItem bikeItem) {
        validate(bikeItem);

        bikeItemMapper.insert(bikeItem);

        // 등록 이력 저장
        dataLogService.insert(TABLE_NAME, bikeItem.getSerialNumber(), "I", null, bikeItem.getRegistIp(), "신규");
    }

    public void update(BikeItem bikeItem) {
        validate(bikeItem);

        // 변경 이력 비교를 위해 수정 전 데이터 조회
        BikeItem before = findById(bikeItem.getSerialNumber());

        bikeItemMapper.update(bikeItem);

        // 수정 이력 저장
        String summary = SummaryUtil.buildUpdateSummary(before, bikeItem, FIELD_LABELS);
        dataLogService.insert(TABLE_NAME, bikeItem.getSerialNumber(), "U", null, bikeItem.getModifyIp(), summary);
    }

    public void delete(Long serialNumber) {
        findById(serialNumber);
        bikeItemMapper.delete(serialNumber);
        dataLogService.insert(TABLE_NAME, serialNumber, "C", null, "127.0.0.1", "삭제");
    }
}