package Expll01h.Abean;
 
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
public class Expll01sMntExamPaperLog  implements ActionListener, java.io.Serializable  {
 
   //  Use final String for the bean name
   public static final String BEANNAME = new String("Expll01sMntExamPaperLog");
 
   //  Constants for Asynchronous status
   public static final int PENDING = CoopFlow.DATA_NOT_READY;
   public static final int AVAILABLE = CoopFlow.DATA_READY;
   public static final int INVALID_ID = CoopFlow.INVALID_ID;
 
   private final SimpleDateFormat nativeTimestampFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
   private final SimpleDateFormat nativeTimeFormatter = new SimpleDateFormat("HHmmss");
   private final SimpleDateFormat nativeDateFormatter = new SimpleDateFormat("yyyyMMdd");
   public Expll01sMntExamPaperLog() {
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
 
 
   private Expll01sMntExamPaperLogOperation oper = null;
 
   /**
    * Calls the transaction/procedure step on the server.
    *
    * @exception java.beans.PropertyVetoException
    * Final property checks can throw this exception.
    */
   public void execute()  throws PropertyVetoException {
      try  {
         if (oper == null) {
            oper = new Expll01sMntExamPaperLogOperation(this);
            addCompletionListener(new operListener(this));
            addExceptionListener(new operListener(this));
         }
 

 
         oper.doExpll01sMntExamPaperLogOperation();
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
      private Expll01sMntExamPaperLog rP;
      operListener(Expll01sMntExamPaperLog r)  {
         rP = r;
      }
      public void actionPerformed(ActionEvent aEvent)  {
         if (Trace.isTracing(Trace.MASK_APPLICATION))
         {
            Trace.record(Trace.MASK_APPLICATION, "Expll01sMntExamPaperLog", "Listener heard that Expll01sMntExamPaperLogOperation completed with " + 
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of completion of operation Expll01sMntExamPaperLog ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      synchronized (this)  {
         targets = (Vector) completionListeners.clone();
      }
      actionEvt = new ActionEvent(this, 0, "Completion.Expll01sMntExamPaperLog");
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of exception of operation Expll01sMntExamPaperLog ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      String failCommand = "Exception.Expll01sMntExamPaperLog";
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
      importView.InWsExpll01QuestPapOption = FixedStringAttr.valueOf("F", 1);
      importView.InWsExpll01LogisticsOption = FixedStringAttr.valueOf("B", 1);
      importView.InLogEntriesGrp_MA = IntAttr.valueOf(InLogEntriesGrpMax);
      exportView.OutLogEntriesGrp_MA = IntAttr.getDefaultValue();
      exportView.OutListGroup_MA = IntAttr.getDefaultValue();
      exportView.OutEquGroup_MA = IntAttr.getDefaultValue();
   }

   Expll01h.EXPLL01S_IA importView = Expll01h.EXPLL01S_IA.getInstance();
   Expll01h.EXPLL01S_OA exportView = Expll01h.EXPLL01S_OA.getInstance();
   public short getInExamTypistLogEntryPaperNo() {
      return importView.InExamTypistLogEntryPaperNo;
   }
   public void setInExamTypistLogEntryPaperNo(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InExamTypistLogEntryPaperNo has more than 1 digits.",
               new PropertyChangeEvent (this, "InExamTypistLogEntryPaperNo", null, null));
      }
      importView.InExamTypistLogEntryPaperNo = ShortAttr.valueOf(s);
   }
   public void setAsStringInExamTypistLogEntryPaperNo(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InExamTypistLogEntryPaperNo is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamTypistLogEntryPaperNo", null, null));
      }
      try {
          setInExamTypistLogEntryPaperNo(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InExamTypistLogEntryPaperNo is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamTypistLogEntryPaperNo", null, null));
      }
   }
 
   public String getInWsExpll01QuestPapOption() {
      return FixedStringAttr.valueOf(importView.InWsExpll01QuestPapOption, 1);
   }
   public void setInWsExpll01QuestPapOption(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Expll01sMntExamPaperLogWsExpll01QuestPapOptionPropertyEditor pe = new Expll01sMntExamPaperLogWsExpll01QuestPapOptionPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InWsExpll01QuestPapOption value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsExpll01QuestPapOption", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsExpll01QuestPapOption must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsExpll01QuestPapOption", null, null));
      }
      importView.InWsExpll01QuestPapOption = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsExpll01LogisticsOption() {
      return FixedStringAttr.valueOf(importView.InWsExpll01LogisticsOption, 1);
   }
   public void setInWsExpll01LogisticsOption(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Expll01sMntExamPaperLogWsExpll01LogisticsOptionPropertyEditor pe = new Expll01sMntExamPaperLogWsExpll01LogisticsOptionPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InWsExpll01LogisticsOption value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsExpll01LogisticsOption", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsExpll01LogisticsOption must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsExpll01LogisticsOption", null, null));
      }
      importView.InWsExpll01LogisticsOption = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public int getInNrOfCombinedPapersIefSuppliedCount() {
      return importView.InNrOfCombinedPapersIefSuppliedCount;
   }
   public void setInNrOfCombinedPapersIefSuppliedCount(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000000000.0) {
         throw new PropertyVetoException("InNrOfCombinedPapersIefSuppliedCount has more than 9 digits.",
               new PropertyChangeEvent (this, "InNrOfCombinedPapersIefSuppliedCount", null, null));
      }
      importView.InNrOfCombinedPapersIefSuppliedCount = IntAttr.valueOf(s);
   }
   public void setAsStringInNrOfCombinedPapersIefSuppliedCount(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InNrOfCombinedPapersIefSuppliedCount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InNrOfCombinedPapersIefSuppliedCount", null, null));
      }
      try {
          setInNrOfCombinedPapersIefSuppliedCount(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InNrOfCombinedPapersIefSuppliedCount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InNrOfCombinedPapersIefSuppliedCount", null, null));
      }
   }
 
   public short getInNextExamTypistLogExamYear() {
      return importView.InNextExamTypistLogExamYear;
   }
   public void setInNextExamTypistLogExamYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InNextExamTypistLogExamYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InNextExamTypistLogExamYear", null, null));
      }
      importView.InNextExamTypistLogExamYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInNextExamTypistLogExamYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InNextExamTypistLogExamYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InNextExamTypistLogExamYear", null, null));
      }
      try {
          setInNextExamTypistLogExamYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InNextExamTypistLogExamYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InNextExamTypistLogExamYear", null, null));
      }
   }
 
   public short getInNextExamTypistLogMkExamPeriodCod() {
      return importView.InNextExamTypistLogMkExamPeriodCod;
   }
   public void setInNextExamTypistLogMkExamPeriodCod(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InNextExamTypistLogMkExamPeriodCod has more than 2 digits.",
               new PropertyChangeEvent (this, "InNextExamTypistLogMkExamPeriodCod", null, null));
      }
      importView.InNextExamTypistLogMkExamPeriodCod = ShortAttr.valueOf(s);
   }
   public void setAsStringInNextExamTypistLogMkExamPeriodCod(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InNextExamTypistLogMkExamPeriodCod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InNextExamTypistLogMkExamPeriodCod", null, null));
      }
      try {
          setInNextExamTypistLogMkExamPeriodCod(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InNextExamTypistLogMkExamPeriodCod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InNextExamTypistLogMkExamPeriodCod", null, null));
      }
   }
 
   public short getInPrevExamTypistLogExamYear() {
      return importView.InPrevExamTypistLogExamYear;
   }
   public void setInPrevExamTypistLogExamYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InPrevExamTypistLogExamYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InPrevExamTypistLogExamYear", null, null));
      }
      importView.InPrevExamTypistLogExamYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInPrevExamTypistLogExamYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InPrevExamTypistLogExamYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InPrevExamTypistLogExamYear", null, null));
      }
      try {
          setInPrevExamTypistLogExamYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InPrevExamTypistLogExamYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InPrevExamTypistLogExamYear", null, null));
      }
   }
 
   public short getInPrevExamTypistLogMkExamPeriodCod() {
      return importView.InPrevExamTypistLogMkExamPeriodCod;
   }
   public void setInPrevExamTypistLogMkExamPeriodCod(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InPrevExamTypistLogMkExamPeriodCod has more than 2 digits.",
               new PropertyChangeEvent (this, "InPrevExamTypistLogMkExamPeriodCod", null, null));
      }
      importView.InPrevExamTypistLogMkExamPeriodCod = ShortAttr.valueOf(s);
   }
   public void setAsStringInPrevExamTypistLogMkExamPeriodCod(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InPrevExamTypistLogMkExamPeriodCod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InPrevExamTypistLogMkExamPeriodCod", null, null));
      }
      try {
          setInPrevExamTypistLogMkExamPeriodCod(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InPrevExamTypistLogMkExamPeriodCod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InPrevExamTypistLogMkExamPeriodCod", null, null));
      }
   }
 
   public final int InLogEntriesGrpMax = 5;
   public short getInLogEntriesGrpCount() {
      return (short)(importView.InLogEntriesGrp_MA);
   };
 
   public void setInLogEntriesGrpCount(short s) throws PropertyVetoException {
      if (s < 0 || s > InLogEntriesGrpMax) {
         throw new PropertyVetoException("InLogEntriesGrpCount value is not a valid value. (0 to 5)",
               new PropertyChangeEvent (this, "InLogEntriesGrpCount", null, null));
      } else {
         importView.InLogEntriesGrp_MA = IntAttr.valueOf((int)s);
      }
   }
 
   public int getInGCalcQuantIefSuppliedCount(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return importView.InGCalcQuantIefSuppliedCount[index];
   }
   public void setInGCalcQuantIefSuppliedCount(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 1000000000.0) {
         throw new PropertyVetoException("InGCalcQuantIefSuppliedCount has more than 9 digits.",
               new PropertyChangeEvent (this, "InGCalcQuantIefSuppliedCount", null, null));
      }
      importView.InGCalcQuantIefSuppliedCount[index] = IntAttr.valueOf(s);
   }
   public void setAsStringInGCalcQuantIefSuppliedCount(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGCalcQuantIefSuppliedCount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGCalcQuantIefSuppliedCount", null, null));
      }
      try {
          setInGCalcQuantIefSuppliedCount(index, Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGCalcQuantIefSuppliedCount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGCalcQuantIefSuppliedCount", null, null));
      }
   }
 
   public String getInGPaperColourCsfStringsString10(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGPaperColourCsfStringsString10[index], 10);
   }
   public void setInGPaperColourCsfStringsString10(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InGPaperColourCsfStringsString10 must be <= 10 characters.",
               new PropertyChangeEvent (this, "InGPaperColourCsfStringsString10", null, null));
      }
      importView.InGPaperColourCsfStringsString10[index] = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public Calendar getInGExamPeriodDateDate(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toCalendar(importView.InGExamPeriodDateDate[index]);
   }
   public int getAsIntInGExamPeriodDateDate(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toInt(importView.InGExamPeriodDateDate[index]);
   }
   public void setInGExamPeriodDateDate(int index, Calendar s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      importView.InGExamPeriodDateDate[index] = DateAttr.valueOf(s);
   }
   public void setAsStringInGExamPeriodDateDate(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s == null || s.length() == 0) {
         setInGExamPeriodDateDate(index, (Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInGExamPeriodDateDate(index, tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InGExamPeriodDateDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InGExamPeriodDateDate", null, null));
         }
      }
   }
   public void setAsIntInGExamPeriodDateDate(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInGExamPeriodDateDate(index, temp);
   }
 
   public int getInGIefSuppliedCount(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return importView.InGIefSuppliedCount[index];
   }
   public void setInGIefSuppliedCount(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 1000000000.0) {
         throw new PropertyVetoException("InGIefSuppliedCount has more than 9 digits.",
               new PropertyChangeEvent (this, "InGIefSuppliedCount", null, null));
      }
      importView.InGIefSuppliedCount[index] = IntAttr.valueOf(s);
   }
   public void setAsStringInGIefSuppliedCount(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGIefSuppliedCount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGIefSuppliedCount", null, null));
      }
      try {
          setInGIefSuppliedCount(index, Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGIefSuppliedCount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGIefSuppliedCount", null, null));
      }
   }
 
   public String getInGIefSuppliedCommand(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGIefSuppliedCommand[index], 80);
   }
   public void setInGIefSuppliedCommand(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s.length() > 80) {
         throw new PropertyVetoException("InGIefSuppliedCommand must be <= 80 characters.",
               new PropertyChangeEvent (this, "InGIefSuppliedCommand", null, null));
      }
      importView.InGIefSuppliedCommand[index] = FixedStringAttr.valueOf(s, (short)80);
   }
 
   public String getInGIefSuppliedSelectChar(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGIefSuppliedSelectChar[index], 1);
   }
   public void setInGIefSuppliedSelectChar(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGIefSuppliedSelectChar must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGIefSuppliedSelectChar", null, null));
      }
      importView.InGIefSuppliedSelectChar[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInGExamTypistLogEntryPaperNo(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return importView.InGExamTypistLogEntryPaperNo[index];
   }
   public void setInGExamTypistLogEntryPaperNo(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InGExamTypistLogEntryPaperNo has more than 1 digits.",
               new PropertyChangeEvent (this, "InGExamTypistLogEntryPaperNo", null, null));
      }
      importView.InGExamTypistLogEntryPaperNo[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGExamTypistLogEntryPaperNo(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGExamTypistLogEntryPaperNo is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGExamTypistLogEntryPaperNo", null, null));
      }
      try {
          setInGExamTypistLogEntryPaperNo(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGExamTypistLogEntryPaperNo is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGExamTypistLogEntryPaperNo", null, null));
      }
   }
 
   public String getInGExamTypistLogEntryTypist(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return StringAttr.valueOf(importView.InGExamTypistLogEntryTypist[index]);
   }
   public void setInGExamTypistLogEntryTypist(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s.length() > 3) {
         throw new PropertyVetoException("InGExamTypistLogEntryTypist must be <= 3 characters.",
               new PropertyChangeEvent (this, "InGExamTypistLogEntryTypist", null, null));
      }
      importView.InGExamTypistLogEntryTypist[index] = StringAttr.valueOf(s, (short)3);
   }
 
   public String getInGExamTypistLogEntryPaperFormat(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGExamTypistLogEntryPaperFormat[index], 1);
   }
   public void setInGExamTypistLogEntryPaperFormat(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGExamTypistLogEntryPaperFormat must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGExamTypistLogEntryPaperFormat", null, null));
      }
      importView.InGExamTypistLogEntryPaperFormat[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public Calendar getInGExamTypistLogEntryDateReceived(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toCalendar(importView.InGExamTypistLogEntryDateReceived[index]);
   }
   public int getAsIntInGExamTypistLogEntryDateReceived(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toInt(importView.InGExamTypistLogEntryDateReceived[index]);
   }
   public void setInGExamTypistLogEntryDateReceived(int index, Calendar s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      importView.InGExamTypistLogEntryDateReceived[index] = DateAttr.valueOf(s);
   }
   public void setAsStringInGExamTypistLogEntryDateReceived(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s == null || s.length() == 0) {
         setInGExamTypistLogEntryDateReceived(index, (Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInGExamTypistLogEntryDateReceived(index, tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InGExamTypistLogEntryDateReceived has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InGExamTypistLogEntryDateReceived", null, null));
         }
      }
   }
   public void setAsIntInGExamTypistLogEntryDateReceived(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInGExamTypistLogEntryDateReceived(index, temp);
   }
 
   public Calendar getInGExamTypistLogEntryDateProof1(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toCalendar(importView.InGExamTypistLogEntryDateProof1[index]);
   }
   public int getAsIntInGExamTypistLogEntryDateProof1(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toInt(importView.InGExamTypistLogEntryDateProof1[index]);
   }
   public void setInGExamTypistLogEntryDateProof1(int index, Calendar s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      importView.InGExamTypistLogEntryDateProof1[index] = DateAttr.valueOf(s);
   }
   public void setAsStringInGExamTypistLogEntryDateProof1(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s == null || s.length() == 0) {
         setInGExamTypistLogEntryDateProof1(index, (Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInGExamTypistLogEntryDateProof1(index, tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InGExamTypistLogEntryDateProof1 has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InGExamTypistLogEntryDateProof1", null, null));
         }
      }
   }
   public void setAsIntInGExamTypistLogEntryDateProof1(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInGExamTypistLogEntryDateProof1(index, temp);
   }
 
   public Calendar getInGExamTypistLogEntryDateProof2(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toCalendar(importView.InGExamTypistLogEntryDateProof2[index]);
   }
   public int getAsIntInGExamTypistLogEntryDateProof2(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toInt(importView.InGExamTypistLogEntryDateProof2[index]);
   }
   public void setInGExamTypistLogEntryDateProof2(int index, Calendar s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      importView.InGExamTypistLogEntryDateProof2[index] = DateAttr.valueOf(s);
   }
   public void setAsStringInGExamTypistLogEntryDateProof2(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s == null || s.length() == 0) {
         setInGExamTypistLogEntryDateProof2(index, (Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInGExamTypistLogEntryDateProof2(index, tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InGExamTypistLogEntryDateProof2 has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InGExamTypistLogEntryDateProof2", null, null));
         }
      }
   }
   public void setAsIntInGExamTypistLogEntryDateProof2(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInGExamTypistLogEntryDateProof2(index, temp);
   }
 
   public Calendar getInGExamTypistLogEntryDateProof3(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toCalendar(importView.InGExamTypistLogEntryDateProof3[index]);
   }
   public int getAsIntInGExamTypistLogEntryDateProof3(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toInt(importView.InGExamTypistLogEntryDateProof3[index]);
   }
   public void setInGExamTypistLogEntryDateProof3(int index, Calendar s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      importView.InGExamTypistLogEntryDateProof3[index] = DateAttr.valueOf(s);
   }
   public void setAsStringInGExamTypistLogEntryDateProof3(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s == null || s.length() == 0) {
         setInGExamTypistLogEntryDateProof3(index, (Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInGExamTypistLogEntryDateProof3(index, tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InGExamTypistLogEntryDateProof3 has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InGExamTypistLogEntryDateProof3", null, null));
         }
      }
   }
   public void setAsIntInGExamTypistLogEntryDateProof3(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInGExamTypistLogEntryDateProof3(index, temp);
   }
 
   public Calendar getInGExamTypistLogEntryDateToPrint(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toCalendar(importView.InGExamTypistLogEntryDateToPrint[index]);
   }
   public int getAsIntInGExamTypistLogEntryDateToPrint(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toInt(importView.InGExamTypistLogEntryDateToPrint[index]);
   }
   public void setInGExamTypistLogEntryDateToPrint(int index, Calendar s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      importView.InGExamTypistLogEntryDateToPrint[index] = DateAttr.valueOf(s);
   }
   public void setAsStringInGExamTypistLogEntryDateToPrint(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s == null || s.length() == 0) {
         setInGExamTypistLogEntryDateToPrint(index, (Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInGExamTypistLogEntryDateToPrint(index, tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InGExamTypistLogEntryDateToPrint has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InGExamTypistLogEntryDateToPrint", null, null));
         }
      }
   }
   public void setAsIntInGExamTypistLogEntryDateToPrint(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInGExamTypistLogEntryDateToPrint(index, temp);
   }
 
   public String getInGExamTypistLogEntryCpfReportPrinted(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return StringAttr.valueOf(importView.InGExamTypistLogEntryCpfReportPrinted[index]);
   }
   public void setInGExamTypistLogEntryCpfReportPrinted(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGExamTypistLogEntryCpfReportPrinted must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGExamTypistLogEntryCpfReportPrinted", null, null));
      }
      importView.InGExamTypistLogEntryCpfReportPrinted[index] = StringAttr.valueOf(s, (short)1);
   }
 
   public Calendar getInGExamTypistLogEntryDateInSafe(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toCalendar(importView.InGExamTypistLogEntryDateInSafe[index]);
   }
   public int getAsIntInGExamTypistLogEntryDateInSafe(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toInt(importView.InGExamTypistLogEntryDateInSafe[index]);
   }
   public void setInGExamTypistLogEntryDateInSafe(int index, Calendar s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      importView.InGExamTypistLogEntryDateInSafe[index] = DateAttr.valueOf(s);
   }
   public void setAsStringInGExamTypistLogEntryDateInSafe(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s == null || s.length() == 0) {
         setInGExamTypistLogEntryDateInSafe(index, (Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInGExamTypistLogEntryDateInSafe(index, tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InGExamTypistLogEntryDateInSafe has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InGExamTypistLogEntryDateInSafe", null, null));
         }
      }
   }
   public void setAsIntInGExamTypistLogEntryDateInSafe(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInGExamTypistLogEntryDateInSafe(index, temp);
   }
 
   public Calendar getInGExamTypistLogEntryDateToDept(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toCalendar(importView.InGExamTypistLogEntryDateToDept[index]);
   }
   public int getAsIntInGExamTypistLogEntryDateToDept(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toInt(importView.InGExamTypistLogEntryDateToDept[index]);
   }
   public void setInGExamTypistLogEntryDateToDept(int index, Calendar s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      importView.InGExamTypistLogEntryDateToDept[index] = DateAttr.valueOf(s);
   }
   public void setAsStringInGExamTypistLogEntryDateToDept(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s == null || s.length() == 0) {
         setInGExamTypistLogEntryDateToDept(index, (Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInGExamTypistLogEntryDateToDept(index, tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InGExamTypistLogEntryDateToDept has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InGExamTypistLogEntryDateToDept", null, null));
         }
      }
   }
   public void setAsIntInGExamTypistLogEntryDateToDept(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInGExamTypistLogEntryDateToDept(index, temp);
   }
 
   public Calendar getInGExamTypistLogEntryDateFromDept(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toCalendar(importView.InGExamTypistLogEntryDateFromDept[index]);
   }
   public int getAsIntInGExamTypistLogEntryDateFromDept(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toInt(importView.InGExamTypistLogEntryDateFromDept[index]);
   }
   public void setInGExamTypistLogEntryDateFromDept(int index, Calendar s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      importView.InGExamTypistLogEntryDateFromDept[index] = DateAttr.valueOf(s);
   }
   public void setAsStringInGExamTypistLogEntryDateFromDept(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s == null || s.length() == 0) {
         setInGExamTypistLogEntryDateFromDept(index, (Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInGExamTypistLogEntryDateFromDept(index, tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InGExamTypistLogEntryDateFromDept has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InGExamTypistLogEntryDateFromDept", null, null));
         }
      }
   }
   public void setAsIntInGExamTypistLogEntryDateFromDept(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInGExamTypistLogEntryDateFromDept(index, temp);
   }
 
   public Calendar getInGExamTypistLogEntryDate2ToPrint(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toCalendar(importView.InGExamTypistLogEntryDate2ToPrint[index]);
   }
   public int getAsIntInGExamTypistLogEntryDate2ToPrint(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toInt(importView.InGExamTypistLogEntryDate2ToPrint[index]);
   }
   public void setInGExamTypistLogEntryDate2ToPrint(int index, Calendar s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      importView.InGExamTypistLogEntryDate2ToPrint[index] = DateAttr.valueOf(s);
   }
   public void setAsStringInGExamTypistLogEntryDate2ToPrint(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s == null || s.length() == 0) {
         setInGExamTypistLogEntryDate2ToPrint(index, (Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInGExamTypistLogEntryDate2ToPrint(index, tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InGExamTypistLogEntryDate2ToPrint has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InGExamTypistLogEntryDate2ToPrint", null, null));
         }
      }
   }
   public void setAsIntInGExamTypistLogEntryDate2ToPrint(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInGExamTypistLogEntryDate2ToPrint(index, temp);
   }
 
   public Calendar getInGExamTypistLogEntryDate2FromPrint(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toCalendar(importView.InGExamTypistLogEntryDate2FromPrint[index]);
   }
   public int getAsIntInGExamTypistLogEntryDate2FromPrint(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toInt(importView.InGExamTypistLogEntryDate2FromPrint[index]);
   }
   public void setInGExamTypistLogEntryDate2FromPrint(int index, Calendar s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      importView.InGExamTypistLogEntryDate2FromPrint[index] = DateAttr.valueOf(s);
   }
   public void setAsStringInGExamTypistLogEntryDate2FromPrint(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s == null || s.length() == 0) {
         setInGExamTypistLogEntryDate2FromPrint(index, (Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInGExamTypistLogEntryDate2FromPrint(index, tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InGExamTypistLogEntryDate2FromPrint has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InGExamTypistLogEntryDate2FromPrint", null, null));
         }
      }
   }
   public void setAsIntInGExamTypistLogEntryDate2FromPrint(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInGExamTypistLogEntryDate2FromPrint(index, temp);
   }
 
   public Calendar getInGExamTypistLogEntryDate3ToPrint(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toCalendar(importView.InGExamTypistLogEntryDate3ToPrint[index]);
   }
   public int getAsIntInGExamTypistLogEntryDate3ToPrint(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toInt(importView.InGExamTypistLogEntryDate3ToPrint[index]);
   }
   public void setInGExamTypistLogEntryDate3ToPrint(int index, Calendar s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      importView.InGExamTypistLogEntryDate3ToPrint[index] = DateAttr.valueOf(s);
   }
   public void setAsStringInGExamTypistLogEntryDate3ToPrint(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s == null || s.length() == 0) {
         setInGExamTypistLogEntryDate3ToPrint(index, (Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInGExamTypistLogEntryDate3ToPrint(index, tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InGExamTypistLogEntryDate3ToPrint has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InGExamTypistLogEntryDate3ToPrint", null, null));
         }
      }
   }
   public void setAsIntInGExamTypistLogEntryDate3ToPrint(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInGExamTypistLogEntryDate3ToPrint(index, temp);
   }
 
   public Calendar getInGExamTypistLogEntryDate3FromPrint(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toCalendar(importView.InGExamTypistLogEntryDate3FromPrint[index]);
   }
   public int getAsIntInGExamTypistLogEntryDate3FromPrint(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toInt(importView.InGExamTypistLogEntryDate3FromPrint[index]);
   }
   public void setInGExamTypistLogEntryDate3FromPrint(int index, Calendar s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      importView.InGExamTypistLogEntryDate3FromPrint[index] = DateAttr.valueOf(s);
   }
   public void setAsStringInGExamTypistLogEntryDate3FromPrint(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s == null || s.length() == 0) {
         setInGExamTypistLogEntryDate3FromPrint(index, (Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInGExamTypistLogEntryDate3FromPrint(index, tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InGExamTypistLogEntryDate3FromPrint has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InGExamTypistLogEntryDate3FromPrint", null, null));
         }
      }
   }
   public void setAsIntInGExamTypistLogEntryDate3FromPrint(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInGExamTypistLogEntryDate3FromPrint(index, temp);
   }
 
   public int getInGExamTypistLogEntryQuant3ToPrint(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return importView.InGExamTypistLogEntryQuant3ToPrint[index];
   }
   public void setInGExamTypistLogEntryQuant3ToPrint(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InGExamTypistLogEntryQuant3ToPrint has more than 5 digits.",
               new PropertyChangeEvent (this, "InGExamTypistLogEntryQuant3ToPrint", null, null));
      }
      importView.InGExamTypistLogEntryQuant3ToPrint[index] = IntAttr.valueOf(s);
   }
   public void setAsStringInGExamTypistLogEntryQuant3ToPrint(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGExamTypistLogEntryQuant3ToPrint is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGExamTypistLogEntryQuant3ToPrint", null, null));
      }
      try {
          setInGExamTypistLogEntryQuant3ToPrint(index, Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGExamTypistLogEntryQuant3ToPrint is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGExamTypistLogEntryQuant3ToPrint", null, null));
      }
   }
 
   public int getInGExamTypistLogEntryQuantToPrint(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return importView.InGExamTypistLogEntryQuantToPrint[index];
   }
   public void setInGExamTypistLogEntryQuantToPrint(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InGExamTypistLogEntryQuantToPrint has more than 5 digits.",
               new PropertyChangeEvent (this, "InGExamTypistLogEntryQuantToPrint", null, null));
      }
      importView.InGExamTypistLogEntryQuantToPrint[index] = IntAttr.valueOf(s);
   }
   public void setAsStringInGExamTypistLogEntryQuantToPrint(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGExamTypistLogEntryQuantToPrint is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGExamTypistLogEntryQuantToPrint", null, null));
      }
      try {
          setInGExamTypistLogEntryQuantToPrint(index, Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGExamTypistLogEntryQuantToPrint is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGExamTypistLogEntryQuantToPrint", null, null));
      }
   }
 
   public Calendar getInGExamTypistLogEntryQuantCalcedOn(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toCalendar(importView.InGExamTypistLogEntryQuantCalcedOn[index]);
   }
   public int getAsIntInGExamTypistLogEntryQuantCalcedOn(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toInt(importView.InGExamTypistLogEntryQuantCalcedOn[index]);
   }
   public void setInGExamTypistLogEntryQuantCalcedOn(int index, Calendar s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      importView.InGExamTypistLogEntryQuantCalcedOn[index] = DateAttr.valueOf(s);
   }
   public void setAsStringInGExamTypistLogEntryQuantCalcedOn(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s == null || s.length() == 0) {
         setInGExamTypistLogEntryQuantCalcedOn(index, (Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInGExamTypistLogEntryQuantCalcedOn(index, tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InGExamTypistLogEntryQuantCalcedOn has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InGExamTypistLogEntryQuantCalcedOn", null, null));
         }
      }
   }
   public void setAsIntInGExamTypistLogEntryQuantCalcedOn(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInGExamTypistLogEntryQuantCalcedOn(index, temp);
   }
 
   public int getInGExamTypistLogEntryQuant2ToPrint(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return importView.InGExamTypistLogEntryQuant2ToPrint[index];
   }
   public void setInGExamTypistLogEntryQuant2ToPrint(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InGExamTypistLogEntryQuant2ToPrint has more than 5 digits.",
               new PropertyChangeEvent (this, "InGExamTypistLogEntryQuant2ToPrint", null, null));
      }
      importView.InGExamTypistLogEntryQuant2ToPrint[index] = IntAttr.valueOf(s);
   }
   public void setAsStringInGExamTypistLogEntryQuant2ToPrint(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGExamTypistLogEntryQuant2ToPrint is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGExamTypistLogEntryQuant2ToPrint", null, null));
      }
      try {
          setInGExamTypistLogEntryQuant2ToPrint(index, Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGExamTypistLogEntryQuant2ToPrint is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGExamTypistLogEntryQuant2ToPrint", null, null));
      }
   }
 
   public Calendar getInGExamTypistLogEntryQuant2CalcedOn(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toCalendar(importView.InGExamTypistLogEntryQuant2CalcedOn[index]);
   }
   public int getAsIntInGExamTypistLogEntryQuant2CalcedOn(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toInt(importView.InGExamTypistLogEntryQuant2CalcedOn[index]);
   }
   public void setInGExamTypistLogEntryQuant2CalcedOn(int index, Calendar s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      importView.InGExamTypistLogEntryQuant2CalcedOn[index] = DateAttr.valueOf(s);
   }
   public void setAsStringInGExamTypistLogEntryQuant2CalcedOn(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s == null || s.length() == 0) {
         setInGExamTypistLogEntryQuant2CalcedOn(index, (Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInGExamTypistLogEntryQuant2CalcedOn(index, tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InGExamTypistLogEntryQuant2CalcedOn has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InGExamTypistLogEntryQuant2CalcedOn", null, null));
         }
      }
   }
   public void setAsIntInGExamTypistLogEntryQuant2CalcedOn(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInGExamTypistLogEntryQuant2CalcedOn(index, temp);
   }
 
   public int getInGExamTypistLogEntrySurplusQuant(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return importView.InGExamTypistLogEntrySurplusQuant[index];
   }
   public void setInGExamTypistLogEntrySurplusQuant(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InGExamTypistLogEntrySurplusQuant has more than 5 digits.",
               new PropertyChangeEvent (this, "InGExamTypistLogEntrySurplusQuant", null, null));
      }
      importView.InGExamTypistLogEntrySurplusQuant[index] = IntAttr.valueOf(s);
   }
   public void setAsStringInGExamTypistLogEntrySurplusQuant(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGExamTypistLogEntrySurplusQuant is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGExamTypistLogEntrySurplusQuant", null, null));
      }
      try {
          setInGExamTypistLogEntrySurplusQuant(index, Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGExamTypistLogEntrySurplusQuant is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGExamTypistLogEntrySurplusQuant", null, null));
      }
   }
 
   public Calendar getInGExamTypistLogEntryDateScanned(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toCalendar(importView.InGExamTypistLogEntryDateScanned[index]);
   }
   public int getAsIntInGExamTypistLogEntryDateScanned(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toInt(importView.InGExamTypistLogEntryDateScanned[index]);
   }
   public void setInGExamTypistLogEntryDateScanned(int index, Calendar s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      importView.InGExamTypistLogEntryDateScanned[index] = DateAttr.valueOf(s);
   }
   public void setAsStringInGExamTypistLogEntryDateScanned(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s == null || s.length() == 0) {
         setInGExamTypistLogEntryDateScanned(index, (Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInGExamTypistLogEntryDateScanned(index, tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InGExamTypistLogEntryDateScanned has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InGExamTypistLogEntryDateScanned", null, null));
         }
      }
   }
   public void setAsIntInGExamTypistLogEntryDateScanned(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInGExamTypistLogEntryDateScanned(index, temp);
   }
 
   public Calendar getInGExamTypistLogEntryDateAfrScanned(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toCalendar(importView.InGExamTypistLogEntryDateAfrScanned[index]);
   }
   public int getAsIntInGExamTypistLogEntryDateAfrScanned(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toInt(importView.InGExamTypistLogEntryDateAfrScanned[index]);
   }
   public void setInGExamTypistLogEntryDateAfrScanned(int index, Calendar s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      importView.InGExamTypistLogEntryDateAfrScanned[index] = DateAttr.valueOf(s);
   }
   public void setAsStringInGExamTypistLogEntryDateAfrScanned(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s == null || s.length() == 0) {
         setInGExamTypistLogEntryDateAfrScanned(index, (Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInGExamTypistLogEntryDateAfrScanned(index, tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InGExamTypistLogEntryDateAfrScanned has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InGExamTypistLogEntryDateAfrScanned", null, null));
         }
      }
   }
   public void setAsIntInGExamTypistLogEntryDateAfrScanned(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInGExamTypistLogEntryDateAfrScanned(index, temp);
   }
 
   public String getInGExamTypistLogEntryOpenForWeb(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return StringAttr.valueOf(importView.InGExamTypistLogEntryOpenForWeb[index]);
   }
   public void setInGExamTypistLogEntryOpenForWeb(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGExamTypistLogEntryOpenForWeb must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGExamTypistLogEntryOpenForWeb", null, null));
      }
      importView.InGExamTypistLogEntryOpenForWeb[index] = StringAttr.valueOf(s, (short)1);
   }
 
   public String getInGExamTypistLogEntryDocketChanges(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGExamTypistLogEntryDocketChanges[index], 1);
   }
   public void setInGExamTypistLogEntryDocketChanges(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGExamTypistLogEntryDocketChanges must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGExamTypistLogEntryDocketChanges", null, null));
      }
      importView.InGExamTypistLogEntryDocketChanges[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInGExamTypistLogEntryPaperChanges(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGExamTypistLogEntryPaperChanges[index], 1);
   }
   public void setInGExamTypistLogEntryPaperChanges(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGExamTypistLogEntryPaperChanges must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGExamTypistLogEntryPaperChanges", null, null));
      }
      importView.InGExamTypistLogEntryPaperChanges[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInGExamTypistLogEntryElectronicPaper(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGExamTypistLogEntryElectronicPaper[index], 1);
   }
   public void setInGExamTypistLogEntryElectronicPaper(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGExamTypistLogEntryElectronicPaper must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGExamTypistLogEntryElectronicPaper", null, null));
      }
      importView.InGExamTypistLogEntryElectronicPaper[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public Calendar getInGMyunisaWsRegistrationDatesFromDate(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toCalendar(importView.InGMyunisaWsRegistrationDatesFromDate[index]);
   }
   public int getAsIntInGMyunisaWsRegistrationDatesFromDate(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toInt(importView.InGMyunisaWsRegistrationDatesFromDate[index]);
   }
   public void setInGMyunisaWsRegistrationDatesFromDate(int index, Calendar s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      importView.InGMyunisaWsRegistrationDatesFromDate[index] = DateAttr.valueOf(s);
   }
   public void setAsStringInGMyunisaWsRegistrationDatesFromDate(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s == null || s.length() == 0) {
         setInGMyunisaWsRegistrationDatesFromDate(index, (Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInGMyunisaWsRegistrationDatesFromDate(index, tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InGMyunisaWsRegistrationDatesFromDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InGMyunisaWsRegistrationDatesFromDate", null, null));
         }
      }
   }
   public void setAsIntInGMyunisaWsRegistrationDatesFromDate(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInGMyunisaWsRegistrationDatesFromDate(index, temp);
   }
 
   public String getInGEngPaperCsfStringsString1(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGEngPaperCsfStringsString1[index], 1);
   }
   public void setInGEngPaperCsfStringsString1(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGEngPaperCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGEngPaperCsfStringsString1", null, null));
      }
      importView.InGEngPaperCsfStringsString1[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInGAfrPaperCsfStringsString1(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGAfrPaperCsfStringsString1[index], 1);
   }
   public void setInGAfrPaperCsfStringsString1(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGAfrPaperCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGAfrPaperCsfStringsString1", null, null));
      }
      importView.InGAfrPaperCsfStringsString1[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInSearchCriteriaExamTypistLogExamYear() {
      return importView.InSearchCriteriaExamTypistLogExamYear;
   }
   public void setInSearchCriteriaExamTypistLogExamYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InSearchCriteriaExamTypistLogExamYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InSearchCriteriaExamTypistLogExamYear", null, null));
      }
      importView.InSearchCriteriaExamTypistLogExamYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInSearchCriteriaExamTypistLogExamYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSearchCriteriaExamTypistLogExamYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSearchCriteriaExamTypistLogExamYear", null, null));
      }
      try {
          setInSearchCriteriaExamTypistLogExamYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSearchCriteriaExamTypistLogExamYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSearchCriteriaExamTypistLogExamYear", null, null));
      }
   }
 
   public short getInSearchCriteriaExamTypistLogMkExamPeriodCod() {
      return importView.InSearchCriteriaExamTypistLogMkExamPeriodCod;
   }
   public void setInSearchCriteriaExamTypistLogMkExamPeriodCod(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InSearchCriteriaExamTypistLogMkExamPeriodCod has more than 2 digits.",
               new PropertyChangeEvent (this, "InSearchCriteriaExamTypistLogMkExamPeriodCod", null, null));
      }
      importView.InSearchCriteriaExamTypistLogMkExamPeriodCod = ShortAttr.valueOf(s);
   }
   public void setAsStringInSearchCriteriaExamTypistLogMkExamPeriodCod(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSearchCriteriaExamTypistLogMkExamPeriodCod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSearchCriteriaExamTypistLogMkExamPeriodCod", null, null));
      }
      try {
          setInSearchCriteriaExamTypistLogMkExamPeriodCod(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSearchCriteriaExamTypistLogMkExamPeriodCod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSearchCriteriaExamTypistLogMkExamPeriodCod", null, null));
      }
   }
 
   public String getInSearchCriteriaExamTypistLogMkStudyUnitCode() {
      return StringAttr.valueOf(importView.InSearchCriteriaExamTypistLogMkStudyUnitCode);
   }
   public void setInSearchCriteriaExamTypistLogMkStudyUnitCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InSearchCriteriaExamTypistLogMkStudyUnitCode must be <= 7 characters.",
               new PropertyChangeEvent (this, "InSearchCriteriaExamTypistLogMkStudyUnitCode", null, null));
      }
      importView.InSearchCriteriaExamTypistLogMkStudyUnitCode = StringAttr.valueOf(s, (short)7);
   }
 
   public short getInKeyExamTypistLogExamYear() {
      return importView.InKeyExamTypistLogExamYear;
   }
   public void setInKeyExamTypistLogExamYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InKeyExamTypistLogExamYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InKeyExamTypistLogExamYear", null, null));
      }
      importView.InKeyExamTypistLogExamYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInKeyExamTypistLogExamYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InKeyExamTypistLogExamYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InKeyExamTypistLogExamYear", null, null));
      }
      try {
          setInKeyExamTypistLogExamYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InKeyExamTypistLogExamYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InKeyExamTypistLogExamYear", null, null));
      }
   }
 
   public short getInKeyExamTypistLogMkExamPeriodCod() {
      return importView.InKeyExamTypistLogMkExamPeriodCod;
   }
   public void setInKeyExamTypistLogMkExamPeriodCod(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InKeyExamTypistLogMkExamPeriodCod has more than 2 digits.",
               new PropertyChangeEvent (this, "InKeyExamTypistLogMkExamPeriodCod", null, null));
      }
      importView.InKeyExamTypistLogMkExamPeriodCod = ShortAttr.valueOf(s);
   }
   public void setAsStringInKeyExamTypistLogMkExamPeriodCod(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InKeyExamTypistLogMkExamPeriodCod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InKeyExamTypistLogMkExamPeriodCod", null, null));
      }
      try {
          setInKeyExamTypistLogMkExamPeriodCod(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InKeyExamTypistLogMkExamPeriodCod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InKeyExamTypistLogMkExamPeriodCod", null, null));
      }
   }
 
   public String getInKeyExamTypistLogMkStudyUnitCode() {
      return StringAttr.valueOf(importView.InKeyExamTypistLogMkStudyUnitCode);
   }
   public void setInKeyExamTypistLogMkStudyUnitCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InKeyExamTypistLogMkStudyUnitCode must be <= 7 characters.",
               new PropertyChangeEvent (this, "InKeyExamTypistLogMkStudyUnitCode", null, null));
      }
      importView.InKeyExamTypistLogMkStudyUnitCode = StringAttr.valueOf(s, (short)7);
   }
 
   public String getInKeyExamTypistLogCombinedWith() {
      return FixedStringAttr.valueOf(importView.InKeyExamTypistLogCombinedWith, 25);
   }
   public void setInKeyExamTypistLogCombinedWith(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 25) {
         throw new PropertyVetoException("InKeyExamTypistLogCombinedWith must be <= 25 characters.",
               new PropertyChangeEvent (this, "InKeyExamTypistLogCombinedWith", null, null));
      }
      importView.InKeyExamTypistLogCombinedWith = FixedStringAttr.valueOf(s, (short)25);
   }
 
   public String getInKeyExamTypistLogRemarks() {
      return StringAttr.valueOf(importView.InKeyExamTypistLogRemarks);
   }
   public void setInKeyExamTypistLogRemarks(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 30) {
         throw new PropertyVetoException("InKeyExamTypistLogRemarks must be <= 30 characters.",
               new PropertyChangeEvent (this, "InKeyExamTypistLogRemarks", null, null));
      }
      importView.InKeyExamTypistLogRemarks = StringAttr.valueOf(s, (short)30);
   }
 
   public String getInKeyExamTypistLogRemarks2() {
      return StringAttr.valueOf(importView.InKeyExamTypistLogRemarks2);
   }
   public void setInKeyExamTypistLogRemarks2(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 30) {
         throw new PropertyVetoException("InKeyExamTypistLogRemarks2 must be <= 30 characters.",
               new PropertyChangeEvent (this, "InKeyExamTypistLogRemarks2", null, null));
      }
      importView.InKeyExamTypistLogRemarks2 = StringAttr.valueOf(s, (short)30);
   }
 
   public String getInKeyExamTypistLogRemarks3() {
      return FixedStringAttr.valueOf(importView.InKeyExamTypistLogRemarks3, 30);
   }
   public void setInKeyExamTypistLogRemarks3(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 30) {
         throw new PropertyVetoException("InKeyExamTypistLogRemarks3 must be <= 30 characters.",
               new PropertyChangeEvent (this, "InKeyExamTypistLogRemarks3", null, null));
      }
      importView.InKeyExamTypistLogRemarks3 = FixedStringAttr.valueOf(s, (short)30);
   }
 
   public String getInKeyExamTypistLogRemarks4() {
      return StringAttr.valueOf(importView.InKeyExamTypistLogRemarks4);
   }
   public void setInKeyExamTypistLogRemarks4(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 30) {
         throw new PropertyVetoException("InKeyExamTypistLogRemarks4 must be <= 30 characters.",
               new PropertyChangeEvent (this, "InKeyExamTypistLogRemarks4", null, null));
      }
      importView.InKeyExamTypistLogRemarks4 = StringAttr.valueOf(s, (short)30);
   }
 
   public String getInKeyExamTypistLogRemarks5() {
      return StringAttr.valueOf(importView.InKeyExamTypistLogRemarks5);
   }
   public void setInKeyExamTypistLogRemarks5(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 30) {
         throw new PropertyVetoException("InKeyExamTypistLogRemarks5 must be <= 30 characters.",
               new PropertyChangeEvent (this, "InKeyExamTypistLogRemarks5", null, null));
      }
      importView.InKeyExamTypistLogRemarks5 = StringAttr.valueOf(s, (short)30);
   }
 
   public String getInKeyExamTypistLogRemarks6() {
      return StringAttr.valueOf(importView.InKeyExamTypistLogRemarks6);
   }
   public void setInKeyExamTypistLogRemarks6(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 30) {
         throw new PropertyVetoException("InKeyExamTypistLogRemarks6 must be <= 30 characters.",
               new PropertyChangeEvent (this, "InKeyExamTypistLogRemarks6", null, null));
      }
      importView.InKeyExamTypistLogRemarks6 = StringAttr.valueOf(s, (short)30);
   }
 
   public String getInKeyExamTypistLogPaperExpected() {
      return FixedStringAttr.valueOf(importView.InKeyExamTypistLogPaperExpected, 1);
   }
   public void setInKeyExamTypistLogPaperExpected(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InKeyExamTypistLogPaperExpected must be <= 1 characters.",
               new PropertyChangeEvent (this, "InKeyExamTypistLogPaperExpected", null, null));
      }
      importView.InKeyExamTypistLogPaperExpected = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public Calendar getInReportDateExamTypistLogEntryDateToPrint() {
      return DateAttr.toCalendar(importView.InReportDateExamTypistLogEntryDateToPrint);
   }
   public int getAsIntInReportDateExamTypistLogEntryDateToPrint() {
      return DateAttr.toInt(importView.InReportDateExamTypistLogEntryDateToPrint);
   }
   public void setInReportDateExamTypistLogEntryDateToPrint(Calendar s)
      throws PropertyVetoException {
      importView.InReportDateExamTypistLogEntryDateToPrint = DateAttr.valueOf(s);
   }
   public void setAsStringInReportDateExamTypistLogEntryDateToPrint(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInReportDateExamTypistLogEntryDateToPrint((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInReportDateExamTypistLogEntryDateToPrint(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InReportDateExamTypistLogEntryDateToPrint has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InReportDateExamTypistLogEntryDateToPrint", null, null));
         }
      }
   }
   public void setAsIntInReportDateExamTypistLogEntryDateToPrint(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInReportDateExamTypistLogEntryDateToPrint(temp);
   }
 
   public String getInReportWizfuncReportingControlPathAndFilename() {
      return StringAttr.valueOf(importView.InReportWizfuncReportingControlPathAndFilename);
   }
   public void setInReportWizfuncReportingControlPathAndFilename(String s)
      throws PropertyVetoException {
      if (s.length() > 256) {
         throw new PropertyVetoException("InReportWizfuncReportingControlPathAndFilename must be <= 256 characters.",
               new PropertyChangeEvent (this, "InReportWizfuncReportingControlPathAndFilename", null, null));
      }
      importView.InReportWizfuncReportingControlPathAndFilename = StringAttr.valueOf(s, (short)256);
   }
 
   public short getInOutstExamPapersWsStudyUnitPeriodDetailMkExamPeriod() {
      return importView.InOutstExamPapersWsStudyUnitPeriodDetailMkExamPeriod;
   }
   public void setInOutstExamPapersWsStudyUnitPeriodDetailMkExamPeriod(short s)
      throws PropertyVetoException {
      Expll01sMntExamPaperLogWsStudyUnitPeriodDetailMkExamPeriodPropertyEditor pe = new Expll01sMntExamPaperLogWsStudyUnitPeriodDetailMkExamPeriodPropertyEditor();
      if (pe.checkTag(String.valueOf(s)) == false) {
         throw new PropertyVetoException("InOutstExamPapersWsStudyUnitPeriodDetailMkExamPeriod value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InOutstExamPapersWsStudyUnitPeriodDetailMkExamPeriod", null, null));
      }
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InOutstExamPapersWsStudyUnitPeriodDetailMkExamPeriod has more than 2 digits.",
               new PropertyChangeEvent (this, "InOutstExamPapersWsStudyUnitPeriodDetailMkExamPeriod", null, null));
      }
      importView.InOutstExamPapersWsStudyUnitPeriodDetailMkExamPeriod = ShortAttr.valueOf(s);
   }
   public void setAsStringInOutstExamPapersWsStudyUnitPeriodDetailMkExamPeriod(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InOutstExamPapersWsStudyUnitPeriodDetailMkExamPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InOutstExamPapersWsStudyUnitPeriodDetailMkExamPeriod", null, null));
      }
      try {
          setInOutstExamPapersWsStudyUnitPeriodDetailMkExamPeriod(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InOutstExamPapersWsStudyUnitPeriodDetailMkExamPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InOutstExamPapersWsStudyUnitPeriodDetailMkExamPeriod", null, null));
      }
   }
 
   public short getInOutstExamPapersWsStudyUnitPeriodDetailMkExamYear() {
      return importView.InOutstExamPapersWsStudyUnitPeriodDetailMkExamYear;
   }
   public void setInOutstExamPapersWsStudyUnitPeriodDetailMkExamYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InOutstExamPapersWsStudyUnitPeriodDetailMkExamYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InOutstExamPapersWsStudyUnitPeriodDetailMkExamYear", null, null));
      }
      importView.InOutstExamPapersWsStudyUnitPeriodDetailMkExamYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInOutstExamPapersWsStudyUnitPeriodDetailMkExamYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InOutstExamPapersWsStudyUnitPeriodDetailMkExamYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InOutstExamPapersWsStudyUnitPeriodDetailMkExamYear", null, null));
      }
      try {
          setInOutstExamPapersWsStudyUnitPeriodDetailMkExamYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InOutstExamPapersWsStudyUnitPeriodDetailMkExamYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InOutstExamPapersWsStudyUnitPeriodDetailMkExamYear", null, null));
      }
   }
 
   public short getInOutstExamPapersWsStudyUnitPeriodDetailMkAcademicYear() {
      return importView.InOutstExamPapersWsStudyUnitPeriodDetailMkAcademicYear;
   }
   public void setInOutstExamPapersWsStudyUnitPeriodDetailMkAcademicYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InOutstExamPapersWsStudyUnitPeriodDetailMkAcademicYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InOutstExamPapersWsStudyUnitPeriodDetailMkAcademicYear", null, null));
      }
      importView.InOutstExamPapersWsStudyUnitPeriodDetailMkAcademicYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInOutstExamPapersWsStudyUnitPeriodDetailMkAcademicYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InOutstExamPapersWsStudyUnitPeriodDetailMkAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InOutstExamPapersWsStudyUnitPeriodDetailMkAcademicYear", null, null));
      }
      try {
          setInOutstExamPapersWsStudyUnitPeriodDetailMkAcademicYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InOutstExamPapersWsStudyUnitPeriodDetailMkAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InOutstExamPapersWsStudyUnitPeriodDetailMkAcademicYear", null, null));
      }
   }
 
   public short getInOutstExamPapersWsStudyUnitPeriodDetailSemesterPeriod() {
      return importView.InOutstExamPapersWsStudyUnitPeriodDetailSemesterPeriod;
   }
   public void setInOutstExamPapersWsStudyUnitPeriodDetailSemesterPeriod(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InOutstExamPapersWsStudyUnitPeriodDetailSemesterPeriod has more than 1 digits.",
               new PropertyChangeEvent (this, "InOutstExamPapersWsStudyUnitPeriodDetailSemesterPeriod", null, null));
      }
      importView.InOutstExamPapersWsStudyUnitPeriodDetailSemesterPeriod = ShortAttr.valueOf(s);
   }
   public void setAsStringInOutstExamPapersWsStudyUnitPeriodDetailSemesterPeriod(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InOutstExamPapersWsStudyUnitPeriodDetailSemesterPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InOutstExamPapersWsStudyUnitPeriodDetailSemesterPeriod", null, null));
      }
      try {
          setInOutstExamPapersWsStudyUnitPeriodDetailSemesterPeriod(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InOutstExamPapersWsStudyUnitPeriodDetailSemesterPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InOutstExamPapersWsStudyUnitPeriodDetailSemesterPeriod", null, null));
      }
   }
 
   public String getInScCpfAccessIefSuppliedFlag() {
      return FixedStringAttr.valueOf(importView.InScCpfAccessIefSuppliedFlag, 1);
   }
   public void setInScCpfAccessIefSuppliedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Expll01sMntExamPaperLogIefSuppliedFlagPropertyEditor pe = new Expll01sMntExamPaperLogIefSuppliedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InScCpfAccessIefSuppliedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InScCpfAccessIefSuppliedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InScCpfAccessIefSuppliedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InScCpfAccessIefSuppliedFlag", null, null));
      }
      importView.InScCpfAccessIefSuppliedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInScTypAccessIefSuppliedFlag() {
      return FixedStringAttr.valueOf(importView.InScTypAccessIefSuppliedFlag, 1);
   }
   public void setInScTypAccessIefSuppliedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Expll01sMntExamPaperLogIefSuppliedFlagPropertyEditor pe = new Expll01sMntExamPaperLogIefSuppliedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InScTypAccessIefSuppliedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InScTypAccessIefSuppliedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InScTypAccessIefSuppliedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InScTypAccessIefSuppliedFlag", null, null));
      }
      importView.InScTypAccessIefSuppliedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public int getInWsFunctionNumber() {
      return importView.InWsFunctionNumber;
   }
   public void setInWsFunctionNumber(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InWsFunctionNumber has more than 5 digits.",
               new PropertyChangeEvent (this, "InWsFunctionNumber", null, null));
      }
      importView.InWsFunctionNumber = IntAttr.valueOf(s);
   }
   public void setAsStringInWsFunctionNumber(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsFunctionNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsFunctionNumber", null, null));
      }
      try {
          setInWsFunctionNumber(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsFunctionNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsFunctionNumber", null, null));
      }
   }
 
   public short getInWsStudyUnitMkDepartmentCode() {
      return importView.InWsStudyUnitMkDepartmentCode;
   }
   public void setInWsStudyUnitMkDepartmentCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InWsStudyUnitMkDepartmentCode has more than 3 digits.",
               new PropertyChangeEvent (this, "InWsStudyUnitMkDepartmentCode", null, null));
      }
      importView.InWsStudyUnitMkDepartmentCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsStudyUnitMkDepartmentCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudyUnitMkDepartmentCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudyUnitMkDepartmentCode", null, null));
      }
      try {
          setInWsStudyUnitMkDepartmentCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudyUnitMkDepartmentCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudyUnitMkDepartmentCode", null, null));
      }
   }
 
   public String getInWsStudyUnitEngLongDescription() {
      return FixedStringAttr.valueOf(importView.InWsStudyUnitEngLongDescription, 168);
   }
   public void setInWsStudyUnitEngLongDescription(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 168) {
         throw new PropertyVetoException("InWsStudyUnitEngLongDescription must be <= 168 characters.",
               new PropertyChangeEvent (this, "InWsStudyUnitEngLongDescription", null, null));
      }
      importView.InWsStudyUnitEngLongDescription = FixedStringAttr.valueOf(s, (short)168);
   }
 
   public String getInWsStudyUnitCollegeFlag() {
      return FixedStringAttr.valueOf(importView.InWsStudyUnitCollegeFlag, 1);
   }
   public void setInWsStudyUnitCollegeFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudyUnitCollegeFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudyUnitCollegeFlag", null, null));
      }
      importView.InWsStudyUnitCollegeFlag = FixedStringAttr.valueOf(s, (short)1);
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
 
   public String getInSecurityWsUserEMail() {
      return FixedStringAttr.valueOf(importView.InSecurityWsUserEMail, 60);
   }
   public void setInSecurityWsUserEMail(String s)
      throws PropertyVetoException {
      if (s.length() > 60) {
         throw new PropertyVetoException("InSecurityWsUserEMail must be <= 60 characters.",
               new PropertyChangeEvent (this, "InSecurityWsUserEMail", null, null));
      }
      importView.InSecurityWsUserEMail = FixedStringAttr.valueOf(s, (short)60);
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
 
   public String getInOnlineOrInternetCsfStringsString1() {
      return FixedStringAttr.valueOf(importView.InOnlineOrInternetCsfStringsString1, 1);
   }
   public void setInOnlineOrInternetCsfStringsString1(String s)
      throws PropertyVetoException {
      if (s.length() > 1) {
         throw new PropertyVetoException("InOnlineOrInternetCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InOnlineOrInternetCsfStringsString1", null, null));
      }
      importView.InOnlineOrInternetCsfStringsString1 = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public int getOutNrOfCombinedPapersIefSuppliedCount() {
      return exportView.OutNrOfCombinedPapersIefSuppliedCount;
   }
 
   public short getOutNextExamTypistLogExamYear() {
      return exportView.OutNextExamTypistLogExamYear;
   }
 
   public short getOutNextExamTypistLogMkExamPeriodCod() {
      return exportView.OutNextExamTypistLogMkExamPeriodCod;
   }
 
   public short getOutPrevExamTypistLogExamYear() {
      return exportView.OutPrevExamTypistLogExamYear;
   }
 
   public short getOutPrevExamTypistLogMkExamPeriodCod() {
      return exportView.OutPrevExamTypistLogMkExamPeriodCod;
   }
 
   public short getOutKeyExamTypistLogExamYear() {
      return exportView.OutKeyExamTypistLogExamYear;
   }
 
   public short getOutKeyExamTypistLogMkExamPeriodCod() {
      return exportView.OutKeyExamTypistLogMkExamPeriodCod;
   }
 
   public String getOutKeyExamTypistLogMkStudyUnitCode() {
      return StringAttr.valueOf(exportView.OutKeyExamTypistLogMkStudyUnitCode);
   }
 
   public String getOutKeyExamTypistLogCombinedWith() {
      return FixedStringAttr.valueOf(exportView.OutKeyExamTypistLogCombinedWith, 25);
   }
 
   public String getOutKeyExamTypistLogRemarks() {
      return StringAttr.valueOf(exportView.OutKeyExamTypistLogRemarks);
   }
 
   public String getOutKeyExamTypistLogRemarks2() {
      return StringAttr.valueOf(exportView.OutKeyExamTypistLogRemarks2);
   }
 
   public String getOutKeyExamTypistLogRemarks3() {
      return FixedStringAttr.valueOf(exportView.OutKeyExamTypistLogRemarks3, 30);
   }
 
   public String getOutKeyExamTypistLogRemarks4() {
      return StringAttr.valueOf(exportView.OutKeyExamTypistLogRemarks4);
   }
 
   public String getOutKeyExamTypistLogRemarks5() {
      return StringAttr.valueOf(exportView.OutKeyExamTypistLogRemarks5);
   }
 
   public String getOutKeyExamTypistLogRemarks6() {
      return StringAttr.valueOf(exportView.OutKeyExamTypistLogRemarks6);
   }
 
   public String getOutKeyExamTypistLogPaperExpected() {
      return FixedStringAttr.valueOf(exportView.OutKeyExamTypistLogPaperExpected, 1);
   }
 
   public short getOutSearchCriteriaExamTypistLogExamYear() {
      return exportView.OutSearchCriteriaExamTypistLogExamYear;
   }
 
   public short getOutSearchCriteriaExamTypistLogMkExamPeriodCod() {
      return exportView.OutSearchCriteriaExamTypistLogMkExamPeriodCod;
   }
 
   public String getOutSearchCriteriaExamTypistLogMkStudyUnitCode() {
      return StringAttr.valueOf(exportView.OutSearchCriteriaExamTypistLogMkStudyUnitCode);
   }
 
   public final int OutLogEntriesGrpMax = 5;
   public short getOutLogEntriesGrpCount() {
      return (short)(exportView.OutLogEntriesGrp_MA);
   };
 
   public int getOutGCalcQuantIefSuppliedCount(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return exportView.OutGCalcQuantIefSuppliedCount[index];
   }
 
   public String getOutGPaperColourCsfStringsString10(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGPaperColourCsfStringsString10[index], 10);
   }
 
   public Calendar getOutGExamPeriodDateDate(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toCalendar(exportView.OutGExamPeriodDateDate[index]);
   }
   public int getAsIntOutGExamPeriodDateDate(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toInt(exportView.OutGExamPeriodDateDate[index]);
   }
 
   public int getOutGIefSuppliedCount(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return exportView.OutGIefSuppliedCount[index];
   }
 
   public String getOutGIefSuppliedCommand(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGIefSuppliedCommand[index], 80);
   }
 
   public String getOutGIefSuppliedSelectChar(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGIefSuppliedSelectChar[index], 1);
   }
 
   public short getOutGExamTypistLogEntryPaperNo(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return exportView.OutGExamTypistLogEntryPaperNo[index];
   }
 
   public String getOutGExamTypistLogEntryTypist(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return StringAttr.valueOf(exportView.OutGExamTypistLogEntryTypist[index]);
   }
 
   public String getOutGExamTypistLogEntryPaperFormat(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGExamTypistLogEntryPaperFormat[index], 1);
   }
 
   public Calendar getOutGExamTypistLogEntryDateReceived(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toCalendar(exportView.OutGExamTypistLogEntryDateReceived[index]);
   }
   public int getAsIntOutGExamTypistLogEntryDateReceived(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toInt(exportView.OutGExamTypistLogEntryDateReceived[index]);
   }
 
   public Calendar getOutGExamTypistLogEntryDateProof1(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toCalendar(exportView.OutGExamTypistLogEntryDateProof1[index]);
   }
   public int getAsIntOutGExamTypistLogEntryDateProof1(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toInt(exportView.OutGExamTypistLogEntryDateProof1[index]);
   }
 
   public Calendar getOutGExamTypistLogEntryDateProof2(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toCalendar(exportView.OutGExamTypistLogEntryDateProof2[index]);
   }
   public int getAsIntOutGExamTypistLogEntryDateProof2(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toInt(exportView.OutGExamTypistLogEntryDateProof2[index]);
   }
 
   public Calendar getOutGExamTypistLogEntryDateProof3(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toCalendar(exportView.OutGExamTypistLogEntryDateProof3[index]);
   }
   public int getAsIntOutGExamTypistLogEntryDateProof3(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toInt(exportView.OutGExamTypistLogEntryDateProof3[index]);
   }
 
   public Calendar getOutGExamTypistLogEntryDateToPrint(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toCalendar(exportView.OutGExamTypistLogEntryDateToPrint[index]);
   }
   public int getAsIntOutGExamTypistLogEntryDateToPrint(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toInt(exportView.OutGExamTypistLogEntryDateToPrint[index]);
   }
 
   public String getOutGExamTypistLogEntryCpfReportPrinted(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return StringAttr.valueOf(exportView.OutGExamTypistLogEntryCpfReportPrinted[index]);
   }
 
   public Calendar getOutGExamTypistLogEntryDateInSafe(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toCalendar(exportView.OutGExamTypistLogEntryDateInSafe[index]);
   }
   public int getAsIntOutGExamTypistLogEntryDateInSafe(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toInt(exportView.OutGExamTypistLogEntryDateInSafe[index]);
   }
 
   public Calendar getOutGExamTypistLogEntryDateToDept(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toCalendar(exportView.OutGExamTypistLogEntryDateToDept[index]);
   }
   public int getAsIntOutGExamTypistLogEntryDateToDept(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toInt(exportView.OutGExamTypistLogEntryDateToDept[index]);
   }
 
   public Calendar getOutGExamTypistLogEntryDateFromDept(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toCalendar(exportView.OutGExamTypistLogEntryDateFromDept[index]);
   }
   public int getAsIntOutGExamTypistLogEntryDateFromDept(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toInt(exportView.OutGExamTypistLogEntryDateFromDept[index]);
   }
 
   public Calendar getOutGExamTypistLogEntryDate2ToPrint(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toCalendar(exportView.OutGExamTypistLogEntryDate2ToPrint[index]);
   }
   public int getAsIntOutGExamTypistLogEntryDate2ToPrint(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toInt(exportView.OutGExamTypistLogEntryDate2ToPrint[index]);
   }
 
   public Calendar getOutGExamTypistLogEntryDate2FromPrint(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toCalendar(exportView.OutGExamTypistLogEntryDate2FromPrint[index]);
   }
   public int getAsIntOutGExamTypistLogEntryDate2FromPrint(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toInt(exportView.OutGExamTypistLogEntryDate2FromPrint[index]);
   }
 
   public Calendar getOutGExamTypistLogEntryDate3ToPrint(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toCalendar(exportView.OutGExamTypistLogEntryDate3ToPrint[index]);
   }
   public int getAsIntOutGExamTypistLogEntryDate3ToPrint(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toInt(exportView.OutGExamTypistLogEntryDate3ToPrint[index]);
   }
 
   public Calendar getOutGExamTypistLogEntryDate3FromPrint(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toCalendar(exportView.OutGExamTypistLogEntryDate3FromPrint[index]);
   }
   public int getAsIntOutGExamTypistLogEntryDate3FromPrint(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toInt(exportView.OutGExamTypistLogEntryDate3FromPrint[index]);
   }
 
   public int getOutGExamTypistLogEntryQuant3ToPrint(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return exportView.OutGExamTypistLogEntryQuant3ToPrint[index];
   }
 
   public int getOutGExamTypistLogEntryQuantToPrint(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return exportView.OutGExamTypistLogEntryQuantToPrint[index];
   }
 
   public Calendar getOutGExamTypistLogEntryQuantCalcedOn(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toCalendar(exportView.OutGExamTypistLogEntryQuantCalcedOn[index]);
   }
   public int getAsIntOutGExamTypistLogEntryQuantCalcedOn(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toInt(exportView.OutGExamTypistLogEntryQuantCalcedOn[index]);
   }
 
   public int getOutGExamTypistLogEntryQuant2ToPrint(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return exportView.OutGExamTypistLogEntryQuant2ToPrint[index];
   }
 
   public Calendar getOutGExamTypistLogEntryQuant2CalcedOn(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toCalendar(exportView.OutGExamTypistLogEntryQuant2CalcedOn[index]);
   }
   public int getAsIntOutGExamTypistLogEntryQuant2CalcedOn(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toInt(exportView.OutGExamTypistLogEntryQuant2CalcedOn[index]);
   }
 
   public int getOutGExamTypistLogEntrySurplusQuant(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return exportView.OutGExamTypistLogEntrySurplusQuant[index];
   }
 
   public Calendar getOutGExamTypistLogEntryDateScanned(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toCalendar(exportView.OutGExamTypistLogEntryDateScanned[index]);
   }
   public int getAsIntOutGExamTypistLogEntryDateScanned(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toInt(exportView.OutGExamTypistLogEntryDateScanned[index]);
   }
 
   public Calendar getOutGExamTypistLogEntryDateAfrScanned(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toCalendar(exportView.OutGExamTypistLogEntryDateAfrScanned[index]);
   }
   public int getAsIntOutGExamTypistLogEntryDateAfrScanned(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toInt(exportView.OutGExamTypistLogEntryDateAfrScanned[index]);
   }
 
   public String getOutGExamTypistLogEntryOpenForWeb(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return StringAttr.valueOf(exportView.OutGExamTypistLogEntryOpenForWeb[index]);
   }
 
   public String getOutGExamTypistLogEntryDocketChanges(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGExamTypistLogEntryDocketChanges[index], 1);
   }
 
   public String getOutGExamTypistLogEntryPaperChanges(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGExamTypistLogEntryPaperChanges[index], 1);
   }
 
   public String getOutGExamTypistLogEntryElectronicPaper(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGExamTypistLogEntryElectronicPaper[index], 1);
   }
 
   public Calendar getOutGMyunisaWsRegistrationDatesFromDate(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toCalendar(exportView.OutGMyunisaWsRegistrationDatesFromDate[index]);
   }
   public int getAsIntOutGMyunisaWsRegistrationDatesFromDate(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return DateAttr.toInt(exportView.OutGMyunisaWsRegistrationDatesFromDate[index]);
   }
 
   public String getOutGEngPaperCsfStringsString1(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGEngPaperCsfStringsString1[index], 1);
   }
 
   public String getOutGAfrPaperCsfStringsString1(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGAfrPaperCsfStringsString1[index], 1);
   }
 
   public String getOutGElectronicLogsCsfStringsString1(int index) throws ArrayIndexOutOfBoundsException {
      if (4 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 4, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGElectronicLogsCsfStringsString1[index], 1);
   }
 
   public String getOutScCpfAccessIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutScCpfAccessIefSuppliedFlag, 1);
   }
 
   public String getOutScTypAccessIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutScTypAccessIefSuppliedFlag, 1);
   }
 
   public String getOutIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutIefSuppliedFlag, 1);
   }
 
   public int getOutWsFunctionNumber() {
      return exportView.OutWsFunctionNumber;
   }
 
   public final int OutListGroupMax = 100;
   public short getOutListGroupCount() {
      return (short)(exportView.OutListGroup_MA);
   };
 
   public String getOutGListIefSuppliedSelectChar(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGListIefSuppliedSelectChar[index], 1);
   }
 
   public String getOutGListCsfStringsString100(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGListCsfStringsString100[index], 100);
   }
 
   public short getOutWsStudyUnitMkDepartmentCode() {
      return exportView.OutWsStudyUnitMkDepartmentCode;
   }
 
   public String getOutWsStudyUnitEngLongDescription() {
      return FixedStringAttr.valueOf(exportView.OutWsStudyUnitEngLongDescription, 168);
   }
 
   public String getOutWsStudyUnitCollegeFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudyUnitCollegeFlag, 1);
   }
 
   public String getOutErrMsgCsfStringsString500() {
      return StringAttr.valueOf(exportView.OutErrMsgCsfStringsString500);
   }
 
   public int getOutSecurityWsUserNumber() {
      return exportView.OutSecurityWsUserNumber;
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
 
   public String getOutReportWizfuncReportingControlFunction() {
      return StringAttr.valueOf(exportView.OutReportWizfuncReportingControlFunction);
   }
 
   public String getOutReportWizfuncReportingControlPathAndFilename() {
      return StringAttr.valueOf(exportView.OutReportWizfuncReportingControlPathAndFilename);
   }
 
   public short getOutReportWizfuncReportingControlReportWidth() {
      return exportView.OutReportWizfuncReportingControlReportWidth;
   }
 
   public int getOutReportWizfuncReportingControlStartingLineCount() {
      return exportView.OutReportWizfuncReportingControlStartingLineCount;
   }
 
   public int getOutReportWizfuncReportingControlEndingLineCount() {
      return exportView.OutReportWizfuncReportingControlEndingLineCount;
   }
 
   public String getOutReportWizfuncReportingControlWizfuncReturnCode() {
      return FixedStringAttr.valueOf(exportView.OutReportWizfuncReportingControlWizfuncReturnCode, 8);
   }
 
   public String getOutReportWizfuncReportingControlWizfuncReturnMessage() {
      return StringAttr.valueOf(exportView.OutReportWizfuncReportingControlWizfuncReturnMessage);
   }
 
   public final int OutEquGroupMax = 20;
   public short getOutEquGroupCount() {
      return (short)(exportView.OutEquGroup_MA);
   };
 
   public String getOutEquGWsExamEquivalentsWsEquivalentCode(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutEquGWsExamEquivalentsWsEquivalentCode[index], 7);
   }
 
   public String getOutEquGWsExamEquivalentsWsSamePaperFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutEquGWsExamEquivalentsWsSamePaperFlag[index], 1);
   }
 
   public int getOutPrintQuantityIefSuppliedCount() {
      return exportView.OutPrintQuantityIefSuppliedCount;
   }
 
};
