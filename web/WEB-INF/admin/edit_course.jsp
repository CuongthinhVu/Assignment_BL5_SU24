<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Course</title>
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/simplemde.min.css" rel="stylesheet">
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
            <a href="${pageContext.request.contextPath}/admin/home">Return</a>
            <h2 class="mt-4">Update metadata</h2>
            <form action="${pageContext.request.contextPath}/admin/edit_course" method="post" enctype="multipart/form-data">
                <input type="hidden" name="id" value="${course.id}">
                <div class="input-group mb-3">
                    <div class="input-group-prepend"><span class="input-group-text">Thumbnail Image</span></div>
                    <div class="custom-file">
                        <input type="file" name="thumbnail" accept="image/jpeg" class="custom-file-input" id="thumbnail" required>
                        <label class="custom-file-label" for="thumbnail">Choose File</label>
                    </div>
                    <button type="submit" name="action" value="update_thumbnail" class="btn btn-primary">Update Thumbnail</button>
                </div>
            </form>
            <form action="${pageContext.request.contextPath}/admin/edit_course" method="post">
                <input type="hidden" name="id" value="${course.id}">
                <div class="input-group mb-3">
                    <div class="input-group-prepend"><span class="input-group-text">Course Title</span></div>
                    <input type="text" name="title" value="${course.title}" class="form-control" required>
                </div>
                <div class="input-group mb-3">
                    <div class="input-group-prepend"><span class="input-group-text">Description</span></div>
                    <textarea name="description" rows="4" class="form-control" required>${course.description}</textarea>
                </div>
                <div class="row mb-3">
                    <div class="input-group col-6">
                        <div class="input-group-prepend"><span class="input-group-text">Price</span></div>
                        <input type="number" name="price" value="${course.salePrice}" min="1" class="form-control" required>
                        <div class="input-group-append"><span class="input-group-text">VND</span></div>
                    </div>
                    <div class="input-group col-6">
                        <button class="btn btn-outline-dark dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Categories</button>
                        <div class="dropdown-menu">
                            <c:forEach var="category" items="${category_list}">
                                <c:set var="has_category" value="false"/>
                                <c:forEach var="course_category" items="${course.categories}">
                                    <c:if test="${course_category.id eq category.id}">
                                        <c:set var="has_category" value="true"/>
                                    </c:if>
                                </c:forEach>
                                <div class="form-check ml-2">
                                    <input class="form-check-input" type="checkbox" name="categories" value="${category.id}" ${has_category ? "checked" : ""} id="cat_${category.id}">
                                    <label class="form-check-label" for="cat_${category.id}">${category.name}</label>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <button type="submit" name="action" value="update_meta" class="btn btn-primary">Update</button>
            </form>
            <h2 class="mt-4">Manage Contents</h2>
            <table class="table table-bordered table-striped">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Name</th>
                        <th>Type</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach  var="content" items="${content_list}" varStatus="status">
                        <tr>
                            <td>${status.index + 1}</td>
                            <td>${content.title}</td>
                            <td>${content.type}</td>
                            <td>
                                <c:if test="${content.type eq 'Lesson'}">
                                    <a href="${pageContext.request.contextPath}/admin/edit_course?id=${course.id}&content=${content.id}" class="btn btn-primary">Details</a>
                                </c:if>
                                <form action="${pageContext.request.contextPath}/admin/manage_course_content" method="post">
                                    <input type="hidden" name="id" value="${content.id}">
                                    <input type="hidden" name="course_id" value="${course.id}">
                                    <button type="submit" name="action" value="delete" class="btn btn-danger">Delete</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <h3 class="mt-3">Upload a quiz</h3>
            <form action="${pageContext.request.contextPath}/admin/manage_course_content" method="post" enctype="multipart/form-data">
                <input type="hidden" name="course_id" value="${course.id}">
                <div class="input-group mb-3">
                    <div class="input-group-prepend"><span class="input-group-text">Title</span></div>
                    <input type="text" name="title" class="form-control" required>
                </div>
                <div class="input-group">
                    <div class="custom-file">
                        <input type="file" name="quiz_file" accept=".json" class="custom-file-input" id="quiz_file" required>
                        <label class="custom-file-label" for="quiz_file">Choose File (*.json)</label>
                    </div>
                    <button type="submit" name="action" value="add_quiz" class="btn btn-primary">Add Quiz</button>
                </div>
            </form>
            <h3 class="mt-3">Edit a Lesson</h3>
            <form action="${pageContext.request.contextPath}/admin/manage_course_content" method="post">
                <input type="hidden" name="id" value="${select_content.id}">
                <input type="hidden" name="course_id" value="${course.id}">
                <div class="input-group mb-3">
                    <div class="input-group-prepend"><span class="input-group-text">Title</span></div>
                    <input type="text" name="title" value="${select_content.title}" class="form-control" required>
                </div>
                <textarea name="lesson_content" rows="10" class="form-control mb-2" id="lesson_editor">${select_content.content}</textarea>
                <button type="submit" name="action" value="add_lesson" class="btn btn-primary mb-5">Add Lesson</button>
                <button type="submit" name="action" value="update_lesson" class="btn btn-primary mb-5">Update</button>
            </form>
        </div>
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/simplemde.min.js"></script>
        <script>
            var simplemde = new SimpleMDE({ element: document.getElementById("lesson_editor") });
        </script>
    </body>
</html>
