/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elemental_td.data;

import elemental_td.helper.Values;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Pascal
 */
public class Creep extends Rectangle {

    private final int size = 50;
    private boolean inGame = false;
    private boolean defeated = false;
    private final int creeptype;
    private int hp;
    private final int dR = 0, dU = 1, dD = 2, dL = 3;
    private int xC, yC, position_inside_tile;
    private boolean iR, iU, iD, iL;
    private int direction = dR;

    public Creep(int creeptype) throws Exception {
        int ammount = Values.eTiles.values().length;
        int c = 0;
        for (Enum e : Values.eTiles.values()) {
            if (creeptype != e.ordinal()) {
                c++;
            }
        }
        if (c == ammount) {
            throw new Exception("Invalid Creeptype!");
        }
        this.creeptype = creeptype;
        hp = Values.enemyHp * Values.enemyHpMultiplierer[this.creeptype];
    }

    public void spawn() {
        if (inGame) {

        } else {
            super.setRect(-50, 50, size, size);
            xC = -1;
            yC = y / 50;
            inGame = true;
        }
    }

    public boolean ingame() {
        return inGame;
    }

    public void chkDefeat() {
        if (hp == 0) {
            defeated = true;
            inGame = false;
        }
    }

    private int frame = 0, frameW = 1;

    public void physic() {
        if (frame >= frameW) {
            if (direction == dR) {
                x++;
            }
            if (direction == dD) {
                y++;
            }
            if (direction == dU) {
                y--;
            }
            if (direction == dL) {
                x--;
            }
            position_inside_tile++;
            if (position_inside_tile == size) {
                if (direction == dR) {
                    xC++;
                    iR = true;
                }
                if (direction == dL) {
                    xC--;
                    iL = true;
                }
                if (direction == dU) {
                    yC--;
                    iU = true;
                }
                if (direction == dD) {
                    yC++;
                    iD = true;
                }
                position_inside_tile = 0;
                try {
                    if (!iD) {
                        if (!iU) {
                            if (yC != Game.field.getTiles().length) {                                
                                if (Game.field.getTiles()[yC + 1][xC].getGroundType() == Values.gTiles.path.ordinal()) {
                                    direction = dD;
                                }
                            }
                        }
                    }
                    if (!iU) {
                        if (!(yC <= 1)) {                            
                            if (!iD) {
                                if (Game.field.getTiles()[yC - 1][xC].getGroundType() == Values.gTiles.path.ordinal()) {
                                    direction = dU;
                                }
                            }
                        }
                    }
                    if (!iL) {
                        if (!(xC <= 1)) {
                            if (!iR) {
                                if (Game.field.getTiles()[yC][xC - 1].getGroundType() == Values.gTiles.path.ordinal()) {
                                    direction = dL;
                                }
                            }
                        }
                    }
                    if (!iR) {
                        if (xC != Game.field.getTiles()[yC].length) {
                            if (Game.field.getTiles()[yC][xC + 1].getGroundType() == Values.gTiles.path.ordinal()) {
                                direction = dR;
                            }
                        }
                    }
                    if(Game.field.getTiles()[yC][xC].getGroundType() == Values.gTiles.ancient.ordinal()){
                        Values.currentHp-=Values.enemyAncientDamage[creeptype];
                        defeated=true;
                        inGame=false;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            iR = false;
            iU = false;
            iD = false;
            iL = false;
            frame = 0;
        } else {
            frame++;
        }
    }

    public boolean isDefeated() {
        return defeated;
    }

    public void drawCreep(Graphics g) {
        if (inGame) {
            g.drawImage(Game.field.getCreepTile(creeptype), x, y, 50, 50, null);
        }
    }
}
