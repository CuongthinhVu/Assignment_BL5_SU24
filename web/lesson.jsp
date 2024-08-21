<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/WEB-INF/includes/head.jsp">
            <jsp:param name="page_title" value="${lesson.title}"/>
        </jsp:include>
    </head>
    <body>
        <jsp:include page="/WEB-INF/includes/nav.jsp"/>
        
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
                        <div class="row justify-content-center mt-5">
                            <a href="${pageContext.request.contextPath}/course_detail?id=${lesson.courseId}" class="btn btn-secondary mr-2">Return</a>
                            <c:if test="${completed eq false}">
                                <form action="${pageContext.request.contextPath}/lesson" method="post">
                                    <input type="hidden" name="course_id" value="${lesson.courseId}">
                                    <input type="hidden" name="id" value="${lesson.id}">
                                    <button type="submit" class="btn btn-primary">Mark as Completed</button>
                                </form>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <jsp:include page="/WEB-INF/includes/footer.jsp"/>
    </body>
</html>
