package Sruaf01h.Abean;
 
import java.beans.*;
 
public class Sruaf01sStudyUnitAdditionStudentAnnualRecordBursaryTypePropertyEditor extends PropertyEditorSupport {
 
   public String[] getTags() {
      String values[] = {
         "1",
         "5",
         "6",
         "7",
         "8",
         "9",
         "10",
         "11",
         "3",
         "2",
         "4",
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
