package com.gobb.repair.domain.trade.service;

import com.gobb.repair.common.exception.BusinessException;
import com.gobb.repair.common.util.SummaryUtil;
import com.gobb.repair.domain.trade.dto.TradeDetailRequest;
import com.gobb.repair.domain.trade.dto.TradeOrderRequest;
import com.gobb.repair.domain.trade.entity.TradeDetail;
import com.gobb.repair.domain.trade.entity.TradeOrder;
import com.gobb.repair.domain.trade.mapper.TradeDetailMapper;
import com.gobb.repair.domain.trade.mapper.TradeOrderMapper;
import com.gobb.repair.domain.log.service.DataLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Transactional
    public void insert(TradeOrderRequest request) {

        // registIp 기본값 처리
        String registIp = (request.getRegistIp() == null || request.getRegistIp().trim().isEmpty())
                ? "127.0.0.1" : request.getRegistIp();

        // 주문번호 생성 (P/I + yyyyMMdd + 순번)
        String prefix = "P".equals(request.getProductBit()) ? "I" : "P";
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String orderNo = prefix + dateStr + String.format("%04d", tradeOrderMapper.findNextOrderSeq(dateStr, prefix));

        // TradeOrder 생성
        TradeOrder tradeOrder = TradeOrder.builder()
                .registIp(registIp)
                .useBit("Y")
                .productBit(request.getProductBit())
                .fromRepairAgent(request.getFromRepairAgent())
                .fromRepairClient(request.getFromRepairClient())
                .toRepairAgent(request.getToRepairAgent())
                .toRepairClient(request.getToRepairClient())
                .orderNo(orderNo)
                .orderCost(request.getDetails().stream().mapToInt(TradeDetailRequest::getOrderCost).sum())
                .buyCost(request.getDetails().stream().mapToInt(TradeDetailRequest::getBuyCost).sum())
                .stateType("10")
                .summary(request.getSummary())
                .tradeType("S")
                .itemUnit(request.getDetails().stream().mapToInt(TradeDetailRequest::getItemUnit).sum())
                .memo(request.getMemo())
                .build();

        tradeOrderMapper.insert(tradeOrder);

        // TradeDetail 등록
        for (TradeDetailRequest detail : request.getDetails()) {
            TradeDetail tradeDetail = TradeDetail.builder()
                    .registIp(registIp)
                    .useBit("Y")
                    .productBit(detail.getProductBit())
                    .productSerial(detail.getProductSerial())
                    .commonTradeOrderSerial(tradeOrder.getSerialNumber())
                    .orderNo(orderNo)
                    .stateType("10")
                    .tradeItemType(detail.getTradeItemType())
                    .itemUnit(detail.getItemUnit())
                    .orderCost(detail.getOrderCost())
                    .buyCost(detail.getBuyCost())
                    .memo(detail.getMemo())
                    .build();
            tradeDetailMapper.insert(tradeDetail);
        }

        // 등록 이력 저장
        dataLogService.insert(TABLE_NAME, tradeOrder.getSerialNumber(), "I", null, registIp, "신규");
    }

    @Transactional
    public void delete(Long serialNumber) {
        findById(serialNumber);

        // 주문항목 먼저 삭제
        tradeDetailMapper.deleteByOrderId(serialNumber);

        // 주문서 삭제
        tradeOrderMapper.delete(serialNumber);

        // 삭제 이력 저장
        dataLogService.insert(TABLE_NAME, serialNumber, "C", null, "127.0.0.1", "삭제");
    }
}