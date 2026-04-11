package com.gobb.repair.domain.trade.service;

import com.gobb.repair.common.exception.BusinessException;
import com.gobb.repair.common.util.SummaryUtil;
import com.gobb.repair.domain.trade.entity.TradeDetail;
import com.gobb.repair.domain.trade.entity.TradeOrder;
import com.gobb.repair.domain.trade.mapper.TradeDetailMapper;
import com.gobb.repair.domain.trade.mapper.TradeOrderMapper;
import com.gobb.repair.domain.log.service.DataLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TradeOrderService {

    private final TradeOrderMapper tradeOrderMapper;
    private final TradeDetailMapper tradeDetailMapper;
    private final DataLogService dataLogService;

    private static final String TABLE_NAME = "TR_ORDER";
    private static final Map<String, String> FIELD_LABELS = Map.of(
            "stateType", "처리상태",
            "summary", "적요",
            "memo", "메모"
    );

    public List<TradeOrder> findAll() {
        return tradeOrderMapper.findAll();
    }

    public TradeOrder findById(Long serialNumber) {
        TradeOrder tradeOrder = tradeOrderMapper.findById(serialNumber);
        if (tradeOrder == null) {
            throw new BusinessException("주문을 찾을 수 없습니다.");
        }
        return tradeOrder;
    }

    public void insert(TradeOrder tradeOrder) {
        tradeOrderMapper.insert(tradeOrder);
        dataLogService.insert(TABLE_NAME, tradeOrder.getSerialNumber(), "I", null, tradeOrder.getRegistIp(), "신규");
    }

    public void updateStatus(Long serialNumber, String stateType, String modifyIp) {
        TradeOrder tradeOrder = findById(serialNumber);

        List<String> states = tradeDetailMapper.findAllStateByOrderId(serialNumber);
        if (states.size() > 1) {
            throw new BusinessException("주문 항목의 처리 상태가 동일하지 않아 일괄 처리할 수 없습니다.");
        }

        TradeOrder before = TradeOrder.builder()
                .stateType(tradeOrder.getStateType())
                .build();

        tradeOrder.setStateType(stateType);
        tradeOrder.setModifyIp(modifyIp);
        tradeOrderMapper.updateStatus(tradeOrder);

        TradeDetail tradeDetail = new TradeDetail();
        tradeDetail.setCommonTradeOrderSerial(serialNumber);
        tradeDetail.setStateType(stateType);
        tradeDetail.setModifyIp(modifyIp);
        tradeDetailMapper.updateStatusByOrderId(tradeDetail);

        String summary = SummaryUtil.buildUpdateSummary(before, tradeOrder, FIELD_LABELS);
        dataLogService.insert(TABLE_NAME, serialNumber, "U", null, modifyIp, summary);
    }

    public void delete(Long serialNumber) {
        findById(serialNumber);
        tradeOrderMapper.delete(serialNumber);
        dataLogService.insert(TABLE_NAME, serialNumber, "C", null, "127.0.0.1", "삭제");
    }
}