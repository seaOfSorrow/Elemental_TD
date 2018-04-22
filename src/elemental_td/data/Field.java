/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elemental_td.data;

import elemental_td.helper.FieldLoader;
import elemental_td.helper.ImageLoader;
import elemental_td.helper.Tile;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Pascal
 */
public class Field extends JPanel {

    private final int WIDTH = 800, HEIGHT = 400;
    private Tile[][] tiles;

    private int[][][] structure = FieldLoader.getBaseField();
    private final Image[] groundTiles = ImageLoader.getGroundTiles();
    private final Image[] airTiles = ImageLoader.getAirTiles();
    private final Image[] creepTiles = ImageLoader.getCreepTiles();
    private ArrayList<Creep> creeps;
    private boolean shopOverlap;
    private int sIID;

    public Field() {
        initField();
    }

    private void initField() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.LIGHT_GRAY);
        tiles = createTiles();
        creeps = new ArrayList<>();
    }

    private Tile[][] createTiles() {
        ArrayList<Tile[]> tilesL = new ArrayList<>();
        ArrayList<Tile> tileLayer = new ArrayList<>();
        for (int y = 0; y < structure[0].length; y++) {
            for (int x = 0; x < structure[0][0].length; x++) {
                Tile t = new Tile(x * 50, y * 50, structure[0][y][x], structure[1][y][x]);
                tileLayer.add(t);
            }
            Tile[] tmp = new Tile[0];
            tmp = tileLayer.toArray(tmp);
            tileLayer.removeAll(tileLayer);
            tilesL.add(tmp);
        }

        Tile[][] returnVal = new Tile[0][0];
        returnVal = tilesL.toArray(returnVal);

        return returnVal;
    }

    private void createCreeps() {
        for (int i = 0; i < 5 * Game.round; i++) {
            try {
                creeps.add(new Creep(0));
            } catch (Exception ex) {
            }
        }
    }

    public void startGame() {
        createCreeps();
    }

    public void loadField(int[][][] field) {
        structure = field;
        tiles = createTiles();
    }

    private void nextRound() {
        boolean noCreeps = false;
        if (Game.round != 0 && creeps.size() >= 1) {
            int c = 0;
            for (Creep creep : creeps) {
                if (creep != null) {
                    if (creep.isDefeated()) {
                        c++;
                    }
                }
            }
            if (c == creeps.size()) {
                noCreeps = true;
            }
            if (noCreeps) {
                Game.round++;
                createCreeps();
            }
        }
    }

    private void drawTiles(Graphics g) {
        for (Tile[] tA : tiles) {
            for (Tile t : tA) {
                g.drawImage(groundTiles[t.getGroundType()], t.x, t.y, t.getWIDTH(), t.getHEIGHT(), this);
                g.drawImage(airTiles[t.getAirType()], t.x, t.y, t.getWIDTH(), t.getHEIGHT(), this);
                if (t.contains(Game.mouse)) {
                    if (t.isUsable()) {
                        g.setColor(new Color(1f, 1f, 1f, 0.4f));
                    } else {
                        g.setColor(new Color(1f, 0f, 0f, 0.2f));
                    }
                    g.fillRect(t.x, t.y, t.width, t.height);
                }
            }
        }
    }
    
    private void drawTowerRange(Graphics g){
        for (Tile[] tA : tiles) {
            for (Tile t : tA) {                    
                t.drawTowerRange(g);
            }
        }
    }
    
    int count = 0;

    private void drawCreeps(Graphics g) {
        if (creeps != null) {
            for (Creep c : creeps) {
                if (c.ingame()) {
                    c.drawCreep(g);
                    c.physic();
                } else if (count >= 120 / Game.round * 1.5 && !c.isDefeated()) {
                    c.spawn();
                    count = 0;
                }
            }
            count++;
        }
    }
    
    private void drawOverlappingContent(Graphics g){
        if(shopOverlap && Game.shop.isHoldingItem()){
            g.drawImage(airTiles[sIID], Game.mouse.x, Game.mouse.y, 50, 50, this);
        }else{
            shopOverlap=false;
        }
    }
    
    public void isOverlap(int id){
        shopOverlap=true;
        sIID=id;
    }

    public void isNotOverlap(){
        shopOverlap=false;
        sIID=-1;
    }
    
    public void changeAirTile(int x, int y, int id){
        tiles[y][x].setAirType(id);
    }
    
    public int[][][] getCurrentField() {
        int[][][] returnVal = new int[2][8][16];
        for (int y = 0; y < returnVal[0].length; y++) {
            for (int x = 0; x < returnVal[0][0].length; x++) {
                returnVal[0][y][x] = tiles[y][x].getGroundType();
                returnVal[1][y][x] = tiles[y][x].getAirType();
            }
        }
        structure = returnVal;
        return returnVal;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public Image getAirTile(int tile) {
        return airTiles[tile];
    }

    public Image getCreepTile(int creepType) {
        return creepTiles[creepType];
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawTiles(g);
        drawCreeps(g);
        if(Game.debug){
            drawTowerRange(g);
        }
        drawOverlappingContent(g);
        nextRound();
    }

}
