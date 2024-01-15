package net.southplus.southplusmaid.ui.control;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalWebView {
    private static WebView webView;
    private static final String COOKIE_FILE="cookies.json";
    public LocalWebView() {
        webView=new WebView();
        CookieManager cookieManager=new CookieManager();
        ObjectMapper objectMapper=new ObjectMapper();
        File file = new File(COOKIE_FILE);
        if (file.exists()){
            try {
                Map<URI, List<LocalHttpCookie>> map = objectMapper.readValue(file, new TypeReference<Map<URI, List<LocalHttpCookie>>>() {
                });
                map.keySet().forEach(uri -> {
                    List<LocalHttpCookie> httpCookies = map.get(uri);
                    httpCookies.forEach(localCookie -> {
                        HttpCookie httpCookie = localCookie.getHttpCookie();
                        if (!httpCookie.hasExpired()) {
                            cookieManager.getCookieStore().add(uri,httpCookie);
                        }
                    });
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        com.sun.webkit.network.CookieManager.setDefault(cookieManager);
    }


    public WebView getWebView() {
        return webView;
    }


    public static void saveCookies(){
        if (webView == null)
            return;
        CookieManager aDefault = (CookieManager) CookieHandler.getDefault();
        CookieStore cookieStore = aDefault.getCookieStore();
        Map<URI,List<HttpCookie>> map=new HashMap<>();
        cookieStore.getURIs().forEach(uri -> {
            map.put(uri,cookieStore.get(uri));
        });

        ObjectMapper mapper=new ObjectMapper();
        try {
            mapper.writeValue(new File(COOKIE_FILE),map);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
