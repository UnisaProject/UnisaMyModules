package utils;

import org.sakaiproject.component.cover.ServerConfigurationService;
import impl.AuditTrail;
import impl.Pdf;
import impl.PdfUploader;


public class PdfDownloadUtil {

	public static String pathToMaterialOnServer = ServerConfigurationService
			.getString("materialPath");

	public static String getFilepath(String itembarcode, String module,
			String documentType, String fileName) throws Exception {
		String filefullPath = "";
/*	AuditTrail auditTrail = new AuditTrail();
		String filefullPath = "";
		
		
		
		if (auditTrail.isRecordExist(itembarcode)
				&& auditTrail.getPath(itembarcode).indexOf("collect") == -1) {
			filefullPath = pathToMaterialOnServer + module + "/" + documentType
					+ "/" + fileName;
		} else {
			filefullPath = pathToMaterialOnServer + "collect/" + module + "/"
					+ documentType + "/" + fileName;
		}*/
		Pdf pdf = new Pdf();
	
		boolean collectionExistFlag = PdfUploader.isCollectionStudyMaterial(documentType,module,fileName);
		if (collectionExistFlag) {
			filefullPath = pdf.getUploadDirPathForCollection(documentType.toLowerCase(),
					module);
			filefullPath = filefullPath+fileName;
		} else {
			filefullPath = pdf.getUploadDirPathForSingle(documentType.toLowerCase(),module);
			filefullPath = filefullPath+fileName;
		}
		
		
		
		return filefullPath;
	}
}
