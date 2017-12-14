package com.base.message.plugin;

import java.util.List;

import com.base.message.MessageListener;
import com.base.message.MessageManager;
import com.base.utils.ClassUtils;
import com.jfinal.plugin.IPlugin;


public class MessagePlugin implements IPlugin {


	@Override
	public boolean start() {
		autoRegister();
		return true;
	}

	private void autoRegister() {
		List<Class<MessageListener>> list = ClassUtils.scanSubClass(MessageListener.class, true);
		if (list != null && list.size() > 0) {
			for (Class<MessageListener> clazz : list) {
				MessageManager.me().registerListener(clazz);
			}
		}
	}

	@Override
	public boolean stop() {
		return true;
	}

}
