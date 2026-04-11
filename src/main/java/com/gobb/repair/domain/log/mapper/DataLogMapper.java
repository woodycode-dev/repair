package com.gobb.repair.domain.log.mapper;

import com.gobb.repair.domain.log.entity.DataLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DataLogMapper {

    int insert(DataLog dataLog);
}