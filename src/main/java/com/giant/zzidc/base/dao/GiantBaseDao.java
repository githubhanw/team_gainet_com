package com.giant.zzidc.base.dao;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.giant.zzidc.base.utils.GiantPager;
import com.giant.zzidc.base.utils.GiantUtils;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/*******************************************************************************
 * [公共DAO层处理类/主要作用是操作数据库为上层(service层)提供服务]
 */
@Repository("dao")
public class GiantBaseDao {

	Logger logger = Logger.getLogger(GiantBaseDao.class);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	protected java.text.DecimalFormat dfxiao = new java.text.DecimalFormat("#.##");
	protected java.text.DecimalFormat dfzheng = new java.text.DecimalFormat("###");

	/**
	 * 采用@Autowired按类型注入SessionFactory, 当有多个SesionFactory的时候Override本函数.
	 */
	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * [获取当前可用的session对象] <br>
	 */
	protected Session getSession() {
		return sessionFactory.openSession();
	}

	private void releaseSession(Session hibernateSession) {
		hibernateSession.close();
	}

	/**
	 * 执行SQL获取对象列表
	 * 
	 * @param sql
	 *            SQL语句
	 * @param obj
	 *            对象（需实例化，即new Object()）
	 * @return null 或 List&lt;?&gt;
	 */
	public List<?> findListBySql(String sql, Object obj) {
		if (sql == null || "".equals(sql = sql.trim()) || obj == null)
			return null;

		Session session = null;
		try {
			session = this.getSession();
			return session.createSQLQuery(sql).addEntity(obj.getClass()).list();
		} catch (Exception e) {
			logger.error("执行SQL获取对象列表（SQL：" + sql + ";实体：" + obj.getClass() + "）发生异常：" + e.toString());
			e.printStackTrace();
		} finally {
			try {
				if (session != null)
					this.releaseSession(session);
			} catch (Exception e) {
				logger.error("释放Session发生异常：" + e.toString());
			}
		}
		return null;
	}


	/**
	 * 执行SQL，返回单一List（List中只有字段值）
	 * 
	 * @param sql
	 *            SQL语句
	 * @return null 或 List
	 */
	public List<?> getSingleList(String sql) {
		Session session = null;
		try {
			session = this.getSession();
			if (session == null)
				return null;

			List<?> list = session.createSQLQuery(sql).setResultTransformer(Transformers.TO_LIST).list();
			if (list != null && list.size() > 0)
				return (List<?>) list.get(0);
		} catch (Exception e) {
			logger.error("[List查询]执行查询出错：" + e.toString());
		} finally {
			if (session != null) {
				try {
					this.releaseSession(session);
				} catch (Exception e) {
				}
			}
		}
		return null;
	}


	/**
	 * 执行SQL，返回结果都是Map对象
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public List<?> ListSql(String sql) throws Exception {
		Session session = null;
		try {
			session = this.getSession();
			Query query = session.createSQLQuery(sql).setResultTransformer(
					org.hibernate.transform.Transformers.ALIAS_TO_ENTITY_MAP);
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new Exception("数据库异常:" + e.getMessage(), e);
		} finally {
			try {
				this.releaseSession(session);
			} catch (Exception e) {
			}
		}
	}

	public List<?> codeSql(String sql) throws Exception {
		Session session = null;
		try {
			session = this.getSession();
			Query query = session.createSQLQuery(sql);

			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new Exception("数据库异常:" + e.getMessage(), e);
		} finally {
			try {
				this.releaseSession(session);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 生成excel文件
	 * 
	 * @param sql
	 * @param title标题
	 * @param type
	 *            1：财务
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String generationExcel(String sql, String[] title, Integer type,
			HttpServletRequest request) {
		String dir = null;
		if (type == 1) {
			dir = request.getSession().getServletContext().getRealPath("")
					+ File.separator + "excel" + File.separator + "caiwu";
		}
		if (dir == null) {
			return null;
		}
		File fileDir = new File(dir);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}

		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String fileName = date + ".xls";

		String exclepath = dir + File.separator + fileName;
		Session session = null;
		WritableWorkbook book = null;
		try {
			book = Workbook.createWorkbook(new File(exclepath));
			WritableSheet sheet = book.createSheet(date, 0);
			for (int j = 0; j < title.length; j++) {
				Label label = new Label(j, 0, title[j]);
				sheet.addCell(label);
			}
			session = this.getSession();
			List<Object[]> list = session.createSQLQuery(sql).list();
			for (int i = 0; i < list.size(); i++) {
				Object[] cell = list.get(i);
				for (int j = 0; j < cell.length; j++) {
					if (cell[j] != null) {
						// 对财务数据进行部分修改
						if (type == 1 && j == 3) {
							String strT = "";
							if ("0".equals(cell[j].toString().trim())) {
								strT = "消费";
							} else if ("1".equals(cell[j].toString().trim())) {
								strT = "存款";
							} else if ("2".equals(cell[j].toString().trim())) {
								strT = "提款";
							} else if ("3".equals(cell[j].toString().trim())) {
								strT = "押金";
							}
							if ("".equals(strT)) {
								Label label = new Label(j, i + 1,
										cell[j].toString());
								sheet.addCell(label);
							} else {
								Label label = new Label(j, i + 1, strT);
								sheet.addCell(label);
							}
						} else {
							Label label = new Label(j, i + 1,
									cell[j].toString());
							sheet.addCell(label);
						}
						// Label label=new Label(j,i+1,cell[j].toString());
						// sheet.addCell(label);
					} else {
						Label label = new Label(j, i + 1, "");
						sheet.addCell(label);
					}
				}
			}
			book.write();
		} catch (Exception e) {
		} finally {
			if (book != null) {
				try {
					book.close();
				} catch (WriteException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				this.releaseSession(session);
			} catch (Exception e) {
			}
		}
		return fileName;
	}

	/**
	 * 执行sql 删除，添加，修改
	 * 
	 * @param sql
	 *            删除，添加，修改数组语句
	 * @return
	 * @throws Exception
	 */
	public boolean executeSql(String[] sql) throws Exception {
		Session session = this.getSession();
		Transaction tx = session.beginTransaction();
		Connection conn = null;
		Statement stmt = null;
		try {
			if (sql != null && sql.length > 0) {
				// conn = session.connection();
				conn = SessionFactoryUtils.getDataSource(sessionFactory).getConnection();
				stmt = conn.createStatement();
				for (String s : sql) {
					stmt.execute(s);
				}
				tx.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new Exception(e.getMessage(), e);
		} finally {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
			try {
				this.releaseSession(session);
			} catch (Exception e) {
			}
		}

	}

	/**
	 * 根据主键获取唯一实体对象
	 * 
	 * @author 娄杰
	 * 
	 * @param entity
	 *            实体[需要实例化即：new Entity()]
	 * @param primaryKey
	 *            主键(类型一定要一致)
	 * 
	 * @return Object 实体对象
	 */
	public Object getEntityByPrimaryKey(Object entity, Object primaryKey) {
		// 初始化hibernateSession实体
		// 利用父类里面的方法获取Session实例
		logger.debug("GiantBaseDao根据主键获取实体"
				+ "getEntityByPrimaryKey(Object entity,"
				+ " Object primaryKey)开始");
		Session hibernateSession = this.getSession();
		Object returnEntity = null;
		if (entity == null) {
			return returnEntity;
		}
		String entityName = "";
		Integer pki = -1;
		Long pkl = -1l;
		String pks = "";
		try {
			entityName = entity.getClass().getName();
			// 判断主键是否为Integer类型
			if (primaryKey instanceof Integer) {
				pki = Integer.valueOf(primaryKey.toString());
				logger.debug("GiantBaseDao根据主键获取实体"
						+ "getEntityByPrimaryKey(Object entity,"
						+ " Object primaryKey)实体名称" + entityName + "主键" + pki);
				returnEntity = hibernateSession.get(entityName, pki);
				// 判断主键是否为Long类型
			} else if (primaryKey instanceof Long) {
				pkl = Long.valueOf(primaryKey.toString());
				logger.debug("GiantBaseDao根据主键获取实体"
						+ "getEntityByPrimaryKey(Object entity,"
						+ " Object primaryKey)实体名称" + entityName + "主键" + pkl);
				returnEntity = hibernateSession.get(entityName, pkl);
				// 判断主键是否为String类型
			} else if (primaryKey instanceof String) {
				pks = primaryKey.toString();
				logger.debug("GiantBaseDao根据主键获取实体"
						+ "getEntityByPrimaryKey(Object entity,"
						+ " Object primaryKey)实体名称" + entityName + "主键" + pks);
				returnEntity = hibernateSession.get(entityName, pks);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			logger.error("GiantBaseDao根据主键获取实体"
					+ "getEntityByPrimaryKey(Object entity,"
					+ " Object primaryKey)实体名称" + entityName + "主键"
					+ primaryKey + "发生异常" + e.getMessage());
			return null;
		} finally {
			try {
				this.releaseSession(hibernateSession);
			} catch (Exception exc) {
				logger.error("GiantBaseDao根据主键获取实体"
						+ "getEntityByPrimaryKey(Object entity,"
						+ " Object primaryKey)实体名称" + entityName + "主键"
						+ primaryKey + "关闭HibernateSession发生异常"
						+ exc.getMessage());
			}
		}
		logger.debug("GiantBaseDao根据主键获取实体"
				+ "getEntityByPrimaryKey(Object entity,"
				+ " Object primaryKey)结束");
		return returnEntity;
	}

	/**
	 * 根据条件采用HQL语句获取实体
	 * 
	 * @author 娄杰
	 * 
	 * @param entityName
	 *            实体的类名(String类型)
	 * @param conditionsMap
	 *            条件
	 * 
	 * @return Object 实体对象
	 */
	public Object getEntityByHQL(String entityName,
			Map<String, Object> conditionMap) {
		// 获取已经处理好的Query实体并获取List列表
		List<?> listEntity = null;
		// 利用父类里面的方法获取Session实例
		Session hibernateSession = this.getSession();
		try {
			logger.debug("GiantBaseDao根据条件以HQL方式获取实体"
					+ "getEntityByHQL(String entityName,"
					+ "Map<String, Object> conditionMap)实体名称" + entityName
					+ "条件" + conditionMap);
			listEntity = this.getHQLQuery(entityName, conditionMap,
					hibernateSession).list();
			// 获取符合条件的实体
			if (listEntity != null && listEntity.size() >= 1) {
				return listEntity.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			logger.error("GiantBaseDao根据条件以HQL方式获取实体"
					+ "getEntityByHQL(String entityName,"
					+ "Map<String, Object> conditionMap)实体名称" + entityName
					+ "条件" + conditionMap + "发现异常" + e.getMessage());
			return null;
		} finally {
			// 关闭hibernateSession对象
			try {
				this.releaseSession(hibernateSession);
			} catch (Exception exc) {
				logger.error("GiantBaseDao根据条件以HQL方式获取实体"
						+ "getEntityByHQL(String entityName,"
						+ "Map<String, Object> conditionMap)实体名称" + entityName
						+ "条件" + conditionMap + "关闭HibernateSession发现异常"
						+ exc.getMessage());
			}
		}
		return null;
	}

	/**
	 * 采用HQL语句获取相应的实体列表
	 * 
	 * @author 娄杰
	 * 
	 * @param entityName
	 *            实体的类名
	 * @param conditionMap
	 *            条件
	 * 
	 * @return List<Object>实体对象列表
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getEntityListByHQL(String entityName,
			Map<String, Object> conditionMap) {
		logger.debug("GiantBaseDao根据条件以HQL方式获取实体列表"
				+ "getEntityListByHQL(String entityName,"
				+ "Map<String, Object> conditionMap)开始");
		// 获取已经处理好的Query实体并获取List列表
		List<Object> listEntity = null;
		// 利用父类里面的方法获取Session实例
		Session hibernateSession = this.getSession();
		try {
			logger.debug("GiantBaseDao根据条件以HQL方式获取实体列表"
					+ "getEntityListByHQL(String entityName,"
					+ "Map<String, Object> conditionMap)实体名称" + entityName
					+ "条件" + conditionMap);
			listEntity = this.getHQLQuery(entityName, conditionMap,
					hibernateSession).list();
			// 获取符合条件的实体
			if (listEntity != null && listEntity.size() > 0) {
				return listEntity;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			logger.error("GiantBaseDao根据条件以HQL方式获取实体列表"
					+ "getEntityListByHQL(String entityName,"
					+ "Map<String, Object> conditionMap)实体名称" + entityName
					+ "条件" + conditionMap + "出现异常" + e.getMessage());
			return null;
		} finally {
			// 关闭hibernateSession对象
			try {
				this.releaseSession(hibernateSession);
			} catch (Exception exc) {
				logger.error("GiantBaseDao根据条件以HQL方式获取实体列表"
						+ "getEntityListByHQL(String entityName,"
						+ "Map<String, Object> conditionMap)实体名称" + entityName
						+ "条件" + conditionMap + "关闭HinbernateSession出现异常"
						+ exc.getMessage());
			}
		}
		logger.debug("GiantBaseDao根据条件以HQL方式获取实体列表"
				+ "getEntityListByHQL(String entityName,"
				+ "Map<String, Object> conditionMap)结束");
		return null;
	}

	/**
	 * 采用HQL语句获取相应的实体列表
	 * 
	 * @author 娄杰
	 * 
	 * @param entityName
	 *            实体的类名
	 * @param conditionMap
	 *            条件
	 * 
	 * @return List<Object>实体对象列表
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getNewEntityListByHQL(String entityName,
			Map<String, Object> conditionMap) {
		logger.debug("GiantBaseDao根据条件以HQL方式获取实体列表"
				+ "getEntityListByHQL(String entityName,"
				+ "Map<String, Object> conditionMap)开始");
		// 获取已经处理好的Query实体并获取List列表
		List<Object> listEntity = null;
		// 利用父类里面的方法获取Session实例
		Session hibernateSession = this.getSession();
		try {
			logger.debug("GiantBaseDao根据条件以HQL方式获取实体列表"
					+ "getEntityListByHQL(String entityName,"
					+ "Map<String, Object> conditionMap)实体名称" + entityName
					+ "条件" + conditionMap);
			listEntity = this.getNewHQLQuery(entityName, conditionMap,
					hibernateSession).list();
			// 获取符合条件的实体
			if (listEntity != null && listEntity.size() > 0) {
				return listEntity;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			logger.error("GiantBaseDao根据条件以HQL方式获取实体列表"
					+ "getEntityListByHQL(String entityName,"
					+ "Map<String, Object> conditionMap)实体名称" + entityName
					+ "条件" + conditionMap + "出现异常" + e.getMessage());
			return null;
		} finally {
			// 关闭hibernateSession对象
			try {
				this.releaseSession(hibernateSession);
			} catch (Exception exc) {
				logger.error("GiantBaseDao根据条件以HQL方式获取实体列表"
						+ "getEntityListByHQL(String entityName,"
						+ "Map<String, Object> conditionMap)实体名称" + entityName
						+ "条件" + conditionMap + "关闭HinbernateSession出现异常"
						+ exc.getMessage());
			}
		}
		logger.debug("GiantBaseDao根据条件以HQL方式获取实体列表"
				+ "getEntityListByHQL(String entityName,"
				+ "Map<String, Object> conditionMap)结束");
		return null;
	}

	/**
	 * 根据条件以SQL方式查询对象列表
	 * 
	 * @author 娄杰
	 * 
	 * @param querySql
	 *            查询语句
	 * @param conditionMap
	 *            条件
	 * 
	 * @return List<Object>实体对象列表
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getObjectListBySQL(String querySql,
			Map<String, Object> conditionMap) {
		logger.debug("GiantBaseDao根据条件以SQL方式获取实体列表"
				+ "getEntityListByHQL(String querySql,"
				+ "Map<String, Object> conditionMap)开始");
		// 获取已经处理好的SQLQuery实体并获取List列表
		List<Object> listEntity = null;
		// 利用父类里面的方法获取Session实例
		Session hibernateSession = this.getSession();
		try {
			logger.debug("GiantBaseDao根据条件以SQL方式获取实体列表"
					+ "getEntityListByHQL(String querySql,"
					+ "Map<String, Object> conditionMap)SQL语句" + querySql
					+ "条件" + conditionMap);
			listEntity = this.getSQLQuery(querySql, conditionMap,
					hibernateSession).list();
			// 返回符合条件的实体
			if (listEntity != null && listEntity.size() > 0) {
				return listEntity;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			logger.error("GiantBaseDao根据条件以SQL方式获取实体列表"
					+ "getEntityListByHQL(String querySql,"
					+ "Map<String, Object> conditionMap)SQL语句" + querySql
					+ "条件" + conditionMap + "出现异常" + e.getMessage());
			return null;
		} finally {
			// 关闭hibernateSession对象
			try {
				this.releaseSession(hibernateSession);
			} catch (Exception exc) {
				logger.error("GiantBaseDao根据条件以SQL方式获取实体列表"
						+ "getEntityListByHQL(String querySql,"
						+ "Map<String, Object> conditionMap)SQL语句" + querySql
						+ "条件" + conditionMap + "关闭HinbernateSession出现异常"
						+ exc.getMessage());
			}
		}
		logger.debug("GiantBaseDao根据条件以SQL方式获取实体列表"
				+ "getEntityListByHQL(String querySql,"
				+ "Map<String, Object> conditionMap)结束");
		return null;
	}

	/**
	 * 获取指定条件的Map列表
	 * 
	 * @author 娄杰
	 * 
	 * @param querySql
	 *            查询语句
	 * @param conditionMap
	 *            条件
	 * 
	 * @return List&lt;Map&lt;String,Object&gt;&gt;（Map对象列表）
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getMapListBySQL(String querySql, Map<String, Object> conditionMap) {
		logger.debug("GiantBaseDao根据条件以SQL方式获取Map列表getMapListBySQL(String querySql, Map<String, Object> conditionMap)开始");
		// 获取已经处理好的SQLQuery实体并获取MapList列表
		List<Map<String, Object>> listMapEntity = null;
		// 利用父类里面的方法获取Session实例
		Session hibernateSession = this.getSession();
		try {
			logger.debug("GiantBaseDao根据条件以SQL方式获取Map列表"
					+ "getMapListBySQL(String querySql,"
					+ "Map<String, Object> conditionMap)SQL语句" + querySql
					+ "条件" + conditionMap);
			listMapEntity = this.getSQLQuery(querySql, conditionMap, hibernateSession)
					.setResultTransformer(org.hibernate.transform.Transformers.ALIAS_TO_ENTITY_MAP).list();
			if (listMapEntity != null && listMapEntity.size() > 0) {
				return listMapEntity;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			logger.error("GiantBaseDao根据条件以SQL方式获取Map列表"
					+ "getMapListBySQL(String querySql,"
					+ "Map<String, Object> conditionMap)SQL语句" + querySql
					+ "条件" + conditionMap + "出现异常" + e.getMessage());
			return null;
		} finally {
			try {
				this.releaseSession(hibernateSession);
			} catch (Exception exc) {
				logger.error("GiantBaseDao根据条件以SQL方式获取Map列表"
						+ "getMapListBySQL(String querySql,"
						+ "Map<String, Object> conditionMap)SQL语句" + querySql
						+ "条件" + conditionMap + "关闭HibernateSession出现异常"
						+ exc.getMessage());
			}
		}
		logger.debug("GiantBaseDao根据条件以SQL方式获取Map列表"
				+ "getMapListBySQL(String querySql,"
				+ "Map<String, Object> conditionMap)SQL语句" + querySql + "条件"
				+ conditionMap + "结束");
		return null;
	}

	/**
	 * 获取Map的列表
	 * 
	 * @author Prince
	 * 
	 * @param querySql
	 *            查询语句
	 * @param conditionMap
	 *            条件
	 * 
	 * @return List<Map<String,Object>> Map对象列表
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getMapListByHQL(String queryHql,
			Map<String, Object> conditionMap) {
		// 获取已经处理好的SQLQuery实体并获取MapList列表
		List<Map<String, Object>> listMapEntity = null;
		// 利用父类里面的方法获取Session实例
		Session hibernateSession = this.getSession();
		try {
			logger.debug("GiantBaseDao根据条件以SQL方式获取Map列表"
					+ "getMapListBySQL(String querySql,"
					+ "Map<String, Object> conditionMap)SQL语句" + queryHql
					+ "条件" + conditionMap);
			listMapEntity = this
					.getHQLQueryByHql(queryHql, conditionMap, hibernateSession)
					.setResultTransformer(
							org.hibernate.transform.Transformers.ALIAS_TO_ENTITY_MAP)
					.list();
			if (listMapEntity != null && listMapEntity.size() > 0) {
				return listMapEntity;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("GiantBaseDao根据条件以SQL方式获取Map列表"
					+ "getMapListBySQL(String querySql,"
					+ "Map<String, Object> conditionMap)SQL语句" + queryHql
					+ "条件" + conditionMap + "出现异常" + e.getMessage());
			return null;
		} finally {
			try {
				this.releaseSession(hibernateSession);
			} catch (Exception exc) {
				logger.error("GiantBaseDao根据条件以SQL方式获取Map列表"
						+ "getMapListBySQL(String querySql,"
						+ "Map<String, Object> conditionMap)SQL语句" + queryHql
						+ "条件" + conditionMap + "关闭HibernateSession出现异常"
						+ exc.getMessage());
			}
		}
		return null;
	}

	/**
	 * 根据条件查询出一页的信息
	 * 
	 * @author 娄杰
	 * 
	 * @param querySql
	 *            查询语句
	 * @param currentPageIndex
	 *            当前是第几页
	 * @param showCounts
	 *            一页展示几条数据
	 * @param conditionMap
	 *            查询的条件
	 * 
	 * @return GiantPage 分页实体对象
	 */
	public GiantPager getPage(String querySql, int currentPageIndex,
			int showCounts, Map<String, Object> conditionMap) {
		logger.debug("GiantBaseDao根据条件以SQL方式获取Page对象"
				+ "getGiantPage(String querySql, int currentPageIndex,"
				+ "int showCounts, Map<String, Object> conditionMap)开始");
		// 实例化Page对象
		GiantPager giantPage = new GiantPager();
		// 利用父类里面的方法获取Session实例
		Session hibernateSession = this.getSession();
		// 获取已经处理好的SQLQuery实体
		SQLQuery sqlQuery = null;
		// 分页获取List实体
		List<?> listEntity = null;
		try {
			logger.debug("GiantBaseDao根据条件以SQL方式获取Page对象"
					+ "getGiantPage(String querySql, int currentPageIndex,"
					+ "int showCounts, Map<String, Object> conditionMap)SQL语句"
					+ querySql + "当前第" + currentPageIndex + "页,每页显示"
					+ showCounts + "条数据,条件" + conditionMap);
			sqlQuery = this.getSQLQuery(querySql, conditionMap,
					hibernateSession);
			if (sqlQuery == null) {
				return giantPage;
			}
			listEntity = sqlQuery
					.setFirstResult((currentPageIndex - 1) * showCounts)
					.setMaxResults(showCounts)
					.setResultTransformer(
							org.hibernate.transform.Transformers.ALIAS_TO_ENTITY_MAP)
					.list();
			// 判断是否为空并且长度大于0
			if (listEntity == null || listEntity.size() == 0) {
				// 当前页面的页数必须大于1
				if (currentPageIndex > 1) {
					List<?> listObject = sqlQuery
							.setFirstResult((currentPageIndex - 2) * showCounts)
							.setMaxResults(showCounts)
							.setResultTransformer(
									org.hibernate.transform.Transformers.ALIAS_TO_ENTITY_MAP)
							.list();
					if (listObject != null && listObject.size() > 0) {
						giantPage.setPageResult(listObject);
						giantPage.setCurrentPage(currentPageIndex - 1);
						giantPage.setPageSize(showCounts);
					} else {/* 给分页的GiantPage赋值 */
						giantPage.setPageResult(null);
					}
				} else {/* 给分页的GiantPage赋值 */
					giantPage.setPageResult(null);
				}
			} else {/* 给分页的GiantPage赋值 */
				giantPage.setPageResult(listEntity);
				giantPage.setCurrentPage(currentPageIndex);
				giantPage.setPageSize(showCounts);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			logger.error("GiantBaseDao根据条件以SQL方式获取Page对象"
					+ "getGiantPage(String querySql, int currentPageIndex,"
					+ "int showCounts, Map<String, Object> conditionMap)SQL语句"
					+ querySql + "当前第" + currentPageIndex + "页,每页显示"
					+ showCounts + "条数据,条件" + conditionMap + "执行该方法过程中出现异常"
					+ e.getMessage());
			return giantPage;
		} finally {
			// 关闭hibernateSession对象
			try {
				this.releaseSession(hibernateSession);
			} catch (Exception exc) {
				logger.error("GiantBaseDao根据条件以SQL方式获取GiantPage对象"
						+ "getGiantPage(String querySql, int currentPageIndex,"
						+ "int showCounts, Map<String, Object> conditionMap)SQL语句"
						+ querySql + "当前第" + currentPageIndex + "页,每页显示"
						+ showCounts + "条数据,条件" + conditionMap
						+ "关闭HibernateSession出现异常" + exc.getMessage());
			}
		}
		logger.debug("GiantBaseDao根据条件以SQL方式获取GiantPage对象"
				+ "getGiantPage(String querySql, int currentPageIndex,"
				+ "int showCounts, Map<String, Object> conditionMap)结束");
		return giantPage;
	}

	/**
	 * 获取记录总个数
	 * 
	 * @author 娄杰
	 * 
	 * @param queryCountSql
	 *            sql语句
	 * @param conditionsMap
	 *            条件
	 * @return Integer
	 */
	public Integer getGiantCounts(String queryCountSql,
			Map<String, Object> conditionMap) {
		Object object = getObjectBySQL(queryCountSql, conditionMap);
		try {
			if (object != null) {
				return Integer.valueOf(object.toString());
			}
		} catch (Exception e) {
			return 0;
		}
		return 0;
	}
	/**
	 * 新分页:同百度分页效果<i>首页 上一页 1 2 3 4 5 下一页<i><br>
	 * writer: XiaXiaobing <br>
	 * rq: 2014-2-26下午8:11:21<br>
	 * @param querySql
	 * @param currentPageIndex
	 * @param showCounts
	 * @param conditionMap
	 * @param totalCounts
	 * @return
	 */
	public GiantPager getPageNew(String querySql, int currentPageIndex,
			int showCounts, Map<String, Object> conditionMap,int totalCounts) {
		if (currentPageIndex > totalCounts || currentPageIndex < 1) {
			currentPageIndex = 1;
		}
		logger.debug("GiantBaseDao根据条件以SQL方式获取Page对象"
				+ "getGiantPage(String querySql, int currentPageIndex,"
				+ "int showCounts, Map<String, Object> conditionMap)开始");
		// 实例化Page对象
		GiantPager giantPage = new GiantPager();
		// 利用父类里面的方法获取Session实例
		Session hibernateSession = this.getSession();
		// 获取已经处理好的SQLQuery实体
		SQLQuery sqlQuery = null;
		// 分页获取List实体
		List<?> listEntity = null;
		try {
			logger.debug("GiantBaseDao根据条件以SQL方式获取Page对象"
					+ "getGiantPage(String querySql, int currentPageIndex,"
					+ "int showCounts, Map<String, Object> conditionMap)SQL语句"
					+ querySql + "当前第" + currentPageIndex + "页,每页显示"
					+ showCounts + "条数据,条件" + conditionMap);
			sqlQuery = this.getSQLQuery(querySql, conditionMap,
					hibernateSession);
			if (sqlQuery == null) {
				return giantPage;
			}
			listEntity = sqlQuery
					.setFirstResult((currentPageIndex - 1) * showCounts)
					.setMaxResults(showCounts)
					.setResultTransformer(
							org.hibernate.transform.Transformers.ALIAS_TO_ENTITY_MAP)
					.list();
			// 判断是否为空并且长度大于0
			if (listEntity == null || listEntity.size() == 0) {
				// 当前页面的页数必须大于1
				if (currentPageIndex > 1) {
					List<?> listObject = sqlQuery
							.setFirstResult((currentPageIndex - 2) * showCounts)
							.setMaxResults(showCounts)
							.setResultTransformer(
									org.hibernate.transform.Transformers.ALIAS_TO_ENTITY_MAP)
							.list();
					if (listObject != null && listObject.size() > 0) {
						giantPage.setPageResult(listObject);
						giantPage.setCurrentPage(currentPageIndex - 1);
						giantPage.setPageSize(showCounts);
						giantPage=setGiantPage(giantPage,totalCounts);
					} else {/* 给分页的GiantPage赋值 */
						giantPage.setPageResult(null);
					}
				} else {/* 给分页的GiantPage赋值 */
					giantPage.setPageResult(null);
				}
			} else {/* 给分页的GiantPage赋值 */
				giantPage.setPageResult(listEntity);
				giantPage.setCurrentPage(currentPageIndex);
				giantPage.setPageSize(showCounts);
				giantPage=setGiantPage(giantPage,totalCounts);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			logger.error("GiantBaseDao根据条件以SQL方式获取Page对象"
					+ "getGiantPage(String querySql, int currentPageIndex,"
					+ "int showCounts, Map<String, Object> conditionMap)SQL语句"
					+ querySql + "当前第" + currentPageIndex + "页,每页显示"
					+ showCounts + "条数据,条件" + conditionMap + "执行该方法过程中出现异常"
					+ e.getMessage());
			return giantPage;
		} finally {
			// 关闭hibernateSession对象
			try {
				this.releaseSession(hibernateSession);
			} catch (Exception exc) {
				logger.error("GiantBaseDao根据条件以SQL方式获取GiantPage对象"
						+ "getGiantPage(String querySql, int currentPageIndex,"
						+ "int showCounts, Map<String, Object> conditionMap)SQL语句"
						+ querySql + "当前第" + currentPageIndex + "页,每页显示"
						+ showCounts + "条数据,条件" + conditionMap
						+ "关闭HibernateSession出现异常" + exc.getMessage());
			}
		}
		logger.debug("GiantBaseDao根据条件以SQL方式获取GiantPage对象"
				+ "getGiantPage(String querySql, int currentPageIndex,"
				+ "int showCounts, Map<String, Object> conditionMap)结束");
		return giantPage;
	}
	/** 分页设置<br>
	 * writer: XiaXiaobing <br>
	 * rq: 2014-2-26下午8:38:19<br>
	 * @param giantPage
	 * @param totalCounts
	 * @return
	 */
	private GiantPager setGiantPage(GiantPager giantPage, int totalCounts) {
		giantPage.setTotalCounts(totalCounts);
		giantPage.setTotalPages((totalCounts+giantPage.getPageSize()-1)/giantPage.getPageSize());
		if (giantPage.getTotalPages() <= 9) {
			giantPage.setBeginPage(1);
			giantPage.setEndPage(giantPage.getTotalPages());
		} else {// 总页码>10
			giantPage.setBeginPage(giantPage.getCurrentPage()-4);
			giantPage.setEndPage(giantPage.getCurrentPage()+4);
			
			if (giantPage.getBeginPage() < 1) {
				giantPage.setBeginPage(1);
				giantPage.setEndPage(9);
			} else if (giantPage.getEndPage() > giantPage.getTotalPages()) {
				giantPage.setBeginPage(giantPage.getTotalPages()-8);
				giantPage.setEndPage(giantPage.getTotalPages());//endPageIndex = pageCount;
			}
		}
		return giantPage;
	}

	/**
	 * 根据语句获取有且仅有的一个值
	 * 
	 * @author 娄杰
	 * 
	 * @param querySql
	 *            查询语句
	 * @param conditionMap
	 *            条件
	 * 
	 * @return Object 实体对象
	 */
	public Object getObjectBySQL(String querySql,
			Map<String, Object> conditionMap) {
		logger.debug("GiantBaseDao根据条件以SQL方式获取对象"
				+ "getEntityBySQL(String querySql,"
				+ "Map<String, Object> conditionMap)开始");
		// 获取处理好的SQLQuery实体并获取列表
		List<?> listEntity = null;
		// 利用父类里面的方法获取Session实例
		Session hibernateSession = this.getSession();
		try {
			logger.debug("GiantBaseDao根据条件以SQL方式获取对象"
					+ "getEntityBySQL(String querySql,"
					+ "Map<String, Object> conditionMap)SQL语句" + querySql
					+ "条件" + conditionMap);
			listEntity = this.getSQLQuery(querySql, conditionMap,
					hibernateSession).list();
			// 判断是否符合条件
			if (listEntity != null && listEntity.size() == 1) {
				return listEntity.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			logger.error("GiantBaseDao根据条件以SQL方式获取对象"
					+ "getEntityBySQL(String querySql,"
					+ "Map<String, Object> conditionMap)SQL语句" + querySql
					+ "条件" + conditionMap + "执行该方法过程中出现异常" + e.getMessage());
			return null;
		} finally {
			// 关闭hibernateSession对象
			try {
				this.releaseSession(hibernateSession);
			} catch (Exception exc) {
				logger.error("GiantBaseDao根据条件以SQL方式获取对象"
						+ "getEntityBySQL(String querySql,"
						+ "Map<String, Object> conditionMap)SQL语句" + querySql
						+ "条件" + conditionMap + "关闭HibernateSession出现异常"
						+ exc.getMessage());
			}
		}
		logger.debug("GiantBaseDao根据条件以SQL方式获取对象"
				+ "getEntityBySQL(String querySql,"
				+ "Map<String, Object> conditionMap)结束");
		return null;
	}

	/**
	 * 保存单个对象
	 * 
	 * @param obj
	 * @return
	 */
	public boolean saveOne(Object obj) {
		// 利用私用方法获取hibernateSession实例
		// 利用父类里面的方法获取Session实例
		Session hibernateSession = this.getSession();
		// 定义返回值
		Boolean tag = true;
		// 开启事物
		Transaction transaction = hibernateSession.beginTransaction();
		try {
			if (obj != null) {
				// 判断是否保存或更新该实体
				hibernateSession.save(obj);
			}
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			// 回滚事物
			transaction.rollback();
			// 改变返回值
			tag = false;
		} finally {
			// 关闭hibernateSession对象
			try {
				this.releaseSession(hibernateSession);
			} catch (Exception exc) {
			}
		}
		return tag;
	}

	/**
	 * 保存日志对象
	 * 
	 * @param obj
	 * @return
	 */
	public boolean saveLog(Object obj) {
		// 利用私用方法获取hibernateSession实例
		// 利用父类里面的方法获取Session实例
		Session hibernateSession = this.getSession();
		// 定义返回值
		Boolean tag = true;
		// 开启事物
		Transaction transaction = hibernateSession.beginTransaction();
		try {
			if (obj != null) {
				// 判断是否保存或更新该实体
				hibernateSession.save(obj);
			}
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			// 回滚事物
			transaction.rollback();
			// 改变返回值
			tag = false;
		} finally {
			// 关闭hibernateSession对象
			try {
				this.releaseSession(hibernateSession);
			} catch (Exception exc) {
			}
		}
		return tag;
	}

	/**
	 * 更新单个对象
	 * 
	 * @param obj
	 * @return
	 */
	public boolean updateOne(Object obj) {
		// 利用私用方法获取hibernateSession实例
		// 利用父类里面的方法获取Session实例
		Session hibernateSession = this.getSession();
		// 定义返回值
		Boolean tag = true;
		// 开启事物
		Transaction transaction = hibernateSession.beginTransaction();
		try {
			if (obj != null) {
				// 判断是否保存或更新该实体
				hibernateSession.update(obj);
			}
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			// 回滚事物
			transaction.rollback();
			// 改变返回值
			tag = false;
		} finally {
			// 关闭hibernateSession对象
			try {
				this.releaseSession(hibernateSession);
			} catch (Exception exc) {
			}
		}
		return tag;
	}

	/**
	 * 保存更新单个对象
	 * 
	 * @param obj
	 * @return
	 */
	public boolean saveOrUpdateOne(Object obj) {
		// 利用私用方法获取hibernateSession实例
		// 利用父类里面的方法获取Session实例
		Session hibernateSession = this.getSession();
		// 定义返回值
		Boolean tag = true;
		// 开启事物
		Transaction transaction = hibernateSession.beginTransaction();
		try {
			if (obj != null) {
				// 判断是否保存或更新该实体
				hibernateSession.saveOrUpdate(obj);
			}
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			// 回滚事物
			transaction.rollback();
			// 改变返回值
			tag = false;
		} finally {
			// 关闭hibernateSession对象
			try {
				this.releaseSession(hibernateSession);
			} catch (Exception exc) {
			}
		}
		return tag;
	}

	/**
	 * 保存更新日志对象
	 * 
	 * @param obj
	 * @return
	 */
	public boolean saveOrUpdateLog(Object obj) {
		// 利用私用方法获取hibernateSession实例
		// 利用父类里面的方法获取Session实例
		Session hibernateSession = this.getSession();
		// 定义返回值
		Boolean tag = true;
		// 开启事物
		Transaction transaction = hibernateSession.beginTransaction();
		try {
			if (obj != null) {
				// 判断是否保存或更新该实体
				hibernateSession.saveOrUpdate(obj);
			}
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			// 回滚事物
			transaction.rollback();
			// 改变返回值
			tag = false;
		} finally {
			// 关闭hibernateSession对象
			try {
				this.releaseSession(hibernateSession);
			} catch (Exception exc) {
			}
		}
		return tag;
	}

	/**
	 * 删除单个对象
	 * 
	 * @param obj
	 * @return
	 */
	public boolean deleteOne(Object obj) {
		// 利用私用方法获取hibernateSession实例
		// 利用父类里面的方法获取Session实例
		Session hibernateSession = this.getSession();
		// 定义返回值
		Boolean tag = true;
		// 开启事物
		hibernateSession.beginTransaction();
		try {
			if (obj != null) {
				// 删除该对象
				hibernateSession.delete(obj);
			}
			hibernateSession.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			// 回滚事物
			hibernateSession.getTransaction().rollback();
			// 改变返回值
			tag = false;
		} finally {
			// 关闭hibernateSession对象
			try {
				this.releaseSession(hibernateSession);
			} catch (Exception exc) {
			}
		}
		return tag;
	}

	/**
	 * 保存，修改，删除实体
	 * 
	 * @author 娄杰
	 * 
	 * @param saveUpdateEntity
	 *            要保存或修改的实体（单个或多个）
	 * @param deleteEntity
	 *            要删除的实体（单个或多个）
	 * 
	 * @return Boolean True成功 False失败
	 */
	@SuppressWarnings("unchecked")
	public Boolean saveUpdateOrDelete(Object saveUpdateEntity, Object deleteEntity) {
		// 利用私用方法获取hibernateSession实例
		// 利用父类里面的方法获取Session实例
		Session hibernateSession = this.getSession();
		// 开启事物
		Transaction transaction = hibernateSession.beginTransaction();
		try {
			if (saveUpdateEntity != null) {
				// 判断是否保存或更新该实体
				if (saveUpdateEntity instanceof List) {
					if (((List<Object>) saveUpdateEntity).size() > 0) {
						for (Object obj : (List<Object>) saveUpdateEntity) {
							hibernateSession.saveOrUpdate(obj);
						}
					}
				} else {
					hibernateSession.saveOrUpdate(saveUpdateEntity);
				}
			}
			// 判断是否删除该实体
			if (deleteEntity != null) {
				if (deleteEntity instanceof List) {
					if (((List<Object>) deleteEntity).size() > 0) {
						for (Object obj : (List<Object>) deleteEntity) {
							hibernateSession.delete(obj);
						}
					}
				} else
					hibernateSession.delete(deleteEntity);
			}
			// 提交事物
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			// 回滚事物
			transaction.rollback();
			// 改变返回值
			return false;
		} finally {
			// 关闭hibernateSession对象
			try {
				this.releaseSession(hibernateSession);
			} catch (Exception exc) {
			}
		}
		return true;
	}

	/**
	 * 保存，修改，删除日志实体
	 * 
	 * @param saveUpdateEntity
	 *            要保存或修改的实体（单个或多个）
	 * @param deleteEntity
	 *            要删除的实体（单个或多个）
	 * 
	 * @return Boolean True成功 False失败
	 */
	@SuppressWarnings("unchecked")
	public Boolean saveUpdateOrDeleteLog(Object saveUpdateEntity, Object deleteEntity) {
		// 利用私用方法获取hibernateSession实例
		// 利用父类里面的方法获取Session实例
		Session hibernateSession = this.getSession();
		// 开启事物
		Transaction transaction = hibernateSession.beginTransaction();
		try {
			if (saveUpdateEntity != null) {
				// 判断是否保存或更新该实体
				if (saveUpdateEntity instanceof List) {
					if (((List<Object>) saveUpdateEntity).size() > 0) {
						for (Object obj : (List<Object>) saveUpdateEntity) {
							hibernateSession.saveOrUpdate(obj);
						}
					}
				} else {
					hibernateSession.saveOrUpdate(saveUpdateEntity);
				}
			}
			// 判断是否删除该实体
			if (deleteEntity != null) {
				if (deleteEntity instanceof List) {
					if (((List<Object>) deleteEntity).size() > 0) {
						for (Object obj : (List<Object>) deleteEntity) {
							hibernateSession.delete(obj);
						}
					}
				} else
					hibernateSession.delete(deleteEntity);
			}
			// 提交事物
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			// 回滚事物
			transaction.rollback();
			// 改变返回值
			return false;
		} finally {
			// 关闭hibernateSession对象
			try {
				this.releaseSession(hibernateSession);
			} catch (Exception exc) {
			}
		}
		return true;
	}

	/**
	 * 获取唯一实体
	 * 
	 * @param querySQL
	 *            SQL查询语句
	 * @param whereMap
	 *            条件
	 * @param objClass
	 *            Object.class
	 * @return Object,唯一实体
	 */
	@SuppressWarnings("unchecked")
	public Object uniqueEntityBySQL(String querySQL,
			Map<String, Object> whereMap, Class<?> objClass) {
		logger.debug("GiantBaseDao根据条件以SQL方式获取指定对象"
				+ "getEntityListBySQL(String querySql,"
				+ "Map<String, Object> conditionMap, Object objItem开始");
		Session hibernateSession = this.getSession();
		try {
			List<Object> listObject = null;
			listObject = getSQLQuery(querySQL, whereMap, hibernateSession)
					.addEntity(objClass).list();
			if (listObject == null || listObject.size() == 0
					|| listObject.size() > 1)
				return null;
			return listObject.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			logger.error("GiantBaseDao根据条件以SQL方式获取指定对象"
					+ "getEntityListBySQL(String querySql,"
					+ "Map<String, Object> conditionMap, Object objItem"
					+ "执行过程中出现异常" + e.getMessage());
			return null;
		} finally {
			// 关闭hibernateSession对象
			try {
				this.releaseSession(hibernateSession);
			} catch (Exception exc) {
				logger.error("GiantBaseDao根据条件以SQL方式获取指定对象"
						+ "getEntityListBySQL(String querySql,"
						+ "Map<String, Object> conditionMap, Object objItem"
						+ "关闭HibernateSession出现异常" + exc.getMessage());
			}
		}
	}

	/**
	 * 利用SQL语句获取实体
	 * 
	 * @author 娄杰
	 * 
	 * @param querySql
	 *            SQL语句
	 * @param conditionMap
	 *            条件
	 * @param objItem
	 *            返回指定实体[需要实例化即new Object()]
	 * 
	 * @return List<Object> 实体对象列表
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getEntityListBySQL(String querySql,
			Map<String, Object> conditionMap, Object objItem) {
		logger.debug("GiantBaseDao根据条件以SQL方式获取指定对象"
				+ "getEntityListBySQL(String querySql,"
				+ "Map<String, Object> conditionMap, Object objItem开始");
		Session hibernateSession = this.getSession();
		try {
			List<Object> listObject = null;
			listObject = getSQLQuery(querySql, conditionMap, hibernateSession)
					.addEntity(objItem.getClass()).list();
			if (listObject == null || listObject.size() == 0)
				return null;
			return listObject;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			logger.error("GiantBaseDao根据条件以SQL方式获取指定对象"
					+ "getEntityListBySQL(String querySql,"
					+ "Map<String, Object> conditionMap, Object objItem"
					+ "执行过程中出现异常" + e.getMessage());
			return null;
		} finally {
			// 关闭hibernateSession对象
			try {
				this.releaseSession(hibernateSession);
			} catch (Exception exc) {
				logger.error("GiantBaseDao根据条件以SQL方式获取指定对象"
						+ "getEntityListBySQL(String querySql,"
						+ "Map<String, Object> conditionMap, Object objItem"
						+ "关闭HibernateSession出现异常" + exc.getMessage());
			}
		}
	}

	/**
	 * 
	 * @param hql
	 * @param whereMap
	 * @return
	 */
	public Object uniqueEntityByHql(String hql, Map<String, Object> whereMap) {
		// 利用父类里面的方法获取Session实例
		Session hibernateSession = this.getSession();
		try {
			// 获取查询信息
			Query query = this
					.getHQLQueryByHql(hql, whereMap, hibernateSession);
			// 获取唯一结果
			return query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			try {
				releaseSession(hibernateSession);
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * 获取唯一Map信息
	 * 
	 * @param querySql
	 * @param whereMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> uniqueMapBySql(String querySql,
			Map<String, Object> whereMap) {
		// 利用父类里面的方法获取Session实例
		Session hibernateSession = this.getSession();
		try {
			Map<String, Object> returnMap = (Map<String, Object>) this
					.getSQLQuery(querySql, whereMap, hibernateSession)
					.setResultTransformer(
							org.hibernate.transform.Transformers.ALIAS_TO_ENTITY_MAP)
					.uniqueResult();
			return returnMap;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			try {
				releaseSession(hibernateSession);
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * 
	 * @param hql
	 * @param conditionMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object> listEntityByHql(String hql,
			Map<String, Object> conditionMap) {
		// 利用父类里面的方法获取Session实例
		Session hibernateSession = this.getSession();
		try {
			// 获取查询信息
			Query query = this.getHQLQueryByHql(hql, conditionMap,
					hibernateSession);
			// 获取唯一结果
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			try {
				releaseSession(hibernateSession);
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * 
	 * @param hql
	 * @param conditionMap
	 * @return
	 */
	public List<?> listEntityByHqlLimit(String hql,
			Map<String, Object> conditionMap, int limits) {
		// 利用父类里面的方法获取Session实例
		Session hibernateSession = this.getSession();
		try {
			// 获取查询信息
			Query query = this.getHQLQueryByHql(hql, conditionMap,
					hibernateSession);
			query.setMaxResults(limits);
			query.setFirstResult(0);
			// 获取唯一结果
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			try {
				releaseSession(hibernateSession);
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * 根据语句获取有且仅有的一个值
	 * 
	 * @author 娄杰
	 * 
	 * @param querySql
	 *            查询语句
	 * @param conditionMap
	 *            条件
	 * @return 要获取的实体
	 */
	public Object getSingleDataBySql(String querySql,
			Map<String, Object> conditionMap) {
		// 获取处理好的SQLQuery实体并获取列表
		List<?> listEntity = null;
		// 利用父类里面的方法获取Session实例
		Session hibernateSession = this.getSession();
		try {
			listEntity = this.getSQLQuery(querySql, conditionMap,
					hibernateSession).list();
			// 判断是否符合条件
			if (listEntity != null && listEntity.size() == 1) {
				return listEntity.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			// 关闭hibernateSession对象
			try {
				releaseSession(hibernateSession);
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		}
		// 当无符合条件的时候返回null
		return null;
	}

	/**
	 * 获取已添加过查询参数的SQLQuery实体
	 * 
	 * @param sql
	 *            SQL语句
	 * @param condition
	 *            查询条件/参数
	 * @param hibernateSession
	 *            HibernateSession对象
	 * @return SQLQuery对象
	 */
	private SQLQuery getSQLQuery(String sql, Map<String, Object> condition, Session hibernateSession) {
		SQLQuery query = hibernateSession.createSQLQuery(sql);

		if (condition != null && condition.size() > 0) {
			for (Map.Entry<String, Object> en : condition.entrySet()) {
				Object obj = en.getValue();
				if (obj instanceof Collection<?>) {
					query.setParameterList(en.getKey(), (Collection<?>) obj);
				} else if (obj instanceof Object[]) {
					query.setParameterList(en.getKey(), (Object[]) obj);
				} else {
					query.setParameter(en.getKey(), obj);
				}
			}
		}

		return query;
	}

	/**
	 * @author Prince
	 * 
	 * @param querySql
	 *            查询语句
	 * 
	 * @return org.hibernate.SQLSession SQL查询对象
	 */
	private Query getHQLQueryByHql(String queryHql,
			Map<String, Object> conditionMap, Session hibernateSession) {
		// 创建SQLQuery实体
		Query query = hibernateSession.createQuery(queryHql);
		// 赋值条件
		if (conditionMap != null && conditionMap.size() > 0) {
			for (Map.Entry<String, Object> enMap : conditionMap.entrySet()) {
				query.setParameter(enMap.getKey(), enMap.getValue());
			}
		}
		// 返回实体
		return query;
	}

	/**
	 * 获取已经添加过条件的Query实体,采用的是HQL语句
	 * 
	 * @author 娄杰
	 * 
	 * @param entityName
	 *            类的名称
	 * @param conditionMap
	 *            条件
	 * @return 已经添加过条件的HQL Query实体
	 */
	private Query getHQLQuery(String entityName,
			Map<String, Object> conditionMap, Session hibernateSession) {
		// 组成Hql语句头部
		String hql = "FROM " + entityName;
		// 拼接Hql条件
		if (conditionMap != null && conditionMap.size() > 0) {
			hql += " WHERE ";
			for (Map.Entry<String, Object> enMap : conditionMap.entrySet()) {
				if (enMap.getKey().endsWith("=")) {
					hql += " ("
							+ enMap.getKey().substring(0,
									enMap.getKey().length() - 1)
							+ " = :"
							+ enMap.getKey().substring(0,
									enMap.getKey().length() - 1)
							+ " OR "
							+ enMap.getKey().substring(0,
									enMap.getKey().length() - 1)
							+ " IS NULL) AND ";
				} else if (enMap.getKey().endsWith("!")) {
					hql += " "
							+ enMap.getKey().substring(0,
									enMap.getKey().length() - 1)
							+ " != :"
							+ enMap.getKey().substring(0,
									enMap.getKey().length() - 1)
							+ " AND "
							+ enMap.getKey().substring(0,
									enMap.getKey().length() - 1)
							+ " IS NOT NULL AND ";
				} else {
					hql += " " + enMap.getKey() + " = :" + enMap.getKey()
							+ " AND ";
				}
			}
			hql = hql.substring(0, hql.lastIndexOf(" AND "));
		}
		// 根据Hql语句创建Query实体
		Query queryEntity = hibernateSession.createQuery(hql);
		// 赋值
		if (conditionMap != null && conditionMap.size() > 0) {
			for (Map.Entry<String, Object> enMap : conditionMap.entrySet()) {
				queryEntity.setParameter(
						enMap.getKey().endsWith("!")
								|| enMap.getKey().endsWith("=") ? enMap
								.getKey().substring(0,
										enMap.getKey().length() - 1) : enMap
								.getKey(), enMap.getValue());
			}
		}
		// 返回Query实体
		return queryEntity;
	}

	/**
	 * 获取已经添加过条件的Query实体,采用的是HQL语句
	 * 
	 * @author 娄杰
	 * 
	 * @param entityName
	 *            类的名称
	 * @param conditionMap
	 *            条件
	 * @return 已经添加过条件的HQL Query实体
	 */
	private Query getNewHQLQuery(String entityName,
			Map<String, Object> conditionMap, Session hibernateSession) {
		// 组成Hql语句头部
		String hql = "FROM " + entityName;
		// 拼接Hql条件
		if (conditionMap != null && conditionMap.size() > 0) {
			hql += " WHERE ";
			for (Map.Entry<String, Object> enMap : conditionMap.entrySet()) {
				if (enMap.getKey().endsWith("=")) {
					hql += " ("
							+ enMap.getKey().substring(0,
									enMap.getKey().length() - 1)
							+ " = :"
							+ enMap.getKey().substring(0,
									enMap.getKey().length() - 1)
							+ " OR "
							+ enMap.getKey().substring(0,
									enMap.getKey().length() - 1)
							+ " IS NULL) AND ";
				} else if (enMap.getKey().endsWith("!")) {
					hql += " "
							+ enMap.getKey().substring(0,
									enMap.getKey().length() - 1)
							+ " != :"
							+ enMap.getKey().substring(0,
									enMap.getKey().length() - 1)
							+ " AND "
							+ enMap.getKey().substring(0,
									enMap.getKey().length() - 1)
							+ " IS NOT NULL AND ";
				} else if (enMap.getKey().endsWith("%")) {
					hql += " "
							+ enMap.getKey().substring(0,
									enMap.getKey().length() - 1)
							+ " LIKE :"
							+ enMap.getKey().substring(0,
									enMap.getKey().length() - 1) + " AND ";
				} else {
					hql += " " + enMap.getKey() + " = :" + enMap.getKey()
							+ " AND ";
				}
			}
			hql = hql.substring(0, hql.lastIndexOf(" AND "));
		}
		// 根据Hql语句创建Query实体
		Query queryEntity = hibernateSession.createQuery(hql);
		// 赋值
		if (conditionMap != null && conditionMap.size() > 0) {
			for (Map.Entry<String, Object> enMap : conditionMap.entrySet()) {
				queryEntity.setParameter(
						enMap.getKey().endsWith("!")
								|| enMap.getKey().endsWith("=")
								|| enMap.getKey().endsWith("%") ? enMap
								.getKey().substring(0,
										enMap.getKey().length() - 1) : enMap
								.getKey(), enMap.getValue());
			}
		}
		// 返回Query实体
		return queryEntity;
	}

	/**
	 * 执行update语句
	 * 
	 * @author Prince
	 * 
	 * @param querySql
	 *            查询语句
	 * @param conditionMap
	 *            条件
	 * @return 结果
	 */
	public boolean runUpdateSql(String querySql,
			Map<String, Object> conditionMap) {
		// 获取处理好的SQLQuery实体并获取列表
		// 利用父类里面的方法获取Session实例
		Session hibernateSession = this.getSession();
		try {
			int i = this.getSQLQuery(querySql, conditionMap, hibernateSession)
					.executeUpdate();
			if (i >= 1) {
				return true;
			}
			// 判断是否符合条件
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return false;
		} finally {
			// 关闭hibernateSession对象
			try {
				releaseSession(hibernateSession);
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		}
		// 当无符合条件的时候返回null
		return false;
	}

	@SuppressWarnings("unchecked")
	public List<Object> getListBySql(String querySql,
			Map<String, Object> conditionMap) {
		Session hibernateSession = this.getSession();
		try {
			List<Object> listObject = null;
			listObject = getSQLQuery(querySql, conditionMap, hibernateSession)
					.list();
			return listObject;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return null;
		} finally {
			try {
				releaseSession(hibernateSession);
			} catch (Exception e) {
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getArrayBySql(String querySql,
			Map<String, Object> conditionMap) {
		Session hibernateSession = this.getSession();
		try {
			List<Object[]> listObject = null;
			listObject = getSQLQuery(querySql, conditionMap, hibernateSession)
					.list();
			return listObject;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return null;
		} finally {
			try {
				releaseSession(hibernateSession);
			} catch (Exception e) {
			}
		}
	}

	public String getCountHql(String hql) {
		String COUNT_SELECT = "select count(0) ";
		String tmp = hql;
		int index = tmp.indexOf(" from ");
		int INDEX = tmp.indexOf(" FROM ");
		if (index == INDEX) {
			throw new IllegalArgumentException(
					"HQL语句不正确，语句中必须包含' from ' 或者 ‘ FROM ’ ");
		}
		if (index > INDEX) {
			tmp = COUNT_SELECT + tmp.substring(index);
		} else {
			tmp = COUNT_SELECT + tmp.substring(INDEX);
		}
		return tmp;
	}

	/**
	 * 获取Map的列表
	 * 
	 * @param querySql
	 *            查询语句
	 * @param conditionMap
	 *            条件
	 * @return List<Map<String,Object>> 实体
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getListMapBySql(String querySql,
			Map<String, Object> conditionMap) {
		// 获取已经处理好的SQLQuery实体并获取MapList列表
		List<Map<String, Object>> listMapEntity = null;
		// 利用父类里面的方法获取Session实例
		Session hibernateSession = this.getSession();
		try {
			listMapEntity = this
					.getSQLQueryEntity(querySql, conditionMap, hibernateSession)
					.setResultTransformer(
							org.hibernate.transform.Transformers.ALIAS_TO_ENTITY_MAP)
					.list();
			if (listMapEntity != null && listMapEntity.size() > 0) {
				return listMapEntity;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return null;
		} finally {
			// 关闭hibernateSession对象
			try {
				this.releaseSession(hibernateSession);
			} catch (Exception exc) {
			}
		}
		// 返回符合条件的值
		return null;
	}

	/**
	 * 获取已经添加过条件的SQLQuery的实体,采用的Sql语句
	 * 
	 * @param querySql
	 *            查询语句
	 * @return org.hibernate.SQLSession实体
	 */
	private SQLQuery getSQLQueryEntity(String querySql,
			Map<String, Object> conditionMap, Session hibernateSession) {
		// 创建SQLQuery实体
		SQLQuery sqlQueryEntity = hibernateSession.createSQLQuery(querySql);
		// 赋值条件
		if (conditionMap != null && conditionMap.size() > 0) {
			for (Map.Entry<String, Object> enMap : conditionMap.entrySet()) {
				sqlQueryEntity.setParameter(enMap.getKey(), enMap.getValue());
			}
		}
		// 返回实体
		return sqlQueryEntity;
	}

	/**
	 * 获取分页（SQL语句的参数请使用GiantPager.putParam()存在GiantPager的Map里面；可统计总记录数；可实现二重排序(SQL语句中存在ORDER BY)）
	 * 
	 * @param sql
	 *            SQL语句（LIKE %模糊查询，请拼接SQL，参数不要放入Map里面）
	 * @param page
	 *            GiantPager对象
	 * @return
	 */
	public GiantPager getPage(String sql, GiantPager page) {
		if (sql == null || "".equals(sql.trim()))
			return new GiantPager();
		if (page == null)
			page = new GiantPager();

		Session session = null;
		try {
			session = this.getSession();

			String order = page.get("orderByValue", true);/* 排序命令 */
			if (order == null || !"[ASC][DESC]".contains("[" + order.toUpperCase() + "]"))
				order = "ASC";
			page.put("orderByValue", order);

			String temp = sql.toUpperCase();/* 统一大小写 */
			String orderBy = GiantUtils.filterStr(page.getOrderColumn());/* 排序字段 */
			page.setOrderColumn(orderBy);
			if (orderBy != null && !"".equals(orderBy)) {
				int n = temp.lastIndexOf(" ORDER BY ");
				if (n != -1) {
					sql = sql.substring(0, n + 10) + orderBy + " " + order + "," + sql.substring(n + 10);
				} else {
					sql += " ORDER BY " + orderBy + " " + order;
				}
			}
			String countSql = null;
			if (temp.contains(" FROM ")) {
				countSql = "SELECT COUNT(0) rowCount " + sql.substring(temp.indexOf(" FROM "), sql.length());
			} else {
				countSql = "SELECT COUNT(0) rowCount (" + sql + ") temp";
			}

			SQLQuery query = session.createSQLQuery(sql);
			SQLQuery count = session.createSQLQuery(countSql).addScalar("rowCount", StandardBasicTypes.LONG);/* count()的结果为BigInteger，需要转换 */

			Map<String, Object> condition = page.getParamMap();
			if (condition != null && condition.entrySet() != null) {
				for (Map.Entry<String, Object> entry : condition.entrySet()) {
					if (entry.getValue() != null && !"".equals(entry.getValue().toString().trim())) {
						query.setParameter(entry.getKey(), entry.getValue());
						count.setParameter(entry.getKey(), entry.getValue());
					}
				}
			}
			page.setTotalCounts(((Long) count.list().get(0)).intValue());/* 总记录数 */
			if (page.getCurrentPage() > page.getTotalPages())
				page.setCurrentPage(page.getTotalPages());/* 校正分页参数 */

			query.setFirstResult((page.getCurrentPage() - 1) * page.getPageSize());/* 设置起始行数 */
			query.setMaxResults(page.getPageSize());/* 设置每页记录数 */
			query.setResultTransformer(org.hibernate.transform.Transformers.ALIAS_TO_ENTITY_MAP);
			page.setPageResult(query.list());/* 分页结果集 */
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[获取分页对象]方法:getPage(String sql, GiantPager page);SQL语句:" + sql + ";当前第" + page.getCurrentPage() + "页,每页显示"
					+ page.getPageSize() + "条;条件" + page.getParamMap() + ";执行该方法过程中出现异常:" + e.toString());
		} finally {
			try {
				this.releaseSession(session);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.toString());
			}
		}
		return page;
	}
	
}
