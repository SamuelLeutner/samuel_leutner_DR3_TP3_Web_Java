package org.example.clients;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ApiChallengeClient {
    private static final int READ_TIMEOUT_MS = 5000;
    private static final int CONNECT_TIMEOUT_MS = 5000;
    private static final String USER_AGENT = "Mozilla/5.0";

    public ApiResponse sendRequest(String urlString, String method, String requestBody, Map<String, String> headers) {
        HttpURLConnection connection = null;
        String responseBody = "";
        int statusCode = -1;
        Map<String, List<String>> responseHeaders = Collections.emptyMap();

        try {
            URI uri = new URI(urlString);
            URL url = uri.toURL();
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod(method);
            connection.setReadTimeout(READ_TIMEOUT_MS);
            connection.setConnectTimeout(CONNECT_TIMEOUT_MS);
            connection.setRequestProperty("User-Agent", USER_AGENT);

            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            if (requestBody != null && (method.equalsIgnoreCase("POST") || method.equalsIgnoreCase("PUT"))) {
                connection.setDoOutput(true);

                if (headers == null || !headers.containsKey("Content-Type")) {
                    connection.setRequestProperty("Content-Type", "application/json");
                }
                try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                    byte[] postData = requestBody.getBytes(StandardCharsets.UTF_8);
                    wr.write(postData);
                    wr.flush();
                }
            } else if (method.equalsIgnoreCase("OPTIONS") || method.equalsIgnoreCase("DELETE")) {
                connection.setDoOutput(false);
            }

            statusCode = connection.getResponseCode();
            responseHeaders = connection.getHeaderFields();

            BufferedReader in = null;
            try {
                if (statusCode > 299) {
                    if (connection.getErrorStream() != null) {
                        in = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8));
                    } else {

                        in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                    }
                } else {
                    in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                }

                StringBuilder responseStringBuilder = new StringBuilder();
                String inputLine;
                if (in != null) {
                    while ((inputLine = in.readLine()) != null) {
                        responseStringBuilder.append(inputLine);
                    }
                }
                responseBody = responseStringBuilder.toString();

            } catch (IOException ioException) {
                System.err.println("Error reading response stream: " + ioException.getMessage());
            } finally {
                if (in != null) {
                    in.close();
                }
            }

        } catch (URISyntaxException e) {
            responseBody = "Error: Invalid URI syntax: " + e.getMessage();
            System.err.println(responseBody);
            e.printStackTrace();
        } catch (IOException e) {
            responseBody = "Error during HTTP request: " + e.getMessage();
            System.err.println(responseBody);
            e.printStackTrace();
        } catch (Exception e) {
            responseBody = "Unexpected error during HTTP request: " + e.getMessage();
            System.err.println(responseBody);
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return new ApiResponse(statusCode, responseBody, responseHeaders);
    }

    public ApiResponse doGet(String urlString) {
        return this.sendRequest(urlString, "GET", null, null);
    }

    public ApiResponse doPost(String urlString, String requestBody) {
        return this.sendRequest(urlString, "POST", requestBody, Map.of("Content-Type", "application/json"));
    }

    public ApiResponse doPut(String urlString, String requestBody) {
        return this.sendRequest(urlString, "PUT", requestBody, Map.of("Content-Type", "application/json"));
    }

    public ApiResponse doDelete(String urlString) {
        return this.sendRequest(urlString, "DELETE", null, null);
    }

    public ApiResponse doOptions(String urlString) {
        return this.sendRequest(urlString, "OPTIONS", null, null);
    }
}