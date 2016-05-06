package cn.com.twsm.newstopic.model;

public class WeixinInfo {
    private Integer id;

    private String name;

    private String title;

    private String weixinLink;

    private Integer releaseNum;

    private String publishTime;

    private String crawlTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getWeixinLink() {
        return weixinLink;
    }

    public void setWeixinLink(String weixinLink) {
        this.weixinLink = weixinLink == null ? null : weixinLink.trim();
    }

    public Integer getReleaseNum() {
        return releaseNum;
    }

    public void setReleaseNum(Integer releaseNum) {
        this.releaseNum = releaseNum;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime == null ? null : publishTime.trim();
    }

    public String getCrawlTime() {
        return crawlTime;
    }

    public void setCrawlTime(String crawlTime) {
        this.crawlTime = crawlTime == null ? null : crawlTime.trim();
    }
}