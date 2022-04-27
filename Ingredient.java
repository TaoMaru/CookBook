
/**Ingredient.java
 * This class contains the basic information needed for a recipe ingredient
 *
 * @author Maria Jackson
 * @version 1.0 Apr 22, 2022
 * @version 1.1 - Apr 23, 2022 - Added getters & setters for fields, and hasDetails()
 * @version 1.2 Apr 26, 2022 - Added Seriablizable implementation (does not have declared
 * static final serial field, but works as a part of a recipe's ingredient collection)
 */
import java.io.Serializable;

public class Ingredient implements Serializable
{
    // instance variables:
    private String name; // ingredient name
    private double amount; // amount of ingredient needed in grams
    private String details; // additional info about the ingredient
    
    //constructors:
    /** set all fields to default
     * 
     */
    public Ingredient()
    {
        name = ""; 
        amount = 0.0;
        details = "";
    }
    
    /**set name and amount fields, details set to default
     * @param name (Str) - name of ingredient
     * @param amount (double) - amount needed
     */
    public Ingredient(String name, double amount)
    {
        this.name = name;
        this.amount = amount;
        details = "";
    }
    
    /**set all fields
     * @param name (Str) - name of ingredient
     * @param amount (double) - amount needed
     * @param details (Str) - additional info about ingredient
     */
    public Ingredient(String name, double amount, String details)
    {
        this.name = name;
        this.amount = amount;
        this.details = details;
    }
    
    //methods:
    public String getName()
    {
        return name;
    }
    
    public double getAmount()
    {
        return amount;
    }
    
    public String getDetails()
    {
        return details;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public void setAmount(double amount)
    {
        this.amount = amount;
    }
    
    public void setDetails(String details)
    {
        this.details = details;
    }
    
    public boolean hasDetails()
    {
        return !(details.equals(""));
    }
}
