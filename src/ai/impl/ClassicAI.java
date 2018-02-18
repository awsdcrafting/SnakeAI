package ai.impl;

import snake.spielfeld.Spielfeld;

public class ClassicAI extends AI
{
    
    private AIDataModel dataModel;
    private AIStateModel stateModel;
    
    public ClassicAI(Spielfeld spielfeld)
    {
        super(spielfeld);
        baseName = "Ki-nimtri";
    }
    
    @Override
    public void save()
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void zug()
    {
        Spielfeld.direction direction = Spielfeld.direction.EAST;
        
        
        spielfeld.setMoveDirection(direction);
    }
    
    @Override
    public void reset()
    {
        // TODO Auto-generated method stub
        
    }
    
    
    private Spielfeld.state[][] getStates(Spielfeld sf)
    {
        int x = spielfeld.getWidth();
        int y = spielfeld.getHeight();
        Spielfeld.state[][] states = new Spielfeld.state[x][y];
        for(int i = 0; i < x; i++)
        {
            for(int j = 0; j < y; j++)
            {
                states[i][j] = spielfeld.getState(i, j);
            }
        }
        return states;
    }
}
