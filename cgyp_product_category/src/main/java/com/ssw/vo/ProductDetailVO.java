package com.ssw.vo;
import java.io.Serializable;
import java.math.BigDecimal;

public class ProductDetailVO implements Serializable {
    private Integer id;
    private Integer categoryId;
    private Integer parentCategoryId;
    private String  name;//
    private String  subtitle;//oppo促销进行中",
    private String  imageHost;//http://img.business.com/",
    private String  mainImage;//mainimage.jpg",
    private String  subImages;//[\"business/aa.jpg\",\"business/bb.jpg\",\"business/cc.jpg\",\"business/dd.jpg\",\"business/ee.jpg\"]",
    private String  detail;//richtext",
    private BigDecimal  price;// 2999.11,
    private Integer stock;// 71,
    private Integer status;// 1,
    private String createTime;// "2016-11-20 14:21:53",
    private String updateTime;//2016-11-20 14:21:53"
    private Boolean isNew;

    public Boolean getNew() {
        return isNew;
    }

    public void setNew(Boolean aNew) {
        isNew = aNew;
    }

    public Boolean getHot() {
        return isHot;
    }

    public void setHot(Boolean hot) {
        isHot = hot;
    }

    public Boolean getBanner() {
        return isBanner;
    }

    public void setBanner(Boolean banner) {
        isBanner = banner;
    }

    private Boolean isHot;
    private Boolean isBanner;
    private String sellerId;//买家id
    private Integer brandId;//品牌id
    private Integer spu;//spu号
    private String specificationItems;//sku规格集合
    private String defaultItemId;//默认sku规格
    private Integer isEnableSpec;//是否使用规格
    private Integer typeTemplateId;//模板类型
    private String packageList;//包装类型
    private String saleService;//售后服务
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
    public Integer getParentCategoryId() {
        return parentCategoryId;
    }
    public void setParentCategoryId(Integer parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
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
    public String getImageHost() {
        return imageHost;
    }
    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }
    public String getMainImage() {
        return mainImage;
    }
    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }
    public String getSubImages() {
        return subImages;
    }
   public void setSubImages(String subImages) {
        this.subImages = subImages;
    }
    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getCreateTime() {
        return createTime;
    }
   public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getSellerId() {
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
    }
}