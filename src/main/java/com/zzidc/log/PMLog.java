package com.zzidc.log;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.giant.zzidc.base.utils.StringUtil;

/**
 * [日志对象]
 * 
 * @author likai
 * @date 2018年8月6日 下午2:29:25
 * @company Gainet
 * @version 1.0
 * @copyright copyright (c) 2018
 */
public class PMLog {
	/**
	 * 模块名称
	 */
	private LogModule module;
	/**
	 * 请求方法
	 */
	private LogMethod method;
	/**
	 * 对象ID
	 */
	private int objectId;
	/**
	 * 方法参数
	 */
	private String params;
	/**
	 * 开始时间
	 */
	private Timestamp startTime = new Timestamp(System.currentTimeMillis());
	/**
	 * 结束时间
	 */
	private Timestamp endTime;
	/**
	 * 备注
	 */
	private String comment;
	/**
	 * 历史纪录
	 */
	private List<PMLogItem> items;

	/**
	 * @param module
	 * @param method
	 * @param desc
	 * @param params
	 * @param comment
	 */
	public PMLog(LogModule module, LogMethod method, String params, String comment) {
		super();
		this.module = module;
		this.method = method;
		this.params = params;
		this.comment = comment;
	}

	/**
	 * @param module
	 * @param method
	 * @param objectId
	 * @param desc
	 * @param params
	 * @param startTime
	 * @param comment
	 */
	public PMLog(LogModule module, LogMethod method, int objectId, String params,
			String comment) {
		super();
		this.module = module;
		this.method = method;
		this.objectId = objectId;
		this.params = params;
		this.comment = comment;
	}

	/**
	 * @param module
	 * @param method
	 * @param objectId
	 * @param desc
	 * @param params
	 * @param startTime
	 * @param endTime
	 * @param comment
	 * @param items
	 */
	public PMLog(LogModule module, LogMethod method, int objectId, String params, Timestamp startTime,
			Timestamp endTime, String comment, List<PMLogItem> items) {
		super();
		this.module = module;
		this.method = method;
		this.objectId = objectId;
		this.params = params;
		this.startTime = startTime;
		this.endTime = endTime;
		this.comment = comment;
		this.items = items;
	}

	public LogModule getModule() {
		return module;
	}

	public void setModule(LogModule module) {
		this.module = module;
	}

	public LogMethod getMethod() {
		return method;
	}

	public void setMethod(LogMethod method) {
		this.method = method;
	}

	public int getObjectId() {
		return objectId;
	}

	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public void setEndTime() {
		this.endTime = new Timestamp(System.currentTimeMillis());
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<PMLogItem> getItems() {
		return items;
	}

	public void setItems(List<PMLogItem> items) {
		this.items = items;
	}
	/**
	 * 
	 * [添加History] <br>
	 * @author likai <br>
	 * @date 2018年8月6日 下午4:26:03 <br>
	 * @param item <br>
	 */
	public void add(PMLogItem item) {
		if(this.items == null) {
			this.items = new ArrayList<PMLogItem>();
		}
		this.items.add(item);
	}
	/**
	 * [添加History] <br>
	 * 
	 * @author likai <br>
	 * @date 2018年8月6日 下午5:30:11 <br>
	 * @param field 字段名称
	 * @param oldData 历史数据
	 * @param newData 新数据
	 * @param isDiff 是否显示区别<br>
	 */
	public void add(String field, String oldData, String newData, boolean isDiff) {
		if(oldData == null) {
			oldData = "";
		}
		if(newData == null) {
			newData = "";
		}
		if(oldData.equals(newData)) {
			return;
		}
		PMLogItem pmLogItem = new PMLogItem();
		pmLogItem.setField(field);
		pmLogItem.setOldData(oldData);
		pmLogItem.setNewData(newData);
		pmLogItem.setDiff(isDiff ? (short) 1 : (short) 0);
		this.add(pmLogItem);
	}
	/**
	 * [计算时长] <br>
	 * 
	 * @author likai <br>
	 * @date 2018年8月6日 下午4:25:43 <br>
	 * @return <br>
	 */
	public int getTimeLength() {
		if(endTime == null) {
			endTime = new Timestamp(System.currentTimeMillis());
		}
		return (int) (endTime.getTime() - startTime.getTime());
	}
	
	public void add(int objectId, Object oldEntity, Object newEntity, String... fields) {
		this.objectId = objectId;
		add(oldEntity, newEntity, null, fields);
	}
	
	public void add(Object oldEntity, Object newEntity, String... fields) {
		add(oldEntity, newEntity, null, fields);
	}
	
	public void add(int objectId, Object oldEntity, Object newEntity, String[] diffFields, String... fields) {
		this.objectId = objectId;
		add(oldEntity, newEntity, diffFields, fields);
	}
	
	public void add(Object oldEntity, Object newEntity, String[] diffFields, String... fields) {
		// 字段如果为空时结束
		if(fields == null || fields.length == 0) {
			return ;
		}
		// oldEntity 和 newEntity 类型不同时结束
		if(oldEntity.getClass() != newEntity.getClass()) {
			return ;
		}
		List<String> diffs = new ArrayList<String>();
		if(diffFields != null && diffFields.length > 0) {
			diffs = Arrays.asList(diffFields);
		}
		for(String field : fields) {
			if(field == null || field.trim().length() == 0) {
				continue;
			}
			try {
				Object oldValue = getFieldValue(oldEntity, field);
				Object newValue = getFieldValue(newEntity, field);
				String oldData = null;
				String newData = null;
				if(oldValue == null && newValue == null) {
					continue;
				} else if(oldValue == null) {
					oldData = "";
					if (newValue instanceof String) {
						newData = (String) newValue;
					} else if(newValue instanceof Integer) {
						newData = String.valueOf((Integer) newValue);
					} else if(newValue instanceof Short) {
						newData = String.valueOf((Short) newValue);
					} else if(newValue instanceof Long) {
						newData = String.valueOf((Long) newValue);
					} else if(newValue instanceof Date) {
						newData = StringUtil.dateToString((Date) newValue);
					} else if(newValue instanceof Timestamp) {
						newData = StringUtil.timestampToString((Timestamp) newValue);
					} else {
						newData = "";
					}
				} else if(newValue == null) {
					newData = "";
					if (oldValue instanceof String) {
						oldData = (String) oldValue;
					} else if(oldValue instanceof Integer) {
						oldData = String.valueOf((Integer) oldValue);
					} else if(oldValue instanceof Short) {
						oldData = String.valueOf((Short) oldValue);
					} else if(oldValue instanceof Long) {
						oldData = String.valueOf((Long) oldValue);
					} else if(oldValue instanceof Date) {
						oldData = StringUtil.dateToString((Date) oldValue);
					} else if(oldValue instanceof Timestamp) {
						oldData = StringUtil.timestampToString((Timestamp) oldValue);
					} else {
						oldData = "";
					}
				} else {
					if (oldValue instanceof String) {
						newData = (String) newValue;
						oldData = (String) oldValue;
					} else if(newValue instanceof Integer) {
						newData = String.valueOf((Integer) newValue);
						oldData = String.valueOf((Integer) oldValue);
					} else if(newValue instanceof Short) {
						newData = String.valueOf((Short) newValue);
						oldData = String.valueOf((Short) oldValue);
					} else if(newValue instanceof Long) {
						newData = String.valueOf((Long) newValue);
						oldData = String.valueOf((Long) oldValue);
					} else if(newValue instanceof Date) {
						newData = StringUtil.dateToString((Date) newValue);
						oldData = StringUtil.dateToString((Date) oldValue);
					} else if(newValue instanceof Timestamp) {
						newData = StringUtil.timestampToString((Timestamp) newValue);
						oldData = StringUtil.timestampToString((Timestamp) oldValue);
					} else {
						newData = "";
						oldData = "";
					}
				}
				add(field, oldData, newData, diffs.contains(field));
			} catch (NoSuchMethodException e) {
				continue;
			} catch (Exception e) {
				continue;
			}
		}
	}
	
	private Object getFieldValue(Object entity, String field ) throws Exception {
		String methodName = getMethodName(getFieldName(field));
		Method method = entity.getClass().getMethod(methodName);
		return method.invoke(entity);
	}
	
	private String getFieldName(String dbField) {
		if(dbField == null || dbField.trim().length() == 0) {
			return "";
		}
		StringBuffer fieldName = new StringBuffer();
		boolean isDD = false;
		for(int i=0; i<dbField.length(); i++) {
			char at = dbField.charAt(i);
			String charAt = String.valueOf(at);
			if(!charAt.equals(" ")) {
				if(charAt.equals("_")) {
					isDD = true;
				} else if(isDD) {
					fieldName.append(charAt.toUpperCase());
					isDD = false;
				} else {
					fieldName.append(charAt);
				}
			}
		}
		return fieldName.toString();
	}
	
	private String getMethodName(String field) {
		if(field.length() > 1) {
			return "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
		} else {
			return "get" + field.toUpperCase();
		}
	}
}
