package utils;
import impl.StudyMaterial;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import module.StudyMaterialModule;
public class FileUploadUtils {
	
	public static  boolean  checkIfFilePathExistInServer(String  pathToFileDirectory ){
		            		File file = new File( pathToFileDirectory);
					    	if (!file.exists()){
						      	 return false;
						    }else {
							        return true;
						   }
	}
	public static  void  createPathInServer(String  pathToFileDirectory ){
        		      File file = new File( pathToFileDirectory);
			          try{
					             boolean test = file.mkdirs();
					  }catch(SecurityException ex){
						ex.printStackTrace();
				      }
	}
	public static  void createUploadDirectoryInServer(String  pathToFileDirectory ){
		                   if(!checkIfFilePathExistInServer(pathToFileDirectory)){
		                	      createPathInServer(pathToFileDirectory);
		                   }
	}
	public static  int getIndexOfItemInList(List studyMaterilaList,String barcode){
         int index=-1;//-1 means the item is not in the  list
         for(int x=0;x<studyMaterilaList.size();x++){
                StudyMaterialModule studyMaterial=( StudyMaterialModule)studyMaterilaList.get(x);
                if( studyMaterial.getItembarcode().equals(barcode)){
             	   index=x;
             	   break;
                }
         }
         return index;
    }
	
	public static void copyFile(String inputFilePath,String outputFilePath,String filename) throws Exception{
		              String outputDirPath=outputFilePath.substring(0, outputFilePath.lastIndexOf("/")+1);
		              createUploadDirectoryInServer(outputDirPath );
		              
		              File destFile= new File(outputFilePath);
		              
		              if(!destFile.exists()) {
		            	  destFile.mkdirs();
		              }
		              
		              File destFilename = new File(destFile, filename);
		              
		              if (!destFilename.exists()) {
		          		  destFilename.createNewFile();
		              }
		               
		             FileInputStream buffIn = new FileInputStream(inputFilePath);
		             FileOutputStream  buffout = new FileOutputStream(destFilename);
		             boolean eof = false;
		             while(!eof){
			              int line = buffIn.read();
			              if (line == -1){
				               eof  = true;
			              } else {
				                   buffout.write(line);
			              }
		            }				
		            buffIn.close();
		            buffout.flush();
		            buffout.close();
	}
	
	
	public static  String getModuleCodeFromFilePath(String path)throws Exception{
        String[] pathTokenArr = path.split("/");
        return pathTokenArr[8];
}
	public static boolean isModuleInLog(String path,String uploadingModule) throws Exception{
        
	       String modulesInLogArr[]=path.split("/"); 
	      boolean moduleInLogs=false;
	      for(int x=0;x<modulesInLogArr.length;x++){
	    	  if(uploadingModule.equals(modulesInLogArr[x])){
	    		  moduleInLogs=true;
	    	  }
	      }
	    return   moduleInLogs;
  }
	
	
}
