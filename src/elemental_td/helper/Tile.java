/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elemental_td.helper;

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
