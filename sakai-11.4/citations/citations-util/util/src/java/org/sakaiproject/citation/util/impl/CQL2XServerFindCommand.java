package org.sakaiproject.citation.util.impl;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import lombok.extern.slf4j.Slf4j;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

@Slf4j
public class CQL2XServerFindCommand extends org.xml.sax.helpers.DefaultHandler
implements org.sakaiproject.citation.util.api.CQL2MetasearchCommand {

	// index mappings
	private static final java.util.Map INDEX_MAP = new java.util.HashMap();
	static {
		INDEX_MAP.put( "keyword", "WRD" );
		INDEX_MAP.put( "title", "WTI" );
		INDEX_MAP.put( "author", "WAU" );
		INDEX_MAP.put( "subject", "WSU" );
		INDEX_MAP.put( "year", "WYR" );
	}
	
	// boolean relation mappings
	private static final java.util.Map BOOL_RELATION_MAP = new java.util.HashMap();
	static {
		BOOL_RELATION_MAP.put( "and", "%20AND%20" );
		BOOL_RELATION_MAP.put( "or", "%20OR%20" );
	}
	
	// for SAX Parsing
	SAXParser saxParser;
	StringBuilder textBuffer;
	StringBuilder searchClause;
	boolean inSearchClause;
	java.util.Stack cqlStack;
	
	public CQL2XServerFindCommand() {
		// initialize stack
		cqlStack = new java.util.Stack();
		
		// initialize SAX Parser
		SAXParserFactory factory;
		
		factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware( true );
		try {
			saxParser = factory.newSAXParser();
		} catch (SAXException sxe) {
            // Error generated by this application
            // (or a parser-initialization error)
            Exception x = sxe;

            if (sxe.getException() != null) {
                x = sxe.getException();
            }

            log.warn( "CQL2XServerFindCommand() SAX exception: " + sxe.getMessage(),
            		x );
        } catch (ParserConfigurationException pce) {
            // Parser with specified options can't be built
        	log.warn( "CQL2XServerFindCommand() SAX parser cannot be built with " +
        			"specified options" );
        }
	}
	
	/**
	 * Converts a CQL-formatted search query into a format that the X-Server
	 * can understand.  Uses org.z3950.zing.cql.CQLNode.toXCQL() and SAX Parsing
	 * to convert the cqlSearchQuery into an X-Server find_command.
	 * 
	 * @param cqlSearchQuery CQL-formatted search query.
	 * @return X-Server find_command or null if cqlSearchQuery is null or empty.
	 * @see org.z3950.zing.cql.CQLNode.toXCQL()
	 */
	public String doCQL2MetasearchCommand( String cqlSearchQuery ) {
		if( cqlSearchQuery == null || cqlSearchQuery.equals( "" ) ) {
			return null;
		}
		
		org.z3950.zing.cql.CQLParser parser = new org.z3950.zing.cql.CQLParser();
		org.z3950.zing.cql.CQLNode root = null;
		try {
			// parse the criteria
			root = parser.parse( cqlSearchQuery );
		} catch( java.io.IOException ioe ) {
			log.warn( "CQL2XServerFindCommand.doCQL2MetasearchCommand() IO " +
					"exception while parsing: " + ioe.getMessage() ); 
		} catch( org.z3950.zing.cql.CQLParseException e ) {
			log.warn( "CQL2XServerFindCommand.doCQL2MetasearchCommand() CQL " +
					"parsing exception while parsing: " + e.getMessage() ); 
		}
		
		if (root == null)
		{
			return null;
		}
		
		String cqlXml = root.toXCQL( 0 );

		// get cqlXml as a stream
		java.io.ByteArrayInputStream byteInputStream = null;
		try {
			byteInputStream = new java.io.ByteArrayInputStream(
					cqlXml.getBytes( "UTF8" ) );
		} catch( java.io.UnsupportedEncodingException uee ) {
			log.warn( "CQL2XServerFindCommand.doCQL2MetasearchCommand() " +
					"unsupported encoding: " + uee.getMessage() ); 
		}
		
		if (byteInputStream == null)
		{
			return null;
		}
		
		// clear the stack
		cqlStack.removeAllElements();
		
		// run the parser
		try {
			saxParser.parse( byteInputStream, this );
			byteInputStream.close();
		} catch( java.io.IOException ioe ) {
			log.warn( "CQL2XServerFindCommand.doCQL2MetasearchCommand() " +
					"unable to close byteStream: " + ioe.getMessage() );
		} catch( org.xml.sax.SAXException sxe ) {
			// Error generated by this application
            // (or a parser-initialization error)
            Exception x = sxe;

            if (sxe.getException() != null) {
                x = sxe.getException();
            }

            log.warn( "CQL2XServerFindCommand() SAX exception: " +
            		sxe.getMessage(), x );
		}

		return ( String ) cqlStack.pop();
	}
	
	//----------------------------------
	// DEFAULT HANDLER IMPLEMENTATIONS -
	//----------------------------------

	/**
	 * Receive notification of the beginning of an element.
	 *   
	 * @see org.xml.sax.helpers.DefaultHandler
	 */
	public void startElement( String namespaceURI, String sName,
			String qName, Attributes attrs ) throws SAXException {
		// set flags to avoid overwriting duplicate tag data
		if( qName.equals( "searchClause" ) ) {
			inSearchClause = true;
		}
	}

	/**
	 * Receive notification of the end of an element.
	 *   
	 * @see org.xml.sax.helpers.DefaultHandler
	 */
	public void endElement( String namespaceURI, String sName, String qName ) 
	throws SAXException {
		// extract data
		extractDataFromText( qName );
		
		// clear flags
		if( qName.equals( "searchClause" ) ) {
			inSearchClause = false;
		}
	}

	/**
	 * Receive notification of character data inside an element.
	 *   
	 * @see org.xml.sax.helpers.DefaultHandler
	 */
	public void characters( char[] buf, int offset, int len )
	throws SAXException {
		// store character data
		String text = new String( buf, offset, len );
		
		if( textBuffer == null ) {
			textBuffer = new StringBuilder( text );
		} else {
			textBuffer.append( text );
		}
	}
	

	//-------------------------
	// PRIVATE HELPER METHODS -
	//-------------------------
	
	private void extractDataFromText( String element ) {
		if( textBuffer == null ) {
			return;
		}
		
		String text = textBuffer.toString().trim();
		if( text.equals( "" ) && !element.equals( "triple" ) ) {
			return;
		}
		
		// check for a boolean relation value
		if( !inSearchClause && element.equals( "value" ) ) {
			cqlStack.push( text );
		}
		
		// construct a search clause
		if( inSearchClause ) {
			if( searchClause == null ) {
				searchClause = new StringBuilder();
			}

			if( element.equals( "index" ) ) {
				searchClause.append( translateIndex( text ) );
			} else if( element.equals( "value" ) ) {
				// relation value should always be '='
				searchClause.append( text );
			} else if( element.equals( "term" ) ) {
				searchClause.append( "(" + text + ")" );
				cqlStack.push( searchClause.toString() );
				searchClause = null;
			}
		}
		
		// evaluate expression so far if we hit a </triple>
		if( element.equals( "triple" ) ) {
			String rightOperand    = ( String ) cqlStack.pop();
			String leftOperand     = ( String ) cqlStack.pop();
			String booleanRelation = ( String ) cqlStack.pop();

			cqlStack.push( leftOperand +
					translateBooleanRelation( booleanRelation ) +
					rightOperand );
		}
		
		textBuffer = null;
	}
	
	private String translateIndex( String cqlIndex ) {
		String xserverIndex = ( String ) INDEX_MAP.get( cqlIndex );
		
		if( xserverIndex == null || xserverIndex.equals( "" ) ) {
			log.warn( "CQL2XServerFindCommand.translateIndex() - null/empty index" );
			// default to keyword
			xserverIndex = "WRD";
		}
		
		return xserverIndex;
	}
	
	private String translateBooleanRelation( String booleanRelation ) {
		String xserverBoolean = ( String ) BOOL_RELATION_MAP.get( booleanRelation );
		
		if( xserverBoolean == null || xserverBoolean.equals( "" ) ) {
			log.warn( "CQL2XServerFindCommand.translateIndex() - null/empty boolean relation" );
			// default to and
			xserverBoolean = "%20AND%20";
		}
		
		return xserverBoolean;
	}
}
