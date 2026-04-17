import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

// 1. O MOLDE DO NOSSO PRODUTO (A CLASSE)
class Palete {
    String lote;
    String tipoFrasco;
    int qtdPacotes;
    String endereco;
    String turno;

    public Palete(String lote, String tipoFrasco, String endereco, String turno) {
        this.lote = lote;
        this.tipoFrasco = tipoFrasco;
        this.qtdPacotes = 7; // Regra de negócio fixa
        this.endereco = endereco;
        this.turno = turno;
    }

    @Override
    public String toString() {
        return "[Endereço: " + endereco + "] | Lote: " + lote + " | Tipo: " + tipoFrasco + " | Turno: " + turno;
    }
}

// 2. O CÉREBRO DO SISTEMA
public class Main {
    static List<Palete> estoque = new ArrayList<>();
    static List<String> vagasLivres = new ArrayList<>();
    static Random random = new Random();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        inicializarEstoque();

        boolean rodando = true;
        while (rodando) {
            System.out.println("\n=== WMS AUTOMATION SYSTEM (VAGAS: " + vagasLivres.size() + ") ===");
            System.out.println("1. Gerar Lote Automático (Entrada de Caminhão)");
            System.out.println("2. Consultar Estoque Atual");
            System.out.println("3. Expedir Carga (Saída para Cliente)");
            System.out.println("0. Sair do Sistema");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    receberCarga();
                    break;
                case 2:
                    listarEstoque();
                    break;
                case 3:
                    expedirCarga();
                    break;
                case 0:
                    System.out.println("Encerrando o sistema WMS. Até logo!");
                    rodando = false;
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    // --- FUNÇÕES DE LÓGICA DE NEGÓCIO ---

    // Gera as 200 posições reais desenhadas pelo Arquiteto (01-1A-01 até 05-2B-10)
    public static void inicializarEstoque() {
        String[] andares = {"1A", "1B", "2A", "2B"};
        for (int rua = 1; rua <= 5; rua++) {
            for (String andar : andares) {
                for (int vaga = 1; vaga <= 10; vaga++) {
                    String endereco = String.format("%02d-%s-%02d", rua, andar, vaga);
                    vagasLivres.add(endereco);
                }
            }
        }
    }

    public static void receberCarga() {
        System.out.print("Quantos paletes o caminhão trouxe? ");
        int qtdPaletes = scanner.nextInt();

        if (qtdPaletes > vagasLivres.size()) {
            System.out.println("[ERRO] Não há vagas suficientes! Vagas disponíveis: " + vagasLivres.size());
            return;
        }

        String[] turnos = {"Manhã", "Tarde", "Noite"};
        String turnoAtual = turnos[random.nextInt(turnos.length)];
        String loteAtual = "LT-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd-HHmm"));

        System.out.println("\n[SISTEMA] Processando entrada e alocando endereços...");
        for (int i = 0; i < qtdPaletes; i++) {
            int indexVaga = random.nextInt(vagasLivres.size());
            String vagaSorteada = vagasLivres.remove(indexVaga);
            String tipo = random.nextBoolean() ? "5 Litros" : "2 Litros";

            Palete novoPalete = new Palete(loteAtual, tipo, vagaSorteada, turnoAtual);
            estoque.add(novoPalete);
            System.out.println("✅ " + novoPalete.toString());
        }
    }

    public static void listarEstoque() {
        if (estoque.isEmpty()) {
            System.out.println("\nO estoque está completamente vazio.");
            return;
        }
        System.out.println("\n--- RELATÓRIO DE ESTOQUE ---");
        for (Palete p : estoque) {
            System.out.println(p.toString());
        }
    }

    public static void expedirCarga() {
        System.out.println("\nQual produto deseja expedir?");
        System.out.println("1. Frascos de 5 Litros");
        System.out.println("2. Frascos de 2 Litros");
        System.out.print("Escolha: ");
        int tipoEscolha = scanner.nextInt();
        String tipoDesejado = (tipoEscolha == 1) ? "5 Litros" : "2 Litros";

        System.out.print("Quantos paletes o cliente pediu? ");
        int qtdPedida = scanner.nextInt();

        int qtdRetirada = 0;
        Iterator<Palete> iterator = estoque.iterator();

        System.out.println("\n[SISTEMA] Iniciando separação (Picking)...");
        while (iterator.hasNext() && qtdRetirada < qtdPedida) {
            Palete p = iterator.next();
            if (p.tipoFrasco.equals(tipoDesejado)) {
                System.out.println("🚚 Retirando Palete Lote " + p.lote + " do Endereço " + p.endereco);
                vagasLivres.add(p.endereco); // Libera a vaga
                iterator.remove(); // Tira do estoque
                qtdRetirada++;
            }
        }

        if (qtdRetirada == 0) {
            System.out.println("[ERRO] Nenhum palete de " + tipoDesejado + " encontrado no estoque.");
        } else if (qtdRetirada < qtdPedida) {
            System.out.println("[AVISO] Estoque insuficiente. Foram expedidos apenas " + qtdRetirada + " paletes.");
        } else {
            System.out.println("[SUCESSO] Carga de " + qtdRetirada + " paletes expedida com sucesso!");
        }
    }
}
