/**
 * @author DanBai
 * @create 2020-05-14 17:17
 * @desc 响应结构
 **/
package cn.p00q.wallpaper.entity;

import cn.p00q.wallpaper.utils.ReturnMap;

import java.io.Serializable;

public class Response implements Serializable {
    private int code;
    private  String msg;
    private Object data;
    private static int OK_CODE=0;
    private static int ERR_CODE=1;
    private static String OK_MSG="成功";
    private static String ERR_MSG="错误";
    /**
     * 参数有误
     */
    public static final int ERR_PARAMETER=100;
    /**
     * 存在重复
     */
    public static final int ERR_DUPLICATION=101;
    public int getCode() {
        return code;
    }

    public Response setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Response setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Response setData(Object data) {
        this.data = data;
        return this;
    }
    public Response setData(ReturnMap data){
        this.data = data.getData();
        return this;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public Response(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public static Response Ok(){
        return new Response(OK_CODE,OK_MSG,null);
    }
    public static Response Ok(String msg){
        return new Response(OK_CODE,msg,null);
    }
    public static Response Ok(String msg,Object data){
        return new Response(OK_CODE,msg,data);
    }
    public static Response Err(){
        return new Response(ERR_CODE,ERR_MSG,null);
    }
    public static Response Err(String msg){
        return new Response(ERR_CODE,msg,null);
    }
    public static Response Err(String msg,Object data){
        return new Response(ERR_CODE,msg,data);
    }
}
