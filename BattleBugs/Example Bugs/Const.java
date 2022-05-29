package BattleBugs.Bugs;
import java.util.ArrayList;

import javax.swing.plaf.synth.SynthEditorPaneUI;

import BattleBugs.BattleBug;
import BattleBugs.BattleBug2012;
import BattleBugs.PowerUp;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.awt.Color;
import info.gridworld.actor.Actor;

public class Const extends BattleBug2012
{   
    //INSTANCE VARIABLE
    private boolean isTrapped = false;  //Determines whether Const is trapped in a area
    private boolean attack = false;     //Determines whether Const should attack
    Color color = Color.RED;
    
    public Const(int str, int def, int spd, String name, Color col)
    {
        super(str, def, spd, name, col);
    }
    public void act()
    {   
        //Getting the row and column of Const's Location
        int rowConst = getLocation().getRow();
        int colConst = getLocation().getCol();
        
        //Getting the ArrayList of powerUps
        ArrayList<Location> puLocs = getPowerUpLocs();

        //Sorting the puLocs ArrayList
        ArrayList<Location> puSorted = sortList(puLocs);
        
        //Getting the size of the poLocs ArrayList
        int numPu = puLocs.size();
        
        //Getting the nearest powerUp on the grid
        Location nearest;
        if(numPu > 0) {
            //The current nearest is at 0 in the ArrayList for now
            nearest = puSorted.get(0);
        }
        else { //There is currently no powerUps
            //Making the bug go diagonally instead, since there is no powerUps
            //Towards middle to avoid the rocks falling later
            if(rowConst >= 14) { //The row is above 14, Const is towards the bottom
                if(colConst >= 14)   //The col is above 14 (Bottom, Right)
                    nearest = new Location(rowConst-1, colConst-1);
                else    //The col is below 14 (Bottom, Left)
                    nearest = new Location(rowConst-1, colConst+1);
            }
            else {   //The row is below 14, Const is towards the top
                if(colConst >= 14)   //The col is above 14 (Top, Right)
                    nearest = new Location(rowConst+1, colConst-1);
                else    //The col is below 14 (Top, Left)
                    nearest = new Location(rowConst+1, colConst+1);
            }
        }
        
        //Getting all the actors in range
        ArrayList<Actor> actors = getActors();

        //Creating an ArrayList of enemy BattleBugs
        ArrayList<BattleBug> enemies = new ArrayList<BattleBug>();

        //Adding the BattleBug actors to the enemies ArrayList
        for(int i = 0; i < actors.size(); i++) {
            if(actors.get(i) instanceof BattleBug == true)
                enemies.add((BattleBug)actors.get(i));
        }
        
        //Position of enemy
        int posEn = -1;
        int posEn2 = -1;
        
        //Getting the stats of the enemy
        if(enemies.size() == 0)
            attack = false;
        else {
            if(enemies.size() > 0) { //There are enemies in range 
                //Checking if there is a enemy that Const can attack
                for(int i = 0; i < enemies.size(); i++) {
                    if(getStrength() - enemies.get(i).getDefense() >= 3) {
                        attack = true;
                        posEn = i;
                    }
                    else {
                        attack = false;
                        posEn2 = i;
                    }
                }
            }
        }
                
        //boolean to check if Const moved
        boolean moved = false;
        
        //Checking if Const should attack the enemy
        if(attack == true) { //Const should attack the enemy
            //Getting the direction towards the enemy
            int toEn = getDirectionToward(enemies.get(posEn).getLocation());
            
            //Check if Const is facing towards the enemy
            if(getDirection() == toEn) {
                move();
                attack();
                moved = true;
            }
            else {
                turnTo(toEn);
                attack();
                moved = true;
            }
        }
        else { //Const shouldn't or can't kill the enemy
            //Checking if there is a enemy in range
            if(posEn2 != -1) {
                //checking if the enemy can kill Const
                BattleBug en = enemies.get(posEn2);
                if(en.getStrength() - this.getDefense() >= 3) { //Enemy can kill Const
                    //Const should run away
                    //Getting the location of the enemy
                    Location enLoc = en.getLocation();
                    int enRow = enLoc.getRow();
                    int enCol = enLoc.getCol();
                    
                    //Getting the row & col distance between Const and the enemy
                    //int difR = Math.abs(this.getLocation().getRow() - enRow);
                    //int difC = Math.abs(this.getLocation().getCol() - enCol);
                    
                    //Making Const go 3 row and 3 col away from enemy
                    //Checking Const's location first
                    if(enRow >= 14 && rowConst >= 14) {   //The row is above 14, Const is towards the bottom
                        if(enCol >= 14 && colConst >= 14 )   //The col is above 14 (Bottom, Right)
                            nearest = new Location(enRow-3, enCol-3);
                        else    //The col is below 14 (Bottom, Left)
                            nearest = new Location(enRow-3, enCol+3);
                    }
                    else {    //The row is below 14, Const is towards the top
                        if(enCol >= 14 && colConst >= 14)   //The col is above 14 (Top, Right)
                           nearest = new Location(enRow+3, enCol-3);
                        else    //The col is below 14 (Top, Left)
                           nearest = new Location(enRow+3, enCol+3);
                    }
                }   
                //Else the enemy can't do anything to Const
            }
            //Else there is no enemy
        }

        // Avoiding the rock walls falling
        if (getNumAct() % 40 >= 38) { // There are less than 2 steps until rocks fall
            // Towards middle to avoid the rocks falling later
            if (rowConst > 14) { // The row is above 14, Const is towards the bottom
                if (colConst > 14) // The col is above 14 (Bottom, Right)
                    nearest = new Location(rowConst - 1, colConst - 1);
                else // The col is below 14 (Bottom, Left)
                    nearest = new Location(rowConst - 1, colConst + 1);
            } else { // The row is below 14, Const is towards the top
                if (colConst > 14) // The col is above 14 (Top, Right)
                    nearest = new Location(rowConst + 1, colConst - 1);
                else // The col is below 14 (Top, Left)
                    nearest = new Location(rowConst + 1, colConst + 1);
            }
        }

        //Getting the diagonal location to nearest
        Location diagToNearest = getDiag(nearest);
        
        //Getting the direction to the diagonal nearest
        int dir = getDirectionToward(diagToNearest);
        
        //Checking if Const is trapped, meaning he cannot move
        if(isTrapped == true) {
            //Move Const so that the regulator won't kill Const
            move();
            
            //Sets isTrapped to false
            isTrapped = false;
            
            //Setting moved to true, since Const has moved. Cannot move more than once
            moved = true;
        }
                
        //Moving or Turning Const to nearest location
        if(moved == false && isTrapped == false) {
            if(getDirection() == dir) { //Const is facing towards nearest Location
                if(canMove()) { //Checking if Const can move
                    if(canMove2Away()) { //Determining if Const can move 2 spaces   
                        //Checking if Const should move 2 spaces
                        if(getDist(diagToNearest) >= 2)//if distance between nearest and Const >= 2, move 2
                            move2();
                        else//distance between nearest and Const < 2, so it should just move 1 space
                            move();
                    }//Const cannot move 2 spaces yet
                    else
                        move();//just move 1 space
                }
                else { //Const cannot move at all
                    //Const is trapped
                    isTrapped = true;
                    
                    //Nearest becomes nearest adjacent location
                    nearest = getEmpty();
                    
                    //Getting the direction towards nearest
                    dir = getDirectionToward(nearest);
                    
                    //Moving or turning Const
                    if(getDirection() == dir)
                        move();
                    else
                        turnTo(dir);
                }
            }
            else//Const is not facing the nearest location
                turnTo(dir);//turning towards the nearest location
        }
        if (attack)
            Const.this.setColor(Color.RED);
        else
            Const.this.setColor(Const.this.getColor());
    }
    
    //Method for getting the distance from Const to specified location
    public double getDist(Location nearest)
    {
        //Getting the row and col of Const
        int rowConst = getLocation().getRow();
        int colConst = getLocation().getCol();
        //Distance Formula
        double powX = Math.pow(rowConst - nearest.getRow(), 2);
        double powY = Math.pow(colConst - nearest.getCol(), 2);
        double sumXY = powX + powY;
        double sqrtXY = Math.sqrt(sumXY);
        //Returning the distance to specified location
        return sqrtXY;
    }
    
    //Method to get the Diagonal location to nearest
    public Location getDiag(Location nearest)
    {
        //Getting the row and col of Const
        int rowConst = getLocation().getRow();
        int colConst = getLocation().getCol();
        
        //Getting the distance between Const's col and nearest col and
        //the distance between Const's row and nearest row
        int difX = Math.abs(rowConst - nearest.getRow());
        int difY = Math.abs(colConst - nearest.getCol());
    
        Location dLoc = nearest;
        //Checking which if Const should approach nearest by col or row
        if(difX > difY) {   //Const should go by row
            //Checking to which direction the nearest is to Const
            if(nearest.getRow() > rowConst && colConst != nearest.getCol()) { //nearest is below Const
                int dRow = rowConst + difY; 
                int dCol = nearest.getCol();  
                dLoc = new Location(dRow, dCol);                    
            }
            else if(nearest.getRow() < rowConst && colConst != nearest.getCol()) { //nearest is above Const
                //Getting the diagonal position of the nearest
                int dRow = rowConst - difY;
                int dCol = nearest.getCol();
                dLoc = new Location(dRow, dCol);                    
            }
            else { //nearest and Const are in the same row
                dLoc = nearest;
            }
        }
        else if(difX < difY) {  //Const should go by col
            if(nearest.getCol() > colConst && rowConst != nearest.getRow()) { //nearest is right of Const
                //Getting the diagonal position of the nearest
                int dRow = nearest.getRow();
                int dCol = colConst + difX;
                dLoc = new Location(dRow, dCol);
            }
            else if(nearest.getCol() < colConst && rowConst != nearest.getRow()) { //nearest is left of Const
                //Getting the diagonal position of the nearest
                int dRow = nearest.getRow();
                int dCol = colConst - difX;
                dLoc = new Location(dRow, dCol);
            }
            else { //nearest and Const are in the same column
                dLoc = nearest;
            }
        }
        else { //The difference in row & col is the same
            dLoc = nearest;
        }  
        return dLoc;
    }
    
    //Gets the nearest empty adjacent location
    public Location getEmpty()
    {
        //Getting all the emptyAdjacentLocations to Const
        ArrayList<Location> empty = getEmptyAdjacentLocations();
        
        //Index for nearest
        int index = 0;
        
        //Getting the distance between the first element and Const
        double dist = getDist(empty.get(0));
        
        //Getting the nearest adjacent location
        for(int i = 1; i < empty.size(); i++) {
            double dist2 = getDist(empty.get(i));
            
            if(dist > dist2) {
                dist = dist2;
                index = i;
            }
        }
        return empty.get(index);
    }
    
    //Sorts an ArrayList of Locations
    public ArrayList<Location> sortList(ArrayList<Location> loc)
    {
        for (int i = 0; i < loc.size(); i++) {
            for (int j = i; j < loc.size(); j++) {
                if (this.getDist(loc.get(i)) > this.getDist(loc.get(j))) {
                    Location temp = loc.get(i);
                    loc.set(i,loc.get(j)) ;
                    loc.set(j,temp);
                }
            }
        }
        return loc;
    }
    
    //Accessor
    public boolean shouldAttack()
    {
        return attack;
    }
    
    /*
    //Returns a boolean determining whether a adjacent location has been occupied
    public boolean isOccupied()
    {
        ArrayList<Location> getOccupied = getOccupiedAdjacentLocations();
        
        if(getOccupied.size() > 0)//there is a occupied space
            return true;
        else//there is no occupied space
            return false;
    }*/
    
    //Gets the opposite direction that the bug is facing
    public int oppDir(Location loc)
    {
        int dir = getDirection();
        
        if(dir == 0)//NORTH
            return 180;//SOUTH
        else if(dir == 90)//EAST
            return 270;//WEST
        else if(dir == 180)//SOUTH
            return 0;//NORTH
        else if(dir == 270)//WEST
            return 90;//EAST
        else if(dir == 45)//NORTHEAST
            return 225;//SOUTHWEST
        else if(dir == 135)//SOUTHEAST
            return 315;//NORTHWEST
        else if(dir == 225)//SOUTHWEST
            return 45;//NORTHEAST
        else//NORTHWEST
            return 135;//SOUTHEAST
    } 
    //Gets the type of power up at a specified location
    public int getPowerUpType(Location loc) {
        //Getting the ArrayLists
        ArrayList<Location> locations = getPowerUpLocs();
        ArrayList<PowerUp> pow = getPowerUps();

        int index = -1;
        for(int i = 0; i < locations.size(); i++) {
            if(locations.get(i).equals(loc))
                index = i;
        }

        if(index == -1)
            return 0; //None 
        else {
            PowerUp type = pow.get(index);
            if(type.toString().equals("Speed Power Up"))
                return 1; //Speed
            else if(type.toString().equals("Defense Power Up"))
                return 2; //Defense
            else if(type.toString().equals("Strength Power Up"))
                return 3; //Strength
        }
        return 0;   //None
    }
}
