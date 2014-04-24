/*
 * Copyright (c) 2002-2014, Mairie de Paris
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

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;

import java.util.List;


/**
 * This class provides instances management methods (create, find, ...) for Widget objects
 */
public final class WidgetHome
{
    // Static variable pointed at the DAO instance
    private static IWidgetDAO _dao = (IWidgetDAO) SpringContextService.getPluginBean( "mypage", "widgetDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "mypage" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private WidgetHome(  )
    {
    }

    /**
     * Create an instance of the widget class
     * @param widget The instance of the Widget which contains the informations to store
     * @return The  instance of widget which has been created with its primary key.
     */
    public static Widget create( Widget widget )
    {
        _dao.insert( widget, _plugin );

        return widget;
    }

    /**
     * Update of the widget data specified in parameter
     * @param widget The instance of the Widget which contains the data to store
     * @return The instance of the  widget which has been updated
     */
    public static Widget update( Widget widget )
    {
        _dao.store( widget, _plugin );

        return widget;
    }

    /**
     * Remove the widget whose identifier is specified in parameter
     * @param nWidgetId The widget Id
     */
    public static void remove( int nWidgetId )
    {
        _dao.delete( nWidgetId, _plugin );
    }

    ///////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Returns an instance of a widget whose identifier is specified in parameter
     * @param nKey The widget primary key
     * @return an instance of Widget
     */
    public static Widget findByPrimaryKey( int nKey )
    {
        return _dao.load( nKey, _plugin );
    }

    /**
     * Load the data of all the widget objects and returns them in form of a collection
     * @return the collection which contains the data of all the widget objects
     */
    public static List<Widget> findAll(  )
    {
        return _dao.selectWidgetsList( _plugin );
    }
}
