
/**CookingRecipe.java
 * This class holds the details for a cooking recipe
 *
 * @author Maria Jackson
 * @version 1.0 Apr 22, 2022
 */
public class CookingRecipe extends BasicRecipe
{
    // instance variables:
    //double cookTime; // cooking time in minutes
    
    // constructors:
    public CookingRecipe()
    {
        super();
        cookTime = 0.0;
    }
    
    //methods:
    public String getFullRecipe()
    {
        return super.getFullRecipe() + "\n\t"
            + "Cook for " + String.valueOf(cookTime);
    }
}
