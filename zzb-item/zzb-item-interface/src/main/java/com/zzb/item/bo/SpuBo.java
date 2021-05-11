package com.zzb.item.bo;

import com.zzb.item.pojo.Sku;
import com.zzb.item.pojo.Spu;
import com.zzb.item.pojo.SpuDetail;

import java.util.List;

public class SpuBo extends Spu {

    private String cname;
    private String bname;

    SpuDetail spuDetail;
    List<Sku> skus;

    public SpuDetail getSpuDetail() {
        return spuDetail;
    }

    public void setSpuDetail(SpuDetail spuDetail) {
        this.spuDetail = spuDetail;
    }

    public List<Sku> getSkus() {
        return skus;
    }

    public void setSkus(List<Sku> skus) {
        this.skus = skus;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }
}
