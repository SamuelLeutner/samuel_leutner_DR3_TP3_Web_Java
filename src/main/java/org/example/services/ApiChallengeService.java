package org.example.services;

import org.example.clients.ApiChallengeClient;
import org.example.clients.ApiResponse;
import org.example.helpers.ExtractJsonHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ApiChallengeService {
    private static final String BaseURL = "https://apichallenges.eviltester.com";
    private static final ApiChallengeClient apiChallengeClient = new ApiChallengeClient();

    public static void ex1() {
        System.out.println("Exercício 1 GET simples de todas as entidades");
        String url = BaseURL + "/sim/entities";
        ApiResponse response = apiChallengeClient.doGet(url);

        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getResponseBody());
    }

    public static void ex2() {
        System.out.println("Exercício 2 GET de entidade específica");

        Map<Integer, String> responses = new HashMap<>();
        for (int id = 1; id <= 8; id++) {
            String url = BaseURL + "/sim/entities/" + id;
            ApiResponse response = apiChallengeClient.doGet(url);
            String responseBody = response.getResponseBody();

            System.out.println("Status Code para entidade " + id + ": " + response.getStatusCode());
            System.out.println("Response Body para entidade " + id + ": " + responseBody + "\n");

            responses.put(id, responseBody);
        }

        for (int i = 1; i <= 8; i++) {
            for (int j = i + 1; j <= 8; j++) {
                boolean equal = responses.get(i).equals(responses.get(j));
                System.out.println("Entidade " + i + " e " + j + " são iguais? " + equal);
            }
        }
    }

    public static void ex3() {
        System.out.println("Exercício 3 GET de entidade inexistente");

        String url = BaseURL + "/sim/entities/13";
        ApiResponse response = apiChallengeClient.doGet(url);

        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getResponseBody());
    }

    public static void ex4() {
        System.out.println("Exercício 4 GET com parâmetros na URL");

        String url = BaseURL + "/sim/entities?categoria=teste&limite=5";
        ApiResponse response = apiChallengeClient.doGet(url);

        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getResponseBody());
    }

    public static Long ex5() {
        System.out.println("Exercício 5 POST criando uma nova entidade");
        Long userId = null;

        String url = BaseURL + "/sim/entities";
        String requestBody = "{\"name\": \"aluno\"}";

        ApiResponse response = apiChallengeClient.doPost(url, requestBody);
        String responseBody = response.getResponseBody();

        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + responseBody);

        if (response.getStatusCode() == 201) {
            userId = Long.valueOf(Objects.requireNonNull(ExtractJsonHelper.value(responseBody, "id")));

            System.out.println("ID da entidade criado: " + userId);
        } else {
            System.out.println("Falha ao criar entidade. Status Code: " + response.getStatusCode());
        }

        return userId;
    }

    public static void ex6(Long id) {
        System.out.println("Exercício 6 GET da entidade criada");

        String url = BaseURL + "/sim/entities/" + id;
        ApiResponse response = apiChallengeClient.doGet(url);

        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getResponseBody());
    }

    public static void ex7() {
        System.out.println("Exercício 7 POST para atualizar uma entidade");

        String url = BaseURL + "/sim/entities/10";
        String requestBody = "{\"name\": \"atualizado\"}";

        ApiResponse response = apiChallengeClient.doPost(url, requestBody);
        String responseBody = response.getResponseBody();

        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + responseBody);

        if (response.getStatusCode() == 200) {
            System.out.println("Entidade atualizada com sucesso.");
            ex6(10L);
        } else {
            System.out.println("Falha ao atualizar entidade. Status Code: " + response.getStatusCode());
        }
    }

    public static void ex8() {
        System.out.println("Exercício 8 PUT para atualizar entidade");

        String url = BaseURL + "/sim/entities/10";
        String requestBody = "{\"name\": \"atualizado\"}";

        ApiResponse response = apiChallengeClient.doPut(url, requestBody);
        String responseBody = response.getResponseBody();

        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + responseBody);

        if (response.getStatusCode() == 200) {
            System.out.println("Entidade atualizada com sucesso.");
            ex6(10L);
        } else {
            System.out.println("Falha ao atualizar entidade. Status Code: " + response.getStatusCode());
        }
    }

    public static void ex9() {
        System.out.println("Exercício 9 DELETE de entidade válida");

        String url = BaseURL + "/sim/entities/9";

        ApiResponse response = apiChallengeClient.doDelete(url);
        Integer responseStatusCode = response.getStatusCode();

        System.out.println("Status Code: " + responseStatusCode);

        if (responseStatusCode == 204) {
            System.out.println("Entidade deletada com sucesso.");

            ex6(9L);
        } else {
            System.out.println("Falha ao deletar entidade. Status Code: " + responseStatusCode);
        }
    }

    public static void ex10() {
        System.out.println("Exercício 10 DELETE inválido");

        String url = BaseURL + "/sim/entities/2";

        ApiResponse response = apiChallengeClient.doDelete(url);
        Integer responseStatusCode = response.getStatusCode();

        System.out.println("Status Code: " + responseStatusCode);
        System.out.println("Response Body: " + response.getResponseBody());

        if (responseStatusCode == 403) {
            System.out.println("Tentativa de deletar entidade inválida capturada com sucesso.");
        } else {
            System.out.println("Falha ao capturar tentativa de deletar entidade inválida. Status Code: " + responseStatusCode);
        }
    }

    public static void ex11() {
        System.out.println("Exercício 11 OPTIONS com verificação de métodos");

        String url = BaseURL + "/sim/entities";

        ApiResponse response = apiChallengeClient.doOptions(url);
        Integer responseStatusCode = response.getStatusCode();
        Map<String, List<String>> headers = response.getHeaders();

        System.out.println("Status Code: " + responseStatusCode);

        if (responseStatusCode == 204 || responseStatusCode == 200) {
            System.out.println("Métodos HTTP permitidos:");
            List<String> allowMethods = headers.get("Allow");
            if (allowMethods != null) {
                for (String method : allowMethods) {
                    System.out.println("- " + method);
                }
            } else {
                System.out.println("Cabeçalho 'Allow' não encontrado.");
            }
        } else {
            System.out.println("Falha ao obter métodos permitidos. Status Code: " + responseStatusCode);
        }
    }

    public static void ex12() {
        System.out.println("Exercício 12 Experimentos com a Simple API");
    }
}
