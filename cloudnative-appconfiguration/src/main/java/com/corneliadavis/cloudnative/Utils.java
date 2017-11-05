package com.corneliadavis.cloudnative;

import org.springframework.beans.factory.annotation.Value;

public class Utils {

    @Value("${INSTANCE_IP}")
    private String ip;
    @Value("${INSTANCE_PORT}")
    private String mappedPort;
    @Value("${server.port:8080}")
    private String serverPort;

    public String ipTag() {

        String port;
        if (mappedPort.equals("none"))
            port = serverPort;
        else
            port = mappedPort;

        return "[" + ip + ":" + port +"] ";
    }
}
