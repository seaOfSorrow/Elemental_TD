/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elemental_td.helper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 *
 * @author Pascal
 */
public class EncryptUtils {
    public static final String DEFAULT_ENCODING = "UTF-8"; 

    public static String base64encode(String text) {
        try {
            return new String(Base64.getEncoder().encode(text.getBytes(DEFAULT_ENCODING)));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static String base64decode(String text) {
        try {
            return new String(Base64.getDecoder().decode(text.getBytes(DEFAULT_ENCODING)));
        } catch (IOException e) {
            return null;
        }
    }
    
    public static String xorMessage(String message, String key) {
        try {
            if (message == null || key == null) return null;

            char[] keys = key.toCharArray();
            char[] mesg = message.toCharArray();

            int ml = mesg.length;
            int kl = keys.length;
            char[] newmsg = new char[ml];

            for (int i = 0; i < ml; i++) {
                newmsg[i] = (char)(mesg[i] ^ keys[i % kl]);
            }//for i

            return new String(newmsg);
        } catch (Exception e) {
            return null;
        }
    }
}