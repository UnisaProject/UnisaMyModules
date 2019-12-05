package Sfrrf03h.Abean;
 
import java.beans.*;
 
public class Sfrrf03sMntOnlineCcPaymentsWsStudentAnnualRecordBursaryTypePropertyEditor extends PropertyEditorSupport {
 
   public String[] getTags() {
      String values[] = {
         "01",
         "04",
         "03",
         "02",
         "07",
         "05",
         "11",
         "10",
         "09",
         "08",
         "06",
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
