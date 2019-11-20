package Grgdg01h.Common;
 
import com.ca.gen80.jprt.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.Serializable;
import java.beans.*;
import com.ca.gen80.csu.trace.*;
 
public class Grgdg01sMntStudentGraduationTran   extends transaction
          implements java.io.Serializable
{
 
    final public String ServletName = "Grgdg01h.Servlet.Grgdg01sMntStudentGraduationServlet";
    public String getServletName()     {return ServletName; }
 
    public Grgdg01sMntStudentGraduationTran ()
   {
      super ();
      String myName = "Grgdg01sMntStudentGraduation()";
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, myName, "entered");
      }
    }
 
    public Grgdg01sMntStudentGraduationTran (serverDialog sysObj,
                                  Grgdg01sMntStudentGraduationInput viewIn,
                                  Grgdg01sMntStudentGraduationOutput viewOut,
                                  URL url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Grgdg01sMntStudentGraduationTran(serverDialog, serverInputIF, serverOutputIF, URL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
    public Grgdg01sMntStudentGraduationTran (serverDialog sysObj,
                                  Grgdg01sMntStudentGraduationInput viewIn,
                                  Grgdg01sMntStudentGraduationOutput viewOut,
                                  String url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Grgdg01sMntStudentGraduationTran(serverDialog, serverInputIF, serverOutputIF, StringURL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
  //--------------------------------------------------
  // Input properties accessor methods
 
  public synchronized void setInView (Grgdg01sMntStudentGraduationInput x)
       throws PropertyVetoException
  {
    checkInViewValue (x);
    super.setInView (x);
  }
 
  public synchronized void setOutView (Grgdg01sMntStudentGraduationOutput x)
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
    if (e.getNewValue().getClass().getName() == "Grgdg01h.Grgdg01sMntStudentGraduationInput")
      checkInViewValue ((Grgdg01sMntStudentGraduationInput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "inView: Bad type in PropertyChangeEvent, must be Grgdg01sMntStudentGraduationInput Type", e);
  }
 
  public synchronized void OutViewVetoableChange (PropertyChangeEvent e)
       throws PropertyVetoException
  {
    if (e.getNewValue().getClass().getName() == "Grgdg01h.Grgdg01sMntStudentGraduationOutput")
      checkOutViewValue ((Grgdg01sMntStudentGraduationOutput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "outView: Bad type in PropertyChangeEvent, must be Grgdg01sMntStudentGraduationOutput Type", e);
  }
 
  //--------------------------------------------------
  // check value of inputed property values
 
  protected synchronized void checkInViewValue (Grgdg01sMntStudentGraduationInput x)
       throws PropertyVetoException
  {
    super.checkInViewValue ((serverInputIF)x);
 
    // do any additional checks needed here
  }
 
  protected synchronized void checkOutViewValue (Grgdg01sMntStudentGraduationOutput x)
       throws PropertyVetoException
  {
    super.checkOutViewValue ((serverOutputIF)x);
 
    // do any additional checks needed here
  }
}
