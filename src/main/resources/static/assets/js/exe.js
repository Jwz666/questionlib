var questionsStatus=null;
var totalPage = 0;
var currentPageIndex=0;

$(function () {
    if ( getQueryVariable("questionsStatus")=="0"||getQueryVariable("questionsStatus")!=false){
        questionsStatus=getQueryVariable("questionsStatus");
    }

    getData();


    $('.select2').select2({
        placeholder: '添加搜索标签',
        maximumSelectionLength: 5,
        allowClear: true
    });
    $("#questionListName").text($("#questionListName").text()+"-"+convertQuestionName(questionsStatus));
});
function convertQuestionName(questionsStatus) {
    if(questionsStatus==0)return "草稿";
    if(questionsStatus==1)return "已审核";
    if(questionsStatus==2)return "已完成";
    if(questionsStatus=="草稿")return 0;
    if(questionsStatus=="已审核")return 1;
    if(questionsStatus=="已完成")return 2;
    return null;


}
function getData(pageInfo={}) {
    var localStoPage=window.localStorage.getItem("currentPage");
    if(localStoPage!=null) {
        pageInfo.page=localStoPage;
        currentPageIndex=localStoPage;
        window.localStorage.removeItem("currentPage");
    }

    if(pageInfo.page==null || pageInfo.page == undefined || pageInfo.page=='') {
        pageInfo.page=1;
        pageInfo.status=questionsStatus;
        currentPageIndex=1;
    }


    pageInfo.size=20;
    $.ajax({
        // async : false,    //表示请求是否异步处理
        type : "get",    //请求类型
        url : "/baseQuestions/getQuestionByPage",//请求的 URL地址
        data: pageInfo,
        dataType : "json",//返回的数据类型
        success: function (data) {
            console.log(data);  //在控制台打印服务器端返回的数据
            if(data.code=='200') {
                var page=data.body;
                var questionList=data.body.records;
                if(questionList.length != 0) {
                    for(var i=0;i<questionList.length;i++) {

                        $("#questionList").append(' <tr >\n' +
                            '                                <td >'+questionList[i].id+'</td>\n' +
                            '                                <td >'+questionList[i].content+'</td>\n' +
                            '                                <td>'+questionTypeFormatter(questionList[i].questionType)+'</td>\n' +
                            '                                <td>'+timeStamp2String(questionList[i].createdAt)+'</td>\n' +
                            '                                <td >\n' +
                            '<a href="javascript:edit('+questionList[i].id+')">查看</a>'+

                            '                                </td>\n' +
                            '<td></td>'+
                            '                                <td><span class="badge badge-success">启用</span></td>\n' +
                            '                            </tr>');
                    }
                }
                totalPage=page.pages;
                currentPageIndex=parseInt(currentPageIndex);
                var showPage=totalPage;
                $(".page-item").remove();
                if(totalPage>3) {
                    if(currentPageIndex-1<1) {
                        var page1=currentPageIndex;
                        var page2=currentPageIndex+1;
                        var page3=currentPageIndex+2;
                        $("#nextPage").before('<li class="page-item" data-id=' + page1 + '><a class="page-link" href="#" onclick="pageChange(this)">' + page1 + '</a></li>');
                        $("#nextPage").before('<li class="page-item" data-id=' + page2 + '><a class="page-link" href="#" onclick="pageChange(this)">' + page2 + '</a></li>');
                        $("#nextPage").before('<li class="page-item" data-id=' + page3 + '><a class="page-link" href="#" onclick="pageChange(this)">' + page3 + '</a></li>');
                    }else if(currentPageIndex+1>totalPage) {

                        var page1=parseInt(currentPageIndex);
                        var page2=parseInt(currentPageIndex)-1;
                        var page3=parseInt(currentPageIndex)-2;

                        $("#nextPage").before('<li class="page-item" data-id=' + page3 + '><a class="page-link" href="#" onclick="pageChange(this)">' + page3 + '</a></li>');
                        $("#nextPage").before('<li class="page-item" data-id=' + page2 + '><a class="page-link" href="#" onclick="pageChange(this)">' + page2 + '</a></li>');
                        $("#nextPage").before('<li class="page-item" data-id=' + page1 + '><a class="page-link" href="#" onclick="pageChange(this)">' + page1 + '</a></li>');
                    } else {
                        var page1=parseInt(currentPageIndex)-1;
                        var page2=parseInt(currentPageIndex);
                        var page3=parseInt(currentPageIndex)+1;

                        $("#nextPage").before('<li class="page-item" data-id=' + page1 + '><a class="page-link" href="#" onclick="pageChange(this)">' + page1 + '</a></li>');
                        $("#nextPage").before('<li class="page-item" data-id=' + page2 + '><a class="page-link" href="#" onclick="pageChange(this)">' + page2 + '</a></li>');
                        $("#nextPage").before('<li class="page-item" data-id=' + page3 + '><a class="page-link" href="#" onclick="pageChange(this)">' + page3 + '</a></li>');
                    }
                } else {

                    for (i = 0; i < totalPage; i++) {
                        var current = i + 1;
                        $("#nextPage").before('<li class="page-item" data-id=' + current + '><a class="page-link" href="#" onclick="pageChange(this)">' + current + '</a></li>');

                    }
                }

            }
            if(data.code=='500') {
                $.alert({
                    title: "",
                    content: data.message,
                    onClose: function () {
                    }
                });
            }
        },
        error:function (data) {
            $.alert({
                title: "",
                content: data.message,
                onClose: function () {
                }
            });
        }

    });
}

function edit(id) {
    var  pageInfo={};
    pageInfo.page=currentPageIndex;
    pageInfo.size=10;
    pageInfo.id=id;
    var url=encodeURI("math-exedit.html?questionId="+JSON.stringify(pageInfo));
    window.location.href = url;


}
function add() {
    window.location.href="math-exedit.html?newQuest=true";
}
function pageChange(target) {
    currentPageIndex=$(target).text();
    console.log(currentPageIndex);
    var pageInfo={};
    pageInfo.page=currentPageIndex;
    getData(pageInfo);
    $("#questionList").empty();

}

function nextPage() {
    var nextPage=parseInt(currentPageIndex)+1;
    console.log(nextPage);
    if(nextPage>totalPage) {
        return;
    }
    currentPageIndex=nextPage;
    console.log(nextPage);
    var pageInfo={};
    pageInfo.page=nextPage;
    getData(pageInfo);
    $("#questionList").empty();

}
function lastPage() {

    var lastPage=currentPageIndex-1;
    console.log(lastPage);
    if(lastPage<0) {
        return;
    }
    currentPageIndex=lastPage;
    console.log(lastPage);
    var pageInfo={};
    pageInfo.page=lastPage;
    getData(pageInfo);
    $("#questionList").empty();

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


$("#customFile").on("change", function () {
    var file = document.getElementById('customFile').files[0];
    $(this).siblings("label").text(file.name);
})

function uploadWord() {
    var form = new FormData();
    var file = document.getElementById('customFile').files[0];
    form.append("file", file);
    $.ajax({
        //async : false,    //表示请求是否异步处理
        type: "post",    //请求类型
        url: "/uploadWord",
        contentType: false,
        processData: false,
        data: form,
        dataType: "json",//返回的数据类型
        success: function (data) {
            console.log(data);  //在控制台打印服务器端返回的数据
            if (data.code == '200') {
                $.alert({
                    title: "",
                    content: '导入成功',
                    onClose: function () {
                    }
                });
                $('#modalBillingInfo').modal('hide');
                $("#questionList").empty();
                getData();
            }
            if (data.code == '500') {
                $.alert({
                    title: "",
                    content: data.message,
                    onClose: function () {
                        $('#modalBillingInfo').modal('hide');
                    }
                });

            }

        },
        error: function (data) {
            $.alert({
                title: "",
                content: data.message,
                onClose: function () {
                }
            });
        }

    });

}

function downloadTemplate() {

    window.location.href = "/downloadTemplate";
}

