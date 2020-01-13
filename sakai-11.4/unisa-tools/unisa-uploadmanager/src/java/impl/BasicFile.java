package impl;

public  class BasicFile {
	String filename;
    String filepath;
    String type;
    private String filesize;
    private boolean filestatus;
    public String getFilename(){
   	 return filename;
    }
    public void setFilename(String filename){
   	           this.filename=filename;
    }
    public String getFilepath(){
   	 return filepath;
    }
    public void setFilepath(String filepath){
   	 this.filepath=filepath;
    }
    public void setType(String type){
        this.type=type;
    }
    public String getType(){
              return  type;
    }
    public String getFilesize() {
    	return filesize;
    }
    public void setFilesize(String filesize) {
    	this.filesize = filesize;
    }
    public boolean isFilestatus() {
    	return filestatus;
    }
    public void setFilestatus(boolean filestatus) {
    	this.filestatus = filestatus;
    }
    

}
