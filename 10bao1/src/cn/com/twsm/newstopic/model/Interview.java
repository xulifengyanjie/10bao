package cn.com.twsm.newstopic.model;

import java.io.Serializable;
import java.util.List;

public class Interview implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String title;

    private Integer topicId;

    private Integer approvalUserId;

    private Integer addUserId;

    private String status;

    private String interviewObject;

    private String content;
    
    private String addTime;
    
    private String appContent;
    
    private List<User> userList;
    
    private List<Topic> topicList;

    public String getAppContent() {
		return appContent;
	}

	public void setAppContent(String appContent) {
		this.appContent = appContent;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public List<Topic> getTopicList() {
		return topicList;
	}

	public void setTopicList(List<Topic> topicList) {
		this.topicList = topicList;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public Integer getApprovalUserId() {
        return approvalUserId;
    }

    public void setApprovalUserId(Integer approvalUserId) {
        this.approvalUserId = approvalUserId;
    }

    public Integer getAddUserId() {
        return addUserId;
    }

    public void setAddUserId(Integer addUserId) {
        this.addUserId = addUserId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getInterviewObject() {
        return interviewObject;
    }

    public void setInterviewObject(String interviewObject) {
        this.interviewObject = interviewObject == null ? null : interviewObject.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}