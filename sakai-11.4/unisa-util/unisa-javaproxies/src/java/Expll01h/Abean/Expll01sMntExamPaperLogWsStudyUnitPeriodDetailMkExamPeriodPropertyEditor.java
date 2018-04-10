package Expll01h.Abean;
 
import java.beans.*;
 
public class Expll01sMntExamPaperLogWsStudyUnitPeriodDetailMkExamPeriodPropertyEditor extends PropertyEditorSupport {
 
   public String[] getTags() {
      String values[] = {
         "5",
         "0",
         "8",
         "10",
         "13",
         "1",
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
