package com.testesoftware.controller;

import com.testesoftware.service.UsuarioService;

public class UsuarioController {
    private UsuarioService usuarioService;

    public UsuarioController() {
        this.usuarioService = new UsuarioService();
    }

    public void cadastrarUsuario(String nome, String sobremone, String email, String senhaHash) {
        usuarioService.cadastrar(nome, sobremone, email, senhaHash);
    }

    public void atualizarUsuario(int id, String nome, String sobrenome, String email, String senhaHash) {
        usuarioService.atualizarUsuario(id, nome, sobrenome, email, senhaHash);
    }

    public void excluirUsuario(int id) {
        usuarioService.excluirUsuario(id);
    }

}
