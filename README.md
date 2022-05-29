# BattleBugs-Project
**Project for APCSA.**

## Description
The next project will require that you write code for a type of bug, a BattleBug, to fight, in a way similar to gladiators, in a grid. These BattleBugs each have three different attributes, Strength, Defense, and Speed that are incremented as a BattleBug either steps on a PowerUp or kills another BattleBug.

BattleBugs will do battle on a special ***(27x27)*** grid with a referee bug in the center of type Regulator. The regulator has many functions that are: to make sure the rules are followed, to add rocks to the limits of the empty space, and to drop PowerUps. The rules are checked at the beginning of every turn, a border of rocks around the empty space will be created every ***(40)*** acts, and a maximum of ***(6)*** flowers are dropped every ***(10)*** turns.  

Strength and defense are used to calculate the results of the attack() method. If the attacking BattleBug’s Strength is at least three points higher than the defender's Defense, the defending bug is “killed” and removed from the grid, and a TombStone is left in its place. The attacking BattleBug has an attribute increased by one based on what the highest value attribute of the defending bug is. 

**__For example:__** if the defending BattleBug’s stats are 5 Strength, 4 Defense, and 7 Speed, the attacking BattleBug’s speed is increased by one. However, if the defending bug has two attributes with the same highest value, a random attribute of the attacking BattleBug is incremented. In the case of an unsuccessful attack, if the Strength of the attacker is not three greater than the defender's defense, nothing happens. 

Speed is the special attribute: Speed determines which bug acts first.  Also a Speed of ten or greater, a BattleBug has his move2() method enabled, and can move two spaces forward instead of one.   Another perk of speed is that it allows the BattleBugs to see farther than they normally can to help them hunt down or avoid the enemy bugs.

<img src="https://user-images.githubusercontent.com/89307499/170883180-931e085b-cbc0-4da3-95ed-f6cddcb73a10.png" height="550" width="550" alt="Grid">

## Example Bugs
- Example code of bugs are provided [here](https://github.com/Alan0893/BattleBugs-Project/tree/main/BattleBugs/Example%20Bugs).
- To create your bug, the only that you will need to override is the ***public void act()*** method.
```java
public class BUG_NAME extends BattleBug2012 {   
    public BUG_NAME (int str, int def, int spd, String name, Color col) {
        super (str, def, spd, name, col);
    }
    public void act() {  
        //YOUR CODE HERE
    }
    //METHODS
}
```

## Methods & Accessors
Methods and Accessors that your bug may use are provided in the [BattleBug class](https://github.com/Alan0893/BattleBugs-Project/blob/main/BattleBugs/BattleBug.java) (methods are inherited from [BattleBug2012](https://github.com/Alan0893/BattleBugs-Project/blob/main/BattleBugs/BattleBug2012.java) or one of its [parents](https://github.com/Alan0893/BattleBugs-Project/blob/main/BattleBugs/BattleBug.java)), [Actor Class](https://github.com/Alan0893/BattleBugs-Project/blob/main/info/gridworld/actor/Actor.java) (methods are provided in the [children class](https://github.com/Alan0893/BattleBugs-Project/blob/main/info/gridworld/actor/Bug.java) as well), [Location class](https://github.com/Alan0893/BattleBugs-Project/blob/main/info/gridworld/grid/Location.java), and [Grid Interface class](https://github.com/Alan0893/BattleBugs-Project/blob/main/info/gridworld/grid/Grid.java).

##### Credits: esuriel & CollegeBoard (AP(r) Computer Science GridWorld Case Study)
