import java.io.File;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Vector;

public class OrdenacaoExterna {
    final static String nomeArquivo = "conta.db";
    final static String prefixo = ".db";
    final static int ramSize = 4;
    static long ptrControl = 4;

    public static void main(String[] args) {
        distribuicao(5, 2);
        listAccouts("tmp0.db");
        System.out.println("---------------");
        listAccouts("tmp1.db");
    }

    public static void distribuicao(int ram, int caminhos) {
        try {
            ArrayList<Conta> contas = new ArrayList<>();
            RandomAccessFile temp[] = new RandomAccessFile[caminhos];
            for (int i = 0; i < caminhos; i++) {
                temp[i] = new RandomAccessFile("tmp" + i + prefixo, "rw");
            }
            while (ptrControl != -1) {
                for (int i = 0; i < caminhos; i++) {
                    for (int j = 0; j < ram; j++) {
                        contas.add(readFile(nomeArquivo));
                    }
                    Collections.sort(contas);
                    for (Conta conta : contas) {
                        byte ba[] = conta.converteContaEmByte();
                        temp[i].writeChar(' ');
                        temp[i].writeInt(ba.length);
                        temp[i].write(ba);
                    }
                    contas.clear();
                }
            }
        } catch (Exception e) {

        }
    }

    public static void intercalacaoAndrei(int ram, int caminhos) {
        try {
            ArrayList<Conta> contas = new ArrayList<>();
            RandomAccessFile temp[] = new RandomAccessFile[caminhos];
            RandomAccessFile temp2[] = new RandomAccessFile[caminhos];
            for (int i = 0; i < caminhos; i++) {
                temp[i] = new RandomAccessFile("tmp" + i + prefixo, "rw");
                temp2[i] = new RandomAccessFile("tmp2" + i + prefixo, "rw");
            }
            boolean moreInput = true;
            int i = 0;
            int tam = temp.length;
            long arrayPtr[] = new long[caminhos];
            while (moreInput) {
                /*
                 * Abrir arq1 e arq2 Extrair conta arq1 e arq2 Comparar o menor Escrever o menor no
                 * temp2 Incrementar o ponteiro do arquivo menor Enquanto os ponteiros do arquivo
                 * não forem -1;
                 */
                for (int j = 0; j < tam; j++) {
                    temp[j].seek(temp[j].getFilePointer()+2);
                    int tamanhoReg = temp[j].readInt();
                    byte ba[] = new byte[tamanhoReg];
                    temp[j].read(ba);
                    Conta conta = new Conta();
                    conta.decodificaByteArray(ba);
                }
                // [][][][][] i
                // [][][][][] i + 1
                // [][][][][][][][][][]

            }



        } catch (Exception e) {
            // TODO: handle exception
        }
    }



    public static void intercalacaoLuiz(int ram, int caminhos) {
        try {
            ArrayList<Conta> contas = new ArrayList<>();
            RandomAccessFile temp[] = new RandomAccessFile[caminhos];
            RandomAccessFile temp2[] = new RandomAccessFile[caminhos];
            for (int i = 0; i < caminhos; i++) {
                temp[i] = new RandomAccessFile("tmp" + i + prefixo, "rw");
                temp2[i] = new RandomAccessFile("tmp2" + i + prefixo, "rw");
            }
            boolean moreInput = true;
            int i = 0;
            int tam = temp.length;
            long arrayPtr[] = new long[caminhos];
            while (moreInput) {
                /*
                 * Abrir arq1 e arq2 Extrair conta arq1 e arq2 Comparar o menor Escrever o menor no
                 * temp2 Incrementar o ponteiro do arquivo menor Enquanto os ponteiros do arquivo
                 * não forem -1;
                 */
                for (int j = 0; j < tam; j++) {
                    temp[j].seek(temp[j].getFilePointer()+2);
                    int tamanhoReg = temp[j].readInt();
                    byte ba[] = new byte[tamanhoReg];
                    temp[j].read(ba);
                    Conta conta = new Conta();
                    conta.decodificaByteArray(ba);
                }
                // [][][][][] i
                // [][][][][] i + 1
                // [][][][][][][][][][]

            }



        } catch (Exception e) {
            // TODO: handle exception
        }
    }





    public static Conta readFile(String fileName) {
        char lapide;
        int tamanho;
        byte[] ba;
        try {
            RandomAccessFile file = new RandomAccessFile(fileName, "rw");
            file.seek(ptrControl);
            if (file.getFilePointer() != -1) {
                lapide = file.readChar();
                tamanho = file.readInt();
                ba = new byte[tamanho];
                file.read(ba);
                ptrControl = file.getFilePointer();
                if (lapide != '*') {
                    Conta conta = new Conta();
                    conta.decodificaByteArray(ba);
                    return conta;
                }
            }
        } catch (Exception e) {

        }
        return null;

    }



    public static ArrayList<Conta> listAccouts(String nome) {
        ArrayList<Conta> contas = new ArrayList<>();
        byte[] array;
        char lapide;

        try {
            RandomAccessFile arquivo = new RandomAccessFile(nome, "rw");
            while (arquivo.getFilePointer() != -1) {
                lapide = arquivo.readChar();
                array = new byte[arquivo.readInt()];
                arquivo.read(array);
                if (lapide != '*') {
                    Conta conta = new Conta();
                    conta.decodificaByteArray(array);
                    System.out.print(conta.idConta + " ");
                    contas.add(conta);

                }
            }
            arquivo.close();
        } catch (Exception e) {
        }
        return contas;
    }
}

