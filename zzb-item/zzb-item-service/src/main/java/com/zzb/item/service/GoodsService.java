package com.zzb.item.service;

import com.github.pagehelper.PageHelper;
import com.zzb.item.bo.SpuBo;
import com.zzb.item.mapper.BrandMapper;
import com.zzb.item.mapper.CategoryMapper;
import com.zzb.item.mapper.SpuMapper;
import com.zzb.item.pojo.Spu;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GoodsService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandMapper brandMapper;

    public List<SpuBo> querySpuBoByPage(String key, Boolean saleable, Integer page, Integer rows){

        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();

        if(StringUtils.isNotBlank(key)){
            criteria.andLike("title", "%" + key + "%");
        }

        if(saleable != null){
            criteria.andEqualTo("saleable", saleable);
        }

        PageHelper.startPage(page, rows);

        List<Spu> spus = this.spuMapper.selectByExample(example);

        List<SpuBo> spuBos = new ArrayList<>();

        spus.forEach(spu -> {
            SpuBo spuBo = new SpuBo();

            BeanUtils.copyProperties(spu, spuBo);

            List<String> names = this.categoryService.queryNamesByIds(Arrays.asList(spu.getCid1(), spu.getCid2(),
                    spu.getCid3()));
            spuBo.setCname(StringUtils.join(names, "/"));

            spuBo.setBname(this.brandMapper.selectByPrimaryKey(spu.getBrandId()).getName());

            spuBos.add(spuBo);

        });

        return spuBos;

    }
}
