package util;

import java.util.ArrayList;

/**
 * @author Isaac_Nattan
 */
public class MatrizDinamica2<T> {
    private ArrayList<ArrayList<T>> matriz;
    public int length;

    public MatrizDinamica2() {
        matriz = new ArrayList<ArrayList<T>>();
    }

    public void adicionaLinha(ArrayList<T> linha) {
        try {
            matriz.add(linha);
            length = matriz.size();
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "Problema ao adicionar linha na Matriz Dinamica" + ex);
        }
    }

    public void removeLinha(int linha) {
        try {
            matriz.remove(linha);
            length = matriz.size();
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "Problema ao remover linha na Matriz Dinamica" + ex);
        }
    }

    public ArrayList<T> obtemLinha(int indice) {
        try {
            ArrayList<T> linha = matriz.get(indice);
            return linha;
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "Problema ao obter linha na Matriz Dinamica" + ex);
            return null;
        }
    }

    public T obtemElementoLinha(int linha, int coluna) {
        try {
            ArrayList<T> linhaM = matriz.get(linha);
            T elemento = linhaM.get(coluna);
            return elemento;
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "Problema ao obter elemento da linha na Matriz Dinamica" + ex);
            return null;
        }
    }

    /*public static void main(String[] args) {
     MatrizDinamica2<String> matriz = new MatrizDinamica2<>();

     ArrayList<String> linha = null;

     for (int i = 0; i < 10; i++) {
     linha = new ArrayList<>();
     linha.add("info " + i+0 + "  ");
     linha.add("info " + i+1 + "  ");
     linha.add("info " + i+2 + "  ");
     linha.add("info " + i+3 + "  ");
     linha.add("info " + i+4 + "  ");
     matriz.adicionaLinha(linha);
     }

     for (int i = 0; i < matriz.length; i++) {
     for (int j = 0; j < linha.size(); j++) {
     System.out.print(matriz.obtemLinha(i).get(j));
     }
     System.out.println();
     }

     matriz.removeLinha(7);
        
     System.out.println();
     System.out.println();
     System.out.println();
     System.out.println();
        
     for (int i = 0; i < matriz.length; i++) {
     for (int j = 0; j < linha.size(); j++) {
     System.out.print(matriz.obtemLinha(i).get(j));
     }
     System.out.println();
     }
        
     System.out.println();
     System.out.println();
     System.out.println();
     System.out.println();
        
     for(int i=0; i<linha.size(); i++)
     System.out.print(matriz.obtemLinha(2).get(i));
        
     System.out.println();
     System.out.println();
     System.out.println();
     System.out.println();        
        
     System.out.println(matriz.obtemElementoLinha(3, 4));
     }*/
}
