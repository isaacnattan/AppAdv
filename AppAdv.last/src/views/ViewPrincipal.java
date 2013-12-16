package views;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.border.TitledBorder;
import util.ModeloTabela;

/**
 * @author Isaac_Nattan
 */
public class ViewPrincipal extends JFrame {

    private JPanel pnlInformacoes;
    private JPanel pnlDocumentos;
    private Container container;
    private JPanel pnlInfoDesenvs;
    private JPanel pnlInfoFicheiros;
    private JPanel painelSuperiorFicheiro;
    private JLabel lblOrganizacaoFicheiros;
    private JLabel lblOrganizacaoDocumentos;
    //private JLabel lblOrdenarPor;
    private JComboBox jComboOrdenarPor;
    private JPanel painelFicheiro;
    private JButton btAdiocionarFicheiro;
    private JButton btRemoverFicheiro;
    private JButton btRenomearFicheiro;
    private JButton btImportarFicheiro;
    private JButton btExportarFicheiro;
    private JButton btClassificarFicheiro;
    private JButton btCompararFicheiro;
    private JPanel painelSupInternoDocs;
    //private JLabel lblDocsOrdenarPor;
    private JComboBox jComboDocsOrdenarPor;
    private JPanel painelInternoDocs;
    private JButton btAdiocionarDocumentos;
    private JButton btRemoverDocumentos;
    private JButton btRenomearDocumentos;
    private JButton btImportarDocumentos;
    private JButton btExportarDocumentos;
    private JButton btClassificarDocumentos;
    private JButton btCompararDocumentos;
    private JButton btEditarDocumentos;
    private static JTable tabelaFicheiros;
    private JTable tabelaDocumentos;
    private JPanel pnlInfoDocumentos;
    // Labels esquerda pnlInfoFicheiro
    private JLabel lblInfoFicheiroNome;
    private JLabel lblInfoFicheiroDataCriacao;
    private JLabel lblInfoFicheiroModificadoEm;
    private JLabel lblInfoFicheiroTamanho;
    private JLabel lblInfoFicheirosAutor;
    // Labels direita pnlInfoFicheiro
    private JLabel lblInfoFicheiroNomeDireita;
    private JLabel lblInfoFicheiroDataCriacaoDireita;
    private JLabel lblInfoFicheiroModificadoEmDireita;
    private JLabel lblInfoFicheiroTamanhoDireita;
    private JLabel lblInfoFicheiroAutorDireita;
    // Labels esquerda pnlInfoDocumentos
    private JLabel lblInfoDocumentosNome;
    private JLabel lblInfoDocumentosDataCriacao;
    private JLabel lblInfoDocumentosModificadoEm;
    private JLabel lblInfoDocumentosTamanho;
    private JLabel lblInfoDocumentosAutor;
    private JLabel lblInfoDocumentosTipo;
    // Labels direita pnlInfoDocumentos
    private JLabel lblInfoDocumentosNomeDireita;
    private JLabel lblInfoDocumentosDataCriacaoDireita;
    private JLabel lblInfoDocumentosModificadoEmDireita;
    private JLabel lblInfoDocumentosTamanhoDireita;
    private JLabel lblInfoDocumentosAutorDireita;
    private JLabel lblInfoDocumentosTipoDireita;
    // Informações painelInfoDesenvs
    private JLabel IsaacNattan;
    private JLabel emailIsaac;
    private JLabel facebookIsaac;
    private JLabel telefoneIsaac;
    private JLabel JoaoPedro;
    private JLabel Alisson;
    // Modelos das tabelas
    private ModeloTabela tabFicheiro;
    private ModeloTabela tabDocs;
    // Fonte utilizada
    Font fonteNegrito = new Font("Arial", Font.BOLD, 15);

    public ViewPrincipal(ModeloTabela tabFicheiro, ModeloTabela tabDocs) {
        super("Aplicativo Advogado");
        this.tabFicheiro = tabFicheiro;
        this.tabDocs = tabDocs;
        setLayout(new GridBagLayout());
        pnlInformacoes = new JPanel(new GridBagLayout());
        pnlDocumentos = new JPanel(new GridBagLayout());
        montarGUI();
    }

    private void montarGUI() {
        try {
            container = getContentPane();
            GridBagConstraints constraints = new GridBagConstraints();
            pnlInformacoes.setBorder(BorderFactory.createTitledBorder(" Informações "));
            // x eh linha e y eh coluna
            constraints.fill = GridBagConstraints.BOTH;
            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.weightx = 0.5;
            constraints.weighty = 1;
            constraints.insets = new Insets(5, 5, 5, 5);
            container.add(pnlInformacoes, constraints);
            GridBagConstraints constraints2 = new GridBagConstraints();
            pnlDocumentos.setBorder(BorderFactory.createTitledBorder(" Documentos "));
            constraints2.fill = GridBagConstraints.BOTH;
            constraints2.gridx = 1;
            constraints2.gridy = 0;
            constraints2.weightx = 0.5;
            constraints2.weighty = 1;
            constraints2.insets = new Insets(5, 0, 5, 5);
            container.add(pnlDocumentos, constraints2);
            montarPainelInformacoes();  // Montar painel mais externo a esquerda
            montarPainelDocs();         // Montar painel mais externo a direita
            pack();
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(this, ex);
        }
    }

    private void montarPainelInformacoes() {
        try {
            GridBagConstraints constPnlInfo = new GridBagConstraints();
            pnlInfoDesenvs = new JPanel();
            TitledBorder tituloPainelDesenvs = BorderFactory.createTitledBorder(" Desenvolvedores ");
            tituloPainelDesenvs.setTitleFont(fonteNegrito);
            pnlInfoDesenvs.setBorder(tituloPainelDesenvs);
            constPnlInfo.fill = GridBagConstraints.BOTH;
            constPnlInfo.gridx = 0;
            constPnlInfo.gridy = 0;
            constPnlInfo.weightx = 1;
            constPnlInfo.weighty = 0.5;
            constPnlInfo.insets = new Insets(5, 5, 5, 5);
            pnlInformacoes.add(pnlInfoDesenvs, constPnlInfo);
            pnlInfoFicheiros = new JPanel(new GridBagLayout());
            TitledBorder tituloPainelInfoFicheiro = BorderFactory.createTitledBorder(" Ficheiros ");
            tituloPainelInfoFicheiro.setTitleFont(fonteNegrito);
            pnlInfoFicheiros.setBorder(tituloPainelInfoFicheiro);
            constPnlInfo.fill = GridBagConstraints.BOTH;
            constPnlInfo.gridx = 0;
            constPnlInfo.gridy = 1;
            constPnlInfo.weightx = 1;
            constPnlInfo.weighty = 0.5;
            constPnlInfo.insets = new Insets(0, 5, 5, 5);
            pnlInformacoes.add(pnlInfoFicheiros, constPnlInfo);
            pnlInfoDocumentos = new JPanel();
            TitledBorder tituloPainelInfoDocumentos = BorderFactory.createTitledBorder(" Documentos ");
            tituloPainelInfoDocumentos.setTitleFont(fonteNegrito);
            pnlInfoDocumentos.setBorder(tituloPainelInfoDocumentos);
            constPnlInfo.fill = GridBagConstraints.BOTH;
            constPnlInfo.gridx = 0;
            constPnlInfo.gridy = 2;
            constPnlInfo.weightx = 1;
            constPnlInfo.weighty = 0.5;
            constPnlInfo.insets = new Insets(0, 5, 5, 5);
            pnlInformacoes.add(pnlInfoDocumentos, constPnlInfo);
            pack();
            montarPainelInfoFicheiro();
            montarPainelInfoDocumentos();
            montarPainelDesenvolvedores();
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(this, ex);
        }
    }
    
    private void montarPainelDesenvolvedores(){
        SpringLayout layoutDesenvs = new SpringLayout();
        pnlInfoDesenvs.setLayout(layoutDesenvs);
        IsaacNattan = new JLabel("Isaac Nattan (Programador) ");
        IsaacNattan.setFont(fonteNegrito);
        pnlInfoDesenvs.add(IsaacNattan);
        layoutDesenvs.putConstraint(SpringLayout.WEST, IsaacNattan,
                6, SpringLayout.WEST, pnlInfoDesenvs);
        layoutDesenvs.putConstraint(SpringLayout.NORTH, IsaacNattan,
                10, SpringLayout.NORTH, pnlInfoDesenvs);
        emailIsaac = new JLabel("    e-mail: isaacnattan2@gmail.com");
        emailIsaac.setFont(fonteNegrito);
        pnlInfoDesenvs.add(emailIsaac);
        layoutDesenvs.putConstraint(SpringLayout.WEST, emailIsaac,
                6, SpringLayout.WEST, pnlInfoDesenvs);
        layoutDesenvs.putConstraint(SpringLayout.NORTH, emailIsaac,
                30, SpringLayout.NORTH, pnlInfoDesenvs);
        facebookIsaac = new JLabel("    facebook: Isaac Nattan");
        facebookIsaac.setFont(fonteNegrito);
        pnlInfoDesenvs.add(facebookIsaac);
        layoutDesenvs.putConstraint(SpringLayout.WEST, facebookIsaac,
                6, SpringLayout.WEST, pnlInfoDesenvs);
        layoutDesenvs.putConstraint(SpringLayout.NORTH, facebookIsaac,
                50, SpringLayout.NORTH, pnlInfoDesenvs);
        telefoneIsaac = new JLabel("    telefone: (79) 8122-4253");
        telefoneIsaac.setFont(fonteNegrito);
        pnlInfoDesenvs.add(telefoneIsaac);
        layoutDesenvs.putConstraint(SpringLayout.WEST, telefoneIsaac,
                6, SpringLayout.WEST, pnlInfoDesenvs);
        layoutDesenvs.putConstraint(SpringLayout.NORTH, telefoneIsaac,
                70, SpringLayout.NORTH, pnlInfoDesenvs);
        JoaoPedro = new JLabel("João Pedro (Programador) ");
        JoaoPedro.setFont(fonteNegrito);
        pnlInfoDesenvs.add(JoaoPedro);
        layoutDesenvs.putConstraint(SpringLayout.WEST, JoaoPedro,
                6, SpringLayout.WEST, pnlInfoDesenvs);
        layoutDesenvs.putConstraint(SpringLayout.NORTH, JoaoPedro,
                90, SpringLayout.NORTH, pnlInfoDesenvs);
        Alisson = new JLabel("Alisson Aragão (Design) ");
        Alisson.setFont(fonteNegrito);
        pnlInfoDesenvs.add(Alisson);
        layoutDesenvs.putConstraint(SpringLayout.WEST, Alisson,
                6, SpringLayout.WEST, pnlInfoDesenvs);
        layoutDesenvs.putConstraint(SpringLayout.NORTH, Alisson,
                110, SpringLayout.NORTH, pnlInfoDesenvs);
        
    }

    private void montarPainelInfoFicheiro() {
        // Informações a esquerda do painel
        SpringLayout layoutPnlInfoFicheiroEsquerda = new SpringLayout();
        pnlInfoFicheiros.setLayout(layoutPnlInfoFicheiroEsquerda);
        lblInfoFicheiroNome = new JLabel("Nome: ");
        lblInfoFicheiroNome.setFont(fonteNegrito);
        pnlInfoFicheiros.add(lblInfoFicheiroNome);
        layoutPnlInfoFicheiroEsquerda.putConstraint(SpringLayout.WEST, lblInfoFicheiroNome,
                6, SpringLayout.WEST, pnlInfoFicheiros);
        layoutPnlInfoFicheiroEsquerda.putConstraint(SpringLayout.NORTH, lblInfoFicheiroNome,
                10, SpringLayout.NORTH, pnlInfoFicheiros);
        lblInfoFicheiroDataCriacao = new JLabel("Data Criação: ");
        lblInfoFicheiroDataCriacao.setFont(fonteNegrito);
        pnlInfoFicheiros.add(lblInfoFicheiroDataCriacao);
        layoutPnlInfoFicheiroEsquerda.putConstraint(SpringLayout.WEST, lblInfoFicheiroDataCriacao,
                6, SpringLayout.WEST, pnlInfoFicheiros);
        layoutPnlInfoFicheiroEsquerda.putConstraint(SpringLayout.NORTH, lblInfoFicheiroDataCriacao,
                30, SpringLayout.NORTH, pnlInfoFicheiros);
        lblInfoFicheiroModificadoEm = new JLabel("Modificado em: ");
        lblInfoFicheiroModificadoEm.setFont(fonteNegrito);
        pnlInfoFicheiros.add(lblInfoFicheiroModificadoEm);
        layoutPnlInfoFicheiroEsquerda.putConstraint(SpringLayout.WEST, lblInfoFicheiroModificadoEm,
                6, SpringLayout.WEST, pnlInfoFicheiros);
        layoutPnlInfoFicheiroEsquerda.putConstraint(SpringLayout.NORTH, lblInfoFicheiroModificadoEm,
                50, SpringLayout.NORTH, pnlInfoFicheiros);
        lblInfoFicheiroTamanho = new JLabel("Tamanho: ");
        lblInfoFicheiroTamanho.setFont(fonteNegrito);
        pnlInfoFicheiros.add(lblInfoFicheiroTamanho);
        layoutPnlInfoFicheiroEsquerda.putConstraint(SpringLayout.WEST, lblInfoFicheiroTamanho,
                6, SpringLayout.WEST, pnlInfoFicheiros);
        layoutPnlInfoFicheiroEsquerda.putConstraint(SpringLayout.NORTH, lblInfoFicheiroTamanho,
                70, SpringLayout.NORTH, pnlInfoFicheiros);
        lblInfoFicheirosAutor = new JLabel("Autor: ");
        lblInfoFicheirosAutor.setFont(fonteNegrito);
        pnlInfoFicheiros.add(lblInfoFicheirosAutor);
        layoutPnlInfoFicheiroEsquerda.putConstraint(SpringLayout.WEST, lblInfoFicheirosAutor,
                6, SpringLayout.WEST, pnlInfoFicheiros);
        layoutPnlInfoFicheiroEsquerda.putConstraint(SpringLayout.NORTH, lblInfoFicheirosAutor,
                90, SpringLayout.NORTH, pnlInfoFicheiros);
        // Informações a esquerda do painel
        lblInfoFicheiroNomeDireita = new JLabel("");
        lblInfoFicheiroNomeDireita.setFont(fonteNegrito);
        pnlInfoFicheiros.add(lblInfoFicheiroNomeDireita);
        layoutPnlInfoFicheiroEsquerda.putConstraint(SpringLayout.WEST, lblInfoFicheiroNomeDireita,
                140, SpringLayout.WEST, pnlInfoFicheiros);
        layoutPnlInfoFicheiroEsquerda.putConstraint(SpringLayout.NORTH, lblInfoFicheiroNomeDireita,
                10, SpringLayout.NORTH, pnlInfoFicheiros);
        lblInfoFicheiroDataCriacaoDireita = new JLabel("");
        lblInfoFicheiroDataCriacaoDireita.setFont(fonteNegrito);
        pnlInfoFicheiros.add(lblInfoFicheiroDataCriacaoDireita);
        layoutPnlInfoFicheiroEsquerda.putConstraint(SpringLayout.WEST, lblInfoFicheiroDataCriacaoDireita,
                140, SpringLayout.WEST, pnlInfoFicheiros);
        layoutPnlInfoFicheiroEsquerda.putConstraint(SpringLayout.NORTH, lblInfoFicheiroDataCriacaoDireita,
                30, SpringLayout.NORTH, pnlInfoFicheiros);
        lblInfoFicheiroModificadoEmDireita = new JLabel("");
        lblInfoFicheiroModificadoEmDireita.setFont(fonteNegrito);
        pnlInfoFicheiros.add(lblInfoFicheiroModificadoEmDireita);
        layoutPnlInfoFicheiroEsquerda.putConstraint(SpringLayout.WEST, lblInfoFicheiroModificadoEmDireita,
                140, SpringLayout.WEST, pnlInfoFicheiros);
        layoutPnlInfoFicheiroEsquerda.putConstraint(SpringLayout.NORTH, lblInfoFicheiroModificadoEmDireita,
                50, SpringLayout.NORTH, pnlInfoFicheiros);
        lblInfoFicheiroTamanhoDireita = new JLabel("");
        lblInfoFicheiroTamanhoDireita.setFont(fonteNegrito);
        pnlInfoFicheiros.add(lblInfoFicheiroTamanhoDireita);
        layoutPnlInfoFicheiroEsquerda.putConstraint(SpringLayout.WEST, lblInfoFicheiroTamanhoDireita,
                140, SpringLayout.WEST, pnlInfoFicheiros);
        layoutPnlInfoFicheiroEsquerda.putConstraint(SpringLayout.NORTH, lblInfoFicheiroTamanhoDireita,
                70, SpringLayout.NORTH, pnlInfoFicheiros);
        lblInfoFicheiroAutorDireita = new JLabel("");
        lblInfoFicheiroAutorDireita.setFont(fonteNegrito);
        pnlInfoFicheiros.add(lblInfoFicheiroAutorDireita);
        layoutPnlInfoFicheiroEsquerda.putConstraint(SpringLayout.WEST, lblInfoFicheiroAutorDireita,
                140, SpringLayout.WEST, pnlInfoFicheiros);
        layoutPnlInfoFicheiroEsquerda.putConstraint(SpringLayout.NORTH, lblInfoFicheiroAutorDireita,
                90, SpringLayout.NORTH, pnlInfoFicheiros);
    }
    
    private void montarPainelInfoDocumentos() {
        // Informações a esquerda do painel
        SpringLayout layoutPnlInfoDocumetnoEsquerda = new SpringLayout();
        pnlInfoDocumentos.setLayout(layoutPnlInfoDocumetnoEsquerda);
        lblInfoDocumentosNome = new JLabel("Nome: ");
        lblInfoDocumentosNome.setFont(fonteNegrito);
        pnlInfoDocumentos.add(lblInfoDocumentosNome);
        layoutPnlInfoDocumetnoEsquerda.putConstraint(SpringLayout.WEST, lblInfoDocumentosNome,
                6, SpringLayout.WEST, pnlInfoDocumentos);
        layoutPnlInfoDocumetnoEsquerda.putConstraint(SpringLayout.NORTH, lblInfoDocumentosNome,
                10, SpringLayout.NORTH, pnlInfoDocumentos);
        lblInfoDocumentosDataCriacao = new JLabel("Data criação: ");
        lblInfoDocumentosDataCriacao.setFont(fonteNegrito);
        pnlInfoDocumentos.add(lblInfoDocumentosDataCriacao);
        layoutPnlInfoDocumetnoEsquerda.putConstraint(SpringLayout.WEST, lblInfoDocumentosDataCriacao,
                6, SpringLayout.WEST, pnlInfoDocumentos);
        layoutPnlInfoDocumetnoEsquerda.putConstraint(SpringLayout.NORTH, lblInfoDocumentosDataCriacao,
                30, SpringLayout.NORTH, pnlInfoDocumentos);
        lblInfoDocumentosModificadoEm = new JLabel("Modificado em: ");
        lblInfoDocumentosModificadoEm.setFont(fonteNegrito);
        pnlInfoDocumentos.add(lblInfoDocumentosModificadoEm);
        layoutPnlInfoDocumetnoEsquerda.putConstraint(SpringLayout.WEST, lblInfoDocumentosModificadoEm,
                6, SpringLayout.WEST, pnlInfoDocumentos);
        layoutPnlInfoDocumetnoEsquerda.putConstraint(SpringLayout.NORTH, lblInfoDocumentosModificadoEm,
                50, SpringLayout.NORTH, pnlInfoDocumentos);
        lblInfoDocumentosTamanho = new JLabel("Tamanho: ");
        lblInfoDocumentosTamanho.setFont(fonteNegrito);
        pnlInfoDocumentos.add(lblInfoDocumentosTamanho);
        layoutPnlInfoDocumetnoEsquerda.putConstraint(SpringLayout.WEST, lblInfoDocumentosTamanho,
                6, SpringLayout.WEST, pnlInfoDocumentos);
        layoutPnlInfoDocumetnoEsquerda.putConstraint(SpringLayout.NORTH, lblInfoDocumentosTamanho,
                70, SpringLayout.NORTH, pnlInfoDocumentos);
        lblInfoDocumentosAutor = new JLabel("Autor: ");
        lblInfoDocumentosAutor.setFont(fonteNegrito);
        pnlInfoDocumentos.add(lblInfoDocumentosAutor);
        layoutPnlInfoDocumetnoEsquerda.putConstraint(SpringLayout.WEST, lblInfoDocumentosAutor,
                6, SpringLayout.WEST, pnlInfoDocumentos);
        layoutPnlInfoDocumetnoEsquerda.putConstraint(SpringLayout.NORTH, lblInfoDocumentosAutor,
                90, SpringLayout.NORTH, pnlInfoDocumentos);
        lblInfoDocumentosTipo = new JLabel("Tipo: ");
        lblInfoDocumentosTipo.setFont(fonteNegrito);
        pnlInfoDocumentos.add(lblInfoDocumentosTipo);
        layoutPnlInfoDocumetnoEsquerda.putConstraint(SpringLayout.WEST, lblInfoDocumentosTipo,
                6, SpringLayout.WEST, pnlInfoDocumentos);
        layoutPnlInfoDocumetnoEsquerda.putConstraint(SpringLayout.NORTH, lblInfoDocumentosTipo,
                110, SpringLayout.NORTH, pnlInfoDocumentos);
        // Informações a direita do painel
        lblInfoDocumentosNomeDireita = new JLabel("");
        lblInfoDocumentosNomeDireita.setFont(fonteNegrito);
        pnlInfoDocumentos.add(lblInfoDocumentosNomeDireita);
        layoutPnlInfoDocumetnoEsquerda.putConstraint(SpringLayout.WEST, lblInfoDocumentosNomeDireita,
                140, SpringLayout.WEST, pnlInfoDocumentos);
        layoutPnlInfoDocumetnoEsquerda.putConstraint(SpringLayout.NORTH, lblInfoDocumentosNomeDireita,
                10, SpringLayout.NORTH, pnlInfoDocumentos);
        lblInfoDocumentosDataCriacaoDireita = new JLabel("");
        lblInfoDocumentosDataCriacaoDireita.setFont(fonteNegrito);
        pnlInfoDocumentos.add(lblInfoDocumentosDataCriacaoDireita);
        layoutPnlInfoDocumetnoEsquerda.putConstraint(SpringLayout.WEST, lblInfoDocumentosDataCriacaoDireita,
                140, SpringLayout.WEST, pnlInfoDocumentos);
        layoutPnlInfoDocumetnoEsquerda.putConstraint(SpringLayout.NORTH, lblInfoDocumentosDataCriacaoDireita,
                30, SpringLayout.NORTH, pnlInfoDocumentos);
        lblInfoDocumentosModificadoEmDireita = new JLabel("");
        lblInfoDocumentosModificadoEmDireita.setFont(fonteNegrito);
        pnlInfoDocumentos.add(lblInfoDocumentosModificadoEmDireita);
        layoutPnlInfoDocumetnoEsquerda.putConstraint(SpringLayout.WEST, lblInfoDocumentosModificadoEmDireita,
                140, SpringLayout.WEST, pnlInfoDocumentos);
        layoutPnlInfoDocumetnoEsquerda.putConstraint(SpringLayout.NORTH, lblInfoDocumentosModificadoEmDireita,
                50, SpringLayout.NORTH, pnlInfoDocumentos);
        lblInfoDocumentosTamanhoDireita = new JLabel("");
        lblInfoDocumentosTamanhoDireita.setFont(fonteNegrito);
        pnlInfoDocumentos.add(lblInfoDocumentosTamanhoDireita);
        layoutPnlInfoDocumetnoEsquerda.putConstraint(SpringLayout.WEST, lblInfoDocumentosTamanhoDireita,
                140, SpringLayout.WEST, pnlInfoDocumentos);
        layoutPnlInfoDocumetnoEsquerda.putConstraint(SpringLayout.NORTH, lblInfoDocumentosTamanhoDireita,
                70, SpringLayout.NORTH, pnlInfoDocumentos);
        lblInfoDocumentosAutorDireita = new JLabel("");
        lblInfoDocumentosAutorDireita.setFont(fonteNegrito);
        pnlInfoDocumentos.add(lblInfoDocumentosAutorDireita);
        layoutPnlInfoDocumetnoEsquerda.putConstraint(SpringLayout.WEST, lblInfoDocumentosAutorDireita,
                140, SpringLayout.WEST, pnlInfoDocumentos);
        layoutPnlInfoDocumetnoEsquerda.putConstraint(SpringLayout.NORTH, lblInfoDocumentosAutorDireita,
                90, SpringLayout.NORTH, pnlInfoDocumentos);
        lblInfoDocumentosTipoDireita = new JLabel("");
        lblInfoDocumentosTipoDireita.setFont(fonteNegrito);
        pnlInfoDocumentos.add(lblInfoDocumentosTipoDireita);
        layoutPnlInfoDocumetnoEsquerda.putConstraint(SpringLayout.WEST, lblInfoDocumentosTipoDireita,
                140, SpringLayout.WEST, pnlInfoDocumentos);
        layoutPnlInfoDocumetnoEsquerda.putConstraint(SpringLayout.NORTH, lblInfoDocumentosTipoDireita,
                110, SpringLayout.NORTH, pnlInfoDocumentos);
    }

    private void montarPainelDocsSupTabFicheiro() {
        painelSuperiorFicheiro = new JPanel(new GridBagLayout());
        GridBagConstraints constSupPnlDocumentos = new GridBagConstraints();
        lblOrganizacaoFicheiros = new JLabel("Organização dos Ficheiros");
        lblOrganizacaoFicheiros.setFont(fonteNegrito);
        constSupPnlDocumentos.fill = GridBagConstraints.HORIZONTAL;
        constSupPnlDocumentos.gridx = 0;
        constSupPnlDocumentos.gridy = 0;
        constSupPnlDocumentos.gridwidth = 1;
        constSupPnlDocumentos.weightx = 0.6;
        constSupPnlDocumentos.weighty = 0;
        constSupPnlDocumentos.insets = new Insets(5, 5, 0, 0);
        painelSuperiorFicheiro.add(lblOrganizacaoFicheiros, constSupPnlDocumentos);
        /*lblOrdenarPor = new JLabel("Ordenar Por:   ");
         constSupPnlDocumentos.fill = GridBagConstraints.HORIZONTAL;
         constSupPnlDocumentos.gridx = 5;
         constSupPnlDocumentos.gridy = 0;
         constSupPnlDocumentos.gridwidth = 1;
         constSupPnlDocumentos.weightx = 0;
         constSupPnlDocumentos.weighty = 0;
         constSupPnlDocumentos.insets = new Insets(5, 0, 0, 0);
         painelSuperiorFicheiro.add(lblOrdenarPor, constSupPnlDocumentos);*/
        /*jComboOrdenarPor = new JComboBox();
         constSupPnlDocumentos.fill = GridBagConstraints.HORIZONTAL;
         constSupPnlDocumentos.gridx = 6;
         constSupPnlDocumentos.gridy = 0;
         constSupPnlDocumentos.gridwidth = 1;
         constSupPnlDocumentos.weightx = 0;
         constSupPnlDocumentos.weighty = 0;
         constSupPnlDocumentos.insets = new Insets(5, 0, 0, 5);
         painelSuperiorFicheiro.add(jComboOrdenarPor, constSupPnlDocumentos);*/
    }

    private void montarTabelaFicheiro() {
        painelFicheiro = new JPanel(new GridBagLayout());
        GridBagConstraints constPnlDocumentos = new GridBagConstraints();
        tabelaFicheiros = getInstanceTabelaFicheiro();
        JScrollPane scrollPane = new JScrollPane(tabelaFicheiros);
        tabelaFicheiros.setFillsViewportHeight(true);
        constPnlDocumentos.fill = GridBagConstraints.BOTH;
        constPnlDocumentos.gridx = 0;
        constPnlDocumentos.gridy = 1;
        constPnlDocumentos.gridwidth = 7;
        constPnlDocumentos.weightx = 0;
        constPnlDocumentos.weighty = 0.5;
        constPnlDocumentos.insets = new Insets(5, 5, 0, 5);
        painelFicheiro.add(scrollPane, constPnlDocumentos);
    }

    private void montarPainelBotoesTabFicheiro() {
        GridBagConstraints constPnlDocumentos = new GridBagConstraints();
        btAdiocionarFicheiro = new JButton("Adicionar");
        constPnlDocumentos.fill = GridBagConstraints.HORIZONTAL;
        constPnlDocumentos.gridx = 0;
        constPnlDocumentos.gridy = 2;
        constPnlDocumentos.gridwidth = 1;
        constPnlDocumentos.weightx = 0.5;
        constPnlDocumentos.weighty = 0;
        constPnlDocumentos.insets = new Insets(5, 5, 0, 5);
        painelFicheiro.add(btAdiocionarFicheiro, constPnlDocumentos);
        btRemoverFicheiro = new JButton("Deletar");
        constPnlDocumentos.fill = GridBagConstraints.HORIZONTAL;
        constPnlDocumentos.gridx = 1;
        constPnlDocumentos.gridy = 2;
        constPnlDocumentos.gridwidth = 1;
        constPnlDocumentos.weightx = 0.5;
        constPnlDocumentos.weighty = 0;
        constPnlDocumentos.insets = new Insets(5, 0, 0, 5);
        painelFicheiro.add(btRemoverFicheiro, constPnlDocumentos);
        btRenomearFicheiro = new JButton("Renomear");
        constPnlDocumentos.fill = GridBagConstraints.HORIZONTAL;
        constPnlDocumentos.gridx = 2;
        constPnlDocumentos.gridy = 2;
        constPnlDocumentos.gridwidth = 1;
        constPnlDocumentos.weightx = 0.5;
        constPnlDocumentos.weighty = 0;
        constPnlDocumentos.insets = new Insets(5, 0, 0, 5);
        painelFicheiro.add(btRenomearFicheiro, constPnlDocumentos);
        btImportarFicheiro = new JButton("Importar");
        constPnlDocumentos.fill = GridBagConstraints.HORIZONTAL;
        constPnlDocumentos.gridx = 3;
        constPnlDocumentos.gridy = 2;
        constPnlDocumentos.gridwidth = 1;
        constPnlDocumentos.weightx = 0.5;
        constPnlDocumentos.weighty = 0;
        constPnlDocumentos.insets = new Insets(5, 0, 0, 5);
        painelFicheiro.add(btImportarFicheiro, constPnlDocumentos);
        btExportarFicheiro = new JButton("Exportar");
        constPnlDocumentos.fill = GridBagConstraints.HORIZONTAL;
        constPnlDocumentos.gridx = 4;
        constPnlDocumentos.gridy = 2;
        constPnlDocumentos.gridwidth = 1;
        constPnlDocumentos.weightx = 0.5;
        constPnlDocumentos.weighty = 0;
        constPnlDocumentos.insets = new Insets(5, 0, 0, 5);
        painelFicheiro.add(btExportarFicheiro, constPnlDocumentos);
        /*btClassificarFicheiro = new JButton("Classificar");
         constPnlDocumentos.fill = GridBagConstraints.HORIZONTAL;
         constPnlDocumentos.gridx = 5;
         constPnlDocumentos.gridy = 2;
         constPnlDocumentos.gridwidth = 1;
         constPnlDocumentos.weightx = 0.5;
         constPnlDocumentos.weighty = 0;
         constPnlDocumentos.insets = new Insets(5, 0, 0, 5);
         painelFicheiro.add(btClassificarFicheiro, constPnlDocumentos);*/
        btCompararFicheiro = new JButton("Comparar");
        constPnlDocumentos.fill = GridBagConstraints.HORIZONTAL;
        constPnlDocumentos.gridx = 6;
        constPnlDocumentos.gridy = 2;
        constPnlDocumentos.gridwidth = 1;
        constPnlDocumentos.weightx = 0.5;
        constPnlDocumentos.weighty = 0;
        constPnlDocumentos.insets = new Insets(5, 0, 0, 5);
        painelFicheiro.add(btCompararFicheiro, constPnlDocumentos);
        constPnlDocumentos.fill = GridBagConstraints.HORIZONTAL;
        constPnlDocumentos.gridx = 0;
        constPnlDocumentos.gridy = 3;
        constPnlDocumentos.gridwidth = 7;
        constPnlDocumentos.weightx = 0;
        constPnlDocumentos.weighty = 0;
        constPnlDocumentos.insets = new Insets(10, 5, 5, 5);
        painelFicheiro.add(new JSeparator(), constPnlDocumentos);
    }

    private void montarPainelDocsSupTabDocumento() {
        painelSupInternoDocs = new JPanel(new GridBagLayout());
        GridBagConstraints constSupPnlDocumentos2 = new GridBagConstraints();
        lblOrganizacaoDocumentos = new JLabel("Organização dos Documentos");
        lblOrganizacaoDocumentos.setFont(fonteNegrito);
        constSupPnlDocumentos2.fill = GridBagConstraints.HORIZONTAL;
        constSupPnlDocumentos2.gridx = 0;
        constSupPnlDocumentos2.gridy = 0;
        constSupPnlDocumentos2.gridwidth = 1;
        constSupPnlDocumentos2.weightx = 0.6;
        constSupPnlDocumentos2.weighty = 0;
        constSupPnlDocumentos2.insets = new Insets(5, 5, 0, 0);
        painelSupInternoDocs.add(lblOrganizacaoDocumentos, constSupPnlDocumentos2);
        /*lblDocsOrdenarPor = new JLabel("Ordenar Por:   ");
         constSupPnlDocumentos2.fill = GridBagConstraints.HORIZONTAL;
         constSupPnlDocumentos2.gridx = 6;
         constSupPnlDocumentos2.gridy = 0;
         constSupPnlDocumentos2.gridwidth = 1;
         constSupPnlDocumentos2.weightx = 0;
         constSupPnlDocumentos2.weighty = 0;
         constSupPnlDocumentos2.insets = new Insets(5, 0, 0, 0);
         painelSupInternoDocs.add(lblDocsOrdenarPor, constSupPnlDocumentos2);*/
        /*jComboDocsOrdenarPor = new JComboBox();
         constSupPnlDocumentos2.fill = GridBagConstraints.HORIZONTAL;
         constSupPnlDocumentos2.gridx = 7;
         constSupPnlDocumentos2.gridy = 0;
         constSupPnlDocumentos2.gridwidth = 1;
         constSupPnlDocumentos2.weightx = 0;
         constSupPnlDocumentos2.weighty = 0;
         constSupPnlDocumentos2.insets = new Insets(5, 0, 0, 5);
         painelSupInternoDocs.add(jComboDocsOrdenarPor, constSupPnlDocumentos2);*/
    }

    private void montarTabelaDocumento() {
        painelInternoDocs = new JPanel(new GridBagLayout());
        GridBagConstraints constPnlDocumentos2 = new GridBagConstraints();
        tabelaDocumentos = new JTable(tabDocs);
        JScrollPane scrollPaneD = new JScrollPane(tabelaDocumentos);
        tabelaDocumentos.setFillsViewportHeight(true);
        constPnlDocumentos2.fill = GridBagConstraints.BOTH;
        constPnlDocumentos2.gridx = 0;
        constPnlDocumentos2.gridy = 1;
        constPnlDocumentos2.gridwidth = 8;
        constPnlDocumentos2.weightx = 0;
        constPnlDocumentos2.weighty = 0.5;
        constPnlDocumentos2.insets = new Insets(5, 5, 0, 5);
        painelInternoDocs.add(scrollPaneD, constPnlDocumentos2);
    }

    private void montarPainelBotoesTabelaDocumento() {
        GridBagConstraints constPnlDocumentos2 = new GridBagConstraints();
        btAdiocionarDocumentos = new JButton("Adicionar");
        constPnlDocumentos2.fill = GridBagConstraints.HORIZONTAL;
        constPnlDocumentos2.gridx = 0;
        constPnlDocumentos2.gridy = 2;
        constPnlDocumentos2.gridwidth = 1;
        constPnlDocumentos2.weightx = 0.5;
        constPnlDocumentos2.weighty = 0;
        constPnlDocumentos2.insets = new Insets(5, 5, 0, 5);
        painelInternoDocs.add(btAdiocionarDocumentos, constPnlDocumentos2);
        btRemoverDocumentos = new JButton("Deletar");
        constPnlDocumentos2.fill = GridBagConstraints.HORIZONTAL;
        constPnlDocumentos2.gridx = 1;
        constPnlDocumentos2.gridy = 2;
        constPnlDocumentos2.gridwidth = 1;
        constPnlDocumentos2.weightx = 0.5;
        constPnlDocumentos2.weighty = 0;
        constPnlDocumentos2.insets = new Insets(5, 0, 0, 5);
        painelInternoDocs.add(btRemoverDocumentos, constPnlDocumentos2);
        btRenomearDocumentos = new JButton("Renomear");
        constPnlDocumentos2.fill = GridBagConstraints.HORIZONTAL;
        constPnlDocumentos2.gridx = 2;
        constPnlDocumentos2.gridy = 2;
        constPnlDocumentos2.gridwidth = 1;
        constPnlDocumentos2.weightx = 0.5;
        constPnlDocumentos2.weighty = 0;
        constPnlDocumentos2.insets = new Insets(5, 0, 0, 5);
        painelInternoDocs.add(btRenomearDocumentos, constPnlDocumentos2);
        btImportarDocumentos = new JButton("Importar");
        constPnlDocumentos2.fill = GridBagConstraints.HORIZONTAL;
        constPnlDocumentos2.gridx = 3;
        constPnlDocumentos2.gridy = 2;
        constPnlDocumentos2.gridwidth = 1;
        constPnlDocumentos2.weightx = 0.5;
        constPnlDocumentos2.weighty = 0;
        constPnlDocumentos2.insets = new Insets(5, 0, 0, 5);
        painelInternoDocs.add(btImportarDocumentos, constPnlDocumentos2);
        btExportarDocumentos = new JButton("Exportar");
        constPnlDocumentos2.fill = GridBagConstraints.HORIZONTAL;
        constPnlDocumentos2.gridx = 4;
        constPnlDocumentos2.gridy = 2;
        constPnlDocumentos2.gridwidth = 1;
        constPnlDocumentos2.weightx = 0.5;
        constPnlDocumentos2.weighty = 0;
        constPnlDocumentos2.insets = new Insets(5, 0, 0, 5);
        painelInternoDocs.add(btExportarDocumentos, constPnlDocumentos2);
        /*btClassificarDocumentos = new JButton("Classificar");
         constPnlDocumentos2.fill = GridBagConstraints.HORIZONTAL;
         constPnlDocumentos2.gridx = 5;
         constPnlDocumentos2.gridy = 2;
         constPnlDocumentos2.gridwidth = 1;
         constPnlDocumentos2.weightx = 0.5;
         constPnlDocumentos2.weighty = 0;
         constPnlDocumentos2.insets = new Insets(5, 0, 0, 5);
         painelInternoDocs.add(btClassificarDocumentos, constPnlDocumentos2);*/
        btCompararDocumentos = new JButton("Comparar");
        constPnlDocumentos2.fill = GridBagConstraints.HORIZONTAL;
        constPnlDocumentos2.gridx = 6;
        constPnlDocumentos2.gridy = 2;
        constPnlDocumentos2.gridwidth = 1;
        constPnlDocumentos2.weightx = 0.5;
        constPnlDocumentos2.weighty = 0;
        constPnlDocumentos2.insets = new Insets(5, 0, 0, 5);
        painelInternoDocs.add(btCompararDocumentos, constPnlDocumentos2);
        btEditarDocumentos = new JButton("Editar");
        constPnlDocumentos2.fill = GridBagConstraints.HORIZONTAL;
        constPnlDocumentos2.gridx = 7;
        constPnlDocumentos2.gridy = 2;
        constPnlDocumentos2.gridwidth = 1;
        constPnlDocumentos2.weightx = 0.5;
        constPnlDocumentos2.weighty = 0;
        constPnlDocumentos2.insets = new Insets(5, 0, 0, 5);
        painelInternoDocs.add(btEditarDocumentos, constPnlDocumentos2);
    }

    private void montarPainelDocs() {
        try {
            montarPainelDocsSupTabFicheiro();
            montarTabelaFicheiro();
            montarPainelBotoesTabFicheiro();
            montarPainelDocsSupTabDocumento();
            montarTabelaDocumento();
            montarPainelBotoesTabelaDocumento();
            // Adicionei mais quatro paineis externos para facilitar o gerenciamento
            // dos componentes demasiados
            GridBagConstraints constPnlDocs = new GridBagConstraints();
            constPnlDocs.fill = GridBagConstraints.HORIZONTAL;
            constPnlDocs.gridx = 0;
            constPnlDocs.gridy = 0;
            constPnlDocs.weightx = 1;
            constPnlDocs.weighty = 0;
            pnlDocumentos.add(painelSuperiorFicheiro, constPnlDocs);
            constPnlDocs.fill = GridBagConstraints.BOTH;
            constPnlDocs.gridx = 0;
            constPnlDocs.gridy = 1;
            constPnlDocs.weightx = 1;
            constPnlDocs.weighty = 0.5;
            pnlDocumentos.add(painelFicheiro, constPnlDocs);
            constPnlDocs.fill = GridBagConstraints.HORIZONTAL;
            constPnlDocs.gridx = 0;
            constPnlDocs.gridy = 2;
            constPnlDocs.weightx = 1;
            constPnlDocs.weighty = 0;
            pnlDocumentos.add(painelSupInternoDocs, constPnlDocs);
            constPnlDocs.fill = GridBagConstraints.BOTH;
            constPnlDocs.gridx = 0;
            constPnlDocs.gridy = 3;
            constPnlDocs.weightx = 1;
            constPnlDocs.weighty = 0.5;
            pnlDocumentos.add(painelInternoDocs, constPnlDocs);
            pack();
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(this, ex);
        }
    }
    // ----- Singleton ------------------------------------------------------------- //

    public JTable getInstanceTabelaFicheiro() {
        if (tabelaFicheiros == null) {
            tabelaFicheiros = new JTable(tabFicheiro);
        }
        return tabelaFicheiros;
    }
    // ----- Getters e Setters ----------------------------------------------------- //

    public JComboBox getComboBoxFicheiroOrdenarPor() {
        return jComboOrdenarPor;
    }

    public JComboBox getComboBoxDocsOrdenarPor() {
        return jComboDocsOrdenarPor;
    }

    public JTable getTabelaFicheiros() {
        return tabelaFicheiros;
    }

    public JTable getTabelaDocumentos() {
        return tabelaDocumentos;
    }

    public void setModeloTabelaFicheiros(ModeloTabela modeltabelaFicheiros) {
        tabelaFicheiros = new JTable(modeltabelaFicheiros);
    }

    public void setTabelaDocumentos(JTable tabelaDocumentos) {
        this.tabelaDocumentos = tabelaDocumentos;
    }

    public JComboBox getjComboOrdenarPor() {
        return jComboOrdenarPor;
    }

    public JButton getBtAdiocionarFicheiro() {
        return btAdiocionarFicheiro;
    }

    public JButton getBtRemoverFicheiro() {
        return btRemoverFicheiro;
    }

    public JButton getBtRenomearFicheiro() {
        return btRenomearFicheiro;
    }

    public JButton getBtImportarFicheiro() {
        return btImportarFicheiro;
    }

    public JButton getBtExportarFicheiro() {
        return btExportarFicheiro;
    }

    public JButton getBtClassificarFicheiro() {
        return btClassificarFicheiro;
    }

    public JButton getBtCompararFicheiro() {
        return btCompararFicheiro;
    }

    public JComboBox getjComboDocsOrdenarPor() {
        return jComboDocsOrdenarPor;
    }

    public JButton getBtAdiocionarDocumentos() {
        return btAdiocionarDocumentos;
    }

    public JButton getBtRemoverDocumentos() {
        return btRemoverDocumentos;
    }

    public JButton getBtRenomearDocumentos() {
        return btRenomearDocumentos;
    }

    public JButton getBtImportarDocumentos() {
        return btImportarDocumentos;
    }

    public JButton getBtExportarDocumentos() {
        return btExportarDocumentos;
    }

    public JButton getBtClassificarDocumentos() {
        return btClassificarDocumentos;
    }

    public JButton getBtCompararDocumentos() {
        return btCompararDocumentos;
    }

    public JButton getBtEditarDocumentos() {
        return btEditarDocumentos;
    }

    public void setLblInfoFicheiroNomeDireita(String lblInfoFicheiroNomeDireita) {
        this.lblInfoFicheiroNomeDireita.setText(lblInfoFicheiroNomeDireita);
    }

    public void setLblInfoFicheiroDataCriacaoDireita(String lblInfoFicheiroDataCriacaoDireita) {
        this.lblInfoFicheiroDataCriacaoDireita.setText(lblInfoFicheiroDataCriacaoDireita);
    }

    public void setLblInfoFicheiroModificadoEmDireita(String lblInfoFicheiroModificadoEmDireita) {
        this.lblInfoFicheiroModificadoEmDireita.setText(lblInfoFicheiroModificadoEmDireita);
    }

    public void setLblInfoFicheiroTamanhoDireita(String lblInfoFicheiroTamanhoDireita) {
        this.lblInfoFicheiroTamanhoDireita.setText(lblInfoFicheiroTamanhoDireita);
    }

    public void setLblInfoFicheiroAutorDireita(String lblInfoFicheiroAutorDireita) {
        this.lblInfoFicheiroAutorDireita.setText(lblInfoFicheiroAutorDireita);
    }

    public void setLblInfoDocumentosNomeDireita(String lblInfoDocumentosNomeDireita) {
        this.lblInfoDocumentosNomeDireita.setText(lblInfoDocumentosNomeDireita);
    }

    public void setLblInfoDocumentosDataCriacaoDireita(String lblInfoDocumentosDataCriacaoDireita) {
        this.lblInfoDocumentosDataCriacaoDireita.setText(lblInfoDocumentosDataCriacaoDireita);
    }

    public void setLblInfoDocumentosModificadoEmDireita(String lblInfoDocumentosModificadoEmDireita) {
        this.lblInfoDocumentosModificadoEmDireita.setText(lblInfoDocumentosModificadoEmDireita);
    }

    public void setLblInfoDocumentosTamanhoDireita(String lblInfoDocumentosTamanhoDireita) {
        this.lblInfoDocumentosTamanhoDireita.setText(lblInfoDocumentosTamanhoDireita);
    }

    public void setLblInfoDocumentosAutorDireita(String lblInfoDocumentosAutorDireita) {
        this.lblInfoDocumentosAutorDireita.setText(lblInfoDocumentosAutorDireita);
    }

    public void setLblInfoDocumentosTipoDireita(String lblInfoDocumentosTipoDireita) {
        this.lblInfoDocumentosTipoDireita.setText(lblInfoDocumentosTipoDireita);
    }
}