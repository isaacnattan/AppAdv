package util;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 * @author Isaac Nattan
 */
public class ModeloTabela extends AbstractTableModel {

    private MatrizDinamica2<String> linhaTabela;
    private ArrayList<String> cabecalhoTabela;

    public ModeloTabela(ArrayList<String> cabecalho) {
        // Inicializa dimensoes da tabela
        cabecalhoTabela = cabecalho;
        linhaTabela = new MatrizDinamica2<String>();
    }

    public void adicionaLinhaTabela(ArrayList<String> linhaTabela) {
        this.linhaTabela.adicionaLinha(linhaTabela);
        fireTableDataChanged();
    }

    public boolean limparTabela() {
        try {
            int tamanho = linhaTabela.tamanho;
            for (int i = tamanho; i > 0; i--) {
                linhaTabela.removeLinha(0);
            }
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "Problemas ao limpar tabela.");
            return false;
        }

        return true;
    }

    public void removeLinha(int linha) {
        linhaTabela.removeLinha(linha);
    }

    public ArrayList<String> getLinhaTabela(int linha) {
        return linhaTabela.obtemLinha(linha);
    }
    
    public ArrayList<String> getAllRows(int coluna){
        ArrayList<String> allRows = new ArrayList<String>();
        for(int i=0; i<linhaTabela.tamanho; i++){
            allRows.add(linhaTabela.obtemElementoLinha(i, coluna));
        }
        return allRows;
    }

    // Esses quatro metodos sao de vital importancia para o modelo, nao
    // devem nao existir !!
    @Override
    public String getColumnName(int num) {
        return this.cabecalhoTabela.get(num);
    }

    @Override
    public int getRowCount() {
        return linhaTabela.tamanho;
    }

    @Override
    public int getColumnCount() {
        return cabecalhoTabela.size();
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        return linhaTabela.obtemElementoLinha(linha, coluna);
    }
}
