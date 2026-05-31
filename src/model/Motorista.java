package model;

public class Motorista extends Usuario {

    private String cnh;

    //construtor Motorista 
    public Motorista(String id, String nome, String cpf, String telefone, String email, String senha, String cnh) {
        super(id, nome, cpf, telefone, email, senha);
        this.cnh = cnh;
    }

    public void cadastrar(String id, String nome, String cpf, String telefone, String email, String senha, String cnh) {
        realizarCadastro(id, nome, cpf, telefone, email, senha);
        this.cnh = cnh;
    }

    public void editar(String nome, String cpf, String telefone, String email, String senha) {
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

    @Override
    public String listar() {
        return "ID: " + getId() + " | Nome: " + getNome() + " | CNH: " + cnh;
    }

}
