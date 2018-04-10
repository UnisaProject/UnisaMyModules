package za.ac.unisa.lms.tools.booklistadmin.module;

public class EreserveBookModule  extends Course_Book{
	
	 private String ebookVolume;
	 private String ebook_pages;
	 private String url;
	 private String eReserveType;
	 
	 	public String getEbookVolume() {
			return ebookVolume;
		}
		public void setEbookVolume(String ebookVolume) {
			this.ebookVolume = ebookVolume;
		}
		public String getEbook_pages() {
			return ebook_pages;
		}
		public void setEbook_pages(String ebook_pages) {
			this.ebook_pages = ebook_pages;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		 public String geteReserveType() {
				return eReserveType;
		 }
		 public void seteReserveType(String eReserveType) {
				this.eReserveType = eReserveType;
		}
		

}
