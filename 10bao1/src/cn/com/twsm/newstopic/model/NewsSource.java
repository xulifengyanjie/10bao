package cn.com.twsm.newstopic.model;

import java.io.Serializable;

public class NewsSource implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String url;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}