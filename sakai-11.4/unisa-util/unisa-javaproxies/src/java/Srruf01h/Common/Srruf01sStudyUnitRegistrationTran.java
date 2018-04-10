package Srruf01h.Common;
 
import com.ca.gen80.jprt.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.Serializable;
import java.beans.*;
import com.ca.gen80.csu.trace.*;
 
public class Srruf01sStudyUnitRegistrationTran   extends transaction
          implements java.io.Serializable
{
 
    final public String ServletName = "Srruf01h.Servlet.Srruf01sStudyUnitRegistrationServlet";
    public String getServletName()     {return ServletName; }
 
    public Srruf01sStudyUnitRegistrationTran ()
   {
      super ();
      String myName = "Srruf01sStudyUnitRegistration()";
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, myName, "entered");
      }
    }
 
    public Srruf01sStudyUnitRegistrationTran (serverDialog sysObj,
                                  Srruf01sStudyUnitRegistrationInput viewIn,
                                  Srruf01sStudyUnitRegistrationOutput viewOut,
                                  URL url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Srruf01sStudyUnitRegistrationTran(serverDialog, serverInputIF, serverOutputIF, URL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
    public Srruf01sStudyUnitRegistrationTran (serverDialog sysObj,
                                  Srruf01sStudyUnitRegistrationInput viewIn,
                                  Srruf01sStudyUnitRegistrationOutput viewOut,
                                  String url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Srruf01sStudyUnitRegistrationTran(serverDialog, serverInputIF, serverOutputIF, StringURL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
  //--------------------------------------------------
  // Input properties accessor methods
 
  public synchronized void setInView (Srruf01sStudyUnitRegistrationInput x)
       throws PropertyVetoException
  {
    checkInViewValue (x);
    super.setInView (x);
  }
 
  public synchronized void setOutView (Srruf01sStudyUnitRegistrationOutput x)
       throws PropertyVetoException
  {
    checkOutViewValue (x);
    super.setOutView (x);
  }
 
  //--------------------------------------------------
  // Process vetoable change requests for input properties with permitted values
 
  public synchronized void InViewVetoableChange (PropertyChangeEvent e)
       throws PropertyVetoException
  {
    if (e.getNewValue().getClass().getName() == "Srruf01h.Srruf01sStudyUnitRegistrationInput")
      checkInViewValue ((Srruf01sStudyUnitRegistrationInput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "inView: Bad type in PropertyChangeEvent, must be Srruf01sStudyUnitRegistrationInput Type", e);
  }
 
  public synchronized void OutViewVetoableChange (PropertyChangeEvent e)
       throws PropertyVetoException
  {
    if (e.getNewValue().getClass().getName() == "Srruf01h.Srruf01sStudyUnitRegistrationOutput")
      checkOutViewValue ((Srruf01sStudyUnitRegistrationOutput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "outView: Bad type in PropertyChangeEvent, must be Srruf01sStudyUnitRegistrationOutput Type", e);
  }
 
  //--------------------------------------------------
  // check value of inputed property values
 
  protected synchronized void checkInViewValue (Srruf01sStudyUnitRegistrationInput x)
       throws PropertyVetoException
  {
    super.checkInViewValue ((serverInputIF)x);
 
    // do any additional checks needed here
  }
 
  protected synchronized void checkOutViewValue (Srruf01sStudyUnitRegistrationOutput x)
       throws PropertyVetoException
  {
    super.checkOutViewValue ((serverOutputIF)x);
 
    // do any additional checks needed here
  }
}
