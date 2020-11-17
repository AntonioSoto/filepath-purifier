package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 *
 * @author Antonio Soto
 */
public class TemplatePrinter {
    
    private static final String EXCEL_FILE_LOCATION = "C:\\Users\\José\\Desktop\\Servicio Social\\FilepathPurifier\\output.xls";
    private static int row = 0;
    
    private static final String CSV_FILE = "C:\\Users\\José\\Desktop\\Servicio Social\\FilepathPurifier\\123456789-1.csv";
    private static ArrayList ids;
    private static ArrayList municipios;
    private static ArrayList nombres;
    
    
    public static void main(String[] args) {
        
        ids = new ArrayList();
        municipios = new ArrayList();
        nombres = new ArrayList();
        
        setupMetadataLists();
        
        printTemplate();
    }
    
    public static void printTemplate(){
        
        WritableWorkbook myFirstWbook = null;
        try {
            myFirstWbook = Workbook.createWorkbook(new File(EXCEL_FILE_LOCATION));
            WritableSheet excelSheet = myFirstWbook.createSheet("Sheet 1", 0);
            
            File folder = new File("C:\\Bitstreams_Patrimonios\\DB_Archivos");
            
            listFilesForFolder(folder, excelSheet);
            
            myFirstWbook.write();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {

            if (myFirstWbook != null) {
                try {
                    myFirstWbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void listFilesForFolder(final File folder, WritableSheet excelSheet) {
        
        for (final File fileEntry : folder.listFiles()) {

            if (fileEntry.isDirectory()) {
                
                listFilesForFolder(fileEntry, excelSheet);
            } else {
                if(fileEntry.getName().startsWith("._")){
                    ;
                }
                else{
                    try {
                        //System.out.println(fileEntry.getName());
                        Label label = new Label(0, row, "");
                        for(int i=0; i < ids.size(); i++){
                            if(fileEntry.getAbsolutePath().contains( (String)municipios.get(i) ) &&
                                    fileEntry.getAbsolutePath().contains( (String)nombres.get(i) )){
                                label = new Label(0, row, (String)ids.get(i));
                            }
                        }
                        excelSheet.addCell(label);
                        Label label2 = new Label(1, row, "Description");
                        excelSheet.addCell(label2);
                        Label label3 = new Label(2, row, fileEntry.getName());
                        excelSheet.addCell(label3);
                        Label label4 = new Label(3, row, fileEntry.getAbsolutePath());
                        excelSheet.addCell(label4);
                        row++;
                        
                    } catch (WriteException ex) {
                        Logger.getLogger(TemplatePrinter.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
    
    private static void setupMetadataLists(){
        
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {
            br = new BufferedReader(new FileReader(CSV_FILE));
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] column = line.split(cvsSplitBy);
                
                ids.add( column[0] );
                municipios.add( replaceCharacters(column[1]) );
                nombres.add( replaceCharacters(column[2]) );
                
                System.out.println(
                        "id = " + column[0] + 
                        " , Municipio = " + replaceCharacters(column[1]) + 
                        " , Nombre = " + replaceCharacters(column[2])
                );
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
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
