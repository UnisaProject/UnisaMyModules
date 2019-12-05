package Ecseq01h.Abean;
 
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
public class Ecseq01sGetEcapsSectionDet  implements ActionListener, java.io.Serializable  {
 
   //  Use final String for the bean name
   public static final String BEANNAME = new String("Ecseq01sGetEcapsSectionDet");
 
   //  Constants for Asynchronous status
   public static final int PENDING = CoopFlow.DATA_NOT_READY;
   public static final int AVAILABLE = CoopFlow.DATA_READY;
   public static final int INVALID_ID = CoopFlow.INVALID_ID;
 
   private final SimpleDateFormat nativeDateFormatter = new SimpleDateFormat("yyyyMMdd");
   public Ecseq01sGetEcapsSectionDet() {
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
 
 
   private Ecseq01sGetEcapsSectionDetOperation oper = null;
 
   /**
    * Calls the transaction/procedure step on the server.
    *
    * @exception java.beans.PropertyVetoException
    * Final property checks can throw this exception.
    */
   public void execute()  throws PropertyVetoException {
      try  {
         if (oper == null) {
            oper = new Ecseq01sGetEcapsSectionDetOperation(this);
            addCompletionListener(new operListener(this));
            addExceptionListener(new operListener(this));
         }
 

 
         oper.doEcseq01sGetEcapsSectionDetOperation();
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
      private Ecseq01sGetEcapsSectionDet rP;
      operListener(Ecseq01sGetEcapsSectionDet r)  {
         rP = r;
      }
      public void actionPerformed(ActionEvent aEvent)  {
         if (Trace.isTracing(Trace.MASK_APPLICATION))
         {
            Trace.record(Trace.MASK_APPLICATION, "Ecseq01sGetEcapsSectionDet", "Listener heard that Ecseq01sGetEcapsSectionDetOperation completed with " + 
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of completion of operation Ecseq01sGetEcapsSectionDet ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      synchronized (this)  {
         targets = (Vector) completionListeners.clone();
      }
      actionEvt = new ActionEvent(this, 0, "Completion.Ecseq01sGetEcapsSectionDet");
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of exception of operation Ecseq01sGetEcapsSectionDet ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      String failCommand = "Exception.Ecseq01sGetEcapsSectionDet";
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

   Ecseq01h.ECSEQ01S_IA importView = Ecseq01h.ECSEQ01S_IA.getInstance();
   Ecseq01h.ECSEQ01S_OA exportView = Ecseq01h.ECSEQ01S_OA.getInstance();
   public String getInEcapsSectionId() {
      return StringAttr.valueOf(importView.InEcapsSectionId);
   }
   public void setInEcapsSectionId(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 20) {
         throw new PropertyVetoException("InEcapsSectionId must be <= 20 characters.",
               new PropertyChangeEvent (this, "InEcapsSectionId", null, null));
      }
      importView.InEcapsSectionId = StringAttr.valueOf(s, (short)20);
   }
 
   public Calendar getInEcapsSectionTextFromDate() {
      return DateAttr.toCalendar(importView.InEcapsSectionTextFromDate);
   }
   public int getAsIntInEcapsSectionTextFromDate() {
      return DateAttr.toInt(importView.InEcapsSectionTextFromDate);
   }
   public void setInEcapsSectionTextFromDate(Calendar s)
      throws PropertyVetoException {
      importView.InEcapsSectionTextFromDate = DateAttr.valueOf(s);
   }
   public void setAsStringInEcapsSectionTextFromDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInEcapsSectionTextFromDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInEcapsSectionTextFromDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InEcapsSectionTextFromDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InEcapsSectionTextFromDate", null, null));
         }
      }
   }
   public void setAsIntInEcapsSectionTextFromDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInEcapsSectionTextFromDate(temp);
   }
 
   public String getInEcapsSectionTextPosition() {
      return StringAttr.valueOf(importView.InEcapsSectionTextPosition);
   }
   public void setInEcapsSectionTextPosition(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 20) {
         throw new PropertyVetoException("InEcapsSectionTextPosition must be <= 20 characters.",
               new PropertyChangeEvent (this, "InEcapsSectionTextPosition", null, null));
      }
      importView.InEcapsSectionTextPosition = StringAttr.valueOf(s, (short)20);
   }
 
   public String getInEcapsSectionTextHeader() {
      return StringAttr.valueOf(importView.InEcapsSectionTextHeader);
   }
   public void setInEcapsSectionTextHeader(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 250) {
         throw new PropertyVetoException("InEcapsSectionTextHeader must be <= 250 characters.",
               new PropertyChangeEvent (this, "InEcapsSectionTextHeader", null, null));
      }
      importView.InEcapsSectionTextHeader = StringAttr.valueOf(s, (short)250);
   }
 
   public String getInEcapsSectionTextTextArea() {
      return StringAttr.valueOf(importView.InEcapsSectionTextTextArea);
   }
   public void setInEcapsSectionTextTextArea(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 250) {
         throw new PropertyVetoException("InEcapsSectionTextTextArea must be <= 250 characters.",
               new PropertyChangeEvent (this, "InEcapsSectionTextTextArea", null, null));
      }
      importView.InEcapsSectionTextTextArea = StringAttr.valueOf(s, (short)250);
   }
 
   public Calendar getInEcapsSectionAdminFromDate() {
      return DateAttr.toCalendar(importView.InEcapsSectionAdminFromDate);
   }
   public int getAsIntInEcapsSectionAdminFromDate() {
      return DateAttr.toInt(importView.InEcapsSectionAdminFromDate);
   }
   public void setInEcapsSectionAdminFromDate(Calendar s)
      throws PropertyVetoException {
      importView.InEcapsSectionAdminFromDate = DateAttr.valueOf(s);
   }
   public void setAsStringInEcapsSectionAdminFromDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInEcapsSectionAdminFromDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInEcapsSectionAdminFromDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InEcapsSectionAdminFromDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InEcapsSectionAdminFromDate", null, null));
         }
      }
   }
   public void setAsIntInEcapsSectionAdminFromDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInEcapsSectionAdminFromDate(temp);
   }
 
   public short getInEcapsSectionAdminDueDate() {
      return importView.InEcapsSectionAdminDueDate;
   }
   public void setInEcapsSectionAdminDueDate(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InEcapsSectionAdminDueDate has more than 4 digits.",
               new PropertyChangeEvent (this, "InEcapsSectionAdminDueDate", null, null));
      }
      importView.InEcapsSectionAdminDueDate = ShortAttr.valueOf(s);
   }
   public void setAsStringInEcapsSectionAdminDueDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InEcapsSectionAdminDueDate is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InEcapsSectionAdminDueDate", null, null));
      }
      try {
          setInEcapsSectionAdminDueDate(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InEcapsSectionAdminDueDate is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InEcapsSectionAdminDueDate", null, null));
      }
   }
 
   public String getInEcapsSectionAdminNovellUserId() {
      return StringAttr.valueOf(importView.InEcapsSectionAdminNovellUserId);
   }
   public void setInEcapsSectionAdminNovellUserId(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InEcapsSectionAdminNovellUserId must be <= 10 characters.",
               new PropertyChangeEvent (this, "InEcapsSectionAdminNovellUserId", null, null));
      }
      importView.InEcapsSectionAdminNovellUserId = StringAttr.valueOf(s, (short)10);
   }
 
   public Calendar getInCurrentWsDateDate() {
      return DateAttr.toCalendar(importView.InCurrentWsDateDate);
   }
   public int getAsIntInCurrentWsDateDate() {
      return DateAttr.toInt(importView.InCurrentWsDateDate);
   }
   public void setInCurrentWsDateDate(Calendar s)
      throws PropertyVetoException {
      importView.InCurrentWsDateDate = DateAttr.valueOf(s);
   }
   public void setAsStringInCurrentWsDateDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInCurrentWsDateDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInCurrentWsDateDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InCurrentWsDateDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InCurrentWsDateDate", null, null));
         }
      }
   }
   public void setAsIntInCurrentWsDateDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInCurrentWsDateDate(temp);
   }
 
   public String getOutEcapsSectionId() {
      return StringAttr.valueOf(exportView.OutEcapsSectionId);
   }
 
   public Calendar getOutEcapsSectionTextFromDate() {
      return DateAttr.toCalendar(exportView.OutEcapsSectionTextFromDate);
   }
   public int getAsIntOutEcapsSectionTextFromDate() {
      return DateAttr.toInt(exportView.OutEcapsSectionTextFromDate);
   }
 
   public String getOutEcapsSectionTextPosition() {
      return StringAttr.valueOf(exportView.OutEcapsSectionTextPosition);
   }
 
   public String getOutEcapsSectionTextHeader() {
      return StringAttr.valueOf(exportView.OutEcapsSectionTextHeader);
   }
 
   public String getOutEcapsSectionTextTextArea() {
      return StringAttr.valueOf(exportView.OutEcapsSectionTextTextArea);
   }
 
   public Calendar getOutEcapsSectionAdminFromDate() {
      return DateAttr.toCalendar(exportView.OutEcapsSectionAdminFromDate);
   }
   public int getAsIntOutEcapsSectionAdminFromDate() {
      return DateAttr.toInt(exportView.OutEcapsSectionAdminFromDate);
   }
 
   public short getOutEcapsSectionAdminDueDate() {
      return exportView.OutEcapsSectionAdminDueDate;
   }
 
   public String getOutEcapsSectionAdminNovellUserId() {
      return StringAttr.valueOf(exportView.OutEcapsSectionAdminNovellUserId);
   }
 
};
