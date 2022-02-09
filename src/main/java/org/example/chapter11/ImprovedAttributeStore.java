package org.example.chapter11;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class ImprovedAttributeStore {

    private final Map<String, String> attributes = new ConcurrentHashMap<>();

    // 스레드 안전성이 확보된 ConcurrentHashMap 클래스를 사용하면
    // ImprovedAttributeStore 클래스의 스레드 안전성을 attributes 변수에게 위임할 수 있다.
    // - 동기화나 락에 대해 신경쓰지 않아도 된다.
    // - 락을 점유하는 시간을 최소화할 수 있다.
    public boolean userLocationMatches(String name, String regexp) {
        String key = "users." + name + ".location";
        String location = attributes.get(key);
        if (location == null) {
            return false;
        }
        return Pattern.matches(regexp, location);
    }
}
