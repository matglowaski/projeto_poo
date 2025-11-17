public class Main {
    public static void main(String[] args) {
        Locadora locadora = new Locadora("Locadora da Galera");

        Cliente cliente1 = new Cliente("Matheus Glowaski", "12345678900", "(41)99999-9999");
        locadora.cadastrarCliente(cliente1);

        Carro carro1 = new Carro("Preto", "Golf", "ABC1D23", "VW", "Golf", 150.0, 2018, 4, "Gasolina", "Manual");
        Moto moto1 = new Moto("Vermelha", "Ninja", "XYZ9Z88", "Kawasaki", "Ninja 300", 80.0, 2020, 300, "esportiva");

        locadora.adicionarVeiculo(carro1);
        locadora.adicionarVeiculo(moto1);

        locadora.listarVeiculosDisponiveis();
        System.out.println();

        // alugar carro (polimorfismo: calcularValorAluguel chama a implementação correta)
        locadora.alugarVeiculo(cliente1.getCpf(), carro1.getPlaca(), 3);
        System.out.println();

        locadora.listarVeiculosDisponiveis();
        locadora.listarVeiculosAlugados();
        System.out.println();

        // devolver
        locadora.devolverVeiculo(cliente1.getCpf(), carro1.getPlaca());
        System.out.println();

        locadora.listarVeiculosDisponiveis();

        // salvar dados (cria arquivos na pasta do projeto)
        locadora.salvarClientes("clientes.csv");
        locadora.salvarVeiculos("veiculos.csv");
    }
}
