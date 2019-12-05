package Smsij01h.Abean;
 
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
public class Smsij01sMntGenDespatchInfo  implements ActionListener, java.io.Serializable  {
 
   //  Use final String for the bean name
   public static final String BEANNAME = new String("Smsij01sMntGenDespatchInfo");
 
   //  Constants for Asynchronous status
   public static final int PENDING = CoopFlow.DATA_NOT_READY;
   public static final int AVAILABLE = CoopFlow.DATA_READY;
   public static final int INVALID_ID = CoopFlow.INVALID_ID;
 
   private final SimpleDateFormat nativeTimestampFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
   private final SimpleDateFormat nativeTimeFormatter = new SimpleDateFormat("HHmmss");
   private final SimpleDateFormat nativeDateFormatter = new SimpleDateFormat("yyyyMMdd");
   public Smsij01sMntGenDespatchInfo() {
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
 
 
   private Smsij01sMntGenDespatchInfoOperation oper = null;
 
   /**
    * Calls the transaction/procedure step on the server.
    *
    * @exception java.beans.PropertyVetoException
    * Final property checks can throw this exception.
    */
   public void execute()  throws PropertyVetoException {
      try  {
         if (oper == null) {
            oper = new Smsij01sMntGenDespatchInfoOperation(this);
            addCompletionListener(new operListener(this));
            addExceptionListener(new operListener(this));
         }
 

 
         oper.doSmsij01sMntGenDespatchInfoOperation();
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
      private Smsij01sMntGenDespatchInfo rP;
      operListener(Smsij01sMntGenDespatchInfo r)  {
         rP = r;
      }
      public void actionPerformed(ActionEvent aEvent)  {
         if (Trace.isTracing(Trace.MASK_APPLICATION))
         {
            Trace.record(Trace.MASK_APPLICATION, "Smsij01sMntGenDespatchInfo", "Listener heard that Smsij01sMntGenDespatchInfoOperation completed with " + 
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of completion of operation Smsij01sMntGenDespatchInfo ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      synchronized (this)  {
         targets = (Vector) completionListeners.clone();
      }
      actionEvt = new ActionEvent(this, 0, "Completion.Smsij01sMntGenDespatchInfo");
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of exception of operation Smsij01sMntGenDespatchInfo ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      String failCommand = "Exception.Smsij01sMntGenDespatchInfo";
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
      importView.InLuGroup_MA = IntAttr.valueOf(InLuGroupMax);
      importView.InStudentAnnualRecordLateRegistrationFlag = FixedStringAttr.valueOf("N", 1);
      importView.InGroup_MA = IntAttr.valueOf(InGroupMax);
      for( int index = 0; index < 30; index++)
         importView.InhpGWsQualificationType[index] = FixedStringAttr.valueOf("G", 1);
      for( int index = 0; index < 30; index++)
         importView.InGWsQualificationType[index] = FixedStringAttr.valueOf("G", 1);
      exportView.OutGrp_MA = IntAttr.getDefaultValue();
      exportView.OutStudentAnnualRecordLateRegistrationFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutLuGroup_MA = IntAttr.getDefaultValue();
      exportView.OutGroup_MA = IntAttr.getDefaultValue();
      for( int index = 0; index < 30; index++)
         exportView.OuthpGWsQualificationType[index] = FixedStringAttr.valueOf("G", 1);
      for( int index = 0; index < 30; index++)
         exportView.OutGWsQualificationType[index] = FixedStringAttr.valueOf("G", 1);
   }

   Smsij01h.SMSIJ01S_IA importView = Smsij01h.SMSIJ01S_IA.getInstance();
   Smsij01h.SMSIJ01S_OA exportView = Smsij01h.SMSIJ01S_OA.getInstance();
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
 
   public String getInEmailOrFaxCsfStringsString1() {
      return FixedStringAttr.valueOf(importView.InEmailOrFaxCsfStringsString1, 1);
   }
   public void setInEmailOrFaxCsfStringsString1(String s)
      throws PropertyVetoException {
      if (s.length() > 1) {
         throw new PropertyVetoException("InEmailOrFaxCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InEmailOrFaxCsfStringsString1", null, null));
      }
      importView.InEmailOrFaxCsfStringsString1 = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInNameCsfStringsString50() {
      return FixedStringAttr.valueOf(importView.InNameCsfStringsString50, 50);
   }
   public void setInNameCsfStringsString50(String s)
      throws PropertyVetoException {
      if (s.length() > 50) {
         throw new PropertyVetoException("InNameCsfStringsString50 must be <= 50 characters.",
               new PropertyChangeEvent (this, "InNameCsfStringsString50", null, null));
      }
      importView.InNameCsfStringsString50 = FixedStringAttr.valueOf(s, (short)50);
   }
 
   public String getInNameCsfStringsString132() {
      return FixedStringAttr.valueOf(importView.InNameCsfStringsString132, 132);
   }
   public void setInNameCsfStringsString132(String s)
      throws PropertyVetoException {
      if (s.length() > 132) {
         throw new PropertyVetoException("InNameCsfStringsString132 must be <= 132 characters.",
               new PropertyChangeEvent (this, "InNameCsfStringsString132", null, null));
      }
      importView.InNameCsfStringsString132 = FixedStringAttr.valueOf(s, (short)132);
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
 
   public final int InLuGroupMax = 100;
   public short getInLuGroupCount() {
      return (short)(importView.InLuGroup_MA);
   };
 
   public void setInLuGroupCount(short s) throws PropertyVetoException {
      if (s < 0 || s > InLuGroupMax) {
         throw new PropertyVetoException("InLuGroupCount value is not a valid value. (0 to 100)",
               new PropertyChangeEvent (this, "InLuGroupCount", null, null));
      } else {
         importView.InLuGroup_MA = IntAttr.valueOf((int)s);
      }
   }
 
   public Calendar getInGWsTrackAndTraceDate(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return DateAttr.toCalendar(importView.InGWsTrackAndTraceDate[index]);
   }
   public int getAsIntInGWsTrackAndTraceDate(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return DateAttr.toInt(importView.InGWsTrackAndTraceDate[index]);
   }
   public void setInGWsTrackAndTraceDate(int index, Calendar s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      importView.InGWsTrackAndTraceDate[index] = DateAttr.valueOf(s);
   }
   public void setAsStringInGWsTrackAndTraceDate(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      if (s == null || s.length() == 0) {
         setInGWsTrackAndTraceDate(index, (Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInGWsTrackAndTraceDate(index, tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InGWsTrackAndTraceDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InGWsTrackAndTraceDate", null, null));
         }
      }
   }
   public void setAsIntInGWsTrackAndTraceDate(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInGWsTrackAndTraceDate(index, temp);
   }
 
   public String getInGWsTrackAndTraceNumber(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGWsTrackAndTraceNumber[index], 15);
   }
   public void setInGWsTrackAndTraceNumber(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      if (s.length() > 15) {
         throw new PropertyVetoException("InGWsTrackAndTraceNumber must be <= 15 characters.",
               new PropertyChangeEvent (this, "InGWsTrackAndTraceNumber", null, null));
      }
      importView.InGWsTrackAndTraceNumber[index] = FixedStringAttr.valueOf(s, (short)15);
   }
 
   public String getInLuGCsfLineActionBarSelectionFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InLuGCsfLineActionBarSelectionFlag[index], 1);
   }
   public void setInLuGCsfLineActionBarSelectionFlag(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InLuGCsfLineActionBarSelectionFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InLuGCsfLineActionBarSelectionFlag", null, null));
      }
      importView.InLuGCsfLineActionBarSelectionFlag[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInWsStoreStudentIssueMkIssuingStore() {
      return importView.InWsStoreStudentIssueMkIssuingStore;
   }
   public void setInWsStoreStudentIssueMkIssuingStore(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InWsStoreStudentIssueMkIssuingStore has more than 2 digits.",
               new PropertyChangeEvent (this, "InWsStoreStudentIssueMkIssuingStore", null, null));
      }
      importView.InWsStoreStudentIssueMkIssuingStore = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsStoreStudentIssueMkIssuingStore(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStoreStudentIssueMkIssuingStore is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStoreStudentIssueMkIssuingStore", null, null));
      }
      try {
          setInWsStoreStudentIssueMkIssuingStore(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStoreStudentIssueMkIssuingStore is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStoreStudentIssueMkIssuingStore", null, null));
      }
   }
 
   public short getInWsStoreStudentIssueStatus() {
      return importView.InWsStoreStudentIssueStatus;
   }
   public void setInWsStoreStudentIssueStatus(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InWsStoreStudentIssueStatus has more than 1 digits.",
               new PropertyChangeEvent (this, "InWsStoreStudentIssueStatus", null, null));
      }
      importView.InWsStoreStudentIssueStatus = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsStoreStudentIssueStatus(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStoreStudentIssueStatus is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStoreStudentIssueStatus", null, null));
      }
      try {
          setInWsStoreStudentIssueStatus(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStoreStudentIssueStatus is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStoreStudentIssueStatus", null, null));
      }
   }
 
   public int getInWsStoreStudentIssuePickerNr() {
      return importView.InWsStoreStudentIssuePickerNr;
   }
   public void setInWsStoreStudentIssuePickerNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InWsStoreStudentIssuePickerNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InWsStoreStudentIssuePickerNr", null, null));
      }
      importView.InWsStoreStudentIssuePickerNr = IntAttr.valueOf(s);
   }
   public void setAsStringInWsStoreStudentIssuePickerNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStoreStudentIssuePickerNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStoreStudentIssuePickerNr", null, null));
      }
      try {
          setInWsStoreStudentIssuePickerNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStoreStudentIssuePickerNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStoreStudentIssuePickerNr", null, null));
      }
   }
 
   public String getInWsStoreStudentIssuePenWorkstation() {
      return FixedStringAttr.valueOf(importView.InWsStoreStudentIssuePenWorkstation, 10);
   }
   public void setInWsStoreStudentIssuePenWorkstation(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InWsStoreStudentIssuePenWorkstation must be <= 10 characters.",
               new PropertyChangeEvent (this, "InWsStoreStudentIssuePenWorkstation", null, null));
      }
      importView.InWsStoreStudentIssuePenWorkstation = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInWsStoreStudentIssueDeliveryType() {
      return FixedStringAttr.valueOf(importView.InWsStoreStudentIssueDeliveryType, 1);
   }
   public void setInWsStoreStudentIssueDeliveryType(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStoreStudentIssueDeliveryType must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStoreStudentIssueDeliveryType", null, null));
      }
      importView.InWsStoreStudentIssueDeliveryType = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public Calendar getInWsStoreStudentIssueDateTime() {
      return TimestampAttr.toCalendar(importView.InWsStoreStudentIssueDateTime);
   }
   public String getAsStringInWsStoreStudentIssueDateTime() {
      return TimestampAttr.toString(importView.InWsStoreStudentIssueDateTime);
   }
   public void setInWsStoreStudentIssueDateTime(Calendar s)
      throws PropertyVetoException {
      importView.InWsStoreStudentIssueDateTime = TimestampAttr.valueOf(s);
   }
   public void setAsStringInWsStoreStudentIssueDateTime(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInWsStoreStudentIssueDateTime((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeTimestampFormatter.parse(s.length() > 17 ? s.substring(0, 17) : s));
            importView.InWsStoreStudentIssueDateTime = TimestampAttr.valueOf(s);
         } catch (ParseException e) {
            throw new PropertyVetoException("InWsStoreStudentIssueDateTime has an invalid format (yyyyMMddHHmmssSSSSSS).",
                  new PropertyChangeEvent (this, "InWsStoreStudentIssueDateTime", null, null));
         }
      }
   }
 
   public String getInWsStoreStudentIssueIssueFromOtherFlag() {
      return FixedStringAttr.valueOf(importView.InWsStoreStudentIssueIssueFromOtherFlag, 1);
   }
   public void setInWsStoreStudentIssueIssueFromOtherFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStoreStudentIssueIssueFromOtherFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStoreStudentIssueIssueFromOtherFlag", null, null));
      }
      importView.InWsStoreStudentIssueIssueFromOtherFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInWsRegionalOfficeCode() {
      return importView.InWsRegionalOfficeCode;
   }
   public void setInWsRegionalOfficeCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InWsRegionalOfficeCode has more than 2 digits.",
               new PropertyChangeEvent (this, "InWsRegionalOfficeCode", null, null));
      }
      importView.InWsRegionalOfficeCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsRegionalOfficeCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsRegionalOfficeCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsRegionalOfficeCode", null, null));
      }
      try {
          setInWsRegionalOfficeCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsRegionalOfficeCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsRegionalOfficeCode", null, null));
      }
   }
 
   public String getInWsRegionalOfficeEngDescription() {
      return FixedStringAttr.valueOf(importView.InWsRegionalOfficeEngDescription, 28);
   }
   public void setInWsRegionalOfficeEngDescription(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsRegionalOfficeEngDescription must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsRegionalOfficeEngDescription", null, null));
      }
      importView.InWsRegionalOfficeEngDescription = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public int getInWsAddressV2ReferenceNo() {
      return importView.InWsAddressV2ReferenceNo;
   }
   public void setInWsAddressV2ReferenceNo(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InWsAddressV2ReferenceNo has more than 8 digits.",
               new PropertyChangeEvent (this, "InWsAddressV2ReferenceNo", null, null));
      }
      importView.InWsAddressV2ReferenceNo = IntAttr.valueOf(s);
   }
   public void setAsStringInWsAddressV2ReferenceNo(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsAddressV2ReferenceNo is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsAddressV2ReferenceNo", null, null));
      }
      try {
          setInWsAddressV2ReferenceNo(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsAddressV2ReferenceNo is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsAddressV2ReferenceNo", null, null));
      }
   }
 
   public String getInWsAddressV2AddressReferenceFlag() {
      return FixedStringAttr.valueOf(importView.InWsAddressV2AddressReferenceFlag, 1);
   }
   public void setInWsAddressV2AddressReferenceFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsAddressV2AddressReferenceFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsAddressV2AddressReferenceFlag", null, null));
      }
      importView.InWsAddressV2AddressReferenceFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInWsAddressV2Type() {
      return importView.InWsAddressV2Type;
   }
   public void setInWsAddressV2Type(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InWsAddressV2Type has more than 2 digits.",
               new PropertyChangeEvent (this, "InWsAddressV2Type", null, null));
      }
      importView.InWsAddressV2Type = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsAddressV2Type(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsAddressV2Type is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsAddressV2Type", null, null));
      }
      try {
          setInWsAddressV2Type(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsAddressV2Type is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsAddressV2Type", null, null));
      }
   }
 
   public short getInWsAddressV2Category() {
      return importView.InWsAddressV2Category;
   }
   public void setInWsAddressV2Category(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InWsAddressV2Category has more than 3 digits.",
               new PropertyChangeEvent (this, "InWsAddressV2Category", null, null));
      }
      importView.InWsAddressV2Category = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsAddressV2Category(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsAddressV2Category is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsAddressV2Category", null, null));
      }
      try {
          setInWsAddressV2Category(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsAddressV2Category is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsAddressV2Category", null, null));
      }
   }
 
   public String getInWsAddressV2CategoryDescription() {
      return FixedStringAttr.valueOf(importView.InWsAddressV2CategoryDescription, 28);
   }
   public void setInWsAddressV2CategoryDescription(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsAddressV2CategoryDescription must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsAddressV2CategoryDescription", null, null));
      }
      importView.InWsAddressV2CategoryDescription = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInWsAddressV2AddressLine1() {
      return FixedStringAttr.valueOf(importView.InWsAddressV2AddressLine1, 28);
   }
   public void setInWsAddressV2AddressLine1(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsAddressV2AddressLine1 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsAddressV2AddressLine1", null, null));
      }
      importView.InWsAddressV2AddressLine1 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInWsAddressV2AddressLine2() {
      return FixedStringAttr.valueOf(importView.InWsAddressV2AddressLine2, 28);
   }
   public void setInWsAddressV2AddressLine2(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsAddressV2AddressLine2 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsAddressV2AddressLine2", null, null));
      }
      importView.InWsAddressV2AddressLine2 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInWsAddressV2AddressLine3() {
      return FixedStringAttr.valueOf(importView.InWsAddressV2AddressLine3, 28);
   }
   public void setInWsAddressV2AddressLine3(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsAddressV2AddressLine3 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsAddressV2AddressLine3", null, null));
      }
      importView.InWsAddressV2AddressLine3 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInWsAddressV2AddressLine4() {
      return FixedStringAttr.valueOf(importView.InWsAddressV2AddressLine4, 28);
   }
   public void setInWsAddressV2AddressLine4(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsAddressV2AddressLine4 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsAddressV2AddressLine4", null, null));
      }
      importView.InWsAddressV2AddressLine4 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInWsAddressV2AddressLine5() {
      return FixedStringAttr.valueOf(importView.InWsAddressV2AddressLine5, 28);
   }
   public void setInWsAddressV2AddressLine5(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsAddressV2AddressLine5 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsAddressV2AddressLine5", null, null));
      }
      importView.InWsAddressV2AddressLine5 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInWsAddressV2AddressLine6() {
      return FixedStringAttr.valueOf(importView.InWsAddressV2AddressLine6, 28);
   }
   public void setInWsAddressV2AddressLine6(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsAddressV2AddressLine6 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsAddressV2AddressLine6", null, null));
      }
      importView.InWsAddressV2AddressLine6 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public short getInWsAddressV2PostalCode() {
      return importView.InWsAddressV2PostalCode;
   }
   public void setInWsAddressV2PostalCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InWsAddressV2PostalCode has more than 4 digits.",
               new PropertyChangeEvent (this, "InWsAddressV2PostalCode", null, null));
      }
      importView.InWsAddressV2PostalCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsAddressV2PostalCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsAddressV2PostalCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsAddressV2PostalCode", null, null));
      }
      try {
          setInWsAddressV2PostalCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsAddressV2PostalCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsAddressV2PostalCode", null, null));
      }
   }
 
   public String getInWsAddressV2HomeNumber() {
      return FixedStringAttr.valueOf(importView.InWsAddressV2HomeNumber, 28);
   }
   public void setInWsAddressV2HomeNumber(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsAddressV2HomeNumber must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsAddressV2HomeNumber", null, null));
      }
      importView.InWsAddressV2HomeNumber = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInWsAddressV2WorkNumber() {
      return FixedStringAttr.valueOf(importView.InWsAddressV2WorkNumber, 28);
   }
   public void setInWsAddressV2WorkNumber(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsAddressV2WorkNumber must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsAddressV2WorkNumber", null, null));
      }
      importView.InWsAddressV2WorkNumber = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInWsAddressV2FaxNumber() {
      return FixedStringAttr.valueOf(importView.InWsAddressV2FaxNumber, 28);
   }
   public void setInWsAddressV2FaxNumber(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsAddressV2FaxNumber must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsAddressV2FaxNumber", null, null));
      }
      importView.InWsAddressV2FaxNumber = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInWsAddressV2CellNumber() {
      return FixedStringAttr.valueOf(importView.InWsAddressV2CellNumber, 20);
   }
   public void setInWsAddressV2CellNumber(String s)
      throws PropertyVetoException {
      if (s.length() > 20) {
         throw new PropertyVetoException("InWsAddressV2CellNumber must be <= 20 characters.",
               new PropertyChangeEvent (this, "InWsAddressV2CellNumber", null, null));
      }
      importView.InWsAddressV2CellNumber = FixedStringAttr.valueOf(s, (short)20);
   }
 
   public String getInWsAddressV2EmailAddress() {
      return FixedStringAttr.valueOf(importView.InWsAddressV2EmailAddress, 60);
   }
   public void setInWsAddressV2EmailAddress(String s)
      throws PropertyVetoException {
      if (s.length() > 60) {
         throw new PropertyVetoException("InWsAddressV2EmailAddress must be <= 60 characters.",
               new PropertyChangeEvent (this, "InWsAddressV2EmailAddress", null, null));
      }
      importView.InWsAddressV2EmailAddress = FixedStringAttr.valueOf(s, (short)60);
   }
 
   public short getInWsAddressV2ErrorCode() {
      return importView.InWsAddressV2ErrorCode;
   }
   public void setInWsAddressV2ErrorCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InWsAddressV2ErrorCode has more than 2 digits.",
               new PropertyChangeEvent (this, "InWsAddressV2ErrorCode", null, null));
      }
      importView.InWsAddressV2ErrorCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsAddressV2ErrorCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsAddressV2ErrorCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsAddressV2ErrorCode", null, null));
      }
      try {
          setInWsAddressV2ErrorCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsAddressV2ErrorCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsAddressV2ErrorCode", null, null));
      }
   }
 
   public int getInWsAddressV2UserNumber() {
      return importView.InWsAddressV2UserNumber;
   }
   public void setInWsAddressV2UserNumber(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InWsAddressV2UserNumber has more than 5 digits.",
               new PropertyChangeEvent (this, "InWsAddressV2UserNumber", null, null));
      }
      importView.InWsAddressV2UserNumber = IntAttr.valueOf(s);
   }
   public void setAsStringInWsAddressV2UserNumber(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsAddressV2UserNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsAddressV2UserNumber", null, null));
      }
      try {
          setInWsAddressV2UserNumber(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsAddressV2UserNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsAddressV2UserNumber", null, null));
      }
   }
 
   public String getInWsAddressV2CourierContactNo() {
      return FixedStringAttr.valueOf(importView.InWsAddressV2CourierContactNo, 40);
   }
   public void setInWsAddressV2CourierContactNo(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 40) {
         throw new PropertyVetoException("InWsAddressV2CourierContactNo must be <= 40 characters.",
               new PropertyChangeEvent (this, "InWsAddressV2CourierContactNo", null, null));
      }
      importView.InWsAddressV2CourierContactNo = FixedStringAttr.valueOf(s, (short)40);
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
 
   public String getInWsStudentPreviousSurname() {
      return FixedStringAttr.valueOf(importView.InWsStudentPreviousSurname, 28);
   }
   public void setInWsStudentPreviousSurname(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsStudentPreviousSurname must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsStudentPreviousSurname", null, null));
      }
      importView.InWsStudentPreviousSurname = FixedStringAttr.valueOf(s, (short)28);
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
 
   public String getInWsStudentMkHomeLanguage() {
      return FixedStringAttr.valueOf(importView.InWsStudentMkHomeLanguage, 2);
   }
   public void setInWsStudentMkHomeLanguage(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InWsStudentMkHomeLanguage must be <= 2 characters.",
               new PropertyChangeEvent (this, "InWsStudentMkHomeLanguage", null, null));
      }
      importView.InWsStudentMkHomeLanguage = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public String getInWsStudentMkCorrespondenceLanguage() {
      return FixedStringAttr.valueOf(importView.InWsStudentMkCorrespondenceLanguage, 2);
   }
   public void setInWsStudentMkCorrespondenceLanguage(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InWsStudentMkCorrespondenceLanguage must be <= 2 characters.",
               new PropertyChangeEvent (this, "InWsStudentMkCorrespondenceLanguage", null, null));
      }
      importView.InWsStudentMkCorrespondenceLanguage = FixedStringAttr.valueOf(s, (short)2);
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
 
   public short getInStudentAnnualRecordMkAcademicYear() {
      return importView.InStudentAnnualRecordMkAcademicYear;
   }
   public void setInStudentAnnualRecordMkAcademicYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InStudentAnnualRecordMkAcademicYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordMkAcademicYear", null, null));
      }
      importView.InStudentAnnualRecordMkAcademicYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentAnnualRecordMkAcademicYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAnnualRecordMkAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAnnualRecordMkAcademicYear", null, null));
      }
      try {
          setInStudentAnnualRecordMkAcademicYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAnnualRecordMkAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAnnualRecordMkAcademicYear", null, null));
      }
   }
 
   public short getInStudentAnnualRecordMkAcademicPeriod() {
      return importView.InStudentAnnualRecordMkAcademicPeriod;
   }
   public void setInStudentAnnualRecordMkAcademicPeriod(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InStudentAnnualRecordMkAcademicPeriod has more than 2 digits.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordMkAcademicPeriod", null, null));
      }
      importView.InStudentAnnualRecordMkAcademicPeriod = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentAnnualRecordMkAcademicPeriod(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAnnualRecordMkAcademicPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAnnualRecordMkAcademicPeriod", null, null));
      }
      try {
          setInStudentAnnualRecordMkAcademicPeriod(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAnnualRecordMkAcademicPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAnnualRecordMkAcademicPeriod", null, null));
      }
   }
 
   public short getInStudentAnnualRecordMkRegionalOfficeCode() {
      return importView.InStudentAnnualRecordMkRegionalOfficeCode;
   }
   public void setInStudentAnnualRecordMkRegionalOfficeCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InStudentAnnualRecordMkRegionalOfficeCode has more than 2 digits.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordMkRegionalOfficeCode", null, null));
      }
      importView.InStudentAnnualRecordMkRegionalOfficeCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentAnnualRecordMkRegionalOfficeCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAnnualRecordMkRegionalOfficeCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAnnualRecordMkRegionalOfficeCode", null, null));
      }
      try {
          setInStudentAnnualRecordMkRegionalOfficeCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAnnualRecordMkRegionalOfficeCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAnnualRecordMkRegionalOfficeCode", null, null));
      }
   }
 
   public String getInStudentAnnualRecordRegistrationMethodCode() {
      return FixedStringAttr.valueOf(importView.InStudentAnnualRecordRegistrationMethodCode, 1);
   }
   public void setInStudentAnnualRecordRegistrationMethodCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Smsij01sMntGenDespatchInfoStudentAnnualRecordRegistrationMethodCodePropertyEditor pe = new Smsij01sMntGenDespatchInfoStudentAnnualRecordRegistrationMethodCodePropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InStudentAnnualRecordRegistrationMethodCode value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordRegistrationMethodCode", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InStudentAnnualRecordRegistrationMethodCode must be <= 1 characters.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordRegistrationMethodCode", null, null));
      }
      importView.InStudentAnnualRecordRegistrationMethodCode = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInStudentAnnualRecordDespatchMethodCode() {
      return FixedStringAttr.valueOf(importView.InStudentAnnualRecordDespatchMethodCode, 1);
   }
   public void setInStudentAnnualRecordDespatchMethodCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InStudentAnnualRecordDespatchMethodCode must be <= 1 characters.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordDespatchMethodCode", null, null));
      }
      importView.InStudentAnnualRecordDespatchMethodCode = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInStudentAnnualRecordStatusCode() {
      return FixedStringAttr.valueOf(importView.InStudentAnnualRecordStatusCode, 2);
   }
   public void setInStudentAnnualRecordStatusCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InStudentAnnualRecordStatusCode must be <= 2 characters.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordStatusCode", null, null));
      }
      importView.InStudentAnnualRecordStatusCode = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public String getInStudentAnnualRecordMkHighestQualificationCode() {
      return FixedStringAttr.valueOf(importView.InStudentAnnualRecordMkHighestQualificationCode, 5);
   }
   public void setInStudentAnnualRecordMkHighestQualificationCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InStudentAnnualRecordMkHighestQualificationCode must be <= 5 characters.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordMkHighestQualificationCode", null, null));
      }
      importView.InStudentAnnualRecordMkHighestQualificationCode = FixedStringAttr.valueOf(s, (short)5);
   }
 
   public String getInStudentAnnualRecordLateRegistrationFlag() {
      return FixedStringAttr.valueOf(importView.InStudentAnnualRecordLateRegistrationFlag, 1);
   }
   public void setInStudentAnnualRecordLateRegistrationFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Smsij01sMntGenDespatchInfoStudentAnnualRecordLateRegistrationFlagPropertyEditor pe = new Smsij01sMntGenDespatchInfoStudentAnnualRecordLateRegistrationFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InStudentAnnualRecordLateRegistrationFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordLateRegistrationFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InStudentAnnualRecordLateRegistrationFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordLateRegistrationFlag", null, null));
      }
      importView.InStudentAnnualRecordLateRegistrationFlag = FixedStringAttr.valueOf(s, (short)1);
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
 
   public final int InGroupMax = 30;
   public short getInGroupCount() {
      return (short)(importView.InGroup_MA);
   };
 
   public void setInGroupCount(short s) throws PropertyVetoException {
      if (s < 0 || s > InGroupMax) {
         throw new PropertyVetoException("InGroupCount value is not a valid value. (0 to 30)",
               new PropertyChangeEvent (this, "InGroupCount", null, null));
      } else {
         importView.InGroup_MA = IntAttr.valueOf((int)s);
      }
   }
 
   public String getInhpGWsQualificationCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InhpGWsQualificationCode[index], 5);
   }
   public void setInhpGWsQualificationCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InhpGWsQualificationCode must be <= 5 characters.",
               new PropertyChangeEvent (this, "InhpGWsQualificationCode", null, null));
      }
      importView.InhpGWsQualificationCode[index] = FixedStringAttr.valueOf(s, (short)5);
   }
 
   public String getInhpGWsQualificationType(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InhpGWsQualificationType[index], 1);
   }
   public void setInhpGWsQualificationType(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      Smsij01sMntGenDespatchInfoWsQualificationTypePropertyEditor pe = new Smsij01sMntGenDespatchInfoWsQualificationTypePropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InhpGWsQualificationType value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InhpGWsQualificationType", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InhpGWsQualificationType must be <= 1 characters.",
               new PropertyChangeEvent (this, "InhpGWsQualificationType", null, null));
      }
      importView.InhpGWsQualificationType[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInhpGWsQualificationShortDescription(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InhpGWsQualificationShortDescription[index], 12);
   }
   public void setInhpGWsQualificationShortDescription(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s.length() > 12) {
         throw new PropertyVetoException("InhpGWsQualificationShortDescription must be <= 12 characters.",
               new PropertyChangeEvent (this, "InhpGWsQualificationShortDescription", null, null));
      }
      importView.InhpGWsQualificationShortDescription[index] = FixedStringAttr.valueOf(s, (short)12);
   }
 
   public String getInGWsQualificationCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGWsQualificationCode[index], 5);
   }
   public void setInGWsQualificationCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InGWsQualificationCode must be <= 5 characters.",
               new PropertyChangeEvent (this, "InGWsQualificationCode", null, null));
      }
      importView.InGWsQualificationCode[index] = FixedStringAttr.valueOf(s, (short)5);
   }
 
   public String getInGWsQualificationType(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGWsQualificationType[index], 1);
   }
   public void setInGWsQualificationType(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      Smsij01sMntGenDespatchInfoWsQualificationTypePropertyEditor pe = new Smsij01sMntGenDespatchInfoWsQualificationTypePropertyEditor();
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
 
   public String getInGWsQualificationShortDescription(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGWsQualificationShortDescription[index], 12);
   }
   public void setInGWsQualificationShortDescription(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s.length() > 12) {
         throw new PropertyVetoException("InGWsQualificationShortDescription must be <= 12 characters.",
               new PropertyChangeEvent (this, "InGWsQualificationShortDescription", null, null));
      }
      importView.InGWsQualificationShortDescription[index] = FixedStringAttr.valueOf(s, (short)12);
   }
 
   public String getInhpGStudentStudyUnitMkStudyUnitCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InhpGStudentStudyUnitMkStudyUnitCode[index], 7);
   }
   public void setInhpGStudentStudyUnitMkStudyUnitCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InhpGStudentStudyUnitMkStudyUnitCode must be <= 7 characters.",
               new PropertyChangeEvent (this, "InhpGStudentStudyUnitMkStudyUnitCode", null, null));
      }
      importView.InhpGStudentStudyUnitMkStudyUnitCode[index] = FixedStringAttr.valueOf(s, (short)7);
   }
 
   public String getInhpGStudentStudyUnitMkCourseStudyUnitCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InhpGStudentStudyUnitMkCourseStudyUnitCode[index], 7);
   }
   public void setInhpGStudentStudyUnitMkCourseStudyUnitCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InhpGStudentStudyUnitMkCourseStudyUnitCode must be <= 7 characters.",
               new PropertyChangeEvent (this, "InhpGStudentStudyUnitMkCourseStudyUnitCode", null, null));
      }
      importView.InhpGStudentStudyUnitMkCourseStudyUnitCode[index] = FixedStringAttr.valueOf(s, (short)7);
   }
 
   public String getInhpGStudentStudyUnitMkStudyUnitOptionCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InhpGStudentStudyUnitMkStudyUnitOptionCode[index], 1);
   }
   public void setInhpGStudentStudyUnitMkStudyUnitOptionCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InhpGStudentStudyUnitMkStudyUnitOptionCode must be <= 1 characters.",
               new PropertyChangeEvent (this, "InhpGStudentStudyUnitMkStudyUnitOptionCode", null, null));
      }
      importView.InhpGStudentStudyUnitMkStudyUnitOptionCode[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInhpGStudentStudyUnitMkQualificationCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InhpGStudentStudyUnitMkQualificationCode[index], 5);
   }
   public void setInhpGStudentStudyUnitMkQualificationCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InhpGStudentStudyUnitMkQualificationCode must be <= 5 characters.",
               new PropertyChangeEvent (this, "InhpGStudentStudyUnitMkQualificationCode", null, null));
      }
      importView.InhpGStudentStudyUnitMkQualificationCode[index] = FixedStringAttr.valueOf(s, (short)5);
   }
 
   public String getInhpGStudentStudyUnitStatusCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InhpGStudentStudyUnitStatusCode[index], 2);
   }
   public void setInhpGStudentStudyUnitStatusCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InhpGStudentStudyUnitStatusCode must be <= 2 characters.",
               new PropertyChangeEvent (this, "InhpGStudentStudyUnitStatusCode", null, null));
      }
      importView.InhpGStudentStudyUnitStatusCode[index] = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public Calendar getInhpGStudentStudyUnitRegistrationDate(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return DateAttr.toCalendar(importView.InhpGStudentStudyUnitRegistrationDate[index]);
   }
   public int getAsIntInhpGStudentStudyUnitRegistrationDate(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return DateAttr.toInt(importView.InhpGStudentStudyUnitRegistrationDate[index]);
   }
   public void setInhpGStudentStudyUnitRegistrationDate(int index, Calendar s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      importView.InhpGStudentStudyUnitRegistrationDate[index] = DateAttr.valueOf(s);
   }
   public void setAsStringInhpGStudentStudyUnitRegistrationDate(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s == null || s.length() == 0) {
         setInhpGStudentStudyUnitRegistrationDate(index, (Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInhpGStudentStudyUnitRegistrationDate(index, tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InhpGStudentStudyUnitRegistrationDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InhpGStudentStudyUnitRegistrationDate", null, null));
         }
      }
   }
   public void setAsIntInhpGStudentStudyUnitRegistrationDate(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInhpGStudentStudyUnitRegistrationDate(index, temp);
   }
 
   public Calendar getInhpGStudentStudyUnitCancellationDate(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return DateAttr.toCalendar(importView.InhpGStudentStudyUnitCancellationDate[index]);
   }
   public int getAsIntInhpGStudentStudyUnitCancellationDate(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return DateAttr.toInt(importView.InhpGStudentStudyUnitCancellationDate[index]);
   }
   public void setInhpGStudentStudyUnitCancellationDate(int index, Calendar s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      importView.InhpGStudentStudyUnitCancellationDate[index] = DateAttr.valueOf(s);
   }
   public void setAsStringInhpGStudentStudyUnitCancellationDate(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s == null || s.length() == 0) {
         setInhpGStudentStudyUnitCancellationDate(index, (Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInhpGStudentStudyUnitCancellationDate(index, tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InhpGStudentStudyUnitCancellationDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InhpGStudentStudyUnitCancellationDate", null, null));
         }
      }
   }
   public void setAsIntInhpGStudentStudyUnitCancellationDate(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInhpGStudentStudyUnitCancellationDate(index, temp);
   }
 
   public String getInhpGStudentStudyUnitLanguageCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InhpGStudentStudyUnitLanguageCode[index], 1);
   }
   public void setInhpGStudentStudyUnitLanguageCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      Smsij01sMntGenDespatchInfoStudentStudyUnitLanguageCodePropertyEditor pe = new Smsij01sMntGenDespatchInfoStudentStudyUnitLanguageCodePropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InhpGStudentStudyUnitLanguageCode value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InhpGStudentStudyUnitLanguageCode", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InhpGStudentStudyUnitLanguageCode must be <= 1 characters.",
               new PropertyChangeEvent (this, "InhpGStudentStudyUnitLanguageCode", null, null));
      }
      importView.InhpGStudentStudyUnitLanguageCode[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInhpGStudentStudyUnitDespatchComment(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InhpGStudentStudyUnitDespatchComment[index], 30);
   }
   public void setInhpGStudentStudyUnitDespatchComment(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s.length() > 30) {
         throw new PropertyVetoException("InhpGStudentStudyUnitDespatchComment must be <= 30 characters.",
               new PropertyChangeEvent (this, "InhpGStudentStudyUnitDespatchComment", null, null));
      }
      importView.InhpGStudentStudyUnitDespatchComment[index] = FixedStringAttr.valueOf(s, (short)30);
   }
 
   public short getInhpGStudentStudyUnitSemesterPeriod(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return importView.InhpGStudentStudyUnitSemesterPeriod[index];
   }
   public void setInhpGStudentStudyUnitSemesterPeriod(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InhpGStudentStudyUnitSemesterPeriod has more than 1 digits.",
               new PropertyChangeEvent (this, "InhpGStudentStudyUnitSemesterPeriod", null, null));
      }
      importView.InhpGStudentStudyUnitSemesterPeriod[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInhpGStudentStudyUnitSemesterPeriod(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InhpGStudentStudyUnitSemesterPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InhpGStudentStudyUnitSemesterPeriod", null, null));
      }
      try {
          setInhpGStudentStudyUnitSemesterPeriod(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InhpGStudentStudyUnitSemesterPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InhpGStudentStudyUnitSemesterPeriod", null, null));
      }
   }
 
   public String getInhpGDespatchCommentMkStudyUnitCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InhpGDespatchCommentMkStudyUnitCode[index], 7);
   }
   public void setInhpGDespatchCommentMkStudyUnitCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InhpGDespatchCommentMkStudyUnitCode must be <= 7 characters.",
               new PropertyChangeEvent (this, "InhpGDespatchCommentMkStudyUnitCode", null, null));
      }
      importView.InhpGDespatchCommentMkStudyUnitCode[index] = FixedStringAttr.valueOf(s, (short)7);
   }
 
   public short getInhpGDespatchCommentSemesterPeriod(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return importView.InhpGDespatchCommentSemesterPeriod[index];
   }
   public void setInhpGDespatchCommentSemesterPeriod(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InhpGDespatchCommentSemesterPeriod has more than 1 digits.",
               new PropertyChangeEvent (this, "InhpGDespatchCommentSemesterPeriod", null, null));
      }
      importView.InhpGDespatchCommentSemesterPeriod[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInhpGDespatchCommentSemesterPeriod(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InhpGDespatchCommentSemesterPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InhpGDespatchCommentSemesterPeriod", null, null));
      }
      try {
          setInhpGDespatchCommentSemesterPeriod(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InhpGDespatchCommentSemesterPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InhpGDespatchCommentSemesterPeriod", null, null));
      }
   }
 
   public short getInhpGDespatchCommentSequenceNumber(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return importView.InhpGDespatchCommentSequenceNumber[index];
   }
   public void setInhpGDespatchCommentSequenceNumber(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InhpGDespatchCommentSequenceNumber has more than 1 digits.",
               new PropertyChangeEvent (this, "InhpGDespatchCommentSequenceNumber", null, null));
      }
      importView.InhpGDespatchCommentSequenceNumber[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInhpGDespatchCommentSequenceNumber(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InhpGDespatchCommentSequenceNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InhpGDespatchCommentSequenceNumber", null, null));
      }
      try {
          setInhpGDespatchCommentSequenceNumber(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InhpGDespatchCommentSequenceNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InhpGDespatchCommentSequenceNumber", null, null));
      }
   }
 
   public String getInhpGDespatchCommentDespatchComment(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InhpGDespatchCommentDespatchComment[index], 80);
   }
   public void setInhpGDespatchCommentDespatchComment(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s.length() > 80) {
         throw new PropertyVetoException("InhpGDespatchCommentDespatchComment must be <= 80 characters.",
               new PropertyChangeEvent (this, "InhpGDespatchCommentDespatchComment", null, null));
      }
      importView.InhpGDespatchCommentDespatchComment[index] = FixedStringAttr.valueOf(s, (short)80);
   }
 
   public String getInGStudentStudyUnitMkStudyUnitCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGStudentStudyUnitMkStudyUnitCode[index], 7);
   }
   public void setInGStudentStudyUnitMkStudyUnitCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InGStudentStudyUnitMkStudyUnitCode must be <= 7 characters.",
               new PropertyChangeEvent (this, "InGStudentStudyUnitMkStudyUnitCode", null, null));
      }
      importView.InGStudentStudyUnitMkStudyUnitCode[index] = FixedStringAttr.valueOf(s, (short)7);
   }
 
   public String getInGStudentStudyUnitMkCourseStudyUnitCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGStudentStudyUnitMkCourseStudyUnitCode[index], 7);
   }
   public void setInGStudentStudyUnitMkCourseStudyUnitCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InGStudentStudyUnitMkCourseStudyUnitCode must be <= 7 characters.",
               new PropertyChangeEvent (this, "InGStudentStudyUnitMkCourseStudyUnitCode", null, null));
      }
      importView.InGStudentStudyUnitMkCourseStudyUnitCode[index] = FixedStringAttr.valueOf(s, (short)7);
   }
 
   public String getInGStudentStudyUnitMkStudyUnitOptionCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGStudentStudyUnitMkStudyUnitOptionCode[index], 1);
   }
   public void setInGStudentStudyUnitMkStudyUnitOptionCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGStudentStudyUnitMkStudyUnitOptionCode must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGStudentStudyUnitMkStudyUnitOptionCode", null, null));
      }
      importView.InGStudentStudyUnitMkStudyUnitOptionCode[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInGStudentStudyUnitMkQualificationCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGStudentStudyUnitMkQualificationCode[index], 5);
   }
   public void setInGStudentStudyUnitMkQualificationCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InGStudentStudyUnitMkQualificationCode must be <= 5 characters.",
               new PropertyChangeEvent (this, "InGStudentStudyUnitMkQualificationCode", null, null));
      }
      importView.InGStudentStudyUnitMkQualificationCode[index] = FixedStringAttr.valueOf(s, (short)5);
   }
 
   public String getInGStudentStudyUnitStatusCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGStudentStudyUnitStatusCode[index], 2);
   }
   public void setInGStudentStudyUnitStatusCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InGStudentStudyUnitStatusCode must be <= 2 characters.",
               new PropertyChangeEvent (this, "InGStudentStudyUnitStatusCode", null, null));
      }
      importView.InGStudentStudyUnitStatusCode[index] = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public Calendar getInGStudentStudyUnitRegistrationDate(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return DateAttr.toCalendar(importView.InGStudentStudyUnitRegistrationDate[index]);
   }
   public int getAsIntInGStudentStudyUnitRegistrationDate(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return DateAttr.toInt(importView.InGStudentStudyUnitRegistrationDate[index]);
   }
   public void setInGStudentStudyUnitRegistrationDate(int index, Calendar s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      importView.InGStudentStudyUnitRegistrationDate[index] = DateAttr.valueOf(s);
   }
   public void setAsStringInGStudentStudyUnitRegistrationDate(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s == null || s.length() == 0) {
         setInGStudentStudyUnitRegistrationDate(index, (Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInGStudentStudyUnitRegistrationDate(index, tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InGStudentStudyUnitRegistrationDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InGStudentStudyUnitRegistrationDate", null, null));
         }
      }
   }
   public void setAsIntInGStudentStudyUnitRegistrationDate(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInGStudentStudyUnitRegistrationDate(index, temp);
   }
 
   public Calendar getInGStudentStudyUnitCancellationDate(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return DateAttr.toCalendar(importView.InGStudentStudyUnitCancellationDate[index]);
   }
   public int getAsIntInGStudentStudyUnitCancellationDate(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return DateAttr.toInt(importView.InGStudentStudyUnitCancellationDate[index]);
   }
   public void setInGStudentStudyUnitCancellationDate(int index, Calendar s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      importView.InGStudentStudyUnitCancellationDate[index] = DateAttr.valueOf(s);
   }
   public void setAsStringInGStudentStudyUnitCancellationDate(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s == null || s.length() == 0) {
         setInGStudentStudyUnitCancellationDate(index, (Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInGStudentStudyUnitCancellationDate(index, tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InGStudentStudyUnitCancellationDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InGStudentStudyUnitCancellationDate", null, null));
         }
      }
   }
   public void setAsIntInGStudentStudyUnitCancellationDate(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInGStudentStudyUnitCancellationDate(index, temp);
   }
 
   public String getInGStudentStudyUnitLanguageCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGStudentStudyUnitLanguageCode[index], 1);
   }
   public void setInGStudentStudyUnitLanguageCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      Smsij01sMntGenDespatchInfoStudentStudyUnitLanguageCodePropertyEditor pe = new Smsij01sMntGenDespatchInfoStudentStudyUnitLanguageCodePropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InGStudentStudyUnitLanguageCode value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InGStudentStudyUnitLanguageCode", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGStudentStudyUnitLanguageCode must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGStudentStudyUnitLanguageCode", null, null));
      }
      importView.InGStudentStudyUnitLanguageCode[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInGStudentStudyUnitDespatchComment(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGStudentStudyUnitDespatchComment[index], 30);
   }
   public void setInGStudentStudyUnitDespatchComment(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s.length() > 30) {
         throw new PropertyVetoException("InGStudentStudyUnitDespatchComment must be <= 30 characters.",
               new PropertyChangeEvent (this, "InGStudentStudyUnitDespatchComment", null, null));
      }
      importView.InGStudentStudyUnitDespatchComment[index] = FixedStringAttr.valueOf(s, (short)30);
   }
 
   public short getInGStudentStudyUnitSemesterPeriod(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return importView.InGStudentStudyUnitSemesterPeriod[index];
   }
   public void setInGStudentStudyUnitSemesterPeriod(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InGStudentStudyUnitSemesterPeriod has more than 1 digits.",
               new PropertyChangeEvent (this, "InGStudentStudyUnitSemesterPeriod", null, null));
      }
      importView.InGStudentStudyUnitSemesterPeriod[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGStudentStudyUnitSemesterPeriod(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGStudentStudyUnitSemesterPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGStudentStudyUnitSemesterPeriod", null, null));
      }
      try {
          setInGStudentStudyUnitSemesterPeriod(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGStudentStudyUnitSemesterPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGStudentStudyUnitSemesterPeriod", null, null));
      }
   }
 
   public String getInGDespatchCommentMkStudyUnitCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGDespatchCommentMkStudyUnitCode[index], 7);
   }
   public void setInGDespatchCommentMkStudyUnitCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InGDespatchCommentMkStudyUnitCode must be <= 7 characters.",
               new PropertyChangeEvent (this, "InGDespatchCommentMkStudyUnitCode", null, null));
      }
      importView.InGDespatchCommentMkStudyUnitCode[index] = FixedStringAttr.valueOf(s, (short)7);
   }
 
   public short getInGDespatchCommentSemesterPeriod(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return importView.InGDespatchCommentSemesterPeriod[index];
   }
   public void setInGDespatchCommentSemesterPeriod(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InGDespatchCommentSemesterPeriod has more than 1 digits.",
               new PropertyChangeEvent (this, "InGDespatchCommentSemesterPeriod", null, null));
      }
      importView.InGDespatchCommentSemesterPeriod[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGDespatchCommentSemesterPeriod(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGDespatchCommentSemesterPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGDespatchCommentSemesterPeriod", null, null));
      }
      try {
          setInGDespatchCommentSemesterPeriod(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGDespatchCommentSemesterPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGDespatchCommentSemesterPeriod", null, null));
      }
   }
 
   public short getInGDespatchCommentSequenceNumber(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return importView.InGDespatchCommentSequenceNumber[index];
   }
   public void setInGDespatchCommentSequenceNumber(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InGDespatchCommentSequenceNumber has more than 1 digits.",
               new PropertyChangeEvent (this, "InGDespatchCommentSequenceNumber", null, null));
      }
      importView.InGDespatchCommentSequenceNumber[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGDespatchCommentSequenceNumber(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGDespatchCommentSequenceNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGDespatchCommentSequenceNumber", null, null));
      }
      try {
          setInGDespatchCommentSequenceNumber(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGDespatchCommentSequenceNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGDespatchCommentSequenceNumber", null, null));
      }
   }
 
   public String getInGDespatchCommentDespatchComment(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGDespatchCommentDespatchComment[index], 80);
   }
   public void setInGDespatchCommentDespatchComment(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s.length() > 80) {
         throw new PropertyVetoException("InGDespatchCommentDespatchComment must be <= 80 characters.",
               new PropertyChangeEvent (this, "InGDespatchCommentDespatchComment", null, null));
      }
      importView.InGDespatchCommentDespatchComment[index] = FixedStringAttr.valueOf(s, (short)80);
   }
 
   public String getInGCsfLineActionBarAction(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGCsfLineActionBarAction[index], 1);
   }
   public void setInGCsfLineActionBarAction(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGCsfLineActionBarAction must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGCsfLineActionBarAction", null, null));
      }
      importView.InGCsfLineActionBarAction[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInGCsfLineActionBarLineReturnCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return importView.InGCsfLineActionBarLineReturnCode[index];
   }
   public void setInGCsfLineActionBarLineReturnCode(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InGCsfLineActionBarLineReturnCode has more than 4 digits.",
               new PropertyChangeEvent (this, "InGCsfLineActionBarLineReturnCode", null, null));
      }
      importView.InGCsfLineActionBarLineReturnCode[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGCsfLineActionBarLineReturnCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGCsfLineActionBarLineReturnCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGCsfLineActionBarLineReturnCode", null, null));
      }
      try {
          setInGCsfLineActionBarLineReturnCode(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGCsfLineActionBarLineReturnCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGCsfLineActionBarLineReturnCode", null, null));
      }
   }
 
   public String getInGCsfLineActionBarSelectionFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGCsfLineActionBarSelectionFlag[index], 1);
   }
   public void setInGCsfLineActionBarSelectionFlag(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGCsfLineActionBarSelectionFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGCsfLineActionBarSelectionFlag", null, null));
      }
      importView.InGCsfLineActionBarSelectionFlag[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInGCsfLineActionBarTranslatedAction(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGCsfLineActionBarTranslatedAction[index], 1);
   }
   public void setInGCsfLineActionBarTranslatedAction(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGCsfLineActionBarTranslatedAction must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGCsfLineActionBarTranslatedAction", null, null));
      }
      importView.InGCsfLineActionBarTranslatedAction[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInGCsfPromptPrompt1(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGCsfPromptPrompt1[index], 1);
   }
   public void setInGCsfPromptPrompt1(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGCsfPromptPrompt1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGCsfPromptPrompt1", null, null));
      }
      importView.InGCsfPromptPrompt1[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getOutEmailToCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutEmailToCsfStringsString132, 132);
   }
 
   public final int OutGrpMax = 30;
   public short getOutGrpCount() {
      return (short)(exportView.OutGrp_MA);
   };
 
   public short getOutGrWsParcelMkAcademicYear(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return exportView.OutGrWsParcelMkAcademicYear[index];
   }
 
   public int getOutGrWsParcelMkStudentNr(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return exportView.OutGrWsParcelMkStudentNr[index];
   }
 
   public short getOutGrWsParcelMNumber(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return exportView.OutGrWsParcelMNumber[index];
   }
 
   public Calendar getOutGrWsCourierboxBoxedDate(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return DateAttr.toCalendar(exportView.OutGrWsCourierboxBoxedDate[index]);
   }
   public int getAsIntOutGrWsCourierboxBoxedDate(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return DateAttr.toInt(exportView.OutGrWsCourierboxBoxedDate[index]);
   }
 
   public short getOutGrWsCourierboxBoxNr(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return exportView.OutGrWsCourierboxBoxNr[index];
   }
 
   public short getOutGrWsCourierboxMkIssuingStore(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return exportView.OutGrWsCourierboxMkIssuingStore[index];
   }
 
   public short getOutGrWsCourierboxMkDestinationStore(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return exportView.OutGrWsCourierboxMkDestinationStore[index];
   }
 
   public String getOutGrWsCourierboxMkAccountType(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGrWsCourierboxMkAccountType[index], 4);
   }
 
   public short getOutGrWsCourierboxMkBranchCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return exportView.OutGrWsCourierboxMkBranchCode[index];
   }
 
   public String getOutGrWsCourierboxWaybillNr(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return StringAttr.valueOf(exportView.OutGrWsCourierboxWaybillNr[index]);
   }
 
   public String getOutEmailFromCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutEmailFromCsfStringsString132, 132);
   }
 
   public short getOutWsParcelMkAcademicYear() {
      return exportView.OutWsParcelMkAcademicYear;
   }
 
   public int getOutWsParcelMkStudentNr() {
      return exportView.OutWsParcelMkStudentNr;
   }
 
   public short getOutWsParcelMNumber() {
      return exportView.OutWsParcelMNumber;
   }
 
   public String getOutEmailOrFaxCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.OutEmailOrFaxCsfStringsString1, 1);
   }
 
   public int getOutWsStudentAnnualRecordMkStudentNr() {
      return exportView.OutWsStudentAnnualRecordMkStudentNr;
   }
 
   public short getOutWsStudentAnnualRecordMkAcademicYear() {
      return exportView.OutWsStudentAnnualRecordMkAcademicYear;
   }
 
   public short getOutWsStudentAnnualRecordMkAcademicPeriod() {
      return exportView.OutWsStudentAnnualRecordMkAcademicPeriod;
   }
 
   public String getOutNameCsfStringsString50() {
      return FixedStringAttr.valueOf(exportView.OutNameCsfStringsString50, 50);
   }
 
   public String getOutNameCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutNameCsfStringsString132, 132);
   }
 
   public String getOutSecurityWsPrinterCode() {
      return FixedStringAttr.valueOf(exportView.OutSecurityWsPrinterCode, 10);
   }
 
   public int getOutSecurityWsUserNumber() {
      return exportView.OutSecurityWsUserNumber;
   }
 
   public int getOutNosWsAddressReferenceNo() {
      return exportView.OutNosWsAddressReferenceNo;
   }
 
   public short getOutNosWsAddressType() {
      return exportView.OutNosWsAddressType;
   }
 
   public short getOutNosWsAddressCategory() {
      return exportView.OutNosWsAddressCategory;
   }
 
   public String getOutNosWsAddressHomeNumber() {
      return FixedStringAttr.valueOf(exportView.OutNosWsAddressHomeNumber, 28);
   }
 
   public String getOutNosWsAddressWorkNumber() {
      return FixedStringAttr.valueOf(exportView.OutNosWsAddressWorkNumber, 28);
   }
 
   public String getOutNosWsAddressEmailAddress() {
      return FixedStringAttr.valueOf(exportView.OutNosWsAddressEmailAddress, 28);
   }
 
   public String getOutNosWsAddressFaxNumber() {
      return FixedStringAttr.valueOf(exportView.OutNosWsAddressFaxNumber, 28);
   }
 
   public String getOutWsMethodResultReturnCode() {
      return FixedStringAttr.valueOf(exportView.OutWsMethodResultReturnCode, 2);
   }
 
   public short getOutWsStoreStudentIssueMkIssuingStore() {
      return exportView.OutWsStoreStudentIssueMkIssuingStore;
   }
 
   public short getOutWsStoreStudentIssueStatus() {
      return exportView.OutWsStoreStudentIssueStatus;
   }
 
   public int getOutWsStoreStudentIssuePickerNr() {
      return exportView.OutWsStoreStudentIssuePickerNr;
   }
 
   public String getOutWsStoreStudentIssuePenWorkstation() {
      return FixedStringAttr.valueOf(exportView.OutWsStoreStudentIssuePenWorkstation, 10);
   }
 
   public String getOutWsStoreStudentIssueDeliveryType() {
      return FixedStringAttr.valueOf(exportView.OutWsStoreStudentIssueDeliveryType, 1);
   }
 
   public Calendar getOutWsStoreStudentIssueDateTime() {
      return TimestampAttr.toCalendar(exportView.OutWsStoreStudentIssueDateTime);
   }
   public String getAsStringOutWsStoreStudentIssueDateTime() {
      return TimestampAttr.toString(exportView.OutWsStoreStudentIssueDateTime);
   }
 
   public String getOutWsStoreStudentIssueIssueFromOtherFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStoreStudentIssueIssueFromOtherFlag, 1);
   }
 
   public short getOutWsRegionalOfficeCode() {
      return exportView.OutWsRegionalOfficeCode;
   }
 
   public String getOutWsRegionalOfficeEngDescription() {
      return FixedStringAttr.valueOf(exportView.OutWsRegionalOfficeEngDescription, 28);
   }
 
   public int getOutWsAddressReferenceNo() {
      return exportView.OutWsAddressReferenceNo;
   }
 
   public String getOutWsAddressAddressReferenceFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressAddressReferenceFlag, 1);
   }
 
   public short getOutWsAddressType() {
      return exportView.OutWsAddressType;
   }
 
   public short getOutWsAddressCategory() {
      return exportView.OutWsAddressCategory;
   }
 
   public String getOutWsAddressCategoryDescription() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressCategoryDescription, 28);
   }
 
   public String getOutWsAddressAddressLine1() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressAddressLine1, 28);
   }
 
   public String getOutWsAddressAddressLine2() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressAddressLine2, 28);
   }
 
   public String getOutWsAddressAddressLine3() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressAddressLine3, 28);
   }
 
   public String getOutWsAddressAddressLine4() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressAddressLine4, 28);
   }
 
   public String getOutWsAddressAddressLine5() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressAddressLine5, 28);
   }
 
   public String getOutWsAddressAddressLine6() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressAddressLine6, 28);
   }
 
   public short getOutWsAddressPostalCode() {
      return exportView.OutWsAddressPostalCode;
   }
 
   public String getOutWsAddressHomeNumber() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressHomeNumber, 28);
   }
 
   public String getOutWsAddressWorkNumber() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressWorkNumber, 28);
   }
 
   public String getOutWsAddressFaxNumber() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressFaxNumber, 28);
   }
 
   public String getOutWsAddressEmailAddress() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressEmailAddress, 28);
   }
 
   public int getOutWsAddressV2ReferenceNo() {
      return exportView.OutWsAddressV2ReferenceNo;
   }
 
   public String getOutWsAddressV2AddressReferenceFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressV2AddressReferenceFlag, 1);
   }
 
   public short getOutWsAddressV2Type() {
      return exportView.OutWsAddressV2Type;
   }
 
   public short getOutWsAddressV2Category() {
      return exportView.OutWsAddressV2Category;
   }
 
   public String getOutWsAddressV2CategoryDescription() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressV2CategoryDescription, 28);
   }
 
   public String getOutWsAddressV2AddressLine1() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressV2AddressLine1, 28);
   }
 
   public String getOutWsAddressV2AddressLine2() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressV2AddressLine2, 28);
   }
 
   public String getOutWsAddressV2AddressLine3() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressV2AddressLine3, 28);
   }
 
   public String getOutWsAddressV2AddressLine4() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressV2AddressLine4, 28);
   }
 
   public String getOutWsAddressV2AddressLine5() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressV2AddressLine5, 28);
   }
 
   public String getOutWsAddressV2AddressLine6() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressV2AddressLine6, 28);
   }
 
   public short getOutWsAddressV2PostalCode() {
      return exportView.OutWsAddressV2PostalCode;
   }
 
   public String getOutWsAddressV2HomeNumber() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressV2HomeNumber, 28);
   }
 
   public String getOutWsAddressV2WorkNumber() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressV2WorkNumber, 28);
   }
 
   public String getOutWsAddressV2FaxNumber() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressV2FaxNumber, 28);
   }
 
   public String getOutWsAddressV2CellNumber() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressV2CellNumber, 20);
   }
 
   public String getOutWsAddressV2EmailAddress() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressV2EmailAddress, 60);
   }
 
   public short getOutWsAddressV2ErrorCode() {
      return exportView.OutWsAddressV2ErrorCode;
   }
 
   public int getOutWsAddressV2UserNumber() {
      return exportView.OutWsAddressV2UserNumber;
   }
 
   public String getOutWsAddressV2CourierContactNo() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressV2CourierContactNo, 40);
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
 
   public String getOutWsStudentFirstNames() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentFirstNames, 60);
   }
 
   public String getOutWsStudentPreviousSurname() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentPreviousSurname, 28);
   }
 
   public String getOutWsStudentInitials() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentInitials, 10);
   }
 
   public String getOutWsStudentIdentityNr() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentIdentityNr, 13);
   }
 
   public String getOutWsStudentMkHomeLanguage() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentMkHomeLanguage, 2);
   }
 
   public String getOutWsStudentMkCorrespondenceLanguage() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentMkCorrespondenceLanguage, 2);
   }
 
   public int getOutStudentAnnualRecordMkStudentNr() {
      return exportView.OutStudentAnnualRecordMkStudentNr;
   }
 
   public short getOutStudentAnnualRecordMkAcademicYear() {
      return exportView.OutStudentAnnualRecordMkAcademicYear;
   }
 
   public short getOutStudentAnnualRecordMkAcademicPeriod() {
      return exportView.OutStudentAnnualRecordMkAcademicPeriod;
   }
 
   public short getOutStudentAnnualRecordMkRegionalOfficeCode() {
      return exportView.OutStudentAnnualRecordMkRegionalOfficeCode;
   }
 
   public String getOutStudentAnnualRecordRegistrationMethodCode() {
      return FixedStringAttr.valueOf(exportView.OutStudentAnnualRecordRegistrationMethodCode, 1);
   }
 
   public String getOutStudentAnnualRecordDespatchMethodCode() {
      return FixedStringAttr.valueOf(exportView.OutStudentAnnualRecordDespatchMethodCode, 1);
   }
 
   public String getOutStudentAnnualRecordStatusCode() {
      return FixedStringAttr.valueOf(exportView.OutStudentAnnualRecordStatusCode, 2);
   }
 
   public String getOutStudentAnnualRecordMkHighestQualificationCode() {
      return FixedStringAttr.valueOf(exportView.OutStudentAnnualRecordMkHighestQualificationCode, 5);
   }
 
   public String getOutStudentAnnualRecordLateRegistrationFlag() {
      return FixedStringAttr.valueOf(exportView.OutStudentAnnualRecordLateRegistrationFlag, 1);
   }
 
   public String getOutCsfStringsString500() {
      return StringAttr.valueOf(exportView.OutCsfStringsString500);
   }
 
   public final int OutLuGroupMax = 100;
   public short getOutLuGroupCount() {
      return (short)(exportView.OutLuGroup_MA);
   };
 
   public Calendar getOutGWsTrackAndTraceDate(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return DateAttr.toCalendar(exportView.OutGWsTrackAndTraceDate[index]);
   }
   public int getAsIntOutGWsTrackAndTraceDate(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return DateAttr.toInt(exportView.OutGWsTrackAndTraceDate[index]);
   }
 
   public String getOutGWsTrackAndTraceNumber(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsTrackAndTraceNumber[index], 15);
   }
 
   public String getOutLuGCsfLineActionBarSelectionFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutLuGCsfLineActionBarSelectionFlag[index], 1);
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
 
   public String getOutCsfClientServerCommunicationsClientIsGuiFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsClientIsGuiFlag, 1);
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
 
   public final int OutGroupMax = 30;
   public short getOutGroupCount() {
      return (short)(exportView.OutGroup_MA);
   };
 
   public String getOuthpGWsQualificationCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OuthpGWsQualificationCode[index], 5);
   }
 
   public String getOuthpGWsQualificationType(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OuthpGWsQualificationType[index], 1);
   }
 
   public String getOuthpGWsQualificationShortDescription(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OuthpGWsQualificationShortDescription[index], 12);
   }
 
   public String getOutGWsQualificationCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsQualificationCode[index], 5);
   }
 
   public String getOutGWsQualificationType(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsQualificationType[index], 1);
   }
 
   public String getOutGWsQualificationShortDescription(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsQualificationShortDescription[index], 12);
   }
 
   public String getOuthpGStudentStudyUnitMkStudyUnitCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OuthpGStudentStudyUnitMkStudyUnitCode[index], 7);
   }
 
   public String getOuthpGStudentStudyUnitMkCourseStudyUnitCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OuthpGStudentStudyUnitMkCourseStudyUnitCode[index], 7);
   }
 
   public String getOuthpGStudentStudyUnitMkStudyUnitOptionCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OuthpGStudentStudyUnitMkStudyUnitOptionCode[index], 1);
   }
 
   public String getOuthpGStudentStudyUnitMkQualificationCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OuthpGStudentStudyUnitMkQualificationCode[index], 5);
   }
 
   public String getOuthpGStudentStudyUnitStatusCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OuthpGStudentStudyUnitStatusCode[index], 2);
   }
 
   public Calendar getOuthpGStudentStudyUnitRegistrationDate(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return DateAttr.toCalendar(exportView.OuthpGStudentStudyUnitRegistrationDate[index]);
   }
   public int getAsIntOuthpGStudentStudyUnitRegistrationDate(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return DateAttr.toInt(exportView.OuthpGStudentStudyUnitRegistrationDate[index]);
   }
 
   public Calendar getOuthpGStudentStudyUnitCancellationDate(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return DateAttr.toCalendar(exportView.OuthpGStudentStudyUnitCancellationDate[index]);
   }
   public int getAsIntOuthpGStudentStudyUnitCancellationDate(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return DateAttr.toInt(exportView.OuthpGStudentStudyUnitCancellationDate[index]);
   }
 
   public String getOuthpGStudentStudyUnitLanguageCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OuthpGStudentStudyUnitLanguageCode[index], 1);
   }
 
   public String getOuthpGStudentStudyUnitDespatchComment(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OuthpGStudentStudyUnitDespatchComment[index], 30);
   }
 
   public short getOuthpGStudentStudyUnitSemesterPeriod(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return exportView.OuthpGStudentStudyUnitSemesterPeriod[index];
   }
 
   public String getOuthpGDespatchCommentMkStudyUnitCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OuthpGDespatchCommentMkStudyUnitCode[index], 7);
   }
 
   public short getOuthpGDespatchCommentSemesterPeriod(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return exportView.OuthpGDespatchCommentSemesterPeriod[index];
   }
 
   public short getOuthpGDespatchCommentSequenceNumber(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return exportView.OuthpGDespatchCommentSequenceNumber[index];
   }
 
   public String getOuthpGDespatchCommentDespatchComment(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OuthpGDespatchCommentDespatchComment[index], 80);
   }
 
   public String getOutGStudentStudyUnitMkStudyUnitCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGStudentStudyUnitMkStudyUnitCode[index], 7);
   }
 
   public String getOutGStudentStudyUnitMkCourseStudyUnitCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGStudentStudyUnitMkCourseStudyUnitCode[index], 7);
   }
 
   public String getOutGStudentStudyUnitMkStudyUnitOptionCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGStudentStudyUnitMkStudyUnitOptionCode[index], 1);
   }
 
   public String getOutGStudentStudyUnitMkQualificationCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGStudentStudyUnitMkQualificationCode[index], 5);
   }
 
   public String getOutGStudentStudyUnitStatusCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGStudentStudyUnitStatusCode[index], 2);
   }
 
   public Calendar getOutGStudentStudyUnitRegistrationDate(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return DateAttr.toCalendar(exportView.OutGStudentStudyUnitRegistrationDate[index]);
   }
   public int getAsIntOutGStudentStudyUnitRegistrationDate(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return DateAttr.toInt(exportView.OutGStudentStudyUnitRegistrationDate[index]);
   }
 
   public Calendar getOutGStudentStudyUnitCancellationDate(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return DateAttr.toCalendar(exportView.OutGStudentStudyUnitCancellationDate[index]);
   }
   public int getAsIntOutGStudentStudyUnitCancellationDate(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return DateAttr.toInt(exportView.OutGStudentStudyUnitCancellationDate[index]);
   }
 
   public String getOutGStudentStudyUnitLanguageCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGStudentStudyUnitLanguageCode[index], 1);
   }
 
   public String getOutGStudentStudyUnitDespatchComment(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGStudentStudyUnitDespatchComment[index], 30);
   }
 
   public short getOutGStudentStudyUnitSemesterPeriod(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return exportView.OutGStudentStudyUnitSemesterPeriod[index];
   }
 
   public String getOutGDespatchCommentMkStudyUnitCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGDespatchCommentMkStudyUnitCode[index], 7);
   }
 
   public short getOutGDespatchCommentSemesterPeriod(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return exportView.OutGDespatchCommentSemesterPeriod[index];
   }
 
   public short getOutGDespatchCommentSequenceNumber(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return exportView.OutGDespatchCommentSequenceNumber[index];
   }
 
   public String getOutGDespatchCommentDespatchComment(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGDespatchCommentDespatchComment[index], 80);
   }
 
   public String getOutGCsfLineActionBarAction(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGCsfLineActionBarAction[index], 1);
   }
 
   public short getOutGCsfLineActionBarLineReturnCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return exportView.OutGCsfLineActionBarLineReturnCode[index];
   }
 
   public String getOutGCsfLineActionBarSelectionFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGCsfLineActionBarSelectionFlag[index], 1);
   }
 
   public String getOutGCsfLineActionBarTranslatedAction(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGCsfLineActionBarTranslatedAction[index], 1);
   }
 
   public String getOutGCsfPromptPrompt1(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGCsfPromptPrompt1[index], 1);
   }
 
};
