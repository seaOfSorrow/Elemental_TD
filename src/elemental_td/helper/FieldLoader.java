/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elemental_td.helper;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Pascal
 */
public class FieldLoader {

    public static boolean saveField(int[][][] field, String dateiname) {
        try {
            String enc = "";
            for (int i = 0; i < field.length; i++) {
                for (int j = 0; j < field[0].length; j++) {
                    for (int k = 0; k < field[0][0].length; k++) {
                        enc += field[i][j][k] + ":";
                    }
                    enc += "\n";
                }
                enc += "\n";
            }
            String output = EncryptUtils.base64encode(enc);
            File dir = new File(System.getProperty("user.home"));
            dir = new File(dir.getCanonicalPath() + "/Games");
            if (!dir.exists()) {
                dir.mkdir();
            }
            dir = new File(dir.getCanonicalPath() + "/" + dateiname + ".sorrow");
            try (BufferedWriter out = new BufferedWriter(new FileWriter(dir))) {
                out.write(output);
                out.flush();
            }
            return true;
        } catch (IOException ex) {

        }
        return false;
    }

    public static int[][][] getSaveState() {
        File savestate = null;
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Elemental TD Savefiles",
                "sorrow");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            savestate = chooser.getSelectedFile();
        }
        if (savestate == null) {
            return null;
        }
        try (BufferedReader in = new BufferedReader(new FileReader(savestate))) {
            String input = "";
            while (in.ready()) {
                input += in.readLine();
            }
            String decode = EncryptUtils.base64decode(input);
            decode = decode.replaceAll("\n", "");
            String[] singleValue = decode.split(":");
            int[][][] returnValue = new int[2][8][16];
            int c = 0;
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 8; j++) {
                    for (int k = 0; k < 16; k++) {
                        returnValue[i][j][k] = Integer.parseInt(singleValue[c]);
                        c++;
                    }
                }
            }
            return returnValue;
        } catch (IOException | NumberFormatException ex) {
            return null;
        }
    }

//    public static void main(String[] args) {
//        int[][][] returnVal=getBaseField();
//        
//        for(int i =0;i<2;i++){
//            for(int j=0;j<8;j++){
//                for(int k=0;k<16;k++){
//                    System.out.print(returnVal[i][j][k]+" ");
//                }
//                System.out.println();
//            }
//            System.out.println();
//        }
//    }
    
    public static int[][][] getBaseField() {
        try {
            try (BufferedInputStream in = new BufferedInputStream(FieldLoader.class.getResourceAsStream("/elemental_td/data/basicField.sorrow"))) {
                String input = "";
                while (in.available()!=0) {
                    input += (char) in.read();
                }
                String decode = EncryptUtils.base64decode(input);
                decode = decode.replaceAll("\n", "");
                String[] singleValue = decode.split(":");
                int[][][] returnValue = new int[2][8][16];
                int c = 0;
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 8; j++) {
                        for (int k = 0; k < 16; k++) {
                            returnValue[i][j][k] = Integer.parseInt(singleValue[c]);
                            c++;
                        }
                    }
                }
                return returnValue;
            }
        } catch (IOException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Internal error\nProgram is going to exit now!");   
            System.exit(1);
            return null;
        }
    }
}
