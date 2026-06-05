package model;

import java.util.ArrayList;
import java.util.List;

public class Viagem {

    private String id;
    private String origem;
    private String destino;
    private String horario;
    private double valor;
    private int vagasDisponiveis;
    private String status;
    private Motorista motorista;
    private Veiculo veiculo;
    private List<Passageiro> passageiros;

    public Viagem(String id, String origem, String destino, String horario, double valor, Motorista motorista, Veiculo veiculo) {
        this.id = id;
        this.origem = origem;
        this.destino = destino;
        this.horario = horario;
        this.valor = valor;
        this.motorista = motorista;
        this.veiculo = veiculo;
        this.vagasDisponiveis = veiculo.getCapacidade();
        this.status = "AGENDADA";
        this.passageiros = new ArrayList<>();
    }

    public void criarViagem(String id, String origem, String destino, String horario, double valor, Motorista motorista, Veiculo veiculo) {
        this.id = id;
        this.origem = origem;
        this.destino = destino;
        this.horario = horario;
        this.valor = valor;
        this.motorista = motorista;
        this.veiculo = veiculo;
        this.vagasDisponiveis = veiculo.getCapacidade();
        this.status = "AGENDADA";
    }

    public void cancelarViagem() {
        this.status = "CANCELADA";
    }

    public String listarViagens() {
        return "ID: " + id + " | " + origem + " -> " + destino + " | Horario: " + horario + " | Valor: R$" + valor + " | Vagas: " + vagasDisponiveis + " | Status: " + status;
    }

    public boolean validarVagas() {
        return vagasDisponiveis > 0;
    }

    public boolean adicionarPassageiro(Passageiro passageiro, double pesoBagageiro, double pesoMao, double pesoAnimal, boolean eCaoGuiaOuApoio) {
        if (!validarVagas()) {
            return false;
        }
        if (pesoBagageiro > 30 || pesoMao > 5) {
            return false;
        }
        if (passageiro.getSaldo() < this.valor) {
            return false;
        }
        if (pesoAnimal > 0 && !eCaoGuiaOuApoio && (pesoAnimal < 8 || pesoAnimal > 12)) {
            return false;
        }
        this.passageiros.add(passageiro);
        this.vagasDisponiveis--;
        return true;
    }

    public void iniciarViagem() {
        this.status = "EM_ANDAMENTO";
    }

    public void finalizarViagem() {
        this.status = "FINALIZADA";
    }

    public String getId() { return id; }
    public String getOrigem() { return origem; }
    public String getDestino() { return destino; }
    public String getHorario() { return horario; }
    public double getValor() { return valor; }
    public int getVagasDisponiveis() { return vagasDisponiveis; }
    public String getStatus() { return status; }
    public Motorista getMotorista() { return motorista; }
    public Veiculo getVeiculo() { return veiculo; }
    public List<Passageiro> getPassageiros() { return passageiros; }
}