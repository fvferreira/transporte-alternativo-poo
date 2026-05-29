package model;

import java.util.ArrayList;
import java.util.List;

public class Viagem {
    private String id;
    private String origem;
    private String destino;
    private String horario;
    private double valor;
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
        this.passageiros = new ArrayList<>();
    }

    public boolean adicionarPassageiro(Passageiro passageiro, double pesoBagageiro, double pesoMao, double pesoAnimal, boolean eCaoGuiaOuApoio) {
        if (this.passageiros.size() >= this.veiculo.getCapacidade()) {
            return false;
        }

        if (pesoBagageiro > 30 || pesoMao > 5) {
            return false;
        }

        if (passageiro.getSaldo() < this.valor) {
            return false;
        }

        if (!eCaoGuiaOuApoio && (pesoAnimal < 8 || pesoAnimal > 12)) {
            return false;
        }

        this.passageiros.add(passageiro);
        return true;
    }

    public void editarViagem(String origem, String destino, String horario, double valor) {
        this.origem = origem;
        this.destino = destino;
        this.horario = horario;
        this.valor = valor;
    }

    public String verViagem() {
        return "ID: " + id + " | " + origem + " para " + destino + " | Horário: " + horario + " | Valor: " + valor;
    }

    public String getId() {
        return id;
    }

    public String getHorario() {
        return horario;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public List<Passageiro> getPassageiros() {
        return passageiros;
    }
    
    public Motorista getMotorista() {
        return motorista;
    }
}