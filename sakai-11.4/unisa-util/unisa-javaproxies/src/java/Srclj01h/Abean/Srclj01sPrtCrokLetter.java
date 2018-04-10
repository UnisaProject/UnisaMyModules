package Srclj01h.Abean;
 
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
public class Srclj01sPrtCrokLetter  implements ActionListener, java.io.Serializable  {
 
   //  Use final String for the bean name
   public static final String BEANNAME = new String("Srclj01sPrtCrokLetter");
 
   //  Constants for Asynchronous status
   public static final int PENDING = CoopFlow.DATA_NOT_READY;
   public static final int AVAILABLE = CoopFlow.DATA_READY;
   public static final int INVALID_ID = CoopFlow.INVALID_ID;
 
   public Srclj01sPrtCrokLetter() {
      super();
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
 
 
   private Srclj01sPrtCrokLetterOperation oper = null;
 
   /**
    * Calls the transaction/procedure step on the server.
    *
    * @exception java.beans.PropertyVetoException
    * Final property checks can throw this exception.
    */
   public void execute()  throws PropertyVetoException {
      try  {
         if (oper == null) {
            oper = new Srclj01sPrtCrokLetterOperation(this);
            addCompletionListener(new operListener(this));
            addExceptionListener(new operListener(this));
         }
 

 
         oper.doSrclj01sPrtCrokLetterOperation();
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
 
   /**
    * Calls the transaction/procedure step on the server.
    * Input and Export data are passed via XML streams.
    *
    */
   public void executeXML(java.io.Reader requestXML,
                          java.io.Writer responseXML) throws NullPointerException, IOException {
      if (requestXML == null)
      {
         throw new NullPointerException("requestXML parameter to executeXML may not be null");
      }
      if (responseXML == null)
      {
         throw new NullPointerException("responseXML parameter to executeXML may not be null");
      }
 
      PrintWriter output = new PrintWriter(responseXML);
 
      Srclj01sPrtCrokLetterParser parser = new Srclj01sPrtCrokLetterParser();
      try  {
         if (oper == null) {
            oper = new Srclj01sPrtCrokLetterOperation(this);
         }
 

 
         parser.parse(requestXML);
 
         oper.doSrclj01sPrtCrokLetterOperation();
 
         parser.buildOutput(output);
 
         output.flush();
      }
      catch (Throwable e)  {
         parser.buildError(output, e);
         output.flush();
         return;
      }
   }
 
   private String saxParser = "org.apache.xerces.parsers.SAXParser";
   /**
    * Sets the SAX parser classname to be used for XML parsing.
    *
    * @param s a string containing the classname
    */
   public void setSAXParser(String s) {
      if (s == null) saxParser = "";
      else  saxParser = s;
   }
   /**
    * Gets the SAX parser classname to be used for XML parsing.
    *
    * @return a string the classname
    */
   public String getSAXParser() {
      return saxParser;
   }
 
 
   private class operListener implements ActionListener, java.io.Serializable  {
      private Srclj01sPrtCrokLetter rP;
      operListener(Srclj01sPrtCrokLetter r)  {
         rP = r;
      }
      public void actionPerformed(ActionEvent aEvent)  {
         if (Trace.isTracing(Trace.MASK_APPLICATION))
         {
            Trace.record(Trace.MASK_APPLICATION, "Srclj01sPrtCrokLetter", "Listener heard that Srclj01sPrtCrokLetterOperation completed with " + 
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of completion of operation Srclj01sPrtCrokLetter ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      synchronized (this)  {
         targets = (Vector) completionListeners.clone();
      }
      actionEvt = new ActionEvent(this, 0, "Completion.Srclj01sPrtCrokLetter");
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of exception of operation Srclj01sPrtCrokLetter ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      String failCommand = "Exception.Srclj01sPrtCrokLetter";
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

   Srclj01h.SRCLJ01S_IA importView = Srclj01h.SRCLJ01S_IA.getInstance();
   Srclj01h.SRCLJ01S_OA exportView = Srclj01h.SRCLJ01S_OA.getInstance();
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
 
   public String getInFaxNoCsfStringsString132() {
      return FixedStringAttr.valueOf(importView.InFaxNoCsfStringsString132, 132);
   }
   public void setInFaxNoCsfStringsString132(String s)
      throws PropertyVetoException {
      if (s.length() > 132) {
         throw new PropertyVetoException("InFaxNoCsfStringsString132 must be <= 132 characters.",
               new PropertyChangeEvent (this, "InFaxNoCsfStringsString132", null, null));
      }
      importView.InFaxNoCsfStringsString132 = FixedStringAttr.valueOf(s, (short)132);
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
 
   public String getInNoOfCopiesCsfStringsString1() {
      return FixedStringAttr.valueOf(importView.InNoOfCopiesCsfStringsString1, 1);
   }
   public void setInNoOfCopiesCsfStringsString1(String s)
      throws PropertyVetoException {
      if (s.length() > 1) {
         throw new PropertyVetoException("InNoOfCopiesCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InNoOfCopiesCsfStringsString1", null, null));
      }
      importView.InNoOfCopiesCsfStringsString1 = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInComment1CsfStringsString100() {
      return FixedStringAttr.valueOf(importView.InComment1CsfStringsString100, 100);
   }
   public void setInComment1CsfStringsString100(String s)
      throws PropertyVetoException {
      if (s.length() > 100) {
         throw new PropertyVetoException("InComment1CsfStringsString100 must be <= 100 characters.",
               new PropertyChangeEvent (this, "InComment1CsfStringsString100", null, null));
      }
      importView.InComment1CsfStringsString100 = FixedStringAttr.valueOf(s, (short)100);
   }
 
   public String getInComment2CsfStringsString100() {
      return FixedStringAttr.valueOf(importView.InComment2CsfStringsString100, 100);
   }
   public void setInComment2CsfStringsString100(String s)
      throws PropertyVetoException {
      if (s.length() > 100) {
         throw new PropertyVetoException("InComment2CsfStringsString100 must be <= 100 characters.",
               new PropertyChangeEvent (this, "InComment2CsfStringsString100", null, null));
      }
      importView.InComment2CsfStringsString100 = FixedStringAttr.valueOf(s, (short)100);
   }
 
   public String getInComment3CsfStringsString100() {
      return FixedStringAttr.valueOf(importView.InComment3CsfStringsString100, 100);
   }
   public void setInComment3CsfStringsString100(String s)
      throws PropertyVetoException {
      if (s.length() > 100) {
         throw new PropertyVetoException("InComment3CsfStringsString100 must be <= 100 characters.",
               new PropertyChangeEvent (this, "InComment3CsfStringsString100", null, null));
      }
      importView.InComment3CsfStringsString100 = FixedStringAttr.valueOf(s, (short)100);
   }
 
   public String getInComment4CsfStringsString100() {
      return FixedStringAttr.valueOf(importView.InComment4CsfStringsString100, 100);
   }
   public void setInComment4CsfStringsString100(String s)
      throws PropertyVetoException {
      if (s.length() > 100) {
         throw new PropertyVetoException("InComment4CsfStringsString100 must be <= 100 characters.",
               new PropertyChangeEvent (this, "InComment4CsfStringsString100", null, null));
      }
      importView.InComment4CsfStringsString100 = FixedStringAttr.valueOf(s, (short)100);
   }
 
   public String getInComment5CsfStringsString100() {
      return FixedStringAttr.valueOf(importView.InComment5CsfStringsString100, 100);
   }
   public void setInComment5CsfStringsString100(String s)
      throws PropertyVetoException {
      if (s.length() > 100) {
         throw new PropertyVetoException("InComment5CsfStringsString100 must be <= 100 characters.",
               new PropertyChangeEvent (this, "InComment5CsfStringsString100", null, null));
      }
      importView.InComment5CsfStringsString100 = FixedStringAttr.valueOf(s, (short)100);
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
 
   public String getInDuplicateIefSuppliedFlag() {
      return FixedStringAttr.valueOf(importView.InDuplicateIefSuppliedFlag, 1);
   }
   public void setInDuplicateIefSuppliedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srclj01sPrtCrokLetterIefSuppliedFlagPropertyEditor pe = new Srclj01sPrtCrokLetterIefSuppliedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InDuplicateIefSuppliedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InDuplicateIefSuppliedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InDuplicateIefSuppliedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InDuplicateIefSuppliedFlag", null, null));
      }
      importView.InDuplicateIefSuppliedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public int getInWsUserNumber() {
      return importView.InWsUserNumber;
   }
   public void setInWsUserNumber(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InWsUserNumber has more than 5 digits.",
               new PropertyChangeEvent (this, "InWsUserNumber", null, null));
      }
      importView.InWsUserNumber = IntAttr.valueOf(s);
   }
   public void setAsStringInWsUserNumber(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsUserNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsUserNumber", null, null));
      }
      try {
          setInWsUserNumber(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsUserNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsUserNumber", null, null));
      }
   }
 
   public String getInWizfuncReportingControlPrinterCode() {
      return StringAttr.valueOf(importView.InWizfuncReportingControlPrinterCode);
   }
   public void setInWizfuncReportingControlPrinterCode(String s)
      throws PropertyVetoException {
      if (s.length() > 10) {
         throw new PropertyVetoException("InWizfuncReportingControlPrinterCode must be <= 10 characters.",
               new PropertyChangeEvent (this, "InWizfuncReportingControlPrinterCode", null, null));
      }
      importView.InWizfuncReportingControlPrinterCode = StringAttr.valueOf(s, (short)10);
   }
 
   public short getOutCsfClientServerCommunicationsReturnCode() {
      return exportView.OutCsfClientServerCommunicationsReturnCode;
   }
 
   public String getOutNameCsfStringsString40() {
      return FixedStringAttr.valueOf(exportView.OutNameCsfStringsString40, 40);
   }
 
   public String getOutFaxOrEmailCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.OutFaxOrEmailCsfStringsString1, 1);
   }
 
   public String getOutFaxNameCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutFaxNameCsfStringsString132, 132);
   }
 
   public String getOutFaxNoCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutFaxNoCsfStringsString132, 132);
   }
 
   public String getOutEmailToCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutEmailToCsfStringsString132, 132);
   }
 
   public String getOutEmailFromCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutEmailFromCsfStringsString132, 132);
   }
 
   public String getOutNoOfCopiesCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.OutNoOfCopiesCsfStringsString1, 1);
   }
 
   public String getOutErrmsgCsfStringsString500() {
      return StringAttr.valueOf(exportView.OutErrmsgCsfStringsString500);
   }
 
   private class Srclj01sPrtCrokLetterParser
                  extends org.xml.sax.helpers.DefaultHandler
                  implements org.xml.sax.ErrorHandler  {
       private int currentLevel;
       private short [] numberOfChildren = new short[20];
       private String [] elementNames = new String[20];
       private String noNamespaceSchemaLocation;
       private StringBuffer textBuffer = new StringBuffer();
 
       public Srclj01sPrtCrokLetterParser() {
       }
 
 
       /**
        * Internal method to parse special characters in XML exception data.
        **/
       private String dumpException(Throwable z) {
           StringWriter sw = new StringWriter();
           PrintWriter pw = new PrintWriter(sw);
           z.printStackTrace(pw);
           return(escapeText(sw.toString()));
       }
 
       private String escapeText(String input) {
           StringBuffer result = new StringBuffer();
           for (int i = 0; i < input.length(); i++)
           {
               switch (input.charAt(i))
               {
                   case '&':  result.append("&amp;"); break;
                   case '<':  result.append("&lt;"); break;
                   case '>':  result.append("&gt;"); break;
                   case '\'': result.append("&apos;"); break;
                   case '\"': result.append("&quot;"); break;
                   default:   result.append(input.charAt(i)); break;
               }
           }
           return result.toString();
       }
 
       public void parse(Reader input) throws Exception {
           System.setProperty("org.xml.sax.driver", getSAXParser());
           org.xml.sax.XMLReader xmlReader = org.xml.sax.helpers.XMLReaderFactory.createXMLReader();
           xmlReader.setErrorHandler(this);
           xmlReader.setContentHandler(this);
           xmlReader.setFeature("http://xml.org/sax/features/validation", true);
           xmlReader.setFeature("http://xml.org/sax/features/namespaces", true);
           try {
               xmlReader.setFeature("http://apache.org/xml/features/validation/dynamic", true);
           } catch (org.xml.sax.SAXNotRecognizedException e) {
              // This was an optional feature, just continue
           } catch (org.xml.sax.SAXNotSupportedException e) {
              // This was an optional feature, just continue
           }
           xmlReader.parse(new org.xml.sax.InputSource(input));
       }
 
       public void buildError(PrintWriter output, Throwable e) {
           output.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
           output.println("");
           output.print("<Srclj01sPrtCrokLetter xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
           if (noNamespaceSchemaLocation != null && noNamespaceSchemaLocation.length() > 0)
           {
               output.println("");
               output.print("               xsi:noNamespaceSchemaLocation=\"" + noNamespaceSchemaLocation + "\"");
           }
           output.println(">");
           output.println("");
           output.println(" <error>");
           if (e instanceof org.xml.sax.SAXParseException)
           {
              // Error generated by the parser
              // Unpack the delivered exception to get the exception it contains
              Throwable  x = e;
              if (((org.xml.sax.SAXParseException)e).getException() != null)
                  x = (Throwable)((org.xml.sax.SAXParseException)e).getException();
              output.println("  <type>" + x.getClass().getName() + "</type>");
              output.print("  <description>LINE: " + ((org.xml.sax.SAXParseException)e).getLineNumber() + ", URI: " + ((org.xml.sax.SAXParseException)e).getSystemId());
              output.print(", MESSAGE: " + escapeText(x.toString()));
              output.println("</description>");
              output.println("  <trace>");
              output.print(dumpException(x));
              output.println("  </trace>");
           }
           else if (e instanceof org.xml.sax.SAXException)
           {
              // Error generated by this application
              // (or a parser-initialization error)
              Throwable  x = e;
              if (((org.xml.sax.SAXException)e).getException() != null)
                  x = (Throwable)((org.xml.sax.SAXException)e).getException();
              output.println("  <type>" + x.getClass().getName() + "</type>");
              output.println("  <description>" + escapeText(x.toString())+ "</description>");
              output.println("  <trace>");
              output.print(dumpException(x));
              output.println("  </trace>");
           }
           else
           {
              output.println("  <type>" + e.getClass().getName() + "</type>");
              output.println("  <description>" + escapeText(e.toString())+ "</description>");
              output.println("  <trace>");
              output.print(dumpException(e));
              output.println("  </trace>");
           }
           output.println(" </error>");
           output.println("");
           output.println("</Srclj01sPrtCrokLetter>");
           output.flush();
       }
 
       public void buildOutput(PrintWriter output) throws Exception {
           output.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
           output.println("");
           output.print("<Srclj01sPrtCrokLetter xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
           if (noNamespaceSchemaLocation != null && noNamespaceSchemaLocation.length() > 0)
           {
               output.println("");
               output.print("               xsi:noNamespaceSchemaLocation=\"" + noNamespaceSchemaLocation + "\"");
           }
           output.println(">");
           output.println("");
           output.println(" <response command=\"" + escapeText(getCommandReturned()) + "\"");
           output.println("           exitState=\"" + getExitStateReturned() + "\"");
           switch (getExitStateType())
           {
              case IInMessage.TYPE_INFORMATIONAL:
                 output.println("           exitStateType=\"Informational\"");
                 break;
              case IInMessage.TYPE_WARNING:
                 output.println("           exitStateType=\"Warning\"");
                 break;
              case IInMessage.TYPE_ERROR:
                 output.println("           exitStateType=\"Error\"");
                 break;
              case IInMessage.TYPE_UNKNOWN:
              default:
                 output.println("           exitStateType=\"OK\"");
                 break;
           }
           output.print("           exitStateMsg=\"" + escapeText(getExitStateMsg()) + "\"");
           output.println(">");
           output.println("  <OutCsfClientServerCommunications>");
            output.print("   <ReturnCode>");
            output.print(escapeText(ShortAttr.toString(exportView.OutCsfClientServerCommunicationsReturnCode)));
            output.println("</ReturnCode>");
           output.println("  </OutCsfClientServerCommunications>");
           output.println("  <OutNameCsfStrings>");
            output.print("   <String40>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutNameCsfStringsString40, 40)));
            output.println("</String40>");
           output.println("  </OutNameCsfStrings>");
           output.println("  <OutFaxOrEmailCsfStrings>");
            output.print("   <String1>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutFaxOrEmailCsfStringsString1, 1)));
            output.println("</String1>");
           output.println("  </OutFaxOrEmailCsfStrings>");
           output.println("  <OutFaxNameCsfStrings>");
            output.print("   <String132>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutFaxNameCsfStringsString132, 132)));
            output.println("</String132>");
           output.println("  </OutFaxNameCsfStrings>");
           output.println("  <OutFaxNoCsfStrings>");
            output.print("   <String132>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutFaxNoCsfStringsString132, 132)));
            output.println("</String132>");
           output.println("  </OutFaxNoCsfStrings>");
           output.println("  <OutEmailToCsfStrings>");
            output.print("   <String132>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutEmailToCsfStringsString132, 132)));
            output.println("</String132>");
           output.println("  </OutEmailToCsfStrings>");
           output.println("  <OutEmailFromCsfStrings>");
            output.print("   <String132>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutEmailFromCsfStringsString132, 132)));
            output.println("</String132>");
           output.println("  </OutEmailFromCsfStrings>");
           output.println("  <OutNoOfCopiesCsfStrings>");
            output.print("   <String1>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutNoOfCopiesCsfStringsString1, 1)));
            output.println("</String1>");
           output.println("  </OutNoOfCopiesCsfStrings>");
           output.println("  <OutErrmsgCsfStrings>");
            output.print("   <String500>");
            output.print(escapeText(StringAttr.toString(exportView.OutErrmsgCsfStringsString500)));
            output.println("</String500>");
           output.println("  </OutErrmsgCsfStrings>");
           output.println(" </response>");
           output.println("");
           output.println("</Srclj01sPrtCrokLetter>");
           output.flush();
       }
 
       public void error(org.xml.sax.SAXParseException e) throws org.xml.sax.SAXException {
           throw e;
       }
 
       public void fatalError(org.xml.sax.SAXParseException e) throws org.xml.sax.SAXException {
           throw e;
       }
 
       public void startDocument() throws org.xml.sax.SAXException {
           currentLevel = 0;
           numberOfChildren[0] = 0;
           elementNames[0] = "";
       }
 
       public void startElement(String namespaceURI,
                                String simpleName,
                                String qualifiedName,
                                org.xml.sax.Attributes attrs) throws org.xml.sax.SAXException {
           textBuffer.setLength(0);
 
           numberOfChildren[currentLevel]++;
           currentLevel++;
 
           String tmpName = simpleName;
           if ("".equals(tmpName) == true)
               tmpName = qualifiedName;
 
           if (currentLevel == 0)
               elementNames[currentLevel] = tmpName;
           else
               elementNames[currentLevel] = elementNames[currentLevel-1] + tmpName;
 
           String attrName;
           if (attrs != null && "Srclj01sPrtCrokLetter".equals(tmpName) == true)
           {
               for (int i = 0; i < attrs.getLength(); i++)
               {
                   attrName = attrs.getLocalName(i);
                   if ("".equals(attrName) == true)
                       attrName = attrs.getQName(i);
 
                   if ("noNamespaceSchemaLocation".equals(attrName) == true)
                   {
                       noNamespaceSchemaLocation = attrs.getValue(i);
                       break;
                   }
               }
           }
           else if (attrs != null && tmpName.equals("request") == true)
           {
               for (int i = 0; i < attrs.getLength(); i++)
               {
                   attrName = attrs.getLocalName(i);
                   if ("".equals(attrName) == true)
                       attrName = attrs.getQName(i);
 
                   if ("command".equals(attrName) == true)
                       setCommandSent(attrs.getValue(i));
                   else if ("clientId".equals(attrName) == true)
                       setClientId(attrs.getValue(i));
                   else if ("clientPassword".equals(attrName) == true)
                       setClientPassword(attrs.getValue(i));
                   else if ("nextLocation".equals(attrName) == true)
                       setNextLocation(attrs.getValue(i));
                   else if ("exitState".equals(attrName) == true)
                   {
                       try {
                           setExitStateSent(Integer.parseInt(attrs.getValue(i)));
                       } catch (NumberFormatException e) {
                           throw new org.xml.sax.SAXException(e);
                       }
                   }
                   else if ("dialect".equals(attrName) == true)
                       setDialect(attrs.getValue(i));
                   else if ("comCfg".equals(attrName) == true)
                       setComCfg(attrs.getValue(i));
                   else if ("serverLocation".equals(attrName) == true)
                   {
                       try {
                           setServerLocation(new java.net.URL(attrs.getValue(i)));
                       } catch (java.net.MalformedURLException e) {
                           throw new org.xml.sax.SAXException(e);
                       }
                   }
                   else if ("servletPath".equals(attrName) == true)
                       setServletPath(attrs.getValue(i));
                   else if ("fileEncoding".equals(attrName) == true)
                       setFileEncoding(attrs.getValue(i));
                   else if ("tracing".equals(attrName) == true &&
                            "true".equals(attrs.getValue(i)) == true)
                       setTracing(1);
                   else if ("tracing".equals(attrName) == true &&
                            "false".equals(attrs.getValue(i)) == true)
                       setTracing(0);
               }
           }
       }
 
       public void endElement(String namespaceURI,
                              String simpleName,
                              String qualifiedName) throws org.xml.sax.SAXException {
           try {
              if (elementNames[currentLevel].endsWith("InCsfClientServerCommunicationsAction") == true) {
                  setInCsfClientServerCommunicationsAction(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InFaxOrEmailCsfStringsString1") == true) {
                  setInFaxOrEmailCsfStringsString1(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InFaxNameCsfStringsString132") == true) {
                  setInFaxNameCsfStringsString132(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InFaxNoCsfStringsString132") == true) {
                  setInFaxNoCsfStringsString132(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InEmailToCsfStringsString132") == true) {
                  setInEmailToCsfStringsString132(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InEmailFromCsfStringsString132") == true) {
                  setInEmailFromCsfStringsString132(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InNoOfCopiesCsfStringsString1") == true) {
                  setInNoOfCopiesCsfStringsString1(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InComment1CsfStringsString100") == true) {
                  setInComment1CsfStringsString100(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InComment2CsfStringsString100") == true) {
                  setInComment2CsfStringsString100(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InComment3CsfStringsString100") == true) {
                  setInComment3CsfStringsString100(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InComment4CsfStringsString100") == true) {
                  setInComment4CsfStringsString100(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InComment5CsfStringsString100") == true) {
                  setInComment5CsfStringsString100(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InStudentAnnualRecordMkStudentNr") == true) {
                  setAsStringInStudentAnnualRecordMkStudentNr(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InStudentAnnualRecordMkAcademicYear") == true) {
                  setAsStringInStudentAnnualRecordMkAcademicYear(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InStudentAnnualRecordMkAcademicPeriod") == true) {
                  setAsStringInStudentAnnualRecordMkAcademicPeriod(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InDuplicateIefSuppliedFlag") == true) {
                  setInDuplicateIefSuppliedFlag(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InWsUserNumber") == true) {
                  setAsStringInWsUserNumber(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InWizfuncReportingControlPrinterCode") == true) {
                  setInWizfuncReportingControlPrinterCode(textBuffer.toString());
              }
           } catch (PropertyVetoException e) {
               throw new org.xml.sax.SAXException(e);
           }
           numberOfChildren[currentLevel] = 0;
           elementNames[currentLevel] = "";
           currentLevel--;
       }
 
       public void characters(char buffer[],
                              int offset,
                              int length) throws org.xml.sax.SAXException {
           textBuffer.append(new String(buffer, offset, length));
       }
   }

};
