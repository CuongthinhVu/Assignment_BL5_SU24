<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Course</title>
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
        <div class="container mt-5">
            <h2>Manage Categories</h2>
            <form action="${pageContext.request.contextPath}/admin/add_course" method="post">
                <div class="input-group mb-3">
                    <div class="input-group-prepend"><span class="input-group-text">Category</span></div>
                    <input type="text" name="new_category" placeholder="New Category" class="form-control">
                    <button type="submit" name="action" value="add_category" class="btn btn-primary">Add</button>
                </div>
            </form>
            <form action="${pageContext.request.contextPath}/admin/add_course" method="post">
                <div class="input-group mb-3">
                    <div class="input-group-prepend"><span class="input-group-text">Category</span></div>
                    <select name="category" class="form-control">
                        <c:forEach var="cate" items="${category_list}">
                            <option value="${cate.id}">${cate.name}</option>
                        </c:forEach>
                    </select>
                    <button type="submit" name="action" value="delete_category" class="btn btn-primary">Delete</button>
                </div>
            </form>
            <h2>Add New Course</h2>
            <form action="${pageContext.request.contextPath}/admin/add_course" method="post" enctype="multipart/form-data">
                <div class="input-group mb-3">
                    <div class="input-group-prepend"><span class="input-group-text">Course Title</span></div>
                    <input type="text" name="title" class="form-control" required>
                </div>
                <div class="input-group mb-3">
                    <div class="input-group-prepend"><span class="input-group-text">Description</span></div>
                    <textarea name="description" rows="4" class="form-control" required></textarea>
                </div>
                <div class="row mb-3">
                    <div class="input-group col-6">
                        <div class="input-group-prepend"><span class="input-group-text">Price</span></div>
                        <input type="number" name="price" min="0" class="form-control" required>
                        <div class="input-group-append"><span class="input-group-text">VND</span></div>
                    </div>
                    <div class="input-group col-6">
                        <div class="input-group-prepend"><span class="input-group-text">Thumbnail Image</span></div>
                        <div class="custom-file">
                            <input type="file" name="thumbnail" accept="image/png, image/jpeg" class="custom-file-input" id="thumbnail" required>
                            <label class="custom-file-label" for="thumbnail">Choose File</label>
                        </div>
                    </div>
                </div>
                <div class="input-group mb-3">
                    <button class="btn btn-outline-dark dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Categories</button>
                    <div class="dropdown-menu">
                        <c:forEach var="category" items="${category_list}">
                            <div class="form-check ml-2">
                                <input class="form-check-input" type="checkbox" name="categories" value="${category.id}" id="cat_${category.id}">
                                <label class="form-check-label" for="cat_${category.id}">${category.name}</label>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <button type="submit" name="action" value="add_course" class="btn btn-primary">Add Course</button>
                <a href="${pageContext.request.contextPath}/admin/home" class="btn btn-secondary">Cancel</a>
            </form>
        </div>
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
