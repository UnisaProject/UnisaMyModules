package Gistl01h;
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
//
//                    Source Code Generated by
//                            CA Gen r8
//
//           Copyright (c) 2012 CA. All rights reserved.
//
//    Name: GISTL01S_OA                      Date: 2012/05/15
//    User: Tanderw                          Time: 13:57:43
//
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// Import Statements
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
import java.lang.*;
import java.io.*;
import java.util.*;
import java.math.*;
import java.sql.*;
import com.ca.gen80.vwrt.*;
import com.ca.gen80.vwrt.types.*;
import com.ca.gen80.vwrt.vdf.*;
import com.ca.gen80.csu.exception.*;

// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// START OF EXPORT VIEW
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
/**
 * Internal data view storage for: GISTL01S_OA
 **/
public class GISTL01S_OA extends ViewBase implements IExportView, Serializable
{
  static VDF localVdf = null;
  
  // Entity View: OUT
  //        Type: STAFF
  /**
   * Attribute missing flag for: OutStaffPersno
   **/
  public char OutStaffPersno_AS;
  /**
   * Dynamic attribute for: OutStaffPersno
   **/
  public DynamicAttr OutStaffPersno_AT;
  /**
   * Attribute for: OutStaffPersno
   **/
  public int OutStaffPersno;
  /**
   * Attribute missing flag for: OutStaffSurname
   **/
  public char OutStaffSurname_AS;
  /**
   * Dynamic attribute for: OutStaffSurname
   **/
  public DynamicAttr OutStaffSurname_AT;
  /**
   * Attribute for: OutStaffSurname
   **/
  public String OutStaffSurname;
  /**
   * Attribute missing flag for: OutStaffInitials
   **/
  public char OutStaffInitials_AS;
  /**
   * Dynamic attribute for: OutStaffInitials
   **/
  public DynamicAttr OutStaffInitials_AT;
  /**
   * Attribute for: OutStaffInitials
   **/
  public String OutStaffInitials;
  /**
   * Attribute missing flag for: OutStaffTitle
   **/
  public char OutStaffTitle_AS;
  /**
   * Dynamic attribute for: OutStaffTitle
   **/
  public DynamicAttr OutStaffTitle_AT;
  /**
   * Attribute for: OutStaffTitle
   **/
  public String OutStaffTitle;
  /**
   * Attribute missing flag for: OutStaffName
   **/
  public char OutStaffName_AS;
  /**
   * Dynamic attribute for: OutStaffName
   **/
  public DynamicAttr OutStaffName_AT;
  /**
   * Attribute for: OutStaffName
   **/
  public String OutStaffName;
  /**
   * Attribute missing flag for: OutStaffLanguageCode
   **/
  public char OutStaffLanguageCode_AS;
  /**
   * Dynamic attribute for: OutStaffLanguageCode
   **/
  public DynamicAttr OutStaffLanguageCode_AT;
  /**
   * Attribute for: OutStaffLanguageCode
   **/
  public String OutStaffLanguageCode;
  /**
   * Attribute missing flag for: OutStaffMkDeptCode
   **/
  public char OutStaffMkDeptCode_AS;
  /**
   * Dynamic attribute for: OutStaffMkDeptCode
   **/
  public DynamicAttr OutStaffMkDeptCode_AT;
  /**
   * Attribute for: OutStaffMkDeptCode
   **/
  public short OutStaffMkDeptCode;
  /**
   * Attribute missing flag for: OutStaffResignDate
   **/
  public char OutStaffResignDate_AS;
  /**
   * Dynamic attribute for: OutStaffResignDate
   **/
  public DynamicAttr OutStaffResignDate_AT;
  /**
   * Attribute for: OutStaffResignDate
   **/
  public int OutStaffResignDate;
  /**
   * Attribute missing flag for: OutStaffEmailAddress
   **/
  public char OutStaffEmailAddress_AS;
  /**
   * Dynamic attribute for: OutStaffEmailAddress
   **/
  public DynamicAttr OutStaffEmailAddress_AT;
  /**
   * Attribute for: OutStaffEmailAddress
   **/
  public String OutStaffEmailAddress;
  /**
   * Attribute missing flag for: OutStaffContactTelno
   **/
  public char OutStaffContactTelno_AS;
  /**
   * Dynamic attribute for: OutStaffContactTelno
   **/
  public DynamicAttr OutStaffContactTelno_AT;
  /**
   * Attribute for: OutStaffContactTelno
   **/
  public String OutStaffContactTelno;
  /**
   * Attribute missing flag for: OutStaffOfficeNo
   **/
  public char OutStaffOfficeNo_AS;
  /**
   * Dynamic attribute for: OutStaffOfficeNo
   **/
  public DynamicAttr OutStaffOfficeNo_AT;
  /**
   * Attribute for: OutStaffOfficeNo
   **/
  public String OutStaffOfficeNo;
  /**
   * Attribute missing flag for: OutStaffMkSubdeptCode
   **/
  public char OutStaffMkSubdeptCode_AS;
  /**
   * Dynamic attribute for: OutStaffMkSubdeptCode
   **/
  public DynamicAttr OutStaffMkSubdeptCode_AT;
  /**
   * Attribute for: OutStaffMkSubdeptCode
   **/
  public short OutStaffMkSubdeptCode;
  /**
   * Attribute missing flag for: OutStaffOldStaffNr
   **/
  public char OutStaffOldStaffNr_AS;
  /**
   * Dynamic attribute for: OutStaffOldStaffNr
   **/
  public DynamicAttr OutStaffOldStaffNr_AT;
  /**
   * Attribute for: OutStaffOldStaffNr
   **/
  public int OutStaffOldStaffNr;
  /**
   * Attribute missing flag for: OutStaffOldInstitution
   **/
  public char OutStaffOldInstitution_AS;
  /**
   * Dynamic attribute for: OutStaffOldInstitution
   **/
  public DynamicAttr OutStaffOldInstitution_AT;
  /**
   * Attribute for: OutStaffOldInstitution
   **/
  public String OutStaffOldInstitution;
  /**
   * Attribute missing flag for: OutStaffMkRcCode
   **/
  public char OutStaffMkRcCode_AS;
  /**
   * Dynamic attribute for: OutStaffMkRcCode
   **/
  public DynamicAttr OutStaffMkRcCode_AT;
  /**
   * Attribute for: OutStaffMkRcCode
   **/
  public String OutStaffMkRcCode;
  /**
   * Attribute missing flag for: OutStaffFirstNames
   **/
  public char OutStaffFirstNames_AS;
  /**
   * Dynamic attribute for: OutStaffFirstNames
   **/
  public DynamicAttr OutStaffFirstNames_AT;
  /**
   * Attribute for: OutStaffFirstNames
   **/
  public String OutStaffFirstNames;
  /**
   * Attribute missing flag for: OutStaffDateOfBirth
   **/
  public char OutStaffDateOfBirth_AS;
  /**
   * Dynamic attribute for: OutStaffDateOfBirth
   **/
  public DynamicAttr OutStaffDateOfBirth_AT;
  /**
   * Attribute for: OutStaffDateOfBirth
   **/
  public int OutStaffDateOfBirth;
  /**
   * Attribute missing flag for: OutStaffIdNr
   **/
  public char OutStaffIdNr_AS;
  /**
   * Dynamic attribute for: OutStaffIdNr
   **/
  public DynamicAttr OutStaffIdNr_AT;
  /**
   * Attribute for: OutStaffIdNr
   **/
  public double OutStaffIdNr;
  /**
   * Attribute missing flag for: OutStaffPassportNr
   **/
  public char OutStaffPassportNr_AS;
  /**
   * Dynamic attribute for: OutStaffPassportNr
   **/
  public DynamicAttr OutStaffPassportNr_AT;
  /**
   * Attribute for: OutStaffPassportNr
   **/
  public String OutStaffPassportNr;
  /**
   * Attribute missing flag for: OutStaffTaxNr
   **/
  public char OutStaffTaxNr_AS;
  /**
   * Dynamic attribute for: OutStaffTaxNr
   **/
  public DynamicAttr OutStaffTaxNr_AT;
  /**
   * Attribute for: OutStaffTaxNr
   **/
  public String OutStaffTaxNr;
  /**
   * Attribute missing flag for: OutStaffTaxDirectiveNr
   **/
  public char OutStaffTaxDirectiveNr_AS;
  /**
   * Dynamic attribute for: OutStaffTaxDirectiveNr
   **/
  public DynamicAttr OutStaffTaxDirectiveNr_AT;
  /**
   * Attribute for: OutStaffTaxDirectiveNr
   **/
  public String OutStaffTaxDirectiveNr;
  /**
   * Attribute missing flag for: OutStaffGender
   **/
  public char OutStaffGender_AS;
  /**
   * Dynamic attribute for: OutStaffGender
   **/
  public DynamicAttr OutStaffGender_AT;
  /**
   * Attribute for: OutStaffGender
   **/
  public String OutStaffGender;
  /**
   * Attribute missing flag for: OutStaffMkRaceCode
   **/
  public char OutStaffMkRaceCode_AS;
  /**
   * Dynamic attribute for: OutStaffMkRaceCode
   **/
  public DynamicAttr OutStaffMkRaceCode_AT;
  /**
   * Attribute for: OutStaffMkRaceCode
   **/
  public String OutStaffMkRaceCode;
  /**
   * Attribute missing flag for: OutStaffNovellUserId
   **/
  public char OutStaffNovellUserId_AS;
  /**
   * Dynamic attribute for: OutStaffNovellUserId
   **/
  public DynamicAttr OutStaffNovellUserId_AT;
  /**
   * Attribute for: OutStaffNovellUserId
   **/
  public String OutStaffNovellUserId;
  /**
   * Attribute missing flag for: OutStaffBuilding
   **/
  public char OutStaffBuilding_AS;
  /**
   * Dynamic attribute for: OutStaffBuilding
   **/
  public DynamicAttr OutStaffBuilding_AT;
  /**
   * Attribute for: OutStaffBuilding
   **/
  public String OutStaffBuilding;
  /**
   * Attribute missing flag for: OutStaffCellNumber
   **/
  public char OutStaffCellNumber_AS;
  /**
   * Dynamic attribute for: OutStaffCellNumber
   **/
  public DynamicAttr OutStaffCellNumber_AT;
  /**
   * Attribute for: OutStaffCellNumber
   **/
  public String OutStaffCellNumber;
  // Entity View: OUT
  //        Type: CSF_STRINGS
  /**
   * Attribute missing flag for: OutCsfStringsString500
   **/
  public char OutCsfStringsString500_AS;
  /**
   * Dynamic attribute for: OutCsfStringsString500
   **/
  public DynamicAttr OutCsfStringsString500_AT;
  /**
   * Attribute for: OutCsfStringsString500
   **/
  public String OutCsfStringsString500;
  /**
   * Default Constructor
   **/
  
  public GISTL01S_OA()
  {
    reset();
  }
  /**
   * Copy Constructor
   **/
  
  public GISTL01S_OA(GISTL01S_OA orig)
  {
    copyFrom(orig);
  }
  /**
   * Static instance creator function
   **/
  
  public static GISTL01S_OA getInstance()
  {
    return new GISTL01S_OA();
  }
  /**
   * Static free instance method
   **/
  
  public void freeInstance()
  {
  }
  /**
   * clone constructor
   **/
  
  @Override public Object clone()
  	throws CloneNotSupportedException
  {
    return(new GISTL01S_OA(this));
  }
  /**
   * Resets all properties to the defaults.
   **/
  
  public void reset()
  {
    OutStaffPersno_AT = null;
    OutStaffPersno_AS = ' ';
    OutStaffPersno = 0;
    OutStaffSurname_AT = null;
    OutStaffSurname_AS = ' ';
    OutStaffSurname = "                            ";
    OutStaffInitials_AT = null;
    OutStaffInitials_AS = ' ';
    OutStaffInitials = "          ";
    OutStaffTitle_AT = null;
    OutStaffTitle_AS = ' ';
    OutStaffTitle = "          ";
    OutStaffName_AT = null;
    OutStaffName_AS = ' ';
    OutStaffName = "                            ";
    OutStaffLanguageCode_AT = null;
    OutStaffLanguageCode_AS = ' ';
    OutStaffLanguageCode = "  ";
    OutStaffMkDeptCode_AT = null;
    OutStaffMkDeptCode_AS = ' ';
    OutStaffMkDeptCode = 0;
    OutStaffResignDate_AT = null;
    OutStaffResignDate_AS = ' ';
    OutStaffResignDate = 00000000;
    OutStaffEmailAddress_AT = null;
    OutStaffEmailAddress_AS = ' ';
    OutStaffEmailAddress = 
      "                                                            ";
    OutStaffContactTelno_AT = null;
    OutStaffContactTelno_AS = ' ';
    OutStaffContactTelno = "                            ";
    OutStaffOfficeNo_AT = null;
    OutStaffOfficeNo_AS = ' ';
    OutStaffOfficeNo = "       ";
    OutStaffMkSubdeptCode_AT = null;
    OutStaffMkSubdeptCode_AS = ' ';
    OutStaffMkSubdeptCode = 0;
    OutStaffOldStaffNr_AT = null;
    OutStaffOldStaffNr_AS = ' ';
    OutStaffOldStaffNr = 0;
    OutStaffOldInstitution_AT = null;
    OutStaffOldInstitution_AS = ' ';
    OutStaffOldInstitution = " ";
    OutStaffMkRcCode_AT = null;
    OutStaffMkRcCode_AS = ' ';
    OutStaffMkRcCode = "";
    OutStaffFirstNames_AT = null;
    OutStaffFirstNames_AS = ' ';
    OutStaffFirstNames = "";
    OutStaffDateOfBirth_AT = null;
    OutStaffDateOfBirth_AS = ' ';
    OutStaffDateOfBirth = 00000000;
    OutStaffIdNr_AT = null;
    OutStaffIdNr_AS = ' ';
    OutStaffIdNr = 0.0;
    OutStaffPassportNr_AT = null;
    OutStaffPassportNr_AS = ' ';
    OutStaffPassportNr = "";
    OutStaffTaxNr_AT = null;
    OutStaffTaxNr_AS = ' ';
    OutStaffTaxNr = "";
    OutStaffTaxDirectiveNr_AT = null;
    OutStaffTaxDirectiveNr_AS = ' ';
    OutStaffTaxDirectiveNr = "";
    OutStaffGender_AT = null;
    OutStaffGender_AS = ' ';
    OutStaffGender = "";
    OutStaffMkRaceCode_AT = null;
    OutStaffMkRaceCode_AS = ' ';
    OutStaffMkRaceCode = "";
    OutStaffNovellUserId_AT = null;
    OutStaffNovellUserId_AS = ' ';
    OutStaffNovellUserId = "";
    OutStaffBuilding_AT = null;
    OutStaffBuilding_AS = ' ';
    OutStaffBuilding = "";
    OutStaffCellNumber_AT = null;
    OutStaffCellNumber_AS = ' ';
    OutStaffCellNumber = "";
    OutCsfStringsString500_AT = null;
    OutCsfStringsString500_AS = ' ';
    OutCsfStringsString500 = "";
  }
  /**
   * Gets the VDF array for the instance, initialized.
   **/
  
  public static VDF getViewDefinition()
  {
    if ( localVdf == null )
    {
      VDFEntry [] vdfEntries = {
        new VDFEntry((int)1, "", "OutStaff", "Staff", "Persno", 
          VDFEntry.TYPE_INT, (short)1, (short)0, 8, (short)0, null), 
        new VDFEntry((int)2, "", "OutStaff", "Staff", "Surname", 
          VDFEntry.TYPE_STRING, (short)1, (short)0, 28, (short)0, null), 
        new VDFEntry((int)3, "", "OutStaff", "Staff", "Initials", 
          VDFEntry.TYPE_STRING, (short)1, (short)0, 10, (short)0, null), 
        new VDFEntry((int)4, "", "OutStaff", "Staff", "Title", 
          VDFEntry.TYPE_STRING, (short)1, (short)0, 10, (short)0, null), 
        new VDFEntry((int)5, "", "OutStaff", "Staff", "Name", 
          VDFEntry.TYPE_STRING, (short)1, (short)0, 28, (short)0, null), 
        new VDFEntry((int)6, "", "OutStaff", "Staff", "LanguageCode", 
          VDFEntry.TYPE_STRING, (short)1, (short)0, 2, (short)0, null), 
        new VDFEntry((int)7, "", "OutStaff", "Staff", "MkDeptCode", 
          VDFEntry.TYPE_SHORT, (short)1, (short)0, 2, (short)0, null), 
        new VDFEntry((int)8, "", "OutStaff", "Staff", "ResignDate", 
          VDFEntry.TYPE_DATE, (short)1, (short)0, 8, (short)0, null), 
        new VDFEntry((int)9, "", "OutStaff", "Staff", "EmailAddress", 
          VDFEntry.TYPE_STRING, (short)1, (short)0, 60, (short)0, null), 
        new VDFEntry((int)10, "", "OutStaff", "Staff", "ContactTelno", 
          VDFEntry.TYPE_STRING, (short)1, (short)0, 28, (short)0, null), 
        new VDFEntry((int)11, "", "OutStaff", "Staff", "OfficeNo", 
          VDFEntry.TYPE_STRING, (short)1, (short)0, 7, (short)0, null), 
        new VDFEntry((int)12, "", "OutStaff", "Staff", "MkSubdeptCode", 
          VDFEntry.TYPE_SHORT, (short)1, (short)0, 2, (short)0, null), 
        new VDFEntry((int)13, "", "OutStaff", "Staff", "OldStaffNr", 
          VDFEntry.TYPE_INT, (short)1, (short)0, 8, (short)0, null), 
        new VDFEntry((int)14, "", "OutStaff", "Staff", "OldInstitution", 
          VDFEntry.TYPE_STRING, (short)1, (short)0, 1, (short)0, null), 
        new VDFEntry((int)15, "", "OutStaff", "Staff", "MkRcCode", 
          VDFEntry.TYPE_STRING, (short)1, (short)0, 6, (short)0, null), 
        new VDFEntry((int)16, "", "OutStaff", "Staff", "FirstNames", 
          VDFEntry.TYPE_STRING, (short)1, (short)0, 240, (short)0, null), 
        new VDFEntry((int)17, "", "OutStaff", "Staff", "DateOfBirth", 
          VDFEntry.TYPE_DATE, (short)1, (short)0, 8, (short)0, null), 
        new VDFEntry((int)18, "", "OutStaff", "Staff", "IdNr", 
          VDFEntry.TYPE_DOUBLE, (short)1, (short)0, 13, (short)0, null), 
        new VDFEntry((int)19, "", "OutStaff", "Staff", "PassportNr", 
          VDFEntry.TYPE_STRING, (short)1, (short)0, 13, (short)0, null), 
        new VDFEntry((int)20, "", "OutStaff", "Staff", "TaxNr", 
          VDFEntry.TYPE_STRING, (short)1, (short)0, 15, (short)0, null), 
        new VDFEntry((int)21, "", "OutStaff", "Staff", "TaxDirectiveNr", 
          VDFEntry.TYPE_STRING, (short)1, (short)0, 13, (short)0, null), 
        new VDFEntry((int)22, "", "OutStaff", "Staff", "Gender", 
          VDFEntry.TYPE_STRING, (short)1, (short)0, 1, (short)0, null), 
        new VDFEntry((int)23, "", "OutStaff", "Staff", "MkRaceCode", 
          VDFEntry.TYPE_STRING, (short)1, (short)0, 3, (short)0, null), 
        new VDFEntry((int)24, "", "OutStaff", "Staff", "NovellUserId", 
          VDFEntry.TYPE_STRING, (short)1, (short)0, 20, (short)0, null), 
        new VDFEntry((int)25, "", "OutStaff", "Staff", "Building", 
          VDFEntry.TYPE_STRING, (short)1, (short)0, 28, (short)0, null), 
        new VDFEntry((int)26, "", "OutStaff", "Staff", "CellNumber", 
          VDFEntry.TYPE_STRING, (short)1, (short)0, 20, (short)0, null), 
        new VDFEntry((int)27, "", "OutCsfStrings", "CsfStrings", "String500", 
          VDFEntry.TYPE_STRING, (short)1, (short)0, 500, (short)0, null), 
      };
      localVdf = new VDF(vdfEntries);
    }
    try {
      VDF result = (VDF)localVdf.clone();
      result.initViewData();
      return result;
    } catch( CloneNotSupportedException e ) {
      return null;
    }
  }
  
  /**
   * Sets the current state of the instance to the VDF version.
   **/
  public void setFromVDF(VDF vdf)
  {
    // predicate view item
    if ( vdf.getEntries()[0].getDataValue().getObject() != null )
    {
      OutStaffPersno = ((Integer)vdf.getEntries()[0].getDataValue().getObject())
        .intValue();
    }
    else 
    {
      OutStaffPersno = 0;
    }
    // predicate view item
    if ( vdf.getEntries()[1].getDataValue().getObject() != null )
    {
      OutStaffSurname = ((String)vdf.getEntries()[1].getDataValue().getObject())
        ;
    }
    else 
    {
      OutStaffSurname = "                            ";
    }
    // predicate view item
    if ( vdf.getEntries()[2].getDataValue().getObject() != null )
    {
      OutStaffInitials = ((String)vdf.getEntries()[2].getDataValue().getObject()
        );
    }
    else 
    {
      OutStaffInitials = "          ";
    }
    // predicate view item
    if ( vdf.getEntries()[3].getDataValue().getObject() != null )
    {
      OutStaffTitle = ((String)vdf.getEntries()[3].getDataValue().getObject());
    }
    else 
    {
      OutStaffTitle = "          ";
    }
    // predicate view item
    if ( vdf.getEntries()[4].getDataValue().getObject() != null )
    {
      OutStaffName = ((String)vdf.getEntries()[4].getDataValue().getObject());
    }
    else 
    {
      OutStaffName = "                            ";
    }
    // predicate view item
    if ( vdf.getEntries()[5].getDataValue().getObject() != null )
    {
      OutStaffLanguageCode = ((String)vdf.getEntries()[5].getDataValue()
        .getObject());
    }
    else 
    {
      OutStaffLanguageCode = "  ";
    }
    // predicate view item
    if ( vdf.getEntries()[6].getDataValue().getObject() != null )
    {
      OutStaffMkDeptCode = ((Short)vdf.getEntries()[6].getDataValue().getObject(
        )).shortValue();
    }
    else 
    {
      OutStaffMkDeptCode = 0;
    }
    // predicate view item
    if ( vdf.getEntries()[7].getDataValue().getObject() != null )
    {
      OutStaffResignDate = ((Integer)vdf.getEntries()[7].getDataValue()
        .getObject()).intValue();
    }
    else 
    {
      OutStaffResignDate = 00000000;
    }
    // predicate view item
    if ( vdf.getEntries()[8].getDataValue().getObject() != null )
    {
      OutStaffEmailAddress = ((String)vdf.getEntries()[8].getDataValue()
        .getObject());
    }
    else 
    {
      OutStaffEmailAddress = 
        "                                                            ";
    }
    // predicate view item
    if ( vdf.getEntries()[9].getDataValue().getObject() != null )
    {
      OutStaffContactTelno = ((String)vdf.getEntries()[9].getDataValue()
        .getObject());
    }
    else 
    {
      OutStaffContactTelno = "                            ";
    }
    // predicate view item
    if ( vdf.getEntries()[10].getDataValue().getObject() != null )
    {
      OutStaffOfficeNo = ((String)vdf.getEntries()[10].getDataValue().getObject(
        ));
    }
    else 
    {
      OutStaffOfficeNo = "       ";
    }
    // predicate view item
    if ( vdf.getEntries()[11].getDataValue().getObject() != null )
    {
      OutStaffMkSubdeptCode = ((Short)vdf.getEntries()[11].getDataValue()
        .getObject()).shortValue();
    }
    else 
    {
      OutStaffMkSubdeptCode = 0;
    }
    // predicate view item
    if ( vdf.getEntries()[12].getDataValue().getObject() != null )
    {
      OutStaffOldStaffNr = ((Integer)vdf.getEntries()[12].getDataValue()
        .getObject()).intValue();
    }
    else 
    {
      OutStaffOldStaffNr = 0;
    }
    // predicate view item
    if ( vdf.getEntries()[13].getDataValue().getObject() != null )
    {
      OutStaffOldInstitution = ((String)vdf.getEntries()[13].getDataValue()
        .getObject());
    }
    else 
    {
      OutStaffOldInstitution = " ";
    }
    // predicate view item
    if ( vdf.getEntries()[14].getDataValue().getObject() != null )
    {
      OutStaffMkRcCode = ((String)vdf.getEntries()[14].getDataValue().getObject(
        ));
    }
    else 
    {
      OutStaffMkRcCode = "";
    }
    // predicate view item
    if ( vdf.getEntries()[15].getDataValue().getObject() != null )
    {
      OutStaffFirstNames = ((String)vdf.getEntries()[15].getDataValue()
        .getObject());
    }
    else 
    {
      OutStaffFirstNames = "";
    }
    // predicate view item
    if ( vdf.getEntries()[16].getDataValue().getObject() != null )
    {
      OutStaffDateOfBirth = ((Integer)vdf.getEntries()[16].getDataValue()
        .getObject()).intValue();
    }
    else 
    {
      OutStaffDateOfBirth = 00000000;
    }
    // predicate view item
    if ( vdf.getEntries()[17].getDataValue().getObject() != null )
    {
      OutStaffIdNr = ((Double)vdf.getEntries()[17].getDataValue().getObject())
        .doubleValue();
    }
    else 
    {
      OutStaffIdNr = 0.0;
    }
    // predicate view item
    if ( vdf.getEntries()[18].getDataValue().getObject() != null )
    {
      OutStaffPassportNr = ((String)vdf.getEntries()[18].getDataValue()
        .getObject());
    }
    else 
    {
      OutStaffPassportNr = "";
    }
    // predicate view item
    if ( vdf.getEntries()[19].getDataValue().getObject() != null )
    {
      OutStaffTaxNr = ((String)vdf.getEntries()[19].getDataValue().getObject())
        ;
    }
    else 
    {
      OutStaffTaxNr = "";
    }
    // predicate view item
    if ( vdf.getEntries()[20].getDataValue().getObject() != null )
    {
      OutStaffTaxDirectiveNr = ((String)vdf.getEntries()[20].getDataValue()
        .getObject());
    }
    else 
    {
      OutStaffTaxDirectiveNr = "";
    }
    // predicate view item
    if ( vdf.getEntries()[21].getDataValue().getObject() != null )
    {
      OutStaffGender = ((String)vdf.getEntries()[21].getDataValue().getObject())
        ;
    }
    else 
    {
      OutStaffGender = "";
    }
    // predicate view item
    if ( vdf.getEntries()[22].getDataValue().getObject() != null )
    {
      OutStaffMkRaceCode = ((String)vdf.getEntries()[22].getDataValue()
        .getObject());
    }
    else 
    {
      OutStaffMkRaceCode = "";
    }
    // predicate view item
    if ( vdf.getEntries()[23].getDataValue().getObject() != null )
    {
      OutStaffNovellUserId = ((String)vdf.getEntries()[23].getDataValue()
        .getObject());
    }
    else 
    {
      OutStaffNovellUserId = "";
    }
    // predicate view item
    if ( vdf.getEntries()[24].getDataValue().getObject() != null )
    {
      OutStaffBuilding = ((String)vdf.getEntries()[24].getDataValue().getObject(
        ));
    }
    else 
    {
      OutStaffBuilding = "";
    }
    // predicate view item
    if ( vdf.getEntries()[25].getDataValue().getObject() != null )
    {
      OutStaffCellNumber = ((String)vdf.getEntries()[25].getDataValue()
        .getObject());
    }
    else 
    {
      OutStaffCellNumber = "";
    }
    // predicate view item
    if ( vdf.getEntries()[26].getDataValue().getObject() != null )
    {
      OutCsfStringsString500 = ((String)vdf.getEntries()[26].getDataValue()
        .getObject());
    }
    else 
    {
      OutCsfStringsString500 = "";
    }
  }
  
  /**
   * Gets the VDF version of the current state of the instance.
   **/
  public VDF getVDF()
  {
    VDF vdf = getViewDefinition();
    // predicate view item
    vdf.getEntries()[0].getDataValue().setObject(new Integer(OutStaffPersno));
    // predicate view item
    vdf.getEntries()[1].getDataValue().setObject(OutStaffSurname);
    // predicate view item
    vdf.getEntries()[2].getDataValue().setObject(OutStaffInitials);
    // predicate view item
    vdf.getEntries()[3].getDataValue().setObject(OutStaffTitle);
    // predicate view item
    vdf.getEntries()[4].getDataValue().setObject(OutStaffName);
    // predicate view item
    vdf.getEntries()[5].getDataValue().setObject(OutStaffLanguageCode);
    // predicate view item
    vdf.getEntries()[6].getDataValue().setObject(new Short(OutStaffMkDeptCode))
      ;
    // predicate view item
    vdf.getEntries()[7].getDataValue().setObject(new Integer(OutStaffResignDate)
      );
    // predicate view item
    vdf.getEntries()[8].getDataValue().setObject(OutStaffEmailAddress);
    // predicate view item
    vdf.getEntries()[9].getDataValue().setObject(OutStaffContactTelno);
    // predicate view item
    vdf.getEntries()[10].getDataValue().setObject(OutStaffOfficeNo);
    // predicate view item
    vdf.getEntries()[11].getDataValue().setObject(new Short(
      OutStaffMkSubdeptCode));
    // predicate view item
    vdf.getEntries()[12].getDataValue().setObject(new Integer(
      OutStaffOldStaffNr));
    // predicate view item
    vdf.getEntries()[13].getDataValue().setObject(OutStaffOldInstitution);
    // predicate view item
    vdf.getEntries()[14].getDataValue().setObject(OutStaffMkRcCode);
    // predicate view item
    vdf.getEntries()[15].getDataValue().setObject(OutStaffFirstNames);
    // predicate view item
    vdf.getEntries()[16].getDataValue().setObject(new Integer(
      OutStaffDateOfBirth));
    // predicate view item
    vdf.getEntries()[17].getDataValue().setObject(new Double(OutStaffIdNr));
    // predicate view item
    vdf.getEntries()[18].getDataValue().setObject(OutStaffPassportNr);
    // predicate view item
    vdf.getEntries()[19].getDataValue().setObject(OutStaffTaxNr);
    // predicate view item
    vdf.getEntries()[20].getDataValue().setObject(OutStaffTaxDirectiveNr);
    // predicate view item
    vdf.getEntries()[21].getDataValue().setObject(OutStaffGender);
    // predicate view item
    vdf.getEntries()[22].getDataValue().setObject(OutStaffMkRaceCode);
    // predicate view item
    vdf.getEntries()[23].getDataValue().setObject(OutStaffNovellUserId);
    // predicate view item
    vdf.getEntries()[24].getDataValue().setObject(OutStaffBuilding);
    // predicate view item
    vdf.getEntries()[25].getDataValue().setObject(OutStaffCellNumber);
    // predicate view item
    vdf.getEntries()[26].getDataValue().setObject(OutCsfStringsString500);
    return(vdf);
  }
  
  /**
   * Sets the current instance based on the passed view.
   **/
  public void copyFrom(IExportView orig)
  {
    this.copyFrom((GISTL01S_OA) orig);
  }
  
  /**
   * Sets the current instance based on the passed view.
   **/
  public void copyFrom(GISTL01S_OA orig)
  {
    OutStaffPersno_AT = orig.OutStaffPersno_AT;
    OutStaffPersno_AS = orig.OutStaffPersno_AS;
    OutStaffPersno = orig.OutStaffPersno;
    OutStaffSurname_AT = orig.OutStaffSurname_AT;
    OutStaffSurname_AS = orig.OutStaffSurname_AS;
    OutStaffSurname = orig.OutStaffSurname;
    OutStaffInitials_AT = orig.OutStaffInitials_AT;
    OutStaffInitials_AS = orig.OutStaffInitials_AS;
    OutStaffInitials = orig.OutStaffInitials;
    OutStaffTitle_AT = orig.OutStaffTitle_AT;
    OutStaffTitle_AS = orig.OutStaffTitle_AS;
    OutStaffTitle = orig.OutStaffTitle;
    OutStaffName_AT = orig.OutStaffName_AT;
    OutStaffName_AS = orig.OutStaffName_AS;
    OutStaffName = orig.OutStaffName;
    OutStaffLanguageCode_AT = orig.OutStaffLanguageCode_AT;
    OutStaffLanguageCode_AS = orig.OutStaffLanguageCode_AS;
    OutStaffLanguageCode = orig.OutStaffLanguageCode;
    OutStaffMkDeptCode_AT = orig.OutStaffMkDeptCode_AT;
    OutStaffMkDeptCode_AS = orig.OutStaffMkDeptCode_AS;
    OutStaffMkDeptCode = orig.OutStaffMkDeptCode;
    OutStaffResignDate_AT = orig.OutStaffResignDate_AT;
    OutStaffResignDate_AS = orig.OutStaffResignDate_AS;
    OutStaffResignDate = orig.OutStaffResignDate;
    OutStaffEmailAddress_AT = orig.OutStaffEmailAddress_AT;
    OutStaffEmailAddress_AS = orig.OutStaffEmailAddress_AS;
    OutStaffEmailAddress = orig.OutStaffEmailAddress;
    OutStaffContactTelno_AT = orig.OutStaffContactTelno_AT;
    OutStaffContactTelno_AS = orig.OutStaffContactTelno_AS;
    OutStaffContactTelno = orig.OutStaffContactTelno;
    OutStaffOfficeNo_AT = orig.OutStaffOfficeNo_AT;
    OutStaffOfficeNo_AS = orig.OutStaffOfficeNo_AS;
    OutStaffOfficeNo = orig.OutStaffOfficeNo;
    OutStaffMkSubdeptCode_AT = orig.OutStaffMkSubdeptCode_AT;
    OutStaffMkSubdeptCode_AS = orig.OutStaffMkSubdeptCode_AS;
    OutStaffMkSubdeptCode = orig.OutStaffMkSubdeptCode;
    OutStaffOldStaffNr_AT = orig.OutStaffOldStaffNr_AT;
    OutStaffOldStaffNr_AS = orig.OutStaffOldStaffNr_AS;
    OutStaffOldStaffNr = orig.OutStaffOldStaffNr;
    OutStaffOldInstitution_AT = orig.OutStaffOldInstitution_AT;
    OutStaffOldInstitution_AS = orig.OutStaffOldInstitution_AS;
    OutStaffOldInstitution = orig.OutStaffOldInstitution;
    OutStaffMkRcCode_AT = orig.OutStaffMkRcCode_AT;
    OutStaffMkRcCode_AS = orig.OutStaffMkRcCode_AS;
    OutStaffMkRcCode = orig.OutStaffMkRcCode;
    OutStaffFirstNames_AT = orig.OutStaffFirstNames_AT;
    OutStaffFirstNames_AS = orig.OutStaffFirstNames_AS;
    OutStaffFirstNames = orig.OutStaffFirstNames;
    OutStaffDateOfBirth_AT = orig.OutStaffDateOfBirth_AT;
    OutStaffDateOfBirth_AS = orig.OutStaffDateOfBirth_AS;
    OutStaffDateOfBirth = orig.OutStaffDateOfBirth;
    OutStaffIdNr_AT = orig.OutStaffIdNr_AT;
    OutStaffIdNr_AS = orig.OutStaffIdNr_AS;
    OutStaffIdNr = orig.OutStaffIdNr;
    OutStaffPassportNr_AT = orig.OutStaffPassportNr_AT;
    OutStaffPassportNr_AS = orig.OutStaffPassportNr_AS;
    OutStaffPassportNr = orig.OutStaffPassportNr;
    OutStaffTaxNr_AT = orig.OutStaffTaxNr_AT;
    OutStaffTaxNr_AS = orig.OutStaffTaxNr_AS;
    OutStaffTaxNr = orig.OutStaffTaxNr;
    OutStaffTaxDirectiveNr_AT = orig.OutStaffTaxDirectiveNr_AT;
    OutStaffTaxDirectiveNr_AS = orig.OutStaffTaxDirectiveNr_AS;
    OutStaffTaxDirectiveNr = orig.OutStaffTaxDirectiveNr;
    OutStaffGender_AT = orig.OutStaffGender_AT;
    OutStaffGender_AS = orig.OutStaffGender_AS;
    OutStaffGender = orig.OutStaffGender;
    OutStaffMkRaceCode_AT = orig.OutStaffMkRaceCode_AT;
    OutStaffMkRaceCode_AS = orig.OutStaffMkRaceCode_AS;
    OutStaffMkRaceCode = orig.OutStaffMkRaceCode;
    OutStaffNovellUserId_AT = orig.OutStaffNovellUserId_AT;
    OutStaffNovellUserId_AS = orig.OutStaffNovellUserId_AS;
    OutStaffNovellUserId = orig.OutStaffNovellUserId;
    OutStaffBuilding_AT = orig.OutStaffBuilding_AT;
    OutStaffBuilding_AS = orig.OutStaffBuilding_AS;
    OutStaffBuilding = orig.OutStaffBuilding;
    OutStaffCellNumber_AT = orig.OutStaffCellNumber_AT;
    OutStaffCellNumber_AS = orig.OutStaffCellNumber_AS;
    OutStaffCellNumber = orig.OutStaffCellNumber;
    OutCsfStringsString500_AT = orig.OutCsfStringsString500_AT;
    OutCsfStringsString500_AS = orig.OutCsfStringsString500_AS;
    OutCsfStringsString500 = orig.OutCsfStringsString500;
  }
}
