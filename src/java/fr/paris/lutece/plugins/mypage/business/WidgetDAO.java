/*
 * Copyright (c) 2002-2012, Mairie de Paris
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
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * This class provides Data Access methods for Widget objects
 */
public final class WidgetDAO implements IWidgetDAO
{
    // Constants
    private static final String SQL_QUERY_NEW_PK = "SELECT max( id_widget ) FROM mypage_widget";
    private static final String SQL_QUERY_SELECT = "SELECT a.id_widget, a.id_portlet, a.widget_color, a.widget_column , b.name FROM mypage_widget a, core_portlet b WHERE a.id_portlet = b.id_portlet AND a.id_widget = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO mypage_widget ( id_widget, id_portlet, widget_color, widget_column ) VALUES ( ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM mypage_widget WHERE id_widget = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE mypage_widget SET id_widget = ?, id_portlet = ?, widget_color = ?, widget_column = ? WHERE id_widget = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT a.id_widget, a.id_portlet, a.widget_color, a.widget_column , b.name FROM mypage_widget a, core_portlet b WHERE a.id_portlet = b.id_portlet";

    /**
     * Generates a new primary key
     * @param plugin The Plugin
     * @return The new primary key
     */
    public int newPrimaryKey( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PK, plugin );
        daoUtil.executeQuery(  );

        int nKey;

        if ( !daoUtil.next(  ) )
        {
            // if the table is empty
            nKey = 1;
        }

        nKey = daoUtil.getInt( 1 ) + 1;
        daoUtil.free(  );

        return nKey;
    }

    /**
     * Insert a new record in the table.
     * @param widget instance of the Widget object to insert
     * @param plugin The plugin
     */
    public void insert( Widget widget, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );

        widget.setId( newPrimaryKey( plugin ) );

        daoUtil.setInt( 1, widget.getId(  ) );
        daoUtil.setInt( 2, widget.getIdPortlet(  ) );
        daoUtil.setString( 3, widget.getColor(  ) );
        daoUtil.setInt( 4, widget.getColumn(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Load the data of the widget from the table
     * @param nId The identifier of the widget
     * @param plugin The plugin
     * @return the instance of the Widget
     */
    public Widget load( int nId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nId );
        daoUtil.executeQuery(  );

        Widget widget = null;

        if ( daoUtil.next(  ) )
        {
            widget = new Widget(  );

            widget.setId( daoUtil.getInt( 1 ) );
            widget.setIdPortlet( daoUtil.getInt( 2 ) );
            widget.setColor( daoUtil.getString( 3 ) );
            widget.setColumn( daoUtil.getInt( 4 ) );
            widget.setPortletName( daoUtil.getString( 5 ) );
        }

        daoUtil.free(  );

        return widget;
    }

    /**
     * Delete a record from the table
     * @param nWidgetId The identifier of the widget
     * @param plugin The plugin
     */
    public void delete( int nWidgetId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nWidgetId );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Update the record in the table
     * @param widget The reference of the widget
     * @param plugin The plugin
     */
    public void store( Widget widget, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

        daoUtil.setInt( 1, widget.getId(  ) );
        daoUtil.setInt( 2, widget.getIdPortlet(  ) );
        daoUtil.setString( 3, widget.getColor(  ) );
        daoUtil.setInt( 4, widget.getColumn(  ) );
        daoUtil.setInt( 5, widget.getId(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Load the data of all the widgets and returns them as a List
     * @param plugin The plugin
     * @return The List which contains the data of all the widgets
     */
    public List<Widget> selectWidgetsList( Plugin plugin )
    {
        List<Widget> widgetList = new ArrayList<Widget>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            Widget widget = new Widget(  );

            widget.setId( daoUtil.getInt( 1 ) );
            widget.setIdPortlet( daoUtil.getInt( 2 ) );
            widget.setColor( daoUtil.getString( 3 ) );
            widget.setColumn( daoUtil.getInt( 4 ) );
            widget.setPortletName( daoUtil.getString( 5 ) );

            widgetList.add( widget );
        }

        daoUtil.free(  );

        return widgetList;
    }
}
