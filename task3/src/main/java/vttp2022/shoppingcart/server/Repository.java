package vttp2022.shoppingcart.server;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Repository {
    
    // properties 
    private String repoDirectory = "";
    List<String> repoList = new LinkedList<>(); // linkedList to store existing content in a file, then to transfer to Cart and added to cartList 

    // constructors
    public Repository (String directory) { // constructor for main to pass in the parameter args[0]
        this.repoDirectory = directory; 
    }

    // methods
    public void open () {
        File dir = new File(repoDirectory);
        if (!dir.isDirectory()) // if directory folder does not exist
        {
            dir.mkdir(); // create a new directory folder
            System.out.printf("The %s directory has been created,\n" , repoDirectory);
        }
        System.out.printf("Using %s directory for persistence\n" , repoDirectory);
        int fileCount = dir.list().length; // count the number of files in the directory folder
        System.out.printf("There are %d carts in %s directory\n" , fileCount, repoDirectory);
        System.out.println("Connection received...");
    }

    public void loadFile (String userName) throws IOException {
        System.out.println();
        String fileNameDirectory = repoDirectory + "/" + userName + ".txt";
        //String fileNameDirectory = "." + "/" + repoDirectory + "/" + userName + ".txt"; // directory of the file
        File file = new File(fileNameDirectory); // instantiate new File Class

        if (!file.isFile()) {
            file.createNewFile(); // if file does not exist, create a new file
            System.out.printf("%s's cart has been created,\n" , userName);
        }

        System.out.printf("%s's shopping cart loaded\n" , userName);
    }

    public String getDirectory() {
        return this.repoDirectory; // getter method to get the directory, so that it can be used in other classes like Session
    }

    public List<String> readFile(String filePath) throws FileNotFoundException, IOException {
        FileReader r = new FileReader(filePath); // instantiate new FileReader
        BufferedReader br = new BufferedReader(r); // instantiate new BufferedReader
        while (true) {
            String contentLine = br.readLine(); // store the read line into a string 
            if (contentLine == null) // check if it is null and exit the loop, otherwise there will be a nullPointerError
                break;
            repoList.add(contentLine.substring(3)); // add each line into the list, excluding the first 3 characters, to avoid duplicate numbering          
        }
        br.close();
        return repoList;
    }

}
