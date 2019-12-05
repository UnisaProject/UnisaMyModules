package Staae01h.Abean;
 
import java.beans.*;
 
public class Staae01sAppEnquireAdmissionWsMatricRecordCategoryPropertyEditor extends PropertyEditorSupport {
 
   public String[] getTags() {
      String values[] = {
         "6",
         "3",
         "0",
         "7",
         "5",
         "9",
         "1",
         "2",
         "4",
         "8",
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
