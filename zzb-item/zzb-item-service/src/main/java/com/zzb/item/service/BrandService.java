package com.zzb.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzb.common.api.Page;
import com.zzb.item.mapper.BrandMapper;
import com.zzb.item.pojo.Brand;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;

    /**
     *查询品牌
     * @param key
     * @param sortBy
     * @param desc
     * @param page
     * @param rows
     * @return
     */
    public List<Brand> queryBrand(String key, String sortBy, Boolean desc, Integer page, Integer rows){

        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();

        if(StringUtils.isNotBlank(key)){
            criteria.andLike("name", "%" + key + "%").orEqualTo("letter", key.toUpperCase());
        }

        PageHelper.startPage(page, rows);

        if(StringUtils.isNotBlank(sortBy)){
            example.setOrderByClause(sortBy + " " + (desc ? "desc" : "asc"));
        }

        return this.brandMapper.selectByExample(example);
    }

    /**
     * 添加品牌
     * @param brand
     * @param cids
     */
    public void addBrand(Brand brand, List<Long> cids){
        this.brandMapper.insertSelective(brand);
        cids.forEach(cid->{
            this.brandMapper.insertBrandAndCategory(cid, brand.getId());
        });
    }

    public void delBrand(Long id){
        Brand brand = new Brand();
        brand.setId(id);
        this.brandMapper.deleteByPrimaryKey(brand);
        this.brandMapper.delBrandAndCatagory(id);
    }
}
