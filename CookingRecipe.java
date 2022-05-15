
/**CookingRecipe.java
 * This class holds the details for a cooking recipe
 *
 * @author Maria Jackson
 * @version 1.0 Apr 22, 2022 - Constructor and getFullRecipe() methods
 * 
 * @version 1.2 Apr 26, 2022 - added override of super's abstract getCookingTime() method,
 * now inherits Serializable implementation
 * 
 * @version 1.3 May 05, 2022 - added documentation
 */
public class CookingRecipe extends BasicRecipe
{
    // instance variables:
    
    // constructors:
    /** default constructor
     * calls super's default constructor
     */
    public CookingRecipe()
    {
        super();
    }
    
    //methods:
    @Override
    /** get recipe's details
     * @return recipe's details (Str) - cook-specific details added to super's method
     */
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
