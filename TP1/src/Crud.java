import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class Crud {
    final static String nomeArquivo = "conta.db";
    
    public Crud() {}

    public static void writeAccount(Conta conta) {
        try {
            RandomAccessFile arquivo = new RandomAccessFile(nomeArquivo, "rw");
            byte[] array;
            array = conta.converteContaEmByte();
            arquivo.seek(0);
            arquivo.writeInt(conta.idConta);
            arquivo.seek(arquivo.length());
            arquivo.writeChar(' ');
            arquivo.writeInt(array.length);
            arquivo.write(array);
            arquivo.close();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Erro ao criar conta!");
        }
    }

    public static int getLastId() {
        int id;
        File f = new File(nomeArquivo);
        if (f.exists() && !f.isDirectory()) {
            try {
                RandomAccessFile arquivo = new RandomAccessFile(nomeArquivo, "rw");
                arquivo.seek(0);
                id = arquivo.readInt();
                arquivo.close();
                return ++id;
            } catch (Exception e) {
                System.out.println("Erro ao obter ultimo ID.");
            }
        }
        return 0;
    }

    public static ArrayList<Conta> listAccouts() {
        ArrayList<Conta> contas = new ArrayList<>();
        byte[] array;
        char lapide;
        try {
            RandomAccessFile arquivo = new RandomAccessFile(nomeArquivo, "rw");
            arquivo.seek(4);
            while (arquivo.getFilePointer() != -1) {
                lapide = arquivo.readChar();
                array = new byte[arquivo.readInt()];
                arquivo.read(array);
                if (lapide != '*') {
                    Conta conta = new Conta();
                    conta.decodificaByteArray(array);
                    contas.add(conta);
                }
            }
            arquivo.close();
        } catch (Exception e) {
        }

        return contas;
    }

    public static Conta readById(int id) {
        ArrayList<Conta> contas = new ArrayList<>();
        contas = listAccouts();
        for (Conta conta : contas) {
            if (conta.idConta == id) {
                return conta;
            }
        }
        System.out.println("Conta não encontrada!");
        return null;
    }

    public static boolean update(Conta conta) {
        try {
            char lapide;
            long posRegistro;
            int tamanhoReg;
            byte buffer[];
            byte novoRegBuf[];
            RandomAccessFile arquivo = new RandomAccessFile(nomeArquivo, "rw");
            arquivo.seek(4);
            while (arquivo.getFilePointer() != -1) {
                posRegistro = arquivo.getFilePointer();
                lapide = arquivo.readChar();
                tamanhoReg = arquivo.readInt();
                buffer = new byte[tamanhoReg];
                arquivo.read(buffer);
                if (lapide != '*') {
                    Conta conta2 = new Conta();
                    conta2.decodificaByteArray(buffer);
                    if (conta.idConta == conta2.idConta) {
                        novoRegBuf = conta.converteContaEmByte();
                        if (novoRegBuf.length <= buffer.length) {
                            arquivo.seek(posRegistro + 6);
                            arquivo.write(novoRegBuf);
                        } else {
                            arquivo.seek(posRegistro);
                            arquivo.writeChar('*');
                            arquivo.seek(arquivo.length());
                            arquivo.writeChar(' ');
                            arquivo.write(novoRegBuf);
                        }
                    }
                }
                arquivo.close();
                return true;
            }
            arquivo.close();
        } catch (Exception e) {
            System.out.println("Erro desgraca!");
        }
        return false;

    }

    public static boolean delete(int id) {
        try {
            long pos;
            char lapide;
            int tamanho;
            byte[] array;
            Conta conta;
            RandomAccessFile arquivo = new RandomAccessFile(nomeArquivo, "rw");
            arquivo.seek(4);
            while (arquivo.getFilePointer() != -1) {
                pos = arquivo.getFilePointer();
                lapide = arquivo.readChar();
                tamanho = arquivo.readInt();
                array = new byte[tamanho];
                arquivo.read(array);
                if (lapide != '*') {
                    conta = new Conta();
                    conta.decodificaByteArray(array);
                    if (conta.idConta == id) {
                        arquivo.seek(pos);
                        arquivo.writeChar('*');
                        arquivo.close();
                        return true;
                    }
                }
            }
            arquivo.close();
            return false;
        } catch (Exception e) {
            System.out.println("Erro ao deletar");
            return false;
        }
    }

    public static boolean transferencia(Conta contaOrigem, Conta contaDestino, Float valor) {
        contaOrigem.transferenciasRealizadas++;
        contaOrigem.saldoConta = contaDestino.saldoConta - valor;
        contaDestino.transferenciasRealizadas++;
        contaDestino.saldoConta = contaDestino.saldoConta + valor;
        return update(contaOrigem) && update(contaDestino);
    }

    public static Conta createAccount() {

        Conta conta =
                new Conta(getLastId(), "andrei", "mail", "nomeUser", "senha", "123", "ita", 0, 2f);
        return conta;
    }

    public static Conta createAccount2() {

        Conta conta =
                new Conta(1, "andreiCaralho", "mail", "nomeUser", "senha", "123", "ita", 0, 2f);
        return conta;
    }
}
