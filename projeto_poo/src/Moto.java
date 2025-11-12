public class Moto extends Veiculo {
    private int cilindradas;
    private String tipo; //esportiva, naked

    public Moto(String corVeiculo, String nomeVeiculo, String placa, String marca, String modelo,
                double valorDiaria, int anoVeiculo, int cilindradas, String tipo) {
        super(corVeiculo, nomeVeiculo, placa, marca, modelo, valorDiaria, anoVeiculo);
        this.cilindradas = cilindradas;
        this.tipo = tipo;
    }

    public int getCilindradas() { return cilindradas; }
    public void setCilindradas(int cilindradas) { this.cilindradas = cilindradas; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    @Override
    public double calcularValorAluguel(int dias) {
        return 0; // implementar depois
    }
}

