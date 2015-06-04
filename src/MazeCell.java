import java.util.ArrayList;
import java.util.List;

/**
 * Represents a vertex in the maze. Has walls in the cardinal directions.
 * 
 * @author Robert Ferguson
 * @author Ian Cresse
 *
 */
public class MazeCell
{
    /** The x coordinate. */
    protected int x;
    /** The y coordinate. */
    protected int y;

    /** Whether or not the MazeCell has been visited by a solving algorithm. */
    protected boolean visited = false;

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
     * 
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

    /**
     * Get's a list of all cells adjacent to the current cell.
     * 
     * @return A List.
     */
    public List<MazeCell> getAdjacentCells()
    {
        ArrayList<MazeCell> returnable = new ArrayList<>();

        if (west.path)
            returnable.add(getOtherCell(west));

        if (north.path)
            returnable.add(getOtherCell(north));

        if (east.path)
            returnable.add(getOtherCell(east));

        if (south.path)
            returnable.add(getOtherCell(south));

        return returnable;
    }

    /**
     * Given a wall, returns reference to the cell that is not this one.
     * 
     * @param wall The wall to check
     * @return
     */
    private MazeCell getOtherCell(MazeWall wall)
    {
        return wall.first.equals(this) ? wall.second : wall.first;
    }

    /**
     * Returns if two cells are equal. They're considered equal if they share x values and y
     * values.
     */
    public boolean equals(Object obj)
    {
        if (obj != null && obj instanceof MazeCell)
        {
            return ((MazeCell) obj).x == x && ((MazeCell) obj).y == y;
        }
        else
        {
            return false;
        }
    }
}
