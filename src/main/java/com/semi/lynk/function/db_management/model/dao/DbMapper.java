package com.semi.lynk.function.db_management.model.dao;

import com.semi.lynk.function.db_management.model.dto.*;
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

//===========================================================================

    List<EmployeeDTO> selectAllEmployees();

    List<CustomerDTO> selectAllCustomers();

    List<ProductManageDTO> searchProducts(@Param("keyword") String keyword,
                                          @Param("insuranceCode") Integer insuranceCode);

    void insertContract(ContractDTO contractDTO);

    ContractDTO findLatestContract();

//=======================================================================================================================

    List<ExpiringCustomerDTO> searchExpiringCustomers(@Param("customerName")String customerName,
                                                       @Param("insuredName")String insuredName,
                                                       @Param("customerSsn")String customerSsn,
                                                       @Param("insuredSsn")String insuredSsn,
                                                       @Param("employeeNo")String employeeNo,
                                                       @Param("employeeName")String employeeName,
                                                       @Param("month")String month);

}

