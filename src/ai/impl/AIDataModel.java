package ai.impl;

import snake.spielfeld.Spielfeld;

public class AIDataModel
{   
    /*Properties*/
    private Spielfeld.state[][] states;
    private boolean valid;
    
    /*Constructors*/
    public AIDataModel()
    {
        this(null,false);
    }
    public AIDataModel(Spielfeld.state[][] states)
    {
        this(states,true);
    }
    public AIDataModel(Spielfeld.state[][] states, boolean valid)
    {
        this.states = states;
        this.valid = valid;
    }
    
    /*Methods*/
    public boolean isValid()
    {
        return valid;
    }
    
}
