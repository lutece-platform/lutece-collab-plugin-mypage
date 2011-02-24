/*
 * Copyright (c) 2002-2009, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.mypage.business;

import fr.paris.lutece.portal.business.portlet.Portlet;
import fr.paris.lutece.portal.business.portlet.PortletHome;
import fr.paris.lutece.portal.service.html.XmlTransformerService;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.util.UniqueIDGenerator;

import javax.servlet.http.HttpServletRequest;


/**
 * This is the business class for the object Widget
 */
public class Widget
{
    private static final String XSL_UNIQUE_PREFIX_ID = UniqueIDGenerator.getNewId(  ) + "mypage-";
    private static final int MODE_NORMAL = 0;

    // Variables declarations
    private int _nId;
    private int _nIdPortlet;
    private String _strPortletName;
    private String _strTitle;
    private String _strColor;
    private String _strContent;
    private int _nColumn;
    private int _nState;

    public void evaluate( HttpServletRequest request )
        throws SiteMessageException
    {
        Portlet p = PortletHome.findByPrimaryKey( _nIdPortlet );
        _strTitle = p.getName(  );

        String strXslUniqueId = XSL_UNIQUE_PREFIX_ID + "-" + p.getStyleId(  ) + "-" + MODE_NORMAL;
        XmlTransformerService xmlTransformerService = new XmlTransformerService(  );

        _strContent = xmlTransformerService.transformBySourceWithXslCache( p.getXml( request ),
                p.getXslSource( MODE_NORMAL ), strXslUniqueId, null, null );
    }

    /**
     * Returns the IdWidget
     * @return The IdWidget
     */
    public int getId(  )
    {
        return _nId;
    }

    /**
     * Sets the IdWidget
     * @param nIdWidget The IdWidget
     */
    public void setId( int nIdWidget )
    {
        _nId = nIdWidget;
    }

    /**
     * Returns the IdPortlet
     * @return The IdPortlet
     */
    public int getIdPortlet(  )
    {
        return _nIdPortlet;
    }

    /**
     * Sets the IdPortlet
     * @param nIdPortlet The IdPortlet
     */
    public void setIdPortlet( int nIdPortlet )
    {
        _nIdPortlet = nIdPortlet;
    }

    /**
     * Returns the PortletName
     * @return The PortletName
     */
    public String getPortletName(  )
    {
        return _strPortletName;
    }

    /**
     * Sets the PortletName
     * @param strPortletName The PortletName
     */
    public void setPortletName( String strPortletName )
    {
        _strPortletName = strPortletName;
    }

    /**
     * Returns the Title
     * @return The Title
     */
    public String getTitle(  )
    {
        return _strTitle;
    }

    /**
     * Sets the Title
     * @param strTitle The Title
     */
    public void setTitle( String strTitle )
    {
        _strTitle = strTitle;
    }

    /**
     * Returns the Color
     * @return The Color
     */
    public String getColor(  )
    {
        return _strColor;
    }

    /**
     * Sets the Color
     * @param strColor The Color
     */
    public void setColor( String strColor )
    {
        _strColor = strColor;
    }

    /**
     * Returns the Content
     * @return The Content
     */
    public String getContent(  )
    {
        return _strContent;
    }

    /**
     * Sets the Content
     * @param strContent The Content
     */
    public void setContent( String strContent )
    {
        _strContent = strContent;
    }

    /**
     * Returns the Column
     * @return The Column
     */
    public int getColumn(  )
    {
        return _nColumn;
    }

    /**
     * Sets the Column
     * @param nColumn The Column
     */
    public void setColumn( int nColumn )
    {
        _nColumn = nColumn;
    }

    /**
     * Returns the State
     * @return The State
     */
    public int getState(  )
    {
        return _nState;
    }

    /**
     * Sets the State
     * @param nState The State
     */
    public void setState( int nState )
    {
        _nState = nState;
    }
}
