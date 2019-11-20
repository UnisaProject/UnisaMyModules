package Staae05h.Abean;
 
import java.beans.*;
 
public class Staae05sAppAdmissionEvaluatorWsMatricRecordCategoryPropertyEditor extends PropertyEditorSupport {
 
   public String[] getTags() {
      String values[] = {
         "6",
         "5",
         "4",
         "3",
         "2",
         "7",
         "8",
         "9",
         "1",
         "0",
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
