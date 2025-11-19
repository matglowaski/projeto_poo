import java.util.ArrayList;
import java.io.Serializable;

public class Cliente extends Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private String cpf;
    private String telefone;
    private ArrayList<Aluguel> alugueis;

    public Cliente(String id, String nome, String email, String senha, String cpf, String telefone) {
        super(id, nome, email, senha);
        this.cpf = cpf;
        this.telefone = telefone;
        this.alugueis = new ArrayList<>();
    }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public ArrayList<Aluguel> getAlugueis() { return alugueis; }

    public void adicionarAluguel(Aluguel a) { alugueis.add(a); }

    @Override
    public String toString() {
        return getNome() + " (CPF: " + cpf + ")";
    }
}

