<%-- 
    Document   : register
    Created on : Aug 12, 2024, 7:46:23 PM
    Author     : LONG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="register" method="post">
            <div class="form-group">
                <label>Email</label>
                <input type="email" name="email" placeholder="Enter Email" id="email" class="margin-5px-bottom" required>
            </div>
            <div class="form-group">
                <label>Password</label>
                <input type="password" name="password" placeholder="Enter Password" id="password" class="margin-5px-bottom" required>
            </div>
            <div class="button">
                <button type="submit" name="action" value="register" class="btn">Register</button>
            </div>
            <p class="outer-link">Already have an account? <a href="${pageContext.request.contextPath}/login">Login</a></p>
        </form>
    </body>
</html>
