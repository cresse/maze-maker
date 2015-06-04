/**
 * MazeWall represents the boundary between two MazeCells.
 * 
 * @author Robert Ferguson
 * @author Ian Cresse
 *
 */
public class MazeWall
{
    /** The head of the wall. */
    protected MazeCell first;
    /** The tail of the wall. */
    protected MazeCell second;
    /** Whether the maze is part of the path or not. */
    protected boolean path = false;

    /**
     * Constructs a wall between two MazeCells
     * 
     * @param firstCell the first cell
     * @param secondCell the second cell
     */
    public MazeWall(MazeCell firstCell, MazeCell secondCell)
    {
        first = firstCell;
        second = secondCell;
    }

    /**
     * Returns a coordinate of both MazeCell's coordinates.
     */
    public String toString()
    {
        return "[" + first.toString() + " " + second.toString() + "]";
    }

}
