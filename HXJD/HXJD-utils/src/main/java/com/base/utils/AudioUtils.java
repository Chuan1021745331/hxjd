package com.base.utils;

import java.io.File;
import java.text.SimpleDateFormat;

import com.jfinal.core.JFinal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AudioUtils {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    private final static Logger logger= LoggerFactory.getLogger(AudioUtils.class);


    public static boolean transAmrToMp3(String oldFile, String newFile){
		//File file = new File(sourcePath);  
		//String webroot = "C:/Users/kevin/Desktop/test/trans";
		//String out = "C:/Users/kevin/Desktop/test/trans/003.mp3";
		//String sourcePath = "C:/Users/kevin/Desktop/test/origin/003.amr";
		String rootPath = JFinal.me().getServletContext().getRealPath("");
		File in = new File(rootPath + oldFile);
		File ou = new File(rootPath + newFile);
		String webRoot = JFinal.me().getServletContext().getRealPath("/common");;
		//String targetPath = sourcePath+".mp3";//转换后文件的存储地址，直接将原来的文件名后加mp3后缀名  
        Runtime run = null;    
        try {    
            run = Runtime.getRuntime();    
            long start=System.currentTimeMillis();    
            Process p=run.exec(webRoot+"/ffmpeg -i "+ " " + in+" -acodec libmp3lame "+ " " + ou);//执行ffmpeg.exe,前面是ffmpeg.exe的地址，中间是需要转换的文件地址，后面是转换后的文件地址。-i是转换方式，意思是可编码解码，mp3编码方式采用的是libmp3lame  
            //释放进程    
            p.getOutputStream().close();    
            p.getInputStream().close();    
            p.getErrorStream().close();    
            p.waitFor();    
            long end=System.currentTimeMillis();    
            logger.info(oldFile+" convert success, costs:"+(end-start)+"ms");
            return true;
            //删除原来的文件    
            //if(file.exists()){    
                //file.delete();    
            //}    
        } catch (Exception e) {    
            e.printStackTrace();
            return false;
        }finally{    
            //run调用lame解码器最后释放内存    
            run.freeMemory();     
        }
		
		
		
		
		
	}
	
	
}
