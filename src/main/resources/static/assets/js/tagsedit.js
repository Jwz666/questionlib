$(function () {
    editId=getQueryVariable("id");
    $.ajax({
        async : false,    //表示请求是否异步处理
        type : "get",    //请求类型
        url : "/tags/getTagInfoById",//请求的 URL地址
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


});
//得到子标签列表
function showChildrenTags(parentdata) {
    pageinfo={};
    pageinfo.page=1;
    pageinfo.size=1000;
    pageinfo.parentId=parentdata.id;
    $.ajax({
        async : false,    //表示请求是否异步处理
        type : "get",    //请求类型
        url : "/tags/getChildrenTags",//请求的 URL地址
        data: pageinfo,
        dataType : "json",//返回的数据类型
        success: function (data) {
            console.log(data);  //在控制台打印服务器端返回的数据
            if(data.code=='200') {
                var tagList=data.body.records;
                if(tagList.length != 0) {
                    for(var i=0;i<tagList.length;i++) {
                        $("#childrenTags").append(
                            ' <tr id="'+tagList[i].id+'">\n' +
                            '     <td id="trTagName">'+tagList[i].tagName+'</td>\n' +
                            '     <td id="trTagType" hidden="hidden">'+tagType(tagList[i].tagType)+'</td>\n' +
                            '     <td>\n' +
                            '         <button class="btn btn-sm btn-white btn-uppercase editTagsBtn">编辑</button>\n' +
                            '     </td>\n' +
                            ' </tr>\n' +
                            ' <tr>                                                                /n'

                        );
                    }
                }
            }

        },
        error:function (data) {
            alert("请刷新重试");
        }

    });

}

//主标签修改
$("body").on('click','.editMasterTagsBtn',function () {
    updateTagId = editId;
    $("#updateTagName").val(editTags.tagName);
    $("#updateTagType").val(editTags.tagType);
    $("#updateModalBillingInfo").modal("show");
});
//子标签修改子标签修改也和主标签一样了
$("body").on('click','.editTagsBtn',function () {

    if (editTags.tagType==1){
    window.location.href="math-tagsedit.html?id="+$(this).parent().parent().attr('id');
    }
    if (editTags.tagType==2) {
        thistr=$(this).parent().parent();
        updateTagId = thistr.attr('id');
        $("#updateTagName").val(thistr.find("#trTagName").text());
        // $("#updateTagType").val(tagType(thistr.find("#trTagType").text()));
        $("#updateModalBillingInfo").modal("show")
    }
});
//模态框更新tag操作
$("body").on('click','#updateTag',function () {
    var  updateTag={};
    updateTag.id=updateTagId;
    updateTag.tagName=$("#updateTagName").val();
    // updateTag.tagType=$("#updateTagType").val();
    $.ajax({
        // async : false,    //表示请求是否异步处理
        type : "post",    //请求类型
        url : "/tags/updateTag",//请求的 URL地址
        data: updateTag,
        dataType : "json",//返回的数据类型
        success: function (data) {
            $("#updateModalBillingInfo").modal("hide")
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
})
//tagType再现
function tagType(tagType) {
    if (tagType==0){return "无";}
    if (tagType=="无"){return 0;}
    if (tagType==1){return "能力点";}
    if (tagType=="能力点"){return 1;}
    if (tagType==2){return "知识点";}
    if (tagType=="知识点"){return 2;}

}
//获取url参数
function getQueryVariable(variable)
{
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if(pair[0] == variable){return pair[1];}
    }
    return(false);
}
$("body").on('click','#insertChildrenTag',function () {

    var  insertTag={};
    insertTag.parentId=editId;
    insertTag.tagName=$("#insertchildrenTagName").val();
    insertTag.tagType=editTags.tagType;
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