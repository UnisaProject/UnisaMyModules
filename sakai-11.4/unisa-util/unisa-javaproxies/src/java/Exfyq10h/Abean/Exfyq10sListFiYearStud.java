package Exfyq10h.Abean;
 
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
public class Exfyq10sListFiYearStud  implements ActionListener, java.io.Serializable  {
 
   //  Use final String for the bean name
   public static final String BEANNAME = new String("Exfyq10sListFiYearStud");
 
   //  Constants for Asynchronous status
   public static final int PENDING = CoopFlow.DATA_NOT_READY;
   public static final int AVAILABLE = CoopFlow.DATA_READY;
   public static final int INVALID_ID = CoopFlow.INVALID_ID;
 
   private final SimpleDateFormat nativeTimestampFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
   private final SimpleDateFormat nativeTimeFormatter = new SimpleDateFormat("HHmmss");
   private final SimpleDateFormat nativeDateFormatter = new SimpleDateFormat("yyyyMMdd");
   public Exfyq10sListFiYearStud() {
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
 
 
   private Exfyq10sListFiYearStudOperation oper = null;
 
   /**
    * Calls the transaction/procedure step on the server.
    *
    * @exception java.beans.PropertyVetoException
    * Final property checks can throw this exception.
    */
   public void execute()  throws PropertyVetoException {
      try  {
         if (oper == null) {
            oper = new Exfyq10sListFiYearStudOperation(this);
            addCompletionListener(new operListener(this));
            addExceptionListener(new operListener(this));
         }
 

 
         oper.doExfyq10sListFiYearStudOperation();
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
      private Exfyq10sListFiYearStud rP;
      operListener(Exfyq10sListFiYearStud r)  {
         rP = r;
      }
      public void actionPerformed(ActionEvent aEvent)  {
         if (Trace.isTracing(Trace.MASK_APPLICATION))
         {
            Trace.record(Trace.MASK_APPLICATION, "Exfyq10sListFiYearStud", "Listener heard that Exfyq10sListFiYearStudOperation completed with " + 
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of completion of operation Exfyq10sListFiYearStud ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      synchronized (this)  {
         targets = (Vector) completionListeners.clone();
      }
      actionEvt = new ActionEvent(this, 0, "Completion.Exfyq10sListFiYearStud");
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of exception of operation Exfyq10sListFiYearStud ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      String failCommand = "Exception.Exfyq10sListFiYearStud";
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
      exportView.OutStatusGroup_MA = IntAttr.getDefaultValue();
   }

   Exfyq10h.EXFYQ101_IA importView = Exfyq10h.EXFYQ101_IA.getInstance();
   Exfyq10h.EXFYQ101_OA exportView = Exfyq10h.EXFYQ101_OA.getInstance();
   public String getInWsStudyUnitCode() {
      return FixedStringAttr.valueOf(importView.InWsStudyUnitCode, 7);
   }
   public void setInWsStudyUnitCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InWsStudyUnitCode must be <= 7 characters.",
               new PropertyChangeEvent (this, "InWsStudyUnitCode", null, null));
      }
      importView.InWsStudyUnitCode = FixedStringAttr.valueOf(s, (short)7);
   }
 
   public String getInCalledFromCsfStringsString1() {
      return FixedStringAttr.valueOf(importView.InCalledFromCsfStringsString1, 1);
   }
   public void setInCalledFromCsfStringsString1(String s)
      throws PropertyVetoException {
      if (s.length() > 1) {
         throw new PropertyVetoException("InCalledFromCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InCalledFromCsfStringsString1", null, null));
      }
      importView.InCalledFromCsfStringsString1 = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public Calendar getInBeginActionDateStudentPaperLogUpdatedOn() {
      return TimestampAttr.toCalendar(importView.InBeginActionDateStudentPaperLogUpdatedOn);
   }
   public String getAsStringInBeginActionDateStudentPaperLogUpdatedOn() {
      return TimestampAttr.toString(importView.InBeginActionDateStudentPaperLogUpdatedOn);
   }
   public void setInBeginActionDateStudentPaperLogUpdatedOn(Calendar s)
      throws PropertyVetoException {
      importView.InBeginActionDateStudentPaperLogUpdatedOn = TimestampAttr.valueOf(s);
   }
   public void setAsStringInBeginActionDateStudentPaperLogUpdatedOn(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInBeginActionDateStudentPaperLogUpdatedOn((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeTimestampFormatter.parse(s.length() > 17 ? s.substring(0, 17) : s));
            importView.InBeginActionDateStudentPaperLogUpdatedOn = TimestampAttr.valueOf(s);
         } catch (ParseException e) {
            throw new PropertyVetoException("InBeginActionDateStudentPaperLogUpdatedOn has an invalid format (yyyyMMddHHmmssSSSSSS).",
                  new PropertyChangeEvent (this, "InBeginActionDateStudentPaperLogUpdatedOn", null, null));
         }
      }
   }
 
   public Calendar getInEndActionDateStudentPaperLogUpdatedOn() {
      return TimestampAttr.toCalendar(importView.InEndActionDateStudentPaperLogUpdatedOn);
   }
   public String getAsStringInEndActionDateStudentPaperLogUpdatedOn() {
      return TimestampAttr.toString(importView.InEndActionDateStudentPaperLogUpdatedOn);
   }
   public void setInEndActionDateStudentPaperLogUpdatedOn(Calendar s)
      throws PropertyVetoException {
      importView.InEndActionDateStudentPaperLogUpdatedOn = TimestampAttr.valueOf(s);
   }
   public void setAsStringInEndActionDateStudentPaperLogUpdatedOn(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInEndActionDateStudentPaperLogUpdatedOn((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeTimestampFormatter.parse(s.length() > 17 ? s.substring(0, 17) : s));
            importView.InEndActionDateStudentPaperLogUpdatedOn = TimestampAttr.valueOf(s);
         } catch (ParseException e) {
            throw new PropertyVetoException("InEndActionDateStudentPaperLogUpdatedOn has an invalid format (yyyyMMddHHmmssSSSSSS).",
                  new PropertyChangeEvent (this, "InEndActionDateStudentPaperLogUpdatedOn", null, null));
         }
      }
   }
 
   public String getInRmkStatusWsGenericCodeCode() {
      return FixedStringAttr.valueOf(importView.InRmkStatusWsGenericCodeCode, 10);
   }
   public void setInRmkStatusWsGenericCodeCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InRmkStatusWsGenericCodeCode must be <= 10 characters.",
               new PropertyChangeEvent (this, "InRmkStatusWsGenericCodeCode", null, null));
      }
      importView.InRmkStatusWsGenericCodeCode = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInRmkStatusWsGenericCodeEngDescription() {
      return FixedStringAttr.valueOf(importView.InRmkStatusWsGenericCodeEngDescription, 40);
   }
   public void setInRmkStatusWsGenericCodeEngDescription(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 40) {
         throw new PropertyVetoException("InRmkStatusWsGenericCodeEngDescription must be <= 40 characters.",
               new PropertyChangeEvent (this, "InRmkStatusWsGenericCodeEngDescription", null, null));
      }
      importView.InRmkStatusWsGenericCodeEngDescription = FixedStringAttr.valueOf(s, (short)40);
   }
 
   public String getInActionCsfStringsString2() {
      return FixedStringAttr.valueOf(importView.InActionCsfStringsString2, 2);
   }
   public void setInActionCsfStringsString2(String s)
      throws PropertyVetoException {
      if (s.length() > 2) {
         throw new PropertyVetoException("InActionCsfStringsString2 must be <= 2 characters.",
               new PropertyChangeEvent (this, "InActionCsfStringsString2", null, null));
      }
      importView.InActionCsfStringsString2 = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public short getInHighWsDepartmentCode() {
      return importView.InHighWsDepartmentCode;
   }
   public void setInHighWsDepartmentCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InHighWsDepartmentCode has more than 3 digits.",
               new PropertyChangeEvent (this, "InHighWsDepartmentCode", null, null));
      }
      importView.InHighWsDepartmentCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInHighWsDepartmentCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InHighWsDepartmentCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InHighWsDepartmentCode", null, null));
      }
      try {
          setInHighWsDepartmentCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InHighWsDepartmentCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InHighWsDepartmentCode", null, null));
      }
   }
 
   public String getInHighWsDepartmentEngDescription() {
      return FixedStringAttr.valueOf(importView.InHighWsDepartmentEngDescription, 28);
   }
   public void setInHighWsDepartmentEngDescription(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InHighWsDepartmentEngDescription must be <= 28 characters.",
               new PropertyChangeEvent (this, "InHighWsDepartmentEngDescription", null, null));
      }
      importView.InHighWsDepartmentEngDescription = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public short getInHighWsUnisaCollegeCode() {
      return importView.InHighWsUnisaCollegeCode;
   }
   public void setInHighWsUnisaCollegeCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InHighWsUnisaCollegeCode has more than 3 digits.",
               new PropertyChangeEvent (this, "InHighWsUnisaCollegeCode", null, null));
      }
      importView.InHighWsUnisaCollegeCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInHighWsUnisaCollegeCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InHighWsUnisaCollegeCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InHighWsUnisaCollegeCode", null, null));
      }
      try {
          setInHighWsUnisaCollegeCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InHighWsUnisaCollegeCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InHighWsUnisaCollegeCode", null, null));
      }
   }
 
   public String getInHighWsUnisaCollegeAbbreviation() {
      return FixedStringAttr.valueOf(importView.InHighWsUnisaCollegeAbbreviation, 10);
   }
   public void setInHighWsUnisaCollegeAbbreviation(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InHighWsUnisaCollegeAbbreviation must be <= 10 characters.",
               new PropertyChangeEvent (this, "InHighWsUnisaCollegeAbbreviation", null, null));
      }
      importView.InHighWsUnisaCollegeAbbreviation = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public short getInHighWsUnisaSchoolCode() {
      return importView.InHighWsUnisaSchoolCode;
   }
   public void setInHighWsUnisaSchoolCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InHighWsUnisaSchoolCode has more than 3 digits.",
               new PropertyChangeEvent (this, "InHighWsUnisaSchoolCode", null, null));
      }
      importView.InHighWsUnisaSchoolCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInHighWsUnisaSchoolCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InHighWsUnisaSchoolCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InHighWsUnisaSchoolCode", null, null));
      }
      try {
          setInHighWsUnisaSchoolCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InHighWsUnisaSchoolCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InHighWsUnisaSchoolCode", null, null));
      }
   }
 
   public String getInHighWsUnisaSchoolAbbreviation() {
      return FixedStringAttr.valueOf(importView.InHighWsUnisaSchoolAbbreviation, 20);
   }
   public void setInHighWsUnisaSchoolAbbreviation(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 20) {
         throw new PropertyVetoException("InHighWsUnisaSchoolAbbreviation must be <= 20 characters.",
               new PropertyChangeEvent (this, "InHighWsUnisaSchoolAbbreviation", null, null));
      }
      importView.InHighWsUnisaSchoolAbbreviation = FixedStringAttr.valueOf(s, (short)20);
   }
 
   public int getInHighExamReMarkMkStudentNr() {
      return importView.InHighExamReMarkMkStudentNr;
   }
   public void setInHighExamReMarkMkStudentNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InHighExamReMarkMkStudentNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InHighExamReMarkMkStudentNr", null, null));
      }
      importView.InHighExamReMarkMkStudentNr = IntAttr.valueOf(s);
   }
   public void setAsStringInHighExamReMarkMkStudentNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InHighExamReMarkMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InHighExamReMarkMkStudentNr", null, null));
      }
      try {
          setInHighExamReMarkMkStudentNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InHighExamReMarkMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InHighExamReMarkMkStudentNr", null, null));
      }
   }
 
   public String getInHighExamReMarkMkStudyUnitCode() {
      return FixedStringAttr.valueOf(importView.InHighExamReMarkMkStudyUnitCode, 7);
   }
   public void setInHighExamReMarkMkStudyUnitCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InHighExamReMarkMkStudyUnitCode must be <= 7 characters.",
               new PropertyChangeEvent (this, "InHighExamReMarkMkStudyUnitCode", null, null));
      }
      importView.InHighExamReMarkMkStudyUnitCode = FixedStringAttr.valueOf(s, (short)7);
   }
 
   public short getInLowWsDepartmentCode() {
      return importView.InLowWsDepartmentCode;
   }
   public void setInLowWsDepartmentCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InLowWsDepartmentCode has more than 3 digits.",
               new PropertyChangeEvent (this, "InLowWsDepartmentCode", null, null));
      }
      importView.InLowWsDepartmentCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInLowWsDepartmentCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InLowWsDepartmentCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLowWsDepartmentCode", null, null));
      }
      try {
          setInLowWsDepartmentCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InLowWsDepartmentCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLowWsDepartmentCode", null, null));
      }
   }
 
   public String getInLowWsDepartmentEngDescription() {
      return FixedStringAttr.valueOf(importView.InLowWsDepartmentEngDescription, 28);
   }
   public void setInLowWsDepartmentEngDescription(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InLowWsDepartmentEngDescription must be <= 28 characters.",
               new PropertyChangeEvent (this, "InLowWsDepartmentEngDescription", null, null));
      }
      importView.InLowWsDepartmentEngDescription = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public short getInLowWsUnisaCollegeCode() {
      return importView.InLowWsUnisaCollegeCode;
   }
   public void setInLowWsUnisaCollegeCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InLowWsUnisaCollegeCode has more than 3 digits.",
               new PropertyChangeEvent (this, "InLowWsUnisaCollegeCode", null, null));
      }
      importView.InLowWsUnisaCollegeCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInLowWsUnisaCollegeCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InLowWsUnisaCollegeCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLowWsUnisaCollegeCode", null, null));
      }
      try {
          setInLowWsUnisaCollegeCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InLowWsUnisaCollegeCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLowWsUnisaCollegeCode", null, null));
      }
   }
 
   public String getInLowWsUnisaCollegeAbbreviation() {
      return FixedStringAttr.valueOf(importView.InLowWsUnisaCollegeAbbreviation, 10);
   }
   public void setInLowWsUnisaCollegeAbbreviation(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InLowWsUnisaCollegeAbbreviation must be <= 10 characters.",
               new PropertyChangeEvent (this, "InLowWsUnisaCollegeAbbreviation", null, null));
      }
      importView.InLowWsUnisaCollegeAbbreviation = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public short getInLowWsUnisaSchoolCode() {
      return importView.InLowWsUnisaSchoolCode;
   }
   public void setInLowWsUnisaSchoolCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InLowWsUnisaSchoolCode has more than 3 digits.",
               new PropertyChangeEvent (this, "InLowWsUnisaSchoolCode", null, null));
      }
      importView.InLowWsUnisaSchoolCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInLowWsUnisaSchoolCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InLowWsUnisaSchoolCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLowWsUnisaSchoolCode", null, null));
      }
      try {
          setInLowWsUnisaSchoolCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InLowWsUnisaSchoolCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLowWsUnisaSchoolCode", null, null));
      }
   }
 
   public String getInLowWsUnisaSchoolAbbreviation() {
      return FixedStringAttr.valueOf(importView.InLowWsUnisaSchoolAbbreviation, 20);
   }
   public void setInLowWsUnisaSchoolAbbreviation(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 20) {
         throw new PropertyVetoException("InLowWsUnisaSchoolAbbreviation must be <= 20 characters.",
               new PropertyChangeEvent (this, "InLowWsUnisaSchoolAbbreviation", null, null));
      }
      importView.InLowWsUnisaSchoolAbbreviation = FixedStringAttr.valueOf(s, (short)20);
   }
 
   public int getInLowExamReMarkMkStudentNr() {
      return importView.InLowExamReMarkMkStudentNr;
   }
   public void setInLowExamReMarkMkStudentNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InLowExamReMarkMkStudentNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InLowExamReMarkMkStudentNr", null, null));
      }
      importView.InLowExamReMarkMkStudentNr = IntAttr.valueOf(s);
   }
   public void setAsStringInLowExamReMarkMkStudentNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InLowExamReMarkMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLowExamReMarkMkStudentNr", null, null));
      }
      try {
          setInLowExamReMarkMkStudentNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InLowExamReMarkMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLowExamReMarkMkStudentNr", null, null));
      }
   }
 
   public String getInLowExamReMarkMkStudyUnitCode() {
      return FixedStringAttr.valueOf(importView.InLowExamReMarkMkStudyUnitCode, 7);
   }
   public void setInLowExamReMarkMkStudyUnitCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InLowExamReMarkMkStudyUnitCode must be <= 7 characters.",
               new PropertyChangeEvent (this, "InLowExamReMarkMkStudyUnitCode", null, null));
      }
      importView.InLowExamReMarkMkStudyUnitCode = FixedStringAttr.valueOf(s, (short)7);
   }
 
   public String getInRmkTypeWsGenericCodeCode() {
      return FixedStringAttr.valueOf(importView.InRmkTypeWsGenericCodeCode, 10);
   }
   public void setInRmkTypeWsGenericCodeCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InRmkTypeWsGenericCodeCode must be <= 10 characters.",
               new PropertyChangeEvent (this, "InRmkTypeWsGenericCodeCode", null, null));
      }
      importView.InRmkTypeWsGenericCodeCode = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInRmkTypeWsGenericCodeEngDescription() {
      return FixedStringAttr.valueOf(importView.InRmkTypeWsGenericCodeEngDescription, 40);
   }
   public void setInRmkTypeWsGenericCodeEngDescription(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 40) {
         throw new PropertyVetoException("InRmkTypeWsGenericCodeEngDescription must be <= 40 characters.",
               new PropertyChangeEvent (this, "InRmkTypeWsGenericCodeEngDescription", null, null));
      }
      importView.InRmkTypeWsGenericCodeEngDescription = FixedStringAttr.valueOf(s, (short)40);
   }
 
   public short getInWsGenericCategoryCode() {
      return importView.InWsGenericCategoryCode;
   }
   public void setInWsGenericCategoryCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InWsGenericCategoryCode has more than 3 digits.",
               new PropertyChangeEvent (this, "InWsGenericCategoryCode", null, null));
      }
      importView.InWsGenericCategoryCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsGenericCategoryCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsGenericCategoryCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsGenericCategoryCode", null, null));
      }
      try {
          setInWsGenericCategoryCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsGenericCategoryCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsGenericCategoryCode", null, null));
      }
   }
 
   public short getInWsUnisaSchoolCode() {
      return importView.InWsUnisaSchoolCode;
   }
   public void setInWsUnisaSchoolCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InWsUnisaSchoolCode has more than 3 digits.",
               new PropertyChangeEvent (this, "InWsUnisaSchoolCode", null, null));
      }
      importView.InWsUnisaSchoolCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsUnisaSchoolCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsUnisaSchoolCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsUnisaSchoolCode", null, null));
      }
      try {
          setInWsUnisaSchoolCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsUnisaSchoolCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsUnisaSchoolCode", null, null));
      }
   }
 
   public String getInWsUnisaSchoolEngDescription() {
      return FixedStringAttr.valueOf(importView.InWsUnisaSchoolEngDescription, 100);
   }
   public void setInWsUnisaSchoolEngDescription(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 100) {
         throw new PropertyVetoException("InWsUnisaSchoolEngDescription must be <= 100 characters.",
               new PropertyChangeEvent (this, "InWsUnisaSchoolEngDescription", null, null));
      }
      importView.InWsUnisaSchoolEngDescription = FixedStringAttr.valueOf(s, (short)100);
   }
 
   public short getInWsUnisaCollegeCode() {
      return importView.InWsUnisaCollegeCode;
   }
   public void setInWsUnisaCollegeCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InWsUnisaCollegeCode has more than 3 digits.",
               new PropertyChangeEvent (this, "InWsUnisaCollegeCode", null, null));
      }
      importView.InWsUnisaCollegeCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsUnisaCollegeCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsUnisaCollegeCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsUnisaCollegeCode", null, null));
      }
      try {
          setInWsUnisaCollegeCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsUnisaCollegeCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsUnisaCollegeCode", null, null));
      }
   }
 
   public String getInWsUnisaCollegeEngDescription() {
      return FixedStringAttr.valueOf(importView.InWsUnisaCollegeEngDescription, 100);
   }
   public void setInWsUnisaCollegeEngDescription(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 100) {
         throw new PropertyVetoException("InWsUnisaCollegeEngDescription must be <= 100 characters.",
               new PropertyChangeEvent (this, "InWsUnisaCollegeEngDescription", null, null));
      }
      importView.InWsUnisaCollegeEngDescription = FixedStringAttr.valueOf(s, (short)100);
   }
 
   public short getInWsDepartmentCode() {
      return importView.InWsDepartmentCode;
   }
   public void setInWsDepartmentCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InWsDepartmentCode has more than 3 digits.",
               new PropertyChangeEvent (this, "InWsDepartmentCode", null, null));
      }
      importView.InWsDepartmentCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsDepartmentCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsDepartmentCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsDepartmentCode", null, null));
      }
      try {
          setInWsDepartmentCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsDepartmentCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsDepartmentCode", null, null));
      }
   }
 
   public String getInWsDepartmentEngDescription() {
      return FixedStringAttr.valueOf(importView.InWsDepartmentEngDescription, 28);
   }
   public void setInWsDepartmentEngDescription(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsDepartmentEngDescription must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsDepartmentEngDescription", null, null));
      }
      importView.InWsDepartmentEngDescription = FixedStringAttr.valueOf(s, (short)28);
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
 
   public short getInExamReMarkPrintBatch() {
      return importView.InExamReMarkPrintBatch;
   }
   public void setInExamReMarkPrintBatch(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InExamReMarkPrintBatch has more than 4 digits.",
               new PropertyChangeEvent (this, "InExamReMarkPrintBatch", null, null));
      }
      importView.InExamReMarkPrintBatch = ShortAttr.valueOf(s);
   }
   public void setAsStringInExamReMarkPrintBatch(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InExamReMarkPrintBatch is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamReMarkPrintBatch", null, null));
      }
      try {
          setInExamReMarkPrintBatch(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InExamReMarkPrintBatch is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamReMarkPrintBatch", null, null));
      }
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
 
   public String getOutRmkStatusWsGenericCodeCode() {
      return FixedStringAttr.valueOf(exportView.OutRmkStatusWsGenericCodeCode, 10);
   }
 
   public String getOutRmkStatusWsGenericCodeEngDescription() {
      return FixedStringAttr.valueOf(exportView.OutRmkStatusWsGenericCodeEngDescription, 40);
   }
 
   public short getOutWsUnisaSchoolCode() {
      return exportView.OutWsUnisaSchoolCode;
   }
 
   public String getOutWsUnisaSchoolEngDescription() {
      return FixedStringAttr.valueOf(exportView.OutWsUnisaSchoolEngDescription, 100);
   }
 
   public short getOutWsUnisaCollegeCode() {
      return exportView.OutWsUnisaCollegeCode;
   }
 
   public String getOutWsUnisaCollegeEngDescription() {
      return FixedStringAttr.valueOf(exportView.OutWsUnisaCollegeEngDescription, 100);
   }
 
   public short getOutWsDepartmentCode() {
      return exportView.OutWsDepartmentCode;
   }
 
   public String getOutWsDepartmentEngDescription() {
      return FixedStringAttr.valueOf(exportView.OutWsDepartmentEngDescription, 28);
   }
 
   public int getOutScSecurityFunctionNumber() {
      return exportView.OutScSecurityFunctionNumber;
   }
 
   public int getOutScSecurityUserNumber() {
      return exportView.OutScSecurityUserNumber;
   }
 
   public String getOutScSecurityFunctionType() {
      return FixedStringAttr.valueOf(exportView.OutScSecurityFunctionType, 3);
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
 
   public final int OutStatusGroupMax = 25;
   public short getOutStatusGroupCount() {
      return (short)(exportView.OutStatusGroup_MA);
   };
 
   public String getOutStatusGIefSuppliedSelectChar(int index) throws ArrayIndexOutOfBoundsException {
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutStatusGIefSuppliedSelectChar[index], 1);
   }
 
   public short getOutStatusGWsUnisaCollegeCode(int index) throws ArrayIndexOutOfBoundsException {
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      return exportView.OutStatusGWsUnisaCollegeCode[index];
   }
 
   public String getOutStatusGWsUnisaCollegeAbbreviation(int index) throws ArrayIndexOutOfBoundsException {
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutStatusGWsUnisaCollegeAbbreviation[index], 10);
   }
 
   public short getOutStatusGWsUnisaSchoolCode(int index) throws ArrayIndexOutOfBoundsException {
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      return exportView.OutStatusGWsUnisaSchoolCode[index];
   }
 
   public String getOutStatusGWsUnisaSchoolAbbreviation(int index) throws ArrayIndexOutOfBoundsException {
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutStatusGWsUnisaSchoolAbbreviation[index], 20);
   }
 
   public short getOutStatusGWsDepartmentCode(int index) throws ArrayIndexOutOfBoundsException {
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      return exportView.OutStatusGWsDepartmentCode[index];
   }
 
   public String getOutStatusGWsDepartmentEngDescription(int index) throws ArrayIndexOutOfBoundsException {
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutStatusGWsDepartmentEngDescription[index], 28);
   }
 
   public int getOutStatusGExamReMarkMkStudentNr(int index) throws ArrayIndexOutOfBoundsException {
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      return exportView.OutStatusGExamReMarkMkStudentNr[index];
   }
 
   public String getOutStatusGExamReMarkMkStudyUnitCode(int index) throws ArrayIndexOutOfBoundsException {
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutStatusGExamReMarkMkStudyUnitCode[index], 7);
   }
 
   public short getOutStatusGExamReMarkPrintBatch(int index) throws ArrayIndexOutOfBoundsException {
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      return exportView.OutStatusGExamReMarkPrintBatch[index];
   }
 
   public int getOutStatusGExamReMarkRemarker(int index) throws ArrayIndexOutOfBoundsException {
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      return exportView.OutStatusGExamReMarkRemarker[index];
   }
 
   public short getOutStatusGWsStudentExamResultFinalMark(int index) throws ArrayIndexOutOfBoundsException {
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      return exportView.OutStatusGWsStudentExamResultFinalMark[index];
   }
 
   public short getOutStatusGWsStudentExamResultMkResultTypeCode(int index) throws ArrayIndexOutOfBoundsException {
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      return exportView.OutStatusGWsStudentExamResultMkResultTypeCode[index];
   }
 
   public short getOutStatusGWsStudentExamResultMkAcademicYear(int index) throws ArrayIndexOutOfBoundsException {
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      return exportView.OutStatusGWsStudentExamResultMkAcademicYear[index];
   }
 
   public short getOutStatusGWsStudentExamResultSemesterPeriod(int index) throws ArrayIndexOutOfBoundsException {
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      return exportView.OutStatusGWsStudentExamResultSemesterPeriod[index];
   }
 
   public String getOutStatusGStudentNameCsfStringsString28(int index) throws ArrayIndexOutOfBoundsException {
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutStatusGStudentNameCsfStringsString28[index], 28);
   }
 
   public String getOutStatusGRemarkerNameCsfStringsString28(int index) throws ArrayIndexOutOfBoundsException {
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutStatusGRemarkerNameCsfStringsString28[index], 28);
   }
 
   public short getOutStatusGExamPaperDetailsNr(int index) throws ArrayIndexOutOfBoundsException {
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      return exportView.OutStatusGExamPaperDetailsNr[index];
   }
 
   public String getOutStatusGShelfCsfStringsString7(int index) throws ArrayIndexOutOfBoundsException {
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutStatusGShelfCsfStringsString7[index], 7);
   }
 
   public String getOutStatusGStudentPaperLogActionGc79(int index) throws ArrayIndexOutOfBoundsException {
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutStatusGStudentPaperLogActionGc79[index], 10);
   }
 
   public String getOutStatusGStudentPaperLogRequestActionFrom(int index) throws ArrayIndexOutOfBoundsException {
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutStatusGStudentPaperLogRequestActionFrom[index], 20);
   }
 
   public String getOutStatusGStudentPaperLogUpdatedBy(int index) throws ArrayIndexOutOfBoundsException {
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutStatusGStudentPaperLogUpdatedBy[index], 20);
   }
 
   public Calendar getOutStatusGStudentPaperLogUpdatedOn(int index) throws ArrayIndexOutOfBoundsException {
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      return TimestampAttr.toCalendar(exportView.OutStatusGStudentPaperLogUpdatedOn[index]);
   }
   public String getAsStringOutStatusGStudentPaperLogUpdatedOn(int index) throws ArrayIndexOutOfBoundsException {
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      return TimestampAttr.toString(exportView.OutStatusGStudentPaperLogUpdatedOn[index]);
   }
 
};
