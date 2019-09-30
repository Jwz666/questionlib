var editId;
var editTags;
$(function () {
    var data={}
    data.id=$('#id').val();
    editId=data.id;
    editTags=data;
            showDepend(data)

    //得到全部能力点标签
});
//得到子标签列表
function showDepend(parentdata) {
    $("#abilityTag").empty();
    $("#intelligenceTag").empty();
    $.ajax({
        async : false,    //表示请求是否异步处理
        type : "get",    //请求类型
        url : "/baseQuestions/getTagsByQuestionId",//请求的 URL地址
        data:{id:parentdata.id} ,
        dataType : "json",//返回的数据类型
        success: function (data) {
            console.log(data);  //在控制台打印服务器端返回的数据
            if(data.code=='200') {
                var tagList=data.body;
                if(tagList.length != 0) {
                    for(var i=0;i<tagList.length;i++) {
                        if (tagList[i].tagType==1){
                            $("#abilityTag").append(
                                '<span class="_tags" id="'+tagList[i].id+'">'+tagList[i].tagName+' <i class="_tagsDelBtn">✖</i></span>'
                            );}
                        if (tagList[i].tagType==2){
                            $("#intelligenceTag").append(
                                '<span class="_tags" id="'+tagList[i].id+'">'+tagList[i].tagName+' <i class="_tagsDelBtn">✖</i></span>'
                            );
                        }
                    }
                }
            }

        },
        error:function (data) {
            alert("请刷新重试");
        }

    });

    $.ajax({
        async : false,    //表示请求是否异步处理
        type : "get",    //请求类型
        url : "/tags/getTagsByTypeAndParentId",//请求的 URL地址
        data:{tagType:1,parentId:0} ,
        dataType : "json",//返回的数据类型
        success: function (data) {
            console.log(data);  //在控制台打印服务器端返回的数据
            if(data.code=='200') {
                var tagList=data.body;
                if(tagList.length != 0) {
                    for(var i=0;i<tagList.length;i++) {
                        if (tagList[i].tagType==1){
                            $("#coreAbility").append(
                                '<option value="'+tagList[i].id+'">'+tagList[i].tagName+'</option>'
                            );}

                    }
                    $("#coreAbility").trigger('change');
                }
            }

        },
        error:function (data) {
            alert("请刷新重试");
        }

    });
    // 知识点
    $.ajax({
        async : false,    //表示请求是否异步处理
        type : "get",    //请求类型
        url : "/tags/getTagsByTypeAndParentId",//请求的 URL地址
        data:{tagType:2,parentId:0} ,
        dataType : "json",//返回的数据类型
        success: function (data) {
            console.log(data);  //在控制台打印服务器端返回的数据
            if(data.code=='200') {
                var tagList=data.body;
                if(tagList.length != 0) {
                    for(var i=0;i<tagList.length;i++) {
                        if (tagList[i].tagType==2){
                            $("#coreIntelligence").append(
                                '<option value="'+tagList[i].id+'">'+tagList[i].tagName+'</option>'
                            );}
                    }
                    $("#coreIntelligence").trigger('change');
                }
            }

        },
        error:function (data) {
            alert("请刷新重试");
        }

    });

}
$("body").on('change','#coreAbility',function () {
    $("#otherAbility").empty();
    var coreid=$(this).val();
    $.ajax({
        async : false,    //表示请求是否异步处理
        type : "get",    //请求类型
        url : "/tags/getTagsByTypeAndParentId",//请求的 URL地址
        data:{tagType:1,parentId:coreid} ,
        dataType : "json",//返回的数据类型
        success: function (data) {
            console.log(data);  //在控制台打印服务器端返回的数据
            if(data.code=='200') {
                var tagList=data.body;
                if(tagList.length != 0) {
                    for(var i=0;i<tagList.length;i++) {
                        $("#otherAbility").append(
                            '<option value="'+tagList[i].id+'">'+tagList[i].tagName+'</option>'
                        );

                    }
                }

            }

        },
        error:function (data) {
            alert("请刷新重试");
        }

    });
});
$("body").on('change','#coreIntelligence',function () {
    $("#otherIntelligence").empty();
    var coreid=$(this).val();
    $.ajax({
        async : false,    //表示请求是否异步处理
        type : "get",    //请求类型
        url : "/tags/getTagsByTypeAndParentId",//请求的 URL地址
        data:{tagType:2,parentId:coreid} ,
        dataType : "json",//返回的数据类型
        success: function (data) {
            console.log(data);  //在控制台打印服务器端返回的数据
            if(data.code=='200') {
                var tagList=data.body;
                if(tagList.length != 0) {
                    for(var i=0;i<tagList.length;i++) {
                        $("#otherIntelligence").append(
                            '<option value="'+tagList[i].id+'">'+tagList[i].tagName+'</option>'
                        );

                    }
                }

            }

        },
        error:function (data) {
            alert("请刷新重试");
        }

    });
});

$("body").on('click','._tagsDelBtn',function () {
    //要删除的标签的id
    var confirm_result = confirm("确定要删除这个标签吗？");
    if (!confirm_result) {
        return false;
    }
    var tagsId=$(this).parent().attr("id");
    //要删除的grade的id是 editid
    $.ajax({
        async : false,    //表示请求是否异步处理
        type : "post",    //请求类型
        url : "/baseQuestions/deleteQuestionsTag",//请求的 URL地址
        data:{gradesId:editId,tagsId:tagsId} ,
        dataType : "json",//返回的数据类型
        success: function (data) {
            console.log(data);  //在控制台打印服务器端返回的数据
            showDepend(editTags)
        },
        error:function (data) {
            alert("请刷新重试");
        }

    });

});
//获取url参数
$("#saveAblity").click(function () {
    appendDependcy( $("#otherAbility").val())
})
$("#saveIntelligence").click(function () {
    appendDependcy( $("#otherIntelligence").val())
})
function appendDependcy(tagid) {
    var GradesTags={};
    GradesTags.questionId=editId;
    GradesTags.tagId=tagid;
    $.ajax({
        // async : false,    //表示请求是否异步处理
        type : "post",    //请求类型
        url : "/tags/insertQuestionsTags",//请求的 URL地址
        data: GradesTags,
        dataType : "json",//返回的数据类型
        success: function (data) {
            console.log(data);  //在控制台打印服务器端返回的数据
            if (data.code == '200') {
                $.alert({
                    title: "提示",
                    // buttons: {
                    //     close: {
                    //         text: "知道了"
                    //     }
                    // },
                    content: data.message,
                    onClose: function () {
                        showDepend(editTags);
                        $(".modal").modal('hide');
                    }
                })}
            if (data.code == '500') {
                $.alert({
                    title: "",
                    content: data.message,
                    onClose: function () {
                        flushTags()
                    }
                });
            }
        }
    });
}
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