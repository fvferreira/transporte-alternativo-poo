package model;

public class Motorista extends Usuario {

    private String cnh;

    public Motorista(String id, String nome, String cpf, String telefone, String email, String senha, String cnh) {
        super(id, nome, cpf, telefone, email, senha);
        this.cnh = cnh;
    }

    public void cadastrar(String id, String nome, String cpf, String telefone, String email, String senha, String cnh) {
        realizarCadastro(id, nome, cpf, telefone, email, senha);
        this.cnh = cnh;
    }

    public void editar(String nome, String cpf, String telefone, String email, String senha, String cnh) {
        setNome(nome);
        setCpf(cpf);
        setTelefone(telefone);
        setEmail(email);
        setSenha(senha);
        this.cnh = cnh;
    }

    public void excluir() {
        setNome(null);
        setCpf(null);
        setTelefone(null);
        setEmail(null);
        setSenha(null);
        this.cnh = null;
    }

    public String getCnh() { return cnh; }
    public void setCnh(String cnh) { this.cnh = cnh; }

    @Override
    public String listar() {
        return "ID: " + getId() + " | Nome: " + getNome() + " | CNH: " + cnh;
    }
}