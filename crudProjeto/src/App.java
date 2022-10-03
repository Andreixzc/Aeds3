import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int option = -1;
        do {
            Acoes.menu();
            System.out.println("Digite a opcao:");
            option = Integer.parseInt(sc.next());
            if (option != 0) {
                if (option == 1) {
                    Acoes.criarContaModel();
                } else if (option == 2) {
                    Acoes.transferenciaModel();
                } else if (option == 3) {
                    Acoes.BuscarPorIdModel();
                } else if (option == 4) {
                    Acoes.updateModel();
                } else if (option == 5) {
                    Acoes.deleteByIdModel();
                }
            }
        } while (option != 0);

    }


}
