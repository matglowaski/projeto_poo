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

    public void cadastrarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public void adicionarVeiculo(Veiculo veiculo) {
        veiculosDisponiveis.add(veiculo);
    }

    public void listarVeiculosDisponiveis() {
        for (Veiculo v : veiculosDisponiveis) {
            System.out.println(v.getNomeVeiculo() + " - " + v.getPlaca());
        }
    }

    public void alugarVeiculo(Cliente cliente, Veiculo veiculo, int dias) {
        if (!veiculosDisponiveis.contains(veiculo)) {
            System.out.println("Esse veículo não tá disponível.");
            return;
        }

        veiculo.alugar();
        double valor = veiculo.calcularValorAluguel(dias);

        System.out.println("Cliente: " + cliente.getNome());
        System.out.println("Veículo: " + veiculo.getNomeVeiculo());
        System.out.println("Valor total: R$ " + valor);

        veiculosDisponiveis.remove(veiculo);
        veiculosAlugados.add(veiculo);
    }

    public void devolverVeiculo(Cliente cliente, Veiculo veiculo) {
        if (!veiculosAlugados.contains(veiculo)) {
            System.out.println("Esse veículo nem tá alugado, fera.");
            return;
        }

        veiculo.devolver();
        veiculosAlugados.remove(veiculo);
        veiculosDisponiveis.add(veiculo);

        System.out.println("Devolução feita! Valeu, " + cliente.getNome());
    }
}


