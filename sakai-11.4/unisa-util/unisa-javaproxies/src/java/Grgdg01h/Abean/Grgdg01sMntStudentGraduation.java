package Grgdg01h.Abean;
 
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
public class Grgdg01sMntStudentGraduation  implements ActionListener, java.io.Serializable  {
 
   //  Use final String for the bean name
   public static final String BEANNAME = new String("Grgdg01sMntStudentGraduation");
 
   //  Constants for Asynchronous status
   public static final int PENDING = CoopFlow.DATA_NOT_READY;
   public static final int AVAILABLE = CoopFlow.DATA_READY;
   public static final int INVALID_ID = CoopFlow.INVALID_ID;
 
   private final SimpleDateFormat nativeTimestampFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
   private final SimpleDateFormat nativeTimeFormatter = new SimpleDateFormat("HHmmss");
   private final SimpleDateFormat nativeDateFormatter = new SimpleDateFormat("yyyyMMdd");
   private static DecimalFormat decimalFormatter;
 
   public Grgdg01sMntStudentGraduation() {
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
 
 
   private Grgdg01sMntStudentGraduationOperation oper = null;
 
   /**
    * Calls the transaction/procedure step on the server.
    *
    * @exception java.beans.PropertyVetoException
    * Final property checks can throw this exception.
    */
   public void execute()  throws PropertyVetoException {
      try  {
         if (oper == null) {
            oper = new Grgdg01sMntStudentGraduationOperation(this);
            addCompletionListener(new operListener(this));
            addExceptionListener(new operListener(this));
         }
 

 
         oper.doGrgdg01sMntStudentGraduationOperation();
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
      private Grgdg01sMntStudentGraduation rP;
      operListener(Grgdg01sMntStudentGraduation r)  {
         rP = r;
      }
      public void actionPerformed(ActionEvent aEvent)  {
         if (Trace.isTracing(Trace.MASK_APPLICATION))
         {
            Trace.record(Trace.MASK_APPLICATION, "Grgdg01sMntStudentGraduation", "Listener heard that Grgdg01sMntStudentGraduationOperation completed with " + 
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of completion of operation Grgdg01sMntStudentGraduation ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      synchronized (this)  {
         targets = (Vector) completionListeners.clone();
      }
      actionEvt = new ActionEvent(this, 0, "Completion.Grgdg01sMntStudentGraduation");
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of exception of operation Grgdg01sMntStudentGraduation ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      String failCommand = "Exception.Grgdg01sMntStudentGraduation";
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
      importView.InStudentGraduationDetailPresentFlag = FixedStringAttr.valueOf("N", 1);
      importView.InStudentGraduationDetailCollectFlag = FixedStringAttr.valueOf("N", 1);
      importView.InStudentGraduationDetailGownSize = ShortAttr.valueOf((short)0);
      importView.InStudentGraduationDetailCapSize = ShortAttr.valueOf((short)0);
      importView.InStudentGraduationDetailPurchaseFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutGroup_MA = IntAttr.getDefaultValue();
      for( int index = 0; index < 5; index++)
         exportView.OutGStudentGraduationDetailPresentFlag[index] = FixedStringAttr.valueOf("N", 1);
      for( int index = 0; index < 5; index++)
         exportView.OutGStudentGraduationDetailCollectFlag[index] = FixedStringAttr.valueOf("N", 1);
      for( int index = 0; index < 5; index++)
         exportView.OutGStudentGraduationDetailGownSize[index] = ShortAttr.valueOf((short)0);
      for( int index = 0; index < 5; index++)
         exportView.OutGStudentGraduationDetailCapSize[index] = ShortAttr.valueOf((short)0);
      for( int index = 0; index < 5; index++)
         exportView.OutGStudentGraduationDetailPurchaseFlag[index] = FixedStringAttr.valueOf("N", 1);
      for( int index = 0; index < 5; index++)
         exportView.OutGWsQualificationType[index] = FixedStringAttr.valueOf("G", 1);
      for( int index = 0; index < 5; index++)
         exportView.OutGWsQualificationInUseFlag[index] = FixedStringAttr.valueOf("Y", 1);
      exportView.LclTestStudentGraduationDetailPresentFlag = FixedStringAttr.valueOf("N", 1);
      exportView.LclTestStudentGraduationDetailCollectFlag = FixedStringAttr.valueOf("N", 1);
      exportView.LclTestStudentGraduationDetailGownSize = ShortAttr.valueOf((short)0);
      exportView.LclTestStudentGraduationDetailCapSize = ShortAttr.valueOf((short)0);
      exportView.LclTestStudentGraduationDetailPurchaseFlag = FixedStringAttr.valueOf("N", 1);
      exportView.LclOutStudentAcademicRecordDistinctionFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsQualificationType = FixedStringAttr.valueOf("G", 1);
      exportView.OutWsQualificationInUseFlag = FixedStringAttr.valueOf("Y", 1);
      exportView.OutWsStudentSpecialCharacterFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsStudentGender = FixedStringAttr.valueOf("U", 1);
      exportView.OutWsStudentDeceasedFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsStudentLibraryBlackList = ShortAttr.valueOf((short)0);
      exportView.OutWsStudentExamFeesDebtFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsStudentDisciplinaryIncident = ShortAttr.valueOf((short)0);
      exportView.OutWsStudentPostGraduateStudiesApproved = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsStudentPhasedOutStatus = ShortAttr.valueOf((short)0);
      exportView.OutWsStudentFinancialBlockFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsStudentTwinFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsStudentAccessRestrictedFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsStudentNumberRestrictedFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsStudentPreviouslyPostGraduateFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsStudentPreviouslyUnisaPostGradFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsStudentResultBlockFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutStudentGraduationDetailPresentFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutStudentGraduationDetailCollectFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutStudentGraduationDetailGownSize = ShortAttr.valueOf((short)0);
      exportView.OutStudentGraduationDetailCapSize = ShortAttr.valueOf((short)0);
      exportView.OutStudentGraduationDetailPurchaseFlag = FixedStringAttr.valueOf("N", 1);
   }

   Grgdg01h.GRGDG01S_IA importView = Grgdg01h.GRGDG01S_IA.getInstance();
   Grgdg01h.GRGDG01S_OA exportView = Grgdg01h.GRGDG01S_OA.getInstance();
   public String getInOverrideIefSuppliedFlag() {
      return FixedStringAttr.valueOf(importView.InOverrideIefSuppliedFlag, 1);
   }
   public void setInOverrideIefSuppliedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Grgdg01sMntStudentGraduationIefSuppliedFlagPropertyEditor pe = new Grgdg01sMntStudentGraduationIefSuppliedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InOverrideIefSuppliedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InOverrideIefSuppliedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InOverrideIefSuppliedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InOverrideIefSuppliedFlag", null, null));
      }
      importView.InOverrideIefSuppliedFlag = FixedStringAttr.valueOf(s, (short)1);
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
 
   public String getInWsQualificationEngDescription() {
      return FixedStringAttr.valueOf(importView.InWsQualificationEngDescription, 28);
   }
   public void setInWsQualificationEngDescription(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsQualificationEngDescription must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsQualificationEngDescription", null, null));
      }
      importView.InWsQualificationEngDescription = FixedStringAttr.valueOf(s, (short)28);
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
 
   public int getInStudentGraduationDetailMkStudentNr() {
      return importView.InStudentGraduationDetailMkStudentNr;
   }
   public void setInStudentGraduationDetailMkStudentNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InStudentGraduationDetailMkStudentNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InStudentGraduationDetailMkStudentNr", null, null));
      }
      importView.InStudentGraduationDetailMkStudentNr = IntAttr.valueOf(s);
   }
   public void setAsStringInStudentGraduationDetailMkStudentNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentGraduationDetailMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentGraduationDetailMkStudentNr", null, null));
      }
      try {
          setInStudentGraduationDetailMkStudentNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentGraduationDetailMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentGraduationDetailMkStudentNr", null, null));
      }
   }
 
   public String getInStudentGraduationDetailMkQualificationCode() {
      return FixedStringAttr.valueOf(importView.InStudentGraduationDetailMkQualificationCode, 5);
   }
   public void setInStudentGraduationDetailMkQualificationCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InStudentGraduationDetailMkQualificationCode must be <= 5 characters.",
               new PropertyChangeEvent (this, "InStudentGraduationDetailMkQualificationCode", null, null));
      }
      importView.InStudentGraduationDetailMkQualificationCode = FixedStringAttr.valueOf(s, (short)5);
   }
 
   public Calendar getInStudentGraduationDetailDateAltered() {
      return DateAttr.toCalendar(importView.InStudentGraduationDetailDateAltered);
   }
   public int getAsIntInStudentGraduationDetailDateAltered() {
      return DateAttr.toInt(importView.InStudentGraduationDetailDateAltered);
   }
   public void setInStudentGraduationDetailDateAltered(Calendar s)
      throws PropertyVetoException {
      importView.InStudentGraduationDetailDateAltered = DateAttr.valueOf(s);
   }
   public void setAsStringInStudentGraduationDetailDateAltered(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInStudentGraduationDetailDateAltered((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInStudentGraduationDetailDateAltered(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InStudentGraduationDetailDateAltered has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InStudentGraduationDetailDateAltered", null, null));
         }
      }
   }
   public void setAsIntInStudentGraduationDetailDateAltered(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInStudentGraduationDetailDateAltered(temp);
   }
 
   public short getInStudentGraduationDetailMkGraduationCeremonyCode() {
      return importView.InStudentGraduationDetailMkGraduationCeremonyCode;
   }
   public void setInStudentGraduationDetailMkGraduationCeremonyCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InStudentGraduationDetailMkGraduationCeremonyCode has more than 3 digits.",
               new PropertyChangeEvent (this, "InStudentGraduationDetailMkGraduationCeremonyCode", null, null));
      }
      importView.InStudentGraduationDetailMkGraduationCeremonyCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentGraduationDetailMkGraduationCeremonyCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentGraduationDetailMkGraduationCeremonyCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentGraduationDetailMkGraduationCeremonyCode", null, null));
      }
      try {
          setInStudentGraduationDetailMkGraduationCeremonyCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentGraduationDetailMkGraduationCeremonyCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentGraduationDetailMkGraduationCeremonyCode", null, null));
      }
   }
 
   public String getInStudentGraduationDetailLanguage() {
      return FixedStringAttr.valueOf(importView.InStudentGraduationDetailLanguage, 1);
   }
   public void setInStudentGraduationDetailLanguage(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Grgdg01sMntStudentGraduationStudentGraduationDetailLanguagePropertyEditor pe = new Grgdg01sMntStudentGraduationStudentGraduationDetailLanguagePropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InStudentGraduationDetailLanguage value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InStudentGraduationDetailLanguage", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InStudentGraduationDetailLanguage must be <= 1 characters.",
               new PropertyChangeEvent (this, "InStudentGraduationDetailLanguage", null, null));
      }
      importView.InStudentGraduationDetailLanguage = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInStudentGraduationDetailSeatNo() {
      return FixedStringAttr.valueOf(importView.InStudentGraduationDetailSeatNo, 4);
   }
   public void setInStudentGraduationDetailSeatNo(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 4) {
         throw new PropertyVetoException("InStudentGraduationDetailSeatNo must be <= 4 characters.",
               new PropertyChangeEvent (this, "InStudentGraduationDetailSeatNo", null, null));
      }
      importView.InStudentGraduationDetailSeatNo = FixedStringAttr.valueOf(s, (short)4);
   }
 
   public short getInStudentGraduationDetailNoOfGuests() {
      return importView.InStudentGraduationDetailNoOfGuests;
   }
   public void setInStudentGraduationDetailNoOfGuests(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InStudentGraduationDetailNoOfGuests has more than 2 digits.",
               new PropertyChangeEvent (this, "InStudentGraduationDetailNoOfGuests", null, null));
      }
      importView.InStudentGraduationDetailNoOfGuests = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentGraduationDetailNoOfGuests(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentGraduationDetailNoOfGuests is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentGraduationDetailNoOfGuests", null, null));
      }
      try {
          setInStudentGraduationDetailNoOfGuests(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentGraduationDetailNoOfGuests is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentGraduationDetailNoOfGuests", null, null));
      }
   }
 
   public String getInStudentGraduationDetailPresentFlag() {
      return FixedStringAttr.valueOf(importView.InStudentGraduationDetailPresentFlag, 1);
   }
   public void setInStudentGraduationDetailPresentFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Grgdg01sMntStudentGraduationStudentGraduationDetailPresentFlagPropertyEditor pe = new Grgdg01sMntStudentGraduationStudentGraduationDetailPresentFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InStudentGraduationDetailPresentFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InStudentGraduationDetailPresentFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InStudentGraduationDetailPresentFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InStudentGraduationDetailPresentFlag", null, null));
      }
      importView.InStudentGraduationDetailPresentFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInStudentGraduationDetailCollectFlag() {
      return FixedStringAttr.valueOf(importView.InStudentGraduationDetailCollectFlag, 1);
   }
   public void setInStudentGraduationDetailCollectFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Grgdg01sMntStudentGraduationStudentGraduationDetailCollectFlagPropertyEditor pe = new Grgdg01sMntStudentGraduationStudentGraduationDetailCollectFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InStudentGraduationDetailCollectFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InStudentGraduationDetailCollectFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InStudentGraduationDetailCollectFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InStudentGraduationDetailCollectFlag", null, null));
      }
      importView.InStudentGraduationDetailCollectFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInStudentGraduationDetailGownSize() {
      return importView.InStudentGraduationDetailGownSize;
   }
   public void setInStudentGraduationDetailGownSize(short s)
      throws PropertyVetoException {
      Grgdg01sMntStudentGraduationStudentGraduationDetailGownSizePropertyEditor pe = new Grgdg01sMntStudentGraduationStudentGraduationDetailGownSizePropertyEditor();
      if (pe.checkTag(String.valueOf(s)) == false) {
         throw new PropertyVetoException("InStudentGraduationDetailGownSize value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InStudentGraduationDetailGownSize", null, null));
      }
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InStudentGraduationDetailGownSize has more than 2 digits.",
               new PropertyChangeEvent (this, "InStudentGraduationDetailGownSize", null, null));
      }
      importView.InStudentGraduationDetailGownSize = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentGraduationDetailGownSize(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentGraduationDetailGownSize is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentGraduationDetailGownSize", null, null));
      }
      try {
          setInStudentGraduationDetailGownSize(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentGraduationDetailGownSize is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentGraduationDetailGownSize", null, null));
      }
   }
 
   public short getInStudentGraduationDetailCapSize() {
      return importView.InStudentGraduationDetailCapSize;
   }
   public void setInStudentGraduationDetailCapSize(short s)
      throws PropertyVetoException {
      Grgdg01sMntStudentGraduationStudentGraduationDetailCapSizePropertyEditor pe = new Grgdg01sMntStudentGraduationStudentGraduationDetailCapSizePropertyEditor();
      if (pe.checkTag(String.valueOf(s)) == false) {
         throw new PropertyVetoException("InStudentGraduationDetailCapSize value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InStudentGraduationDetailCapSize", null, null));
      }
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InStudentGraduationDetailCapSize has more than 2 digits.",
               new PropertyChangeEvent (this, "InStudentGraduationDetailCapSize", null, null));
      }
      importView.InStudentGraduationDetailCapSize = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentGraduationDetailCapSize(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentGraduationDetailCapSize is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentGraduationDetailCapSize", null, null));
      }
      try {
          setInStudentGraduationDetailCapSize(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentGraduationDetailCapSize is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentGraduationDetailCapSize", null, null));
      }
   }
 
   public String getInStudentGraduationDetailPreviousDegree() {
      return FixedStringAttr.valueOf(importView.InStudentGraduationDetailPreviousDegree, 7);
   }
   public void setInStudentGraduationDetailPreviousDegree(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InStudentGraduationDetailPreviousDegree must be <= 7 characters.",
               new PropertyChangeEvent (this, "InStudentGraduationDetailPreviousDegree", null, null));
      }
      importView.InStudentGraduationDetailPreviousDegree = FixedStringAttr.valueOf(s, (short)7);
   }
 
   public String getInStudentGraduationDetailPurchaseFlag() {
      return FixedStringAttr.valueOf(importView.InStudentGraduationDetailPurchaseFlag, 1);
   }
   public void setInStudentGraduationDetailPurchaseFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Grgdg01sMntStudentGraduationStudentGraduationDetailPurchaseFlagPropertyEditor pe = new Grgdg01sMntStudentGraduationStudentGraduationDetailPurchaseFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InStudentGraduationDetailPurchaseFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InStudentGraduationDetailPurchaseFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InStudentGraduationDetailPurchaseFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InStudentGraduationDetailPurchaseFlag", null, null));
      }
      importView.InStudentGraduationDetailPurchaseFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public double getInStudentGraduationDetailAmount() {
      return importView.InStudentGraduationDetailAmount;
   }
   public void setInStudentGraduationDetailAmount(double s)
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
         throw new PropertyVetoException("InStudentGraduationDetailAmount has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InStudentGraduationDetailAmount", null, null));
      }
      if (java.lang.Math.abs(s) >= 1000000000.0) {
         throw new PropertyVetoException("InStudentGraduationDetailAmount has more than 9 integral digits.",
               new PropertyChangeEvent (this, "InStudentGraduationDetailAmount", null, null));
      }
      importView.InStudentGraduationDetailAmount = DoubleAttr.valueOf(s);
   }
   public void setAsStringInStudentGraduationDetailAmount(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentGraduationDetailAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentGraduationDetailAmount", null, null));
      }
      try {
          setInStudentGraduationDetailAmount(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentGraduationDetailAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentGraduationDetailAmount", null, null));
      }
   }
 
   public Calendar getInStudentGraduationDetailBundleDate() {
      return DateAttr.toCalendar(importView.InStudentGraduationDetailBundleDate);
   }
   public int getAsIntInStudentGraduationDetailBundleDate() {
      return DateAttr.toInt(importView.InStudentGraduationDetailBundleDate);
   }
   public void setInStudentGraduationDetailBundleDate(Calendar s)
      throws PropertyVetoException {
      importView.InStudentGraduationDetailBundleDate = DateAttr.valueOf(s);
   }
   public void setAsStringInStudentGraduationDetailBundleDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInStudentGraduationDetailBundleDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInStudentGraduationDetailBundleDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InStudentGraduationDetailBundleDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InStudentGraduationDetailBundleDate", null, null));
         }
      }
   }
   public void setAsIntInStudentGraduationDetailBundleDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInStudentGraduationDetailBundleDate(temp);
   }
 
   public short getInStudentGraduationDetailBundleNr() {
      return importView.InStudentGraduationDetailBundleNr;
   }
   public void setInStudentGraduationDetailBundleNr(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InStudentGraduationDetailBundleNr has more than 4 digits.",
               new PropertyChangeEvent (this, "InStudentGraduationDetailBundleNr", null, null));
      }
      importView.InStudentGraduationDetailBundleNr = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentGraduationDetailBundleNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentGraduationDetailBundleNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentGraduationDetailBundleNr", null, null));
      }
      try {
          setInStudentGraduationDetailBundleNr(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentGraduationDetailBundleNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentGraduationDetailBundleNr", null, null));
      }
   }
 
   public short getInStudentGraduationDetailBundleDoc() {
      return importView.InStudentGraduationDetailBundleDoc;
   }
   public void setInStudentGraduationDetailBundleDoc(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InStudentGraduationDetailBundleDoc has more than 3 digits.",
               new PropertyChangeEvent (this, "InStudentGraduationDetailBundleDoc", null, null));
      }
      importView.InStudentGraduationDetailBundleDoc = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentGraduationDetailBundleDoc(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentGraduationDetailBundleDoc is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentGraduationDetailBundleDoc", null, null));
      }
      try {
          setInStudentGraduationDetailBundleDoc(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentGraduationDetailBundleDoc is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentGraduationDetailBundleDoc", null, null));
      }
   }
 
   public String getInStudentGraduationDetailTransferBlockedFlag() {
      return StringAttr.valueOf(importView.InStudentGraduationDetailTransferBlockedFlag);
   }
   public void setInStudentGraduationDetailTransferBlockedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InStudentGraduationDetailTransferBlockedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InStudentGraduationDetailTransferBlockedFlag", null, null));
      }
      importView.InStudentGraduationDetailTransferBlockedFlag = StringAttr.valueOf(s, (short)1);
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
 
   public String getInWsGraduationCeremonyCentreV2EngDescription() {
      return FixedStringAttr.valueOf(importView.InWsGraduationCeremonyCentreV2EngDescription, 28);
   }
   public void setInWsGraduationCeremonyCentreV2EngDescription(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsGraduationCeremonyCentreV2EngDescription must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsGraduationCeremonyCentreV2EngDescription", null, null));
      }
      importView.InWsGraduationCeremonyCentreV2EngDescription = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInCellPhoneNumberWsAddressV2CellNumber() {
      return FixedStringAttr.valueOf(importView.InCellPhoneNumberWsAddressV2CellNumber, 20);
   }
   public void setInCellPhoneNumberWsAddressV2CellNumber(String s)
      throws PropertyVetoException {
      if (s.length() > 20) {
         throw new PropertyVetoException("InCellPhoneNumberWsAddressV2CellNumber must be <= 20 characters.",
               new PropertyChangeEvent (this, "InCellPhoneNumberWsAddressV2CellNumber", null, null));
      }
      importView.InCellPhoneNumberWsAddressV2CellNumber = FixedStringAttr.valueOf(s, (short)20);
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
 
   public String getInFaxNumberCsfStringsString132() {
      return FixedStringAttr.valueOf(importView.InFaxNumberCsfStringsString132, 132);
   }
   public void setInFaxNumberCsfStringsString132(String s)
      throws PropertyVetoException {
      if (s.length() > 132) {
         throw new PropertyVetoException("InFaxNumberCsfStringsString132 must be <= 132 characters.",
               new PropertyChangeEvent (this, "InFaxNumberCsfStringsString132", null, null));
      }
      importView.InFaxNumberCsfStringsString132 = FixedStringAttr.valueOf(s, (short)132);
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
 
   public String getInSmsMessageIefSuppliedFlag() {
      return FixedStringAttr.valueOf(importView.InSmsMessageIefSuppliedFlag, 1);
   }
   public void setInSmsMessageIefSuppliedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Grgdg01sMntStudentGraduationIefSuppliedFlagPropertyEditor pe = new Grgdg01sMntStudentGraduationIefSuppliedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InSmsMessageIefSuppliedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InSmsMessageIefSuppliedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InSmsMessageIefSuppliedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InSmsMessageIefSuppliedFlag", null, null));
      }
      importView.InSmsMessageIefSuppliedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getOutOverrideIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutOverrideIefSuppliedFlag, 1);
   }
 
   public final int OutGroupMax = 5;
   public short getOutGroupCount() {
      return (short)(exportView.OutGroup_MA);
   };
 
   public String getOutGCsfStringsString50(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGCsfStringsString50[index], 50);
   }
 
   public String getOutGCsfEventCommunicationsRgvExistingLineSelectedFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGCsfEventCommunicationsRgvExistingLineSelectedFlag[index], 1);
   }
 
   public String getOutGWsGraduationCeremonyCentreV2EngDescription(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsGraduationCeremonyCentreV2EngDescription[index], 28);
   }
 
   public int getOutGStudentGraduationDetailMkStudentNr(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return exportView.OutGStudentGraduationDetailMkStudentNr[index];
   }
 
   public String getOutGStudentGraduationDetailMkQualificationCode(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGStudentGraduationDetailMkQualificationCode[index], 5);
   }
 
   public Calendar getOutGStudentGraduationDetailDateAltered(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toCalendar(exportView.OutGStudentGraduationDetailDateAltered[index]);
   }
   public int getAsIntOutGStudentGraduationDetailDateAltered(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toInt(exportView.OutGStudentGraduationDetailDateAltered[index]);
   }
 
   public short getOutGStudentGraduationDetailMkGraduationCeremonyCode(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return exportView.OutGStudentGraduationDetailMkGraduationCeremonyCode[index];
   }
 
   public String getOutGStudentGraduationDetailLanguage(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGStudentGraduationDetailLanguage[index], 1);
   }
 
   public String getOutGStudentGraduationDetailSeatNo(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGStudentGraduationDetailSeatNo[index], 4);
   }
 
   public short getOutGStudentGraduationDetailNoOfGuests(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return exportView.OutGStudentGraduationDetailNoOfGuests[index];
   }
 
   public String getOutGStudentGraduationDetailPresentFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGStudentGraduationDetailPresentFlag[index], 1);
   }
 
   public String getOutGStudentGraduationDetailCollectFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGStudentGraduationDetailCollectFlag[index], 1);
   }
 
   public short getOutGStudentGraduationDetailGownSize(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return exportView.OutGStudentGraduationDetailGownSize[index];
   }
 
   public short getOutGStudentGraduationDetailCapSize(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return exportView.OutGStudentGraduationDetailCapSize[index];
   }
 
   public String getOutGStudentGraduationDetailPreviousDegree(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGStudentGraduationDetailPreviousDegree[index], 7);
   }
 
   public String getOutGStudentGraduationDetailPurchaseFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGStudentGraduationDetailPurchaseFlag[index], 1);
   }
 
   public double getOutGStudentGraduationDetailAmount(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return exportView.OutGStudentGraduationDetailAmount[index];
   }
 
   public Calendar getOutGStudentGraduationDetailBundleDate(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toCalendar(exportView.OutGStudentGraduationDetailBundleDate[index]);
   }
   public int getAsIntOutGStudentGraduationDetailBundleDate(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toInt(exportView.OutGStudentGraduationDetailBundleDate[index]);
   }
 
   public short getOutGStudentGraduationDetailBundleNr(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return exportView.OutGStudentGraduationDetailBundleNr[index];
   }
 
   public short getOutGStudentGraduationDetailBundleDoc(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return exportView.OutGStudentGraduationDetailBundleDoc[index];
   }
 
   public String getOutGStudentGraduationDetailTransferBlockedFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return StringAttr.valueOf(exportView.OutGStudentGraduationDetailTransferBlockedFlag[index]);
   }
 
   public String getOutGWsQualificationEngDescription(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsQualificationEngDescription[index], 28);
   }
 
   public String getOutGWsQualificationCode(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsQualificationCode[index], 5);
   }
 
   public String getOutGWsQualificationType(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsQualificationType[index], 1);
   }
 
   public String getOutGWsQualificationInUseFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsQualificationInUseFlag[index], 1);
   }
 
   public short getOutGWsQualificationMkFacultyCode(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return exportView.OutGWsQualificationMkFacultyCode[index];
   }
 
   public short getOutGWsQualificationMkDepartmentCode(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return exportView.OutGWsQualificationMkDepartmentCode[index];
   }
 
   public String getOutGWsQualificationShortDescription(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsQualificationShortDescription[index], 12);
   }
 
   public String getOutGWsQualificationAfrDescription(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsQualificationAfrDescription[index], 28);
   }
 
   public String getOutGWsQualificationLongEngDescription(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsQualificationLongEngDescription[index], 120);
   }
 
   public String getOutGWsQualificationLongAfrDescription(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsQualificationLongAfrDescription[index], 120);
   }
 
   public int getOutGWsQualificationNumberRegistered(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return exportView.OutGWsQualificationNumberRegistered[index];
   }
 
   public int getOutGWsQualificationNumberCancelled(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return exportView.OutGWsQualificationNumberCancelled[index];
   }
 
   public String getOutGWsQualificationContactPhoneNo1(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsQualificationContactPhoneNo1[index], 20);
   }
 
   public String getOutGWsQualificationContactPhoneNo2(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsQualificationContactPhoneNo2[index], 20);
   }
 
   public double getOutGWsQualificationMinimumTime(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return exportView.OutGWsQualificationMinimumTime[index];
   }
 
   public short getLclOutWsGraduationCeremonyCode() {
      return exportView.LclOutWsGraduationCeremonyCode;
   }
 
   public String getLclOutWsGraduationCeremonyContactPhoneNo1() {
      return FixedStringAttr.valueOf(exportView.LclOutWsGraduationCeremonyContactPhoneNo1, 20);
   }
 
   public String getLclOutWsGraduationCeremonyContactPhoneNo2() {
      return FixedStringAttr.valueOf(exportView.LclOutWsGraduationCeremonyContactPhoneNo2, 20);
   }
 
   public Calendar getLclOutWsGraduationCeremonyDate() {
      return DateAttr.toCalendar(exportView.LclOutWsGraduationCeremonyDate);
   }
   public int getAsIntLclOutWsGraduationCeremonyDate() {
      return DateAttr.toInt(exportView.LclOutWsGraduationCeremonyDate);
   }
 
   public short getLclOutWsGraduationCeremonyNoOfStudentsAbsentia() {
      return exportView.LclOutWsGraduationCeremonyNoOfStudentsAbsentia;
   }
 
   public short getLclOutWsGraduationCeremonyNoOfStudentsAttending() {
      return exportView.LclOutWsGraduationCeremonyNoOfStudentsAttending;
   }
 
   public Calendar getLclOutWsGraduationCeremonyStartingTime() {
      return TimeAttr.toCalendar(exportView.LclOutWsGraduationCeremonyStartingTime);
   }
   public int getAsIntLclOutWsGraduationCeremonyStartingTime() {
      return TimeAttr.toInt(exportView.LclOutWsGraduationCeremonyStartingTime);
   }
 
   public short getLclOutWsGraduationCeremonyNoOutstanding() {
      return exportView.LclOutWsGraduationCeremonyNoOutstanding;
   }
 
   public short getLclOutWsGraduationCeremonyNo12YrAbsent() {
      return exportView.LclOutWsGraduationCeremonyNo12YrAbsent;
   }
 
   public int getLclTestStudentGraduationDetailMkStudentNr() {
      return exportView.LclTestStudentGraduationDetailMkStudentNr;
   }
 
   public String getLclTestStudentGraduationDetailMkQualificationCode() {
      return FixedStringAttr.valueOf(exportView.LclTestStudentGraduationDetailMkQualificationCode, 5);
   }
 
   public Calendar getLclTestStudentGraduationDetailDateAltered() {
      return DateAttr.toCalendar(exportView.LclTestStudentGraduationDetailDateAltered);
   }
   public int getAsIntLclTestStudentGraduationDetailDateAltered() {
      return DateAttr.toInt(exportView.LclTestStudentGraduationDetailDateAltered);
   }
 
   public short getLclTestStudentGraduationDetailMkGraduationCeremonyCode() {
      return exportView.LclTestStudentGraduationDetailMkGraduationCeremonyCode;
   }
 
   public String getLclTestStudentGraduationDetailLanguage() {
      return FixedStringAttr.valueOf(exportView.LclTestStudentGraduationDetailLanguage, 1);
   }
 
   public String getLclTestStudentGraduationDetailSeatNo() {
      return FixedStringAttr.valueOf(exportView.LclTestStudentGraduationDetailSeatNo, 4);
   }
 
   public short getLclTestStudentGraduationDetailNoOfGuests() {
      return exportView.LclTestStudentGraduationDetailNoOfGuests;
   }
 
   public String getLclTestStudentGraduationDetailPresentFlag() {
      return FixedStringAttr.valueOf(exportView.LclTestStudentGraduationDetailPresentFlag, 1);
   }
 
   public String getLclTestStudentGraduationDetailCollectFlag() {
      return FixedStringAttr.valueOf(exportView.LclTestStudentGraduationDetailCollectFlag, 1);
   }
 
   public short getLclTestStudentGraduationDetailGownSize() {
      return exportView.LclTestStudentGraduationDetailGownSize;
   }
 
   public short getLclTestStudentGraduationDetailCapSize() {
      return exportView.LclTestStudentGraduationDetailCapSize;
   }
 
   public String getLclTestStudentGraduationDetailPreviousDegree() {
      return FixedStringAttr.valueOf(exportView.LclTestStudentGraduationDetailPreviousDegree, 7);
   }
 
   public String getLclTestStudentGraduationDetailPurchaseFlag() {
      return FixedStringAttr.valueOf(exportView.LclTestStudentGraduationDetailPurchaseFlag, 1);
   }
 
   public double getLclTestStudentGraduationDetailAmount() {
      return exportView.LclTestStudentGraduationDetailAmount;
   }
 
   public Calendar getLclTestStudentGraduationDetailBundleDate() {
      return DateAttr.toCalendar(exportView.LclTestStudentGraduationDetailBundleDate);
   }
   public int getAsIntLclTestStudentGraduationDetailBundleDate() {
      return DateAttr.toInt(exportView.LclTestStudentGraduationDetailBundleDate);
   }
 
   public short getLclTestStudentGraduationDetailBundleNr() {
      return exportView.LclTestStudentGraduationDetailBundleNr;
   }
 
   public short getLclTestStudentGraduationDetailBundleDoc() {
      return exportView.LclTestStudentGraduationDetailBundleDoc;
   }
 
   public String getLclTestStudentGraduationDetailTransferBlockedFlag() {
      return StringAttr.valueOf(exportView.LclTestStudentGraduationDetailTransferBlockedFlag);
   }
 
   public String getLclOutWizfuncReportingControlPrinterCode() {
      return StringAttr.valueOf(exportView.LclOutWizfuncReportingControlPrinterCode);
   }
 
   public String getLclOutCsfStringsString500() {
      return StringAttr.valueOf(exportView.LclOutCsfStringsString500);
   }
 
   public int getLclOutStudentAcademicRecordMkStudentNr() {
      return exportView.LclOutStudentAcademicRecordMkStudentNr;
   }
 
   public String getLclOutStudentAcademicRecordMkQualificationCode() {
      return FixedStringAttr.valueOf(exportView.LclOutStudentAcademicRecordMkQualificationCode, 5);
   }
 
   public short getLclOutStudentAcademicRecordLastAcademicRegistrationYear() {
      return exportView.LclOutStudentAcademicRecordLastAcademicRegistrationYear;
   }
 
   public short getLclOutStudentAcademicRecordActualCompletionYear() {
      return exportView.LclOutStudentAcademicRecordActualCompletionYear;
   }
 
   public short getLclOutStudentAcademicRecordMkGraduationCeremonyCode() {
      return exportView.LclOutStudentAcademicRecordMkGraduationCeremonyCode;
   }
 
   public Calendar getLclOutStudentAcademicRecordGraduationCeremonyDate() {
      return DateAttr.toCalendar(exportView.LclOutStudentAcademicRecordGraduationCeremonyDate);
   }
   public int getAsIntLclOutStudentAcademicRecordGraduationCeremonyDate() {
      return DateAttr.toInt(exportView.LclOutStudentAcademicRecordGraduationCeremonyDate);
   }
 
   public Calendar getLclOutStudentAcademicRecordLastRegistrationDate() {
      return DateAttr.toCalendar(exportView.LclOutStudentAcademicRecordLastRegistrationDate);
   }
   public int getAsIntLclOutStudentAcademicRecordLastRegistrationDate() {
      return DateAttr.toInt(exportView.LclOutStudentAcademicRecordLastRegistrationDate);
   }
 
   public Calendar getLclOutStudentAcademicRecordFirstRegistrationDate() {
      return DateAttr.toCalendar(exportView.LclOutStudentAcademicRecordFirstRegistrationDate);
   }
   public int getAsIntLclOutStudentAcademicRecordFirstRegistrationDate() {
      return DateAttr.toInt(exportView.LclOutStudentAcademicRecordFirstRegistrationDate);
   }
 
   public Calendar getLclOutStudentAcademicRecordLastExamDate() {
      return DateAttr.toCalendar(exportView.LclOutStudentAcademicRecordLastExamDate);
   }
   public int getAsIntLclOutStudentAcademicRecordLastExamDate() {
      return DateAttr.toInt(exportView.LclOutStudentAcademicRecordLastExamDate);
   }
 
   public String getLclOutStudentAcademicRecordStatusCode() {
      return FixedStringAttr.valueOf(exportView.LclOutStudentAcademicRecordStatusCode, 2);
   }
 
   public String getLclOutStudentAcademicRecordTemporaryFlag() {
      return FixedStringAttr.valueOf(exportView.LclOutStudentAcademicRecordTemporaryFlag, 1);
   }
 
   public String getLclOutStudentAcademicRecordTemporaryStatusCode() {
      return FixedStringAttr.valueOf(exportView.LclOutStudentAcademicRecordTemporaryStatusCode, 2);
   }
 
   public String getLclOutStudentAcademicRecordDistinctionFlag() {
      return FixedStringAttr.valueOf(exportView.LclOutStudentAcademicRecordDistinctionFlag, 1);
   }
 
   public String getLclOutStudentAcademicRecordComment1() {
      return FixedStringAttr.valueOf(exportView.LclOutStudentAcademicRecordComment1, 50);
   }
 
   public String getLclOutStudentAcademicRecordComment2() {
      return FixedStringAttr.valueOf(exportView.LclOutStudentAcademicRecordComment2, 50);
   }
 
   public int getLclOutStudentAcademicRecordLastUserCode() {
      return exportView.LclOutStudentAcademicRecordLastUserCode;
   }
 
   public short getLclOutStudentAcademicRecordFinalMarkControlTotal() {
      return exportView.LclOutStudentAcademicRecordFinalMarkControlTotal;
   }
 
   public short getLclOutStudentAcademicRecordTotalCreditsControlTotal() {
      return exportView.LclOutStudentAcademicRecordTotalCreditsControlTotal;
   }
 
   public short getLclOutStudentAcademicRecordAveragePercent() {
      return exportView.LclOutStudentAcademicRecordAveragePercent;
   }
 
   public String getLclOutStudentAcademicRecordAdmissionRequirements() {
      return FixedStringAttr.valueOf(exportView.LclOutStudentAcademicRecordAdmissionRequirements, 50);
   }
 
   public String getLclOutStudentAcademicRecordOtherAdmissionRequirements() {
      return FixedStringAttr.valueOf(exportView.LclOutStudentAcademicRecordOtherAdmissionRequirements, 50);
   }
 
   public short getLclOutStudentAcademicRecordWeeksExperience() {
      return exportView.LclOutStudentAcademicRecordWeeksExperience;
   }
 
   public String getLclOutStudentAcademicRecordPrevQualRegistrationFlag() {
      return FixedStringAttr.valueOf(exportView.LclOutStudentAcademicRecordPrevQualRegistrationFlag, 1);
   }
 
   public short getLclOutStudentAcademicRecordEarliestExemptionUnisaYear() {
      return exportView.LclOutStudentAcademicRecordEarliestExemptionUnisaYear;
   }
 
   public short getLclOutStudentAcademicRecordEarliestExemptionOtherYear() {
      return exportView.LclOutStudentAcademicRecordEarliestExemptionOtherYear;
   }
 
   public String getLclOutWsMethodResultReturnCode() {
      return FixedStringAttr.valueOf(exportView.LclOutWsMethodResultReturnCode, 2);
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
 
   public String getOutWsQualificationEngDescription() {
      return FixedStringAttr.valueOf(exportView.OutWsQualificationEngDescription, 28);
   }
 
   public String getOutWsQualificationCode() {
      return FixedStringAttr.valueOf(exportView.OutWsQualificationCode, 5);
   }
 
   public String getOutWsQualificationType() {
      return FixedStringAttr.valueOf(exportView.OutWsQualificationType, 1);
   }
 
   public String getOutWsQualificationInUseFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsQualificationInUseFlag, 1);
   }
 
   public short getOutWsQualificationMkFacultyCode() {
      return exportView.OutWsQualificationMkFacultyCode;
   }
 
   public short getOutWsQualificationMkDepartmentCode() {
      return exportView.OutWsQualificationMkDepartmentCode;
   }
 
   public String getOutWsQualificationShortDescription() {
      return FixedStringAttr.valueOf(exportView.OutWsQualificationShortDescription, 12);
   }
 
   public String getOutWsQualificationAfrDescription() {
      return FixedStringAttr.valueOf(exportView.OutWsQualificationAfrDescription, 28);
   }
 
   public String getOutWsQualificationLongEngDescription() {
      return FixedStringAttr.valueOf(exportView.OutWsQualificationLongEngDescription, 120);
   }
 
   public String getOutWsQualificationLongAfrDescription() {
      return FixedStringAttr.valueOf(exportView.OutWsQualificationLongAfrDescription, 120);
   }
 
   public int getOutWsQualificationNumberRegistered() {
      return exportView.OutWsQualificationNumberRegistered;
   }
 
   public int getOutWsQualificationNumberCancelled() {
      return exportView.OutWsQualificationNumberCancelled;
   }
 
   public String getOutWsQualificationContactPhoneNo1() {
      return FixedStringAttr.valueOf(exportView.OutWsQualificationContactPhoneNo1, 20);
   }
 
   public String getOutWsQualificationContactPhoneNo2() {
      return FixedStringAttr.valueOf(exportView.OutWsQualificationContactPhoneNo2, 20);
   }
 
   public double getOutWsQualificationMinimumTime() {
      return exportView.OutWsQualificationMinimumTime;
   }
 
   public String getOutWsStudentSurname() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentSurname, 28);
   }
 
   public String getOutWsStudentInitials() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentInitials, 10);
   }
 
   public int getOutWsStudentNr() {
      return exportView.OutWsStudentNr;
   }
 
   public String getOutWsStudentMkTitle() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentMkTitle, 10);
   }
 
   public String getOutWsStudentSpecialCharacterFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentSpecialCharacterFlag, 1);
   }
 
   public String getOutWsStudentFirstNames() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentFirstNames, 60);
   }
 
   public String getOutWsStudentPreviousSurname() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentPreviousSurname, 28);
   }
 
   public String getOutWsStudentSquashCode() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentSquashCode, 8);
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
 
   public String getOutWsStudentGender() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentGender, 1);
   }
 
   public String getOutWsStudentMkNationality() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentMkNationality, 4);
   }
 
   public String getOutWsStudentMkHomeLanguage() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentMkHomeLanguage, 2);
   }
 
   public String getOutWsStudentMkCorrespondenceLanguage() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentMkCorrespondenceLanguage, 2);
   }
 
   public String getOutWsStudentDeceasedFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentDeceasedFlag, 1);
   }
 
   public short getOutWsStudentLibraryBlackList() {
      return exportView.OutWsStudentLibraryBlackList;
   }
 
   public String getOutWsStudentExamFeesDebtFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentExamFeesDebtFlag, 1);
   }
 
   public short getOutWsStudentDisciplinaryIncident() {
      return exportView.OutWsStudentDisciplinaryIncident;
   }
 
   public String getOutWsStudentPostGraduateStudiesApproved() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentPostGraduateStudiesApproved, 1);
   }
 
   public short getOutWsStudentPhasedOutStatus() {
      return exportView.OutWsStudentPhasedOutStatus;
   }
 
   public String getOutWsStudentFinancialBlockFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentFinancialBlockFlag, 1);
   }
 
   public String getOutWsStudentTwinFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentTwinFlag, 1);
   }
 
   public String getOutWsStudentAccessRestrictedFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentAccessRestrictedFlag, 1);
   }
 
   public String getOutWsStudentNumberRestrictedFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentNumberRestrictedFlag, 1);
   }
 
   public short getOutWsStudentUnisaUndergradYearsRegistered() {
      return exportView.OutWsStudentUnisaUndergradYearsRegistered;
   }
 
   public short getOutWsStudentUnisaHonoursYearsRegistered() {
      return exportView.OutWsStudentUnisaHonoursYearsRegistered;
   }
 
   public short getOutWsStudentUnisaMastersYearsRegistered() {
      return exportView.OutWsStudentUnisaMastersYearsRegistered;
   }
 
   public short getOutWsStudentUnisaDoctrateYearsRegistered() {
      return exportView.OutWsStudentUnisaDoctrateYearsRegistered;
   }
 
   public short getOutWsStudentOtherMastersYearsRegistered() {
      return exportView.OutWsStudentOtherMastersYearsRegistered;
   }
 
   public short getOutWsStudentOtherDoctrateYearsRegistered() {
      return exportView.OutWsStudentOtherDoctrateYearsRegistered;
   }
 
   public String getOutWsStudentPreviouslyPostGraduateFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentPreviouslyPostGraduateFlag, 1);
   }
 
   public String getOutWsStudentPreviouslyUnisaPostGradFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentPreviouslyUnisaPostGradFlag, 1);
   }
 
   public String getOutWsStudentResultBlockFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentResultBlockFlag, 1);
   }
 
   public short getOutWsStudentMkLastAcademicYear() {
      return exportView.OutWsStudentMkLastAcademicYear;
   }
 
   public short getOutWsStudentMkLastAcademicPeriod() {
      return exportView.OutWsStudentMkLastAcademicPeriod;
   }
 
   public String getOutWsStudentMkCountryCode() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentMkCountryCode, 4);
   }
 
   public String getOutWsStudentPassportNo() {
      return StringAttr.valueOf(exportView.OutWsStudentPassportNo);
   }
 
   public int getOutStudentGraduationDetailMkStudentNr() {
      return exportView.OutStudentGraduationDetailMkStudentNr;
   }
 
   public String getOutStudentGraduationDetailMkQualificationCode() {
      return FixedStringAttr.valueOf(exportView.OutStudentGraduationDetailMkQualificationCode, 5);
   }
 
   public Calendar getOutStudentGraduationDetailDateAltered() {
      return DateAttr.toCalendar(exportView.OutStudentGraduationDetailDateAltered);
   }
   public int getAsIntOutStudentGraduationDetailDateAltered() {
      return DateAttr.toInt(exportView.OutStudentGraduationDetailDateAltered);
   }
 
   public short getOutStudentGraduationDetailMkGraduationCeremonyCode() {
      return exportView.OutStudentGraduationDetailMkGraduationCeremonyCode;
   }
 
   public String getOutStudentGraduationDetailLanguage() {
      return FixedStringAttr.valueOf(exportView.OutStudentGraduationDetailLanguage, 1);
   }
 
   public String getOutStudentGraduationDetailSeatNo() {
      return FixedStringAttr.valueOf(exportView.OutStudentGraduationDetailSeatNo, 4);
   }
 
   public short getOutStudentGraduationDetailNoOfGuests() {
      return exportView.OutStudentGraduationDetailNoOfGuests;
   }
 
   public String getOutStudentGraduationDetailPresentFlag() {
      return FixedStringAttr.valueOf(exportView.OutStudentGraduationDetailPresentFlag, 1);
   }
 
   public String getOutStudentGraduationDetailCollectFlag() {
      return FixedStringAttr.valueOf(exportView.OutStudentGraduationDetailCollectFlag, 1);
   }
 
   public short getOutStudentGraduationDetailGownSize() {
      return exportView.OutStudentGraduationDetailGownSize;
   }
 
   public short getOutStudentGraduationDetailCapSize() {
      return exportView.OutStudentGraduationDetailCapSize;
   }
 
   public String getOutStudentGraduationDetailPreviousDegree() {
      return FixedStringAttr.valueOf(exportView.OutStudentGraduationDetailPreviousDegree, 7);
   }
 
   public String getOutStudentGraduationDetailPurchaseFlag() {
      return FixedStringAttr.valueOf(exportView.OutStudentGraduationDetailPurchaseFlag, 1);
   }
 
   public double getOutStudentGraduationDetailAmount() {
      return exportView.OutStudentGraduationDetailAmount;
   }
 
   public Calendar getOutStudentGraduationDetailBundleDate() {
      return DateAttr.toCalendar(exportView.OutStudentGraduationDetailBundleDate);
   }
   public int getAsIntOutStudentGraduationDetailBundleDate() {
      return DateAttr.toInt(exportView.OutStudentGraduationDetailBundleDate);
   }
 
   public short getOutStudentGraduationDetailBundleNr() {
      return exportView.OutStudentGraduationDetailBundleNr;
   }
 
   public short getOutStudentGraduationDetailBundleDoc() {
      return exportView.OutStudentGraduationDetailBundleDoc;
   }
 
   public String getOutStudentGraduationDetailTransferBlockedFlag() {
      return StringAttr.valueOf(exportView.OutStudentGraduationDetailTransferBlockedFlag);
   }
 
   public String getOutSecurityWsPrinterCode() {
      return FixedStringAttr.valueOf(exportView.OutSecurityWsPrinterCode, 10);
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
 
   public String getOutWsGraduationCeremonyCentreV2EngDescription() {
      return FixedStringAttr.valueOf(exportView.OutWsGraduationCeremonyCentreV2EngDescription, 28);
   }
 
   public String getOutCellPhoneNumberWsAddressV2CellNumber() {
      return FixedStringAttr.valueOf(exportView.OutCellPhoneNumberWsAddressV2CellNumber, 20);
   }
 
   public String getOutEmailFromCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutEmailFromCsfStringsString132, 132);
   }
 
   public String getOutEmailToCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutEmailToCsfStringsString132, 132);
   }
 
   public String getOutFaxNameCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutFaxNameCsfStringsString132, 132);
   }
 
   public String getOutFaxNumberCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutFaxNumberCsfStringsString132, 132);
   }
 
   public String getOutFaxOrEmailCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.OutFaxOrEmailCsfStringsString1, 1);
   }
 
   public String getOutSmsMessageIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutSmsMessageIefSuppliedFlag, 1);
   }
 
};
