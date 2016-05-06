package cn.com.twsm.newstopic.model;

import java.io.Serializable;

public class ApproveArchive implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer archiveId;

    private Integer userId;

    private String connent;

    private String approveTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArchiveId() {
        return archiveId;
    }

    public void setArchiveId(Integer archiveId) {
        this.archiveId = archiveId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getConnent() {
        return connent;
    }

    public void setConnent(String connent) {
        this.connent = connent == null ? null : connent.trim();
    }

    public String getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(String approveTime) {
        this.approveTime = approveTime == null ? null : approveTime.trim();
    }
}