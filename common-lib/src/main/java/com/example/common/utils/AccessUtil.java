package com.example.common.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class AccessUtil {

    /**
     * 获取请求真实IP
     * 不使用request.getRemoteAddr(),避免使用代理软件方式障眼真实IP
     * 如果通过多级反向代理，X-Forwarded-For的值会是多个，X-Forwarded-For中第一个非unknown为有效真实IP
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取请求地址
     * @param request
     * @param flg 0->完整URL；1->域名；2->URI
     * @return
     */
    public static String getPath(HttpServletRequest request, int flg){
        StringBuffer url = request.getRequestURL();
        String tempContextUrl = request.getRequestURI();
        if (flg == 1){
            tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();
        }
        if (flg == 2){
            tempContextUrl = url.delete(0,url.length() - request.getRequestURI().length()).toString();
        }
        return tempContextUrl;
    }

    /**
     * 获取请求客户的主机名
     * @param request
     * @return
     */
    public static String getHost(HttpServletRequest request){
        return request.getRemoteHost();
    }
}


