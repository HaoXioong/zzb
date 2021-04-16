package com.zzb.item.controller;

import com.zzb.common.api.Page;
import com.zzb.common.api.Result;
import com.zzb.item.pojo.Brand;
import com.zzb.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("")
    public Result<Page<Brand>> queryBrand() {
        return this.brandService.queryBrand();
    }
}
