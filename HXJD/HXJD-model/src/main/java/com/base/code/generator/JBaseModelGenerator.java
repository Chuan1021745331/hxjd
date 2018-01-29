package com.base.code.generator;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.generator.BaseModelGenerator;
import com.jfinal.plugin.activerecord.generator.ColumnMeta;
import com.jfinal.plugin.activerecord.generator.TableMeta;
/**
 * 
 * All rights Reserved, Designed By hxjd
 * @类名: JBaseModelGenerator.java   
 * @包名: com.base.code.generator   
 * @描述: Model类处理   
 * @所属: 华夏九鼎     
 * @日期: 2018年1月29日 上午9:36:38   
 * @版本: V1.0 
 * @创建人：Administrator 
 * @修改人：Administrator
 * @版权: 2018 hxjd Inc. All rights reserved. 
 * @注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
public class JBaseModelGenerator extends BaseModelGenerator {

	public JBaseModelGenerator(String baseModelPackageName,
			String baseModelOutputDir) {
		super(baseModelPackageName, baseModelOutputDir);
		
		this.packageTemplate = "package %s;%n%n";

		this.classDefineTemplate = "/**%n"
				+ " *  Auto generated by JPress, do not modify this file.%n"
				+ " */%n"
				+ "@SuppressWarnings(\"serial\")%n"
				+ "public abstract class %s<M extends %s<M>> extends JModel<M> implements IBean {%n%n"
				+ "\tpublic static final String CACHE_NAME = \"%s\";%n"
				+ "\tpublic static final String METADATA_TYPE = \"%s\";%n%n"
				
				+ "\tpublic static final String ACTION_ADD = \"%s:add\";%n"
				+ "\tpublic static final String ACTION_DELETE = \"%s:delete\";%n"
				+ "\tpublic static final String ACTION_UPDATE = \"%s:update\";%n%n"
				
				+ "\tpublic void removeCache(Object key){%n"
				+ "\t\tif(key == null) return;%n"
				+ "\t\tCacheKit.remove(CACHE_NAME, key);%n"
				+ "\t}%n%n"
				
				+ "\tpublic void putCache(Object key,Object value){%n"
				+ "\t\tCacheKit.put(CACHE_NAME, key, value);%n"
				+ "\t}%n%n"
				
				+ "\tpublic M getCache(Object key){%n"
				+ "\t\treturn CacheKit.get(CACHE_NAME, key);%n"
				+ "\t}%n%n"
				
				+ "\tpublic M getCache(Object key,IDataLoader dataloader){%n"
				+ "\t\treturn CacheKit.get(CACHE_NAME, key, dataloader);%n"
				+ "\t}%n%n"
		
				+ "\t@Override%n"
				+ "\tpublic boolean equals(Object o) {%n"
				+ "\t\tif(o == null){ return false; }%n"
				+ "\t\tif(!(o instanceof %s<?>)){return false;}%n%n"
				+ "\t\t%s<?> m = (%s<?>) o;%n"
				+ "\t\tif(m.getId() == null){return false;}%n%n"
				+ "\t\treturn m.getId().compareTo(this.getId()) == 0;%n"
				+ "\t}%n%n"
				
				+ "\t@Override%n"
				+ "\tpublic boolean save() {%n"
				+ "\t\tboolean saved = super.save();%n"
				+ "\t\tif (saved) { MessageKit.sendMessage(ACTION_ADD, this); }%n"
				+ "\t\treturn saved;%n"
				+ "\t}%n%n"
				
				+ "\t@Override%n"
				+ "\tpublic boolean delete() {%n"
				+ "\t\tboolean deleted = super.delete();%n"
				+ "\t\tif (deleted) { MessageKit.sendMessage(ACTION_DELETE, this); }%n"
				+ "\t\treturn deleted;%n"
				+ "\t}%n%n"
				
				+ "\t@Override%n"
				+ "\tpublic boolean deleteById(Object idValue) {%n"
				+ "\t\tboolean deleted = super.deleteById(idValue);%n"
				+ "\t\tif (deleted) { MessageKit.sendMessage(ACTION_DELETE, this); }%n"
				+ "\t\treturn deleted;%n"
				+ "\t}%n%n"
				
				+ "\t@Override%n"
				+ "\tpublic boolean update() {%n"
				+ "\t\tboolean update = super.update();%n"
				+ "\t\tif (update) { MessageKit.sendMessage(ACTION_UPDATE, this); }%n"
				+ "\t\treturn update;%n"
				+ "\t}%n%n"
				;
		this.importTemplate = "import com.base.message.MessageKit;%n"
				+ "import com.base.model.core.JModel;%n"
				+ "import java.math.BigInteger;%n%n"
				+ "import com.jfinal.plugin.activerecord.IBean;%n"
				+ "import com.jfinal.plugin.ehcache.CacheKit;%n"
				+ "import com.jfinal.plugin.ehcache.IDataLoader;%n%n";
	}

	@Override
	protected void genClassDefine(TableMeta tableMeta, StringBuilder ret) {
		ret.append(String.format(classDefineTemplate, tableMeta.baseModelName,
				tableMeta.baseModelName, tableMeta.name.split("_")[1],tableMeta.name.split("_")[1],tableMeta.name.split("_")[1],tableMeta.name.split("_")[1], tableMeta.name.split("_")[1],tableMeta.baseModelName,
				tableMeta.baseModelName,tableMeta.baseModelName));
	}
	
//	protected String idGetterTemplate =
//			"\tpublic java.math.BigInteger getId() {%n" +
//				"\t\tObject id = get(\"id\");%n" +
//				"\t\tif (id == null)%n" +
//				"\t\t\treturn null;%n%n" +
//				"\t\treturn id instanceof BigInteger ? (BigInteger)id : new BigInteger(id.toString());%n" +
//			"\t}%n%n";
	
	
	@Override
	protected void genGetMethodName(ColumnMeta columnMeta, StringBuilder ret) {
//		if("id".equals(columnMeta.attrName)){
//			ret.append(String.format(idGetterTemplate));
//		}else{
			String getterMethodName = "get" + StrKit.firstCharToUpperCase(columnMeta.attrName);
			String getter = String.format(getterTemplate, columnMeta.javaType, getterMethodName, columnMeta.name);
			ret.append(getter);
//		}
	}

}
