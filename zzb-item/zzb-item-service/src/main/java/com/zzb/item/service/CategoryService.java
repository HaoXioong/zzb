package com.zzb.item.service;

import com.zzb.item.mapper.CategoryMapper;
import com.zzb.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 根据父节点查子节点
     * @param pid
     * @return
     */
    public List<Category> queryCategoriesByPid(Long pid) {

        Category record = new Category();
        record.setParentId(pid);
        return this.categoryMapper.select(record);
    }
}
