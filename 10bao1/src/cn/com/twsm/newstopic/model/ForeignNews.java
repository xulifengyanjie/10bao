package cn.com.twsm.newstopic.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class ForeignNews implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String newsNum;

    private String title;

    private String subtitle;

    private String publishTime;

    private String publishMedia;

    private String newsLink;

    private String author;

    private Integer readNum;

    private String crawlTime;

    private BigDecimal newsIndex;

    private Integer newsLevel;

    private Integer catalogId;
    
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
		this.newsNum = newsNum;
	}

	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle == null ? null : subtitle.trim();
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime == null ? null : publishTime.trim();
    }

    public String getPublishMedia() {
        return publishMedia;
    }

    public void setPublishMedia(String publishMedia) {
        this.publishMedia = publishMedia == null ? null : publishMedia.trim();
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

    public Integer getReadNum() {
        return readNum;
    }

    public void setReadNum(Integer readNum) {
        this.readNum = readNum;
    }

    public String getCrawlTime() {
        return crawlTime;
    }

    public void setCrawlTime(String crawlTime) {
        this.crawlTime = crawlTime == null ? null : crawlTime.trim();
    }

    public BigDecimal getNewsIndex() {
        return newsIndex;
    }

    public void setNewsIndex(BigDecimal newsIndex) {
        this.newsIndex = newsIndex;
    }

    public Integer getNewsLevel() {
        return newsLevel;
    }

    public void setNewsLevel(Integer newsLevel) {
        this.newsLevel = newsLevel;
    }

    public Integer getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Integer catalogId) {
        this.catalogId = catalogId;
    }
}