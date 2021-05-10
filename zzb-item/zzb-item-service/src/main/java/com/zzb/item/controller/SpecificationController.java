package com.zzb.item.controller;

import com.zzb.common.api.Result;
import com.zzb.item.pojo.SpecGroup;
import com.zzb.item.pojo.SpecParam;
import com.zzb.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("spec")
public class SpecificationController {

    @Autowired
    private SpecificationService specificationService;

    @GetMapping("groups/{cid}")
    public Result<List<SpecGroup>> queryGroupsByCid(@PathVariable("cid") Long cid){
        List<SpecGroup> list = this.specificationService.queryGroupsByCid(cid);
        return Result.success(list);
    }

    @GetMapping("params")
    public Result<List<SpecParam>> queryParamsByGid(@RequestParam("gid") Long gid){
        List<SpecParam> list = this.specificationService.queryParamsBygid(gid);
        return Result.success(list);
    }
}
