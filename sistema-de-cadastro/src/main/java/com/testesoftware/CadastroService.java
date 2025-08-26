package com.testesoftware;

import java.io.*;
import com.google.gson.Gson;

public class CadastroService {
    private static final Gson gson = new Gson();

    public void salvaUsuario (Usuario usuario) {
        String usuarioJson = "src/main/java/com/testesoftware/" + usuario.getId() + ".json";
        File file = new File("usuarios/" + usuarioJson);
        
        File dir = file.getParentFile();
        if (!dir.exists()) {
        dir.mkdirs();
    }

        if (file.exists()) {
            System.out.println("Usuário com ID " + usuario.getId() + " já existe.");
            return;
        }

        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(usuario, writer);
            System.out.println("Usuário: " + usuario.getNome() + "cadastrado com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar o usuário: " + e.getMessage());
        }
    }

}
