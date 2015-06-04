import java.util.Stack;

/**
 * Creates a maze.
 * 
 * @author Robert Ferguson
 * @author Ian Cresse
 *
 */
public class Main
{
    /**
     * Makes and solves a maze!
     * 
     * @param args Command line arguments. Unused in this program.
     */
    public static void main(String[] args)
    {
        // testMakingMazes();
        // makeMazeWDebug();
         new Maze(5, 5, true);
         solveMaze(new Maze(9, 6, false));
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

    private static void solveMaze(Maze m)
    {
        MazeCell start = m.getStart();

        Stack<MazeCell> stack = new Stack<>();

        stack.push(start);

        while (!stack.isEmpty() && !m.isMazeEnd(stack.peek()))
        {
            MazeCell temp = stack.pop();
            if (!temp.visited)
            {
                stack.addAll(temp.getAdjacentCells());
                temp.visited = true;
                m.display();
            }
        }

        if (m.isMazeEnd(stack.peek()))
        {
            stack.peek().visited = true;
        }

        m.display();
    }
}
