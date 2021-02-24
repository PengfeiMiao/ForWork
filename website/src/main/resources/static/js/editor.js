$(document).ready(function () {
    console.log($.query.get('id'));
    convertToMarkDown();
})

var editor1;
function convertToMarkDown() {
    editor1 = editormd("test-editor1", {
        width: "100%",              //宽度，不填为100%
        height: "600px",            //高度，不填为100%
        theme: "white",             //主题,不填为默认主题
        path: "editormd/lib/",      //editor.md插件的lib目录地址
        saveHTMLToTextarea: true,   // 保存 HTML 到 Textarea
        toolbarAutoFixed: true,     //工具栏自动固定定位的开启与禁用
        imageUpload: true,          //运行本地上传
        imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp"],    //允许上传的文件格式
        imageUploadURL: baseUrl + "/test/saveImage",                   //上传的后台服务器路径
        onload: function () {
            this.setMarkdown("#Hello World!");
            // 引入插件 执行监听方法
            editormd.loadPlugin("editormd/plugins/image-handle-paste/image-handle-paste", function () {
                editor1.imagePaste();
            });
        }
    });
}

function save() {
    // window.location.reload();
    localStorage.setItem("temp", '#HTML');
    console.log($("#title1").val());
    console.log(editor1.getMarkdown());
}