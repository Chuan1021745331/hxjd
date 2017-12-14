package com.base.utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * GSON工具类
 * 
 * @author zy
 * 
 */
public class GsonUtil {

	private static Gson gson = null;
	static {
		gson = new GsonBuilder().setFieldNamingPolicy(
				FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
	}

	/**
	 * 小写下划线的格式解析JSON字符串到对象
	 * <p>
	 * 例如 is_success->isSuccess
	 * </p>
	 * 
	 * @param json
	 * @param classOfT
	 * @return
	 */
	public static <T> T fromJsonUnderScoreStyle(String json, Class<T> classOfT) {
		return gson.fromJson(json, classOfT);
	}

	/**
	 * JSON字符串转为Map<String,String>
	 * 
	 * @param json
	 * @return
	 */
	@SuppressWarnings("all")
	public static <T> T fronJson2Map(String json) {
		return gson.fromJson(json, new TypeToken<Map<String, String>>() {
		}.getType());
	}

	/**
	 * 小写下划线的格式将对象转换成JSON字符串
	 * 
	 * @param src
	 * @return
	 */
	public static String toJson(Object src) {
		return gson.toJson(src);
	}
	public static <T> String mapToJson(Map<String, String> map) {
		Gson gson = new Gson();
		String jsonStr = gson.toJson(map);
//		List<String> keys = new ArrayList<String>(map.keySet());
//        String prestr = "";
//		for (int i = 0; i < keys.size(); i++) {
//			String key = keys.get(i);
//            String value = map.get(key);
//            if (i == 0) {
//                prestr = prestr + key + ":\"" + value+"\"";
//            } else {
//            	prestr = prestr +","+ key + ":\"" + value + "\"";
//            }
//		}
		return jsonStr.toString();
	}
	@SuppressWarnings("rawtypes")
	public static HashMap<String, Object> toHashMap(Object object) {  
       HashMap<String, Object> data = new HashMap<String, Object>();  
       // 将json字符串转换成jsonObject  
       JSONObject jsonObject = JSONObject.fromObject(object);  
       Iterator it = jsonObject.keys();  
       // 遍历jsonObject数据，添加到Map对象  
       while (it.hasNext()){  
           String key = String.valueOf(it.next());  
           Object value = jsonObject.get(key);  
           data.put(key, value);  
       }  
       return data;  
   }
	
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public static List<Map<String, Object>> jsonToListMap(String jsonArrayData){
		//JSONArray  
        JSONArray jsonArray = JSONArray.fromObject(jsonArrayData);  
  
        List<Map<String,Object>> mapListJson = (List)jsonArray;  
        for (int i = 0; i < mapListJson.size(); i++) {  
            Map<String,Object> obj=mapListJson.get(i);  
              
            for(Entry<String,Object> entry : obj.entrySet()){  
                String strkey1 = entry.getKey();  
                Object strval1 = entry.getValue();
                
            }  
        } 
        return mapListJson;
	}
}
