package Ecdcq01h.Abean;
 
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
public class Ecdcq01sEcapsDocumentMnt  implements ActionListener, java.io.Serializable  {
 
   //  Use final String for the bean name
   public static final String BEANNAME = new String("Ecdcq01sEcapsDocumentMnt");
 
   //  Constants for Asynchronous status
   public static final int PENDING = CoopFlow.DATA_NOT_READY;
   public static final int AVAILABLE = CoopFlow.DATA_READY;
   public static final int INVALID_ID = CoopFlow.INVALID_ID;
 
   private final SimpleDateFormat nativeTimestampFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
   public Ecdcq01sEcapsDocumentMnt() {
      super();
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
 
 
   private Ecdcq01sEcapsDocumentMntOperation oper = null;
 
   /**
    * Calls the transaction/procedure step on the server.
    *
    * @exception java.beans.PropertyVetoException
    * Final property checks can throw this exception.
    */
   public void execute()  throws PropertyVetoException {
      try  {
         if (oper == null) {
            oper = new Ecdcq01sEcapsDocumentMntOperation(this);
            addCompletionListener(new operListener(this));
            addExceptionListener(new operListener(this));
         }
 

 
         oper.doEcdcq01sEcapsDocumentMntOperation();
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
      private Ecdcq01sEcapsDocumentMnt rP;
      operListener(Ecdcq01sEcapsDocumentMnt r)  {
         rP = r;
      }
      public void actionPerformed(ActionEvent aEvent)  {
         if (Trace.isTracing(Trace.MASK_APPLICATION))
         {
            Trace.record(Trace.MASK_APPLICATION, "Ecdcq01sEcapsDocumentMnt", "Listener heard that Ecdcq01sEcapsDocumentMntOperation completed with " + 
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of completion of operation Ecdcq01sEcapsDocumentMnt ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      synchronized (this)  {
         targets = (Vector) completionListeners.clone();
      }
      actionEvt = new ActionEvent(this, 0, "Completion.Ecdcq01sEcapsDocumentMnt");
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of exception of operation Ecdcq01sEcapsDocumentMnt ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      String failCommand = "Exception.Ecdcq01sEcapsDocumentMnt";
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
      importView.InAttributeGroup_MA = IntAttr.valueOf(InAttributeGroupMax);
      exportView.OutAttributeGroup_MA = IntAttr.getDefaultValue();
   }

   Ecdcq01h.ECDCQ01S_IA importView = Ecdcq01h.ECDCQ01S_IA.getInstance();
   Ecdcq01h.ECDCQ01S_OA exportView = Ecdcq01h.ECDCQ01S_OA.getInstance();
   public String getInDocDepIndWsEcapsString1() {
      return FixedStringAttr.valueOf(importView.InDocDepIndWsEcapsString1, 1);
   }
   public void setInDocDepIndWsEcapsString1(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InDocDepIndWsEcapsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InDocDepIndWsEcapsString1", null, null));
      }
      importView.InDocDepIndWsEcapsString1 = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInFieldEcapsDocumentAttrField() {
      return StringAttr.valueOf(importView.InFieldEcapsDocumentAttrField);
   }
   public void setInFieldEcapsDocumentAttrField(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 20) {
         throw new PropertyVetoException("InFieldEcapsDocumentAttrField must be <= 20 characters.",
               new PropertyChangeEvent (this, "InFieldEcapsDocumentAttrField", null, null));
      }
      importView.InFieldEcapsDocumentAttrField = StringAttr.valueOf(s, (short)20);
   }
 
   public String getInActionIefSuppliedCommand() {
      return FixedStringAttr.valueOf(importView.InActionIefSuppliedCommand, 80);
   }
   public void setInActionIefSuppliedCommand(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 80) {
         throw new PropertyVetoException("InActionIefSuppliedCommand must be <= 80 characters.",
               new PropertyChangeEvent (this, "InActionIefSuppliedCommand", null, null));
      }
      importView.InActionIefSuppliedCommand = FixedStringAttr.valueOf(s, (short)80);
   }
 
   public Calendar getInTimestampEcapsDocumentAttrCreatedTimestamp() {
      return TimestampAttr.toCalendar(importView.InTimestampEcapsDocumentAttrCreatedTimestamp);
   }
   public String getAsStringInTimestampEcapsDocumentAttrCreatedTimestamp() {
      return TimestampAttr.toString(importView.InTimestampEcapsDocumentAttrCreatedTimestamp);
   }
   public void setInTimestampEcapsDocumentAttrCreatedTimestamp(Calendar s)
      throws PropertyVetoException {
      importView.InTimestampEcapsDocumentAttrCreatedTimestamp = TimestampAttr.valueOf(s);
   }
   public void setAsStringInTimestampEcapsDocumentAttrCreatedTimestamp(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInTimestampEcapsDocumentAttrCreatedTimestamp((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeTimestampFormatter.parse(s.length() > 17 ? s.substring(0, 17) : s));
            importView.InTimestampEcapsDocumentAttrCreatedTimestamp = TimestampAttr.valueOf(s);
         } catch (ParseException e) {
            throw new PropertyVetoException("InTimestampEcapsDocumentAttrCreatedTimestamp has an invalid format (yyyyMMddHHmmssSSSSSS).",
                  new PropertyChangeEvent (this, "InTimestampEcapsDocumentAttrCreatedTimestamp", null, null));
         }
      }
   }
 
   public short getInEcapsDocumentMkAcademicYear() {
      return importView.InEcapsDocumentMkAcademicYear;
   }
   public void setInEcapsDocumentMkAcademicYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InEcapsDocumentMkAcademicYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InEcapsDocumentMkAcademicYear", null, null));
      }
      importView.InEcapsDocumentMkAcademicYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInEcapsDocumentMkAcademicYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InEcapsDocumentMkAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InEcapsDocumentMkAcademicYear", null, null));
      }
      try {
          setInEcapsDocumentMkAcademicYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InEcapsDocumentMkAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InEcapsDocumentMkAcademicYear", null, null));
      }
   }
 
   public short getInEcapsDocumentSemester() {
      return importView.InEcapsDocumentSemester;
   }
   public void setInEcapsDocumentSemester(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InEcapsDocumentSemester has more than 1 digits.",
               new PropertyChangeEvent (this, "InEcapsDocumentSemester", null, null));
      }
      importView.InEcapsDocumentSemester = ShortAttr.valueOf(s);
   }
   public void setAsStringInEcapsDocumentSemester(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InEcapsDocumentSemester is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InEcapsDocumentSemester", null, null));
      }
      try {
          setInEcapsDocumentSemester(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InEcapsDocumentSemester is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InEcapsDocumentSemester", null, null));
      }
   }
 
   public String getInEcapsDocumentMkStudyUnitCode() {
      return StringAttr.valueOf(importView.InEcapsDocumentMkStudyUnitCode);
   }
   public void setInEcapsDocumentMkStudyUnitCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InEcapsDocumentMkStudyUnitCode must be <= 7 characters.",
               new PropertyChangeEvent (this, "InEcapsDocumentMkStudyUnitCode", null, null));
      }
      importView.InEcapsDocumentMkStudyUnitCode = StringAttr.valueOf(s, (short)7);
   }
 
   public String getInEcapsDocumentStatusGc31() {
      return StringAttr.valueOf(importView.InEcapsDocumentStatusGc31);
   }
   public void setInEcapsDocumentStatusGc31(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InEcapsDocumentStatusGc31 must be <= 10 characters.",
               new PropertyChangeEvent (this, "InEcapsDocumentStatusGc31", null, null));
      }
      importView.InEcapsDocumentStatusGc31 = StringAttr.valueOf(s, (short)10);
   }
 
   public Calendar getInEcapsDocumentSignedTimestamp() {
      return TimestampAttr.toCalendar(importView.InEcapsDocumentSignedTimestamp);
   }
   public String getAsStringInEcapsDocumentSignedTimestamp() {
      return TimestampAttr.toString(importView.InEcapsDocumentSignedTimestamp);
   }
   public void setInEcapsDocumentSignedTimestamp(Calendar s)
      throws PropertyVetoException {
      importView.InEcapsDocumentSignedTimestamp = TimestampAttr.valueOf(s);
   }
   public void setAsStringInEcapsDocumentSignedTimestamp(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInEcapsDocumentSignedTimestamp((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeTimestampFormatter.parse(s.length() > 17 ? s.substring(0, 17) : s));
            importView.InEcapsDocumentSignedTimestamp = TimestampAttr.valueOf(s);
         } catch (ParseException e) {
            throw new PropertyVetoException("InEcapsDocumentSignedTimestamp has an invalid format (yyyyMMddHHmmssSSSSSS).",
                  new PropertyChangeEvent (this, "InEcapsDocumentSignedTimestamp", null, null));
         }
      }
   }
 
   public final int InAttributeGroupMax = 50;
   public short getInAttributeGroupCount() {
      return (short)(importView.InAttributeGroup_MA);
   };
 
   public void setInAttributeGroupCount(short s) throws PropertyVetoException {
      if (s < 0 || s > InAttributeGroupMax) {
         throw new PropertyVetoException("InAttributeGroupCount value is not a valid value. (0 to 50)",
               new PropertyChangeEvent (this, "InAttributeGroupCount", null, null));
      } else {
         importView.InAttributeGroup_MA = IntAttr.valueOf((int)s);
      }
   }
 
   public String getInGrpDocDepIndWsEcapsString1(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGrpDocDepIndWsEcapsString1[index], 1);
   }
   public void setInGrpDocDepIndWsEcapsString1(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGrpDocDepIndWsEcapsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGrpDocDepIndWsEcapsString1", null, null));
      }
      importView.InGrpDocDepIndWsEcapsString1[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInGrpEcapsDocumentAttrField(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return StringAttr.valueOf(importView.InGrpEcapsDocumentAttrField[index]);
   }
   public void setInGrpEcapsDocumentAttrField(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 20) {
         throw new PropertyVetoException("InGrpEcapsDocumentAttrField must be <= 20 characters.",
               new PropertyChangeEvent (this, "InGrpEcapsDocumentAttrField", null, null));
      }
      importView.InGrpEcapsDocumentAttrField[index] = StringAttr.valueOf(s, (short)20);
   }
 
   public String getInGrpEcapsDocumentAttrFieldValue(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return StringAttr.valueOf(importView.InGrpEcapsDocumentAttrFieldValue[index]);
   }
   public void setInGrpEcapsDocumentAttrFieldValue(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 100) {
         throw new PropertyVetoException("InGrpEcapsDocumentAttrFieldValue must be <= 100 characters.",
               new PropertyChangeEvent (this, "InGrpEcapsDocumentAttrFieldValue", null, null));
      }
      importView.InGrpEcapsDocumentAttrFieldValue[index] = StringAttr.valueOf(s, (short)100);
   }
 
   public Calendar getInGrpEcapsDocumentAttrCreatedTimestamp(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return TimestampAttr.toCalendar(importView.InGrpEcapsDocumentAttrCreatedTimestamp[index]);
   }
   public String getAsStringInGrpEcapsDocumentAttrCreatedTimestamp(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return TimestampAttr.toString(importView.InGrpEcapsDocumentAttrCreatedTimestamp[index]);
   }
   public void setInGrpEcapsDocumentAttrCreatedTimestamp(int index, Calendar s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      importView.InGrpEcapsDocumentAttrCreatedTimestamp[index] = TimestampAttr.valueOf(s);
   }
   public void setAsStringInGrpEcapsDocumentAttrCreatedTimestamp(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s == null || s.length() == 0) {
         setInGrpEcapsDocumentAttrCreatedTimestamp(index, (Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeTimestampFormatter.parse(s.length() > 17 ? s.substring(0, 17) : s));
            importView.InGrpEcapsDocumentAttrCreatedTimestamp[index] = TimestampAttr.valueOf(s);
         } catch (ParseException e) {
            throw new PropertyVetoException("InGrpEcapsDocumentAttrCreatedTimestamp has an invalid format (yyyyMMddHHmmssSSSSSS).",
                  new PropertyChangeEvent (this, "InGrpEcapsDocumentAttrCreatedTimestamp", null, null));
         }
      }
   }
 
   public short getInGrpEcapsDocumentAttrExamYear(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return importView.InGrpEcapsDocumentAttrExamYear[index];
   }
   public void setInGrpEcapsDocumentAttrExamYear(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InGrpEcapsDocumentAttrExamYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InGrpEcapsDocumentAttrExamYear", null, null));
      }
      importView.InGrpEcapsDocumentAttrExamYear[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGrpEcapsDocumentAttrExamYear(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGrpEcapsDocumentAttrExamYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGrpEcapsDocumentAttrExamYear", null, null));
      }
      try {
          setInGrpEcapsDocumentAttrExamYear(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGrpEcapsDocumentAttrExamYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGrpEcapsDocumentAttrExamYear", null, null));
      }
   }
 
   public short getInGrpEcapsDocumentAttrExamPeriod(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return importView.InGrpEcapsDocumentAttrExamPeriod[index];
   }
   public void setInGrpEcapsDocumentAttrExamPeriod(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InGrpEcapsDocumentAttrExamPeriod has more than 2 digits.",
               new PropertyChangeEvent (this, "InGrpEcapsDocumentAttrExamPeriod", null, null));
      }
      importView.InGrpEcapsDocumentAttrExamPeriod[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGrpEcapsDocumentAttrExamPeriod(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGrpEcapsDocumentAttrExamPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGrpEcapsDocumentAttrExamPeriod", null, null));
      }
      try {
          setInGrpEcapsDocumentAttrExamPeriod(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGrpEcapsDocumentAttrExamPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGrpEcapsDocumentAttrExamPeriod", null, null));
      }
   }
 
   public String getInGrpEcapsDocumentAttrDepenencyIndGc30(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return StringAttr.valueOf(importView.InGrpEcapsDocumentAttrDepenencyIndGc30[index]);
   }
   public void setInGrpEcapsDocumentAttrDepenencyIndGc30(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InGrpEcapsDocumentAttrDepenencyIndGc30 must be <= 10 characters.",
               new PropertyChangeEvent (this, "InGrpEcapsDocumentAttrDepenencyIndGc30", null, null));
      }
      importView.InGrpEcapsDocumentAttrDepenencyIndGc30[index] = StringAttr.valueOf(s, (short)10);
   }
 
   public String getInGrpEcapsDocumentAttrNovellUserId(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return StringAttr.valueOf(importView.InGrpEcapsDocumentAttrNovellUserId[index]);
   }
   public void setInGrpEcapsDocumentAttrNovellUserId(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InGrpEcapsDocumentAttrNovellUserId must be <= 10 characters.",
               new PropertyChangeEvent (this, "InGrpEcapsDocumentAttrNovellUserId", null, null));
      }
      importView.InGrpEcapsDocumentAttrNovellUserId[index] = StringAttr.valueOf(s, (short)10);
   }
 
   public String getInGrpErrCsfStringsString10(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGrpErrCsfStringsString10[index], 10);
   }
   public void setInGrpErrCsfStringsString10(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InGrpErrCsfStringsString10 must be <= 10 characters.",
               new PropertyChangeEvent (this, "InGrpErrCsfStringsString10", null, null));
      }
      importView.InGrpErrCsfStringsString10[index] = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public short getOutEcapsDocumentMkAcademicYear() {
      return exportView.OutEcapsDocumentMkAcademicYear;
   }
 
   public short getOutEcapsDocumentSemester() {
      return exportView.OutEcapsDocumentSemester;
   }
 
   public String getOutEcapsDocumentMkStudyUnitCode() {
      return StringAttr.valueOf(exportView.OutEcapsDocumentMkStudyUnitCode);
   }
 
   public String getOutEcapsDocumentStatusGc31() {
      return StringAttr.valueOf(exportView.OutEcapsDocumentStatusGc31);
   }
 
   public Calendar getOutEcapsDocumentSignedTimestamp() {
      return TimestampAttr.toCalendar(exportView.OutEcapsDocumentSignedTimestamp);
   }
   public String getAsStringOutEcapsDocumentSignedTimestamp() {
      return TimestampAttr.toString(exportView.OutEcapsDocumentSignedTimestamp);
   }
 
   public final int OutAttributeGroupMax = 50;
   public short getOutAttributeGroupCount() {
      return (short)(exportView.OutAttributeGroup_MA);
   };
 
   public String getOutGrpDocDepIndWsEcapsString1(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGrpDocDepIndWsEcapsString1[index], 1);
   }
 
   public String getOutGrpEcapsDocumentAttrField(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return StringAttr.valueOf(exportView.OutGrpEcapsDocumentAttrField[index]);
   }
 
   public String getOutGrpEcapsDocumentAttrFieldValue(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return StringAttr.valueOf(exportView.OutGrpEcapsDocumentAttrFieldValue[index]);
   }
 
   public Calendar getOutGrpEcapsDocumentAttrCreatedTimestamp(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return TimestampAttr.toCalendar(exportView.OutGrpEcapsDocumentAttrCreatedTimestamp[index]);
   }
   public String getAsStringOutGrpEcapsDocumentAttrCreatedTimestamp(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return TimestampAttr.toString(exportView.OutGrpEcapsDocumentAttrCreatedTimestamp[index]);
   }
 
   public short getOutGrpEcapsDocumentAttrExamYear(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return exportView.OutGrpEcapsDocumentAttrExamYear[index];
   }
 
   public short getOutGrpEcapsDocumentAttrExamPeriod(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return exportView.OutGrpEcapsDocumentAttrExamPeriod[index];
   }
 
   public String getOutGrpEcapsDocumentAttrDepenencyIndGc30(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return StringAttr.valueOf(exportView.OutGrpEcapsDocumentAttrDepenencyIndGc30[index]);
   }
 
   public String getOutGrpEcapsDocumentAttrNovellUserId(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return StringAttr.valueOf(exportView.OutGrpEcapsDocumentAttrNovellUserId[index]);
   }
 
   public String getOutGrpErrCsfStringsString10(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGrpErrCsfStringsString10[index], 10);
   }
 
   public String getOutErrmsgCsfStringsString500() {
      return StringAttr.valueOf(exportView.OutErrmsgCsfStringsString500);
   }
 
};
