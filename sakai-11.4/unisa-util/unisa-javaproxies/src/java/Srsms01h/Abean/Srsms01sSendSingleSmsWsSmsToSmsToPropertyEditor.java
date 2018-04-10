package Srsms01h.Abean;
 
import java.beans.*;
 
public class Srsms01sSendSingleSmsWsSmsToSmsToPropertyEditor extends PropertyEditorSupport {
 
   public String[] getTags() {
      String values[] = {
         "S",
         "P",
         "",
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
