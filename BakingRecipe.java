
/**BakingRecipe.java
 * This class holds the details for a baking recipe
 *
 * @author Maria Jackson
 * @version 1.0 Apr 22, 2022
 * @version 1.1 Apr 23, 2022 - Added constructors with calls to super(), added getter 
 * & setter for bakeTime
 */
public class BakingRecipe extends BasicRecipe
{
    // instance variables:
    double bakeTime; // bake time in minutes
    
    //constructors:
    public BakingRecipe()
    {
        super();
        bakeTime = 0.0;
    }
    
    public BakingRecipe(String title)
    {
        super(title);
        bakeTime = 0.0;
    }
    
    //methods:
    public String getFullRecipe()
    {
        return super.getFullRecipe() + "\n\t" 
            + "Bake for " + String.valueOf(bakeTime);
    }
    
    public double getBakeTime()
    {
        return bakeTime;
    }
}
