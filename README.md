# Game-of-Life
Conway's Game of Life created using Java GUI. Press Next button to show next generation of automata. Animate button shows all the following generations at equal intervals of time. Supports Undo,Redo operations. Animation is done using inheritance from Java Timer class. The development of the game as a whole is done under the "Model-View-Controller" architecture, in the correspondingly named java classes.

The colors of the alive cells are as follows: Dark Green if 1 neighbour, Green if 2/3 neighbours, Brown if 4,5 neighbours, Black if 6,7,8 neighbours.

![UndoExample.png](https://raw.githubusercontent.com/parthnan/Game-of-Life/master/UndoExample.png)

You can even change the Conway's rules of Survival,Reproduction and Death in a dialog box at the start of running this program. Example: when you change the rules to always surviving and reproducing regardless of number of neighbours, this happens : 

![InputRules.png](https://raw.githubusercontent.com/parthnan/Game-of-Life/master/InputRules.png)

As expected, Each cell bordering an alive cell comes alive!

![AlwaysReproducing.png](https://raw.githubusercontent.com/parthnan/Game-of-Life/master/AlwaysReproducing.png)
