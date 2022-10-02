import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
public class Crud {
    final String nomeArquivo = "conta.db";
    public Crud(){
        try {
            RandomAccessFile arquivo = new RandomAccessFile(nomeArquivo, "rw");
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao criar o arquivo");
        }
    }
    public boolean verificaNome(String nome){
        ArrayList<Conta> lista = listar();
        for (Conta conta : lista) {
            if (conta.nomeUsuario.equals(nome)) {
               return false; 
            }
        }
        return true;
        
    }
    public boolean criarConta(Conta conta) throws IOException {
        byte[] contaConvertida;
        conta.idConta = retornaUltimoID();
        contaConvertida = conta.converteContaEmByte();

        try {
            RandomAccessFile arquivo = new RandomAccessFile(nomeArquivo, "rw");
            arquivo.seek(0);
            arquivo.writeInt(conta.idConta);
            arquivo.seek(arquivo.length());
            arquivo.writeChar(' ');
            arquivo.writeInt(contaConvertida.length);
            arquivo.write(contaConvertida);
            arquivo.close();
            return true;

        } catch (Exception e) {
            System.out.println("Erro ao criar conta!");
            return false;
        }
    }
    public int retornaUltimoID() {
        int id;
        try {
            RandomAccessFile arquivo = new RandomAccessFile(nomeArquivo, "rw");
            arquivo.seek(0);
            id = arquivo.readInt();
            arquivo.close();
            return id++;

        } catch (Exception e) {
            return -1;
        }
    }
    public Conta BuscarPorId(int id) {
        int tamanhoRegistro;
        char lapide;
        byte[] vetorByte;
        try {
            RandomAccessFile arquivo = new RandomAccessFile(nomeArquivo, "rw");
            arquivo.seek(4);// Pulando cabeçalho.
            while (arquivo.getFilePointer() < arquivo.length()) {
                lapide = arquivo.readChar();
                tamanhoRegistro = arquivo.readInt();
                vetorByte = new byte[tamanhoRegistro];
                arquivo.read(vetorByte);// armazena o registro em bytes nesse vetor, e move-se o
                                        // ponteiro para o prox registro para o prox loop;
                if (lapide != '*') {
                    Conta conta = new Conta();
                    conta.decodificaByteArray(vetorByte);
                    if (conta.idConta == id) {
                        arquivo.close();
                        return conta;
                    }
                }
            }
            arquivo.close();

        } catch (Exception e) {
            System.out.println("Erro ao abrir o arquivo!");
        }
        return null;

    }
    public boolean deletarPorId(int id) {
        char lapide;
        int tamanhoRegistro;
        byte[] vetorByte;
        long lapidePos;
        try {
            RandomAccessFile arquivo = new RandomAccessFile(nomeArquivo, "rw");
            arquivo.seek(4);
            while (arquivo.getFilePointer() < arquivo.length()) {
                lapidePos = arquivo.getFilePointer();
                lapide = arquivo.readChar();
                tamanhoRegistro = arquivo.readInt();
                vetorByte = new byte[tamanhoRegistro];
                arquivo.read(vetorByte);
                if (lapide != '*') {
                    Conta conta = new Conta();
                    conta.decodificaByteArray(vetorByte);
                    if (conta.idConta == id) {
                        arquivo.seek(lapidePos);
                        arquivo.writeChar('*');
                        arquivo.close();
                        return true;
                    }
                }

            }
            arquivo.close();
        } catch (Exception e) {
            System.out.println("Erro ao abrir o arquivo!");
        }
        return false;
    }
    public boolean update(Conta novoRegistro) {
        char lapide;
        long posAtual;
        long lapidePos;
        int tamanhoRegistro;
        byte[] bytesContaAtual;
        try {
            RandomAccessFile arquivo = new RandomAccessFile(nomeArquivo, "rw");
            arquivo.seek(4);
            while (arquivo.getFilePointer() < arquivo.length()) {
                lapidePos = arquivo.getFilePointer();
                lapide = arquivo.readChar();
                tamanhoRegistro = arquivo.readInt();//dispensavel?
                posAtual = arquivo.getFilePointer();//pegando pos depois do tamanho
                bytesContaAtual = new byte[tamanhoRegistro];
                arquivo.read(bytesContaAtual);
                if (lapide != '*') {
                    Conta contaAtual = new Conta();
                    contaAtual.decodificaByteArray(bytesContaAtual);//crio a conta com seus atributos, lendo os bytes do registro.
                    if (contaAtual.idConta == novoRegistro.idConta) 
                    {
                        byte[] novoRegistroEmByte = novoRegistro.converteContaEmByte();//Transformo a conta do parametro em vetor de byte.
                        if (novoRegistroEmByte.length <= bytesContaAtual.length ) {
                            arquivo.seek(posAtual);
                            arquivo.write(novoRegistroEmByte);
                            arquivo.close();
                            return true;
                        }
                        else {
                            arquivo.seek(lapidePos);
                            arquivo.writeChar('*');
                            arquivo.seek(arquivo.length());
                            arquivo.write(novoRegistroEmByte);
                            arquivo.close();
                            return true;
                        }
                    }
                }
            }
            arquivo.close();
        } catch (Exception e) {
            System.out.println("Erro ao abrir o arquivo");
        }
        return false;
    }
    public ArrayList<Conta> listar(){
        char lapide;
        byte[] vetorByte;
        ArrayList<Conta> list = new ArrayList<>();
        
        try {
            RandomAccessFile arquivo = new RandomAccessFile(nomeArquivo, "rw");
            arquivo.seek(4);
            while (arquivo.getFilePointer()<arquivo.length()) {
                lapide = arquivo.readChar();
                vetorByte = new byte[arquivo.readInt()];
                arquivo.read(vetorByte);
                if (lapide!= '*') {
                    Conta conta = new Conta();
                    conta.decodificaByteArray(vetorByte);
                    list.add(conta);
                }
                
            }
            arquivo.close();

        } catch (Exception e) {
            System.out.println("Erro ao abrir o arquivo");
        }
        return list;


    }
    public boolean transferencia(int idOrigem,int IdDestino, float valor){
        Conta contaOrigem = BuscarPorId(idOrigem);
        Conta contaDestino = BuscarPorId(IdDestino);
        if (contaOrigem == null || contaDestino == null) {
            return false;
        } else{
            contaOrigem.transferenciasRealizadas = contaOrigem.transferenciasRealizadas + 1;
            contaDestino.transferenciasRealizadas = contaDestino.transferenciasRealizadas + 1;
            contaOrigem.saldoConta = contaOrigem.saldoConta - valor;
            contaDestino.saldoConta = contaDestino.saldoConta + valor;
        }
        return true;
    }
}
