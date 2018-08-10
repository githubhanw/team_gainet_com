package com.giant.zzidc.base.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GiantPager implements Serializable {
	private static final long serialVersionUID = 1L;

	/** (页面传入的)查询条件（常用：orderByValue[排序规则(ASC/DESC)];queryValue[查询字段的值];...） */
	private Map<String, String> queryCondition;
	/** SQL/HQL语句查询条件(可以减少Map变量的使用) */
	private Map<String, Object> paramMap;
	/** 分页结果集 */
	private List<?> pageResult;
	/** 当前页码（默认为1） */
	private int currentPage = 1;
	/** 总页数 */
	private int totalPages;
	/** 总条数 */
	private int totalCounts;
	/** 每页显示多少条记录（默认为10） */
	private int pageSize = 10;
	/** 要跳转的Action（页面地址） */
	private String desAction;
	/** 排序字段 */
	private String orderColumn;
	/** 起始页码 */
	private int beginPage;
	/** 结束页码 */
	private int endPage;

	/**
	 * 获取查询条件(queryCondition)的值（不进行防注入过滤【不建议使用】）
	 * 
	 * @param key
	 *            键
	 * @return null或非空字符串
	 */
	public String get(String key) {
		if (queryCondition == null)
			return null;
		return (queryCondition.get(key) == null || queryCondition.get(key)
				.equals("")) ? null : queryCondition.get(key).trim();
	}

	/**
	 * 获取查询条件(queryCondition)的值
	 * 
	 * @param key
	 *            键
	 * @param isAntiInjected
	 *            是否防止注入
	 * @return null或非空字符串
	 */
	public String get(String key, boolean isAntiInjected) {
		if (queryCondition == null)
			return null;

		String value = queryCondition.get(key);
		if (value == null || "".equals(value = value.trim()))
			return null;

		if (isAntiInjected) {
			value = GiantUtils.filterStr(value);/* 防注入过滤 */
			queryCondition.put(key, value);
		}

		return value;
	}

	/** 保存查询条件(queryCondition)（进行防注入过滤） */
	public void put(String key, String value) {
		if (queryCondition == null)
			queryCondition = new HashMap<String, String>();
		queryCondition.put(key, GiantUtils.filterStr(value));
	}

	/** 保存SQL/HQL语句查询条件(paramMap) */
	public void putParam(String key, Object value) {
		if (paramMap == null)
			this.paramMap = new HashMap<String, Object>();
		paramMap.put(key, value);
	}

	public GiantPager() {
		queryCondition = new HashMap<String, String>();
		queryCondition.put("orderByValue", "ASC");
	}

	/** (页面传入的)查询条件（常用：orderByValue[排序规则(ASC/DESC)];queryValue[查询字段的值];...） */
	public Map<String, String> getQueryCondition() {
		return queryCondition;
	}

	/** (页面传入的)查询条件（常用：orderByValue[排序规则(ASC/DESC)];queryValue[查询字段的值];...） */
	public void setQueryCondition(Map<String, String> queryCondition) {
		this.queryCondition = queryCondition;
	}

	/** SQL/HQL语句查询条件(可以减少Map变量的使用) */
	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	/** SQL/HQL语句查询条件(可以减少Map变量的使用) */
	public void setParamMap(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}

	/** 分页结果集 */
	public List<?> getPageResult() {
		return pageResult;
	}

	/** 分页结果集 */
	public void setPageResult(List<?> pageResult) {
		this.pageResult = pageResult;
	}

	/** 当前页码（默认为1） */
	public int getCurrentPage() {
		return currentPage;
	}

	/** 当前页码（默认为1） */
	public void setCurrentPage(int currentPage) {
		if (currentPage <= 0)
			this.currentPage = 1;
		else
			this.currentPage = currentPage;
	}

	/** 总页数（自动计算） */
	public int getTotalPages() {
		totalPages = pulseTotalCounts();
		return totalPages;
	}

	/** 计算总页数 */
	private int pulseTotalCounts() {
		return (int) Math.ceil(totalCounts / (double) pageSize);
	}

	/** 总页数 */
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	/** 总条数 */
	public int getTotalCounts() {
		return totalCounts;
	}

	/** 总条数 */
	public void setTotalCounts(int totalCounts) {
		this.totalCounts = totalCounts;
	}

	/** 每页显示多少条记录(默认为10) */
	public int getPageSize() {
		return pageSize;
	}

	/** 每页显示多少条记录(默认为10) */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/** 要跳转的Action（页面地址） */
	public String getDesAction() {
		return desAction;
	}

	/** 要跳转的Action（页面地址） */
	public void setDesAction(String desAction) {
		this.desAction = desAction;
	}

	/** 排序字段 */
	public String getOrderColumn() {
		return GiantUtils.filterStr(orderColumn);
	}

	/** 排序字段 */
	public void setOrderColumn(String orderColumn) {
		this.orderColumn = GiantUtils.filterStr(orderColumn);
	}

	/** 起始页码 */
	public int getBeginPage() {
		return beginPage;
	}

	/** 起始页码 */
	public void setBeginPage(int beginPage) {
		this.beginPage = beginPage;
	}

	/** 结束页码 */
	public int getEndPage() {
		return endPage;
	}

	/** 结束页码 */
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public void setPageToResult(GiantPager resultPager, int beginPage,
			int pageSize) {
		if (resultPager.getTotalCounts() % pageSize == 0) {
			resultPager.setTotalPages(resultPager.getTotalCounts() / pageSize);
		} else {
			resultPager.setTotalPages(resultPager.getTotalCounts() / pageSize
					+ 1);
		}
		List<?> dataList = resultPager.getPageResult();
		resultPager.setPageSize(pageSize);
		if (dataList.size() <= pageSize) {
			resultPager.setCurrentPage(beginPage);
			resultPager.setPageResult(dataList);
			resultPager.setEndPage(beginPage);
		} else if (dataList.size() / pageSize >= beginPage) {
			dataList = dataList.subList((beginPage - 1) * pageSize, beginPage
					* pageSize);
			resultPager.setPageResult(dataList);
			resultPager.setCurrentPage(beginPage);

		} else {
			resultPager.setCurrentPage(beginPage);
			resultPager.setEndPage(resultPager.getEndPage());
			resultPager.setPageResult(dataList.subList((beginPage - 1)
					* pageSize, dataList.size()));
		}
	}

}