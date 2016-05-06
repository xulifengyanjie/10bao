package cn.com.twsm.newstopic.model;

public class Weibo {
    private Integer id;

    private String weiboId;

    private String text;

    private String weight;

    private String repostsCount;

    private String source;

    private String createdAt;

    private String link;

    private String commentsCount;

    private String repostsId;

    private String publisher;
    
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

    public String getWeiboId() {
        return weiboId;
    }

    public void setWeiboId(String weiboId) {
        this.weiboId = weiboId == null ? null : weiboId.trim();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight == null ? null : weight.trim();
    }

    public String getRepostsCount() {
        return repostsCount;
    }

    public void setRepostsCount(String repostsCount) {
        this.repostsCount = repostsCount == null ? null : repostsCount.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt == null ? null : createdAt.trim();
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link == null ? null : link.trim();
    }

    public String getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(String commentsCount) {
        this.commentsCount = commentsCount == null ? null : commentsCount.trim();
    }

    public String getRepostsId() {
        return repostsId;
    }

    public void setRepostsId(String repostsId) {
        this.repostsId = repostsId == null ? null : repostsId.trim();
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher == null ? null : publisher.trim();
    }
}