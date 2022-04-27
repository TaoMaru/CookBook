
/**BakingRecipe.java
 * This class holds the details for a baking recipe
 *
 * @author Maria Jackson
 * @version 1.0 Apr 22, 2022
 * @version 1.1 Apr 23, 2022 - Added constructors with calls to super(), added getter 
 * & setter for bakeTime
 * @version 1.2 Apr 26, 2022 - now inherits Serializable implementation from super, added
 * override of super's abstract getCookingTime method, removed bakeTime field: redundant.
 */
public class BakingRecipe extends BasicRecipe
{
    // instance variables:
    double bakeTime; // bake time in minutes
    
    //constructors:
    public BakingRecipe()
    {
        super();
    }
    
    public BakingRecipe(String title)
    {
        super(title);
    }
    
    //methods:
    public String getFullRecipe()
    {
        return super.getFullRecipe() + "\n\t" 
            + "Bake for " + String.valueOf(bakeTime);
    }
    
    @Override
    /** get baking time for recipe
     * @return baking-specific cook time format (Str)
     */
    public  String getCookingTime()
    {
        return "Bake for: " + String.valueOf(getCookTime());
    }
}
