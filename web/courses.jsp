<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="/WEB-INF/includes/head.jsp">
        <jsp:param name="page_title" value="Courses"/>
    </jsp:include>
</head>
<body>
    <jsp:include page="/WEB-INF/includes/nav.jsp"/>

    <!-- Header Start -->
    <div class="jumbotron jumbotron-fluid page-header position-relative overlay-bottom" style="margin-bottom: 90px;">
        <div class="container text-center py-5">
            <h1 class="text-white display-1">Courses</h1>
            <div class="d-inline-flex text-white mb-5">
                <p class="m-0 text-uppercase"><a class="text-white" href="">Home</a></p>
                <i class="fa fa-angle-double-right pt-1 px-3"></i>
                <p class="m-0 text-uppercase">Courses</p>
            </div>
            <form action="${pageContext.request.contextPath}/courses" method="get">
                <div class="mx-auto mb-5" style="width: 100%; max-width: 600px;">
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <button class="btn btn-outline-light bg-white text-body px-4 dropdown-toggle" type="button" data-toggle="dropdown"
                                aria-haspopup="true" aria-expanded="false">Sort</button>
                            <div class="dropdown-menu">
                                <a class="dropdown-item" href="${pageContext.request.contextPath}/courses?sort=release_new&page=0<c:if test="${not empty param.search}">&search=${param.search}</c:if><c:forEach var="cate" items="${checked_categories}">&category=${cate}</c:forEach><c:forEach var="range" items="${checked_prices}">&price_range=${range}</c:forEach>">Release date - New to Old</a>
                                <a class="dropdown-item" href="${pageContext.request.contextPath}/courses?sort=release_old&page=0<c:if test="${not empty param.search}">&search=${param.search}</c:if><c:forEach var="cate" items="${checked_categories}">&category=${cate}</c:forEach><c:forEach var="range" items="${checked_prices}">&price_range=${range}</c:forEach>">Release date - Old to New</a>
                                <a class="dropdown-item" href="${pageContext.request.contextPath}/courses?sort=price_low&page=0<c:if test="${not empty param.search}">&search=${param.search}</c:if><c:forEach var="cate" items="${checked_categories}">&category=${cate}</c:forEach><c:forEach var="range" items="${checked_prices}">&price_range=${range}</c:forEach>">Price - Low to High</a>
                                <a class="dropdown-item" href="${pageContext.request.contextPath}/courses?sort=price_high&page=0<c:if test="${not empty param.search}">&search=${param.search}</c:if><c:forEach var="cate" items="${checked_categories}">&category=${cate}</c:forEach><c:forEach var="range" items="${checked_prices}">&price_range=${range}</c:forEach>">Price - High to Low</a>
                            </div>
                        </div>
                        <input type="text" name="search" value="${param.search}" class="form-control border-light" style="padding: 30px 25px;" placeholder="Search by title">
                        <div class="input-group-append">
                            <button type="submit" class="btn btn-secondary px-4 px-lg-5">Search</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <!-- Header End -->

    <!-- Courses Start -->
    <div class="container-fluid py-5">
        <div class="row mx-0 justify-content-center">
            <div class="col-lg-8">
                <div class="section-title text-center position-relative mb-5">
                    <h6 class="d-inline-block position-relative text-secondary text-uppercase pb-2">Our Courses</h6>
                    <h1 class="display-4">Courses</h1>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-3">
                <div class="bg-light p-4">
                    <h4>Filter Courses</h4>
                    <form action="${pageContext.request.contextPath}/courses" method="get">
                        <input type="hidden" name="search" value="${param.search}">
                        <div class="mb-4">
                            <h5>Categories</h5>
                            <c:forEach var="category" items="${category_list}">
                                <c:set var="cate_checked" value="${false}"/>
                                <c:forEach var="id" items="${checked_categories}">
                                    <c:if test="${id eq category.id}">
                                        <c:set var="cate_checked" value="${true}"/>
                                    </c:if>
                                </c:forEach>
                                <div class="form-check">
                                    <input ${cate_checked eq true ? "checked" : ""} type="checkbox" name="category" value="${category.id}" class="form-check-input" id="cat_${category.id}">
                                    <label class="form-check-label" for="cat_${category.id}">${category.name}</label>
                                </div>
                            </c:forEach>
                        </div>
                        <!-- Price Filter Section -->
                        <div class="mb-4">
                            <h5>Price</h5>
                            <div class="form-check">
                                <input <c:forEach var="range" items="${checked_prices}"><c:if test="${range eq '0_0'}">checked</c:if></c:forEach> type="checkbox" name="price_range" value="0_0" class="form-check-input" id="price1">
                                <label class="form-check-label" for="price1">Free</label>
                            </div>
                            <div class="form-check">
                                <input <c:forEach var="range" items="${checked_prices}"><c:if test="${range eq '0_50000'}">checked</c:if></c:forEach> type="checkbox" name="price_range" value="0_50000" class="form-check-input" id="price2">
                                <label class="form-check-label" for="price2">Under <fmt:formatNumber value="${50000}" type="currency" currencyCode="VND" maxFractionDigits="0"/></label>
                            </div>  
                            <div class="form-check">
                                <input <c:forEach var="range" items="${checked_prices}"><c:if test="${range eq '50000_500000'}">checked</c:if></c:forEach> type="checkbox" name="price_range" value="50000_500000" class="form-check-input" id="price3">
                                <label class="form-check-label" for="price3"><fmt:formatNumber value="${50000}" type="currency" currencyCode="VND" maxFractionDigits="0"/> - <fmt:formatNumber value="${500000}" type="currency" currencyCode="VND" maxFractionDigits="0"/></label>
                            </div>
                            <div class="form-check">
                                <input <c:forEach var="range" items="${checked_prices}"><c:if test="${range eq '500000_'}">checked</c:if></c:forEach> type="checkbox" name="price_range" value="500000_" class="form-check-input" id="price4">
                                <label class="form-check-label" for="price4">Above <fmt:formatNumber value="${500000}" type="currency" currencyCode="VND" maxFractionDigits="0"/></label>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-primary">Filter</button>
                    </form>
                </div>
            </div>
            <div class="col-lg-9">
                <div class="row">
                    <c:forEach var="course" items="${course_list}" begin="${page.begin}" end="${page.end - 1 < 0 ? 0 : page.end - 1}">
                        <div class="col-md-4 mb-4 d-flex align-items-stretch">
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
        </div>
        <nav aria-label="Page navigation">
            <ul class="pagination pagination-lg justify-content-center mb-0">
                <li class="page-item ${page.currentPage == 0 ? 'disabled' : ''}">
                    <a class="page-link rounded-0" href="${pageContext.request.contextPath}/courses?page=${page.currentPage - 1}<c:if test="${not empty param.search}">&search=${param.search}</c:if><c:if test="${not empty param.sort}">&sort=${param.sort}</c:if><c:forEach var="cate" items="${checked_categories}">&category=${cate}</c:forEach><c:forEach var="range" items="${checked_prices}">&price_range=${range}</c:forEach>" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                        <span class="sr-only">Previous</span>
                    </a>
                </li>
                <c:forEach var="i" begin="1" end="${page.totalPages}">
                    <li class="page-item ${page.currentPage == i - 1 ? 'active' : ''}">
                        <a class="page-link" href="${pageContext.request.contextPath}/courses?page=${i - 1}<c:if test="${not empty param.search}">&search=${param.search}</c:if><c:if test="${not empty param.sort}">&sort=${param.sort}</c:if><c:forEach var="cate" items="${checked_categories}">&category=${cate}</c:forEach><c:forEach var="range" items="${checked_prices}">&price_range=${range}</c:forEach>">${i}</a>
                    </li>
                </c:forEach>
                <li class="page-item ${page.currentPage == page.totalPages - 1 ? 'disabled' : ''}">
                    <a class="page-link rounded-0" href="${pageContext.request.contextPath}/courses?page=${page.currentPage + 1}<c:if test="${not empty param.search}">&search=${param.search}</c:if><c:if test="${not empty param.sort}">&sort=${param.sort}</c:if><c:forEach var="cate" items="${checked_categories}">&category=${cate}</c:forEach><c:forEach var="range" items="${checked_prices}">&price_range=${range}</c:forEach>" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                        <span class="sr-only">Next</span>
                    </a>
                </li>
            </ul>
        </nav>
    </div>
    <!-- Courses End -->

    <jsp:include page="/WEB-INF/includes/footer.jsp"/>
</body>
</html>