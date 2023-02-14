package com.github.morningwn.tools.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * @author morningwn
 * @create in 2022/10/21 15:27
 */
public class HttpUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);

    public static String get(String url) {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout( Duration.ofSeconds(1L, 0))
                .GET()
                .build();
        try {
            LOGGER.info("HttpUtils#get start request: {}", request);
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            LOGGER.info("HttpUtils#get start response: {}", response);

            return response.body();
        } catch (IOException e) {
            LOGGER.error("HttpUtils#get fail", e);
        } catch (InterruptedException e) {
            LOGGER.error("HttpUtils#get fail", e);
            Thread.currentThread().interrupt();
        }

        return null;
    }

    public static byte[] getByte(String url) {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        try {
            LOGGER.info("HttpUtils#get start request: {}", request);
            HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());
            LOGGER.info("HttpUtils#get start response: {}", response);
            return response.body();
        } catch (IOException e) {
            LOGGER.error("HttpUtils#get fail", e);
        } catch (InterruptedException e) {
            LOGGER.error("HttpUtils#get fail", e);
            Thread.currentThread().interrupt();
        }

        return null;
    }

    public static String post(String url, String params) {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(params))
                .build();
        try {
            LOGGER.info("HttpUtils#post start request: {}", request);
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            LOGGER.info("HttpUtils#post start response: {}", response);
            return response.body();
        } catch (IOException e) {
            LOGGER.error("HttpUtils#post fail", e);
        } catch (InterruptedException e) {
            LOGGER.error("HttpUtils#post fail", e);
            Thread.currentThread().interrupt();
        }

        return null;
    }
}
