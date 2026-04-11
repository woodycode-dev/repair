package com.gobb.repair.domain.trade.mapper;

import com.gobb.repair.domain.trade.entity.TradeDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TradeDetailMapper {

    List<TradeDetail> findAll();

    List<TradeDetail> findAllByOrderId(Long commonTradeOrderSerial);

    List<String> findAllStateByOrderId(Long commonTradeOrderSerial);

    TradeDetail findById(Long serialNumber);

    int insert(TradeDetail tradeDetail);

    int updateStatus(TradeDetail tradeDetail);

    int updateStatusByOrderId(TradeDetail tradeDetail);

    int delete(Long serialNumber);
}
