package Srmas01h.Abean;
 
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
public class Srmas01sMDStudentActivity  implements ActionListener, java.io.Serializable  {
 
   //  Use final String for the bean name
   public static final String BEANNAME = new String("Srmas01sMDStudentActivity");
 
   //  Constants for Asynchronous status
   public static final int PENDING = CoopFlow.DATA_NOT_READY;
   public static final int AVAILABLE = CoopFlow.DATA_READY;
   public static final int INVALID_ID = CoopFlow.INVALID_ID;
 
   private final SimpleDateFormat nativeTimestampFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
   private final SimpleDateFormat nativeTimeFormatter = new SimpleDateFormat("HHmmss");
   private final SimpleDateFormat nativeDateFormatter = new SimpleDateFormat("yyyyMMdd");
   public Srmas01sMDStudentActivity() {
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
 
 
   private Srmas01sMDStudentActivityOperation oper = null;
 
   /**
    * Calls the transaction/procedure step on the server.
    *
    * @exception java.beans.PropertyVetoException
    * Final property checks can throw this exception.
    */
   public void execute()  throws PropertyVetoException {
      try  {
         if (oper == null) {
            oper = new Srmas01sMDStudentActivityOperation(this);
            addCompletionListener(new operListener(this));
            addExceptionListener(new operListener(this));
         }
 

 
         oper.doSrmas01sMDStudentActivityOperation();
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
      private Srmas01sMDStudentActivity rP;
      operListener(Srmas01sMDStudentActivity r)  {
         rP = r;
      }
      public void actionPerformed(ActionEvent aEvent)  {
         if (Trace.isTracing(Trace.MASK_APPLICATION))
         {
            Trace.record(Trace.MASK_APPLICATION, "Srmas01sMDStudentActivity", "Listener heard that Srmas01sMDStudentActivityOperation completed with " + 
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of completion of operation Srmas01sMDStudentActivity ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      synchronized (this)  {
         targets = (Vector) completionListeners.clone();
      }
      actionEvt = new ActionEvent(this, 0, "Completion.Srmas01sMDStudentActivity");
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of exception of operation Srmas01sMDStudentActivity ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      String failCommand = "Exception.Srmas01sMDStudentActivity";
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
      importView.InPromotorGroup_MA = IntAttr.valueOf(InPromotorGroupMax);
      importView.InGroup_MA = IntAttr.valueOf(InGroupMax);
      importView.InWsStudentPostGraduateStudiesApproved = FixedStringAttr.valueOf("N", 1);
      exportView.OutPromotorGroup_MA = IntAttr.getDefaultValue();
      exportView.OutGroup_MA = IntAttr.getDefaultValue();
      exportView.OutWsStudentPostGraduateStudiesApproved = FixedStringAttr.valueOf("N", 1);
      exportView.OutStudyUnitGroup_MA = IntAttr.getDefaultValue();
   }

   Srmas01h.SRMAS01S_IA importView = Srmas01h.SRMAS01S_IA.getInstance();
   Srmas01h.SRMAS01S_OA exportView = Srmas01h.SRMAS01S_OA.getInstance();
   public final int InPromotorGroupMax = 5;
   public short getInPromotorGroupCount() {
      return (short)(importView.InPromotorGroup_MA);
   };
 
   public void setInPromotorGroupCount(short s) throws PropertyVetoException {
      if (s < 0 || s > InPromotorGroupMax) {
         throw new PropertyVetoException("InPromotorGroupCount value is not a valid value. (0 to 5)",
               new PropertyChangeEvent (this, "InPromotorGroupCount", null, null));
      } else {
         importView.InPromotorGroup_MA = IntAttr.valueOf((int)s);
      }
   }
 
   public int getInGStudentDissertationPromoterMkPromotorNr(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return importView.InGStudentDissertationPromoterMkPromotorNr[index];
   }
   public void setInGStudentDissertationPromoterMkPromotorNr(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InGStudentDissertationPromoterMkPromotorNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InGStudentDissertationPromoterMkPromotorNr", null, null));
      }
      importView.InGStudentDissertationPromoterMkPromotorNr[index] = IntAttr.valueOf(s);
   }
   public void setAsStringInGStudentDissertationPromoterMkPromotorNr(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGStudentDissertationPromoterMkPromotorNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGStudentDissertationPromoterMkPromotorNr", null, null));
      }
      try {
          setInGStudentDissertationPromoterMkPromotorNr(index, Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGStudentDissertationPromoterMkPromotorNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGStudentDissertationPromoterMkPromotorNr", null, null));
      }
   }
 
   public String getInGStudentDissertationPromoterSupervisorFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGStudentDissertationPromoterSupervisorFlag[index], 1);
   }
   public void setInGStudentDissertationPromoterSupervisorFlag(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      Srmas01sMDStudentActivityStudentDissertationPromoterSupervisorFlagPropertyEditor pe = new Srmas01sMDStudentActivityStudentDissertationPromoterSupervisorFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InGStudentDissertationPromoterSupervisorFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InGStudentDissertationPromoterSupervisorFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGStudentDissertationPromoterSupervisorFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGStudentDissertationPromoterSupervisorFlag", null, null));
      }
      importView.InGStudentDissertationPromoterSupervisorFlag[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInGStudentDissertationPromoterMkDepartmentCode(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return importView.InGStudentDissertationPromoterMkDepartmentCode[index];
   }
   public void setInGStudentDissertationPromoterMkDepartmentCode(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InGStudentDissertationPromoterMkDepartmentCode has more than 3 digits.",
               new PropertyChangeEvent (this, "InGStudentDissertationPromoterMkDepartmentCode", null, null));
      }
      importView.InGStudentDissertationPromoterMkDepartmentCode[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGStudentDissertationPromoterMkDepartmentCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGStudentDissertationPromoterMkDepartmentCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGStudentDissertationPromoterMkDepartmentCode", null, null));
      }
      try {
          setInGStudentDissertationPromoterMkDepartmentCode(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGStudentDissertationPromoterMkDepartmentCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGStudentDissertationPromoterMkDepartmentCode", null, null));
      }
   }
 
   public String getInGCsfStringsString100(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGCsfStringsString100[index], 100);
   }
   public void setInGCsfStringsString100(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s.length() > 100) {
         throw new PropertyVetoException("InGCsfStringsString100 must be <= 100 characters.",
               new PropertyChangeEvent (this, "InGCsfStringsString100", null, null));
      }
      importView.InGCsfStringsString100[index] = FixedStringAttr.valueOf(s, (short)100);
   }
 
   public String getInGCsfStringsString28(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGCsfStringsString28[index], 28);
   }
   public void setInGCsfStringsString28(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InGCsfStringsString28 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InGCsfStringsString28", null, null));
      }
      importView.InGCsfStringsString28[index] = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInWsGenericCodeCode() {
      return FixedStringAttr.valueOf(importView.InWsGenericCodeCode, 10);
   }
   public void setInWsGenericCodeCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InWsGenericCodeCode must be <= 10 characters.",
               new PropertyChangeEvent (this, "InWsGenericCodeCode", null, null));
      }
      importView.InWsGenericCodeCode = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public int getInStudentDissertationPromoterMkPromotorNr() {
      return importView.InStudentDissertationPromoterMkPromotorNr;
   }
   public void setInStudentDissertationPromoterMkPromotorNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InStudentDissertationPromoterMkPromotorNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InStudentDissertationPromoterMkPromotorNr", null, null));
      }
      importView.InStudentDissertationPromoterMkPromotorNr = IntAttr.valueOf(s);
   }
   public void setAsStringInStudentDissertationPromoterMkPromotorNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentDissertationPromoterMkPromotorNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentDissertationPromoterMkPromotorNr", null, null));
      }
      try {
          setInStudentDissertationPromoterMkPromotorNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentDissertationPromoterMkPromotorNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentDissertationPromoterMkPromotorNr", null, null));
      }
   }
 
   public String getInStudentDissertationPromoterSupervisorFlag() {
      return FixedStringAttr.valueOf(importView.InStudentDissertationPromoterSupervisorFlag, 1);
   }
   public void setInStudentDissertationPromoterSupervisorFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srmas01sMDStudentActivityStudentDissertationPromoterSupervisorFlagPropertyEditor pe = new Srmas01sMDStudentActivityStudentDissertationPromoterSupervisorFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InStudentDissertationPromoterSupervisorFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InStudentDissertationPromoterSupervisorFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InStudentDissertationPromoterSupervisorFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InStudentDissertationPromoterSupervisorFlag", null, null));
      }
      importView.InStudentDissertationPromoterSupervisorFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInStudentDissertationPromoterType() {
      return FixedStringAttr.valueOf(importView.InStudentDissertationPromoterType, 1);
   }
   public void setInStudentDissertationPromoterType(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InStudentDissertationPromoterType must be <= 1 characters.",
               new PropertyChangeEvent (this, "InStudentDissertationPromoterType", null, null));
      }
      importView.InStudentDissertationPromoterType = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInPromotorNameCsfStringsString50() {
      return FixedStringAttr.valueOf(importView.InPromotorNameCsfStringsString50, 50);
   }
   public void setInPromotorNameCsfStringsString50(String s)
      throws PropertyVetoException {
      if (s.length() > 50) {
         throw new PropertyVetoException("InPromotorNameCsfStringsString50 must be <= 50 characters.",
               new PropertyChangeEvent (this, "InPromotorNameCsfStringsString50", null, null));
      }
      importView.InPromotorNameCsfStringsString50 = FixedStringAttr.valueOf(s, (short)50);
   }
 
   public int getInWsStaffPersno() {
      return importView.InWsStaffPersno;
   }
   public void setInWsStaffPersno(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InWsStaffPersno has more than 8 digits.",
               new PropertyChangeEvent (this, "InWsStaffPersno", null, null));
      }
      importView.InWsStaffPersno = IntAttr.valueOf(s);
   }
   public void setAsStringInWsStaffPersno(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStaffPersno is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStaffPersno", null, null));
      }
      try {
          setInWsStaffPersno(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStaffPersno is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStaffPersno", null, null));
      }
   }
 
   public String getInStaffNameCsfStringsString50() {
      return FixedStringAttr.valueOf(importView.InStaffNameCsfStringsString50, 50);
   }
   public void setInStaffNameCsfStringsString50(String s)
      throws PropertyVetoException {
      if (s.length() > 50) {
         throw new PropertyVetoException("InStaffNameCsfStringsString50 must be <= 50 characters.",
               new PropertyChangeEvent (this, "InStaffNameCsfStringsString50", null, null));
      }
      importView.InStaffNameCsfStringsString50 = FixedStringAttr.valueOf(s, (short)50);
   }
 
   public Calendar getInPositionMdActivityActivityDate() {
      return DateAttr.toCalendar(importView.InPositionMdActivityActivityDate);
   }
   public int getAsIntInPositionMdActivityActivityDate() {
      return DateAttr.toInt(importView.InPositionMdActivityActivityDate);
   }
   public void setInPositionMdActivityActivityDate(Calendar s)
      throws PropertyVetoException {
      importView.InPositionMdActivityActivityDate = DateAttr.valueOf(s);
   }
   public void setAsStringInPositionMdActivityActivityDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInPositionMdActivityActivityDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInPositionMdActivityActivityDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InPositionMdActivityActivityDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InPositionMdActivityActivityDate", null, null));
         }
      }
   }
   public void setAsIntInPositionMdActivityActivityDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInPositionMdActivityActivityDate(temp);
   }
 
   public short getInMaintainMdActivityActivityCode() {
      return importView.InMaintainMdActivityActivityCode;
   }
   public void setInMaintainMdActivityActivityCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InMaintainMdActivityActivityCode has more than 3 digits.",
               new PropertyChangeEvent (this, "InMaintainMdActivityActivityCode", null, null));
      }
      importView.InMaintainMdActivityActivityCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInMaintainMdActivityActivityCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InMaintainMdActivityActivityCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InMaintainMdActivityActivityCode", null, null));
      }
      try {
          setInMaintainMdActivityActivityCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InMaintainMdActivityActivityCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InMaintainMdActivityActivityCode", null, null));
      }
   }
 
   public Calendar getInMaintainMdActivityActivityDate() {
      return DateAttr.toCalendar(importView.InMaintainMdActivityActivityDate);
   }
   public int getAsIntInMaintainMdActivityActivityDate() {
      return DateAttr.toInt(importView.InMaintainMdActivityActivityDate);
   }
   public void setInMaintainMdActivityActivityDate(Calendar s)
      throws PropertyVetoException {
      importView.InMaintainMdActivityActivityDate = DateAttr.valueOf(s);
   }
   public void setAsStringInMaintainMdActivityActivityDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInMaintainMdActivityActivityDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInMaintainMdActivityActivityDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InMaintainMdActivityActivityDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InMaintainMdActivityActivityDate", null, null));
         }
      }
   }
   public void setAsIntInMaintainMdActivityActivityDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInMaintainMdActivityActivityDate(temp);
   }
 
   public Calendar getInMaintainMdActivityFeedbackDate() {
      return DateAttr.toCalendar(importView.InMaintainMdActivityFeedbackDate);
   }
   public int getAsIntInMaintainMdActivityFeedbackDate() {
      return DateAttr.toInt(importView.InMaintainMdActivityFeedbackDate);
   }
   public void setInMaintainMdActivityFeedbackDate(Calendar s)
      throws PropertyVetoException {
      importView.InMaintainMdActivityFeedbackDate = DateAttr.valueOf(s);
   }
   public void setAsStringInMaintainMdActivityFeedbackDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInMaintainMdActivityFeedbackDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInMaintainMdActivityFeedbackDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InMaintainMdActivityFeedbackDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InMaintainMdActivityFeedbackDate", null, null));
         }
      }
   }
   public void setAsIntInMaintainMdActivityFeedbackDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInMaintainMdActivityFeedbackDate(temp);
   }
 
   public String getInMaintainMdActivityComments() {
      return StringAttr.valueOf(importView.InMaintainMdActivityComments);
   }
   public void setInMaintainMdActivityComments(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 150) {
         throw new PropertyVetoException("InMaintainMdActivityComments must be <= 150 characters.",
               new PropertyChangeEvent (this, "InMaintainMdActivityComments", null, null));
      }
      importView.InMaintainMdActivityComments = StringAttr.valueOf(s, (short)150);
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
 
   public int getInGrMdActivityStaffNumber(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return importView.InGrMdActivityStaffNumber[index];
   }
   public void setInGrMdActivityStaffNumber(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InGrMdActivityStaffNumber has more than 8 digits.",
               new PropertyChangeEvent (this, "InGrMdActivityStaffNumber", null, null));
      }
      importView.InGrMdActivityStaffNumber[index] = IntAttr.valueOf(s);
   }
   public void setAsStringInGrMdActivityStaffNumber(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGrMdActivityStaffNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGrMdActivityStaffNumber", null, null));
      }
      try {
          setInGrMdActivityStaffNumber(index, Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGrMdActivityStaffNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGrMdActivityStaffNumber", null, null));
      }
   }
 
   public short getInGrMdActivityActivityCode(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return importView.InGrMdActivityActivityCode[index];
   }
   public void setInGrMdActivityActivityCode(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InGrMdActivityActivityCode has more than 3 digits.",
               new PropertyChangeEvent (this, "InGrMdActivityActivityCode", null, null));
      }
      importView.InGrMdActivityActivityCode[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGrMdActivityActivityCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGrMdActivityActivityCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGrMdActivityActivityCode", null, null));
      }
      try {
          setInGrMdActivityActivityCode(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGrMdActivityActivityCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGrMdActivityActivityCode", null, null));
      }
   }
 
   public Calendar getInGrMdActivityEntryTimestamp(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return TimestampAttr.toCalendar(importView.InGrMdActivityEntryTimestamp[index]);
   }
   public String getAsStringInGrMdActivityEntryTimestamp(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return TimestampAttr.toString(importView.InGrMdActivityEntryTimestamp[index]);
   }
   public void setInGrMdActivityEntryTimestamp(int index, Calendar s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      importView.InGrMdActivityEntryTimestamp[index] = TimestampAttr.valueOf(s);
   }
   public void setAsStringInGrMdActivityEntryTimestamp(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      if (s == null || s.length() == 0) {
         setInGrMdActivityEntryTimestamp(index, (Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeTimestampFormatter.parse(s.length() > 17 ? s.substring(0, 17) : s));
            importView.InGrMdActivityEntryTimestamp[index] = TimestampAttr.valueOf(s);
         } catch (ParseException e) {
            throw new PropertyVetoException("InGrMdActivityEntryTimestamp has an invalid format (yyyyMMddHHmmssSSSSSS).",
                  new PropertyChangeEvent (this, "InGrMdActivityEntryTimestamp", null, null));
         }
      }
   }
 
   public Calendar getInGrMdActivityActivityDate(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return DateAttr.toCalendar(importView.InGrMdActivityActivityDate[index]);
   }
   public int getAsIntInGrMdActivityActivityDate(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return DateAttr.toInt(importView.InGrMdActivityActivityDate[index]);
   }
   public void setInGrMdActivityActivityDate(int index, Calendar s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      importView.InGrMdActivityActivityDate[index] = DateAttr.valueOf(s);
   }
   public void setAsStringInGrMdActivityActivityDate(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      if (s == null || s.length() == 0) {
         setInGrMdActivityActivityDate(index, (Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInGrMdActivityActivityDate(index, tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InGrMdActivityActivityDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InGrMdActivityActivityDate", null, null));
         }
      }
   }
   public void setAsIntInGrMdActivityActivityDate(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInGrMdActivityActivityDate(index, temp);
   }
 
   public Calendar getInGrMdActivityFeedbackDate(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return DateAttr.toCalendar(importView.InGrMdActivityFeedbackDate[index]);
   }
   public int getAsIntInGrMdActivityFeedbackDate(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return DateAttr.toInt(importView.InGrMdActivityFeedbackDate[index]);
   }
   public void setInGrMdActivityFeedbackDate(int index, Calendar s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      importView.InGrMdActivityFeedbackDate[index] = DateAttr.valueOf(s);
   }
   public void setAsStringInGrMdActivityFeedbackDate(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      if (s == null || s.length() == 0) {
         setInGrMdActivityFeedbackDate(index, (Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInGrMdActivityFeedbackDate(index, tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InGrMdActivityFeedbackDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InGrMdActivityFeedbackDate", null, null));
         }
      }
   }
   public void setAsIntInGrMdActivityFeedbackDate(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInGrMdActivityFeedbackDate(index, temp);
   }
 
   public String getInGrMdActivityComments(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return StringAttr.valueOf(importView.InGrMdActivityComments[index]);
   }
   public void setInGrMdActivityComments(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      if (s.length() > 150) {
         throw new PropertyVetoException("InGrMdActivityComments must be <= 150 characters.",
               new PropertyChangeEvent (this, "InGrMdActivityComments", null, null));
      }
      importView.InGrMdActivityComments[index] = StringAttr.valueOf(s, (short)150);
   }
 
   public String getInGrDescriptionsCsfStringsString40(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGrDescriptionsCsfStringsString40[index], 40);
   }
   public void setInGrDescriptionsCsfStringsString40(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      if (s.length() > 40) {
         throw new PropertyVetoException("InGrDescriptionsCsfStringsString40 must be <= 40 characters.",
               new PropertyChangeEvent (this, "InGrDescriptionsCsfStringsString40", null, null));
      }
      importView.InGrDescriptionsCsfStringsString40[index] = FixedStringAttr.valueOf(s, (short)40);
   }
 
   public String getInGrDescriptionsCsfStringsString50(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGrDescriptionsCsfStringsString50[index], 50);
   }
   public void setInGrDescriptionsCsfStringsString50(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      if (s.length() > 50) {
         throw new PropertyVetoException("InGrDescriptionsCsfStringsString50 must be <= 50 characters.",
               new PropertyChangeEvent (this, "InGrDescriptionsCsfStringsString50", null, null));
      }
      importView.InGrDescriptionsCsfStringsString50[index] = FixedStringAttr.valueOf(s, (short)50);
   }
 
   public String getInTypeDescriptionCsfStringsString30() {
      return FixedStringAttr.valueOf(importView.InTypeDescriptionCsfStringsString30, 30);
   }
   public void setInTypeDescriptionCsfStringsString30(String s)
      throws PropertyVetoException {
      if (s.length() > 30) {
         throw new PropertyVetoException("InTypeDescriptionCsfStringsString30 must be <= 30 characters.",
               new PropertyChangeEvent (this, "InTypeDescriptionCsfStringsString30", null, null));
      }
      importView.InTypeDescriptionCsfStringsString30 = FixedStringAttr.valueOf(s, (short)30);
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
 
   public String getInWsStudentPostGraduateStudiesApproved() {
      return FixedStringAttr.valueOf(importView.InWsStudentPostGraduateStudiesApproved, 1);
   }
   public void setInWsStudentPostGraduateStudiesApproved(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srmas01sMDStudentActivityWsStudentPostGraduateStudiesApprovedPropertyEditor pe = new Srmas01sMDStudentActivityWsStudentPostGraduateStudiesApprovedPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InWsStudentPostGraduateStudiesApproved value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsStudentPostGraduateStudiesApproved", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentPostGraduateStudiesApproved must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentPostGraduateStudiesApproved", null, null));
      }
      importView.InWsStudentPostGraduateStudiesApproved = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsStudentAnnualRecordStatusCode() {
      return FixedStringAttr.valueOf(importView.InWsStudentAnnualRecordStatusCode, 2);
   }
   public void setInWsStudentAnnualRecordStatusCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srmas01sMDStudentActivityWsStudentAnnualRecordStatusCodePropertyEditor pe = new Srmas01sMDStudentActivityWsStudentAnnualRecordStatusCodePropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InWsStudentAnnualRecordStatusCode value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordStatusCode", null, null));
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InWsStudentAnnualRecordStatusCode must be <= 2 characters.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordStatusCode", null, null));
      }
      importView.InWsStudentAnnualRecordStatusCode = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public String getInWsStudentAnnualRecordMkHighestQualCode() {
      return FixedStringAttr.valueOf(importView.InWsStudentAnnualRecordMkHighestQualCode, 5);
   }
   public void setInWsStudentAnnualRecordMkHighestQualCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InWsStudentAnnualRecordMkHighestQualCode must be <= 5 characters.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordMkHighestQualCode", null, null));
      }
      importView.InWsStudentAnnualRecordMkHighestQualCode = FixedStringAttr.valueOf(s, (short)5);
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
 
   public int getInStudentDissertationMkStudentNr() {
      return importView.InStudentDissertationMkStudentNr;
   }
   public void setInStudentDissertationMkStudentNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InStudentDissertationMkStudentNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InStudentDissertationMkStudentNr", null, null));
      }
      importView.InStudentDissertationMkStudentNr = IntAttr.valueOf(s);
   }
   public void setAsStringInStudentDissertationMkStudentNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentDissertationMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentDissertationMkStudentNr", null, null));
      }
      try {
          setInStudentDissertationMkStudentNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentDissertationMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentDissertationMkStudentNr", null, null));
      }
   }
 
   public String getInStudentDissertationMkStudyUnitCode() {
      return FixedStringAttr.valueOf(importView.InStudentDissertationMkStudyUnitCode, 7);
   }
   public void setInStudentDissertationMkStudyUnitCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InStudentDissertationMkStudyUnitCode must be <= 7 characters.",
               new PropertyChangeEvent (this, "InStudentDissertationMkStudyUnitCode", null, null));
      }
      importView.InStudentDissertationMkStudyUnitCode = FixedStringAttr.valueOf(s, (short)7);
   }
 
   public String getInStudentDissertationType() {
      return FixedStringAttr.valueOf(importView.InStudentDissertationType, 1);
   }
   public void setInStudentDissertationType(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srmas01sMDStudentActivityStudentDissertationTypePropertyEditor pe = new Srmas01sMDStudentActivityStudentDissertationTypePropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InStudentDissertationType value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InStudentDissertationType", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InStudentDissertationType must be <= 1 characters.",
               new PropertyChangeEvent (this, "InStudentDissertationType", null, null));
      }
      importView.InStudentDissertationType = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInStudentDissertationTitle() {
      return StringAttr.valueOf(importView.InStudentDissertationTitle);
   }
   public void setInStudentDissertationTitle(String s)
      throws PropertyVetoException {
      if (s.length() > 480) {
         throw new PropertyVetoException("InStudentDissertationTitle must be <= 480 characters.",
               new PropertyChangeEvent (this, "InStudentDissertationTitle", null, null));
      }
      importView.InStudentDissertationTitle = StringAttr.valueOf(s, (short)480);
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
 
   public String getInAllowEditCsfStringsString1() {
      return FixedStringAttr.valueOf(importView.InAllowEditCsfStringsString1, 1);
   }
   public void setInAllowEditCsfStringsString1(String s)
      throws PropertyVetoException {
      if (s.length() > 1) {
         throw new PropertyVetoException("InAllowEditCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InAllowEditCsfStringsString1", null, null));
      }
      importView.InAllowEditCsfStringsString1 = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInMessageCsfStringsString500() {
      return StringAttr.valueOf(importView.InMessageCsfStringsString500);
   }
   public void setInMessageCsfStringsString500(String s)
      throws PropertyVetoException {
      if (s.length() > 500) {
         throw new PropertyVetoException("InMessageCsfStringsString500 must be <= 500 characters.",
               new PropertyChangeEvent (this, "InMessageCsfStringsString500", null, null));
      }
      importView.InMessageCsfStringsString500 = StringAttr.valueOf(s, (short)500);
   }
 
   public String getInMaintainCsfStringsString40() {
      return FixedStringAttr.valueOf(importView.InMaintainCsfStringsString40, 40);
   }
   public void setInMaintainCsfStringsString40(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         throw new PropertyVetoException("InMaintainCsfStringsString40 is required and may not be null or zero length.",
               new PropertyChangeEvent (this, "InMaintainCsfStringsString40", null, null));
      }
      if (s.length() > 40) {
         throw new PropertyVetoException("InMaintainCsfStringsString40 must be <= 40 characters.",
               new PropertyChangeEvent (this, "InMaintainCsfStringsString40", null, null));
      }
      importView.InMaintainCsfStringsString40 = FixedStringAttr.valueOf(s, (short)40);
   }
 
   public String getInMaintainCsfStringsString50() {
      return FixedStringAttr.valueOf(importView.InMaintainCsfStringsString50, 50);
   }
   public void setInMaintainCsfStringsString50(String s)
      throws PropertyVetoException {
      if (s.length() > 50) {
         throw new PropertyVetoException("InMaintainCsfStringsString50 must be <= 50 characters.",
               new PropertyChangeEvent (this, "InMaintainCsfStringsString50", null, null));
      }
      importView.InMaintainCsfStringsString50 = FixedStringAttr.valueOf(s, (short)50);
   }
 
   public final int OutPromotorGroupMax = 5;
   public short getOutPromotorGroupCount() {
      return (short)(exportView.OutPromotorGroup_MA);
   };
 
   public int getOutGStudentDissertationPromoterMkPromotorNr(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return exportView.OutGStudentDissertationPromoterMkPromotorNr[index];
   }
 
   public String getOutGStudentDissertationPromoterSupervisorFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGStudentDissertationPromoterSupervisorFlag[index], 1);
   }
 
   public short getOutGStudentDissertationPromoterMkDepartmentCode(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return exportView.OutGStudentDissertationPromoterMkDepartmentCode[index];
   }
 
   public String getOutGCsfStringsString100(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGCsfStringsString100[index], 100);
   }
 
   public String getOutGCsfStringsString28(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGCsfStringsString28[index], 28);
   }
 
   public int getOutStudentDissertationPromoterMkPromotorNr() {
      return exportView.OutStudentDissertationPromoterMkPromotorNr;
   }
 
   public String getOutStudentDissertationPromoterSupervisorFlag() {
      return FixedStringAttr.valueOf(exportView.OutStudentDissertationPromoterSupervisorFlag, 1);
   }
 
   public String getOutStudentDissertationPromoterType() {
      return FixedStringAttr.valueOf(exportView.OutStudentDissertationPromoterType, 1);
   }
 
   public String getOutPromotorNameCsfStringsString50() {
      return FixedStringAttr.valueOf(exportView.OutPromotorNameCsfStringsString50, 50);
   }
 
   public int getOutWsStaffPersno() {
      return exportView.OutWsStaffPersno;
   }
 
   public String getOutStaffNameCsfStringsString50() {
      return FixedStringAttr.valueOf(exportView.OutStaffNameCsfStringsString50, 50);
   }
 
   public short getOutMaintainMdActivityActivityCode() {
      return exportView.OutMaintainMdActivityActivityCode;
   }
 
   public Calendar getOutMaintainMdActivityActivityDate() {
      return DateAttr.toCalendar(exportView.OutMaintainMdActivityActivityDate);
   }
   public int getAsIntOutMaintainMdActivityActivityDate() {
      return DateAttr.toInt(exportView.OutMaintainMdActivityActivityDate);
   }
 
   public Calendar getOutMaintainMdActivityFeedbackDate() {
      return DateAttr.toCalendar(exportView.OutMaintainMdActivityFeedbackDate);
   }
   public int getAsIntOutMaintainMdActivityFeedbackDate() {
      return DateAttr.toInt(exportView.OutMaintainMdActivityFeedbackDate);
   }
 
   public String getOutMaintainMdActivityComments() {
      return StringAttr.valueOf(exportView.OutMaintainMdActivityComments);
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
 
   public int getOutGrMdActivityStaffNumber(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return exportView.OutGrMdActivityStaffNumber[index];
   }
 
   public short getOutGrMdActivityActivityCode(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return exportView.OutGrMdActivityActivityCode[index];
   }
 
   public Calendar getOutGrMdActivityEntryTimestamp(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return TimestampAttr.toCalendar(exportView.OutGrMdActivityEntryTimestamp[index]);
   }
   public String getAsStringOutGrMdActivityEntryTimestamp(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return TimestampAttr.toString(exportView.OutGrMdActivityEntryTimestamp[index]);
   }
 
   public Calendar getOutGrMdActivityActivityDate(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return DateAttr.toCalendar(exportView.OutGrMdActivityActivityDate[index]);
   }
   public int getAsIntOutGrMdActivityActivityDate(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return DateAttr.toInt(exportView.OutGrMdActivityActivityDate[index]);
   }
 
   public Calendar getOutGrMdActivityFeedbackDate(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return DateAttr.toCalendar(exportView.OutGrMdActivityFeedbackDate[index]);
   }
   public int getAsIntOutGrMdActivityFeedbackDate(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return DateAttr.toInt(exportView.OutGrMdActivityFeedbackDate[index]);
   }
 
   public String getOutGrMdActivityComments(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return StringAttr.valueOf(exportView.OutGrMdActivityComments[index]);
   }
 
   public String getOutGrDescriptionsCsfStringsString40(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGrDescriptionsCsfStringsString40[index], 40);
   }
 
   public String getOutGrDescriptionsCsfStringsString50(int index) throws ArrayIndexOutOfBoundsException {
      if (69 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 69, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGrDescriptionsCsfStringsString50[index], 50);
   }
 
   public String getOutTypeDescriptionCsfStringsString30() {
      return FixedStringAttr.valueOf(exportView.OutTypeDescriptionCsfStringsString30, 30);
   }
 
   public int getOutWsStudentNr() {
      return exportView.OutWsStudentNr;
   }
 
   public short getOutWsStudentMkLastAcademicYear() {
      return exportView.OutWsStudentMkLastAcademicYear;
   }
 
   public String getOutWsStudentPostGraduateStudiesApproved() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentPostGraduateStudiesApproved, 1);
   }
 
   public String getOutWsStudentAnnualRecordStatusCode() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentAnnualRecordStatusCode, 2);
   }
 
   public String getOutWsStudentAnnualRecordMkHighestQualCode() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentAnnualRecordMkHighestQualCode, 5);
   }
 
   public String getOutStudentNameCsfStringsString100() {
      return FixedStringAttr.valueOf(exportView.OutStudentNameCsfStringsString100, 100);
   }
 
   public String getOutWsQualificationShortDescription() {
      return FixedStringAttr.valueOf(exportView.OutWsQualificationShortDescription, 12);
   }
 
   public int getOutStudentDissertationMkStudentNr() {
      return exportView.OutStudentDissertationMkStudentNr;
   }
 
   public String getOutStudentDissertationMkStudyUnitCode() {
      return FixedStringAttr.valueOf(exportView.OutStudentDissertationMkStudyUnitCode, 7);
   }
 
   public String getOutStudentDissertationType() {
      return FixedStringAttr.valueOf(exportView.OutStudentDissertationType, 1);
   }
 
   public String getOutStudentDissertationTitle() {
      return StringAttr.valueOf(exportView.OutStudentDissertationTitle);
   }
 
   public final int OutStudyUnitGroupMax = 10;
   public short getOutStudyUnitGroupCount() {
      return (short)(exportView.OutStudyUnitGroup_MA);
   };
 
   public String getOutGEditableCsfStringsString1(int index) throws ArrayIndexOutOfBoundsException {
      if (9 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 9, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGEditableCsfStringsString1[index], 1);
   }
 
   public String getOutGEditableCsfStringsString100(int index) throws ArrayIndexOutOfBoundsException {
      if (9 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 9, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGEditableCsfStringsString100[index], 100);
   }
 
   public String getOutGEditableCsfStringsString3(int index) throws ArrayIndexOutOfBoundsException {
      if (9 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 9, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGEditableCsfStringsString3[index], 3);
   }
 
   public String getOutGEditableCsfStringsString500(int index) throws ArrayIndexOutOfBoundsException {
      if (9 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 9, not: " + index);
      }
      return StringAttr.valueOf(exportView.OutGEditableCsfStringsString500[index]);
   }
 
   public short getOutGWsStudentAnnualRecordMkAcademicYear(int index) throws ArrayIndexOutOfBoundsException {
      if (9 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 9, not: " + index);
      }
      return exportView.OutGWsStudentAnnualRecordMkAcademicYear[index];
   }
 
   public String getOutGWsQualificationCode(int index) throws ArrayIndexOutOfBoundsException {
      if (9 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 9, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsQualificationCode[index], 5);
   }
 
   public String getOutGWsQualificationShortDescription(int index) throws ArrayIndexOutOfBoundsException {
      if (9 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 9, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsQualificationShortDescription[index], 12);
   }
 
   public String getOutGDissEntryIefSuppliedFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (9 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 9, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGDissEntryIefSuppliedFlag[index], 1);
   }
 
   public String getOutGStatusCsfStringsString50(int index) throws ArrayIndexOutOfBoundsException {
      if (9 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 9, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGStatusCsfStringsString50[index], 50);
   }
 
   public String getOutGWsStudyUnitCode(int index) throws ArrayIndexOutOfBoundsException {
      if (9 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 9, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsStudyUnitCode[index], 7);
   }
 
   public String getOutGWsStudyUnitEngShortDescription(int index) throws ArrayIndexOutOfBoundsException {
      if (9 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 9, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsStudyUnitEngShortDescription[index], 28);
   }
 
   public String getOutCsfStringsString500() {
      return StringAttr.valueOf(exportView.OutCsfStringsString500);
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
 
   public String getOutAllowEditCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.OutAllowEditCsfStringsString1, 1);
   }
 
   public String getOutMessageCsfStringsString500() {
      return StringAttr.valueOf(exportView.OutMessageCsfStringsString500);
   }
 
   public String getOutMaintainCsfStringsString40() {
      return FixedStringAttr.valueOf(exportView.OutMaintainCsfStringsString40, 40);
   }
 
   public String getOutMaintainCsfStringsString50() {
      return FixedStringAttr.valueOf(exportView.OutMaintainCsfStringsString50, 50);
   }
 
};
