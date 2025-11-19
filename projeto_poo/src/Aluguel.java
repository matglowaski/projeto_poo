import java.io.Serializable;
import java.time.LocalDate;

public class Aluguel implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum Status {ATIVO, FINALIZADO, CANCELADO}

    private String id;
    private Cliente cliente;
    private Veiculo veiculo;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private double valorTotal;
    private Status status;

    public Aluguel(String id, Cliente cliente, Veiculo veiculo, LocalDate dataInicio, LocalDate dataFim) {
        this.id = id;
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.status = Status.ATIVO;
        this.valorTotal = calcularValor();
    }

    public String getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public Veiculo getVeiculo() { return veiculo; }
    public LocalDate getDataInicio() { return dataInicio; }
    public LocalDate getDataFim() { return dataFim; }
    public double getValorTotal() { return valorTotal; }
    public Status getStatus() { return status; }

    public double calcularValor() {
        if (dataInicio == null || dataFim == null) return 0.0;
        long dias = java.time.temporal.ChronoUnit.DAYS.between(dataInicio, dataFim) + 1;
        if (dias <= 0) return 0.0;
        return veiculo.getValorDiaria() * dias;
    }

    public void finalizar() {
        this.status = Status.FINALIZADO;
        veiculo.setDisponivel(true);
    }

    public void cancelar() {
        this.status = Status.CANCELADO;
        veiculo.setDisponivel(true);
    }
}
