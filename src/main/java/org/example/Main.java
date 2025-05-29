package org.example;

import java.util.Scanner;
import org.example.services.ApiChallengeService;

public class Main {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            Long lastCreatedEntityId = null;

            while (true) {
                System.out.println("Bem-vindo ao TP 3 Web Java de Samuel!");
                System.out.println("\n--- Menu Principal ---");
                System.out.println("Escolha uma parte do projeto:");
                System.out.println("0 - Sair");
                System.out.println("1 - Exercício 1: GET simples de todas as entidades");
                System.out.println("2 - Exercício 2: GET de entidade específica");
                System.out.println("3 - Exercício 3: GET de entidade inexistente");
                System.out.println("4 - Exercício 4: GET com parâmetros na URL");
                System.out.println("5 - Exercício 5: POST criando uma nova entidade");
                System.out.println("6 - Exercício 6: GET da entidade criada");
                System.out.println("7 - Exercício 7: POST para atualizar uma entidade");
                System.out.println("8 - Exercício 8: PUT para atualizar entidade");
                System.out.println("9 - Exercício 9: DELETE de entidade válida");
                System.out.println("10 - Exercício 10: DELETE inválido");
                System.out.println("11 - Exercício 11: OPTIONS com verificação de métodos");
                System.out.println("12 - Exercício 12: Experimentos com a Simple API");

                System.out.print("Sua opção: ");
                int parte;
                try {
                    parte = sc.nextInt();
                } catch (java.util.InputMismatchException e) {
                    System.out.println("Entrada inválida. Por favor, digite um número.");
                    sc.nextLine();
                    continue;
                }
                sc.nextLine();

                if (parte == 0) {
                    System.out.println("Encerrando o programa...");
                    return;
                }

                switch (parte) {
                    case 1:
                        ApiChallengeService.ex1();
                        break;
                    case 2:
                        ApiChallengeService.ex2();
                        break;
                    case 3:
                        ApiChallengeService.ex3();
                        break;
                    case 4:
                        ApiChallengeService.ex4();
                        break;
                    case 5:
                        lastCreatedEntityId = ApiChallengeService.ex5();
                        break;
                    case 6:
                        if (lastCreatedEntityId != null) {
                            ApiChallengeService.ex6(lastCreatedEntityId);
                        } else {
                            System.out.println("Por favor, execute o Exercício 5 primeiro para criar uma entidade.");
                        }
                        break;
                    case 7:
                        ApiChallengeService.ex7();
                        break;
                    case 8:
                        ApiChallengeService.ex8();
                        break;
                    case 9:
                        ApiChallengeService.ex9();
                        break;
                    case 10:
                        ApiChallengeService.ex10();
                        break;
                    case 11:
                        ApiChallengeService.ex11();
                        break;
                    case 12:
                        ApiChallengeService.ex12();
                        break;
                    default:
                        System.out.println("Opção inválida! Por favor, escolha um número de 0 a 12.");
                }

                System.out.println("\nPressione Enter para continuar...");
                sc.nextLine();
            }
        }
    }
}