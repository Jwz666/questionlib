var editId;
var editTags;
$(function () {
    editId=getQueryVariable("id");
    $.ajax({
        async : false,    //表示请求是否异步处理
        type : "get",    //请求类型
        url : "/grades/getGradeInfoById",//请求的 URL地址
        data: {id:editId},
        dataType : "json",//返回的数据类型
        success: function (data) {
            console.log(data);  //在控制台打印服务器端返回的数据
            $("#editTagsName").val(data.gradeName);
            editTags=data;
            //子标签刷新
            showDepend(data)

        },
        error:function (data) {

            $.alert({
                title: "",
                content: "请刷新重试",
                onClose: function () {
                }
            });
        }

    });
    //得到全部能力点标签
    $("#coreIntelligence").trigger('change');
    $("#coreAbility").trigger('change');
});
//得到子标签列表
function showDepend(parentdata) {
    $("#abilityTag").empty();
    $("#intelligenceTag").empty();
    $.ajax({
        async : false,    //表示请求是否异步处理
        type : "get",    //请求类型
        url : "/grades/getTagsByGradesId",//请求的 URL地址
        data:{id:parentdata.id} ,
        dataType : "json",//返回的数据类型
        success: function (data) {
            console.log(data);  //在控制台打印服务器端返回的数据
            if(data.code=='200') {
                var tagList=data.body;
                if(tagList.length != 0) {
                    for(var i=0;i<tagList.length;i++) {
                        if (tagList[i].sonTags.tagType==1){
                             $("#abilityTag").append(
                                  '<span class="_tags" id="'+tagList[i].sonTags.id+'">'+tagList[i].parentTags.tagName+"-"+tagList[i].sonTags.tagName+' <i class="_tagsDelBtn">✖</i></span>'
                        );}
                        if (tagList[i].sonTags.tagType==2){
                            $("#intelligenceTag").append(
                                  '<span class="_tags" id="'+tagList[i].sonTags.id+'">'+(tagList[i].parentTags!=null?tagList[i].parentTags.tagName+"-":"")+tagList[i].sonTags.tagName+' <i class="_tagsDelBtn">✖</i></span>'
                            );
                        }
                    }
                }
            }

        },
        error:function (data) {
            $.alert({
                title: "",
                content: "请刷新重试",
                onClose: function () {
                }
            });
        }

    });



}
// 知识点
//得到根据tagType以及是否有parent得到tagList
//能力
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
                    if (tagList[i].tagType == 1) {
                        $("#coreAbility").append(
                            '<option value="' + tagList[i].id + '">' + tagList[i].tagName + '</option>'
                        );
                    }
                }
            }

        }

    },
    error:function (data) {
        $.alert({
            title: "",
            content: "请刷新重试",
            onClose: function () {
            }
        });
    }

});
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
            }

        }
    },
    error:function (data) {
        $.alert({
            title: "",
            content: "请刷新重试",
            onClose: function () {
            }
        });
    }

});
$("body").on('change','#coreAbility',function () {
    $("#otherAbility").empty();
    $("#otherAbility").append(
        '<option value="" ></option>>'
    );
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
            $.alert({
                title: "",
                content: "请刷新重试",
                onClose: function () {
                }
            });
        }

    });
});
$("body").on('change','#coreIntelligence',function () {
    $("#otherIntelligence").empty();
    $("#otherIntelligence").append(
        '<option value="" ></option>>'
    );
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
            $.alert({
                title: "",
                content: "请刷新重试",
                onClose: function () {
                }
            });
        }

    });
});

$("body").on('click','._tagsDelBtn',function () {

    deleteTagId=$(this).parent().attr("id");
    $.confirm({
        title: "",

        content: "确定要删除这个标签吗？",
        buttons:{
            confirm:{
                text:"确定",
                action: function () {
                    var tagsId=deleteTagId;
                    //要删除的grade的id是 editid
                    $.ajax({
                        async : false,    //表示请求是否异步处理
                        type : "post",    //请求类型
                        url : "/grades/deleteGradesTag",//请求的 URL地址
                        data:{gradesId:editId,tagsId:tagsId} ,
                        dataType : "json",//返回的数据类型
                        success: function (data) {
                            console.log(data);  //在控制台打印服务器端返回的数据
                            showDepend(editTags)
                        },
                        error:function (data) {
                            $.alert({
                                title: "",
                                content: "请刷新重试",
                                onClose: function () {
                                }
                            });
                        }

                    });

                }
            },
            cancel:{
                text:"取消"
            }
        }

    });

});
//获取url参数
$("#saveAblity").click(function () {
    appendDependcy( $("#otherAbility").val())
})
$("#saveIntelligence").click(function () {

   if($("#otherIntelligence").val()!=null&&$("#otherIntelligence").val()!=""){ appendDependcy( $("#otherIntelligence").val())}
    else  if($("#coreIntelligence").val()!=null&&$("#coreIntelligence").val()!="") { appendDependcy( $("#coreIntelligence").val())}
})
function appendDependcy(tagid) {
   var GradesTags={};
    GradesTags.gradeId=editId;
    GradesTags.tagId=tagid
    $.ajax({
        // async : false,    //表示请求是否异步处理
        type : "post",    //请求类型
        url : "/tags/insertGradesTags",//请求的 URL地址
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
                });
            }
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
function updateGrade() {
    var Grades=editTags;
    Grades.id=editId;
    Grades.gradeName=$("#editTagsName").val();
    $.ajax({
        // async : false,    //表示请求是否异步处理
        type : "post",    //请求类型
        url : "/grades/updateGrade",//请求的 URL地址
        data: Grades,
        dataType : "json",//返回的数据类型
        success: function (data) {
            console.log(data);  //在控制台打印服务器端返回的数据
            if (data.code == '200') {
                $.alert({
                    title: "提示",
                    content: data.message,
                    onClose: function () {
                    }
                });
            }
            if (data.code == '500') {
                $.alert({
                    title: "",
                    content: data.message,
                    onClose: function () {
                    }
                });
            }
        }
    });

}
