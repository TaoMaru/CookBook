
/**CookBookDriver.java
 * This class serves as the user interface for creating, saving, and retreiving recipes
 *
 * @author Maria Jackson
 * @version 1.0 Apr 22, 2022
 * @version 1.1 Apr 23, 2022 - added method to store recipes as data with DataOutputStream
 * object. Added methods to read recipe data file and print the information.
 * Tested functions in main().
 * @version 1.2 Apr 26, 2022 - added documentation to existing methods, added Serializable 
 * implementation for ObjectOutput/InputStream classes: can now write/read binary files to
 * save/read recipe objects, will replace item-by-item methods of v1.1, added 
 * displayRecipes() method: streamlined organization & display of recipe info, respective
 * fields added
 */

import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

public class CookBookDriver implements Serializable
{
    // instance variables:
    private static int numRecipes; // number of recipes created
    private ArrayList<BasicRecipe> currentRecipes; // recipes in book
    BufferedReader inFile; // recipe text file reader
    private BasicRecipe newRecipe; // current recipe being made
    DataOutputStream outFile; // recipe data file writer
    DataInputStream inStreamFile; // recipe data file reader
    ObjectOutputStream outObject; // recipe object out stream
    ObjectInputStream inObject; // recipe object in stream
    private ArrayList<BasicRecipe> newRecipeBatch; // recipes from file
    
    // constructor:
    /** default no params constructor
     * 
     */
    public CookBookDriver()
    {
        numRecipes = 0;
        currentRecipes = new ArrayList<BasicRecipe>();
        newRecipeBatch = new ArrayList<BasicRecipe>();
    }
    
    //methods:
    //original object file method, just framework, unused
    /** get recipes from file
     * @param filename (Str) - .bin file containing recipes
     * @return currentRecipes (ArrayList) - collection of recipes
     */
    public ArrayList<BasicRecipe> getRecipes(String filename)
    {
        return currentRecipes;
    }
    
    //String version of read recipes from file idea, unused, possibly obsolete
    /** get String version of recipes
     * @param filename (Str) - .txt file containing recipes
     * recipeList (ArrayList) - collection of Str details of recipes
     */
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
    
    
    //Read & Write binary item-by-item recipe files: 
    //**likely obsolete with object file methods below**//
    /** save recipes in a file
     * @param filename (Str) - .bin file name to save recipes to
     */
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
    
    /** read recipe data from file
     * @param filename (Str) - .bin file name containing recipes data
     */
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
    
    
    // Recipe Object Write, Read, and Display methods: Added Apr 26. 2022
    public ArrayList<BasicRecipe> readRecipeObjectsFromFile(String filename)
    {
        BasicRecipe newRecipe = null;
        try
        {
            inObject = new ObjectInputStream(new FileInputStream( filename ));
            try
            {
                while(true)
                {
                    newRecipe = (BasicRecipe) inObject.readObject();
                    newRecipeBatch.add(newRecipe);
                }
            }
            catch(ClassNotFoundException e)
            {
                System.out.println("Object class not found.");
            }
            catch(EOFException e){}
        }
        catch(IOException e)
        {
            System.out.println("Error occured reading object from file.");
            System.out.println(e.getMessage());
        }
        return newRecipeBatch;
    }
    
    public void writeRecipeObjectsToFile(String filename)
    {
        try
        {
            outObject = new ObjectOutputStream( new FileOutputStream( filename ));
            for(int index = 0; index < currentRecipes.size(); index++)
            {
                outObject.writeObject(currentRecipes.get(index));
            }
            outObject.close();
        }
        catch(IOException e)
        {
            System.out.println("Error occurred writing recipe objects to file.");
            System.out.println(e.getMessage());
        }
    }
    
    public void displayRecipes(ArrayList<BasicRecipe> recipeArr)
    {
        for(int index = 0; index < recipeArr.size(); index++)
        {
            BasicRecipe recipe = recipeArr.get(index);
            ArrayList<Ingredient> ingredients = recipe.getIngredients();
            String recipeDetails = "";
            for(int i = 0; i < ingredients.size(); i++)
            {
                recipeDetails += "       " + ingredients.get(i).getName() + ": "
                                    + ingredients.get(i).getAmount() + "\n";
            }
            System.out.println(recipe.getTitle() + ": \n" + "   Ingredients: \n"
                + recipeDetails + "\n   " + recipe.getCookingTime());
            
        }
    }
    
    // execution:
    public static void main(String[] args)
    {
        CookBookDriver c = new CookBookDriver();
        // create a recipe:
        c.newRecipe = new BakingRecipe("Bread");
        c.newRecipe.addNewIngredient("Flour", 500.0);
        c.newRecipe.addNewIngredient("Water", 270.0);
        c.newRecipe.addNewIngredient("Yeast", 7.0);
        c.newRecipe.addNewIngredient("Salt", 8.0);
        c.newRecipe.addNewIngredient("Sugar", 10.0);
        c.newRecipe.setCookTime(30.0);
        //add recipe to currentRecipes collection:
        c.currentRecipes.add(c.newRecipe);
        c.numRecipes++; //increment total number of recipes
        
        //System.out.println(c.newRecipe.getFullRecipe()); <--old way
        
        //save and read binary recipe file: item-by-item
        c.writeToFile("Recipes01.bin");
        c.readRecipeDataFile("Recipes01.bin");
        
        // save and read binary recipe object file: entire objects
        c.writeRecipeObjectsToFile("Recipes02.bin");
        c.readRecipeObjectsFromFile("Recipes02.bin");
        c.displayRecipes(c.newRecipeBatch); // <--new way
        
    }
}
