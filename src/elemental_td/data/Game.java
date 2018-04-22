/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elemental_td.data;

import elemental_td.helper.Audio;
import elemental_td.helper.MAdapter;
import elemental_td.helper.Values;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author Pascal
 */
public class Game extends JFrame implements ActionListener {

    private final Audio music = new Audio(1);
    private final int TIMERDELAY = 1000, TIMERDELAY_MULITPLIKATOR = 60;
    private final Timer timer1 = new Timer(TIMERDELAY / TIMERDELAY_MULITPLIKATOR, this);
    private final int WIDTH = 800, HEIGHT = 550;
    private boolean pause = false;
    private PauseScreen pauseScreen;
    public static int victoryCondition = -1;

    public static Field field;
    public static Shop shop;
    public static Point mouse = new Point(0, 0);
    public static int gameTimeSec = 0, gameTimeMin = 0;
    public static int pauseTimeSec = 0, pauseTimeMin = 0;
    public static int round = 0;
    public static boolean start = false, debug = false;

    public Game() {
        initGame();
    }
    
    private void initGame() {
        field = new Field();
        shop = new Shop();
        pauseScreen = new PauseScreen(WIDTH, HEIGHT);
        shop.setBounds(new Rectangle(new Point(0, 400), new Dimension(800, 150)));

        this.add(shop);
        this.add(field);
        this.revalidate();

        this.setTitle("Elemental Tower Defense by Pascal Musiolik");
        this.setIconImage(new ImageIcon(Game.class.getClass().getResource("/elemental_td/data/res/icon.png")).getImage());

        this.addMouseListener(new MAdapter());
        this.addMouseMotionListener(new MAdapter());
        this.addKeyListener(new TAdapter());

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        this.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setUndecorated(true);
        this.setLocationRelativeTo(null);

        timer1.start();
    }

    @Override
    public void paint(Graphics g) {
        if (!pause) {
            super.paint(g);
        } else {
            pauseScreen.paintComponent(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        isDefeat();
        isVictory();
    }

    private void isDefeat() {
        if (Values.currentHp <= 0) {
            JOptionPane.showMessageDialog(rootPane, "Niederlage\nDanke fürs Spielen!");
            System.exit(0);
        }
    }

    private void isVictory() {
        if (victoryCondition >= 30) {
            if (round == victoryCondition + 1) {
                JOptionPane.showMessageDialog(rootPane, "Herzlichen Glückwunsch,\nDu hast gewonnen!\nJetzt kannst du dir ein Eis holen.");
            }
        }
    }

    private class TAdapter implements KeyListener {

        private Set<Integer> pressed = new HashSet<>();

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            pressed.add(e.getKeyCode());
            if (pressed.size() > 1) {
                if (pressed.contains(KeyEvent.VK_CONTROL) && pressed.contains(KeyEvent.VK_E)) {
                    if (JOptionPane.showConfirmDialog(rootPane, "Sind Sie sich sicher,\ndass Sie das Programm beenden wollen?", "Exit", JOptionPane.YES_NO_OPTION) == 0) {
                        System.exit(0);
                    } else {
                        pressed.removeAll(pressed);
                        return;
                    }

                }
                if (pressed.contains(KeyEvent.VK_CONTROL) && pressed.contains(KeyEvent.VK_S)) {
                    if (JOptionPane.showConfirmDialog(rootPane, "Wollen Sie den aktuellen Spielstand speichern?", "Speichern", JOptionPane.YES_NO_OPTION) == 0) {
                        String dateiname = JOptionPane.showInputDialog(rootPane, "Geben Sie einen Dateinamen an:", "Speichern", JOptionPane.QUESTION_MESSAGE);
                        elemental_td.helper.FieldLoader.saveField(field.getCurrentField(), dateiname);
                        pressed.removeAll(pressed);
                        return;
                    } else {
                        pressed.removeAll(pressed);
                        return;
                    }
                }
                if (pressed.contains(KeyEvent.VK_CONTROL) && pressed.contains(KeyEvent.VK_L)) {
                    if (JOptionPane.showConfirmDialog(rootPane, "Wollen Sie einen alten Spielstand laden?", "Laden", JOptionPane.YES_NO_OPTION) == 0) {
                        field.loadField(elemental_td.helper.FieldLoader.getSaveState());
                        pressed.removeAll(pressed);
                        return;
                    } else {
                        pressed.removeAll(pressed);
                        return;
                    }
                }
                if (pressed.contains(KeyEvent.VK_ALT) && pressed.contains(KeyEvent.VK_CONTROL) && pressed.contains(KeyEvent.VK_D)) {
                    debug = !debug;
                }
            } else if (pressed.contains(KeyEvent.VK_P)) {
                pause = !pause;
                if (pause) {
                    music.stop();
                } else {
                    music.loop();
                }
            } else if (pressed.contains(KeyEvent.VK_B) && round == 0) {
                String[] optionen = {"Leicht", "Mittel", "Schwer", "Yasuo"};
                int s = JOptionPane.showOptionDialog(rootPane, "Wählen sie einen Schwierigkeitsgrad aus", "Schwierigkeit", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(Game.class.getClass().getResource("/elemental_td/data/res/icon.png")), optionen, null);
                if (s != -1) {
                    victoryCondition = Values.difficulty[s];
                    Values.currentHp=Values.hpDiff[s];
                    round = 1;
                    music.loop();
                    field.startGame();
                }else{
                    pressed.removeAll(pressed);
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            pressed.remove(e.getKeyCode());
        }

    }
}
