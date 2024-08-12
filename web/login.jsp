<%-- 
    Document   : login
    Created on : Aug 12, 2024, 7:44:25 PM
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
        <form action="login" method="post">
            <div class="form-group">
                <label>Email</label>
                <input type="email" name="email" placeholder="Your Email" id="email" class="margin-5px-bottom" required>
            </div>
            <div class="form-group">
                <label>Password</label>
                <input type="password" name="password" placeholder="Your Password" id="password" class="margin-5px-bottom" required>
            </div>
            <div class="check-and-pass">
                <div class="row align-items-center">
                    <div class="col-lg-6 col-12">
                        <div class="form-check">
                            <input type="checkbox" name="remember_me" value="true" id="remember_me" class="form-check-input width-auto">
                            <label class="form-check-label">Remember me</label>
                        </div>
                    </div>
                    <div class="col-lg-6 col-12">
                        <a href="${pageContext.request.contextPath}/login?action=forgot_pw" class="lost-pass">Lost your password?</a>
                    </div>
                </div>
            </div>
            <div class="button">
                <button type="submit" name="action" value="login" class="btn">Log In</button>
            </div>
            <p class="outer-link">
                Don't have an account? <a href="${pageContext.request.contextPath}/register">Register here</a>
            </p>
        </form>
    </body>
</html>
