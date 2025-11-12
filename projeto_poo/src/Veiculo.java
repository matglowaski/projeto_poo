abstract class Veiculo implements Alugavel{
    private String corVeiculo;
    private String nomeVeiculo;
    private String placa;
    private String marca;
    private String modelo;
    private double valorDiaria;
    private int anoVeiculo;
    private boolean disponivel;

    public Veiculo(String corVeiculo, String nomeVeiculo, String placa, String marca, String modelo, double valorDiaria, int anoVeiculo) {
        this.corVeiculo = corVeiculo;
        this.nomeVeiculo = nomeVeiculo;
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.valorDiaria = valorDiaria;
        this.anoVeiculo = anoVeiculo;
        this.disponivel = true;
    }

    public String getCorVeiculo() { return corVeiculo; }
    public void setCorVeiculo(String corVeiculo) { this.corVeiculo = corVeiculo; }

    public String getNomeVeiculo() { return nomeVeiculo; }
    public void setNomeVeiculo(String nomeVeiculo) { this.nomeVeiculo = nomeVeiculo; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public double getValorDiaria() { return valorDiaria; }
    public void setValorDiaria(double valorDiaria) { this.valorDiaria = valorDiaria; }

    public int getAnoVeiculo() { return anoVeiculo; }
    public void setAnoVeiculo(int anoVeiculo) { this.anoVeiculo = anoVeiculo; }

    public boolean isDisponivel() { return disponivel; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }

    // metodos da interface
    @Override
    public void alugar() {}

    @Override
    public void devolver() {}

    // Método abstrato obrigatório
    @Override
    public abstract double calcularValorAluguel(int dias);
}
