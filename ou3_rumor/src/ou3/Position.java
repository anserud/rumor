
package ou3;


// TODO: Auto-generated Javadoc
/**
 * The Class Position.
 */
public class Position
{
    
    /** The x. */
    private int x;
    
    /** The y. */
    private int y;
    
    /**
     * Instantiates a new position.
     *
     * @param x the x
     * @param y the y
     */
    public Position( int x, int y )
    {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Gets the x.
     *
     * @return the x
     */
    public int getX()
    {
        return x;
    }
    
    /**
     * Sets the x.
     *
     * @param x the new x
     */
    public void setX( int x )
    {
        this.x = x;
    }
    
    /**
     * Gets the y.
     *
     * @return the y
     */
    public int getY()
    {
        return y;
    }
    
    /**
     * Sets the y.
     *
     * @param y the new y
     */
    public void setY( int y )
    {
        this.y = y;
    }
    
    /**
     * Compare.
     *
     * @param other the other
     * @return the int
     */
    public int compare( Position other )
    {
        return ( ( this.x - other.getX() ) + 10000 * ( this.y - other.getY() ) );
    }
}
