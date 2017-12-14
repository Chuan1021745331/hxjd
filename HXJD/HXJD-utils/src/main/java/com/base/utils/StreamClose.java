package com.base.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

public class StreamClose {
	
	public static void bufferedReaderClose(BufferedReader reader){
		if(reader != null){
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void inputStreamClose(InputStream inputStream){
		if(inputStream != null){
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
