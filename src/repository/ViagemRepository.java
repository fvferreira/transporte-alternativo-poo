package repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import model.Motorista;
import model.Veiculo;
import model.Viagem;

public class ViagemRepository {

    private static final String ARQUIVO_VIAGENS = "viagens.txt";

    public void salvarViagem(Viagem viagem) {
        List<String> linhas = carregarLinhas();
        linhas.removeIf(l -> l.startsWith(viagem.getId() + ";"));
        linhas.add(viagem.getId() + ";" + viagem.getOrigem() + ";" + viagem.getDestino() + ";" + viagem.getHorario() + ";" + viagem.getValor() + ";" + viagem.getVagasDisponiveis() + ";" + viagem.getStatus() + ";" + viagem.getMotorista().getId() + ";" + viagem.getVeiculo().getPlaca());
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_VIAGENS))) {
            for (String linha : linhas) {
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Viagem> carregarViagens(UsuarioRepository usuarioRepository, VeiculoRepository veiculoRepository) {
        List<Viagem> viagens = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_VIAGENS))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                Motorista motorista = usuarioRepository.carregarMotoristas().stream()
                        .filter(m -> m.getId().equals(dados[7]))
                        .findFirst().orElse(null);
                Veiculo veiculo = veiculoRepository.carregarVeiculos().stream()
                        .filter(v -> v.getPlaca().equals(dados[8]))
                        .findFirst().orElse(null);
                if (motorista != null && veiculo != null) {
                    Viagem v = new Viagem(dados[0], dados[1], dados[2], dados[3], Double.parseDouble(dados[4]), motorista, veiculo);
                    viagens.add(v);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return viagens;
    }

    public void excluirViagem(String id) {
        List<String> linhas = carregarLinhas();
        linhas.removeIf(l -> l.startsWith(id + ";"));
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_VIAGENS))) {
            for (String linha : linhas) {
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> carregarLinhas() {
        List<String> linhas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_VIAGENS))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhas.add(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return linhas;
    }
}