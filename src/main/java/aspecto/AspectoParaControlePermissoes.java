package aspecto;

import anotacao.*;
import excecao.ExecucaoDeMetodoSemARespectivaPermissaoException;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Aspect
public class AspectoParaControlePermissoes
{
	private static Logger logger = null;
	
//	@Pointcut("execution(* visao..*.*(..)) || execution(visao..*.new(..))")
	@Pointcut("call(* service..*.*(..))")
	public void efetuaControle() { }

	@Around("efetuaControle()")
	public Object efetuaControle(ProceedingJoinPoint joinPoint) throws Throwable
	{
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		boolean possuiPermissao = true;

		Annotation[] declaredAnnotations = method.getAnnotations();
		for (int i = 0; i < declaredAnnotations.length; i++){
			Class aClass = declaredAnnotations[i].annotationType();

			String roleToCheck = null;
			if(aClass == RoleAdmin.class){
				roleToCheck = RoleAdmin.PERMISSAO;
			}
			else if(aClass == RoleUser1.class){
				roleToCheck = RoleUser1.PERMISSAO;
			}
			else if(aClass == RoleUser2.class){
				roleToCheck = RoleUser2.PERMISSAO;
			}
			else if(aClass == RoleUser3.class){
				roleToCheck = RoleUser3.PERMISSAO;
			}

			System.out.println("Verificicando permissão para: " + roleToCheck);
			if(roleToCheck != null && !PermissoesSingleton.getPermissoesSingleton().possuiPermissao(roleToCheck)){
				possuiPermissao = false;
			}

			if(PermissoesSingleton.getPermissoesSingleton().possuiPermissao(RoleAdmin.PERMISSAO) ){
				possuiPermissao = true;
			}
		}

		if(possuiPermissao){
			return joinPoint.proceed();
		}

		throw new ExecucaoDeMetodoSemARespectivaPermissaoException("Você não tem permissão para efetuar esta operação");
	}
}