/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elemental_td.data;

import elemental_td.helper.Values;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Pascal
 */
public class Shop extends JPanel {

    private Rectangle hp = new Rectangle(50, 30, 30, 30);
    private Rectangle co = new Rectangle(50, 80, 30, 30);
    private Image hp_icon;
    private Image coin_icon;
    private Rectangle[] shopTiles = new Rectangle[8];
    private int[] shopContent = {Values.aTiles.tower_b.ordinal(), Values.aTiles.tower_m.ordinal(), Values.aTiles.tower_l.ordinal(), Values.aTiles.tower_a.ordinal(), 0, 0, 0, Values.aTiles.trash.ordinal()};
    private int[] shopPrice = {10,20,50,100,0,0,0,0};
    private int holdItemID;
    private boolean holdsItem;

    public Shop() {
        try {
            this.hp_icon = ImageIO.read(Shop.class.getResourceAsStream("/elemental_td/data/res/hp.png"));
            this.hp_icon = this.hp_icon.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            this.coin_icon = ImageIO.read(Shop.class.getResourceAsStream("/elemental_td/data/res/coin.png"));
            this.coin_icon = this.coin_icon.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            initShop();
        } catch (IOException ex) {
            Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initShop() {
        this.setBounds(0, 400, 800, 100);
        this.setBackground(new Color(0.2f, 0.2f, 0.2f, 0.6f));
        initTiles();
    }

    private void initTiles() {
        for (int i = 0; i < shopTiles.length; i++) {
            shopTiles[i] = new Rectangle(200 + 50 * i + 5 * i, 450, 50, 50);
        }
    }

    int c = 0;

    public void pressMouse(int key){
        if(key==MouseEvent.BUTTON1){
            for(int i=0;i<shopTiles.length;i++){
                if(shopTiles[i].contains(Game.mouse)){
                    if(shopContent[i]==Values.aTiles.trash.ordinal()){
                        holdsItem=false;
                    }else{
                        if(!holdsItem){
                            if(shopContent[i]!=Values.aTiles.air.ordinal()){
                            holdsItem=true;
                            holdItemID=shopContent[i];
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void countTime() {
        if (Game.round != 0) {
            if (c == 60) {
                Game.gameTimeSec++;
                c = 0;
            }
            if (Game.gameTimeSec == 60) {
                Game.gameTimeMin++;
                Game.gameTimeSec = 0;
            }
            c++;
        }
    }

    private void drawItemOnMouse(Graphics g){
        if(Game.mouse.y-400 < 0){
            Game.field.isOverlap(Game.mouse.x, Game.mouse.y, holdItemID);
        }else{
            Game.field.isNotOverlap();
            g.drawImage(Game.field.getAirTile(holdItemID), Game.mouse.x, Game.mouse.y-400, this);
        }
    }
    
    private void drawTime(Graphics g) {
        countTime();
        if (Game.gameTimeSec < 10) {
            g.drawString("" + Game.gameTimeMin + ":0" + Game.gameTimeSec, 0, 150);
        } else {
            g.drawString("" + Game.gameTimeMin + ":" + Game.gameTimeSec, 0, 150);
        }
    }

    private void drawRound_hp_coins(Graphics g) {
        g.drawImage(hp_icon, hp.x, hp.y, hp.width, hp.height, this);
        g.drawString(Integer.toString(Values.currentHp), hp.x + 30, hp.y + 20);
        g.drawImage(coin_icon, co.x, co.y, co.width, co.height, this);
        g.drawString(Integer.toString(Values.currentCoins), co.x + 30, co.y + 20);
        g.setFont(new Font("Courier New", Font.BOLD, 14));
        g.drawString("Round: " + Integer.toString(elemental_td.data.Game.round), 0, 130);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.setColor(Color.WHITE);
        drawRound_hp_coins(g);
        drawTime(g);
        if(holdsItem){            
            drawItemOnMouse(g);
        }
        for (int i = 0; i < shopTiles.length; i++) {
            g.setColor(Color.BLACK);
            g.drawRect(shopTiles[i].x, shopTiles[i].y - 400, shopTiles[i].width, shopTiles[i].height);
            g.drawImage(Game.field.getAirTile(shopContent[i]), shopTiles[i].x, shopTiles[i].y, 50, 50, this);
            if(1<shopPrice[i]){
                g.setColor(Color.white);
                g.drawString("$"+shopPrice[i], shopTiles[i].x+10, shopTiles[i].y+15-400);
            }
            if (shopTiles[i].contains(Game.mouse)) {
                g.setColor(new Color(0f, 0f, 0f, 0.2f));
                g.fillRect(shopTiles[i].x, shopTiles[i].y - 400, shopTiles[i].width, shopTiles[i].height);
            }
        }
    }
}
