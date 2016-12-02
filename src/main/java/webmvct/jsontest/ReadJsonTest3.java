package webmvct.jsontest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


public class ReadJsonTest3 {
	
    public static void main(String[] args) throws IOException {
    	String url = "/products";
		String address = "src/main/resources/data/swagger4.json";
		File file = new File(address);
        Scanner scanner = null;
        StringBuilder buffer = new StringBuilder();
        try {
            scanner = new Scanner(file, "utf-8");
            while (scanner.hasNextLine()) {
                buffer.append(scanner.nextLine());
            }
 
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block  
 
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
         
        System.out.println(buffer.toString());
	}
}
