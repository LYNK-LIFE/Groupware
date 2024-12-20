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

    public void meritzRegistration(ProductManageDTO productManageDTO) {
        dbMapper.insertMeritz(productManageDTO);
    }


    public List<ProductManageDTO> MeritzProducts() {
        return dbMapper.selectMeritz();
    }
}
