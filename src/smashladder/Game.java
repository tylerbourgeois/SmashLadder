/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smashladder;

/**
 *
 * @author tybik
 */
public class Game {
    private Player p1;
    private Player p2;
    private int p1W;
    private int p2W;
    private boolean active;
    
    public Game(Player player1, Player player2){
        p1 = player1;
        p2 = player2;
        active = true;
    }

    public Player getP1() { return p1; }
    public void setP1(Player p1) { this.p1 = p1;}
    public Player getP2() { return p2;}
    public void setP2(Player p2) {this.p2 = p2;}
    public int getP1W() {return p1W;}
    public void setP1W(int p1W) {this.p1W = p1W;}
    public int getP2W() {return p2W;}
    public void setP2W(int p2W) {this.p2W = p2W;}
    public boolean isActive() {return active;}
    public void setActive(boolean active) {this.active = active;}
    
    @Override
    public String toString(){
        return p1.getName() + " " + p2.getName()+" "+p1W+" "+p2W;   //      IMPORTANT
    }
    public void playGame(int p1Wins,int p2Wins){
        active = false;
        p1.setInMatch(false);
        p2.setInMatch(false);
        p1W=p1Wins;
        p2W=p2Wins;
        String player1_before = p1.getName() + ": " + p1.getSetWins() + "-" + p1.getSetLosses();
        String player2_before = p2.getName() + ": " + p2.getSetWins() + "-" + p2.getSetLosses();
        if(p1W > p2W){
            p1.Won(p1W,p2W,p2);
            p2.Lost(p2W,p1W,p1);
            System.out.println(p1.getName() + " Won " + p2.getName() + " Lost" );
            System.out.println(player1_before + " -> " + p1.getSetWins() + "-" + p1.getSetLosses());
            System.out.println(player2_before + " -> " + p2.getSetWins() + "-" + p2.getSetLosses());
        }
        else if(p1W < p2W){
            p1.Lost(p1W,p2W,p2);
            p2.Won(p2W,p1W,p1);
            System.out.println(p2.getName() + " Won " + p1.getName() + " Lost" );
            System.out.println(player2_before + " -> " + p2.getSetWins() + "-" + p2.getSetLosses());
            System.out.println(player1_before + " -> " + p1.getSetWins() + "-" + p1.getSetLosses());
        }
    }
    
    
}
