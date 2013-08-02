<%@ include file="/include.jsp"%>
<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>

<div>
  <table class="editProviderTable">
    <c:if test="${showType}">
      <tr>
        <th><label class="shortLabel">Connection Type:</label></th>
        <td>QualityCenter</td>
      </tr>
    </c:if>
    <tr>
      <th><label for="name" class="shortLabel">Display Name: <l:star/></label></th>
      <td>
        <props:textProperty name="name" maxlength="100" style="width: 16em;"/>
        <span id="error_name" class="error"></span>
      </td>
    </tr>
    <tr>
      <th><label for="host" class="shortLabel">Server URL: <l:star/></label></th>
      <td>
        <props:textProperty name="host" maxlength="100" style="width: 16em;"/>
        <span id="error_host" class="error"></span>
      </td>
    </tr>
    <tr>
      <th><label for="username" class="shortLabel">Username:</label></th>
      <td>
        <props:textProperty name="username" maxlength="100" style="width: 16em;"/>
        <span id="error_username" class="error"></span>
      </td>
    </tr>
    <tr>
      <th><label for="secure:password" class="shortLabel">Password:</label></th>
      <td>
        <props:passwordProperty name="secure:password" maxlength="100" style="width: 16em;"/>
        <span id="error_secure:password" class="error"></span>
      </td>
    </tr>
    <tr>
      <th><label for="idPrefix" class="shortLabel">Project IDs: <l:star/></label></th>
      <td>
        <jsp:useBean id="propertiesBean" scope="request" type="jetbrains.buildServer.controllers.BasePropertiesBean"/>
        <forms:textField name="prop:idPrefix" id="idPrefix" style="width: 16em;" expandable="true"
                         value="${propertiesBean.properties['idPrefix']}"/>
        <span id="error_idPrefix" class="error"></span>
        <span class="fieldExplanation">Space-separated list of project ids.</span>
      </td>
    </tr>
  </table>
</div>
