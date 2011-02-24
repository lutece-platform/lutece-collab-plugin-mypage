--
-- Structure for table mypage_widget
--
DROP TABLE IF EXISTS mypage_widget;

CREATE TABLE mypage_widget 
(              
  id_widget int(11) NOT NULL,             
  id_portlet int(11) default NULL,        
  widget_color varchar(50) default NULL,  
  widget_column int(11) default NULL,
  PRIMARY KEY  (id_widget)                
);

