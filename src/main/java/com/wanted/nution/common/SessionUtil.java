package com.wanted.nution.common;

import org.springframework.http.RequestEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SessionUtil {

    public interface Parsers {
        String parse(RequestEntity<Map> req);
    }


    private static Map<String, Parsers> parserSelector = new HashMap<>(){{
        put("Chrome", req -> {
            String regex = "cookie";
            Pattern pattern = Pattern.compile(regex);
            List<String> headerKey = req.getHeaders().keySet().stream().collect(Collectors.toList());

            String headerVal = req.getHeaders().get(headerKey.stream().filter(x -> {
            Matcher matcher = pattern.matcher(x);
            if (matcher.find()) {
                return true;
            }
            return false;
        }).collect(Collectors.toList()).get(0)).get(0).split(";")[0].split("=")[1];

            return headerVal;
        });

        put("Safari", req -> {
            String regex = "cookie";
            Pattern pattern = Pattern.compile(regex);
            List<String> headerKey = req.getHeaders().keySet().stream().collect(Collectors.toList());

            String headerVal = req.getHeaders().get(headerKey.stream().filter(x -> {
                Matcher matcher = pattern.matcher(x);
                if (matcher.find()) {
                    return true;
                }
                return false;
            }).collect(Collectors.toList()).get(0)).get(0).split(";")[0].split("=")[0];

            return headerVal;
        });

        put("curl", req -> {
            return "none";
        });
    }};
    


    /**
     * requestEntity를 받아 cookie나 token이 포함되는 문자열을 키로 가지는 경우
     * 세션 식별자로 이용하기 위해 해당 문자열(key)을 반환
     *
     */

    public static String getSessionKey(RequestEntity<Map> req) {
        String regex = "Safari|Chrome|curl";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(req.getHeaders().get("user-agent").get(0));
        if (matcher.find()) {
            return parserSelector.get(matcher.group()).parse(req);
        }

        return null;
    }


}
