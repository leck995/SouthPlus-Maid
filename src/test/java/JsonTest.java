import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import net.southplus.southplusmaid.model.dlsite.DLActor;
import net.southplus.southplusmaid.model.dlsite.DLCreater;
import net.southplus.southplusmaid.model.dlsite.DLLanguage;
import net.southplus.southplusmaid.model.dlsite.DLSiteWork;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonTest {
    @Test
    public void json() throws IOException {


        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 7890));
        URL url = new URL("https://www.dlsite.com/maniax/api/=/product.json?workno=RJ01138101");
        HttpURLConnection uc = (HttpURLConnection)url.openConnection(proxy);
        uc.connect();
        byte[] bytes = uc.getInputStream().readAllBytes();
        uc.disconnect();

        System.out.println(new String(bytes));

        String row = new String(bytes).replace("\"creaters\":[]","\"creaters\":{}");


        ObjectMapper mapper=new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        List<DLSiteWork> dlSiteWorks = mapper.readValue(row, new TypeReference<>() {
        });
        JsonNode jsonNode = mapper.readTree(bytes);
        JsonNode languageEditions = jsonNode.get(0).get("language_editions");

        List<DLLanguage> languages=new ArrayList<>();
        DLLanguage dlLanguage;
        for (JsonNode node : languageEditions) {
            dlLanguage=new DLLanguage();
            dlLanguage.setWorkno(node.get("workno").asText());
            dlLanguage.setLabel(node.get("label").asText());
            languages.add(dlLanguage);
        }


/*        JsonNode creaters = jsonNode.get(0).get("creaters");
        if (!creaters.isEmpty()){
            DLCreater dlCreater=new DLCreater();
            JsonNode created_by = creaters.get("created_by");
            if (!created_by.isEmpty()){
                DLActor actor=new DLActor(created_by.get("id").asText(),created_by.get("name").asText());
                dlCreater.setCreated_by(actor);
            }
        }*/



    /*    List<DLLanguage> list1 = mapper.treeToValue(jsonString1, new TypeReference<Map<String, DLLanguage>>() {}).values().stream().toList();*/

        /*mapper.treeToValue(jsonNode,new TypeReference<List<DLLanguage>>(){})
        List<DLLanguage> o =mapper.treeToValue(languageEditions,new TypeReference<>(){});*/

/*        try{
            List<DLLanguage> o = mapper.readValue(languageEditions.textValue(), new TypeReference<>() {
            });

        }catch (MismatchedInputException exception){
            Map<String,DLLanguage> o = mapper.readValue(languageEditions.textValue(), new TypeReference<>() {
            });


        }*/




        System.out.println(dlSiteWorks.get(0).getWork_name());
        System.out.println(dlSiteWorks.get(0).getImage_main().getUrl());
    }
}
