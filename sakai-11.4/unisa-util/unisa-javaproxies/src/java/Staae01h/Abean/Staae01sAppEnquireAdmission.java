package Staae01h.Abean;
 
import java.applet.*;
import java.awt.event.*;
import java.io.*;
import java.beans.*;
import java.util.*;
import java.net.URL;
import java.math.*;
import java.text.*;

import Staae01h.STAAE01S_IA;
import Staae01h.STAAE01S_OA;

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
public class Staae01sAppEnquireAdmission  implements ActionListener, java.io.Serializable  {
 
   //  Use final String for the bean name
   public static final String BEANNAME = new String("Staae01sAppEnquireAdmission");
 
   //  Constants for Asynchronous status
   public static final int PENDING = CoopFlow.DATA_NOT_READY;
   public static final int AVAILABLE = CoopFlow.DATA_READY;
   public static final int INVALID_ID = CoopFlow.INVALID_ID;
 
   private final SimpleDateFormat nativeTimestampFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
   private final SimpleDateFormat nativeTimeFormatter = new SimpleDateFormat("HHmmss");
   private final SimpleDateFormat nativeDateFormatter = new SimpleDateFormat("yyyyMMdd");
   public Staae01sAppEnquireAdmission() {
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
 
 
   private Staae01sAppEnquireAdmissionOperation oper = null;
 
   /**
    * Calls the transaction/procedure step on the server.
    *
    * @exception java.beans.PropertyVetoException
    * Final property checks can throw this exception.
    */
   public void execute()  throws PropertyVetoException {
      try  {
         if (oper == null) {
            oper = new Staae01sAppEnquireAdmissionOperation(this);
            addCompletionListener(new operListener(this));
            addExceptionListener(new operListener(this));
         }
 

 
         oper.doStaae01sAppEnquireAdmissionOperation();
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
      private Staae01sAppEnquireAdmission rP;
      operListener(Staae01sAppEnquireAdmission r)  {
         rP = r;
      }
      public void actionPerformed(ActionEvent aEvent)  {
         if (Trace.isTracing(Trace.MASK_APPLICATION))
         {
            Trace.record(Trace.MASK_APPLICATION, "Staae01sAppEnquireAdmission", "Listener heard that Staae01sAppEnquireAdmissionOperation completed with " + 
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of completion of operation Staae01sAppEnquireAdmission ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      synchronized (this)  {
         targets = (Vector) completionListeners.clone();
      }
      actionEvt = new ActionEvent(this, 0, "Completion.Staae01sAppEnquireAdmission");
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of exception of operation Staae01sAppEnquireAdmission ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      String failCommand = "Exception.Staae01sAppEnquireAdmission";
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
      importView.InWsQualificationType = FixedStringAttr.valueOf("G", 1);
      importView.InWsMatricRecordCategory = ShortAttr.valueOf((short)6);
      importView.InWsMatricRecordAuditedFlag = FixedStringAttr.valueOf("N", 1);
      importView.InGroup_MA = IntAttr.valueOf(InGroupMax);
      for( int index = 0; index < 2000; index++)
         importView.InGWsQualificationType[index] = FixedStringAttr.valueOf("G", 1);
      importView.InGroupMatric_MA = IntAttr.valueOf(InGroupMatricMax);
      for( int index = 0; index < 15; index++)
         importView.InGmWsMatricSubjectResultGrade[index] = FixedStringAttr.valueOf("H", 1);
      exportView.OutWsQualificationType = FixedStringAttr.valueOf("G", 1);
      exportView.OutWsMatricRecordCategory = ShortAttr.valueOf((short)6);
      exportView.OutWsMatricRecordAuditedFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutGroup_MA = IntAttr.getDefaultValue();
      for( int index = 0; index < 2000; index++)
         exportView.OutGWsQualificationType[index] = FixedStringAttr.valueOf("G", 1);
      exportView.OutScreenGroup_MA = IntAttr.getDefaultValue();
      exportView.OutGroupMatric_MA = IntAttr.getDefaultValue();
      for( int index = 0; index < 15; index++)
         exportView.OutGmWsMatricSubjectResultGrade[index] = FixedStringAttr.valueOf("H", 1);
   }

   Staae01h.STAAE01S_IA importView = Staae01h.STAAE01S_IA.getInstance();
   Staae01h.STAAE01S_OA exportView = Staae01h.STAAE01S_OA.getInstance();
   public short getInWsAcademicYearYear() {
      return importView.InWsAcademicYearYear;
   }
   public void setInWsAcademicYearYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InWsAcademicYearYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InWsAcademicYearYear", null, null));
      }
      importView.InWsAcademicYearYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsAcademicYearYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsAcademicYearYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsAcademicYearYear", null, null));
      }
      try {
          setInWsAcademicYearYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsAcademicYearYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsAcademicYearYear", null, null));
      }
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
 
   public String getInWsQualificationType() {
      return FixedStringAttr.valueOf(importView.InWsQualificationType, 1);
   }
   public void setInWsQualificationType(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Staae01sAppEnquireAdmissionWsQualificationTypePropertyEditor pe = new Staae01sAppEnquireAdmissionWsQualificationTypePropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InWsQualificationType value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsQualificationType", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsQualificationType must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsQualificationType", null, null));
      }
      importView.InWsQualificationType = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsQualificationLongEngDescription() {
      return FixedStringAttr.valueOf(importView.InWsQualificationLongEngDescription, 120);
   }
   public void setInWsQualificationLongEngDescription(String s)
      throws PropertyVetoException {
      if (s.length() > 120) {
         throw new PropertyVetoException("InWsQualificationLongEngDescription must be <= 120 characters.",
               new PropertyChangeEvent (this, "InWsQualificationLongEngDescription", null, null));
      }
      importView.InWsQualificationLongEngDescription = FixedStringAttr.valueOf(s, (short)120);
   }
 
   public String getInWsQualificationSpecialityTypeMkQualificationCode() {
      return FixedStringAttr.valueOf(importView.InWsQualificationSpecialityTypeMkQualificationCode, 5);
   }
   public void setInWsQualificationSpecialityTypeMkQualificationCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InWsQualificationSpecialityTypeMkQualificationCode must be <= 5 characters.",
               new PropertyChangeEvent (this, "InWsQualificationSpecialityTypeMkQualificationCode", null, null));
      }
      importView.InWsQualificationSpecialityTypeMkQualificationCode = FixedStringAttr.valueOf(s, (short)5);
   }
 
   public String getInWsQualificationSpecialityTypeSpecialityCode() {
      return FixedStringAttr.valueOf(importView.InWsQualificationSpecialityTypeSpecialityCode, 3);
   }
   public void setInWsQualificationSpecialityTypeSpecialityCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 3) {
         throw new PropertyVetoException("InWsQualificationSpecialityTypeSpecialityCode must be <= 3 characters.",
               new PropertyChangeEvent (this, "InWsQualificationSpecialityTypeSpecialityCode", null, null));
      }
      importView.InWsQualificationSpecialityTypeSpecialityCode = FixedStringAttr.valueOf(s, (short)3);
   }
 
   public int getInWsStudentNr() {
      return importView.InWsStudentNr;
   }
   public void setInWsStudentNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InWsStudentNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InWsStudentNr", null, null));
      }
      importView.InWsStudentNr = IntAttr.valueOf(s);
   }
   public void setAsStringInWsStudentNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentNr", null, null));
      }
      try {
          setInWsStudentNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentNr", null, null));
      }
   }
 
   public String getInWsStudentMkTitle() {
      return FixedStringAttr.valueOf(importView.InWsStudentMkTitle, 10);
   }
   public void setInWsStudentMkTitle(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InWsStudentMkTitle must be <= 10 characters.",
               new PropertyChangeEvent (this, "InWsStudentMkTitle", null, null));
      }
      importView.InWsStudentMkTitle = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInWsStudentSurname() {
      return FixedStringAttr.valueOf(importView.InWsStudentSurname, 28);
   }
   public void setInWsStudentSurname(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsStudentSurname must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsStudentSurname", null, null));
      }
      importView.InWsStudentSurname = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInWsStudentInitials() {
      return FixedStringAttr.valueOf(importView.InWsStudentInitials, 10);
   }
   public void setInWsStudentInitials(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InWsStudentInitials must be <= 10 characters.",
               new PropertyChangeEvent (this, "InWsStudentInitials", null, null));
      }
      importView.InWsStudentInitials = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInWsStudentIdentityNr() {
      return FixedStringAttr.valueOf(importView.InWsStudentIdentityNr, 13);
   }
   public void setInWsStudentIdentityNr(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 13) {
         throw new PropertyVetoException("InWsStudentIdentityNr must be <= 13 characters.",
               new PropertyChangeEvent (this, "InWsStudentIdentityNr", null, null));
      }
      importView.InWsStudentIdentityNr = FixedStringAttr.valueOf(s, (short)13);
   }
 
   public Calendar getInWsStudentBirthDate() {
      return DateAttr.toCalendar(importView.InWsStudentBirthDate);
   }
   public int getAsIntInWsStudentBirthDate() {
      return DateAttr.toInt(importView.InWsStudentBirthDate);
   }
   public void setInWsStudentBirthDate(Calendar s)
      throws PropertyVetoException {
      importView.InWsStudentBirthDate = DateAttr.valueOf(s);
   }
   public void setAsStringInWsStudentBirthDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInWsStudentBirthDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInWsStudentBirthDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InWsStudentBirthDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InWsStudentBirthDate", null, null));
         }
      }
   }
   public void setAsIntInWsStudentBirthDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInWsStudentBirthDate(temp);
   }
 
   public String getInWsStudentMkNationality() {
      return FixedStringAttr.valueOf(importView.InWsStudentMkNationality, 4);
   }
   public void setInWsStudentMkNationality(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 4) {
         throw new PropertyVetoException("InWsStudentMkNationality must be <= 4 characters.",
               new PropertyChangeEvent (this, "InWsStudentMkNationality", null, null));
      }
      importView.InWsStudentMkNationality = FixedStringAttr.valueOf(s, (short)4);
   }
 
   public short getInWsStudentMkLastAcademicYear() {
      return importView.InWsStudentMkLastAcademicYear;
   }
   public void setInWsStudentMkLastAcademicYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InWsStudentMkLastAcademicYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InWsStudentMkLastAcademicYear", null, null));
      }
      importView.InWsStudentMkLastAcademicYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsStudentMkLastAcademicYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudentMkLastAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentMkLastAcademicYear", null, null));
      }
      try {
          setInWsStudentMkLastAcademicYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudentMkLastAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentMkLastAcademicYear", null, null));
      }
   }
 
   public String getInWsStudentMkCountryCode() {
      return FixedStringAttr.valueOf(importView.InWsStudentMkCountryCode, 4);
   }
   public void setInWsStudentMkCountryCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 4) {
         throw new PropertyVetoException("InWsStudentMkCountryCode must be <= 4 characters.",
               new PropertyChangeEvent (this, "InWsStudentMkCountryCode", null, null));
      }
      importView.InWsStudentMkCountryCode = FixedStringAttr.valueOf(s, (short)4);
   }
 
   public String getInWsMatricStatusCode() {
      return FixedStringAttr.valueOf(importView.InWsMatricStatusCode, 4);
   }
   public void setInWsMatricStatusCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 4) {
         throw new PropertyVetoException("InWsMatricStatusCode must be <= 4 characters.",
               new PropertyChangeEvent (this, "InWsMatricStatusCode", null, null));
      }
      importView.InWsMatricStatusCode = FixedStringAttr.valueOf(s, (short)4);
   }
 
   public String getInWsMatricCertificateCode() {
      return FixedStringAttr.valueOf(importView.InWsMatricCertificateCode, 3);
   }
   public void setInWsMatricCertificateCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 3) {
         throw new PropertyVetoException("InWsMatricCertificateCode must be <= 3 characters.",
               new PropertyChangeEvent (this, "InWsMatricCertificateCode", null, null));
      }
      importView.InWsMatricCertificateCode = FixedStringAttr.valueOf(s, (short)3);
   }
 
   public short getInWsMatricRecordCategory() {
      return importView.InWsMatricRecordCategory;
   }
   public void setInWsMatricRecordCategory(short s)
      throws PropertyVetoException {
      Staae01sAppEnquireAdmissionWsMatricRecordCategoryPropertyEditor pe = new Staae01sAppEnquireAdmissionWsMatricRecordCategoryPropertyEditor();
      if (pe.checkTag(String.valueOf(s)) == false) {
         throw new PropertyVetoException("InWsMatricRecordCategory value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsMatricRecordCategory", null, null));
      }
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InWsMatricRecordCategory has more than 2 digits.",
               new PropertyChangeEvent (this, "InWsMatricRecordCategory", null, null));
      }
      importView.InWsMatricRecordCategory = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsMatricRecordCategory(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsMatricRecordCategory is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsMatricRecordCategory", null, null));
      }
      try {
          setInWsMatricRecordCategory(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsMatricRecordCategory is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsMatricRecordCategory", null, null));
      }
   }
 
   public Calendar getInWsMatricRecordFullExemptionDate() {
      return DateAttr.toCalendar(importView.InWsMatricRecordFullExemptionDate);
   }
   public int getAsIntInWsMatricRecordFullExemptionDate() {
      return DateAttr.toInt(importView.InWsMatricRecordFullExemptionDate);
   }
   public void setInWsMatricRecordFullExemptionDate(Calendar s)
      throws PropertyVetoException {
      importView.InWsMatricRecordFullExemptionDate = DateAttr.valueOf(s);
   }
   public void setAsStringInWsMatricRecordFullExemptionDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInWsMatricRecordFullExemptionDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInWsMatricRecordFullExemptionDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InWsMatricRecordFullExemptionDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InWsMatricRecordFullExemptionDate", null, null));
         }
      }
   }
   public void setAsIntInWsMatricRecordFullExemptionDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInWsMatricRecordFullExemptionDate(temp);
   }
 
   public Calendar getInWsMatricRecordExemptionEffectiveDate() {
      return DateAttr.toCalendar(importView.InWsMatricRecordExemptionEffectiveDate);
   }
   public int getAsIntInWsMatricRecordExemptionEffectiveDate() {
      return DateAttr.toInt(importView.InWsMatricRecordExemptionEffectiveDate);
   }
   public void setInWsMatricRecordExemptionEffectiveDate(Calendar s)
      throws PropertyVetoException {
      importView.InWsMatricRecordExemptionEffectiveDate = DateAttr.valueOf(s);
   }
   public void setAsStringInWsMatricRecordExemptionEffectiveDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInWsMatricRecordExemptionEffectiveDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInWsMatricRecordExemptionEffectiveDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InWsMatricRecordExemptionEffectiveDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InWsMatricRecordExemptionEffectiveDate", null, null));
         }
      }
   }
   public void setAsIntInWsMatricRecordExemptionEffectiveDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInWsMatricRecordExemptionEffectiveDate(temp);
   }
 
   public Calendar getInWsMatricRecordExemptionExpiryDate() {
      return DateAttr.toCalendar(importView.InWsMatricRecordExemptionExpiryDate);
   }
   public int getAsIntInWsMatricRecordExemptionExpiryDate() {
      return DateAttr.toInt(importView.InWsMatricRecordExemptionExpiryDate);
   }
   public void setInWsMatricRecordExemptionExpiryDate(Calendar s)
      throws PropertyVetoException {
      importView.InWsMatricRecordExemptionExpiryDate = DateAttr.valueOf(s);
   }
   public void setAsStringInWsMatricRecordExemptionExpiryDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInWsMatricRecordExemptionExpiryDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInWsMatricRecordExemptionExpiryDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InWsMatricRecordExemptionExpiryDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InWsMatricRecordExemptionExpiryDate", null, null));
         }
      }
   }
   public void setAsIntInWsMatricRecordExemptionExpiryDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInWsMatricRecordExemptionExpiryDate(temp);
   }
 
   public String getInWsMatricRecordAuditedFlag() {
      return FixedStringAttr.valueOf(importView.InWsMatricRecordAuditedFlag, 1);
   }
   public void setInWsMatricRecordAuditedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Staae01sAppEnquireAdmissionWsMatricRecordAuditedFlagPropertyEditor pe = new Staae01sAppEnquireAdmissionWsMatricRecordAuditedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InWsMatricRecordAuditedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsMatricRecordAuditedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsMatricRecordAuditedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsMatricRecordAuditedFlag", null, null));
      }
      importView.InWsMatricRecordAuditedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public int getInWsMatricRecordMkUserCode() {
      return importView.InWsMatricRecordMkUserCode;
   }
   public void setInWsMatricRecordMkUserCode(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InWsMatricRecordMkUserCode has more than 5 digits.",
               new PropertyChangeEvent (this, "InWsMatricRecordMkUserCode", null, null));
      }
      importView.InWsMatricRecordMkUserCode = IntAttr.valueOf(s);
   }
   public void setAsStringInWsMatricRecordMkUserCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsMatricRecordMkUserCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsMatricRecordMkUserCode", null, null));
      }
      try {
          setInWsMatricRecordMkUserCode(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsMatricRecordMkUserCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsMatricRecordMkUserCode", null, null));
      }
   }
 
   public String getInWsMatricRecordSymbol() {
      return FixedStringAttr.valueOf(importView.InWsMatricRecordSymbol, 3);
   }
   public void setInWsMatricRecordSymbol(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 3) {
         throw new PropertyVetoException("InWsMatricRecordSymbol must be <= 3 characters.",
               new PropertyChangeEvent (this, "InWsMatricRecordSymbol", null, null));
      }
      importView.InWsMatricRecordSymbol = FixedStringAttr.valueOf(s, (short)3);
   }
 
   public String getInWsMatricRecordExamNumber() {
      return FixedStringAttr.valueOf(importView.InWsMatricRecordExamNumber, 13);
   }
   public void setInWsMatricRecordExamNumber(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 13) {
         throw new PropertyVetoException("InWsMatricRecordExamNumber must be <= 13 characters.",
               new PropertyChangeEvent (this, "InWsMatricRecordExamNumber", null, null));
      }
      importView.InWsMatricRecordExamNumber = FixedStringAttr.valueOf(s, (short)13);
   }
 
   public short getInWsMatricRecordAggregate() {
      return importView.InWsMatricRecordAggregate;
   }
   public void setInWsMatricRecordAggregate(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InWsMatricRecordAggregate has more than 4 digits.",
               new PropertyChangeEvent (this, "InWsMatricRecordAggregate", null, null));
      }
      importView.InWsMatricRecordAggregate = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsMatricRecordAggregate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsMatricRecordAggregate is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsMatricRecordAggregate", null, null));
      }
      try {
          setInWsMatricRecordAggregate(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsMatricRecordAggregate is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsMatricRecordAggregate", null, null));
      }
   }
 
   public Calendar getInWsMatricRecordOrigExemptDate() {
      return DateAttr.toCalendar(importView.InWsMatricRecordOrigExemptDate);
   }
   public int getAsIntInWsMatricRecordOrigExemptDate() {
      return DateAttr.toInt(importView.InWsMatricRecordOrigExemptDate);
   }
   public void setInWsMatricRecordOrigExemptDate(Calendar s)
      throws PropertyVetoException {
      importView.InWsMatricRecordOrigExemptDate = DateAttr.valueOf(s);
   }
   public void setAsStringInWsMatricRecordOrigExemptDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInWsMatricRecordOrigExemptDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInWsMatricRecordOrigExemptDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InWsMatricRecordOrigExemptDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InWsMatricRecordOrigExemptDate", null, null));
         }
      }
   }
   public void setAsIntInWsMatricRecordOrigExemptDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInWsMatricRecordOrigExemptDate(temp);
   }
 
   public String getInWsMatricRecordSecEduComplete() {
      return FixedStringAttr.valueOf(importView.InWsMatricRecordSecEduComplete, 2);
   }
   public void setInWsMatricRecordSecEduComplete(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InWsMatricRecordSecEduComplete must be <= 2 characters.",
               new PropertyChangeEvent (this, "InWsMatricRecordSecEduComplete", null, null));
      }
      importView.InWsMatricRecordSecEduComplete = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public short getInWsMatricRecordMCount() {
      return importView.InWsMatricRecordMCount;
   }
   public void setInWsMatricRecordMCount(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InWsMatricRecordMCount has more than 2 digits.",
               new PropertyChangeEvent (this, "InWsMatricRecordMCount", null, null));
      }
      importView.InWsMatricRecordMCount = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsMatricRecordMCount(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsMatricRecordMCount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsMatricRecordMCount", null, null));
      }
      try {
          setInWsMatricRecordMCount(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsMatricRecordMCount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsMatricRecordMCount", null, null));
      }
   }
 
   public String getInWsMatricRecordComment0() {
      return FixedStringAttr.valueOf(importView.InWsMatricRecordComment0, 100);
   }
   public void setInWsMatricRecordComment0(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 100) {
         throw new PropertyVetoException("InWsMatricRecordComment0 must be <= 100 characters.",
               new PropertyChangeEvent (this, "InWsMatricRecordComment0", null, null));
      }
      importView.InWsMatricRecordComment0 = FixedStringAttr.valueOf(s, (short)100);
   }
 
   public String getInWsMatricRecordHcerCompleted() {
      return FixedStringAttr.valueOf(importView.InWsMatricRecordHcerCompleted, 1);
   }
   public void setInWsMatricRecordHcerCompleted(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsMatricRecordHcerCompleted must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsMatricRecordHcerCompleted", null, null));
      }
      importView.InWsMatricRecordHcerCompleted = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsMatricRecordDiplCompleted() {
      return FixedStringAttr.valueOf(importView.InWsMatricRecordDiplCompleted, 1);
   }
   public void setInWsMatricRecordDiplCompleted(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsMatricRecordDiplCompleted must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsMatricRecordDiplCompleted", null, null));
      }
      importView.InWsMatricRecordDiplCompleted = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsMatricRecordScienceFoundation() {
      return FixedStringAttr.valueOf(importView.InWsMatricRecordScienceFoundation, 1);
   }
   public void setInWsMatricRecordScienceFoundation(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsMatricRecordScienceFoundation must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsMatricRecordScienceFoundation", null, null));
      }
      importView.InWsMatricRecordScienceFoundation = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsMatricRecordAlternativeRoute() {
      return FixedStringAttr.valueOf(importView.InWsMatricRecordAlternativeRoute, 1);
   }
   public void setInWsMatricRecordAlternativeRoute(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsMatricRecordAlternativeRoute must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsMatricRecordAlternativeRoute", null, null));
      }
      importView.InWsMatricRecordAlternativeRoute = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsMatricRecordAltComment1() {
      return FixedStringAttr.valueOf(importView.InWsMatricRecordAltComment1, 80);
   }
   public void setInWsMatricRecordAltComment1(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 80) {
         throw new PropertyVetoException("InWsMatricRecordAltComment1 must be <= 80 characters.",
               new PropertyChangeEvent (this, "InWsMatricRecordAltComment1", null, null));
      }
      importView.InWsMatricRecordAltComment1 = FixedStringAttr.valueOf(s, (short)80);
   }
 
   public String getInWsMatricRecordAltComment2() {
      return FixedStringAttr.valueOf(importView.InWsMatricRecordAltComment2, 200);
   }
   public void setInWsMatricRecordAltComment2(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 200) {
         throw new PropertyVetoException("InWsMatricRecordAltComment2 must be <= 200 characters.",
               new PropertyChangeEvent (this, "InWsMatricRecordAltComment2", null, null));
      }
      importView.InWsMatricRecordAltComment2 = FixedStringAttr.valueOf(s, (short)200);
   }
 
   public short getInWsMatricRecordApsScore() {
      return importView.InWsMatricRecordApsScore;
   }
   public void setInWsMatricRecordApsScore(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InWsMatricRecordApsScore has more than 3 digits.",
               new PropertyChangeEvent (this, "InWsMatricRecordApsScore", null, null));
      }
      importView.InWsMatricRecordApsScore = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsMatricRecordApsScore(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsMatricRecordApsScore is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsMatricRecordApsScore", null, null));
      }
      try {
          setInWsMatricRecordApsScore(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsMatricRecordApsScore is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsMatricRecordApsScore", null, null));
      }
   }
 
   public short getInWsMatricRecordAcsScore() {
      return importView.InWsMatricRecordAcsScore;
   }
   public void setInWsMatricRecordAcsScore(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InWsMatricRecordAcsScore has more than 3 digits.",
               new PropertyChangeEvent (this, "InWsMatricRecordAcsScore", null, null));
      }
      importView.InWsMatricRecordAcsScore = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsMatricRecordAcsScore(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsMatricRecordAcsScore is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsMatricRecordAcsScore", null, null));
      }
      try {
          setInWsMatricRecordAcsScore(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsMatricRecordAcsScore is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsMatricRecordAcsScore", null, null));
      }
   }
 
   public final int InGroupMax = 2000;
   public short getInGroupCount() {
      return (short)(importView.InGroup_MA);
   };
 
   public void setInGroupCount(short s) throws PropertyVetoException {
      if (s < 0 || s > InGroupMax) {
         throw new PropertyVetoException("InGroupCount value is not a valid value. (0 to 2000)",
               new PropertyChangeEvent (this, "InGroupCount", null, null));
      } else {
         importView.InGroup_MA = IntAttr.valueOf((int)s);
      }
   }
 
   public String getInGCsfLineActionBarSelectionFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGCsfLineActionBarSelectionFlag[index], 1);
   }
   public void setInGCsfLineActionBarSelectionFlag(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGCsfLineActionBarSelectionFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGCsfLineActionBarSelectionFlag", null, null));
      }
      importView.InGCsfLineActionBarSelectionFlag[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInGCsfLineActionBarAction(int index) throws ArrayIndexOutOfBoundsException {
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGCsfLineActionBarAction[index], 1);
   }
   public void setInGCsfLineActionBarAction(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGCsfLineActionBarAction must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGCsfLineActionBarAction", null, null));
      }
      importView.InGCsfLineActionBarAction[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInGWsUnisaCollegeCode(int index) throws ArrayIndexOutOfBoundsException {
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      return importView.InGWsUnisaCollegeCode[index];
   }
   public void setInGWsUnisaCollegeCode(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InGWsUnisaCollegeCode has more than 3 digits.",
               new PropertyChangeEvent (this, "InGWsUnisaCollegeCode", null, null));
      }
      importView.InGWsUnisaCollegeCode[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGWsUnisaCollegeCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGWsUnisaCollegeCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGWsUnisaCollegeCode", null, null));
      }
      try {
          setInGWsUnisaCollegeCode(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGWsUnisaCollegeCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGWsUnisaCollegeCode", null, null));
      }
   }
 
   public String getInGWsUnisaCollegeAbbreviation(int index) throws ArrayIndexOutOfBoundsException {
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGWsUnisaCollegeAbbreviation[index], 10);
   }
   public void setInGWsUnisaCollegeAbbreviation(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InGWsUnisaCollegeAbbreviation must be <= 10 characters.",
               new PropertyChangeEvent (this, "InGWsUnisaCollegeAbbreviation", null, null));
      }
      importView.InGWsUnisaCollegeAbbreviation[index] = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInGWsQualificationCode(int index) throws ArrayIndexOutOfBoundsException {
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGWsQualificationCode[index], 5);
   }
   public void setInGWsQualificationCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InGWsQualificationCode must be <= 5 characters.",
               new PropertyChangeEvent (this, "InGWsQualificationCode", null, null));
      }
      importView.InGWsQualificationCode[index] = FixedStringAttr.valueOf(s, (short)5);
   }
 
   public String getInGWsQualificationType(int index) throws ArrayIndexOutOfBoundsException {
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGWsQualificationType[index], 1);
   }
   public void setInGWsQualificationType(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      Staae01sAppEnquireAdmissionWsQualificationTypePropertyEditor pe = new Staae01sAppEnquireAdmissionWsQualificationTypePropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InGWsQualificationType value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InGWsQualificationType", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGWsQualificationType must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGWsQualificationType", null, null));
      }
      importView.InGWsQualificationType[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInGWsQualificationLongEngDescription(int index) throws ArrayIndexOutOfBoundsException {
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGWsQualificationLongEngDescription[index], 120);
   }
   public void setInGWsQualificationLongEngDescription(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      if (s.length() > 120) {
         throw new PropertyVetoException("InGWsQualificationLongEngDescription must be <= 120 characters.",
               new PropertyChangeEvent (this, "InGWsQualificationLongEngDescription", null, null));
      }
      importView.InGWsQualificationLongEngDescription[index] = FixedStringAttr.valueOf(s, (short)120);
   }
 
   public short getInGWsQualificationCollegeCode(int index) throws ArrayIndexOutOfBoundsException {
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      return importView.InGWsQualificationCollegeCode[index];
   }
   public void setInGWsQualificationCollegeCode(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InGWsQualificationCollegeCode has more than 3 digits.",
               new PropertyChangeEvent (this, "InGWsQualificationCollegeCode", null, null));
      }
      importView.InGWsQualificationCollegeCode[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGWsQualificationCollegeCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGWsQualificationCollegeCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGWsQualificationCollegeCode", null, null));
      }
      try {
          setInGWsQualificationCollegeCode(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGWsQualificationCollegeCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGWsQualificationCollegeCode", null, null));
      }
   }
 
   public String getInGWsQualificationSpecialityTypeSpecialityCode(int index) throws ArrayIndexOutOfBoundsException {
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGWsQualificationSpecialityTypeSpecialityCode[index], 3);
   }
   public void setInGWsQualificationSpecialityTypeSpecialityCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      if (s.length() > 3) {
         throw new PropertyVetoException("InGWsQualificationSpecialityTypeSpecialityCode must be <= 3 characters.",
               new PropertyChangeEvent (this, "InGWsQualificationSpecialityTypeSpecialityCode", null, null));
      }
      importView.InGWsQualificationSpecialityTypeSpecialityCode[index] = FixedStringAttr.valueOf(s, (short)3);
   }
 
   public String getInGCsfStringsString2(int index) throws ArrayIndexOutOfBoundsException {
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGCsfStringsString2[index], 2);
   }
   public void setInGCsfStringsString2(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InGCsfStringsString2 must be <= 2 characters.",
               new PropertyChangeEvent (this, "InGCsfStringsString2", null, null));
      }
      importView.InGCsfStringsString2[index] = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public String getInGCsfStringsString3(int index) throws ArrayIndexOutOfBoundsException {
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGCsfStringsString3[index], 3);
   }
   public void setInGCsfStringsString3(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      if (s.length() > 3) {
         throw new PropertyVetoException("InGCsfStringsString3 must be <= 3 characters.",
               new PropertyChangeEvent (this, "InGCsfStringsString3", null, null));
      }
      importView.InGCsfStringsString3[index] = FixedStringAttr.valueOf(s, (short)3);
   }
 
   public String getInGCsfStringsString5(int index) throws ArrayIndexOutOfBoundsException {
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGCsfStringsString5[index], 5);
   }
   public void setInGCsfStringsString5(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InGCsfStringsString5 must be <= 5 characters.",
               new PropertyChangeEvent (this, "InGCsfStringsString5", null, null));
      }
      importView.InGCsfStringsString5[index] = FixedStringAttr.valueOf(s, (short)5);
   }
 
   public String getInGCsfStringsString60(int index) throws ArrayIndexOutOfBoundsException {
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGCsfStringsString60[index], 60);
   }
   public void setInGCsfStringsString60(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      if (s.length() > 60) {
         throw new PropertyVetoException("InGCsfStringsString60 must be <= 60 characters.",
               new PropertyChangeEvent (this, "InGCsfStringsString60", null, null));
      }
      importView.InGCsfStringsString60[index] = FixedStringAttr.valueOf(s, (short)60);
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
 
   public final int InGroupMatricMax = 15;
   public short getInGroupMatricCount() {
      return (short)(importView.InGroupMatric_MA);
   };
 
   public void setInGroupMatricCount(short s) throws PropertyVetoException {
      if (s < 0 || s > InGroupMatricMax) {
         throw new PropertyVetoException("InGroupMatricCount value is not a valid value. (0 to 15)",
               new PropertyChangeEvent (this, "InGroupMatricCount", null, null));
      } else {
         importView.InGroupMatric_MA = IntAttr.valueOf((int)s);
      }
   }
 
   public String getInGmCsfLineActionBarSelectionFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (14 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 14, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGmCsfLineActionBarSelectionFlag[index], 1);
   }
   public void setInGmCsfLineActionBarSelectionFlag(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (14 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 14, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGmCsfLineActionBarSelectionFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGmCsfLineActionBarSelectionFlag", null, null));
      }
      importView.InGmCsfLineActionBarSelectionFlag[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInGmWsMatricSubjectCode(int index) throws ArrayIndexOutOfBoundsException {
      if (14 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 14, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGmWsMatricSubjectCode[index], 3);
   }
   public void setInGmWsMatricSubjectCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (14 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 14, not: " + index);
      }
      if (s.length() > 3) {
         throw new PropertyVetoException("InGmWsMatricSubjectCode must be <= 3 characters.",
               new PropertyChangeEvent (this, "InGmWsMatricSubjectCode", null, null));
      }
      importView.InGmWsMatricSubjectCode[index] = FixedStringAttr.valueOf(s, (short)3);
   }
 
   public String getInGmWsMatricSubjectEngDescription(int index) throws ArrayIndexOutOfBoundsException {
      if (14 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 14, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGmWsMatricSubjectEngDescription[index], 28);
   }
   public void setInGmWsMatricSubjectEngDescription(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (14 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 14, not: " + index);
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InGmWsMatricSubjectEngDescription must be <= 28 characters.",
               new PropertyChangeEvent (this, "InGmWsMatricSubjectEngDescription", null, null));
      }
      importView.InGmWsMatricSubjectEngDescription[index] = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInGmWsMatricSubjectResultGrade(int index) throws ArrayIndexOutOfBoundsException {
      if (14 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 14, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGmWsMatricSubjectResultGrade[index], 1);
   }
   public void setInGmWsMatricSubjectResultGrade(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (14 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 14, not: " + index);
      }
      Staae01sAppEnquireAdmissionWsMatricSubjectResultGradePropertyEditor pe = new Staae01sAppEnquireAdmissionWsMatricSubjectResultGradePropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InGmWsMatricSubjectResultGrade value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InGmWsMatricSubjectResultGrade", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGmWsMatricSubjectResultGrade must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGmWsMatricSubjectResultGrade", null, null));
      }
      importView.InGmWsMatricSubjectResultGrade[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInGmWsMatricSubjectResultMark(int index) throws ArrayIndexOutOfBoundsException {
      if (14 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 14, not: " + index);
      }
      return importView.InGmWsMatricSubjectResultMark[index];
   }
   public void setInGmWsMatricSubjectResultMark(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (14 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 14, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InGmWsMatricSubjectResultMark has more than 2 digits.",
               new PropertyChangeEvent (this, "InGmWsMatricSubjectResultMark", null, null));
      }
      importView.InGmWsMatricSubjectResultMark[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGmWsMatricSubjectResultMark(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (14 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 14, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGmWsMatricSubjectResultMark is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGmWsMatricSubjectResultMark", null, null));
      }
      try {
          setInGmWsMatricSubjectResultMark(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGmWsMatricSubjectResultMark is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGmWsMatricSubjectResultMark", null, null));
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
 
   public String getInProddevCsfStringsString4() {
      return FixedStringAttr.valueOf(importView.InProddevCsfStringsString4, 4);
   }
   public void setInProddevCsfStringsString4(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 4) {
         throw new PropertyVetoException("InProddevCsfStringsString4 must be <= 4 characters.",
               new PropertyChangeEvent (this, "InProddevCsfStringsString4", null, null));
      }
      importView.InProddevCsfStringsString4 = FixedStringAttr.valueOf(s, (short)4);
   }
 
   public String getInCsfStringsString500() {
      return StringAttr.valueOf(importView.InCsfStringsString500);
   }
   public void setInCsfStringsString500(String s)
      throws PropertyVetoException {
      if (s.length() > 500) {
         throw new PropertyVetoException("InCsfStringsString500 must be <= 500 characters.",
               new PropertyChangeEvent (this, "InCsfStringsString500", null, null));
      }
      importView.InCsfStringsString500 = StringAttr.valueOf(s, (short)500);
   }
 
   public short getOutWsAcademicYearYear() {
      return exportView.OutWsAcademicYearYear;
   }
 
   public String getOutWsQualificationCode() {
      return FixedStringAttr.valueOf(exportView.OutWsQualificationCode, 5);
   }
 
   public String getOutWsQualificationType() {
      return FixedStringAttr.valueOf(exportView.OutWsQualificationType, 1);
   }
 
   public String getOutWsQualificationLongEngDescription() {
      return FixedStringAttr.valueOf(exportView.OutWsQualificationLongEngDescription, 120);
   }
 
   public String getOutWsQualificationSpecialityTypeMkQualificationCode() {
      return FixedStringAttr.valueOf(exportView.OutWsQualificationSpecialityTypeMkQualificationCode, 5);
   }
 
   public String getOutWsQualificationSpecialityTypeSpecialityCode() {
      return FixedStringAttr.valueOf(exportView.OutWsQualificationSpecialityTypeSpecialityCode, 3);
   }
 
   public int getOutWsStudentNr() {
      return exportView.OutWsStudentNr;
   }
 
   public String getOutWsStudentMkTitle() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentMkTitle, 10);
   }
 
   public String getOutWsStudentSurname() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentSurname, 28);
   }
 
   public String getOutWsStudentInitials() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentInitials, 10);
   }
 
   public String getOutWsStudentIdentityNr() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentIdentityNr, 13);
   }
 
   public Calendar getOutWsStudentBirthDate() {
      return DateAttr.toCalendar(exportView.OutWsStudentBirthDate);
   }
   public int getAsIntOutWsStudentBirthDate() {
      return DateAttr.toInt(exportView.OutWsStudentBirthDate);
   }
 
   public String getOutWsStudentMkNationality() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentMkNationality, 4);
   }
 
   public short getOutWsStudentMkLastAcademicYear() {
      return exportView.OutWsStudentMkLastAcademicYear;
   }
 
   public String getOutWsStudentMkCountryCode() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentMkCountryCode, 4);
   }
 
   public String getOutWsMatricStatusCode() {
      return FixedStringAttr.valueOf(exportView.OutWsMatricStatusCode, 4);
   }
 
   public String getOutWsMatricCertificateCode() {
      return FixedStringAttr.valueOf(exportView.OutWsMatricCertificateCode, 3);
   }
 
   public short getOutWsMatricRecordCategory() {
      return exportView.OutWsMatricRecordCategory;
   }
 
   public Calendar getOutWsMatricRecordFullExemptionDate() {
      return DateAttr.toCalendar(exportView.OutWsMatricRecordFullExemptionDate);
   }
   public int getAsIntOutWsMatricRecordFullExemptionDate() {
      return DateAttr.toInt(exportView.OutWsMatricRecordFullExemptionDate);
   }
 
   public Calendar getOutWsMatricRecordExemptionEffectiveDate() {
      return DateAttr.toCalendar(exportView.OutWsMatricRecordExemptionEffectiveDate);
   }
   public int getAsIntOutWsMatricRecordExemptionEffectiveDate() {
      return DateAttr.toInt(exportView.OutWsMatricRecordExemptionEffectiveDate);
   }
 
   public Calendar getOutWsMatricRecordExemptionExpiryDate() {
      return DateAttr.toCalendar(exportView.OutWsMatricRecordExemptionExpiryDate);
   }
   public int getAsIntOutWsMatricRecordExemptionExpiryDate() {
      return DateAttr.toInt(exportView.OutWsMatricRecordExemptionExpiryDate);
   }
 
   public String getOutWsMatricRecordAuditedFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsMatricRecordAuditedFlag, 1);
   }
 
   public int getOutWsMatricRecordMkUserCode() {
      return exportView.OutWsMatricRecordMkUserCode;
   }
 
   public String getOutWsMatricRecordSymbol() {
      return FixedStringAttr.valueOf(exportView.OutWsMatricRecordSymbol, 3);
   }
 
   public String getOutWsMatricRecordExamNumber() {
      return FixedStringAttr.valueOf(exportView.OutWsMatricRecordExamNumber, 13);
   }
 
   public short getOutWsMatricRecordAggregate() {
      return exportView.OutWsMatricRecordAggregate;
   }
 
   public Calendar getOutWsMatricRecordOrigExemptDate() {
      return DateAttr.toCalendar(exportView.OutWsMatricRecordOrigExemptDate);
   }
   public int getAsIntOutWsMatricRecordOrigExemptDate() {
      return DateAttr.toInt(exportView.OutWsMatricRecordOrigExemptDate);
   }
 
   public String getOutWsMatricRecordSecEduComplete() {
      return FixedStringAttr.valueOf(exportView.OutWsMatricRecordSecEduComplete, 2);
   }
 
   public short getOutWsMatricRecordMCount() {
      return exportView.OutWsMatricRecordMCount;
   }
 
   public String getOutWsMatricRecordComment0() {
      return FixedStringAttr.valueOf(exportView.OutWsMatricRecordComment0, 100);
   }
 
   public String getOutWsMatricRecordHcerCompleted() {
      return FixedStringAttr.valueOf(exportView.OutWsMatricRecordHcerCompleted, 1);
   }
 
   public String getOutWsMatricRecordDiplCompleted() {
      return FixedStringAttr.valueOf(exportView.OutWsMatricRecordDiplCompleted, 1);
   }
 
   public String getOutWsMatricRecordScienceFoundation() {
      return FixedStringAttr.valueOf(exportView.OutWsMatricRecordScienceFoundation, 1);
   }
 
   public String getOutWsMatricRecordAlternativeRoute() {
      return FixedStringAttr.valueOf(exportView.OutWsMatricRecordAlternativeRoute, 1);
   }
 
   public String getOutWsMatricRecordAltComment1() {
      return FixedStringAttr.valueOf(exportView.OutWsMatricRecordAltComment1, 80);
   }
 
   public String getOutWsMatricRecordAltComment2() {
      return FixedStringAttr.valueOf(exportView.OutWsMatricRecordAltComment2, 200);
   }
 
   public short getOutWsMatricRecordApsScore() {
      return exportView.OutWsMatricRecordApsScore;
   }
 
   public short getOutWsMatricRecordAcsScore() {
      return exportView.OutWsMatricRecordAcsScore;
   }
 
   public final int OutGroupMax = 2000;
   public short getOutGroupCount() {
      return (short)(exportView.OutGroup_MA);
   };
 
   public String getOutGCsfLineActionBarSelectionFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGCsfLineActionBarSelectionFlag[index], 1);
   }
 
   public String getOutGCsfLineActionBarAction(int index) throws ArrayIndexOutOfBoundsException {
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGCsfLineActionBarAction[index], 1);
   }
 
   public short getOutGWsUnisaCollegeCode(int index) throws ArrayIndexOutOfBoundsException {
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      return exportView.OutGWsUnisaCollegeCode[index];
   }
 
   public String getOutGWsUnisaCollegeAbbreviation(int index) throws ArrayIndexOutOfBoundsException {
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsUnisaCollegeAbbreviation[index], 10);
   }
 
   public String getOutGWsQualificationCode(int index) throws ArrayIndexOutOfBoundsException {
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsQualificationCode[index], 5);
   }
 
   public String getOutGWsQualificationType(int index) throws ArrayIndexOutOfBoundsException {
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsQualificationType[index], 1);
   }
 
   public String getOutGWsQualificationLongEngDescription(int index) throws ArrayIndexOutOfBoundsException {
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsQualificationLongEngDescription[index], 120);
   }
 
   public short getOutGWsQualificationCollegeCode(int index) throws ArrayIndexOutOfBoundsException {
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      return exportView.OutGWsQualificationCollegeCode[index];
   }
 
   public String getOutGWsQualificationSpecialityTypeSpecialityCode(int index) throws ArrayIndexOutOfBoundsException {
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsQualificationSpecialityTypeSpecialityCode[index], 3);
   }
 
   public String getOutGCsfStringsString2(int index) throws ArrayIndexOutOfBoundsException {
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGCsfStringsString2[index], 2);
   }
 
   public String getOutGCsfStringsString3(int index) throws ArrayIndexOutOfBoundsException {
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGCsfStringsString3[index], 3);
   }
 
   public String getOutGCsfStringsString5(int index) throws ArrayIndexOutOfBoundsException {
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGCsfStringsString5[index], 5);
   }
 
   public String getOutGCsfStringsString60(int index) throws ArrayIndexOutOfBoundsException {
      if (1999 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 1999, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGCsfStringsString60[index], 60);
   }
 
   public String getOutWsPrinterCode() {
      return FixedStringAttr.valueOf(exportView.OutWsPrinterCode, 10);
   }
 
   public int getOutWsFunctionNumber() {
      return exportView.OutWsFunctionNumber;
   }
 
   public final int OutScreenGroupMax = 100;
   public short getOutScreenGroupCount() {
      return (short)(exportView.OutScreenGroup_MA);
   };
 
   public String getOutSgCsfLineActionBarSelectionFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutSgCsfLineActionBarSelectionFlag[index], 1);
   }
 
   public String getOutSgCsfStringsString60(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutSgCsfStringsString60[index], 60);
   }
 
   public final int OutGroupMatricMax = 15;
   public short getOutGroupMatricCount() {
      return (short)(exportView.OutGroupMatric_MA);
   };
 
   public String getOutGmCsfLineActionBarSelectionFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (14 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 14, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGmCsfLineActionBarSelectionFlag[index], 1);
   }
 
   public String getOutGmWsMatricSubjectCode(int index) throws ArrayIndexOutOfBoundsException {
      if (14 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 14, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGmWsMatricSubjectCode[index], 3);
   }
 
   public String getOutGmWsMatricSubjectEngDescription(int index) throws ArrayIndexOutOfBoundsException {
      if (14 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 14, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGmWsMatricSubjectEngDescription[index], 28);
   }
 
   public String getOutGmWsMatricSubjectResultGrade(int index) throws ArrayIndexOutOfBoundsException {
      if (14 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 14, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGmWsMatricSubjectResultGrade[index], 1);
   }
 
   public short getOutGmWsMatricSubjectResultMark(int index) throws ArrayIndexOutOfBoundsException {
      if (14 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 14, not: " + index);
      }
      return exportView.OutGmWsMatricSubjectResultMark[index];
   }
 
   public int getOutWsUserNumber() {
      return exportView.OutWsUserNumber;
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
 
   public String getOutProddevCsfStringsString4() {
      return FixedStringAttr.valueOf(exportView.OutProddevCsfStringsString4, 4);
   }
 
   public String getOutCsfStringsString500() {
      return StringAttr.valueOf(exportView.OutCsfStringsString500);
   }
 
};
