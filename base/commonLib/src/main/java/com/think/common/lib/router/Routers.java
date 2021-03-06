package com.think.common.lib.router;

/**
 * @author think
 * @date 2018/5/10 上午9:31
 */

public class Routers {

    public static class App {
        private static final String BASE = "/app";
        public static final String MAIN = BASE + "/main";
    }

    public static class Service {
        private static final String BASE = "/service";
        public static final String RETROFIT = BASE + "/retrofit";
        public static final String OKHTTP = BASE + "/okhttp";
    }
}
