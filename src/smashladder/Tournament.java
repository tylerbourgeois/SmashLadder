/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smashladder;
import java.io.*;
import java.util.*;
/**
 *
 * @author tybik
 */
public class Tournament {
    private Player[] players = new Player[0];
    private Player[] seeded_players;
    private Game[] games = new Game[0];
    private String file;
    private String name;
    public Tournament(String new_file,String new_name){
        file = new_file;
        name = new_name;
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            for(String line; (line = br.readLine()) != null; ) {
                String[] info = line.split("\\s");
                int elo = Integer.parseInt(info[1]);
                Player p = new Player(info[0],elo);
                
                Player[] newArray = new Player[players.length + 1];
                for(int i = 0;i < players.length;i++){
                    newArray[i] = players[i];
                }
                newArray[newArray.length-1] = p;
                players = newArray;
            }
        }catch(IOException e) {
            System.out.print("Exception");
        }
        setSeeds();
        roundOne();
    }
    
    public boolean matchCalc(Player p1, Player p){
        if((p1.getSeed() >= 5 && p.getSeed() <=5) || (p1.getSeed() >= 5 && p.getSeed() >=5) || (p.getSeed() >= 5 && p1.getSeed() <=5)){
            if((p1.isInMatch() != true )&& (p.isInMatch() != true)){ 
                if((p1.isPlayedAllSets() == false) && (p.isPlayedAllSets() == false)){    
                    if((p1.hasPlayed(p) != true) || (p.hasPlayed(p1) != true)){
                        return true;
                    }
                }    
            }
        }
        return false;
    }
    public void findMatch(Player p1){
        for(Player p : players){
            if(p.getSetWins() == p1.getSetWins() && p.getName().compareTo(p1.getName()) != 0){  
                if(matchCalc(p1,p)){
                    System.out.println(p1.getName() + " vs. " + p.getName());
                    p1.setInMatch(true);
                    p.setInMatch(true);
                    this.addGame(new Game(p,p1));
                    return;
                }
            }
        }
        for(Player player : players){
            if(player.getSetWins() == p1.getSetWins()-1 && player.getName().compareTo(p1.getName()) != 0){  
                if(matchCalc(p1,player)){
                    System.out.println(p1.getName() + " vs. " + player.getName());
                    p1.setInMatch(true);
                    player.setInMatch(true);
                    this.addGame(new Game(player,p1));
                    return;
                }
            }
        }
        for(Player p2 : players){
            if(p2.getSetWins()-1 == p1.getSetWins() && p2.getName().compareTo(p1.getName()) != 0){  
                if(matchCalc(p1,p2)){
                    System.out.println(p1.getName() + " vs. " + p2.getName());
                    p1.setInMatch(true);
                    p2.setInMatch(true);
                    this.addGame(new Game(p2,p1));
                    return;
                }
            }
        }
        for(Player p3 : players){
            if(p3.getSetWins()-2 == p1.getSetWins() && p3.getName().compareTo(p1.getName()) != 0){  
                if(matchCalc(p1,p3)){
                    System.out.println(p1.getName() + " vs. " + p3.getName());
                    p1.setInMatch(true);
                    p3.setInMatch(true);
                    this.addGame(new Game(p3,p1));
                    return;
                }
            }
        }
        for(Player p4 : players){
            if(p4.getSetWins() == p1.getSetWins()-2 && p4.getName().compareTo(p1.getName()) != 0){  
                if(matchCalc(p1,p4)){
                    System.out.println(p1.getName() + " vs. " + p4.getName());
                    p1.setInMatch(true);
                    p4.setInMatch(true);
                    Game newgame = new Game(p4,p1);
                    this.addGame(newgame);
                    return;
                }
            }
        }
//        for(Player p : players){
//            if(p.getWins() == p1.getWins() - 1 && p.getName().compareTo(p1.getName()) != 0){
//                System.out.println(p1.getName() + " plays " + p.getName());
//                break;
//            }
//        }
    }
    
    public void reportMatches(){
        int count = 1;
        System.out.println("Active Games:");
        for(Game g : games) if(g.isActive()) System.out.println((count++) + ": " + g);
        Game[] activeGames = new Game[count];
        count = 0;
        for(Game g : games) if(g.isActive()) activeGames[count++] = g;
        int choice = getChoice(1,(activeGames.length - 1),"What match do you want to report? ");
        if(choice == 100) return;
        Game g = activeGames[choice-1];
        int p1Wins = 0,p2Wins =0;
        while(p1Wins == p2Wins || (p1Wins < 2 && p2Wins < 2)){
            p1Wins = getChoice(0,2,("How many matches did " + g.getP1().getName() + " win? "));
            p2Wins = getChoice(0,2,("How many matches did " + g.getP2().getName() + " win? "));
            if(p1Wins == 100 || p2Wins == 100) return;
        }
        g.playGame(p1Wins,p2Wins);
    }
    
    private void roundOne(){
        System.out.println("Round 1 Matches: ");
        shuffle(players);
        for(Player p : players) findMatch(p);
        System.out.println();
    }
    
    public void addGame(Game newGame){

        Game[] newArray = new Game[games.length + 1];
                for(int i = 0;i < games.length;i++){
                    newArray[i] = games[i];
                }
                newArray[newArray.length-1] = newGame;
                games = newArray;
    }
    
    public void setSeeds(){
        for(int i = 0; i<players.length;i++){
            for(int j = 0;j<players.length-1;j++){
                if(players[j].getElo()<players[j+1].getElo()){
                    Player temp = players[j];
                    players[j] = players[j+1];
                    players[j+1] = temp;
                }
            }
        }
        seeded_players = new Player[players.length];
        for(int i = 0;i<players.length;i++){
            seeded_players[i]=players[i];
            players[i].setSeed(i+1);
        }
    }
    public void printPlayers(){
        for(Player p : seeded_players){
            System.out.println(p.getSeed() + ": " + p.getName());
        }
    }
    public void printActiveGames(){
        for(Game g : games)
            if(g.isActive()) System.out.println(g.getP1() + " is playing " + g.getP2());
        
    }
    
    public void printPastGames(){
        System.out.println("Played Sets:");
        for(Game g : games) if(!g.isActive()) System.out.println(g);
    }
    
    public void deleteMatch(){
        int count = 1;
        System.out.println("Played Sets:");
        for(Game g : games) if(!g.isActive()) System.out.println(((count++)) + ": " + g);
        Game[] played_games = new Game[count];
        count = 0;
        for(Game g : games) if(!g.isActive()) played_games[count++] = g;
        int choice = getChoice(1,(played_games.length - 1), "What match do you want to delete? ");
        if(choice == 100) return;
        Game game  = played_games[choice - 1];
        
        Player winner,loser;
        boolean p1Won,p2Won;
        if(game.getP1W() > game.getP2W()){
            winner = game.getP1();;
            p1Won = true;
            loser = game.getP2();
            p2Won = false;
            winner.modifyGameScores(loser, game.getP1W(),game.getP2W(),p1Won);
            loser.modifyGameScores(winner, game.getP2W(),game.getP1W(),p2Won);
        }
        else if(game.getP1W() < game.getP2W()){
            winner = game.getP2();
            p2Won = true;
            loser = game.getP1();
            p1Won = false;
            winner.modifyGameScores(loser, game.getP2W(),game.getP1W(), p2Won);
            loser.modifyGameScores(winner, game.getP1W(), game.getP2W(), p1Won);
        }
        game.setP1W(0);
        game.setP2W(0);
        
        Game[] new_array = new Game[this.games.length - 1];
        count = 0;
        for(Game g : games) if(!(g == game)) new_array[count++] = g;
        games = new_array;
    }
    
    public void editMatch(){
        int count = 1;
        System.out.println("Played Sets:");
        for(Game g : games) if(!g.isActive()) System.out.println(((count++)) + ": " + g);
        Game[] played_games = new Game[count];
        count = 0;
        for(Game g : games) if(!g.isActive()) played_games[count++] = g;
        int choice = getChoice(1,(played_games.length - 1), "What match do you want to edit? ");
        if(choice == 100) return;
        Game g  = played_games[choice - 1];
        
        Player winner,loser;
        boolean p1Won,p2Won;
        if(g.getP1W() > g.getP2W()){
            winner = g.getP1();;
            p1Won = true;
            loser = g.getP2();
            p2Won = false;
            winner.modifyGameScores(loser, g.getP1W(),g.getP2W(),p1Won);
            loser.modifyGameScores(winner, g.getP2W(),g.getP1W(),p2Won);
        }
        else if(g.getP1W() < g.getP2W()){
            winner = g.getP2();
            p2Won = true;
            loser = g.getP1();
            p1Won = false;
            winner.modifyGameScores(loser, g.getP2W(),g.getP1W(), p2Won);
            loser.modifyGameScores(winner, g.getP1W(), g.getP2W(), p1Won);
        }
        g.setP1W(0);
        g.setP2W(0);
        
        System.out.println(g);
        int p1Wins = 0,p2Wins = 0;
        while(p1Wins == p2Wins || (p1Wins < 2 && p2Wins < 2)){
            p1Wins = getChoice(0,2,("How many matches did " + g.getP1().getName() + " win? "));
            p2Wins = getChoice(0,2,("How many matches did " + g.getP2().getName() + " win? "));
            if(p1Wins == 100 || p2Wins == 100) return;
        }
        g.playGame(p1Wins,p2Wins);
            
    }
    
    public void addMatch(){
        int count = 0;
        for(Player p : players) if(!p.isInMatch() || p.isPlayedAllSets()) count++;
        Player[] available = new Player[count];
        count = 0;
        for(Player p: players) if(!p.isInMatch() || p.isPlayedAllSets()) available[count++] = p;
        sort(available);
        count = 1;
        for(Player p : available) System.out.println(count++ + ": " + p);
        int choice = getChoice(1,(available.length),"What player do you want to add a match for? ");
        if(choice == 100) return;
        Player p1 = available[choice-1];
        Player[] new_available = new Player[available.length-1];
        count = 0;
        for(Player p : available) if(!p.getName().equals(p1.getName())) {
            System.out.println((count + 1) + ": " + p);
            new_available[count++] = p;
        }
        choice = getChoice(1,new_available.length,("What player will play " + p1.getName() + "? "));
        if(choice == 100) return;
        Player p2 = new_available[choice-1];
        this.addGame(new Game(p1,p2));
        p1.setInMatch(true);
        p2.setInMatch(true);
        System.out.println(p1.getName() + " vs. " + p2.getName());
    }
    
    public void printStandings(){
        for(Player p : players) System.out.println(p);
    }
    public void findMatch(){
        int count = 0;
        for(Player p : players)if(!(p.isInMatch() || p.isPlayedAllSets()))count++;
        Player[] available = new Player[count];
        count = 0;
        for(Player p: players)if(!(p.isInMatch() || p.isPlayedAllSets())) available[count++] = p;
        sort(available);
        for(int i = 0; i< available.length ; i++) System.out.println((i+1) + ": " + available[i]);
        int choice = getChoice(1,(available.length),"What player do you want to find a match for? ");
        if(choice == 100) return;
        if(!available[choice-1].isPlayedAllSets())
            findMatch(available[choice-1]);
        else
            System.out.println(available[choice-1].getName() + " has already played all of their sets!");
    }
    private boolean allTrue(){
        for(Player p : players){
            if(p.isPlayedAllSets() == false){
                return false;
            }
        }
        return true;
    }
    public void finalFour(){
        //if(allTrue()){
            Player[] top4=new Player[4];
            for (int i=0;i<4;i++) top4[i]=seeded_players[i];
            sortPct(top4);
            for (int i = 4;i<seeded_players.length;i++){
                if (seeded_players[i].getWinPct()>top4[3].getWinPct())
                    top4[3]=seeded_players[i];
                else if (seeded_players[i].getGameWinPct()>top4[3].getGameWinPct())
                    top4[3]=seeded_players[i];
                sortPct(top4);
            }
            for (int i=0;i<4;i++)
                System.out.println((i+1)+": "+top4[i].getName()+": "+top4[i].getWinPct());
            System.out.println();
        //}
        //else System.out.println("Please play all sets first!");
    }
    
    public static int getChoice(int min, int max, String message){
        Scanner k = new Scanner(System.in); 
        int choice = (-1);
        do {
            try {
                System.out.print(message);
                choice = k.nextInt();
                if(choice == 100) return choice;
            } catch (InputMismatchException e) {
                System.out.print("Invalid choice. ");
            }
            k.nextLine(); 
        } while (choice < min || choice > max);
        return choice;
    }
//    public void writeToFile(String string) 
//        throws IOException {
//        String fileName = string + ".txt";
//        FileWriter fileWriter = new FileWriter(fileName);
//        PrintWriter printWriter = new PrintWriter(fileWriter);
//        printWriter.print(string + ":");
//        printWriter.close();
//        }
    public static void sort(Player[] arr){
        Player temp;
        for (int i=0;i<arr.length;i++){
            for (int j=0;j<arr.length-1;j++){
                if (arr[j+1].getSeed()<arr[j].getSeed()){
                    temp=arr[j+1];
                    arr[j+1]=arr[j];
                    arr[j]=temp;
                }
            }
        }
    }
    public static void sortPct(Player[] arr){
        Player temp;
        for (int i=0;i<arr.length;i++){
            for (int j=0;j<arr.length-1;j++){
                if (arr[j+1].getWinPct()>arr[j].getWinPct()){
                    temp=arr[j+1];
                    arr[j+1]=arr[j];
                    arr[j]=temp;
                }
                else if (arr[j+1].getGameWinPct()>arr[j].getGameWinPct()){
                    temp=arr[j+1];
                    arr[j+1]=arr[j];
                    arr[j]=temp;
                }
            }
        }
    }
    public static void shuffle(Player[] a) {
        int n = a.length,change;
        Player temp;
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            change = i + random.nextInt(n - i);
            temp=a[i];
            a[i]=a[change];
            a[change]=temp;
        }
    }
}
