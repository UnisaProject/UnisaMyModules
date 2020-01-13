package za.ac.unisa.lms.tools.tpstudymaterial.util;

public class MetaDataUtils {

	public static String getfileName(String itemShortDesdc) {

		String s[] = itemShortDesdc.split("_");
		String modCode = s[0];
		String yr = s[1];
		String type = s[2];
		String nr = s[3];
		String period = s[4];
		String lang = s[5];
		String filename = nr + "_" + yr + "_" + period + "_"
				+ lang.toLowerCase() + ".pdf";

		return filename;
	}

	public static String getlanguage(String itemShortDesdc) {

		int lastIndexOff_ = itemShortDesdc.lastIndexOf("_") + 1;
		return itemShortDesdc.substring(lastIndexOff_).trim().toUpperCase();
	}

	public static String getModule(String itemShortDesdc) {
		
		String s[] = itemShortDesdc.split("_");
		return s[0];
	}

	public static String getType(String itemShortDesdc) {
		
		String s[] = itemShortDesdc.split("_");
		if (ServerConfig.isTestEnvironment()) {
			return s[2].toUpperCase();
		} else {
			return s[2];
		}
	}

}
