package com.gobb.repair.domain.trade.service;

import com.gobb.repair.common.exception.BusinessException;
import com.gobb.repair.common.util.SummaryUtil;
import com.gobb.repair.domain.trade.entity.TradeDetail;
import com.gobb.repair.domain.trade.entity.TradeList;
import com.gobb.repair.domain.trade.mapper.TradeDetailMapper;
import com.gobb.repair.domain.trade.mapper.TradeListMapper;
import com.gobb.repair.domain.log.service.DataLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TradeDetailService {

    private final TradeDetailMapper tradeDetailMapper;
    private final TradeListMapper tradeListMapper;
    private final DataLogService dataLogService;

    private static final String TABLE_NAME = "TR_ORDER_DTL";
    private static final Map<String, String> FIELD_LABELS = Map.of(
            "stateType", "처리상태",
            "tradeItemType", "정품구분",
            "itemUnit", "주문수량",
            "orderCost", "주문금액",
            "buyCost", "매입금액",
            "memo", "메모"
    );

    public List<TradeDetail> findAllByOrderId(Long commonTradeOrderSerial) {
        return tradeDetailMapper.findAllByOrderId(commonTradeOrderSerial);
    }

    public TradeDetail findById(Long serialNumber) {
        TradeDetail tradeDetail = tradeDetailMapper.findById(serialNumber);
        if (tradeDetail == null) {
            throw new BusinessException("주문 항목을 찾을 수 없습니다.");
        }
        return tradeDetail;
    }

    public void insert(TradeDetail tradeDetail) {
        tradeDetailMapper.insert(tradeDetail);
        dataLogService.insert(TABLE_NAME, tradeDetail.getSerialNumber(), "I", null, tradeDetail.getRegistIp(), "신규");
    }

    public void updateStatus(Long serialNumber, String stateType, String modifyIp) {
        TradeDetail tradeDetail = findById(serialNumber);
        String currentState = tradeDetail.getStateType();

        if (stateType.equals("12")) {
            if (!currentState.equals("10") && !currentState.equals("11")) {
                throw new BusinessException("주문접수 또는 주문확정 상태에서만 취소할 수 있습니다.");
            }
        } else if (stateType.equals("60")) {
            if (!currentState.equals("20")) {
                throw new BusinessException("출고완료 상태에서만 반품접수할 수 있습니다.");
            }
            TradeList tradeList = TradeList.builder()
                    .registIp(modifyIp)
                    .useBit("Y")
                    .productBit(tradeDetail.getProductBit())
                    .commonTradeOrderSerial(tradeDetail.getCommonTradeOrderSerial())
                    .commonTradeDetailSerial(serialNumber)
                    .orderNo(tradeDetail.getOrderNo())
                    .tradeBit("I")
                    .tradeCost(tradeDetail.getOrderCost())
                    .buyCost(tradeDetail.getBuyCost())
                    .itemUnit(tradeDetail.getItemUnit())
                    .tradeItemType(tradeDetail.getTradeItemType())
                    .tradeItemUse("R")
                    .memo("반품접수")
                    .build();
            tradeListMapper.insert(tradeList);
        } else {
            throw new BusinessException("유효하지 않은 상태값입니다.");
        }

        TradeDetail before = TradeDetail.builder()
                .stateType(currentState)
                .build();

        tradeDetail.setStateType(stateType);
        tradeDetail.setModifyIp(modifyIp);
        tradeDetailMapper.updateStatus(tradeDetail);

        String summary = SummaryUtil.buildUpdateSummary(before, tradeDetail, FIELD_LABELS);
        dataLogService.insert(TABLE_NAME, serialNumber, "U", null, modifyIp, summary);
    }

    public void delete(Long serialNumber) {
        findById(serialNumber);
        tradeDetailMapper.delete(serialNumber);
        dataLogService.insert(TABLE_NAME, serialNumber, "C", null, "127.0.0.1", "삭제");
    }
}