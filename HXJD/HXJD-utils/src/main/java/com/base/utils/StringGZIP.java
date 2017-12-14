package com.base.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 字符串的压缩与解压缩
 * @author z
 *
 */
public class StringGZIP {
	public static void main(String[] args) throws IOException {
//		// 字符串超过一定的长度
//		String str = "这边讲述一下聊天室服务的思考过程.•  单进程聊天室优点是实现简单,当然缺点也显而易见. 虽然现在的服务器, 衡量高并发连接的性能时, 言必称C1000K. 但实际上, 为了服务可靠稳定, 其经验的链接上限值, 往往限定在万级别. 甚至更低, 毕竟聊天室的连接是高活跃的.•当然缺点也显而易见. 虽然现在的服务器, 衡量高并发连接的性能时, 言必称C1000K. 但实际上, 为了服务可靠稳定, 其经验的链接上限值, 往往限定在万级别. 甚至更低, 毕竟聊天室的连接是高活跃的.•当然缺点也显而易见. 虽然现在的服务器, 衡量高并发连接的性能时, 言必称C1000K. 但实际上, 为了服务可靠稳定, 其经验的链接上限值, 往往限定在万级别. 甚至更低, 毕竟聊天室的连接是高活跃的.•当然缺点也显而易见. 虽然现在的服务器, 衡量高并发连接的性能时, 言必称C1000K. 但实际上, 为了服务可靠稳定, 其经验的链接上限值, 往往限定在万级别. 甚至更低, 毕竟聊天室的连接是高活跃的.•当然缺点也显而易见. 虽然现在的服务器, 衡量高并发连接的性能时, 言必称C1000K. 但实际上, 为了服务可靠稳定, 其经验的链接上限值, 往往限定在万级别. 甚至更低, 毕竟聊天室的连接是高活跃的.•当然缺点也显而易见. 虽然现在的服务器, 衡量高并发连接的性能时, 言必称C1000K. 但实际上, 为了服务可靠稳定, 其经验的链接上限值, 往往限定在万级别. 甚至更低, 毕竟聊天室的连接是高活跃的.•当然缺点也显而易见. 虽然现在的服务器, 衡量高并发连接的性能时, 言必称C1000K. 但实际上, 为了服务可靠稳定, 其经验的链接上限值, 往往限定在万级别. 甚至更低, 毕竟聊天室的连接是高活跃的.•当然缺点也显而易见. 虽然现在的服务器, 衡量高并发连接的性能时, 言必称C1000K. 但实际上, 为了服务可靠稳定, 其经验的链接上限值, 往往限定在万级别. 甚至更低, 毕竟聊天室的连接是高活跃的.•当然缺点也显而易见. 虽然现在的服务器, 衡量高并发连接的性能时, 言必称C1000K. 但实际上, 为了服务可靠稳定, 其经验的链接上限值, 往往限定在万级别. 甚至更低, 毕竟聊天室的连接是高活跃的.• 当然缺点也显而易见. 虽然现在的服务器, 衡量高并发连接的性能时, 言必称C1000K. 但实际上, 为了服务可靠稳定, 其经验的链接上限值, 往往限定在万级别. 甚至更低, 毕竟聊天室的连接是高活跃的.• 以群分割的聊天室架构聊天室服务, 多是以地域/兴趣来构建和划分. 同群用户只要在同一个进程即可, 但不同群的用户, 未必需要在同一个进程中. 因此基于这个假定, 以群来分割分布式聊天室集群.整个聊天室服务, 拥有多个服务节点. 每个节点承担多个地域/兴趣群. 那用户如何进入指定的聊天室进程节点呢? 需要有一个专门的聊天室群管理节点, 用于路由决策, 同时管理扩容/缩容集群的过程.";
//		int b = str.getBytes().length;
//		System.out.println("原始的字符串为--->" + str);
//		System.out.println("原始的字符串长度为------->" + b);
//
//		String ys = compress(str);
//		int a = ys.getBytes().length;
//		System.out.println("压缩后的字符串长度为----->" + a);
//
//		String jy = unCompress(ys);
//		System.out.println("解压后字符串为--->" + jy);
//		System.out.println("解压后字符串长度为--->" + jy.getBytes().length);
//
//		BigDecimal c = new BigDecimal(b + "").divide(new BigDecimal(a + ""), 5, BigDecimal.ROUND_HALF_UP);
//		System.out.println("压缩比例为" + c.multiply(new BigDecimal(100)) + "%");
//
//		// 判断
//		if (str.equals(jy)) {
//			System.out.println("先压缩再解压以后字符串和原来的是一模一样的");
//		}
	}

	/**
	 * 字符串的压缩
	 * 
	 * @param str 待压缩的字符串
	 * @return 返回压缩后的字符串
	 * @throws IOException
	 */
	public static String compress(String str) throws IOException {
		if (null == str || str.length() <= 0) {
			return str;
		}
		// 创建一个新的 byte 数组输出流
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		// 使用默认缓冲区大小创建新的输出流
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		// 将 b.length 个字节写入此输出流
		gzip.write(str.getBytes());
		gzip.close();
		// 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串
		return out.toString("ISO-8859-1");
	}

	/**
	 * 字符串的解压
	 * 
	 * @param str 压缩的字符串
	 * @return 返回解压缩后的字符串
	 * @throws IOException
	 */
	public static String unCompress(byte[] str) throws IOException {
//		if (null == str || str.length() <= 0) {
//			return str;
//		}
		// 创建一个新的 byte 数组输出流
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		// 创建一个 ByteArrayInputStream，使用 buf 作为其缓冲区数组
		ByteArrayInputStream in = new ByteArrayInputStream(str);
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
	}
}
