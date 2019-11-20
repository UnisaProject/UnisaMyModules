package Exsug07h.Common;
 
import com.ca.gen80.jprt.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.Serializable;
import java.beans.*;
import com.ca.gen80.csu.trace.*;
 
public class Exsug07sPrtAdmissionLetterTran   extends transaction
          implements java.io.Serializable
{
 
    final public String ServletName = "Exsug07h.Servlet.Exsug07sPrtAdmissionLetterServlet";
    public String getServletName()     {return ServletName; }
 
    public Exsug07sPrtAdmissionLetterTran ()
   {
      super ();
      String myName = "Exsug07sPrtAdmissionLetter()";
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, myName, "entered");
      }
    }
 
    public Exsug07sPrtAdmissionLetterTran (serverDialog sysObj,
                                  Exsug07sPrtAdmissionLetterInput viewIn,
                                  Exsug07sPrtAdmissionLetterOutput viewOut,
                                  URL url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Exsug07sPrtAdmissionLetterTran(serverDialog, serverInputIF, serverOutputIF, URL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
    public Exsug07sPrtAdmissionLetterTran (serverDialog sysObj,
                                  Exsug07sPrtAdmissionLetterInput viewIn,
                                  Exsug07sPrtAdmissionLetterOutput viewOut,
                                  String url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Exsug07sPrtAdmissionLetterTran(serverDialog, serverInputIF, serverOutputIF, StringURL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
  //--------------------------------------------------
  // Input properties accessor methods
 
  public synchronized void setInView (Exsug07sPrtAdmissionLetterInput x)
       throws PropertyVetoException
  {
    checkInViewValue (x);
    super.setInView (x);
  }
 
  public synchronized void setOutView (Exsug07sPrtAdmissionLetterOutput x)
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
    if (e.getNewValue().getClass().getName() == "Exsug07h.Exsug07sPrtAdmissionLetterInput")
      checkInViewValue ((Exsug07sPrtAdmissionLetterInput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "inView: Bad type in PropertyChangeEvent, must be Exsug07sPrtAdmissionLetterInput Type", e);
  }
 
  public synchronized void OutViewVetoableChange (PropertyChangeEvent e)
       throws PropertyVetoException
  {
    if (e.getNewValue().getClass().getName() == "Exsug07h.Exsug07sPrtAdmissionLetterOutput")
      checkOutViewValue ((Exsug07sPrtAdmissionLetterOutput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "outView: Bad type in PropertyChangeEvent, must be Exsug07sPrtAdmissionLetterOutput Type", e);
  }
 
  //--------------------------------------------------
  // check value of inputed property values
 
  protected synchronized void checkInViewValue (Exsug07sPrtAdmissionLetterInput x)
       throws PropertyVetoException
  {
    super.checkInViewValue ((serverInputIF)x);
 
    // do any additional checks needed here
  }
 
  protected synchronized void checkOutViewValue (Exsug07sPrtAdmissionLetterOutput x)
       throws PropertyVetoException
  {
    super.checkOutViewValue ((serverOutputIF)x);
 
    // do any additional checks needed here
  }
}
