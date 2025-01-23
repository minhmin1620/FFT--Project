//Author: Mackenzie Carter
package dataaccess;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import model.pojos.Buyer;
//Contributing Authors: Mackenzie Carter, Minh nguyen, Yousef Khai
public class BuyerAccess {
    private static final String FILE_PATH = System.getProperty("user.home") + "/Documents/BuyerList.xlsx";

    
    public static List<Buyer> loadBuyers() {
        List<Buyer> buyers = new ArrayList<>();
        File file = new File(FILE_PATH);
        
        if (!file.exists()) {
            createNewFile(); 
            return buyers;
        }

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; 

                int id = (int) row.getCell(0).getNumericCellValue();
                String initials = row.getCell(1).getStringCellValue();
                buyers.add(new Buyer(id, initials));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buyers;
    }
    
    public static List<String> loadCategories() {
        List<String> categories = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            createNewFile(); 
            return categories;
        }

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

        	Sheet sheet = workbook.getSheet("Categories");
        	if (sheet == null) {
        	    sheet = workbook.createSheet("Categories");
        	    Row header = sheet.createRow(0);
        	    header.createCell(0).setCellValue("Category");
        	    try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
        	        workbook.write(fos); 
        	    }
        	}

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; 
                String category = row.getCell(0).getStringCellValue();
                categories.add(category);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return categories;
    }


    public static void clearBuyers() {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Buyers");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Initials");

            try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
                workbook.write(fos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public static void addBuyer(Buyer buyer) {
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            int newRowNum = sheet.getLastRowNum() + 1;

            Row newRow = sheet.createRow(newRowNum);
            newRow.createCell(0).setCellValue(buyer.getId());
            newRow.createCell(1).setCellValue(buyer.getInitials());

            try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
                workbook.write(fos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

  
    private static void createNewFile() {
        try (Workbook workbook = new XSSFWorkbook()) {
            
            Sheet buyersSheet = workbook.createSheet("Buyers");
            Row buyersHeader = buyersSheet.createRow(0);
            buyersHeader.createCell(0).setCellValue("ID");
            buyersHeader.createCell(1).setCellValue("Initials");

            Sheet categoriesSheet = workbook.createSheet("Categories");
            Row categoriesHeader = categoriesSheet.createRow(0);
            categoriesHeader.createCell(0).setCellValue("Category");

            try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
                workbook.write(fos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void addCategory(String categoryName) {
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet;
            if (workbook.getNumberOfSheets() <= 1) {
                sheet = workbook.createSheet("Categories"); 
                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("Category");
            } else {
                sheet = workbook.getSheetAt(1); 
            }

            int newRowNum = sheet.getLastRowNum() + 1;
            Row newRow = sheet.createRow(newRowNum);
            newRow.createCell(0).setCellValue(categoryName);

            try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
                workbook.write(fos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static List<String> loadBuyersInitial(){
    	List<String> initials = new ArrayList<>();
    	for(Buyer i : loadBuyers()) {
    		initials.add(i.getInitials());
    	}
    	return initials;
    }


    public static void clearCategories() {
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet("Categories");
            if (sheet == null) {
                sheet = workbook.createSheet("Categories");
            }
            
            int lastRow = sheet.getLastRowNum();
            for (int i = 1; i <= lastRow; i++) {
                sheet.removeRow(sheet.getRow(i));
            }

            try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
                workbook.write(fos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}