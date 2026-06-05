package controller;

import java.util.ArrayList;
import java.util.List;
import model.Motorista;
import model.Passageiro;
import model.Viagem;

public class ViagemController {

    private List<Viagem> viagens;

    public ViagemController() {
        this.viagens = new ArrayList<>();
    }

    public boolean criarViagem(Viagem novaViagem) {
        if (novaViagem.getMotorista() == null) {
            return false;
        }
        for (Viagem v : viagens) {
            if (v.getVeiculo().equals(novaViagem.getVeiculo()) && v.getHorario().equals(novaViagem.getHorario())) {
                return false;
            }
        }
        viagens.add(novaViagem);
        return true;
    }

    public void cancelarViagem(String id) {
        for (Viagem v : viagens) {
            if (v.getId().equals(id)) {
                v.cancelarViagem();
                return;
            }
        }
    }

    public List<Viagem> listarViagens() {
        return new ArrayList<>(viagens);
    }

    public boolean validarVagas(String id) {
        for (Viagem v : viagens) {
            if (v.getId().equals(id)) {
                return v.validarVagas();
            }
        }
        return false;
    }

    public boolean reservarVaga(String viagemId, Passageiro passageiro, double pesoBagageiro, double pesoMao, double pesoAnimal, boolean eCaoGuiaOuApoio) {
        if (verificarConflitoPassageiro(passageiro, obterHorario(viagemId))) {
            return false;
        }
        for (Viagem v : viagens) {
            if (v.getId().equals(viagemId)) {
                return v.adicionarPassageiro(passageiro, pesoBagageiro, pesoMao, pesoAnimal, eCaoGuiaOuApoio);
            }
        }
        return false;
    }

    public double calcularReembolsoCancelamento(double valorViagem, int horasRestantes) {
        if (horasRestantes < 24) {
            return valorViagem * 0.9;
        }
        return valorViagem;
    }

    public boolean iniciarViagem(String viagemId, Motorista motorista) {
        for (Viagem v : viagens) {
            if (v.getId().equals(viagemId) && v.getMotorista().getId().equals(motorista.getId())) {
                v.iniciarViagem();
                return true;
            }
        }
        return false;
    }

    public boolean finalizarViagem(String viagemId, Motorista motorista) {
        for (Viagem v : viagens) {
            if (v.getId().equals(viagemId) && v.getMotorista().getId().equals(motorista.getId())) {
                v.finalizarViagem();
                return true;
            }
        }
        return false;
    }

    private boolean verificarConflitoPassageiro(Passageiro passageiro, String horario) {
        if (horario == null) return false;
        for (Viagem v : viagens) {
            if (v.getPassageiros().contains(passageiro) && v.getHorario().equals(horario)) {
                return true;
            }
        }
        return false;
    }

    private String obterHorario(String viagemId) {
        for (Viagem v : viagens) {
            if (v.getId().equals(viagemId)) {
                return v.getHorario();
            }
        }
        return null;
    }
}