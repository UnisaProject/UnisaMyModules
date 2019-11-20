package Exfyq01h.Abean;
 
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
public class Exfyq01sMntFiYearStudConc  implements ActionListener, java.io.Serializable  {
 
   //  Use final String for the bean name
   public static final String BEANNAME = new String("Exfyq01sMntFiYearStudConc");
 
   //  Constants for Asynchronous status
   public static final int PENDING = CoopFlow.DATA_NOT_READY;
   public static final int AVAILABLE = CoopFlow.DATA_READY;
   public static final int INVALID_ID = CoopFlow.INVALID_ID;
 
   private final SimpleDateFormat nativeTimestampFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
   private final SimpleDateFormat nativeTimeFormatter = new SimpleDateFormat("HHmmss");
   private final SimpleDateFormat nativeDateFormatter = new SimpleDateFormat("yyyyMMdd");
   public Exfyq01sMntFiYearStudConc() {
      super();
      nativeDateFormatter.setLenient(false);
      nativeTimeFormatter.setLenient(false);
      nativeTimestampFormatter.setLenient(false);
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
 
 
   private Exfyq01sMntFiYearStudConcOperation oper = null;
 
   /**
    * Calls the transaction/procedure step on the server.
    *
    * @exception java.beans.PropertyVetoException
    * Final property checks can throw this exception.
    */
   public void execute()  throws PropertyVetoException {
      try  {
         if (oper == null) {
            oper = new Exfyq01sMntFiYearStudConcOperation(this);
            addCompletionListener(new operListener(this));
            addExceptionListener(new operListener(this));
         }
 

 
         oper.doExfyq01sMntFiYearStudConcOperation();
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
      private Exfyq01sMntFiYearStudConc rP;
      operListener(Exfyq01sMntFiYearStudConc r)  {
         rP = r;
      }
      public void actionPerformed(ActionEvent aEvent)  {
         if (Trace.isTracing(Trace.MASK_APPLICATION))
         {
            Trace.record(Trace.MASK_APPLICATION, "Exfyq01sMntFiYearStudConc", "Listener heard that Exfyq01sMntFiYearStudConcOperation completed with " + 
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of completion of operation Exfyq01sMntFiYearStudConc ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      synchronized (this)  {
         targets = (Vector) completionListeners.clone();
      }
      actionEvt = new ActionEvent(this, 0, "Completion.Exfyq01sMntFiYearStudConc");
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of exception of operation Exfyq01sMntFiYearStudConc ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      String failCommand = "Exception.Exfyq01sMntFiYearStudConc";
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
      importView.InWsWsFinalYearStudentAccessmentProcessedFlag = FixedStringAttr.valueOf("N", 1);
      importView.InWsWsFinalYearStudentAccessmentDeclinedFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutExamReMarkStatusCode = FixedStringAttr.valueOf("R", 1);
      exportView.OutExamReMarkMarksPrintedFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutExamReMarkApplicationPrintedFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutExamReMarkMoneyRefundedFlag = FixedStringAttr.valueOf("N", 1);
   }

   Exfyq01h.EXFYQ011_IA importView = Exfyq01h.EXFYQ011_IA.getInstance();
   Exfyq01h.EXFYQ011_OA exportView = Exfyq01h.EXFYQ011_OA.getInstance();
   public int getInResponsibleLecturerWsStaffPersno() {
      return importView.InResponsibleLecturerWsStaffPersno;
   }
   public void setInResponsibleLecturerWsStaffPersno(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InResponsibleLecturerWsStaffPersno has more than 8 digits.",
               new PropertyChangeEvent (this, "InResponsibleLecturerWsStaffPersno", null, null));
      }
      importView.InResponsibleLecturerWsStaffPersno = IntAttr.valueOf(s);
   }
   public void setAsStringInResponsibleLecturerWsStaffPersno(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InResponsibleLecturerWsStaffPersno is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InResponsibleLecturerWsStaffPersno", null, null));
      }
      try {
          setInResponsibleLecturerWsStaffPersno(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InResponsibleLecturerWsStaffPersno is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InResponsibleLecturerWsStaffPersno", null, null));
      }
   }
 
   public String getInResponsibleLecturerWsStaffName() {
      return FixedStringAttr.valueOf(importView.InResponsibleLecturerWsStaffName, 28);
   }
   public void setInResponsibleLecturerWsStaffName(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InResponsibleLecturerWsStaffName must be <= 28 characters.",
               new PropertyChangeEvent (this, "InResponsibleLecturerWsStaffName", null, null));
      }
      importView.InResponsibleLecturerWsStaffName = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInResponsibleLecturerWsStaffNovellUserId() {
      return FixedStringAttr.valueOf(importView.InResponsibleLecturerWsStaffNovellUserId, 20);
   }
   public void setInResponsibleLecturerWsStaffNovellUserId(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 20) {
         throw new PropertyVetoException("InResponsibleLecturerWsStaffNovellUserId must be <= 20 characters.",
               new PropertyChangeEvent (this, "InResponsibleLecturerWsStaffNovellUserId", null, null));
      }
      importView.InResponsibleLecturerWsStaffNovellUserId = FixedStringAttr.valueOf(s, (short)20);
   }
 
   public String getInAcademicSupportWsGenericCodeCode() {
      return FixedStringAttr.valueOf(importView.InAcademicSupportWsGenericCodeCode, 10);
   }
   public void setInAcademicSupportWsGenericCodeCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InAcademicSupportWsGenericCodeCode must be <= 10 characters.",
               new PropertyChangeEvent (this, "InAcademicSupportWsGenericCodeCode", null, null));
      }
      importView.InAcademicSupportWsGenericCodeCode = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInAcademicSupportWsGenericCodeEngDescription() {
      return FixedStringAttr.valueOf(importView.InAcademicSupportWsGenericCodeEngDescription, 40);
   }
   public void setInAcademicSupportWsGenericCodeEngDescription(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 40) {
         throw new PropertyVetoException("InAcademicSupportWsGenericCodeEngDescription must be <= 40 characters.",
               new PropertyChangeEvent (this, "InAcademicSupportWsGenericCodeEngDescription", null, null));
      }
      importView.InAcademicSupportWsGenericCodeEngDescription = FixedStringAttr.valueOf(s, (short)40);
   }
 
   public String getInAlternativeInformationWsGenericCodeCode() {
      return FixedStringAttr.valueOf(importView.InAlternativeInformationWsGenericCodeCode, 10);
   }
   public void setInAlternativeInformationWsGenericCodeCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InAlternativeInformationWsGenericCodeCode must be <= 10 characters.",
               new PropertyChangeEvent (this, "InAlternativeInformationWsGenericCodeCode", null, null));
      }
      importView.InAlternativeInformationWsGenericCodeCode = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInAlternativeInformationWsGenericCodeEngDescription() {
      return FixedStringAttr.valueOf(importView.InAlternativeInformationWsGenericCodeEngDescription, 40);
   }
   public void setInAlternativeInformationWsGenericCodeEngDescription(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 40) {
         throw new PropertyVetoException("InAlternativeInformationWsGenericCodeEngDescription must be <= 40 characters.",
               new PropertyChangeEvent (this, "InAlternativeInformationWsGenericCodeEngDescription", null, null));
      }
      importView.InAlternativeInformationWsGenericCodeEngDescription = FixedStringAttr.valueOf(s, (short)40);
   }
 
   public String getInWsWsFinalYearStudentAccessmentProcessedFlag() {
      return FixedStringAttr.valueOf(importView.InWsWsFinalYearStudentAccessmentProcessedFlag, 1);
   }
   public void setInWsWsFinalYearStudentAccessmentProcessedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Exfyq01sMntFiYearStudConcWsFinalYearStudentAccessmentProcessedFlagPropertyEditor pe = new Exfyq01sMntFiYearStudConcWsFinalYearStudentAccessmentProcessedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InWsWsFinalYearStudentAccessmentProcessedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsWsFinalYearStudentAccessmentProcessedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsWsFinalYearStudentAccessmentProcessedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsWsFinalYearStudentAccessmentProcessedFlag", null, null));
      }
      importView.InWsWsFinalYearStudentAccessmentProcessedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsWsFinalYearStudentAccessmentDeclinedFlag() {
      return FixedStringAttr.valueOf(importView.InWsWsFinalYearStudentAccessmentDeclinedFlag, 1);
   }
   public void setInWsWsFinalYearStudentAccessmentDeclinedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Exfyq01sMntFiYearStudConcWsFinalYearStudentAccessmentDeclinedFlagPropertyEditor pe = new Exfyq01sMntFiYearStudConcWsFinalYearStudentAccessmentDeclinedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InWsWsFinalYearStudentAccessmentDeclinedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsWsFinalYearStudentAccessmentDeclinedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsWsFinalYearStudentAccessmentDeclinedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsWsFinalYearStudentAccessmentDeclinedFlag", null, null));
      }
      importView.InWsWsFinalYearStudentAccessmentDeclinedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInExamReMarkExamYear() {
      return importView.InExamReMarkExamYear;
   }
   public void setInExamReMarkExamYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InExamReMarkExamYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InExamReMarkExamYear", null, null));
      }
      importView.InExamReMarkExamYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInExamReMarkExamYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InExamReMarkExamYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamReMarkExamYear", null, null));
      }
      try {
          setInExamReMarkExamYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InExamReMarkExamYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamReMarkExamYear", null, null));
      }
   }
 
   public short getInExamReMarkMkExamPeriodCode() {
      return importView.InExamReMarkMkExamPeriodCode;
   }
   public void setInExamReMarkMkExamPeriodCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InExamReMarkMkExamPeriodCode has more than 2 digits.",
               new PropertyChangeEvent (this, "InExamReMarkMkExamPeriodCode", null, null));
      }
      importView.InExamReMarkMkExamPeriodCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInExamReMarkMkExamPeriodCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InExamReMarkMkExamPeriodCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamReMarkMkExamPeriodCode", null, null));
      }
      try {
          setInExamReMarkMkExamPeriodCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InExamReMarkMkExamPeriodCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamReMarkMkExamPeriodCode", null, null));
      }
   }
 
   public int getInExamReMarkMkStudentNr() {
      return importView.InExamReMarkMkStudentNr;
   }
   public void setInExamReMarkMkStudentNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InExamReMarkMkStudentNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InExamReMarkMkStudentNr", null, null));
      }
      importView.InExamReMarkMkStudentNr = IntAttr.valueOf(s);
   }
   public void setAsStringInExamReMarkMkStudentNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InExamReMarkMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamReMarkMkStudentNr", null, null));
      }
      try {
          setInExamReMarkMkStudentNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InExamReMarkMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamReMarkMkStudentNr", null, null));
      }
   }
 
   public String getInExamReMarkMkStudyUnitCode() {
      return FixedStringAttr.valueOf(importView.InExamReMarkMkStudyUnitCode, 7);
   }
   public void setInExamReMarkMkStudyUnitCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InExamReMarkMkStudyUnitCode must be <= 7 characters.",
               new PropertyChangeEvent (this, "InExamReMarkMkStudyUnitCode", null, null));
      }
      importView.InExamReMarkMkStudyUnitCode = FixedStringAttr.valueOf(s, (short)7);
   }
 
   public String getInExamReMarkType() {
      return FixedStringAttr.valueOf(importView.InExamReMarkType, 1);
   }
   public void setInExamReMarkType(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InExamReMarkType must be <= 1 characters.",
               new PropertyChangeEvent (this, "InExamReMarkType", null, null));
      }
      importView.InExamReMarkType = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInExamReMarkTrackStatus() {
      return FixedStringAttr.valueOf(importView.InExamReMarkTrackStatus, 10);
   }
   public void setInExamReMarkTrackStatus(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InExamReMarkTrackStatus must be <= 10 characters.",
               new PropertyChangeEvent (this, "InExamReMarkTrackStatus", null, null));
      }
      importView.InExamReMarkTrackStatus = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public int getInExamReMarkRemarker() {
      return importView.InExamReMarkRemarker;
   }
   public void setInExamReMarkRemarker(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InExamReMarkRemarker has more than 8 digits.",
               new PropertyChangeEvent (this, "InExamReMarkRemarker", null, null));
      }
      importView.InExamReMarkRemarker = IntAttr.valueOf(s);
   }
   public void setAsStringInExamReMarkRemarker(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InExamReMarkRemarker is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamReMarkRemarker", null, null));
      }
      try {
          setInExamReMarkRemarker(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InExamReMarkRemarker is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamReMarkRemarker", null, null));
      }
   }
 
   public String getInExamReMarkAssessOptGc91() {
      return FixedStringAttr.valueOf(importView.InExamReMarkAssessOptGc91, 10);
   }
   public void setInExamReMarkAssessOptGc91(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InExamReMarkAssessOptGc91 must be <= 10 characters.",
               new PropertyChangeEvent (this, "InExamReMarkAssessOptGc91", null, null));
      }
      importView.InExamReMarkAssessOptGc91 = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInExamReMarkAssessOptOther() {
      return FixedStringAttr.valueOf(importView.InExamReMarkAssessOptOther, 250);
   }
   public void setInExamReMarkAssessOptOther(String s)
      throws PropertyVetoException {
      if (s.length() > 250) {
         throw new PropertyVetoException("InExamReMarkAssessOptOther must be <= 250 characters.",
               new PropertyChangeEvent (this, "InExamReMarkAssessOptOther", null, null));
      }
      importView.InExamReMarkAssessOptOther = FixedStringAttr.valueOf(s, (short)250);
   }
 
   public String getInExamReMarkSupportProvGc92() {
      return FixedStringAttr.valueOf(importView.InExamReMarkSupportProvGc92, 10);
   }
   public void setInExamReMarkSupportProvGc92(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InExamReMarkSupportProvGc92 must be <= 10 characters.",
               new PropertyChangeEvent (this, "InExamReMarkSupportProvGc92", null, null));
      }
      importView.InExamReMarkSupportProvGc92 = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInExamReMarkSupportProdOther() {
      return FixedStringAttr.valueOf(importView.InExamReMarkSupportProdOther, 250);
   }
   public void setInExamReMarkSupportProdOther(String s)
      throws PropertyVetoException {
      if (s.length() > 250) {
         throw new PropertyVetoException("InExamReMarkSupportProdOther must be <= 250 characters.",
               new PropertyChangeEvent (this, "InExamReMarkSupportProdOther", null, null));
      }
      importView.InExamReMarkSupportProdOther = FixedStringAttr.valueOf(s, (short)250);
   }
 
   public short getInExamReMarkRevisedFinalMark() {
      return importView.InExamReMarkRevisedFinalMark;
   }
   public void setInExamReMarkRevisedFinalMark(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InExamReMarkRevisedFinalMark has more than 3 digits.",
               new PropertyChangeEvent (this, "InExamReMarkRevisedFinalMark", null, null));
      }
      importView.InExamReMarkRevisedFinalMark = ShortAttr.valueOf(s);
   }
   public void setAsStringInExamReMarkRevisedFinalMark(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InExamReMarkRevisedFinalMark is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamReMarkRevisedFinalMark", null, null));
      }
      try {
          setInExamReMarkRevisedFinalMark(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InExamReMarkRevisedFinalMark is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamReMarkRevisedFinalMark", null, null));
      }
   }
 
   public String getInExamReMarkAuthResponseEmail() {
      return StringAttr.valueOf(importView.InExamReMarkAuthResponseEmail);
   }
   public void setInExamReMarkAuthResponseEmail(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 60) {
         throw new PropertyVetoException("InExamReMarkAuthResponseEmail must be <= 60 characters.",
               new PropertyChangeEvent (this, "InExamReMarkAuthResponseEmail", null, null));
      }
      importView.InExamReMarkAuthResponseEmail = StringAttr.valueOf(s, (short)60);
   }
 
   public String getInExamReMarkDeclineOptGc98() {
      return FixedStringAttr.valueOf(importView.InExamReMarkDeclineOptGc98, 10);
   }
   public void setInExamReMarkDeclineOptGc98(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InExamReMarkDeclineOptGc98 must be <= 10 characters.",
               new PropertyChangeEvent (this, "InExamReMarkDeclineOptGc98", null, null));
      }
      importView.InExamReMarkDeclineOptGc98 = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInExamReMarkDeclineOptOther() {
      return FixedStringAttr.valueOf(importView.InExamReMarkDeclineOptOther, 250);
   }
   public void setInExamReMarkDeclineOptOther(String s)
      throws PropertyVetoException {
      if (s.length() > 250) {
         throw new PropertyVetoException("InExamReMarkDeclineOptOther must be <= 250 characters.",
               new PropertyChangeEvent (this, "InExamReMarkDeclineOptOther", null, null));
      }
      importView.InExamReMarkDeclineOptOther = FixedStringAttr.valueOf(s, (short)250);
   }
 
   public String getInSecurityWsWorkstationCode() {
      return StringAttr.valueOf(importView.InSecurityWsWorkstationCode);
   }
   public void setInSecurityWsWorkstationCode(String s)
      throws PropertyVetoException {
      if (s.length() > 10) {
         throw new PropertyVetoException("InSecurityWsWorkstationCode must be <= 10 characters.",
               new PropertyChangeEvent (this, "InSecurityWsWorkstationCode", null, null));
      }
      importView.InSecurityWsWorkstationCode = StringAttr.valueOf(s, (short)10);
   }
 
   public String getInSecurityWsPrinterCode() {
      return FixedStringAttr.valueOf(importView.InSecurityWsPrinterCode, 10);
   }
   public void setInSecurityWsPrinterCode(String s)
      throws PropertyVetoException {
      if (s.length() > 10) {
         throw new PropertyVetoException("InSecurityWsPrinterCode must be <= 10 characters.",
               new PropertyChangeEvent (this, "InSecurityWsPrinterCode", null, null));
      }
      importView.InSecurityWsPrinterCode = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public int getInSecurityWsUserNumber() {
      return importView.InSecurityWsUserNumber;
   }
   public void setInSecurityWsUserNumber(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InSecurityWsUserNumber has more than 5 digits.",
               new PropertyChangeEvent (this, "InSecurityWsUserNumber", null, null));
      }
      importView.InSecurityWsUserNumber = IntAttr.valueOf(s);
   }
   public void setAsStringInSecurityWsUserNumber(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSecurityWsUserNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSecurityWsUserNumber", null, null));
      }
      try {
          setInSecurityWsUserNumber(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSecurityWsUserNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSecurityWsUserNumber", null, null));
      }
   }
 
   public String getInSecurityWsUserNovellUserCode() {
      return FixedStringAttr.valueOf(importView.InSecurityWsUserNovellUserCode, 20);
   }
   public void setInSecurityWsUserNovellUserCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 20) {
         throw new PropertyVetoException("InSecurityWsUserNovellUserCode must be <= 20 characters.",
               new PropertyChangeEvent (this, "InSecurityWsUserNovellUserCode", null, null));
      }
      importView.InSecurityWsUserNovellUserCode = FixedStringAttr.valueOf(s, (short)20);
   }
 
   public String getInSecurityWsActionCode() {
      return FixedStringAttr.valueOf(importView.InSecurityWsActionCode, 2);
   }
   public void setInSecurityWsActionCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InSecurityWsActionCode must be <= 2 characters.",
               new PropertyChangeEvent (this, "InSecurityWsActionCode", null, null));
      }
      importView.InSecurityWsActionCode = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public String getInSecurityWsActionDescription() {
      return FixedStringAttr.valueOf(importView.InSecurityWsActionDescription, 10);
   }
   public void setInSecurityWsActionDescription(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InSecurityWsActionDescription must be <= 10 characters.",
               new PropertyChangeEvent (this, "InSecurityWsActionDescription", null, null));
      }
      importView.InSecurityWsActionDescription = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public int getInSecurityWsFunctionNumber() {
      return importView.InSecurityWsFunctionNumber;
   }
   public void setInSecurityWsFunctionNumber(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InSecurityWsFunctionNumber has more than 5 digits.",
               new PropertyChangeEvent (this, "InSecurityWsFunctionNumber", null, null));
      }
      importView.InSecurityWsFunctionNumber = IntAttr.valueOf(s);
   }
   public void setAsStringInSecurityWsFunctionNumber(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSecurityWsFunctionNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSecurityWsFunctionNumber", null, null));
      }
      try {
          setInSecurityWsFunctionNumber(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSecurityWsFunctionNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSecurityWsFunctionNumber", null, null));
      }
   }
 
   public String getInSecurityWsFunctionTrancode() {
      return FixedStringAttr.valueOf(importView.InSecurityWsFunctionTrancode, 8);
   }
   public void setInSecurityWsFunctionTrancode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 8) {
         throw new PropertyVetoException("InSecurityWsFunctionTrancode must be <= 8 characters.",
               new PropertyChangeEvent (this, "InSecurityWsFunctionTrancode", null, null));
      }
      importView.InSecurityWsFunctionTrancode = FixedStringAttr.valueOf(s, (short)8);
   }
 
   public String getInSecurityWsFunctionDescription() {
      return FixedStringAttr.valueOf(importView.InSecurityWsFunctionDescription, 28);
   }
   public void setInSecurityWsFunctionDescription(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InSecurityWsFunctionDescription must be <= 28 characters.",
               new PropertyChangeEvent (this, "InSecurityWsFunctionDescription", null, null));
      }
      importView.InSecurityWsFunctionDescription = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public short getInCsfClientServerCommunicationsClientVersionNumber() {
      return importView.InCsfClientServerCommunicationsClientVersionNumber;
   }
   public void setInCsfClientServerCommunicationsClientVersionNumber(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsClientVersionNumber has more than 4 digits.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsClientVersionNumber", null, null));
      }
      importView.InCsfClientServerCommunicationsClientVersionNumber = ShortAttr.valueOf(s);
   }
   public void setAsStringInCsfClientServerCommunicationsClientVersionNumber(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InCsfClientServerCommunicationsClientVersionNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InCsfClientServerCommunicationsClientVersionNumber", null, null));
      }
      try {
          setInCsfClientServerCommunicationsClientVersionNumber(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InCsfClientServerCommunicationsClientVersionNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InCsfClientServerCommunicationsClientVersionNumber", null, null));
      }
   }
 
   public short getInCsfClientServerCommunicationsClientRevisionNumber() {
      return importView.InCsfClientServerCommunicationsClientRevisionNumber;
   }
   public void setInCsfClientServerCommunicationsClientRevisionNumber(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsClientRevisionNumber has more than 4 digits.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsClientRevisionNumber", null, null));
      }
      importView.InCsfClientServerCommunicationsClientRevisionNumber = ShortAttr.valueOf(s);
   }
   public void setAsStringInCsfClientServerCommunicationsClientRevisionNumber(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InCsfClientServerCommunicationsClientRevisionNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InCsfClientServerCommunicationsClientRevisionNumber", null, null));
      }
      try {
          setInCsfClientServerCommunicationsClientRevisionNumber(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InCsfClientServerCommunicationsClientRevisionNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InCsfClientServerCommunicationsClientRevisionNumber", null, null));
      }
   }
 
   public String getInCsfClientServerCommunicationsClientDevelopmentPhase() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsClientDevelopmentPhase, 1);
   }
   public void setInCsfClientServerCommunicationsClientDevelopmentPhase(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsClientDevelopmentPhase must be <= 1 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsClientDevelopmentPhase", null, null));
      }
      importView.InCsfClientServerCommunicationsClientDevelopmentPhase = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInCsfClientServerCommunicationsAction() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsAction, 2);
   }
   public void setInCsfClientServerCommunicationsAction(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsAction must be <= 2 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsAction", null, null));
      }
      importView.InCsfClientServerCommunicationsAction = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public String getInCsfClientServerCommunicationsClientIsGuiFlag() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsClientIsGuiFlag, 1);
   }
   public void setInCsfClientServerCommunicationsClientIsGuiFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsClientIsGuiFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsClientIsGuiFlag", null, null));
      }
      importView.InCsfClientServerCommunicationsClientIsGuiFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public Calendar getInCsfClientServerCommunicationsClientDate() {
      return DateAttr.toCalendar(importView.InCsfClientServerCommunicationsClientDate);
   }
   public int getAsIntInCsfClientServerCommunicationsClientDate() {
      return DateAttr.toInt(importView.InCsfClientServerCommunicationsClientDate);
   }
   public void setInCsfClientServerCommunicationsClientDate(Calendar s)
      throws PropertyVetoException {
      importView.InCsfClientServerCommunicationsClientDate = DateAttr.valueOf(s);
   }
   public void setAsStringInCsfClientServerCommunicationsClientDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInCsfClientServerCommunicationsClientDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInCsfClientServerCommunicationsClientDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InCsfClientServerCommunicationsClientDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InCsfClientServerCommunicationsClientDate", null, null));
         }
      }
   }
   public void setAsIntInCsfClientServerCommunicationsClientDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInCsfClientServerCommunicationsClientDate(temp);
   }
 
   public Calendar getInCsfClientServerCommunicationsClientTime() {
      return TimeAttr.toCalendar(importView.InCsfClientServerCommunicationsClientTime);
   }
   public int getAsIntInCsfClientServerCommunicationsClientTime() {
      return TimeAttr.toInt(importView.InCsfClientServerCommunicationsClientTime);
   }
   public void setInCsfClientServerCommunicationsClientTime(Calendar s)
      throws PropertyVetoException {
      importView.InCsfClientServerCommunicationsClientTime = TimeAttr.valueOf(s);
   }
   public void setAsStringInCsfClientServerCommunicationsClientTime(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInCsfClientServerCommunicationsClientTime((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeTimeFormatter.parse(s.length() > 6 ? s.substring(0, 6) : s));
            setInCsfClientServerCommunicationsClientTime(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InCsfClientServerCommunicationsClientTime has an invalid format (HHmmss).",
                  new PropertyChangeEvent (this, "InCsfClientServerCommunicationsClientTime", null, null));
         }
      }
   }
   public void setAsIntInCsfClientServerCommunicationsClientTime(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 6)
      {
         temp = "000000".substring(temp.length()) + temp;
      }
      setAsStringInCsfClientServerCommunicationsClientTime(temp);
   }
 
   public Calendar getInCsfClientServerCommunicationsClientTimestamp() {
      return TimestampAttr.toCalendar(importView.InCsfClientServerCommunicationsClientTimestamp);
   }
   public String getAsStringInCsfClientServerCommunicationsClientTimestamp() {
      return TimestampAttr.toString(importView.InCsfClientServerCommunicationsClientTimestamp);
   }
   public void setInCsfClientServerCommunicationsClientTimestamp(Calendar s)
      throws PropertyVetoException {
      importView.InCsfClientServerCommunicationsClientTimestamp = TimestampAttr.valueOf(s);
   }
   public void setAsStringInCsfClientServerCommunicationsClientTimestamp(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInCsfClientServerCommunicationsClientTimestamp((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeTimestampFormatter.parse(s.length() > 17 ? s.substring(0, 17) : s));
            importView.InCsfClientServerCommunicationsClientTimestamp = TimestampAttr.valueOf(s);
         } catch (ParseException e) {
            throw new PropertyVetoException("InCsfClientServerCommunicationsClientTimestamp has an invalid format (yyyyMMddHHmmssSSSSSS).",
                  new PropertyChangeEvent (this, "InCsfClientServerCommunicationsClientTimestamp", null, null));
         }
      }
   }
 
   public String getInCsfClientServerCommunicationsClientTransactionCode() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsClientTransactionCode, 8);
   }
   public void setInCsfClientServerCommunicationsClientTransactionCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 8) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsClientTransactionCode must be <= 8 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsClientTransactionCode", null, null));
      }
      importView.InCsfClientServerCommunicationsClientTransactionCode = FixedStringAttr.valueOf(s, (short)8);
   }
 
   public String getInCsfClientServerCommunicationsClientUserId() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsClientUserId, 8);
   }
   public void setInCsfClientServerCommunicationsClientUserId(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 8) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsClientUserId must be <= 8 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsClientUserId", null, null));
      }
      importView.InCsfClientServerCommunicationsClientUserId = FixedStringAttr.valueOf(s, (short)8);
   }
 
   public String getInCsfClientServerCommunicationsListLinkFlag() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsListLinkFlag, 1);
   }
   public void setInCsfClientServerCommunicationsListLinkFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsListLinkFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsListLinkFlag", null, null));
      }
      importView.InCsfClientServerCommunicationsListLinkFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInCsfClientServerCommunicationsMaintLinkFlag() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsMaintLinkFlag, 1);
   }
   public void setInCsfClientServerCommunicationsMaintLinkFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsMaintLinkFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsMaintLinkFlag", null, null));
      }
      importView.InCsfClientServerCommunicationsMaintLinkFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInCsfClientServerCommunicationsFirstpassFlag() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsFirstpassFlag, 1);
   }
   public void setInCsfClientServerCommunicationsFirstpassFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsFirstpassFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsFirstpassFlag", null, null));
      }
      importView.InCsfClientServerCommunicationsFirstpassFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInStudentPaperLogRequestActionFrom() {
      return FixedStringAttr.valueOf(importView.InStudentPaperLogRequestActionFrom, 20);
   }
   public void setInStudentPaperLogRequestActionFrom(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 20) {
         throw new PropertyVetoException("InStudentPaperLogRequestActionFrom must be <= 20 characters.",
               new PropertyChangeEvent (this, "InStudentPaperLogRequestActionFrom", null, null));
      }
      importView.InStudentPaperLogRequestActionFrom = FixedStringAttr.valueOf(s, (short)20);
   }
 
   public String getInStudentPaperLogComment0() {
      return FixedStringAttr.valueOf(importView.InStudentPaperLogComment0, 250);
   }
   public void setInStudentPaperLogComment0(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 250) {
         throw new PropertyVetoException("InStudentPaperLogComment0 must be <= 250 characters.",
               new PropertyChangeEvent (this, "InStudentPaperLogComment0", null, null));
      }
      importView.InStudentPaperLogComment0 = FixedStringAttr.valueOf(s, (short)250);
   }
 
   public String getOutDeclineOptionWsGenericCodeCode() {
      return FixedStringAttr.valueOf(exportView.OutDeclineOptionWsGenericCodeCode, 10);
   }
 
   public String getOutDeclineOptionWsGenericCodeEngDescription() {
      return FixedStringAttr.valueOf(exportView.OutDeclineOptionWsGenericCodeEngDescription, 40);
   }
 
   public String getOutStatusWsGenericCodeCode() {
      return FixedStringAttr.valueOf(exportView.OutStatusWsGenericCodeCode, 10);
   }
 
   public String getOutStatusWsGenericCodeEngDescription() {
      return FixedStringAttr.valueOf(exportView.OutStatusWsGenericCodeEngDescription, 40);
   }
 
   public short getOutExamReMarkExamYear() {
      return exportView.OutExamReMarkExamYear;
   }
 
   public short getOutExamReMarkMkExamPeriodCode() {
      return exportView.OutExamReMarkMkExamPeriodCode;
   }
 
   public int getOutExamReMarkMkStudentNr() {
      return exportView.OutExamReMarkMkStudentNr;
   }
 
   public String getOutExamReMarkMkStudyUnitCode() {
      return FixedStringAttr.valueOf(exportView.OutExamReMarkMkStudyUnitCode, 7);
   }
 
   public String getOutExamReMarkStatusCode() {
      return FixedStringAttr.valueOf(exportView.OutExamReMarkStatusCode, 1);
   }
 
   public String getOutExamReMarkMarksPrintedFlag() {
      return FixedStringAttr.valueOf(exportView.OutExamReMarkMarksPrintedFlag, 1);
   }
 
   public String getOutExamReMarkApplicationPrintedFlag() {
      return FixedStringAttr.valueOf(exportView.OutExamReMarkApplicationPrintedFlag, 1);
   }
 
   public short getOutExamReMarkOriginalFinalMark() {
      return exportView.OutExamReMarkOriginalFinalMark;
   }
 
   public short getOutExamReMarkRevisedFinalMark() {
      return exportView.OutExamReMarkRevisedFinalMark;
   }
 
   public short getOutExamReMarkOriginalResultTypeCode() {
      return exportView.OutExamReMarkOriginalResultTypeCode;
   }
 
   public short getOutExamReMarkRevisedResultTypeCode() {
      return exportView.OutExamReMarkRevisedResultTypeCode;
   }
 
   public String getOutExamReMarkMoneyRefundedFlag() {
      return FixedStringAttr.valueOf(exportView.OutExamReMarkMoneyRefundedFlag, 1);
   }
 
   public String getOutExamReMarkType() {
      return FixedStringAttr.valueOf(exportView.OutExamReMarkType, 1);
   }
 
   public short getOutExamReMarkPrintBatch() {
      return exportView.OutExamReMarkPrintBatch;
   }
 
   public String getOutExamReMarkTrackStatus() {
      return FixedStringAttr.valueOf(exportView.OutExamReMarkTrackStatus, 10);
   }
 
   public int getOutExamReMarkRemarker() {
      return exportView.OutExamReMarkRemarker;
   }
 
   public String getOutExamReMarkAssessOptGc91() {
      return FixedStringAttr.valueOf(exportView.OutExamReMarkAssessOptGc91, 10);
   }
 
   public String getOutExamReMarkAssessOptOther() {
      return FixedStringAttr.valueOf(exportView.OutExamReMarkAssessOptOther, 250);
   }
 
   public String getOutExamReMarkSupportProvGc92() {
      return FixedStringAttr.valueOf(exportView.OutExamReMarkSupportProvGc92, 10);
   }
 
   public String getOutExamReMarkSupportProdOther() {
      return FixedStringAttr.valueOf(exportView.OutExamReMarkSupportProdOther, 250);
   }
 
   public String getOutExamReMarkAuthResponseEmail() {
      return StringAttr.valueOf(exportView.OutExamReMarkAuthResponseEmail);
   }
 
   public String getOutExamReMarkDeclineOptGc98() {
      return FixedStringAttr.valueOf(exportView.OutExamReMarkDeclineOptGc98, 10);
   }
 
   public String getOutExamReMarkDeclineOptOther() {
      return FixedStringAttr.valueOf(exportView.OutExamReMarkDeclineOptOther, 250);
   }
 
   public String getOutCsfStringsString500() {
      return StringAttr.valueOf(exportView.OutCsfStringsString500);
   }
 
   public short getOutCsfClientServerCommunicationsReturnCode() {
      return exportView.OutCsfClientServerCommunicationsReturnCode;
   }
 
   public short getOutCsfClientServerCommunicationsServerVersionNumber() {
      return exportView.OutCsfClientServerCommunicationsServerVersionNumber;
   }
 
   public short getOutCsfClientServerCommunicationsServerRevisionNumber() {
      return exportView.OutCsfClientServerCommunicationsServerRevisionNumber;
   }
 
   public String getOutCsfClientServerCommunicationsServerDevelopmentPhase() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsServerDevelopmentPhase, 1);
   }
 
   public String getOutCsfClientServerCommunicationsAction() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsAction, 2);
   }
 
   public String getOutCsfClientServerCommunicationsClientIsGuiFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsClientIsGuiFlag, 1);
   }
 
   public Calendar getOutCsfClientServerCommunicationsServerDate() {
      return DateAttr.toCalendar(exportView.OutCsfClientServerCommunicationsServerDate);
   }
   public int getAsIntOutCsfClientServerCommunicationsServerDate() {
      return DateAttr.toInt(exportView.OutCsfClientServerCommunicationsServerDate);
   }
 
   public Calendar getOutCsfClientServerCommunicationsServerTime() {
      return TimeAttr.toCalendar(exportView.OutCsfClientServerCommunicationsServerTime);
   }
   public int getAsIntOutCsfClientServerCommunicationsServerTime() {
      return TimeAttr.toInt(exportView.OutCsfClientServerCommunicationsServerTime);
   }
 
   public Calendar getOutCsfClientServerCommunicationsServerTimestamp() {
      return TimestampAttr.toCalendar(exportView.OutCsfClientServerCommunicationsServerTimestamp);
   }
   public String getAsStringOutCsfClientServerCommunicationsServerTimestamp() {
      return TimestampAttr.toString(exportView.OutCsfClientServerCommunicationsServerTimestamp);
   }
 
   public String getOutCsfClientServerCommunicationsServerTransactionCode() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsServerTransactionCode, 8);
   }
 
   public String getOutCsfClientServerCommunicationsServerUserId() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsServerUserId, 8);
   }
 
   public String getOutCsfClientServerCommunicationsServerRollbackFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsServerRollbackFlag, 1);
   }
 
   public String getOutCsfClientServerCommunicationsActionsPendingFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsActionsPendingFlag, 1);
   }
 
   public short getOutCsfClientServerCommunicationsClientVersionNumber() {
      return exportView.OutCsfClientServerCommunicationsClientVersionNumber;
   }
 
   public short getOutCsfClientServerCommunicationsClientRevisionNumber() {
      return exportView.OutCsfClientServerCommunicationsClientRevisionNumber;
   }
 
   public String getOutCsfClientServerCommunicationsClientDevelopmentPhase() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsClientDevelopmentPhase, 1);
   }
 
   public String getOutCsfClientServerCommunicationsListLinkFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsListLinkFlag, 1);
   }
 
   public String getOutCsfClientServerCommunicationsCancelFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsCancelFlag, 1);
   }
 
   public String getOutCsfClientServerCommunicationsMaintLinkFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsMaintLinkFlag, 1);
   }
 
   public String getOutCsfClientServerCommunicationsForceDatabaseRead() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsForceDatabaseRead, 1);
   }
 
   public String getOutCsfClientServerCommunicationsRgvScrollUpFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsRgvScrollUpFlag, 1);
   }
 
   public String getOutCsfClientServerCommunicationsRgvScrollDownFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsRgvScrollDownFlag, 1);
   }
 
   public int getOutResponsibleLecturerWsStaffPersno() {
      return exportView.OutResponsibleLecturerWsStaffPersno;
   }
 
   public String getOutResponsibleLecturerWsStaffName() {
      return FixedStringAttr.valueOf(exportView.OutResponsibleLecturerWsStaffName, 28);
   }
 
   public String getOutResponsibleLecturerWsStaffNovellUserId() {
      return FixedStringAttr.valueOf(exportView.OutResponsibleLecturerWsStaffNovellUserId, 20);
   }
 
   public String getOutAcademicSupportWsGenericCodeCode() {
      return FixedStringAttr.valueOf(exportView.OutAcademicSupportWsGenericCodeCode, 10);
   }
 
   public String getOutAcademicSupportWsGenericCodeEngDescription() {
      return FixedStringAttr.valueOf(exportView.OutAcademicSupportWsGenericCodeEngDescription, 40);
   }
 
   public String getOutAlternativeInformationWsGenericCodeCode() {
      return FixedStringAttr.valueOf(exportView.OutAlternativeInformationWsGenericCodeCode, 10);
   }
 
   public String getOutAlternativeInformationWsGenericCodeEngDescription() {
      return FixedStringAttr.valueOf(exportView.OutAlternativeInformationWsGenericCodeEngDescription, 40);
   }
 
};
