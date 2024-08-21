<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/WEB-INF/includes/head.jsp">
            <jsp:param name="page_title" value="Account"/>
        </jsp:include>
    </head>
    <body>
        <jsp:include page="/WEB-INF/includes/nav.jsp"/>
        
        <div class="container-fluid py-5">
            <div class="container py-5">
                <h2 class="mb-3">Change Password</h2>
                <form action="${pageContext.request.contextPath}/account" method="post">
                    <div class="input-group mb-2">
                        <div class="input-group-prepend"><span class="input-group-text">Old Password</span></div>
                        <input type="password" name="old_password" class="form-control" required>
                    </div>
                    <div class="input-group mb-2">
                        <div class="input-group-prepend"><span class="input-group-text">New Password</span></div>
                        <input type="password" name="new_password" id="new-password" class="form-control" onkeyup="checkPassword()" required>
                    </div>
                    <div class="input-group mb-2">
                        <div class="input-group-prepend"><span class="input-group-text">Re-enter Password</span></div>
                        <input type="password" id="confirm-password" class="form-control" onkeyup="checkPassword()" required>
                    </div>
                    <button type="submit" name="action" value="change_pw" id="submit" class="btn btn-primary mb-5" disabled>Submit</button>
                </form>
                
                <h2 class="mb-3">Delete Account</h2>
                <p>This will delete all your payment history and study progress, proceed with caution!</p>
                <form action="${pageContext.request.contextPath}/account" method="post">
                    <button type="submit" name="action" value="delete_account" class="btn btn-danger">Delete Account</button>
                </form>
            </div>
        </div>
        
        <jsp:include page="/WEB-INF/includes/footer.jsp"/>
        <script>
            function checkPassword() {
                const password = document.getElementById("new-password");
                const confirmPassword = document.getElementById("confirm-password");
                const submit = document.getElementById("submit");
                if (password.value !== "" && password.value === confirmPassword.value) {
                    submit.disabled = false;
                }
                else {
                    submit.disabled = true;
                }
            }
        </script>
    </body>
</html>
