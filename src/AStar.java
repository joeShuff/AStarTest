import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by joeshuff on 14/02/2017.
 */
public class AStar {

    public static void main(String[] args)
    {
        new AStar();
    }

    public AStar()
    {
        System.out.println(aStarPath(new Vector2Int(7, 10), new Vector2Int(20, 20)));
    }

    public List<Vector2Int> aStarPath(Vector2Int start, Vector2Int destination)
    {
        List<Vector2Int> closedSet = new ArrayList<Vector2Int>();

        List<Vector2Int> openSet = new ArrayList<Vector2Int>();
        openSet.add(start);

        HashMap<Vector2Int, Integer> gScore = new HashMap<Vector2Int, Integer>();
        gScore.put(openSet.get(0), 0);

        HashMap<Vector2Int, Vector2Int> cameFrom = new HashMap<Vector2Int, Vector2Int>();

        HashMap<Vector2Int, Integer> fScore = new HashMap<Vector2Int, Integer>();
        fScore.put(openSet.get(0), heuristic(start, destination));

        while (!openSet.isEmpty())
        {
            Vector2Int current = getLowestFScore(openSet, fScore);

            if (current.equals(destination))
            {
                return reconstructPath(cameFrom, current);
            }

            openSet.remove(current);
            closedSet.add(current);

            List<Vector2Int> neighbours = getNeighbours(current);

            for (Vector2Int neighbour : neighbours)
            {
                if (closedSet.contains(neighbour))
                {
                    continue;
                }

                int tentativeGScore = gScore.get(current) + distFromNeighbour(current, neighbour);

                if (!openSet.contains(neighbour))
                {
                    openSet.add(neighbour);
                }
                else
                {
                    int prevScore = gScore.get(neighbour);

                    if (tentativeGScore >= prevScore)
                    {
                        continue;
                    }
                }

                cameFrom.put(neighbour, current);
                gScore.put(neighbour, tentativeGScore);
                fScore.put(neighbour, gScore.get(neighbour) + heuristic(neighbour, destination));
            }
        }

        return null;
    }

    public Vector2Int getLowestFScore(List<Vector2Int> openSet, HashMap<Vector2Int, Integer> fScore)
    {
        if (openSet.isEmpty()) return null;

        Vector2Int lowest = openSet.get(0);
        int lowestInt = fScore.get(lowest);

        for (Vector2Int v : openSet)
        {
            if (fScore.get(v) < lowestInt)
            {
                lowest = v;
                lowestInt = fScore.get(v);
            }
        }

        return lowest;
    }

    public int distFromNeighbour(Vector2Int current, Vector2Int neighbour)
    {
        return Math.abs(current.getX() - neighbour.getX()) + Math.abs(current.getY() - neighbour.getY());
    }

    public List<Vector2Int> reconstructPath(HashMap<Vector2Int, Vector2Int> cameFrom, Vector2Int current)
    {
        List<Vector2Int> path = new ArrayList<Vector2Int>();
        path.add(current);

        while (cameFrom.keySet().contains(current))
        {
            current = cameFrom.get(current);
            path.add(current);
        }

        return path;
    }

    public List<Vector2Int> getNeighbours(Vector2Int current)
    {
        int roomWidth = 500;  //Extreme Value
        int roomHeight = 500;  //Extreme Value

        List<Vector2Int> neighbours = new ArrayList<Vector2Int>();

        if (current.getX() + 1 <= roomWidth)
        {
            neighbours.add(new Vector2Int(current.getX() + 1, current.getY()));
        }

        if (current.getY() + 1 <= roomHeight)
        {
            neighbours.add(new Vector2Int(current.getX(), current.getY() + 1));
        }

        if (current.getX() - 1 >= 0)
        {
            neighbours.add(new Vector2Int(current.getX() - 1, current.getY()));
        }

        if (current.getY() - 1 >= 0)
        {
            neighbours.add(new Vector2Int(current.getX(), current.getY() - 1));
        }

        return neighbours;
    }

    public int heuristic(Vector2Int start, Vector2Int end)
    {
        return Math.abs(start.getX() - end.getX()) + Math.abs(start.getY() - end.getY());
    }

}
