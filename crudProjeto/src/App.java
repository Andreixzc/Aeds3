import java.io.FileNotFoundException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int option = -1;
        do {
            menu();
            System.out.println("Digite a opcao:");
            option = Integer.parseInt(sc.nextLine());
            if (option != 0) {
                if (option == 1) {
                    criaContaModel();
                }
            }
        } while (option != 0);


    }

    public static void criaContaModel() throws FileNotFoundException {
        Crud crud = new Crud();
        Scanner scanner = new Scanner(System.in);
        System.out.println("caralho");
        Conta conta = new Conta();
        System.out.println("Criacao de conta:");
        System.out.println("Digite seu nome:");
        conta.nomePessoa = scanner.nextLine();

        System.out.println("Digite a quantidade de emails:");
        conta.emailQnt(Integer.parseInt(scanner.nextLine()));

        for (int i = 0; i < conta.email.length; i++) {
            int qnt = i +1;
            System.out.println("Digite o " + qnt + " Email:");
            conta.email[i] = scanner.nextLine();
        }
        System.out.println("Digite o nome do Usuario:");
        conta.nomeUsuario = scanner.nextLine();
        //Verifica nome(scanner.nextline())
        System.out.println("Digite sua senha:");
        conta.senha = scanner.nextLine();
        System.out.println("Digite seu CPF:");
        conta.cpf = scanner.nextLine();
        System.out.println("Digite sua Cidade:");
        conta.cidade = scanner.nextLine();
        System.out.println("Saldo da conta:");
        conta.saldoConta = scanner.nextFloat();
        conta.transferenciasRealizadas = 0;
        System.out.println(conta.toString());

    }
    public static void menu() {
        System.out.println("Menu:");
        System.out.println("1 - criar conta:");
        System.out.println("2 - Realizar transferencia:");
        System.out.println("3 - Ler registro por ID:");
        System.out.println("4 - Editar registro:");
        System.out.println("5 - Deletar registro por ID:");
        System.out.println("0 - Finalizar");
    }

}
