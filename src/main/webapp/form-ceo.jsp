<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <%@include file="base-head.jsp"%>
</head>
<body>
    <%@include file="nav-menu.jsp"%>

    <div id="container" class="container-fluid">
        <h3 class="page-header">${not empty ceo ? 'Editar' : 'Adicionar'} CEO</h3>

        <form action="${pageContext.request.contextPath}/ceo/${action}" method="POST">
            <input type="hidden" value="${ceo.getId()}" name="ceoId">
            <div class="row">
                <div class="form-group col-md-4">
                    <label for="cpf">CPF</label>
                    <input type="text" class="form-control" id="cpf" name="cpf" 
                           autofocus="autofocus" placeholder="CPF do CEO" 
                           required oninvalid="this.setCustomValidity('Por favor, informe o CPF do CEO.')"
                           oninput="setCustomValidity('')"
                           value="${ceo.getCpf()}">
                </div>

                <div class="form-group col-md-4">
                    <label for="rg">RG</label>
                    <input type="text" class="form-control" id="rg" name="rg" 
                           autofocus="autofocus" placeholder="RG do CEO" 
                           required oninvalid="this.setCustomValidity('Por favor, informe o RG do CEO.')"
                           oninput="setCustomValidity('')"
                           value="${ceo.getRg()}">
                </div>
                
                <div class="form-group col-md-4">
						<label for="user">CEO/Usuário</label>
						<select id="user" class="form-control selectpicker" name="user" 
							    required oninvalid="this.setCustomValidity('Por favor, informe o usuário.')"
							    oninput="setCustomValidity('')">
						  <option value="" disabled ${not empty post ? "" : "selected"}>Selecione um usuário</option>
						  <c:forEach var="user" items="${users}">
						  	<option value="${user.getId()}"  ${ceo.getUser().getId() == user.getId() ? "selected" : ""}>
						  		${user.getName()}
						  	</option>	
						  </c:forEach>
						</select>
				</div>
				
				
				<div class="form-group col-md-4">
						<label for="company">Empresa</label>
						<select id="company" class="form-control selectpicker" name="company" 
							    required oninvalid="this.setCustomValidity('Por favor, informe a empresa.')"
							    oninput="setCustomValidity('')">
						  <option value="" disabled ${not empty post ? "" : "selected"}>Selecione uma empresa</option>
						  <c:forEach var="company" items="${companies}">
						  	<option value="${company.getId()}"  ${ceo.getCompany().getId() == company.getId() ? "selected" : ""}>
						  		${company.getName()}
						  	</option>	
						  </c:forEach>
						</select>
				</div>
                
           
            </div>
            <hr />
            <div id="actions" class="row pull-right">
                <div class="col-md-12">
                    <a href="${pageContext.request.contextPath}/ceos" class="btn btn-default">Cancelar</a>
                    <button type="submit" class="btn btn-primary">${not empty ceo ? "Alterar CEO" : "Cadastrar CEO"}</button>
                </div>
            </div>
        </form>
    </div>

    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</body>
</html>
