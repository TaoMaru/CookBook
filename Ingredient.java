
/**Ingredient.java
 * This class contains the basic information needed for a recipe ingredient
 *
 * @author Maria Jackson
 * @version 1.0 Apr 22, 2022
 * 
 * @version 1.1 - Apr 23, 2022 - Added getters & setters for fields, and hasDetails()
 * 
 * @version 1.2 Apr 26, 2022 - Added Seriablizable implementation (does not have declared
 * static final serial field, but works as a part of a recipe's ingredient collection)
 * 
 * @version 1.3 May 04, 2022 - Added documentation for all methods.
 * 
 * @version 1.4 May 07, 2022 -Added measurement unit field, updated constructors as needed,
 * added set/getter for unit
 */
import java.io.Serializable;

public class Ingredient implements Serializable
{
    // instance variables:
    private String name; // ingredient name
    private double amount; // amount of ingredient needed in grams
    private String unit; // the measurement type for ingredient
    private String details; // additional info about the ingredient
    
    //constructors:
    /** set all fields to default
     * 
     */
    public Ingredient()
    {
        name = ""; 
        amount = 0.0;
        unit = "";
        details = "";
    }
    
    /**set name, amount, and unit fields, details set to default
     * @param name (Str) - name of ingredient
     * @param amount (double) - amount needed
     */
    public Ingredient(String name, double amount, String unit)
    {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
        details = "";
    }
    
    /**set all fields
     * @param name (Str) - name of ingredient
     * @param amount (double) - amount needed
     * @param unit (Str) - measurement type
     * @param details (Str) - additional info about ingredient
     */
    public Ingredient(String name, double amount, String unit, String details)
    {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
        this.details = details;
    }
    
    //methods:
    /** get ingredient name
     * @return name (Str) - ingredient name
     */
    public String getName()
    {
        return name;
    }
    
    /** get ingredient amount
     * @return amount (double) - ingredient amount
     */
    public double getAmount()
    {
        return amount;
    }
    
    /** get ingredient unit
     * @return unit (Str) - ingredient measurement type
     */
    public String getUnit()
    {
        return unit;
    }
    
    /** get ingredient details
     * @return details (Str) - ingredient details
     */
    public String getDetails()
    {
        return details;
    }
    
    /** set ingredient name
     * @param name (Str) - ingredient name
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    /** set amount needed
     * @param amount (double) - amount of ingredient
     */
    public void setAmount(double amount)
    {
        this.amount = amount;
    }
    
    /** set unit
     * @param unit (Str) - the measurement type for ingredient
     */
    public void setUnit(String unit)
    {
        this.unit = unit;
    }
    
    /** set details
     * @param details (Str) - ingredient details
     */
    public void setDetails(String details)
    {
        this.details = details;
    }
    
    /** check if ingredient has extra details
     * @return boolean - true if ingredient has extra details
     */
    public boolean hasDetails()
    {
        return !(details.equals(""));
    }
}
