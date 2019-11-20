package Saacq01h.Abean;
 
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
public class Saacq01sAssessmentCriteriaMnt  implements ActionListener, java.io.Serializable  {
 
   //  Use final String for the bean name
   public static final String BEANNAME = new String("Saacq01sAssessmentCriteriaMnt");
 
   //  Constants for Asynchronous status
   public static final int PENDING = CoopFlow.DATA_NOT_READY;
   public static final int AVAILABLE = CoopFlow.DATA_READY;
   public static final int INVALID_ID = CoopFlow.INVALID_ID;
 
   private final SimpleDateFormat nativeTimestampFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
   private final SimpleDateFormat nativeTimeFormatter = new SimpleDateFormat("HHmmss");
   private final SimpleDateFormat nativeDateFormatter = new SimpleDateFormat("yyyyMMdd");
   private static DecimalFormat decimalFormatter;
 
   public Saacq01sAssessmentCriteriaMnt() {
      super();
      DecimalFormatSymbols symbols = new DecimalFormatSymbols();
      symbols.setDecimalSeparator('.');
      symbols.setGroupingSeparator(',');
      decimalFormatter = new DecimalFormat("###################.###################", symbols);
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
 
 
   private Saacq01sAssessmentCriteriaMntOperation oper = null;
 
   /**
    * Calls the transaction/procedure step on the server.
    *
    * @exception java.beans.PropertyVetoException
    * Final property checks can throw this exception.
    */
   public void execute()  throws PropertyVetoException {
      try  {
         if (oper == null) {
            oper = new Saacq01sAssessmentCriteriaMntOperation(this);
            addCompletionListener(new operListener(this));
            addExceptionListener(new operListener(this));
         }
 

 
         oper.doSaacq01sAssessmentCriteriaMntOperation();
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
      private Saacq01sAssessmentCriteriaMnt rP;
      operListener(Saacq01sAssessmentCriteriaMnt r)  {
         rP = r;
      }
      public void actionPerformed(ActionEvent aEvent)  {
         if (Trace.isTracing(Trace.MASK_APPLICATION))
         {
            Trace.record(Trace.MASK_APPLICATION, "Saacq01sAssessmentCriteriaMnt", "Listener heard that Saacq01sAssessmentCriteriaMntOperation completed with " + 
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of completion of operation Saacq01sAssessmentCriteriaMnt ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      synchronized (this)  {
         targets = (Vector) completionListeners.clone();
      }
      actionEvt = new ActionEvent(this, 0, "Completion.Saacq01sAssessmentCriteriaMnt");
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of exception of operation Saacq01sAssessmentCriteriaMnt ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      String failCommand = "Exception.Saacq01sAssessmentCriteriaMnt";
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
      importView.InAssLanguageGrp_MA = IntAttr.valueOf(InAssLanguageGrpMax);
      importView.InAssMarkerLanguageGrp_MA = IntAttr.valueOf(InAssMarkerLanguageGrpMax);
      importView.InAssFormatGrp_MA = IntAttr.valueOf(InAssFormatGrpMax);
      importView.InMarkerGroup_MA = IntAttr.valueOf(InMarkerGroupMax);
      for( int index = 0; index < 100; index++)
         importView.InMarkerGrpAssignmentMarkingDetailsStatusFlag[index] = FixedStringAttr.valueOf("A", 1);
      for( int index = 0; index < 100; index++)
         importView.InMarkerErrGrpCsfStringsString1[index] = FixedStringAttr.valueOf("M", 1);
      importView.InAssignmentMarkingDetailsStatusFlag = FixedStringAttr.valueOf("A", 1);
      importView.InUniqueAssignmentType = FixedStringAttr.valueOf("A", 1);
      importView.InAssignmentGroup_MA = IntAttr.valueOf(InAssignmentGroupMax);
      for( int index = 0; index < 100; index++)
         importView.InAssErrGrpCsfStringsString1[index] = FixedStringAttr.valueOf("M", 1);
      exportView.OutAssLanguageGrp_MA = IntAttr.getDefaultValue();
      exportView.OutAssMarkerLanguageGroup_MA = IntAttr.getDefaultValue();
      exportView.OutAssFormatGrp_MA = IntAttr.getDefaultValue();
      exportView.OutMarkerGroup_MA = IntAttr.getDefaultValue();
      for( int index = 0; index < 100; index++)
         exportView.OutMarkerGrpAssignmentMarkingDetailsStatusFlag[index] = FixedStringAttr.valueOf("A", 1);
      for( int index = 0; index < 100; index++)
         exportView.OutMarkerErrGrpCsfStringsString1[index] = FixedStringAttr.valueOf("M", 1);
      exportView.OutAssignmentMarkingDetailsStatusFlag = FixedStringAttr.valueOf("A", 1);
      exportView.OutUniqueAssignmentType = FixedStringAttr.valueOf("A", 1);
      exportView.OutAssignmentGroup_MA = IntAttr.getDefaultValue();
      for( int index = 0; index < 100; index++)
         exportView.OutErrGrpCsfStringsString1[index] = FixedStringAttr.valueOf("M", 1);
   }

   Saacq01h.SAACQ011_IA importView = Saacq01h.SAACQ011_IA.getInstance();
   Saacq01h.SAACQ011_OA exportView = Saacq01h.SAACQ011_OA.getInstance();
   public final int InAssLanguageGrpMax = 20;
   public short getInAssLanguageGrpCount() {
      return (short)(importView.InAssLanguageGrp_MA);
   };
 
   public void setInAssLanguageGrpCount(short s) throws PropertyVetoException {
      if (s < 0 || s > InAssLanguageGrpMax) {
         throw new PropertyVetoException("InAssLanguageGrpCount value is not a valid value. (0 to 20)",
               new PropertyChangeEvent (this, "InAssLanguageGrpCount", null, null));
      } else {
         importView.InAssLanguageGrp_MA = IntAttr.valueOf((int)s);
      }
   }
 
   public int getInGrpOnlineAssignmentLangMkUniqueNr(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return importView.InGrpOnlineAssignmentLangMkUniqueNr[index];
   }
   public void setInGrpOnlineAssignmentLangMkUniqueNr(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 10000000.0) {
         throw new PropertyVetoException("InGrpOnlineAssignmentLangMkUniqueNr has more than 7 digits.",
               new PropertyChangeEvent (this, "InGrpOnlineAssignmentLangMkUniqueNr", null, null));
      }
      importView.InGrpOnlineAssignmentLangMkUniqueNr[index] = IntAttr.valueOf(s);
   }
   public void setAsStringInGrpOnlineAssignmentLangMkUniqueNr(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGrpOnlineAssignmentLangMkUniqueNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGrpOnlineAssignmentLangMkUniqueNr", null, null));
      }
      try {
          setInGrpOnlineAssignmentLangMkUniqueNr(index, Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGrpOnlineAssignmentLangMkUniqueNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGrpOnlineAssignmentLangMkUniqueNr", null, null));
      }
   }
 
   public String getInGrpOnlineAssignmentLangLanguageGc203(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGrpOnlineAssignmentLangLanguageGc203[index], 10);
   }
   public void setInGrpOnlineAssignmentLangLanguageGc203(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InGrpOnlineAssignmentLangLanguageGc203 must be <= 10 characters.",
               new PropertyChangeEvent (this, "InGrpOnlineAssignmentLangLanguageGc203", null, null));
      }
      importView.InGrpOnlineAssignmentLangLanguageGc203[index] = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInGrpOnlineAssignmentLangOtherLangDesc(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGrpOnlineAssignmentLangOtherLangDesc[index], 20);
   }
   public void setInGrpOnlineAssignmentLangOtherLangDesc(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      if (s.length() > 20) {
         throw new PropertyVetoException("InGrpOnlineAssignmentLangOtherLangDesc must be <= 20 characters.",
               new PropertyChangeEvent (this, "InGrpOnlineAssignmentLangOtherLangDesc", null, null));
      }
      importView.InGrpOnlineAssignmentLangOtherLangDesc[index] = FixedStringAttr.valueOf(s, (short)20);
   }
 
   public final int InAssMarkerLanguageGrpMax = 20;
   public short getInAssMarkerLanguageGrpCount() {
      return (short)(importView.InAssMarkerLanguageGrp_MA);
   };
 
   public void setInAssMarkerLanguageGrpCount(short s) throws PropertyVetoException {
      if (s < 0 || s > InAssMarkerLanguageGrpMax) {
         throw new PropertyVetoException("InAssMarkerLanguageGrpCount value is not a valid value. (0 to 20)",
               new PropertyChangeEvent (this, "InAssMarkerLanguageGrpCount", null, null));
      } else {
         importView.InAssMarkerLanguageGrp_MA = IntAttr.valueOf((int)s);
      }
   }
 
   public int getInGrpAssignmentMarkerLangMkUniqueNr(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return importView.InGrpAssignmentMarkerLangMkUniqueNr[index];
   }
   public void setInGrpAssignmentMarkerLangMkUniqueNr(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 10000000.0) {
         throw new PropertyVetoException("InGrpAssignmentMarkerLangMkUniqueNr has more than 7 digits.",
               new PropertyChangeEvent (this, "InGrpAssignmentMarkerLangMkUniqueNr", null, null));
      }
      importView.InGrpAssignmentMarkerLangMkUniqueNr[index] = IntAttr.valueOf(s);
   }
   public void setAsStringInGrpAssignmentMarkerLangMkUniqueNr(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGrpAssignmentMarkerLangMkUniqueNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGrpAssignmentMarkerLangMkUniqueNr", null, null));
      }
      try {
          setInGrpAssignmentMarkerLangMkUniqueNr(index, Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGrpAssignmentMarkerLangMkUniqueNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGrpAssignmentMarkerLangMkUniqueNr", null, null));
      }
   }
 
   public int getInGrpAssignmentMarkerLangMkLecturerNr(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return importView.InGrpAssignmentMarkerLangMkLecturerNr[index];
   }
   public void setInGrpAssignmentMarkerLangMkLecturerNr(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InGrpAssignmentMarkerLangMkLecturerNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InGrpAssignmentMarkerLangMkLecturerNr", null, null));
      }
      importView.InGrpAssignmentMarkerLangMkLecturerNr[index] = IntAttr.valueOf(s);
   }
   public void setAsStringInGrpAssignmentMarkerLangMkLecturerNr(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGrpAssignmentMarkerLangMkLecturerNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGrpAssignmentMarkerLangMkLecturerNr", null, null));
      }
      try {
          setInGrpAssignmentMarkerLangMkLecturerNr(index, Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGrpAssignmentMarkerLangMkLecturerNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGrpAssignmentMarkerLangMkLecturerNr", null, null));
      }
   }
 
   public String getInGrpAssignmentMarkerLangLanguageGc203(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGrpAssignmentMarkerLangLanguageGc203[index], 10);
   }
   public void setInGrpAssignmentMarkerLangLanguageGc203(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InGrpAssignmentMarkerLangLanguageGc203 must be <= 10 characters.",
               new PropertyChangeEvent (this, "InGrpAssignmentMarkerLangLanguageGc203", null, null));
      }
      importView.InGrpAssignmentMarkerLangLanguageGc203[index] = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInGrpAssignmentMarkerLangOtherLangDesc(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGrpAssignmentMarkerLangOtherLangDesc[index], 20);
   }
   public void setInGrpAssignmentMarkerLangOtherLangDesc(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      if (s.length() > 20) {
         throw new PropertyVetoException("InGrpAssignmentMarkerLangOtherLangDesc must be <= 20 characters.",
               new PropertyChangeEvent (this, "InGrpAssignmentMarkerLangOtherLangDesc", null, null));
      }
      importView.InGrpAssignmentMarkerLangOtherLangDesc[index] = FixedStringAttr.valueOf(s, (short)20);
   }
 
   public final int InAssFormatGrpMax = 50;
   public short getInAssFormatGrpCount() {
      return (short)(importView.InAssFormatGrp_MA);
   };
 
   public void setInAssFormatGrpCount(short s) throws PropertyVetoException {
      if (s < 0 || s > InAssFormatGrpMax) {
         throw new PropertyVetoException("InAssFormatGrpCount value is not a valid value. (0 to 50)",
               new PropertyChangeEvent (this, "InAssFormatGrpCount", null, null));
      } else {
         importView.InAssFormatGrp_MA = IntAttr.valueOf((int)s);
      }
   }
 
   public String getInGrpOnlineAssignmentFormatsMkStudyUnitCode(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGrpOnlineAssignmentFormatsMkStudyUnitCode[index], 7);
   }
   public void setInGrpOnlineAssignmentFormatsMkStudyUnitCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InGrpOnlineAssignmentFormatsMkStudyUnitCode must be <= 7 characters.",
               new PropertyChangeEvent (this, "InGrpOnlineAssignmentFormatsMkStudyUnitCode", null, null));
      }
      importView.InGrpOnlineAssignmentFormatsMkStudyUnitCode[index] = FixedStringAttr.valueOf(s, (short)7);
   }
 
   public String getInGrpOnlineAssignmentFormatsExtention(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGrpOnlineAssignmentFormatsExtention[index], 4);
   }
   public void setInGrpOnlineAssignmentFormatsExtention(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 4) {
         throw new PropertyVetoException("InGrpOnlineAssignmentFormatsExtention must be <= 4 characters.",
               new PropertyChangeEvent (this, "InGrpOnlineAssignmentFormatsExtention", null, null));
      }
      importView.InGrpOnlineAssignmentFormatsExtention[index] = FixedStringAttr.valueOf(s, (short)4);
   }
 
   public int getInGrpOnlineAssignmentFormatsMkUniqueNr(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return importView.InGrpOnlineAssignmentFormatsMkUniqueNr[index];
   }
   public void setInGrpOnlineAssignmentFormatsMkUniqueNr(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 10000000.0) {
         throw new PropertyVetoException("InGrpOnlineAssignmentFormatsMkUniqueNr has more than 7 digits.",
               new PropertyChangeEvent (this, "InGrpOnlineAssignmentFormatsMkUniqueNr", null, null));
      }
      importView.InGrpOnlineAssignmentFormatsMkUniqueNr[index] = IntAttr.valueOf(s);
   }
   public void setAsStringInGrpOnlineAssignmentFormatsMkUniqueNr(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGrpOnlineAssignmentFormatsMkUniqueNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGrpOnlineAssignmentFormatsMkUniqueNr", null, null));
      }
      try {
          setInGrpOnlineAssignmentFormatsMkUniqueNr(index, Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGrpOnlineAssignmentFormatsMkUniqueNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGrpOnlineAssignmentFormatsMkUniqueNr", null, null));
      }
   }
 
   public String getInGrpOnlineAssignmentFormatsDescription(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGrpOnlineAssignmentFormatsDescription[index], 32);
   }
   public void setInGrpOnlineAssignmentFormatsDescription(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 32) {
         throw new PropertyVetoException("InGrpOnlineAssignmentFormatsDescription must be <= 32 characters.",
               new PropertyChangeEvent (this, "InGrpOnlineAssignmentFormatsDescription", null, null));
      }
      importView.InGrpOnlineAssignmentFormatsDescription[index] = FixedStringAttr.valueOf(s, (short)32);
   }
 
   public short getInAssessmentLogYear() {
      return importView.InAssessmentLogYear;
   }
   public void setInAssessmentLogYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InAssessmentLogYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InAssessmentLogYear", null, null));
      }
      importView.InAssessmentLogYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInAssessmentLogYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InAssessmentLogYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InAssessmentLogYear", null, null));
      }
      try {
          setInAssessmentLogYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InAssessmentLogYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InAssessmentLogYear", null, null));
      }
   }
 
   public short getInAssessmentLogPeriod() {
      return importView.InAssessmentLogPeriod;
   }
   public void setInAssessmentLogPeriod(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InAssessmentLogPeriod has more than 2 digits.",
               new PropertyChangeEvent (this, "InAssessmentLogPeriod", null, null));
      }
      importView.InAssessmentLogPeriod = ShortAttr.valueOf(s);
   }
   public void setAsStringInAssessmentLogPeriod(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InAssessmentLogPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InAssessmentLogPeriod", null, null));
      }
      try {
          setInAssessmentLogPeriod(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InAssessmentLogPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InAssessmentLogPeriod", null, null));
      }
   }
 
   public String getInAssessmentLogMkStudyUnitCode() {
      return StringAttr.valueOf(importView.InAssessmentLogMkStudyUnitCode);
   }
   public void setInAssessmentLogMkStudyUnitCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InAssessmentLogMkStudyUnitCode must be <= 7 characters.",
               new PropertyChangeEvent (this, "InAssessmentLogMkStudyUnitCode", null, null));
      }
      importView.InAssessmentLogMkStudyUnitCode = StringAttr.valueOf(s, (short)7);
   }
 
   public String getInAssessmentLogTypeGc52() {
      return StringAttr.valueOf(importView.InAssessmentLogTypeGc52);
   }
   public void setInAssessmentLogTypeGc52(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InAssessmentLogTypeGc52 must be <= 10 characters.",
               new PropertyChangeEvent (this, "InAssessmentLogTypeGc52", null, null));
      }
      importView.InAssessmentLogTypeGc52 = StringAttr.valueOf(s, (short)10);
   }
 
   public String getInAssessmentLogActionGc53() {
      return StringAttr.valueOf(importView.InAssessmentLogActionGc53);
   }
   public void setInAssessmentLogActionGc53(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InAssessmentLogActionGc53 must be <= 10 characters.",
               new PropertyChangeEvent (this, "InAssessmentLogActionGc53", null, null));
      }
      importView.InAssessmentLogActionGc53 = StringAttr.valueOf(s, (short)10);
   }
 
   public String getInAssessmentLogComments() {
      return StringAttr.valueOf(importView.InAssessmentLogComments);
   }
   public void setInAssessmentLogComments(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 250) {
         throw new PropertyVetoException("InAssessmentLogComments must be <= 250 characters.",
               new PropertyChangeEvent (this, "InAssessmentLogComments", null, null));
      }
      importView.InAssessmentLogComments = StringAttr.valueOf(s, (short)250);
   }
 
   public String getInAssessmentLogReturnEmailAddress() {
      return StringAttr.valueOf(importView.InAssessmentLogReturnEmailAddress);
   }
   public void setInAssessmentLogReturnEmailAddress(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 60) {
         throw new PropertyVetoException("InAssessmentLogReturnEmailAddress must be <= 60 characters.",
               new PropertyChangeEvent (this, "InAssessmentLogReturnEmailAddress", null, null));
      }
      importView.InAssessmentLogReturnEmailAddress = StringAttr.valueOf(s, (short)60);
   }
 
   public String getInAssessmentLogUpdatedBy() {
      return StringAttr.valueOf(importView.InAssessmentLogUpdatedBy);
   }
   public void setInAssessmentLogUpdatedBy(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 20) {
         throw new PropertyVetoException("InAssessmentLogUpdatedBy must be <= 20 characters.",
               new PropertyChangeEvent (this, "InAssessmentLogUpdatedBy", null, null));
      }
      importView.InAssessmentLogUpdatedBy = StringAttr.valueOf(s, (short)20);
   }
 
   public Calendar getInAssessmentLogUpdatedOn() {
      return TimestampAttr.toCalendar(importView.InAssessmentLogUpdatedOn);
   }
   public String getAsStringInAssessmentLogUpdatedOn() {
      return TimestampAttr.toString(importView.InAssessmentLogUpdatedOn);
   }
   public void setInAssessmentLogUpdatedOn(Calendar s)
      throws PropertyVetoException {
      importView.InAssessmentLogUpdatedOn = TimestampAttr.valueOf(s);
   }
   public void setAsStringInAssessmentLogUpdatedOn(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInAssessmentLogUpdatedOn((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeTimestampFormatter.parse(s.length() > 17 ? s.substring(0, 17) : s));
            importView.InAssessmentLogUpdatedOn = TimestampAttr.valueOf(s);
         } catch (ParseException e) {
            throw new PropertyVetoException("InAssessmentLogUpdatedOn has an invalid format (yyyyMMddHHmmssSSSSSS).",
                  new PropertyChangeEvent (this, "InAssessmentLogUpdatedOn", null, null));
         }
      }
   }
 
   public String getInAssessmentLogRequestActionFrom() {
      return StringAttr.valueOf(importView.InAssessmentLogRequestActionFrom);
   }
   public void setInAssessmentLogRequestActionFrom(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 20) {
         throw new PropertyVetoException("InAssessmentLogRequestActionFrom must be <= 20 characters.",
               new PropertyChangeEvent (this, "InAssessmentLogRequestActionFrom", null, null));
      }
      importView.InAssessmentLogRequestActionFrom = StringAttr.valueOf(s, (short)20);
   }
 
   public final int InMarkerGroupMax = 100;
   public short getInMarkerGroupCount() {
      return (short)(importView.InMarkerGroup_MA);
   };
 
   public void setInMarkerGroupCount(short s) throws PropertyVetoException {
      if (s < 0 || s > InMarkerGroupMax) {
         throw new PropertyVetoException("InMarkerGroupCount value is not a valid value. (0 to 100)",
               new PropertyChangeEvent (this, "InMarkerGroupCount", null, null));
      } else {
         importView.InMarkerGroup_MA = IntAttr.valueOf((int)s);
      }
   }
 
   public int getInMarkerGrpAssignmentMarkingDetailsMkLecturerNr(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return importView.InMarkerGrpAssignmentMarkingDetailsMkLecturerNr[index];
   }
   public void setInMarkerGrpAssignmentMarkingDetailsMkLecturerNr(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InMarkerGrpAssignmentMarkingDetailsMkLecturerNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InMarkerGrpAssignmentMarkingDetailsMkLecturerNr", null, null));
      }
      importView.InMarkerGrpAssignmentMarkingDetailsMkLecturerNr[index] = IntAttr.valueOf(s);
   }
   public void setAsStringInMarkerGrpAssignmentMarkingDetailsMkLecturerNr(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InMarkerGrpAssignmentMarkingDetailsMkLecturerNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InMarkerGrpAssignmentMarkingDetailsMkLecturerNr", null, null));
      }
      try {
          setInMarkerGrpAssignmentMarkingDetailsMkLecturerNr(index, Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InMarkerGrpAssignmentMarkingDetailsMkLecturerNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InMarkerGrpAssignmentMarkingDetailsMkLecturerNr", null, null));
      }
   }
 
   public short getInMarkerGrpAssignmentMarkingDetailsMarkPercentage(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return importView.InMarkerGrpAssignmentMarkingDetailsMarkPercentage[index];
   }
   public void setInMarkerGrpAssignmentMarkingDetailsMarkPercentage(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InMarkerGrpAssignmentMarkingDetailsMarkPercentage has more than 3 digits.",
               new PropertyChangeEvent (this, "InMarkerGrpAssignmentMarkingDetailsMarkPercentage", null, null));
      }
      importView.InMarkerGrpAssignmentMarkingDetailsMarkPercentage[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInMarkerGrpAssignmentMarkingDetailsMarkPercentage(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InMarkerGrpAssignmentMarkingDetailsMarkPercentage is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InMarkerGrpAssignmentMarkingDetailsMarkPercentage", null, null));
      }
      try {
          setInMarkerGrpAssignmentMarkingDetailsMarkPercentage(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InMarkerGrpAssignmentMarkingDetailsMarkPercentage is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InMarkerGrpAssignmentMarkingDetailsMarkPercentage", null, null));
      }
   }
 
   public int getInMarkerGrpAssignmentMarkingDetailsNrOutstanding(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return importView.InMarkerGrpAssignmentMarkingDetailsNrOutstanding[index];
   }
   public void setInMarkerGrpAssignmentMarkingDetailsNrOutstanding(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InMarkerGrpAssignmentMarkingDetailsNrOutstanding has more than 5 digits.",
               new PropertyChangeEvent (this, "InMarkerGrpAssignmentMarkingDetailsNrOutstanding", null, null));
      }
      importView.InMarkerGrpAssignmentMarkingDetailsNrOutstanding[index] = IntAttr.valueOf(s);
   }
   public void setAsStringInMarkerGrpAssignmentMarkingDetailsNrOutstanding(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InMarkerGrpAssignmentMarkingDetailsNrOutstanding is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InMarkerGrpAssignmentMarkingDetailsNrOutstanding", null, null));
      }
      try {
          setInMarkerGrpAssignmentMarkingDetailsNrOutstanding(index, Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InMarkerGrpAssignmentMarkingDetailsNrOutstanding is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InMarkerGrpAssignmentMarkingDetailsNrOutstanding", null, null));
      }
   }
 
   public int getInMarkerGrpAssignmentMarkingDetailsNrReturned(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return importView.InMarkerGrpAssignmentMarkingDetailsNrReturned[index];
   }
   public void setInMarkerGrpAssignmentMarkingDetailsNrReturned(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InMarkerGrpAssignmentMarkingDetailsNrReturned has more than 5 digits.",
               new PropertyChangeEvent (this, "InMarkerGrpAssignmentMarkingDetailsNrReturned", null, null));
      }
      importView.InMarkerGrpAssignmentMarkingDetailsNrReturned[index] = IntAttr.valueOf(s);
   }
   public void setAsStringInMarkerGrpAssignmentMarkingDetailsNrReturned(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InMarkerGrpAssignmentMarkingDetailsNrReturned is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InMarkerGrpAssignmentMarkingDetailsNrReturned", null, null));
      }
      try {
          setInMarkerGrpAssignmentMarkingDetailsNrReturned(index, Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InMarkerGrpAssignmentMarkingDetailsNrReturned is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InMarkerGrpAssignmentMarkingDetailsNrReturned", null, null));
      }
   }
 
   public short getInMarkerGrpAssignmentMarkingDetailsAverageMark(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return importView.InMarkerGrpAssignmentMarkingDetailsAverageMark[index];
   }
   public void setInMarkerGrpAssignmentMarkingDetailsAverageMark(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InMarkerGrpAssignmentMarkingDetailsAverageMark has more than 4 digits.",
               new PropertyChangeEvent (this, "InMarkerGrpAssignmentMarkingDetailsAverageMark", null, null));
      }
      importView.InMarkerGrpAssignmentMarkingDetailsAverageMark[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInMarkerGrpAssignmentMarkingDetailsAverageMark(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InMarkerGrpAssignmentMarkingDetailsAverageMark is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InMarkerGrpAssignmentMarkingDetailsAverageMark", null, null));
      }
      try {
          setInMarkerGrpAssignmentMarkingDetailsAverageMark(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InMarkerGrpAssignmentMarkingDetailsAverageMark is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InMarkerGrpAssignmentMarkingDetailsAverageMark", null, null));
      }
   }
 
   public String getInMarkerGrpAssignmentMarkingDetailsStatusFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InMarkerGrpAssignmentMarkingDetailsStatusFlag[index], 1);
   }
   public void setInMarkerGrpAssignmentMarkingDetailsStatusFlag(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      Saacq01sAssessmentCriteriaMntAssignmentMarkingDetailsStatusFlagPropertyEditor pe = new Saacq01sAssessmentCriteriaMntAssignmentMarkingDetailsStatusFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InMarkerGrpAssignmentMarkingDetailsStatusFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InMarkerGrpAssignmentMarkingDetailsStatusFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InMarkerGrpAssignmentMarkingDetailsStatusFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InMarkerGrpAssignmentMarkingDetailsStatusFlag", null, null));
      }
      importView.InMarkerGrpAssignmentMarkingDetailsStatusFlag[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInMarkerErrGrpCsfStringsString1(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InMarkerErrGrpCsfStringsString1[index], 1);
   }
   public void setInMarkerErrGrpCsfStringsString1(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      Saacq01sAssessmentCriteriaMntCsfStringsString1PropertyEditor pe = new Saacq01sAssessmentCriteriaMntCsfStringsString1PropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InMarkerErrGrpCsfStringsString1 value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InMarkerErrGrpCsfStringsString1", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InMarkerErrGrpCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InMarkerErrGrpCsfStringsString1", null, null));
      }
      importView.InMarkerErrGrpCsfStringsString1[index] = FixedStringAttr.valueOf(s, (short)1);
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
 
   public int getInStudentMarkReadingSheetMkStudentNr() {
      return importView.InStudentMarkReadingSheetMkStudentNr;
   }
   public void setInStudentMarkReadingSheetMkStudentNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InStudentMarkReadingSheetMkStudentNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InStudentMarkReadingSheetMkStudentNr", null, null));
      }
      importView.InStudentMarkReadingSheetMkStudentNr = IntAttr.valueOf(s);
   }
   public void setAsStringInStudentMarkReadingSheetMkStudentNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentMarkReadingSheetMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentMarkReadingSheetMkStudentNr", null, null));
      }
      try {
          setInStudentMarkReadingSheetMkStudentNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentMarkReadingSheetMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentMarkReadingSheetMkStudentNr", null, null));
      }
   }
 
   public double getInStudentMarkReadingSheetBatchNr() {
      return importView.InStudentMarkReadingSheetBatchNr;
   }
   public void setInStudentMarkReadingSheetBatchNr(double s)
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
         throw new PropertyVetoException("InStudentMarkReadingSheetBatchNr has more than 0 fractional digits.",
               new PropertyChangeEvent (this, "InStudentMarkReadingSheetBatchNr", null, null));
      }
      if (java.lang.Math.abs(s) >= 10000000000.0) {
         throw new PropertyVetoException("InStudentMarkReadingSheetBatchNr has more than 10 integral digits.",
               new PropertyChangeEvent (this, "InStudentMarkReadingSheetBatchNr", null, null));
      }
      importView.InStudentMarkReadingSheetBatchNr = DoubleAttr.valueOf(s);
   }
   public void setAsStringInStudentMarkReadingSheetBatchNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentMarkReadingSheetBatchNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentMarkReadingSheetBatchNr", null, null));
      }
      try {
          setInStudentMarkReadingSheetBatchNr(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentMarkReadingSheetBatchNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentMarkReadingSheetBatchNr", null, null));
      }
   }
 
   public short getInStudentMarkReadingSheetBatchPosition() {
      return importView.InStudentMarkReadingSheetBatchPosition;
   }
   public void setInStudentMarkReadingSheetBatchPosition(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InStudentMarkReadingSheetBatchPosition has more than 4 digits.",
               new PropertyChangeEvent (this, "InStudentMarkReadingSheetBatchPosition", null, null));
      }
      importView.InStudentMarkReadingSheetBatchPosition = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentMarkReadingSheetBatchPosition(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentMarkReadingSheetBatchPosition is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentMarkReadingSheetBatchPosition", null, null));
      }
      try {
          setInStudentMarkReadingSheetBatchPosition(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentMarkReadingSheetBatchPosition is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentMarkReadingSheetBatchPosition", null, null));
      }
   }
 
   public short getInStudentMarkReadingSheetFinalMarkCode() {
      return importView.InStudentMarkReadingSheetFinalMarkCode;
   }
   public void setInStudentMarkReadingSheetFinalMarkCode(short s)
      throws PropertyVetoException {
      Saacq01sAssessmentCriteriaMntStudentMarkReadingSheetFinalMarkCodePropertyEditor pe = new Saacq01sAssessmentCriteriaMntStudentMarkReadingSheetFinalMarkCodePropertyEditor();
      if (pe.checkTag(String.valueOf(s)) == false) {
         throw new PropertyVetoException("InStudentMarkReadingSheetFinalMarkCode value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InStudentMarkReadingSheetFinalMarkCode", null, null));
      }
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InStudentMarkReadingSheetFinalMarkCode has more than 1 digits.",
               new PropertyChangeEvent (this, "InStudentMarkReadingSheetFinalMarkCode", null, null));
      }
      importView.InStudentMarkReadingSheetFinalMarkCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentMarkReadingSheetFinalMarkCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentMarkReadingSheetFinalMarkCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentMarkReadingSheetFinalMarkCode", null, null));
      }
      try {
          setInStudentMarkReadingSheetFinalMarkCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentMarkReadingSheetFinalMarkCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentMarkReadingSheetFinalMarkCode", null, null));
      }
   }
 
   public String getInStudentMarkReadingSheetAnswers() {
      return StringAttr.valueOf(importView.InStudentMarkReadingSheetAnswers);
   }
   public void setInStudentMarkReadingSheetAnswers(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 140) {
         throw new PropertyVetoException("InStudentMarkReadingSheetAnswers must be <= 140 characters.",
               new PropertyChangeEvent (this, "InStudentMarkReadingSheetAnswers", null, null));
      }
      importView.InStudentMarkReadingSheetAnswers = StringAttr.valueOf(s, (short)140);
   }
 
   public short getInStudentMarkReadingSheetCalculatedMark() {
      return importView.InStudentMarkReadingSheetCalculatedMark;
   }
   public void setInStudentMarkReadingSheetCalculatedMark(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InStudentMarkReadingSheetCalculatedMark has more than 3 digits.",
               new PropertyChangeEvent (this, "InStudentMarkReadingSheetCalculatedMark", null, null));
      }
      importView.InStudentMarkReadingSheetCalculatedMark = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentMarkReadingSheetCalculatedMark(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentMarkReadingSheetCalculatedMark is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentMarkReadingSheetCalculatedMark", null, null));
      }
      try {
          setInStudentMarkReadingSheetCalculatedMark(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentMarkReadingSheetCalculatedMark is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentMarkReadingSheetCalculatedMark", null, null));
      }
   }
 
   public String getInStudentMarkReadingSheetSolFlag() {
      return StringAttr.valueOf(importView.InStudentMarkReadingSheetSolFlag);
   }
   public void setInStudentMarkReadingSheetSolFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InStudentMarkReadingSheetSolFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InStudentMarkReadingSheetSolFlag", null, null));
      }
      importView.InStudentMarkReadingSheetSolFlag = StringAttr.valueOf(s, (short)1);
   }
 
   public int getInAssignmentMarkingDetailsMkLecturerNr() {
      return importView.InAssignmentMarkingDetailsMkLecturerNr;
   }
   public void setInAssignmentMarkingDetailsMkLecturerNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InAssignmentMarkingDetailsMkLecturerNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InAssignmentMarkingDetailsMkLecturerNr", null, null));
      }
      importView.InAssignmentMarkingDetailsMkLecturerNr = IntAttr.valueOf(s);
   }
   public void setAsStringInAssignmentMarkingDetailsMkLecturerNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InAssignmentMarkingDetailsMkLecturerNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InAssignmentMarkingDetailsMkLecturerNr", null, null));
      }
      try {
          setInAssignmentMarkingDetailsMkLecturerNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InAssignmentMarkingDetailsMkLecturerNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InAssignmentMarkingDetailsMkLecturerNr", null, null));
      }
   }
 
   public short getInAssignmentMarkingDetailsMarkPercentage() {
      return importView.InAssignmentMarkingDetailsMarkPercentage;
   }
   public void setInAssignmentMarkingDetailsMarkPercentage(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InAssignmentMarkingDetailsMarkPercentage has more than 3 digits.",
               new PropertyChangeEvent (this, "InAssignmentMarkingDetailsMarkPercentage", null, null));
      }
      importView.InAssignmentMarkingDetailsMarkPercentage = ShortAttr.valueOf(s);
   }
   public void setAsStringInAssignmentMarkingDetailsMarkPercentage(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InAssignmentMarkingDetailsMarkPercentage is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InAssignmentMarkingDetailsMarkPercentage", null, null));
      }
      try {
          setInAssignmentMarkingDetailsMarkPercentage(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InAssignmentMarkingDetailsMarkPercentage is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InAssignmentMarkingDetailsMarkPercentage", null, null));
      }
   }
 
   public int getInAssignmentMarkingDetailsNrOutstanding() {
      return importView.InAssignmentMarkingDetailsNrOutstanding;
   }
   public void setInAssignmentMarkingDetailsNrOutstanding(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InAssignmentMarkingDetailsNrOutstanding has more than 5 digits.",
               new PropertyChangeEvent (this, "InAssignmentMarkingDetailsNrOutstanding", null, null));
      }
      importView.InAssignmentMarkingDetailsNrOutstanding = IntAttr.valueOf(s);
   }
   public void setAsStringInAssignmentMarkingDetailsNrOutstanding(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InAssignmentMarkingDetailsNrOutstanding is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InAssignmentMarkingDetailsNrOutstanding", null, null));
      }
      try {
          setInAssignmentMarkingDetailsNrOutstanding(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InAssignmentMarkingDetailsNrOutstanding is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InAssignmentMarkingDetailsNrOutstanding", null, null));
      }
   }
 
   public int getInAssignmentMarkingDetailsNrReturned() {
      return importView.InAssignmentMarkingDetailsNrReturned;
   }
   public void setInAssignmentMarkingDetailsNrReturned(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InAssignmentMarkingDetailsNrReturned has more than 5 digits.",
               new PropertyChangeEvent (this, "InAssignmentMarkingDetailsNrReturned", null, null));
      }
      importView.InAssignmentMarkingDetailsNrReturned = IntAttr.valueOf(s);
   }
   public void setAsStringInAssignmentMarkingDetailsNrReturned(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InAssignmentMarkingDetailsNrReturned is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InAssignmentMarkingDetailsNrReturned", null, null));
      }
      try {
          setInAssignmentMarkingDetailsNrReturned(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InAssignmentMarkingDetailsNrReturned is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InAssignmentMarkingDetailsNrReturned", null, null));
      }
   }
 
   public short getInAssignmentMarkingDetailsAverageMark() {
      return importView.InAssignmentMarkingDetailsAverageMark;
   }
   public void setInAssignmentMarkingDetailsAverageMark(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InAssignmentMarkingDetailsAverageMark has more than 4 digits.",
               new PropertyChangeEvent (this, "InAssignmentMarkingDetailsAverageMark", null, null));
      }
      importView.InAssignmentMarkingDetailsAverageMark = ShortAttr.valueOf(s);
   }
   public void setAsStringInAssignmentMarkingDetailsAverageMark(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InAssignmentMarkingDetailsAverageMark is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InAssignmentMarkingDetailsAverageMark", null, null));
      }
      try {
          setInAssignmentMarkingDetailsAverageMark(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InAssignmentMarkingDetailsAverageMark is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InAssignmentMarkingDetailsAverageMark", null, null));
      }
   }
 
   public String getInAssignmentMarkingDetailsStatusFlag() {
      return FixedStringAttr.valueOf(importView.InAssignmentMarkingDetailsStatusFlag, 1);
   }
   public void setInAssignmentMarkingDetailsStatusFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Saacq01sAssessmentCriteriaMntAssignmentMarkingDetailsStatusFlagPropertyEditor pe = new Saacq01sAssessmentCriteriaMntAssignmentMarkingDetailsStatusFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InAssignmentMarkingDetailsStatusFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InAssignmentMarkingDetailsStatusFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InAssignmentMarkingDetailsStatusFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InAssignmentMarkingDetailsStatusFlag", null, null));
      }
      importView.InAssignmentMarkingDetailsStatusFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInUniqueAssignmentYear() {
      return importView.InUniqueAssignmentYear;
   }
   public void setInUniqueAssignmentYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InUniqueAssignmentYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InUniqueAssignmentYear", null, null));
      }
      importView.InUniqueAssignmentYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInUniqueAssignmentYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InUniqueAssignmentYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InUniqueAssignmentYear", null, null));
      }
      try {
          setInUniqueAssignmentYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InUniqueAssignmentYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InUniqueAssignmentYear", null, null));
      }
   }
 
   public short getInUniqueAssignmentPeriod() {
      return importView.InUniqueAssignmentPeriod;
   }
   public void setInUniqueAssignmentPeriod(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InUniqueAssignmentPeriod has more than 2 digits.",
               new PropertyChangeEvent (this, "InUniqueAssignmentPeriod", null, null));
      }
      importView.InUniqueAssignmentPeriod = ShortAttr.valueOf(s);
   }
   public void setAsStringInUniqueAssignmentPeriod(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InUniqueAssignmentPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InUniqueAssignmentPeriod", null, null));
      }
      try {
          setInUniqueAssignmentPeriod(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InUniqueAssignmentPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InUniqueAssignmentPeriod", null, null));
      }
   }
 
   public int getInUniqueAssignmentUniqueNr() {
      return importView.InUniqueAssignmentUniqueNr;
   }
   public void setInUniqueAssignmentUniqueNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000000.0) {
         throw new PropertyVetoException("InUniqueAssignmentUniqueNr has more than 7 digits.",
               new PropertyChangeEvent (this, "InUniqueAssignmentUniqueNr", null, null));
      }
      importView.InUniqueAssignmentUniqueNr = IntAttr.valueOf(s);
   }
   public void setAsStringInUniqueAssignmentUniqueNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InUniqueAssignmentUniqueNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InUniqueAssignmentUniqueNr", null, null));
      }
      try {
          setInUniqueAssignmentUniqueNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InUniqueAssignmentUniqueNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InUniqueAssignmentUniqueNr", null, null));
      }
   }
 
   public String getInUniqueAssignmentMkStudyUnitCode() {
      return FixedStringAttr.valueOf(importView.InUniqueAssignmentMkStudyUnitCode, 7);
   }
   public void setInUniqueAssignmentMkStudyUnitCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InUniqueAssignmentMkStudyUnitCode must be <= 7 characters.",
               new PropertyChangeEvent (this, "InUniqueAssignmentMkStudyUnitCode", null, null));
      }
      importView.InUniqueAssignmentMkStudyUnitCode = FixedStringAttr.valueOf(s, (short)7);
   }
 
   public short getInUniqueAssignmentAssignmentNr() {
      return importView.InUniqueAssignmentAssignmentNr;
   }
   public void setInUniqueAssignmentAssignmentNr(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InUniqueAssignmentAssignmentNr has more than 2 digits.",
               new PropertyChangeEvent (this, "InUniqueAssignmentAssignmentNr", null, null));
      }
      importView.InUniqueAssignmentAssignmentNr = ShortAttr.valueOf(s);
   }
   public void setAsStringInUniqueAssignmentAssignmentNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InUniqueAssignmentAssignmentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InUniqueAssignmentAssignmentNr", null, null));
      }
      try {
          setInUniqueAssignmentAssignmentNr(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InUniqueAssignmentAssignmentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InUniqueAssignmentAssignmentNr", null, null));
      }
   }
 
   public short getInUniqueAssignmentNrOfQuestions() {
      return importView.InUniqueAssignmentNrOfQuestions;
   }
   public void setInUniqueAssignmentNrOfQuestions(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InUniqueAssignmentNrOfQuestions has more than 3 digits.",
               new PropertyChangeEvent (this, "InUniqueAssignmentNrOfQuestions", null, null));
      }
      importView.InUniqueAssignmentNrOfQuestions = ShortAttr.valueOf(s);
   }
   public void setAsStringInUniqueAssignmentNrOfQuestions(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InUniqueAssignmentNrOfQuestions is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InUniqueAssignmentNrOfQuestions", null, null));
      }
      try {
          setInUniqueAssignmentNrOfQuestions(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InUniqueAssignmentNrOfQuestions is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InUniqueAssignmentNrOfQuestions", null, null));
      }
   }
 
   public String getInUniqueAssignmentType() {
      return FixedStringAttr.valueOf(importView.InUniqueAssignmentType, 1);
   }
   public void setInUniqueAssignmentType(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Saacq01sAssessmentCriteriaMntUniqueAssignmentTypePropertyEditor pe = new Saacq01sAssessmentCriteriaMntUniqueAssignmentTypePropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InUniqueAssignmentType value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InUniqueAssignmentType", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InUniqueAssignmentType must be <= 1 characters.",
               new PropertyChangeEvent (this, "InUniqueAssignmentType", null, null));
      }
      importView.InUniqueAssignmentType = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInUniqueAssignmentNegativeMarkFactor() {
      return importView.InUniqueAssignmentNegativeMarkFactor;
   }
   public void setInUniqueAssignmentNegativeMarkFactor(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InUniqueAssignmentNegativeMarkFactor has more than 1 digits.",
               new PropertyChangeEvent (this, "InUniqueAssignmentNegativeMarkFactor", null, null));
      }
      importView.InUniqueAssignmentNegativeMarkFactor = ShortAttr.valueOf(s);
   }
   public void setAsStringInUniqueAssignmentNegativeMarkFactor(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InUniqueAssignmentNegativeMarkFactor is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InUniqueAssignmentNegativeMarkFactor", null, null));
      }
      try {
          setInUniqueAssignmentNegativeMarkFactor(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InUniqueAssignmentNegativeMarkFactor is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InUniqueAssignmentNegativeMarkFactor", null, null));
      }
   }
 
   public short getInUniqueAssignmentMaxCredits() {
      return importView.InUniqueAssignmentMaxCredits;
   }
   public void setInUniqueAssignmentMaxCredits(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InUniqueAssignmentMaxCredits has more than 3 digits.",
               new PropertyChangeEvent (this, "InUniqueAssignmentMaxCredits", null, null));
      }
      importView.InUniqueAssignmentMaxCredits = ShortAttr.valueOf(s);
   }
   public void setAsStringInUniqueAssignmentMaxCredits(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InUniqueAssignmentMaxCredits is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InUniqueAssignmentMaxCredits", null, null));
      }
      try {
          setInUniqueAssignmentMaxCredits(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InUniqueAssignmentMaxCredits is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InUniqueAssignmentMaxCredits", null, null));
      }
   }
 
   public short getInUniqueAssignmentCreditSystem() {
      return importView.InUniqueAssignmentCreditSystem;
   }
   public void setInUniqueAssignmentCreditSystem(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InUniqueAssignmentCreditSystem has more than 1 digits.",
               new PropertyChangeEvent (this, "InUniqueAssignmentCreditSystem", null, null));
      }
      importView.InUniqueAssignmentCreditSystem = ShortAttr.valueOf(s);
   }
   public void setAsStringInUniqueAssignmentCreditSystem(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InUniqueAssignmentCreditSystem is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InUniqueAssignmentCreditSystem", null, null));
      }
      try {
          setInUniqueAssignmentCreditSystem(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InUniqueAssignmentCreditSystem is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InUniqueAssignmentCreditSystem", null, null));
      }
   }
 
   public short getInUniqueAssignmentPassMarkPercentage() {
      return importView.InUniqueAssignmentPassMarkPercentage;
   }
   public void setInUniqueAssignmentPassMarkPercentage(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InUniqueAssignmentPassMarkPercentage has more than 2 digits.",
               new PropertyChangeEvent (this, "InUniqueAssignmentPassMarkPercentage", null, null));
      }
      importView.InUniqueAssignmentPassMarkPercentage = ShortAttr.valueOf(s);
   }
   public void setAsStringInUniqueAssignmentPassMarkPercentage(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InUniqueAssignmentPassMarkPercentage is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InUniqueAssignmentPassMarkPercentage", null, null));
      }
      try {
          setInUniqueAssignmentPassMarkPercentage(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InUniqueAssignmentPassMarkPercentage is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InUniqueAssignmentPassMarkPercentage", null, null));
      }
   }
 
   public Calendar getInUniqueAssignmentClosingDate() {
      return DateAttr.toCalendar(importView.InUniqueAssignmentClosingDate);
   }
   public int getAsIntInUniqueAssignmentClosingDate() {
      return DateAttr.toInt(importView.InUniqueAssignmentClosingDate);
   }
   public void setInUniqueAssignmentClosingDate(Calendar s)
      throws PropertyVetoException {
      importView.InUniqueAssignmentClosingDate = DateAttr.valueOf(s);
   }
   public void setAsStringInUniqueAssignmentClosingDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInUniqueAssignmentClosingDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInUniqueAssignmentClosingDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InUniqueAssignmentClosingDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InUniqueAssignmentClosingDate", null, null));
         }
      }
   }
   public void setAsIntInUniqueAssignmentClosingDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInUniqueAssignmentClosingDate(temp);
   }
 
   public short getInUniqueAssignmentReportIndicator() {
      return importView.InUniqueAssignmentReportIndicator;
   }
   public void setInUniqueAssignmentReportIndicator(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InUniqueAssignmentReportIndicator has more than 1 digits.",
               new PropertyChangeEvent (this, "InUniqueAssignmentReportIndicator", null, null));
      }
      importView.InUniqueAssignmentReportIndicator = ShortAttr.valueOf(s);
   }
   public void setAsStringInUniqueAssignmentReportIndicator(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InUniqueAssignmentReportIndicator is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InUniqueAssignmentReportIndicator", null, null));
      }
      try {
          setInUniqueAssignmentReportIndicator(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InUniqueAssignmentReportIndicator is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InUniqueAssignmentReportIndicator", null, null));
      }
   }
 
   public int getInUniqueAssignmentNextUnqNr() {
      return importView.InUniqueAssignmentNextUnqNr;
   }
   public void setInUniqueAssignmentNextUnqNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000000.0) {
         throw new PropertyVetoException("InUniqueAssignmentNextUnqNr has more than 7 digits.",
               new PropertyChangeEvent (this, "InUniqueAssignmentNextUnqNr", null, null));
      }
      importView.InUniqueAssignmentNextUnqNr = IntAttr.valueOf(s);
   }
   public void setAsStringInUniqueAssignmentNextUnqNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InUniqueAssignmentNextUnqNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InUniqueAssignmentNextUnqNr", null, null));
      }
      try {
          setInUniqueAssignmentNextUnqNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InUniqueAssignmentNextUnqNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InUniqueAssignmentNextUnqNr", null, null));
      }
   }
 
   public short getInUniqueAssignmentSupplStartNr() {
      return importView.InUniqueAssignmentSupplStartNr;
   }
   public void setInUniqueAssignmentSupplStartNr(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InUniqueAssignmentSupplStartNr has more than 3 digits.",
               new PropertyChangeEvent (this, "InUniqueAssignmentSupplStartNr", null, null));
      }
      importView.InUniqueAssignmentSupplStartNr = ShortAttr.valueOf(s);
   }
   public void setAsStringInUniqueAssignmentSupplStartNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InUniqueAssignmentSupplStartNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InUniqueAssignmentSupplStartNr", null, null));
      }
      try {
          setInUniqueAssignmentSupplStartNr(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InUniqueAssignmentSupplStartNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InUniqueAssignmentSupplStartNr", null, null));
      }
   }
 
   public String getInUniqueAssignmentBlockSol() {
      return FixedStringAttr.valueOf(importView.InUniqueAssignmentBlockSol, 1);
   }
   public void setInUniqueAssignmentBlockSol(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InUniqueAssignmentBlockSol must be <= 1 characters.",
               new PropertyChangeEvent (this, "InUniqueAssignmentBlockSol", null, null));
      }
      importView.InUniqueAssignmentBlockSol = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInUniqueAssignmentBlockEsol() {
      return FixedStringAttr.valueOf(importView.InUniqueAssignmentBlockEsol, 1);
   }
   public void setInUniqueAssignmentBlockEsol(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InUniqueAssignmentBlockEsol must be <= 1 characters.",
               new PropertyChangeEvent (this, "InUniqueAssignmentBlockEsol", null, null));
      }
      importView.InUniqueAssignmentBlockEsol = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public Calendar getInUniqueAssignmentMarkingDate() {
      return DateAttr.toCalendar(importView.InUniqueAssignmentMarkingDate);
   }
   public int getAsIntInUniqueAssignmentMarkingDate() {
      return DateAttr.toInt(importView.InUniqueAssignmentMarkingDate);
   }
   public void setInUniqueAssignmentMarkingDate(Calendar s)
      throws PropertyVetoException {
      importView.InUniqueAssignmentMarkingDate = DateAttr.valueOf(s);
   }
   public void setAsStringInUniqueAssignmentMarkingDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInUniqueAssignmentMarkingDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInUniqueAssignmentMarkingDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InUniqueAssignmentMarkingDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InUniqueAssignmentMarkingDate", null, null));
         }
      }
   }
   public void setAsIntInUniqueAssignmentMarkingDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInUniqueAssignmentMarkingDate(temp);
   }
 
   public short getInUniqueAssignmentNoCoverSheets() {
      return importView.InUniqueAssignmentNoCoverSheets;
   }
   public void setInUniqueAssignmentNoCoverSheets(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InUniqueAssignmentNoCoverSheets has more than 1 digits.",
               new PropertyChangeEvent (this, "InUniqueAssignmentNoCoverSheets", null, null));
      }
      importView.InUniqueAssignmentNoCoverSheets = ShortAttr.valueOf(s);
   }
   public void setAsStringInUniqueAssignmentNoCoverSheets(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InUniqueAssignmentNoCoverSheets is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InUniqueAssignmentNoCoverSheets", null, null));
      }
      try {
          setInUniqueAssignmentNoCoverSheets(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InUniqueAssignmentNoCoverSheets is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InUniqueAssignmentNoCoverSheets", null, null));
      }
   }
 
   public short getInUniqueAssignmentMaxPerDocket() {
      return importView.InUniqueAssignmentMaxPerDocket;
   }
   public void setInUniqueAssignmentMaxPerDocket(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InUniqueAssignmentMaxPerDocket has more than 2 digits.",
               new PropertyChangeEvent (this, "InUniqueAssignmentMaxPerDocket", null, null));
      }
      importView.InUniqueAssignmentMaxPerDocket = ShortAttr.valueOf(s);
   }
   public void setAsStringInUniqueAssignmentMaxPerDocket(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InUniqueAssignmentMaxPerDocket is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InUniqueAssignmentMaxPerDocket", null, null));
      }
      try {
          setInUniqueAssignmentMaxPerDocket(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InUniqueAssignmentMaxPerDocket is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InUniqueAssignmentMaxPerDocket", null, null));
      }
   }
 
   public String getInUniqueAssignmentCompulsory() {
      return FixedStringAttr.valueOf(importView.InUniqueAssignmentCompulsory, 1);
   }
   public void setInUniqueAssignmentCompulsory(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InUniqueAssignmentCompulsory must be <= 1 characters.",
               new PropertyChangeEvent (this, "InUniqueAssignmentCompulsory", null, null));
      }
      importView.InUniqueAssignmentCompulsory = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInUniqueAssignmentReleaseResults() {
      return StringAttr.valueOf(importView.InUniqueAssignmentReleaseResults);
   }
   public void setInUniqueAssignmentReleaseResults(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InUniqueAssignmentReleaseResults must be <= 1 characters.",
               new PropertyChangeEvent (this, "InUniqueAssignmentReleaseResults", null, null));
      }
      importView.InUniqueAssignmentReleaseResults = StringAttr.valueOf(s, (short)1);
   }
 
   public String getInUniqueAssignmentCancelForSuppl() {
      return StringAttr.valueOf(importView.InUniqueAssignmentCancelForSuppl);
   }
   public void setInUniqueAssignmentCancelForSuppl(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InUniqueAssignmentCancelForSuppl must be <= 1 characters.",
               new PropertyChangeEvent (this, "InUniqueAssignmentCancelForSuppl", null, null));
      }
      importView.InUniqueAssignmentCancelForSuppl = StringAttr.valueOf(s, (short)1);
   }
 
   public int getInUniqueAssignmentTsaUniqueNr() {
      return importView.InUniqueAssignmentTsaUniqueNr;
   }
   public void setInUniqueAssignmentTsaUniqueNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000000.0) {
         throw new PropertyVetoException("InUniqueAssignmentTsaUniqueNr has more than 7 digits.",
               new PropertyChangeEvent (this, "InUniqueAssignmentTsaUniqueNr", null, null));
      }
      importView.InUniqueAssignmentTsaUniqueNr = IntAttr.valueOf(s);
   }
   public void setAsStringInUniqueAssignmentTsaUniqueNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InUniqueAssignmentTsaUniqueNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InUniqueAssignmentTsaUniqueNr", null, null));
      }
      try {
          setInUniqueAssignmentTsaUniqueNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InUniqueAssignmentTsaUniqueNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InUniqueAssignmentTsaUniqueNr", null, null));
      }
   }
 
   public String getInUniqueAssignmentOnlineTypeGc176() {
      return FixedStringAttr.valueOf(importView.InUniqueAssignmentOnlineTypeGc176, 10);
   }
   public void setInUniqueAssignmentOnlineTypeGc176(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InUniqueAssignmentOnlineTypeGc176 must be <= 10 characters.",
               new PropertyChangeEvent (this, "InUniqueAssignmentOnlineTypeGc176", null, null));
      }
      importView.InUniqueAssignmentOnlineTypeGc176 = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInUniqueAssignmentOnscreenMarkFlag() {
      return FixedStringAttr.valueOf(importView.InUniqueAssignmentOnscreenMarkFlag, 1);
   }
   public void setInUniqueAssignmentOnscreenMarkFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InUniqueAssignmentOnscreenMarkFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InUniqueAssignmentOnscreenMarkFlag", null, null));
      }
      importView.InUniqueAssignmentOnscreenMarkFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public Calendar getInUniqueAssignmentFileReleaseDate() {
      return DateAttr.toCalendar(importView.InUniqueAssignmentFileReleaseDate);
   }
   public int getAsIntInUniqueAssignmentFileReleaseDate() {
      return DateAttr.toInt(importView.InUniqueAssignmentFileReleaseDate);
   }
   public void setInUniqueAssignmentFileReleaseDate(Calendar s)
      throws PropertyVetoException {
      importView.InUniqueAssignmentFileReleaseDate = DateAttr.valueOf(s);
   }
   public void setAsStringInUniqueAssignmentFileReleaseDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInUniqueAssignmentFileReleaseDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInUniqueAssignmentFileReleaseDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InUniqueAssignmentFileReleaseDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InUniqueAssignmentFileReleaseDate", null, null));
         }
      }
   }
   public void setAsIntInUniqueAssignmentFileReleaseDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInUniqueAssignmentFileReleaseDate(temp);
   }
 
   public String getInUniqueAssignmentStuSpecifyLang() {
      return FixedStringAttr.valueOf(importView.InUniqueAssignmentStuSpecifyLang, 1);
   }
   public void setInUniqueAssignmentStuSpecifyLang(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InUniqueAssignmentStuSpecifyLang must be <= 1 characters.",
               new PropertyChangeEvent (this, "InUniqueAssignmentStuSpecifyLang", null, null));
      }
      importView.InUniqueAssignmentStuSpecifyLang = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public Calendar getInUniqueAssignmentPfOpenDate() {
      return DateAttr.toCalendar(importView.InUniqueAssignmentPfOpenDate);
   }
   public int getAsIntInUniqueAssignmentPfOpenDate() {
      return DateAttr.toInt(importView.InUniqueAssignmentPfOpenDate);
   }
   public void setInUniqueAssignmentPfOpenDate(Calendar s)
      throws PropertyVetoException {
      importView.InUniqueAssignmentPfOpenDate = DateAttr.valueOf(s);
   }
   public void setAsStringInUniqueAssignmentPfOpenDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInUniqueAssignmentPfOpenDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInUniqueAssignmentPfOpenDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InUniqueAssignmentPfOpenDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InUniqueAssignmentPfOpenDate", null, null));
         }
      }
   }
   public void setAsIntInUniqueAssignmentPfOpenDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInUniqueAssignmentPfOpenDate(temp);
   }
 
   public short getInUniqueAssignmentFileSizeLimit() {
      return importView.InUniqueAssignmentFileSizeLimit;
   }
   public void setInUniqueAssignmentFileSizeLimit(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InUniqueAssignmentFileSizeLimit has more than 3 digits.",
               new PropertyChangeEvent (this, "InUniqueAssignmentFileSizeLimit", null, null));
      }
      importView.InUniqueAssignmentFileSizeLimit = ShortAttr.valueOf(s);
   }
   public void setAsStringInUniqueAssignmentFileSizeLimit(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InUniqueAssignmentFileSizeLimit is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InUniqueAssignmentFileSizeLimit", null, null));
      }
      try {
          setInUniqueAssignmentFileSizeLimit(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InUniqueAssignmentFileSizeLimit is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InUniqueAssignmentFileSizeLimit", null, null));
      }
   }
 
   public Calendar getInUniqueAssignmentFinalSubmitDate() {
      return TimestampAttr.toCalendar(importView.InUniqueAssignmentFinalSubmitDate);
   }
   public String getAsStringInUniqueAssignmentFinalSubmitDate() {
      return TimestampAttr.toString(importView.InUniqueAssignmentFinalSubmitDate);
   }
   public void setInUniqueAssignmentFinalSubmitDate(Calendar s)
      throws PropertyVetoException {
      importView.InUniqueAssignmentFinalSubmitDate = TimestampAttr.valueOf(s);
   }
   public void setAsStringInUniqueAssignmentFinalSubmitDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInUniqueAssignmentFinalSubmitDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeTimestampFormatter.parse(s.length() > 17 ? s.substring(0, 17) : s));
            importView.InUniqueAssignmentFinalSubmitDate = TimestampAttr.valueOf(s);
         } catch (ParseException e) {
            throw new PropertyVetoException("InUniqueAssignmentFinalSubmitDate has an invalid format (yyyyMMddHHmmssSSSSSS).",
                  new PropertyChangeEvent (this, "InUniqueAssignmentFinalSubmitDate", null, null));
         }
      }
   }
 
   public String getInUniqueAssignmentAssessGroupGc230() {
      return StringAttr.valueOf(importView.InUniqueAssignmentAssessGroupGc230);
   }
   public void setInUniqueAssignmentAssessGroupGc230(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InUniqueAssignmentAssessGroupGc230 must be <= 10 characters.",
               new PropertyChangeEvent (this, "InUniqueAssignmentAssessGroupGc230", null, null));
      }
      importView.InUniqueAssignmentAssessGroupGc230 = StringAttr.valueOf(s, (short)10);
   }
 
   public Calendar getInUniqueAssignmentPfOpenTime() {
      return TimeAttr.toCalendar(importView.InUniqueAssignmentPfOpenTime);
   }
   public int getAsIntInUniqueAssignmentPfOpenTime() {
      return TimeAttr.toInt(importView.InUniqueAssignmentPfOpenTime);
   }
   public void setInUniqueAssignmentPfOpenTime(Calendar s)
      throws PropertyVetoException {
      importView.InUniqueAssignmentPfOpenTime = TimeAttr.valueOf(s);
   }
   public void setAsStringInUniqueAssignmentPfOpenTime(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInUniqueAssignmentPfOpenTime((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeTimeFormatter.parse(s.length() > 6 ? s.substring(0, 6) : s));
            setInUniqueAssignmentPfOpenTime(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InUniqueAssignmentPfOpenTime has an invalid format (HHmmss).",
                  new PropertyChangeEvent (this, "InUniqueAssignmentPfOpenTime", null, null));
         }
      }
   }
   public void setAsIntInUniqueAssignmentPfOpenTime(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 6)
      {
         temp = "000000".substring(temp.length()) + temp;
      }
      setAsStringInUniqueAssignmentPfOpenTime(temp);
   }
 
   public String getInYearMarkCalculationStudentAssignmentNr() {
      return FixedStringAttr.valueOf(importView.InYearMarkCalculationStudentAssignmentNr, 2);
   }
   public void setInYearMarkCalculationStudentAssignmentNr(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InYearMarkCalculationStudentAssignmentNr must be <= 2 characters.",
               new PropertyChangeEvent (this, "InYearMarkCalculationStudentAssignmentNr", null, null));
      }
      importView.InYearMarkCalculationStudentAssignmentNr = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public String getInYearMarkCalculationType() {
      return FixedStringAttr.valueOf(importView.InYearMarkCalculationType, 1);
   }
   public void setInYearMarkCalculationType(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Saacq01sAssessmentCriteriaMntYearMarkCalculationTypePropertyEditor pe = new Saacq01sAssessmentCriteriaMntYearMarkCalculationTypePropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InYearMarkCalculationType value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InYearMarkCalculationType", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InYearMarkCalculationType must be <= 1 characters.",
               new PropertyChangeEvent (this, "InYearMarkCalculationType", null, null));
      }
      importView.InYearMarkCalculationType = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public double getInYearMarkCalculationNormalWeight() {
      return importView.InYearMarkCalculationNormalWeight;
   }
   public void setInYearMarkCalculationNormalWeight(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InYearMarkCalculationNormalWeight has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InYearMarkCalculationNormalWeight", null, null));
      }
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InYearMarkCalculationNormalWeight has more than 4 integral digits.",
               new PropertyChangeEvent (this, "InYearMarkCalculationNormalWeight", null, null));
      }
      importView.InYearMarkCalculationNormalWeight = DoubleAttr.valueOf(s);
   }
   public void setAsStringInYearMarkCalculationNormalWeight(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InYearMarkCalculationNormalWeight is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InYearMarkCalculationNormalWeight", null, null));
      }
      try {
          setInYearMarkCalculationNormalWeight(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InYearMarkCalculationNormalWeight is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InYearMarkCalculationNormalWeight", null, null));
      }
   }
 
   public double getInYearMarkCalculationRepeatWeight() {
      return importView.InYearMarkCalculationRepeatWeight;
   }
   public void setInYearMarkCalculationRepeatWeight(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InYearMarkCalculationRepeatWeight has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InYearMarkCalculationRepeatWeight", null, null));
      }
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InYearMarkCalculationRepeatWeight has more than 4 integral digits.",
               new PropertyChangeEvent (this, "InYearMarkCalculationRepeatWeight", null, null));
      }
      importView.InYearMarkCalculationRepeatWeight = DoubleAttr.valueOf(s);
   }
   public void setAsStringInYearMarkCalculationRepeatWeight(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InYearMarkCalculationRepeatWeight is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InYearMarkCalculationRepeatWeight", null, null));
      }
      try {
          setInYearMarkCalculationRepeatWeight(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InYearMarkCalculationRepeatWeight is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InYearMarkCalculationRepeatWeight", null, null));
      }
   }
 
   public String getInYearMarkCalculationOptionalityGc3() {
      return StringAttr.valueOf(importView.InYearMarkCalculationOptionalityGc3);
   }
   public void setInYearMarkCalculationOptionalityGc3(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InYearMarkCalculationOptionalityGc3 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InYearMarkCalculationOptionalityGc3", null, null));
      }
      importView.InYearMarkCalculationOptionalityGc3 = StringAttr.valueOf(s, (short)1);
   }
 
   public int getInYearMarkCalculationSupplWeight() {
      return importView.InYearMarkCalculationSupplWeight;
   }
   public void setInYearMarkCalculationSupplWeight(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000000.0) {
         throw new PropertyVetoException("InYearMarkCalculationSupplWeight has more than 6 digits.",
               new PropertyChangeEvent (this, "InYearMarkCalculationSupplWeight", null, null));
      }
      importView.InYearMarkCalculationSupplWeight = IntAttr.valueOf(s);
   }
   public void setAsStringInYearMarkCalculationSupplWeight(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InYearMarkCalculationSupplWeight is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InYearMarkCalculationSupplWeight", null, null));
      }
      try {
          setInYearMarkCalculationSupplWeight(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InYearMarkCalculationSupplWeight is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InYearMarkCalculationSupplWeight", null, null));
      }
   }
 
   public String getInYearMarkCalculationSubTypeGc206() {
      return StringAttr.valueOf(importView.InYearMarkCalculationSubTypeGc206);
   }
   public void setInYearMarkCalculationSubTypeGc206(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InYearMarkCalculationSubTypeGc206 must be <= 10 characters.",
               new PropertyChangeEvent (this, "InYearMarkCalculationSubTypeGc206", null, null));
      }
      importView.InYearMarkCalculationSubTypeGc206 = StringAttr.valueOf(s, (short)10);
   }
 
   public short getInFinalMarkCalculationExamYear() {
      return importView.InFinalMarkCalculationExamYear;
   }
   public void setInFinalMarkCalculationExamYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InFinalMarkCalculationExamYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InFinalMarkCalculationExamYear", null, null));
      }
      importView.InFinalMarkCalculationExamYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInFinalMarkCalculationExamYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InFinalMarkCalculationExamYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InFinalMarkCalculationExamYear", null, null));
      }
      try {
          setInFinalMarkCalculationExamYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InFinalMarkCalculationExamYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InFinalMarkCalculationExamYear", null, null));
      }
   }
 
   public short getInFinalMarkCalculationMkExamPeriod() {
      return importView.InFinalMarkCalculationMkExamPeriod;
   }
   public void setInFinalMarkCalculationMkExamPeriod(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InFinalMarkCalculationMkExamPeriod has more than 2 digits.",
               new PropertyChangeEvent (this, "InFinalMarkCalculationMkExamPeriod", null, null));
      }
      importView.InFinalMarkCalculationMkExamPeriod = ShortAttr.valueOf(s);
   }
   public void setAsStringInFinalMarkCalculationMkExamPeriod(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InFinalMarkCalculationMkExamPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InFinalMarkCalculationMkExamPeriod", null, null));
      }
      try {
          setInFinalMarkCalculationMkExamPeriod(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InFinalMarkCalculationMkExamPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InFinalMarkCalculationMkExamPeriod", null, null));
      }
   }
 
   public String getInFinalMarkCalculationMkStudyUnitCode() {
      return FixedStringAttr.valueOf(importView.InFinalMarkCalculationMkStudyUnitCode, 7);
   }
   public void setInFinalMarkCalculationMkStudyUnitCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InFinalMarkCalculationMkStudyUnitCode must be <= 7 characters.",
               new PropertyChangeEvent (this, "InFinalMarkCalculationMkStudyUnitCode", null, null));
      }
      importView.InFinalMarkCalculationMkStudyUnitCode = FixedStringAttr.valueOf(s, (short)7);
   }
 
   public double getInFinalMarkCalculationYearMarkWeight() {
      return importView.InFinalMarkCalculationYearMarkWeight;
   }
   public void setInFinalMarkCalculationYearMarkWeight(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InFinalMarkCalculationYearMarkWeight has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InFinalMarkCalculationYearMarkWeight", null, null));
      }
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InFinalMarkCalculationYearMarkWeight has more than 4 integral digits.",
               new PropertyChangeEvent (this, "InFinalMarkCalculationYearMarkWeight", null, null));
      }
      importView.InFinalMarkCalculationYearMarkWeight = DoubleAttr.valueOf(s);
   }
   public void setAsStringInFinalMarkCalculationYearMarkWeight(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InFinalMarkCalculationYearMarkWeight is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InFinalMarkCalculationYearMarkWeight", null, null));
      }
      try {
          setInFinalMarkCalculationYearMarkWeight(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InFinalMarkCalculationYearMarkWeight is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InFinalMarkCalculationYearMarkWeight", null, null));
      }
   }
 
   public double getInFinalMarkCalculationExaminationWeight() {
      return importView.InFinalMarkCalculationExaminationWeight;
   }
   public void setInFinalMarkCalculationExaminationWeight(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InFinalMarkCalculationExaminationWeight has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InFinalMarkCalculationExaminationWeight", null, null));
      }
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InFinalMarkCalculationExaminationWeight has more than 4 integral digits.",
               new PropertyChangeEvent (this, "InFinalMarkCalculationExaminationWeight", null, null));
      }
      importView.InFinalMarkCalculationExaminationWeight = DoubleAttr.valueOf(s);
   }
   public void setAsStringInFinalMarkCalculationExaminationWeight(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InFinalMarkCalculationExaminationWeight is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InFinalMarkCalculationExaminationWeight", null, null));
      }
      try {
          setInFinalMarkCalculationExaminationWeight(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InFinalMarkCalculationExaminationWeight is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InFinalMarkCalculationExaminationWeight", null, null));
      }
   }
 
   public double getInFinalMarkCalculationPortfolioWeight() {
      return importView.InFinalMarkCalculationPortfolioWeight;
   }
   public void setInFinalMarkCalculationPortfolioWeight(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InFinalMarkCalculationPortfolioWeight has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InFinalMarkCalculationPortfolioWeight", null, null));
      }
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InFinalMarkCalculationPortfolioWeight has more than 4 integral digits.",
               new PropertyChangeEvent (this, "InFinalMarkCalculationPortfolioWeight", null, null));
      }
      importView.InFinalMarkCalculationPortfolioWeight = DoubleAttr.valueOf(s);
   }
   public void setAsStringInFinalMarkCalculationPortfolioWeight(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InFinalMarkCalculationPortfolioWeight is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InFinalMarkCalculationPortfolioWeight", null, null));
      }
      try {
          setInFinalMarkCalculationPortfolioWeight(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InFinalMarkCalculationPortfolioWeight is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InFinalMarkCalculationPortfolioWeight", null, null));
      }
   }
 
   public String getInFinalMarkCalculationYmSystemGc2() {
      return StringAttr.valueOf(importView.InFinalMarkCalculationYmSystemGc2);
   }
   public void setInFinalMarkCalculationYmSystemGc2(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InFinalMarkCalculationYmSystemGc2 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InFinalMarkCalculationYmSystemGc2", null, null));
      }
      importView.InFinalMarkCalculationYmSystemGc2 = StringAttr.valueOf(s, (short)1);
   }
 
   public short getInFinalMarkCalculationYmSubminimum() {
      return importView.InFinalMarkCalculationYmSubminimum;
   }
   public void setInFinalMarkCalculationYmSubminimum(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InFinalMarkCalculationYmSubminimum has more than 2 digits.",
               new PropertyChangeEvent (this, "InFinalMarkCalculationYmSubminimum", null, null));
      }
      importView.InFinalMarkCalculationYmSubminimum = ShortAttr.valueOf(s);
   }
   public void setAsStringInFinalMarkCalculationYmSubminimum(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InFinalMarkCalculationYmSubminimum is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InFinalMarkCalculationYmSubminimum", null, null));
      }
      try {
          setInFinalMarkCalculationYmSubminimum(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InFinalMarkCalculationYmSubminimum is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InFinalMarkCalculationYmSubminimum", null, null));
      }
   }
 
   public short getInFinalMarkCalculationNoElectiveAssignments() {
      return importView.InFinalMarkCalculationNoElectiveAssignments;
   }
   public void setInFinalMarkCalculationNoElectiveAssignments(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InFinalMarkCalculationNoElectiveAssignments has more than 2 digits.",
               new PropertyChangeEvent (this, "InFinalMarkCalculationNoElectiveAssignments", null, null));
      }
      importView.InFinalMarkCalculationNoElectiveAssignments = ShortAttr.valueOf(s);
   }
   public void setAsStringInFinalMarkCalculationNoElectiveAssignments(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InFinalMarkCalculationNoElectiveAssignments is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InFinalMarkCalculationNoElectiveAssignments", null, null));
      }
      try {
          setInFinalMarkCalculationNoElectiveAssignments(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InFinalMarkCalculationNoElectiveAssignments is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InFinalMarkCalculationNoElectiveAssignments", null, null));
      }
   }
 
   public short getInFinalMarkCalculationPfSubminimum() {
      return importView.InFinalMarkCalculationPfSubminimum;
   }
   public void setInFinalMarkCalculationPfSubminimum(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InFinalMarkCalculationPfSubminimum has more than 2 digits.",
               new PropertyChangeEvent (this, "InFinalMarkCalculationPfSubminimum", null, null));
      }
      importView.InFinalMarkCalculationPfSubminimum = ShortAttr.valueOf(s);
   }
   public void setAsStringInFinalMarkCalculationPfSubminimum(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InFinalMarkCalculationPfSubminimum is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InFinalMarkCalculationPfSubminimum", null, null));
      }
      try {
          setInFinalMarkCalculationPfSubminimum(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InFinalMarkCalculationPfSubminimum is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InFinalMarkCalculationPfSubminimum", null, null));
      }
   }
 
   public String getInFinalMarkCalculationPfCompulsory() {
      return FixedStringAttr.valueOf(importView.InFinalMarkCalculationPfCompulsory, 1);
   }
   public void setInFinalMarkCalculationPfCompulsory(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InFinalMarkCalculationPfCompulsory must be <= 1 characters.",
               new PropertyChangeEvent (this, "InFinalMarkCalculationPfCompulsory", null, null));
      }
      importView.InFinalMarkCalculationPfCompulsory = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInFinalMarkCalculationYmCompulsory() {
      return FixedStringAttr.valueOf(importView.InFinalMarkCalculationYmCompulsory, 1);
   }
   public void setInFinalMarkCalculationYmCompulsory(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InFinalMarkCalculationYmCompulsory must be <= 1 characters.",
               new PropertyChangeEvent (this, "InFinalMarkCalculationYmCompulsory", null, null));
      }
      importView.InFinalMarkCalculationYmCompulsory = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInFinalMarkCalculationXmCompulsory() {
      return FixedStringAttr.valueOf(importView.InFinalMarkCalculationXmCompulsory, 1);
   }
   public void setInFinalMarkCalculationXmCompulsory(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InFinalMarkCalculationXmCompulsory must be <= 1 characters.",
               new PropertyChangeEvent (this, "InFinalMarkCalculationXmCompulsory", null, null));
      }
      importView.InFinalMarkCalculationXmCompulsory = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInFinalMarkCalculationNoOfElectivePf() {
      return importView.InFinalMarkCalculationNoOfElectivePf;
   }
   public void setInFinalMarkCalculationNoOfElectivePf(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InFinalMarkCalculationNoOfElectivePf has more than 2 digits.",
               new PropertyChangeEvent (this, "InFinalMarkCalculationNoOfElectivePf", null, null));
      }
      importView.InFinalMarkCalculationNoOfElectivePf = ShortAttr.valueOf(s);
   }
   public void setAsStringInFinalMarkCalculationNoOfElectivePf(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InFinalMarkCalculationNoOfElectivePf is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InFinalMarkCalculationNoOfElectivePf", null, null));
      }
      try {
          setInFinalMarkCalculationNoOfElectivePf(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InFinalMarkCalculationNoOfElectivePf is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InFinalMarkCalculationNoOfElectivePf", null, null));
      }
   }
 
   public short getInFinalMarkCalculationXamSubminimum() {
      return importView.InFinalMarkCalculationXamSubminimum;
   }
   public void setInFinalMarkCalculationXamSubminimum(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InFinalMarkCalculationXamSubminimum has more than 2 digits.",
               new PropertyChangeEvent (this, "InFinalMarkCalculationXamSubminimum", null, null));
      }
      importView.InFinalMarkCalculationXamSubminimum = ShortAttr.valueOf(s);
   }
   public void setAsStringInFinalMarkCalculationXamSubminimum(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InFinalMarkCalculationXamSubminimum is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InFinalMarkCalculationXamSubminimum", null, null));
      }
      try {
          setInFinalMarkCalculationXamSubminimum(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InFinalMarkCalculationXamSubminimum is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InFinalMarkCalculationXamSubminimum", null, null));
      }
   }
 
   public String getInFinalMarkCalculationCalcYmSuppl() {
      return StringAttr.valueOf(importView.InFinalMarkCalculationCalcYmSuppl);
   }
   public void setInFinalMarkCalculationCalcYmSuppl(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InFinalMarkCalculationCalcYmSuppl must be <= 1 characters.",
               new PropertyChangeEvent (this, "InFinalMarkCalculationCalcYmSuppl", null, null));
      }
      importView.InFinalMarkCalculationCalcYmSuppl = StringAttr.valueOf(s, (short)1);
   }
 
   public String getInFinalMarkCalculationCalcYmSubmin() {
      return StringAttr.valueOf(importView.InFinalMarkCalculationCalcYmSubmin);
   }
   public void setInFinalMarkCalculationCalcYmSubmin(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InFinalMarkCalculationCalcYmSubmin must be <= 1 characters.",
               new PropertyChangeEvent (this, "InFinalMarkCalculationCalcYmSubmin", null, null));
      }
      importView.InFinalMarkCalculationCalcYmSubmin = StringAttr.valueOf(s, (short)1);
   }
 
   public String getInFinalMarkCalculationExamAdmMethGc224() {
      return StringAttr.valueOf(importView.InFinalMarkCalculationExamAdmMethGc224);
   }
   public void setInFinalMarkCalculationExamAdmMethGc224(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InFinalMarkCalculationExamAdmMethGc224 must be <= 10 characters.",
               new PropertyChangeEvent (this, "InFinalMarkCalculationExamAdmMethGc224", null, null));
      }
      importView.InFinalMarkCalculationExamAdmMethGc224 = StringAttr.valueOf(s, (short)10);
   }
 
   public short getInFinalMarkCalculationExamAdmNrAss() {
      return importView.InFinalMarkCalculationExamAdmNrAss;
   }
   public void setInFinalMarkCalculationExamAdmNrAss(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InFinalMarkCalculationExamAdmNrAss has more than 2 digits.",
               new PropertyChangeEvent (this, "InFinalMarkCalculationExamAdmNrAss", null, null));
      }
      importView.InFinalMarkCalculationExamAdmNrAss = ShortAttr.valueOf(s);
   }
   public void setAsStringInFinalMarkCalculationExamAdmNrAss(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InFinalMarkCalculationExamAdmNrAss is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InFinalMarkCalculationExamAdmNrAss", null, null));
      }
      try {
          setInFinalMarkCalculationExamAdmNrAss(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InFinalMarkCalculationExamAdmNrAss is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InFinalMarkCalculationExamAdmNrAss", null, null));
      }
   }
 
   public short getInFinalMarkCalculationExamAdmYrSubmin() {
      return importView.InFinalMarkCalculationExamAdmYrSubmin;
   }
   public void setInFinalMarkCalculationExamAdmYrSubmin(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InFinalMarkCalculationExamAdmYrSubmin has more than 2 digits.",
               new PropertyChangeEvent (this, "InFinalMarkCalculationExamAdmYrSubmin", null, null));
      }
      importView.InFinalMarkCalculationExamAdmYrSubmin = ShortAttr.valueOf(s);
   }
   public void setAsStringInFinalMarkCalculationExamAdmYrSubmin(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InFinalMarkCalculationExamAdmYrSubmin is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InFinalMarkCalculationExamAdmYrSubmin", null, null));
      }
      try {
          setInFinalMarkCalculationExamAdmYrSubmin(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InFinalMarkCalculationExamAdmYrSubmin is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InFinalMarkCalculationExamAdmYrSubmin", null, null));
      }
   }
 
   public final int InAssignmentGroupMax = 100;
   public short getInAssignmentGroupCount() {
      return (short)(importView.InAssignmentGroup_MA);
   };
 
   public void setInAssignmentGroupCount(short s) throws PropertyVetoException {
      if (s < 0 || s > InAssignmentGroupMax) {
         throw new PropertyVetoException("InAssignmentGroupCount value is not a valid value. (0 to 100)",
               new PropertyChangeEvent (this, "InAssignmentGroupCount", null, null));
      } else {
         importView.InAssignmentGroup_MA = IntAttr.valueOf((int)s);
      }
   }
 
   public short getInAssGrpUniqueAssignmentYear(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return importView.InAssGrpUniqueAssignmentYear[index];
   }
   public void setInAssGrpUniqueAssignmentYear(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InAssGrpUniqueAssignmentYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InAssGrpUniqueAssignmentYear", null, null));
      }
      importView.InAssGrpUniqueAssignmentYear[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInAssGrpUniqueAssignmentYear(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InAssGrpUniqueAssignmentYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InAssGrpUniqueAssignmentYear", null, null));
      }
      try {
          setInAssGrpUniqueAssignmentYear(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InAssGrpUniqueAssignmentYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InAssGrpUniqueAssignmentYear", null, null));
      }
   }
 
   public short getInAssGrpUniqueAssignmentPeriod(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return importView.InAssGrpUniqueAssignmentPeriod[index];
   }
   public void setInAssGrpUniqueAssignmentPeriod(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InAssGrpUniqueAssignmentPeriod has more than 2 digits.",
               new PropertyChangeEvent (this, "InAssGrpUniqueAssignmentPeriod", null, null));
      }
      importView.InAssGrpUniqueAssignmentPeriod[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInAssGrpUniqueAssignmentPeriod(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InAssGrpUniqueAssignmentPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InAssGrpUniqueAssignmentPeriod", null, null));
      }
      try {
          setInAssGrpUniqueAssignmentPeriod(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InAssGrpUniqueAssignmentPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InAssGrpUniqueAssignmentPeriod", null, null));
      }
   }
 
   public int getInAssGrpUniqueAssignmentUniqueNr(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return importView.InAssGrpUniqueAssignmentUniqueNr[index];
   }
   public void setInAssGrpUniqueAssignmentUniqueNr(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 10000000.0) {
         throw new PropertyVetoException("InAssGrpUniqueAssignmentUniqueNr has more than 7 digits.",
               new PropertyChangeEvent (this, "InAssGrpUniqueAssignmentUniqueNr", null, null));
      }
      importView.InAssGrpUniqueAssignmentUniqueNr[index] = IntAttr.valueOf(s);
   }
   public void setAsStringInAssGrpUniqueAssignmentUniqueNr(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InAssGrpUniqueAssignmentUniqueNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InAssGrpUniqueAssignmentUniqueNr", null, null));
      }
      try {
          setInAssGrpUniqueAssignmentUniqueNr(index, Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InAssGrpUniqueAssignmentUniqueNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InAssGrpUniqueAssignmentUniqueNr", null, null));
      }
   }
 
   public String getInAssErrGrpCsfStringsString1(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InAssErrGrpCsfStringsString1[index], 1);
   }
   public void setInAssErrGrpCsfStringsString1(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      Saacq01sAssessmentCriteriaMntCsfStringsString1PropertyEditor pe = new Saacq01sAssessmentCriteriaMntCsfStringsString1PropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InAssErrGrpCsfStringsString1 value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InAssErrGrpCsfStringsString1", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InAssErrGrpCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InAssErrGrpCsfStringsString1", null, null));
      }
      importView.InAssErrGrpCsfStringsString1[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public final int OutAssLanguageGrpMax = 20;
   public short getOutAssLanguageGrpCount() {
      return (short)(exportView.OutAssLanguageGrp_MA);
   };
 
   public int getOutGrpOnlineAssignmentLangMkUniqueNr(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return exportView.OutGrpOnlineAssignmentLangMkUniqueNr[index];
   }
 
   public String getOutGrpOnlineAssignmentLangLanguageGc203(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGrpOnlineAssignmentLangLanguageGc203[index], 10);
   }
 
   public String getOutGrpOnlineAssignmentLangOtherLangDesc(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGrpOnlineAssignmentLangOtherLangDesc[index], 20);
   }
 
   public final int OutAssMarkerLanguageGroupMax = 20;
   public short getOutAssMarkerLanguageGroupCount() {
      return (short)(exportView.OutAssMarkerLanguageGroup_MA);
   };
 
   public int getOutGrpAssignmentMarkerLangMkUniqueNr(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return exportView.OutGrpAssignmentMarkerLangMkUniqueNr[index];
   }
 
   public int getOutGrpAssignmentMarkerLangMkLecturerNr(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return exportView.OutGrpAssignmentMarkerLangMkLecturerNr[index];
   }
 
   public String getOutGrpAssignmentMarkerLangLanguageGc203(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGrpAssignmentMarkerLangLanguageGc203[index], 10);
   }
 
   public String getOutGrpAssignmentMarkerLangOtherLangDesc(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGrpAssignmentMarkerLangOtherLangDesc[index], 20);
   }
 
   public final int OutAssFormatGrpMax = 50;
   public short getOutAssFormatGrpCount() {
      return (short)(exportView.OutAssFormatGrp_MA);
   };
 
   public String getOutGrpOnlineAssignmentFormatsMkStudyUnitCode(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGrpOnlineAssignmentFormatsMkStudyUnitCode[index], 7);
   }
 
   public String getOutGrpOnlineAssignmentFormatsExtention(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGrpOnlineAssignmentFormatsExtention[index], 4);
   }
 
   public int getOutGrpOnlineAssignmentFormatsMkUniqueNr(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return exportView.OutGrpOnlineAssignmentFormatsMkUniqueNr[index];
   }
 
   public String getOutGrpOnlineAssignmentFormatsDescription(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGrpOnlineAssignmentFormatsDescription[index], 32);
   }
 
   public short getOutAssessmentLogYear() {
      return exportView.OutAssessmentLogYear;
   }
 
   public short getOutAssessmentLogPeriod() {
      return exportView.OutAssessmentLogPeriod;
   }
 
   public String getOutAssessmentLogMkStudyUnitCode() {
      return StringAttr.valueOf(exportView.OutAssessmentLogMkStudyUnitCode);
   }
 
   public String getOutAssessmentLogTypeGc52() {
      return StringAttr.valueOf(exportView.OutAssessmentLogTypeGc52);
   }
 
   public String getOutAssessmentLogActionGc53() {
      return StringAttr.valueOf(exportView.OutAssessmentLogActionGc53);
   }
 
   public String getOutAssessmentLogComments() {
      return StringAttr.valueOf(exportView.OutAssessmentLogComments);
   }
 
   public String getOutAssessmentLogReturnEmailAddress() {
      return StringAttr.valueOf(exportView.OutAssessmentLogReturnEmailAddress);
   }
 
   public String getOutAssessmentLogUpdatedBy() {
      return StringAttr.valueOf(exportView.OutAssessmentLogUpdatedBy);
   }
 
   public Calendar getOutAssessmentLogUpdatedOn() {
      return TimestampAttr.toCalendar(exportView.OutAssessmentLogUpdatedOn);
   }
   public String getAsStringOutAssessmentLogUpdatedOn() {
      return TimestampAttr.toString(exportView.OutAssessmentLogUpdatedOn);
   }
 
   public String getOutAssessmentLogRequestActionFrom() {
      return StringAttr.valueOf(exportView.OutAssessmentLogRequestActionFrom);
   }
 
   public final int OutMarkerGroupMax = 100;
   public short getOutMarkerGroupCount() {
      return (short)(exportView.OutMarkerGroup_MA);
   };
 
   public int getOutMarkerGrpAssignmentMarkingDetailsMkLecturerNr(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return exportView.OutMarkerGrpAssignmentMarkingDetailsMkLecturerNr[index];
   }
 
   public short getOutMarkerGrpAssignmentMarkingDetailsMarkPercentage(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return exportView.OutMarkerGrpAssignmentMarkingDetailsMarkPercentage[index];
   }
 
   public int getOutMarkerGrpAssignmentMarkingDetailsNrOutstanding(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return exportView.OutMarkerGrpAssignmentMarkingDetailsNrOutstanding[index];
   }
 
   public int getOutMarkerGrpAssignmentMarkingDetailsNrReturned(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return exportView.OutMarkerGrpAssignmentMarkingDetailsNrReturned[index];
   }
 
   public short getOutMarkerGrpAssignmentMarkingDetailsAverageMark(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return exportView.OutMarkerGrpAssignmentMarkingDetailsAverageMark[index];
   }
 
   public String getOutMarkerGrpAssignmentMarkingDetailsStatusFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutMarkerGrpAssignmentMarkingDetailsStatusFlag[index], 1);
   }
 
   public String getOutMarkerErrGrpCsfStringsString1(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutMarkerErrGrpCsfStringsString1[index], 1);
   }
 
   public int getOutStudentMarkReadingSheetMkStudentNr() {
      return exportView.OutStudentMarkReadingSheetMkStudentNr;
   }
 
   public double getOutStudentMarkReadingSheetBatchNr() {
      return exportView.OutStudentMarkReadingSheetBatchNr;
   }
 
   public short getOutStudentMarkReadingSheetBatchPosition() {
      return exportView.OutStudentMarkReadingSheetBatchPosition;
   }
 
   public short getOutStudentMarkReadingSheetFinalMarkCode() {
      return exportView.OutStudentMarkReadingSheetFinalMarkCode;
   }
 
   public String getOutStudentMarkReadingSheetAnswers() {
      return StringAttr.valueOf(exportView.OutStudentMarkReadingSheetAnswers);
   }
 
   public short getOutStudentMarkReadingSheetCalculatedMark() {
      return exportView.OutStudentMarkReadingSheetCalculatedMark;
   }
 
   public String getOutStudentMarkReadingSheetSolFlag() {
      return StringAttr.valueOf(exportView.OutStudentMarkReadingSheetSolFlag);
   }
 
   public int getOutAssignmentMarkingDetailsMkLecturerNr() {
      return exportView.OutAssignmentMarkingDetailsMkLecturerNr;
   }
 
   public short getOutAssignmentMarkingDetailsMarkPercentage() {
      return exportView.OutAssignmentMarkingDetailsMarkPercentage;
   }
 
   public int getOutAssignmentMarkingDetailsNrOutstanding() {
      return exportView.OutAssignmentMarkingDetailsNrOutstanding;
   }
 
   public int getOutAssignmentMarkingDetailsNrReturned() {
      return exportView.OutAssignmentMarkingDetailsNrReturned;
   }
 
   public short getOutAssignmentMarkingDetailsAverageMark() {
      return exportView.OutAssignmentMarkingDetailsAverageMark;
   }
 
   public String getOutAssignmentMarkingDetailsStatusFlag() {
      return FixedStringAttr.valueOf(exportView.OutAssignmentMarkingDetailsStatusFlag, 1);
   }
 
   public short getOutUniqueAssignmentYear() {
      return exportView.OutUniqueAssignmentYear;
   }
 
   public short getOutUniqueAssignmentPeriod() {
      return exportView.OutUniqueAssignmentPeriod;
   }
 
   public int getOutUniqueAssignmentUniqueNr() {
      return exportView.OutUniqueAssignmentUniqueNr;
   }
 
   public String getOutUniqueAssignmentMkStudyUnitCode() {
      return FixedStringAttr.valueOf(exportView.OutUniqueAssignmentMkStudyUnitCode, 7);
   }
 
   public short getOutUniqueAssignmentAssignmentNr() {
      return exportView.OutUniqueAssignmentAssignmentNr;
   }
 
   public short getOutUniqueAssignmentNrOfQuestions() {
      return exportView.OutUniqueAssignmentNrOfQuestions;
   }
 
   public String getOutUniqueAssignmentType() {
      return FixedStringAttr.valueOf(exportView.OutUniqueAssignmentType, 1);
   }
 
   public short getOutUniqueAssignmentNegativeMarkFactor() {
      return exportView.OutUniqueAssignmentNegativeMarkFactor;
   }
 
   public short getOutUniqueAssignmentMaxCredits() {
      return exportView.OutUniqueAssignmentMaxCredits;
   }
 
   public short getOutUniqueAssignmentCreditSystem() {
      return exportView.OutUniqueAssignmentCreditSystem;
   }
 
   public short getOutUniqueAssignmentPassMarkPercentage() {
      return exportView.OutUniqueAssignmentPassMarkPercentage;
   }
 
   public Calendar getOutUniqueAssignmentClosingDate() {
      return DateAttr.toCalendar(exportView.OutUniqueAssignmentClosingDate);
   }
   public int getAsIntOutUniqueAssignmentClosingDate() {
      return DateAttr.toInt(exportView.OutUniqueAssignmentClosingDate);
   }
 
   public short getOutUniqueAssignmentReportIndicator() {
      return exportView.OutUniqueAssignmentReportIndicator;
   }
 
   public int getOutUniqueAssignmentNextUnqNr() {
      return exportView.OutUniqueAssignmentNextUnqNr;
   }
 
   public short getOutUniqueAssignmentSupplStartNr() {
      return exportView.OutUniqueAssignmentSupplStartNr;
   }
 
   public String getOutUniqueAssignmentBlockSol() {
      return FixedStringAttr.valueOf(exportView.OutUniqueAssignmentBlockSol, 1);
   }
 
   public String getOutUniqueAssignmentBlockEsol() {
      return FixedStringAttr.valueOf(exportView.OutUniqueAssignmentBlockEsol, 1);
   }
 
   public Calendar getOutUniqueAssignmentMarkingDate() {
      return DateAttr.toCalendar(exportView.OutUniqueAssignmentMarkingDate);
   }
   public int getAsIntOutUniqueAssignmentMarkingDate() {
      return DateAttr.toInt(exportView.OutUniqueAssignmentMarkingDate);
   }
 
   public short getOutUniqueAssignmentNoCoverSheets() {
      return exportView.OutUniqueAssignmentNoCoverSheets;
   }
 
   public short getOutUniqueAssignmentMaxPerDocket() {
      return exportView.OutUniqueAssignmentMaxPerDocket;
   }
 
   public String getOutUniqueAssignmentCompulsory() {
      return FixedStringAttr.valueOf(exportView.OutUniqueAssignmentCompulsory, 1);
   }
 
   public String getOutUniqueAssignmentReleaseResults() {
      return StringAttr.valueOf(exportView.OutUniqueAssignmentReleaseResults);
   }
 
   public String getOutUniqueAssignmentCancelForSuppl() {
      return StringAttr.valueOf(exportView.OutUniqueAssignmentCancelForSuppl);
   }
 
   public int getOutUniqueAssignmentTsaUniqueNr() {
      return exportView.OutUniqueAssignmentTsaUniqueNr;
   }
 
   public String getOutUniqueAssignmentOnlineTypeGc176() {
      return FixedStringAttr.valueOf(exportView.OutUniqueAssignmentOnlineTypeGc176, 10);
   }
 
   public String getOutUniqueAssignmentOnscreenMarkFlag() {
      return FixedStringAttr.valueOf(exportView.OutUniqueAssignmentOnscreenMarkFlag, 1);
   }
 
   public Calendar getOutUniqueAssignmentFileReleaseDate() {
      return DateAttr.toCalendar(exportView.OutUniqueAssignmentFileReleaseDate);
   }
   public int getAsIntOutUniqueAssignmentFileReleaseDate() {
      return DateAttr.toInt(exportView.OutUniqueAssignmentFileReleaseDate);
   }
 
   public String getOutUniqueAssignmentStuSpecifyLang() {
      return FixedStringAttr.valueOf(exportView.OutUniqueAssignmentStuSpecifyLang, 1);
   }
 
   public Calendar getOutUniqueAssignmentPfOpenDate() {
      return DateAttr.toCalendar(exportView.OutUniqueAssignmentPfOpenDate);
   }
   public int getAsIntOutUniqueAssignmentPfOpenDate() {
      return DateAttr.toInt(exportView.OutUniqueAssignmentPfOpenDate);
   }
 
   public short getOutUniqueAssignmentFileSizeLimit() {
      return exportView.OutUniqueAssignmentFileSizeLimit;
   }
 
   public Calendar getOutUniqueAssignmentFinalSubmitDate() {
      return TimestampAttr.toCalendar(exportView.OutUniqueAssignmentFinalSubmitDate);
   }
   public String getAsStringOutUniqueAssignmentFinalSubmitDate() {
      return TimestampAttr.toString(exportView.OutUniqueAssignmentFinalSubmitDate);
   }
 
   public String getOutUniqueAssignmentAssessGroupGc230() {
      return StringAttr.valueOf(exportView.OutUniqueAssignmentAssessGroupGc230);
   }
 
   public Calendar getOutUniqueAssignmentPfOpenTime() {
      return TimeAttr.toCalendar(exportView.OutUniqueAssignmentPfOpenTime);
   }
   public int getAsIntOutUniqueAssignmentPfOpenTime() {
      return TimeAttr.toInt(exportView.OutUniqueAssignmentPfOpenTime);
   }
 
   public String getOutYearMarkCalculationStudentAssignmentNr() {
      return FixedStringAttr.valueOf(exportView.OutYearMarkCalculationStudentAssignmentNr, 2);
   }
 
   public String getOutYearMarkCalculationType() {
      return FixedStringAttr.valueOf(exportView.OutYearMarkCalculationType, 1);
   }
 
   public double getOutYearMarkCalculationNormalWeight() {
      return exportView.OutYearMarkCalculationNormalWeight;
   }
 
   public double getOutYearMarkCalculationRepeatWeight() {
      return exportView.OutYearMarkCalculationRepeatWeight;
   }
 
   public String getOutYearMarkCalculationOptionalityGc3() {
      return StringAttr.valueOf(exportView.OutYearMarkCalculationOptionalityGc3);
   }
 
   public int getOutYearMarkCalculationSupplWeight() {
      return exportView.OutYearMarkCalculationSupplWeight;
   }
 
   public String getOutYearMarkCalculationSubTypeGc206() {
      return StringAttr.valueOf(exportView.OutYearMarkCalculationSubTypeGc206);
   }
 
   public short getOutFinalMarkCalculationExamYear() {
      return exportView.OutFinalMarkCalculationExamYear;
   }
 
   public short getOutFinalMarkCalculationMkExamPeriod() {
      return exportView.OutFinalMarkCalculationMkExamPeriod;
   }
 
   public String getOutFinalMarkCalculationMkStudyUnitCode() {
      return FixedStringAttr.valueOf(exportView.OutFinalMarkCalculationMkStudyUnitCode, 7);
   }
 
   public double getOutFinalMarkCalculationYearMarkWeight() {
      return exportView.OutFinalMarkCalculationYearMarkWeight;
   }
 
   public double getOutFinalMarkCalculationExaminationWeight() {
      return exportView.OutFinalMarkCalculationExaminationWeight;
   }
 
   public double getOutFinalMarkCalculationPortfolioWeight() {
      return exportView.OutFinalMarkCalculationPortfolioWeight;
   }
 
   public String getOutFinalMarkCalculationYmSystemGc2() {
      return StringAttr.valueOf(exportView.OutFinalMarkCalculationYmSystemGc2);
   }
 
   public short getOutFinalMarkCalculationYmSubminimum() {
      return exportView.OutFinalMarkCalculationYmSubminimum;
   }
 
   public short getOutFinalMarkCalculationNoElectiveAssignments() {
      return exportView.OutFinalMarkCalculationNoElectiveAssignments;
   }
 
   public short getOutFinalMarkCalculationPfSubminimum() {
      return exportView.OutFinalMarkCalculationPfSubminimum;
   }
 
   public String getOutFinalMarkCalculationPfCompulsory() {
      return FixedStringAttr.valueOf(exportView.OutFinalMarkCalculationPfCompulsory, 1);
   }
 
   public String getOutFinalMarkCalculationYmCompulsory() {
      return FixedStringAttr.valueOf(exportView.OutFinalMarkCalculationYmCompulsory, 1);
   }
 
   public String getOutFinalMarkCalculationXmCompulsory() {
      return FixedStringAttr.valueOf(exportView.OutFinalMarkCalculationXmCompulsory, 1);
   }
 
   public short getOutFinalMarkCalculationNoOfElectivePf() {
      return exportView.OutFinalMarkCalculationNoOfElectivePf;
   }
 
   public short getOutFinalMarkCalculationXamSubminimum() {
      return exportView.OutFinalMarkCalculationXamSubminimum;
   }
 
   public String getOutFinalMarkCalculationCalcYmSuppl() {
      return StringAttr.valueOf(exportView.OutFinalMarkCalculationCalcYmSuppl);
   }
 
   public String getOutFinalMarkCalculationCalcYmSubmin() {
      return StringAttr.valueOf(exportView.OutFinalMarkCalculationCalcYmSubmin);
   }
 
   public String getOutFinalMarkCalculationExamAdmMethGc224() {
      return StringAttr.valueOf(exportView.OutFinalMarkCalculationExamAdmMethGc224);
   }
 
   public short getOutFinalMarkCalculationExamAdmNrAss() {
      return exportView.OutFinalMarkCalculationExamAdmNrAss;
   }
 
   public short getOutFinalMarkCalculationExamAdmYrSubmin() {
      return exportView.OutFinalMarkCalculationExamAdmYrSubmin;
   }
 
   public final int OutAssignmentGroupMax = 100;
   public short getOutAssignmentGroupCount() {
      return (short)(exportView.OutAssignmentGroup_MA);
   };
 
   public short getOutGrpUniqueAssignmentYear(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return exportView.OutGrpUniqueAssignmentYear[index];
   }
 
   public short getOutGrpUniqueAssignmentPeriod(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return exportView.OutGrpUniqueAssignmentPeriod[index];
   }
 
   public int getOutGrpUniqueAssignmentUniqueNr(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return exportView.OutGrpUniqueAssignmentUniqueNr[index];
   }
 
   public String getOutErrGrpCsfStringsString1(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutErrGrpCsfStringsString1[index], 1);
   }
 
   public String getOutCsfClientServerCommunicationsServerRollbackFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsServerRollbackFlag, 1);
   }
 
   public String getOutCsfStringsString500() {
      return StringAttr.valueOf(exportView.OutCsfStringsString500);
   }
 
};
