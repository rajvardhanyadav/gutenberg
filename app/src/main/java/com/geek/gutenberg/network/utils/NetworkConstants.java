package com.geek.gutenberg.network.utils;

public class NetworkConstants {
    public static class Retrofit {
        public static final String BASE_URL = "http://skunkworks.ignitesol.com:8000";
        public static int TIMEOUT = 30; //timeunit seconds
    }
    public static class QueryParams{
        public static final String PARAM_MIME_TYPE = "mime_type";
        public static final String PARAM_TOPIC = "topic";
        public static final String PARAM_SEARCH = "search";
        public static final String PARAM_PAGE = "page";
    }
    public static class BookFormats{
        public static final String[] INVALID_FORMATS = {".zip"};
    }
}
