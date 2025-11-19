import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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

    public void alugarVeiculo(String cpf, String placa, int dias) {
        Cliente cliente = buscarClientePorCpf(cpf);
        if (cliente == null) {
            System.out.println("Cliente não encontrado: " + cpf);
            return;
        }

        Veiculo veiculo = null;
        for (Veiculo v : veiculosDisponiveis) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                veiculo = v;
                break;
            }
        }

        if (veiculo == null) {
            System.out.println("Veículo não encontrado ou indisponível: " + placa);
            return;
        }

        veiculo.alugar();
        double valor = veiculo.calcularValorAluguel(dias);

        System.out.println("Cliente: " + cliente.getNome());
        System.out.println("Veículo: " + veiculo.getNomeVeiculo());
        System.out.println(String.format("Valor total: R$ %.2f", valor));

        veiculosDisponiveis.remove(veiculo);
        veiculosAlugados.add(veiculo);
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

    public void devolverVeiculo(String cpf, String placa) {
        Cliente cliente = buscarClientePorCpf(cpf);
        if (cliente == null) {
            System.out.println("Cliente não encontrado: " + cpf);
            return;
        }

        Veiculo veiculo = null;
        for (Veiculo v : veiculosAlugados) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                veiculo = v;
                break;
            }
        }

        if (veiculo == null) {
            System.out.println("Veículo não encontrado entre alugados: " + placa);
            return;
        }

        veiculo.devolver();
        veiculosAlugados.remove(veiculo);
        veiculosDisponiveis.add(veiculo);

        System.out.println("Devolução feita! Valeu, " + cliente.getNome());
    }

    public void listarVeiculosAlugados() {
        for (Veiculo v : veiculosAlugados) {
            System.out.println(v.getNomeVeiculo() + " - " + v.getPlaca());
        }
    }

    private Cliente buscarClientePorCpf(String cpf) {
        for (Cliente c : clientes) {
            if (c.getCpf().equalsIgnoreCase(cpf)) {
                return c;
            }
        }
        return null;
    }

    public void salvarClientes(String arquivo) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(arquivo))) {
            for (Cliente c : clientes) {
                pw.println(c.getCpf() + "," + c.getNome() + "," + c.getTelefone());
            }
            System.out.println("Clientes salvos em: " + arquivo);
        } catch (IOException e) {
            System.out.println("Erro ao salvar clientes: " + e.getMessage());
        }
    }

    public void salvarVeiculos(String arquivo) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(arquivo))) {
            ArrayList<Veiculo> todos = new ArrayList<>();
            todos.addAll(veiculosDisponiveis);
            for (Veiculo v : veiculosAlugados) {
                if (!todos.contains(v)) {
                    todos.add(v);
                }
            }

            for (Veiculo v : todos) {
                pw.println(v.getPlaca() + "," + v.getMarca() + "," + v.getModelo() + "," + v.getAnoVeiculo() + "," + v.getValorDiaria() + "," + (v.isDisponivel() ? "disponivel" : "alugado") + "," + v.getCorVeiculo() + "," + v.getNomeVeiculo());
            }
            System.out.println("Veículos salvos em: " + arquivo);
        } catch (IOException e) {
            System.out.println("Erro ao salvar veículos: " + e.getMessage());
        }
    }
}


