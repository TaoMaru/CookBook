
/**CookBookDriver.java
 * This class serves as the user interface for creating, saving, and retreiving recipes
 *
 * @author Maria Jackson
 * @version 1.0 Apr 22, 2022
 * @version 1.1 Apr 23, 2022 - added method to store recipes as data with DataOutputStream
 * object. Added methods to read recipe data file and print the information.
 * Tested functions in main().
 */

import java.util.ArrayList;
import java.io.*;

public class CookBookDriver
{
    // instance variables:
    private static int numRecipes; // number of recipes created
    private ArrayList<BasicRecipe> currentRecipes; // recipes in book
    BufferedReader inFile; // recipe text file reader
    private BasicRecipe newRecipe; // current recipe being made
    DataOutputStream outFile; // recipe data file writer
    DataInputStream inStreamFile; // recipe data file reader
    
    // constructor:
    public CookBookDriver()
    {
        numRecipes = 0;
        currentRecipes = new ArrayList<BasicRecipe>();
    }
    
    //methods:
    public ArrayList<BasicRecipe> getRecipes(String filename)
    {
        return currentRecipes;
    }
    
    public ArrayList<String> getStringRecipeList(String filename)
    {
        ArrayList<String> recipeList = new ArrayList<String>();
        try
        {
            inFile = new BufferedReader(new FileReader(filename));
            String recipeContent = inFile.readLine();
            while(recipeContent != null)
            {
                recipeList.add(recipeContent.trim());
                recipeContent = inFile.readLine();
            }
        }
        catch(IOException e)
        {
            System.out.println("Sorry, an error occurred accessing file.");
            return recipeList;
        }
        return recipeList;
    }
    
    public void writeToFile(String filename)
    {
        try
        {
            outFile = new DataOutputStream(new FileOutputStream( filename ));
            for(int index = 0; index < currentRecipes.size(); index++)
            {
                BasicRecipe recipe = currentRecipes.get(index);
                outFile.writeUTF(recipe.getTitle());
                outFile.writeUTF("Ingredients: ");
                ArrayList<Ingredient> ingredientList = recipe.getIngredients();
                for(int inIndex = 0; inIndex < ingredientList.size(); inIndex++)
                {
                    outFile.writeUTF(ingredientList.get(inIndex).getName());
                    outFile.writeDouble(ingredientList.get(inIndex).getAmount());
                }
                outFile.writeUTF("Cook Time: ");
                outFile.writeDouble(recipe.getCookTime());
            }
            outFile.close();
        }
        catch(IOException e)
        {
            System.out.println("Error writing file.");
        }
    }
    
    public void readRecipeDataFile(String filename)
    {
        try
        {
            inStreamFile = new DataInputStream(new FileInputStream( filename));
            try
            {
                while(true)
                {
                    String recipeTitle = inStreamFile.readUTF();
                    String recipeIngLabel = inStreamFile.readUTF();
                    String ing1Name = inStreamFile.readUTF();
                    double ing1Amt = inStreamFile.readDouble();
                    String ing2Name = inStreamFile.readUTF();
                    double ing2Amt = inStreamFile.readDouble();
                    String ing3Name = inStreamFile.readUTF();
                    double ing3Amt = inStreamFile.readDouble();
                    String ing4Name = inStreamFile.readUTF();
                    double ing4Amt = inStreamFile.readDouble();
                    String ing5Name = inStreamFile.readUTF();
                    double ing5Amt = inStreamFile.readDouble();
                    String cookLabel = inStreamFile.readUTF();
                    double recipeTime = inStreamFile.readDouble();
                    System.out.println(recipeTitle + "\n\t" + recipeIngLabel + "\n\t\t"
                        + ing1Name + " " + String.valueOf(ing1Amt) + "\n\t\t"
                        + ing2Name + " " + String.valueOf(ing2Amt) + "\n\t\t"
                        + ing3Name + " " + String.valueOf(ing3Amt) + "\n\t\t"
                        + ing4Name + " " + String.valueOf(ing4Amt) + "\n\t\t"
                        + ing5Name + " " + String.valueOf(ing5Amt) + "\n\t"
                        + cookLabel + " " + String.valueOf(recipeTime));
                }
            }
            catch(EOFException e){}
            finally
            {
                inStreamFile.close();
            }
        }
        catch(IOException e)
        {
            System.out.println("Error occurred while reading file.");
        }
    }
    
    public static void main(String[] args)
    {
        CookBookDriver c = new CookBookDriver();
        c.newRecipe = new BakingRecipe("Bread");
        c.newRecipe.addNewIngredient("Flour", 500.0);
        c.newRecipe.addNewIngredient("Water", 270.0);
        c.newRecipe.addNewIngredient("Yeast", 7.0);
        c.newRecipe.addNewIngredient("Salt", 8.0);
        c.newRecipe.addNewIngredient("Sugar", 10.0);
        c.newRecipe.setCookTime(30.0);
        c.currentRecipes.add(c.newRecipe);
        c.numRecipes++;
        System.out.println(c.newRecipe.getFullRecipe());
        c.writeToFile("Recipes01.bin");
        System.out.println("Success!");
        c.readRecipeDataFile("Recipes01.bin");
    }
}
