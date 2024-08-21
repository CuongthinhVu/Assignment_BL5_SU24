<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/WEB-INF/includes/head.jsp">
            <jsp:param name="page_title" value="My Courses"/>
        </jsp:include>
    </head>
    <body>
        <jsp:include page="/WEB-INF/includes/nav.jsp"/>
        <div class="container-fluid py-5">
            <div class="row mx-0 justify-content-center">
                <div class="col-lg-8">
                    <div class="section-title text-center position-relative mb-5">
                        <h6 class="d-inline-block position-relative text-secondary text-uppercase pb-2">Our Courses</h6>
                        <h1 class="display-4">In Progress</h1>
                    </div>
                </div>
            </div>
                
            <c:forEach var="course" items="${in_progress_courses}">
                <div class="row justify-content-center">
                    <div class="col-10 px-0 card mb-3">
                        <div class="row no-gutters">
                            <!-- Course Thumbnail -->
                            <div class="col-md-4 overflow-hidden" style="height: 200px;">
                                <img src="${pageContext.request.contextPath}/img/${course.imagePath}" class="card-img" alt="${course.title}" style="object-fit: cover; height: 100%;">
                            </div>
                            <div class="col-md-8">
                                <div class="card-body d-flex justify-content-between align-items-center">
                                    <div>
                                        <h5 class="card-title mb-0"><a href="${pageContext.request.contextPath}/course_detail?id=${course.id}">${course.title}</a></h5>
                                    </div>
                                    <div>
                                        <span class="badge badge-warning"><i class="fas fa-clock"></i> In Progress</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        
        <div class="container-fluid py-5">
            <div class="row mx-0 justify-content-center">
                <div class="col-lg-8">
                    <div class="section-title text-center position-relative mb-5">
                        <h6 class="d-inline-block position-relative text-secondary text-uppercase pb-2">Our Courses</h6>
                        <h1 class="display-4">Completed</h1>
                    </div>
                </div>
            </div>
                
            <c:forEach var="course" items="${completed_courses}">
                <div class="row justify-content-center">
                    <div class="col-10 px-0 card mb-3">
                        <div class="row no-gutters">
                            <!-- Course Thumbnail -->
                            <div class="col-md-4 overflow-hidden" style="height: 200px;">
                                <img src="${pageContext.request.contextPath}/img/${course.imagePath}" class="card-img" alt="${course.title}" style="object-fit: cover; height: 100%;">
                            </div>
                            <div class="col-md-8">
                                <div class="card-body d-flex justify-content-between align-items-center">
                                    <div>
                                        <h5 class="card-title mb-0"><a href="${pageContext.request.contextPath}/course_detail?id=${course.id}">${course.title}</a></h5>
                                    </div>
                                    <div>
                                        <span class="badge badge-success"><i class="fas fa-check-circle"></i> Completed</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        
        <jsp:include page="/WEB-INF/includes/footer.jsp"/>
    </body>
</html>
