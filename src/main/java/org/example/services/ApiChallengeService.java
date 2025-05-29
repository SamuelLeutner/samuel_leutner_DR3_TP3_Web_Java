package org.example.services;

import org.example.clients.ApiChallengeClient;
import org.example.clients.ApiResponse;
import org.example.helpers.ExtractJsonHelper;

import java.util.*;

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

    public static void ex12() throws InterruptedException {
        System.out.println("Exercício 12 Experimentos com a Simple API");

        String generatedIsbn = null;
        String createdItemId;

        String urlItems = BaseURL + "/simpleapi/items";
        String randomISBN = BaseURL + "/simpleapi/randomisbn";

        System.out.println("\n1. GET todos os itens (estado inicial) ");
        ApiResponse getItemsInitialResponse = apiChallengeClient.doGet(urlItems);
        System.out.println("Status Code: " + getItemsInitialResponse.getStatusCode());
        System.out.println("Response Body: " + getItemsInitialResponse.getResponseBody());

        System.out.println("\n2. Gerar ISBN aleatório ");
        ApiResponse getRandomIsbnResponse = apiChallengeClient.doGet(randomISBN);

        if (getRandomIsbnResponse.getStatusCode() == 200) {
            generatedIsbn = getRandomIsbnResponse.getResponseBody();
            if (generatedIsbn != null) {
                System.out.println("ISBN aleatório gerado: " + generatedIsbn);
            } else {
                System.out.println("Não foi possível extrair o ISBN aleatório.");
            }
        } else {
            System.out.println("Falha ao gerar ISBN aleatório. Status: " + getRandomIsbnResponse.getStatusCode());
        }

        if (generatedIsbn == null) {
            System.out.println("Não foi possível continuar com os exercícios de POST/PUT/DELETE sem um ISBN gerado.");
            return;
        }

        System.out.println("\n3. Criar item com POST ");

        String postItemBody = String.format("{\"isbn13\": \"%s\", \"type\": \"book\", \"price\": 8.53, \"numberinstock\": 14}", generatedIsbn);
        System.out.println("Corpo da requisição POST: " + postItemBody);

        ApiResponse postItemResponse = apiChallengeClient.doPost(urlItems, postItemBody);
        System.out.println("Status Code: " + postItemResponse.getStatusCode());
        System.out.println("url: " + urlItems);
        System.out.println(postItemResponse.getResponseBody());

        if (postItemResponse.getStatusCode() == 201) {
            createdItemId = ExtractJsonHelper.value(postItemResponse.getResponseBody(), "id");
            if (createdItemId != null) {
                System.out.println("Item criado com sucesso. ID do item: " + createdItemId);
            } else {
                System.out.println("Item criado, mas não foi possível extrair o ID.");
            }
        } else {
            createdItemId = null;
            System.out.println("Falha ao criar item. Status Code: " + postItemResponse.getStatusCode());
        }

        if (createdItemId == null) {
            System.out.println("Não foi possível continuar com os exercícios de PUT/DELETE sem o ID do item criado.");
            return;
        }

        System.out.println("\n4. Atualizar item com PUT ");

        String putItemUrl = urlItems + "/" + createdItemId;
        String putItemBody = String.format("{\"id\": \"%s\",\"isbn13\": \"%s\", \"type\": \"book\", \"price\": 10.53, \"numberinstock\": 20}", createdItemId, generatedIsbn);

        System.out.println("URL PUT: " + putItemUrl);
        System.out.println("Corpo da requisição PUT: " + putItemBody);

        ApiResponse putItemResponse = apiChallengeClient.doPut(putItemUrl, putItemBody);

        if (putItemResponse.getStatusCode() == 200) {
            System.out.println("Item atualizado com sucesso via PUT.");
        } else {
            System.out.println("Falha ao atualizar item. Status Code: " + putItemResponse.getStatusCode());
        }

        System.out.println("\n5. Remover item com DELETE ");

        String deleteItemUrl = urlItems + "/" + createdItemId;

        System.out.println("URL DELETE: " + deleteItemUrl);
        ApiResponse deleteItemResponse = apiChallengeClient.doDelete(deleteItemUrl);
        System.out.println(deleteItemResponse.getStatusCode());
        System.out.println(deleteItemResponse.getResponseBody());

        if (deleteItemResponse.getStatusCode() == 204 || deleteItemResponse.getStatusCode() == 200) {
            System.out.println("Item removido com sucesso via DELETE.");
        } else {
            System.out.println("Falha ao remover item. Status Code: " + deleteItemResponse.getStatusCode());
        }

        System.out.println("\n6. GET todos os itens (estado final, após manipulações) ");
        ApiResponse getItemsFinalResponse = apiChallengeClient.doGet(urlItems);
        System.out.println("Status Code: " + getItemsFinalResponse.getStatusCode());
        System.out.println("Response Body: " + getItemsFinalResponse.getResponseBody());

        List<String> ids = Collections.singletonList(ExtractJsonHelper.value(getItemsFinalResponse.getResponseBody(), "id"));
        boolean itemExists = ids.stream().anyMatch(id -> id.equals(createdItemId));
        if (!itemExists) {
            System.out.println("\nO item criado foi removido com sucesso.");
        } else {
            System.out.println("\nO item criado ainda existe, o que indica que a remoção falhou.");
        }
    }
}
