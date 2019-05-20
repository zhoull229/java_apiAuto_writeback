package com.wd.api.cases;

public class Rest {
 private String apiId;
 private String apiName;
 private String type;
 private String url;
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}
public String getApiId() {
	return apiId;
}
public void setApiId(String apiId) {
	this.apiId = apiId;
}
public String getApiName() {
	return apiName;
}
public void setApiName(String apiName) {
	this.apiName = apiName;
}
 @Override
	public String toString() {
		// TODO Auto-generated method stub
		return "apiId:"+apiId+",apiName:"+apiName+",type:"+type+",url:"+url;
	}
 
}
