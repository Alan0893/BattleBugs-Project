package BattleBugs.Bugs;
//attacking is on line 81
//goinga orund osbtacles is on 146
import java.util.ArrayList;

import BattleBugs.BattleBug;
import BattleBugs.BattleBug2012;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.awt.Color;
import info.gridworld.actor.Actor;

public class Jerry extends BattleBug2012
{
    private boolean trapped;

    public Jerry(int str, int def, int spd, String name, Color col)
    {
        super(str, def, spd, name, col);
        trapped=false;
    }
    public void act()
    {    
        //declare a Location named goTo and initialize it with the bug's current locations
        Location goTo=new Location(13,13);
        //Call the getDirectionToward() method and store the result in a variable named dir.    

        //Call the getPowerUpLocs() method and store the result in a variable named puLocs.
        ArrayList<Location> puLocs=this.getPowerUpLocs();

        //CHECK TO SEE if there exists a PowerUp Location, if so then store the first location from the List into goTo
        int min=0;
            
        if(puLocs.size()>0)
        {      
            double yValue=Math.pow((puLocs.get(0).getCol()-this.getLocation().getCol()),2);
            double xValue=Math.pow((puLocs.get(0).getRow()-this.getLocation().getRow()),2);
            double minDist=Math.sqrt(yValue+xValue);  
                
            for(int i=1;i<puLocs.size();i++)
            {
                double currY=Math.pow((puLocs.get(i).getCol()-this.getLocation().getCol()),2);
                double currX=Math.pow((puLocs.get(i).getRow()-this.getLocation().getRow()),2);
                double currDist=  Math.sqrt(currY+currX);
                if(minDist>currDist)
                {
                    minDist=currDist;
                    min=i;
                }
                    
            }
            goTo=puLocs.get(min);
        }
        
        ArrayList<Actor> actors= this.getActors();
        //Using the getDirection() method check to see if your bug is facing the desired direction dir
        int mydir=this.getDirection();
        //Name it enemies, this will store the nearby enemies.
        ArrayList<BattleBug> enemies=new ArrayList<BattleBug>();
        
        //You will now traverse through actors and add the actors that are BattleBugs into the enemies ArrayList.
        
        //use the keyword  instanceof    to determine if the current Actor is a BattleBug
        //When you add it into the enemies ArrayList do not forget to Cast the current Actor into a BattleBug
        for(int i=0;i<actors.size();i++)
        {
            if(actors.get(i) instanceof BattleBug)
                enemies.add((BattleBug)actors.get(i));
        }
        
        int totWalls=this.getNumAct()/40;
        int currRow1=this.getLocation().getRow();
        int currCol1=this.getLocation().getCol();
        if(this.getNumAct()%40>37) 
        {
            if(currRow1==totWalls||currRow1==totWalls+1||currRow1==27-totWalls||currRow1==27-totWalls-1||currCol1==totWalls||currCol1==totWalls+1||currCol1==27-totWalls||currCol1==27-totWalls-1)
                    goTo=new Location(13,13);
        }
        //here is my attacking method
        boolean moved=false;
        if(enemies.size()>0)
        {
            for(int i=0; i<enemies.size();i++)
            {
                int speed=enemies.get(i).getSpeed();
                int strength=enemies.get(i).getStrength();
                int defense=enemies.get(i).getDefense();
                int actualDamage=this.getStrength()-defense; 
                int enemyDamage=strength-this.getDefense();
                    double dist=Math.sqrt(Math.pow((enemies.get(i).getLocation().getCol()-this.getLocation().getCol()),2)+Math.pow((enemies.get(i).getLocation().getRow()-this.getLocation().getRow()),2));
                if(this.getStrength()>defense)
                {
                    if(actualDamage>enemyDamage)
                    {
                        moved=true;
                        if(mydir==getDirectionToward(enemies.get(i).getLocation()))
                        {
                            
                            this.move();
                        }
                        else
                        {
                            this.turnTo(getDirectionToward(enemies.get(i).getLocation()));
                        }
                        this.attack();
                        return;
                    }  
                    
                }
            }
        }
        
        double dist=Math.sqrt(Math.pow((goTo.getCol()-this.getLocation().getCol()),2)+Math.pow((goTo.getRow()-this.getLocation().getRow()),2));
        int intDist=(int)dist;
        double straight=dist/intDist;
        int row=Math.abs(goTo.getRow()-this.getLocation().getRow());
        int col=Math.abs(goTo.getCol()-this.getLocation().getCol());
        int dirPow=this.getDirectionToward(goTo);
        if(!(straight==1))
        {
            if(row>col)
            {
                if(dirPow==45||dirPow==315||dirPow==0)
                    goTo=new Location(this.getLocation().getRow()-1,this.getLocation().getCol());
                else if(dirPow==225||dirPow==180||dirPow==135)
                    goTo=new Location(this.getLocation().getRow()+1,this.getLocation().getCol());
            }
            else if(col>row)
            {
                if(dirPow==225||dirPow==315||dirPow==270)
                {
                    goTo=new Location(this.getLocation().getRow(),this.getLocation().getCol()-1);
                }
                else if(dirPow==90||dirPow==45||dirPow==135)
                    goTo=new Location(this.getLocation().getRow(),this.getLocation().getCol()+1);
            }
            else
            {
                goTo=goTo;
            }
        }
        

        //below here is my going around obstacles 
        ArrayList <Location> empty=this.getEmptyAdjacentLocations(); 
        int dir=this.getDirectionToward(goTo);
        int small=0;
        
        if(trapped==true)
        {
            this.move();
            trapped=false;
            return;
        }
            

        if(moved==false&&trapped==false)
        {
            //If so then call the move() method
            //if not then call turnTo() method towards the desired direction dir.
            if(mydir==dir)
            {
                if(this.canMove()==false)
                {
                
                double yValue=Math.pow((goTo.getCol()-empty.get(0).getCol()),2);
                double xValue=Math.pow((goTo.getRow()-empty.get(0).getRow()),2);
                    double minDist=Math.sqrt(yValue+xValue);
            
            
                    for(int i=1;i<empty.size();i++)
                    {
                    double currY=Math.pow((goTo.getCol()-empty.get(i).getCol()),2);
                    double currX=Math.pow((goTo.getRow()-empty.get(i).getRow()),2);
                    double currDist=  Math.sqrt(currY+currX);
                        if(minDist>currDist)
                        {
                            minDist=currDist;
                            small=i;
                        }
                    
                    }
                    goTo=empty.get(small);
                    dir=this.getDirectionToward(goTo);
                    trapped=true;
                    if(mydir==dir)
                    {
                    this.move();
                    }
                    else
                    this.turnTo(dir);
                }
                else
                {
                    this.move();
                }
        }
        else if(moved==false) 
            this.turnTo(dir);
        }     
    }
}