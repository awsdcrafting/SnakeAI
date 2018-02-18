package ai.impl;

import snake.spielfeld.Spielfeld;

public class ClassicAI extends AI
{
    
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
    
}
