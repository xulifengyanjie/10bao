package cn.com.twsm.newstopic.model;

public class WeiboInfo {
    private Integer id;

    private String author;

    private String publishTime;

    private String content;

    private Integer commentNum;

    private Integer repostNum;

    private String comments;

    private String commentTime;

    private String repostTime;

    private Integer fanNum;

    private Integer focusNum;

    private Integer releaseNum;

    private String summary;

    private String crawlTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime == null ? null : publishTime.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Integer getRepostNum() {
        return repostNum;
    }

    public void setRepostNum(Integer repostNum) {
        this.repostNum = repostNum;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime == null ? null : commentTime.trim();
    }

    public String getRepostTime() {
        return repostTime;
    }

    public void setRepostTime(String repostTime) {
        this.repostTime = repostTime == null ? null : repostTime.trim();
    }

    public Integer getFanNum() {
        return fanNum;
    }

    public void setFanNum(Integer fanNum) {
        this.fanNum = fanNum;
    }

    public Integer getFocusNum() {
        return focusNum;
    }

    public void setFocusNum(Integer focusNum) {
        this.focusNum = focusNum;
    }

    public Integer getReleaseNum() {
        return releaseNum;
    }

    public void setReleaseNum(Integer releaseNum) {
        this.releaseNum = releaseNum;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public String getCrawlTime() {
        return crawlTime;
    }

    public void setCrawlTime(String crawlTime) {
        this.crawlTime = crawlTime == null ? null : crawlTime.trim();
    }
}