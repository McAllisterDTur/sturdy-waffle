<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>Copyright &copy; <jstl:out value="${year}"/> ACME Co.</p>

<jstl:if test="${locale == 'en' }">
	
	<p>Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:<br/>
	<br/>
	The above copyright notice and this permission notice shall be included in all
	copies or substantial portions of the Software.<br/>
	<br/>
	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
	SOFTWARE.</p>
</jstl:if>
<jstl:if test="${locale == 'es' }">

	<p>Por la presente se concede permiso, de forma gratuita, a cualquier persona que obtenga una copia
	de este software y de los archivos de documentación asociados (el "Software"), para manipular
	el Software sin restricción, incluyendo sin limitación los derechos de
	usar, copiar, modificar, fusionar, publicar, distribuir, sublicenciar y/o vender
	copias del Software, y para permitir a las personas a las que se dirige el Software
	hacerlo, sujeto a las siguientes condiciones:<br/>
	<br/>
	El aviso de derechos de autor anterior y este aviso de permiso se incluirán en todas las 
	copias o partes sustanciales del Software.<br/>
	<br/>
	EL SOFTWARE SE SUMINISTRA "TAL CUAL", SIN GARANTÍA DE NINGÚN TIPO, EXPRESA O IMPLÍCITA
	INCLUYENDO PERO NO LIMITÁNDOSE A LAS GARANTÍAS DE COMERCIABILIDAD,
	LA IDONEIDAD PARA UN FIN DETERMINADO Y LA NO INFRACCIÓN. EN NINGÚN CASO LOS AUTORES 
	O LOS TITULARES DE LOS DERECHOS DE AUTOR SERÁN RESPONSABLES DE CUALQUIER RECLAMACIÓN, 
	DAÑO U OTRO TIPO DE DAÑO O RESPONSABILIDAD, YA SEA EN UNA ACCIÓN CONTRACTUAL, 
	EXTRACONTRACTUAL O DE OTRO TIPO, QUE SURJA DE FUERA O EN CONEXIÓN CON EL SOFTWARE 
	O EL USO U OTRAS TRANSACCIONES EN EL SITIO WEB DEL SOFTWARE.</p>
	
</jstl:if>