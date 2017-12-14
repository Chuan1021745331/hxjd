package com.baidu.ueditor.hunter;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.MultiState;
import com.baidu.ueditor.define.State;
import com.base.utils.ConfigParamsUtil;
import com.jfinal.kit.PathKit;

public class FileManager {

	private String dir = null;
	private String rootPath = null;
	private String[] allowFiles = null;
	private int count = 0;
	private static String basePath =  PathKit.getWebRootPath();
	private static String imgPath = ConfigParamsUtil.getInstance().getProperty(ConfigParamsUtil.IMGPATH);
	private static String imgUrl = ConfigParamsUtil.getInstance().getProperty(ConfigParamsUtil.IMGURL);

	public FileManager(Map<String, Object> conf) {

//		this.rootPath = (String) conf.get("rootPath");
//		this.dir = this.rootPath + (String) conf.get("dir");
		this.rootPath = basePath;
//		String a = PathKit.getWebRootPath();
		this.dir = basePath+imgPath+"/images/";
		this.allowFiles = this.getAllowFiles(conf.get("allowFiles"));
		this.count = (Integer) conf.get("count");

	}

	public State listFile( HttpServletRequest request,int index) {

		File dir = new File(this.dir);
		State state = null;

		if (!dir.exists()) {
			return new BaseState(false, AppInfo.NOT_EXIST);
		}

		if (!dir.isDirectory()) {
			return new BaseState(false, AppInfo.NOT_DIRECTORY);
		}

		Collection<File> list = FileUtils.listFiles(dir, this.allowFiles, true);

		if (index < 0 || index > list.size()) {
			state = new MultiState(true);
		} else {
			Object[] fileList = Arrays.copyOfRange(list.toArray(), index, index + this.count);
			state = this.getState(request,fileList);
		}

		state.putInfo("start", index);
		state.putInfo("total", list.size());

		return state;

	}

	private State getState( HttpServletRequest request,Object[] files) {

		MultiState state = new MultiState(true);
		BaseState fileState = null;

		File file = null;

		for (Object obj : files) {
			if (obj == null) {
				break;
			}
			file = (File) obj;
			fileState = new BaseState(true);
			fileState.putInfo("url", imgUrl+""+PathFormat.format(this.getPath(file)));
			state.addState(fileState);
		}

		return state;

	}

	private String getPath(File file) {

		String path = file.getAbsolutePath();
		// 在windows下，path的路径斜线是'\'是这样的，所以，要把rootPath里面/线换是File.separator
		// rootPath是通过application.getRealPath("/");得到的，所以全是这样的斜线/。
		String rootPath = this.rootPath.replace("/", File.separator);
		return path.replace(rootPath, "/");

	}

	private String[] getAllowFiles(Object fileExt) {

		String[] exts = null;
		String ext = null;

		if (fileExt == null) {
			return new String[0];
		}

		exts = (String[]) fileExt;

		for (int i = 0, len = exts.length; i < len; i++) {

			ext = exts[i];
			exts[i] = ext.replace(".", "");

		}

		return exts;

	}

}
