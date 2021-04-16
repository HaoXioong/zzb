package com.zzb.item.controller;

import com.alibaba.fastjson.JSON;
import com.zzb.common.api.Result;
import com.zzb.item.pojo.Category;
import com.zzb.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     *
     * @param pid
     * @return
     */
    @GetMapping("list")
    public Result<List<Category>> queryCategoriesByPid(@RequestParam(value="pid", defaultValue = "0")Long pid){
//        try {
            if(pid == null || pid < 0){
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                return Result.validateFailed("参数错误");
            }
            List<Category> categories = this.categoryService.queryCategoriesByPid(pid);
//            if(CollectionUtils.isEmpty(categories)){
////                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//                return ;
//            }
            return Result.success(categories, "success");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 创建分类
     * @param category
     * @return
     */
    @PostMapping("addCategory")
    public Result<Integer> addCategory(@RequestBody Category category) {
        if(category.getParentId()==null||category.getName()==null){
            return Result.validateFailed("参数错误！");
        }
        Integer num = this.categoryService.addCategory(category);
        if(num > 0){
            return Result.success(num);
        }else{
            return Result.failed();
        }
    }

    @PostMapping("editCategory")
    public Result<Integer> editCategory(@RequestBody String str){
        Long id = JSON.parseObject(str).getLong("id");
        String name = JSON.parseObject(str).getString("name");
        if(id==null||id<0||name==null){
            return Result.validateFailed("参数错误！");
        }
        Integer num = this.categoryService.editCategory(id, name);
        if(num > 0){
            return Result.success(num);
        }else{
            return Result.failed();
        }
    }

    @PostMapping("delCategory")
    public Result<Integer> delCategory(@RequestBody String str){
        Long id = JSON.parseObject(str).getLong("id");
        if(id==null||id<0){
            return Result.validateFailed("参数错误！");
        }
        Integer num = this.categoryService.delCategory(id);
        if(num > 0){
            return Result.success(num);
        }else{
            return Result.failed();
        }
    }


}
