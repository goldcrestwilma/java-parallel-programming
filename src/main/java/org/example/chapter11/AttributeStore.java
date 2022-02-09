package org.example.chapter11;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.example.CallbackTest.SyncOrderServiceImpl;

public class AttributeStore {

    private final Map<String, String> attributes = new HashMap<>();

    // 필요 이상으로 락을 계속 확보하고 있다.
    // 락이 필요한 부분은 attributes.get() 메서드를 호출하는 부분 뿐이다.
    public synchronized boolean userLocationMatches(String name, String regexp) {
        String key = "users." + name + ".location";
        String location = attributes.get(key);
        if (location == null) {
            return false;
        }
        return Pattern.matches(regexp, location);
    }
}
