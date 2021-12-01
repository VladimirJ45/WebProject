<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form name='loginForm' action="login-process" method="post" id="login-box"
      onsubmit="return onSubmit();">
    <div id="input_cont">
        <input type="text" id="username" name="username" placeholder="email" />
        <img id="usernameerror" src="/alert" title="Please enter email">
    </div>
    <div id="input_cont">
        <input type="password" id="password" name="password"
               placeholder="password" /> <img id="passworderror" src="/alert"
                                              title="Please enter password">
    </div>
        <button type="submit" class="button" id="button">LOGIN</button>
    <input type="hidden" name="${_csrf.parameterName}"
           value="${_csrf.token}" />
</form>

<script type="text/javascript">
    var url = window.location.href;
    var msg = document.getElementById('invalid');
    if (url.search('invalid') > 0) {
        msg.style.display = "block";
    } else if (url.search('new') > 0) {
        document.getElementById('new').style.display = "block";
    }
</script>
</body>
</html>
