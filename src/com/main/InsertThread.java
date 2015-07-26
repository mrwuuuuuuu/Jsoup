package com.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class InsertThread extends Thread{
	private LinkedList<String > queueLinkedList=new LinkedList<String>();
	 File outFile=null;
	 BufferedWriter bw=null;
	static int i=1;
//	static int j=1;
	
	public  void init() throws IOException{
		 outFile = new File(MainControl.cfg.outPath+String.valueOf(i++)+".txt");
		 bw=new BufferedWriter(new FileWriter(outFile));
	}
	
	public void putMsg(String msg) {
		synchronized(queueLinkedList){
			queueLinkedList.add(msg);
//			System.out.println("queue add msg:------->"+i++ +","+msg);
		}
	}
	
	private String getMsg(){
		synchronized(queueLinkedList){
			if(queueLinkedList.size()>0){
				return queueLinkedList.pop();
			}else {
				return null;
			}
		}
	}
	
	private void process(String msg) throws IOException{
		String url="http://www.haosou.com/s?ie=utf-8&shb=1&src=360sou_newhome&q=";
		if(!msg.equals("")){
			String line=url+msg;
			Document doc = Jsoup.connect(line).get();
			if (doc.getElementsByClass("mohe-tips").select("span.mohe-ph-mark").text().equals("")) {
				bw.write(msg + "," + "ÎÞ±ê¼Ç" + "\n");
			} else {
				String tag=doc.getElementsByClass("mohe-tips").select("span.mohe-ph-mark").text();
				String num=doc.getElementsByClass("mohe-tips").select("b").text();
				bw.write(msg+ ","+tag+"," +num+"\n");
			}
		}
	}
	
	public void run() {
		while(true){
			String msg=getMsg();
//			System.out.println("queue pop msg---->"+j++ +","+msg);
			if(msg!=null)
			{
				try {
					process(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}
}
