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
                var tagList=data.body.records;
                if(tagList.length != 0) {
                    for(var i=0;i<tagList.length;i++) {
                        $("#tagList").append(
                            ' <tr id="'+tagList[i].id+'">\n' +
                            '     <td id="trTagName">'+tagList[i].tagName+'</td>\n' +
                            '     <td id="trTagType">'+tagType(tagList[i].tagType)+'</td>\n' +
                            '     <td>'+i+1+'</td>\n' +
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


    $('.select2').select2({
        placeholder: '添加搜索标签',
        maximumSelectionLength: 5,
        allowClear: true
    });
});

function tagType(tagType) {
    if (tagType==1){return "能力点";}
    if (tagType=="能力点"){return 1;}
    if (tagType==2){return "知识点";}
    if (tagType=="知识点"){return 2;}


}


updateTagId=null;

$("body").on('click','.editTagsBtn',function () {
    thistr=$(this).parent().parent();
    updateTagId = thistr.attr('id');
    $("#updateTagName").val(thistr.find("#trTagName").text());
    $("#updateTagType").val(tagType(thistr.find("#trTagType").text()));
    $("#updateModalBillingInfo").modal("show")
})

$("body").on('click','#updateTag',function updateTag() {
    var  updateTag={};
    updateTag.id=updateTagId;
    updateTag.tagName=$("#updateTagName").val();
    updateTag.tagType=$("#updateTagType").val();
    $.ajax({
        // async : false,    //表示请求是否异步处理
        type : "post",    //请求类型
        url : "/tags/updateTag",//请求的 URL地址
        data: updateTag,
        dataType : "json",//返回的数据类型
        success: function (data) {
            console.log(data);  //在控制台打印服务器端返回的数据
            if (data.code == '200') {
                $.alert({
                    title: "",
                    content: data.message,
                    type: "green",
                    onClose: function () {
                        window.location.reload();
                    }
            })}
            if (data.code == '500') {
                $.alert({
                    title: "",
                    content: data.message,
                    type: "red",
                    onClose: function () {
                        window.location.reload();
                    }
               });
            }
        }
    });
})

   

function add() {
    window.location.href="math-exedit.html";
}


