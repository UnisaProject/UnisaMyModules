package za.ac.unisa.utils;

public class LecturerAccessLevel {
	public static final String LAL_NONE = "";
	public static final String LAL_ACCESS = "Student";
	public static final String LAL_MAINTAIN = "Instructor";
        public static final String LAL_ASSISTANCE = "Teaching Assistant";

	public static String getAccessLevel(String accessLevel) {
		accessLevel = accessLevel.toUpperCase();
		if (accessLevel.equalsIgnoreCase("HOD")) {
			return LAL_ACCESS;
		} else if (accessLevel.equalsIgnoreCase("PRIML")) {
			return LAL_MAINTAIN;
		} else if (accessLevel.equalsIgnoreCase("SECDL")) {
			return LAL_MAINTAIN;
		} else if (accessLevel.equalsIgnoreCase("TUTOR")) {
			return LAL_ASSISTANCE;
		} else if (accessLevel.equalsIgnoreCase("CADMN")) {
			return LAL_MAINTAIN;
		} else if (accessLevel.equalsIgnoreCase("OBSRV")) {
			return LAL_ACCESS;
		} else if (accessLevel.equalsIgnoreCase("AGENT")) {
			return LAL_ACCESS;
		} else {
			return LAL_NONE;
		}
	}
}
