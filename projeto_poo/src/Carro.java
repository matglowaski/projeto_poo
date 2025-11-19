public class Carro extends Veiculo {
    private int portas;
    private String tipoCombustivel;
    private String cambio; 

    public Carro(String corVeiculo, String nomeVeiculo, String placa, String marca, String modelo,
                 double valorDiaria, int anoVeiculo, int portas, String tipoCombustivel, String cambio) {
        super(corVeiculo, nomeVeiculo, placa, marca, modelo, valorDiaria, anoVeiculo);
        this.portas = portas;
        this.tipoCombustivel = tipoCombustivel;
        this.cambio = cambio;
    }

    public int getPortas() { return portas; }
    public void setPortas(int portas) { this.portas = portas; }

    public String getTipoCombustivel() { return tipoCombustivel; }
    public void setTipoCombustivel(String tipoCombustivel) { this.tipoCombustivel = tipoCombustivel; }

    public String getCambio() { return cambio; }
    public void setCambio(String cambio) { this.cambio = cambio; }

    @Override
    public void alugar() {
        super.alugar();
        if (!isDisponivel()) { 
            System.out.println("Verifique seguro e documentos do carro antes da entrega.");
        }
    }

    @Override
    public double calcularValorAluguel(int dias) {
        return getValorDiaria() * dias;
    }
}



