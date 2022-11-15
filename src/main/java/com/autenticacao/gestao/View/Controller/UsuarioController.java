package com.autenticacao.gestao.View.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autenticacao.gestao.Model.Usuario;
import com.autenticacao.gestao.Service.UsuarioService;
import com.autenticacao.gestao.View.Model.usuario.LoginRequest;
import com.autenticacao.gestao.View.Model.usuario.LoginResponse;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    public List<Usuario> obterTodos() {
        return usuarioService.obterTodos();
    }

    @GetMapping("/{id}")
    public Optional<Usuario> obterPorId(@PathVariable("id") Long id) {
        return usuarioService.obterPorId(id);

    }

    @PostMapping
    public Usuario adicionar(Usuario usuario) {
        return usuarioService.adicionar(usuario);
    }

    @PostMapping("/login")
    public LoginResponse login(LoginRequest request) {
        return usuarioService.logar(request.getEmail(), request.getSenha());
    }

}
