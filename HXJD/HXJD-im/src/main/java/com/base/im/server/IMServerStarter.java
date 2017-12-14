package com.base.im.server;

import java.io.IOException;

import com.base.im.common.Const;
import com.base.im.common.IMPacket;
import com.jfinal.plugin.IPlugin;
import org.tio.server.AioServer;
import org.tio.server.ServerGroupContext;
import org.tio.server.intf.ServerAioHandler;
import org.tio.server.intf.ServerAioListener;

public class IMServerStarter implements IPlugin {
	//handler, 包括编码、解码、消息处理
    public static ServerAioHandler<Object, IMPacket, Object> aioHandler = null;
    //事件监听器，可以为null，但建议自己实现该接口，可以参考showcase了解些接口
    public static ServerAioListener<Object, IMPacket, Object> aioListener = null;
    //一组连接共用的上下文对象
    public static ServerGroupContext<Object, IMPacket, Object> serverGroupContext = null;
    //aioServer对象
    public static AioServer<Object, IMPacket, Object> aioServer = null;
    //有时候需要绑定ip，不需要则null
    public static String serverIp = null;
    //监听的端口
    public static int serverPort = Const.PORT;

	public static void main(String[] args) throws IOException {
		aioHandler = new IMServerAioHandler();
		aioListener = new IMServerAioListener(); // 可以为空
		serverGroupContext = new ServerGroupContext<>(aioHandler, aioListener);
		aioServer = new AioServer<>(serverGroupContext);
		aioServer.start(serverIp, serverPort);
	}

	@Override
	public boolean start() {
		aioHandler = new IMServerAioHandler();
		aioListener = new IMServerAioListener(); // 可以为空
		serverGroupContext = new ServerGroupContext<>(aioHandler, aioListener);
		aioServer = new AioServer<>(serverGroupContext);
		try {
			aioServer.start(serverIp, serverPort);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean stop() {
		return aioServer.stop();
	}
}
