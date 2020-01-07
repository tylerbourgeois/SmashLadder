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
public class Player {
    private String name;
    private int setWins;
    private int setLosses;
    private int gameWins;
    private int gameLosses;
    private int seed;
    private int elo;
    private boolean inMatch;
    private boolean playedAllSets;
    private Player[] played = new Player[0];
    public Player(String playername, int init_elo){
        name = playername;
        elo = init_elo;
        setWins = 0;
        setLosses = 0;
        gameWins = 0;
        gameLosses = 0;
        inMatch = false;
        playedAllSets = false;
    }
    public void Won(int gW, int gL, Player p){
        setWins++;
        gameWins += gW;
        gameLosses += gL;
        if((setLosses + setWins) >= 5){
            playedAllSets = true;
        }
        Player[] newArray = new Player[played.length + 1];
        for(int i=0;i<played.length;i++)
            newArray[i] = played[i];
        newArray[newArray.length-1] = p;
        played = newArray;
    }
     public void Lost(int gW, int gL, Player p){
        setLosses++;
        gameLosses += gL;
        gameWins += gW;
        if((setLosses + setWins) >= 5){
            playedAllSets = true;
            System.out.println(this.getName() + " " + getWinPct() + "%");
        }
        Player[] newArray = new Player[played.length + 1];
        for(int i=0;i<played.length;i++){
            newArray[i] = played[i];
        }
        newArray[newArray.length-1] = p;
        played = newArray;
     }
    public double getWinPct(){return ((double) setWins)/((double)setLosses+(double)setWins);}
    public double getGameWinPct(){return ((double) gameWins)/((double)gameLosses+(double)gameWins);}
    public boolean hasPlayed(Player p) {
        for(Player player : played) if (player==p) return true;
        return false;
    }
    public boolean isPlayedAllSets() {return playedAllSets;}
    public void setPlayedAllSets(boolean playedAllSets) {this.playedAllSets = playedAllSets;}
    public boolean isInMatch() {return inMatch;}
    public void setInMatch(boolean inMatch) {this.inMatch = inMatch;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public int getSetWins() {return setWins;}
    public void setSetWins(int setWins) {this.setWins = setWins;}
    public int getSetLosses() {return setLosses;}
    public void setSetLosses(int setLosses) {this.setLosses = setLosses;}
    public int getElo() {return elo;}
    public void setElo(int elo) {this.elo = elo;}
    public int getSeed() {return seed;}
    public void setSeed(int seed) {this.seed = seed;}
    public int getGameWins(){return gameWins;}
    public int getGameLosses(){return gameLosses;}
    public Player[] getPlayed(){ return played;}
    public void setPlayed(Player[] played){this.played = played;}
    
    public void modifyGameScores(Player player, int wins, int losses, boolean won){
        this.gameWins -= wins;
        this.gameLosses -= losses;
        if(won) setWins--;
        else if(!won) setLosses--;
        Player[] new_array = new Player[this.played.length - 1];
        int count = 0;
        for(Player p : this.played) if(!player.getName().equals(p.getName())) new_array[count++] = p;
        this.played = new_array;
    }
    @Override
    public String toString(){
        return name + ": Sets (" + setWins + "-" + setLosses +")" + " Games (" + gameWins + "-" + gameLosses + ")";
    }
}
