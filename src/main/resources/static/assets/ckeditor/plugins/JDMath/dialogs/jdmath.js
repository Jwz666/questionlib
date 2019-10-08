CKEDITOR.dialog.add("jdmath", function (a) {
    var jme_fid = "jdmath_" + Math.ceil(Math.random()*10) ;
    return {
        title: "数学公式编辑器",
        minWidth: "500px",
        minHeight: "500px",
        contents: [{
            id: "tab1",
            label: "",
            title: "",
            expand: true,
            width: "500px",
            height: "500px",
            padding: 0,
            elements: [{
                type: "html",
                html: '<div style="width:400px;height:300px;"><iframe id="' + jme_fid + '" style="width:400px;height:300px;" frameborder="no" src="' + CKEDITOR.basePath + 'plugins/jdmath/dialogs/jdmath.html"></iframe></div>'
            }]
        }],
        onOk: function () {
            //点击确定按钮后的操作
            var mathHTML = '<span class="mq-math-mode" latex-data="'+getval(jme_fid,'latex')+'">'+gethtml(jme_fid,'jme-math')+ '</span><span>&nbsp;</span>';
            a.insertHtml(mathHTML);
            return true;
        }
    }
});

function getval(fid, tid) {
    var ret = document.getElementById(fid).contentWindow.document.getElementsByName(tid);
    return ret[0].value;
}
function gethtml(fid,tid) {
    var ret = document.getElementById(fid).contentWindow.document.getElementById(tid).innerHTML;
    return ret;
}

