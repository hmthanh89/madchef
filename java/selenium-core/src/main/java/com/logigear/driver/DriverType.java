package com.logigear.driver;

import java.util.Arrays;

public enum DriverType {
    Chrome, Firefox, IE, Edge, Safari, Android, IOS;

    public static String asString() {
        return Arrays.toString(DriverType.values()).replaceAll("^.|.$", "");
    }
}
