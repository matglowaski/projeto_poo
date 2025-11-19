import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class MainApp {
    private JFrame frame;
    private CardLayout cards;
    private JPanel cardPanel;

    private List<Usuario> usuarios = new ArrayList<>();
    private List<Veiculo> veiculos = new ArrayList<>();
    private List<Aluguel> alugueis = new ArrayList<>();

    private Cliente usuarioLogadoCliente = null;
    private Funcionario usuarioLogadoFunc = null;
    private final String RESOURCES = "src/resources/";
    private final String DATA = "src/data/";

    public MainApp() {
        carregarDados();
        initUI();
    }

    private void carregarDados() {
        Object uobj = Persistencia.carregarSerializado(DATA + "usuarios.ser");
        Object vobj = Persistencia.carregarSerializado(DATA + "veiculos.ser");
        Object aobj = Persistencia.carregarSerializado(DATA + "alugueis.ser");

        if (uobj instanceof List) usuarios = (List<Usuario>) uobj;
        else usuarios = Persistencia.lerUsuariosTXT(RESOURCES + "usuarios.txt");

        if (vobj instanceof List) veiculos = (List<Veiculo>) vobj;
        else veiculos = Persistencia.lerVeiculosTXT(RESOURCES + "veiculos.txt");

        if (aobj instanceof List) alugueis = (List<Aluguel>) aobj;
    }

    private void salvarEstado() {
        try {
            Persistencia.salvarSerializado(usuarios, DATA + "usuarios.ser");
            Persistencia.salvarSerializado(veiculos, DATA + "veiculos.ser");
            Persistencia.salvarSerializado(alugueis, DATA + "alugueis.ser");
            System.out.println("Estado salvo.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Erro ao salvar estado: " + e.getMessage());
        }
    }

    private void initUI() {
        frame = new JFrame("Sistema de Aluguel de Veículos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        cards = new CardLayout();
        cardPanel = new JPanel(cards);

        cardPanel.add(createInitialPanel(), "inicio");
        cardPanel.add(createLoginPanel("CLIENTE"), "login_cliente");
        cardPanel.add(createLoginPanel("FUNC"), "login_func");
        cardPanel.add(createClienteMenuPanel(), "menu_cliente");
        cardPanel.add(createFuncionarioMenuPanel(), "menu_func");

        frame.getContentPane().add(cardPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel createInitialPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        JButton b1 = new JButton("Login Cliente");
        JButton b2 = new JButton("Login Funcionário");
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10,10,10,10);
        p.add(b1, c);
        p.add(b2, c);

        b1.addActionListener(e -> cards.show(cardPanel, "login_cliente"));
        b2.addActionListener(e -> cards.show(cardPanel, "login_func"));
        return p;
    }

    private JPanel createLoginPanel(String tipo) {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel l1 = new JLabel("Email:");
        JTextField tfEmail = new JTextField(20);
        JLabel l2 = new JLabel("Senha:");
        JPasswordField pf = new JPasswordField(20);
        JButton btn = new JButton("Entrar");
        JButton back = new JButton("Voltar");

        c.gridx=0; c.gridy=0; p.add(l1,c);
        c.gridx=1; p.add(tfEmail,c);
        c.gridx=0; c.gridy=1; p.add(l2,c);
        c.gridx=1; p.add(pf,c);
        c.gridx=0; c.gridy=2; p.add(back,c);
        c.gridx=1; p.add(btn,c);

        back.addActionListener(e -> cards.show(cardPanel, "inicio"));

        btn.addActionListener(e -> {
            String email = tfEmail.getText().trim();
            String senha = new String(pf.getPassword());
            String id = java.util.UUID.randomUUID().toString();
            String nome = email.isEmpty() ? ("User-" + id.substring(0,5)) : email.split("@")[0];
            if ("CLIENTE".equalsIgnoreCase(tipo)) {
                Cliente clienteTemp = new Cliente(id, nome, email, senha, "", "");
                usuarioLogadoCliente = clienteTemp;
                usuarios.add(clienteTemp);
                salvarEstado();
                cards.show(cardPanel, "menu_cliente");
            } else {
                Funcionario funcionarioTemp = new Funcionario(id, nome, email, senha);
                usuarioLogadoFunc = funcionarioTemp;
                usuarios.add(funcionarioTemp);
                salvarEstado();
                cards.show(cardPanel, "menu_func");
            }
        });

        return p;
    }

    private Usuario autenticar(String email, String senha, String tipoEsperado) {
        for (Usuario u : usuarios) {
            boolean tipoOk = ("CLIENTE".equalsIgnoreCase(tipoEsperado) && u instanceof Cliente)
                    || ("FUNC".equalsIgnoreCase(tipoEsperado) && u instanceof Funcionario);
            if (!tipoOk) continue;
            if (u.getEmail().equalsIgnoreCase(email) && u.getSenha().equals(senha)) return u;
        }
        return null;
    }

    private JPanel createClienteMenuPanel() {
        JPanel p = new JPanel(new BorderLayout());
        JPanel top = new JPanel();
        JButton listar = new JButton("Listar Veículos");
        JButton meus = new JButton("Meus Aluguéis");
        JButton logout = new JButton("Logout");
        top.add(listar); top.add(meus); top.add(logout);
        p.add(top, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout());
        p.add(center, BorderLayout.CENTER);

        listar.addActionListener(e -> mostrarTabelaVeiculos(center, true));
        meus.addActionListener(e -> mostrarMeusAlugueis(center));
        logout.addActionListener(e -> { usuarioLogadoCliente = null; salvarEstado(); cards.show(cardPanel, "inicio"); });

        return p;
    }

    private JPanel createFuncionarioMenuPanel() {
        JPanel p = new JPanel(new BorderLayout());
        JPanel top = new JPanel();
        JButton cadastrar = new JButton("Cadastrar Veículo");
        JButton listar = new JButton("Listar Veículos");
        JButton logout = new JButton("Logout");
        top.add(cadastrar); top.add(listar); top.add(logout);
        p.add(top, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout());
        p.add(center, BorderLayout.CENTER);

        cadastrar.addActionListener(e -> mostrarFormularioVeiculo(center));
        listar.addActionListener(e -> mostrarTabelaVeiculos(center, false));
        logout.addActionListener(e -> { usuarioLogadoFunc = null; salvarEstado(); cards.show(cardPanel, "inicio"); });

        return p;
    }

    private void mostrarTabelaVeiculos(JPanel container, boolean clienteFlow) {
        container.removeAll();
        String[] cols = {"Placa","Marca","Modelo","Ano","Diária","Disponível"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r,int c){return false;}
        };
        for (Veiculo v : veiculos) {
            model.addRow(new Object[]{v.getPlaca(), v.getMarca(), v.getModelo(), v.getAnoVeiculo(), v.getValorDiaria(), v.isDisponivel()});
        }
        JTable table = new JTable(model);
        container.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel actions = new JPanel();
        if (clienteFlow) {
            JButton alugar = new JButton("Alugar Veículo");
            actions.add(alugar);
            alugar.addActionListener(e -> {
                int sel = table.getSelectedRow();
                if (sel < 0) { JOptionPane.showMessageDialog(frame, "Selecione um veículo."); return; }
                String placa = (String) model.getValueAt(sel, 0);
                alugarVeiculoFlow(placa);
                mostrarTabelaVeiculos(container, true);
            });
        } else {
            JButton remover = new JButton("Remover Veículo");
            actions.add(remover);
            remover.addActionListener(e -> {
                int sel = table.getSelectedRow();
                if (sel < 0) { JOptionPane.showMessageDialog(frame, "Selecione um veículo."); return; }
                String placa = (String) model.getValueAt(sel, 0);
                Veiculo alvo = buscarVeiculoPorPlaca(placa);
                if (alvo != null && !alvo.isDisponivel()) {
                    JOptionPane.showMessageDialog(frame, "Não é possível remover veículo alugado.");
                    return;
                }
                veiculos.remove(alvo);
                salvarEstado();
                mostrarTabelaVeiculos(container, false);
            });
        }

        container.add(actions, BorderLayout.SOUTH);
        container.revalidate(); container.repaint();
    }

    private void mostrarFormularioVeiculo(JPanel container) {
        container.removeAll();
        JPanel form = new JPanel(new GridLayout(0,2,5,5));
        JTextField placa = new JTextField();
        JTextField marca = new JTextField();
        JTextField modelo = new JTextField();
        JTextField ano = new JTextField();
        JTextField diaria = new JTextField();
        JTextField nome = new JTextField();
        JTextField cor = new JTextField();

        form.add(new JLabel("Placa:")); form.add(placa);
        form.add(new JLabel("Marca:")); form.add(marca);
        form.add(new JLabel("Modelo:")); form.add(modelo);
        form.add(new JLabel("Ano:")); form.add(ano);
        form.add(new JLabel("Diária:")); form.add(diaria);
        form.add(new JLabel("Nome Exibido:")); form.add(nome);
        form.add(new JLabel("Cor:")); form.add(cor);

        JButton salvar = new JButton("Salvar");
        form.add(new JLabel()); form.add(salvar);

        salvar.addActionListener(e -> {
            try {
                String p = placa.getText().trim();
                String m = marca.getText().trim();
                String mo = modelo.getText().trim();
                int a = Integer.parseInt(ano.getText().trim());
                double d = Double.parseDouble(diaria.getText().trim());
                String n = nome.getText().trim();
                String c = cor.getText().trim();
                Carro v = new Carro(c, n, p, m, mo, d, a, 4, "Gasolina", "Manual");
                veiculos.add(v);
                salvarEstado();
                JOptionPane.showMessageDialog(frame, "Veículo cadastrado.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Ano ou diária inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        container.add(form, BorderLayout.NORTH);
        container.revalidate(); container.repaint();
    }

    private Veiculo buscarVeiculoPorPlaca(String placa) {
        for (Veiculo v : veiculos) if (v.getPlaca().equalsIgnoreCase(placa)) return v;
        return null;
    }

    private void alugarVeiculoFlow(String placa) {
        Veiculo v = buscarVeiculoPorPlaca(placa);
        if (v == null) { JOptionPane.showMessageDialog(frame, "Veículo não encontrado."); return; }
        if (!v.isDisponivel()) { JOptionPane.showMessageDialog(frame, "Veículo indisponível."); return; }

        String ds = JOptionPane.showInputDialog(frame, "Data início (YYYY-MM-DD):");
        String de = JOptionPane.showInputDialog(frame, "Data fim (YYYY-MM-DD):");
        try {
            LocalDate si = LocalDate.parse(ds);
            LocalDate sf = LocalDate.parse(de);
            if (sf.isBefore(si)) throw new DateTimeParseException("datas", de, 0);

            Aluguel a = new Aluguel(java.util.UUID.randomUUID().toString(), usuarioLogadoCliente, v, si, sf);
            if (a.getValorTotal() <= 0) { JOptionPane.showMessageDialog(frame, "Período inválido."); return; }
            v.setDisponivel(false);
            usuarioLogadoCliente.adicionarAluguel(a);
            alugueis.add(a);
            salvarEstado();
            JOptionPane.showMessageDialog(frame, String.format("Aluguel registrado. Valor: R$ %.2f", a.getValorTotal()));
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(frame, "Formato de data inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarMeusAlugueis(JPanel container) {
        container.removeAll();
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID","Veículo","Início","Fim","Valor","Status"},0){@Override public boolean isCellEditable(int r,int c){return false;}};
        for (Aluguel a : usuarioLogadoCliente.getAlugueis()) {
            model.addRow(new Object[]{a.getId(), a.getVeiculo().getNomeVeiculo(), a.getDataInicio(), a.getDataFim(), a.getValorTotal(), a.getStatus()});
        }
        JTable table = new JTable(model);
        container.add(new JScrollPane(table), BorderLayout.CENTER);
        container.revalidate(); container.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainApp());
    }
}
