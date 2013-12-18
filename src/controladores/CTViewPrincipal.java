package controladores;

import util.UVAlert;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.JTableHeader;
import modelo.DAOInfoArquivosTabela;
import util.MatrizDinamica2;
import util.ModeloTabela;
import util.TableSorter;
import views.ViewPrincipal;

/**
 * Controlador da view principal do projeto. Determina o fluxo e os métodos
 * necessários para o perfeito funcionamento de cada ação implementada em cada
 * objeto da interface a exemplo de botões, comboBoxes, paineis e qualquer outro
 * recurso.
 *
 * @author Isaac Nattan
 * @version 1.0
 */
public class CTViewPrincipal extends CTPai implements MouseListener, KeyListener {

    private ViewPrincipal viewPrincipal;
    private File workspace;
    private static final String pathWorkspace = System.getProperty("user.home")
            + File.separator + "RepositorioDeFicheiros";
    private ArrayList<String> linhaTabelaFicheiro;
    private ArrayList<String> cabecalhoTabelaFicheiro;
    private ArrayList<String> linhaTabelaDocumento;
    private ArrayList<String> cabecalhoTabelaDocumento;
    private ModeloTabela modeloTabelaFicheiro;
    private ModeloTabela modeloTabelaDocumento;
    private MatrizDinamica2<String> conteudoLinhaSelecionadaTabelaFicheiro;
    private MatrizDinamica2<String> conteudoLinhaSelecionadaTabelaDocumento;
    private ArrayList<String> conteudoPastaSelecionada;
    private File pastaSelecionada;
    private File arquivoSelecionado;
    private int[] linhasSelecionadas;
    // variaveis do ProgressBar
    JProgressBar progressBar;
    JPanel painelProgress;
    JDialog painelPrincipal;
    int indiceProgressBar;
    long novoPedaco;
    // Mais a frente essas variaveis irao matar muitas outras acima
    private ArrayList<File> pathsSelecionadosTabFicheiro;
    private ArrayList<File> cashPathsTabDocs;
    private ArrayList<File> pathsSelecionadosTabDocumentos;
    // variaveis de teste de desempanho
    long timeInicio;
    long timeFim;
    // variaveis que vao ficar  //////////////////////////////////////////////
    private MatrizDinamica2<String> cashArquivos;
    private MatrizDinamica2<String> cashFicheiros;
    private MatrizDinamica2<String> ficheirosSelecionados;
    private MatrizDinamica2<String> arquivosFicheirosSeleciodados;
    private MatrizDinamica2<String> arquivosSelecionados;

    public CTViewPrincipal() {
        viewPrincipal = new ViewPrincipal(modelTabFicheiro(), modelTabDocumento());    // Monta a Gui Inteira
        // Monta os cabecalhos das tabelas para a orenacao
        //montarComboBoxOrdenarPor();
        //montarComboBoxDocsOrdenarPor();
        addListeners();
        addAtalhos();
        //addListenerComboBox();
        criaWorkspace();
        carregaInformacoes();
        carregaFicheiros();
        viewPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewPrincipal.setExtendedState(JFrame.MAXIMIZED_BOTH);
        viewPrincipal.setMinimumSize(new Dimension(800, 600));
        viewPrincipal.setVisible(true);
        // Ordenacao clicando na coluna
        viewPrincipal.getTabelaDocumentos().setAutoCreateRowSorter(true);
        viewPrincipal.getTabelaFicheiros().setAutoCreateRowSorter(true);
    }

    /**
     * Método responsável por adicionar informação ao comboBox passado por
     * parametro.
     *
     * @param combo
     * @param info
     * @return void
     */
    public void addInfoComboBoxOrdenarPor(JComboBox combo, String info) {
        combo.addItem(info);
    }

    private void montarComboBoxOrdenarPor() {
        for (int i = 0; i < cabecalhoTabelaFicheiro.size(); i++) {
            addInfoComboBoxOrdenarPor(viewPrincipal.getComboBoxFicheiroOrdenarPor(),
                    cabecalhoTabelaFicheiro.get(i));
        }
    }

    private void montarComboBoxDocsOrdenarPor() {
        for (int i = 0; i < cabecalhoTabelaDocumento.size(); i++) {
            addInfoComboBoxOrdenarPor(viewPrincipal.getjComboDocsOrdenarPor(),
                    cabecalhoTabelaDocumento.get(i));
        }
    }

    private void addListeners() {
        viewPrincipal.getBtAdiocionarDocumentos().addMouseListener(this);           //Documentos
        viewPrincipal.getBtRemoverDocumentos().addMouseListener(this);
        viewPrincipal.getBtRenomearDocumentos().addMouseListener(this);
        //viewPrincipal.getBtClassificarDocumentos().addMouseListener(this);
        viewPrincipal.getBtExportarDocumentos().addMouseListener(this);
        viewPrincipal.getBtImportarDocumentos().addMouseListener(this);
        viewPrincipal.getBtCompararDocumentos().addMouseListener(this);
        viewPrincipal.getBtEditarDocumentos().addMouseListener(this);
        viewPrincipal.getBtAdiocionarFicheiro().addMouseListener(this);             // Ficheiro
        //viewPrincipal.getBtClassificarFicheiro().addMouseListener(this);
        viewPrincipal.getBtCompararFicheiro().addMouseListener(this);
        viewPrincipal.getBtExportarFicheiro().addMouseListener(this);
        viewPrincipal.getBtImportarFicheiro().addMouseListener(this);
        viewPrincipal.getBtRemoverFicheiro().addMouseListener(this);
        viewPrincipal.getBtRenomearFicheiro().addMouseListener(this);
        //viewPrincipal.getComboBoxDocsOrdenarPor().addMouseListener(this);           // ComboBoxes
        //viewPrincipal.getComboBoxFicheiroOrdenarPor().addMouseListener(this);
        viewPrincipal.getTabelaFicheiros().addMouseListener(this);                  // Tabelas
        viewPrincipal.getTabelaDocumentos().addMouseListener(this);
        viewPrincipal.getTabelaDocumentos().addKeyListener(this);
        viewPrincipal.getTabelaFicheiros().addKeyListener(this);
        // Deselecionar linhas das tabelas
        viewPrincipal.getRootPane().addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getSource() == viewPrincipal.getBtAdiocionarFicheiro()) {              // Ficheiros
            adicionarFicheiro();
        } else if (me.getSource() == viewPrincipal.getBtRemoverFicheiro()) {
            removerFicheiro();
        } else if (me.getSource() == viewPrincipal.getBtRenomearFicheiro()) {
            renomearFicheiro();
        } else if (me.getSource() == viewPrincipal.getBtImportarFicheiro()) {
            importar(true);       // Funciona bem, so que sem barra de progresso
            //importar();           // so copia arquivos, tem que modificar pra copiar subdiretorios
        } else if (me.getSource() == viewPrincipal.getBtExportarFicheiro()) {
            exportar(true);
        } else if (me.getSource() == viewPrincipal.getBtCompararFicheiro()) {
            compararFicheiros();
        } else if (me.getSource() == viewPrincipal.getBtAdiocionarDocumentos()) {    // Docs
            adicionarDocumento();
        } else if (me.getSource() == viewPrincipal.getBtRemoverDocumentos()) {
            removerDocumento();
        } else if (me.getSource() == viewPrincipal.getBtRenomearDocumentos()) {
            renomearDocumento();
        } else if (me.getSource() == viewPrincipal.getBtImportarDocumentos()) {
            importar(false);
        } else if (me.getSource() == viewPrincipal.getBtExportarDocumentos()) {
            exportar(false);
        } else if (me.getSource() == viewPrincipal.getBtCompararDocumentos()) {
            compararDocumentos();
        } else if (me.getSource() == viewPrincipal.getBtEditarDocumentos()) {
            editarDocumento();
        } else if (me.getSource() == viewPrincipal.getTabelaFicheiros() && me.getClickCount() == 2) {   // Tabelas
            abrirFicheiroWindows();
        } else if (me.getSource() == viewPrincipal.getTabelaFicheiros()) {           //  1 clicke na Tabela Ficheiro
            //obterLinhasSelecionadasTabelaFicheiro();
            //getLinhasSelecionadasTabFicheiro();
            //getDocumentosFicheiro("ficheiro2");
            clicouNaLinhaDaTabelaFicheiro();
        } else if (me.getSource() == viewPrincipal.getTabelaDocumentos() && me.getClickCount() == 2) {
            editarDocumento();
        } else if (me.getSource() == viewPrincipal.getTabelaDocumentos()) {         // 1 clicke na tabela Documento
            //obterLinhaSelecionadaTabelaDocumento();
            clicouNaLinhaTabelaDocumento();
        } else if (me.getSource() == viewPrincipal.getRootPane()) {
            viewPrincipal.getTabelaDocumentos().getSelectionModel().clearSelection();
            viewPrincipal.getTabelaFicheiros().getSelectionModel().clearSelection();
            setaInformacaoPainelDocumentos("", "", "", "", "", "");
            setaInformacaoPainelFicheiros("", "", "", "", "");
            modeloTabelaDocumento.limparTabela();
            modeloTabelaDocumento.fireTableDataChanged();
        }
    }

    /**
     * Adiciona listener KeyListener.
     *
     * @param ke
     * @return void
     */
    @Override
    public void keyPressed(KeyEvent ke) {
        if (ke.getSource() == viewPrincipal.getTabelaFicheiros()) {
            if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                abrirFicheiroWindows();
            }
        } else if (ke.getSource() == viewPrincipal.getTabelaDocumentos()) {
            if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                for (int i = 0; i < pathsSelecionadosTabDocumentos.size(); i++) {
                    abrirDocumentoOuDiretorio(i);
                }
            }
        }
    }

    /*private void addListenerComboBox() {
     viewPrincipal.getComboBoxDocsOrdenarPor().addActionListener(new ActionListener() {
     @Override
     public void actionPerformed(ActionEvent e) {
     UVAlert.alertSucess(viewPrincipal.getComboBoxDocsOrdenarPor().getSelectedItem().toString());
     }
     });
     viewPrincipal.getComboBoxFicheiroOrdenarPor().addActionListener(new ActionListener() {
     @Override
     public void actionPerformed(ActionEvent e) {
     UVAlert.alertSucess(viewPrincipal.getComboBoxFicheiroOrdenarPor().getSelectedItem().toString());
     }
     });
     }*/
    private void removerFicheiro() {
        if (conteudoLinhaSelecionadaTabelaFicheiro != null) {
            if (conteudoLinhaSelecionadaTabelaFicheiro.tamanho > 0) {
                if (conteudoLinhaSelecionadaTabelaFicheiro.tamanho == 1) {
                    if (mensagemDeConfirmacaoRemover(true) == JOptionPane.YES_OPTION) {
                        deleteDir(pastaSelecionada);
                        //Remover o conteudo das duas tabelas
                        modeloTabelaDocumento.limparTabela();   // Retira todos os registros da tabela de documentos
                        modeloTabelaFicheiro.removeLinha(viewPrincipal.getTabelaFicheiros().getSelectedRow());
                        modeloTabelaDocumento.fireTableDataChanged();
                        modeloTabelaFicheiro.fireTableDataChanged();
                    } else {
                        javax.swing.JOptionPane.showMessageDialog(painelProgress, "Ação cancelada pelo usuário.");
                    }
                    // Trata multiplas delecoes 
                } else if (conteudoLinhaSelecionadaTabelaFicheiro.tamanho > 1) {
                    javax.swing.JOptionPane.showMessageDialog(viewPrincipal, "Você está prestes a deletar mais de um ficheiro, "
                            + "isso pode envolver uma quantidade de documentos muito grande.");
                    if (mensagemDeConfirmacaoRemover(true) == JOptionPane.YES_OPTION) {
                        for (int i = 0; i < pathsSelecionadosTabFicheiro.size(); i++) {
                            deleteDir(pathsSelecionadosTabFicheiro.get(i));
                            //Remover o conteudo das duas tabelas
                            modeloTabelaFicheiro.removeLinha(linhasSelecionadas[i]);
                        }
                    }
                    modeloTabelaDocumento.limparTabela();   // Retira todos os registros da tabela de documentos
                    modeloTabelaDocumento.fireTableDataChanged();
                    modeloTabelaFicheiro.fireTableDataChanged();
                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                        "Selecione antes o ficheiro que deseja remover.");
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                    "Selecione antes o ficheiro que deseja remover.");
        }
    }

    /**
     * Adicionar dicas nos objetos da interface (Botao, linha tabela, painel)
     * que for necessário
     *
     * @return void
     */
    private void addToolTipTexts() {
    }

    private void importar() {
        new Thread() {
            @Override
            public void run() {
                JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnVal = fc.showSaveDialog(viewPrincipal);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    copiarDiretorio(file.getPath(), pathWorkspace);
                }
                //Atualiza tabela para exibir novo ficheiro
                modeloTabelaDocumento.limparTabela();   // Retira todos os registros da tabela de documentos
                modeloTabelaFicheiro.limparTabela();    // Retira todos os registros da tabela de ficheiros
                carregaFicheiros();                     // recarrega ficheiros
                modeloTabelaDocumento.fireTableDataChanged();
                modeloTabelaFicheiro.fireTableDataChanged();
            }
        }.start();
    }

    private void carregaInformacoes() {
        cashArquivos = new MatrizDinamica2<String>();
        cashFicheiros = new MatrizDinamica2<String>();
        DAOInfoArquivosTabela dao = new DAOInfoArquivosTabela(pathWorkspace);
        cashArquivos = dao.getDAOInfoDocs();
        cashFicheiros = dao.getDAOInfoFicheiro();
    }

    private void copiarDiretorio(String origem, String destino) {
        File ficheiroOrigem = new File(origem);
        File ficheiroDestino = new File(destino);
        if (ficheiroOrigem.exists()) {
            if (ficheiroDestino.exists()) {
                if (ficheiroOrigem.isDirectory()) {     // Verifica se o arquivo de origem eh mesmo um ficheiro
                    if (ficheiroDestino.isDirectory()) {  // verifica se o destino eh mesmo um diretorio
                        // Cria um ficheiro com o mesmo nome no destino
                        File copiaFicheiroDestino = new File(destino + File.separator + ficheiroOrigem.getName());
                        if (!copiaFicheiroDestino.exists()) {
                            copiaFicheiroDestino.mkdir();
                            String[] conteudoOrigem = ficheiroOrigem.list();
                            for (int i = 0; i < conteudoOrigem.length; i++) {
                                File arquivo = new File(copiaFicheiroDestino + File.separator + conteudoOrigem[i]);
                                if (!arquivo.exists()) {
                                    try {
                                        arquivo.createNewFile();
                                    } catch (IOException ex) {
                                        javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                                                "Problema com a criação de arquivo do método copiarArquivo."
                                                + ex);
                                    }
                                } else {
                                    javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                                            "Já existe um arquivo com esse nome.");
                                }
                                progressBar(conteudoOrigem.length, i);
                            }
                        } else {
                            javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                                    "Já existe um ficheiro com esse nome.");
                            //deleteDir(copiaFicheiroDestino);
                            //copiaFicheiroDestino.delete();
                        }
                    } else {
                        javax.swing.JOptionPane.showMessageDialog(null, "Você deve selecionar um ficheiro para copiar seu documento.");
                    }
                } else {
                    javax.swing.JOptionPane.showMessageDialog(null, "O arquivo passado não é um ficheiro.");
                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "O ficheiro destino não existe.");
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "O ficheiro de origem não existe.");
        }
    }

    // Inicializa como uma nova Thread, tarefa pode ser demorada
    private void compararDocumentos() {
        new Thread() {
            @Override
            public void run() {
                if (conteudoLinhaSelecionadaTabelaDocumento != null) {
                    if (conteudoLinhaSelecionadaTabelaDocumento.tamanho == 2) {
                        if (pathsSelecionadosTabDocumentos != null) {
                            if (compararDocumentos(pathsSelecionadosTabDocumentos.get(0).getPath(),
                                    pathsSelecionadosTabDocumentos.get(1).getPath())) {  // arquivo selecionado2
                                javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                                        "Os arquivos são iguais.");
                            } else {
                                javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                                        "Os arquivos não são iguais.");
                            }
                        } else {
                            javax.swing.JOptionPane.showMessageDialog(viewPrincipal, "Você deve selecionar 2 arquivos "
                                    + "para realizar a comparação.");
                        }
                    } else {
                        javax.swing.JOptionPane.showMessageDialog(viewPrincipal, "Você deve selecionar 2 arquivos "
                                + "para realizar a comparação.");
                    }
                } else {
                    javax.swing.JOptionPane.showMessageDialog(viewPrincipal, "Você deve selecionar 2 arquivos "
                            + "para realizar a comparação.");
                }
            }
        }.start();
    }

    private int mensagemDeConfirmacaoRemover(boolean isFicheiro) {
        Object[] options = {"Sim", "Não"};
        int i;
        if (isFicheiro) {
            i = JOptionPane.showOptionDialog(viewPrincipal,
                    "Essa opção fará com que o ficheiro e todo o seu conteúdo seja eliminado."
                    + "\nDeseja prosseguir?", "Questão",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    options, options[0]);
        } else {
            i = JOptionPane.showOptionDialog(viewPrincipal,
                    "Essa opção fará com que seu documento seja eliminado."
                    + "\nDeseja prosseguir?", "Questão",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    options, options[0]);
        }
        return i;
    }

    /**
     * Esse método preenche os arquivo de informações com as informações dos
     * ficheiros/arquivos adicionados manualmente no diretório de trabalho. Foi
     * como uma rotina para agilisar os testes de desempenho.
     *
     * @return void
     */
    private void carregaArquivosDeInformacao() {
        String[] subdiretorios = workspace.list();
        DAOInfoArquivosTabela dao = new DAOInfoArquivosTabela(pathWorkspace);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss | dd/MM/yyyy");
        for (int i = 0; i < subdiretorios.length; i++) {
            File sub = new File(pathWorkspace + File.separator + subdiretorios[i]);
            if (sub.isDirectory()) {
                dao.gravaInfoFicheiro(subdiretorios[i], "",
                        sdf.format(new Date(sub.lastModified())),
                        String.valueOf(sub.length() / 1024) + "KB", "dr. Fulano",
                        sub.getPath());
                String[] subdir = sub.list();
                for (int j = 0; j < subdir.length; j++) {
                    File s = new File(pathWorkspace + File.separator
                            + subdiretorios[i] + File.separator + subdir[j]);
                    dao.gravaInfoArquivo(subdir[j], "", sdf.format(new Date(s.lastModified())),
                            String.valueOf(s.length() / 1024) + "KB", "Dr. Fulano",
                            "Arquivo pdf", s.getPath());
                }
                progressBar(subdiretorios.length, i);
            }
        }
    }

    private void abrirDocumentoOuDiretorio(int indice) {
        arquivoSelecionado = new File(arquivosSelecionados.obtemElementoLinha(indice, 6).replace(";", "").trim());
        new Thread() {
            @Override
            public void run() {
                if (!arquivoSelecionado.isDirectory()) {
                    try {
                        // ta dando false quando eh diretorio, =\
                        Runtime.getRuntime().exec("cmd.exe /c start " + "\""
                                + "Título" + "\" " + "\"" + arquivoSelecionado + "\"").waitFor();
                    } catch (IOException ex) {
                        UVAlert.alertError("Problema ao abrir arquivo/diretório." + ex);
                    } catch (InterruptedException ex) {
                        UVAlert.alertError("Problema ao abrir arquivo/diretório." + ex);
                    }
                } else {
                    try {
                        Runtime.getRuntime().exec("explorer.exe " + "\"" + arquivoSelecionado + "\"");
                    } catch (IOException ex) {
                        UVAlert.alertError("Problema ao abrir arquivo/diretório." + ex);
                    }
                }
            }
        }.start();
    }

    private void obterLinhasSelecionadasTabelaFicheiro() {
        timeInicio = System.currentTimeMillis();
        // Sim, sempre deve-se gerar uma nova instancia desses caras
        linhasSelecionadas = viewPrincipal.getTabelaFicheiros().getSelectedRows();      // manipulacao de multiplas linhas selecionadas
        conteudoLinhaSelecionadaTabelaFicheiro = new MatrizDinamica2<String>();
        pathsSelecionadosTabFicheiro = new ArrayList<File>();       // Guarda path dos ficheiros
        cashPathsTabDocs = new ArrayList<File>();           // Guarda os Paths dos arquivos para evitar o problema de qual arquivo pertence a qual pasta
        for (int k = 0; k < linhasSelecionadas.length; k++) {   // guarda conteudo de todas as linhas selecionadas
            conteudoPastaSelecionada = new ArrayList<String>();     // a cada iteracao o conteudo da pasta selecionada muda
            if (linhasSelecionadas.length > 0) {            // talvez naum seja mais necessario, ver depois
                conteudoLinhaSelecionadaTabelaFicheiro.adicionaLinha(modeloTabelaFicheiro.
                        getLinhaTabela(linhasSelecionadas[k]));     //guarda conteudo da linha selecionada
                DAOInfoArquivosTabela dao = new DAOInfoArquivosTabela(pathWorkspace);
                setaInformacaoPainelFicheiros(conteudoLinhaSelecionadaTabelaFicheiro.obtemElementoLinha(
                        conteudoLinhaSelecionadaTabelaFicheiro.tamanho - 1, 0),
                        dao.getDataCriacao(conteudoLinhaSelecionadaTabelaFicheiro.obtemElementoLinha(
                        conteudoLinhaSelecionadaTabelaFicheiro.tamanho - 1, 0), "infoFicheiro").
                        replace(" ", "").replace("|", ", "),
                        conteudoLinhaSelecionadaTabelaFicheiro.obtemElementoLinha(
                        conteudoLinhaSelecionadaTabelaFicheiro.tamanho - 1, 2),
                        conteudoLinhaSelecionadaTabelaFicheiro.obtemElementoLinha(
                        conteudoLinhaSelecionadaTabelaFicheiro.tamanho - 1, 3),
                        conteudoLinhaSelecionadaTabelaFicheiro.obtemElementoLinha(
                        conteudoLinhaSelecionadaTabelaFicheiro.tamanho - 1, 4));
                // Apagar o conteudo anterior da tabela
                if (modeloTabelaDocumento.limparTabela()) {
                    pastaSelecionada = new File(pathWorkspace + File.separator + conteudoLinhaSelecionadaTabelaFicheiro.
                            obtemElementoLinha(k, 0));
                    conteudoPastaSelecionada.addAll(Arrays.asList(pastaSelecionada.list()));
                    pathsSelecionadosTabFicheiro.add(new File(pastaSelecionada.getPath()));
                    int novoTamanhocashDocs = cashPathsTabDocs.size();
                    for (int a = novoTamanhocashDocs; a < novoTamanhocashDocs + conteudoPastaSelecionada.size(); a++) {   // vai do ultimo elemento do arraylist ate a quantidade que ta por vir
                        cashPathsTabDocs.add(new File(pastaSelecionada.getPath() + File.separator + conteudoPastaSelecionada.get(a - novoTamanhocashDocs)));
                    }
                    // Atualiza o tamanho para exibir na tela
                    novoTamanhocashDocs = cashPathsTabDocs.size();
                    for (int i = 0; i < novoTamanhocashDocs; i++) {
                        DateFormat sdf = new SimpleDateFormat("HH:mm:ss, dd/MM/yyyy");
                        addLinhaTabelaDocumento(cashPathsTabDocs.get(i).getName(),
                                dao.getDataCriacao(cashPathsTabDocs.get(i).getName(), "infoFicheiro"),
                                sdf.format(new Date(cashPathsTabDocs.get(i).lastModified())),
                                String.valueOf(cashPathsTabDocs.get(i).length() / 1024) + " KB", "Dr. Fulano", "Arquivo pdf");
                    }
                }
            }
            modeloTabelaDocumento.fireTableDataChanged();
            timeFim = System.currentTimeMillis();
            System.out.println("Tempo; " + String.valueOf(timeFim - timeInicio));
        }
    }

    /**
     * Procedimento responsável por definir o que acontece quando o usuário
     * clica em uma ou mais linhas da tabela de ficheiros.
     */
    private void clicouNaLinhaDaTabelaFicheiro() {
        ficheirosSelecionados = new MatrizDinamica2<String>();
        arquivosFicheirosSeleciodados = new MatrizDinamica2<String>();
        int[] selectedLines = viewPrincipal.getTabelaFicheiros().getSelectedRows();
        if (selectedLines.length > 0) {
            ficheirosSelecionados = getLinhasSelecionadasTabFicheiro();
            for (int i = 0; i < ficheirosSelecionados.tamanho; i++) {
                MatrizDinamica2<String> matrizTemporaria = getDocumentosFicheiro(
                        ficheirosSelecionados.obtemElementoLinha(i, 0).replace(
                        ficheirosSelecionados.obtemElementoLinha(i, 0).substring(0, 2), ""));
                ArrayList<String> arrayTemp = new ArrayList<String>();
                for (int k = 0; k < matrizTemporaria.tamanho; k++) {
                    arrayTemp = matrizTemporaria.obtemLinha(k);
                    arquivosFicheirosSeleciodados.adicionaLinha(arrayTemp);
                }
            }
        }
        setaArquivosDoFicheiroNaTabelaDeFicheiro();
        setaInformacaoPainelFicheiros(ficheirosSelecionados.obtemElementoLinha(ficheirosSelecionados.tamanho - 1, 0).split("-")[1],
                ficheirosSelecionados.obtemElementoLinha(ficheirosSelecionados.tamanho - 1, 1).replace(" ", "").replace("|", ", "),
                ficheirosSelecionados.obtemElementoLinha(ficheirosSelecionados.tamanho - 1, 2).replace(" ", "").replace("|", ", "),
                ficheirosSelecionados.obtemElementoLinha(ficheirosSelecionados.tamanho - 1, 3),
                ficheirosSelecionados.obtemElementoLinha(ficheirosSelecionados.tamanho - 1, 4));
    }

    private void clicouNaLinhaTabelaDocumento() {
        arquivosSelecionados = new MatrizDinamica2<String>();
        int[] selectedLines = viewPrincipal.getTabelaDocumentos().getSelectedRows();
        if (selectedLines.length > 0) {
            for (int j = 0; j < selectedLines.length; j++) {
                String nome = (String) modeloTabelaDocumento.getValueAt(selectedLines[j], 0);
                for (int i = 0; i < cashArquivos.tamanho; i++) {
                    String[] pedacos = cashArquivos.obtemElementoLinha(i, 6).replace("\\", "/").split("/");
                    if (pedacos[5].replace(";", "").equals(nome)) {
                        ArrayList<String> linha = new ArrayList<String>();
                        linha.add(cashArquivos.obtemElementoLinha(i, 0));
                        linha.add(cashArquivos.obtemElementoLinha(i, 1));
                        linha.add(cashArquivos.obtemElementoLinha(i, 2));
                        linha.add(cashArquivos.obtemElementoLinha(i, 3));
                        linha.add(cashArquivos.obtemElementoLinha(i, 4));
                        linha.add(cashArquivos.obtemElementoLinha(i, 5));
                        linha.add(cashArquivos.obtemElementoLinha(i, 6));
                        arquivosSelecionados.adicionaLinha(linha);
                        break;
                    }
                }
            }
            setaInformacaoPainelDocumentos(arquivosSelecionados.obtemElementoLinha(arquivosSelecionados.tamanho - 1, 0).split("-")[1],
                    arquivosSelecionados.obtemElementoLinha(arquivosSelecionados.tamanho - 1, 1).replace(" ", "").replace("|", ", "),
                    arquivosSelecionados.obtemElementoLinha(arquivosSelecionados.tamanho - 1, 2).replace(" ", "").replace("|", ", "),
                    arquivosSelecionados.obtemElementoLinha(arquivosSelecionados.tamanho - 1, 3),
                    arquivosSelecionados.obtemElementoLinha(arquivosSelecionados.tamanho - 1, 4),
                    arquivosSelecionados.obtemElementoLinha(arquivosSelecionados.tamanho - 1, 5));
        }
    }

    /**
     * Retornar uma {@link MatrizDinamica2<String>} com todo o conteúdo [nome,
     * dtCriacao, dtModifi, tamanho, autor, path] dos ficheiros selecio- nados.
     * Complexidade: O(n²) em cima de um tamanho que não vai exceder 1000, logo
     * eh rápido. xD. Foda-se tia Leila !!
     *
     * @return {@link MatrizDinamica2<String>}
     */
    private MatrizDinamica2<String> getLinhasSelecionadasTabFicheiro() {
        MatrizDinamica2<String> conteudoLinhasSelecionadas = new MatrizDinamica2<String>();
        int[] selectedLines = viewPrincipal.getTabelaFicheiros().getSelectedRows();
        if (selectedLines.length > 0) {
            for (int j = 0; j < selectedLines.length; j++) {
                String nome = (String) modeloTabelaFicheiro.getValueAt(selectedLines[j], 0);
                for (int i = 0; i < cashFicheiros.tamanho; i++) {
                    if (cashFicheiros.obtemElementoLinha(i, 0).contains(nome)) {
                        ArrayList<String> linha = new ArrayList<String>();
                        linha.add(cashFicheiros.obtemElementoLinha(i, 0));
                        linha.add(cashFicheiros.obtemElementoLinha(i, 1));
                        linha.add(cashFicheiros.obtemElementoLinha(i, 2));
                        linha.add(cashFicheiros.obtemElementoLinha(i, 3));
                        linha.add(cashFicheiros.obtemElementoLinha(i, 4));
                        linha.add(cashFicheiros.obtemElementoLinha(i, 5));
                        conteudoLinhasSelecionadas.adicionaLinha(linha);
                        break;
                    }
                }
            }
        }
        return conteudoLinhasSelecionadas;
    }

    /**
     * o nome diz tudo ! xD
     */
    private void setaArquivosDoFicheiroNaTabelaDeFicheiro() {
        if (modeloTabelaDocumento.limparTabela()) {
            for (int i = 0; i < arquivosFicheirosSeleciodados.tamanho; i++) {
                addLinhaTabelaDocumento(arquivosFicheirosSeleciodados.obtemElementoLinha(i, 0).split("-")[1],
                        arquivosFicheirosSeleciodados.obtemElementoLinha(i, 1),
                        arquivosFicheirosSeleciodados.obtemElementoLinha(i, 2),
                        arquivosFicheirosSeleciodados.obtemElementoLinha(i, 3),
                        arquivosFicheirosSeleciodados.obtemElementoLinha(i, 4),
                        arquivosFicheirosSeleciodados.obtemElementoLinha(i, 5).replace(";", ""));
            }
        }
        modeloTabelaDocumento.fireTableDataChanged();
    }

    /**
     * Dado um nomeFicheiro retorna os arquivos contidos dentro dele.
     * Complexidade: O(n)
     *
     * @param nomeFicheiro
     * @return {@link MatrizDinamica2<String>}
     */
    private MatrizDinamica2<String> getDocumentosFicheiro(String nomeFicheiro) {
        MatrizDinamica2<String> arquivosFicheiro = new MatrizDinamica2<String>();
        for (int i = 0; i < cashArquivos.tamanho; i++) {
            String[] pedacos = cashArquivos.obtemElementoLinha(i, 6).replace("\\", "/").split("/");
            if (pedacos[4].contains(nomeFicheiro)) {
                ArrayList<String> linha = new ArrayList<String>();
                linha.add(cashArquivos.obtemElementoLinha(i, 0));
                linha.add(cashArquivos.obtemElementoLinha(i, 1));
                linha.add(cashArquivos.obtemElementoLinha(i, 2));
                linha.add(cashArquivos.obtemElementoLinha(i, 3));
                linha.add(cashArquivos.obtemElementoLinha(i, 4));
                linha.add(cashArquivos.obtemElementoLinha(i, 5));
                linha.add(cashArquivos.obtemElementoLinha(i, 6));
                arquivosFicheiro.adicionaLinha(linha);
            }
        }
        return arquivosFicheiro;
    }

    // complexidade linear
    private void obterLinhaSelecionadaTabelaDocumento() {
        // Sim, sempre deve-se gerar uma nova instancia desses caras
        linhasSelecionadas = viewPrincipal.getTabelaDocumentos().getSelectedRows();      // manipulacao de multiplas linhas selecionadas
        conteudoLinhaSelecionadaTabelaDocumento = new MatrizDinamica2<String>();
        pathsSelecionadosTabDocumentos = new ArrayList<File>();
        for (int i = 0; i < linhasSelecionadas.length; i++) {   // guarda conteudo de todas as linhas selecionadas
            conteudoLinhaSelecionadaTabelaDocumento.adicionaLinha(modeloTabelaDocumento.
                    getLinhaTabela(linhasSelecionadas[i]));     //guarda conteudo da linha selecionada
            DAOInfoArquivosTabela dao = new DAOInfoArquivosTabela(pathWorkspace);
            setaInformacaoPainelDocumentos(conteudoLinhaSelecionadaTabelaDocumento.
                    obtemElementoLinha(conteudoLinhaSelecionadaTabelaDocumento.tamanho - 1, 0),
                    dao.getDataCriacao(conteudoLinhaSelecionadaTabelaDocumento.
                    obtemElementoLinha(conteudoLinhaSelecionadaTabelaDocumento.tamanho - 1, 1),
                    "infoArquivo").replace(" ", "").replace("|", ", "),
                    conteudoLinhaSelecionadaTabelaDocumento.
                    obtemElementoLinha(conteudoLinhaSelecionadaTabelaDocumento.tamanho - 1, 2),
                    conteudoLinhaSelecionadaTabelaDocumento.
                    obtemElementoLinha(conteudoLinhaSelecionadaTabelaDocumento.tamanho - 1, 3),
                    conteudoLinhaSelecionadaTabelaDocumento.
                    obtemElementoLinha(conteudoLinhaSelecionadaTabelaDocumento.tamanho - 1, 4),
                    conteudoLinhaSelecionadaTabelaDocumento.
                    obtemElementoLinha(conteudoLinhaSelecionadaTabelaDocumento.tamanho - 1, 5));
            // Obtem path da linha selecionada
            for (int j = 0; j < cashPathsTabDocs.size(); j++) {
                if (cashPathsTabDocs.get(j).getPath().contains(
                        conteudoLinhaSelecionadaTabelaDocumento.obtemElementoLinha(i, 0))) {
                    // Nesse ponto teremos o path do arquivo selecionado, nao leva 
                    // em consideracao a existencia de arquivos iguais em ficheiros diferentes
                    pathsSelecionadosTabDocumentos.add(cashPathsTabDocs.get(j));
                    break;
                }
            }
        }
    }

    private void renomearFicheiro() {
        if (viewPrincipal.getTabelaFicheiros().getSelectedRow() >= 0) {
            String renomear = javax.swing.JOptionPane.showInputDialog(null,
                    "Entre com o novo nome do seu Ficheiro:",
                    "Questão", JOptionPane.PLAIN_MESSAGE);
            if (renomear != null) {         // Para quando o usuário cancelar antes de digitar algo
                if (!renomear.equals("")) {
                    boolean ok = false;
                    String[] ficheirosWorkspace = workspace.list();
                    for (int i = 0; i < ficheirosWorkspace.length; i++) {
                        if (!renomear.equals(ficheirosWorkspace[i])) {
                            ok = true;
                        } else {
                            ok = false;
                            break;
                        }
                    }
                    if (ok) {
                        try {
                            pastaSelecionada.renameTo(new File(pathWorkspace + File.separator + renomear));
                        } catch (Exception ex) {
                            javax.swing.JOptionPane.showMessageDialog(viewPrincipal, ex);
                        }
                        modeloTabelaFicheiro.limparTabela();
                        carregaFicheiros();
                    } else {
                        javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                                "Existe um documento com esse mesmo identificador, por favor insira outro");
                        renomearFicheiro();
                    }
                    modeloTabelaDocumento.fireTableDataChanged();
                    modeloTabelaFicheiro.fireTableDataChanged();
                } else {
                    javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                            "Insira um identificaor válido.");
                    renomearFicheiro();
                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                        "Ação cancelada pelo usuário.");

            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                    "Selecione antes o ficheiro que deseja renomear.");
        }
    }

    private void editarDocumento() {
        // Se existe somente uma linha slecionada
        if (arquivosSelecionados != null) {
            for (int i = 0; i < arquivosSelecionados.tamanho; i++) {
                abrirDocumentoOuDiretorio(i);
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                    "Você deve selecionar um documento para edita-lo.");
        }
    }

    private void ordenarTabela(JTable tabela, ModeloTabela modeloTab) {
        JTableHeader tableHeader = tabela.getTableHeader();
        TableSorter ts = new TableSorter(modeloTab, tableHeader);
        if (ts.isSorting()) {
            UVAlert.alertSucess("Dá pra ordenar!");
        } else {
            UVAlert.alertSucess("Não dá pra ordenar!");
        }
    }

    private boolean compararDocumentos(String file1, String file2) {
        File f1 = new File(file1);
        File f2 = new File(file2);
        if (f1.length() == f2.length()) {
            try {
                final InputStream isf1 = new FileInputStream(f1);
                final InputStream isf2 = new FileInputStream(f2);
                for (int i = 0; i <= f1.length(); i++) {
                    try {
                        int byte_f1 = isf1.read();
                        int byte_f2 = isf2.read();
                        if (byte_f1 != byte_f2) {
                            isf1.close();
                            isf2.close();
                        }
                    } catch (IOException ex) {
                        javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                                "Problemas na verificação de igualdade de documentos." + ex);
                    }
                    // So mostra um progressBar se o arquivo tiver um determinado
                    // tamanho
                    if (f1.length() > 100000) {
                        progressBar(f1.length(), i);
                    }
                }   // fim do for
            } catch (FileNotFoundException ex) {
                javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                        "Problemas na verificação de igualdade de documentos." + ex);
            }
        } else {
            return false; // tamanho e conteudo diferente  
        }
        return true; // arquivos iguais  
    }

    private void progressBar(long limite, long indice) {
        // Inicializacao
        if (indice == 0) {
            // Cria progressBar
            progressBar = new JProgressBar(0, 100);
            progressBar.setValue(0);
            progressBar.setStringPainted(true);
            // Seta progressBar no painel
            painelProgress = new JPanel();
            painelProgress.setSize(100, 150);
            painelProgress.add(progressBar);
            // Seta painel do ProgressBar no painelPrincipal
            painelPrincipal = new JDialog();
            painelPrincipal.setSize(400, 200);
            painelPrincipal.setLayout(new BorderLayout());
            painelPrincipal.setLocationRelativeTo(null);
            painelPrincipal.add(painelProgress, BorderLayout.CENTER);
            painelPrincipal.setVisible(true);
            // Inicializa o indice do progressBar
            indiceProgressBar = 1;
            novoPedaco = limite / 100;
        } else {
            // andamento
            if (indice == novoPedaco) {
                novoPedaco = (limite / 100) * (indiceProgressBar + 1);
                System.out.println(indiceProgressBar);
                progressBar.setValue(indiceProgressBar++);
            }
            // Finalizacao
            if (indice == limite) {
                painelPrincipal.dispose();
            }
        }
    }

    private void abrirFicheiroWindows() {
        try {
            Runtime.getRuntime().exec("explorer.exe " + "\"" + pathWorkspace
                    + File.separator + modeloTabelaFicheiro.
                    getLinhaTabela(viewPrincipal.getTabelaFicheiros().
                    getSelectedRow()).get(0) + "\"");
        } catch (IOException ex) {
            javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                    "Problemas ao abrir o ficheiro" + ex);
        }
    }

    private void compararFicheiros() {
        if (linhasSelecionadas == null) {
            javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                    "Voce deve selecionar dois ficheiros para realizar a comparação.");
        } else if (linhasSelecionadas.length == 2) {
            File ficheiro1 = new File(pathWorkspace + File.separator
                    + conteudoLinhaSelecionadaTabelaFicheiro.obtemElementoLinha(0, 0));
            File ficheiro2 = new File(pathWorkspace + File.separator
                    + conteudoLinhaSelecionadaTabelaFicheiro.obtemElementoLinha(1, 0));
            String[] conteudoFicheiro1 = ficheiro1.list();
            String[] conteudoFicheiro2 = ficheiro2.list();
            if (conteudoFicheiro1 == null && conteudoFicheiro2 == null) {     //Ficheiros iguais (ambos vazios)
                javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                        "Os ficheiros são iguais.");
            } else if (conteudoFicheiro1 == null || conteudoFicheiro2 == null) {  // Ficheiros diferentes (somente um dos dois vazios)
                javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                        "Os ficheiros não são iguais.");
            } else {
                if (conteudoFicheiro1.length != conteudoFicheiro2.length) {   // se tamanho diferente, diferente
                    javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                            "Os ficheiros não são iguais.");
                } else {        // Se tamanho igual, verificar se são iguais
                    // se eh igual nao importa qual dos 2 tamanhos colocar como limite
                    int i;
                    for (i = 0; i < conteudoFicheiro1.length; i++) {
                        // a organizacao dos documentos eh a do SO, logo
                        // se os diretorios sao mesmo iguais estao igualmente ordenados
                        if (!compararDocumentos(ficheiro1.getPath() + File.separator
                                + conteudoFicheiro1[i],
                                ficheiro2.getPath() + File.separator
                                + conteudoFicheiro2[i])) {
                            javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                                    "Os ficheiros não são iguais.");
                        }
                        if (conteudoFicheiro1.length > 1000) {
                            progressBar(conteudoFicheiro1.length, i);
                        }
                    }
                    if (i == conteudoFicheiro1.length) {
                        // verifica se as comparações foram mesmo feitas
                        // pode ter havio problema com o FileInputStream
                        // Se saiu da comparacao arquivo a arquivo eh igual
                        javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                                "Os ficheiros são iguais.");
                    } else {
                        javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                                "Problemas com o FileInputStream, filho da puta.");
                    }
                }
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                    "Voce deve selecionar dois ficheiros para realizar a comparação.");
        }
    }

    private void criaWorkspace() {
        try {
            workspace = new File(pathWorkspace);
            if (!workspace.exists()) {
                // Criar pasta no diretorio do usuario
                workspace.mkdir();
                //Para Ocultar, colocar somente leitura, e colocar como arquivo de sistema:
                Runtime.getRuntime().exec("cmd /c attrib +h +s +r " + pathWorkspace);
            }
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(viewPrincipal, ex);
        }
    }

    // testar esse metodo depois -----------+++++++++++++++++++=----------------
    private void copiaRapidaArquivos(String pathOrigem, String pathDestino) {
        File fIn = new File(pathOrigem);
        File fOut = new File(pathDestino);
        FileInputStream fis;
        FileOutputStream fos;
        FileChannel fcIn;
        FileChannel fcOut;
        try {
            fis = new FileInputStream(fIn);
            fos = new FileOutputStream(fOut);
            fcIn = fis.getChannel();
            fcOut = fos.getChannel();
            fcOut.transferFrom(fcIn, 0, fIn.length());
            fos.close();
            fis.close();


        } catch (IOException ex) {
        }
    }

    private void renomearDocumento() {
        // Se ha uma linha selecionada na tabela de Documenos
        if (viewPrincipal.getTabelaDocumentos().getSelectedRows().length == 1) {
            String renomear = javax.swing.JOptionPane.showInputDialog(null,
                    "Entre com o novo nome do seu Documento:",
                    "Questão", JOptionPane.PLAIN_MESSAGE);
            if (renomear != null) {         // Para quando o usuário cancelar antes de digitar algo
                if (!renomear.equals("")) {
                    String pathFicheiro = pathWorkspace + File.separator
                            + modeloTabelaFicheiro.getLinhaTabela(
                            viewPrincipal.getTabelaFicheiros().getSelectedRow()).get(0);
                    pastaSelecionada = new File(pathFicheiro);
                    arquivoSelecionado = new File(pathFicheiro
                            + File.separator + modeloTabelaDocumento.getLinhaTabela(
                            viewPrincipal.getTabelaDocumentos().getSelectedRow()).get(0));
                    boolean ok = false;
                    String[] documentosFicheiro = pastaSelecionada.list();
                    for (int i = 0; i < documentosFicheiro.length; i++) {
                        if (!renomear.equals(documentosFicheiro[i])) {
                            ok = true;
                        } else {
                            ok = false;
                            break;
                        }
                    }
                    if (ok) {
                        try {
                            arquivoSelecionado.renameTo(new File(pastaSelecionada + File.separator + renomear));
                        } catch (Exception ex) {
                            javax.swing.JOptionPane.showMessageDialog(viewPrincipal, ex);
                        }
                        modeloTabelaDocumento.removeLinha(viewPrincipal.getTabelaDocumentos().getSelectedRow());
                        obterLinhasSelecionadasTabelaFicheiro();
                        modeloTabelaDocumento.fireTableDataChanged();
                    } else {
                        javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                                "Já existe um documento com esse identificador, por favor escolha outro.");
                        renomearDocumento();
                    }
                } else {
                    javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                            "Insira um identificador válido.");
                    renomearDocumento();
                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                        "Ação canelada pelo usuário.");
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                    "Selecione antes o documento que deseja renomear.");
        }
    }

    private void classificarFicheiro() {
    }

    private void importar(boolean isFicheiro) { // isficheiro -> se importar ficheiro
        // Quando importar um arquivo/pasta, verificar com o metodo de comparacao
        // se os dois sao iguais e alertar
        JFileChooser fc = new JFileChooser();
        if (isFicheiro) {
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        } else {
            fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        }
        int returnVal = fc.showSaveDialog(viewPrincipal);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            if (viewPrincipal.getTabelaFicheiros().getSelectedRows().length == 1) {
                File file = fc.getSelectedFile();
                try {
                    File destino = null;
                    if (isFicheiro) {
                        destino = new File(pathWorkspace + File.separator + file.getName());
                        // Cria uma pasta no workspace com o mesmo nome do arquivo a ser copiado
                        destino.mkdir();
                    } else {
                        // Copia o arquivo para o ficheiro que esta selecionado
                        if (viewPrincipal.getTabelaFicheiros().getSelectedRows().length == 1) {
                            destino = new File(pathWorkspace + File.separator
                                    + modeloTabelaFicheiro.getLinhaTabela(
                                    viewPrincipal.getTabelaFicheiros().getSelectedRow()).get(0)
                                    + File.separator + file.getName());
                        }
                    }
                    if (isFicheiro) {
                        // Perde portabilidade, tratar isso mais a frente
                        Runtime.getRuntime().exec("cmd /c xcopy /E "
                                + "\"" + file + "\"" + " " + "\"" + destino + "\"");
                    } else {
                        if (!destino.isDirectory()) {
                            File destiny = new File(pathWorkspace + File.separator
                                    + modeloTabelaFicheiro.getLinhaTabela(viewPrincipal.
                                    getTabelaFicheiros().getSelectedRow()).get(0));
                            Runtime.getRuntime().exec("cmd /c xcopy "
                                    + "\"" + file + "\"" + " " + "\"" + destiny + "\"");
                        } else {
                            File ficheiro = new File(destino + File.separator + file.getName());
                            // Cria uma pasta no workspace com o mesmo nome do arquivo a ser copiado
                            ficheiro.mkdir();
                            Runtime.getRuntime().exec("cmd /c xcopy /E "
                                    + "\"" + file + "\"" + " " + "\"" + ficheiro + "\"");
                        }
                    }
                    //Atualiza tabela para exibir novo ficheiro
                    modeloTabelaDocumento.limparTabela();   // Retira todos os registros da tabela de documentos
                    modeloTabelaFicheiro.limparTabela();    // Retira todos os registros da tabela de ficheiros
                    carregaFicheiros();                     // recarrega ficheiros
                    modeloTabelaDocumento.fireTableDataChanged();
                    modeloTabelaFicheiro.fireTableDataChanged();
                } catch (Exception ex) {
                    javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                            "Problemas na importação de arquivos" + ex);
                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(null,
                        "Você deve primeiro selecionar um ficheiro para importar arquivos.");
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "Ação cancelada pelo usuário.");
        }
    }

    private void exportar(boolean isFicheiro) {
        if (viewPrincipal.getTabelaFicheiros().getSelectedRow() >= 0) {
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnVal = fc.showSaveDialog(viewPrincipal);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                try {
                    File destino = null;
                    if (isFicheiro) {
                        destino = new File(file + File.separator
                                + conteudoLinhaSelecionadaTabelaFicheiro.obtemElementoLinha(0, 0));
                        destino.mkdir();
                    } else {
                        destino = new File(file.getPath());
                    }
                    if (isFicheiro) {
                        // Perde portabilidade, tratar isso mais a frente
                        Runtime.getRuntime().exec("cmd /c xcopy /E "
                                + "\"" + pathWorkspace + File.separator
                                + conteudoLinhaSelecionadaTabelaFicheiro.obtemElementoLinha(0, 0) // linhaSelecionada.get(0) -> nome
                                + "\"" + " " + "\"" + destino + "\"");
                    } else {
                        Runtime.getRuntime().exec("cmd /c xcopy "
                                + "\"" + pathWorkspace + File.separator
                                + conteudoLinhaSelecionadaTabelaFicheiro.obtemElementoLinha(0, 0)
                                + File.separator
                                + conteudoLinhaSelecionadaTabelaDocumento.obtemElementoLinha(0, 0) // linhaSelecionada.get(0) -> nome
                                + "\"" + " " + "\"" + destino + "\"");
                    }
                } catch (Exception ex) {
                    javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                            "Problemas na exportação de arquivos" + ex);
                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Ação cancelada pelo usuário.");
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                    "Selecione antes o ficheiro que deseja exportar.");
        }
    }

    private void adicionarDocumento() {
        if (ficheirosSelecionados != null && ficheirosSelecionados.tamanho == 1) {  // se houver exatamente 1 ficheiro selecionado
            String idArquivo = javax.swing.JOptionPane.showInputDialog(viewPrincipal,
                    "Digite o nome: ", "Questão", JOptionPane.INFORMATION_MESSAGE);
            // verifica se ja ha um arquivo com o mesmo nome e da mesma extensao
            File ficheiroSelecionado = new File(ficheirosSelecionados.obtemElementoLinha(0, 5));
            String[] conteudo = ficheiroSelecionado.list();
            if (conteudo != null && conteudo.length > 0) {  // se a pasta nao esta vazia, pode haver arquivos iguais
                for (int i = 0; i < conteudo.length; i++) {
                    if (conteudo[i].equals(idArquivo)) {
                        javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                                "Já existe um documento com esse identificador, por favor escolha outro.");
                        adicionarDocumento();
                    } else {
                        if (i == (conteudo.length - 1)) {
                            if (idArquivo != null && !idArquivo.equals("")) {
                                if (idArquivo.equalsIgnoreCase("")) {
                                    javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                                            "Por favor insira um identificador válido.");
                                    adicionarDocumento();
                                } else {
                                    criarDocumento(idArquivo);
                                    javax.swing.JOptionPane.showMessageDialog(viewPrincipal, "Documento criado com sucesso!");
                                }
                            } else {
                                javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                                        "Ação cancelada pelo usuário.");
                            }
                        }
                    }
                }
            } else {    // pasta vazia, nao tem como ter arquivo iguais
                if (idArquivo != null && !idArquivo.equals("")) {
                    if (idArquivo.equalsIgnoreCase("")) {
                        javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                                "Por favor insira um identificador válido.");

                        adicionarDocumento();
                    } else {
                        criarDocumento(idArquivo);
                    }
                } else {
                    javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                            "Ação cancelada pelo usuário.");
                }
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                    "Selecione antes o ficheiro o qual deseja adicionar documentos.");
        }
    }

    private void setaInformacaoPainelFicheiros(String nome, String dtCriacao,
            String modificadoEm, String tamanho, String autor) {
        viewPrincipal.setLblInfoFicheiroNomeDireita(nome);
        viewPrincipal.setLblInfoFicheiroDataCriacaoDireita(dtCriacao);
        viewPrincipal.setLblInfoFicheiroModificadoEmDireita(modificadoEm);
        viewPrincipal.setLblInfoFicheiroTamanhoDireita(tamanho);
        viewPrincipal.setLblInfoFicheiroAutorDireita(autor);
    }

    private void setaInformacaoPainelDocumentos(String nome, String dtCriacao,
            String modificadoEm, String tamanho, String autor, String tipo) {
        viewPrincipal.setLblInfoDocumentosNomeDireita(nome);
        viewPrincipal.setLblInfoDocumentosDataCriacaoDireita(dtCriacao);
        viewPrincipal.setLblInfoDocumentosModificadoEmDireita(modificadoEm);
        viewPrincipal.setLblInfoDocumentosTamanhoDireita(tamanho);
        viewPrincipal.setLblInfoDocumentosAutorDireita(autor);
        viewPrincipal.setLblInfoDocumentosTipoDireita(tipo);
    }

    private void removerDocumento() {
        if (viewPrincipal.getTabelaDocumentos().getSelectedRows().length == 1) {
            if (mensagemDeConfirmacaoRemover(false) == JOptionPane.YES_OPTION) {
                if (pathsSelecionadosTabDocumentos.get(0).isDirectory()) {     // Se for um diretoria tem que esvazia-lo antes de apagar
                    deleteDir(pathsSelecionadosTabDocumentos.get(0));
                } else {
                    pathsSelecionadosTabDocumentos.get(0).delete();    // ta retornando false, mas deleta =\
                    javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                            "Documento removido com sucesso.");
                    modeloTabelaDocumento.removeLinha(
                            viewPrincipal.getTabelaDocumentos().getSelectedRow());
                    modeloTabelaDocumento.fireTableDataChanged();
                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(viewPrincipal, "Ação cancelada pelo usuário.");
                return;
            }
        } else if (viewPrincipal.getTabelaDocumentos().getSelectedRows().length > 1) {
            javax.swing.JOptionPane.showMessageDialog(viewPrincipal, "Você está prestes a deletar vários arquivos "
                    + "isso pode implicar em perda de grande quantidade de dados.");
            if (mensagemDeConfirmacaoRemover(false) == JOptionPane.YES_OPTION) {
                for (int i = 0; i < pathsSelecionadosTabDocumentos.size(); i++) {
                    if (pathsSelecionadosTabDocumentos.get(i).isDirectory()) {     // Se for um diretoria tem que esvazia-lo antes de apagar
                        deleteDir(pathsSelecionadosTabDocumentos.get(i));
                    } else {
                        pathsSelecionadosTabDocumentos.get(i).delete();    // ta retornando false, mas deleta =\
                        modeloTabelaDocumento.removeLinha(
                                viewPrincipal.getTabelaDocumentos().getSelectedRow());
                    }
                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(viewPrincipal, "Ação cancelada pelo usuário.");
                return;
            }
            modeloTabelaDocumento.fireTableDataChanged();
            javax.swing.JOptionPane.showMessageDialog(viewPrincipal, "Arquivos removido com sucesso.");
        } else {
            javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                    "Selecione antes o documento que deseja excluir.");
        }
    }

    private void criarDocumento(String idArquivo) {
        File arquivo = new File(ficheirosSelecionados.obtemElementoLinha(0, 5).replace(";", "").trim() + File.separator + idArquivo);
        //File arquivo = new File("C:\\Users\\Desenv_03\\RepositorioDeFicheiros\\ficheiro1\\arquivo0ficheiro1.txt");
        try {
            arquivo.createNewFile();
            Date dataCriacao = new Date(arquivo.lastModified());
            DateFormat sdfInterface = new SimpleDateFormat("HH:mm:ss, dd/MM/yyyy");
            DateFormat sdfInfo = new SimpleDateFormat("HH:mm:ss| dd/MM/yyyy");
            String lastModifiedInterface = sdfInterface.format(dataCriacao);
            String lastModifiedInfo = sdfInfo.format(dataCriacao);
            addLinhaTabelaDocumento(idArquivo, lastModifiedInterface,
                    lastModifiedInterface, String.valueOf(arquivo.length()),
                    "Dr. Fulano", "arquivo de texto");
            // Adiciona informacao no DAO
            DAOInfoArquivosTabela dao = new DAOInfoArquivosTabela(pathWorkspace);
            dao.gravaInfoArquivo(idArquivo, lastModifiedInfo, lastModifiedInfo,
                    String.valueOf(arquivo.length() / 1024), "Dr. Fulano",
                    "arquivo de texto", arquivo.getPath());
            // Adiciona informacao ao arquivo de acesso rapido
            // grava informacoes no arquivo de aceesso rapido
            ArrayList<String> linha = new ArrayList<String>();
            linha.add(idArquivo);
            linha.add(lastModifiedInfo);
            linha.add(lastModifiedInfo);
            linha.add(String.valueOf(arquivo.length() / 1024) + " KB");
            linha.add("Dr. Fulano");
            linha.add("arquivo de texto");
            linha.add(arquivo.getPath());
            cashArquivos.adicionaLinha(linha);
            modeloTabelaDocumento.fireTableDataChanged();
            javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                    "Documento criado com sucesso.");
        } catch (IOException ex) {
            javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                    "Problemas na criação do documento." + ex);
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // Agora o diretório está vazio, restando apenas deletá-lo
        return dir.delete();
    }

    public static int getFolderSize(String path) {
        File folder = new File(path);
        int size = 0;
        if (folder.isDirectory()) {
            String[] dirList = folder.list();
            if (dirList != null) {
                for (int i = 0; i < dirList.length; i++) {
                    String fileName = dirList[i];
                    File f = new File(path, fileName);
                    if (f.isDirectory()) {
                        String filePath = f.getPath();
                        size += getFolderSize(filePath);
                        continue;
                    }
                    size += f.length();
                }
            }
        }
        return size;
    }

    private void adicionarFicheiro() {
        // Esse Metodo deve adicionar uma pasta no workspace do usuario
        String idFicheiro = javax.swing.JOptionPane.showInputDialog(viewPrincipal,
                "Entre com um identificador para o ficheiro:", "Adiciona Ficheiro", MIN_PRIORITY);
        File pastaUsuario = new File(pathWorkspace + File.separator + idFicheiro);
        try {
            if (idFicheiro != null) {
                if (!pastaUsuario.exists()) {
                    pastaUsuario.mkdir();
                } else if (idFicheiro.equals("")) {
                    javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                            "Entre com um identificador válido !");
                    adicionarFicheiro();
                } else {
                    javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                            "Já existe um ficheiro com esse nome !");
                    return;
                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                        "Ação cancelada pelo usuário !");
                return;
            }
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                    "Houve um problema no momento de criação do ficheiro" + ex);
        }
        javax.swing.JOptionPane.showMessageDialog(viewPrincipal,
                "Diretório criado com sucesso !");
        // Captura data para adicionar ao registro [isso eh conteudo para outro metodo]
        Date dataCriacao = new Date(pastaUsuario.lastModified());
        DateFormat sdfInterface = new SimpleDateFormat("HH:mm:ss, dd/MM/yyyy");
        DateFormat sdfInfo = new SimpleDateFormat("HH:mm:ss | dd/MM/yyyy");
        String lastModifiedInterface = sdfInterface.format(dataCriacao);
        String lastModifiedInfo = sdfInfo.format(dataCriacao);
        addLinhaTabelaFicheiro(idFicheiro, lastModifiedInterface, lastModifiedInterface,
                String.valueOf(pastaUsuario.length()) + " bytes", "Dr. Fulano");
        // Grava informacoes no arquivo de texto
        DAOInfoArquivosTabela dao = new DAOInfoArquivosTabela(pathWorkspace);
        dao.gravaInfoFicheiro(idFicheiro, lastModifiedInfo, lastModifiedInfo,
                String.valueOf(pastaUsuario.length()) + " bytes", "Dr. Fulano",
                pastaUsuario.getPath());
        // grava informacoes no arquivo de acesso rapido
        ArrayList<String> linha = new ArrayList<String>();
        linha.add(idFicheiro);
        linha.add(lastModifiedInfo);
        linha.add(lastModifiedInfo);
        linha.add(String.valueOf(pastaUsuario.length() / 1024) + " KB");
        linha.add("Dr. Fulano");
        linha.add(pastaUsuario.getPath());
        cashFicheiros.adicionaLinha(linha);
    }

    private ArrayList<String> getCabecalhoTabelaFicheiro() {
        if (cabecalhoTabelaFicheiro == null) {
            cabecalhoTabelaFicheiro = new ArrayList<String>();
        }
        return cabecalhoTabelaFicheiro;
    }

    private void addLinhaTabelaFicheiro(String nome, String dtCriacao, String dtModif,
            String tamanho, String autor) {
        linhaTabelaFicheiro = new ArrayList<String>();
        linhaTabelaFicheiro.add(nome);
        linhaTabelaFicheiro.add(dtCriacao);
        linhaTabelaFicheiro.add(dtModif);
        linhaTabelaFicheiro.add(tamanho);
        linhaTabelaFicheiro.add(autor);
        modeloTabelaFicheiro.adicionaLinhaTabela(linhaTabelaFicheiro);
    }

    private ModeloTabela modelTabFicheiro() {
        getCabecalhoTabelaFicheiro().add("Nome");
        getCabecalhoTabelaFicheiro().add("Data Criação");
        getCabecalhoTabelaFicheiro().add("Data Modificação");
        getCabecalhoTabelaFicheiro().add("Tamanho");
        getCabecalhoTabelaFicheiro().add("Autor");
        modeloTabelaFicheiro = new ModeloTabela(getCabecalhoTabelaFicheiro());
        return modeloTabelaFicheiro;
    }

    private void carregaFicheiros() {
        String[] ficheirosWorkspace = workspace.list();
        for (int i = 0; i < ficheirosWorkspace.length; i++) {
            File arquivoTemp = new File(pathWorkspace + File.separator + ficheirosWorkspace[i]);
            DateFormat sdf = new SimpleDateFormat("HH:mm:ss, dd/MM/yyyy");
            if (!ficheirosWorkspace[i].contains(".")) {
                addLinhaTabelaFicheiro(arquivoTemp.getName(), "",
                        sdf.format(new Date(arquivoTemp.lastModified())),
                        String.valueOf(arquivoTemp.length() / 1024) + " KB", "Dr. Fulano");
            }
        }
    }

    private ArrayList<String> getCabecalhoDocumento() {
        if (cabecalhoTabelaDocumento == null) {
            cabecalhoTabelaDocumento = new ArrayList<String>();
        }
        return cabecalhoTabelaDocumento;
    }

    private ModeloTabela modelTabDocumento() {
        getCabecalhoDocumento().add("Nome");
        getCabecalhoDocumento().add("Data Criação");
        getCabecalhoDocumento().add("Data Modificação");
        getCabecalhoDocumento().add("Tamanho");
        getCabecalhoDocumento().add("Autor");
        getCabecalhoDocumento().add("Tipo");
        modeloTabelaDocumento = new ModeloTabela(getCabecalhoDocumento());
        return modeloTabelaDocumento;
    }

    private void addLinhaTabelaDocumento(String nome, String dtCriacao, String dtModif,
            String tamanho, String autor, String tipo) {
        linhaTabelaDocumento = new ArrayList<String>();
        linhaTabelaDocumento.add(nome);
        linhaTabelaDocumento.add(dtCriacao);
        linhaTabelaDocumento.add(dtModif);
        linhaTabelaDocumento.add(tamanho);
        linhaTabelaDocumento.add(autor);
        linhaTabelaDocumento.add(tipo);
        modeloTabelaDocumento.adicionaLinhaTabela(linhaTabelaDocumento);
    }

    private void addAtalhos() {
        //Atalho <ESC>
        viewPrincipal.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancelarAction");
        viewPrincipal.getRootPane().getActionMap().put("cancelarAction", new AbstractAction("cancelarAction") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
        // Ctrl+Shift+A para adicionar um ficheiro, Ctrl+A ja eh do SO
        viewPrincipal.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_A,
                InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK), "ctrlShiftA");
        viewPrincipal.getRootPane().getActionMap().put("ctrlShiftA", new AbstractAction("ctrlShiftA") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                adicionarFicheiro();
            }
        });
        // Ctrl+D para deletar um ficheiro
        viewPrincipal.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK), "ctrlD");
        viewPrincipal.getRootPane().getActionMap().put("ctrlD", new AbstractAction("ctrlD") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                removerFicheiro();
            }
        });
        // Ctrl+R para renomear um ficheiro
        viewPrincipal.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK), "ctrlR");
        viewPrincipal.getRootPane().getActionMap().put("ctrlR", new AbstractAction("ctrlR") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                renomearFicheiro();
            }
        });
        // Ctrl+I para importar um ficheiro
        viewPrincipal.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK), "ctrlI");
        viewPrincipal.getRootPane().getActionMap().put("ctrlI", new AbstractAction("ctrlI") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                importar(true);
            }
        });
        // Ctrl+E para exportar um ficheiro
        viewPrincipal.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK), "ctrlE");
        viewPrincipal.getRootPane().getActionMap().put("ctrlE", new AbstractAction("ctrlE") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                exportar(true);
            }
        });
        // Alt+C para comparar ficheiros
        viewPrincipal.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_MASK), "altC");
        viewPrincipal.getRootPane().getActionMap().put("altC", new AbstractAction("altC") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                compararFicheiros();
            }
        });
        // Ctrl+Shift+C para classificar ficheiros
        /*viewPrincipal.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
         KeyStroke.getKeyStroke(KeyEvent.VK_C,
         InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK), "ctrlShiftC");
         viewPrincipal.getRootPane().getActionMap().put("ctrlShiftC", new AbstractAction("ctrlShiftC") {
         @Override
         public void actionPerformed(ActionEvent ae) {
         javax.swing.JOptionPane.showMessageDialog(viewPrincipal, "Classificar ficheiros");
         }
         });*/
        //Ctrl+Alt+A para criar um documento
        viewPrincipal.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_A,
                InputEvent.CTRL_MASK | InputEvent.ALT_MASK), "ctrlAltA");
        viewPrincipal.getRootPane().getActionMap().put("ctrlAltA", new AbstractAction("ctrlAltA") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                adicionarDocumento();
            }
        });
        //Ctrl+Alt+D para deletar um documento
        viewPrincipal.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_D,
                InputEvent.CTRL_MASK | InputEvent.ALT_MASK), "ctrlAltD");
        viewPrincipal.getRootPane().getActionMap().put("ctrlAltD", new AbstractAction("ctrlAltD") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                removerDocumento();
            }
        });
        //Ctrl+Alt+R para renomear um documento
        viewPrincipal.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_R,
                InputEvent.CTRL_MASK | InputEvent.ALT_MASK), "ctrlAltR");
        viewPrincipal.getRootPane().getActionMap().put("ctrlAltR", new AbstractAction("ctrlAltR") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                renomearDocumento();
            }
        });
        //Ctrl+Alt+I para Importar um documento
        viewPrincipal.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_I,
                InputEvent.CTRL_MASK | InputEvent.ALT_MASK), "ctrlAltI");
        viewPrincipal.getRootPane().getActionMap().put("ctrlAltI", new AbstractAction("ctrlAltI") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                importar(false);
            }
        });
        //Ctrl+Alt+E para Exportar um documento
        viewPrincipal.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_E,
                InputEvent.CTRL_MASK | InputEvent.ALT_MASK), "ctrlAltE");
        viewPrincipal.getRootPane().getActionMap().put("ctrlAltE", new AbstractAction("ctrlAltE") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                exportar(false);
            }
        });
        //Ctrl+Alt+C para Comparar documentos
        viewPrincipal.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_C,
                InputEvent.CTRL_MASK | InputEvent.ALT_MASK), "ctrlAltC");
        viewPrincipal.getRootPane().getActionMap().put("ctrlAltC", new AbstractAction("ctrlAltC") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                compararDocumentos();
            }
        });
        // para classificar documentos
    }

    public static void main(String[] args) {
        CTViewPrincipal ctvp = new CTViewPrincipal();
        ctvp.carregaArquivosDeInformacao();
    }
}

/*          Observações importantes 
 * -O cara, na importacao de ficheiro, pode escolher importar um ficheiro
 * para dentro de outro, tratar isso; resp: a ideia do aplicativo é gerenciar
 * múltiplos ficheiros de ARQUIVOS de clientes diferentes de advogados, a priori
 * não haverá necessidade (até que me provem o contrário de adicionar um ficheiro 
 * dentr do outro, uma vez que os ficheiros deve estar bem modularizados). [Questão]
 * 
 * -Tratar tambem um ficheiro dentro do outro, como resolver esse problema ??; [reposta na questão anterior]
 * 
 * -Implementar a view que escolhe o tipo de arquivo a ser criado. 
 * (Arquivo do world, bloco de notas, apresentação PowerPoint, Planilha Excel) [extremamente necessário !!]
 * 
 * - Na comparação de arquivos, não foi tratado o caso de haver dois arquivos com o 
 * mesmo nome em pastas diferentes. Como ,na implementação, procuro o path do arquivo 
 * dentro do cash de paths, se houver dois arquivos do mesmo nome, a busca vai retornar
 * dois paths diferentes e válidos, tratar isso !
 * 
 * - Abrir dois arquivos ao mesmo tempo não ta funcionando muito bem, ainda; [urgente]
 */

/*
 * tem que mudar a lógica do programa. Toda vez que o cara clica em uma linha
 * da tabela de ficheiros, executa um algoritmo que carrega todas as linhas que foram
 * selecionadas SEMPRE que isso acontece. Isso tem uma complexidade muito ruim.
 * Uma alternativa eh, deixar a demora somente para quando iniciar o programa, em outras
 * palavras, quando o programa inicia deve recorrer para os arquivos de informações
 * e carregar uma MatrizDinamica2 com todos os arquivos e outra com todos os ficheiro e
 * ordená-los para aplicar
 * 
 */
