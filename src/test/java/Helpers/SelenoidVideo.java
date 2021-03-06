package Helpers;

import io.qameta.allure.Allure;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SelenoidVideo {

    public static String selenoidUrl = "http://0.0.0.0:4444";

    public static void attachAllureVideo(String sessionId) {
        try {
            URL videoUrl = new URL(selenoidUrl + "/video/" + sessionId + ".mp4");
            InputStream is = getSelenoidVideo(videoUrl);
            Allure.addAttachment("Video", "video/mp4", is, "mp4");
            deleteSelenoidVideo(videoUrl);
        } catch (Exception e) {
            System.out.println("attachAllureVideo");
            e.printStackTrace();
        }
    }

    public static InputStream getSelenoidVideo(URL url) {
        int lastSize = 0;
        int exit = 2;
        for (int i = 0; i < 20; i++) {
            try {
                int size = Integer.parseInt(url.openConnection().getHeaderField("Content-Length"));
                System.out.println("Content-Length: " + size);
                System.out.println("i: " + i);
                if (size > lastSize) {
                    lastSize = size;
                    Thread.sleep(1500);
                } else if (size == lastSize) {
                    System.out.println("Content-Length: " + size);
                    System.out.println("exit: " + exit);
                    exit--;
                    Thread.sleep(1000);
                }
                if (exit < 0) {
                    System.out.println("video ok!");
                    return url.openStream();
                }
            } catch (Exception e) {
                System.out.println("getSelenoidVideo: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return null;
    }

    public static void deleteSelenoidVideo(URL url) {
        try {
            HttpURLConnection deleteConn = (HttpURLConnection) url.openConnection();
            deleteConn.setDoOutput(true);
            deleteConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            deleteConn.setRequestMethod("DELETE");
            deleteConn.connect();
            System.out.println("deleteSelenoidVideo");
            System.out.println(deleteConn.getResponseCode());
            System.out.println(deleteConn.getResponseMessage());
            deleteConn.disconnect();
        } catch (IOException e) {
            System.out.println("deleteSelenoidVideo");
            e.printStackTrace();
        }
    }

}
