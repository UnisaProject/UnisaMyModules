package Grgdg01h.Abean;
 
import java.beans.*;
 
public class Grgdg01sMntStudentGraduationStudentGraduationDetailGownSizePropertyEditor extends PropertyEditorSupport {
 
   public String[] getTags() {
      String values[] = {
         "0",
         "52",
         "54",
         "56",
         "50",
         "48",
         "46",
         "42",
         "44",
         "0",
      };
      return values;
   }

   public boolean checkTag(String s) {
      String values[] = getTags();
      for(int n=0; n< values.length; n++) {
         if (s.compareTo(values[n]) == 0) {
            return true;
         }
      }
      return false;
   }
}
