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
package fr.paris.lutece.plugins.mypage.web;

import fr.paris.lutece.plugins.mypage.business.Widget;
import fr.paris.lutece.plugins.mypage.business.WidgetHome;
import fr.paris.lutece.portal.business.portlet.AliasPortletHome;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.web.admin.PluginAdminPageJspBean;
import fr.paris.lutece.portal.web.constants.Messages;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.url.UrlItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;


/**
 * This class provides the user interface to manage  Widget
features ( manage, create, modify, remove )
 */
public class MyPageJspBean extends PluginAdminPageJspBean
{
    ////////////////////////////////////////////////////////////////////////////
    // Constants

    // Right
    public static final String RIGHT_MANAGE_MYPAGE = "MYPAGE_MANAGEMENT";

    // Parameters
    private static final String PARAMETER_WIDGET_ID = "widget_id";
    private static final String PARAMETER_WIDGET_ID_PORTLET = "widget_id_portlet";
    private static final String PARAMETER_WIDGET_COLOR = "widget_color";
    private static final String PARAMETER_WIDGET_COLUMN = "widget_column";

    // templates
    private static final String TEMPLATE_MANAGE_WIDGETS = "/admin/plugins/mypage/manage_widgets.html";
    private static final String TEMPLATE_CREATE_WIDGET = "/admin/plugins/mypage/create_widget.html";
    private static final String TEMPLATE_MODIFY_WIDGET = "/admin/plugins/mypage/modify_widget.html";

    // properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_WIDGETS = "mypage.manage_widgets.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_WIDGET = "mypage.modify_widget.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_WIDGET = "mypage.create_widget.pageTitle";

    // Markers
    private static final String MARK_WIDGET_LIST = "widget_list";
    private static final String MARK_WIDGET = "widget";
    private static final String MARK_PORTLETS_LIST = "portlets_list";
    private static final String MARK_COLORS_LIST = "colors_list";

    // Jsp Definition
    private static final String JSP_DO_REMOVE_WIDGET = "jsp/admin/plugins/mypage/DoRemoveWidget.jsp";
    private static final String JSP_REDIRECT_TO_MANAGE_MYPAGE = "ManageMyPage.jsp";

    // Properties
    private static final String PROPERTY_COLORS = "mypage.colors";

    // Messages
    private static final String MESSAGE_CONFIRM_REMOVE_WIDGET = "mypage.message.confirmRemoveWidget";

    //Variables

    /**
     * Returns the list of widget
     *
     * @param request The Http request
     * @return the widgets list
     */
    public String getManageMyPage( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_MANAGE_WIDGETS );

        List<Widget> listWidgets = WidgetHome.findAll(  );

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_WIDGET_LIST, listWidgets );

        HtmlTemplate templateList = AppTemplateService.getTemplate( TEMPLATE_MANAGE_WIDGETS, getLocale(  ), model );

        return getAdminPage( templateList.getHtml(  ) );
    }

    /**
     * Returns the form to create a widget
     *
     * @param request The Http request
     * @return the html code of the widget form
     */
    public String getCreateWidget( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_CREATE_WIDGET );

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_PORTLETS_LIST, AliasPortletHome.getAcceptAliasPortletList(  ) );
        model.put( MARK_COLORS_LIST, getColors(  ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_WIDGET, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Process the data capture form of a new widget
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     */
    public String doCreateWidget( HttpServletRequest request )
    {
        Widget widget = new Widget(  );

        // Mandatory field
        if ( request.getParameter( PARAMETER_WIDGET_ID_PORTLET ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        int nIdPortlet = Integer.parseInt( request.getParameter( PARAMETER_WIDGET_ID_PORTLET ) );
        widget.setIdPortlet( nIdPortlet );

        // Mandatory field
        if ( request.getParameter( PARAMETER_WIDGET_COLOR ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        widget.setColor( request.getParameter( PARAMETER_WIDGET_COLOR ) );

        // Mandatory field
        if ( request.getParameter( PARAMETER_WIDGET_COLUMN ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        int nColumn = Integer.parseInt( request.getParameter( PARAMETER_WIDGET_COLUMN ) );
        widget.setColumn( nColumn );

        WidgetHome.create( widget );

        return JSP_REDIRECT_TO_MANAGE_MYPAGE;
    }

    /**
     * Manages the removal form of a widget whose identifier is in the http request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    public String getConfirmRemoveWidget( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_WIDGET_ID ) );

        UrlItem url = new UrlItem( JSP_DO_REMOVE_WIDGET );
        url.addParameter( PARAMETER_WIDGET_ID, nId );

        return AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_WIDGET, url.getUrl(  ),
            AdminMessage.TYPE_CONFIRMATION );
    }

    /**
     * Treats the removal form of a widget
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage widgets
     */
    public String doRemoveWidget( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_WIDGET_ID ) );

        WidgetHome.remove( nId );

        return JSP_REDIRECT_TO_MANAGE_MYPAGE;
    }

    /**
     * Returns the form to update info about a widget
     *
     * @param request The Http request
     * @return The HTML form to update info
     */
    public String getModifyWidget( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_MODIFY_WIDGET );

        int nId = Integer.parseInt( request.getParameter( PARAMETER_WIDGET_ID ) );

        Widget widget = WidgetHome.findByPrimaryKey( nId );

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_WIDGET, widget );
        model.put( MARK_PORTLETS_LIST, AliasPortletHome.getAcceptAliasPortletList(  ) );
        model.put( MARK_COLORS_LIST, getColors(  ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MODIFY_WIDGET, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Process the change form of a widget
     *
     * @param request The Http request
     * @return The Jsp URL of the process result
     */
    public String doModifyWidget( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_WIDGET_ID ) );

        Widget widget = WidgetHome.findByPrimaryKey( nId );

        // Mandatory field
        if ( request.getParameter( PARAMETER_WIDGET_ID ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        // Mandatory field
        if ( request.getParameter( PARAMETER_WIDGET_ID_PORTLET ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        int nIdPortlet = Integer.parseInt( request.getParameter( PARAMETER_WIDGET_ID_PORTLET ) );
        widget.setIdPortlet( nIdPortlet );

        // Mandatory field
        if ( request.getParameter( PARAMETER_WIDGET_COLOR ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        widget.setColor( request.getParameter( PARAMETER_WIDGET_COLOR ) );

        // Mandatory field
        if ( request.getParameter( PARAMETER_WIDGET_COLUMN ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        int nColumn = Integer.parseInt( request.getParameter( PARAMETER_WIDGET_COLUMN ) );
        widget.setColumn( nColumn );

        WidgetHome.update( widget );

        return JSP_REDIRECT_TO_MANAGE_MYPAGE;
    }

    /**
     * Returns a color list
     * @return A color list
     */
    private ReferenceList getColors(  )
    {
        ReferenceList listColors = new ReferenceList(  );
        String strColors = AppPropertiesService.getProperty( PROPERTY_COLORS );
        StringTokenizer st = new StringTokenizer( strColors, "," );

        while ( st.hasMoreTokens(  ) )
        {
            String strColor = st.nextToken(  );
            listColors.addItem( strColor, strColor );
        }

        return listColors;
    }
}
