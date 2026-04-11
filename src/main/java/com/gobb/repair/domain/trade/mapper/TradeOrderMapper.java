package com.gobb.repair.domain.trade.mapper;

import com.gobb.repair.domain.trade.entity.TradeOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TradeOrderMapper {

    List<TradeOrder> findAll();

    TradeOrder findById(Long serialNumber);

    int insert(TradeOrder tradeOrder);

    int updateStatus(TradeOrder tradeOrder);

    int delete(Long serialNumber);
}