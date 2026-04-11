package com.gobb.repair.domain.item.service;

import com.gobb.repair.common.exception.BusinessException;
import com.gobb.repair.common.util.SummaryUtil;
import com.gobb.repair.domain.item.entity.BikeItemCd;
import com.gobb.repair.domain.item.mapper.BikeItemCdMapper;
import com.gobb.repair.domain.log.service.DataLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BikeItemCdService {

    private final BikeItemCdMapper bikeItemCdMapper;
    private final DataLogService dataLogService;

    private static final String TABLE_NAME = "CM_BIKE_ITEM_CD";
    private static final Map<String, String> FIELD_LABELS = Map.of(
            "codeType", "부품분류",
            "subject", "부품분류명",
            "useBit", "사용여부",
            "memo", "메모"
    );

    public List<BikeItemCd> findAll(String useBit) {
        return bikeItemCdMapper.findAll(useBit);
    }

    public BikeItemCd findById(Long serialNumber) {
        BikeItemCd bikeItemCd = bikeItemCdMapper.findById(serialNumber);
        if (bikeItemCd == null) {
            throw new BusinessException("부품분류를 찾을 수 없습니다.");
        }
        return bikeItemCd;
    }

    public void insert(BikeItemCd bikeItemCd) {
        bikeItemCdMapper.insert(bikeItemCd);
        dataLogService.insert(TABLE_NAME, bikeItemCd.getSerialNumber(), "I", null, bikeItemCd.getRegistIp(), "신규");
    }

    public void update(BikeItemCd bikeItemCd) {
        BikeItemCd before = findById(bikeItemCd.getSerialNumber());
        bikeItemCdMapper.update(bikeItemCd);
        String summary = SummaryUtil.buildUpdateSummary(before, bikeItemCd, FIELD_LABELS);
        dataLogService.insert(TABLE_NAME, bikeItemCd.getSerialNumber(), "U", null, bikeItemCd.getModifyIp(), summary);
    }

    public void delete(Long serialNumber) {
        findById(serialNumber);
        bikeItemCdMapper.delete(serialNumber);
        dataLogService.insert(TABLE_NAME, serialNumber, "C", null, "127.0.0.1", "삭제");
    }
}