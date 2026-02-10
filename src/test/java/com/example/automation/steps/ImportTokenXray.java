package com.example.automation.steps;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import org.junit.Test;

import javax.net.ssl.SSLContext;


public class ImportTokenXray {

    String clientID = "19B21B3A87A84FB187E8ED0C929260EA";
    String clientSecret = "8f8d1ba97004498878ddc1f48835d9a997dea57056fe756891ffbd7e1ac68177";
    String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0ZW5hbnQiOiJiNmNhZGQwNS1lMzQxLTNmMTctYjU1Zi00OTM0MTI4MWQ4MmEiLCJhY2NvdW50SWQiOiI2MzVhNDAwNWM5N2Y1NDczYWY3MDcwYWYiLCJpc1hlYSI6ZmFsc2UsImlhdCI6MTc3MDI4MjY0MCwiZXhwIjoxNzcwMzY5MDQwLCJhdWQiOiIxOUIyMUIzQTg3QTg0RkIxODdFOEVEMEM5MjkyNjBFQSIsImlzcyI6ImNvbS54cGFuZGl0LnBsdWdpbnMueHJheSIsInN1YiI6IjE5QjIxQjNBODdBODRGQjE4N0U4RUQwQzkyOTI2MEVBIn0.nNt796yAEdWSFdxkI1yXpT1PQZ26hvnohrQ1GT8--ts";


    public String getToken() throws IOException, NoSuchAlgorithmException, KeyManagementException {
        // Méthode correcte pour HttpClient 4.x
        SSLContext sslContext = SSLContexts.custom()
                .useProtocol("TLSv1.2")  // useProtocol, PAS setProtocol
                .build();

        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                sslContext,
                new String[]{"TLSv1.2", "TLSv1.3"},
                null,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier()
        );

        try (CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslConnectionSocketFactory)
                .build()) {

            HttpPost request = new HttpPost("https://xray.cloud.getxray.app/api/v1/authenticate");
            request.addHeader("Content-Type", "application/json");

            String input = "{ \"client_id\": \"" + clientID + "\", \"client_secret\" : \"" + clientSecret + "\"}";
            request.setEntity(new StringEntity(input, StandardCharsets.UTF_8));

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String result = EntityUtils.toString(response.getEntity());
                System.out.println(result);
                return result.replace("\"", "").trim();
            }
        }
    }


    @Test
    public void myTest() throws IOException, NoSuchAlgorithmException, KeyManagementException{

        ImportTokenXray importTokenXray = new ImportTokenXray();
        String token = importTokenXray.getToken();
        System.out.println("XRAY TOKEN = " + token);

    }


    public void remonterXray() throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, InterruptedException {
        HttpURLConnection conn;
        URL url;
        String result;
        String  URL ="https://xray.cloud.getxray.app/api/v2/import/execution/cucumber";
        //Call the openConnection method on the URL to create a connection object
        url = new URL(URL);
        conn = (HttpURLConnection) url.openConnection();

        //Various settings of HttpURLConnection
        //Set HTTP method to POST
        conn.setRequestMethod("POST");
        //Allow body submission of request
        conn.setDoInput(true);
        //Allow body reception of response
        conn.setDoOutput(true);
        //Specify Json format
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        conn.setRequestProperty("Authorization", "Bearer " + token);
// 2.Establish a connection
        conn.connect();
        // 3.Write to request and body
        //Get OutputStream from HttpURLConnection and write json string
        Thread.sleep(10000);
        PrintStream ps = new PrintStream(conn.getOutputStream());

        Path filePath = Path.of(System.getProperty("user.dir")+"\\target\\cucumber.json");
        String content = Files.readString(filePath);
        System.out.println("mon fichier  = "+content);
        ps.print(content);
        ps.close();

        // 4.Receive a response
        //HttpStatusCode 200 is returned at the end of normal operation
        if (conn.getResponseCode() != 200) {
            //Error handling
        }
        //Get InputStream from HttpURLConnection and read
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        result = sb.toString();
        // 5.Disconnect
        conn.disconnect();
        //Return the result to the caller
        System.out.println(result);
    }

    @Test
    public void myTest2() throws IOException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, InterruptedException {
        remonterXray();
    }
}

