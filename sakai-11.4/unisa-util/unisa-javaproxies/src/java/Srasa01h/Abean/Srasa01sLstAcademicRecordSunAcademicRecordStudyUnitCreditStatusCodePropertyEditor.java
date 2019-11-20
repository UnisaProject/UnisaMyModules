package Srasa01h.Abean;
 
import java.beans.*;
 
public class Srasa01sLstAcademicRecordSunAcademicRecordStudyUnitCreditStatusCodePropertyEditor extends PropertyEditorSupport {
 
   public String[] getTags() {
      String values[] = {
         "5",
         "6",
         "7",
         "4",
         "3",
         "8",
         "1",
         "2",
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
