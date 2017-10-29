package com.corneliadavis.cloudnative;

public class Utils {

    public static String ipTag () {
        return "[IP=" + System.getenv("INSTANCE_IP") +"] ";
    }
}
