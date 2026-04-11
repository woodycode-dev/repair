package com.gobb.repair.domain.item.mapper;

import com.gobb.repair.domain.item.entity.BikeItemCd;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BikeItemCdMapper {

    List<BikeItemCd> findAll();

    BikeItemCd findById(Long serialNumber);

    int insert(BikeItemCd bikeItemCd);

    int update(BikeItemCd bikeItemCd);

    int delete(Long serialNumber);
}