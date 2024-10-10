package com.galvatron.users.utils;

import jakarta.servlet.http.HttpServletRequest;

public class HttpServletRequestUtil {

    public static String getDeviceType(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (userAgent != null) {
            if (userAgent.contains("Mobile")) {
                return "MOBILE";
            } else if (userAgent.contains("Tablet")) {
                return "TABLET";
            } else if(userAgent.contains("Postman")) {
                return "POSTMAN";
            }else {
                return "WEB";
            }
        }
        return "UNKNOWN";
    }

    public static String getDeviceName(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }

    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
