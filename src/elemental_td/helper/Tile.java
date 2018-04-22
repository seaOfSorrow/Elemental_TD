/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elemental_td.helper;

import elemental_td.data.Creep;
import elemental_td.data.Game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Pascal
 */
public class Tile extends Rectangle {

    private final int groundType;
    private int airType;
    private final int WIDTH = 50, HEIGHT = 50;
    private boolean usable = true;
    private Rectangle towerRange;
    private boolean shooting;
    private int mobIndexShot;

    public Tile(int x, int y, int groundType, int airType) {
        super(x, y, 0, 0);
        super.setSize(WIDTH, HEIGHT);
        this.groundType = groundType;
        this.airType = airType;
        if (this.groundType == Values.gTiles.path.ordinal()) {
            usable = false;
        }
    }

    public void setAirType(int airType) {
        towerRange = new Rectangle(x - (Values.towerRange[airType] / 2), y - (Values.towerRange[airType] / 2), width + Values.towerRange[airType], height + Values.towerRange[airType]);
        this.airType = airType;
    }

    public void physic(){
        shooting=false;
        if(towerRange!=null){
            for(int i =0;i< Game.field.creeps.size();i++){
                if(Game.field.creeps.get(i).ingame()&&!Game.field.creeps.get(i).isDefeated()){
                    if(Game.field.creeps.get(i).intersects(towerRange)){
                        shooting = true;
                        mobIndexShot=i;
                        return;
                    }
                }
            }
        }
    }
    
    int c=0;
    
    public void drawPhysic(Graphics g){
        if(shooting){
            g.setColor(new Color(1f, 0.1f, 0.01f, 0.5f));
            g.drawLine(x+width/2, y+height/2, Game.field.creeps.get(mobIndexShot).x+Game.field.creeps.get(mobIndexShot).width/2, Game.field.creeps.get(mobIndexShot).y+Game.field.creeps.get(mobIndexShot).height/2);
            if(c==50/airType){
            Game.field.creeps.get(mobIndexShot).getDamage(Values.towerDmg[airType]);
                c=0;
            }else{
                c++;
            }
        }
    }
    
    public void drawTowerRange(Graphics g) {
        g.setColor(Color.BLACK);
        if (towerRange != null) {
            g.drawRect(towerRange.x, towerRange.y, towerRange.width, towerRange.height);
        }
    }

    public int getGroundType() {
        return groundType;
    }

    public int getAirType() {
        return airType;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public boolean isUsable() {
        return usable;
    }
}
