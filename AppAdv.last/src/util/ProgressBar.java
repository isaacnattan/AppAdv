package util;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * @author Isaac_Nattan
 */
public class ProgressBar extends JDialog {

    // variaveis ProgressBar
    private JProgressBar progressBar;
    private JPanel painelProgress;
    private int indiceProgressBar;
    private long novoPedaco;
    // artificio temporario
    private JLabel lblPorcentagemComparacao;

    public ProgressBar() {
        // label temporaria
        lblPorcentagemComparacao = new JLabel("0%");
        // Cria progressBar
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        // Seta progressBar no painel
        painelProgress = new JPanel();
        painelProgress.add(progressBar);
        // Stea tudo no painel principal
        setSize(400, 200);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        add(lblPorcentagemComparacao, BorderLayout.NORTH);
        add(painelProgress, BorderLayout.CENTER);
        setVisible(true);
    }
    
    public void incrementaProgressBar(long limite, long indice){
        if (indice == 0) {
            // Inicializa o indice do progressBar
            indiceProgressBar = 1;
            novoPedaco = limite / 100;
        }
        if (indice == novoPedaco) {
            novoPedaco = (limite / 100) * (indiceProgressBar + 1);
            System.out.println(indiceProgressBar);
            progressBar.setValue(indiceProgressBar++);
        }
        lblPorcentagemComparacao.setText(String.valueOf(indiceProgressBar)+"%");
    }
    
    public static void main(String [] args){
        ProgressBar pb = new ProgressBar();
    }
}
