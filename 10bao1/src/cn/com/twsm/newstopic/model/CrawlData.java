package cn.com.twsm.newstopic.model;

public class CrawlData {
	private Integer id;

	private String collectionsource;

	private String collectamount;

	private String collectacquisition;

	private String collecttime;

	private String link;

	private String collectionlayer;

	private String weiboid;

	private String wechataccount;

	private String type;
	
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCollectionsource() {
		return collectionsource;
	}

	public void setCollectionsource(String collectionsource) {
		this.collectionsource = collectionsource == null ? null
				: collectionsource.trim();
	}

	public String getCollectamount() {
		return collectamount;
	}

	public void setCollectamount(String collectamount) {
		this.collectamount = collectamount == null ? null : collectamount
				.trim();
	}

	public String getCollectacquisition() {
		return collectacquisition;
	}

	public void setCollectacquisition(String collectacquisition) {
		this.collectacquisition = collectacquisition == null ? null
				: collectacquisition.trim();
	}

	public String getCollecttime() {
		return collecttime;
	}

	public void setCollecttime(String collecttime) {
		this.collecttime = collecttime == null ? null : collecttime.trim();
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link == null ? null : link.trim();
	}

	public String getCollectionlayer() {
		return collectionlayer;
	}

	public void setCollectionlayer(String collectionlayer) {
		this.collectionlayer = collectionlayer == null ? null : collectionlayer
				.trim();
	}

	public String getWeiboid() {
		return weiboid;
	}

	public void setWeiboid(String weiboid) {
		this.weiboid = weiboid == null ? null : weiboid.trim();
	}

	public String getWechataccount() {
		return wechataccount;
	}

	public void setWechataccount(String wechataccount) {
		this.wechataccount = wechataccount == null ? null : wechataccount
				.trim();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}
}