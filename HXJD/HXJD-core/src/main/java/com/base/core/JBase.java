package com.base.core;

import java.io.File;

import com.base.freemarker.JFunction;
import com.base.freemarker.JTag;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PathKit;
import com.jfinal.render.FreeMarkerRender;

public class JBase {
	public static void addTag(String key, JTag tag) {
		FreeMarkerRender.getConfiguration().setSharedVariable(key, tag);
	}

	public static void addFunction(String key, JFunction function) {
		FreeMarkerRender.getConfiguration().setSharedVariable(key, function);
	}

	public static void renderImmediately() {
		FreeMarkerRender.getConfiguration().setTemplateUpdateDelayMilliseconds(0);
	}

	private static boolean isInstalled = false;

	public static boolean isInstalled() {
		if (!isInstalled) {
			File dbConfig = new File(PathKit.getRootClassPath(), "db.properties");
			isInstalled = dbConfig.exists();
		}
		return isInstalled;
	}

	public static boolean isDevMode() {
		return JFinal.me().getConstants().getDevMode();
	}

	private static boolean isLoaded = false;

	public static boolean isLoaded() {
		return isLoaded;
	}

	public static void loadFinished() {
		isLoaded = true;
	}
}
