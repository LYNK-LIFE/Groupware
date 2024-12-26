package com.semi.lynk.function.db_management.service;

import com.semi.lynk.function.db_management.model.dao.DbMapper;
import com.semi.lynk.function.db_management.model.dto.ContractDTO;
import com.semi.lynk.function.db_management.model.dto.CustomerDTO;
import com.semi.lynk.function.db_management.model.dto.EmployeeDTO;
import com.semi.lynk.function.db_management.model.dto.ProductManageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DbService {

    private final DbMapper dbMapper;

    @Autowired
    public DbService (DbMapper dbMapper){this.dbMapper=dbMapper;}

//====================================================================================================================
    //각종 보험사 등록

    public void insuranceRegistration(ProductManageDTO productManageDTO) {
        dbMapper.insertinsurance(productManageDTO);
    }


    public List<ProductManageDTO> insuranceProducts() {
        return dbMapper.selectinsurance();
    }


    public void deleteProduct(String productNo) {

        int rowsAffected = dbMapper.deleteProduct(productNo);

        if (rowsAffected == 0) {
            throw new RuntimeException("Failed to delete product. Product not found.");
        }
    }

//=====================================================================================================================
    //고객등록

    public void registerCustomer(CustomerDTO customerDTO) {
        dbMapper.insertCustomer(customerDTO);
    }

//=====================================================================================================================
    //신규계약 등록

    public List<EmployeeDTO> getAllEmployees() { return dbMapper.selectAllEmployees();}


    public List<CustomerDTO> getAllCustomers() {
        return dbMapper.selectAllCustomers();
    }


    public List<ProductManageDTO> searchProducts(String keyword, Integer insuranceCode) {
        return dbMapper.searchProducts(keyword, insuranceCode);
    }


    public void registerContract(ContractDTO contractDTO) {
        dbMapper.insertContract(contractDTO);
    }

    public ContractDTO getLatestContract() {
        ContractDTO contract = dbMapper.findLatestContract();
        if (contract == null) {
            contract = new ContractDTO();
            contract.setLastReformDate(new Date());
            contract.setLastInseminatee("Unknown User");
        }
        return contract;
    }
}







