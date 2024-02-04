package net.southplus.southplusmaid.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import de.saxsys.mvvmfx.MvvmFX;
import javafx.concurrent.Task;
import net.southplus.southplusmaid.config.Config;
import net.southplus.southplusmaid.model.MessageInfo;
import net.southplus.southplusmaid.model.MessageType;
import net.southplus.southplusmaid.model.dlsite.DLLanguage;
import net.southplus.southplusmaid.model.dlsite.DLSiteWork;

import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class DLSiteThread extends Task<DLSiteWork> {

    private String rjId;

    public DLSiteThread(String rjId) {
        this.rjId = rjId;
    }

    @Override
    protected DLSiteWork call() throws Exception {
        DLSiteWork dlSiteWork = getDLSiteWork(rjId);
        String lowerCase = dlSiteWork.getWork_name().toLowerCase();
        for (String key : Config.BBS_ITEM_TYPE.keySet()) {
            if (lowerCase.contains(key.toLowerCase())){
                dlSiteWork.setType(Config.BBS_ITEM_TYPE.get(key));
                break;
            }
        }
        Thread.sleep(300);
        return dlSiteWork;
    }


    public DLSiteWork getDLSiteWork(String rj){
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(Config.PROXY_HOST, Config.PROXY_PORT));
        URL url = null;
        try {
            url = new URL(Config.DLSITE_HOST+"/maniax/api/=/product.json?locale=zh_CN&workno="+rj);
            HttpURLConnection uc = (HttpURLConnection)url.openConnection(proxy);
            uc.setReadTimeout(3000);
            uc.connect();
            byte[] bytes = uc.getInputStream().readAllBytes();
            uc.getInputStream().close();
            uc.disconnect();

            String row = new String(bytes)
                    .replace("\"creaters\":[]","\"creaters\":{}"); //当creaters不存在的异常处理
            ObjectMapper mapper=new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            List<DLSiteWork> dlSiteWorks = mapper.readValue(row, new TypeReference<>() {
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
                MvvmFX.getNotificationCenter().publish("message",new MessageInfo(MessageType.ERROR,rj+":查不到信息，可能RJ号有误"));
                return null;//RJ错误时
            }
        }catch (SocketTimeoutException e){
            System.err.println(e.getMessage());
            MvvmFX.getNotificationCenter().publish("message",new MessageInfo(MessageType.ERROR,rj+":请求超时"));
            return null;
        }catch (MismatchedInputException e){
            System.err.println(e.getMessage());
            MvvmFX.getNotificationCenter().publish("message",new MessageInfo(MessageType.ERROR,rj+":DLSite JSON解析错误"));
            return null;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            MvvmFX.getNotificationCenter().publish("message",new MessageInfo(MessageType.ERROR,e.getCause().toString()));
            return null;
        }
    }
}
