import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Maze builds a maze of the provided size and generates paths based on Kruskal's algorithm.
 * 
<<<<<<< HEAD
 * @author Robert Ferguson - Primary coder, primary bug implementer, primary bug remover
 * @author Ian Cresse - Design, code, code review, documentation
=======
 * @author Robert Ferguson - Primary coder, design, bug squashing
 * @author Ian Cresse - Design, code, code review
 *
>>>>>>> master
 */
public class Maze
{
    /** The maze, represented as a 2D matrix. */
    private MazeCell[][] cells;
    /** Initially all walls in the maze. Gets smaller as paths are made. */
    private List<MazeWall> allWalls;
<<<<<<< HEAD

    /**
     * A list of the set of cells that have paths. Initially has each cell in their own set.
     * Goes down to 1 set.
     */
    private List<Set<MazeCell>> setsOfCells;

=======
    /** A list of the set of cells that have paths. Initially has each cell in their own set. Goes down to 1 set.*/
    private List<Set<MazeCell>> setsOfCells;
    /** The width of the maze. */
    private int mazeWidth;
>>>>>>> master
    /** The depth of the maze. */
    private int mazeDepth;
    /** The width of the maze. */
    private int mazeWidth;

    public final boolean debug;
    
    /**
     * Generates a new maze using Kruskal's algorithm.
     * 
     * @param width the width of the maze
     * @param depth the depth of the maze
     * @param debug Whether or not to show the maze being generated.
     */
    public Maze(int width, int depth, boolean debug)
    {
        allWalls = new ArrayList<>();
        setsOfCells = new ArrayList<Set<MazeCell>>();

        cells = new MazeCell[depth][width];
        mazeWidth = width;
        mazeDepth = depth;
<<<<<<< HEAD
        this.debug = debug;

        // Makes a cell at every index of the matrix.
=======
        
        //Makes a cell at every index of the matrix.
>>>>>>> master
        for (int row = 0; row < depth; row++)
        {
            for (int col = 0; col < width; col++)
            {
                cells[row][col] = new MazeCell(row, col);
                setsOfCells.add(new HashSet<MazeCell>());
                setsOfCells.get(setsOfCells.size() - 1).add(cells[row][col]);
            }
        }

<<<<<<< HEAD
        // Adds walls to the south and the east if possible. When a wall is made,
        // the wall connecting the vertices gains a reference to the same wall.
=======
        //Adds walls to the south and the east if possible. When a wall is made,
        //the wall connecting the vertices gains a reference to the same wall.
>>>>>>> master
        for (int row = 0; row < depth; row++)
        {
            for (int col = 0; col < width; col++)
            {
                if (!(row + 1 == depth))
                {
                    cells[row][col].south = new MazeWall(cells[row][col], cells[row + 1][col]);
                    cells[row + 1][col].north = cells[row][col].south;
                    allWalls.add(cells[row][col].south);
                }

                if (!(col + 1 == width))
                {
                    cells[row][col].east = new MazeWall(cells[row][col], cells[row][col + 1]);
                    cells[row][col + 1].west = cells[row][col].east;
                    allWalls.add(cells[row][col].east);
                }
            }
        }

<<<<<<< HEAD
        // Adds walls to the north of the top level of the maze and to the south of the bottom
        // level
=======
        //Adds walls to the north of the top level of the maze (not handled in the above loops)
>>>>>>> master
        for (int col = 0; col < mazeWidth; col++)
        {
            cells[0][col].north = new MazeWall(cells[0][col], null);
            cells[mazeDepth - 1][col].south = new MazeWall(cells[mazeDepth - 1][col], null);
        }

<<<<<<< HEAD
        // Adds west walls to the leftmost cells and east walls to the rightmost side of the
        // maze.
=======
        //Adds walls to the leftmost side of the maze.
>>>>>>> master
        for (int row = 0; row < mazeDepth; row++)
        {
            cells[row][0].west = new MazeWall(cells[row][0], null);
            cells[row][mazeWidth - 1].east = new MazeWall(cells[row][mazeWidth - 1], null);
        }
        
        //Displays maze with all walls.
        if (debug) display();

<<<<<<< HEAD
        // Displays maze with all walls.
        if (debug)
            display();

=======
>>>>>>> master
        // Quick check to make sure cells have walls in expected positions
        // does the 4th index contain the 4th cell? Yup
        // System.out.println(setsOfCells.get(3).contains(cells[1][0]));

<<<<<<< HEAD
        // Randomly choose a wall and mark it as path if the cells connected to it are not in
        // the
        // same set.
        // short circuits on setsOfCells.size == 1
        while (allWalls.size() > 0 && setsOfCells.size() > 1)
=======
        //Randomly choose a wall and remove it if the cells connected to it are not in the same set.
        while (allWalls.size() > 0)
>>>>>>> master
        {
            int temp = (int) (Math.random() * allWalls.size());

            if (!inSameSet(allWalls.get(temp).first, allWalls.get(temp).second))
            {
                combineSets(allWalls.get(temp).first, allWalls.get(temp).second);
                allWalls.get(temp).path = true;
            }

            allWalls.remove(temp);

            if (debug)
                display();
            // System.out.println("Removed a wall! " + (temp));
        }
        // System.out.println("Maze complete, but it sucks!");
        display();
    }

    /**
     * Displays the wall in its current form.
     */
    public void display()
    {
        System.out.println(toString());
        System.out.println();
    }

    /**
<<<<<<< HEAD
     * Builds the maze. -, | and + indicate boundaries around the maze and are not part of the
     * maze.
     * X indicates a wall. A space indicates a path or a vertex that has yet
     * to be visited.
     * V indicates a visited vertex.
=======
     * Builds the maze. -, | and + indicate boundaries around the maze.
     * X indicates unpathable terrain. A space indicates a viable path.
>>>>>>> master
     */
    public String toString()
    {
        // I hate this method so much.
        StringBuilder s = new StringBuilder();
        boolean flag = true;
<<<<<<< HEAD
        // topmost row
=======
        //topmost row
>>>>>>> master
        s.append('+');

        for (int i = 0; i < (mazeWidth * 2) + 1; i++)
            s.append('-');

        s.append('+');
        s.append('\n');

        for (int row = 0; row < mazeDepth; row++)
        {
            s.append('|'); // new frame piece at the beginning of the line
            for (int col = 0; col < mazeWidth; col++)
            {
                // If we're on the line where we're looking at what's above a vertex.
                if (flag)
                {
                    s.append('X'); // corners between vertexes are automatically unpathable

                    // If there is a path there, mark it as a space, otherwise as an X
                    if (cells[row][col].north.path)
                        s.append(' ');
                    else
                        s.append('X');

                    // The first time we reach the end of the line in a loop
                    if (col == mazeWidth - 1)
                    {
                        flag = false;
                        s.append('X');
                        s.append('|');
                        s.append('\n');
                        s.append('|');
                        col = -1; // will be incremented immediately to 0 via col++.
                    }
                }
                else
                {
                    // If there is a path there, mark it as a space, otherwise as an X
                    if (cells[row][col].west.path)
<<<<<<< HEAD
                        s.append(' '); // s.append('_'); //for vertex visibility
=======
                        s.append(' '); //s.append('_'); //for vertex visibility
>>>>>>> master
                    else
                        s.append('X');

                    // mark the location of a vertex.
<<<<<<< HEAD
                    if (cells[row][col].visited)
                        s.append((char) 248);
                    else
                        s.append(' ');
=======
                    s.append(' ');
>>>>>>> master

                    if (col == mazeWidth - 1)
                    {
                        if (cells[row][col].east.path)
                            s.append(' ');
                        else
                            s.append('X');
                        flag = true;
                    }
                }
            }
            s.append('|');
            s.append('\n');
        }

        s.append('|');

        // go over the south walls of the very last row
        for (int i = 0; i < mazeWidth; i++)
        {
            s.append('X');

            if (cells[mazeDepth - 1][i].south.path)
                s.append(' ');
            else
                s.append('X');
        }
        s.append('X');
        s.append('|');
        s.append('\n');
<<<<<<< HEAD
        // bottom most row
=======
        //bottom most row
>>>>>>> master
        s.append('+');
        
        for (int i = 0; i < (mazeWidth * 2) + 1; i++)
            s.append('-');

        s.append('+');

        return s.toString();
    }

    /**
<<<<<<< HEAD
     * Returns whether the two MazeCells are in the same set or not (thus should not be
     * removed)
     * 
=======
     * Returns whether the two MazeCells are in the same set or not (thus should not be removed)
>>>>>>> master
     * @param first The first MazeCell
     * @param second The second MazeCell
     * @return whether the two MazeCells are in the same set
     */
    private boolean inSameSet(MazeCell first, MazeCell second)
    {
        for (int i = 0; i < setsOfCells.size(); i++)
        {
            if (setsOfCells.get(i).contains(first) && setsOfCells.get(i).contains(second))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds the indexes of two Cells in the setsOfCells list and then combines the sets.
     * 
     * @param a The first MazeCell
     * @param b The second MazeCell
     */
    private void combineSets(MazeCell a, MazeCell b)
    {
        int indexOfFirst = 0;
        int indexOfSecond = 0;

        // Find the indexes of the cells we want to remove
        while (!setsOfCells.get(indexOfFirst).contains(a))
            indexOfFirst++;

        while (indexOfSecond < setsOfCells.size()
                && !setsOfCells.get(indexOfSecond).contains(b))
            indexOfSecond++;

        setsOfCells.get(indexOfFirst).addAll(setsOfCells.get(indexOfSecond));
        setsOfCells.remove(indexOfSecond);
    }

    /**
<<<<<<< HEAD
     * Gives reference to the start of the maze.
     * 
     * @return
     */
    public MazeCell getStart()
    {
        return cells[0][0];
    }

    /**
     * Checks to see if a MazeCell is the end of the maze.
     * 
     * @param cell The cell.
     * @return Whether or not it's the end.
     */
    public boolean isMazeEnd(MazeCell cell)
    {
        return cell.equals(cells[mazeDepth - 1][mazeWidth - 1]);
=======
     * MazeWall represents the boundary between two MazeCells.
     * @author Robert Ferguson
     * @author Ian Cresse
     *
     */
    private class MazeWall
    {
        /** The head of the wall. */
        protected MazeCell a;
        /** The tail of the wall. */
        protected MazeCell b;
        /** Whether the maze is part of the path or not. */
        protected boolean path = false;

        /**
         * Constructs a wall between two MazeCells
         * @param firstCell the first cell
         * @param secondCell the second cell
         */
        public MazeWall(MazeCell firstCell, MazeCell secondCell)
        {
            a = firstCell;
            b = secondCell;
        }

        /**
         * Returns a coordinate of both MazeCell's coordinates.
         */
        public String toString()
        {
            return "[" + a.toString() + " " + b.toString() + "]";
        }

    }

    /**
     * Represents a vertex in the maze. Has walls in the cardinal directions.
     * @author Robert Ferguson
     * @author Ian Cresse
     *
     */
    private class MazeCell
    {
        /** The x coordinate. */
        protected int x;
        /** The y coordinate. */
        protected int y;

        /** The wall to the north. */
        protected MazeWall north;
        /** The wall to the south. */
        protected MazeWall south;
        /** The wall to the east. */
        protected MazeWall east;
        /** The wall to the west. */
        protected MazeWall west;

        /**
         * The cell's position within the matrix.
         * @param horiztonalCoordinate the horizontal coordinate.
         * @param verticalCoordinate the vertical coordinate.
         */
        public MazeCell(int horiztonalCoordinate, int verticalCoordinate)
        {
            x = horiztonalCoordinate;
            y = verticalCoordinate;
        }
        
        /**
         * Returns the coordinate position in the matrix in the maze.
         */
        public String toString()
        {
            return "(" + x + ", " + y + ")";
        }
>>>>>>> master
    }
}
