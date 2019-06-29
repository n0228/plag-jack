package anups.htclan.plag.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import com.google.gson.Gson;

import anups.htclan.plag.constants.ProjectEnum;
import anups.htclan.plag.core.FileUtilCore;
import anups.htclan.plag.pojo.FileReadLetterPojo;
import anups.htclan.plag.pojo.FileReadWordPojo;

public class FileReadManager {
 private String FILE_INFO_PATH;
 private String FILE_INFO_NAME;
 private String FILE_INFO_EXTENSION;
 private int FILE_CONTENT_LINES = 0;
 private ArrayList<Integer> FILE_CONTENT_LBLWORDCOUNT = new ArrayList<Integer>();
 private ArrayList<String> FILE_CONTENT_WORDS = new ArrayList<String>(); // Add Also line Index
 private int FILE_CONTENT_TOTALWORDS = 0;
 private StringBuilder CONTENT = new StringBuilder();
 private ArrayList<FileReadWordPojo> FILE_COMBO_CONTENT = new ArrayList<FileReadWordPojo>();
  
 public FileReadManager(String fileWithPath){
   /* File management */
   FileUtilCore fileUtilCore = new FileUtilCore(fileWithPath);
   FILE_INFO_PATH = fileUtilCore.getFilePath();
   FILE_INFO_NAME = fileUtilCore.getFileName();
   FILE_INFO_EXTENSION = fileUtilCore.getFileExtension();
   /* Read the File */
   try {
	   BufferedReader br = new BufferedReader(new FileReader(fileWithPath));
	   for(String line = br.readLine();line!=null;) {
		   this.CONTENT.append(line);
		   this.FILE_CONTENT_LINES++;
		   int count = 0;
		   int lineIndex=0;
		   
		   for(String word : line.split(" ")) { 
			  count++;
			  
		      FILE_CONTENT_WORDS.add(word);
		      
		   }
		   FILE_CONTENT_LBLWORDCOUNT.add(count);
		   FILE_CONTENT_TOTALWORDS+=count;
		   line = br.readLine();
	   }
	   
	   br.close();
   } catch(Exception e) {
	   e.printStackTrace();
   }
  }
  
 public void fileStatistics() {
	System.out.println("----------------------------------------------------------------------------");
	System.out.println("FILE STATISTICS:");
	System.out.println("----------------------------------------------------------------------------");
	System.out.println("FILE_INFO_PATH           : "+FILE_INFO_PATH);
	System.out.println("FILE_INFO_NAME           : "+FILE_INFO_NAME);
	System.out.println("FILE_INFO_EXTENSION      : "+FILE_INFO_EXTENSION);
	System.out.println("FILE_CONTENT_LINES       : "+FILE_CONTENT_LINES); 
	System.out.println("FILE_CONTENT_TOTALWORDS  : "+FILE_CONTENT_TOTALWORDS); 
	System.out.println("----------------------------------------------------------------------------"); 
	System.out.println("WORDS (LINE BY LINE)"); 
	System.out.println("----------------------------------------------------------------------------"); 
	for(int index=0;index<FILE_CONTENT_LBLWORDCOUNT.size();index++) {
		System.out.println("Line - "+(index+1)+" : "+FILE_CONTENT_LBLWORDCOUNT.get(index));
	}
  }
  
 public void combinations() {
   Gson gson = new Gson();
   for(int i=0;i<FILE_CONTENT_TOTALWORDS;i++) {
	for(int j=0;j<FILE_CONTENT_TOTALWORDS;j++) {
	  if(i<=j) {
		  StringBuilder sb = new StringBuilder();
		  for(int k=i;k<=j;k++) {
			  sb.append(FILE_CONTENT_WORDS.get(k)).append(" ");
		  }
		  String combination = sb.toString().trim().replaceAll("\\p{Punct}","");
		  // Combinations
		  
		  /* Duplicate Combination Exists */
		  int comboRecognizerCount = 1;
		  int comboRecognizerIndex = 0;
		  for(int index=0;index<FILE_COMBO_CONTENT.size();index++) {
			  FileReadWordPojo fileReadPojo = FILE_COMBO_CONTENT.get(index);
			  String word = (String) fileReadPojo.getWord().replaceAll("\\p{Punct}","");
			  /* If word contains RegExp remove and check */
			  if(word.equalsIgnoreCase(combination)) {
				  comboRecognizerCount++;
				  comboRecognizerIndex = index;
			  }
		  }
		  FileReadWordPojo fileReadPojo = new FileReadWordPojo();
		  fileReadPojo.setWordCount(comboRecognizerCount);
		  fileReadPojo.setWord(combination);
		  
		  for(int index=0;index<FILE_CONTENT_LBLWORDCOUNT.size();index++) {
			int lineNumber = (index+1);
			int WordsInLine = FILE_CONTENT_LBLWORDCOUNT.get(index);
			if(i<WordsInLine) {
				System.out.println("combination: "+combination+" lineNumber: "+lineNumber+"WordsInLine:"+WordsInLine+" i:"+i+" j:"+j);
			}
			
		  }
		  /* Duplicate Combination Exists */
		  if(comboRecognizerCount>1) {
			  FILE_COMBO_CONTENT.set(comboRecognizerIndex, fileReadPojo);
		  } else {
			  FILE_COMBO_CONTENT.add(fileReadPojo);
		  }
	  }
	}
   }
   /*  Upload Combinations to the File */
   try {
   String comboFilePath = ProjectEnum.PROJECT_DIR_MAIN.value() + ProjectEnum.PROJECT_TITLE_PLAGFILES.value()+"\\combo\\"+FILE_INFO_NAME+"-combo.json";
   FileWriter fileWriter =new FileWriter(comboFilePath);
   fileWriter.write(gson.toJson(FILE_COMBO_CONTENT)); 
   fileWriter.close();
   } catch(Exception e) {
	   e.printStackTrace();
   } 
 }
 
 public static void main(String args[]) {
	  String fileName = ProjectEnum.PROJECT_DIR_MAIN.value() + ProjectEnum.PROJECT_TITLE_PLAGFILES.value()+"\\files\\sample1.txt";
	  
	  FileReadManager fileReadManager = new FileReadManager(fileName);
	  fileReadManager.fileStatistics();
	  fileReadManager.combinations();
  }
}
