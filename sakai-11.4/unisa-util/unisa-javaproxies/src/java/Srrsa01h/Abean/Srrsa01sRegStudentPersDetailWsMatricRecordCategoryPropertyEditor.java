package Srrsa01h.Abean;
 
import java.beans.*;
 
public class Srrsa01sRegStudentPersDetailWsMatricRecordCategoryPropertyEditor extends PropertyEditorSupport {
 
   public String[] getTags() {
      String values[] = {
         "6",
         "4",
         "5",
         "7",
         "8",
         "3",
         "9",
         "1",
         "0",
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
