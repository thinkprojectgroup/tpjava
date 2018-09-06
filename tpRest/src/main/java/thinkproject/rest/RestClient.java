/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thinkproject.rest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

/**
 *
 * @author joost.meulenkamp
 */
public class RestClient {

    private String appKey;
    private String baseUri;
    private String token;

    public boolean isAuthenticated() {
        return token != null;
    }

    public RestClient(String baseUri, String appKey) {
        this.baseUri = baseUri;
        this.appKey = appKey;
    }

    public String postClipboardFile(String path, RestClient.Method method, File file) {
        return executeRequest(path, method, file);
    }

    public String executeRequest(String path, RestClient.Method method) {
        return executeRequest(path, method, null);
    }

    public String executeRequest(String path, RestClient.Method method, String body) {
        return executeRequest(path, method, (Object) body);
    }

    private String executeRequest(String path, RestClient.Method method, Object body) {

        try {
            String restPath = path.startsWith(baseUri) ? path : baseUri + path;
            URL url = new URL(restPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method.toString());
            connection.setRequestProperty("X-TP-APPLICATION-CODE", appKey);
            String contentType = "application/json";
            connection.setRequestProperty("Content-Type", contentType);
            if (token != null) {
                connection.setRequestProperty("Authorization", "Bearer " + token);
            }

            if (method == Method.POST) {
                if (body instanceof String) {
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
                    writer.write((String) body);
                    writer.flush();
                    writer.close();
                } else if (body instanceof File) {
                    File file = (File) body;
                    FileBody fileBody = new FileBody(file);
                    MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.STRICT);
                    multipartEntity.addPart("Content-Disposition", new StringBody("form-data"));
                    multipartEntity.addPart("filename", new StringBody(file.getName()));
                    multipartEntity.addPart("Filedata", fileBody);
                    contentType = Files.probeContentType(Paths.get(file.getPath()));
                    multipartEntity.addPart("Content-Type", new StringBody(contentType));
                    connection.setRequestProperty("Content-Type", multipartEntity.getContentType().getValue());
                    OutputStream out = connection.getOutputStream();
                    try {
                        multipartEntity.writeTo(out);
                    } finally {
                        out.flush();
                        out.close();
                    }
                }
            }

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                //create log entry
                System.out.println(connection.getResponseCode() + " " + connection.getResponseMessage());
                return null;
            }

            StringBuilder response = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                response.append(line);
                response.append("\n");
            }
            reader.close();
            return response.toString();

        } catch (IOException e) {
            return null;
        }
    }

    public void authenticate(String user, String pass) {
        Map<String, Object> map = new TreeMap<>();
        map.put("username", user);
        map.put("password", pass);
        String response = executeRequest("/services/api/auth", Method.POST, thinkproject.Util.toJson(map));

        if (response != null) {
            String isToken = thinkproject.Util.getProperty(response, "token");
            token = isToken != null ? isToken : null;
        }
    }

    public void reverseAuthenticate(String session) {
        Map<String, Object> map = new TreeMap<>();
        map.put("session_id", session);
        String response = executeRequest("/services/api/reverse_auth", Method.POST, thinkproject.Util.toJson(map));

        if (response != null) {
            String isToken = thinkproject.Util.getProperty(response, "token");
            token = isToken != null ? isToken : null;
        }
    }

    public enum Method {
        GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE;
    }
}
