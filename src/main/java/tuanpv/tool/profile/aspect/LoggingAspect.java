package tuanpv.tool.profile.aspect;

import java.lang.annotation.Annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import tuanpv.tool.domain.ActionInfo;
import tuanpv.tool.utils.LogUtils;

/**
 * Sample AOP configuration by JAVA. The smallest order will be run first.
 * 
 * @author TuanPV
 */
@Component
@Aspect
@Order(1)
public class LoggingAspect {
	private ActionInfo getActionInfo(JoinPoint joinPoint) {
		Object action = joinPoint.getTarget();
		Annotation[] annotations = action.getClass().getAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation.annotationType() == ActionInfo.class) {
				return (ActionInfo) annotation;
			}
		}

		return null;
	}

	@Before("execution(* tuanpv.tool.domain.ProfileAction.execute(..))")
	public void logBefore(JoinPoint joinPoint) {
		System.out.println();

		ActionInfo info = this.getActionInfo(joinPoint);
		if (info == null)
			LogUtils.logOut("BEGIN");
		else
			LogUtils.logOut("BEGIN", info);
	}

	@After("execution(* tuanpv.tool.domain.ProfileAction.execute(..))")
	public void logAfter(JoinPoint joinPoint) {
		LogUtils.logOut("END");
	}
}
