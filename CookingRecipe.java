
/**CookingRecipe.java
 * This class holds the details for a cooking recipe
 *
 * @author Maria Jackson
 * @version 1.0 Apr 22, 2022 - Constructor and getFullRecipe() methods
 * @version 1.2 Apr 26, 2022 - added override of super's abstract getCookingTime() method,
 * now inherits Serializable implementation
 */
public class CookingRecipe extends BasicRecipe
{
    // instance variables:
    
    // constructors:
    public CookingRecipe()
    {
        super();
    }
    
    //methods:
    public String getFullRecipe()
    {
        return super.getFullRecipe() + "\n\t"
            + "Cook for " + String.valueOf(cookTime);
    }
    
    @Override
    /** get cooking time for recipe
     * @return stovetop-specific cook time (Str)
     */
    public String getCookingTime()
    {
        return "Cook on stovetop for: " + String.valueOf(getCookTime());
    }
}
