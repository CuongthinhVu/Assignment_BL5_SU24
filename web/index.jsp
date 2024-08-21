<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="/WEB-INF/includes/head.jsp">
        <jsp:param name="page_title" value="Edukate"/>
    </jsp:include>
</head>
<body>
    <jsp:include page="/WEB-INF/includes/nav.jsp"/>

    <!-- Header Start -->
    <div class="jumbotron jumbotron-fluid position-relative overlay-bottom" style="margin-bottom: 90px;">
        <div class="container text-center my-5 py-5">
            <h1 class="text-white mt-4 mb-4">Learn From Home</h1>
            <h1 class="text-white display-1 mb-5">Education Courses</h1>
            <div class="mx-auto mb-5" style="width: 100%; max-width: 600px;">
                <form action="${pageContext.request.contextPath}/courses" method="get">
                    <div class="input-group">
                        <input type="text" name="search" class="form-control border-light" style="padding: 30px 25px;" placeholder="Find Courses">
                        <div class="input-group-append">
                            <button type="submit" class="btn btn-secondary px-4 px-lg-5">Search</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!-- Header End -->

    <div class="container-fluid py-5">
        <div class="row mx-0 justify-content-center pt-5">
            <div class="col-lg-6">
                <div class="section-title text-center position-relative mb-4">
                    <h6 class="d-inline-block position-relative text-secondary text-uppercase pb-2">Our Courses</h6>
                    <h1 class="display-4">New Courses</h1>
                </div>
            </div>
        </div>
        <div class="row">
            <c:forEach var="course" items="${new_courses}">
                <div class="col-lg-3 d-flex align-items-stretch">
                    <div class="card w-100">
                        <div class="overflow-hidden" style="height: 200px;">
                            <img src="${pageContext.request.contextPath}/img/${course.imagePath}" class="card-img-top" alt="${course.title}" style="object-fit: cover; height: 100%;">
                        </div>
                        <div class="card-body d-flex flex-column">
                            <h5 class="card-title">${course.title}</h5>
                            <c:choose>
                                <c:when test="${course.salePrice > 0}">
                                    <p class="card-text">Price: <fmt:formatNumber value="${course.salePrice}" type="currency" currencyCode="VND"/></p>
                                </c:when>
                                <c:otherwise>
                                    <p class="card-text">Price: FREE</p>
                                </c:otherwise>
                            </c:choose>
                            <a href="${pageContext.request.contextPath}/course_detail?id=${course.id}" class="btn btn-primary mt-auto">Details</a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    
    <div class="container-fluid bg-image py-5">
        <div class="row mx-0 justify-content-center pt-5">
            <div class="col-lg-6">
                <div class="section-title text-center position-relative mb-4">
                    <h6 class="d-inline-block position-relative text-secondary text-uppercase pb-2">Our Courses</h6>
                    <h1 class="display-4">Best Sellers</h1>
                </div>
            </div>
        </div>
        <div class="row">
            <c:forEach var="course" items="${best_sellers}">
                <div class="col-lg-3 d-flex align-items-stretch">
                    <div class="card w-100">
                        <div class="overflow-hidden" style="height: 200px;">
                            <img src="${pageContext.request.contextPath}/img/${course.imagePath}" class="card-img-top" alt="${course.title}" style="object-fit: cover; height: 100%;">
                        </div>
                        <div class="card-body d-flex flex-column">
                            <h5 class="card-title">${course.title}</h5>
                            <c:choose>
                                <c:when test="${course.salePrice > 0}">
                                    <p class="card-text">Price: <fmt:formatNumber value="${course.salePrice}" type="currency" currencyCode="VND"/></p>
                                </c:when>
                                <c:otherwise>
                                    <p class="card-text">Price: FREE</p>
                                </c:otherwise>
                            </c:choose>
                            <a href="${pageContext.request.contextPath}/course_detail?id=${course.id}" class="btn btn-primary mt-auto">Details</a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    
    <jsp:include page="/WEB-INF/includes/footer.jsp"/>
</body>
</html>