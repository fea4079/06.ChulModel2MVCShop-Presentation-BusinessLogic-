package com.model2.mvc.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

/*
 * FileName : PojoAspectJ.java
 *	:: XML �� ���������� aspect �� ����   
  */
public class LogAspectJ {

	///Constructor
	public LogAspectJ() {
		System.out.println("LogAspectJ.java.LogAspectJ() ���̾ "+this.getClass()+"\n");
	}
	
	//Around  Advice
	public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {
			
		System.out.println("");
		System.out.println("[LogAspectJ.java Around before] Ÿ����ü.�޼��� :"+
													joinPoint.getTarget().getClass().getName() +"."+
													joinPoint.getSignature().getName());
		if(joinPoint.getArgs().length !=0){
			System.out.println("[LogAspectJ.java Around before]method�� ���޵Ǵ� ���� : "+ joinPoint.getArgs()[0]);
		}
		//==> Ÿ�� ��ü�� Method �� ȣ�� �ϴ� �κ� 
		Object obj = joinPoint.proceed();

		System.out.println("[LogAspectJ.java Around after] Ÿ�� ��üreturn value  : "+obj);
		System.out.println("");
		
		return obj;
	}
	
}//end of class