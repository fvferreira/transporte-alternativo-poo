package view;

import controller.UsuarioController;
import controller.ViagemController;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import model.Passageiro;
import model.Viagem;
import repository.UsuarioRepository;
import repository.VeiculoRepository;
import repository.ViagemRepository;

public class PassageiroFrame extends JFrame {

    private Passageiro passageiro;
    private UsuarioController usuarioController;
    private ViagemController viagemController;
    private UsuarioRepository usuarioRepository;
    private VeiculoRepository veiculoRepository;
    private ViagemRepository viagemRepository;

    public PassageiroFrame(Passageiro passageiro, UsuarioController usuarioController, UsuarioRepository usuarioRepository, VeiculoRepository veiculoRepository, ViagemRepository viagemRepository) {
        this.passageiro = passageiro;
        this.usuarioController = usuarioController;
        this.usuarioRepository = usuarioRepository;
        this.veiculoRepository = veiculoRepository;
        this.viagemRepository = viagemRepository;
        this.viagemController = new ViagemController();
        carregarViagens();
        configurarJanela();
    }

    private void carregarViagens() {
        for (Viagem v : viagemRepository.carregarViagens(usuarioRepository, veiculoRepository)) {
            viagemController.criarViagem(v);
        }
    }

    private void configurarJanela() {
        setTitle("Menu Passageiro - " + passageiro.getNome());
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Perfil", criarAbaPerfil());
        abas.addTab("Viagens", criarAbaViagens());
        abas.addTab("Carteira", criarAbaCarteira());

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

        gbc.gridy = 1; painel.add(new JLabel("Nome: " + passageiro.getNome()), gbc);
        gbc.gridy = 2; painel.add(new JLabel("CPF: " + passageiro.getCpf()), gbc);
        gbc.gridy = 3; painel.add(new JLabel("Telefone: " + passageiro.getTelefone()), gbc);
        gbc.gridy = 4; painel.add(new JLabel("Email: " + passageiro.getEmail()), gbc);

        JButton btnEditar = new JButton("Editar Perfil");
        gbc.gridy = 5; painel.add(btnEditar, gbc);
        btnEditar.addActionListener(e -> editarPerfil());

        JButton btnSair = new JButton("Sair");
        gbc.gridy = 6; painel.add(btnSair, gbc);
        btnSair.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

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
        JButton btnReservar = new JButton("Reservar Vaga");
        botoes.add(btnReservar);

        btnReservar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha >= 0) {
                String id = (String) modelo.getValueAt(linha, 0);
                reservarVaga(id);
                atualizarTabelaViagens(modelo);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma viagem.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        painel.add(new JScrollPane(tabela), BorderLayout.CENTER);
        painel.add(botoes, BorderLayout.SOUTH);
        return painel;
    }

    private JPanel criarAbaCarteira() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.gridx = 0;

        JLabel titulo = new JLabel("Minha Carteira", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridy = 0; painel.add(titulo, gbc);

        JLabel saldoLabel = new JLabel("Saldo: R$ " + passageiro.getSaldo(), SwingConstants.CENTER);
        saldoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridy = 1; painel.add(saldoLabel, gbc);

        JTextField valorField = new JTextField();
        gbc.gridy = 2; painel.add(new JLabel("Valor a adicionar:"), gbc);
        gbc.gridy = 3; painel.add(valorField, gbc);

        JButton btnAdicionar = new JButton("Adicionar Saldo");
        gbc.gridy = 4; painel.add(btnAdicionar, gbc);

        btnAdicionar.addActionListener(e -> {
            try {
                double valor = Double.parseDouble(valorField.getText());
                passageiro.adicionarSaldo(valor);
                usuarioRepository.salvarPassageiro(passageiro);
                saldoLabel.setText("Saldo: R$ " + passageiro.getSaldo());
                JOptionPane.showMessageDialog(this, "Saldo adicionado!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Valor invalido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        return painel;
    }

    private void atualizarTabelaViagens(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        for (Viagem v : viagemController.listarViagens()) {
            modelo.addRow(new Object[]{v.getId(), v.getOrigem(), v.getDestino(), v.getHorario(), v.getValor(), v.getVagasDisponiveis(), v.getStatus()});
        }
    }

    private void reservarVaga(String viagemId) {
        JTextField pesoBagageiro = new JTextField();
        JTextField pesoMao = new JTextField();
        JTextField pesoAnimal = new JTextField("0");
        JCheckBox caoGuia = new JCheckBox("Cao guia ou apoio emocional");
        Object[] campos = {"Peso bagageiro (kg):", pesoBagageiro, "Peso bagagem de mao (kg):", pesoMao, "Peso do animal (0 se nao tiver):", pesoAnimal, caoGuia};
        int resultado = JOptionPane.showConfirmDialog(this, campos, "Reservar Vaga", JOptionPane.OK_CANCEL_OPTION);
        if (resultado == JOptionPane.OK_OPTION) {
            boolean ok = viagemController.reservarVaga(viagemId, passageiro, Double.parseDouble(pesoBagageiro.getText()), Double.parseDouble(pesoMao.getText()), Double.parseDouble(pesoAnimal.getText()), caoGuia.isSelected());
            if (ok) {
                salvarViagens();
                JOptionPane.showMessageDialog(this, "Reserva realizada!");
            } else {
                JOptionPane.showMessageDialog(this, "Nao foi possivel reservar. Verifique saldo, vagas ou conflito de horario.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarPerfil() {
        JTextField nome = new JTextField(passageiro.getNome());
        JTextField cpf = new JTextField(passageiro.getCpf());
        JTextField telefone = new JTextField(passageiro.getTelefone());
        JTextField email = new JTextField(passageiro.getEmail());
        JPasswordField senha = new JPasswordField();
        Object[] campos = {"Nome:", nome, "CPF:", cpf, "Telefone:", telefone, "Email:", email, "Senha:", senha};
        int resultado = JOptionPane.showConfirmDialog(this, campos, "Editar Perfil", JOptionPane.OK_CANCEL_OPTION);
        if (resultado == JOptionPane.OK_OPTION) {
            usuarioController.editarUsuario(passageiro.getId(), nome.getText(), cpf.getText(), telefone.getText(), email.getText(), new String(senha.getPassword()));
            usuarioRepository.salvarPassageiro(passageiro);
            JOptionPane.showMessageDialog(this, "Perfil atualizado!");
        }
    }

    private void salvarViagens() {
        for (Viagem v : viagemController.listarViagens()) {
            viagemRepository.salvarViagem(v);
        }
    }
}