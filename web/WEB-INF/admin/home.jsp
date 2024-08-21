<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Home</title>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">
                    <h1 class="m-0 text-uppercase text-info"><i class="fa fa-book-reader mr-3"></i>Edukate</h1>
                </a>
                <div class="collapse navbar-collapse justify-content-end">
                    <a class="nav-link btn btn-outline-light" href="${pageContext.request.contextPath}/admin?action=log_out">Logout</a>
                </div>
            </div>
        </nav>
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
                <div class="row justify-content-center">
                    <div class="col-lg-12">
                        <div class="row pt-3 mx-0">
                            <div class="col-2 px-0">
                                <div class="bg-success text-center p-4">
                                    <h1 class="text-white">${courses_size}</h1>
                                    <h6 class="text-uppercase text-white">Number of<span class="d-block">Courses</span></h6>
                                </div>
                            </div>
                            <div class="col-2 px-0">
                                <div class="bg-primary text-center p-4">
                                    <h1 class="text-white">${active_size}</h1>
                                    <h6 class="text-uppercase text-white">Active<span class="d-block">Courses</span></h6>
                                </div>
                            </div>
                            <div class="col-2 px-0">
                                <div class="bg-secondary text-center p-4">
                                    <h1 class="text-white">${user_size}</h1>
                                    <h6 class="text-uppercase text-white">Number of<span class="d-block">Users</span></h6>
                                </div>
                            </div>
                            <div class="col-6 px-0">
                                <div class="bg-warning text-center p-4">
                                    <h1 class="text-white"><fmt:formatNumber value="${total_profit}" type="currency" currencyCode="VND" maxFractionDigits="0"/></h1>
                                    <h6 class="text-uppercase text-white">Total<span class="d-block">Profits</span></h6>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="container mt-5">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h2>Manage Courses</h2>
                <a href="${pageContext.request.contextPath}/admin/add_course" class="btn btn-primary">Add Course</a>
            </div>

            <table class="table table-striped table-hover">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Thumbnail</th>
                        <th>Course Title</th>
                        <th>Stats</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="course" items="${course_list}" varStatus="status">
                        <c:forEach var="report" items="${report_list}">
                            <c:if test="${report.courseId eq course.id}">
                                <c:set var="course_report" value="${report}"/>
                            </c:if>
                        </c:forEach>
                        <tr>
                            <td>${status.index + 1}</td>
                            <td>
                                <img src="${pageContext.request.contextPath}/img/${course.imagePath}" alt="Course Thumbnail" class="img-fluid" style="max-width: 100px;">
                            </td>
                            <td>${course.title}</td>
                            <td>
                                <div class="text-muted">No. Purchase: ${course_report.purchaseCount}</div>
                                <div class="text-muted">No. Completed: ${course_report.completedCount}</div>
                                <div class="text-muted">Profits: <fmt:formatNumber value="${course_report.profit}" type="currency" currencyCode="VND" maxFractionDigits="0"/></div>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${course.active eq true}">
                                        <form action="${pageContext.request.contextPath}/admin/home" method="post">
                                            <input type="hidden" name="id" value="${course.id}">
                                            <button type="submit" name="action" value="deactivate_course" class="btn btn-danger">Deactivate</button>
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="${pageContext.request.contextPath}/admin/edit_course?id=${course.id}" class="btn btn-warning">Edit</a>
                                        <form action="${pageContext.request.contextPath}/admin/home" method="post">
                                            <input type="hidden" name="id" value="${course.id}">
                                            <button type="submit" name="action" value="activate_course" class="btn btn-primary">Activate</button>
                                        </form>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
