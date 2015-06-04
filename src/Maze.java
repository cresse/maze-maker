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
    public Maze(int width, int depth, boolean debug)
    {
        allWalls = new ArrayList<>();
        setsOfCells = new ArrayList<Set<MazeCell>>();

        cells = new MazeCell[width][depth];
        mazeWidth = width;
        mazeDepth = depth;

        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < depth; j++)
            {
                cells[i][j] = new MazeCell(i, j);
                setsOfCells.add(new HashSet<MazeCell>());
                setsOfCells.get(setsOfCells.size() - 1).add(cells[i][j]);
            }
        }

        if (debug)
        {
            System.out.println("Maze cells created. Adding walls");
            display();
        }

        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < depth; j++)
            {
                if (!(i + 1 == width))
                {
                    cells[i][j].east = new MazeWall(cells[i][j], cells[i + 1][j]);
                    cells[i + 1][j].west = cells[i][j].east;
                    allWalls.add(cells[i][j].east);
                }

                if (!(j + 1 == depth))
                {
                    cells[i][j].south = new MazeWall(cells[i][j], cells[i][j + 1]);
                    cells[i][j + 1].north = cells[i][j].south;
                    allWalls.add(cells[i][j].south);
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

        for (int i = 0; i < mazeDepth; i++)
        {
            s.append('|'); // new frame piece at the beginning of the line
            for (int j = 0; j < mazeWidth; j++)
            {
                // If we're on the line where we're looking at what's above a vertex.
                if (flag)
                {
                    s.append('X'); // corners between vertexes are automatically unpathable

                    // If there is a path there, mark it as a star, otherwise as an X
                    if (cells[i][j].north != null)
                        s.append('*');
                    else
                        s.append('X');

                    // The first time we reach the end of the line in a loop
                    if (j == mazeWidth - 1)
                    {
                        flag = false;
                        s.append('X');
                        s.append('|');
                        s.append('\n');
                        s.append('|');
                        j = -1; // will be incremented immediately to 0 via j++.
                    }
                }
                else
                // if(!flag)
                {
                    // If there is a path there, mark it as a star, otherwise as an X
                    if (cells[i][j].west != null)
                        s.append('*');
                    else
                        s.append('X');

                    // mark the location of a vertex.
                    s.append('V');

                    if (j == mazeWidth - 1)
                    {
                        if (cells[i][j].east != null)
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

            if (cells[i][mazeWidth - 1].south != null)
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

        public MazeCell getOtherCell(MazeCell curCell)
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
