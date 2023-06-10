<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="pt-br">
	<head>
		<%@include file="base-head.jsp"%>
	</head>
	<body>
		<%@include file="nav-menu.jsp"%>
			
		<div id="container" class="container-fluid">
			<h3 class="page-header">Modificar Empresa</h3>

			<form action="${pageContext.request.contextPath}/company/${action}" method="POST">
				<input type="hidden" value="${company.getId()}" name="companyId">
				<div class="row">
					<div class="form-group col-md-3">
					<label for="name">Empresa</label>
						<input type="text" class="form-control" id="name" name="name" 
							   autofocus="autofocus" placeholder="Nome da empresa" 
							   required oninvalid="this.setCustomValidity('Por favor, informe o nome da empresa.')"
							   oninput="setCustomValidity('')"
							   value="${company.getName()}">
					</div>
					
					<div class="form-group col-md-2">
					<label for="role">Atuação</label>
						<input type="text" class="form-control" id="role" name="role" 
							   autofocus="autofocus" placeholder="Área de atuação da empresa" 
							   required oninvalid="this.setCustomValidity('Por favor, informe o ramo da empresa.')"
							   oninput="setCustomValidity('')"
							   value="${company.getRole()}">
					</div>

					<div class="form-group col-md-2">
					<label for="start">Início</label>
						<input type="date" class="form-control" id="start" name="start" 
							   autofocus="autofocus" placeholder="Data de início da empresa" 
							   required oninvalid="this.setCustomValidity('Por favor, informe a data de início da empresa.')"
							   oninput="setCustomValidity('')"
							   value="${company.getStart()}">
					</div>
					
					<div class="form-group col-md-2">
					<label for="end">Fim</label>
						<input type="date" class="form-control" id="end" name="end" 
							   autofocus="autofocus" placeholder="Data de encerramento da empresa" 
							   oninput="setCustomValidity('')"
							   value="${company.getEnd()}">
					</div>

					<div class="form-group col-md-6">
						<label for="user">Usuário</label>
						<select id="user" class="form-control selectpicker" name="user" 
							    required oninvalid="this.setCustomValidity('Por favor, informe o usuário.')"
							    oninput="setCustomValidity('')">
						  <option value="" disabled ${not empty post ? "" : "selected"}>Selecione um usuário</option>
						  <c:forEach var="user" items="${users}">
						  	<option value="${user.getId()}"  ${post.getUser().getId() == user.getId() ? "selected" : ""}>
						  		${user.getName()}
						  	</option>	
						  </c:forEach>
						</select>
					</div>
				</div>
				<hr />
				<div id="actions" class="row pull-right">
					<div class="col-md-12">
						<a href="${pageContext.request.contextPath}/companies" class="btn btn-default">Cancelar</a>
						<button type="submit" class="btn btn-primary">${not empty company ? "Alterar Empresa" : "Criar Empresa"}</button>
					</div>
				</div>
			</form>
		</div>

		<script src="js/jquery.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
	</body>
</html>