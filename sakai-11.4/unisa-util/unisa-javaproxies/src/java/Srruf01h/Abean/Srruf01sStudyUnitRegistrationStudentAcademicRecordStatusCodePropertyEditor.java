package Srruf01h.Abean;
 
import java.beans.*;
 
public class Srruf01sStudyUnitRegistrationStudentAcademicRecordStatusCodePropertyEditor extends PropertyEditorSupport {
 
   public String[] getTags() {
      String values[] = {
         "IN",
         "CA",
         "CO",
         "AC",
         "FI",
         "PC",
         "  ",
      };
      return values;
   }

   public boolean checkTag(String s) {
      String values[] = getTags();
      StringBuffer temp = new StringBuffer(s);
      while (temp.length() < 2)
         temp.append(' ');
      s = temp.toString();
      for(int n=0; n< values.length; n++) {
         if (s.compareTo(values[n]) == 0) {
            return true;
         }
      }
      return false;
   }
}
