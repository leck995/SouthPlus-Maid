package net.southplus.southplusmaid.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.southplus.southplusmaid.model.NetDisk;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Config {
    private static final String SETTING_FILE="setting.json";

    public static Integer WIDTH=1600;
    public static Integer HEIGHT=1000;
    public static String SOUTH_PLUS_HOST;
    public static String COOKIE;
    public static Boolean PROXY=true;
    public static String PROXY_HOST="127.0.0.1";
    public static Integer PROXY_PORT=7890;
    public static String DLSITE_HOST;
    public static Integer SKIP_BBS_ITEM_SIZE=4;//跳过第一页置顶数量置顶的帖子，目前是4

    public static Integer BBS_ITEM_SHOW_MODEL=0;//0大图，1缩略图
    public static Double BBS_ITEM_BIG_WIDTH=400.0;
    public static Double BBS_ITEM_BIG_HEIGHT=300.0;
    public static Boolean BROWSER_TYPE=true;//设置浏览器策略，默认true使用内置浏览器，false为系统默认浏览器

    public static List<NetDisk> NET_DISKS=new ArrayList<>(); //不区分大小写




    static {
        File setting=new File(SETTING_FILE);
        if (setting.exists()){
            ObjectMapper mapper=new ObjectMapper();
            try {
                JsonNode jsonNode = mapper.readTree(setting);
                WIDTH=jsonNode.get("WIDTH").asInt();
                HEIGHT=jsonNode.get("HEIGHT").asInt();
                SOUTH_PLUS_HOST= jsonNode.get("SOUTH_PLUS_HOST").asText();
                COOKIE=  jsonNode.get("COOKIE").asText();
                PROXY=jsonNode.get("PROXY").asBoolean();
                PROXY_HOST=jsonNode.get("PROXY_HOST").asText();
                PROXY_PORT= jsonNode.get("PROXY_PORT").asInt();
                DLSITE_HOST= jsonNode.get("DLSITE_HOST").asText();
                SKIP_BBS_ITEM_SIZE = jsonNode.get("SKIP_BBS_ITEM_SIZE").asInt();
                BBS_ITEM_SHOW_MODEL= jsonNode.get("BBS_ITEM_SHOW_MODEL").asInt();
                BROWSER_TYPE =jsonNode.get("BROWSER_TYPE").asBoolean();
                BBS_ITEM_BIG_WIDTH=jsonNode.get("BBS_ITEM_BIG_WIDTH").asDouble();
                BBS_ITEM_BIG_HEIGHT=BBS_ITEM_BIG_WIDTH * 0.75;
                JsonNode netDisksNode = jsonNode.get("NET_DISKS");
                for (JsonNode node : netDisksNode) {
                    NetDisk netDisk = mapper.treeToValue(node, NetDisk.class);
                    NET_DISKS.add(netDisk);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }else {
            NET_DISKS.add(new NetDisk("度盘","度盘","百度","bd"));
            NET_DISKS.add(new NetDisk("夸克","夸克"));
            NET_DISKS.add(new NetDisk("OneDrive","OneDrive","od"));
        }

    }


    public static void saveSetting(){
        LinkedHashMap<String,Object> map=new LinkedHashMap<>();
        map.put("WIDTH",WIDTH);
        map.put("HEIGHT",HEIGHT);
        map.put("SOUTH_PLUS_HOST",SOUTH_PLUS_HOST);
        map.put("COOKIE",COOKIE);
        map.put("PROXY",PROXY);
        map.put("PROXY_HOST",PROXY_HOST);
        map.put("PROXY_PORT",PROXY_PORT);
        map.put("DLSITE_HOST",DLSITE_HOST);
        map.put("SKIP_BBS_ITEM_SIZE",SKIP_BBS_ITEM_SIZE);
        map.put("BBS_ITEM_SHOW_MODEL",BBS_ITEM_SHOW_MODEL);
        map.put("BBS_ITEM_BIG_WIDTH",BBS_ITEM_BIG_WIDTH);
        map.put("BROWSER_TYPE",BROWSER_TYPE);
        map.put("NET_DISKS",NET_DISKS);

        ObjectMapper mapper=new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(SETTING_FILE),map);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }



}
