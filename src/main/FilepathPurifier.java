package main;

import java.io.File;

/**
 *
 * @author Antonio Soto
 */
public class FilepathPurifier {
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        File folder = new File("C:\\Bitstreams_Patrimonios\\DB_Archivos");
        purifyFilepaths(folder);
    }
    
    public static void purifyFilepaths(final File folder){
        
        for (final File fileEntry : folder.listFiles()) {
            
            if (fileEntry.isDirectory()) {
                
                String filepath = fileEntry.getAbsolutePath();
                String outputFilepath = replaceCharacters(filepath);

                File modFile = new File(outputFilepath);
                fileEntry.renameTo(modFile);
                
                System.out.println(fileEntry.getName());
                System.out.println(modFile.getName());
                
                purifyFilepaths(modFile);
            } else {
                if(fileEntry.getName().startsWith("._")){
                    ;
                }
                else{
                    
                    String filepath = fileEntry.getAbsolutePath();
                    String outputFilepath = replaceCharacters(filepath);

                    File modFile = new File(outputFilepath);
                    fileEntry.renameTo(modFile);

                    System.out.println(modFile.getAbsolutePath());
                }
            }
        }
    }
    
    private static String replaceCharacters(String input){
        
        String output = 
            input.replaceAll(" ", "_")
                    .replaceAll("á", "a")
                    .replaceAll("é", "e")
                    .replaceAll("í", "i")
                    .replaceAll("ó", "o")
                    .replaceAll("ú", "u")
                    .replaceAll("ñ", "n")
                    .replaceAll("Á", "A")
                    .replaceAll("É", "E")
                    .replaceAll("Í", "I")
                    .replaceAll("Ó", "O")
                    .replaceAll("Ú", "U")
                    .replaceAll("Ñ", "N");
        
        return output;
    }
}
