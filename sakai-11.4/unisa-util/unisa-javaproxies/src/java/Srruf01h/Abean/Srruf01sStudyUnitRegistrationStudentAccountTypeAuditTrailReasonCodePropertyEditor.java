package Srruf01h.Abean;
 
import java.beans.*;
 
public class Srruf01sStudyUnitRegistrationStudentAccountTypeAuditTrailReasonCodePropertyEditor extends PropertyEditorSupport {
 
   public String[] getTags() {
      String values[] = {
         "02",
         "03",
         "04",
         "05",
         "06",
         "01",
         "07",
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
