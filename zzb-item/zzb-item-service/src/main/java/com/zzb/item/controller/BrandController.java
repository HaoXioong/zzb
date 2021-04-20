package com.zzb.item.controller;

import com.zzb.common.api.Page;
import com.zzb.common.api.Result;
import com.zzb.item.pojo.Brand;
import com.zzb.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("page")
    public Result<Page<Brand>> queryBrand(@RequestParam(required = false) String key,
                                          @RequestParam(required = false) String sortBy,
                                          @RequestParam(required = false) Boolean desc,
                                          @RequestParam(required = false, defaultValue = "0") Integer page,
                                          @RequestParam(required = false, defaultValue = "5") Integer rows) {

        List<Brand> listBrand = this.brandService.queryBrand(key, sortBy, desc, page, rows);
        return Result.success(Page.restPage(listBrand));
    }

    @PostMapping("addBrand")
    public Result addBrand(Brand brand, @RequestParam("cids") List<Long> cids){
        this.brandService.addBrand(brand, cids);
        return Result.success(null);
    }

    @PostMapping("delBrand")
    public Result delBrand(@RequestParam("id") Long id){
        this.brandService.delBrand(id);
        return Result.success(null);
    }

}
