package com.ypf.kuaicha.http;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GsonTools {

    public GsonTools() {}

    public static String createGsonString(Object object) {
        if (object == null) {
            return null;
        }
        
        try {
            Gson gson = new Gson();
            String gsonString = gson.toJson(object);
            return gsonString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <E> E changeGsonToBean(String gsonString, Class<E> cls) {
        if (TextUtils.isEmpty(gsonString)) {
            return null;
        }
        try {
            Gson gson = new Gson();
            JsonParser sParse = new JsonParser();
            JsonObject rootObj = sParse.parse(gsonString).getAsJsonObject();
            E t = gson.fromJson(rootObj, cls);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } catch(Throwable throwable){
            throwable.printStackTrace();
            return null;
        }
    }

    //    public static <T> List<T> changeGsonToList(String gsonString, Class<T> cls) {
    //        Gson gson = new Gson();
    //        List<T> list_person = gson.fromJson(gsonString, new TypeToken<List<T>>() {
    //        }.getType());
    //        return list_person;
    //    }

    //	public static ArrayList<FreeWalkerRecoHotelInfo> getFreeWalkerRecoHotelInfos(String json){
    //		Type listType = new TypeToken<ArrayList<FreeWalkerRecoHotelInfo>>(){}.getType();
    //		Gson gson = new Gson();
    //		ArrayList<FreeWalkerRecoHotelInfo> freeWalkerRecoHotelInfos = gson.fromJson(json, listType);
    //		return freeWalkerRecoHotelInfos;
    //	}
    //    public static ArrayList<T> getFreeWalkerRecoHotelInfos(String json) {
    //        Type listType = new TypeToken<ArrayList<T>>() {}.getType();
    //        Gson gson = new Gson();
    //        ArrayList<T> freeWalkerRecoHotelInfos = gson.fromJson(json, listType);
    //        return freeWalkerRecoHotelInfos;
    //    }

    //    public static List<Map<String, Object>> changeGsonToListMaps(String gsonString) {
    //        List<Map<String, Object>> list = null;
    //        Gson gson = new Gson();
    //        list = gson.fromJson(gsonString, new TypeToken<List<Map<String, Object>>>() {
    //        }.getType());
    //        return list;
    //    }

}
