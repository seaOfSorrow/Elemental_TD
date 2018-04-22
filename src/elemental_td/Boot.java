/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elemental_td;

import java.awt.EventQueue;

/**
 *
 * @author Pascal
 */
public class Boot {
    
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new elemental_td.data.Game().setVisible(true);
        });
    }
}
