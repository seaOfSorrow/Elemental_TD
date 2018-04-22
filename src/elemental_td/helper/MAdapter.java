/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elemental_td.helper;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import elemental_td.data.Game;
import java.awt.Point;
/**
 *
 * @author Pascal
 */
public class MAdapter implements MouseListener,MouseMotionListener{

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Game.shop.pressMouse(e.getButton());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Game.mouse=new Point(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Game.mouse=new Point(e.getX(), e.getY());
    }
    
}
