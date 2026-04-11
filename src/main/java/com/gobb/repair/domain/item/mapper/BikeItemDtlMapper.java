package com.gobb.repair.domain.item.mapper;

import com.gobb.repair.domain.item.entity.BikeItemDtl;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BikeItemDtlMapper {

    List<BikeItemDtl> findAllByItemId(Long bikeItemSerial);

    BikeItemDtl findById(Long serialNumber);

    int insert(BikeItemDtl bikeItemDtl);

    int update(BikeItemDtl bikeItemDtl);

    int delete(Long serialNumber);
}
