<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div id="menuMessages">
	<ul> 
		<li><a href="#send"><span><s:property value="tr.tr_html(30)"/></span></a></li> 
	    <li><a href="#receive"><span><s:property value="tr.tr_html(31)"/></span></a></li>
	</ul>
	
	<div id="send" class="body" > 
	   	<div align="center" id="divWaitingSend" style="display:none;"><img src="img/ajax-loader.gif" width="500"></div>
	</div> 
	<div id="receive" class="body">
		<div align="center" id="divWaitingReceive" style="display:none;"><img src="img/ajax-loader.gif" width="500"></div>
	</div>
</div>