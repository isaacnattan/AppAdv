package modelo;

import java.io.BufferedReader;
import util.UVAlert;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import util.MatrizDinamica2;

/**
 * @author Isaac Nattan
 * @version 1.0
 */
public class DAOInfoArquivosTabela {

    private File infoFicheiro;
    private File infoArquivo;
    private File temp;
    private String pathWorkspace;                       // Agilizar consultas
    private MatrizDinamica2<String> DAOInfoFicheiro;
    private MatrizDinamica2<String> DAOInfoDocs;
    private boolean isTemp = false;

    public DAOInfoArquivosTabela(String pathWorkspace) {
        infoFicheiro = new File(pathWorkspace + File.separator + "infoTabFicheiro.txt");
        infoArquivo = new File(pathWorkspace + File.separator + "infoTabArquivo.txt");
        this.pathWorkspace = pathWorkspace;
        if (!infoFicheiro.exists()) {
            try {
                infoFicheiro.createNewFile();
                //Para Ocultar, colocar somente leitura, e colocar como arquivo de sistema:
                //Runtime.getRuntime().exec("cmd /c attrib +h +s +r " + infoFicheiro);
            } catch (IOException ex) {
                UVAlert.alertError("Problemas na criação do arquivo de informações." + ex);
            }
        }
        if (!infoArquivo.exists()) {
            try {
                infoArquivo.createNewFile();
                //Para Ocultar, colocar somente leitura, e colocar como arquivo de sistema:
                //Runtime.getRuntime().exec("cmd /c attrib +h +s +r " + infoArquivo);
            } catch (IOException ex) {
                UVAlert.alertError("Problemas na criação do arquivo de informacaes." + ex);
            }
        }
        DAOInfoDocs = getAllRegs("infoDocs");
        DAOInfoFicheiro = getAllRegs("infoFicheiro");
    }

    public void gravaInfoFicheiro(String nome, String dtCriacao, String dtModificacao,
            String tamanho, String autor, String path) {
        try {
            FileWriter fw;
            if (!isTemp) {
                fw = new FileWriter(infoFicheiro, true);
            } else {
                fw = new FileWriter(temp, true);
            }
            BufferedWriter bw = new BufferedWriter(fw);
            if (!isTemp) {
                bw.write(String.valueOf(getNovaTag("infoFicheiro")) + "-" + nome + ","
                        + dtCriacao + "," + dtModificacao + "," + tamanho + "KB" + "," + autor
                        + "," + path);
            } else {
                bw.write(String.valueOf(getNovaTag("temp")) + "-" + nome + ","
                        + dtCriacao + "," + dtModificacao + "," + tamanho + "," + autor
                        + "," + path);
            }
            ArrayList<String> linha = new ArrayList<String>();
            linha.add(nome);
            linha.add(dtCriacao);
            linha.add(dtModificacao);
            linha.add(tamanho);
            linha.add(autor);
            linha.add(path);
            DAOInfoFicheiro.adicionaLinha(linha);
            bw.write(";");          // caracteriza o final de uma instrucao
            bw.newLine();
            bw.close();
            fw.close();
        } catch (IOException ex) {
            UVAlert.alertError("Problemas com a escrita de informacao no Arquivo de Informacaes." + ex);
        }
    }

    private void removeInfoFicheiro(String id) {
        try {
            FileReader fr = new FileReader(infoFicheiro);
            BufferedReader br = new BufferedReader(fr);
            temp = new File(pathWorkspace + File.separator + "temp.txt");
            if (!temp.exists()) {
                temp.createNewFile();
                DAOInfoFicheiro = null;     // zera cash para nova inicializacao
            }
            FileWriter fw = new FileWriter(temp, true);
            BufferedWriter bw = new BufferedWriter(fw);
            isTemp = true;
            while (br.ready()) {
                String linha = br.readLine();
                if (!linha.contains(id)) {
                    String[] pedacosLinha = linha.split(",");
                    gravaInfoFicheiro(pedacosLinha[0].split("-")[1],
                            pedacosLinha[1], pedacosLinha[2],
                            pedacosLinha[3], pedacosLinha[4],
                            pedacosLinha[5].replace(";", ""));
                }
            }
            br.close();
            fr.close();
            bw.close();
            fw.close();
            atualizaArquivoTxt();
        } catch (IOException ex) {
            UVAlert.alertError("Problemas com a leitura de informacao no Arquivo de Informacaes." + ex);
        }
    }

    public MatrizDinamica2<String> getAllRegs(String arquivoString) {
        MatrizDinamica2<String> allRegs = new MatrizDinamica2<String>();
        File arquivo = getFile(arquivoString);
        try {
            FileReader fr = new FileReader(arquivo);
            BufferedReader br = new BufferedReader(fr);
            try {
                while (br.ready()) {
                    String linha = br.readLine();
                    String[] campos = linha.split(",");
                    ArrayList<String> reg = new ArrayList<String>();
                    for (int j = 0; j < campos.length; j++) {
                        reg.add(campos[j]);
                    }
                    allRegs.adicionaLinha(reg);
                    reg = null;
                    linha = null;
                    campos = null;
                }
                return allRegs;
            } catch (IOException ex) {
                UVAlert.alertError("Problemas na leitura do arquivo de informacaes." + ex);
            }
        } catch (FileNotFoundException ex) {
            UVAlert.alertError("Problemas na leitura do arquivo de informacaes." + ex);
        }
        return null;
    }

    private void atualizaArquivoTxt() {
        try {
            Runtime.getRuntime().exec("cmd /c ERASE /f /q " + infoArquivo.getPath()).waitFor();
            temp.renameTo(infoFicheiro);
            infoFicheiro = temp;
            isTemp = false;
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "Problemas com deletar arquivo temp." + ex);
        }
    }

    private void removeAtributos(File arquivo) {
        try {
            // Retira atributo de arquivo do sistema temporariamente
            Runtime.getRuntime().exec("cmd /c attrib -h -s -r " + arquivo).waitFor();
        } catch (IOException ex) {
            UVAlert.alertError("Problemas com a retirada de atributo do sistema do arquivo bd." + ex);
        } catch (InterruptedException ex) {
            UVAlert.alertError("Problemas com a retirada de atributo do sistema do arquivo bd." + ex);
        }
    }

    private void adicionaAtributos(File arquivo) {
        try {
            // Retira atributo de arquivo do sistema temporariamente
            Runtime.getRuntime().exec("cmd /c attrib +h +s +r " + arquivo).waitFor();
        } catch (IOException ex) {
            UVAlert.alertError("Problemas com a retirada de atributo do sistema do arquivo bd." + ex);
        } catch (InterruptedException ex) {
            UVAlert.alertError("Problemas com a retirada de atributo do sistema do arquivo bd." + ex);
        }
    }

    public void gravaInfoArquivo(String nome, String dtCriacao, String dtModificacao,
            String tamanho, String autor, String tipo, String path) {
        try {
            FileWriter fw = new FileWriter(infoArquivo, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(String.valueOf(getNovaTag("infoArquivo")) + "-" + nome + ","
                    + dtCriacao + "," + dtModificacao + "," + tamanho + "KB" + ","
                    + autor + "," + tipo + "," + path);
            ArrayList<String> linha = new ArrayList<String>();
            linha.add(nome);
            linha.add(dtCriacao);
            linha.add(dtModificacao);
            linha.add(tamanho);
            linha.add(autor);
            linha.add(tipo);
            linha.add(path);
            DAOInfoDocs.adicionaLinha(linha);
            bw.write(";");          // caracteriza o final de uma instrucao
            bw.newLine();
            bw.close();
            fw.close();
        } catch (IOException ex) {
            UVAlert.alertError("Problemas com a escrita de informacao no Arquivo de Informacaes." + ex);
        }
    }

    private File getFile(String arquivoString) {
        File arquivo = null;
        if (arquivoString.equals("infoFicheiro")) {
            arquivo = infoFicheiro;
        } else {
            arquivo = infoArquivo;
        }
        return arquivo;
    }

    public int getNovaTag(String arquivo) {
        int indice;
        int limite;
        int i = 0;
        if (arquivo.equals("infoFicheiro")) {
            limite = DAOInfoFicheiro.tamanho;
        } else if (arquivo.equals("infoArquivo")) {
            limite = DAOInfoDocs.tamanho;
        } else {    // recriando arquivo
            if (DAOInfoFicheiro == null) {
                limite = 0;
                DAOInfoFicheiro = new MatrizDinamica2<String>();
            } else {
                limite = DAOInfoFicheiro.tamanho;
            }
        }

        while (i < limite) {
            i++;
        }
        indice = i;
        indice++;       // inicia sempre de 1
        return indice;
    }

    public String getDataCriacao(String nome, String arquivoString) {
        int limite;
        try {
            MatrizDinamica2<String> dao = new MatrizDinamica2<String>();
            if (arquivoString.equals("infoFicheiro")) {
                limite = DAOInfoFicheiro.tamanho;
                dao = DAOInfoFicheiro;
            } else {
                limite = DAOInfoDocs.tamanho;
                dao = DAOInfoDocs;
            }
            for (int i = 0; i < limite; i++) {
                if (dao.obtemElementoLinha(i, 0).contains(nome)) {
                    return dao.obtemElementoLinha(i, 1);
                }
            }
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Prolema ao obter a data de criacao de um registro." + ex);
        }
        return null;
    }

    //getters
    public MatrizDinamica2<String> getDAOInfoFicheiro() {
        return DAOInfoFicheiro;
    }

    public MatrizDinamica2<String> getDAOInfoDocs() {
        return DAOInfoDocs;
    }

    public static void main(String[] args) {
        DAOInfoArquivosTabela dao =
                new DAOInfoArquivosTabela(System.getProperty("user.home") + File.separator
                + "RepositorioDeFicheiros");

        dao.removeInfoFicheiro("f4");
    }
}