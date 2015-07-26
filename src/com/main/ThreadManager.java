package com.main;

import java.io.IOException;

public class ThreadManager {
	InsertThread[] pool=null;
	private int count=0;
	
	public void init() {
		pool=new InsertThread[MainControl.cfg.threadNum];
		for(int i=0;i<pool.length;i++)
		{
			pool[i]=new InsertThread();
		}
	}
	
	public void startService() throws IOException{
		for(int i=0;i<pool.length;i++){
			pool[i].init();
			pool[i].start();
		}
	}
	public void putMsg(String msg){
		if(count>=pool.length){
			count=0;
		}
		pool[count].putMsg(msg);
		++count;
	}
}
