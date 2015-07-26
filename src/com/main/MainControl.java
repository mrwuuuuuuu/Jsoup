package com.main;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.util.Configure;

public class MainControl extends Thread{

	public static ThreadManager mgr=null;
	Logger LOG = Logger.getLogger(MainControl.class);
	public static Configure cfg = new Configure();

	public void init() throws Exception {
		LOG.info("System init...");
		cfg.load("conf/default.cfg");
		LOG.info(cfg.toString());
		mgr=new ThreadManager();
		mgr.init();
	}

	
	public  void   process() throws Exception {
		File inFile = new File(cfg.inPath);
		BufferedReader br = new BufferedReader(new FileReader(inFile));
		String phoneNo = br.readLine();
//		int i=1;
		while (phoneNo != null) {
//			System.out.println(i++ +","+phoneNo);
			mgr.putMsg(phoneNo);
			phoneNo=br.readLine();
		}
		br.close();
	}
	
	public static void main(String[] args) throws Exception {
		PropertyConfigurator.configure("conf/log4j.properties");
		MainControl control = new MainControl();
		control.init();
		control.process();
		mgr.startService();
	}
}
