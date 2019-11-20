package Srasa01h.Abean;
 
import java.beans.*;
 
public class Srasa01sLstAcademicRecordSunAcademicRecordStudyUnitExemptionCodePropertyEditor extends PropertyEditorSupport {
 
   public String[] getTags() {
      String values[] = {
         "1",
         "2",
         "3",
         "4",
         "5",
         "6",
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
