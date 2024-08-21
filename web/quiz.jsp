<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/WEB-INF/includes/head.jsp">
            <jsp:param name="page_title" value="${quiz.title}"/>
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
                                <h1 class="display-4">${quiz.title}</h1>
                                <p class="text-muted mt-2">Created at <fmt:parseDate type="both" value="${quiz.createdAt}" pattern="yyyy-MM-dd'T'HH:mm"/></p>
                                <p class="text-muted mt-2">You need ${pass_grade} / ${question_list.size()} correct answers to pass.</p>
                            </div>
                        </div>
                    </div>
                </div>
                <form action="${pageContext.request.contextPath}/quiz" method="post">
                    <input type="hidden" name="course_id" value="${quiz.courseId}">
                    <input type="hidden" name="id" value="${quiz.id}">
                    <c:forEach var="question" items="${question_list}">
                        <div class="card mb-3">
                            <div class="card-body">
                                <h5 class="card-title"><c:out value="${question.text}"/></h5>
                                <c:forEach var="option" items="${question.options}">
                                    <div class="form-check">
                                        <input class="form-check-input" type="${question.multipleAnswers ? "checkbox" : "radio"}" name="question_${question.id}" id="option_${option.id}" value="${option.id}">
                                        <label class="form-check-label" for="option_${option.id}"><c:out value="${option.text}"/></label>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </c:forEach>
                    <a href="${pageContext.request.contextPath}/course_detail?id=${quiz.courseId}" class="btn btn-secondary">Return</a>
                    <button type="submit" name="action" value="grade_quiz" class="btn btn-primary">Submit</button>
                </form>
            </div>
        </div>
        
        <jsp:include page="/WEB-INF/includes/footer.jsp"/>
    </body>
</html>
