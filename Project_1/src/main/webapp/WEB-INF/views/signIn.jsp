<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <title>Sign In | Global Flow JSC</title>
    <!-- Favicon-->
    <link rel="icon" href="<c:url value='/static/gfl.png' />" type="image/x-icon">

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,700&subset=latin,cyrillic-ext" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet" type="text/css">

    <!-- Bootstrap Core Css -->
    <link href="<c:url value='/static/plugins/bootstrap/css/bootstrap.css' />" rel="stylesheet">

    <!-- Waves Effect Css -->
    <link href="<c:url value='/static/plugins/node-waves/waves.css'/>" rel="stylesheet" />

    <!-- Animation Css -->
    <link href="<c:url value='/static/plugins/animate-css/animate.css'/>" rel="stylesheet" />

    <!-- Custom Css -->
    <link href="<c:url value='/static/css/style.css'/>" rel="stylesheet">
</head>

<body class="login-page">
    <div class="login-box">
        <div class="logo">
            <a href="javascript:void(0);">GLOBAL FLOW</a>
            <small>Simplified and Effective</small>
        </div>
        <div class="card">
            <div class="body">
            <c:url var="loginUrl" value="/login" />
                <form action="${loginUrl}" id="sign_in" method="POST">
                    <div class="msg">Sign in to start your session</div>
					<c:if test="${param.error != null}">
						<div class="alert alert-danger">
							<p>Tên đăng nhập hoặc mật khẩu không đúng</p>
						</div>
					</c:if>
					<c:if test="${param.logout != null}">
						<div class="alert alert-success">
							<p>Bạn đã thoát</p>
						</div>
					</c:if>
					<div class="input-group">
                        <span class="input-group-addon">
                            <i class="material-icons">person</i>
                        </span>
                        <div class="form-line">
                            <input type="text" class="form-control" name="ssoId" placeholder="Tên đăng nhập" required autofocus>
                        </div>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">
                            <i class="material-icons">lock</i>
                        </span>
                        <div class="form-line">
                            <input type="password" class="form-control" name="password" placeholder="Mật khẩu" required>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-8 p-t-5">
                            <input type="checkbox" name="remember-me" id="rememberme" class="filled-in chk-col-pink">
                            <label for="rememberme">Nhớ tôi</label>
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
                        <div class="col-xs-4">
                            <button class="btn btn-block bg-pink waves-effect" type="submit">SIGN IN</button>
                        </div>
                    </div>
                    <div class="row m-t-15 m-b--20">
                        <div class="col-xs-6 align-right">
                            <a href="forgot-password.html">Quên mật khẩu?</a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Jquery Core Js -->
    <script src="<c:url value='/static/plugins/jquery/jquery.min.js'/>"></script>

    <!-- Bootstrap Core Js -->
    <script src="<c:url value='/static/plugins/bootstrap/js/bootstrap.js'/>"></script>

    <!-- Waves Effect Plugin Js -->
    <script src="<c:url value='/static/plugins/node-waves/waves.js'/>"></script>

    <!-- Validation Plugin Js -->
    <script src="<c:url value='/static/plugins/jquery-validation/jquery.validate.js'/>"></script>

    <!-- Custom Js -->
    <script src="<c:url value='/static/js/admin.js'/>"></script>
    <script src="<c:url value='/static/js/pages/examples/sign-in.js'/>"></script>
</body>

</html>