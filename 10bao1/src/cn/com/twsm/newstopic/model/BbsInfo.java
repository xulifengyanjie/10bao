package cn.com.twsm.newstopic.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class BbsInfo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer bbsNum;

    private String publishTime;

    private String title;

    private String author;

    private String bbsLink;

    private Integer repliesNum;

    private String repliesTime;

    private String repliesContent;

    private String crawlTime;

    private BigDecimal newsIndex;

    private Integer newsLevel;

    private Integer catalogId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBbsNum() {
        return bbsNum;
    }

    public void setBbsNum(Integer bbsNum) {
        this.bbsNum = bbsNum;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime == null ? null : publishTime.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public String getBbsLink() {
        return bbsLink;
    }

    public void setBbsLink(String bbsLink) {
        this.bbsLink = bbsLink == null ? null : bbsLink.trim();
    }

    public Integer getRepliesNum() {
        return repliesNum;
    }

    public void setRepliesNum(Integer repliesNum) {
        this.repliesNum = repliesNum;
    }

    public String getRepliesTime() {
        return repliesTime;
    }

    public void setRepliesTime(String repliesTime) {
        this.repliesTime = repliesTime == null ? null : repliesTime.trim();
    }

    public String getRepliesContent() {
        return repliesContent;
    }

    public void setRepliesContent(String repliesContent) {
        this.repliesContent = repliesContent == null ? null : repliesContent.trim();
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