import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Acoes {
    private static Crud crud = new Crud();

    private Acoes() {}

    public static void BuscarPorId() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o ID:");
        int id = Integer.parseInt(sc.nextLine());
        Conta conta = crud.BuscarPorId(id);
        System.out.println(conta.toString());
        sc.close();
    }

    public static void updateModel() throws IOException {
        Conta conta = criaContaModel();
        if (crud.update(conta)) {
            System.out.println("Update realizado com sucesso");
        } else {
            System.out.println("Erro ao atualizar");
        }
    }

    public static void deleteById() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o ID a ser deletado:");
        int id = Integer.parseInt(sc.nextLine());
        if (crud.deletarPorId(id)) {
            System.out.println("Registro deletado com sucesso");
        } else {
            System.out.println("Erro ao deletar");
        }
        sc.close();
    }

    public static Conta criaContaModel() throws IOException {
        Crud crud = new Crud();
        Scanner scanner = new Scanner(System.in);
        Conta conta = new Conta();
        System.out.println("Criacao de conta:");
        System.out.println("Digite seu nome:");
        conta.nomePessoa = scanner.nextLine();

        System.out.println("Digite a quantidade de emails:");
        conta.emailQnt(Integer.parseInt(scanner.nextLine()));

        for (int i = 0; i < conta.email.length; i++) {
            int qnt = i + 1;
            System.out.println("Digite o " + qnt + " Email:");
            conta.email[i] = scanner.nextLine();
        }
        while (true) {
            System.out.println("Digite o nome do Usuario:");
            String nome = scanner.nextLine();
            if (crud.verificaNome(nome)) {
                conta.nomeUsuario = nome;
                break;
            } else {
                System.out.println("O nome não está disponível");
                System.out.println("Digite outro nome de Usuario:");
                nome = scanner.nextLine();
                continue;
            }
        }
        System.out.println("Digite sua senha:");
        conta.senha = scanner.nextLine();
        System.out.println("Digite seu CPF:");
        conta.cpf = scanner.nextLine();
        System.out.println("Digite sua Cidade:");
        conta.cidade = scanner.nextLine();
        System.out.println("Saldo da conta:");
        conta.saldoConta = scanner.nextFloat();
        conta.transferenciasRealizadas = 0;
        scanner.close();
        return conta;
    }

    public void criarContaCrud(Conta conta) throws IOException {
        Crud crud = new Crud();
        if (crud.criarConta(conta)) {
            System.out.println("Conta criada com sucesso");
        } else {
            System.out.println("Erro ao criar conta");
        }

    }

    public static void transferenciaModel() throws FileNotFoundException {
        Crud crud = new Crud();
        Scanner scanner = new Scanner(System.in);
        int idOrigem;
        int idDestino;
        float valor;
        System.out.println("Digite o ID da conta de origem:");
        idOrigem = Integer.parseInt(scanner.nextLine());
        System.out.println("Digite o valor a ser transferido:");
        valor = Float.parseFloat(scanner.nextLine());
        System.out.println("Digite o ID da conta destino:");
        idDestino = Integer.parseInt(scanner.nextLine());
        if (crud.transferencia(idOrigem, idDestino, valor)) {
            System.out.println("Transferencia realizada com sucesso");
        } else {
            System.out.println("Erro na transferencia");
        }
        scanner.close();
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
