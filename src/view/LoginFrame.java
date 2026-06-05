package view;

import controller.UsuarioController;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import model.Motorista;
import model.Passageiro;
import model.Usuario;
import repository.UsuarioRepository;
import repository.VeiculoRepository;
import repository.ViagemRepository;

public class LoginFrame extends JFrame {

    private UsuarioController usuarioController;
    private UsuarioRepository usuarioRepository;
    private VeiculoRepository veiculoRepository;
    private ViagemRepository viagemRepository;
    private JTextField emailField;
    private JPasswordField senhaField;

    public LoginFrame() {
        this.usuarioRepository = new UsuarioRepository();
        this.veiculoRepository = new VeiculoRepository();
        this.viagemRepository = new ViagemRepository();
        this.usuarioController = new UsuarioController();
        carregarDados();
        configurarJanela();
    }

    private void carregarDados() {
        for (Motorista m : usuarioRepository.carregarMotoristas()) {
            usuarioController.cadastrarUsuario(m);
        }
        for (Passageiro p : usuarioRepository.carregarPassageiros()) {
            usuarioController.cadastrarUsuario(p);
        }
    }

    private void configurarJanela() {
        setTitle("Sistema de Transporte Alternativo");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painel = new JPanel();
        painel.setLayout(new GridBagLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 0);

        JLabel titulo = new JLabel("Transporte Alternativo", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        painel.add(titulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        painel.add(new JLabel("Email:"), gbc);

        emailField = new JTextField();
        gbc.gridx = 0; gbc.gridy = 2;
        painel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        painel.add(new JLabel("Senha:"), gbc);

        senhaField = new JPasswordField();
        gbc.gridx = 0; gbc.gridy = 4;
        painel.add(senhaField, gbc);

        JButton btnLogin = new JButton("Entrar");
        gbc.gridx = 0; gbc.gridy = 5;
        painel.add(btnLogin, gbc);

        JButton btnCadastro = new JButton("Cadastrar");
        btnCadastro.setForeground(new Color(100, 180, 255));
        gbc.gridx = 0; gbc.gridy = 6;
        painel.add(btnCadastro, gbc);

        btnLogin.addActionListener(e -> realizarLogin());
        btnCadastro.addActionListener(e -> abrirCadastro());

        add(painel);
    }

    private void realizarLogin() {
        String email = emailField.getText();
        String senha = new String(senhaField.getPassword());
        Usuario usuario = usuarioController.autenticar(email, senha);
        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "Email ou senha incorretos.", "Erro", JOptionPane.ERROR_MESSAGE);
        } else if (usuario instanceof Motorista) {
            new MotoristaFrame((Motorista) usuario, usuarioController, usuarioRepository, veiculoRepository, viagemRepository).setVisible(true);
            dispose();
        } else if (usuario instanceof Passageiro) {
            new PassageiroFrame((Passageiro) usuario, usuarioController, usuarioRepository, veiculoRepository, viagemRepository).setVisible(true);
            dispose();
        }
    }

    private void abrirCadastro() {
        new CadastroFrame(usuarioController, usuarioRepository).setVisible(true);
    }
}