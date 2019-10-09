var editId;
var editTags;
$(function () {



    getQuestionType();
    if(getQueryVariable("newQuest")==false){
        getData();
    }else{
        addNewQuestion()
    }
    CKEDITOR.replace( 'content' );
    CKEDITOR.replace( 'answer' );
    CKEDITOR.replace( 'analyis' );
    var data={}
    data.id=$('#id').val();
    if (editId==null||editId=="") {
        editId=$('#id').val();
        data.id=$('#id').val();
        editTags = data;

        showDepend(data)
    }
    //得到全部能力点标签
});

tagsId=[];

function getData(){

    var questionInfo = window.location.href;
    console.log('index of ='+questionInfo.indexOf("="));
    if(questionInfo.indexOf("=")!= -1) {
        var twodata = questionInfo.split("="); //截取 url中的“=”,获得“=”后面的参数
        var data = decodeURI(twodata[1]); //decodeURI解码
        var jsonData=JSON.parse(data);
        console.log(jsonData.id);
        dataToTable(jsonData);
    }

}

function dataToTable(data) {
    console.log("in data to table");
    console.log(data);
    $("#id").val(data.id);
    $.ajax({
        // async : false,    //表示请求是否异步处理
        type : "get",    //请求类型
        url : "/baseQuestions/getQuestionList",//请求的 URL地址
        data: data,
        dataType : "json",//返回的数据类型
        success: function (data) {
            console.log(data);  //在控制台打印服务器端返回的数据
            if (data.code == '200') {
                var questionList = data.body;
                if (questionList.length != 0) {
                    var questionInfo=null;
                    for (var i = 0; i < questionList.length; i++) {
                        questionInfo = questionList[i];
                    }

                    $("#content").text(questionInfo.content);
                    $("#type").val(questionInfo.questionType);
                    $("#answer").val(questionInfo.answer);
                    $("#analyis").text(questionInfo.analysis);
                    $("#years").val(questionInfo.questionDesignTime);
                } else {
                    alert("暂无数据");
                }
            }
            if (data.code == '500') {
                alert(data.message);
            }
        }
    });

}

function getQuestionType() {
    $.ajax({
        // async : false,    //表示请求是否异步处理
        type : "get",    //请求类型
        url : "/baseQuestions/getQuestionType",//请求的 URL地址
        dataType : "json",//返回的数据类型
        success: function (data) {
            console.log(data);  //在控制台打印服务器端返回的数据
            if (data.code == '200') {
                var types=data.body;
                if(types.length != 0) {
                    for (var i = 0; i < types.length; i++) {

                        $("#type").append(' <option value='+types[i].questionTypeCode+'>'+types[i].questionTypeName+'</option>');
                    }
                }
            }
            if (data.code == '500') {
                alert(data.message);
            }
        }
    });


}

function addOrEdit(status) {

    var questionInfo={};
    questionInfo.id=$('#id').val();
    questionInfo.content=CKEDITOR.instances.content.getData();
    questionInfo.questionType=$("#type").val();
    questionInfo.status=status;
    if(questionInfo.questionType==-1){
        alert("请修改题目类型");
        return;
    }
    //questionInfo.optionList=
    questionInfo.answer=CKEDITOR.instances.answer.getData();
    questionInfo.analysis=CKEDITOR.instances.analyis.getData();
    questionInfo.questionDesignTime=$("#years").val();
    console.log(questionInfo);
    var flag=vaild(questionInfo);
    if(!flag) {
        return;
    }
    //questionDesignTime
    $.ajax({
        // async : false,    //表示请求是否异步处理
        type : "post",    //请求类型
        url : "/baseQuestions/addOrUpdateQuestion",
        contentType: 'application/json',//请求的 URL地址
        data:JSON.stringify(questionInfo),
        dataType : "json",//返回的数据类型
        success: function (data) {
            console.log(data);  //在控制台打印服务器端返回的数据
            if (data.code == '200') {
                alert("操作成功");
                window.location.href="math-exe.html";
            }
            if (data.code == '500') {
                alert(data.message);
            }
        }
    });
}


function vaild(questionInfo) {
    if(questionInfo.content==null || questionInfo.content=='') {
        alert("请添加题干内容");
        return false;
    }
    if(questionInfo.answer==null || questionInfo.answer=='') {
        alert("请添加答案内容");
        return false;
    }
    return true;
}
$('#years').datepicker({
    startView: 2,
    minViewMode: 2,
    maxViewMode: 2,
    language: "zh-CN",
    format:"yyyy",
    autoclose: true
});



//得到子标签列表
function showDepend(parentdata) {
    $("#abilityTag").empty();
    $("#intelligenceTag").empty();
    if(!(parentdata.id==null||parentdata.id==0||parentdata.id=="")){


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
                        if (tagList[i].sonTags.tagType==1){
                            $("#abilityTag").append(
                                '<span class="_tags" id="'+tagList[i].sonTags.id+'">'+tagList[i].parentTags.tagName+"-"+tagList[i].sonTags.tagName+' <i class="_tagsDelBtn">✖</i></span>'
                            );}
                        if (tagList[i].sonTags.tagType==2){
                            $("#intelligenceTag").append(
                                '<span class="_tags" id="'+tagList[i].sonTags.id+'">'+(tagList[i].parentTags!=null?tagList[i].parentTags.tagName+"-":"")+"-"+tagList[i].sonTags.tagName+' <i class="_tagsDelBtn">✖</i></span>'
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
    }

}
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
    if($("#otherIntelligence").val()!=null&&$("#otherIntelligence").val()!=""){ appendDependcy( $("#otherIntelligence").val())}
    else  if($("#coreIntelligence").val()!=null&&$("#coreIntelligence").val()!="") { appendDependcy( $("#coreIntelligence").val())}
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
function addNewQuestion() {
    var questioninfo2={};
    questioninfo2.questionType="-1";
    questioninfo2.id=null;
    questioninfo2.content=null;
    questioninfo2.optionList=null;
    questioninfo2.answer=null;
    questioninfo2.analysis=null;
    questioninfo2.createdAt=null;
    questioninfo2.updatedAt=null;
    questioninfo2.status=null;
    questioninfo2.source=null;
    questioninfo2.comment=null;
    questioninfo2.questionDesignTime=null;
    questioninfo2.creatorId=null;


    $.ajax({
        // async : false,    //表示请求是否异步处理
        type : "post",    //请求类型
        url : "/baseQuestions/addOrUpdateQuestion",
        contentType: 'application/json',//请求的 URL地址
        data: JSON.stringify(questioninfo2),
        dataType : "json",//返回的数据类型
        success: function (data) {
            console.log(data);  //在控制台打印服务器端返回的数据
            if (data.code == '200') {
                var questionInfo=data.body;
                editId=questionInfo.id;
                editTags=questionInfo;
                $("#id").val(questionInfo.id);
                // $("#content").text(questionInfo.content);
                $("#type").val(questionInfo.questionType);
                // $("#answer").val(questionInfo.answer);
                // $("#analyis").text(questionInfo.analysis);
                // $("#years").val(questionInfo.questionDesignTime);
            }
            if (data.code == '500') {
                alert(data.message);
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