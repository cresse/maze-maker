import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// TODO equals method for MazeCell
// TODO Start of the maze
// TODO isEndOfMaze(MazeCell m)
// TODO A stack

/**
 * Maze builds a maze of the provided size and generates paths based on Kruskal's algorithm.
 * 
 * @author Robert Ferguson - Primary coder, design, bug squashing
 * @author Ian Cresse - Design, code, code review
 *
 */
public class Maze
{
    /** The maze, represented as a 2D matrix. */
    private MazeCell[][] cells;
    /** Initially all walls in the maze. Gets smaller as paths are made. */
    private List<MazeWall> allWalls;

    /**
     * A list of the set of cells that have paths. Initially has each cell in their own set.
     * Goes down to 1 set.
     */
    private List<Set<MazeCell>> setsOfCells;

    /** The depth of the maze. */
    private int mazeDepth;
    /** The width of the maze. */
    private int mazeWidth;

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

        // Makes a cell at every index of the matrix.
        for (int row = 0; row < depth; row++)
        {
            for (int col = 0; col < width; col++)
            {
                cells[row][col] = new MazeCell(row, col);
                setsOfCells.add(new HashSet<MazeCell>());
                setsOfCells.get(setsOfCells.size() - 1).add(cells[row][col]);
            }
        }

        // Adds walls to the south and the east if possible. When a wall is made,
        // the wall connecting the vertices gains a reference to the same wall.
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

        // Adds walls to the north of the top level of the maze (not handled in the above
        // loops)
        for (int col = 0; col < mazeWidth; col++)
        {
            cells[0][col].north = new MazeWall(cells[0][col], null);
            cells[mazeDepth - 1][col].south = new MazeWall(cells[mazeDepth - 1][col], null);
        }

        // Adds walls to the leftmost side of the maze.
        for (int row = 0; row < mazeDepth; row++)
        {
            cells[row][0].west = new MazeWall(cells[row][0], null);
            cells[row][mazeWidth - 1].east = new MazeWall(cells[row][mazeWidth - 1], null);
        }

        // Displays maze with all walls.
        if (debug)
            display();

        // Quick check to make sure cells have walls in expected positions
        // does the 4th index contain the 4th cell? Yup
        // System.out.println(setsOfCells.get(3).contains(cells[1][0]));

        // Randomly choose a wall and remove it if the cells connected to it are not in the
        // same set.
        // short circuits on setsOfCells.size == 1
        while (allWalls.size() > 0 && setsOfCells.size() > 1)
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
     * Builds the maze. -, | and + indicate boundaries around the maze.
     * X indicates unpathable terrain. A space indicates a viable path or a vertex that has yet
     * to be visited.
     * V indicates a visited vertex.
     */
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        boolean flag = true;
        // topmost row
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
                // if(!flag)
                {
                    // If there is a path there, mark it as a space, otherwise as an X
                    if (cells[row][col].west.path)
                        s.append(' '); // s.append('_'); //for vertex visibility
                    else
                        s.append('X');

                    // mark the location of a vertex.
                    if (cells[row][col].visited)
                        s.append((char) 248);
                    else
                        s.append(' ');

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
        // bottom most row
        s.append('+');

        for (int i = 0; i < (mazeWidth * 2) + 1; i++)
            s.append('-');

        s.append('+');

        return s.toString();
    }

    /**
     * Returns whether the two MazeCells are in the same set or not (thus should not be
     * removed)
     * 
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

    public MazeCell getStart()
    {
        return cells[0][0];
    }

    public boolean isMazeEnd(MazeCell m)
    {
        return m.equals(cells[mazeDepth - 1][mazeWidth - 1]);
    }
}
