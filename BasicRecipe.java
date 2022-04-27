
/**BasicRecipe.java
 * This abstract class details the basic information needed for a cooking recipe
 *
 * @author Maria Jackson
 * @version 1.0 Apr 22, 2022
 * @version 1.1 Apr 23, 2022 - Added getters and setters for fields, added getIngredients
 * method: allows access to entire ArrayList of ingredients.
 * @version 1.2 Apr 26, 2022 - Added Serializable implementation, added abstract
 * getCookingTime() method
 */
import java.util.ArrayList;
import java.io.Serializable;

public abstract class BasicRecipe implements Serializable
{
    // instance variables:
    protected static final long serialVersionUID = 1L;
    
    protected String title;
    protected ArrayList<Ingredient> ingredients;
    protected double cookTime;
    
    // constructors:
    /**fields set to default values
     * 
     */
    public BasicRecipe()
    {
        ingredients = new ArrayList<Ingredient>();
        title = "";
        cookTime = 0.0;
    }
    
    /**recipe title set, all other fields default
     * 
     */
    public BasicRecipe(String title)
    {
        this.title = title;
        ingredients = new ArrayList<Ingredient>();
        cookTime = 0.0;
    }
    
    // methods:
    public Ingredient addNewIngredient(String name, double amount)
    {
        Ingredient newIngredient = new Ingredient(name, amount);
        ingredients.add(newIngredient);
        return newIngredient;
    }
    
    /**get the full recipe as String
     * 
     */
    public String getFullRecipe()
    {
       String organizedIngredients = "";
       ArrayList allIngredients = getIngredients();
       for(int index = 0; index < allIngredients.size(); index++)
       {
           organizedIngredients += String.valueOf(allIngredients.get(index)) + "\n";
       }
       return title + "\n\t" + allIngredients;
    }
    
    /** get the full list of ingredients
     * @return ingredients (ArrayList) - collection of recipe's ingredients
     */
    public ArrayList getIngredients()
    {
        return ingredients;
    }
    
    /**set the ingredients
     * @param ingredientList (ArrayList) - the collection of ingredients
     */
    public void setIngredients(ArrayList ingredientList)
    {
        ingredients = ingredientList;
    }
    
    public void setCookTime(double cookTime)
    {
        this.cookTime = cookTime;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public double getCookTime()
    {
        return cookTime;
    }
    
    public abstract String getCookingTime();
}
