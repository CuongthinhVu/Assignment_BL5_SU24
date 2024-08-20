<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin login</title>
        <!-- Customized Bootstrap Stylesheet -->
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    </head>
    <body>
        <c:if test="${not empty param.err}">
            <div class="container">
                <div class="row justify-content-center mt-3">
                    <div class="col-6 alert alert-danger fade show position-fixed" role="alert" style="z-index: 1050;">
                        <c:out value="${param.err}" escapeXml="false"/>
                    </div>
                </div>
            </div>
        </c:if>
        <div class="d-flex align-items-center bg-light" style="height: 100vh;">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-md-6 col-lg-4">
                        <div class="card shadow-sm">
                            <div class="card-body">
                                <h2 class="text-center mb-4">Admin Login</h2>
                                <form action="${pageContext.request.contextPath}/admin" method="post">
                                    <div class="form-group mb-3">
                                        <label for="username">Username</label>
                                        <input type="text" id="username" name="username" class="form-control" required>
                                    </div>
                                    <div class="form-group mb-3">
                                        <label for="password">Password</label>
                                        <input type="password" id="password" name="password" class="form-control" required>
                                    </div>
                                    <button type="submit" class="btn btn-primary btn-block">Login</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
