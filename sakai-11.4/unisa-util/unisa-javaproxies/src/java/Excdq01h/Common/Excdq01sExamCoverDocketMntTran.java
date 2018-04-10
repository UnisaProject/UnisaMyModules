package Excdq01h.Common;
 
import com.ca.gen80.jprt.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.Serializable;
import java.beans.*;
import com.ca.gen80.csu.trace.*;
 
public class Excdq01sExamCoverDocketMntTran   extends transaction
          implements java.io.Serializable
{
 
    final public String ServletName = "Excdq01h.Servlet.Excdq01sExamCoverDocketMntServlet";
    public String getServletName()     {return ServletName; }
 
    public Excdq01sExamCoverDocketMntTran ()
   {
      super ();
      String myName = "Excdq01sExamCoverDocketMnt()";
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, myName, "entered");
      }
    }
 
    public Excdq01sExamCoverDocketMntTran (serverDialog sysObj,
                                  Excdq01sExamCoverDocketMntInput viewIn,
                                  Excdq01sExamCoverDocketMntOutput viewOut,
                                  URL url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Excdq01sExamCoverDocketMntTran(serverDialog, serverInputIF, serverOutputIF, URL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
    public Excdq01sExamCoverDocketMntTran (serverDialog sysObj,
                                  Excdq01sExamCoverDocketMntInput viewIn,
                                  Excdq01sExamCoverDocketMntOutput viewOut,
                                  String url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Excdq01sExamCoverDocketMntTran(serverDialog, serverInputIF, serverOutputIF, StringURL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
  //--------------------------------------------------
  // Input properties accessor methods
 
  public synchronized void setInView (Excdq01sExamCoverDocketMntInput x)
       throws PropertyVetoException
  {
    checkInViewValue (x);
    super.setInView (x);
  }
 
  public synchronized void setOutView (Excdq01sExamCoverDocketMntOutput x)
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
    if (e.getNewValue().getClass().getName() == "Excdq01h.Excdq01sExamCoverDocketMntInput")
      checkInViewValue ((Excdq01sExamCoverDocketMntInput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "inView: Bad type in PropertyChangeEvent, must be Excdq01sExamCoverDocketMntInput Type", e);
  }
 
  public synchronized void OutViewVetoableChange (PropertyChangeEvent e)
       throws PropertyVetoException
  {
    if (e.getNewValue().getClass().getName() == "Excdq01h.Excdq01sExamCoverDocketMntOutput")
      checkOutViewValue ((Excdq01sExamCoverDocketMntOutput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "outView: Bad type in PropertyChangeEvent, must be Excdq01sExamCoverDocketMntOutput Type", e);
  }
 
  //--------------------------------------------------
  // check value of inputed property values
 
  protected synchronized void checkInViewValue (Excdq01sExamCoverDocketMntInput x)
       throws PropertyVetoException
  {
    super.checkInViewValue ((serverInputIF)x);
 
    // do any additional checks needed here
  }
 
  protected synchronized void checkOutViewValue (Excdq01sExamCoverDocketMntOutput x)
       throws PropertyVetoException
  {
    super.checkOutViewValue ((serverOutputIF)x);
 
    // do any additional checks needed here
  }
}
