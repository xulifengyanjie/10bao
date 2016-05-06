package cn.com.twsm.newstopic.model;

public class WeiboResult {
    private Integer id;

    private String newsNum;

    private String weight;

    private String batchNum;

    private Integer sort;

    private String batchDate;

    private Integer catalogId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNewsNum() {
        return newsNum;
    }

    public void setNewsNum(String newsNum) {
        this.newsNum = newsNum == null ? null : newsNum.trim();
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight == null ? null : weight.trim();
    }

    public String getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum == null ? null : batchNum.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getBatchDate() {
        return batchDate;
    }

    public void setBatchDate(String batchDate) {
        this.batchDate = batchDate == null ? null : batchDate.trim();
    }

    public Integer getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Integer catalogId) {
        this.catalogId = catalogId;
    }
}