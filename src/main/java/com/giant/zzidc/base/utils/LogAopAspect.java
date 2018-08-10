package com.giant.zzidc.base.utils;

import java.lang.reflect.Method;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.giant.zzidc.base.service.GiantBaseService;
import com.zzidc.team.entity.ActionLog;

/**
 * [切点类，用于公共日志记录] 【前置通知[Before advice]】：在连接点前面执行，前置通知不会影响连接点的执行，除非此处抛出异常。
 * 【正常返回通知[After returning advice]】：在连接点正常执行完成后执行，如果连接点抛出异常，则不会执行。 【异常返回通知[After
 * throwing advice]】：在连接点抛出异常后执行。 【返回通知[After (finally)
 * advice]】：在连接点执行完成后执行，不管是正常执行完成，还是抛出异常，都会执行返回通知中的内容。 【环绕通知[Around
 * advice]】：环绕通知围绕在连接点前后
 * ，比如一个方法调用的前后。这是最强大的通知类型，能在方法调用前后自定义一些操作。环绕通知还需要负责决定是继续处理joinPoint(调用ProceedingJoinPoint的proceed方法)还是中断执行。
 * 
 * @author Mr.Library
 * @date 2017-7-13 下午3:52:59
 * @company 公司
 * @version v1.0
 * @copyright copyright (c) 2017
 */
//@Aspect
//@Component
public class LogAopAspect {

	@Autowired
	protected GiantBaseService baseService;

	/* 本地异常日志记录对象 */
	private static final Logger logger = LoggerFactory.getLogger(LogAopAspect.class);
	// 定义本次log实体
	private ActionLog al;

	// 获取开始时间
	private long BEGIN_TIME;

	// 获取结束时间
	private long END_TIME;
//
//	/* Service层切点 */
//	@Pointcut("execution(* com.zzidc.fgasdfasdfadf.service.*.*(..))")
//	public void serviceAspect() {
//		System.out.println("我是一个Service切入点");
//	}

	/**
	 * 前置通知 用于拦截Controller层记录用户的操作
	 */
//	@Before("serviceAspect()")
//	public void doBefore(JoinPoint joinPoint) {
//		al = new ActionLog();
//		BEGIN_TIME = System.currentTimeMillis();
//		Integer logId = saveLogBefore(joinPoint);
//		if (logId > 0) {
//			try {
//				@SuppressWarnings("unchecked")
//				Map<String, String> mvm = (Map<String, String>) joinPoint.getArgs()[0];
//				al.setComment(mvm.get("comment"));
//			} catch (Exception e) {
//			}
//		}
//	}

	/**
     * 方法结束执行
     */
//	@AfterReturning(returning="rvt", pointcut="serviceAspect()")
//    public void after(JoinPoint joinPoint, Object rvt){
//        END_TIME = System.currentTimeMillis();
//        try {
//        	CustomLog customLog = getCustomLog2(joinPoint);
//        	if (customLog != null) {
//        		if(rvt instanceof Integer){
//        			al.setObjectId((Integer) rvt);
//        		}
//        		al.setActionLen((int) (END_TIME - BEGIN_TIME));
//        		baseService.saveUpdateOrDeleteLog(al, null);
//        	}
//		} catch (Exception e) {
//		}
//    }

	/**
	 * 配置service环绕通知
	 * 
	 * @author Mr.Liboary <br>
	 * @date 2017-7-13 下午5:45:12 <br>
	 */
	/*@Around("serviceAspect()")
	public void around(JoinPoint joinPoint) {
		al = new ActionLog();
		BEGIN_TIME = System.currentTimeMillis();
		try {
			Integer logId = saveLogBefore(joinPoint);
			if (logId > 0) {
				try {
					@SuppressWarnings("unchecked")
					Map<String, String> mvm = (Map<String, String>) joinPoint.getArgs()[0];
					mvm.put("logId", String.valueOf(logId));
					Object prm[] = { mvm };
					((ProceedingJoinPoint) joinPoint).proceed(prm);
				} catch (Exception e) {
				}
			} else {
				((ProceedingJoinPoint) joinPoint).proceed();
			}
		} catch (Throwable e) {
		}
		END_TIME = System.currentTimeMillis();
	}*/

	/**
	 * [按照模块保存前置通知日志] <br>
	 * 
	 * @author Mr.Liboary <br>
	 * @date 2017-7-13 下午5:45:12 <br>
	 * @param joinPoint
	 *            切点
	 */
	private Integer saveLogBefore(JoinPoint joinPoint) {
		try {
			/* 获取自定义日志注解对象 */
			String module = getMethodModule(joinPoint);
			CustomLog customLog = getCustomLog2(joinPoint);
			if (customLog == null) {
				return 0;
			}
			String desc = getMethodDesc(joinPoint);
			String method = joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()";
			String params = "";
			if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
				for (int i = 0; i < joinPoint.getArgs().length; i++) {
					// TODO 需要把joinPoin.getArgs()[i]转化为JSON字符串
					params += JSON.toJSONString(joinPoint.getArgs()[i]) + ";";
				}
				try {
					@SuppressWarnings("unchecked")
					Map<String, String> mvm = (Map<String, String>) joinPoint.getArgs()[0];
					al.setObjectId(Integer.parseInt(mvm.get("id")));
				} catch (Exception e) {
				}
				try {
					@SuppressWarnings("unchecked")
					Map<String, String> mvm = (Map<String, String>) joinPoint.getArgs()[0];
					al.setComment(mvm.get("comment"));
				} catch (Exception e) {
				}
			}
			al.setModule(module);
//			al.setDesc(desc);
			al.setMethod(method);
			al.setParams(params);
//			al.setResult("正常");
//			al.setCreateTime(new Timestamp(System.currentTimeMillis()));
			baseService.saveLog(al);
			return al.getId();
		} catch (Exception ex) {
			logger.error("异常信息:{}", ex.getMessage());
		}
		return 0;
	}

	/**
	 * 获取注解中对方法的描述信息
	 * 
	 * @param joinPoint
	 *            切点
	 * @return 方法描述
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private static String getMethodDesc(JoinPoint joinPoint) throws Exception {
		/* 拦截的实体类名字，就是当前正在执行的类 */
		String targetName = joinPoint.getTarget().getClass().getName();
		/* 拦截的方法名称。当前正在执行的方法 */
		String methodName = joinPoint.getSignature().getName();
		/* 拦截的方法参数 */
		Object[] args = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		String description = "";
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == args.length) {
					if (method.getAnnotation(CustomLog.class) == null) {
						continue;
					}
					description = method.getAnnotation(CustomLog.class).desc();
					break;
				}
			}
		}
		return description;
	}

	/**
	 * 获取注解中对方法的模块信息
	 * 
	 * @param joinPoint
	 *            切点
	 * @return 方法描述
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private static String getMethodModule(JoinPoint joinPoint) throws Exception {
		/* 拦截的实体类名字，就是当前正在执行的类 */
		String targetName = joinPoint.getTarget().getClass().getName();
		/* 拦截的方法名称。当前正在执行的方法 */
		String methodName = joinPoint.getSignature().getName();
		/* 拦截的方法参数 */
		Object[] args = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		String module = "";
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == args.length) {
					if (method.getAnnotation(CustomLog.class) == null) {
						continue;
					}
					module = method.getAnnotation(CustomLog.class).module();
					break;
				}
			}
		}
		return module;
	}

	/**
	 * [获取自定义日志注解对象] <br>
	 * 
	 * @author Mr.Liboary <br>
	 * @date 2017-7-13 下午5:26:05 <br>
	 * @param joinPoint
	 * @return
	 * @throws Exception
	 *             <br>
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	private static CustomLog getCustomLog3(JoinPoint joinPoint) throws Exception {
		/* 拦截的实体类名字，就是当前正在执行的类 */
		String targetName = joinPoint.getTarget().getClass().getName();
		/* 拦截的方法名称。当前正在执行的方法 */
		String methodName = joinPoint.getSignature().getName();
		/* 拦截的方法参数 */
		Object[] args = joinPoint.getArgs();
		Class<?> targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		CustomLog customLog = null;
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == args.length) {
					customLog = method.getAnnotation(CustomLog.class);
					return customLog;
				}
			}
		}
		return customLog;
	}

	/**
	 * 
	 * [获取自定义日志注解对象] <br>
	 * 
	 * @deprecated 请使用getCustomLog方法 <br>
	 * @author Mr.Liboary <br>
	 * @date 2017-7-13 下午5:26:05 <br>
	 * @param joinPoint
	 * @return
	 * @throws Exception
	 *             <br>
	 */
	@Deprecated
	@SuppressWarnings({ "rawtypes", "unused" })
	private static CustomLog getCustomLog2(JoinPoint joinPoint) throws Exception {
		/* 拦截的实体类名字，就是当前正在执行的类 */
		String targetName = joinPoint.getTarget().getClass().getName();
		/* 拦截的方法名称。当前正在执行的方法 */
		String methodName = joinPoint.getSignature().getName();
		/* 拦截的方法参数 */
		Object[] args = joinPoint.getArgs();
		Class<?> targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		CustomLog customLog = null;
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == args.length) {
					customLog = method.getAnnotation(CustomLog.class);
					return customLog;
				}
			}
		}
		return customLog;
	}

	/**
	 * 1 [获取自定义日志注解对象] <br>
	 * 
	 * @author Mr.Liboary <br>
	 * @date 2017-7-14 上午9:49:07 <br>
	 * @param joinPoint
	 *            切入点
	 * @return
	 * @throws Exception
	 *             <br>
	 */
	@SuppressWarnings("rawtypes")
	private static CustomLog getCustomLog(JoinPoint joinPoint) throws Exception {
		/* 拦截的实体类名字，就是当前正在执行的类 */
		String targetName = joinPoint.getTarget().getClass().getName();
		/* 拦截的方法名称。当前正在执行的方法 */
		String methodName = joinPoint.getSignature().getName();
		/* 拦截的放参数类型 */
		Signature sig = joinPoint.getSignature();
		MethodSignature msig = null;
		if (!(sig instanceof MethodSignature)) {
			throw new IllegalArgumentException("该注解只能用于方法");
		}
		msig = (MethodSignature) sig;
		Class[] parameterTypes = msig.getMethod().getParameterTypes();
		/* 获得被拦截的方法 */
		Method method = null;
		try {
			method = targetName.getClass().getMethod(methodName, parameterTypes);
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		}
		if (null != method) {
			// 判断是否包含自定义的注解，说明一下这里的SystemLog就是我自己自定义的注解
			if (method.isAnnotationPresent(CustomLog.class)) {
				CustomLog customLog = method.getAnnotation(CustomLog.class);
				return customLog;
			}
		}
		return null;
	}

}
