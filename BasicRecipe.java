
/**BasicRecipe.java
 * This abstract class details the basic information needed for a cooking recipe
 *
 * @author Maria Jackson
 * @version 1.0 Apr 22, 2022
 * 
 * @version 1.1 Apr 23, 2022 - Added getters and setters for fields, added getIngredients
 * method: allows access to entire ArrayList of ingredients.
 * 
 * @version 1.2 Apr 26, 2022 - Added Serializable implementation, added abstract
 * getCookingTime() method
 * 
 * @version 1.3 May 05, 2022 - Added instructions field and accompanying get & set methods,
 * added documentation, added method to add step to instructions
 * 
 * @version 1.4 May 07, 2022 - Updated addIngredient method to reflect new unit field
 */
import java.util.ArrayList;
import java.io.Serializable;

public abstract class BasicRecipe implements Serializable
{
    // instance variables:
    protected static final long serialVersionUID = 1L;
    
    protected String title; // recipe title
    protected ArrayList<Ingredient> ingredients; // list of ingredients
    protected double cookTime; // total cook time
    protected ArrayList<String> instructions; // list of instructions
    
    // constructors:
    /** all fields set to default values
     * 
     */
    public BasicRecipe()
    {
        ingredients = new ArrayList<Ingredient>();
        title = "";
        cookTime = 0.0;
        instructions = new ArrayList<String>();
    }
    
    /** set recipe title, all other fields set to default
     * 
     */
    public BasicRecipe(String title)
    {
        this.title = title;
        ingredients = new ArrayList<Ingredient>();
        cookTime = 0.0;
        instructions = new ArrayList<String>();
    }
    
    /** set all fields except instructions, which are default
     * @param title (str) - title of recipe
     * @param ingredients (ArrayList<Ingredient>) - list of ingredients
     * @param cookTime (double) - total cook time
     */
    public BasicRecipe(String title, ArrayList<Ingredient> ingredients, double cookTime)
    {
        this.title = title;
        this.ingredients = ingredients;
        this.cookTime = cookTime;
        instructions = new ArrayList<String>();
    }
    
    // methods:
    /** add an Ingredient to recipe
     * calls Ingredient constructor
     * @param name (Str) - ingredient's name
     * @param amount (double) - amount of ingredient
     * @param unit (Str) - measurement type
     * @post adds new ingredient to recipe's ingredient ArrayList
     * @return newIngredient (Ingredient) - the new ingredient object
     */
    public Ingredient addNewIngredient(String name, double amount, String unit)
    {
        Ingredient newIngredient = new Ingredient(name, amount, unit);
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
    
    public String getFullRecipeWithForEach()
    {
       String organizedIngredients = "";
       ArrayList<Ingredient> allIngredients = getIngredients();
       for(Ingredient i : allIngredients)
       {
           organizedIngredients += String.valueOf(i) + "\n";
       }
       return title + "\n\t" + allIngredients;
    }
    
    /** set recipe title
     * @param title (Str) - recipe's new title
     */
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    /** set cook time
     * @param cookTime (double) - total cook time
     */
    public void setCookTime(double cookTime)
    {
        this.cookTime = cookTime;
    }
    
    /**set the ingredients
     * @param ingredients (ArrayList<Ingredient>) - the collection of ingredients
     */
    public void setIngredients(ArrayList<Ingredient> ingredients)
    {
        this.ingredients = ingredients;
    }
    
    /** set the instructions
     * @param instructions (ArrayList<String>) - list of instructions
     */
    public void setInstructions(ArrayList<String> instructions)
    {
        this.instructions = instructions;
    }
    
    /** add a step to instructions
     * @post a step is added to the end of the instructions ArrayList
     */
    public void addStep(String newStep)
    {
        instructions.add(newStep);
    }
    
    /** get the recipe title
     * @return title (Str) - recipe's title
     */
    public String getTitle()
    {
        return title;
    }
    
    /** get the cook time
     * @return cookTime (double) - total cook time
     */
    public double getCookTime()
    {
        return cookTime;
    }
    
    /** get the full list of ingredients
     * @return ingredients (ArrayList<Ingredient>) - collection of recipe's ingredients
     */
    public ArrayList<Ingredient> getIngredients()
    {
        return ingredients;
    }
    
    /** get recipe's instructions
     * @return ArrayList<String> instructions - list of recipe's instructions
     */
    public ArrayList<String> getInstructions()
    {
        return instructions;
    }
    
    /** get recipe type-specific cooking time
     * 
     */
    public abstract String getCookingTime();
    
}
