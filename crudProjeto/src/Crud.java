import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
public class Crud {
    final String nomeArquivo = "conta.db";
    private RandomAccessFile arquivo;
    public Crud(){}


    public boolean verificaNome(String nome){
        ArrayList<Conta> lista = listar();
        if (lista!= null) {
            for (Conta conta : lista) {
                if (conta.nomeUsuario.equals(nome)) {
                   return false; 
                }
            }
        }
        return true;
        
    }
    public boolean criarConta(Conta conta) throws IOException {
        //conta completa
        System.out.println("Criar conta");
        byte[] contaConvertida;
        conta.idConta = retornaUltimoID();
        int emailQnt = conta.email.length;
        try {
            arquivo = new RandomAccessFile(nomeArquivo, "rw");
            contaConvertida = conta.converteContaEmByte();
            arquivo.seek(0);
            arquivo.writeInt(conta.idConta);//Escreve ID no cabeçalho.
            arquivo.seek(arquivo.length());//Posiciona o ponteiro para o fim do arquivo.
            arquivo.writeInt(emailQnt);//Indicação de quantidade de email.
            arquivo.writeChar(' ');//Aloca espaço pra lápide.
            arquivo.writeInt(contaConvertida.length);//Escreve o tamanho em bytes do registro.
            arquivo.write(contaConvertida);//Escreve a conta no arquivo.
            arquivo.close();
            return true;

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Erro ao criar conta!");
            return false;
        }
    }
    public int retornaUltimoID() throws IOException {
        File f = new File(nomeArquivo);
        if(f.exists() && !f.isDirectory()) { 
            arquivo = new RandomAccessFile(nomeArquivo, "rw");
            arquivo.seek(0);
            int id = arquivo.readInt();
            return ++id;
        } else{
            return 1;
        } 
    }
    public Conta BuscarPorId(int id) {
        int tamanhoRegistro;
        char lapide;
        byte[] vetorByte;
        try {
            arquivo = new RandomAccessFile(nomeArquivo, "rw");
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
        int emailCount;
        ArrayList<Conta> list = new ArrayList<>();
        
        if (new File(nomeArquivo).exists()) {
            try {
                arquivo = new RandomAccessFile(nomeArquivo, "rw");
                arquivo.seek(4);//Pulando o cabeçalho
                while (arquivo.getFilePointer() != -1) {
                    emailCount = arquivo.readInt();//Lendo a quantidade de emails.
                    lapide = arquivo.readChar();//lendo o espaço da lápide.
                    vetorByte = new byte[arquivo.readInt()];//Alocando o vetor de bytes, lendo o tamanho indicado pelo registro.
                    arquivo.read(vetorByte);
                    if (lapide!= '*') {
                        Conta conta = new Conta();//email
                        conta.emailQnt(emailCount);//intanciando o atributo email com seu respectivo tamanho.
                        conta.decodificaByteArray(vetorByte);
                        list.add(conta);
                    }
                }
                return list;
    
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("Erro ao abrir o arquivo");
            }
        } return null;
        


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
