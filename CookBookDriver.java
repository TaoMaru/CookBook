
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
 * @version 1.4 Apr 28, 2022 - added menu options for saving/loading recipe files & their
 * respective methods, added menu option for merging loaded recipes with new recipes,
 * modulated menu options 1-7 more clearly.
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
        + "\n5 - View loaded recipes"
        + "\n6 - Merge loaded & current recipes"
        + "\n7 - Search recipes by title"
        + "\n8 - Delete a recipe"
        + "\n9 - Exit";
    private String validOptions = "123456789";
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
    
    /**display greeting
     * 
     */
    public void displayGreeting()
    {
        System.out.println("Hey, Good Cookin'!"
            + "\nWelcome to the Virtual CookBook!"
            + "\nHere you can create, delete, save, and "
            + "\nload your recipes!");
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
        currentRecipes.add(recipe);
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
    
    /** merge current recipes ArrayList with loaded recipes ArrayList
     * newRecipeBatch added to currentRecipes
     */
    public void mergeRecipeLists()
    {
        if(newRecipeBatch.size() > 0)
        {
            System.out.println("Merging " + newRecipeBatch.size() + " recipes with"
                + " current recipes...");
            for(int index = 0; index < newRecipeBatch.size(); index++)
            {
                currentRecipes.add(newRecipeBatch.get(index));
                numRecipes++;
            }
        }
        else
        {
            System.out.println("There are no loaded recipes to merge.");
        }
        System.out.println("Changes only apply to current recipes list."
            + "\nAny saved or loaded files are unchanged.");
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
    /** read recipe objects from data file & add them to newRecipeBatch ArrayList
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
    
    /** write recipes as objects to data file item-by-item (outside of arrayList)
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
    
    //recipe display method:
    /** format and display recipe details (menu options 2 & 5)
     * @param recipeArr (ArrayList<BasicRecipe>) - recipes to print
     */
    public void displayRecipes(ArrayList<BasicRecipe> recipeArr)
    {
        System.out.println("Displaying " + recipeArr.size() + " recipes: ");
        for(int index = 0; index < recipeArr.size(); index++)
        {
            BasicRecipe recipe = recipeArr.get(index);
            String recipeDetails = "";
            ArrayList<Ingredient> ingredients = recipe.getIngredients();
            for(int i = 0; i < ingredients.size(); i++)
            {
                recipeDetails += "       " + ingredients.get(i).getName() + ": "
                                    + ingredients.get(i).getAmount() + "\n";
            }
            System.out.println("\n" + recipe.getTitle() + ": \n" + "   Ingredients: \n"
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
        ArrayList<BasicRecipe> inRecipeBatch = new ArrayList<BasicRecipe>();
        try
        {
            inObject = new ObjectInputStream(new FileInputStream( filename ));
            try
            {
                inRecipeBatch = (ArrayList<BasicRecipe>) inObject.readObject();
                System.out.println("Loading recipe file...");
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
        return inRecipeBatch;
    }
    
    /**write recipe ArrayList to file as one object
     * @param filename (Str) - name of file to write to
     */
    public void writeRecipeArrayListFile(String filename, ArrayList<BasicRecipe> recipeList)
    {
        try
        {
            outObject = new ObjectOutputStream(new FileOutputStream( filename ));
            outObject.writeObject(recipeList);
            outObject.close();
        }
        catch(IOException e)
        {
            System.out.println("Error occured writing ArrayList file.");
        }
    }
    
    /**save recipes list to file (menu option 3)
     * @param recipeList (ArrayList<BasicRecipe>) - list of recipes to save
     * @pre recipes list should not be empty when passed to method
     * @post entire recipes list is saved as object in .bin file
     */
    public void saveRecipeList(ArrayList<BasicRecipe> recipeList, String filename)
    {
        System.out.println("Saving recipe collection...");
        writeRecipeArrayListFile(filename, recipeList);
    }
    
    /** get filename and load a recipe array from file (menu option 4)
     * @post loaded recipe array is saved to newRecipeBatch ArrayList
     */
    public void loadRecipeList()
    {
        newRecipeBatch = readRecipeArrayListFile(getValidFilename());
    }
    
    /** get a new .bin filename
     * filenames are valid if ending in .bin
     * @return newFilename (Str) - .bin filename
     */
    public String getValidFilename()
    {
        invalid = true;
        String newFilename = "";
        do
        {
            System.out.println("Please enter the filename: ");
            if(inScan.hasNextLine())
            {
                newFilename = inScan.nextLine();
            }
            if(newFilename.endsWith(".bin"))
            {
                invalid = false;
            }
        }
        while(invalid);
        return newFilename;
    }
    
    
    // execution:
    public static void main(String[] args)
    {
        CookBookDriver c = new CookBookDriver();
        c.displayGreeting(); // greeting
        // prompt menu selection:
        c.menuChoice = ""; // menuChoice placeholder
        while(!c.menuChoice.equals("9")) // prompt menu until 9/Exit selected
        {
            c.menuChoice = c.getMenuChoice(); // display menu & get new choice
            if(c.menuChoice.equals("1")) // add new recipe
            {
                System.out.println("Creating a recipe!");
                c.getChoiceSetNewRecipe(); // get recipe type, create new recipe
                c.setNewTitle(); // add recipe title
            }
            else if(c.menuChoice.equals("2")) // view current recipes
            {
                if(c.currentRecipes.size() < 1) // no recipes in list
                {
                    System.out.println("There are no current recipes to display");
                }
                else // at least one recipe has been made
                {
                    c.displayRecipes(c.currentRecipes); // display all recipes
                }
            }
            else if(c.menuChoice.equals("3")) // save current recipes to file
            {
                c.saveRecipeList(c.currentRecipes, c.getValidFilename()); //save new file
            }
            else if(c.menuChoice.equals("4")) // load recipes from file
            {
                c.loadRecipeList(); // get filename, load recipes into newRecipeBatch
            }
            else if(c.menuChoice.equals("5")) // view loaded recipes
            {
                if(c.newRecipeBatch.size() <1) // no recipes in new recipe batch list
                {
                    System.out.println("There are no recipes from loaded file");
                }
                else // at least one recipe in new batch list
                {
                    c.displayRecipes(c.newRecipeBatch); // display all new batch recipes
                }
            }
            else if(c.menuChoice.equals("6")) // merge current & loaded recipe lists
            {
                c.mergeRecipeLists(); // add loaded recipes to current recipe list
            }
        }
        System.out.println("Goodbye!");
    }
}
