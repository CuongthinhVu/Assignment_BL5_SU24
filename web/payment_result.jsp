<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/WEB-INF/includes/head.jsp">
            <jsp:param name="page_title" value="Success"/>
        </jsp:include>
    </head>
    <body>
        <jsp:include page="/WEB-INF/includes/nav.jsp"/>
        
        <div class="container-fluid vh-100 d-flex justify-content-center align-items-center">
            <div class="text-center">
                <h1 class="mb-4">
                    <i class="fas fa-check-circle text-success" style="font-size: 12rem;"></i>
                </h1>
                <h1 class="my-4">Course Successfully Purchased</h1>
                <a href="${pageContext.request.contextPath}/course_detail?id=${course_id}" class="btn btn-primary">Return to Course</a>
            </div>
        </div>
            
        <jsp:include page="/WEB-INF/includes/footer.jsp"/>
    </body>
</html>
