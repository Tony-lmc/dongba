package com.db.common.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

import com.alibaba.druid.sql.ast.statement.SQLJoinTableSource.JoinType;

/**
 * 
 * @author lmc
 *@Aspect描述的类为一个切面对面
 *	1.pointcut 切入点（织入扩展功能的点）
 *  2.advice  通知（扩展功能）
 */
@Aspect
@Service
public class LogAspect {
	/**定义切入点
	 * 1.bean(bean的名字)，例如bean(sysUserServiceImpl)
	 * 2.bean(bean表达式)，例如bean(*ServiceImpl)通配符表达式
	 * */
	@Pointcut("bean(sysUserServiceImpl)")
	public void doLog() {}
	
	/**
	 * @around  该注解为一个通知，在目标方法之前或者之后执行一些事情
	 * @param joinPoint  连接点对象封装目标方法信息
	 * @return
	 * @throws Throwable
	 */
	@Around("doLog()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
		long t1 = System.currentTimeMillis();
		Object result = joinPoint.proceed();//执行下一个切面方法，没有则执行目标方法
		long t2=System.currentTimeMillis();
		System.out.println("execute time:"+(t2-t1));
		return result;
		
	}
	@Around("doLog()")
	private Method getTargetMethod(ProceedingJoinPoint joinPoint) {//获取连接点信息
		//获取目标对面的类
		Class<?> targetClass = joinPoint.getTarget().getClass();
		MethodSignature ms = (MethodSignature) joinPoint.getSignature();
		System.out.println(ms.getDeclaringTypeName());
		System.out.println(targetClass);
		return null;
		
	}
}
