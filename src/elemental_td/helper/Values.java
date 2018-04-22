/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elemental_td.helper;

/**
 *
 * @author Pascal
 */
public final class Values {
    public static final int enemyHp=100,ancientHp=500,startCoins=100;
    public final static int[] enemyHpMultiplierer={1,2,5,10};
    public final static int[] enemyAncientDamage={1,5,10,50};
    public static int currentHp=ancientHp;
    public static int currentCoins=startCoins;
    
    public static enum gTiles{
        ground,path,ancient
    }
    
    public static enum aTiles{
        air,ancient,tower_b,tower_m,tower_l,tower_a,trash
    }
    
    public static enum eTiles{
        enemyS,enemyM,enemyL,enemyB
    }
}
