public class Moto extends Veiculo {
    private int cilindradas;
    private String tipo; 

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
    public void devolver() {
        super.devolver();
        if (isDisponivel()) {
            System.out.println("Faça cheque rápido de óleo e pneus na devolução da moto.");
        }
    }

    @Override
    public double calcularValorAluguel(int dias) {
        return getValorDiaria() * dias;
    }
}






