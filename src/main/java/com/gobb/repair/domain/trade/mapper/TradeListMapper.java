package com.gobb.repair.domain.trade.mapper;

import com.gobb.repair.domain.trade.entity.TradeList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TradeListMapper {

    List<TradeList> findAllByDetailId(Long commonTradeDetailSerial);

    int insert(TradeList tradeList);

    int delete(Long serialNumber);
}