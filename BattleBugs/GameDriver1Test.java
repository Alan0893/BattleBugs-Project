package BattleBugs;
import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;
import info.gridworld.grid.Grid;
import info.gridworld.grid.BoundedGrid;
import java.awt.Color;
import java.util.ArrayList;

import BattleBugs.Bugs.Const;
import BattleBugs.Bugs.Jerry;
import BattleBugs.Bugs.Pedilinguini;
import BattleBugs.Bugs.Radmir;
import BattleBugs.Bugs.MooMooCow;
import BattleBugs.Bugs.Rex;
import BattleBugs.Bugs.Reaper;
import BattleBugs.Bugs.Chu;
import info.gridworld.actor.Actor;

/**
 * This class runs a BattleBugWorld. <br />
 * Use this class to test a real game scenario
 */
public class GameDriver1Test
{
    //Must be 3x3 or larger
    private static final int DEFAULT_ROWS = 27;
    private static final int DEFAULT_COLS = 27;

    public static void main(String[] args)
    {
    	ArrayList<BattleBug> battleBugs = new ArrayList<BattleBug>();
    	Grid<Actor> gr = new BoundedGrid<Actor>(DEFAULT_ROWS, DEFAULT_COLS);
        BBWorld world = new BBWorld(gr);

        //This is where you enter your Bugs into the game
	    battleBugs.add(new Chu(0,5,0, "Chu", null));
	    battleBugs.add(new Reaper(0,5,0,"Reaper", null));
	    battleBugs.add(new Radmir(0,5,0, "Radmir", null));
        battleBugs.add(new Const(0, 5, 0, "Const", null));
        Regulator theBoss = new Regulator(battleBugs);

        world.add(new Location(DEFAULT_ROWS/2, DEFAULT_COLS/2), theBoss);
        theBoss.placeBugsInGrid();
        theBoss.checkLevel5();
        world.show();
    }
}

 /* Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GRAY,
                Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE,
                Color.PINK, Color.RED, Color.WHITE, Color.YELLOW */