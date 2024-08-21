<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/WEB-INF/includes/head.jsp">
            <jsp:param name="page_title" value="Register"/>
        </jsp:include>
    </head>
    <body>
        <jsp:include page="/WEB-INF/includes/nav.jsp"/>
        
        <div class="container-fluid py-5">
            <div class="container py-5">
                <div class="row align-items-center">
                    <div class="col-lg-7">
                        <div class="section-title position-relative mb-4">
                            <h6 class="d-inline-block position-relative text-secondary text-uppercase pb-2">Want to join us?</h6>
                            <h1 class="display-4">Register</h1>
                        </div>
                        <form action="${pageContext.request.contextPath}/register" method="post">
                            <div class="form-group mb-3">
                                <input type="email" name="email" class="form-control border-top-0 border-right-0 border-left-0 p-0" placeholder="Enter Email" required>
                            </div>
                            <div class="form-group mb-4">
                                <input type="password" name="password" class="form-control border-top-0 border-right-0 border-left-0 p-0" placeholder="Enter Password" required>
                            </div>
                            <div>
                                <button type="submit" name="action" value="register" class="btn btn-primary py-3 px-5">Register</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
                            
        <jsp:include page="/WEB-INF/includes/footer.jsp"/>
    </body>
</html>
