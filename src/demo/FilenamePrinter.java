package demo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Antonio Soto
 */
public class FilenamePrinter {
    
    private static BufferedWriter out = null;
    
    public static void main(String[] args) {
        
        try {
            
            File folder = new File("C:\\Bitstreams_Patrimonios\\DB_Archivos");
            out = new BufferedWriter(new FileWriter("names.xls"));
            listFilesForFolder(folder);
            out.close();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void listFilesForFolder(final File folder) {
        
        for (final File fileEntry : folder.listFiles()) {

            if (fileEntry.isDirectory()) {

                //System.out.println(fileEntry.getName());
                listFilesForFolder(fileEntry);
            } else {
                if(fileEntry.getName().startsWith("._")){
                    ;
                }
                else{

                    try {
                        System.out.println(fileEntry.getName());
                        out.write(fileEntry.getName()+"\n");
                        
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
    
}
