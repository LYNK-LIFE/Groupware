package com.semi.lynk.function.db_management.model.dao;

import com.semi.lynk.function.db_management.model.dto.ProductManageDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DbMapper {

    void insertMeritz(ProductManageDTO productManageDTO);


    List<ProductManageDTO> selectMeritz();
}
