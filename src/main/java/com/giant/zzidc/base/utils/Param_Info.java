package com.giant.zzidc.base.utils;

import java.io.Serializable;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class Param_Info implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int code;
	private Object message;
	private Object result;
	private Object pageInfo;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Object getMessage() {
		return message;
	}
	public void setMessage(Object message) {
		this.message = message;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public Object getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(Object pageInfo) {
		this.pageInfo = pageInfo;
	}
	public Param_Info jsonToEntity(String json){
		JSONObject jsonObj=JSONObject.fromObject(json);
		Param_Info paramInfo = (Param_Info) jsonObj.toBean(jsonObj);
		return paramInfo;
	}
	public String entityToJson(){
		return JSONSerializer.toJSON(this).toString(); 
	}
	public Param_Info(int code, Object message, Object result, Object pageInfo) {
		super();
		this.code = code;
		this.message = message;
		this.result = result;
		this.pageInfo = pageInfo;
	}
	public Param_Info() {
		super();
	}
	
}
