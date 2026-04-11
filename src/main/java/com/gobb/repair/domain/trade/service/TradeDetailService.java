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
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void updateStatus(Long serialNumber, String stateType, String modifyIp) {

        TradeDetail tradeDetail = findById(serialNumber);
        String currentState = tradeDetail.getStateType();

        // 상태 전환 검증
        switch (stateType) {
            case "11" -> { // 주문확정
                if (!currentState.equals("10"))
                    throw new BusinessException("주문접수 상태에서만 주문확정할 수 있습니다.");
            }
            case "12" -> { // 주문취소
                if (!currentState.equals("10") && !currentState.equals("11"))
                    throw new BusinessException("주문접수 또는 주문확정 상태에서만 취소할 수 있습니다.");
            }
            case "20" -> { // 출고완료
                if (!currentState.equals("11"))
                    throw new BusinessException("주문확정 상태에서만 출고완료할 수 있습니다.");

                // 출고 LIST 생성
                TradeList tradeList = TradeList.builder()
                        .registIp(modifyIp)
                        .useBit("Y")
                        .productBit(tradeDetail.getProductBit())
                        .commonTradeOrderSerial(tradeDetail.getCommonTradeOrderSerial())
                        .commonTradeDetailSerial(serialNumber)
                        .orderNo(tradeDetail.getOrderNo())
                        .tradeBit("O") // 출고
                        .tradeCost(tradeDetail.getOrderCost())
                        .buyCost(tradeDetail.getBuyCost())
                        .itemUnit(tradeDetail.getItemUnit())
                        .tradeItemType(tradeDetail.getTradeItemType())
                        .tradeItemUse("S") // 판매
                        .memo("출고완료")
                        .build();
                tradeListMapper.insert(tradeList);
            }
            case "50" -> { // 판매완료
                if (!currentState.equals("20"))
                    throw new BusinessException("출고완료 상태에서만 판매완료할 수 있습니다.");
            }
            case "60" -> { // 반품접수
                if (!currentState.equals("20"))
                    throw new BusinessException("출고완료 상태에서만 반품접수할 수 있습니다.");
            }
            case "61" -> { // 반품확정
                if (!currentState.equals("60"))
                    throw new BusinessException("반품접수 상태에서만 반품확정할 수 있습니다.");
            }
            case "62" -> { // 반품완료
                if (!currentState.equals("61"))
                    throw new BusinessException("반품확정 상태에서만 반품완료할 수 있습니다.");

                // 반품 LIST 생성
                TradeList tradeList = TradeList.builder()
                        .registIp(modifyIp)
                        .useBit("Y")
                        .productBit(tradeDetail.getProductBit())
                        .commonTradeOrderSerial(tradeDetail.getCommonTradeOrderSerial())
                        .commonTradeDetailSerial(serialNumber)
                        .orderNo(tradeDetail.getOrderNo())
                        .tradeBit("I") // 입고
                        .tradeCost(tradeDetail.getOrderCost())
                        .buyCost(tradeDetail.getBuyCost())
                        .itemUnit(tradeDetail.getItemUnit())
                        .tradeItemType(tradeDetail.getTradeItemType())
                        .tradeItemUse("R") // 반품
                        .memo("반품완료")
                        .build();
                tradeListMapper.insert(tradeList);
            }
            default -> throw new BusinessException("유효하지 않은 상태값입니다.");
        }

        // 상태 변경
        tradeDetail.setStateType(stateType);
        tradeDetail.setModifyIp(modifyIp);
        tradeDetailMapper.updateStatus(tradeDetail);

        // 이력 저장
        dataLogService.insert("TR_ORDER_DTL", serialNumber, "U", null, modifyIp,
                "처리상태 변경: " + currentState + " → " + stateType);
    }
}