package Dissh10h.Abean;
 
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
public class Dissh10sLinksSuToStm  implements ActionListener, java.io.Serializable  {
 
   //  Use final String for the bean name
   public static final String BEANNAME = new String("Dissh10sLinksSuToStm");
 
   //  Constants for Asynchronous status
   public static final int PENDING = CoopFlow.DATA_NOT_READY;
   public static final int AVAILABLE = CoopFlow.DATA_READY;
   public static final int INVALID_ID = CoopFlow.INVALID_ID;
 
   private final SimpleDateFormat nativeTimestampFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
   private final SimpleDateFormat nativeTimeFormatter = new SimpleDateFormat("HHmmss");
   private final SimpleDateFormat nativeDateFormatter = new SimpleDateFormat("yyyyMMdd");
   public Dissh10sLinksSuToStm() {
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
 
 
   private Dissh10sLinksSuToStmOperation oper = null;
 
   /**
    * Calls the transaction/procedure step on the server.
    *
    * @exception java.beans.PropertyVetoException
    * Final property checks can throw this exception.
    */
   public void execute()  throws PropertyVetoException {
      try  {
         if (oper == null) {
            oper = new Dissh10sLinksSuToStmOperation(this);
            addCompletionListener(new operListener(this));
            addExceptionListener(new operListener(this));
         }
 

 
         oper.doDissh10sLinksSuToStmOperation();
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
      private Dissh10sLinksSuToStm rP;
      operListener(Dissh10sLinksSuToStm r)  {
         rP = r;
      }
      public void actionPerformed(ActionEvent aEvent)  {
         if (Trace.isTracing(Trace.MASK_APPLICATION))
         {
            Trace.record(Trace.MASK_APPLICATION, "Dissh10sLinksSuToStm", "Listener heard that Dissh10sLinksSuToStmOperation completed with " + 
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of completion of operation Dissh10sLinksSuToStm ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      synchronized (this)  {
         targets = (Vector) completionListeners.clone();
      }
      actionEvt = new ActionEvent(this, 0, "Completion.Dissh10sLinksSuToStm");
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of exception of operation Dissh10sLinksSuToStm ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      String failCommand = "Exception.Dissh10sLinksSuToStm";
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
      importView.InGroup_MA = IntAttr.valueOf(InGroupMax);
      exportView.OutGroup_MA = IntAttr.getDefaultValue();
   }

   Dissh10h.DISSH10S_IA importView = Dissh10h.DISSH10S_IA.getInstance();
   Dissh10h.DISSH10S_OA exportView = Dissh10h.DISSH10S_OA.getInstance();
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
 
   public String getInHighAnnualStudyMaterialName() {
      return FixedStringAttr.valueOf(importView.InHighAnnualStudyMaterialName, 7);
   }
   public void setInHighAnnualStudyMaterialName(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InHighAnnualStudyMaterialName must be <= 7 characters.",
               new PropertyChangeEvent (this, "InHighAnnualStudyMaterialName", null, null));
      }
      importView.InHighAnnualStudyMaterialName = FixedStringAttr.valueOf(s, (short)7);
   }
 
   public String getInHighAnnualStudyMaterialNr() {
      return FixedStringAttr.valueOf(importView.InHighAnnualStudyMaterialNr, 3);
   }
   public void setInHighAnnualStudyMaterialNr(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 3) {
         throw new PropertyVetoException("InHighAnnualStudyMaterialNr must be <= 3 characters.",
               new PropertyChangeEvent (this, "InHighAnnualStudyMaterialNr", null, null));
      }
      importView.InHighAnnualStudyMaterialNr = FixedStringAttr.valueOf(s, (short)3);
   }
 
   public String getInHighAnnualStudyMaterialDinLanguage() {
      return FixedStringAttr.valueOf(importView.InHighAnnualStudyMaterialDinLanguage, 1);
   }
   public void setInHighAnnualStudyMaterialDinLanguage(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Dissh10sLinksSuToStmAnnualStudyMaterialDinLanguagePropertyEditor pe = new Dissh10sLinksSuToStmAnnualStudyMaterialDinLanguagePropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InHighAnnualStudyMaterialDinLanguage value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InHighAnnualStudyMaterialDinLanguage", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InHighAnnualStudyMaterialDinLanguage must be <= 1 characters.",
               new PropertyChangeEvent (this, "InHighAnnualStudyMaterialDinLanguage", null, null));
      }
      importView.InHighAnnualStudyMaterialDinLanguage = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInHighStudyMaterialTypeCode() {
      return FixedStringAttr.valueOf(importView.InHighStudyMaterialTypeCode, 2);
   }
   public void setInHighStudyMaterialTypeCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InHighStudyMaterialTypeCode must be <= 2 characters.",
               new PropertyChangeEvent (this, "InHighStudyMaterialTypeCode", null, null));
      }
      importView.InHighStudyMaterialTypeCode = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public String getInLowAnnualStudyMaterialName() {
      return FixedStringAttr.valueOf(importView.InLowAnnualStudyMaterialName, 7);
   }
   public void setInLowAnnualStudyMaterialName(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InLowAnnualStudyMaterialName must be <= 7 characters.",
               new PropertyChangeEvent (this, "InLowAnnualStudyMaterialName", null, null));
      }
      importView.InLowAnnualStudyMaterialName = FixedStringAttr.valueOf(s, (short)7);
   }
 
   public String getInLowAnnualStudyMaterialNr() {
      return FixedStringAttr.valueOf(importView.InLowAnnualStudyMaterialNr, 3);
   }
   public void setInLowAnnualStudyMaterialNr(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 3) {
         throw new PropertyVetoException("InLowAnnualStudyMaterialNr must be <= 3 characters.",
               new PropertyChangeEvent (this, "InLowAnnualStudyMaterialNr", null, null));
      }
      importView.InLowAnnualStudyMaterialNr = FixedStringAttr.valueOf(s, (short)3);
   }
 
   public String getInLowAnnualStudyMaterialDinLanguage() {
      return FixedStringAttr.valueOf(importView.InLowAnnualStudyMaterialDinLanguage, 1);
   }
   public void setInLowAnnualStudyMaterialDinLanguage(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Dissh10sLinksSuToStmAnnualStudyMaterialDinLanguagePropertyEditor pe = new Dissh10sLinksSuToStmAnnualStudyMaterialDinLanguagePropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InLowAnnualStudyMaterialDinLanguage value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InLowAnnualStudyMaterialDinLanguage", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InLowAnnualStudyMaterialDinLanguage must be <= 1 characters.",
               new PropertyChangeEvent (this, "InLowAnnualStudyMaterialDinLanguage", null, null));
      }
      importView.InLowAnnualStudyMaterialDinLanguage = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInLowStudyMaterialTypeCode() {
      return FixedStringAttr.valueOf(importView.InLowStudyMaterialTypeCode, 2);
   }
   public void setInLowStudyMaterialTypeCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InLowStudyMaterialTypeCode must be <= 2 characters.",
               new PropertyChangeEvent (this, "InLowStudyMaterialTypeCode", null, null));
      }
      importView.InLowStudyMaterialTypeCode = FixedStringAttr.valueOf(s, (short)2);
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
 
   public String getInLowStudyUnitStudyMaterialRefName() {
      return FixedStringAttr.valueOf(importView.InLowStudyUnitStudyMaterialRefName, 7);
   }
   public void setInLowStudyUnitStudyMaterialRefName(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InLowStudyUnitStudyMaterialRefName must be <= 7 characters.",
               new PropertyChangeEvent (this, "InLowStudyUnitStudyMaterialRefName", null, null));
      }
      importView.InLowStudyUnitStudyMaterialRefName = FixedStringAttr.valueOf(s, (short)7);
   }
 
   public String getInLowStudyUnitStudyMaterialRefNo() {
      return FixedStringAttr.valueOf(importView.InLowStudyUnitStudyMaterialRefNo, 3);
   }
   public void setInLowStudyUnitStudyMaterialRefNo(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 3) {
         throw new PropertyVetoException("InLowStudyUnitStudyMaterialRefNo must be <= 3 characters.",
               new PropertyChangeEvent (this, "InLowStudyUnitStudyMaterialRefNo", null, null));
      }
      importView.InLowStudyUnitStudyMaterialRefNo = FixedStringAttr.valueOf(s, (short)3);
   }
 
   public String getInLowStudyUnitStudyMaterialRefLanguage() {
      return FixedStringAttr.valueOf(importView.InLowStudyUnitStudyMaterialRefLanguage, 1);
   }
   public void setInLowStudyUnitStudyMaterialRefLanguage(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Dissh10sLinksSuToStmStudyUnitStudyMaterialRefLanguagePropertyEditor pe = new Dissh10sLinksSuToStmStudyUnitStudyMaterialRefLanguagePropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InLowStudyUnitStudyMaterialRefLanguage value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InLowStudyUnitStudyMaterialRefLanguage", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InLowStudyUnitStudyMaterialRefLanguage must be <= 1 characters.",
               new PropertyChangeEvent (this, "InLowStudyUnitStudyMaterialRefLanguage", null, null));
      }
      importView.InLowStudyUnitStudyMaterialRefLanguage = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInHighStudyUnitStudyMaterialRefName() {
      return FixedStringAttr.valueOf(importView.InHighStudyUnitStudyMaterialRefName, 7);
   }
   public void setInHighStudyUnitStudyMaterialRefName(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InHighStudyUnitStudyMaterialRefName must be <= 7 characters.",
               new PropertyChangeEvent (this, "InHighStudyUnitStudyMaterialRefName", null, null));
      }
      importView.InHighStudyUnitStudyMaterialRefName = FixedStringAttr.valueOf(s, (short)7);
   }
 
   public String getInHighStudyUnitStudyMaterialRefNo() {
      return FixedStringAttr.valueOf(importView.InHighStudyUnitStudyMaterialRefNo, 3);
   }
   public void setInHighStudyUnitStudyMaterialRefNo(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 3) {
         throw new PropertyVetoException("InHighStudyUnitStudyMaterialRefNo must be <= 3 characters.",
               new PropertyChangeEvent (this, "InHighStudyUnitStudyMaterialRefNo", null, null));
      }
      importView.InHighStudyUnitStudyMaterialRefNo = FixedStringAttr.valueOf(s, (short)3);
   }
 
   public String getInHighStudyUnitStudyMaterialRefLanguage() {
      return FixedStringAttr.valueOf(importView.InHighStudyUnitStudyMaterialRefLanguage, 1);
   }
   public void setInHighStudyUnitStudyMaterialRefLanguage(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Dissh10sLinksSuToStmStudyUnitStudyMaterialRefLanguagePropertyEditor pe = new Dissh10sLinksSuToStmStudyUnitStudyMaterialRefLanguagePropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InHighStudyUnitStudyMaterialRefLanguage value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InHighStudyUnitStudyMaterialRefLanguage", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InHighStudyUnitStudyMaterialRefLanguage must be <= 1 characters.",
               new PropertyChangeEvent (this, "InHighStudyUnitStudyMaterialRefLanguage", null, null));
      }
      importView.InHighStudyUnitStudyMaterialRefLanguage = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInStartingStudyUnitStudyMaterialRefLanguage() {
      return FixedStringAttr.valueOf(importView.InStartingStudyUnitStudyMaterialRefLanguage, 1);
   }
   public void setInStartingStudyUnitStudyMaterialRefLanguage(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Dissh10sLinksSuToStmStudyUnitStudyMaterialRefLanguagePropertyEditor pe = new Dissh10sLinksSuToStmStudyUnitStudyMaterialRefLanguagePropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InStartingStudyUnitStudyMaterialRefLanguage value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InStartingStudyUnitStudyMaterialRefLanguage", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InStartingStudyUnitStudyMaterialRefLanguage must be <= 1 characters.",
               new PropertyChangeEvent (this, "InStartingStudyUnitStudyMaterialRefLanguage", null, null));
      }
      importView.InStartingStudyUnitStudyMaterialRefLanguage = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInHighSuStudyMaterialTypeCode() {
      return FixedStringAttr.valueOf(importView.InHighSuStudyMaterialTypeCode, 2);
   }
   public void setInHighSuStudyMaterialTypeCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InHighSuStudyMaterialTypeCode must be <= 2 characters.",
               new PropertyChangeEvent (this, "InHighSuStudyMaterialTypeCode", null, null));
      }
      importView.InHighSuStudyMaterialTypeCode = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public String getInLowSuStudyMaterialTypeCode() {
      return FixedStringAttr.valueOf(importView.InLowSuStudyMaterialTypeCode, 2);
   }
   public void setInLowSuStudyMaterialTypeCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InLowSuStudyMaterialTypeCode must be <= 2 characters.",
               new PropertyChangeEvent (this, "InLowSuStudyMaterialTypeCode", null, null));
      }
      importView.InLowSuStudyMaterialTypeCode = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public String getInStudyUnitStudyMaterialMkStudyUnitCode() {
      return FixedStringAttr.valueOf(importView.InStudyUnitStudyMaterialMkStudyUnitCode, 7);
   }
   public void setInStudyUnitStudyMaterialMkStudyUnitCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InStudyUnitStudyMaterialMkStudyUnitCode must be <= 7 characters.",
               new PropertyChangeEvent (this, "InStudyUnitStudyMaterialMkStudyUnitCode", null, null));
      }
      importView.InStudyUnitStudyMaterialMkStudyUnitCode = FixedStringAttr.valueOf(s, (short)7);
   }
 
   public short getInAnnualStudyMaterialAcademicYear() {
      return importView.InAnnualStudyMaterialAcademicYear;
   }
   public void setInAnnualStudyMaterialAcademicYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InAnnualStudyMaterialAcademicYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InAnnualStudyMaterialAcademicYear", null, null));
      }
      importView.InAnnualStudyMaterialAcademicYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInAnnualStudyMaterialAcademicYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InAnnualStudyMaterialAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InAnnualStudyMaterialAcademicYear", null, null));
      }
      try {
          setInAnnualStudyMaterialAcademicYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InAnnualStudyMaterialAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InAnnualStudyMaterialAcademicYear", null, null));
      }
   }
 
   public short getInAnnualStudyMaterialAcademicPeriod() {
      return importView.InAnnualStudyMaterialAcademicPeriod;
   }
   public void setInAnnualStudyMaterialAcademicPeriod(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InAnnualStudyMaterialAcademicPeriod has more than 2 digits.",
               new PropertyChangeEvent (this, "InAnnualStudyMaterialAcademicPeriod", null, null));
      }
      importView.InAnnualStudyMaterialAcademicPeriod = ShortAttr.valueOf(s);
   }
   public void setAsStringInAnnualStudyMaterialAcademicPeriod(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InAnnualStudyMaterialAcademicPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InAnnualStudyMaterialAcademicPeriod", null, null));
      }
      try {
          setInAnnualStudyMaterialAcademicPeriod(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InAnnualStudyMaterialAcademicPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InAnnualStudyMaterialAcademicPeriod", null, null));
      }
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
 
   public String getInCsfClientServerCommunicationsRgvScrollUpFlag() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsRgvScrollUpFlag, 1);
   }
   public void setInCsfClientServerCommunicationsRgvScrollUpFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsRgvScrollUpFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsRgvScrollUpFlag", null, null));
      }
      importView.InCsfClientServerCommunicationsRgvScrollUpFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInCsfClientServerCommunicationsRgvScrollDownFlag() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsRgvScrollDownFlag, 1);
   }
   public void setInCsfClientServerCommunicationsRgvScrollDownFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsRgvScrollDownFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsRgvScrollDownFlag", null, null));
      }
      importView.InCsfClientServerCommunicationsRgvScrollDownFlag = FixedStringAttr.valueOf(s, (short)1);
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
 
   public String getInCsfClientServerCommunicationsObjectRetrievedFlag() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsObjectRetrievedFlag, 1);
   }
   public void setInCsfClientServerCommunicationsObjectRetrievedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsObjectRetrievedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsObjectRetrievedFlag", null, null));
      }
      importView.InCsfClientServerCommunicationsObjectRetrievedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public final int InGroupMax = 50;
   public short getInGroupCount() {
      return (short)(importView.InGroup_MA);
   };
 
   public void setInGroupCount(short s) throws PropertyVetoException {
      if (s < 0 || s > InGroupMax) {
         throw new PropertyVetoException("InGroupCount value is not a valid value. (0 to 50)",
               new PropertyChangeEvent (this, "InGroupCount", null, null));
      } else {
         importView.InGroup_MA = IntAttr.valueOf((int)s);
      }
   }
 
   public Calendar getInGStudyMaterialStockItemInStockDate(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return DateAttr.toCalendar(importView.InGStudyMaterialStockItemInStockDate[index]);
   }
   public int getAsIntInGStudyMaterialStockItemInStockDate(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return DateAttr.toInt(importView.InGStudyMaterialStockItemInStockDate[index]);
   }
   public void setInGStudyMaterialStockItemInStockDate(int index, Calendar s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      importView.InGStudyMaterialStockItemInStockDate[index] = DateAttr.valueOf(s);
   }
   public void setAsStringInGStudyMaterialStockItemInStockDate(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s == null || s.length() == 0) {
         setInGStudyMaterialStockItemInStockDate(index, (Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInGStudyMaterialStockItemInStockDate(index, tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InGStudyMaterialStockItemInStockDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InGStudyMaterialStockItemInStockDate", null, null));
         }
      }
   }
   public void setAsIntInGStudyMaterialStockItemInStockDate(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInGStudyMaterialStockItemInStockDate(index, temp);
   }
 
   public String getInGSuStudyMaterialTypeCode(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGSuStudyMaterialTypeCode[index], 2);
   }
   public void setInGSuStudyMaterialTypeCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InGSuStudyMaterialTypeCode must be <= 2 characters.",
               new PropertyChangeEvent (this, "InGSuStudyMaterialTypeCode", null, null));
      }
      importView.InGSuStudyMaterialTypeCode[index] = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public int getInGStudyMaterialBarcode(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return importView.InGStudyMaterialBarcode[index];
   }
   public void setInGStudyMaterialBarcode(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InGStudyMaterialBarcode has more than 8 digits.",
               new PropertyChangeEvent (this, "InGStudyMaterialBarcode", null, null));
      }
      importView.InGStudyMaterialBarcode[index] = IntAttr.valueOf(s);
   }
   public void setAsStringInGStudyMaterialBarcode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGStudyMaterialBarcode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGStudyMaterialBarcode", null, null));
      }
      try {
          setInGStudyMaterialBarcode(index, Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGStudyMaterialBarcode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGStudyMaterialBarcode", null, null));
      }
   }
 
   public String getInGStudyMaterialRemarks(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGStudyMaterialRemarks[index], 20);
   }
   public void setInGStudyMaterialRemarks(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 20) {
         throw new PropertyVetoException("InGStudyMaterialRemarks must be <= 20 characters.",
               new PropertyChangeEvent (this, "InGStudyMaterialRemarks", null, null));
      }
      importView.InGStudyMaterialRemarks[index] = FixedStringAttr.valueOf(s, (short)20);
   }
 
   public String getInGStudyUnitStudyMaterialMkStudyUnitCode(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGStudyUnitStudyMaterialMkStudyUnitCode[index], 7);
   }
   public void setInGStudyUnitStudyMaterialMkStudyUnitCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InGStudyUnitStudyMaterialMkStudyUnitCode must be <= 7 characters.",
               new PropertyChangeEvent (this, "InGStudyUnitStudyMaterialMkStudyUnitCode", null, null));
      }
      importView.InGStudyUnitStudyMaterialMkStudyUnitCode[index] = FixedStringAttr.valueOf(s, (short)7);
   }
 
   public String getInGStudyUnitStudyMaterialRefName(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGStudyUnitStudyMaterialRefName[index], 7);
   }
   public void setInGStudyUnitStudyMaterialRefName(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InGStudyUnitStudyMaterialRefName must be <= 7 characters.",
               new PropertyChangeEvent (this, "InGStudyUnitStudyMaterialRefName", null, null));
      }
      importView.InGStudyUnitStudyMaterialRefName[index] = FixedStringAttr.valueOf(s, (short)7);
   }
 
   public String getInGStudyUnitStudyMaterialRefNo(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGStudyUnitStudyMaterialRefNo[index], 3);
   }
   public void setInGStudyUnitStudyMaterialRefNo(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 3) {
         throw new PropertyVetoException("InGStudyUnitStudyMaterialRefNo must be <= 3 characters.",
               new PropertyChangeEvent (this, "InGStudyUnitStudyMaterialRefNo", null, null));
      }
      importView.InGStudyUnitStudyMaterialRefNo[index] = FixedStringAttr.valueOf(s, (short)3);
   }
 
   public String getInGStudyUnitStudyMaterialRefLanguage(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGStudyUnitStudyMaterialRefLanguage[index], 1);
   }
   public void setInGStudyUnitStudyMaterialRefLanguage(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      Dissh10sLinksSuToStmStudyUnitStudyMaterialRefLanguagePropertyEditor pe = new Dissh10sLinksSuToStmStudyUnitStudyMaterialRefLanguagePropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InGStudyUnitStudyMaterialRefLanguage value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InGStudyUnitStudyMaterialRefLanguage", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGStudyUnitStudyMaterialRefLanguage must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGStudyUnitStudyMaterialRefLanguage", null, null));
      }
      importView.InGStudyUnitStudyMaterialRefLanguage[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public Calendar getInGStudyUnitStudyMaterialPlannedIssueDate(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return DateAttr.toCalendar(importView.InGStudyUnitStudyMaterialPlannedIssueDate[index]);
   }
   public int getAsIntInGStudyUnitStudyMaterialPlannedIssueDate(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return DateAttr.toInt(importView.InGStudyUnitStudyMaterialPlannedIssueDate[index]);
   }
   public void setInGStudyUnitStudyMaterialPlannedIssueDate(int index, Calendar s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      importView.InGStudyUnitStudyMaterialPlannedIssueDate[index] = DateAttr.valueOf(s);
   }
   public void setAsStringInGStudyUnitStudyMaterialPlannedIssueDate(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s == null || s.length() == 0) {
         setInGStudyUnitStudyMaterialPlannedIssueDate(index, (Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInGStudyUnitStudyMaterialPlannedIssueDate(index, tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InGStudyUnitStudyMaterialPlannedIssueDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InGStudyUnitStudyMaterialPlannedIssueDate", null, null));
         }
      }
   }
   public void setAsIntInGStudyUnitStudyMaterialPlannedIssueDate(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInGStudyUnitStudyMaterialPlannedIssueDate(index, temp);
   }
 
   public Calendar getInGStudyUnitStudyMaterialActualIssueDate(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return DateAttr.toCalendar(importView.InGStudyUnitStudyMaterialActualIssueDate[index]);
   }
   public int getAsIntInGStudyUnitStudyMaterialActualIssueDate(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return DateAttr.toInt(importView.InGStudyUnitStudyMaterialActualIssueDate[index]);
   }
   public void setInGStudyUnitStudyMaterialActualIssueDate(int index, Calendar s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      importView.InGStudyUnitStudyMaterialActualIssueDate[index] = DateAttr.valueOf(s);
   }
   public void setAsStringInGStudyUnitStudyMaterialActualIssueDate(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s == null || s.length() == 0) {
         setInGStudyUnitStudyMaterialActualIssueDate(index, (Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInGStudyUnitStudyMaterialActualIssueDate(index, tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InGStudyUnitStudyMaterialActualIssueDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InGStudyUnitStudyMaterialActualIssueDate", null, null));
         }
      }
   }
   public void setAsIntInGStudyUnitStudyMaterialActualIssueDate(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInGStudyUnitStudyMaterialActualIssueDate(index, temp);
   }
 
   public short getInGStudyUnitStudyMaterialIssueQuantity(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return importView.InGStudyUnitStudyMaterialIssueQuantity[index];
   }
   public void setInGStudyUnitStudyMaterialIssueQuantity(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InGStudyUnitStudyMaterialIssueQuantity has more than 2 digits.",
               new PropertyChangeEvent (this, "InGStudyUnitStudyMaterialIssueQuantity", null, null));
      }
      importView.InGStudyUnitStudyMaterialIssueQuantity[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGStudyUnitStudyMaterialIssueQuantity(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGStudyUnitStudyMaterialIssueQuantity is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGStudyUnitStudyMaterialIssueQuantity", null, null));
      }
      try {
          setInGStudyUnitStudyMaterialIssueQuantity(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGStudyUnitStudyMaterialIssueQuantity is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGStudyUnitStudyMaterialIssueQuantity", null, null));
      }
   }
 
   public String getInGStudyUnitStudyMaterialOnlyGuideFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGStudyUnitStudyMaterialOnlyGuideFlag[index], 1);
   }
   public void setInGStudyUnitStudyMaterialOnlyGuideFlag(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      Dissh10sLinksSuToStmStudyUnitStudyMaterialOnlyGuideFlagPropertyEditor pe = new Dissh10sLinksSuToStmStudyUnitStudyMaterialOnlyGuideFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InGStudyUnitStudyMaterialOnlyGuideFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InGStudyUnitStudyMaterialOnlyGuideFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGStudyUnitStudyMaterialOnlyGuideFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGStudyUnitStudyMaterialOnlyGuideFlag", null, null));
      }
      importView.InGStudyUnitStudyMaterialOnlyGuideFlag[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInGStudyMaterialTypeCode(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGStudyMaterialTypeCode[index], 2);
   }
   public void setInGStudyMaterialTypeCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InGStudyMaterialTypeCode must be <= 2 characters.",
               new PropertyChangeEvent (this, "InGStudyMaterialTypeCode", null, null));
      }
      importView.InGStudyMaterialTypeCode[index] = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public String getInGAnnualStudyMaterialName(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGAnnualStudyMaterialName[index], 7);
   }
   public void setInGAnnualStudyMaterialName(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InGAnnualStudyMaterialName must be <= 7 characters.",
               new PropertyChangeEvent (this, "InGAnnualStudyMaterialName", null, null));
      }
      importView.InGAnnualStudyMaterialName[index] = FixedStringAttr.valueOf(s, (short)7);
   }
 
   public String getInGAnnualStudyMaterialNr(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGAnnualStudyMaterialNr[index], 3);
   }
   public void setInGAnnualStudyMaterialNr(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 3) {
         throw new PropertyVetoException("InGAnnualStudyMaterialNr must be <= 3 characters.",
               new PropertyChangeEvent (this, "InGAnnualStudyMaterialNr", null, null));
      }
      importView.InGAnnualStudyMaterialNr[index] = FixedStringAttr.valueOf(s, (short)3);
   }
 
   public String getInGAnnualStudyMaterialDinLanguage(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGAnnualStudyMaterialDinLanguage[index], 1);
   }
   public void setInGAnnualStudyMaterialDinLanguage(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      Dissh10sLinksSuToStmAnnualStudyMaterialDinLanguagePropertyEditor pe = new Dissh10sLinksSuToStmAnnualStudyMaterialDinLanguagePropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InGAnnualStudyMaterialDinLanguage value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InGAnnualStudyMaterialDinLanguage", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGAnnualStudyMaterialDinLanguage must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGAnnualStudyMaterialDinLanguage", null, null));
      }
      importView.InGAnnualStudyMaterialDinLanguage[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInGAnnualStudyMaterialPublishingDescription(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return StringAttr.valueOf(importView.InGAnnualStudyMaterialPublishingDescription[index]);
   }
   public void setInGAnnualStudyMaterialPublishingDescription(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 200) {
         throw new PropertyVetoException("InGAnnualStudyMaterialPublishingDescription must be <= 200 characters.",
               new PropertyChangeEvent (this, "InGAnnualStudyMaterialPublishingDescription", null, null));
      }
      importView.InGAnnualStudyMaterialPublishingDescription[index] = StringAttr.valueOf(s, (short)200);
   }
 
   public short getInGAnnualStudyMaterialAcademicPeriod(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return importView.InGAnnualStudyMaterialAcademicPeriod[index];
   }
   public void setInGAnnualStudyMaterialAcademicPeriod(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InGAnnualStudyMaterialAcademicPeriod has more than 2 digits.",
               new PropertyChangeEvent (this, "InGAnnualStudyMaterialAcademicPeriod", null, null));
      }
      importView.InGAnnualStudyMaterialAcademicPeriod[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGAnnualStudyMaterialAcademicPeriod(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGAnnualStudyMaterialAcademicPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGAnnualStudyMaterialAcademicPeriod", null, null));
      }
      try {
          setInGAnnualStudyMaterialAcademicPeriod(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGAnnualStudyMaterialAcademicPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGAnnualStudyMaterialAcademicPeriod", null, null));
      }
   }
 
   public String getInGCsfLineActionBarAction(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGCsfLineActionBarAction[index], 1);
   }
   public void setInGCsfLineActionBarAction(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGCsfLineActionBarAction must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGCsfLineActionBarAction", null, null));
      }
      importView.InGCsfLineActionBarAction[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInGCsfLineActionBarLineReturnCode(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return importView.InGCsfLineActionBarLineReturnCode[index];
   }
   public void setInGCsfLineActionBarLineReturnCode(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InGCsfLineActionBarLineReturnCode has more than 4 digits.",
               new PropertyChangeEvent (this, "InGCsfLineActionBarLineReturnCode", null, null));
      }
      importView.InGCsfLineActionBarLineReturnCode[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGCsfLineActionBarLineReturnCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
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
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGCsfLineActionBarSelectionFlag[index], 1);
   }
   public void setInGCsfLineActionBarSelectionFlag(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGCsfLineActionBarSelectionFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGCsfLineActionBarSelectionFlag", null, null));
      }
      importView.InGCsfLineActionBarSelectionFlag[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInGCsfLineActionBarTranslatedAction(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGCsfLineActionBarTranslatedAction[index], 1);
   }
   public void setInGCsfLineActionBarTranslatedAction(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGCsfLineActionBarTranslatedAction must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGCsfLineActionBarTranslatedAction", null, null));
      }
      importView.InGCsfLineActionBarTranslatedAction[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInGCsfPromptPrompt1(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGCsfPromptPrompt1[index], 1);
   }
   public void setInGCsfPromptPrompt1(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGCsfPromptPrompt1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGCsfPromptPrompt1", null, null));
      }
      importView.InGCsfPromptPrompt1[index] = FixedStringAttr.valueOf(s, (short)1);
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
 
   public String getOutWhatToDoCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.OutWhatToDoCsfStringsString1, 1);
   }
 
   public String getOutEmailFromCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutEmailFromCsfStringsString132, 132);
   }
 
   public String getOutEmailToCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutEmailToCsfStringsString132, 132);
   }
 
   public String getOutWsMethodResultReturnCode() {
      return FixedStringAttr.valueOf(exportView.OutWsMethodResultReturnCode, 2);
   }
 
   public String getOutStudyUnitStudyMaterialMkStudyUnitCode() {
      return FixedStringAttr.valueOf(exportView.OutStudyUnitStudyMaterialMkStudyUnitCode, 7);
   }
 
   public String getOutStudyUnitStudyMaterialRefName() {
      return FixedStringAttr.valueOf(exportView.OutStudyUnitStudyMaterialRefName, 7);
   }
 
   public String getOutStudyUnitStudyMaterialRefNo() {
      return FixedStringAttr.valueOf(exportView.OutStudyUnitStudyMaterialRefNo, 3);
   }
 
   public String getOutStudyUnitStudyMaterialRefLanguage() {
      return FixedStringAttr.valueOf(exportView.OutStudyUnitStudyMaterialRefLanguage, 1);
   }
 
   public Calendar getOutStudyUnitStudyMaterialPlannedIssueDate() {
      return DateAttr.toCalendar(exportView.OutStudyUnitStudyMaterialPlannedIssueDate);
   }
   public int getAsIntOutStudyUnitStudyMaterialPlannedIssueDate() {
      return DateAttr.toInt(exportView.OutStudyUnitStudyMaterialPlannedIssueDate);
   }
 
   public Calendar getOutStudyUnitStudyMaterialActualIssueDate() {
      return DateAttr.toCalendar(exportView.OutStudyUnitStudyMaterialActualIssueDate);
   }
   public int getAsIntOutStudyUnitStudyMaterialActualIssueDate() {
      return DateAttr.toInt(exportView.OutStudyUnitStudyMaterialActualIssueDate);
   }
 
   public short getOutStudyUnitStudyMaterialIssueQuantity() {
      return exportView.OutStudyUnitStudyMaterialIssueQuantity;
   }
 
   public String getOutStudyUnitStudyMaterialOnlyGuideFlag() {
      return FixedStringAttr.valueOf(exportView.OutStudyUnitStudyMaterialOnlyGuideFlag, 1);
   }
 
   public String getOutKeepCsfStringsString500() {
      return StringAttr.valueOf(exportView.OutKeepCsfStringsString500);
   }
 
   public short getOutAnnualStudyMaterialAcademicYear() {
      return exportView.OutAnnualStudyMaterialAcademicYear;
   }
 
   public short getOutAnnualStudyMaterialAcademicPeriod() {
      return exportView.OutAnnualStudyMaterialAcademicPeriod;
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
 
   public String getOutCsfClientServerCommunicationsRgvScrollUpFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsRgvScrollUpFlag, 1);
   }
 
   public String getOutCsfClientServerCommunicationsRgvScrollDownFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsRgvScrollDownFlag, 1);
   }
 
   public String getOutCsfClientServerCommunicationsObjectRetrievedFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsObjectRetrievedFlag, 1);
   }
 
   public final int OutGroupMax = 50;
   public short getOutGroupCount() {
      return (short)(exportView.OutGroup_MA);
   };
 
   public Calendar getOutGStudyMaterialStockItemInStockDate(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return DateAttr.toCalendar(exportView.OutGStudyMaterialStockItemInStockDate[index]);
   }
   public int getAsIntOutGStudyMaterialStockItemInStockDate(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return DateAttr.toInt(exportView.OutGStudyMaterialStockItemInStockDate[index]);
   }
 
   public String getOutGSuStudyMaterialTypeCode(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGSuStudyMaterialTypeCode[index], 2);
   }
 
   public int getOutGStudyMaterialBarcode(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return exportView.OutGStudyMaterialBarcode[index];
   }
 
   public String getOutGStudyMaterialRemarks(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGStudyMaterialRemarks[index], 20);
   }
 
   public String getOutGStudyUnitStudyMaterialMkStudyUnitCode(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGStudyUnitStudyMaterialMkStudyUnitCode[index], 7);
   }
 
   public String getOutGStudyUnitStudyMaterialRefName(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGStudyUnitStudyMaterialRefName[index], 7);
   }
 
   public String getOutGStudyUnitStudyMaterialRefNo(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGStudyUnitStudyMaterialRefNo[index], 3);
   }
 
   public String getOutGStudyUnitStudyMaterialRefLanguage(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGStudyUnitStudyMaterialRefLanguage[index], 1);
   }
 
   public Calendar getOutGStudyUnitStudyMaterialPlannedIssueDate(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return DateAttr.toCalendar(exportView.OutGStudyUnitStudyMaterialPlannedIssueDate[index]);
   }
   public int getAsIntOutGStudyUnitStudyMaterialPlannedIssueDate(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return DateAttr.toInt(exportView.OutGStudyUnitStudyMaterialPlannedIssueDate[index]);
   }
 
   public Calendar getOutGStudyUnitStudyMaterialActualIssueDate(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return DateAttr.toCalendar(exportView.OutGStudyUnitStudyMaterialActualIssueDate[index]);
   }
   public int getAsIntOutGStudyUnitStudyMaterialActualIssueDate(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return DateAttr.toInt(exportView.OutGStudyUnitStudyMaterialActualIssueDate[index]);
   }
 
   public short getOutGStudyUnitStudyMaterialIssueQuantity(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return exportView.OutGStudyUnitStudyMaterialIssueQuantity[index];
   }
 
   public String getOutGStudyUnitStudyMaterialOnlyGuideFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGStudyUnitStudyMaterialOnlyGuideFlag[index], 1);
   }
 
   public String getOutGStudyMaterialTypeCode(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGStudyMaterialTypeCode[index], 2);
   }
 
   public String getOutGAnnualStudyMaterialName(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGAnnualStudyMaterialName[index], 7);
   }
 
   public String getOutGAnnualStudyMaterialNr(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGAnnualStudyMaterialNr[index], 3);
   }
 
   public String getOutGAnnualStudyMaterialDinLanguage(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGAnnualStudyMaterialDinLanguage[index], 1);
   }
 
   public String getOutGAnnualStudyMaterialPublishingDescription(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return StringAttr.valueOf(exportView.OutGAnnualStudyMaterialPublishingDescription[index]);
   }
 
   public short getOutGAnnualStudyMaterialAcademicPeriod(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return exportView.OutGAnnualStudyMaterialAcademicPeriod[index];
   }
 
   public String getOutGCsfLineActionBarAction(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGCsfLineActionBarAction[index], 1);
   }
 
   public short getOutGCsfLineActionBarLineReturnCode(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return exportView.OutGCsfLineActionBarLineReturnCode[index];
   }
 
   public String getOutGCsfLineActionBarSelectionFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGCsfLineActionBarSelectionFlag[index], 1);
   }
 
   public String getOutGCsfLineActionBarTranslatedAction(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGCsfLineActionBarTranslatedAction[index], 1);
   }
 
   public String getOutGCsfPromptPrompt1(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGCsfPromptPrompt1[index], 1);
   }
 
   public String getOutSecurityWsActionCode() {
      return FixedStringAttr.valueOf(exportView.OutSecurityWsActionCode, 2);
   }
 
   public String getOutSecurityWsActionDescription() {
      return FixedStringAttr.valueOf(exportView.OutSecurityWsActionDescription, 10);
   }
 
   public int getOutSecurityWsFunctionNumber() {
      return exportView.OutSecurityWsFunctionNumber;
   }
 
   public String getOutSecurityWsFunctionTrancode() {
      return FixedStringAttr.valueOf(exportView.OutSecurityWsFunctionTrancode, 8);
   }
 
   public String getOutSecurityWsFunctionDescription() {
      return FixedStringAttr.valueOf(exportView.OutSecurityWsFunctionDescription, 28);
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
 
   public String getOutSecurityWsWorkstationCode() {
      return StringAttr.valueOf(exportView.OutSecurityWsWorkstationCode);
   }
 
};
