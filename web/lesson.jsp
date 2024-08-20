<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Edukate - Online Education Website Template</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <meta content="Free HTML Templates" name="keywords">
        <meta content="Free HTML Templates" name="description">

        <!-- Favicon -->
        <link href="img/favicon.ico" rel="icon">

        <!-- Google Web Fonts -->
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Jost:wght@500;600;700&family=Open+Sans:wght@400;600&display=swap" rel="stylesheet"> 

        <!-- Font Awesome -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">

        <!-- Libraries Stylesheet -->
        <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

        <!-- Customized Bootstrap Stylesheet -->
        <link href="css/style.css" rel="stylesheet">
    </head>
    <body>
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
                    </div>
                    <a href="${pageContext.request.contextPath}/login" class="btn btn-secondary py-2 px-4 mr-2 d-none d-lg-block">Login</a>
                    <a href="${pageContext.request.contextPath}/register" class="btn btn-primary py-2 px-4 d-none d-lg-block">Register</a>
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
        
        <div class="container-fluid py-5">
            <div class="container py-5">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="mb-5">
                            <div class="section-title position-relative mb-5">
                                <h1 class="display-4">${lesson.title}</h1>
                                <p class="text-muted mt-2">Created at <fmt:parseDate type="both" value="${lesson.createdAt}" pattern="yyyy-MM-dd'T'HH:mm"/></p>
                            </div>
                            <c:out value="${lesson.content}" escapeXml="false"/>
                        </div>
                        <div class="text-center mt-5">
                            <a href="${pageContext.request.contextPath}/course_detail?id=${lesson.courseId}" class="btn btn-secondary">Return</a>
                            <a href="#" class="btn btn-primary">Mark as Completed</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
