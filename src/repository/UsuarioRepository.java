package repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import model.Motorista;
import model.Passageiro;

public class UsuarioRepository {

    private static final String ARQUIVO_MOTORISTAS = "motoristas.txt";
    private static final String ARQUIVO_PASSAGEIROS = "passageiros.txt";

    public void salvarMotorista(Motorista motorista) {
        List<Motorista> motoristas = carregarMotoristas();
        motoristas.removeIf(m -> m.getId().equals(motorista.getId()));
        motoristas.add(motorista);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_MOTORISTAS))) {
            for (Motorista m : motoristas) {
                bw.write(m.getId() + ";" + m.getNome() + ";" + m.getCpf() + ";" + m.getTelefone() + ";" + m.getEmail() + ";" + m.getSenha() + ";" + m.getCnh());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void salvarPassageiro(Passageiro passageiro) {
        List<Passageiro> passageiros = carregarPassageiros();
        passageiros.removeIf(p -> p.getId().equals(passageiro.getId()));
        passageiros.add(passageiro);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_PASSAGEIROS))) {
            for (Passageiro p : passageiros) {
                bw.write(p.getId() + ";" + p.getNome() + ";" + p.getCpf() + ";" + p.getTelefone() + ";" + p.getEmail() + ";" + p.getSenha() + ";" + p.getSaldo());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Motorista> carregarMotoristas() {
        List<Motorista> motoristas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_MOTORISTAS))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                Motorista m = new Motorista(dados[0], dados[1], dados[2], dados[3], dados[4], dados[5], dados[6]);
                motoristas.add(m);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return motoristas;
    }

    public List<Passageiro> carregarPassageiros() {
        List<Passageiro> passageiros = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_PASSAGEIROS))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                Passageiro p = new Passageiro(dados[0], dados[1], dados[2], dados[3], dados[4], dados[5], Double.parseDouble(dados[6]));
                passageiros.add(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return passageiros;
    }

    public void excluirMotorista(String id) {
        List<Motorista> motoristas = carregarMotoristas();
        motoristas.removeIf(m -> m.getId().equals(id));
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_MOTORISTAS))) {
            for (Motorista m : motoristas) {
                bw.write(m.getId() + ";" + m.getNome() + ";" + m.getCpf() + ";" + m.getTelefone() + ";" + m.getEmail() + ";" + m.getSenha() + ";" + m.getCnh());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void excluirPassageiro(String id) {
        List<Passageiro> passageiros = carregarPassageiros();
        passageiros.removeIf(p -> p.getId().equals(id));
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_PASSAGEIROS))) {
            for (Passageiro p : passageiros) {
                bw.write(p.getId() + ";" + p.getNome() + ";" + p.getCpf() + ";" + p.getTelefone() + ";" + p.getEmail() + ";" + p.getSenha() + ";" + p.getSaldo());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}