package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MatrizDinamica<T> {

    private final Map<Integer, Map<Integer, T>> elementos = new HashMap<Integer, Map<Integer, T>>();
    private int i = 0, j = 0;
    private int TAMANHO_REGISTRO = 0;
    private int QUANTIDADE_REGISTROS = 0;
    private int QUANTIDADE_REGISTROS_ULTIMA_ITERACAO = 0;

    public MatrizDinamica(int tamanhoRegistro) {
        TAMANHO_REGISTRO = tamanhoRegistro;
    }

    // Para o caso de uma inicializacao sem limite de coluna
    public MatrizDinamica() {
        javax.swing.JOptionPane.showMessageDialog(null, "Você não inicializou o tamanho do registro "
                + "logo não pode usar o setDinamico, metodo que gerencia os indices automaticamente");
    }

    public void set(int linha, int coluna, T elemento) {
        Map<Integer, T> colunas = getColunas(linha);
        Integer chave = Integer.valueOf(coluna);
        if (elemento != null) {
            colunas.put(chave, elemento);
        } else {
            colunas.remove(chave);
        }
    }

    public T get(int linha, int coluna) {
        try {
            Map<Integer, T> colunas = getColunas(linha);
            Integer chave = Integer.valueOf(coluna);
            T elemento = colunas.get(chave);
            return elemento;
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "Elemento não constante." + ex);
            return null;
        }
    }

    public boolean remove(int linha, int coluna) {
        return true;
    }

    public boolean removeLine(int linha) {
        int chave = Integer.valueOf(linha);
        Map<Integer, T> colunas = elementos.get(chave);
        if (colunas != null) {
            elementos.remove(chave);
            return true;
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "");
            return false;
        }
    }

    // Metodo interno
    private Map<Integer, T> getColunas(int linha) {
        int chave = Integer.valueOf(linha);
        Map<Integer, T> colunas = elementos.get(chave);
        if (colunas == null) {
            colunas = new HashMap<Integer, T>();
            elementos.put(chave, colunas);
        }
        return colunas;
    }

    public void setD(T elemento) {   // Seta Dinamico
        Map<Integer, T> colunas = getColunas(i);    // cria mapeamento entre coluna(tipo) e chave inteiro
        Integer chave = Integer.valueOf(j);

        if (elemento != null) {
            colunas.put(chave, elemento);
        } else {
            colunas.remove(chave);
        }

        // Gerenciamento dos indices automaticos 
        if (j == (TAMANHO_REGISTRO - 1)) {     // Atingiu o limite do vetor lateral (colunas)
            i++;
            j = 0;
        } else {
            j++;
            // So atualiza o ultimo registro para valores menores que o limite da coluna
            QUANTIDADE_REGISTROS_ULTIMA_ITERACAO = j;
        }
        QUANTIDADE_REGISTROS = i;   // Atribui novo tamanho a quantidade de registros
    }

    // Forma generica de listar todos os elementos da MatrizBidimensionalDinamica
    public void listaTodosRegistros() {
        // Ps.: Embora haja preocupacao com registros quebrados (com menos elementos)
        // nao eh necessario pq a tabela sempre sera completa
        for (int k = 0; k <= QUANTIDADE_REGISTROS; k++) {
            for (int l = 0; l < TAMANHO_REGISTRO; l++) {
                if (l == QUANTIDADE_REGISTROS_ULTIMA_ITERACAO
                        && k == QUANTIDADE_REGISTROS) {
                    break;
                } else {
                    // Acao final filtrada
                    System.out.println(get(k, l) + "[" + k + "]" + "[" + l + "]");
                }
            }
        }
    }

    public ArrayList<String> getLinha(int linha) {
        ArrayList<String> registro = new ArrayList<String>();
        for (int k = 0; k < TAMANHO_REGISTRO; k++) {
            registro.add(get(linha, k).toString());
        }

        return registro;
    }

    // Getters que podem ajudar
    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public int getTAMANHO_REGISTRO() {
        return TAMANHO_REGISTRO;
    }

    public int getQUANTIDADE_REGISTROS() {
        return QUANTIDADE_REGISTROS;
    }

    public int getQUANTIDADE_REGISTROS_ULTIMA_ITERACAO() {
        return QUANTIDADE_REGISTROS_ULTIMA_ITERACAO;
    }

    // testbench
    public static void main(String[] args) {
        MatrizDinamica<String> mt = new MatrizDinamica<String>(5);
        
        //linha 0
        mt.setD("1");
        mt.setD("2");
        mt.setD("3");
        mt.setD("4");
        mt.setD("5");
        
        //linha 1
        mt.setD("6");
        mt.setD("7");
        mt.setD("8");
        mt.setD("9");
        mt.setD("10");
        
        //linha 2
        mt.setD("11");
        mt.setD("12");
        mt.setD("13");
        mt.setD("14");
        mt.setD("15");

        //linha 3
        mt.setD("16");
        mt.setD("17");
        mt.setD("18");
        mt.setD("19");
        mt.setD("20");

        //linha 4
        mt.setD("21");
        mt.setD("22");
        
        if (mt.removeLine(3)) {
            mt.listaTodosRegistros();
        }
    }
}

/*
 * Essa classe deve implementar uma matriz bidimensional dinamica com indices
 * auto gerenciaveis
 */