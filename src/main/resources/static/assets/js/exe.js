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
});

function getData(pageInfo={}) {

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
                        console.log("heheh = " + questionList[i].questionType);
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
                        console.log("situation 2--"+currentPageIndex);
                        console.log("totalpage--"+totalPage);
                        var page1=parseInt(currentPageIndex);
                        var page2=parseInt(currentPageIndex)-1;
                        var page3=parseInt(currentPageIndex)-2;
                        console.log("page----"+page1);
                        console.log("page----"+page2);
                        console.log("page----"+page3);
                        $("#nextPage").before('<li class="page-item" data-id=' + page3 + '><a class="page-link" href="#" onclick="pageChange(this)">' + page3 + '</a></li>');
                        $("#nextPage").before('<li class="page-item" data-id=' + page2 + '><a class="page-link" href="#" onclick="pageChange(this)">' + page2 + '</a></li>');
                        $("#nextPage").before('<li class="page-item" data-id=' + page1 + '><a class="page-link" href="#" onclick="pageChange(this)">' + page1 + '</a></li>');
                    } else {
                        var page1=parseInt(currentPageIndex)-1;
                        var page2=parseInt(currentPageIndex);
                        var page3=parseInt(currentPageIndex)+1;
                        console.log("situation 3--"+currentPageIndex);
                        console.log("page----"+page1);
                        console.log("page----"+page2);
                        console.log("page----"+page3);
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
                alert(data.message);
            }
        },
        error:function (data) {
            alert(data.message);
        }

    });
}

function edit(id) {
    var  pageInfo={};
    pageInfo.page=1;
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

