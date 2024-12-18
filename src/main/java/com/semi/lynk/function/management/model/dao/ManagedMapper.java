package com.semi.lynk.function.management.model.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ManagedMapper {
    Map<String, Object> getMemberStatusCounts();

    List<Map<String, Object>> getActiveEmployee();
}
