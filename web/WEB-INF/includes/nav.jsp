<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Navbar Start -->
<div class="container-fluid p-0">
    <nav class="navbar navbar-expand-lg bg-white navbar-light py-3 py-lg-0 px-lg-5">
        <a href="${pageContext.request.contextPath}" class="navbar-brand ml-lg-3">
            <h1 class="m-0 text-uppercase text-primary"><i class="fa fa-book-reader mr-3"></i>Edukate</h1>
        </a>
        <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse justify-content-between px-lg-3" id="navbarCollapse">
            <div class="navbar-nav mx-auto py-0">
                <a href="${pageContext.request.contextPath}/about.jsp" class="nav-item nav-link">About</a>
                <a href="${pageContext.request.contextPath}/course.jsp" class="nav-item nav-link">Courses</a>
                <a href="${pageContext.request.contextPath}/contact.jsp" class="nav-item nav-link">Contact</a>
                <c:if test="${not empty sessionScope.user}">
                    <div class="nav-item dropdown">
                        <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown">${sessionScope.user.email}</a>
                        <div class="dropdown-menu m-0">
                            <a href="${pageContext.request.contextPath}/account" class="dropdown-item">Account</a>
                            <a href="${pageContext.request.contextPath}/my_courses" class="dropdown-item">My Courses</a>
                            <a href="${pageContext.request.contextPath}/logout" class="dropdown-item">Logout</a>
                        </div>
                    </div>
                </c:if>
            </div>
            <c:if test="${empty sessionScope.user}">
                <a href="${pageContext.request.contextPath}/login" class="btn btn-secondary py-2 px-4 mr-2 d-none d-lg-block">Login</a>
                <a href="${pageContext.request.contextPath}/register" class="btn btn-primary py-2 px-4 d-none d-lg-block">Register</a>
            </c:if>
        </div>
    </nav>
</div>
<!-- Navbar End -->

<c:if test="${not empty param.err}">
    <div class="container">
        <div class="row justify-content-center mt-3">
            <div class="col-6 alert alert-danger fade show position-fixed" role="alert" style="z-index: 1050;">
                <c:out value="${param.err}" escapeXml="false"/>
            </div>
        </div>
    </div>
</c:if>