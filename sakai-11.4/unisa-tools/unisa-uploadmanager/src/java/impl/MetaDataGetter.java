package impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import module.StudyMaterialModule;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.unisa.uploadermanager.jaxb.beans.ResourceDTO;
import com.unisa.uploadermanager.jaxb.beans.StudyMaterialResponse;

import utils.MetaDataUtils;
import utils.PdfDownloadUtil;
import utils.Utilities;
import za.ac.unisa.lms.tools.uploadmanager.dao.AuditDAO;
import za.ac.unisa.lms.tools.uploadmanager.dao.SCMwebserviceAccess;

public class MetaDataGetter {

	public ArrayList getStudyMaterialList(String modCode, String year,
			String period, String lang, String type) throws Exception {
		ArrayList materialList = new ArrayList();

		try {
			
			SCMwebserviceAccess scmWebserviceAccess = new SCMwebserviceAccess();
			Document doc = scmWebserviceAccess.getXMLDoc(modCode, year, period,
					lang, type);
			NodeList nList = doc.getElementsByTagName("Resource");
			String filefullPath = "";
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				
				StudyMaterialModule details = new StudyMaterialModule();
				details.setModule(modCode);
				setStudyMaterialData(details, nNode);
				if (type.equals("-1")) {
					addMaterialWithAppropriateLang(lang, materialList, details);

				} else {
					if (type.equals(details.getDocumenttype())) {
						addMaterialWithAppropriateLang(lang, materialList,
								details);
					}
				}

			}
		} catch (Exception e) {
			materialList = null;
			throw e;
		}
		return materialList;
	}

	private void setStudyMaterialData(StudyMaterialModule details, Node nNode) {
		setDataFromXMList(details, nNode);
		//setStudyMaterialFromDTO(details,resouceDTO);
		setAdditionalData(details);

	}
	
	private void setStudyMaterialFromDTO(StudyMaterialModule details, ResourceDTO resourceDTO) {
		
		String dept = resourceDTO.getDept();
		
		//details.setItembarcode(eElement.getAttribute("itembarcode"));
		details.setItembarcode(resourceDTO.getBarcode());
		//details.setItemshortdesc(eElement.getAttribute("itemshortdesc"));
		
		details.setItemshortdesc(resourceDTO.getShortDescription());
		
		//details.setItemfulldesc(eElement.getAttribute("itemfulldesc"));
		
		details.setItemfulldesc(resourceDTO.getFullDescription());
		
		// details.setModule(eElement.getAttribute("module"));
		//details.setAcademicyear(eElement.getAttribute("year"));
		
		details.setAcademicyear(resourceDTO.getYear());
		
		//details.setAcademicperiod(eElement.getAttribute("period"));
		
		details.setAcademicperiod(resourceDTO.getPeriod());
		
		//details.setUnitno(eElement.getAttribute("unitno"));
		
		details.setUnitno(resourceDTO.getUnitNumber());
		
		//details.setDocumenttype(eElement.getAttribute("documenttype"));
		
		details.setDocumenttype(resourceDTO.getDocumentType());
		
		//details.setDateavailable(eElement.getAttribute("dateavailable")
		//		.substring(0, 10));
		
		details.setDateavailable(resourceDTO.getDateAvailable().toString().substring(0, 10));
		
		
		//details.setFilesize(eElement.getAttribute("filesize"));
		details.setFilesize(resourceDTO.getFileSize());
		
		
	}

	private void setDataFromXMList(StudyMaterialModule details, Node nNode) {
		Element eElement = (Element) nNode;
		String dept = eElement.getAttribute("dept");
		details.setItembarcode(eElement.getAttribute("barcode"));
		details.setItemshortdesc(eElement.getAttribute("shortDescription"));
		details.setItemfulldesc(eElement.getAttribute("fullDescription"));
		 details.setModule(eElement.getAttribute("module"));
		details.setAcademicyear(eElement.getAttribute("year"));
		details.setAcademicperiod(eElement.getAttribute("period"));
		details.setUnitno(eElement.getAttribute("unitNumber"));
		details.setDocumenttype(eElement.getAttribute("documentType"));
		details.setDateavailable(eElement.getAttribute("dateAvailable")
				.substring(0, 10));
		details.setFilesize(eElement.getAttribute("filesize"));
	}

	private void setAdditionalData(StudyMaterialModule details) {
		
		String documentname = details.getItemshortdesc() + ".pdf";
		details.setDocumentname(documentname);
		details.setLanguage(MetaDataUtils.getlanguage(details.getItemshortdesc()));
		boolean fileExisting = isFileExisting(details);
		details.setFilestatus(isFileExisting(details));
		boolean docAvail = docAvailability(details.getDateavailable());
		details.setDocAvailability(docAvail);
		
		if (fileExisting) {
			AuditTrail auditTrail = new AuditTrail();
			String lastdate = getLastUpdateDate(details);
		//	details.setDateavailable(lastdate);
			String updater = auditTrail.getUpdater(details.getItembarcode());
			details.setLecturer(updater);
		}
		
	}

	private String getLastUpdateDate(StudyMaterialModule details) {
		AuditDAO db = new AuditDAO();
		String lastdate = db.getUpdatedDate(details.getItembarcode());
		return lastdate;
	}

	private boolean isFileExisting(StudyMaterialModule details) {
		
		String filefullPath = "";
		try {
			String itembarcode = details.getItembarcode();
			String module = details.getModule();
			//String documentType = details.getDocumenttype();
			
			String documentType = Utilities.getStudyMaterialTypeDirectoryName(details.getDocumenttype());
			
			if (documentType != null) {
				documentType = documentType.toLowerCase();
			}
			
			String fileName = MetaDataUtils.getfileName(details.getItemshortdesc());
			filefullPath = PdfDownloadUtil.getFilepath(itembarcode, module,documentType, fileName);
			File file = new File(filefullPath);
			boolean fileExist = file.exists();
			return fileExist;
		} catch (Exception ex) {
			return false;
		}
	}

	private boolean docAvailability(String dateavailable) {
		boolean docAvail = false;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date1 = formatter.parse(dateavailable);
			Calendar cal = Calendar.getInstance();
			String today = formatter.format(cal.getTime());
			Date date2 = formatter.parse(today);
			if (date1.compareTo(date2) > 0) {
				docAvail = false;
			} else if (date1.compareTo(date2) < 0) {
				docAvail = true;
			} else {
				docAvail = true;
			}
		} catch (Exception e) {

		}

		return docAvail;
	}

	private void addMaterialWithAppropriateLang(String lang,
			ArrayList materialList, StudyMaterialModule details) {
		if (lang.equalsIgnoreCase("B")) {
			materialList.add(details);
		} else if (lang.equalsIgnoreCase("A")
				&& (details.getLanguage().equalsIgnoreCase("A") || details
						.getLanguage().equalsIgnoreCase("B"))) {
			materialList.add(details);
		} else if (lang.equalsIgnoreCase("E")
				&& (details.getLanguage().equalsIgnoreCase("E") || details
						.getLanguage().equalsIgnoreCase("B"))) {
			materialList.add(details);

		}

	}

}