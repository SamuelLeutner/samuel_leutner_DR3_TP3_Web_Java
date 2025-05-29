package org.example.services;

import org.example.clients.ApiChallengeClient;
import org.example.clients.ApiResponse;

public class ApiChallengeService {
    private static final ApiChallengeClient apiChallengeClient = new ApiChallengeClient();
    private static final String BaseURL = "https://apichallenges.eviltester.com";

    public static void ex1() {
        System.out.println("Exercício 1 GET simples de todas as entidades");
        String url = BaseURL + "/sim/entities";
        ApiResponse response = apiChallengeClient.doGet(url);

        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getResponseBody());
    }

    public static void ex2() {
        System.out.println("Exercício 2 GET de entidade específica");
    }

    public static void ex3() {
        System.out.println("Exercício 3 GET de entidade inexistente");
    }

    public static void ex4() {
        System.out.println("Exercício 4 GET com parâmetros na URL");
    }

    public static Long ex5() {
        System.out.println("Exercício 5 POST criando uma nova entidade");
        Long userId = null;
        return userId;
    }

    public static void ex6(Long id) {
        System.out.println("Exercício 6 GET da entidade criada");
    }

    public static void ex7() {
        System.out.println("Exercício 7 POST para atualizar uma entidade");
    }

    public static void ex8() {
        System.out.println("Exercício 8 PUT para atualizar entidade");
    }

    public static void ex9() {
        System.out.println("Exercício 9 DELETE de entidade válida");
    }

    public static void ex10() {
        System.out.println("Exercício 10 DELETE inválido");
    }

    public static void ex11() {
        System.out.println("Exercício 11 OPTIONS com verificação de métodos");
    }

    public static void ex12() {
        System.out.println("Exercício 12 Experimentos com a Simple API");
    }
}
