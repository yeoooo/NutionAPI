package com.wanted.nution.common;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@Getter
@ToString
public class SessionManager {
    @Getter
    private static Map<String, Session> sessions = new HashMap<>();
    public Session addSession(String token) {
        if (!sessions.containsKey(token)) {
            sessions.put(token, new Session());

        }
        return sessions.get(token);
    }

    public Session getSession(String token) {
        if (sessions.containsKey(token)) {
            return sessions.get(token);
        }else{
            log.warn("No Such Session!");
            return null;
        }
    }
}
