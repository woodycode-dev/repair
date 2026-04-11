package com.gobb.repair.domain.item.service;

import com.gobb.repair.common.exception.BusinessException;
import com.gobb.repair.common.util.SummaryUtil;
import com.gobb.repair.domain.item.entity.BikeItemDtl;
import com.gobb.repair.domain.item.mapper.BikeItemDtlMapper;
import com.gobb.repair.domain.log.service.DataLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BikeItemDtlService {

    private final BikeItemDtlMapper bikeItemDtlMapper;
    private final DataLogService dataLogService;

    private static final String TABLE_NAME = "CM_BIKE_ITEM_UNIT";
    private static final Map<String, String> FIELD_LABELS = Map.of(
            "itemName", "부품상세명",
            "itemEnName", "부품상세명(영문)",
            "itemNickName", "부품상세명(필드)",
            "itemCode", "부품번호",
            "buyCost", "매입단가",
            "taxCost", "세액",
            "agentCost", "직영점단가",
            "clientCost", "관리점단가",
            "stock", "재고현황",
            "useBit", "사용여부"
    );

    // 부품상세 등록/수정 유효성 검증
    private void validate(BikeItemDtl bikeItemDtl) {

        if (bikeItemDtl.getRegistIp() == null || bikeItemDtl.getRegistIp().trim().isEmpty()) {
            bikeItemDtl.setRegistIp("127.0.0.1");
        }

        if (bikeItemDtl.getUseBit() == null || bikeItemDtl.getUseBit().trim().isEmpty()) {
            bikeItemDtl.setUseBit("Y");
        }

        if (bikeItemDtl.getBikeItemSerial() == null || bikeItemDtl.getBikeItemSerial() == 0) {
            throw new BusinessException("부품은 필수입니다.");
        }

        if (bikeItemDtl.getItemName() == null || bikeItemDtl.getItemName().trim().isEmpty()) {
            throw new BusinessException("부품상세명은 필수입니다.");
        }

        if (bikeItemDtl.getItemCode() == null || bikeItemDtl.getItemCode().trim().isEmpty()) {
            throw new BusinessException("부품번호는 필수입니다.");
        }
    }

    public List<BikeItemDtl> findAllByItemId(Long bikeItemSerial) {
        return bikeItemDtlMapper.findAllByItemId(bikeItemSerial);
    }

    public BikeItemDtl findById(Long serialNumber) {
        BikeItemDtl bikeItemDtl = bikeItemDtlMapper.findById(serialNumber);
        if (bikeItemDtl == null) {
            throw new BusinessException("부품상세를 찾을 수 없습니다.");
        }
        return bikeItemDtl;
    }

    public void insert(BikeItemDtl bikeItemDtl) {
        validate(bikeItemDtl);

        bikeItemDtlMapper.insert(bikeItemDtl);

        // 등록 이력 저장
        dataLogService.insert(TABLE_NAME, bikeItemDtl.getSerialNumber(), "I", null, bikeItemDtl.getRegistIp(), "신규");
    }

    public void update(BikeItemDtl bikeItemDtl) {
        validate(bikeItemDtl);

        // 변경 이력 비교를 위해 수정 전 데이터 조회
        BikeItemDtl before = findById(bikeItemDtl.getSerialNumber());

        bikeItemDtlMapper.update(bikeItemDtl);

        // 수정 이력 저장
        String summary = SummaryUtil.buildUpdateSummary(before, bikeItemDtl, FIELD_LABELS);
        dataLogService.insert(TABLE_NAME, bikeItemDtl.getSerialNumber(), "U", null, bikeItemDtl.getModifyIp(), summary);
    }

    public void delete(Long serialNumber) {
        findById(serialNumber);
        bikeItemDtlMapper.delete(serialNumber);
        dataLogService.insert(TABLE_NAME, serialNumber, "C", null, "127.0.0.1", "삭제");
    }
}