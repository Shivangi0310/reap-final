package com.ttn.reap.enums;

import java.util.HashMap;
import java.util.Map;

public enum Badge {

    GOLD(30),
    SILVER(20),
    BRONZE(10);

    private int weightAge;

    private static Map<Integer, Object> map = new HashMap<>();

    Badge(int weightAge) {
        this.weightAge = weightAge;
    }

    static {
        for (Badge badgeType : Badge.values()) {
            map.put(badgeType.weightAge, badgeType);
        }
    }

    public static Badge valueOf(int badge) {
        return (Badge) map.get(badge);
    }

    public Integer getBadgeWeight() {
        return weightAge;
    }
}
