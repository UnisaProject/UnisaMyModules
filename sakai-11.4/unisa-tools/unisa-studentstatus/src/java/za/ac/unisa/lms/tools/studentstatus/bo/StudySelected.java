package za.ac.unisa.lms.tools.studentstatus.bo;

import java.io.Serializable;

public class StudySelected implements Serializable {

	private static final long serialVersionUID = 9083651122738140217L;
	private String category;
	private String category1;
	private String category2;
	
	private String qual;
	private String qual1;
	private String qual2;
	
	private String spec;
	private String spec1;
	private String spec2;
	
	private String docQual;
	private String docQual1;
	private String docQual2;
	private String docSpec;
	private String docSpec1;
	private String docSpec2;
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getQual() {
		return qual;
	}
	public void setQual(String qual) {
		this.qual = qual;
	}
	public String getDocQual() {
		return docQual;
	}
	public void setDocQual(String docQual) {
		this.docQual = docQual;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public String getDocSpec() {
		return docSpec;
	}
	public void setDocSpec(String docSpec) {
		this.docSpec = docSpec;
	}
	 
	public String getCategory1() {
		return category1;
	}
	public void setCategory1(String category1) {
		this.category1 = category1;
	}
	public String getCategory2() {
		return category2;
	}
	public void setCategory2(String category2) {
		this.category2 = category2;
	}
	
	public String getQual1() {
		return qual1;
	}
	public void setQual1(String qual1) {
		this.qual1 = qual1;
	}
	public String getQual2() {
		return qual2;
	}
	public void setQual2(String qual2) {
		this.qual2 = qual2;
	}
	
	public String getSpec1() {
		return spec1;
	}
	public void setSpec1(String spec1) {
		this.spec1 = spec1;
	}
	public String getSpec2() {
		return spec2;
	}
	public void setSpec2(String spec2) {
		this.spec2 = spec2;
	}
	
	public String getDocQual1() {
		return docQual1;
	}
	public void setDocQual1(String docQual1) {
		this.docQual1 = docQual1;
	}
	public String getDocQual2() {
		return docQual2;
	}
	public void setDocQual2(String docQual2) {
		this.docQual2 = docQual2;
	}
	
	public String getDocSpec1() {
		return docSpec1;
	}
	public void setDocSpec1(String docSpec1) {
		this.docSpec1 = docSpec1;
	}
	public String getDocSpec2() {
		return docSpec2;
	}
	public void setDocSpec2(String docSpec2) {
		this.docSpec2 = docSpec2;
	}
}
