
package ou3;


public class Position
{
    
    private int x;
    private int y;
    
    public Position( int x, int y )
    {
        this.x = x;
        this.y = y;
    }
    
    public int getX()
    {
        return x;
    }
    
    public void setX( int x )
    {
        this.x = x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public void setY( int y )
    {
        this.y = y;
    }
    
    public int compare( Position other )
    {
        int dX = ( this.x - other.getX() );
        if ( dX == 0 )
            return ( this.y - other.getY() );
        else
            return dX;
        
    }
    
    @Override
    public boolean equals( Object other )
    {
        if ( this == other ) return true;
        if ( other == null ) return false;
        if ( this.getClass() != other.getClass() ) return false;
        return ( ( this.x == ( (Position) other ).getX() ) && ( this.y == ( (Position) other ).getY() ) );
    }
}
