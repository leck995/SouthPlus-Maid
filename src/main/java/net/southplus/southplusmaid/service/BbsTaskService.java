package net.southplus.southplusmaid.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.saxsys.mvvmfx.MvvmFX;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.input.DataFormat;
import net.southplus.southplusmaid.config.Config;
import net.southplus.southplusmaid.model.*;
import net.southplus.southplusmaid.model.dlsite.DLLanguage;
import net.southplus.southplusmaid.model.dlsite.DLSiteWork;
import net.southplus.southplusmaid.util.RJUtils;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: 领取任务的线程类
 * @author: Leck
 * @date:   2024/2/3
 */
public class BbsTaskService extends Task<Boolean> {
    public static final String TITLE="plugin.php?H_name-tasks.html";//默认任务栏目url
    private static final String ACCEPT_TASK="plugin.php?H_name=tasks&action=ajax&actions=job&cid=";//默认领取任务栏目url，结尾任务id
    private static final String FINISH_TASK="plugin.php?H_name=tasks&action=ajax&actions=job2&cid=";//默认完成任务栏目url，结尾任务id

    @Override
    protected Boolean call() throws Exception {
        Document document = getDocument();
        if (document != null){
            List<BbsTask> tasks = getTasks(document);
            List<BbsTask> list = tasks.stream().filter(BbsTask::ready).toList();
            if (!list.isEmpty()){
                MvvmFX.getNotificationCenter().publish("message",new MessageInfo(MessageType.INFO,list.size()+"个任务可领取，开始执行"));
                for (BbsTask task : list) {
                    boolean b = acceptTask(task);
                    if (b){
                        MvvmFX.getNotificationCenter().publish("message",new MessageInfo(MessageType.SUCCESS,task.getTitle()+"成功领取，获取SP:"+task.getSp()));
                    }else {
                        MvvmFX.getNotificationCenter().publish("message",new MessageInfo(MessageType.ERROR,task.getTitle()+"领取失败"));
                    }
                }
            }else {
                MvvmFX.getNotificationCenter().publish("message",new MessageInfo(MessageType.INFO,"当前无可领取任务"));
            }
        }else {
            System.err.println("未获取到html");
        }
        return true;
    }



    private Document getDocument(){
        try {
            return Jsoup
                    .connect(Config.SOUTH_PLUS_HOST +"/"+TITLE)
                    .header("Cookie",Config.COOKIE)
                    .header("User-Agent",Config.USER_AGENT)
                    .proxy(Config.PROXY_HOST, Config.PROXY_PORT)
                    .timeout(5000)
                    .get();
        }catch (SocketTimeoutException e){
            System.err.println(e.getMessage());
            MvvmFX.getNotificationCenter().publish("message",new MessageInfo(MessageType.ERROR,"论坛请求超时:"+e.getMessage()));
            return null;
        }catch (HttpStatusException e){
            System.err.println(e.getMessage());
            MvvmFX.getNotificationCenter().publish("message",new MessageInfo(MessageType.ERROR,"论坛访问异常:"+e.getMessage()));
            return null;
        } catch (IllegalArgumentException e){
            System.err.println(e.getMessage());
            MvvmFX.getNotificationCenter().publish("message",new MessageInfo(MessageType.ERROR,"论坛访问异常:"+e.getMessage()));
            return null;
        } catch (Exception e) {
            System.err.println("Jsoup："+e);
            throw new RuntimeException(e);
        }
    }

    private List<BbsTask> getTasks(Element body) throws ParseException {
        Element main = body.getElementById("main");
        Element td = main.getElementsByAttributeValue("valign","top").get(1);
        Element table2 = td.getElementsByTag("table").get(0);

        Elements tr = table2.getElementsByTag("tr");
        tr.remove(0);
        List<BbsTask> list=new ArrayList<>();
        if (tr.size() % 3 == 0){
            for (int i = 0; i < tr.size(); i=i+3) {
                BbsTask  task=new BbsTask();

                Element element = tr.get(i);
                Elements tds = element.children();

                String[] strings = tds.get(1).text().split("任务时效");
                //标题
                String title = strings[0];
                task.setTitle(title);
                //时间
                String[] dates = strings[1].split("~");
                String startDateS=dates[0];
                String endDateS=dates[1];
                SimpleDateFormat dataFormat=new SimpleDateFormat("yyyy-MM-dd");
                Date startDate=dataFormat.parse(startDateS);
                Date endDate=dataFormat.parse(endDateS);
                task.setStart(startDate);
                task.setEnd(endDate);


                //sp
                String sp = tds.get(2).text();
                sp=sp.replace("奖励 : SP币 ","").replace(" G","").trim();
                task.setSp(Integer.valueOf(sp));


                //type
                Element idElement = tds.get(3);
                String type = idElement.text();
                if (type.contains("上次领取")){
                    task.setType(BbsTaskType.GOT);
                }else {
                    Elements span = idElement.getElementsByTag("span");
                    if (!span.isEmpty()){
                        String id = span.get(0).id().replace("p_","");
                        task.setId(id);
                        task.setType(BbsTaskType.READY);
                    }
                }
                list.add(task);
            }

        }
        return list;
    }



    private boolean acceptTask(BbsTask task){
        try {
            Connection.Response response = Jsoup.connect(Config.SOUTH_PLUS_HOST + "/" + ACCEPT_TASK+task.getId())
                    .header("Cookie", Config.COOKIE)
                    .header("User-Agent", Config.USER_AGENT)
                    .proxy(Config.PROXY_HOST, Config.PROXY_PORT)
                    .timeout(5000)
                    .execute();
            String body = response.body();
            System.out.println("领取结果："+body);


            Connection.Response response2 = Jsoup.connect(Config.SOUTH_PLUS_HOST + "/" + FINISH_TASK+task.getId())
                    .header("Cookie", Config.COOKIE)
                    .header("User-Agent", Config.USER_AGENT)
                    .proxy(Config.PROXY_HOST, Config.PROXY_PORT)
                    .timeout(5000)
                    .execute();
            String body2 = response2.body();
            System.out.println("完成结果："+body2);

            return body2.contains("请赶紧去完成任务吧");
        }catch (SocketTimeoutException e){
            System.err.println(e.getMessage());
            MvvmFX.getNotificationCenter().publish("message",new MessageInfo(MessageType.ERROR,"论坛请求超时:"+e.getMessage()));
            return false;
        }catch (HttpStatusException e){
            System.err.println(e.getMessage());
            MvvmFX.getNotificationCenter().publish("message",new MessageInfo(MessageType.ERROR,"论坛访问异常:"+e.getMessage()));
            return false;
        } catch (IllegalArgumentException e){
            System.err.println(e.getMessage());
            MvvmFX.getNotificationCenter().publish("message",new MessageInfo(MessageType.ERROR,"论坛访问异常:"+e.getMessage()));
            return false;
        } catch (Exception e) {
            System.err.println("Jsoup："+e);
            throw new RuntimeException(e);
        }
    }
}
