<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="/WEB-INF/includes/head.jsp">
        <jsp:param name="page_title" value="${course.title}"/>
    </jsp:include>
</head>
<body>
    <jsp:include page="/WEB-INF/includes/nav.jsp"/>

    <!-- Detail Start -->
    <div class="container-fluid py-5">
        <div class="container py-5">
            <div class="row">
                <div class="col-lg-8">
                    <div class="mb-5">
                        <div class="section-title position-relative mb-5">
                            <h6 class="d-inline-block position-relative text-secondary text-uppercase pb-2">Course Detail</h6>
                            <h1 class="display-4">${course.title}</h1>
                        </div>
                        <img class="img-fluid rounded w-100 mb-4" src="${pageContext.request.contextPath}/img/${course.imagePath}" alt="Image">
                        <p>${course.description}</p>
                    </div>

                    <h2 class="mb-3">Contents</h2>
                    <!-- Check for enrollment -->
                    <div class="list-group">
                        <c:forEach var="content" items="${content_list}">
                            <c:set var="completed" value="${false}"/>
                            <c:set var="quiz_passed" value="${false}"/>
                            <c:if test="${content.type eq 'Lesson'}">
                                <c:forEach var="prog" items="${lesson_progresses}">
                                    <c:if test="${prog eq content.id}">
                                        <c:set var="completed" value="${true}"/>
                                    </c:if>
                                </c:forEach>
                            </c:if>
                            <c:if test="${content.type eq 'Quiz'}">
                                <c:forEach var="res" items="${quiz_results}">
                                    <c:if test="${res.quizId eq content.id}">
                                        <c:set var="completed" value="${true}"/>
                                        <c:if test="${res.passed eq true}">
                                            <c:set var="quiz_passed" value="${true}"/>
                                        </c:if>
                                    </c:if>
                                </c:forEach>
                            </c:if>
                            <a href="${pageContext.request.contextPath}/${content.type eq 'Lesson' ? 'lesson' : 'quiz'}?course_id=${course.id}&id=${content.id}" class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
                                <div>
                                    <i class="fas ${content.type eq 'Lesson' ? 'fa-book-reader' : 'fa-check-circle'} mr-2"></i>
                                    ${content.title}
                                </div>
                                <c:if test="${completed eq true}">
                                    <c:choose>
                                        <c:when test="${content.type eq 'Lesson'}">
                                            <i class="fas fa-check-circle text-success"></i>
                                        </c:when>
                                        <c:otherwise>
                                            <i class="fas ${quiz_passed eq true ? 'fa-check-circle text-success' : 'fa-exclamation-circle text-danger'}"></i>
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                            </a>
                        </c:forEach>
                    </div>
               </div>

                <div class="col-lg-4 mt-5 mt-lg-0">
                    <div class="bg-primary mb-5 py-3">
                        <h3 class="text-white py-3 px-4 m-0">Course Features</h3>
                        <div class="d-flex justify-content-between border-bottom px-4">
                            <h6 class="text-white my-3">Contents</h6>
                            <h6 class="text-white my-3">${content_list.size()}</h6>
                        </div>
                            <c:choose>
                                <c:when test="${course.salePrice > 0}">
                                    <h5 class="text-white py-3 px-4 m-0">Course Price: <fmt:formatNumber value="${course.salePrice}" type="currency" currencyCode="VND"/></h5>
                                </c:when>
                                <c:otherwise>
                                    <h5 class="text-white py-3 px-4 m-0">Course Price: FREE</h5>
                                </c:otherwise>
                            </c:choose>
                        <div class="py-3 px-4">
                            <c:choose>
                                <c:when test="${not empty enroll}">
                                    <div class="btn btn-block ${enroll.status eq 'Completed' ? 'btn-success' : 'btn-warning'} py-3 px-5">${enroll.status}</div>
                                </c:when>
                                <c:otherwise>
                                    <a class="btn btn-block btn-secondary py-3 px-5" href="${pageContext.request.contextPath}/checkout?course_id=${course.id}">Pay and Enroll</a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>

                    <div class="mb-5">
                        <h2 class="mb-3">Categories</h2>
                        <ul class="list-group list-group-flush">
                            <c:forEach var="category" items="${course.categories}">
                                <li class="list-group-item d-flex justify-content-between align-items-center px-0">
                                    <a href="${pageContext.request.contextPath}/courses?category=${category.id}" class="text-decoration-none h6 m-0">${category.name}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Detail End -->

    <jsp:include page="/WEB-INF/includes/footer.jsp"/>
</body>
</html>