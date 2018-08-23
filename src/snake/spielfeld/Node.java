package snake.spielfeld;
/**
 * Created by scisneromam on 24.02.2018.
 */
public class Node implements Comparable<Node>
{
	public int x;
	public int y;
	public int g;
	public int h;
	public int f;
	public Node north;
	public Node east;
	public Node south;
	public Node west;
	public Node previous;
	public boolean passable;
	public boolean appleField;

	public Node(int x, int y, Node north, Node east, Node south, Node west, boolean passable, boolean appleField)
	{
		this.x = x;
		this.y = y;
		this.north = north;
		this.east = east;
		this.south = south;
		this.west = west;
		this.passable = passable;
		this.appleField = appleField;
	}
	public Node(int x, int y, boolean passable, boolean appleField)
	{
		this.x = x;
		this.y = y;
		this.passable = passable;
		this.appleField = appleField;

		north = null;
		east = null;
		south = null;
		west = null;
	}

	@Override
	public int compareTo(Node node)
	{
		return f - node.f;
	}

	@Override
	public String toString()
	{
		return "Node{" + "x=" + x + ", y=" + y + ", passable=" + passable + ", appleField=" + appleField + '}';
	}
}