package tuanpv.tool.profile.aspect;

import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

@Component(value = "logging")
public class Logging {
	public void beforeAdvice(JoinPoint joinPoint) {
		System.out.println("Going to setup student profile.");

		Object[] signatureArgs = joinPoint.getArgs();
		for (Object signatureArg : signatureArgs) {
			System.out.println("Arg: " + signatureArg);
		}
	}

	public void afterAdvice() {
		System.out.println("Student profile has been setup.");
	}

	public void afterReturningAdvice(Object retVal) {
		System.out.println("Returning:" + retVal.toString());
	}

	public void afterThrowingAdvice(IllegalArgumentException ex) {
		System.out.println("There has been an exception: " + ex.toString());
	}
}
