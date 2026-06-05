package view;

import controller.UsuarioController;
import controller.ViagemController;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import java.util.UUID;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import model.Motorista;
import model.Veiculo;
import model.Viagem;
import repository.UsuarioRepository;
import repository.VeiculoRepository;
import repository.ViagemRepository;

public class MotoristaFrame extends JFrame {

    private Motorista motorista;
    private UsuarioController usuarioController;
    private ViagemController viagemController;
    private UsuarioRepository usuarioRepository;
    private VeiculoRepository veiculoRepository;
    private ViagemRepository viagemRepository;
    private List<Veiculo> veiculos;

    public MotoristaFrame(Motorista motorista, UsuarioController usuarioController, UsuarioRepository usuarioRepository, VeiculoRepository veiculoRepository, ViagemRepository viagemRepository) {
        this.motorista = motorista;
        this.usuarioController = usuarioController;
        this.usuarioRepository = usuarioRepository;
        this.veiculoRepository = veiculoRepository;
        this.viagemRepository = viagemRepository;
        this.viagemController = new ViagemController();
        this.veiculos = veiculoRepository.carregarVeiculos();
        carregarViagens();
        configurarJanela();
    }

    private void carregarViagens() {
        for (Viagem v : viagemRepository.carregarViagens(usuarioRepository, veiculoRepository)) {
            viagemController.criarViagem(v);
        }
    }

    private void configurarJanela() {
        setTitle("Menu Motorista - " + motorista.getNome());
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Perfil", criarAbaPerfil());
        abas.addTab("Veiculos", criarAbaVeiculos());
        abas.addTab("Viagens", criarAbaViagens());

        add(abas);
    }

    private JPanel criarAbaPerfil() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.gridx = 0;

        JLabel titulo = new JLabel("Meu Perfil", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridy = 0; painel.add(titulo, gbc);

        gbc.gridy = 1; painel.add(new JLabel("Nome: " + motorista.getNome()), gbc);
        gbc.gridy = 2; painel.add(new JLabel("CPF: " + motorista.getCpf()), gbc);
        gbc.gridy = 3; painel.add(new JLabel("Telefone: " + motorista.getTelefone()), gbc);
        gbc.gridy = 4; painel.add(new JLabel("Email: " + motorista.getEmail()), gbc);
        gbc.gridy = 5; painel.add(new JLabel("CNH: " + motorista.getCnh()), gbc);

        JButton btnEditar = new JButton("Editar Perfil");
        gbc.gridy = 6; painel.add(btnEditar, gbc);
        btnEditar.addActionListener(e -> editarPerfil());

        JButton btnSair = new JButton("Sair");
        gbc.gridy = 7; painel.add(btnSair, gbc);
        btnSair.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        return painel;
    }

    private JPanel criarAbaVeiculos() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] colunas = {"Placa", "Modelo", "Capacidade", "Ano"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(modelo);
        atualizarTabelaVeiculos(modelo);

        JPanel botoes = new JPanel(new FlowLayout());
        JButton btnCadastrar = new JButton("Cadastrar Veiculo");
        JButton btnExcluir = new JButton("Excluir Veiculo");
        botoes.add(btnCadastrar);
        botoes.add(btnExcluir);

        btnCadastrar.addActionListener(e -> {
            cadastrarVeiculo();
            atualizarTabelaVeiculos(modelo);
        });

        btnExcluir.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha >= 0) {
                String placa = (String) modelo.getValueAt(linha, 0);
                veiculoRepository.excluirVeiculo(placa);
                veiculos.removeIf(v -> v.getPlaca().equals(placa));
                atualizarTabelaVeiculos(modelo);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um veiculo.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        painel.add(new JScrollPane(tabela), BorderLayout.CENTER);
        painel.add(botoes, BorderLayout.SOUTH);
        return painel;
    }

    private JPanel criarAbaViagens() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] colunas = {"ID", "Origem", "Destino", "Horario", "Valor", "Vagas", "Status"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(modelo);
        atualizarTabelaViagens(modelo);

        JPanel botoes = new JPanel(new FlowLayout());
        JButton btnCriar = new JButton("Criar Viagem");
        JButton btnIniciar = new JButton("Iniciar Viagem");
        JButton btnFinalizar = new JButton("Finalizar Viagem");
        JButton btnCancelar = new JButton("Cancelar Viagem");
        botoes.add(btnCriar);
        botoes.add(btnIniciar);
        botoes.add(btnFinalizar);
        botoes.add(btnCancelar);

        btnCriar.addActionListener(e -> {
            criarViagem();
            atualizarTabelaViagens(modelo);
        });

        btnIniciar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha >= 0) {
                String id = (String) modelo.getValueAt(linha, 0);
                boolean ok = viagemController.iniciarViagem(id, motorista);
                if (ok) {
                    salvarViagens();
                    atualizarTabelaViagens(modelo);
                    JOptionPane.showMessageDialog(this, "Viagem iniciada!");
                } else {
                    JOptionPane.showMessageDialog(this, "Nao foi possivel iniciar.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnFinalizar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha >= 0) {
                String id = (String) modelo.getValueAt(linha, 0);
                boolean ok = viagemController.finalizarViagem(id, motorista);
                if (ok) {
                    salvarViagens();
                    atualizarTabelaViagens(modelo);
                    JOptionPane.showMessageDialog(this, "Viagem finalizada!");
                } else {
                    JOptionPane.showMessageDialog(this, "Nao foi possivel finalizar.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnCancelar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha >= 0) {
                String id = (String) modelo.getValueAt(linha, 0);
                viagemController.cancelarViagem(id);
                salvarViagens();
                atualizarTabelaViagens(modelo);
                JOptionPane.showMessageDialog(this, "Viagem cancelada.");
            }
        });

        painel.add(new JScrollPane(tabela), BorderLayout.CENTER);
        painel.add(botoes, BorderLayout.SOUTH);
        return painel;
    }

    private void atualizarTabelaVeiculos(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        for (Veiculo v : veiculos) {
            modelo.addRow(new Object[]{v.getPlaca(), v.getModelo(), v.getCapacidade(), v.getAno()});
        }
    }

    private void atualizarTabelaViagens(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        for (Viagem v : viagemController.listarViagens()) {
            modelo.addRow(new Object[]{v.getId(), v.getOrigem(), v.getDestino(), v.getHorario(), v.getValor(), v.getVagasDisponiveis(), v.getStatus()});
        }
    }

    private void cadastrarVeiculo() {
        JTextField placa = new JTextField();
        JTextField modelo = new JTextField();
        JTextField capacidade = new JTextField();
        JTextField ano = new JTextField();
        Object[] campos = {"Placa:", placa, "Modelo:", modelo, "Capacidade:", capacidade, "Ano:", ano};
        int resultado = JOptionPane.showConfirmDialog(this, campos, "Cadastrar Veiculo", JOptionPane.OK_CANCEL_OPTION);
        if (resultado == JOptionPane.OK_OPTION) {
            Veiculo v = new Veiculo(placa.getText(), modelo.getText(), Integer.parseInt(capacidade.getText()), Integer.parseInt(ano.getText()));
            veiculos.add(v);
            veiculoRepository.salvarVeiculo(v);
        }
    }

    private void criarViagem() {
        if (veiculos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cadastre um veiculo primeiro.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JTextField origem = new JTextField();
        JTextField destino = new JTextField();
        JTextField horario = new JTextField();
        JTextField valor = new JTextField();
        String[] placas = veiculos.stream().map(Veiculo::getPlaca).toArray(String[]::new);
        JComboBox<String> veiculoCombo = new JComboBox<>(placas);
        Object[] campos = {"Origem:", origem, "Destino:", destino, "Horario (dd/MM/yyyy HH:mm):", horario, "Valor:", valor, "Veiculo:", veiculoCombo};
        int resultado = JOptionPane.showConfirmDialog(this, campos, "Criar Viagem", JOptionPane.OK_CANCEL_OPTION);
        if (resultado == JOptionPane.OK_OPTION) {
            Veiculo veiculo = veiculos.stream().filter(v -> v.getPlaca().equals(veiculoCombo.getSelectedItem())).findFirst().orElse(null);
            String id = UUID.randomUUID().toString();
            Viagem viagem = new Viagem(id, origem.getText(), destino.getText(), horario.getText(), Double.parseDouble(valor.getText()), motorista, veiculo);
            boolean criada = viagemController.criarViagem(viagem);
            if (criada) {
                viagemRepository.salvarViagem(viagem);
                JOptionPane.showMessageDialog(this, "Viagem criada!");
            } else {
                JOptionPane.showMessageDialog(this, "Conflito de horario ou motorista invalido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarPerfil() {
        JTextField nome = new JTextField(motorista.getNome());
        JTextField cpf = new JTextField(motorista.getCpf());
        JTextField telefone = new JTextField(motorista.getTelefone());
        JTextField email = new JTextField(motorista.getEmail());
        JTextField cnh = new JTextField(motorista.getCnh());
        JPasswordField senha = new JPasswordField();
        Object[] campos = {"Nome:", nome, "CPF:", cpf, "Telefone:", telefone, "Email:", email, "Senha:", senha, "CNH:", cnh};
        int resultado = JOptionPane.showConfirmDialog(this, campos, "Editar Perfil", JOptionPane.OK_CANCEL_OPTION);
        if (resultado == JOptionPane.OK_OPTION) {
            usuarioController.editarUsuario(motorista.getId(), nome.getText(), cpf.getText(), telefone.getText(), email.getText(), new String(senha.getPassword()));
            motorista.setCnh(cnh.getText());
            usuarioRepository.salvarMotorista(motorista);
            JOptionPane.showMessageDialog(this, "Perfil atualizado!");
        }
    }

    private void salvarViagens() {
        for (Viagem v : viagemController.listarViagens()) {
            viagemRepository.salvarViagem(v);
        }
    }
}