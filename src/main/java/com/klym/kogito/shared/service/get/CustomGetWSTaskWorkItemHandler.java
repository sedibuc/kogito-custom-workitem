package com.klym.kogito.shared.service.get;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kie.kogito.internal.process.runtime.KogitoWorkItem;
import org.kie.kogito.internal.process.runtime.KogitoWorkItemHandler;
import org.kie.kogito.internal.process.runtime.KogitoWorkItemManager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.klym.kogito.shared.model.CustomResult;
import org.json.JSONObject;
import java.util.Map;

public class CustomGetWSTaskWorkItemHandler implements KogitoWorkItemHandler {

    @Override
    public void abortWorkItem(KogitoWorkItem arg0, KogitoWorkItemManager arg1) {
        System.err.println("Error happened in the custom work item definition.");

    }

    public static void main(String[] args) {
        CustomResult data = CustomGetWSTaskWorkItemHandler.invokeUrl("https://randomuser.me/api/");
        System.out.println(((List<Map<String, Object>>)data.getData().get("results")).get(0).get("gender"));
        System.out.println(data.getJsonData().getJSONArray("results").getJSONObject(0).getString("gender"));
        System.out.println(data.getValue("results[0].gender"));
    }

    private static CustomResult invokeUrl(String url_str) {
        URL url;
        CustomResult cr = new CustomResult();
        ObjectMapper mapper = new ObjectMapper();
        try {
            url = new URL(url_str);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setInstanceFollowRedirects(false);

            cr.setResponseCode(con.getResponseCode());
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();

            cr.setData(mapper.readValue(content.toString(), Map.class));
            cr.setJsonData(new JSONObject(content.toString()));
            cr.setJsonString(content.toString());
            // cr.getData().put("data", content.toString());
        } catch (MalformedURLException e) {
            cr.setResponseCode(-1);
            System.out.println("Error en la URL");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error en la escritura");
            cr.setResponseCode(-1);
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error genérico");
            cr.setResponseCode(-1);
            e.printStackTrace();
        }
        return cr;
    }

    @Override
    public void executeWorkItem(KogitoWorkItem workItem, KogitoWorkItemManager manager) {
        System.out.println("Hello from the custom work item definition.");
        System.out.println("Passed parameters:");
        for(String parameter : workItem.getParameters().keySet()) {
            System.out.println(parameter + " = " + workItem.getParameters().get(parameter));
        }
        //i.e. url can be https://randomuser.me/api/
        CustomResult answer = CustomGetWSTaskWorkItemHandler.invokeUrl(workItem.getParameters().get("Url").toString());
        // CustomResult answer = new CustomResult();
        // answer.setResponseCode(10);
        // answer.getData().put("primero", "prueba");
        
        Map<String, Object> results = new HashMap<String, Object>();
        results.put("Result", answer);
     // Don’t forget to finish the work item otherwise the process
     // will be active infinitely and never will pass the flow
     // to the next node.
        // manager.completeWorkItem(workItem.getStringId(), results);
        manager.completeWorkItem(workItem.getStringId(), results);
    }
    
}
