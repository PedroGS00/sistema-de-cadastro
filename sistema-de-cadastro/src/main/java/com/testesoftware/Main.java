package com.testesoftware;

import java.util.Scanner;

public class Main {
    public static void main( String[] args ){
        Scanner sc = new Scanner(System.in);

        CadastroService cadastro = new CadastroService();

        System.out.println("### Sistema de cadastro de usuários ###");
        System.out.println("Digite o ID do usuário:");
        int id = sc.nextInt();
        sc.nextLine(); // Consumir a quebra de linha

        System.out.println("Digite o nome do usuário:");
        String nome = sc.nextLine();

        System.out.println("Digite o email do usuário:");
        String email = sc.nextLine();

        Usuario usuario = new Usuario(id, nome, email);
        cadastro.salvaUsuario(usuario);

        sc.close();
    }
    
}
