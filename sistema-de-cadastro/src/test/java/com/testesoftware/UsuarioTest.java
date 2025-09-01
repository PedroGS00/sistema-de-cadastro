package com.testesoftware;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.testesoftware.service.UsuarioService;

public class UsuarioTest {
    private static final String DB_URL = "jdbc:postgresql://db.qasoimiiiiguwthmcbgb.supabase.co:5432/postgres?sslmode=require";
    private static final String USER = "postgres";
    private static final String PASSWORD = "senac@2025";

    private UsuarioService service;

    @BeforeEach
    public void setUp() {
        service = new UsuarioService();
        // Limpa a tabela antes de cada teste para garantir isolamento
        try (java.sql.Connection conn = java.sql.DriverManager.getConnection(DB_URL, USER, PASSWORD);
             java.sql.Statement stmt = conn.createStatement()) {
            String sql = "DELETE FROM usuarios";
            stmt.executeUpdate(sql);
        } catch (java.sql.SQLException e) {
         e.printStackTrace();        
        }
    }

    @Test
    public void testCadastrarUsuario() {
        // Cenário de teste: Cadastrar um novo usuário
        // Ação: Chamar o método cadastrar
        service.cadastrar("Pedro", "Silva", "pedro.silva@senac.com ","senha123");

        // Verificação: Verificar se a lista de IDs agora tem 1 elemento
        List<Integer> usuarios = service.listarUsuarios();
        Assertions.assertEquals(1, usuarios.size());
    }

    @Test
    public void testAtualizarUsuario() {
        // Cenário de teste: Atualizar um usuário existente
        service.cadastrar("Ryan", "Cavalcante", "ryan@senac.com", "ryan123");
        int id = service.listarUsuarios().get(0);

        service.atualizarUsuario(id, "Ryan", "Cavalcante", "ryan.jc@senac.com", "novaSenha123");
    }
    
    @Test
    public void testExcluirUsuario() {
        // Cenário de teste: Excluir um usuário existente
        service.cadastrar("Matheus", "Assunção", "matheus.assunção@senac.com", "matheus123");
        int id = service.listarUsuarios().get(0);

        service.excluirUsuario(id);

        List<Integer> usuarios = service.listarUsuarios();
        Assertions.assertTrue(usuarios.isEmpty());
    }


}
