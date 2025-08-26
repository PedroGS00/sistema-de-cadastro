package com.testesoftware.service;

import java.io.*;
import com.google.gson.Gson;
import com.testesoftware.model.Usuario;

public class UsuarioService {
    private static final Gson gson = new Gson();

    public Usuario cadastrar (String nome, String sobrenome, String email, String senhaHash) {
    
        int id = (int) (System.currentTimeMillis() / 100); 
        Usuario novoUsuario = new Usuario(id,nome, sobrenome, email, senhaHash);
        salvaUsuario(novoUsuario);

        System.out.println("Usuário " + nome + " cadastrado com sucesso!");
        return novoUsuario;
    }

    public void atualizarUsuario(int id , String nome, String sobrenome,String email, String senhaHash) {
        Usuario userAtualizado = buscarUsuario(id);
        if (userAtualizado != null) {
            System.out.println("Erro: Usuário com ID " + id + " não encontrado.");
            return;
        }
        
        userAtualizado.setNome(nome);
        userAtualizado.setEmail(email);
        userAtualizado.setSobrenome(sobrenome);
        userAtualizado.setSenhaHash(senhaHash);
        salvaUsuario(userAtualizado);
        System.out.println("Usuário " + nome + " atualizado com sucesso!");
    }

    public void excluirUsuario(int id) {
        Usuario userParaExcluir = buscarUsuario(id);
        if (userParaExcluir == null) {
            System.out.println("Erro: Usuário com ID " + id + " não encontrado.");
            return;
        }

        String usuarioJson = "src/main/java/com/testesoftware/" + id + ".json";
        File file = new File("usuarios/" + usuarioJson);
        if (file.delete()) {
            System.out.println("Usuário com ID " + id + " excluído com sucesso!");
        } else {
            System.out.println("Erro ao excluir o usuário com ID " + id + ".");
        }
    }


    public void salvaUsuario (Usuario usuario) {
        String usuarioJson = "src/main/java/com/testesoftware/" + usuario.getId() + ".json";
        File file = new File("usuarios/" + usuarioJson);
        
        File dir = file.getParentFile();
        if (!dir.exists()) {
        dir.mkdirs();
    }

        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(usuario, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar o usuário: " + e.getMessage());
        }
    }

    public Usuario buscarUsuario(int id) {
        String usuarioJson = "src/main/java/com/testesoftware/" + id + ".json";
        File file = new File("usuarios/" + usuarioJson);
        
        if (!file.exists()) {
            return null;
        }

        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, Usuario.class);
        } catch (IOException e) {
            System.out.println("Erro ao buscar o usuário: " + e.getMessage());
            return null;
        }
    }



}
