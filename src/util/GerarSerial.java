package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Isaac_Nattan
 */
public class GerarSerial {
    // Coloque aqui o serial do usario

    public final String serialIsaacDell = "40fd5bc873c776d35657b2f5ef2dfbe6";
    public final String serialIsaacEMachines = "d7ae06e73129fdb3e797c643ebcd383f";
    public final String serialTrbalho = "6b89c2696cded2e95efd2c2f0a9662f2";

    public String getSerial() {
        String codigo = null;
        try {
            codigo = calculaHash(getMAC() + getCPUSerial() + getHDSerial("c"));
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "Aconteceu algum problema com a geraçãodo serial." + ex);
        }
        return codigo;
    }

    public String calculaHash(String palavra) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(palavra.getBytes());
            BigInteger hashMd5 = new BigInteger(1, md.digest());

            return hashMd5.toString(16);
        } catch (NoSuchAlgorithmException exHash) {
            return null;
        }
    }

    public String getHDSerial(String drive) {
        String result = "";
        try {
            File file = File.createTempFile("tmp", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);
            String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n" + "Set colDrives = objFSO.Drives\n"
                    + "Set objDrive = colDrives.item(\"" + drive + "\")\n" + "Wscript.Echo objDrive.SerialNumber";
            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
        } catch (Exception e) {
        }
        if (result.trim().length() < 1 || result == null) {
            result = "NO_DISK_ID";
        }
        return result.trim();
    }

    public String getCPUSerial() {
        String result = "";
        try {
            File file = File.createTempFile("tmp", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);
            String vbs =
                    "On Error Resume Next \r\n\r\n"
                    + "strComputer = \".\"  \r\n"
                    + "Set objWMIService = GetObject(\"winmgmts:\" _ \r\n"
                    + "    & \"{impersonationLevel=impersonate}!\\\\\" & strComputer & \"\\root\\cimv2\") \r\n"
                    + "Set colItems = objWMIService.ExecQuery(\"Select * from Win32_Processor\")  \r\n "
                    + "For Each objItem in colItems\r\n "
                    + "    Wscript.Echo objItem.ProcessorId  \r\n "
                    + "    exit for  ' do the first cpu only! \r\n"
                    + "Next                    ";
            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
        } catch (Exception e) {
        }
        if (result.trim().length() < 1 || result == null) {
            result = "NO_CPU_ID";
        }
        return result.trim();
    }

    public String getMAC() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            StringBuilder sb = new StringBuilder();
            Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
            while (networks.hasMoreElements()) {
                NetworkInterface network = networks.nextElement();
                byte[] mac = network.getHardwareAddress();
                if (mac != null) {
                    for (int i = 0; i < mac.length; i++) {
                        sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                    }
                }
            }
            return sb.substring(0, 17).toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        } catch (SocketException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void gerarTxtSerial(String codigo) {
        File arquivoSerial = new File(System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "serial.txt");
        if (!arquivoSerial.exists()) {
            try {
                arquivoSerial.createNewFile();
                // Escreve o serial no arquivo
                FileWriter fw = new FileWriter(arquivoSerial);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("Serial usuario: " + codigo);
                bw.newLine();
                bw.close();
                fw.close();
                javax.swing.JOptionPane.showMessageDialog(null, "Vá até a área de trabalho, "
                        + "lá tem um arquivo chamado serial.txt contendo seu número serial.");
            } catch (IOException ex) {
                Logger.getLogger(GerarSerial.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "Seu serial já foi gerado.");
            javax.swing.JOptionPane.showMessageDialog(null, "Vá até a área de trabalho, "
                    + "lá tem um arquivo chamado serial.txt contendo seu número serial.");
        }
    }

    public static void main(String[] args) {
        GerarSerial gc = new GerarSerial();
        gc.gerarTxtSerial(gc.getSerial());
    }
}

/*
 * Pega o mac e o numero de serie do hd concatena
 * gera um hash e esse hash eh o serial do usuário
 * poderia pensar em características mais singulares
 * da maquina pra concatenar e gerar um hash mais seguro
 * e menos dependente de mudancas do sistema
 */