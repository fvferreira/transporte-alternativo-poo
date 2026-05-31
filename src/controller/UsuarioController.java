package controller;

import java.util.ArrayList;
import java.util.List;
import model.Usuario;

public class UsuarioController {

    private List<Usuario> usuarios;

    public UsuarioController() {
        this.usuarios = new ArrayList<>();
    }

    public void cadastrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public Usuario autenticar(String email, String senha) {
        for (Usuario u : usuarios) {
            if (u.realizarLogin(email, email)) {
                return u;
            }
        }
        return null;
    }

    public void editarUsuario(String id, String nome, String cpf, String telefone, String email, String senha) {
        for (Usuario u : usuarios) {
            if (u.getId().equals(id)) {
                u.setNome(nome);
                u.setCpf(cpf);
                u.setTelefone(telefone);
                u.setEmail(email);
                u.setSenha(senha);
                return;
            }
        }
    }

    public Usuario carregarPerfil(String id) {
        for (Usuario u : usuarios) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        return null;
    }

    public boolean validarCadastro(String cpf, String email) {
        for (Usuario u : usuarios) {
            if (u.getCpf().equals(cpf) || u.getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }

    public List<Usuario> listarUsuarios() {
        return new ArrayList<>(usuarios);
    }
}
