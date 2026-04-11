package com.gobb.repair.domain.item.mapper;

import com.gobb.repair.domain.item.entity.BikeItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BikeItemMapper {

    List<BikeItem> findAll();

    BikeItem findById(Long serialNumber);

    int insert(BikeItem bikeItem);

    int update(BikeItem bikeItem);

    int delete(Long serialNumber);
}