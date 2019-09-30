var editId;
var editTags;
$(function () {
    editId=getQueryVariable("id");
    $.ajax({
        async : false,    //表示请求是否异步处理
        type : "get",    //请求类型
        url : "/grade/getGradeInfoById",//请求的 URL地址
        data: {id:editId},
        dataType : "json",//返回的数据类型
        success: function (data) {
            console.log(data);  //在控制台打印服务器端返回的数据
            $("#editTagsName").val(data.tagName);
            editTags=data;
            //子标签刷新
            showChildrenTags(data)
        },
        error:function (data) {
            alert("请刷新重试");
        }

    });
    //得到全部能力点标签
});