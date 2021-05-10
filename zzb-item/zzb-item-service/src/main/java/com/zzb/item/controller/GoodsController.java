package com.zzb.item.controller;

import com.zzb.common.api.Page;
import com.zzb.common.api.Result;
import com.zzb.item.bo.SpuBo;
import com.zzb.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("spu")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("page")
    public Result<Page<SpuBo>> querySpuBoByPage(@RequestParam(required = false) String key,
                                                @RequestParam(required = false) Boolean saleable,
                                                @RequestParam(required = false, defaultValue = "1") Integer page,
                                                @RequestParam(required = false, defaultValue = "5") Integer rows) {
        List<SpuBo> list  = this.goodsService.querySpuBoByPage(key, saleable, page, rows);
        return Result.success(Page.restPage(list));

    }
}
