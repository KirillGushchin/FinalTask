package Utils;


import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class APIUtils {
    public static HttpResponse postRequest(String url, File file , String credentials){
        HttpResponse result = null;
        try {
            result = Request.Post(url)
                    .addHeader("Authorization","Basic " + credentials)
                    .body(setHttpEntity(file))
                    .execute()
                    .returnResponse();
        } catch (IOException e) {
            System.err.println("Request cannot be competed due to following error : " + e.getMessage());
        }
        return result;
    }

    public static HttpResponse postRequest(String url){
        HttpResponse response = null;
        try {
            response = Request.Post(url)
                    .execute()
                    .returnResponse();
        } catch (IOException e) {
            System.err.println("Cannot sent request due to following error: " + e.getMessage());
        }
        return response;
    }
    public static HttpResponse postRequest(String url, Map<String,String> parameters, String credentials){
        HttpResponse result = null;
        try {
            result = Request.Post(url)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization","Basic " + credentials)
                    .bodyString(new Gson().toJson(parameters), ContentType.APPLICATION_JSON)
                    .execute()
                    .returnResponse();
        } catch (IOException e) {
            System.err.println("Request cannot be competed due to following error : " + e.getMessage());
        }
        return result;
    }

    public static String getContent(HttpResponse response){
        String result = null;
        try {
            result = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            System.err.println("Cannot get content form response due to following error : " + e.getMessage());
        }
        return result;
    }

    public static HttpEntity setHttpEntity(File file){
        HttpEntity httpEntity = MultipartEntityBuilder.create()
                .addBinaryBody("attachment",file, ContentType.MULTIPART_FORM_DATA,file.getName())
                .build();
        return httpEntity;
    }

}
