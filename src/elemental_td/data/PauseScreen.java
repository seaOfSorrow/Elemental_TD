/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elemental_td.data;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Pascal
 */
public class PauseScreen extends JPanel {

    private final int WIDTH, HEIGHT;
    private Image pauseImg;
    private int c;

    public PauseScreen(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
        try {
            if(PauseScreen.class.getResourceAsStream("/elemental_td/data/res/pause.jpg")==null){
                throw new IOException();
            }else{
            pauseImg = ImageIO.read(PauseScreen.class.getResourceAsStream("/elemental_td/data/res/pause.jpg"));
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Internal error\nProgram is going to exit now!");
            System.exit(1);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.setColor(Color.WHITE);
        g.drawImage(pauseImg, 0, 0, WIDTH, HEIGHT, this);
        c++;
        if (c == 60) {
            Game.pauseTimeSec++;
            c = 0;
        }
        if (Game.pauseTimeSec == 60) {
            Game.pauseTimeMin++;
            Game.pauseTimeSec = 0;
        }
        if (Game.pauseTimeSec < 10) {
            g.drawString(Integer.toString(Game.pauseTimeMin) + ":0" + Integer.toString(Game.pauseTimeSec), 0, 550);
        } else {
            g.drawString(Integer.toString(Game.pauseTimeMin) + ":" + Integer.toString(Game.pauseTimeSec), 0, 550);
        }
    }
}
