package com.testesoftware.service;

import java.sql.DriverManager;
import java.util.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class UsuarioService {

    private static final String DB_URL = "jdbc:postgresql://db.qasoimiiiiguwthmcbgb.supabase.co:5432/postgres?user=postgres&password=senac@2025";
    private static final String USER = "postgres";
    private static final String PASSWORD = "senac@2025";
    

   private java.sql.Connection getConnection() throws java.sql.SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    public void cadastrar (String nome, String sobrenome, String email, String senha) {
        String sql = "INSERT INTO usuarios (nome, sobrenome, email, senha_hash) VALUES (?, ?, ?, ?)";
        try (java.sql.Connection conn = getConnection();
             java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String senhaHash = BCrypt.hashpw(senha, BCrypt.gensalt());

            pstmt.setString(1, nome);
            pstmt.setString(2, sobrenome);
            pstmt.setString(3, email);
            pstmt.setString(4, senhaHash);
            pstmt.executeUpdate();

            System.out.println("Usuário " + nome + " cadastrado com sucesso!");
             } catch (java.sql.SQLException e) {
            System.out.println("Erro ao cadastrar o usuário: " + e.getMessage());
             }   
    }

    public void atualizarUsuario(int id , String nome, String sobrenome,String email, String senhaHash) {
        String sql = "UPDATE usuarios SET nome = ?, sobrenome = ?, email = ?, senha_hash = ? WHERE id = ?";
        try (java.sql.Connection conn = getConnection();
                java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setString(2, sobrenome);
            pstmt.setString(3, email);
            pstmt.setInt(id, id);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Usuário com ID " + id + " atualizado com sucesso!");
            } else {
                System.out.println("Erro: Usuário com ID " + id + " não encontrado.");
            }
        } catch (java.sql.SQLException e) {
            System.out.println("Erro ao atualizar o usuário: " + e.getMessage());
        }    
    }

    public void excluirUsuario(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (java.sql.Connection conn = getConnection();
                java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Usuário com ID " + id + " excluído com sucesso!");
            } else {
                System.out.println("Erro: Usuário com ID " + id + " não encontrado.");
            }
        } catch (java.sql.SQLException e) {
            System.out.println("Erro ao excluir o usuário: " + e.getMessage());
        }    
    }

    public List <Integer> listarUsuarios() {
        List <Integer> usuarios = new ArrayList<>();
        String sql = "SELECT id FROM usuarios ORDER BY id";
        try (java.sql.Connection conn = getConnection();
            java.sql.Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                usuarios.add(rs.getInt("id"));
            }
        } catch (java.sql.SQLException e) {
            System.out.println("Erro ao listar os usuários: " + e.getMessage());
        }

        return usuarios;
    }

}
