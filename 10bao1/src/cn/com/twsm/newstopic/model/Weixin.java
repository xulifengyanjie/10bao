package cn.com.twsm.newstopic.model;

public class Weixin {
    private Integer id;
    
    private String newsNum;
    
    private String weight;

    private String supportCount;

    private String hitsCount;

    private String publishTime;

    private String transmitCount;

    private String commentsCount;

    private String smallType;

    private String collectSource;

    private String newsSource;

    private String newsLink;

    private String author;

    private String title;

    private String collectTime;

    private String subType;

    private String bigType;
    
    private Integer sort;

    public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight == null ? null : weight.trim();
    }

    public String getSupportCount() {
        return supportCount;
    }

    public void setSupportCount(String supportCount) {
        this.supportCount = supportCount == null ? null : supportCount.trim();
    }

    public String getHitsCount() {
        return hitsCount;
    }

    public void setHitsCount(String hitsCount) {
        this.hitsCount = hitsCount == null ? null : hitsCount.trim();
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime == null ? null : publishTime.trim();
    }

    public String getTransmitCount() {
        return transmitCount;
    }

    public void setTransmitCount(String transmitCount) {
        this.transmitCount = transmitCount == null ? null : transmitCount.trim();
    }

    public String getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(String commentsCount) {
        this.commentsCount = commentsCount == null ? null : commentsCount.trim();
    }

    public String getSmallType() {
        return smallType;
    }

    public void setSmallType(String smallType) {
        this.smallType = smallType == null ? null : smallType.trim();
    }

    public String getCollectSource() {
        return collectSource;
    }

    public void setCollectSource(String collectSource) {
        this.collectSource = collectSource == null ? null : collectSource.trim();
    }

    public String getNewsSource() {
        return newsSource;
    }

    public void setNewsSource(String newsSource) {
        this.newsSource = newsSource == null ? null : newsSource.trim();
    }

    public String getNewsLink() {
        return newsLink;
    }

    public void setNewsLink(String newsLink) {
        this.newsLink = newsLink == null ? null : newsLink.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(String collectTime) {
        this.collectTime = collectTime == null ? null : collectTime.trim();
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType == null ? null : subType.trim();
    }

    public String getBigType() {
        return bigType;
    }

    public void setBigType(String bigType) {
        this.bigType = bigType == null ? null : bigType.trim();
    }

	public String getNewsNum() {
		return newsNum;
	}

	public void setNewsNum(String newsNum) {
		this.newsNum = newsNum;
	}
    
    
}