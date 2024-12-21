package com.semi.lynk.function.db_management.service;

import com.semi.lynk.function.db_management.model.dao.DbMapper;
import com.semi.lynk.function.db_management.model.dto.ProductManageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DbService {

    private final DbMapper dbMapper;

    @Autowired
    public DbService (DbMapper dbMapper){this.dbMapper=dbMapper;}


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




}
