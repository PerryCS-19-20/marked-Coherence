/* 
 * The MIT License
 *
 * Copyright 2016 PerryHighCS.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package coherence;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The marked-coherence HTML pre-processor Create an HTML page from a lightly
 * formatted text file.
 *
 * @author mrbdahlem
 * @version 0.0.0
 */
public class Coherence {
    /**
     * @param args the command line arguments The first argument is the input
     * file to be processed The second argument is the HTML file to create The
     * (optional) third argument is a boilerplate HTML file to use to create the
     * output file
     */
    public static void main(String[] args) {
        // If the proper args weren't given, print usage text and exit
        if (args.length < 2) {
            System.out.println("Usage:");
            System.out.println("java -jar coherence.jar infile.txt outfile.html [boilerplate.html]");
            System.exit(1);
        }
        
        String boilerplate = "";
        String intext = "";
        
        // Load the input file
        try {
            intext = loadFile(args[0]);
        }
        catch (IOException e) {
            System.out.println("Could not load file: " + args[0]);
            System.exit(1);
        }
        
        // Load the html template file
        if (args.length < 3) {
            // If no boilerplate was specified, load the default boilerplate
            // built into the jar file
            try {
                boilerplate = loadResourceAsString("boilerplate.html");
            }
            catch (IOException e) {
                System.out.println("Could not load default boilerplate.");
                System.exit(1);
            }
        }
        else {
            // Load the specified boilerplate file
            try {
                boilerplate = loadFile(args[2]);
            }
            catch (IOException e) {
                System.out.println("Could not load default boilerplate.");
                System.exit(1);
            }
        }
        
        //***************************************************
        // Convert the input text to html here!!!
        //***************************************************
        String htmltext = boilerplate;
        
        // Write the html to the output file
        try (PrintWriter out = new PrintWriter(args[1])){
            out.print(htmltext);
        } catch (FileNotFoundException ex) {
            System.out.println("Could not write to file: " + args[1]);
            System.exit(1);
        }
    }

    /**
     * Load a file from disk into a String
     *
     * @param filepath The file to load
     *
     * @return the contents of the file as a String.
     */
    private static String loadFile(String filepath)
            throws IOException {
        byte[] filebytes = Files.readAllBytes(Paths.get(filepath));

        return new String(filebytes);
    }
    
    /**
     * Loads a resource from the jar file and returns its contents as a string.
     * 
     * @param name The resource file to load
     * @return A string representing the resource file's contents
     * @throws IOException 
     */
    private static String loadResourceAsString(String name)
        throws IOException {
        
        name = "resources/" + name;
        InputStream inputStream = Coherence.class.getResourceAsStream(name);
        
        // Split the inputStream on the end of input character... 
        // either there is input, or the file is empty.
        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
