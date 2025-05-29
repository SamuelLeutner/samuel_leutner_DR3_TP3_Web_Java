package org.example.clients;

import java.util.Map;
import java.util.List;

public class ApiResponse {
    private final int statusCode;
    private final String responseBody;
    private final Map<String, List<String>> headers;

    public ApiResponse(int statusCode, String responseBody, Map<String, List<String>> headers) {
        this.statusCode = statusCode;
        this.responseBody = responseBody;
        this.headers = headers;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }
}