package com.gobb.repair.domain.item.mapper;

import com.gobb.repair.domain.item.entity.BikeItemUnit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BikeItemUnitMapper {

    List<BikeItemUnit> findAllByItemId(Long bikeItemSerial);

    BikeItemUnit findById(Long serialNumber);

    int insert(BikeItemUnit bikeItemUnit);

    int update(BikeItemUnit bikeItemUnit);

    int delete(Long serialNumber);
}
