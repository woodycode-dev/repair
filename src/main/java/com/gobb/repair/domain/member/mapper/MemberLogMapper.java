package com.gobb.repair.domain.member.mapper;

import com.gobb.repair.domain.member.entity.MemberLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberLogMapper {

    int insert(MemberLog memberLog);
}