/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elemental_td.helper;

import java.awt.Component;
import java.awt.Image;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author Pascal
 */
public class ImageLoader {

    private static Component c = new Component() {
    };

    public static Image[] getGroundTiles() {
        ArrayList<Image> tilesA = new ArrayList<>();
        try {
            Image tileset = ImageIO.read(ImageLoader.class.getResource("/elemental_td/data/res/groundTiles.png"));
            int scaler = tileset.getHeight(null) / 50;
            for (int i = 0; i < scaler; i++) {
                tilesA.add(c.createImage(new FilteredImageSource(tileset.getSource(), new CropImageFilter(0, i * 50, 50, 50))));
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Internal error\nProgram is going to exit now!");
            System.exit(1);
        }
        Image[] tilesR = new Image[tilesA.size()];
        for (int i = 0; i < tilesR.length; i++) {
            tilesR[i] = tilesA.get(i);
        }
        return tilesR;
    }

    public static Image[] getAirTiles() {
        ArrayList<Image> tilesA = new ArrayList<>();
        try {
            Image tileset = ImageIO.read(ImageLoader.class.getResource("/elemental_td/data/res/airTiles.png"));
            int scaler = tileset.getHeight(null) / 50;
            for (int i = 0; i < scaler; i++) {
                tilesA.add(c.createImage(new FilteredImageSource(tileset.getSource(), new CropImageFilter(0, i * 50, 50, 50))));
            }
        } catch (IOException ex) {
            ex.toString();
            JOptionPane.showMessageDialog(null, "Internal error\nProgram is going to exit now!");
            System.exit(1);
        }
        Image[] tilesR = new Image[tilesA.size()];
        for (int i = 0; i < tilesR.length; i++) {
            tilesR[i] = tilesA.get(i);
        }
        return tilesR;
    }

    public static Image[] getCreepTiles() {
        ArrayList<Image> tilesA = new ArrayList<>();
        try {
            Image tileset = ImageIO.read(ImageLoader.class.getResource("/elemental_td/data/res/creepTileset.png"));
            int scaler = tileset.getHeight(null) / 128;
            for (int i = 0; i < scaler; i++) {
                tilesA.add(c.createImage(new FilteredImageSource(tileset.getSource(), new CropImageFilter(0, i * 128, 128, 128))));
            }
        } catch (IOException ex) {
            ex.toString();
            JOptionPane.showMessageDialog(null, "Internal error\nProgram is going to exit now!");
            System.exit(1);
        }
        Image[] tilesR = new Image[tilesA.size()];
        for (int i = 0; i < tilesR.length; i++) {
            tilesR[i] = tilesA.get(i);
        }
        return tilesR;
    }
}
