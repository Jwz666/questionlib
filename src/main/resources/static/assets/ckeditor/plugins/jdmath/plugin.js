CKEDITOR.plugins.add("jdmath", {
    icons: 'jdmath',
        requires: ["dialog"],
        init: function(a) {
            a.addCommand("jdmath", new CKEDITOR.dialogCommand("jdmath"));
            a.ui.addButton("jdmath", {
                label: "数学公式",//调用dialog时显示的名称
                command: "jdmath",
                icon: this.path + "/icons/jdmath.png"//在toolbar中的图标

            });
            CKEDITOR.dialog.add("jdmath", this.path + "dialogs/jdmath.js")

        }

});