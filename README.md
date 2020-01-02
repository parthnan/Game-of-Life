# Game-of-Life
Conway's Game of Life created using Java GUI, showing each generation of Automata. Animate button shows all the following generations every 0.25s. Supports Undo,Redo operations. The development is under the Model-View-Controller architecture, in the correspondingly named java classes.

The colors of the alive cells are as follows: Dark Green if 1 neighbour, Green if 2/3 neighbours, Brown if 4,5 neighbours, Black if 6,7,8 neighbours.

Next generation shown in new frame, every 0.25s. An example of Undo is below. We press Next in the slides 1-3, and the Fourth side is after one Undo.

![UndoExample.png](https://raw.githubusercontent.com/parthnan/Game-of-Life/master/UndoExample.png)

# Bonus Feature!
You can change the Conway's rules in a dialog box. Example: when you change the rules to always reproducing (regardless of number of neighbours), this happens : 

![InputRules.png](https://raw.githubusercontent.com/parthnan/Game-of-Life/master/InputRules.png)

Each cell bordering an alive cell comes alive!

![AlwaysReproducing.png](https://raw.githubusercontent.com/parthnan/Game-of-Life/master/AlwaysReproducing.png)
