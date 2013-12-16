package util;

/**
 * @author Isaac_Nattan
 */
public class AutenticacaoHardware {

    private GerarSerial serial;

    public boolean autenticacao() {
        serial = new GerarSerial();
        // Verifica se o hash obtido eh igual ao fornecido
        // Ps.: a ordem dos numeros eh de extrema importancia
        // sempre usar: mac + cpuserial + hdserial
        if (serial.getSerial().equals(serial.serialIsaacDell) ||
            serial.getSerial().equals(serial.serialIsaacEMachines) ||
            serial.getSerial().equals(serial.serialTrbalho)) {
            // Obtem permissao para usar o software 
            return true;
        }  else {
            javax.swing.JOptionPane.showMessageDialog(null, "Desculpe, mas você "
                + "não tem autorização para usar esse aplicativo !");

        return false;
    }
}
}
