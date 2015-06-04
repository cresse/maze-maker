<<<<<<< HEAD
import java.util.Stack;

/**
 * Creates a maze using Kruskal's Algorithm. Solves it too.
 * 
=======
/**
 * Creates a maze.
>>>>>>> master
 * @author Robert Ferguson
 * @author Ian Cresse
 *
 */
public class Main
{
    /**
<<<<<<< HEAD
     * Makes and solves a maze!
     * 
     * @param args Command line arguments. Unused in this program.
     */
    public static void main(String[] args)
    {
        // testMakingMazes();
        // makeMazeWDebug();
        // new Maze(5, 5, true);
        solveMaze(new Maze(12, 8, false));
    }

    /**
     * Makes mazes in a variety of shapes and sizes.
     */
    private static void testMakingMazes()
    {
        new Maze(2, 2, false);
        new Maze(5, 5, false);
        new Maze(10, 10, false);
        new Maze(15, 15, false);
        new Maze(10, 5, false);
        new Maze(5, 10, false);
        new Maze(20, 15, false);
        new Maze(25, 20, false);
        new Maze(30, 15, false);
        new Maze(42, 23, false);
    }

    /**
     * Makes one maze to solve.
     */
    private static void makeMazeWDebug()
    {
        new Maze(10, 10, true);
    }

    /**
     * Solves a maze using a Depth first search.
     * 
     * @param theMaze The maze to solve.
     */
    private static void solveMaze(Maze theMaze)
    {
        MazeCell start = theMaze.getStart();

        Stack<MazeCell> solver = new Stack<>();

        solver.push(start);

        while (!solver.isEmpty() && !theMaze.isMazeEnd(solver.peek()))
        {
            MazeCell temp = solver.pop();
            if (!temp.visited)
            {
                solver.addAll(temp.getAdjacentCells());
                temp.visited = true;

                if (theMaze.debug)
                    theMaze.display();
            }
        }

        if (theMaze.isMazeEnd(solver.peek()))
        {
            solver.peek().visited = true;
        }

        theMaze.display();
=======
     * Instantiates the maze's constructor, which runs everything.
     * @param args Command line arguments. None expected.
     */
    public static void main(String[] args)
    {
        new Maze(10, 10, false);
>>>>>>> master
    }
}
