package com.gobb.repair.domain.item.service;

import com.gobb.repair.common.exception.BusinessException;
import com.gobb.repair.common.util.SummaryUtil;
import com.gobb.repair.domain.item.entity.BikeItemUnit;
import com.gobb.repair.domain.item.mapper.BikeItemUnitMapper;
import com.gobb.repair.domain.log.service.DataLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BikeItemUnitService {

    private final BikeItemUnitMapper bikeItemUnitMapper;
    private final DataLogService dataLogService;

    private static final String TABLE_NAME = "CM_BIKE_ITEM_UNIT";
    private static final Map<String, String> FIELD_LABELS = Map.of(
            "unitName", "부품상세명",
            "unitEnName", "부품상세명(영문)",
            "unitNickName", "부품상세명(필드)",
            "unitCode", "부품번호",
            "unitCost", "공장도가",
            "unitTax", "세액",
            "unitPrice", "공급가액",
            "unitLimit", "재고현황",
            "useBit", "사용여부",
            "memo", "메모"
    );

    public List<BikeItemUnit> findAllByItemId(Long bikeItemSerial) {
        return bikeItemUnitMapper.findAllByItemId(bikeItemSerial);
    }

    public BikeItemUnit findById(Long serialNumber) {
        BikeItemUnit bikeItemUnit = bikeItemUnitMapper.findById(serialNumber);
        if (bikeItemUnit == null) {
            throw new BusinessException("부품상세를 찾을 수 없습니다.");
        }
        return bikeItemUnit;
    }

    public void insert(BikeItemUnit bikeItemUnit) {
        bikeItemUnitMapper.insert(bikeItemUnit);
        dataLogService.insert(TABLE_NAME, bikeItemUnit.getSerialNumber(), "I", null, bikeItemUnit.getRegistIp(), "신규");
    }

    public void update(BikeItemUnit bikeItemUnit) {
        BikeItemUnit before = findById(bikeItemUnit.getSerialNumber());
        bikeItemUnitMapper.update(bikeItemUnit);
        String summary = SummaryUtil.buildUpdateSummary(before, bikeItemUnit, FIELD_LABELS);
        dataLogService.insert(TABLE_NAME, bikeItemUnit.getSerialNumber(), "U", null, bikeItemUnit.getModifyIp(), summary);
    }

    public void delete(Long serialNumber) {
        findById(serialNumber);
        bikeItemUnitMapper.delete(serialNumber);
        dataLogService.insert(TABLE_NAME, serialNumber, "C", null, "127.0.0.1", "삭제");
    }
}