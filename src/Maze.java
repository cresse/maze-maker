import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Maze
{
    private MazeCell[][] cells;
    private List<MazeWall> allWalls;
    private List<Set<MazeCell>> setsOfCells;
    private int mazeWidth;
    private int mazeDepth;

    // generates a maze using Kruskal's algorithm.
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

        for (int row = 0; row < depth; row++)
        {
            for (int col = 0; col < width; col++)
            {
                cells[row][col] = new MazeCell(row, col);
                setsOfCells.add(new HashSet<MazeCell>());
                setsOfCells.get(setsOfCells.size() - 1).add(cells[row][col]);
            }
        }

        if (debug)
        {
            System.out.println("Maze cells created. Adding walls");
            display();
        }

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

        if (debug)
        {
            System.out.println("Walls attached.");
            display();
        }

        // does the 4th index contain the 4th cell? Yup
        // System.out.println(setsOfCells.get(3).contains(cells[1][0]));

        while (allWalls.size() > 0)
        {
            int temp = (int) (Math.random() * allWalls.size());

            if (!inSameSet(allWalls.get(temp).a, allWalls.get(temp).b))
            {
                combineSets(allWalls.get(temp).a, allWalls.get(temp).b);
            }

            allWalls.remove(temp);

            if (debug)
            {
                display();
            }
            // System.out.println("Removed a wall! " + (temp));
        }
        // System.out.println("Maze complete, but it sucks!");
    }

    public void display()
    {
        System.out.println(toString());
        System.out.println();
    }

    public MazeCell getMazeStart()
    {
        return cells[0][0];
    }

    public String toString()
    {
        StringBuilder s = new StringBuilder();
        boolean flag = true;

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

                    // If there is a path there, mark it as a star, otherwise as an X
                    if (cells[row][col].north != null)
                        s.append('*');
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
                        col = -1; // will be incremented immediately to 0 via j++.
                    }
                }
                else
                // if(!flag)
                {
                    // If there is a path there, mark it as a star, otherwise as an X
                    if (cells[row][col].west != null)
                        s.append('*');
                    else
                        s.append('X');

                    // mark the location of a vertex.
                    s.append('V');

                    if (col == mazeWidth - 1)
                    {
                        if (cells[row][col].east != null)
                            s.append('*');
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

            if (cells[mazeDepth - 1][i].south != null)
                s.append('*');
            else
                s.append('X');
        }
        s.append('X');
        s.append('|');
        s.append('\n');

        s.append('+');

        for (int i = 0; i < (mazeWidth * 2) + 1; i++)
            s.append('-');

        s.append('+');

        return s.toString();
    }

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
     * @param a
     * @param b
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

    private class MazeWall
    {
        protected MazeCell a;
        protected MazeCell b;
        protected boolean path = false;

        public MazeWall(MazeCell firstCell, MazeCell secondCell)
        {
            a = firstCell;
            b = secondCell;
        }

        protected MazeCell getOtherCell(MazeCell curCell)
        {
            return a.equals(curCell) ? a : b;
        }

        public String toString()
        {
            return "[" + a.toString() + " " + b.toString() + "]";
        }

    }

    private class MazeCell
    {
        protected boolean frontier;
        protected boolean used;

        protected int x;
        protected int y;

        protected MazeWall north;
        protected MazeWall south;
        protected MazeWall east;
        protected MazeWall west;

        public MazeCell(int horiztonalCoordinate, int verticalCoordinate)
        {
            x = horiztonalCoordinate;
            y = verticalCoordinate;
        }

        private MazeWall getDirectionOfWall(MazeWall m)
        {
            if (m.equals(north))
                return north;
            else if (m.equals(south))
                return south;
            else if (m.equals(east))
                return east;
            else if (m.equals(west))
                return west;
            else
                return null;
        }

        public String toString()
        {
            return "(" + x + ", " + y + ")";
        }
    }
}
