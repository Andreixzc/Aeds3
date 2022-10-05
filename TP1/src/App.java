import java.util.Scanner;

public class App {
    public static String nomeArquivo = "conta.db";

    public static void main(String[] args) throws Exception {
            Scanner scanner = new Scanner(System.in);
            int opcao = -1;
            while (opcao!=0) {
                menu();
                opcao = Integer.parseInt(scanner.nextLine());  
            }

    }

    public static void menu() {
        System.out.println("\nMENU:");
        System.out.println("1- Criar conta");
        System.out.println("2- Realizar uma transferencia");
        System.out.println("3- Ler um registro por ID");
        System.out.println("4- Atualizar um registro");
        System.out.println("5- Deletar um registro");
    }

}
