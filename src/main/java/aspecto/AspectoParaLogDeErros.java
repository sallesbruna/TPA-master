package aspecto;

import anotacao.ExcecaoDeAplicacao;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.io.PrintWriter;
import java.io.StringWriter;

@Aspect
public class AspectoParaLogDeErros 
{
	private static Logger logger = null;
	
//	@Pointcut("execution(* visao..*.*(..)) || execution(visao..*.new(..))")
	@Pointcut("execution(* service..*.*(..))")
	public void efetuaLogDeErro() {


	}

	@Around("efetuaLogDeErro()")
	public Object efetuaLog(ProceedingJoinPoint joinPoint) throws Throwable 
	{	
		try
		{	return joinPoint.proceed();
		}
		catch(Throwable throwable)
		{	
			if (throwable.getClass().isAnnotationPresent(ExcecaoDeAplicacao.class))
				throw throwable;
			
			String metodo = joinPoint.getSignature().getName();
	    	    	
	    	String mensagem = (throwable.getMessage() != null ? throwable.getMessage() : "");
			
	    	Throwable t = throwable.getCause();
			
	    	while ( t != null)
			{	
				mensagem = mensagem + (t.getMessage() != null ? " <==> " + t.getMessage() : ""); 
				t = t.getCause();
			}

			// As 4 linhas de código abaixo geram o stack trace como um String
			StringWriter sw = new StringWriter();
	    	PrintWriter pw = new PrintWriter(sw);
	    	throwable.printStackTrace(pw); 	
	    	String stackTrace = sw.toString();

	    	logger = Logger.getLogger(this.getClass().getName());
	    	
			logger.error("   Classe do erro: " + throwable.getClass().getName() + 
					     "   Metodo: " + metodo + 
					     "   Mensagem: " + mensagem + 
					     "   Stack Trace: " + stackTrace);
	    	
	    	throw throwable;
			//return null;
		}
	}
}