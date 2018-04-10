package Gistl01h.Abean;
 
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
public class Gistl01sProxyForGistf01m  implements ActionListener, java.io.Serializable  {
 
   //  Use final String for the bean name
   public static final String BEANNAME = new String("Gistl01sProxyForGistf01m");
 
   //  Constants for Asynchronous status
   public static final int PENDING = CoopFlow.DATA_NOT_READY;
   public static final int AVAILABLE = CoopFlow.DATA_READY;
   public static final int INVALID_ID = CoopFlow.INVALID_ID;
 
   private final SimpleDateFormat nativeDateFormatter = new SimpleDateFormat("yyyyMMdd");
   private static DecimalFormat decimalFormatter;
 
   public Gistl01sProxyForGistf01m() {
      super();
      DecimalFormatSymbols symbols = new DecimalFormatSymbols();
      symbols.setDecimalSeparator('.');
      symbols.setGroupingSeparator(',');
      decimalFormatter = new DecimalFormat("###################.###################", symbols);
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
 
 
   private Gistl01sProxyForGistf01mOperation oper = null;
 
   /**
    * Calls the transaction/procedure step on the server.
    *
    * @exception java.beans.PropertyVetoException
    * Final property checks can throw this exception.
    */
   public void execute()  throws PropertyVetoException {
      try  {
         if (oper == null) {
            oper = new Gistl01sProxyForGistf01mOperation(this);
            addCompletionListener(new operListener(this));
            addExceptionListener(new operListener(this));
         }
 

 
         oper.doGistl01sProxyForGistf01mOperation();
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
      private Gistl01sProxyForGistf01m rP;
      operListener(Gistl01sProxyForGistf01m r)  {
         rP = r;
      }
      public void actionPerformed(ActionEvent aEvent)  {
         if (Trace.isTracing(Trace.MASK_APPLICATION))
         {
            Trace.record(Trace.MASK_APPLICATION, "Gistl01sProxyForGistf01m", "Listener heard that Gistl01sProxyForGistf01mOperation completed with " + 
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of completion of operation Gistl01sProxyForGistf01m ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      synchronized (this)  {
         targets = (Vector) completionListeners.clone();
      }
      actionEvt = new ActionEvent(this, 0, "Completion.Gistl01sProxyForGistf01m");
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of exception of operation Gistl01sProxyForGistf01m ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      String failCommand = "Exception.Gistl01sProxyForGistf01m";
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
   }

   Gistl01h.GISTL01S_IA importView = Gistl01h.GISTL01S_IA.getInstance();
   Gistl01h.GISTL01S_OA exportView = Gistl01h.GISTL01S_OA.getInstance();
   public int getInStaffPersno() {
      return importView.InStaffPersno;
   }
   public void setInStaffPersno(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InStaffPersno has more than 8 digits.",
               new PropertyChangeEvent (this, "InStaffPersno", null, null));
      }
      importView.InStaffPersno = IntAttr.valueOf(s);
   }
   public void setAsStringInStaffPersno(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStaffPersno is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStaffPersno", null, null));
      }
      try {
          setInStaffPersno(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStaffPersno is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStaffPersno", null, null));
      }
   }
 
   public String getInStaffSurname() {
      return FixedStringAttr.valueOf(importView.InStaffSurname, 28);
   }
   public void setInStaffSurname(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InStaffSurname must be <= 28 characters.",
               new PropertyChangeEvent (this, "InStaffSurname", null, null));
      }
      importView.InStaffSurname = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInStaffInitials() {
      return FixedStringAttr.valueOf(importView.InStaffInitials, 10);
   }
   public void setInStaffInitials(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InStaffInitials must be <= 10 characters.",
               new PropertyChangeEvent (this, "InStaffInitials", null, null));
      }
      importView.InStaffInitials = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInStaffTitle() {
      return FixedStringAttr.valueOf(importView.InStaffTitle, 10);
   }
   public void setInStaffTitle(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InStaffTitle must be <= 10 characters.",
               new PropertyChangeEvent (this, "InStaffTitle", null, null));
      }
      importView.InStaffTitle = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInStaffName() {
      return FixedStringAttr.valueOf(importView.InStaffName, 28);
   }
   public void setInStaffName(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InStaffName must be <= 28 characters.",
               new PropertyChangeEvent (this, "InStaffName", null, null));
      }
      importView.InStaffName = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInStaffLanguageCode() {
      return FixedStringAttr.valueOf(importView.InStaffLanguageCode, 2);
   }
   public void setInStaffLanguageCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InStaffLanguageCode must be <= 2 characters.",
               new PropertyChangeEvent (this, "InStaffLanguageCode", null, null));
      }
      importView.InStaffLanguageCode = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public short getInStaffMkDeptCode() {
      return importView.InStaffMkDeptCode;
   }
   public void setInStaffMkDeptCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InStaffMkDeptCode has more than 3 digits.",
               new PropertyChangeEvent (this, "InStaffMkDeptCode", null, null));
      }
      importView.InStaffMkDeptCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInStaffMkDeptCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStaffMkDeptCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStaffMkDeptCode", null, null));
      }
      try {
          setInStaffMkDeptCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStaffMkDeptCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStaffMkDeptCode", null, null));
      }
   }
 
   public Calendar getInStaffResignDate() {
      return DateAttr.toCalendar(importView.InStaffResignDate);
   }
   public int getAsIntInStaffResignDate() {
      return DateAttr.toInt(importView.InStaffResignDate);
   }
   public void setInStaffResignDate(Calendar s)
      throws PropertyVetoException {
      importView.InStaffResignDate = DateAttr.valueOf(s);
   }
   public void setAsStringInStaffResignDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInStaffResignDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInStaffResignDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InStaffResignDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InStaffResignDate", null, null));
         }
      }
   }
   public void setAsIntInStaffResignDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInStaffResignDate(temp);
   }
 
   public String getInStaffEmailAddress() {
      return FixedStringAttr.valueOf(importView.InStaffEmailAddress, 60);
   }
   public void setInStaffEmailAddress(String s)
      throws PropertyVetoException {
      if (s.length() > 60) {
         throw new PropertyVetoException("InStaffEmailAddress must be <= 60 characters.",
               new PropertyChangeEvent (this, "InStaffEmailAddress", null, null));
      }
      importView.InStaffEmailAddress = FixedStringAttr.valueOf(s, (short)60);
   }
 
   public String getInStaffContactTelno() {
      return FixedStringAttr.valueOf(importView.InStaffContactTelno, 28);
   }
   public void setInStaffContactTelno(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InStaffContactTelno must be <= 28 characters.",
               new PropertyChangeEvent (this, "InStaffContactTelno", null, null));
      }
      importView.InStaffContactTelno = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInStaffOfficeNo() {
      return FixedStringAttr.valueOf(importView.InStaffOfficeNo, 7);
   }
   public void setInStaffOfficeNo(String s)
      throws PropertyVetoException {
      if (s.length() > 7) {
         throw new PropertyVetoException("InStaffOfficeNo must be <= 7 characters.",
               new PropertyChangeEvent (this, "InStaffOfficeNo", null, null));
      }
      importView.InStaffOfficeNo = FixedStringAttr.valueOf(s, (short)7);
   }
 
   public short getInStaffMkSubdeptCode() {
      return importView.InStaffMkSubdeptCode;
   }
   public void setInStaffMkSubdeptCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InStaffMkSubdeptCode has more than 2 digits.",
               new PropertyChangeEvent (this, "InStaffMkSubdeptCode", null, null));
      }
      importView.InStaffMkSubdeptCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInStaffMkSubdeptCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStaffMkSubdeptCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStaffMkSubdeptCode", null, null));
      }
      try {
          setInStaffMkSubdeptCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStaffMkSubdeptCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStaffMkSubdeptCode", null, null));
      }
   }
 
   public int getInStaffOldStaffNr() {
      return importView.InStaffOldStaffNr;
   }
   public void setInStaffOldStaffNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InStaffOldStaffNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InStaffOldStaffNr", null, null));
      }
      importView.InStaffOldStaffNr = IntAttr.valueOf(s);
   }
   public void setAsStringInStaffOldStaffNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStaffOldStaffNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStaffOldStaffNr", null, null));
      }
      try {
          setInStaffOldStaffNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStaffOldStaffNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStaffOldStaffNr", null, null));
      }
   }
 
   public String getInStaffOldInstitution() {
      return FixedStringAttr.valueOf(importView.InStaffOldInstitution, 1);
   }
   public void setInStaffOldInstitution(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InStaffOldInstitution must be <= 1 characters.",
               new PropertyChangeEvent (this, "InStaffOldInstitution", null, null));
      }
      importView.InStaffOldInstitution = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInStaffMkRcCode() {
      return StringAttr.valueOf(importView.InStaffMkRcCode);
   }
   public void setInStaffMkRcCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 6) {
         throw new PropertyVetoException("InStaffMkRcCode must be <= 6 characters.",
               new PropertyChangeEvent (this, "InStaffMkRcCode", null, null));
      }
      importView.InStaffMkRcCode = StringAttr.valueOf(s, (short)6);
   }
 
   public String getInStaffFirstNames() {
      return StringAttr.valueOf(importView.InStaffFirstNames);
   }
   public void setInStaffFirstNames(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 240) {
         throw new PropertyVetoException("InStaffFirstNames must be <= 240 characters.",
               new PropertyChangeEvent (this, "InStaffFirstNames", null, null));
      }
      importView.InStaffFirstNames = StringAttr.valueOf(s, (short)240);
   }
 
   public Calendar getInStaffDateOfBirth() {
      return DateAttr.toCalendar(importView.InStaffDateOfBirth);
   }
   public int getAsIntInStaffDateOfBirth() {
      return DateAttr.toInt(importView.InStaffDateOfBirth);
   }
   public void setInStaffDateOfBirth(Calendar s)
      throws PropertyVetoException {
      importView.InStaffDateOfBirth = DateAttr.valueOf(s);
   }
   public void setAsStringInStaffDateOfBirth(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInStaffDateOfBirth((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInStaffDateOfBirth(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InStaffDateOfBirth has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InStaffDateOfBirth", null, null));
         }
      }
   }
   public void setAsIntInStaffDateOfBirth(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInStaffDateOfBirth(temp);
   }
 
   public double getInStaffIdNr() {
      return importView.InStaffIdNr;
   }
   public void setInStaffIdNr(double s)
      throws PropertyVetoException {
      int decimals = 0;
      boolean decimal_found = false;
      String tempDataStr = decimalFormatter.format(s);
      for (int i=tempDataStr.length(); i>0; i--) {
         if (tempDataStr.charAt(i-1) == '.') {
            decimal_found = true;
            break;
         }
         decimals++;
      }
      if (decimal_found == true && decimals > 0) {
         throw new PropertyVetoException("InStaffIdNr has more than 0 fractional digits.",
               new PropertyChangeEvent (this, "InStaffIdNr", null, null));
      }
      if (java.lang.Math.abs(s) >= 10000000000000.0) {
         throw new PropertyVetoException("InStaffIdNr has more than 13 integral digits.",
               new PropertyChangeEvent (this, "InStaffIdNr", null, null));
      }
      importView.InStaffIdNr = DoubleAttr.valueOf(s);
   }
   public void setAsStringInStaffIdNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStaffIdNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStaffIdNr", null, null));
      }
      try {
          setInStaffIdNr(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStaffIdNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStaffIdNr", null, null));
      }
   }
 
   public String getInStaffPassportNr() {
      return StringAttr.valueOf(importView.InStaffPassportNr);
   }
   public void setInStaffPassportNr(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 13) {
         throw new PropertyVetoException("InStaffPassportNr must be <= 13 characters.",
               new PropertyChangeEvent (this, "InStaffPassportNr", null, null));
      }
      importView.InStaffPassportNr = StringAttr.valueOf(s, (short)13);
   }
 
   public String getInStaffTaxNr() {
      return StringAttr.valueOf(importView.InStaffTaxNr);
   }
   public void setInStaffTaxNr(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 15) {
         throw new PropertyVetoException("InStaffTaxNr must be <= 15 characters.",
               new PropertyChangeEvent (this, "InStaffTaxNr", null, null));
      }
      importView.InStaffTaxNr = StringAttr.valueOf(s, (short)15);
   }
 
   public String getInStaffTaxDirectiveNr() {
      return StringAttr.valueOf(importView.InStaffTaxDirectiveNr);
   }
   public void setInStaffTaxDirectiveNr(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 13) {
         throw new PropertyVetoException("InStaffTaxDirectiveNr must be <= 13 characters.",
               new PropertyChangeEvent (this, "InStaffTaxDirectiveNr", null, null));
      }
      importView.InStaffTaxDirectiveNr = StringAttr.valueOf(s, (short)13);
   }
 
   public String getInStaffGender() {
      return StringAttr.valueOf(importView.InStaffGender);
   }
   public void setInStaffGender(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InStaffGender must be <= 1 characters.",
               new PropertyChangeEvent (this, "InStaffGender", null, null));
      }
      importView.InStaffGender = StringAttr.valueOf(s, (short)1);
   }
 
   public String getInStaffMkRaceCode() {
      return StringAttr.valueOf(importView.InStaffMkRaceCode);
   }
   public void setInStaffMkRaceCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 3) {
         throw new PropertyVetoException("InStaffMkRaceCode must be <= 3 characters.",
               new PropertyChangeEvent (this, "InStaffMkRaceCode", null, null));
      }
      importView.InStaffMkRaceCode = StringAttr.valueOf(s, (short)3);
   }
 
   public String getInStaffNovellUserId() {
      return StringAttr.valueOf(importView.InStaffNovellUserId);
   }
   public void setInStaffNovellUserId(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 20) {
         throw new PropertyVetoException("InStaffNovellUserId must be <= 20 characters.",
               new PropertyChangeEvent (this, "InStaffNovellUserId", null, null));
      }
      importView.InStaffNovellUserId = StringAttr.valueOf(s, (short)20);
   }
 
   public String getInStaffBuilding() {
      return StringAttr.valueOf(importView.InStaffBuilding);
   }
   public void setInStaffBuilding(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InStaffBuilding must be <= 28 characters.",
               new PropertyChangeEvent (this, "InStaffBuilding", null, null));
      }
      importView.InStaffBuilding = StringAttr.valueOf(s, (short)28);
   }
 
   public String getInStaffCellNumber() {
      return StringAttr.valueOf(importView.InStaffCellNumber);
   }
   public void setInStaffCellNumber(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 20) {
         throw new PropertyVetoException("InStaffCellNumber must be <= 20 characters.",
               new PropertyChangeEvent (this, "InStaffCellNumber", null, null));
      }
      importView.InStaffCellNumber = StringAttr.valueOf(s, (short)20);
   }
 
   public int getOutStaffPersno() {
      return exportView.OutStaffPersno;
   }
 
   public String getOutStaffSurname() {
      return FixedStringAttr.valueOf(exportView.OutStaffSurname, 28);
   }
 
   public String getOutStaffInitials() {
      return FixedStringAttr.valueOf(exportView.OutStaffInitials, 10);
   }
 
   public String getOutStaffTitle() {
      return FixedStringAttr.valueOf(exportView.OutStaffTitle, 10);
   }
 
   public String getOutStaffName() {
      return FixedStringAttr.valueOf(exportView.OutStaffName, 28);
   }
 
   public String getOutStaffLanguageCode() {
      return FixedStringAttr.valueOf(exportView.OutStaffLanguageCode, 2);
   }
 
   public short getOutStaffMkDeptCode() {
      return exportView.OutStaffMkDeptCode;
   }
 
   public Calendar getOutStaffResignDate() {
      return DateAttr.toCalendar(exportView.OutStaffResignDate);
   }
   public int getAsIntOutStaffResignDate() {
      return DateAttr.toInt(exportView.OutStaffResignDate);
   }
 
   public String getOutStaffEmailAddress() {
      return FixedStringAttr.valueOf(exportView.OutStaffEmailAddress, 60);
   }
 
   public String getOutStaffContactTelno() {
      return FixedStringAttr.valueOf(exportView.OutStaffContactTelno, 28);
   }
 
   public String getOutStaffOfficeNo() {
      return FixedStringAttr.valueOf(exportView.OutStaffOfficeNo, 7);
   }
 
   public short getOutStaffMkSubdeptCode() {
      return exportView.OutStaffMkSubdeptCode;
   }
 
   public int getOutStaffOldStaffNr() {
      return exportView.OutStaffOldStaffNr;
   }
 
   public String getOutStaffOldInstitution() {
      return FixedStringAttr.valueOf(exportView.OutStaffOldInstitution, 1);
   }
 
   public String getOutStaffMkRcCode() {
      return StringAttr.valueOf(exportView.OutStaffMkRcCode);
   }
 
   public String getOutStaffFirstNames() {
      return StringAttr.valueOf(exportView.OutStaffFirstNames);
   }
 
   public Calendar getOutStaffDateOfBirth() {
      return DateAttr.toCalendar(exportView.OutStaffDateOfBirth);
   }
   public int getAsIntOutStaffDateOfBirth() {
      return DateAttr.toInt(exportView.OutStaffDateOfBirth);
   }
 
   public double getOutStaffIdNr() {
      return exportView.OutStaffIdNr;
   }
 
   public String getOutStaffPassportNr() {
      return StringAttr.valueOf(exportView.OutStaffPassportNr);
   }
 
   public String getOutStaffTaxNr() {
      return StringAttr.valueOf(exportView.OutStaffTaxNr);
   }
 
   public String getOutStaffTaxDirectiveNr() {
      return StringAttr.valueOf(exportView.OutStaffTaxDirectiveNr);
   }
 
   public String getOutStaffGender() {
      return StringAttr.valueOf(exportView.OutStaffGender);
   }
 
   public String getOutStaffMkRaceCode() {
      return StringAttr.valueOf(exportView.OutStaffMkRaceCode);
   }
 
   public String getOutStaffNovellUserId() {
      return StringAttr.valueOf(exportView.OutStaffNovellUserId);
   }
 
   public String getOutStaffBuilding() {
      return StringAttr.valueOf(exportView.OutStaffBuilding);
   }
 
   public String getOutStaffCellNumber() {
      return StringAttr.valueOf(exportView.OutStaffCellNumber);
   }
 
   public String getOutCsfStringsString500() {
      return StringAttr.valueOf(exportView.OutCsfStringsString500);
   }
 
};
