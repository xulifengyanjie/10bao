package cn.com.twsm.newstopic.model;

public class BbsNews {
    private Integer id;

    private String newsNum;

    private String title;

    private String weight;

    private Integer hitsCount;

    private String collectTime;

    private String subType;

    private String poster;

    private Integer commentsCount;

    private String smallType;

    private String forumLink;

    private String collectSource;

    private String bigType;

    private String postTime;
    
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

    public String getNewsNum() {
        return newsNum;
    }

    public void setNewsNum(String newsNum) {
        this.newsNum = newsNum == null ? null : newsNum.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight == null ? null : weight.trim();
    }

    public Integer getHitsCount() {
        return hitsCount;
    }

    public void setHitsCount(Integer hitsCount) {
        this.hitsCount = hitsCount;
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

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster == null ? null : poster.trim();
    }

    public Integer getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(Integer commentsCount) {
        this.commentsCount = commentsCount;
    }

    public String getSmallType() {
        return smallType;
    }

    public void setSmallType(String smallType) {
        this.smallType = smallType == null ? null : smallType.trim();
    }

    public String getForumLink() {
        return forumLink;
    }

    public void setForumLink(String forumLink) {
        this.forumLink = forumLink == null ? null : forumLink.trim();
    }

    public String getCollectSource() {
        return collectSource;
    }

    public void setCollectSource(String collectSource) {
        this.collectSource = collectSource == null ? null : collectSource.trim();
    }

    public String getBigType() {
        return bigType;
    }

    public void setBigType(String bigType) {
        this.bigType = bigType == null ? null : bigType.trim();
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime == null ? null : postTime.trim();
    }
}