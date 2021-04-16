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

    /**
     * 根据父节点添加子节点
     * @param category
     * @return
     */
    public int addCategory(Category category){
        return this.categoryMapper.insert(category);
    }

    /**
     * 根据id修改名称
     * @param id
     * @param name
     * @return
     */
    public int editCategory(Long id, String name){
        Category category = new Category();
        category.setId(id);
        Category record = this.categoryMapper.selectOne(category);
        record.setName(name);
        return this.categoryMapper.updateByPrimaryKey(record);
    }

    public int delCategory(Long id) {
        Category category = new Category();
        category.setId(id);
        return this.categoryMapper.delete(category);
    }
}
