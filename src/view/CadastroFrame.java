package view;

import controller.UsuarioController;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.UUID;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import model.Motorista;
import model.Passageiro;
import repository.UsuarioRepository;

public class CadastroFrame extends JFrame {

    private UsuarioController usuarioController;
    private UsuarioRepository usuarioRepository;
    private JTextField nomeField, cpfField, telefoneField, emailField, cnhField;
    private JPasswordField senhaField;
    private JComboBox<String> tipoCombo;
    private JLabel cnhLabel;

    public CadastroFrame(UsuarioController usuarioController, UsuarioRepository usuarioRepository) {
        this.usuarioController = usuarioController;
        this.usuarioRepository = usuarioRepository;
        configurarJanela();
    }

    private void configurarJanela() {
        setTitle("Cadastro");
        setSize(400, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painel = new JPanel();
        painel.setLayout(new GridBagLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.gridx = 0;

        JLabel titulo = new JLabel("Novo Cadastro", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridy = 0; painel.add(titulo, gbc);

        gbc.gridy = 1; painel.add(new JLabel("Tipo:"), gbc);
        tipoCombo = new JComboBox<>(new String[]{"Passageiro", "Motorista"});
        gbc.gridy = 2; painel.add(tipoCombo, gbc);

        gbc.gridy = 3; painel.add(new JLabel("Nome:"), gbc);
        nomeField = new JTextField(); gbc.gridy = 4; painel.add(nomeField, gbc);

        gbc.gridy = 5; painel.add(new JLabel("CPF:"), gbc);
        cpfField = new JTextField(); gbc.gridy = 6; painel.add(cpfField, gbc);

        gbc.gridy = 7; painel.add(new JLabel("Telefone:"), gbc);
        telefoneField = new JTextField(); gbc.gridy = 8; painel.add(telefoneField, gbc);

        gbc.gridy = 9; painel.add(new JLabel("Email:"), gbc);
        emailField = new JTextField(); gbc.gridy = 10; painel.add(emailField, gbc);

        gbc.gridy = 11; painel.add(new JLabel("Senha:"), gbc);
        senhaField = new JPasswordField(); gbc.gridy = 12; painel.add(senhaField, gbc);

        cnhLabel = new JLabel("CNH:");
        cnhField = new JTextField();
        cnhLabel.setVisible(false);
        cnhField.setVisible(false);
        gbc.gridy = 13; painel.add(cnhLabel, gbc);
        gbc.gridy = 14; painel.add(cnhField, gbc);

        tipoCombo.addActionListener(e -> {
            boolean isMotorista = tipoCombo.getSelectedItem().equals("Motorista");
            cnhLabel.setVisible(isMotorista);
            cnhField.setVisible(isMotorista);
        });

        JButton btnCadastrar = new JButton("Cadastrar");
        gbc.gridy = 15; painel.add(btnCadastrar, gbc);
        btnCadastrar.addActionListener(e -> realizarCadastro());

        add(painel);
    }

    private void realizarCadastro() {
        String nome = nomeField.getText();
        String cpf = cpfField.getText();
        String telefone = telefoneField.getText();
        String email = emailField.getText();
        String senha = new String(senhaField.getPassword());

        if (!usuarioController.validarCadastro(cpf, email)) {
            JOptionPane.showMessageDialog(this, "CPF ou email ja cadastrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String id = UUID.randomUUID().toString();
        if (tipoCombo.getSelectedItem().equals("Motorista")) {
            String cnh = cnhField.getText();
            Motorista motorista = new Motorista(id, nome, cpf, telefone, email, senha, cnh);
            usuarioController.cadastrarUsuario(motorista);
            usuarioRepository.salvarMotorista(motorista);
        } else {
            Passageiro passageiro = new Passageiro(id, nome, cpf, telefone, email, senha, 0);
            usuarioController.cadastrarUsuario(passageiro);
            usuarioRepository.salvarPassageiro(passageiro);
        }

        JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}