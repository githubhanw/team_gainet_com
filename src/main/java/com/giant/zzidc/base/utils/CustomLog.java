package com.giant.zzidc.base.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * [自定义注解 拦截service]
 * 
 * @author Mr.Library
 * @date 2017-7-13 下午5:12:54
 * @company 公司
 * @version v1.0
 * @copyright copyright (c) 2017
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomLog {

	/* 记录到不同的日志表 */
	LogEnum logEnum() default LogEnum.PUB;

	/* 模块名称：会员管理，交易管理、云服务器管理、VPS管理 */
	String module() default "";

	/* 方法描述：会员列表、高级查询、导出、录入订单 */
	String desc() default "";

}
