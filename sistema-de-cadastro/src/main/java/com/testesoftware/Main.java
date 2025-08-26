package com.testesoftware;

import java.util.Scanner;
import com.testesoftware.controller.UsuarioController;


public class Main {
    public static void main(String[] args) {
        UsuarioController controller = new UsuarioController();

        Scanner sc = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0){
            System.out.println(
                "Menu:\n" +
                "1 - Cadastrar Usuário\n" +
                "2 - Atualizar Usuário\n" +
                "3 - Excluir Usuário\n" +
                "0 - Sair"
            );
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();
                    System.out.println("Sobrenome: ");
                    String sobrenome = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("Senha: ");
                    String senha = sc.nextLine();
                    controller.cadastrarUsuario(nome, sobrenome, email, senha);
                    break;
                case 2:
                    System.out.print("ID do usuário a ser atualizado: ");
                    int idAtualizar = sc.nextInt();
                    sc.nextLine(); 
                    System.out.print("Novo Nome: ");
                    String novoNome = sc.nextLine();
                    System.out.println("Novo Sobrenome: ");
                    String novoSobrenome = sc.nextLine();
                    System.out.print("Novo Email: ");
                    String novoEmail = sc.nextLine();
                    System.out.print("Nova Senha: ");
                    String novaSenha = sc.nextLine();
                    controller.atualizarUsuario(idAtualizar, novoNome, novoSobrenome, novoEmail, novaSenha);
                    break;
                case 3:
                    System.out.print("ID do usuário a ser excluído: ");
                    int idExcluir = sc.nextInt();
                    controller.excluirUsuario(idExcluir);
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
        sc.close();
    }    
}
