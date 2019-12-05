package Sfrrf03h.Abean;
 
import java.beans.*;
 
public class Sfrrf03sMntOnlineCcPaymentsBundleTypePropertyEditor extends PropertyEditorSupport {
 
   public String[] getTags() {
      String values[] = {
         "16",
         "13",
         "10",
         "9",
         "8",
         "7",
         "5",
         "4",
         "3",
         "2",
         "17",
         "1",
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
