$(function () {
    var pageInfo={};
    pageInfo.page=1;
    pageInfo.size=100;
    $.ajax({
        // async : false,    //表示请求是否异步处理
        type : "get",    //请求类型
        url : "/tags/getTagsByPage",//请求的 URL地址
        data: pageInfo,
        dataType : "json",//返回的数据类型
        success: function (data) {
            console.log(data);  //在控制台打印服务器端返回的数据
            if(data.code=='200') {
               tagList=data.body.records;
                if(tagList.length != 0) {
                    for(var i=0;i<tagList.length;i++) {
                        $("#tagList").append(
                            ' <tr id="'+tagList[i].id+'">\n' +
                            '     <td id="trTagName">'+tagList[i].tagName+'</td>\n' +
                            '     <td id="trTagType">'+tagType(tagList[i].tagType)+'</td>\n' +
                            '     <td></td>\n' +
                            '     <td>\n' +
                            '         <button class="btn btn-sm btn-primary btn-uppercase editTagsBtn">编辑</button>\n' +
                            '     </td>\n' +
                            ' </tr>\n' +
                            ' <tr>                                                                /n'
                        );
                    }
                }
            }
            if(data.code=='500') {
                alert(data.message);
            }
        },
        error:function (data) {
            alert(data.message);
        }

    });
// 父标签插入
    $("#insertParentTag").click(function () {

        var  insertTag={};
        insertTag.tagName=$("#insertParentTagName").val();
        insertTag.tagType=$("#insertParentTagType").val();
        insertTag.parentId=0;
        $.ajax({
            // async : false,    //表示请求是否异步处理
            type : "post",    //请求类型
            url : "/tags/insertTags",//请求的 URL地址
            data: insertTag,
            dataType : "json",//返回的数据类型
            success: function (data) {
                $("#insertModalBillingInfo").modal("hide")
                console.log(data);  //在控制台打印服务器端返回的数据
                if (data.code == '200') {
                    $.alert({
                        title: "提示",
                        content: data.message,
                        onClose: function () {
                            window.location.reload();
                        }
                    })}
                if (data.code == '500') {
                    $.alert({
                        title: "提示",
                        content: data.message,
                        onClose: function () {
                            window.location.reload();
                        }
                    });
                }
            }
        });



    });
});

function tagType(tagType) {
    if (tagType==1){return "能力点";}
    if (tagType=="能力点"){return 1;}
    if (tagType==2){return "知识点";}
    if (tagType=="知识点"){return 2;}

}
// updateTagId=null;
$("body").on('click','.editTagsBtn',function () {
    thistr = $(this).parent().parent();
    updateTagId = thistr.attr('id');
    $.ajax({
        // async : false,    //表示请求是否异步处理
        type: "post",    //请求类型
        url: "/tags/readyToEdit",//请求的 URL地址
        data: {id:updateTagId},
        dataType: "json",//返回的数据类型
        success: function (data) {
            console.log(data);  //在控制台打印服务器端返回的数据
            if (data.code == '200') {
                window.location.href = "math-tagsedit.html?id="+updateTagId;
            }
        }
    });
});
$("#selectShowType").change(function() {
    $("#tagList").empty();
    if(tagList.length != 0) {
        for(var i=0;i<tagList.length;i++) {
            if(tagList[i].tagType==$(this).val()) {
                $("#tagList").append(
                    ' <tr id="' + tagList[i].id + '">\n' +
                    '     <td id="trTagName">' + tagList[i].tagName + '</td>\n' +
                    '     <td id="trTagType">' + tagType(tagList[i].tagType) + '</td>\n' +
                    '     <td></td>\n' +
                    '     <td>\n' +
                    '         <button class="btn btn-sm btn-primary btn-uppercase editTagsBtn">编辑</button>\n' +
                    '     </td>\n' +
                    ' </tr>\n' +
                    ' <tr>                                                                /n'
                );
            }
            if ("all"==$(this).val()) {
                $("#tagList").append(
                    ' <tr id="' + tagList[i].id + '">\n' +
                    '     <td id="trTagName">' + tagList[i].tagName + '</td>\n' +
                    '     <td id="trTagType">' + tagType(tagList[i].tagType) + '</td>\n' +
                    '     <td></td>\n' +
                    '     <td>\n' +
                    '         <button class="btn btn-sm btn-primary btn-uppercase editTagsBtn">编辑</button>\n' +
                    '     </td>\n' +
                    ' </tr>\n' +
                    ' <tr>                                                                /n'
                );
            }
        }
    }
})







   




