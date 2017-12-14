package com.base.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZipUtil
{

	/**
	 * 数据压缩
	 * 
	 * @param
	 * @param
	 * @throws Exception
	 */
	public static byte[] doZip(String msg) throws Exception
	{
		byte[] input = msg.getBytes("utf-8");
		ByteArrayInputStream in = new ByteArrayInputStream(input);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		GZIPOutputStream gos = new GZIPOutputStream(os);
		int count;
		byte data[] = new byte[input.length];
		while ((count = in.read(data, 0, input.length)) != -1)
		{
			gos.write(data, 0, count);
		}
		gos.finish();
		gos.close();
		in.close();
		return os.toByteArray();
	}

	/**
	 * 文件解压
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static String unZipFile(String filePath) throws Exception
	{
		FileInputStream in = new FileInputStream(filePath);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPInputStream gis = new GZIPInputStream(in);
		int count;
		byte data[] = new byte[1024 * 1024 * 2];
		while ((count = gis.read(data, 0, 1024 * 1024 * 2)) != -1)
		{
			out.write(data, 0, count);
		}
		gis.close();
		byte[] strByte = out.toByteArray();
		return new String(strByte, 0, strByte.length, "gbk");
	}

	/**
	 * 数据解压
	 * 
	 * @param bite
	 * @return
	 * @throws Exception
	 */
	public static String unZipByte(byte[] bite) throws Exception
	{
		// 创建一个新的 byte 数组输出流
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		// 创建一个 ByteArrayInputStream，使用 buf 作为其缓冲区数组
		ByteArrayInputStream in = new ByteArrayInputStream(bite);
		// 使用默认缓冲区大小创建新的输入流
		GZIPInputStream gzip = new GZIPInputStream(in);
		byte[] buffer = new byte[256];
		int n = 0;
		while ((n = gzip.read(buffer)) >= 0) {// 将未压缩数据读入字节数组
			// 将指定 byte 数组中从偏移量 off 开始的 len 个字节写入此 byte数组输出流
			out.write(buffer, 0, n);
		}
		// 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串
		return out.toString("UTF-8");
//		ByteArrayInputStream in = new ByteArrayInputStream(bite);
//		ByteArrayOutputStream out = new ByteArrayOutputStream();
//		GZIPInputStream gis = new GZIPInputStream(in);
//		int count;
//		byte data[] = new byte[1024];
//		while ((count = gis.read(data, 0, 1024)) != -1)
//		{
//			out.write(data, 0, count);
//		}
//		gis.close();
//		byte[] strByte = out.toByteArray();
//		return new String(strByte, 0, strByte.length);
	}
}
