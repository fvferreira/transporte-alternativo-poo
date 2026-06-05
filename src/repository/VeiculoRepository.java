package repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import model.Veiculo;

public class VeiculoRepository {

    private static final String ARQUIVO_VEICULOS = "veiculos.txt";

    public void salvarVeiculo(Veiculo veiculo) {
        List<Veiculo> veiculos = carregarVeiculos();
        veiculos.removeIf(v -> v.getPlaca().equals(veiculo.getPlaca()));
        veiculos.add(veiculo);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_VEICULOS))) {
            for (Veiculo v : veiculos) {
                bw.write(v.getPlaca() + ";" + v.getModelo() + ";" + v.getCapacidade() + ";" + v.getAno());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Veiculo> carregarVeiculos() {
        List<Veiculo> veiculos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_VEICULOS))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                Veiculo v = new Veiculo(dados[0], dados[1], Integer.parseInt(dados[2]), Integer.parseInt(dados[3]));
                veiculos.add(v);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return veiculos;
    }

    public void excluirVeiculo(String placa) {
        List<Veiculo> veiculos = carregarVeiculos();
        veiculos.removeIf(v -> v.getPlaca().equals(placa));
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_VEICULOS))) {
            for (Veiculo v : veiculos) {
                bw.write(v.getPlaca() + ";" + v.getModelo() + ";" + v.getCapacidade() + ";" + v.getAno());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}