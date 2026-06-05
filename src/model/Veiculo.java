package model;

public class Veiculo {

    private String placa;
    private String modelo;
    private int capacidade;
    private int ano;

    public Veiculo(String placa, String modelo, int capacidade, int ano) {
        this.placa = placa;
        this.modelo = modelo;
        this.capacidade = capacidade;
        this.ano = ano;
    }

    public void cadastrar(String placa, String modelo, int capacidade, int ano) {
        this.placa = placa;
        this.modelo = modelo;
        this.capacidade = capacidade;
        this.ano = ano;
    }

    public void editar(String placa, String modelo, int capacidade, int ano) {
        this.placa = placa;
        this.modelo = modelo;
        this.capacidade = capacidade;
        this.ano = ano;
    }

    public void excluir() {
        this.placa = null;
        this.modelo = null;
        this.capacidade = 0;
        this.ano = 0;
    }

    public String listar() {
        return "Placa: " + placa + " | Modelo: " + modelo + " | Capacidade: " + capacidade + " | Ano: " + ano;
    }

    public String getPlaca() { return placa; }
    public String getModelo() { return modelo; }
    public int getCapacidade() { return capacidade; }
    public int getAno() { return ano; }

    public void setPlaca(String placa) { this.placa = placa; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public void setCapacidade(int capacidade) { this.capacidade = capacidade; }
    public void setAno(int ano) { this.ano = ano; }
}