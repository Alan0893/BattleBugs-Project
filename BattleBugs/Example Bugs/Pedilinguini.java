//Class Name: 
//Pedilinguini

package BattleBugs.Bugs;
import java.util.ArrayList;

import BattleBugs.BattleBug;
import BattleBugs.BattleBug2012;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.awt.Color;
import info.gridworld.actor.Actor;

public class Pedilinguini extends BattleBug2012
{
    public Pedilinguini(int str, int def, int spd, String name, Color col)
    {
        super(str, def, spd, name, col, "AudioCutter_record20210604230310 (1)", null);
    }
    
    boolean tryEsc = false;
/*  public boolean canAtt(Location l1, Location l2)
    {
        boolean returnBoolean = false;
        int l1Col = l1.getCol();
        int l2Col = l2.getCol();
        int l1Row = l1.getRow();
        int l2Row = l2.getRow();
        if(Math.abs(l1Col-l2Col) <= 1)
            if(Math.abs(l2Row-l1Row) <= 1)
                return true;
        return returnBoolean;
    }
*/  
    public double findDist(Location l1, Location l2)
    {
        double x1 = (Math.pow((l1.getCol()- l2.getCol()),2));
        double y1 = (Math.pow((l1.getRow()- l2.getRow()),2));
        double val = (Math.sqrt(x1+y1));
        return val;
    }
    
    public boolean canAtt(BattleBug b1)
    {
        
        BattleBug mine = this;
        if(b1.getDefense()+3 <= mine.getStrength())
            return true;
        return false;
    }
    
    public void act()
    {
        //declare a Location named goTo and initialize it with the location (5,5)
        Location goTo = new Location(13,13);
        
        //Getting Location of my bug
        Location curLoc = this.getLocation();
        //Call the getPowerUpLocs() method and store the result in a variable named puLocs.
        ArrayList<Location> puLocs = getPowerUpLocs();

        //USE getNumActs % 40 in order to predict when theb border will close, and get away from the border

        //GETTING IMMEDIATE NEIGHBORS
        ArrayList<Actor> actors = getNeighbors();
        ArrayList<BattleBug> bugs = new ArrayList<BattleBug>();
        for(int i = 0; i < actors.size(); i++)
        {
            Actor currActor = actors.get(i);
            if (currActor instanceof BattleBug)
                bugs.add((BattleBug)currActor);
        }

        Location l2 = new Location(13,13);

//            System.out.println(bugs.size() + "                    THIS IS FOR SIZE ______________________________+_-+_=-+_=-=-=");

        //boolean checkIf = false;
        boolean canGo = true;
        boolean comp = false;
        int dir = getDirectionToward(goTo);
        
        if (this.getNumAct() % 40 >= 37)
        {
            comp = true;
            canGo = false;
            if (dir == getDirection())
            { 
                move2();
            }
            else
                turnTo(dir);
            
        }
        boolean bugsNear = false;
        if(bugs.size() != 0 && canGo)
        {        
            if(canAtt(bugs.get(0)))
                bugsNear = true;
            else 
                bugsNear = false;
            goTo = bugs.get(0).getLocation();
        }

        if(canGo)
        {
            if(puLocs.size() > 0)
                l2 = puLocs.get(0);

            if(puLocs.size() > 1)
            {
                l2 = puLocs.get(0);
                for(int i = 1; i < puLocs.size()-1; i++)
                {
                    Location l1 = puLocs.get(i);
                    double l1Val = findDist(l1,curLoc);
                    double l2Val = findDist(l2,curLoc);
                    if(l1Val < l2Val)
                        l2 = l1;
                    //else 
                      //  goTo = l1;        
                }
            }
            goTo = l2;
            dir = getDirectionToward(goTo);
        }
        //Call the getDirectionToward() method and store the result in a variable named dir.
        //boolean valid = true;

        //If the bug cannot move, it will be given a new desired direction: the center
        //ESCAPE CODE (avoiding objects)
        if((!canMove() || tryEsc) && canGo && !comp && !bugsNear)
        {
            comp = true;
            tryEsc = true;
            canGo = false;
            dir = getDirectionToward(new Location(1,1));
            if(!canMove())
            {
                dir = getDirectionToward(new Location(13,13));
                
            }
            if(canMove())
            {
         //       dir = getDirectionToward(new Location(27,27));
                tryEsc = false;
                move2();

            }
            else
                turn(45);
        }


        //Using the getDirection() method check to see if your bug is facing the desired direction dir
        //If so then call the move() method
        //MOVING/TURNING UNLESS ALREADY MOVED/TURNED
        if (dir == getDirection() && !tryEsc && !comp && !bugsNear)
        {
            comp = true;
            move2();
        }
        else
            if(!tryEsc && !comp && !bugsNear)
            {
                comp = true;
                turnTo(dir);
            }

        //TO ATTACK ENEMIES
        if(bugsNear && !tryEsc && !comp)
        {
            comp = true;
            BattleBug b1 = bugs.get(0);
            Location l1 = b1.getLocation();
            int l1Col = curLoc.getCol();
            int l2Col = l1.getCol();
            int l1Row = curLoc.getRow();
            int l2Row = l1.getRow();
            if(Math.abs(l1Col-l2Col) <= 1)
                if(Math.abs(l2Row-l1Row) <= 1)
                {
                    int turningTo = this.getDirectionToward(l1);
                    turnTo(turningTo);
                    attack();
                    
                }
            
        }        

        //}
        /*        else
        {
        int dir = getDirectionToward(new Location(1,1));
        if(!canMove())
        {
        dir = getDirectionToward(new Location(13,13));
        }
        if(canMove())
        {
        dir = getDirectionToward(new Location(27,27));
        }
        if (dir == getDirection())
        {
        
        move2();
        tryEsc = false;
        }
        else
        turnTo(dir);
        
        }*/
    }
    
}