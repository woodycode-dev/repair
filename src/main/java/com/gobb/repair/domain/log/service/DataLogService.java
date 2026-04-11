package com.gobb.repair.domain.log.service;

import com.gobb.repair.domain.log.entity.DataLog;
import com.gobb.repair.domain.log.mapper.DataLogMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DataLogService {

    private final DataLogMapper dataLogMapper;

    public void insert(String tableName, Long fkSerial, String stateType, String userId, String registIp, String summary) {
        DataLog dataLog = DataLog.builder()
                .registIp(registIp)
                .useBit("Y")
                .tableName(tableName)
                .fkSerial(fkSerial)
                .stateType(stateType)
                .userId(userId)
                .summary(summary)
                .build();
        dataLogMapper.insert(dataLog);
    }
}