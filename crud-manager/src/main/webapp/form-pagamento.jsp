<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <%@include file="base-head.jsp"%>
    </head>
    <body>
        <%@include file="nav-menu.jsp"%>
        
        <div id="container" class="container-fluid">
            <h3 class="page-header">${not empty pagamento ? 'Atualizar' : 'Cadastrar'} Pagamento</h3>
            
            <form action="${pageContext.request.contextPath}/pagamento/${action}" method="POST">
                
                <input type="hidden" value="${pagamento.getId()}" name="pagamentoId">
                
                <div class="row">
                    <div class="form-group col-md-6">
                        <label for="name">Salario</label>
                        <input type="text" class="form-control" id="salário" name="salario" 
                               autofocus="autofocus" placeholder="Salário do funcionário" 
                               required oninvalid="this.setCustomValidity('Por favor, informe o valor do salario do funcionário.')"
                               oninput="setCustomValidity('')" value="${salario.getSalario()}"/>
                    </div>
                    
                    <div class="form-group col-md-6">
                        <label for="role">Bonus</label>
                        <input type="text" class="form-control" id="bonus" name="bonus" 
                               autofocus="autofocus" placeholder="Bonus recebido" 
                               required oninvalid="this.setCustomValidity('Por favor, informe o valor do Bonus.')"
                               oninput="setCustomValidity('')" value="${pagamento.getRole()}"/>
                    </div>
                </div>
                
                <div class="row">
                    <div class="form-group col-md-4">
                        <label for="start">Data de admissão</label>
                        <input type="date" class="form-control" id="dataAdmin" name="dataAdmin" 
                               autofocus="autofocus" placeholder="Data de inicio" 
                               required oninvalid="this.setCustomValidity('Por favor, informe a data de inicio.')"
                               oninput="setCustomValidity('')" value="${pagamento.getDataAdmin()}"/>
                    </div>
                    
                    <div class="form-group col-md-4">
                        <label for="end">Dias trabalhados</label>
                        <input type="date" class="form-control" id="qtdDias" name="qtdDias" 
                               autofocus="autofocus" placeholder="Dias trabalhados" 
                               oninvalid="this.setCustomValidity('Por favor, informe os dias trabalhados.')"
                               oninput="setCustomValidity('')" value="${pagamento.getQtdDias()}"/>
                    </div>
                    
                    <div class="form-group col-md-4">
                        <label for="user">Usuário</label>
                        <select id="user" class="form-control selectpicker" name="user" 
                                required oninvalid="this.setCustomValidity('Por favor, informe o usuário.')"
                                oninput="setCustomValidity('')">
                                <option value="" ${not empty company ? "" : 'selected' } >Selecione um usuario</option>
                          
                          <c:forEach var="user" items="${users}">
                              <option value="${user.getId()}" 
                              ${company.getUser().getId() == user.getId() ? 'selected' : ""}>
                                  ${user.getName()}
                              </option>    
                          </c:forEach>
                        </select>
                    </div>
                </div>
                
                <hr />
                <div id="actions" class="row pull-right">
                    <div class="col-md-12">
                        <a href="${pageContext.request.contextPath}/pagamentos" class="btn btn-default">Cancelar</a>
                        <button type="submit" class="btn btn-primary">${not empty company ? 'Atulizar' : 'Cadastrar'} Pagamento</button>
                    </div>
                </div>
                
            </form>
        </div>
        
    </body>
</html>