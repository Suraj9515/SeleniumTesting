package Javaprogrammes;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class Read_All_PDF {

    public static void main(String[] args) {
        // Specify the folder path containing PDF files
        String folderPath = "G:\\QA_JOB_pdfs";

        // Get the folder as a File object
        File folder = new File(folderPath);

        // Check if the folder exists and is a directory
        if (folder.exists() && folder.isDirectory()) {
            // Filter and get all PDF files in the folder
            File[] pdfFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".pdf"));

            // Ensure there are PDF files in the folder
            if (pdfFiles != null && pdfFiles.length > 0) {
                for (File pdfFile : pdfFiles) {
//                    System.out.println("Reading file: " + pdfFile.getName());
                    try {
                        // Load each PDF file
                        PDDocument document = PDDocument.load(pdfFile);

                        // Extract text from the PDF
                        PDFTextStripper pdfStripper = new PDFTextStripper();
                        String text = pdfStripper.getText(document);

                        // Extract email addresses using regex
                        extractEmails(text);

                        // Close the document
                        document.close();
                    } catch (IOException e) {
                        System.out.println("Error reading file: " + pdfFile.getName());
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("No PDF files found in the folder: " + folderPath);
            }
        } else {
            System.out.println("The specified path is not a valid folder: " + folderPath);
        }
    }

    // Method to extract and print email addresses from text
    public static void extractEmails(String text) {
        String emailRegex = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(text);

//        System.out.println("Email addresses found:");
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }
}
