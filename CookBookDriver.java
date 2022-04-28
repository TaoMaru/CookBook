
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
 * @version 1.3 Apr 27, 2022 - added menu/selection functionality, organized fields/methods
 */

import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

public class CookBookDriver implements Serializable
{
    // instance variables:
    private static int numRecipes; // number of recipes
    private ArrayList<BasicRecipe> currentRecipes; // recipes made in session
    private BasicRecipe newRecipe; // current recipe being made
    private String menu = "\nCookBook Menu"
        + "\n1 - Add a new recipe"
        + "\n2 - View current recipes"
        + "\n3 - Save current recipes to file"
        + "\n4 - Load saved recipes from file"
        + "\n5 - Search recipes by title"
        + "\n6 - Delete a recipe"
        + "\n7 - Exit";
    private String validOptions = "1234567";
    private String menuChoice;
    private String recipeOptions = "Recipe Types:"
        + "\nB - Baking Recipe"
        + "\nC - Cooking Recipe";
    private String validRecipeTypes = "BC";
    private String typeChoice;
    private Scanner inScan;
    private boolean invalid;
    // file IO fields:
    BufferedReader inFile; // recipe text file reader
    DataOutputStream outFile; // recipe data file writer
    DataInputStream inStreamFile; // recipe data file reader
    ObjectOutputStream outObject; // recipe object out stream
    ObjectInputStream inObject; // recipe object in stream
    private ArrayList<BasicRecipe> newRecipeBatch; // recipes from file
    
    // constructor:
    /** default no params constructor
     * numRecipes, currentRecipes, newRecipeBatch fields set to default
     */
    public CookBookDriver()
    {
        numRecipes = 0; // default number of recipes
        currentRecipes = new ArrayList<BasicRecipe>(); // create session's recipe list
        newRecipeBatch = new ArrayList<BasicRecipe>(); // create file input recipe list
        inScan = new Scanner(System.in); // create new scanner for input
    }
    
    //methods:
    /** display menu options
     * 
     */
    public void displayMenu()
    {
        System.out.println(menu);
    }
    
    /** display recipe type options
     * 
     */
    public void displayRecipeTypeOptions()
    {
        System.out.println(recipeOptions);
    }
    
    /** get valid menu choice input
     * @return menuChoice (Str) - valid option selected from menu
     */
    public String getMenuChoice()
    {
        invalid = true;
        do
        {
            displayMenu();
            System.out.println("Please select from the menu: ");
            if(inScan.hasNextLine())
            {
                menuChoice = inScan.nextLine();
                if(menuChoice.length() == 1 && validOptions.contains(menuChoice))
                {
                    invalid = false;
                }
                else
                {
                    System.out.println(menuChoice + " is not a valid option.");
                }
            }
        }
        while(invalid);
        System.out.println("You entered: " + menuChoice);
        return menuChoice;
    }
    
    /** get valid recipe type choice
     * @return typeChoice (Str) - selected recipe type
     */
    public String getRecipeTypeChoice()
    {
        invalid = true;
        do
        {
            displayRecipeTypeOptions();
            System.out.println("Please choose a recipe type: ");
            if(inScan.hasNextLine())
            {
                typeChoice = inScan.nextLine().toUpperCase();
                if(typeChoice.length() == 1 && validRecipeTypes.contains(typeChoice))
                {
                    invalid = false;
                }
                else
                {
                    System.out.println(typeChoice + " is not a valid recipe type.");
                }
            }
        }
        while(invalid);
        return typeChoice;
    }
    
    //recipe creation, deletion, and editing methods:
    /** create new recipe by type
     * @param type (Str) - the type of recipe to be made
     * @pre type selection must be made and passed to method
     * @post new recipe object created, added to currentRecipes, numRecipes incremented
     */
    public BasicRecipe createNewRecipe(String type)
    {
        BasicRecipe recipe = null;
        switch(type)
        {
            case "B" : recipe = new BakingRecipe();
            break;
            case "C" : recipe = new CookingRecipe();
            break;
        }
        currentRecipes.add(newRecipe);
        numRecipes++;
        return recipe;
    }
    
    /**get recipe type choice and create a new recipe obj, newRecipe
     * 
     */
    public void getChoiceSetNewRecipe()
    {
        newRecipe = createNewRecipe(getRecipeTypeChoice());
    }
    
    /**get new recipe title input
     * @param typeOption (Str) - the type of recipe being made, used for prompt
     */
    public String getNewTitle(String typeOption)
    {
        String newTitle = "";
        String type = "baking";
        if(typeOption.equals("C"))
        {
            type = "cooking";
        }
        System.out.println("Please enter the title for this " + type + " recipe: ");
        if(inScan.hasNextLine())
        {
            newTitle = inScan.nextLine();
        }
        System.out.println("You entered: " + newTitle);
        return newTitle;
    }
    
    /** set new recipe title
     * 
     */
    public void setNewTitle()
    {
        newRecipe.setTitle(getNewTitle(typeChoice));
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
    
    
    // Recipe Object Write, Read, and Display methods: Added Apr 26. 2022
    /** read & pull recipe objects from data file
     * @param filename (Str) - .bin file to read
     */
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
                System.out.println("Class not found.");
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
    
    /** write recipes as objects to data file
     * @param filename (Str) - .bin filename to write to
     */
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
    
    /** format and display recipe details
     * @param recipeArr (ArrayList<BasicRecipe>) - recipes to print
     */
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
    
    
    // Read & Write ArrayList recipe data file methods: added Apr 27, 2022
    /** get recipe ArrayList from file
     * @param filename (Str) - .bin file containing recipes
     * @return newRecipeBatch (ArrayList) - collection of recipes from file
     */
    public ArrayList<BasicRecipe> readRecipeArrayListFile(String filename)
    {
        try
        {
            inObject = new ObjectInputStream(new FileInputStream( filename ));
            try
            {
                newRecipeBatch = (ArrayList<BasicRecipe>) inObject.readObject();
            }
            catch(ClassNotFoundException e)
            {
                System.out.println("Class not found.");
            }
            catch(EOFException e){}
        }
        catch(IOException e)
        {
            System.out.println("Error reading recipe ArrayList file.");
            System.out.println(e.getMessage());
        }
        return newRecipeBatch;
    }
    
    /**write recipe ArrayList to file as one object
     * @param filename (Str) - name of file to write to
     */
    public void writeRecipeArrayListFile(String filename)
    {
        try
        {
            outObject = new ObjectOutputStream(new FileOutputStream( filename ));
            outObject.writeObject(currentRecipes);
            outObject.close();
        }
        catch(IOException e)
        {
            System.out.println("Error occured writing ArrayList file.");
        }
    }
    
    
    // execution:
    public static void main(String[] args)
    {
        CookBookDriver c = new CookBookDriver();
        /* Pre Apr 27, 2022 recipe creation trial *
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
        
        /*save and read binary recipe file: item-by-item
        c.writeToFile("Recipes01.bin");
        c.readRecipeDataFile("Recipes01.bin");
        
        
        // save and read binary recipe object file: entire objects
        c.writeRecipeObjectsToFile("Recipes02.bin");
        c.readRecipeObjectsFromFile("Recipes02.bin");
        c.displayRecipes(c.newRecipeBatch); // <--new way
        */
       
        //*New Recipe Creation Trial:Apr27*
        System.out.println("Hey, Good Cookin'!"
            + "\nWelcome to the Virtual CookBook!"
            + "\nHere you can create,delete, save, and load your recipes!");
        c.menuChoice = "";
        while(!c.menuChoice.equals("7"))
        {
            c.menuChoice = c.getMenuChoice();
            if(c.menuChoice.equals("1"))
            {
                System.out.println("Creating a recipe!");
                c.getChoiceSetNewRecipe();
                c.setNewTitle();
            }
        }
        System.out.println("Goodbye!");
    }
}
