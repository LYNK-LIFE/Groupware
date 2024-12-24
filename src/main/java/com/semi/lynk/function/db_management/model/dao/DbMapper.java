package com.semi.lynk.function.db_management.model.dao;

import com.semi.lynk.function.db_management.model.dto.CustomerDTO;
import com.semi.lynk.function.db_management.model.dto.EmployeeDTO;
import com.semi.lynk.function.db_management.model.dto.ProductManageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DbMapper {

    void insertinsurance(ProductManageDTO productManageDTO);


    List<ProductManageDTO> selectinsurance();


    int deleteProduct(String productNo);

//========================================================================

    void insertCustomer(CustomerDTO customerDTO);

    List<CustomerDTO> insuranceCustomer();


//=====================================================================================================================

    List<EmployeeDTO> findEmployeesByName(@Param("employeeName") String employeeName);







}
