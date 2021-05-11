package com.zzb.item.controller;

import com.zzb.common.api.Page;
import com.zzb.common.api.Result;
import com.zzb.item.bo.SpuBo;
import com.zzb.item.pojo.Sku;
import com.zzb.item.pojo.Spu;
import com.zzb.item.pojo.SpuDetail;
import com.zzb.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("spu/page")
    public Result<Page<SpuBo>> querySpuBoByPage(@RequestParam(required = false) String key,
                                                @RequestParam(required = false) Boolean saleable,
                                                @RequestParam(required = false, defaultValue = "1") Integer page,
                                                @RequestParam(required = false, defaultValue = "5") Integer rows) {
        List<SpuBo> list  = this.goodsService.querySpuBoByPage(key, saleable, page, rows);
        return Result.success(Page.restPage(list));

    }

    @PostMapping("goods")
    public Result saveGoods(@RequestBody SpuBo spuBo){
        this.goodsService.saveGoods(spuBo);
        return Result.success("添加成功！");
    }

    @GetMapping("spu/detail/{spuId}")
    public Result<SpuDetail> querySpuDetailById(@PathVariable("spuId") Long spuId){
        SpuDetail spuDetail = this.goodsService.querySpuDetailById(spuId);
        return Result.success(spuDetail);
    }

    @GetMapping("sku/list")
    public Result<List<Sku>> querySkuListById(@RequestParam("id") Long spuId){
        List<Sku> skuList = this.goodsService.querySkuListById(spuId);
        return Result.success(skuList);
    }

    @PutMapping("goods")
    public Result updateGoods(@RequestBody SpuBo spuBo){
        this.goodsService.updateGoods(spuBo);
        return Result.success("修改成功");
    }

}
