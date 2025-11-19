import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Persistencia {

    public static List<Usuario> lerUsuariosTXT(String caminho) {
        List<Usuario> lista = new ArrayList<>();
        File f = new File(caminho);
        if (!f.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linha = linha.trim();
                if (linha.isEmpty() || linha.startsWith("#")) continue;
                String[] parts = linha.split(",");
                if (parts.length < 5) continue;
                String tipo = parts[0].trim();
                String id = parts[1].trim();
                String nome = parts[2].trim();
                String email = parts[3].trim();
                String senha = parts[4].trim();
                if ("CLIENTE".equalsIgnoreCase(tipo)) {
                    String cpf = parts.length > 5 ? parts[5].trim() : "";
                    String tel = parts.length > 6 ? parts[6].trim() : "";
                    lista.add(new Cliente(id, nome, email, senha, cpf, tel));
                } else {
                    lista.add(new Funcionario(id, nome, email, senha));
                }
            }
        } catch (IOException e) {
            System.out.println("Erro lendo usuarios: " + e.getMessage());
        }
        return lista;
    }

    public static List<Veiculo> lerVeiculosTXT(String caminho) {
        List<Veiculo> lista = new ArrayList<>();
        File f = new File(caminho);
        if (!f.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linha = linha.trim();
                if (linha.isEmpty() || linha.startsWith("#")) continue;
                String[] p = linha.split(",");
                if (p.length < 6) continue;
                String placa = p[0].trim();
                String marca = p[1].trim();
                String modelo = p[2].trim();
                int ano = Integer.parseInt(p[3].trim());
                double valor = Double.parseDouble(p[4].trim());
                String nome = p[5].trim();
                String cor = p.length > 6 ? p[6].trim() : "--";
                Carro c = new Carro(cor, nome, placa, marca, modelo, valor, ano, 4, "Gasolina", "Manual");
                lista.add(c);
            }
        } catch (IOException e) {
            System.out.println("Erro lendo veiculos: " + e.getMessage());
        }
        return lista;
    }

    public static void salvarSerializado(Object obj, String caminho) throws IOException {
        File f = new File(caminho);
        f.getParentFile().mkdirs();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f))) {
            oos.writeObject(obj);
        }
    }

    public static Object carregarSerializado(String caminho) {
        File f = new File(caminho);
        if (!f.exists()) return null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar serializado: " + e.getMessage());
            return null;
        }
    }
}
