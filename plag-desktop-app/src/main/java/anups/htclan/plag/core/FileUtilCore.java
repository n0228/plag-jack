package anups.htclan.plag.core;

public class FileUtilCore {
	
 private String filePath;
 private String extension;
 private String fileName;
 
 public FileUtilCore(String fileWithPath) {
   StringBuilder fileName = new StringBuilder();
   StringBuilder extension = new StringBuilder();
   boolean extensionRecognizer = false;
   boolean fileNameRecognizer = false;
   for(int index=fileWithPath.length()-1;index>0;index--) {
	 if(!extensionRecognizer && !fileNameRecognizer) {
		if(fileWithPath.charAt(index)=='.') { extensionRecognizer=true; }
		else { extension.append(fileWithPath.charAt(index)); }
	 } else if(extensionRecognizer && !fileNameRecognizer){
		 if(fileWithPath.charAt(index)=='\\') { fileNameRecognizer=true; }
			else { fileName.append(fileWithPath.charAt(index)); }
	 }
   }
   this.extension = extension.reverse().toString();
   this.fileName = fileName.reverse().toString();
   this.filePath = fileWithPath.replace(this.fileName, "").replace(".", "").replace(this.extension, "");
 }
 
 public String getFilePath() {
	return this.filePath;
 }
 
 public String getFileExtension() {
	return this.extension;
 }
 
 public String getFileName() {
	return this.fileName;
 }
 
}
