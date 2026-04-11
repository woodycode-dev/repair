package com.gobb.repair.domain.item.service;

import com.gobb.repair.common.exception.BusinessException;
import com.gobb.repair.common.util.SummaryUtil;
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

    private static final String TABLE_NAME = "CM_BIKE_ITEM";
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

    public List<BikeItem> findAll() {
        return bikeItemMapper.findAll();
    }

    public BikeItem findById(Long serialNumber) {
        BikeItem bikeItem = bikeItemMapper.findById(serialNumber);
        if (bikeItem == null) {
            throw new BusinessException("부품을 찾을 수 없습니다.");
        }
        return bikeItem;
    }

    public void insert(BikeItem bikeItem) {
        bikeItemMapper.insert(bikeItem);
        dataLogService.insert(TABLE_NAME, bikeItem.getSerialNumber(), "I", null, bikeItem.getRegistIp(), "신규");
    }

    public void update(BikeItem bikeItem) {
        BikeItem before = findById(bikeItem.getSerialNumber());
        bikeItemMapper.update(bikeItem);
        String summary = SummaryUtil.buildUpdateSummary(before, bikeItem, FIELD_LABELS);
        dataLogService.insert(TABLE_NAME, bikeItem.getSerialNumber(), "U", null, bikeItem.getModifyIp(), summary);
    }

    public void delete(Long serialNumber) {
        findById(serialNumber);
        bikeItemMapper.delete(serialNumber);
        dataLogService.insert(TABLE_NAME, serialNumber, "C", null, "127.0.0.1", "삭제");
    }
}