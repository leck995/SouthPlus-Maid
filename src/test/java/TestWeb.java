
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestWeb extends Application {
    public static final String SOUTH_PLUS_HOST="https://wap.baidu.com/";
    public static final String COOKIE= "_ga=GA1.2.736480112.1700998828; eb9e6_ck_info=%2F%09; eb9e6_cknum=AVUBAlRbBAVUA206UQkGUFIOAVtQBwRVVFNXB1MLBwgGUAUFAgQDCFcAVAY%3D; eb9e6_winduser=AVUGAVFSBThWBABWBAAHUVcAAVoBVldXD1UEAF0AVQJSBQUPAFEJBW8%3D; eb9e6_threadlog=%2C73%2C9%2C201%2C128%2C; _ga_3G0BZEH5V0=GS1.2.1704181033.15.1.1704181168.0.0.0; peacemaker=1; eb9e6_readlog=%2C2058769%2C2055538%2C2054811%2C2054269%2C2053873%2C2052430%2C2046826%2C2035308%2C2012659%2C; eb9e6_lastpos=other; eb9e6_ol_offset=136479; eb9e6_lastvisit=508%091704901838%09%2Fsearch.php%3F";

    private static final String cookie ="PSTM=1700992471; BIDUPSID=DE695F8EDB39D455A30103F02E061BDC; BAIDUID=0B7EEDD4E810AFC2069488200E455003:SL=0:NR=10:FG=1; BD_UPN=12314753; H_PS_PSSID=40115; BDUSS=25HZjRLLVdtfkZpU2NkMEZyMmFlUEFmS35IR2xNZ3NJMVV5SjRnbnJaTGdMTVpsSVFBQUFBJCQAAAAAAAAAAAEAAABCgxY3eWlxaXU5OTUAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAOCfnmXgn55lQ; BDUSS_BFESS=25HZjRLLVdtfkZpU2NkMEZyMmFlUEFmS35IR2xNZ3NJMVV5SjRnbnJaTGdMTVpsSVFBQUFBJCQAAAAAAAAAAAEAAABCgxY3eWlxaXU5OTUAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAOCfnmXgn55lQ; BDORZ=FFFB88E999055A3F8A630C64834BD6D0; BA_HECTOR=0h25008l8k2k01ak2g242h25nid0q31iq34ti1t; ZFY=Si8q1tuHg0Kl58wpAkgGpBnkDMmJSluGPZ8UADgkacg:C; BAIDUID_BFESS=0B7EEDD4E810AFC2069488200E455003:SL=0:NR=10:FG=1; BD_CK_SAM=1; PSINO=3; delPer=0; BDRCVFR[C0sZzZJZb70]=mk3SLVN4HKm; sug=3; sugstore=0; ORIGIN=2; bdime=0; H_PS_645EC=75481B1idrmIlbpThOCo18a3TtTlt9Pl%2FEozl605OfNNjmUXNlp%2F6%2FX17DfDr7S2jUsG7Fc; BDSVRTM=0\n";
    @Override
    public void start(Stage stage) throws Exception {
        System.setProperty("https.proxyHost","127.0.0.1");
        System.setProperty("https.proxyPort","7890");

        System.setProperty("https.proxyHost","127.0.0.1");
        System.setProperty("https.proxyPort","7890");

        URL url=new URL(SOUTH_PLUS_HOST);
        WebView webView=new WebView();
        WebEngine engine = webView.getEngine();
        CookieManager cookieManager=new CookieManager();
/*        ObjectMapper objectMapper=new ObjectMapper();
        File file = new File("a.json");
        if (file.exists()){
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
        }*/


        String s="csrfToken=riptEJmqnG0eA70sGxYjy1jN; BAIDUID=2AD23CDA49C3D76EE2C494964771F3C4:FG=1; BAIDUID_BFESS=2AD23CDA49C3D76EE2C494964771F3C4:FG=1; newlogin=1; BDUSS=3dnVWdZQ24yMldWWjFMQ1ItNnVTbElQcExIYzFWUW8teXExaGxVVGZiQVp1TXBsSVFBQUFBJCQAAAAAAAAAAAEAAABCgxY3eWlxaXU5OTUAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABkro2UZK6NlV; BDUSS_BFESS=3dnVWdZQ24yMldWWjFMQ1ItNnVTbElQcExIYzFWUW8teXExaGxVVGZiQVp1TXBsSVFBQUFBJCQAAAAAAAAAAAEAAABCgxY3eWlxaXU5OTUAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABkro2UZK6NlV; STOKEN=0b8d577829a9518d109b160c475c886ce75e7732dec2926a1af8d3df764d3d32; PANPSC=1995649188956509461%3ACU2JWesajwDDYVid3JSZc8Wn1hbHg1WZ1P0SDcvUfQj3%2BQUzJcNZjZpXVAfLeXHbojI05axXZB56g9ChlHbsrKHfxtc%2FnrOqiVleys3zxXZFah9uWgECZ5U8UWh0S%2BHQyDc2vJUZq3Wi%2BY1X7udr9G2gud9gLjYtpqvB0tQPnfltcQe%2BbgwEaTCo8bcT1HMK; ndut_fmt=B950883BD78AC41E5046486430FD0C8BEA27C5F20B9BDF26B4BE8E459AEC65D5; ab_sr=1.0.1_ZmNkYmRjNWEyM2I1M2JhMzg1ZWY0ZGY2MjQxM2IyNThjY2NmODRmZDYxNGRiZjAzYWJlMmFlOGJkZmExNWE5ODViMjhlZGMyZTk3NzFmYzZhOTkwMzcyYjFhNTc5MzlkZjVlODA0NmM4YzJkN2Y4ZDAyOTE1OWQwNjFhNTdhNzhhYzUwMzBhY2MwMDZkNGJhYzI0MmVjMmE3ZDAzNWUxN2JmYTU1ZTA2ZWExOTZkMTEyYWVhYmJmYTJjOTdmZWE1";
        //String s="_ga=GA1.2.736480112.1700998828; eb9e6_ck_info=%2F%09; eb9e6_cknum=AVUBAlRbBAVUA206UQkGUFIOAVtQBwRVVFNXB1MLBwgGUAUFAgQDCFcAVAY%3D; eb9e6_winduser=AVUGAVFSBThWBABWBAAHUVcAAVoBVldXD1UEAF0AVQJSBQUPAFEJBW8%3D; eb9e6_threadlog=%2C73%2C9%2C201%2C128%2C; _ga_3G0BZEH5V0=GS1.2.1704181033.15.1.1704181168.0.0.0; peacemaker=1; eb9e6_readlog=%2C2058769%2C2055538%2C2054811%2C2054269%2C2053873%2C2052430%2C2046826%2C2035308%2C2012659%2C; eb9e6_lastpos=other; eb9e6_ol_offset=136479; eb9e6_lastvisit=508%091704901838%09%2Fsearch.php%3F";
        String[] strings = s.split(";");
        for (String string : strings) {
            String[] strings1 = string.split("=");
            HttpCookie httpCookie = new HttpCookie(strings1[0],strings1[1]);

            cookieManager.getCookieStore().add(new URL("https://pan.baidu.com").toURI(),httpCookie);
        }

        com.sun.webkit.network.CookieManager.setDefault(cookieManager);

        engine.setJavaScriptEnabled(true);

        engine.load(url.toString());


        stage.setScene(new Scene(webView));
        stage.setWidth(1200);
        stage.setHeight(1200);
        stage.show();
        stage.setOnCloseRequest(windowEvent -> {
            CookieManager aDefault = (CookieManager) CookieHandler.getDefault();
            CookieStore cookieStore = aDefault.getCookieStore();
            Map<URI,List<HttpCookie>> map=new HashMap<>();
            cookieStore.getURIs().forEach(uri -> {
                map.put(uri,cookieStore.get(uri));
            });

            ObjectMapper mapper=new ObjectMapper();
            try {
                mapper.writeValue(new File("a.json"),map);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            //windowEvent.consume();
        });



    }
}
