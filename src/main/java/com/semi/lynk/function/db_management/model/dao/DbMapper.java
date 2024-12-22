package com.semi.lynk.function.db_management.model.dao;

import com.semi.lynk.function.db_management.model.dto.ProductManageDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DbMapper {

    void insertinsurance(ProductManageDTO productManageDTO);


    List<ProductManageDTO> selectinsurance();


    int deleteProduct(String productNo);


}
