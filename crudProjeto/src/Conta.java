import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class Conta {
    protected int idConta;
    protected String nomePessoa;
    protected String[] email;
    protected String nomeUsuario;
    protected String senha;
    protected String cpf;
    protected String cidade;
    protected int transferenciasRealizadas;
    protected float saldoConta;

    public Conta(){};

    public void emailQnt(int quantidade){
        this.email = new String[quantidade];
    }
    
    public Conta(int idConta, String nomePessoa, String[] email, String nomeUsuario, String senha,
            String cpf, String cidade, int transferenciasRealizadas, float saldoConta) {
        this.idConta = idConta;
        this.nomePessoa = nomePessoa;
        this.email = email;
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.cpf = cpf;
        this.cidade = cidade;
        this.transferenciasRealizadas = transferenciasRealizadas;
        this.saldoConta = saldoConta;
    }

    public byte[] converteContaEmByte()throws IOException{
        //Este metodo converte a conta instanciada em um vetor de byte.
        ByteArrayOutputStream vetorByte = new ByteArrayOutputStream();//Escrita
        DataOutputStream buffer = new DataOutputStream(vetorByte);//Escrita|buffer?
        buffer.writeInt(idConta);
        buffer.writeUTF(nomePessoa);
        for (int i = 0; i < email.length; i++) {
            buffer.writeUTF(email[i]);
        }
        buffer.writeUTF(nomeUsuario);
        buffer.writeUTF(senha);
        buffer.writeUTF(cpf);
        buffer.writeUTF(cidade);
        buffer.writeInt(transferenciasRealizadas);
        buffer.writeFloat(saldoConta);
        return vetorByte.toByteArray();
    }

    public void decodificaByteArray(byte[] vetorByte)throws IOException{
        //Este metodo recebe um vetor de byte do arquivo e converte o mesmo em um objeto do tipo Conta.
        ByteArrayInputStream bufferParaLeitura = new ByteArrayInputStream(vetorByte);
        DataInputStream leitura = new DataInputStream(bufferParaLeitura);
        idConta = leitura.readInt();
        nomePessoa = leitura.readUTF();
        for (int i = 0; i < email.length; i++) {
            System.out.println("Entrou no loop");
            email[i] = leitura.readUTF();
            System.out.println(email[i]);
        }
        nomeUsuario = leitura.readUTF();
        senha = leitura.readUTF();
        cpf = leitura.readUTF();
        cidade = leitura.readUTF();
        transferenciasRealizadas = leitura.readInt();
        saldoConta = leitura.readFloat();
        
    }

    @Override
    public String toString() {
        return "Conta [idConta=" + idConta + ", nomePessoa=" + nomePessoa + ", email="
                + Arrays.toString(email) + ", nomeUsuario=" + nomeUsuario + ", senha=" + senha
                + ", cpf=" + cpf + ", cidade=" + cidade + ", transferenciasRealizadas="
                + transferenciasRealizadas + ", saldoConta=" + saldoConta + "]";
    } 
}
