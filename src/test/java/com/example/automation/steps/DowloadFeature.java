package com.example.automation.steps;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertTrue;

public class DowloadFeature {

    ImportTokenXray importTokenXray = new ImportTokenXray();

    public static void downloadFeatureFiles(String token, String testKeys) {
        try {
            URL url = new URL("https://xray.cloud.getxray.app/api/v2/export/cucumber?keys=" + testKeys);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setRequestProperty("Content-Type", "application/json");

            InputStream inputStream = conn.getInputStream();
            FileOutputStream outputStream = new FileOutputStream("features.zip");
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            inputStream.close();
            new net.lingala.zip4j.ZipFile("C:\\dev\\SauceLabsGherkin\\features.zip").extractAll("C:\\dev\\SauceLabsGherkin\\src\\test\\resources\\features");
        } catch (Exception e) {
            System.err.println("Erreur lors du téléchargement des fichiers feature: " + e.getMessage());
        }
    }

    @Test
    public void testDownloadFeatureFiles() throws Exception {
        String token = importTokenXray.token;
        String testKeys = "POEI2-635";

        DowloadFeature.downloadFeatureFiles(token, testKeys);
    }
}
