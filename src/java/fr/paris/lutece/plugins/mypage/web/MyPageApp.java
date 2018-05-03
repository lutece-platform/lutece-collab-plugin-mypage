/*
 * Copyright (c) 2002-2017, Mairie de Paris
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
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.web.xpages.XPage;
import fr.paris.lutece.portal.web.xpages.XPageApplication;
import fr.paris.lutece.util.html.HtmlTemplate;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


/**
 * Thi class manage MyPage Page
 */
public class MyPageApp implements XPageApplication
{
    //Constants
    private static final String TEMPLATE_MY_PAGE = "/skin/plugins/mypage/mypage.html";
    private static final String MARK_WIDGETS_LIST = "widgets_list";
    private static final String PROPERTY_PAGE_TITLE = "mypage.pageTitle";
    private static final String PROPERTY_PAGE_PATH_LABEL = "mypage.pagePath";

    /**
     * getPage
     *
     * @param request HttpServletRequest
     * @param nMode int
     * @param plugin Plugin
     * @return XPage
     * @throws SiteMessageException If an error occurs
     */
    public XPage getPage( HttpServletRequest request, int nMode, Plugin plugin )
        throws SiteMessageException
    {
        XPage page = new XPage(  );

        page.setTitle( I18nService.getLocalizedString( PROPERTY_PAGE_TITLE, request.getLocale(  ) ) );
        page.setPathLabel( I18nService.getLocalizedString( PROPERTY_PAGE_PATH_LABEL, request.getLocale(  ) ) );

        HashMap model = new HashMap(  );
        model.put( MARK_WIDGETS_LIST, getUserWidgets( request ) );

        HtmlTemplate t = AppTemplateService.getTemplate( TEMPLATE_MY_PAGE, request.getLocale(  ), model );
        page.setContent( t.getHtml(  ) );

        return page;
    }

    /**
     * Gets user's widgets
     * @param request The Http request
     * @return A widget list
     * @throws SiteMessageException if an error occurs
     */
    private List<Widget> getUserWidgets( HttpServletRequest request )
        throws SiteMessageException
    {
        List<Widget> list = WidgetHome.findAll(  );

        for ( Widget widget : list )
        {
            widget.evaluate( request );
        }

        return list;
    }
}
