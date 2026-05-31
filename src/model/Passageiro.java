package model;

public class Passageiro extends Usuario {

    private double saldoCarteira;

    public Passageiro(String id, String nome, String cpf, String telefone, String email, String senha, double saldoCarteira) {
        super(id, nome, id, telefone, email, senha);
        this.saldoCarteira = saldoCarteira;
    }

    public void cadastrar(String id, String nome, String cpf, String telefone, String email, String senha) {
        realizarCadastro(id, nome, id, telefone, email, senha);
        this.saldoCarteira = 0;
    }

    public void editar(String nome, String cpf, String telefone, String email, String senha) {
        setNome(nome);
        setCpf(cpf);
        setTelefone(telefone);
        setEmail(email);
        setSenha(senha);
    }

    public void desativar() {
        setSenha(null);
    }

    @Override
    public String listar() {
        return "ID: " + getId() + " | Nome: " + getNome() + " | Saldo: R$" + saldoCarteira;
    }

    public void adicionarSaldo(double valor) {
        this.saldoCarteira += valor;
    }

    public double getSaldo() {
        return saldoCarteira;
    }

    public void setSaldoCarteira(double saldoCarteira) {
        this.saldoCarteira = saldoCarteira;
    }

}
