import java.util.ArrayList;

public class Locadora {
    private String nome;
    private ArrayList<Veiculo> veiculosDisponiveis;
    private ArrayList<Veiculo> veiculosAlugados;
    private ArrayList<Cliente> clientes;

    public Locadora(String nome) {
        this.nome = nome;
        this.veiculosDisponiveis = new ArrayList<>();
        this.veiculosAlugados = new ArrayList<>();
        this.clientes = new ArrayList<>();
    }

    // metodos (vazios por enquanto)
    public void cadastrarCliente(Cliente cliente) {}
    public void adicionarVeiculo(Veiculo veiculo) {}
    public void listarVeiculosDisponiveis() {}
    public void alugarVeiculo(Cliente cliente, Veiculo veiculo, int dias) {}
    public void devolverVeiculo(Cliente cliente, Veiculo veiculo) {}
}
