package Excdq01h.Abean;
 
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
public class Excdq01sExamCoverDocketMnt  implements ActionListener, java.io.Serializable  {
 
   //  Use final String for the bean name
   public static final String BEANNAME = new String("Excdq01sExamCoverDocketMnt");
 
   //  Constants for Asynchronous status
   public static final int PENDING = CoopFlow.DATA_NOT_READY;
   public static final int AVAILABLE = CoopFlow.DATA_READY;
   public static final int INVALID_ID = CoopFlow.INVALID_ID;
 
   private final SimpleDateFormat nativeTimestampFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
   private final SimpleDateFormat nativeTimeFormatter = new SimpleDateFormat("HHmmss");
   private final SimpleDateFormat nativeDateFormatter = new SimpleDateFormat("yyyyMMdd");
   private static DecimalFormat decimalFormatter;
 
   public Excdq01sExamCoverDocketMnt() {
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
 
 
   private Excdq01sExamCoverDocketMntOperation oper = null;
 
   /**
    * Calls the transaction/procedure step on the server.
    *
    * @exception java.beans.PropertyVetoException
    * Final property checks can throw this exception.
    */
   public void execute()  throws PropertyVetoException {
      try  {
         if (oper == null) {
            oper = new Excdq01sExamCoverDocketMntOperation(this);
            addCompletionListener(new operListener(this));
            addExceptionListener(new operListener(this));
         }
 

 
         oper.doExcdq01sExamCoverDocketMntOperation();
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
      private Excdq01sExamCoverDocketMnt rP;
      operListener(Excdq01sExamCoverDocketMnt r)  {
         rP = r;
      }
      public void actionPerformed(ActionEvent aEvent)  {
         if (Trace.isTracing(Trace.MASK_APPLICATION))
         {
            Trace.record(Trace.MASK_APPLICATION, "Excdq01sExamCoverDocketMnt", "Listener heard that Excdq01sExamCoverDocketMntOperation completed with " + 
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of completion of operation Excdq01sExamCoverDocketMnt ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      synchronized (this)  {
         targets = (Vector) completionListeners.clone();
      }
      actionEvt = new ActionEvent(this, 0, "Completion.Excdq01sExamCoverDocketMnt");
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of exception of operation Excdq01sExamCoverDocketMnt ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      String failCommand = "Exception.Excdq01sExamCoverDocketMnt";
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
      importView.InExamStationaryGroup_MA = IntAttr.valueOf(InExamStationaryGroupMax);
      importView.InExamInstructionGroup_MA = IntAttr.valueOf(InExamInstructionGroupMax);
      importView.InPaperLanguageGroup_MA = IntAttr.valueOf(InPaperLanguageGroupMax);
      importView.InAdditionalInstrGroup_MA = IntAttr.valueOf(InAdditionalInstrGroupMax);
      exportView.OutExamGroup_MA = IntAttr.getDefaultValue();
      exportView.OutExaminersGroup_MA = IntAttr.getDefaultValue();
      exportView.OutEquivalentGroup_MA = IntAttr.getDefaultValue();
      exportView.OutExamStationaryGroup_MA = IntAttr.getDefaultValue();
      exportView.OutExamInstructionGroup_MA = IntAttr.getDefaultValue();
      exportView.OutPaperLanguageGroup_MA = IntAttr.getDefaultValue();
      exportView.OutAdditionalInstrGroup_MA = IntAttr.getDefaultValue();
   }

   Excdq01h.EXCDQ01S_IA importView = Excdq01h.EXCDQ01S_IA.getInstance();
   Excdq01h.EXCDQ01S_OA exportView = Excdq01h.EXCDQ01S_OA.getInstance();
   public int getInExamPaperCoverDocketContactPersNr() {
      return importView.InExamPaperCoverDocketContactPersNr;
   }
   public void setInExamPaperCoverDocketContactPersNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InExamPaperCoverDocketContactPersNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InExamPaperCoverDocketContactPersNr", null, null));
      }
      importView.InExamPaperCoverDocketContactPersNr = IntAttr.valueOf(s);
   }
   public void setAsStringInExamPaperCoverDocketContactPersNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InExamPaperCoverDocketContactPersNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamPaperCoverDocketContactPersNr", null, null));
      }
      try {
          setInExamPaperCoverDocketContactPersNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InExamPaperCoverDocketContactPersNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamPaperCoverDocketContactPersNr", null, null));
      }
   }
 
   public String getInExamPaperCoverDocketBookRequired() {
      return StringAttr.valueOf(importView.InExamPaperCoverDocketBookRequired);
   }
   public void setInExamPaperCoverDocketBookRequired(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InExamPaperCoverDocketBookRequired must be <= 1 characters.",
               new PropertyChangeEvent (this, "InExamPaperCoverDocketBookRequired", null, null));
      }
      importView.InExamPaperCoverDocketBookRequired = StringAttr.valueOf(s, (short)1);
   }
 
   public String getInExamPaperCoverDocketProofReadRequest() {
      return StringAttr.valueOf(importView.InExamPaperCoverDocketProofReadRequest);
   }
   public void setInExamPaperCoverDocketProofReadRequest(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InExamPaperCoverDocketProofReadRequest must be <= 1 characters.",
               new PropertyChangeEvent (this, "InExamPaperCoverDocketProofReadRequest", null, null));
      }
      importView.InExamPaperCoverDocketProofReadRequest = StringAttr.valueOf(s, (short)1);
   }
 
   public String getInExamPaperCoverDocketStatusGc41() {
      return StringAttr.valueOf(importView.InExamPaperCoverDocketStatusGc41);
   }
   public void setInExamPaperCoverDocketStatusGc41(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InExamPaperCoverDocketStatusGc41 must be <= 10 characters.",
               new PropertyChangeEvent (this, "InExamPaperCoverDocketStatusGc41", null, null));
      }
      importView.InExamPaperCoverDocketStatusGc41 = StringAttr.valueOf(s, (short)10);
   }
 
   public short getInExamPaperCoverDocketTotalRackPages() {
      return importView.InExamPaperCoverDocketTotalRackPages;
   }
   public void setInExamPaperCoverDocketTotalRackPages(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InExamPaperCoverDocketTotalRackPages has more than 4 digits.",
               new PropertyChangeEvent (this, "InExamPaperCoverDocketTotalRackPages", null, null));
      }
      importView.InExamPaperCoverDocketTotalRackPages = ShortAttr.valueOf(s);
   }
   public void setAsStringInExamPaperCoverDocketTotalRackPages(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InExamPaperCoverDocketTotalRackPages is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamPaperCoverDocketTotalRackPages", null, null));
      }
      try {
          setInExamPaperCoverDocketTotalRackPages(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InExamPaperCoverDocketTotalRackPages is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamPaperCoverDocketTotalRackPages", null, null));
      }
   }
 
   public short getInExamPaperCoverDocketTotalPages() {
      return importView.InExamPaperCoverDocketTotalPages;
   }
   public void setInExamPaperCoverDocketTotalPages(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InExamPaperCoverDocketTotalPages has more than 4 digits.",
               new PropertyChangeEvent (this, "InExamPaperCoverDocketTotalPages", null, null));
      }
      importView.InExamPaperCoverDocketTotalPages = ShortAttr.valueOf(s);
   }
   public void setAsStringInExamPaperCoverDocketTotalPages(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InExamPaperCoverDocketTotalPages is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamPaperCoverDocketTotalPages", null, null));
      }
      try {
          setInExamPaperCoverDocketTotalPages(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InExamPaperCoverDocketTotalPages is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamPaperCoverDocketTotalPages", null, null));
      }
   }
 
   public String getInExamPaperCoverDocketAnnexurePages() {
      return StringAttr.valueOf(importView.InExamPaperCoverDocketAnnexurePages);
   }
   public void setInExamPaperCoverDocketAnnexurePages(String s)
      throws PropertyVetoException {
      if (s.length() > 20) {
         throw new PropertyVetoException("InExamPaperCoverDocketAnnexurePages must be <= 20 characters.",
               new PropertyChangeEvent (this, "InExamPaperCoverDocketAnnexurePages", null, null));
      }
      importView.InExamPaperCoverDocketAnnexurePages = StringAttr.valueOf(s, (short)20);
   }
 
   public String getInExamPaperCoverDocketMemorandumIncl() {
      return StringAttr.valueOf(importView.InExamPaperCoverDocketMemorandumIncl);
   }
   public void setInExamPaperCoverDocketMemorandumIncl(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InExamPaperCoverDocketMemorandumIncl must be <= 1 characters.",
               new PropertyChangeEvent (this, "InExamPaperCoverDocketMemorandumIncl", null, null));
      }
      importView.InExamPaperCoverDocketMemorandumIncl = StringAttr.valueOf(s, (short)1);
   }
 
   public String getInExamPaperCoverDocketDeclarationFlag() {
      return StringAttr.valueOf(importView.InExamPaperCoverDocketDeclarationFlag);
   }
   public void setInExamPaperCoverDocketDeclarationFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InExamPaperCoverDocketDeclarationFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InExamPaperCoverDocketDeclarationFlag", null, null));
      }
      importView.InExamPaperCoverDocketDeclarationFlag = StringAttr.valueOf(s, (short)1);
   }
 
   public String getInExamPaperCoverDocketFillInPaperGc50() {
      return StringAttr.valueOf(importView.InExamPaperCoverDocketFillInPaperGc50);
   }
   public void setInExamPaperCoverDocketFillInPaperGc50(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InExamPaperCoverDocketFillInPaperGc50 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InExamPaperCoverDocketFillInPaperGc50", null, null));
      }
      importView.InExamPaperCoverDocketFillInPaperGc50 = StringAttr.valueOf(s, (short)1);
   }
 
   public String getInExamPaperCoverDocketOpenBookGc50() {
      return StringAttr.valueOf(importView.InExamPaperCoverDocketOpenBookGc50);
   }
   public void setInExamPaperCoverDocketOpenBookGc50(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InExamPaperCoverDocketOpenBookGc50 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InExamPaperCoverDocketOpenBookGc50", null, null));
      }
      importView.InExamPaperCoverDocketOpenBookGc50 = StringAttr.valueOf(s, (short)1);
   }
 
   public String getInExamPaperCoverDocketKeepPaperFlag() {
      return StringAttr.valueOf(importView.InExamPaperCoverDocketKeepPaperFlag);
   }
   public void setInExamPaperCoverDocketKeepPaperFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InExamPaperCoverDocketKeepPaperFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InExamPaperCoverDocketKeepPaperFlag", null, null));
      }
      importView.InExamPaperCoverDocketKeepPaperFlag = StringAttr.valueOf(s, (short)1);
   }
 
   public String getInExamPaperCoverDocketCalcPermitFlag() {
      return StringAttr.valueOf(importView.InExamPaperCoverDocketCalcPermitFlag);
   }
   public void setInExamPaperCoverDocketCalcPermitFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InExamPaperCoverDocketCalcPermitFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InExamPaperCoverDocketCalcPermitFlag", null, null));
      }
      importView.InExamPaperCoverDocketCalcPermitFlag = StringAttr.valueOf(s, (short)1);
   }
 
   public String getInExamPaperCoverDocketExaminerPrtGc107() {
      return FixedStringAttr.valueOf(importView.InExamPaperCoverDocketExaminerPrtGc107, 10);
   }
   public void setInExamPaperCoverDocketExaminerPrtGc107(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InExamPaperCoverDocketExaminerPrtGc107 must be <= 10 characters.",
               new PropertyChangeEvent (this, "InExamPaperCoverDocketExaminerPrtGc107", null, null));
      }
      importView.InExamPaperCoverDocketExaminerPrtGc107 = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInExamPaperCoverDocketMcqInstrFlag() {
      return FixedStringAttr.valueOf(importView.InExamPaperCoverDocketMcqInstrFlag, 1);
   }
   public void setInExamPaperCoverDocketMcqInstrFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InExamPaperCoverDocketMcqInstrFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InExamPaperCoverDocketMcqInstrFlag", null, null));
      }
      importView.InExamPaperCoverDocketMcqInstrFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInExamPaperCoverDocketAttendRegFlag() {
      return FixedStringAttr.valueOf(importView.InExamPaperCoverDocketAttendRegFlag, 1);
   }
   public void setInExamPaperCoverDocketAttendRegFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InExamPaperCoverDocketAttendRegFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InExamPaperCoverDocketAttendRegFlag", null, null));
      }
      importView.InExamPaperCoverDocketAttendRegFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInExamPaperCoverDocketPaperColour() {
      return FixedStringAttr.valueOf(importView.InExamPaperCoverDocketPaperColour, 10);
   }
   public void setInExamPaperCoverDocketPaperColour(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InExamPaperCoverDocketPaperColour must be <= 10 characters.",
               new PropertyChangeEvent (this, "InExamPaperCoverDocketPaperColour", null, null));
      }
      importView.InExamPaperCoverDocketPaperColour = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public short getInExamPaperCoverDocketNrDocsSubmitted() {
      return importView.InExamPaperCoverDocketNrDocsSubmitted;
   }
   public void setInExamPaperCoverDocketNrDocsSubmitted(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InExamPaperCoverDocketNrDocsSubmitted has more than 2 digits.",
               new PropertyChangeEvent (this, "InExamPaperCoverDocketNrDocsSubmitted", null, null));
      }
      importView.InExamPaperCoverDocketNrDocsSubmitted = ShortAttr.valueOf(s);
   }
   public void setAsStringInExamPaperCoverDocketNrDocsSubmitted(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InExamPaperCoverDocketNrDocsSubmitted is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamPaperCoverDocketNrDocsSubmitted", null, null));
      }
      try {
          setInExamPaperCoverDocketNrDocsSubmitted(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InExamPaperCoverDocketNrDocsSubmitted is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamPaperCoverDocketNrDocsSubmitted", null, null));
      }
   }
 
   public String getInExamPaperLogActionGc39() {
      return StringAttr.valueOf(importView.InExamPaperLogActionGc39);
   }
   public void setInExamPaperLogActionGc39(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InExamPaperLogActionGc39 must be <= 10 characters.",
               new PropertyChangeEvent (this, "InExamPaperLogActionGc39", null, null));
      }
      importView.InExamPaperLogActionGc39 = StringAttr.valueOf(s, (short)10);
   }
 
   public String getInExamPaperLogUpdatedBy() {
      return StringAttr.valueOf(importView.InExamPaperLogUpdatedBy);
   }
   public void setInExamPaperLogUpdatedBy(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 20) {
         throw new PropertyVetoException("InExamPaperLogUpdatedBy must be <= 20 characters.",
               new PropertyChangeEvent (this, "InExamPaperLogUpdatedBy", null, null));
      }
      importView.InExamPaperLogUpdatedBy = StringAttr.valueOf(s, (short)20);
   }
 
   public String getInWsUserStudyUnitNovellUserId() {
      return FixedStringAttr.valueOf(importView.InWsUserStudyUnitNovellUserId, 10);
   }
   public void setInWsUserStudyUnitNovellUserId(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InWsUserStudyUnitNovellUserId must be <= 10 characters.",
               new PropertyChangeEvent (this, "InWsUserStudyUnitNovellUserId", null, null));
      }
      importView.InWsUserStudyUnitNovellUserId = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public short getInExamPaperDetailsExamYear() {
      return importView.InExamPaperDetailsExamYear;
   }
   public void setInExamPaperDetailsExamYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InExamPaperDetailsExamYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InExamPaperDetailsExamYear", null, null));
      }
      importView.InExamPaperDetailsExamYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInExamPaperDetailsExamYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InExamPaperDetailsExamYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamPaperDetailsExamYear", null, null));
      }
      try {
          setInExamPaperDetailsExamYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InExamPaperDetailsExamYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamPaperDetailsExamYear", null, null));
      }
   }
 
   public String getInExamPaperDetailsMkStudyUnitCode() {
      return FixedStringAttr.valueOf(importView.InExamPaperDetailsMkStudyUnitCode, 7);
   }
   public void setInExamPaperDetailsMkStudyUnitCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InExamPaperDetailsMkStudyUnitCode must be <= 7 characters.",
               new PropertyChangeEvent (this, "InExamPaperDetailsMkStudyUnitCode", null, null));
      }
      importView.InExamPaperDetailsMkStudyUnitCode = FixedStringAttr.valueOf(s, (short)7);
   }
 
   public short getInExamPaperDetailsNr() {
      return importView.InExamPaperDetailsNr;
   }
   public void setInExamPaperDetailsNr(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InExamPaperDetailsNr has more than 1 digits.",
               new PropertyChangeEvent (this, "InExamPaperDetailsNr", null, null));
      }
      importView.InExamPaperDetailsNr = ShortAttr.valueOf(s);
   }
   public void setAsStringInExamPaperDetailsNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InExamPaperDetailsNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamPaperDetailsNr", null, null));
      }
      try {
          setInExamPaperDetailsNr(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InExamPaperDetailsNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamPaperDetailsNr", null, null));
      }
   }
 
   public short getInExamPaperDetailsNrOfPages() {
      return importView.InExamPaperDetailsNrOfPages;
   }
   public void setInExamPaperDetailsNrOfPages(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InExamPaperDetailsNrOfPages has more than 3 digits.",
               new PropertyChangeEvent (this, "InExamPaperDetailsNrOfPages", null, null));
      }
      importView.InExamPaperDetailsNrOfPages = ShortAttr.valueOf(s);
   }
   public void setAsStringInExamPaperDetailsNrOfPages(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InExamPaperDetailsNrOfPages is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamPaperDetailsNrOfPages", null, null));
      }
      try {
          setInExamPaperDetailsNrOfPages(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InExamPaperDetailsNrOfPages is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamPaperDetailsNrOfPages", null, null));
      }
   }
 
   public short getInExamPeriodDateMkExamPeriodCode() {
      return importView.InExamPeriodDateMkExamPeriodCode;
   }
   public void setInExamPeriodDateMkExamPeriodCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InExamPeriodDateMkExamPeriodCode has more than 2 digits.",
               new PropertyChangeEvent (this, "InExamPeriodDateMkExamPeriodCode", null, null));
      }
      importView.InExamPeriodDateMkExamPeriodCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInExamPeriodDateMkExamPeriodCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InExamPeriodDateMkExamPeriodCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamPeriodDateMkExamPeriodCode", null, null));
      }
      try {
          setInExamPeriodDateMkExamPeriodCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InExamPeriodDateMkExamPeriodCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamPeriodDateMkExamPeriodCode", null, null));
      }
   }
 
   public String getInExamPeriodDateMarkreadingCode() {
      return StringAttr.valueOf(importView.InExamPeriodDateMarkreadingCode);
   }
   public void setInExamPeriodDateMarkreadingCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InExamPeriodDateMarkreadingCode must be <= 1 characters.",
               new PropertyChangeEvent (this, "InExamPeriodDateMarkreadingCode", null, null));
      }
      importView.InExamPeriodDateMarkreadingCode = StringAttr.valueOf(s, (short)1);
   }
 
   public short getInExamPeriodDateScriptMarkTot() {
      return importView.InExamPeriodDateScriptMarkTot;
   }
   public void setInExamPeriodDateScriptMarkTot(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InExamPeriodDateScriptMarkTot has more than 3 digits.",
               new PropertyChangeEvent (this, "InExamPeriodDateScriptMarkTot", null, null));
      }
      importView.InExamPeriodDateScriptMarkTot = ShortAttr.valueOf(s);
   }
   public void setAsStringInExamPeriodDateScriptMarkTot(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InExamPeriodDateScriptMarkTot is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamPeriodDateScriptMarkTot", null, null));
      }
      try {
          setInExamPeriodDateScriptMarkTot(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InExamPeriodDateScriptMarkTot is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamPeriodDateScriptMarkTot", null, null));
      }
   }
 
   public short getInExamPeriodDateMarkreadTot() {
      return importView.InExamPeriodDateMarkreadTot;
   }
   public void setInExamPeriodDateMarkreadTot(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InExamPeriodDateMarkreadTot has more than 3 digits.",
               new PropertyChangeEvent (this, "InExamPeriodDateMarkreadTot", null, null));
      }
      importView.InExamPeriodDateMarkreadTot = ShortAttr.valueOf(s);
   }
   public void setAsStringInExamPeriodDateMarkreadTot(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InExamPeriodDateMarkreadTot is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamPeriodDateMarkreadTot", null, null));
      }
      try {
          setInExamPeriodDateMarkreadTot(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InExamPeriodDateMarkreadTot is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamPeriodDateMarkreadTot", null, null));
      }
   }
 
   public short getInExamPeriodDatePaperMarkTot() {
      return importView.InExamPeriodDatePaperMarkTot;
   }
   public void setInExamPeriodDatePaperMarkTot(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InExamPeriodDatePaperMarkTot has more than 3 digits.",
               new PropertyChangeEvent (this, "InExamPeriodDatePaperMarkTot", null, null));
      }
      importView.InExamPeriodDatePaperMarkTot = ShortAttr.valueOf(s);
   }
   public void setAsStringInExamPeriodDatePaperMarkTot(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InExamPeriodDatePaperMarkTot is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamPeriodDatePaperMarkTot", null, null));
      }
      try {
          setInExamPeriodDatePaperMarkTot(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InExamPeriodDatePaperMarkTot is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamPeriodDatePaperMarkTot", null, null));
      }
   }
 
   public short getInCsfClientServerCommunicationsReturnCode() {
      return importView.InCsfClientServerCommunicationsReturnCode;
   }
   public void setInCsfClientServerCommunicationsReturnCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsReturnCode has more than 4 digits.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsReturnCode", null, null));
      }
      importView.InCsfClientServerCommunicationsReturnCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInCsfClientServerCommunicationsReturnCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InCsfClientServerCommunicationsReturnCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InCsfClientServerCommunicationsReturnCode", null, null));
      }
      try {
          setInCsfClientServerCommunicationsReturnCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InCsfClientServerCommunicationsReturnCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InCsfClientServerCommunicationsReturnCode", null, null));
      }
   }
 
   public short getInCsfClientServerCommunicationsServerVersionNumber() {
      return importView.InCsfClientServerCommunicationsServerVersionNumber;
   }
   public void setInCsfClientServerCommunicationsServerVersionNumber(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsServerVersionNumber has more than 4 digits.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsServerVersionNumber", null, null));
      }
      importView.InCsfClientServerCommunicationsServerVersionNumber = ShortAttr.valueOf(s);
   }
   public void setAsStringInCsfClientServerCommunicationsServerVersionNumber(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InCsfClientServerCommunicationsServerVersionNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InCsfClientServerCommunicationsServerVersionNumber", null, null));
      }
      try {
          setInCsfClientServerCommunicationsServerVersionNumber(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InCsfClientServerCommunicationsServerVersionNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InCsfClientServerCommunicationsServerVersionNumber", null, null));
      }
   }
 
   public short getInCsfClientServerCommunicationsServerRevisionNumber() {
      return importView.InCsfClientServerCommunicationsServerRevisionNumber;
   }
   public void setInCsfClientServerCommunicationsServerRevisionNumber(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsServerRevisionNumber has more than 4 digits.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsServerRevisionNumber", null, null));
      }
      importView.InCsfClientServerCommunicationsServerRevisionNumber = ShortAttr.valueOf(s);
   }
   public void setAsStringInCsfClientServerCommunicationsServerRevisionNumber(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InCsfClientServerCommunicationsServerRevisionNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InCsfClientServerCommunicationsServerRevisionNumber", null, null));
      }
      try {
          setInCsfClientServerCommunicationsServerRevisionNumber(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InCsfClientServerCommunicationsServerRevisionNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InCsfClientServerCommunicationsServerRevisionNumber", null, null));
      }
   }
 
   public String getInCsfClientServerCommunicationsServerDevelopmentPhase() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsServerDevelopmentPhase, 1);
   }
   public void setInCsfClientServerCommunicationsServerDevelopmentPhase(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsServerDevelopmentPhase must be <= 1 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsServerDevelopmentPhase", null, null));
      }
      importView.InCsfClientServerCommunicationsServerDevelopmentPhase = FixedStringAttr.valueOf(s, (short)1);
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
 
   public Calendar getInCsfClientServerCommunicationsServerDate() {
      return DateAttr.toCalendar(importView.InCsfClientServerCommunicationsServerDate);
   }
   public int getAsIntInCsfClientServerCommunicationsServerDate() {
      return DateAttr.toInt(importView.InCsfClientServerCommunicationsServerDate);
   }
   public void setInCsfClientServerCommunicationsServerDate(Calendar s)
      throws PropertyVetoException {
      importView.InCsfClientServerCommunicationsServerDate = DateAttr.valueOf(s);
   }
   public void setAsStringInCsfClientServerCommunicationsServerDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInCsfClientServerCommunicationsServerDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInCsfClientServerCommunicationsServerDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InCsfClientServerCommunicationsServerDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InCsfClientServerCommunicationsServerDate", null, null));
         }
      }
   }
   public void setAsIntInCsfClientServerCommunicationsServerDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInCsfClientServerCommunicationsServerDate(temp);
   }
 
   public Calendar getInCsfClientServerCommunicationsServerTime() {
      return TimeAttr.toCalendar(importView.InCsfClientServerCommunicationsServerTime);
   }
   public int getAsIntInCsfClientServerCommunicationsServerTime() {
      return TimeAttr.toInt(importView.InCsfClientServerCommunicationsServerTime);
   }
   public void setInCsfClientServerCommunicationsServerTime(Calendar s)
      throws PropertyVetoException {
      importView.InCsfClientServerCommunicationsServerTime = TimeAttr.valueOf(s);
   }
   public void setAsStringInCsfClientServerCommunicationsServerTime(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInCsfClientServerCommunicationsServerTime((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeTimeFormatter.parse(s.length() > 6 ? s.substring(0, 6) : s));
            setInCsfClientServerCommunicationsServerTime(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InCsfClientServerCommunicationsServerTime has an invalid format (HHmmss).",
                  new PropertyChangeEvent (this, "InCsfClientServerCommunicationsServerTime", null, null));
         }
      }
   }
   public void setAsIntInCsfClientServerCommunicationsServerTime(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 6)
      {
         temp = "000000".substring(temp.length()) + temp;
      }
      setAsStringInCsfClientServerCommunicationsServerTime(temp);
   }
 
   public Calendar getInCsfClientServerCommunicationsServerTimestamp() {
      return TimestampAttr.toCalendar(importView.InCsfClientServerCommunicationsServerTimestamp);
   }
   public String getAsStringInCsfClientServerCommunicationsServerTimestamp() {
      return TimestampAttr.toString(importView.InCsfClientServerCommunicationsServerTimestamp);
   }
   public void setInCsfClientServerCommunicationsServerTimestamp(Calendar s)
      throws PropertyVetoException {
      importView.InCsfClientServerCommunicationsServerTimestamp = TimestampAttr.valueOf(s);
   }
   public void setAsStringInCsfClientServerCommunicationsServerTimestamp(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInCsfClientServerCommunicationsServerTimestamp((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeTimestampFormatter.parse(s.length() > 17 ? s.substring(0, 17) : s));
            importView.InCsfClientServerCommunicationsServerTimestamp = TimestampAttr.valueOf(s);
         } catch (ParseException e) {
            throw new PropertyVetoException("InCsfClientServerCommunicationsServerTimestamp has an invalid format (yyyyMMddHHmmssSSSSSS).",
                  new PropertyChangeEvent (this, "InCsfClientServerCommunicationsServerTimestamp", null, null));
         }
      }
   }
 
   public String getInCsfClientServerCommunicationsServerTransactionCode() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsServerTransactionCode, 8);
   }
   public void setInCsfClientServerCommunicationsServerTransactionCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 8) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsServerTransactionCode must be <= 8 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsServerTransactionCode", null, null));
      }
      importView.InCsfClientServerCommunicationsServerTransactionCode = FixedStringAttr.valueOf(s, (short)8);
   }
 
   public String getInCsfClientServerCommunicationsServerUserId() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsServerUserId, 8);
   }
   public void setInCsfClientServerCommunicationsServerUserId(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 8) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsServerUserId must be <= 8 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsServerUserId", null, null));
      }
      importView.InCsfClientServerCommunicationsServerUserId = FixedStringAttr.valueOf(s, (short)8);
   }
 
   public String getInCsfClientServerCommunicationsServerRollbackFlag() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsServerRollbackFlag, 1);
   }
   public void setInCsfClientServerCommunicationsServerRollbackFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsServerRollbackFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsServerRollbackFlag", null, null));
      }
      importView.InCsfClientServerCommunicationsServerRollbackFlag = FixedStringAttr.valueOf(s, (short)1);
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
 
   public final int InExamStationaryGroupMax = 20;
   public short getInExamStationaryGroupCount() {
      return (short)(importView.InExamStationaryGroup_MA);
   };
 
   public void setInExamStationaryGroupCount(short s) throws PropertyVetoException {
      if (s < 0 || s > InExamStationaryGroupMax) {
         throw new PropertyVetoException("InExamStationaryGroupCount value is not a valid value. (0 to 20)",
               new PropertyChangeEvent (this, "InExamStationaryGroupCount", null, null));
      } else {
         importView.InExamStationaryGroup_MA = IntAttr.valueOf((int)s);
      }
   }
 
   public String getInGExamStationeryItemCode(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGExamStationeryItemCode[index], 2);
   }
   public void setInGExamStationeryItemCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InGExamStationeryItemCode must be <= 2 characters.",
               new PropertyChangeEvent (this, "InGExamStationeryItemCode", null, null));
      }
      importView.InGExamStationeryItemCode[index] = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public double getInGExamPaperStationeryNeedsAveragePerStudent(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return importView.InGExamPaperStationeryNeedsAveragePerStudent[index];
   }
   public void setInGExamPaperStationeryNeedsAveragePerStudent(int index, double s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
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
         throw new PropertyVetoException("InGExamPaperStationeryNeedsAveragePerStudent has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InGExamPaperStationeryNeedsAveragePerStudent", null, null));
      }
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InGExamPaperStationeryNeedsAveragePerStudent has more than 2 integral digits.",
               new PropertyChangeEvent (this, "InGExamPaperStationeryNeedsAveragePerStudent", null, null));
      }
      importView.InGExamPaperStationeryNeedsAveragePerStudent[index] = DoubleAttr.valueOf(s);
   }
   public void setAsStringInGExamPaperStationeryNeedsAveragePerStudent(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGExamPaperStationeryNeedsAveragePerStudent is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGExamPaperStationeryNeedsAveragePerStudent", null, null));
      }
      try {
          setInGExamPaperStationeryNeedsAveragePerStudent(index, new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGExamPaperStationeryNeedsAveragePerStudent is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGExamPaperStationeryNeedsAveragePerStudent", null, null));
      }
   }
 
   public final int InExamInstructionGroupMax = 25;
   public short getInExamInstructionGroupCount() {
      return (short)(importView.InExamInstructionGroup_MA);
   };
 
   public void setInExamInstructionGroupCount(short s) throws PropertyVetoException {
      if (s < 0 || s > InExamInstructionGroupMax) {
         throw new PropertyVetoException("InExamInstructionGroupCount value is not a valid value. (0 to 25)",
               new PropertyChangeEvent (this, "InExamInstructionGroupCount", null, null));
      } else {
         importView.InExamInstructionGroup_MA = IntAttr.valueOf((int)s);
      }
   }
 
   public short getInGExamDateInstructionMkDocumentParagraphCode(int index) throws ArrayIndexOutOfBoundsException {
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      return importView.InGExamDateInstructionMkDocumentParagraphCode[index];
   }
   public void setInGExamDateInstructionMkDocumentParagraphCode(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InGExamDateInstructionMkDocumentParagraphCode has more than 4 digits.",
               new PropertyChangeEvent (this, "InGExamDateInstructionMkDocumentParagraphCode", null, null));
      }
      importView.InGExamDateInstructionMkDocumentParagraphCode[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGExamDateInstructionMkDocumentParagraphCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGExamDateInstructionMkDocumentParagraphCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGExamDateInstructionMkDocumentParagraphCode", null, null));
      }
      try {
          setInGExamDateInstructionMkDocumentParagraphCode(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGExamDateInstructionMkDocumentParagraphCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGExamDateInstructionMkDocumentParagraphCode", null, null));
      }
   }
 
   public String getInGExamDateInstructionMkWidthType(int index) throws ArrayIndexOutOfBoundsException {
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGExamDateInstructionMkWidthType[index], 1);
   }
   public void setInGExamDateInstructionMkWidthType(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGExamDateInstructionMkWidthType must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGExamDateInstructionMkWidthType", null, null));
      }
      importView.InGExamDateInstructionMkWidthType[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInGExamDateInstructionMkContents(int index) throws ArrayIndexOutOfBoundsException {
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGExamDateInstructionMkContents[index], 2);
   }
   public void setInGExamDateInstructionMkContents(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InGExamDateInstructionMkContents must be <= 2 characters.",
               new PropertyChangeEvent (this, "InGExamDateInstructionMkContents", null, null));
      }
      importView.InGExamDateInstructionMkContents[index] = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public final int InPaperLanguageGroupMax = 10;
   public short getInPaperLanguageGroupCount() {
      return (short)(importView.InPaperLanguageGroup_MA);
   };
 
   public void setInPaperLanguageGroupCount(short s) throws PropertyVetoException {
      if (s < 0 || s > InPaperLanguageGroupMax) {
         throw new PropertyVetoException("InPaperLanguageGroupCount value is not a valid value. (0 to 10)",
               new PropertyChangeEvent (this, "InPaperLanguageGroupCount", null, null));
      } else {
         importView.InPaperLanguageGroup_MA = IntAttr.valueOf((int)s);
      }
   }
 
   public String getInGExamPaperLanguageLanguageGc40(int index) throws ArrayIndexOutOfBoundsException {
      if (9 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 9, not: " + index);
      }
      return StringAttr.valueOf(importView.InGExamPaperLanguageLanguageGc40[index]);
   }
   public void setInGExamPaperLanguageLanguageGc40(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (9 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 9, not: " + index);
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InGExamPaperLanguageLanguageGc40 must be <= 10 characters.",
               new PropertyChangeEvent (this, "InGExamPaperLanguageLanguageGc40", null, null));
      }
      importView.InGExamPaperLanguageLanguageGc40[index] = StringAttr.valueOf(s, (short)10);
   }
 
   public final int InAdditionalInstrGroupMax = 20;
   public short getInAdditionalInstrGroupCount() {
      return (short)(importView.InAdditionalInstrGroup_MA);
   };
 
   public void setInAdditionalInstrGroupCount(short s) throws PropertyVetoException {
      if (s < 0 || s > InAdditionalInstrGroupMax) {
         throw new PropertyVetoException("InAdditionalInstrGroupCount value is not a valid value. (0 to 20)",
               new PropertyChangeEvent (this, "InAdditionalInstrGroupCount", null, null));
      } else {
         importView.InAdditionalInstrGroup_MA = IntAttr.valueOf((int)s);
      }
   }
 
   public short getInGExamPaperAddInstructionsInstructionNr(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return importView.InGExamPaperAddInstructionsInstructionNr[index];
   }
   public void setInGExamPaperAddInstructionsInstructionNr(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InGExamPaperAddInstructionsInstructionNr has more than 2 digits.",
               new PropertyChangeEvent (this, "InGExamPaperAddInstructionsInstructionNr", null, null));
      }
      importView.InGExamPaperAddInstructionsInstructionNr[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGExamPaperAddInstructionsInstructionNr(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGExamPaperAddInstructionsInstructionNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGExamPaperAddInstructionsInstructionNr", null, null));
      }
      try {
          setInGExamPaperAddInstructionsInstructionNr(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGExamPaperAddInstructionsInstructionNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGExamPaperAddInstructionsInstructionNr", null, null));
      }
   }
 
   public String getInGExamPaperAddInstructionsInstruction(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return StringAttr.valueOf(importView.InGExamPaperAddInstructionsInstruction[index]);
   }
   public void setInGExamPaperAddInstructionsInstruction(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      if (s.length() > 132) {
         throw new PropertyVetoException("InGExamPaperAddInstructionsInstruction must be <= 132 characters.",
               new PropertyChangeEvent (this, "InGExamPaperAddInstructionsInstruction", null, null));
      }
      importView.InGExamPaperAddInstructionsInstruction[index] = StringAttr.valueOf(s, (short)132);
   }
 
   public String getInWsAddressV2CellNumber() {
      return FixedStringAttr.valueOf(importView.InWsAddressV2CellNumber, 20);
   }
   public void setInWsAddressV2CellNumber(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 20) {
         throw new PropertyVetoException("InWsAddressV2CellNumber must be <= 20 characters.",
               new PropertyChangeEvent (this, "InWsAddressV2CellNumber", null, null));
      }
      importView.InWsAddressV2CellNumber = FixedStringAttr.valueOf(s, (short)20);
   }
 
   public String getInWsAddressV2HomeNumber() {
      return FixedStringAttr.valueOf(importView.InWsAddressV2HomeNumber, 28);
   }
   public void setInWsAddressV2HomeNumber(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
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
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsAddressV2WorkNumber must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsAddressV2WorkNumber", null, null));
      }
      importView.InWsAddressV2WorkNumber = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public short getOutWsDepartmentCode() {
      return exportView.OutWsDepartmentCode;
   }
 
   public short getOutWsDepartmentCollegeCode() {
      return exportView.OutWsDepartmentCollegeCode;
   }
 
   public short getOutWsDepartmentSchoolCode() {
      return exportView.OutWsDepartmentSchoolCode;
   }
 
   public final int OutExamGroupMax = 400;
   public short getOutExamGroupCount() {
      return (short)(exportView.OutExamGroup_MA);
   };
 
   public short getOutGExamPaperDetailsExamYear(int index) throws ArrayIndexOutOfBoundsException {
      if (399 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 399, not: " + index);
      }
      return exportView.OutGExamPaperDetailsExamYear[index];
   }
 
   public String getOutGExamPaperDetailsMkStudyUnitCode(int index) throws ArrayIndexOutOfBoundsException {
      if (399 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 399, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGExamPaperDetailsMkStudyUnitCode[index], 7);
   }
 
   public short getOutGExamPaperDetailsNr(int index) throws ArrayIndexOutOfBoundsException {
      if (399 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 399, not: " + index);
      }
      return exportView.OutGExamPaperDetailsNr[index];
   }
 
   public short getOutGExamPeriodDateMkExamPeriodCode(int index) throws ArrayIndexOutOfBoundsException {
      if (399 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 399, not: " + index);
      }
      return exportView.OutGExamPeriodDateMkExamPeriodCode[index];
   }
 
   public String getOutWsStudyUnitCode() {
      return FixedStringAttr.valueOf(exportView.OutWsStudyUnitCode, 7);
   }
 
   public String getOutWsStudyUnitEngLongDescription() {
      return FixedStringAttr.valueOf(exportView.OutWsStudyUnitEngLongDescription, 168);
   }
 
   public short getOutExamPaperDetailsExamYear() {
      return exportView.OutExamPaperDetailsExamYear;
   }
 
   public String getOutExamPaperDetailsMkStudyUnitCode() {
      return FixedStringAttr.valueOf(exportView.OutExamPaperDetailsMkStudyUnitCode, 7);
   }
 
   public short getOutExamPaperDetailsNr() {
      return exportView.OutExamPaperDetailsNr;
   }
 
   public short getOutExamPaperDetailsNrOfPages() {
      return exportView.OutExamPaperDetailsNrOfPages;
   }
 
   public short getOutExamPaperDetailsDurationHours() {
      return exportView.OutExamPaperDetailsDurationHours;
   }
 
   public short getOutExamPaperDetailsDurationMinutes() {
      return exportView.OutExamPaperDetailsDurationMinutes;
   }
 
   public short getOutExamPeriodDateMkExamPeriodCode() {
      return exportView.OutExamPeriodDateMkExamPeriodCode;
   }
 
   public String getOutExamPeriodDateMarkreadingCode() {
      return StringAttr.valueOf(exportView.OutExamPeriodDateMarkreadingCode);
   }
 
   public short getOutExamPeriodDateScriptMarkTot() {
      return exportView.OutExamPeriodDateScriptMarkTot;
   }
 
   public short getOutExamPeriodDateMarkreadTot() {
      return exportView.OutExamPeriodDateMarkreadTot;
   }
 
   public short getOutExamPeriodDatePaperMarkTot() {
      return exportView.OutExamPeriodDatePaperMarkTot;
   }
 
   public Calendar getOutExamPeriodDateDate() {
      return DateAttr.toCalendar(exportView.OutExamPeriodDateDate);
   }
   public int getAsIntOutExamPeriodDateDate() {
      return DateAttr.toInt(exportView.OutExamPeriodDateDate);
   }
 
   public Calendar getOutExamPeriodDateStartingTime() {
      return TimeAttr.toCalendar(exportView.OutExamPeriodDateStartingTime);
   }
   public int getAsIntOutExamPeriodDateStartingTime() {
      return TimeAttr.toInt(exportView.OutExamPeriodDateStartingTime);
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
 
   public String getOutCsfStringsString500() {
      return StringAttr.valueOf(exportView.OutCsfStringsString500);
   }
 
   public final int OutExaminersGroupMax = 30;
   public short getOutExaminersGroupCount() {
      return (short)(exportView.OutExaminersGroup_MA);
   };
 
   public String getOutGWsExaminerSurname(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsExaminerSurname[index], 28);
   }
 
   public String getOutGWsExaminerInitials(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsExaminerInitials[index], 10);
   }
 
   public String getOutGWsExaminerTitle(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsExaminerTitle[index], 10);
   }
 
   public String getOutGExaminerTypeCsfStringsString3(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGExaminerTypeCsfStringsString3[index], 3);
   }
 
   public String getOutGWsEducationalInstitutionEngName(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsEducationalInstitutionEngName[index], 28);
   }
 
   public final int OutEquivalentGroupMax = 20;
   public short getOutEquivalentGroupCount() {
      return (short)(exportView.OutEquivalentGroup_MA);
   };
 
   public String getOutGEquivalentWsExamEquivalentsWsEquivalentCode(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGEquivalentWsExamEquivalentsWsEquivalentCode[index], 7);
   }
 
   public int getOutGWsUniqueAssignmentUniqueNr(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return exportView.OutGWsUniqueAssignmentUniqueNr[index];
   }
 
   public Calendar getOutGEquivalentExamPeriodDateDate(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return DateAttr.toCalendar(exportView.OutGEquivalentExamPeriodDateDate[index]);
   }
   public int getAsIntOutGEquivalentExamPeriodDateDate(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return DateAttr.toInt(exportView.OutGEquivalentExamPeriodDateDate[index]);
   }
 
   public short getOutGEquivalentExamPeriodDateDurationDays(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return exportView.OutGEquivalentExamPeriodDateDurationDays[index];
   }
 
   public short getOutGEquivalentExamPeriodDateDurationHours(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return exportView.OutGEquivalentExamPeriodDateDurationHours[index];
   }
 
   public short getOutGEquivalentExamPeriodDateDurationMinutes(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return exportView.OutGEquivalentExamPeriodDateDurationMinutes[index];
   }
 
   public Calendar getOutGEquivalentExamPeriodDateStartingTime(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return TimeAttr.toCalendar(exportView.OutGEquivalentExamPeriodDateStartingTime[index]);
   }
   public int getAsIntOutGEquivalentExamPeriodDateStartingTime(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return TimeAttr.toInt(exportView.OutGEquivalentExamPeriodDateStartingTime[index]);
   }
 
   public final int OutExamStationaryGroupMax = 20;
   public short getOutExamStationaryGroupCount() {
      return (short)(exportView.OutExamStationaryGroup_MA);
   };
 
   public String getOutGExamStationeryItemCode(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGExamStationeryItemCode[index], 2);
   }
 
   public double getOutGExamPaperStationeryNeedsAveragePerStudent(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return exportView.OutGExamPaperStationeryNeedsAveragePerStudent[index];
   }
 
   public final int OutExamInstructionGroupMax = 25;
   public short getOutExamInstructionGroupCount() {
      return (short)(exportView.OutExamInstructionGroup_MA);
   };
 
   public short getOutGExamDateInstructionMkDocumentParagraphCode(int index) throws ArrayIndexOutOfBoundsException {
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      return exportView.OutGExamDateInstructionMkDocumentParagraphCode[index];
   }
 
   public String getOutGExamDateInstructionMkWidthType(int index) throws ArrayIndexOutOfBoundsException {
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGExamDateInstructionMkWidthType[index], 1);
   }
 
   public String getOutGExamDateInstructionMkContents(int index) throws ArrayIndexOutOfBoundsException {
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGExamDateInstructionMkContents[index], 2);
   }
 
   public final int OutPaperLanguageGroupMax = 10;
   public short getOutPaperLanguageGroupCount() {
      return (short)(exportView.OutPaperLanguageGroup_MA);
   };
 
   public String getOutGExamPaperLanguageLanguageGc40(int index) throws ArrayIndexOutOfBoundsException {
      if (9 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 9, not: " + index);
      }
      return StringAttr.valueOf(exportView.OutGExamPaperLanguageLanguageGc40[index]);
   }
 
   public final int OutAdditionalInstrGroupMax = 20;
   public short getOutAdditionalInstrGroupCount() {
      return (short)(exportView.OutAdditionalInstrGroup_MA);
   };
 
   public short getOutGExamPaperAddInstructionsInstructionNr(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return exportView.OutGExamPaperAddInstructionsInstructionNr[index];
   }
 
   public String getOutGExamPaperAddInstructionsInstruction(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return StringAttr.valueOf(exportView.OutGExamPaperAddInstructionsInstruction[index]);
   }
 
   public String getOutExamPaperLogActionGc39() {
      return StringAttr.valueOf(exportView.OutExamPaperLogActionGc39);
   }
 
   public String getOutExamPaperLogUpdatedBy() {
      return StringAttr.valueOf(exportView.OutExamPaperLogUpdatedBy);
   }
 
   public Calendar getOutExamPaperLogUpdatedOn() {
      return TimestampAttr.toCalendar(exportView.OutExamPaperLogUpdatedOn);
   }
   public String getAsStringOutExamPaperLogUpdatedOn() {
      return TimestampAttr.toString(exportView.OutExamPaperLogUpdatedOn);
   }
 
   public String getOutCoverDocketExistsFlagCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.OutCoverDocketExistsFlagCsfStringsString1, 1);
   }
 
   public int getOutExamPaperCoverDocketContactPersNr() {
      return exportView.OutExamPaperCoverDocketContactPersNr;
   }
 
   public String getOutExamPaperCoverDocketBookRequired() {
      return StringAttr.valueOf(exportView.OutExamPaperCoverDocketBookRequired);
   }
 
   public String getOutExamPaperCoverDocketProofReadRequest() {
      return StringAttr.valueOf(exportView.OutExamPaperCoverDocketProofReadRequest);
   }
 
   public String getOutExamPaperCoverDocketStatusGc41() {
      return StringAttr.valueOf(exportView.OutExamPaperCoverDocketStatusGc41);
   }
 
   public short getOutExamPaperCoverDocketTotalRackPages() {
      return exportView.OutExamPaperCoverDocketTotalRackPages;
   }
 
   public short getOutExamPaperCoverDocketTotalPages() {
      return exportView.OutExamPaperCoverDocketTotalPages;
   }
 
   public String getOutExamPaperCoverDocketAnnexurePages() {
      return StringAttr.valueOf(exportView.OutExamPaperCoverDocketAnnexurePages);
   }
 
   public String getOutExamPaperCoverDocketMemorandumIncl() {
      return StringAttr.valueOf(exportView.OutExamPaperCoverDocketMemorandumIncl);
   }
 
   public String getOutExamPaperCoverDocketDeclarationFlag() {
      return StringAttr.valueOf(exportView.OutExamPaperCoverDocketDeclarationFlag);
   }
 
   public String getOutExamPaperCoverDocketFillInPaperGc50() {
      return StringAttr.valueOf(exportView.OutExamPaperCoverDocketFillInPaperGc50);
   }
 
   public String getOutExamPaperCoverDocketOpenBookGc50() {
      return StringAttr.valueOf(exportView.OutExamPaperCoverDocketOpenBookGc50);
   }
 
   public String getOutExamPaperCoverDocketKeepPaperFlag() {
      return StringAttr.valueOf(exportView.OutExamPaperCoverDocketKeepPaperFlag);
   }
 
   public String getOutExamPaperCoverDocketCalcPermitFlag() {
      return StringAttr.valueOf(exportView.OutExamPaperCoverDocketCalcPermitFlag);
   }
 
   public String getOutExamPaperCoverDocketExaminerPrtGc107() {
      return FixedStringAttr.valueOf(exportView.OutExamPaperCoverDocketExaminerPrtGc107, 10);
   }
 
   public String getOutExamPaperCoverDocketMcqInstrFlag() {
      return FixedStringAttr.valueOf(exportView.OutExamPaperCoverDocketMcqInstrFlag, 1);
   }
 
   public String getOutExamPaperCoverDocketAttendRegFlag() {
      return FixedStringAttr.valueOf(exportView.OutExamPaperCoverDocketAttendRegFlag, 1);
   }
 
   public String getOutExamPaperCoverDocketPaperColour() {
      return FixedStringAttr.valueOf(exportView.OutExamPaperCoverDocketPaperColour, 10);
   }
 
   public short getOutExamPaperCoverDocketNrDocsSubmitted() {
      return exportView.OutExamPaperCoverDocketNrDocsSubmitted;
   }
 
   public String getOutWsStaffSurname() {
      return FixedStringAttr.valueOf(exportView.OutWsStaffSurname, 28);
   }
 
   public String getOutWsStaffInitials() {
      return FixedStringAttr.valueOf(exportView.OutWsStaffInitials, 10);
   }
 
   public String getOutWsStaffTitle() {
      return FixedStringAttr.valueOf(exportView.OutWsStaffTitle, 10);
   }
 
   public String getOutWsStaffEmailAddress() {
      return FixedStringAttr.valueOf(exportView.OutWsStaffEmailAddress, 60);
   }
 
   public String getOutWsStaffContactTelno() {
      return FixedStringAttr.valueOf(exportView.OutWsStaffContactTelno, 28);
   }
 
   public String getOutWsAddressV2HomeNumber() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressV2HomeNumber, 28);
   }
 
   public String getOutWsAddressV2WorkNumber() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressV2WorkNumber, 28);
   }
 
   public String getOutWsAddressV2CellNumber() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressV2CellNumber, 20);
   }
 
};
