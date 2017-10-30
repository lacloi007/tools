package tuanpv.tool.profile.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import tuanpv.tool.utils.LogUtils;

/**
 * Sample AOP configuration by JAVA.
 * The smallest order will be run first.
 * 
 * @author TuanPV
 */
@Component
@Aspect
@Order(1)
public class LoggingAspect {
	@Before("execution(* tuanpv.tool.domain.ProfileAction.execute(..))")
	public void logBefore(JoinPoint joinPoint) {
		System.out.println();
		LogUtils.logOut("BEGIN");
	}

	@After("execution(* tuanpv.tool.domain.ProfileAction.execute(..))")
	public void logAfter(JoinPoint joinPoint) {
		LogUtils.logOut("END");
	}
}
