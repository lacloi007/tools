package tuanpv.tool.profile.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import tuanpv.tool.utils.LogUtils;

@Aspect
public class LoggingAspect {
	@Before("execution(* tuanpv.tool.domain.ProfileAction.execute(..))")
	public void logBefore(JoinPoint joinPoint) {
		LogUtils.logOut("BEGIN");
	}

	@After("execution(* tuanpv.tool.domain.ProfileAction.execute(..))")
	public void logAfter(JoinPoint joinPoint) {
		LogUtils.logOut("END");
	}
}
