package com.ssw.vo;
import java.io.Serializable;
import java.math.BigDecimal;

public class ProductListVO  implements Serializable{
    private Integer id;
    private Integer categoryId;
    private String  name;//
    private String  subtitle;//oppo促销进行中",
    private Integer status;// 1,
    private String  mainImage;//mainimage.jpg",
    private BigDecimal price;// 2999.11,
    /*private String sellerId;//买家id
    private Integer brandId;//品牌id
    private Integer spu;//spu号
    private String specificationItems;//sku规格集合
    private String defaultItemId;//默认sku规格
    private Integer isEnableSpec;//是否使用规格
    private Integer typeTemplateId;//模板类型
    private String packageList;//包装类型
    private String saleService;//售后服务*/
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getCategoryId() {
        return categoryId;
   }
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSubtitle() {
        return subtitle;
    }
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getMainImage() {
        return mainImage;
    }
    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

   /* public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getSpu() {
        return spu;
    }

    public void setSpu(Integer spu) {
        this.spu = spu;
    }

    public String getSpecificationItems() {
        return specificationItems;
    }

    public void setSpecificationItems(String specificationItems) {
        this.specificationItems = specificationItems;
    }

    public String getDefaultItemId() {
        return defaultItemId;
    }

    public void setDefaultItemId(String defaultItemId) {
        this.defaultItemId = defaultItemId;
    }

    public Integer getIsEnableSpec() {
        return isEnableSpec;
    }

    public void setIsEnableSpec(Integer isEnableSpec) {
        this.isEnableSpec = isEnableSpec;
    }

    public Integer getTypeTemplateId() {
        return typeTemplateId;
    }

    public void setTypeTemplateId(Integer typeTemplateId) {
        this.typeTemplateId = typeTemplateId;
    }

    public String getPackageList() {
        return packageList;
    }

    public void setPackageList(String packageList) {
        this.packageList = packageList;
    }

    public String getSaleService() {
        return saleService;
    }

    public void setSaleService(String saleService) {
        this.saleService = saleService;
    }*/
}