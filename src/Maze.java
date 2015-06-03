import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Maze
{
    private MazeCell[][] cells;
    List<MazeWall> allWalls;
    List<Set<MazeCell>> setsOfCells;

    // generates a maze using Kruskal's algorithm.
    public Maze(int width, int depth, boolean debug)
    {
        allWalls = new ArrayList<>();
        setsOfCells = new ArrayList<Set<MazeCell>>();

        cells = new MazeCell[width][depth];

        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < depth; j++)
            {
                cells[i][j] = new MazeCell(i, j);
                setsOfCells.add(new HashSet<MazeCell>());
                setsOfCells.get(setsOfCells.size() - 1).add(cells[i][j]);
            }
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

        System.out.println("Done!~");
        // does the 4th index contain the 4th cell?
        // System.out.println(setsOfCells.get(3).contains(cells[1][0]));

        while (allWalls.size() > 0)
        {
            int temp = (int) (Math.random() * allWalls.size());

            if (inSameSet(allWalls.get(temp).a, allWalls.get(temp).b))
            {
                combineSets(allWalls.get(temp).a, allWalls.get(temp).b);
            }

            allWalls.remove(temp);
             System.out.println("Removed a wall! " + (temp));
        }
        // System.out.println("Maze complete, but it sucks!");
    }

    public void display()
    {

    }

    private boolean inSameSet(MazeCell first, MazeCell second)
    {
        boolean temp = false;

        for (int i = 0; i < setsOfCells.size(); i++)
        {
            if (setsOfCells.get(i).contains(first) && setsOfCells.get(i).contains(first))
            {
                temp = true;
                break;
            }
        }
        return temp;
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

        while (!setsOfCells.get(indexOfFirst).contains(a))
            indexOfFirst++;

        while (!setsOfCells.get(indexOfSecond).contains(b))
            indexOfSecond++;

        setsOfCells.get(indexOfFirst).addAll(setsOfCells.get(indexOfSecond));
        setsOfCells.remove(indexOfSecond);
    }

    private class MazeWall
    {
        protected MazeCell a;
        protected MazeCell b;

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

        public String toString()
        {
            return "(" + x + ", " + y + ")";
        }
    }
}
