<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js"></script>
    <title>Title</title>
</head>
<body>
<script type="text/javascript">
    $(function () {
        $("#testJson").click(function () {
            $.ajax({
                type: "post",
                url: "${pageContext.request.contextPath}/testResponseJson",
                contentType: "application/json;charset=utf-8",
                data: '{"id":1,"username":"test","password":999.0}',
                dataType: "json",
                success: function (data) {
                    alert(data);
                }
            });
        });
    })
</script>
<!-- 测试异步请求 -->
<input type="button" value="测试 ajax 请求 json 和响应 json" id="testJson"/>
</body>
</html>
