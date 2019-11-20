package Expsc10h.Common;
 
import com.ca.gen80.jprt.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.Serializable;
import java.beans.*;
import com.ca.gen80.csu.trace.*;
 
public class Expsc10sLstExamPaperVenueStTran   extends transaction
          implements java.io.Serializable
{
 
    final public String ServletName = "Expsc10h.Servlet.Expsc10sLstExamPaperVenueStServlet";
    public String getServletName()     {return ServletName; }
 
    public Expsc10sLstExamPaperVenueStTran ()
   {
      super ();
      String myName = "Expsc10sLstExamPaperVenueSt()";
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, myName, "entered");
      }
    }
 
    public Expsc10sLstExamPaperVenueStTran (serverDialog sysObj,
                                  Expsc10sLstExamPaperVenueStInput viewIn,
                                  Expsc10sLstExamPaperVenueStOutput viewOut,
                                  URL url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Expsc10sLstExamPaperVenueStTran(serverDialog, serverInputIF, serverOutputIF, URL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
    public Expsc10sLstExamPaperVenueStTran (serverDialog sysObj,
                                  Expsc10sLstExamPaperVenueStInput viewIn,
                                  Expsc10sLstExamPaperVenueStOutput viewOut,
                                  String url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Expsc10sLstExamPaperVenueStTran(serverDialog, serverInputIF, serverOutputIF, StringURL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
  //--------------------------------------------------
  // Input properties accessor methods
 
  public synchronized void setInView (Expsc10sLstExamPaperVenueStInput x)
       throws PropertyVetoException
  {
    checkInViewValue (x);
    super.setInView (x);
  }
 
  public synchronized void setOutView (Expsc10sLstExamPaperVenueStOutput x)
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
    if (e.getNewValue().getClass().getName() == "Expsc10h.Expsc10sLstExamPaperVenueStInput")
      checkInViewValue ((Expsc10sLstExamPaperVenueStInput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "inView: Bad type in PropertyChangeEvent, must be Expsc10sLstExamPaperVenueStInput Type", e);
  }
 
  public synchronized void OutViewVetoableChange (PropertyChangeEvent e)
       throws PropertyVetoException
  {
    if (e.getNewValue().getClass().getName() == "Expsc10h.Expsc10sLstExamPaperVenueStOutput")
      checkOutViewValue ((Expsc10sLstExamPaperVenueStOutput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "outView: Bad type in PropertyChangeEvent, must be Expsc10sLstExamPaperVenueStOutput Type", e);
  }
 
  //--------------------------------------------------
  // check value of inputed property values
 
  protected synchronized void checkInViewValue (Expsc10sLstExamPaperVenueStInput x)
       throws PropertyVetoException
  {
    super.checkInViewValue ((serverInputIF)x);
 
    // do any additional checks needed here
  }
 
  protected synchronized void checkOutViewValue (Expsc10sLstExamPaperVenueStOutput x)
       throws PropertyVetoException
  {
    super.checkOutViewValue ((serverOutputIF)x);
 
    // do any additional checks needed here
  }
}
