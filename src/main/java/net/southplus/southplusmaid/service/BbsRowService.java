package net.southplus.southplusmaid.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.saxsys.mvvmfx.MvvmFX;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import net.southplus.southplusmaid.config.Config;
import net.southplus.southplusmaid.model.*;
import net.southplus.southplusmaid.model.dlsite.DLLanguage;
import net.southplus.southplusmaid.model.dlsite.DLSiteWork;
import net.southplus.southplusmaid.util.RJUtils;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BbsRowService extends Service<BbsInfo> {
    private static final String PAGE_TEMPLATE="-page-%d.html";
    public static final String TITLE="thread.php?fid-128.html";//默认栏目url
    private String fUrl;//论坛分区地址

    private boolean isPageEvent; //判断是翻页行为还是切换子栏目行为
    private boolean skip;//判断跳过第一页置顶数量置顶的帖子



    /**
     * @description: 切换子栏目调用该方法，第一次使用也调用该方法
     * @param fUrl
     * @return: void
     * @author: leck
     * @time: 2024/1/11 21:44
     */
    public void init(String fUrl) {
        this.fUrl = fUrl;
        isPageEvent=false;
        skip=true;
        restart();
    }

    /**
     * @description: 翻页调用该方法
     * @param fUrl
     * @param pageIndex
     * @return: void
     * @author: leck
     * @time: 2024/1/11 21:45
     */
    public void changePage(String fUrl, Integer pageIndex,boolean isPageEvent) {
        if (isPageEvent){
            this.fUrl = fUrl.replace(".html", String.format("-page-%d.html", pageIndex + 1));
            skip=false;
            skip= pageIndex==0;
        }else {
            this.fUrl = fUrl;
            skip=true;

        }
        this.isPageEvent=isPageEvent;
        restart();
    }

    @Override
    protected Task<BbsInfo> createTask() {
        Task<BbsInfo> task=new Task<BbsInfo>() {
            @Override
            protected BbsInfo call(){
                updateTitle("true");
                BbsInfo bbsInfo=new BbsInfo();
                Document document = getDocument(fUrl);
                Element body = document.body();
                bbsInfo.setItems(getItems(body));
                String pageInfo = getPageInfo(document);
                if (pageInfo!=null){
                    String[] split = pageInfo.split("/");
                    bbsInfo.setPageIndex(Integer.parseInt(split[0])-1);
                    if (!isPageEvent){
                        bbsInfo.setPageSize(Integer.parseInt(split[1]));
                        bbsInfo.setTitles(getTitles(body));
                    }
                }else {
                    bbsInfo.setPageIndex(0);
                    if (!isPageEvent){
                        bbsInfo.setPageSize(1);
                        bbsInfo.setTitles(getTitles(body));
                    }
                }
                if (skip){
                    bbsInfo.getItems().subList(0,Config.SKIP_BBS_ITEM_SIZE).clear();
                }
                updateTitle("false");

                return bbsInfo;
            }
        };


        return task;
    }



    private Document getDocument(String fUrl){
        System.out.println("furl:"+fUrl);
        try {
            return Jsoup
                    .connect(Config.SOUTH_PLUS_HOST +"/"+fUrl)
                    .header("Cookie",Config.COOKIE)
                    .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36 Edg/120.0.0.0")
                    .proxy(Config.PROXY_HOST, Config.PROXY_PORT)
                    .timeout(3000)
                    .get();
        }catch (SocketTimeoutException e){
            System.err.println(e.getMessage());
            MvvmFX.getNotificationCenter().publish("message",new MessageInfo(MessageType.ERROR,"论坛请求超时，请检查网络并刷新"));
            return null;
        }catch (HttpStatusException e){
            System.err.println(e.getMessage());
            MvvmFX.getNotificationCenter().publish("message",new MessageInfo(MessageType.ERROR,"论坛访问异常，请检查网络并刷新"));
            return null;
        } catch (IllegalArgumentException e){
            System.err.println(e.getMessage());
            MvvmFX.getNotificationCenter().publish("message",new MessageInfo(MessageType.ERROR,"论坛访问异常，请检查网络并刷新"));
            return null;
        } catch (Exception e) {
            System.err.println("Jsoup："+e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @description: 获取论坛子栏目
     * @param body
     * @return: java.util.List<net.southplus.southplusmaid.model.BbsTitle>
     * @author: leck
     * @time: 2024/1/11 1:24
     */
    private List<BbsTitle> getTitles(Element body){
        Element ajaxtable = body.getElementById("ajaxtable");
        Elements tbody = ajaxtable.getElementsByTag("tbody");
        Elements hthread = tbody.get(0).getElementsByClass("hthread");
        Elements lis = hthread.get(0).getElementsByTag("li");
        List<BbsTitle> list=new ArrayList<>();
        BbsTitle bbsTitle;
        for (Element li : lis) {
            //boolean current = li.hasAttr("current");
            String attr = li.getElementsByTag("a").attr("href");
            if (attr.endsWith("#c"))
                attr=attr.replace("#c","");
            System.out.println(attr);
            bbsTitle=new BbsTitle(li.text(),attr);
            list.add(bbsTitle);
        }
        return list;
    }

    private List<BbsItem> getItems(Element body){
        Element ajaxtable = body.getElementById("ajaxtable");
        Elements tbody = ajaxtable.getElementsByTag("tbody");
        Elements trs = tbody.get(1).getElementsByTag("h3");
        List<BbsItem> list=new ArrayList<>();
        BbsItem item;
        for (int i = 2; i < trs.size(); i++) {
            Element element = trs.get(i);
            String text = element.text();
            String rj = RJUtils.getRj(text);
            item=new BbsItem();
            item.setTitle(text);
            if (rj != null){
                item.setRjId(rj);
            }

            Element a = element.getElementsByTag("a").get(0);
            item.setUrl(a.attr("href"));

            for (NetDisk netDisk : Config.NET_DISKS) {
                Boolean aBoolean = netDisk.hasKeyWords(text);
                if (aBoolean){
                    item.setDisk(netDisk);
                }
            }



            list.add(item);
        }
        return list;
    }


    private String getPageInfo(Document document){
        Elements pagesone = document.getElementsByClass("pagesone");
        if (!pagesone.isEmpty()){
            String text = pagesone.get(0).text().replace("Go","").replace(" ","").trim();
            return text.substring(6);
        }else {
            return null;
        }

    }



    public DLSiteWork getDLSiteWork(String rj){
        System.out.println("加载信息："+rj);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(Config.PROXY_HOST, Config.PROXY_PORT));
        URL url = null;
        try {
            url = new URL(Config.DLSITE_HOST+"/maniax/api/=/product.json?locale=zh_CN&workno="+rj);
            HttpURLConnection uc = (HttpURLConnection)url.openConnection(proxy);
            uc.setReadTimeout(3000);
            uc.connect();
            byte[] bytes = uc.getInputStream().readAllBytes();
            uc.disconnect();


            ObjectMapper mapper=new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            List<DLSiteWork> dlSiteWorks = mapper.readValue(bytes, new TypeReference<>() {
            });
            if (dlSiteWorks.size() > 0){
                JsonNode jsonNode = mapper.readTree(bytes);
                JsonNode languageEditions = jsonNode.get(0).get("language_editions");

                if (!languageEditions.isEmpty()){
                    List<DLLanguage> languages=new ArrayList<>();
                    DLLanguage dlLanguage;
                    for (JsonNode node : languageEditions) {
                        dlLanguage=new DLLanguage();
                        dlLanguage.setWorkno(node.get("workno").asText());
                        dlLanguage.setLabel(node.get("label").asText());
                        languages.add(dlLanguage);
                    }
                    dlSiteWorks.get(0).setLanguage_editions(languages);
                }




                return dlSiteWorks.get(0);
            }else {
                return null;//RJ错误时
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

}
