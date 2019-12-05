package Srces02h.Abean;
 
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
public class Srces02sGrantExemptions  implements ActionListener, java.io.Serializable  {
 
   //  Use final String for the bean name
   public static final String BEANNAME = new String("Srces02sGrantExemptions");
 
   //  Constants for Asynchronous status
   public static final int PENDING = CoopFlow.DATA_NOT_READY;
   public static final int AVAILABLE = CoopFlow.DATA_READY;
   public static final int INVALID_ID = CoopFlow.INVALID_ID;
 
   private final SimpleDateFormat nativeTimestampFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
   private final SimpleDateFormat nativeTimeFormatter = new SimpleDateFormat("HHmmss");
   private final SimpleDateFormat nativeDateFormatter = new SimpleDateFormat("yyyyMMdd");
   public Srces02sGrantExemptions() {
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
 
 
   private Srces02sGrantExemptionsOperation oper = null;
 
   /**
    * Calls the transaction/procedure step on the server.
    *
    * @exception java.beans.PropertyVetoException
    * Final property checks can throw this exception.
    */
   public void execute()  throws PropertyVetoException {
      try  {
         if (oper == null) {
            oper = new Srces02sGrantExemptionsOperation(this);
            addCompletionListener(new operListener(this));
            addExceptionListener(new operListener(this));
         }
 

 
         oper.doSrces02sGrantExemptionsOperation();
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
      private Srces02sGrantExemptions rP;
      operListener(Srces02sGrantExemptions r)  {
         rP = r;
      }
      public void actionPerformed(ActionEvent aEvent)  {
         if (Trace.isTracing(Trace.MASK_APPLICATION))
         {
            Trace.record(Trace.MASK_APPLICATION, "Srces02sGrantExemptions", "Listener heard that Srces02sGrantExemptionsOperation completed with " + 
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of completion of operation Srces02sGrantExemptions ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      synchronized (this)  {
         targets = (Vector) completionListeners.clone();
      }
      actionEvt = new ActionEvent(this, 0, "Completion.Srces02sGrantExemptions");
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of exception of operation Srces02sGrantExemptions ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      String failCommand = "Exception.Srces02sGrantExemptions";
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
      importView.InWsStudentNumberRestrictedFlag = FixedStringAttr.valueOf("N", 1);
      importView.InOtherGroup_MA = IntAttr.valueOf(InOtherGroupMax);
      importView.InGroup_MA = IntAttr.valueOf(InGroupMax);
      importView.InWsMatricRecordCategory = ShortAttr.valueOf((short)6);
      importView.InWsMatricRecordAuditedFlag = FixedStringAttr.valueOf("N", 1);
      importView.InGroupMatric_MA = IntAttr.valueOf(InGroupMatricMax);
      for( int index = 0; index < 50; index++)
         importView.InGWsMatricSubjectResultGrade[index] = FixedStringAttr.valueOf("H", 1);
      exportView.OutWsStudentNumberRestrictedFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutOtherGroup_MA = IntAttr.getDefaultValue();
      exportView.OutGroup_MA = IntAttr.getDefaultValue();
      exportView.OutWsMatricRecordCategory = ShortAttr.valueOf((short)6);
      exportView.OutWsMatricRecordAuditedFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutGroupMatric_MA = IntAttr.getDefaultValue();
      for( int index = 0; index < 50; index++)
         exportView.OutGWsMatricSubjectResultGrade[index] = FixedStringAttr.valueOf("H", 1);
   }

   Srces02h.SRCES02S_IA importView = Srces02h.SRCES02S_IA.getInstance();
   Srces02h.SRCES02S_OA exportView = Srces02h.SRCES02S_OA.getInstance();
   public String getInCanUpdateCsfStringsString1() {
      return FixedStringAttr.valueOf(importView.InCanUpdateCsfStringsString1, 1);
   }
   public void setInCanUpdateCsfStringsString1(String s)
      throws PropertyVetoException {
      if (s.length() > 1) {
         throw new PropertyVetoException("InCanUpdateCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InCanUpdateCsfStringsString1", null, null));
      }
      importView.InCanUpdateCsfStringsString1 = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInFaxOrEmailCsfStringsString1() {
      return FixedStringAttr.valueOf(importView.InFaxOrEmailCsfStringsString1, 1);
   }
   public void setInFaxOrEmailCsfStringsString1(String s)
      throws PropertyVetoException {
      if (s.length() > 1) {
         throw new PropertyVetoException("InFaxOrEmailCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InFaxOrEmailCsfStringsString1", null, null));
      }
      importView.InFaxOrEmailCsfStringsString1 = FixedStringAttr.valueOf(s, (short)1);
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
 
   public String getInWsStudentFirstNames() {
      return FixedStringAttr.valueOf(importView.InWsStudentFirstNames, 60);
   }
   public void setInWsStudentFirstNames(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 60) {
         throw new PropertyVetoException("InWsStudentFirstNames must be <= 60 characters.",
               new PropertyChangeEvent (this, "InWsStudentFirstNames", null, null));
      }
      importView.InWsStudentFirstNames = FixedStringAttr.valueOf(s, (short)60);
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
 
   public String getInWsStudentNumberRestrictedFlag() {
      return FixedStringAttr.valueOf(importView.InWsStudentNumberRestrictedFlag, 1);
   }
   public void setInWsStudentNumberRestrictedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srces02sGrantExemptionsWsStudentNumberRestrictedFlagPropertyEditor pe = new Srces02sGrantExemptionsWsStudentNumberRestrictedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InWsStudentNumberRestrictedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsStudentNumberRestrictedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentNumberRestrictedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentNumberRestrictedFlag", null, null));
      }
      importView.InWsStudentNumberRestrictedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInStudentNameCsfStringsString100() {
      return FixedStringAttr.valueOf(importView.InStudentNameCsfStringsString100, 100);
   }
   public void setInStudentNameCsfStringsString100(String s)
      throws PropertyVetoException {
      if (s.length() > 100) {
         throw new PropertyVetoException("InStudentNameCsfStringsString100 must be <= 100 characters.",
               new PropertyChangeEvent (this, "InStudentNameCsfStringsString100", null, null));
      }
      importView.InStudentNameCsfStringsString100 = FixedStringAttr.valueOf(s, (short)100);
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
 
   public String getInWsQualificationShortDescription() {
      return FixedStringAttr.valueOf(importView.InWsQualificationShortDescription, 12);
   }
   public void setInWsQualificationShortDescription(String s)
      throws PropertyVetoException {
      if (s.length() > 12) {
         throw new PropertyVetoException("InWsQualificationShortDescription must be <= 12 characters.",
               new PropertyChangeEvent (this, "InWsQualificationShortDescription", null, null));
      }
      importView.InWsQualificationShortDescription = FixedStringAttr.valueOf(s, (short)12);
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
 
   public short getInSecurityWsUserNumberOfLogonAttempts() {
      return importView.InSecurityWsUserNumberOfLogonAttempts;
   }
   public void setInSecurityWsUserNumberOfLogonAttempts(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InSecurityWsUserNumberOfLogonAttempts has more than 1 digits.",
               new PropertyChangeEvent (this, "InSecurityWsUserNumberOfLogonAttempts", null, null));
      }
      importView.InSecurityWsUserNumberOfLogonAttempts = ShortAttr.valueOf(s);
   }
   public void setAsStringInSecurityWsUserNumberOfLogonAttempts(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSecurityWsUserNumberOfLogonAttempts is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSecurityWsUserNumberOfLogonAttempts", null, null));
      }
      try {
          setInSecurityWsUserNumberOfLogonAttempts(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSecurityWsUserNumberOfLogonAttempts is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSecurityWsUserNumberOfLogonAttempts", null, null));
      }
   }
 
   public String getInSecurityWsUserLoggedOnFlag() {
      return FixedStringAttr.valueOf(importView.InSecurityWsUserLoggedOnFlag, 1);
   }
   public void setInSecurityWsUserLoggedOnFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InSecurityWsUserLoggedOnFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InSecurityWsUserLoggedOnFlag", null, null));
      }
      importView.InSecurityWsUserLoggedOnFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInSecurityWsUserInUsedFlag() {
      return FixedStringAttr.valueOf(importView.InSecurityWsUserInUsedFlag, 1);
   }
   public void setInSecurityWsUserInUsedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InSecurityWsUserInUsedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InSecurityWsUserInUsedFlag", null, null));
      }
      importView.InSecurityWsUserInUsedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public Calendar getInSecurityWsUserLastLogonDate() {
      return DateAttr.toCalendar(importView.InSecurityWsUserLastLogonDate);
   }
   public int getAsIntInSecurityWsUserLastLogonDate() {
      return DateAttr.toInt(importView.InSecurityWsUserLastLogonDate);
   }
   public void setInSecurityWsUserLastLogonDate(Calendar s)
      throws PropertyVetoException {
      importView.InSecurityWsUserLastLogonDate = DateAttr.valueOf(s);
   }
   public void setAsStringInSecurityWsUserLastLogonDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInSecurityWsUserLastLogonDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInSecurityWsUserLastLogonDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InSecurityWsUserLastLogonDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InSecurityWsUserLastLogonDate", null, null));
         }
      }
   }
   public void setAsIntInSecurityWsUserLastLogonDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInSecurityWsUserLastLogonDate(temp);
   }
 
   public String getInSecurityWsUserName() {
      return FixedStringAttr.valueOf(importView.InSecurityWsUserName, 28);
   }
   public void setInSecurityWsUserName(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InSecurityWsUserName must be <= 28 characters.",
               new PropertyChangeEvent (this, "InSecurityWsUserName", null, null));
      }
      importView.InSecurityWsUserName = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInSecurityWsUserPersonnelNo() {
      return FixedStringAttr.valueOf(importView.InSecurityWsUserPersonnelNo, 10);
   }
   public void setInSecurityWsUserPersonnelNo(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InSecurityWsUserPersonnelNo must be <= 10 characters.",
               new PropertyChangeEvent (this, "InSecurityWsUserPersonnelNo", null, null));
      }
      importView.InSecurityWsUserPersonnelNo = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInSecurityWsUserPhoneNumber() {
      return FixedStringAttr.valueOf(importView.InSecurityWsUserPhoneNumber, 20);
   }
   public void setInSecurityWsUserPhoneNumber(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 20) {
         throw new PropertyVetoException("InSecurityWsUserPhoneNumber must be <= 20 characters.",
               new PropertyChangeEvent (this, "InSecurityWsUserPhoneNumber", null, null));
      }
      importView.InSecurityWsUserPhoneNumber = FixedStringAttr.valueOf(s, (short)20);
   }
 
   public String getInSecurityWsUserPassword() {
      return FixedStringAttr.valueOf(importView.InSecurityWsUserPassword, 12);
   }
   public void setInSecurityWsUserPassword(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 12) {
         throw new PropertyVetoException("InSecurityWsUserPassword must be <= 12 characters.",
               new PropertyChangeEvent (this, "InSecurityWsUserPassword", null, null));
      }
      importView.InSecurityWsUserPassword = FixedStringAttr.valueOf(s, (short)12);
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
 
   public final int InOtherGroupMax = 90;
   public short getInOtherGroupCount() {
      return (short)(importView.InOtherGroup_MA);
   };
 
   public void setInOtherGroupCount(short s) throws PropertyVetoException {
      if (s < 0 || s > InOtherGroupMax) {
         throw new PropertyVetoException("InOtherGroupCount value is not a valid value. (0 to 90)",
               new PropertyChangeEvent (this, "InOtherGroupCount", null, null));
      } else {
         importView.InOtherGroup_MA = IntAttr.valueOf((int)s);
      }
   }
 
   public String getInGroCsfLineActionBarAction(int index) throws ArrayIndexOutOfBoundsException {
      if (89 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 89, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGroCsfLineActionBarAction[index], 1);
   }
   public void setInGroCsfLineActionBarAction(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (89 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 89, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGroCsfLineActionBarAction must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGroCsfLineActionBarAction", null, null));
      }
      importView.InGroCsfLineActionBarAction[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInGroCsfLineActionBarSelectionFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (89 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 89, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGroCsfLineActionBarSelectionFlag[index], 1);
   }
   public void setInGroCsfLineActionBarSelectionFlag(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (89 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 89, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGroCsfLineActionBarSelectionFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGroCsfLineActionBarSelectionFlag", null, null));
      }
      importView.InGroCsfLineActionBarSelectionFlag[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInGroCsfLineActionBarTranslatedAction(int index) throws ArrayIndexOutOfBoundsException {
      if (89 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 89, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGroCsfLineActionBarTranslatedAction[index], 1);
   }
   public void setInGroCsfLineActionBarTranslatedAction(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (89 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 89, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGroCsfLineActionBarTranslatedAction must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGroCsfLineActionBarTranslatedAction", null, null));
      }
      importView.InGroCsfLineActionBarTranslatedAction[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInGroStudentAcadrecOtherMkEducationalInst(int index) throws ArrayIndexOutOfBoundsException {
      if (89 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 89, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGroStudentAcadrecOtherMkEducationalInst[index], 4);
   }
   public void setInGroStudentAcadrecOtherMkEducationalInst(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (89 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 89, not: " + index);
      }
      if (s.length() > 4) {
         throw new PropertyVetoException("InGroStudentAcadrecOtherMkEducationalInst must be <= 4 characters.",
               new PropertyChangeEvent (this, "InGroStudentAcadrecOtherMkEducationalInst", null, null));
      }
      importView.InGroStudentAcadrecOtherMkEducationalInst[index] = FixedStringAttr.valueOf(s, (short)4);
   }
 
   public String getInGroStudentAcadrecOtherStudyUnitCode(int index) throws ArrayIndexOutOfBoundsException {
      if (89 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 89, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGroStudentAcadrecOtherStudyUnitCode[index], 12);
   }
   public void setInGroStudentAcadrecOtherStudyUnitCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (89 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 89, not: " + index);
      }
      if (s.length() > 12) {
         throw new PropertyVetoException("InGroStudentAcadrecOtherStudyUnitCode must be <= 12 characters.",
               new PropertyChangeEvent (this, "InGroStudentAcadrecOtherStudyUnitCode", null, null));
      }
      importView.InGroStudentAcadrecOtherStudyUnitCode[index] = FixedStringAttr.valueOf(s, (short)12);
   }
 
   public short getInGroStudentAcadrecOtherAcademicYear(int index) throws ArrayIndexOutOfBoundsException {
      if (89 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 89, not: " + index);
      }
      return importView.InGroStudentAcadrecOtherAcademicYear[index];
   }
   public void setInGroStudentAcadrecOtherAcademicYear(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (89 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 89, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InGroStudentAcadrecOtherAcademicYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InGroStudentAcadrecOtherAcademicYear", null, null));
      }
      importView.InGroStudentAcadrecOtherAcademicYear[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGroStudentAcadrecOtherAcademicYear(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (89 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 89, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGroStudentAcadrecOtherAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGroStudentAcadrecOtherAcademicYear", null, null));
      }
      try {
          setInGroStudentAcadrecOtherAcademicYear(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGroStudentAcadrecOtherAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGroStudentAcadrecOtherAcademicYear", null, null));
      }
   }
 
   public short getInGroStudentAcadrecOtherMark(int index) throws ArrayIndexOutOfBoundsException {
      if (89 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 89, not: " + index);
      }
      return importView.InGroStudentAcadrecOtherMark[index];
   }
   public void setInGroStudentAcadrecOtherMark(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (89 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 89, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InGroStudentAcadrecOtherMark has more than 3 digits.",
               new PropertyChangeEvent (this, "InGroStudentAcadrecOtherMark", null, null));
      }
      importView.InGroStudentAcadrecOtherMark[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGroStudentAcadrecOtherMark(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (89 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 89, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGroStudentAcadrecOtherMark is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGroStudentAcadrecOtherMark", null, null));
      }
      try {
          setInGroStudentAcadrecOtherMark(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGroStudentAcadrecOtherMark is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGroStudentAcadrecOtherMark", null, null));
      }
   }
 
   public String getInGroWsEducationalInstitutionEngName(int index) throws ArrayIndexOutOfBoundsException {
      if (89 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 89, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGroWsEducationalInstitutionEngName[index], 28);
   }
   public void setInGroWsEducationalInstitutionEngName(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (89 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 89, not: " + index);
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InGroWsEducationalInstitutionEngName must be <= 28 characters.",
               new PropertyChangeEvent (this, "InGroWsEducationalInstitutionEngName", null, null));
      }
      importView.InGroWsEducationalInstitutionEngName[index] = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public final int InGroupMax = 70;
   public short getInGroupCount() {
      return (short)(importView.InGroup_MA);
   };
 
   public void setInGroupCount(short s) throws PropertyVetoException {
      if (s < 0 || s > InGroupMax) {
         throw new PropertyVetoException("InGroupCount value is not a valid value. (0 to 70)",
               new PropertyChangeEvent (this, "InGroupCount", null, null));
      } else {
         importView.InGroup_MA = IntAttr.valueOf((int)s);
      }
   }
 
   public String getInGrCsfLineActionBarAction(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGrCsfLineActionBarAction[index], 1);
   }
   public void setInGrCsfLineActionBarAction(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGrCsfLineActionBarAction must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGrCsfLineActionBarAction", null, null));
      }
      importView.InGrCsfLineActionBarAction[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInGrCsfLineActionBarSelectionFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGrCsfLineActionBarSelectionFlag[index], 1);
   }
   public void setInGrCsfLineActionBarSelectionFlag(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGrCsfLineActionBarSelectionFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGrCsfLineActionBarSelectionFlag", null, null));
      }
      importView.InGrCsfLineActionBarSelectionFlag[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInGrCsfLineActionBarTranslatedAction(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGrCsfLineActionBarTranslatedAction[index], 1);
   }
   public void setInGrCsfLineActionBarTranslatedAction(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGrCsfLineActionBarTranslatedAction must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGrCsfLineActionBarTranslatedAction", null, null));
      }
      importView.InGrCsfLineActionBarTranslatedAction[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInGrUnisaWsStudyUnitCode(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGrUnisaWsStudyUnitCode[index], 7);
   }
   public void setInGrUnisaWsStudyUnitCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InGrUnisaWsStudyUnitCode must be <= 7 characters.",
               new PropertyChangeEvent (this, "InGrUnisaWsStudyUnitCode", null, null));
      }
      importView.InGrUnisaWsStudyUnitCode[index] = FixedStringAttr.valueOf(s, (short)7);
   }
 
   public String getInGrCsfStringsString1(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGrCsfStringsString1[index], 1);
   }
   public void setInGrCsfStringsString1(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGrCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGrCsfStringsString1", null, null));
      }
      importView.InGrCsfStringsString1[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInGrCsfStringsString20(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGrCsfStringsString20[index], 20);
   }
   public void setInGrCsfStringsString20(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      if (s.length() > 20) {
         throw new PropertyVetoException("InGrCsfStringsString20 must be <= 20 characters.",
               new PropertyChangeEvent (this, "InGrCsfStringsString20", null, null));
      }
      importView.InGrCsfStringsString20[index] = FixedStringAttr.valueOf(s, (short)20);
   }
 
   public String getInGrPreviousExemptionCsfStringsString1(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGrPreviousExemptionCsfStringsString1[index], 1);
   }
   public void setInGrPreviousExemptionCsfStringsString1(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGrPreviousExemptionCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGrPreviousExemptionCsfStringsString1", null, null));
      }
      importView.InGrPreviousExemptionCsfStringsString1[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public int getInGrValidCombinationIefSuppliedCount(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return importView.InGrValidCombinationIefSuppliedCount[index];
   }
   public void setInGrValidCombinationIefSuppliedCount(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 1000000000.0) {
         throw new PropertyVetoException("InGrValidCombinationIefSuppliedCount has more than 9 digits.",
               new PropertyChangeEvent (this, "InGrValidCombinationIefSuppliedCount", null, null));
      }
      importView.InGrValidCombinationIefSuppliedCount[index] = IntAttr.valueOf(s);
   }
   public void setAsStringInGrValidCombinationIefSuppliedCount(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGrValidCombinationIefSuppliedCount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGrValidCombinationIefSuppliedCount", null, null));
      }
      try {
          setInGrValidCombinationIefSuppliedCount(index, Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGrValidCombinationIefSuppliedCount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGrValidCombinationIefSuppliedCount", null, null));
      }
   }
 
   public String getInGrWsCombinationDescriptionDescription(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return StringAttr.valueOf(importView.InGrWsCombinationDescriptionDescription[index]);
   }
   public void setInGrWsCombinationDescriptionDescription(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      if (s.length() > 105) {
         throw new PropertyVetoException("InGrWsCombinationDescriptionDescription must be <= 105 characters.",
               new PropertyChangeEvent (this, "InGrWsCombinationDescriptionDescription", null, null));
      }
      importView.InGrWsCombinationDescriptionDescription[index] = StringAttr.valueOf(s, (short)105);
   }
 
   public String getInGrWsEducationalInstitutionCode(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGrWsEducationalInstitutionCode[index], 4);
   }
   public void setInGrWsEducationalInstitutionCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      if (s.length() > 4) {
         throw new PropertyVetoException("InGrWsEducationalInstitutionCode must be <= 4 characters.",
               new PropertyChangeEvent (this, "InGrWsEducationalInstitutionCode", null, null));
      }
      importView.InGrWsEducationalInstitutionCode[index] = FixedStringAttr.valueOf(s, (short)4);
   }
 
   public String getInQualificationSpecialityTypeSpecialityCode() {
      return FixedStringAttr.valueOf(importView.InQualificationSpecialityTypeSpecialityCode, 3);
   }
   public void setInQualificationSpecialityTypeSpecialityCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 3) {
         throw new PropertyVetoException("InQualificationSpecialityTypeSpecialityCode must be <= 3 characters.",
               new PropertyChangeEvent (this, "InQualificationSpecialityTypeSpecialityCode", null, null));
      }
      importView.InQualificationSpecialityTypeSpecialityCode = FixedStringAttr.valueOf(s, (short)3);
   }
 
   public String getInQualificationSpecialityTypeEnglishDescription() {
      return FixedStringAttr.valueOf(importView.InQualificationSpecialityTypeEnglishDescription, 132);
   }
   public void setInQualificationSpecialityTypeEnglishDescription(String s)
      throws PropertyVetoException {
      if (s.length() > 132) {
         throw new PropertyVetoException("InQualificationSpecialityTypeEnglishDescription must be <= 132 characters.",
               new PropertyChangeEvent (this, "InQualificationSpecialityTypeEnglishDescription", null, null));
      }
      importView.InQualificationSpecialityTypeEnglishDescription = FixedStringAttr.valueOf(s, (short)132);
   }
 
   public String getInMaj1StudentQualificationMajorMkMajorSubjectCode() {
      return FixedStringAttr.valueOf(importView.InMaj1StudentQualificationMajorMkMajorSubjectCode, 3);
   }
   public void setInMaj1StudentQualificationMajorMkMajorSubjectCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 3) {
         throw new PropertyVetoException("InMaj1StudentQualificationMajorMkMajorSubjectCode must be <= 3 characters.",
               new PropertyChangeEvent (this, "InMaj1StudentQualificationMajorMkMajorSubjectCode", null, null));
      }
      importView.InMaj1StudentQualificationMajorMkMajorSubjectCode = FixedStringAttr.valueOf(s, (short)3);
   }
 
   public String getInMaj2StudentQualificationMajorMkMajorSubjectCode() {
      return FixedStringAttr.valueOf(importView.InMaj2StudentQualificationMajorMkMajorSubjectCode, 3);
   }
   public void setInMaj2StudentQualificationMajorMkMajorSubjectCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 3) {
         throw new PropertyVetoException("InMaj2StudentQualificationMajorMkMajorSubjectCode must be <= 3 characters.",
               new PropertyChangeEvent (this, "InMaj2StudentQualificationMajorMkMajorSubjectCode", null, null));
      }
      importView.InMaj2StudentQualificationMajorMkMajorSubjectCode = FixedStringAttr.valueOf(s, (short)3);
   }
 
   public short getInEarliestExemptionStudentAcademicRecordEarliestExemptionUnisaYear() {
      return importView.InEarliestExemptionStudentAcademicRecordEarliestExemptionUnisaYear;
   }
   public void setInEarliestExemptionStudentAcademicRecordEarliestExemptionUnisaYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InEarliestExemptionStudentAcademicRecordEarliestExemptionUnisaYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InEarliestExemptionStudentAcademicRecordEarliestExemptionUnisaYear", null, null));
      }
      importView.InEarliestExemptionStudentAcademicRecordEarliestExemptionUnisaYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInEarliestExemptionStudentAcademicRecordEarliestExemptionUnisaYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InEarliestExemptionStudentAcademicRecordEarliestExemptionUnisaYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InEarliestExemptionStudentAcademicRecordEarliestExemptionUnisaYear", null, null));
      }
      try {
          setInEarliestExemptionStudentAcademicRecordEarliestExemptionUnisaYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InEarliestExemptionStudentAcademicRecordEarliestExemptionUnisaYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InEarliestExemptionStudentAcademicRecordEarliestExemptionUnisaYear", null, null));
      }
   }
 
   public short getInEarliestExemptionStudentAcademicRecordEarliestExemptionOtherYear() {
      return importView.InEarliestExemptionStudentAcademicRecordEarliestExemptionOtherYear;
   }
   public void setInEarliestExemptionStudentAcademicRecordEarliestExemptionOtherYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InEarliestExemptionStudentAcademicRecordEarliestExemptionOtherYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InEarliestExemptionStudentAcademicRecordEarliestExemptionOtherYear", null, null));
      }
      importView.InEarliestExemptionStudentAcademicRecordEarliestExemptionOtherYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInEarliestExemptionStudentAcademicRecordEarliestExemptionOtherYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InEarliestExemptionStudentAcademicRecordEarliestExemptionOtherYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InEarliestExemptionStudentAcademicRecordEarliestExemptionOtherYear", null, null));
      }
      try {
          setInEarliestExemptionStudentAcademicRecordEarliestExemptionOtherYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InEarliestExemptionStudentAcademicRecordEarliestExemptionOtherYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InEarliestExemptionStudentAcademicRecordEarliestExemptionOtherYear", null, null));
      }
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
 
   public String getInWsMatricCertificateEngDescription() {
      return FixedStringAttr.valueOf(importView.InWsMatricCertificateEngDescription, 28);
   }
   public void setInWsMatricCertificateEngDescription(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsMatricCertificateEngDescription must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsMatricCertificateEngDescription", null, null));
      }
      importView.InWsMatricCertificateEngDescription = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInWsMatricCertificateAfrDescription() {
      return FixedStringAttr.valueOf(importView.InWsMatricCertificateAfrDescription, 28);
   }
   public void setInWsMatricCertificateAfrDescription(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsMatricCertificateAfrDescription must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsMatricCertificateAfrDescription", null, null));
      }
      importView.InWsMatricCertificateAfrDescription = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public short getInWsMatricRecordCategory() {
      return importView.InWsMatricRecordCategory;
   }
   public void setInWsMatricRecordCategory(short s)
      throws PropertyVetoException {
      Srces02sGrantExemptionsWsMatricRecordCategoryPropertyEditor pe = new Srces02sGrantExemptionsWsMatricRecordCategoryPropertyEditor();
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
      Srces02sGrantExemptionsWsMatricRecordAuditedFlagPropertyEditor pe = new Srces02sGrantExemptionsWsMatricRecordAuditedFlagPropertyEditor();
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
 
   public short getInWsMatricStatusNumber() {
      return importView.InWsMatricStatusNumber;
   }
   public void setInWsMatricStatusNumber(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InWsMatricStatusNumber has more than 2 digits.",
               new PropertyChangeEvent (this, "InWsMatricStatusNumber", null, null));
      }
      importView.InWsMatricStatusNumber = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsMatricStatusNumber(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsMatricStatusNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsMatricStatusNumber", null, null));
      }
      try {
          setInWsMatricStatusNumber(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsMatricStatusNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsMatricStatusNumber", null, null));
      }
   }
 
   public String getInWsMatricStatusAfrDescription() {
      return FixedStringAttr.valueOf(importView.InWsMatricStatusAfrDescription, 28);
   }
   public void setInWsMatricStatusAfrDescription(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsMatricStatusAfrDescription must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsMatricStatusAfrDescription", null, null));
      }
      importView.InWsMatricStatusAfrDescription = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInWsMatricStatusEngDescription() {
      return FixedStringAttr.valueOf(importView.InWsMatricStatusEngDescription, 28);
   }
   public void setInWsMatricStatusEngDescription(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsMatricStatusEngDescription must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsMatricStatusEngDescription", null, null));
      }
      importView.InWsMatricStatusEngDescription = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public short getInWsMatricStatusDateType() {
      return importView.InWsMatricStatusDateType;
   }
   public void setInWsMatricStatusDateType(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InWsMatricStatusDateType has more than 1 digits.",
               new PropertyChangeEvent (this, "InWsMatricStatusDateType", null, null));
      }
      importView.InWsMatricStatusDateType = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsMatricStatusDateType(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsMatricStatusDateType is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsMatricStatusDateType", null, null));
      }
      try {
          setInWsMatricStatusDateType(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsMatricStatusDateType is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsMatricStatusDateType", null, null));
      }
   }
 
   public final int InGroupMatricMax = 50;
   public short getInGroupMatricCount() {
      return (short)(importView.InGroupMatric_MA);
   };
 
   public void setInGroupMatricCount(short s) throws PropertyVetoException {
      if (s < 0 || s > InGroupMatricMax) {
         throw new PropertyVetoException("InGroupMatricCount value is not a valid value. (0 to 50)",
               new PropertyChangeEvent (this, "InGroupMatricCount", null, null));
      } else {
         importView.InGroupMatric_MA = IntAttr.valueOf((int)s);
      }
   }
 
   public String getInGWsMatricSubjectResultGrade(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGWsMatricSubjectResultGrade[index], 1);
   }
   public void setInGWsMatricSubjectResultGrade(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      Srces02sGrantExemptionsWsMatricSubjectResultGradePropertyEditor pe = new Srces02sGrantExemptionsWsMatricSubjectResultGradePropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InGWsMatricSubjectResultGrade value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InGWsMatricSubjectResultGrade", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGWsMatricSubjectResultGrade must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGWsMatricSubjectResultGrade", null, null));
      }
      importView.InGWsMatricSubjectResultGrade[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInGWsMatricSubjectResultMark(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return importView.InGWsMatricSubjectResultMark[index];
   }
   public void setInGWsMatricSubjectResultMark(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InGWsMatricSubjectResultMark has more than 2 digits.",
               new PropertyChangeEvent (this, "InGWsMatricSubjectResultMark", null, null));
      }
      importView.InGWsMatricSubjectResultMark[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGWsMatricSubjectResultMark(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGWsMatricSubjectResultMark is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGWsMatricSubjectResultMark", null, null));
      }
      try {
          setInGWsMatricSubjectResultMark(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGWsMatricSubjectResultMark is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGWsMatricSubjectResultMark", null, null));
      }
   }
 
   public String getInGWsMatricSubjectResultBorderMark(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGWsMatricSubjectResultBorderMark[index], 2);
   }
   public void setInGWsMatricSubjectResultBorderMark(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InGWsMatricSubjectResultBorderMark must be <= 2 characters.",
               new PropertyChangeEvent (this, "InGWsMatricSubjectResultBorderMark", null, null));
      }
      importView.InGWsMatricSubjectResultBorderMark[index] = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public String getInGWsMatricSubjectCode(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGWsMatricSubjectCode[index], 3);
   }
   public void setInGWsMatricSubjectCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 3) {
         throw new PropertyVetoException("InGWsMatricSubjectCode must be <= 3 characters.",
               new PropertyChangeEvent (this, "InGWsMatricSubjectCode", null, null));
      }
      importView.InGWsMatricSubjectCode[index] = FixedStringAttr.valueOf(s, (short)3);
   }
 
   public String getInPermExemptAllowedIefSuppliedFlag() {
      return FixedStringAttr.valueOf(importView.InPermExemptAllowedIefSuppliedFlag, 1);
   }
   public void setInPermExemptAllowedIefSuppliedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srces02sGrantExemptionsIefSuppliedFlagPropertyEditor pe = new Srces02sGrantExemptionsIefSuppliedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InPermExemptAllowedIefSuppliedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InPermExemptAllowedIefSuppliedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InPermExemptAllowedIefSuppliedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InPermExemptAllowedIefSuppliedFlag", null, null));
      }
      importView.InPermExemptAllowedIefSuppliedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInEmailFromCsfStringsString132() {
      return FixedStringAttr.valueOf(importView.InEmailFromCsfStringsString132, 132);
   }
   public void setInEmailFromCsfStringsString132(String s)
      throws PropertyVetoException {
      if (s.length() > 132) {
         throw new PropertyVetoException("InEmailFromCsfStringsString132 must be <= 132 characters.",
               new PropertyChangeEvent (this, "InEmailFromCsfStringsString132", null, null));
      }
      importView.InEmailFromCsfStringsString132 = FixedStringAttr.valueOf(s, (short)132);
   }
 
   public String getInEmailToCsfStringsString132() {
      return FixedStringAttr.valueOf(importView.InEmailToCsfStringsString132, 132);
   }
   public void setInEmailToCsfStringsString132(String s)
      throws PropertyVetoException {
      if (s.length() > 132) {
         throw new PropertyVetoException("InEmailToCsfStringsString132 must be <= 132 characters.",
               new PropertyChangeEvent (this, "InEmailToCsfStringsString132", null, null));
      }
      importView.InEmailToCsfStringsString132 = FixedStringAttr.valueOf(s, (short)132);
   }
 
   public String getInFaxNrCsfStringsString132() {
      return FixedStringAttr.valueOf(importView.InFaxNrCsfStringsString132, 132);
   }
   public void setInFaxNrCsfStringsString132(String s)
      throws PropertyVetoException {
      if (s.length() > 132) {
         throw new PropertyVetoException("InFaxNrCsfStringsString132 must be <= 132 characters.",
               new PropertyChangeEvent (this, "InFaxNrCsfStringsString132", null, null));
      }
      importView.InFaxNrCsfStringsString132 = FixedStringAttr.valueOf(s, (short)132);
   }
 
   public String getInFaxNameCsfStringsString132() {
      return FixedStringAttr.valueOf(importView.InFaxNameCsfStringsString132, 132);
   }
   public void setInFaxNameCsfStringsString132(String s)
      throws PropertyVetoException {
      if (s.length() > 132) {
         throw new PropertyVetoException("InFaxNameCsfStringsString132 must be <= 132 characters.",
               new PropertyChangeEvent (this, "InFaxNameCsfStringsString132", null, null));
      }
      importView.InFaxNameCsfStringsString132 = FixedStringAttr.valueOf(s, (short)132);
   }
 
   public String getInPrintPermsCsfStringsString1() {
      return FixedStringAttr.valueOf(importView.InPrintPermsCsfStringsString1, 1);
   }
   public void setInPrintPermsCsfStringsString1(String s)
      throws PropertyVetoException {
      if (s.length() > 1) {
         throw new PropertyVetoException("InPrintPermsCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InPrintPermsCsfStringsString1", null, null));
      }
      importView.InPrintPermsCsfStringsString1 = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInPrintTempsCsfStringsString1() {
      return FixedStringAttr.valueOf(importView.InPrintTempsCsfStringsString1, 1);
   }
   public void setInPrintTempsCsfStringsString1(String s)
      throws PropertyVetoException {
      if (s.length() > 1) {
         throw new PropertyVetoException("InPrintTempsCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InPrintTempsCsfStringsString1", null, null));
      }
      importView.InPrintTempsCsfStringsString1 = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getOutCanUpdateCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.OutCanUpdateCsfStringsString1, 1);
   }
 
   public String getOutCsfStringsString500() {
      return StringAttr.valueOf(exportView.OutCsfStringsString500);
   }
 
   public String getOutFaxOrEmailCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.OutFaxOrEmailCsfStringsString1, 1);
   }
 
   public String getOutPermExemptAllowedIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutPermExemptAllowedIefSuppliedFlag, 1);
   }
 
   public int getOutWsStudentNr() {
      return exportView.OutWsStudentNr;
   }
 
   public String getOutWsStudentSurname() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentSurname, 28);
   }
 
   public String getOutWsStudentFirstNames() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentFirstNames, 60);
   }
 
   public Calendar getOutWsStudentBirthDate() {
      return DateAttr.toCalendar(exportView.OutWsStudentBirthDate);
   }
   public int getAsIntOutWsStudentBirthDate() {
      return DateAttr.toInt(exportView.OutWsStudentBirthDate);
   }
 
   public String getOutWsStudentNumberRestrictedFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentNumberRestrictedFlag, 1);
   }
 
   public String getOutStudentNameCsfStringsString100() {
      return FixedStringAttr.valueOf(exportView.OutStudentNameCsfStringsString100, 100);
   }
 
   public String getOutWsQualificationCode() {
      return FixedStringAttr.valueOf(exportView.OutWsQualificationCode, 5);
   }
 
   public String getOutWsQualificationShortDescription() {
      return FixedStringAttr.valueOf(exportView.OutWsQualificationShortDescription, 12);
   }
 
   public int getOutSecurityWsUserNumber() {
      return exportView.OutSecurityWsUserNumber;
   }
 
   public short getOutSecurityWsUserNumberOfLogonAttempts() {
      return exportView.OutSecurityWsUserNumberOfLogonAttempts;
   }
 
   public String getOutSecurityWsUserLoggedOnFlag() {
      return FixedStringAttr.valueOf(exportView.OutSecurityWsUserLoggedOnFlag, 1);
   }
 
   public String getOutSecurityWsUserInUsedFlag() {
      return FixedStringAttr.valueOf(exportView.OutSecurityWsUserInUsedFlag, 1);
   }
 
   public Calendar getOutSecurityWsUserLastLogonDate() {
      return DateAttr.toCalendar(exportView.OutSecurityWsUserLastLogonDate);
   }
   public int getAsIntOutSecurityWsUserLastLogonDate() {
      return DateAttr.toInt(exportView.OutSecurityWsUserLastLogonDate);
   }
 
   public String getOutSecurityWsUserName() {
      return FixedStringAttr.valueOf(exportView.OutSecurityWsUserName, 28);
   }
 
   public String getOutSecurityWsUserPersonnelNo() {
      return FixedStringAttr.valueOf(exportView.OutSecurityWsUserPersonnelNo, 10);
   }
 
   public String getOutSecurityWsUserPhoneNumber() {
      return FixedStringAttr.valueOf(exportView.OutSecurityWsUserPhoneNumber, 20);
   }
 
   public String getOutSecurityWsUserPassword() {
      return FixedStringAttr.valueOf(exportView.OutSecurityWsUserPassword, 12);
   }
 
   public String getOutSecurityWsUserNovellUserCode() {
      return FixedStringAttr.valueOf(exportView.OutSecurityWsUserNovellUserCode, 20);
   }
 
   public String getOutSecurityWsPrinterCode() {
      return FixedStringAttr.valueOf(exportView.OutSecurityWsPrinterCode, 10);
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
 
   public String getOutCsfClientServerCommunicationsFirstpassFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsFirstpassFlag, 1);
   }
 
   public String getOutCsfClientServerCommunicationsRgvScrollUpFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsRgvScrollUpFlag, 1);
   }
 
   public String getOutCsfClientServerCommunicationsRgvScrollDownFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsRgvScrollDownFlag, 1);
   }
 
   public final int OutOtherGroupMax = 90;
   public short getOutOtherGroupCount() {
      return (short)(exportView.OutOtherGroup_MA);
   };
 
   public String getOutGroCsfLineActionBarAction(int index) throws ArrayIndexOutOfBoundsException {
      if (89 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 89, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGroCsfLineActionBarAction[index], 1);
   }
 
   public String getOutGroCsfLineActionBarSelectionFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (89 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 89, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGroCsfLineActionBarSelectionFlag[index], 1);
   }
 
   public String getOutGroCsfLineActionBarTranslatedAction(int index) throws ArrayIndexOutOfBoundsException {
      if (89 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 89, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGroCsfLineActionBarTranslatedAction[index], 1);
   }
 
   public String getOutGroStudentAcadrecOtherMkEducationalInst(int index) throws ArrayIndexOutOfBoundsException {
      if (89 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 89, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGroStudentAcadrecOtherMkEducationalInst[index], 4);
   }
 
   public String getOutGroStudentAcadrecOtherStudyUnitCode(int index) throws ArrayIndexOutOfBoundsException {
      if (89 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 89, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGroStudentAcadrecOtherStudyUnitCode[index], 12);
   }
 
   public short getOutGroStudentAcadrecOtherAcademicYear(int index) throws ArrayIndexOutOfBoundsException {
      if (89 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 89, not: " + index);
      }
      return exportView.OutGroStudentAcadrecOtherAcademicYear[index];
   }
 
   public short getOutGroStudentAcadrecOtherMark(int index) throws ArrayIndexOutOfBoundsException {
      if (89 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 89, not: " + index);
      }
      return exportView.OutGroStudentAcadrecOtherMark[index];
   }
 
   public String getOutGroWsEducationalInstitutionEngName(int index) throws ArrayIndexOutOfBoundsException {
      if (89 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 89, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGroWsEducationalInstitutionEngName[index], 28);
   }
 
   public final int OutGroupMax = 70;
   public short getOutGroupCount() {
      return (short)(exportView.OutGroup_MA);
   };
 
   public String getOutGrCsfLineActionBarAction(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGrCsfLineActionBarAction[index], 1);
   }
 
   public String getOutGrCsfLineActionBarSelectionFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGrCsfLineActionBarSelectionFlag[index], 1);
   }
 
   public String getOutGrCsfLineActionBarTranslatedAction(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGrCsfLineActionBarTranslatedAction[index], 1);
   }
 
   public short getOutGrCsfLineActionBarLineReturnCode(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return exportView.OutGrCsfLineActionBarLineReturnCode[index];
   }
 
   public String getOutGrUnisaWsStudyUnitCode(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGrUnisaWsStudyUnitCode[index], 7);
   }
 
   public String getOutGrCsfStringsString1(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGrCsfStringsString1[index], 1);
   }
 
   public String getOutGrCsfStringsString20(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGrCsfStringsString20[index], 20);
   }
 
   public String getOutGrPreviousExemptionCsfStringsString1(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGrPreviousExemptionCsfStringsString1[index], 1);
   }
 
   public int getOutGrValidCombinationIefSuppliedCount(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return exportView.OutGrValidCombinationIefSuppliedCount[index];
   }
 
   public String getOutGrWsCombinationDescriptionDescription(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return StringAttr.valueOf(exportView.OutGrWsCombinationDescriptionDescription[index]);
   }
 
   public String getOutGrWsEducationalInstitutionCode(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGrWsEducationalInstitutionCode[index], 4);
   }
 
   public String getOutQualificationSpecialityTypeSpecialityCode() {
      return FixedStringAttr.valueOf(exportView.OutQualificationSpecialityTypeSpecialityCode, 3);
   }
 
   public String getOutQualificationSpecialityTypeEnglishDescription() {
      return FixedStringAttr.valueOf(exportView.OutQualificationSpecialityTypeEnglishDescription, 132);
   }
 
   public String getOutMaj1StudentQualificationMajorMkMajorSubjectCode() {
      return FixedStringAttr.valueOf(exportView.OutMaj1StudentQualificationMajorMkMajorSubjectCode, 3);
   }
 
   public String getOutMaj2StudentQualificationMajorMkMajorSubjectCode() {
      return FixedStringAttr.valueOf(exportView.OutMaj2StudentQualificationMajorMkMajorSubjectCode, 3);
   }
 
   public short getOutEarliestExemptionStudentAcademicRecordEarliestExemptionUnisaYear() {
      return exportView.OutEarliestExemptionStudentAcademicRecordEarliestExemptionUnisaYear;
   }
 
   public short getOutEarliestExemptionStudentAcademicRecordEarliestExemptionOtherYear() {
      return exportView.OutEarliestExemptionStudentAcademicRecordEarliestExemptionOtherYear;
   }
 
   public String getOutWsMatricCertificateCode() {
      return FixedStringAttr.valueOf(exportView.OutWsMatricCertificateCode, 3);
   }
 
   public String getOutWsMatricCertificateEngDescription() {
      return FixedStringAttr.valueOf(exportView.OutWsMatricCertificateEngDescription, 28);
   }
 
   public String getOutWsMatricCertificateAfrDescription() {
      return FixedStringAttr.valueOf(exportView.OutWsMatricCertificateAfrDescription, 28);
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
 
   public Calendar getOutWsMatricRecordOrigExemptDate() {
      return DateAttr.toCalendar(exportView.OutWsMatricRecordOrigExemptDate);
   }
   public int getAsIntOutWsMatricRecordOrigExemptDate() {
      return DateAttr.toInt(exportView.OutWsMatricRecordOrigExemptDate);
   }
 
   public String getOutWsMatricStatusCode() {
      return FixedStringAttr.valueOf(exportView.OutWsMatricStatusCode, 4);
   }
 
   public short getOutWsMatricStatusNumber() {
      return exportView.OutWsMatricStatusNumber;
   }
 
   public String getOutWsMatricStatusAfrDescription() {
      return FixedStringAttr.valueOf(exportView.OutWsMatricStatusAfrDescription, 28);
   }
 
   public String getOutWsMatricStatusEngDescription() {
      return FixedStringAttr.valueOf(exportView.OutWsMatricStatusEngDescription, 28);
   }
 
   public short getOutWsMatricStatusDateType() {
      return exportView.OutWsMatricStatusDateType;
   }
 
   public final int OutGroupMatricMax = 50;
   public short getOutGroupMatricCount() {
      return (short)(exportView.OutGroupMatric_MA);
   };
 
   public String getOutGWsMatricSubjectResultGrade(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsMatricSubjectResultGrade[index], 1);
   }
 
   public short getOutGWsMatricSubjectResultMark(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return exportView.OutGWsMatricSubjectResultMark[index];
   }
 
   public String getOutGWsMatricSubjectResultBorderMark(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsMatricSubjectResultBorderMark[index], 2);
   }
 
   public String getOutGWsMatricSubjectCode(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsMatricSubjectCode[index], 3);
   }
 
   public String getOutEmailFromCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutEmailFromCsfStringsString132, 132);
   }
 
   public String getOutEmailToCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutEmailToCsfStringsString132, 132);
   }
 
   public String getOutFaxNrCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutFaxNrCsfStringsString132, 132);
   }
 
   public String getOutFaxNameCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutFaxNameCsfStringsString132, 132);
   }
 
   public String getOutPrintPermsCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.OutPrintPermsCsfStringsString1, 1);
   }
 
   public String getOutPrintTempsCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.OutPrintTempsCsfStringsString1, 1);
   }
 
};
