/**
 * @author DanBai
 * @create 2020-05-17 19:52
 * @desc 返回map
 **/
package cn.p00q.wallpaper.utils;

import cn.p00q.wallpaper.entity.Response;

import java.util.HashMap;
import java.util.Map;

public class ReturnMap {
    private Map data;
    private boolean succeed;
    private String msg;

    public boolean isSucceed() {
        return succeed;
    }

    public void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ReturnMap(){
        this.data=new HashMap();
    }
    public ReturnMap(Map data){
        this.data=data;
    }
    public Object get(String key) {
        return data.get(key);
    }
    public <T> T get(String key,Class<T> clazz) {
        return (T)data.get(key);
    }
    public Map put(String key,Object obj){
        this.data.put(key,obj);
        return data;
    }
    public static ReturnMap getReturnMap(){
        return new ReturnMap();
    }

    public Map getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }
    public static ReturnMap succeed(String msg){
        ReturnMap returnMap=new ReturnMap();
        returnMap.setSucceed(true);
        returnMap.setMsg(msg);
        return returnMap;
    }
    public static ReturnMap error(String msg){
        ReturnMap returnMap=new ReturnMap();
        returnMap.setSucceed(false);
        returnMap.setMsg(msg);
        return returnMap;
    }
    public Response toResponse(){
        if(succeed){
            return Response.Ok(msg,data);
        }
        return Response.Err(msg,data);
    }
}
