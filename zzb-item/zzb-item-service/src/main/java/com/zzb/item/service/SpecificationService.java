package com.zzb.item.service;

import com.zzb.item.mapper.SpecGroupMapper;
import com.zzb.item.mapper.SpecParamMapper;
import com.zzb.item.pojo.SpecGroup;
import com.zzb.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecificationService {

    @Autowired
    private SpecGroupMapper specGroupMapper;

    @Autowired
    private SpecParamMapper specParamMapper;

    public List<SpecGroup> queryGroupsByCid(Long cid){
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        return this.specGroupMapper.select(specGroup);
    }


    public List<SpecParam> queryParamsBygid(Long gid){
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        return this.specParamMapper.select(specParam);
    }
}
