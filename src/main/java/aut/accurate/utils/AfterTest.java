package aut.accurate.utils;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static aut.accurate.utils.Constants.ROOT;
import static aut.accurate.utils.Utils.env;

public class AfterTest {
    public static void main(String[] args) {
        File file = new File(ROOT + "/build/result.html");
        String username = env("WEBDAV_USERNAME");
        String password = env("WEBDAV_PASSWORD");
        String url = env("WEBDAV_URL");

        try{
            HttpClient client = new HttpClient();
            Credentials credentialsWebDav = new UsernamePasswordCredentials(username, password);
            client.getState().setCredentials(AuthScope.ANY, credentialsWebDav);

            PutMethod method = new PutMethod(url + file.getName());
            RequestEntity requestEntity = new InputStreamRequestEntity(
                    Files.newInputStream(file.toPath()));
            method.setRequestEntity(requestEntity);
            client.executeMethod(method);
            System.out.println(method.getStatusCode() + " " + method.getStatusText());
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
