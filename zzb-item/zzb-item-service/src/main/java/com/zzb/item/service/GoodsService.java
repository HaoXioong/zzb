package com.zzb.item.service;

import com.github.pagehelper.PageHelper;
import com.zzb.item.bo.SpuBo;
import com.zzb.item.mapper.*;
import com.zzb.item.pojo.Sku;
import com.zzb.item.pojo.Spu;
import com.zzb.item.pojo.SpuDetail;
import com.zzb.item.pojo.Stock;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoodsService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    SpuDetailMapper spuDetailMapper;

    @Autowired
    SkuMapper skuMapper;

    @Autowired
    StockMapper stockMapper;

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

    @Transactional
    public void saveGoods(SpuBo spuBo){
        spuBo.setId(null);
        spuBo.setSaleable(true);
        spuBo.setValid(true);
        spuBo.setCreateTime(new Date());
        spuBo.setLastUpdateTime(spuBo.getLastUpdateTime());
        this.spuMapper.insertSelective(spuBo);

        SpuDetail spuDetail = spuBo.getSpuDetail();
        spuDetail.setSpuId(spuBo.getId());
        this.spuDetailMapper.insertSelective(spuDetail);

        saveSkuAndStock(spuBo);
    }

    public void saveSkuAndStock(SpuBo spuBo){
        spuBo.getSkus().forEach(sku -> {
            sku.setSpuId(spuBo.getId());
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            this.skuMapper.insertSelective(sku);

            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            this.stockMapper.insertSelective(stock);
        });
    }

    public SpuDetail querySpuDetailById(Long spuId){
        return this.spuDetailMapper.selectByPrimaryKey(spuId);
    }

    public List<Sku> querySkuListById(Long spuId){
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        List<Sku> skus = this.skuMapper.select(sku);
        skus.forEach(s->{
            Stock stock = this.stockMapper.selectByPrimaryKey(s.getId());
            s.setStock(stock.getStock());
        });
        return skus;
    }

    @Transactional
    public void updateGoods(SpuBo spu){
        List<Sku> skuList = this.querySkuListById(spu.getId());
        if(!CollectionUtils.isEmpty(skuList)){
            List<Long> ids = skuList.stream().map(sku -> sku.getId()).collect(Collectors.toList());

            Example example = new Example(Stock.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andIn("skuId", ids);
            this.stockMapper.deleteByExample(example);

            Sku sku = new Sku();
            sku.setSpuId(spu.getId());
            this.skuMapper.delete(sku);
        }

        saveSkuAndStock(spu);

        spu.setLastUpdateTime(new Date());
        spu.setCreateTime(null);
        spu.setValid(null);
        spu.setSaleable(null);
        this.spuMapper.updateByPrimaryKeySelective(spu);

        this.spuDetailMapper.updateByPrimaryKeySelective(spu.getSpuDetail());
    }
}
