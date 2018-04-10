package Srsrj11h.Abean;
 
import java.applet.*;
import java.awt.event.*;
import java.io.*;
import java.beans.*;
import java.util.*;
import java.net.URL;
import java.math.*;
import java.text.*;
import com.ca.gen80.jprt.*;
import com.ca.gen80.odc.coopflow.*;
import com.ca.gen80.odc.msgobj.*;
import com.ca.gen80.csu.trace.*;
import com.ca.gen80.vwrt.types.*;
 
/**
 * <strong>API bean documentation.</strong><p>
 *
 * This html file contains the public methods available in this bean.<br>
 * NOTE:  the links at the top of this page do not work, as they are not connected to anything. 
 * To get the images in the file to work, copy the images directory 
 * from 'jdk1.1.x/docs/api/images' to the directory where this file is located.
 */
public class Srsrj11sPrtNormalDeclaration  implements ActionListener, java.io.Serializable  {
 
   //  Use final String for the bean name
   public static final String BEANNAME = new String("Srsrj11sPrtNormalDeclaration");
 
   //  Constants for Asynchronous status
   public static final int PENDING = CoopFlow.DATA_NOT_READY;
   public static final int AVAILABLE = CoopFlow.DATA_READY;
   public static final int INVALID_ID = CoopFlow.INVALID_ID;
 
   private final SimpleDateFormat nativeDateFormatter = new SimpleDateFormat("yyyyMMdd");
   public Srsrj11sPrtNormalDeclaration() {
      super();
      nativeDateFormatter.setLenient(false);
      Trace.initialize(null);
   }
   /**
    * Sets the traceflag to tell the bean to output diagnostic messages on stdout.
    *
    * @param traceFlag 1 to turn tracing on, 0 to turn tracing off.
    */
   public void setTracing(int traceFlag) {
      if (traceFlag == 0)
         Trace.setMask(Trace.MASK_NONE);
      else
         Trace.setMask(Trace.MASK_ALL);
   }
   /**
    * Gets the current state of tracing.
    *
    * @return traceFlag value
    */
   public int  getTracing() {
      if (Trace.getMask() == Trace.MASK_NONE)
         return 0;
      else
         return 1;
   }
   protected void traceOut(String x)  {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, x);
      }
   }
 
 
   private Srsrj11sPrtNormalDeclarationOperation oper = null;
 
   /**
    * Calls the transaction/procedure step on the server.
    *
    * @exception java.beans.PropertyVetoException
    * Final property checks can throw this exception.
    */
   public void execute()  throws PropertyVetoException {
      try  {
         if (oper == null) {
            oper = new Srsrj11sPrtNormalDeclarationOperation(this);
            addCompletionListener(new operListener(this));
            addExceptionListener(new operListener(this));
         }
 

 
         oper.doSrsrj11sPrtNormalDeclarationOperation();
         notifyCompletionListeners();
      }
      catch (PropertyVetoException ePve)  {
         PropertyChangeEvent pce = ePve.getPropertyChangeEvent();
         String s = pce.getPropertyName();
         System.out.println("\nPropertyVetoException on " + s + ": " + ePve.toString());
         throw ePve;
      }
      catch (ProxyException e)  {
         notifyExceptionListeners(e.toString());
         return;
      }
   }
 
 
   private class operListener implements ActionListener, java.io.Serializable  {
      private Srsrj11sPrtNormalDeclaration rP;
      operListener(Srsrj11sPrtNormalDeclaration r)  {
         rP = r;
      }
      public void actionPerformed(ActionEvent aEvent)  {
         if (Trace.isTracing(Trace.MASK_APPLICATION))
         {
            Trace.record(Trace.MASK_APPLICATION, "Srsrj11sPrtNormalDeclaration", "Listener heard that Srsrj11sPrtNormalDeclarationOperation completed with " + 
               aEvent.getActionCommand());
         }
         String excp = "Exception";
         if (excp.equalsIgnoreCase(aEvent.getActionCommand().substring(0,9)))
            System.out.println("\nException on " + aEvent.getActionCommand().substring(10));
      }
   }
 
   private Vector completionListeners = new Vector();
   /**
    * Adds an object to the list of listeners that are called when execute has completed.
    *
    * @param l a class object that implements the ActionListener interface.  See the test UI 
    * for an example.
    */
   public synchronized void addCompletionListener(ActionListener l)  {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "addCompletionListener registered");
      }
      completionListeners.addElement(l);     //add listeners
   }
   /**
    * Removes the object from the list of listeners that are called after completion of execute.
    *
    * @param l the class object that was registered as a CompletionListener.  See the test UI 
    * for an example.
    */
   public synchronized void removeCompletionListener(ActionListener l)  {
      completionListeners.removeElement(l);  //remove listeners
   }
   private void notifyCompletionListeners()  {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of completion of operation Srsrj11sPrtNormalDeclaration ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      synchronized (this)  {
         targets = (Vector) completionListeners.clone();
      }
      actionEvt = new ActionEvent(this, 0, "Completion.Srsrj11sPrtNormalDeclaration");
      for (int i = 0; targets.size() > i; i++)  {
         ActionListener target = (ActionListener)targets.elementAt(i);
         target.actionPerformed(actionEvt);
      }
   }
 
   private Vector exceptionListeners = new Vector();
   /**
    * Adds an object to the list of listeners that are called when an exception occurs.
    *
    * @param l a class object that implements the ActionListener interface.  See the test UI 
    * for an example.
    */
   public synchronized void addExceptionListener(ActionListener l)  {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "addExceptionListener registered");
      }
      exceptionListeners.addElement(l);     //add listeners
   }
   /**
    * Removes the object from the list of listeners that are called when an exception occurs.
    *
    * @param l the class object that was registered as an ExceptionListener.  See the test UI 
    * for an example.
    */
   public synchronized void removeExceptionListener(ActionListener l)  {
      exceptionListeners.removeElement(l);  //remove listeners
   }
   private void notifyExceptionListeners(String s)  {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of exception of operation Srsrj11sPrtNormalDeclaration ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      String failCommand = "Exception.Srsrj11sPrtNormalDeclaration";
      synchronized (this)  {
         targets = (Vector) exceptionListeners.clone();
      }
      if (s.length() > 0)
          failCommand = failCommand.concat(s);
      actionEvt = new ActionEvent(this, 0, failCommand);
      for (int i = 0; targets.size() > i; i++)  {
         ActionListener target = (ActionListener)targets.elementAt(i);
         target.actionPerformed(actionEvt);
      }
   }
 
   /**
    * Called by the sender of Listener events.
    */
   public void actionPerformed(ActionEvent e) {
      String command = e.getActionCommand();
 
      if (command.equals("execute"))  {
         try {
            execute();
         } catch (PropertyVetoException pve) {}
      } else {
         if (Trace.isTracing(Trace.MASK_APPLICATION))
         {
            Trace.record(Trace.MASK_APPLICATION, BEANNAME, "ActionEvent " + e.toString());
         }
      }
   }
 
   //these are the standard properties that are passed into the Server Dialog 
   //all of these are checked when loaded by the operation into the srvdialog class
 
   private String commandSent = "";
   /**
    * Sets the command sent property to be sent to the server where
    * the procedure step's executable code is installed. This property should only be
    * set if the procedure step uses case of command construct.
    *
    * @param s a string representing the command name
    */
   public void setCommandSent(String s) {
      if (s == null) commandSent = "";
      else commandSent = s;
   }
   /**
    * Gets the command sent property to be sent to the server where
    * the procedure step's executable code is installed.
    *
    * @return a string representing the command name
    */
   public String getCommandSent() {
      return commandSent;
   }
 
   private String clientId = "";
   /**
    * Sets the client user id property which will be sent to
    * the server where the procedure step's executable code is installed. A client
    * user id is usually accompanied by a client user password, which can be set
    * with the clientPassword property.  Security is not enabled until the security
    * user exit is modified to enable it.
    *
    * @param s a string representing the client user id
    */
   public void setClientId(String s) {
      if (s == null) clientId = "";
      else clientId = s;
   }
   /**
    * Gets the client user id property which will be sent to
    * the server where the procedure step's executable code is installed. A client
    * user id is usually accompanied by a client user password, which can also be set
    * with the clientPassword property. Security is not enabled until the security
    * user exit is modified to enable it.
    *
    * @return a string representing the client user id
    */
   public String getClientId() {
      return clientId;
   }
 
   private String clientPassword = "";
   /**
    * Sets the client password property which will be sent to
    * the server where the procedure step's executable code is installed. A client
    * password usually accompanies a client user id, which can be set
    * with the clientId property. Security is not enabled until the security
    * user exit is modified to enable it.
    *
    * @param s a string representing the client password
    */
   public void setClientPassword(String s) {
      if (s == null) clientPassword = "";
      else  clientPassword = s;
   }
   /**
    * Gets the client password property which will be sent to
    * the server where the procedure step's executable code is installed. A client
    * password usually accompanies a client user id, which can be set
    * with the clientId property. Security is not enabled until the security
    * user exit is modified to enable it.
    *
    * @return a string representing the client password
    */
   public String getClientPassword() {
      return clientPassword;
   }
 
   private String nextLocation = "";
   /**
    * Sets the location name (NEXTLOC) property that may be
    * used by ODC user exit flow dlls.
    *
    * @param s a string representing the NEXTLOC value
    */
   public void setNextLocation(String s) {
      if (s == null) nextLocation = "";
      else nextLocation = s;
   }
   /**
    * Gets the location name (NEXTLOC) property that may be
    * used by ODC user exit flow dlls.
    *
    * @return a string representing the NEXTLOC value
    */
   public String getNextLocation() {
      return nextLocation;
   }
 
   private int exitStateSent = 0;
   /**
    * Sets the exit state property which will be sent to server procedure step.
    *
    * @param n an integer representing the exit state value
    */
   public void setExitStateSent(int n) {
      exitStateSent = n;
   }
   /**
    * Gets the exit state property which will be sent to server procedure step.
    *
    * @return an integer representing the exit state value
    */
   public int getExitStateSent() {
      return exitStateSent;
   }
 
   private String dialect = "DEFAULT";
   /**
    * Sets the dialect property.  It has the default value of "DEFAULT".
    *
    * @param s a string representing the dialect value
    */
   public void setDialect(String s) {
      if (s == null) dialect = "DEFAULT";
      else dialect = s;
   }
   /**
    * Gets the dialect property.  It has the default value of "DEFAULT".
    *
    * @return a string representing the dialect value
    */
   public String getDialect() {
      return dialect;
   }
 
   private String commandReturned;
   protected void setCommandReturned(String s) {
      commandReturned = s;
   }
   /**
    * Retrieves the command returned property, if any,
    * after the server procedure step has been executed.
    *
    * @return a string representing the command returned value
    */
   public String getCommandReturned() {
      return commandReturned;
   }
 
   private int exitStateReturned;
   protected void setExitStateReturned(int n) {
      exitStateReturned = n;
   }
   /**
    * Retrieves the exit state returned property, if any,
    * after the server procedure step has been executed.
    *
    * @return a string representing the exit state returned value
    */
   public int getExitStateReturned() {
      return exitStateReturned;
   }
 
   private int exitStateType;
   protected void setExitStateType(int n) {
      exitStateType = n;
   }
   /**
    * Gets the exit state type based upon the server procedure step exit state. 
    *
    * @return a string representing the exit state type value
    */
   public int getExitStateType() {
      return exitStateType;
   }
 
   private String exitStateMsg = "";
   protected void setExitStateMsg(String s) {
      exitStateMsg = s;
   }
   /**
    * Gets the current status text message, if
    * one exists. A status message is associated with a status code, and can
    * be returned by a Gen exit state.
    *
    * @return a string representing the exit state message value
    */
   public String getExitStateMsg() {
      return exitStateMsg;
   }
 
   private String comCfg = "";
   /**
    * Sets the value to be used for communications instead of the information in commcfg.properties.
    * For details on this information, refer to the commcfg.properties file provided.
    *
    * @param s a string containing the communications command value
    */
   public void setComCfg(String s) {
      if (s == null) comCfg = "";
      else  comCfg = s;
   }
   /**
    * Gets the value to be used for communications instead of the information in commcfg.properties.
    * For details on this information, refer to the commcfg.properties file provided.
    *
    * @return a string containing the communications command value
    */
   public String getComCfg() {
      return comCfg;
   }
 
   private URL serverLocation;
   /**
    * Sets the URL used to locate the servlet.  Set this property by calling
    * myObject.setServerLocation( getDocumentBase()) from your applet
    * or, force a server connection by using<br>
    * <code>try {new URL("http://localhost:80");} catch(MalformedURLException e) {}</code>
    * 
    * @param s a URL containing the base URL for the servlet
    */
   public void setServerLocation(URL newServerLoc) {
      serverLocation = newServerLoc;
   }
   /**
    * Gets the URL used to locate the servlet.
    * 
    * @return a URL containing the base URL for the servlet
    */
   public URL getServerLocation() {
      return serverLocation;
   }
 
   private String servletPath = "";
   /**
    * Sets the URL path to be used to locate the servlet.  Set this property by calling
    * myObject.setServletPath( ... ) from your applet.
    * If the servletPath is left blank, then a default path
    * of "servlet" will be used.
    * 
    * @param s a String containing the URL path for the servlet
    */
   public void setServletPath(String newServletPath) {
      if (newServletPath == null) servletPath = "";
      else servletPath = newServletPath;
   }
   /**
    * Gets the URL path that will be used to locate the servlet.
    * 
    * @return a String containing the URL path for the servlet
    */
   public String getServletPath() {
      return servletPath;
   }
 
   private String fileEncoding = "";
   /**
    * Sets the file encoding to be used for converting to/from UNICODE.
    *
    * @param s a string containing the file encoding value
    */
   public void setFileEncoding(String s) {
      if (s == null) fileEncoding = "";
      else  fileEncoding = s;
   }
   /**
    * Gets the file encoding that will be used for converting to/from UNICODE.
    *
    * @return a string the file encoding value
    */
   public String getFileEncoding() {
      return fileEncoding;
   }
 
   //  Property interfaces
   //     (names include full predicate viewname)
   //  get...  for each output predicate view
   //  set...  for each input predicate view
   //  both for input-output views
   //  export (set and fire pcs) for input-output and output views
 
   // support notifying bound properties when a attribute value changes
   // see pcs changes below
   protected transient PropertyChangeSupport pcs = new PropertyChangeSupport(this);
   /**
    * Adds an object to the list of listeners that are called when a property value changes.
    *
    * @param l a class object that implements the PropertyChangeListener interface.  See the test UI 
    * for an example.
    */
   public void addPropertyChangeListener (PropertyChangeListener l)
                           { pcs.addPropertyChangeListener (l);    }
   /**
    * Removes the object from the list of listeners that are called when a property value changes.
    *
    * @param l the class object that was registered as a PropertyChangeListener.  See the test UI 
    * for an example.
    */
   public void removePropertyChangeListener (PropertyChangeListener l)
                           { pcs.removePropertyChangeListener (l);    }
 
   /**
    * This method clears all import and export attribute properties. The
    * default value is used if one is specified in the model otherwise 0 is used
    * for numeric, date and time attributes, an empty string is used for string attributes
    * and "00000000000000000000" is used for timestamp attributes. For attributes in repeating
    * groups, the clear method sets the repeat count to 0. In addition to clearing
    * attribute properties, the <code>clear</code> method also clears the system properties
    * commandReturned, exitStateReturned, and exitStateMsg.
    *
    * @exception java.beans.PropertyVetoException
    * This is needed to cover the setXXXX calls used in the function.
    */
   public void clear()  throws PropertyVetoException  {
      setCommandReturned("");
      setExitStateReturned(0);
      setExitStateMsg("");
 
      importView.reset();
      exportView.reset();
      importView.InWsServiceFeesReasonCode = FixedStringAttr.valueOf("Y", 1);
   }

   Srsrj11h.SRSRJ11S_IA importView = Srsrj11h.SRSRJ11S_IA.getInstance();
   Srsrj11h.SRSRJ11S_OA exportView = Srsrj11h.SRSRJ11S_OA.getInstance();
   public String getInPrintAcademicSupplementIefSuppliedFlag() {
      return FixedStringAttr.valueOf(importView.InPrintAcademicSupplementIefSuppliedFlag, 1);
   }
   public void setInPrintAcademicSupplementIefSuppliedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srsrj11sPrtNormalDeclarationIefSuppliedFlagPropertyEditor pe = new Srsrj11sPrtNormalDeclarationIefSuppliedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InPrintAcademicSupplementIefSuppliedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InPrintAcademicSupplementIefSuppliedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InPrintAcademicSupplementIefSuppliedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InPrintAcademicSupplementIefSuppliedFlag", null, null));
      }
      importView.InPrintAcademicSupplementIefSuppliedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInNqfLevelIefSuppliedFlag() {
      return FixedStringAttr.valueOf(importView.InNqfLevelIefSuppliedFlag, 1);
   }
   public void setInNqfLevelIefSuppliedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srsrj11sPrtNormalDeclarationIefSuppliedFlagPropertyEditor pe = new Srsrj11sPrtNormalDeclarationIefSuppliedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InNqfLevelIefSuppliedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InNqfLevelIefSuppliedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InNqfLevelIefSuppliedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InNqfLevelIefSuppliedFlag", null, null));
      }
      importView.InNqfLevelIefSuppliedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInNqfCreditsIefSuppliedFlag() {
      return FixedStringAttr.valueOf(importView.InNqfCreditsIefSuppliedFlag, 1);
   }
   public void setInNqfCreditsIefSuppliedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srsrj11sPrtNormalDeclarationIefSuppliedFlagPropertyEditor pe = new Srsrj11sPrtNormalDeclarationIefSuppliedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InNqfCreditsIefSuppliedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InNqfCreditsIefSuppliedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InNqfCreditsIefSuppliedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InNqfCreditsIefSuppliedFlag", null, null));
      }
      importView.InNqfCreditsIefSuppliedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInFunctionTypeCsfStringsString6() {
      return FixedStringAttr.valueOf(importView.InFunctionTypeCsfStringsString6, 6);
   }
   public void setInFunctionTypeCsfStringsString6(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 6) {
         throw new PropertyVetoException("InFunctionTypeCsfStringsString6 must be <= 6 characters.",
               new PropertyChangeEvent (this, "InFunctionTypeCsfStringsString6", null, null));
      }
      importView.InFunctionTypeCsfStringsString6 = FixedStringAttr.valueOf(s, (short)6);
   }
 
   public String getInWsQualificationCode() {
      return FixedStringAttr.valueOf(importView.InWsQualificationCode, 5);
   }
   public void setInWsQualificationCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InWsQualificationCode must be <= 5 characters.",
               new PropertyChangeEvent (this, "InWsQualificationCode", null, null));
      }
      importView.InWsQualificationCode = FixedStringAttr.valueOf(s, (short)5);
   }
 
   public String getIn1DistinctWsMajorSubjectCode() {
      return FixedStringAttr.valueOf(importView.In1DistinctWsMajorSubjectCode, 3);
   }
   public void setIn1DistinctWsMajorSubjectCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 3) {
         throw new PropertyVetoException("In1DistinctWsMajorSubjectCode must be <= 3 characters.",
               new PropertyChangeEvent (this, "In1DistinctWsMajorSubjectCode", null, null));
      }
      importView.In1DistinctWsMajorSubjectCode = FixedStringAttr.valueOf(s, (short)3);
   }
 
   public String getIn2DistinctWsMajorSubjectCode() {
      return FixedStringAttr.valueOf(importView.In2DistinctWsMajorSubjectCode, 3);
   }
   public void setIn2DistinctWsMajorSubjectCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 3) {
         throw new PropertyVetoException("In2DistinctWsMajorSubjectCode must be <= 3 characters.",
               new PropertyChangeEvent (this, "In2DistinctWsMajorSubjectCode", null, null));
      }
      importView.In2DistinctWsMajorSubjectCode = FixedStringAttr.valueOf(s, (short)3);
   }
 
   public String getIn3DistinctWsMajorSubjectCode() {
      return FixedStringAttr.valueOf(importView.In3DistinctWsMajorSubjectCode, 3);
   }
   public void setIn3DistinctWsMajorSubjectCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 3) {
         throw new PropertyVetoException("In3DistinctWsMajorSubjectCode must be <= 3 characters.",
               new PropertyChangeEvent (this, "In3DistinctWsMajorSubjectCode", null, null));
      }
      importView.In3DistinctWsMajorSubjectCode = FixedStringAttr.valueOf(s, (short)3);
   }
 
   public short getInWsProvSubjDeclarationYear() {
      return importView.InWsProvSubjDeclarationYear;
   }
   public void setInWsProvSubjDeclarationYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InWsProvSubjDeclarationYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InWsProvSubjDeclarationYear", null, null));
      }
      importView.InWsProvSubjDeclarationYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsProvSubjDeclarationYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsProvSubjDeclarationYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsProvSubjDeclarationYear", null, null));
      }
      try {
          setInWsProvSubjDeclarationYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsProvSubjDeclarationYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsProvSubjDeclarationYear", null, null));
      }
   }
 
   public String getInWsProvSubjDeclarationLanguage() {
      return FixedStringAttr.valueOf(importView.InWsProvSubjDeclarationLanguage, 1);
   }
   public void setInWsProvSubjDeclarationLanguage(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsProvSubjDeclarationLanguage must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsProvSubjDeclarationLanguage", null, null));
      }
      importView.InWsProvSubjDeclarationLanguage = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public Calendar getInWsProvSubjDeclarationExamDate() {
      return DateAttr.toCalendar(importView.InWsProvSubjDeclarationExamDate);
   }
   public int getAsIntInWsProvSubjDeclarationExamDate() {
      return DateAttr.toInt(importView.InWsProvSubjDeclarationExamDate);
   }
   public void setInWsProvSubjDeclarationExamDate(Calendar s)
      throws PropertyVetoException {
      importView.InWsProvSubjDeclarationExamDate = DateAttr.valueOf(s);
   }
   public void setAsStringInWsProvSubjDeclarationExamDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInWsProvSubjDeclarationExamDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInWsProvSubjDeclarationExamDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InWsProvSubjDeclarationExamDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InWsProvSubjDeclarationExamDate", null, null));
         }
      }
   }
   public void setAsIntInWsProvSubjDeclarationExamDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInWsProvSubjDeclarationExamDate(temp);
   }
 
   public short getInWsProvSubjDeclarationHours() {
      return importView.InWsProvSubjDeclarationHours;
   }
   public void setInWsProvSubjDeclarationHours(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InWsProvSubjDeclarationHours has more than 4 digits.",
               new PropertyChangeEvent (this, "InWsProvSubjDeclarationHours", null, null));
      }
      importView.InWsProvSubjDeclarationHours = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsProvSubjDeclarationHours(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsProvSubjDeclarationHours is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsProvSubjDeclarationHours", null, null));
      }
      try {
          setInWsProvSubjDeclarationHours(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsProvSubjDeclarationHours is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsProvSubjDeclarationHours", null, null));
      }
   }
 
   public Calendar getInWsProvSubjDeclarationReportDate() {
      return DateAttr.toCalendar(importView.InWsProvSubjDeclarationReportDate);
   }
   public int getAsIntInWsProvSubjDeclarationReportDate() {
      return DateAttr.toInt(importView.InWsProvSubjDeclarationReportDate);
   }
   public void setInWsProvSubjDeclarationReportDate(Calendar s)
      throws PropertyVetoException {
      importView.InWsProvSubjDeclarationReportDate = DateAttr.valueOf(s);
   }
   public void setAsStringInWsProvSubjDeclarationReportDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInWsProvSubjDeclarationReportDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInWsProvSubjDeclarationReportDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InWsProvSubjDeclarationReportDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InWsProvSubjDeclarationReportDate", null, null));
         }
      }
   }
   public void setAsIntInWsProvSubjDeclarationReportDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInWsProvSubjDeclarationReportDate(temp);
   }
 
   public Calendar getInWsProvSubjDeclarationLastPracticalDate() {
      return DateAttr.toCalendar(importView.InWsProvSubjDeclarationLastPracticalDate);
   }
   public int getAsIntInWsProvSubjDeclarationLastPracticalDate() {
      return DateAttr.toInt(importView.InWsProvSubjDeclarationLastPracticalDate);
   }
   public void setInWsProvSubjDeclarationLastPracticalDate(Calendar s)
      throws PropertyVetoException {
      importView.InWsProvSubjDeclarationLastPracticalDate = DateAttr.valueOf(s);
   }
   public void setAsStringInWsProvSubjDeclarationLastPracticalDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInWsProvSubjDeclarationLastPracticalDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInWsProvSubjDeclarationLastPracticalDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InWsProvSubjDeclarationLastPracticalDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InWsProvSubjDeclarationLastPracticalDate", null, null));
         }
      }
   }
   public void setAsIntInWsProvSubjDeclarationLastPracticalDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInWsProvSubjDeclarationLastPracticalDate(temp);
   }
 
   public String getInWsProvSubjDeclarationReference() {
      return FixedStringAttr.valueOf(importView.InWsProvSubjDeclarationReference, 20);
   }
   public void setInWsProvSubjDeclarationReference(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 20) {
         throw new PropertyVetoException("InWsProvSubjDeclarationReference must be <= 20 characters.",
               new PropertyChangeEvent (this, "InWsProvSubjDeclarationReference", null, null));
      }
      importView.InWsProvSubjDeclarationReference = FixedStringAttr.valueOf(s, (short)20);
   }
 
   public String getInWsProvSubjDeclarationCoverLetter() {
      return FixedStringAttr.valueOf(importView.InWsProvSubjDeclarationCoverLetter, 1);
   }
   public void setInWsProvSubjDeclarationCoverLetter(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsProvSubjDeclarationCoverLetter must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsProvSubjDeclarationCoverLetter", null, null));
      }
      importView.InWsProvSubjDeclarationCoverLetter = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsProvSubjDeclarationMarksFlag() {
      return FixedStringAttr.valueOf(importView.InWsProvSubjDeclarationMarksFlag, 1);
   }
   public void setInWsProvSubjDeclarationMarksFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsProvSubjDeclarationMarksFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsProvSubjDeclarationMarksFlag", null, null));
      }
      importView.InWsProvSubjDeclarationMarksFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsProvSubjDeclarationOldDegree() {
      return FixedStringAttr.valueOf(importView.InWsProvSubjDeclarationOldDegree, 1);
   }
   public void setInWsProvSubjDeclarationOldDegree(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsProvSubjDeclarationOldDegree must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsProvSubjDeclarationOldDegree", null, null));
      }
      importView.InWsProvSubjDeclarationOldDegree = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsProvSubjDeclarationCoursesFlag() {
      return FixedStringAttr.valueOf(importView.InWsProvSubjDeclarationCoursesFlag, 1);
   }
   public void setInWsProvSubjDeclarationCoursesFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsProvSubjDeclarationCoursesFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsProvSubjDeclarationCoursesFlag", null, null));
      }
      importView.InWsProvSubjDeclarationCoursesFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInWsProvSubjDeclarationNumberCopies() {
      return importView.InWsProvSubjDeclarationNumberCopies;
   }
   public void setInWsProvSubjDeclarationNumberCopies(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InWsProvSubjDeclarationNumberCopies has more than 1 digits.",
               new PropertyChangeEvent (this, "InWsProvSubjDeclarationNumberCopies", null, null));
      }
      importView.InWsProvSubjDeclarationNumberCopies = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsProvSubjDeclarationNumberCopies(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsProvSubjDeclarationNumberCopies is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsProvSubjDeclarationNumberCopies", null, null));
      }
      try {
          setInWsProvSubjDeclarationNumberCopies(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsProvSubjDeclarationNumberCopies is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsProvSubjDeclarationNumberCopies", null, null));
      }
   }
 
   public String getInWsProvSubjDeclarationLlbOtherLine1() {
      return FixedStringAttr.valueOf(importView.InWsProvSubjDeclarationLlbOtherLine1, 30);
   }
   public void setInWsProvSubjDeclarationLlbOtherLine1(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 30) {
         throw new PropertyVetoException("InWsProvSubjDeclarationLlbOtherLine1 must be <= 30 characters.",
               new PropertyChangeEvent (this, "InWsProvSubjDeclarationLlbOtherLine1", null, null));
      }
      importView.InWsProvSubjDeclarationLlbOtherLine1 = FixedStringAttr.valueOf(s, (short)30);
   }
 
   public String getInWsProvSubjDeclarationLlbOtherLine2() {
      return FixedStringAttr.valueOf(importView.InWsProvSubjDeclarationLlbOtherLine2, 30);
   }
   public void setInWsProvSubjDeclarationLlbOtherLine2(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 30) {
         throw new PropertyVetoException("InWsProvSubjDeclarationLlbOtherLine2 must be <= 30 characters.",
               new PropertyChangeEvent (this, "InWsProvSubjDeclarationLlbOtherLine2", null, null));
      }
      importView.InWsProvSubjDeclarationLlbOtherLine2 = FixedStringAttr.valueOf(s, (short)30);
   }
 
   public String getInWsProvSubjDeclarationLlbOtherLine3() {
      return FixedStringAttr.valueOf(importView.InWsProvSubjDeclarationLlbOtherLine3, 30);
   }
   public void setInWsProvSubjDeclarationLlbOtherLine3(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 30) {
         throw new PropertyVetoException("InWsProvSubjDeclarationLlbOtherLine3 must be <= 30 characters.",
               new PropertyChangeEvent (this, "InWsProvSubjDeclarationLlbOtherLine3", null, null));
      }
      importView.InWsProvSubjDeclarationLlbOtherLine3 = FixedStringAttr.valueOf(s, (short)30);
   }
 
   public String getInWsProvSubjDeclarationEducInst1() {
      return FixedStringAttr.valueOf(importView.InWsProvSubjDeclarationEducInst1, 4);
   }
   public void setInWsProvSubjDeclarationEducInst1(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 4) {
         throw new PropertyVetoException("InWsProvSubjDeclarationEducInst1 must be <= 4 characters.",
               new PropertyChangeEvent (this, "InWsProvSubjDeclarationEducInst1", null, null));
      }
      importView.InWsProvSubjDeclarationEducInst1 = FixedStringAttr.valueOf(s, (short)4);
   }
 
   public String getInWsProvSubjDeclarationEducInst2() {
      return FixedStringAttr.valueOf(importView.InWsProvSubjDeclarationEducInst2, 4);
   }
   public void setInWsProvSubjDeclarationEducInst2(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 4) {
         throw new PropertyVetoException("InWsProvSubjDeclarationEducInst2 must be <= 4 characters.",
               new PropertyChangeEvent (this, "InWsProvSubjDeclarationEducInst2", null, null));
      }
      importView.InWsProvSubjDeclarationEducInst2 = FixedStringAttr.valueOf(s, (short)4);
   }
 
   public String getInWsProvSubjDeclarationEducInst3() {
      return FixedStringAttr.valueOf(importView.InWsProvSubjDeclarationEducInst3, 4);
   }
   public void setInWsProvSubjDeclarationEducInst3(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 4) {
         throw new PropertyVetoException("InWsProvSubjDeclarationEducInst3 must be <= 4 characters.",
               new PropertyChangeEvent (this, "InWsProvSubjDeclarationEducInst3", null, null));
      }
      importView.InWsProvSubjDeclarationEducInst3 = FixedStringAttr.valueOf(s, (short)4);
   }
 
   public String getIn1WsStudyUnitCode() {
      return FixedStringAttr.valueOf(importView.In1WsStudyUnitCode, 7);
   }
   public void setIn1WsStudyUnitCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("In1WsStudyUnitCode must be <= 7 characters.",
               new PropertyChangeEvent (this, "In1WsStudyUnitCode", null, null));
      }
      importView.In1WsStudyUnitCode = FixedStringAttr.valueOf(s, (short)7);
   }
 
   public String getIn2WsStudyUnitCode() {
      return FixedStringAttr.valueOf(importView.In2WsStudyUnitCode, 7);
   }
   public void setIn2WsStudyUnitCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("In2WsStudyUnitCode must be <= 7 characters.",
               new PropertyChangeEvent (this, "In2WsStudyUnitCode", null, null));
      }
      importView.In2WsStudyUnitCode = FixedStringAttr.valueOf(s, (short)7);
   }
 
   public String getIn3WsStudyUnitCode() {
      return FixedStringAttr.valueOf(importView.In3WsStudyUnitCode, 7);
   }
   public void setIn3WsStudyUnitCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("In3WsStudyUnitCode must be <= 7 characters.",
               new PropertyChangeEvent (this, "In3WsStudyUnitCode", null, null));
      }
      importView.In3WsStudyUnitCode = FixedStringAttr.valueOf(s, (short)7);
   }
 
   public int getInStudentAnnualRecordMkStudentNr() {
      return importView.InStudentAnnualRecordMkStudentNr;
   }
   public void setInStudentAnnualRecordMkStudentNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InStudentAnnualRecordMkStudentNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordMkStudentNr", null, null));
      }
      importView.InStudentAnnualRecordMkStudentNr = IntAttr.valueOf(s);
   }
   public void setAsStringInStudentAnnualRecordMkStudentNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAnnualRecordMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAnnualRecordMkStudentNr", null, null));
      }
      try {
          setInStudentAnnualRecordMkStudentNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAnnualRecordMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAnnualRecordMkStudentNr", null, null));
      }
   }
 
   public int getInStudentAnnualRecordPersonnelNr() {
      return importView.InStudentAnnualRecordPersonnelNr;
   }
   public void setInStudentAnnualRecordPersonnelNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InStudentAnnualRecordPersonnelNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordPersonnelNr", null, null));
      }
      importView.InStudentAnnualRecordPersonnelNr = IntAttr.valueOf(s);
   }
   public void setAsStringInStudentAnnualRecordPersonnelNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAnnualRecordPersonnelNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAnnualRecordPersonnelNr", null, null));
      }
      try {
          setInStudentAnnualRecordPersonnelNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAnnualRecordPersonnelNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAnnualRecordPersonnelNr", null, null));
      }
   }
 
   public int getInWsUserNumber() {
      return importView.InWsUserNumber;
   }
   public void setInWsUserNumber(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InWsUserNumber has more than 5 digits.",
               new PropertyChangeEvent (this, "InWsUserNumber", null, null));
      }
      importView.InWsUserNumber = IntAttr.valueOf(s);
   }
   public void setAsStringInWsUserNumber(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsUserNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsUserNumber", null, null));
      }
      try {
          setInWsUserNumber(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsUserNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsUserNumber", null, null));
      }
   }
 
   public String getInWizfuncReportingControlPrinterCode() {
      return StringAttr.valueOf(importView.InWizfuncReportingControlPrinterCode);
   }
   public void setInWizfuncReportingControlPrinterCode(String s)
      throws PropertyVetoException {
      if (s.length() > 10) {
         throw new PropertyVetoException("InWizfuncReportingControlPrinterCode must be <= 10 characters.",
               new PropertyChangeEvent (this, "InWizfuncReportingControlPrinterCode", null, null));
      }
      importView.InWizfuncReportingControlPrinterCode = StringAttr.valueOf(s, (short)10);
   }
 
   public String getInWsWorkstationCode() {
      return FixedStringAttr.valueOf(importView.InWsWorkstationCode, 10);
   }
   public void setInWsWorkstationCode(String s)
      throws PropertyVetoException {
      if (s.length() > 10) {
         throw new PropertyVetoException("InWsWorkstationCode must be <= 10 characters.",
               new PropertyChangeEvent (this, "InWsWorkstationCode", null, null));
      }
      importView.InWsWorkstationCode = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public int getInWsFunctionNumber() {
      return importView.InWsFunctionNumber;
   }
   public void setInWsFunctionNumber(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InWsFunctionNumber has more than 5 digits.",
               new PropertyChangeEvent (this, "InWsFunctionNumber", null, null));
      }
      importView.InWsFunctionNumber = IntAttr.valueOf(s);
   }
   public void setAsStringInWsFunctionNumber(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsFunctionNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsFunctionNumber", null, null));
      }
      try {
          setInWsFunctionNumber(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsFunctionNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsFunctionNumber", null, null));
      }
   }
 
   public String getInWsServiceFeesReasonCode() {
      return FixedStringAttr.valueOf(importView.InWsServiceFeesReasonCode, 1);
   }
   public void setInWsServiceFeesReasonCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srsrj11sPrtNormalDeclarationWsServiceFeesReasonCodePropertyEditor pe = new Srsrj11sPrtNormalDeclarationWsServiceFeesReasonCodePropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InWsServiceFeesReasonCode value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsServiceFeesReasonCode", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsServiceFeesReasonCode must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsServiceFeesReasonCode", null, null));
      }
      importView.InWsServiceFeesReasonCode = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsPrinterCode() {
      return FixedStringAttr.valueOf(importView.InWsPrinterCode, 10);
   }
   public void setInWsPrinterCode(String s)
      throws PropertyVetoException {
      if (s.length() > 10) {
         throw new PropertyVetoException("InWsPrinterCode must be <= 10 characters.",
               new PropertyChangeEvent (this, "InWsPrinterCode", null, null));
      }
      importView.InWsPrinterCode = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public short getInSepWsProvSubjDeclarationYear() {
      return importView.InSepWsProvSubjDeclarationYear;
   }
   public void setInSepWsProvSubjDeclarationYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InSepWsProvSubjDeclarationYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InSepWsProvSubjDeclarationYear", null, null));
      }
      importView.InSepWsProvSubjDeclarationYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInSepWsProvSubjDeclarationYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSepWsProvSubjDeclarationYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSepWsProvSubjDeclarationYear", null, null));
      }
      try {
          setInSepWsProvSubjDeclarationYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSepWsProvSubjDeclarationYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSepWsProvSubjDeclarationYear", null, null));
      }
   }
 
   public String getInActionCsfClientServerCommunicationsAction() {
      return FixedStringAttr.valueOf(importView.InActionCsfClientServerCommunicationsAction, 2);
   }
   public void setInActionCsfClientServerCommunicationsAction(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InActionCsfClientServerCommunicationsAction must be <= 2 characters.",
               new PropertyChangeEvent (this, "InActionCsfClientServerCommunicationsAction", null, null));
      }
      importView.InActionCsfClientServerCommunicationsAction = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public String getInSendByEmailIefSuppliedFlag() {
      return FixedStringAttr.valueOf(importView.InSendByEmailIefSuppliedFlag, 1);
   }
   public void setInSendByEmailIefSuppliedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srsrj11sPrtNormalDeclarationIefSuppliedFlagPropertyEditor pe = new Srsrj11sPrtNormalDeclarationIefSuppliedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InSendByEmailIefSuppliedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InSendByEmailIefSuppliedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InSendByEmailIefSuppliedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InSendByEmailIefSuppliedFlag", null, null));
      }
      importView.InSendByEmailIefSuppliedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInEmailAddressWsAddressEmailAddress() {
      return FixedStringAttr.valueOf(importView.InEmailAddressWsAddressEmailAddress, 28);
   }
   public void setInEmailAddressWsAddressEmailAddress(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InEmailAddressWsAddressEmailAddress must be <= 28 characters.",
               new PropertyChangeEvent (this, "InEmailAddressWsAddressEmailAddress", null, null));
      }
      importView.InEmailAddressWsAddressEmailAddress = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInEmailAddressWsAddressCellNumber() {
      return StringAttr.valueOf(importView.InEmailAddressWsAddressCellNumber);
   }
   public void setInEmailAddressWsAddressCellNumber(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 20) {
         throw new PropertyVetoException("InEmailAddressWsAddressCellNumber must be <= 20 characters.",
               new PropertyChangeEvent (this, "InEmailAddressWsAddressCellNumber", null, null));
      }
      importView.InEmailAddressWsAddressCellNumber = StringAttr.valueOf(s, (short)20);
   }
 
   public String getInSendSmsIefSuppliedFlag() {
      return FixedStringAttr.valueOf(importView.InSendSmsIefSuppliedFlag, 1);
   }
   public void setInSendSmsIefSuppliedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srsrj11sPrtNormalDeclarationIefSuppliedFlagPropertyEditor pe = new Srsrj11sPrtNormalDeclarationIefSuppliedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InSendSmsIefSuppliedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InSendSmsIefSuppliedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InSendSmsIefSuppliedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InSendSmsIefSuppliedFlag", null, null));
      }
      importView.InSendSmsIefSuppliedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInEmailFromWsAddressEmailAddress() {
      return FixedStringAttr.valueOf(importView.InEmailFromWsAddressEmailAddress, 28);
   }
   public void setInEmailFromWsAddressEmailAddress(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InEmailFromWsAddressEmailAddress must be <= 28 characters.",
               new PropertyChangeEvent (this, "InEmailFromWsAddressEmailAddress", null, null));
      }
      importView.InEmailFromWsAddressEmailAddress = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getOutNqfLevelIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutNqfLevelIefSuppliedFlag, 1);
   }
 
   public String getOutNqfCreditsIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutNqfCreditsIefSuppliedFlag, 1);
   }
 
   public int getOutEducInstIefSuppliedCount() {
      return exportView.OutEducInstIefSuppliedCount;
   }
 
   public int getOutMajorSubjectIefSuppliedCount() {
      return exportView.OutMajorSubjectIefSuppliedCount;
   }
 
   public short getOutCsfClientServerCommunicationsReturnCode() {
      return exportView.OutCsfClientServerCommunicationsReturnCode;
   }
 
   public String getOutCsfClientServerCommunicationsServerRollbackFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsServerRollbackFlag, 1);
   }
 
   public String getOutErrmsgCsfStringsString500() {
      return StringAttr.valueOf(exportView.OutErrmsgCsfStringsString500);
   }
 
   public String getOutWsMethodResultReturnCode() {
      return FixedStringAttr.valueOf(exportView.OutWsMethodResultReturnCode, 2);
   }
 
   public String getOutEmailCellAddressToWsAddressEmailAddress() {
      return FixedStringAttr.valueOf(exportView.OutEmailCellAddressToWsAddressEmailAddress, 28);
   }
 
   public String getOutEmailCellAddressToWsAddressCellNumber() {
      return StringAttr.valueOf(exportView.OutEmailCellAddressToWsAddressCellNumber);
   }
 
   public String getOutEmailAddressFromWsAddressEmailAddress() {
      return FixedStringAttr.valueOf(exportView.OutEmailAddressFromWsAddressEmailAddress, 28);
   }
 
};
