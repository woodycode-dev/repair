package com.gobb.repair.domain.trade.mapper;

import com.gobb.repair.domain.trade.entity.TradeOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TradeOrderMapper {

    List<TradeOrder> findAll();

    TradeOrder findById(Long serialNumber);

    // 주문번호 순번 조회
    int findNextOrderSeq(@Param("dateStr") String dateStr, @Param("prefix") String prefix);

    int insert(TradeOrder tradeOrder);

    int delete(Long serialNumber);
}