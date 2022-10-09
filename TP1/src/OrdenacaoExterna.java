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
    final static String tempFile1 = "temp1.db";
    final static String tempFile2 = "temp2.db";
    public static void main(String[] args) {
        caralho();
        listAccouts(tempFile1);
        System.out.println("-------------------------------------------------------------------------------");
        listAccouts(tempFile2);
    }
    
    public static void sort() {
        char lapide;
        int tamanho;
        byte vetor[];
        int cont = 0;
        ArrayList<Conta> contas = new ArrayList<>();
        try {
            RandomAccessFile arquivo = new RandomAccessFile(nomeArquivo, "rw");
            RandomAccessFile tmp1 = new RandomAccessFile(tempFile1, "rw");
            RandomAccessFile tmp2 = new RandomAccessFile(tempFile2, "rw");
            RandomAccessFile temp = tmp1;
            arquivo.seek(4);
            while (arquivo.getFilePointer() != -1) {
                lapide = arquivo.readChar();
                tamanho = arquivo.readInt();
                vetor = new byte[tamanho];
                arquivo.read(vetor);
                if (lapide != '*') {
                    Conta conta = new Conta();
                    conta.decodificaByteArray(vetor);
                    contas.add(conta);
                }
                if (contas.size() == 4) {
                    Collections.sort(contas);
                    for (Conta conta : contas) {
                        byte ba[] = conta.converteContaEmByte();
                        temp.write(ba);
                        cont++;
                        if (cont % 4 == 0) {
                            if (temp == tmp1) {
                                temp = tmp2;
                            } else {
                                temp = tmp1;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void caralho(){
        try {
            ArrayList <Conta> contas = new ArrayList<>();
            RandomAccessFile arquivo = new RandomAccessFile(nomeArquivo, "rw");
            RandomAccessFile tmp1 = new RandomAccessFile(tempFile1, "rw");
            RandomAccessFile tmp2 = new RandomAccessFile(tempFile2, "rw");
            RandomAccessFile temp = tmp1;
            char lapide;
            int tamanho;
            byte[] vetor;
            int cont = 0;
            arquivo.seek(4);
            while (arquivo.getFilePointer()!= -1) {

                lapide = arquivo.readChar();
                tamanho = arquivo.readInt();
                vetor = new byte[tamanho];
                arquivo.read(vetor);
                if (lapide!= '*') {
                    Conta conta = new Conta();
                    conta.decodificaByteArray(vetor);
                    contas.add(conta);
                }
                if (contas.size() == 4) {
                    Collections.sort(contas);
                    for (Conta conta : contas) {
                        byte ba[] = conta.converteContaEmByte();
                        temp.writeChar(' ');
                        temp.writeInt(ba.length);
                        temp.write(ba);
                        cont++;
                        if (cont % 4 == 0) {
                            if (temp == tmp1) {
                                temp = tmp2;
                            } else {
                                temp = tmp1;
                            }
                        }
                    }
                    contas.clear();
                }
                
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
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
                    System.out.print(conta.idConta+" ");
                    contas.add(conta);
                    
                }
            }
            arquivo.close();
        } catch (Exception e) {
        }
        return contas;
    }    
}

