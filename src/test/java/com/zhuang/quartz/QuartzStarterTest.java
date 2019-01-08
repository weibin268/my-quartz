package com.zhuang.quartz;

import java.util.Scanner;

import org.junit.Test;
import org.quartz.SchedulerException;

public class QuartzStarterTest {

	@Test
	public void testStart() throws ClassNotFoundException, SchedulerException {
		
		QuartzStarter quartzStarter=new QuartzStarter();
		quartzStarter.start();
		
		Scanner sc = new Scanner(System.in);
		String str = sc.nextLine();
		quartzStarter.stop();
		System.out.println("end");

	}
	
	
	@Test
	public void test()
	{
		System.out.println(System.getProperty("user.dir"));
		
	}
}
