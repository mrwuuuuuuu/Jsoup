package com.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Configure {
    public String inPath;
    public String outPath;
    public int threadNum;

    /**
     * º”‘ÿ≈‰÷√œÓ
     * 
     * @throws IOException
     */
    public void load(String strPath) throws IOException {
	Properties p = new Properties();
	p.load(new FileReader(strPath));
	inPath = p.getProperty("inPath");
	outPath = p.getProperty("outPath");
	threadNum=Integer.parseInt(p.getProperty("threadNum"));
    }
}
