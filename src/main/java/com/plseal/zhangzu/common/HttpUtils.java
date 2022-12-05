package com.plseal.zhangzu.common;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HttpUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);
    private static final String[] IP_HEADERS = {
        "X-Forwarded-For",
        "Proxy-Client-IP",
        "WL-Proxy-Client-IP",
        "HTTP_X_FORWARDED_FOR",
        "HTTP_X_FORWARDED",
        "HTTP_X_CLUSTER_CLIENT_IP",
        "HTTP_CLIENT_IP",
        "HTTP_FORWARDED_FOR",
        "HTTP_FORWARDED",
        "HTTP_VIA",
        "REMOTE_ADDR"

        // you can add more matching headers here ...
    };

    private HttpUtils() {
        // nothing here ...
    }

    public static String getRequestIP(HttpServletRequest request) {
        for (String header: IP_HEADERS) {
            String value = request.getHeader(header);
            if (value == null || value.isEmpty()) {
                continue;
            }
            String[] parts = value.split("\\s*,\\s*");
            return parts[0];
        }
        return request.getRemoteAddr();
    }

    public static boolean isAdmin(HttpServletRequest request){
        String client_ip = getRequestIP(request);
        logger.info("[HttpUtils][isAdmin][client_ip]"+client_ip);
        boolean result = client_ip.equals("122.133.196.154") || client_ip.equals("127.0.0.1");
        return result;
    }
}