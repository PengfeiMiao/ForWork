layui.use('layer', function () {
    var articleId = '';
    $(document).ready(function () {
        articleId = $.query.get('id');
        console.log('articleId', articleId);
        // layui.use('layer', function () {
        layer.load({
            shade: [0.1, '#fff'] //0.1透明度的白色背景
        });
        axios.get(baseUrl + '/article/detail', {
            params: {
                'articleId': articleId
            }
        }).then(res => {
            console.log(res);
            layer.closeAll('loading');
            if (res.data.status === 200) {
                $("#title1").val(res.data.data.title);
                convertToMarkDown(res.data.data.content); // res.data.data.content
            }
        })

        $('#save').on('click', () => {
            layer.msg('是否保存博客', {
                time: 0
                // , icon: 6 // 微笑icon
                , btn: ['确认', '取消']
                , yes: function (index) {
                    layer.close(index);
                    layer.load({
                        shade: [0.1, '#fff'] //0.1透明度的白色背景
                    });
                    save(1);
                }
            });
        })

        $('#draft').on('click', () => {
            layer.msg('是否保存为草稿', {
                time: 0
                // , icon: 6 // 微笑icon
                , btn: ['确认', '取消']
                , yes: function (index) {
                    layer.close(index);
                    layer.load({
                        shade: [0.1, '#fff'] //0.1透明度的白色背景
                    });
                    save(2);
                }
            });
        })

        // })
    })

    var editor1;

    function convertToMarkDown(content) {
        editor1 = editormd("test-editor1", {
            width: "100%",              //宽度，不填为100%
            height: "600px",            //高度，不填为100%
            theme: "white",             //主题,不填为默认主题
            path: "editormd/lib/",      //editor.md插件的lib目录地址
            saveHTMLToTextarea: true,   // 保存 HTML 到 Textarea
            toolbarAutoFixed: true,     //工具栏自动固定定位的开启与禁用
            searchReplace: true,
            //watch: false,             // 关闭实时预览
            htmlDecode: "style,script,iframe|on*",      // 开启 HTML 标签解析，为了安全性，默认不开启
            //toolbar: false,                           //关闭工具栏
            //previewCodeHighlight : false,             // 关闭预览 HTML 的代码块高亮，默认开启
            emoji: true,
            taskList: true,
            tocm: true,                 // Using [TOCM]
            tex: true,                  // 开启科学公式TeX语言支持，默认关闭
            flowChart: true,            // 开启流程图支持，默认关闭
            sequenceDiagram: true,     // 开启时序/序列图支持，默认关闭,
            imageUpload: true,          //运行本地上传
            imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp"],    //允许上传的文件格式
            imageUploadURL: baseUrl + "/test/saveImage",                   //上传的后台服务器路径
            onload: function () {
                this.setMarkdown(content);
                // 引入插件 执行监听方法
                editormd.loadPlugin("editormd/plugins/image-handle-paste/image-handle-paste", function () {
                    editor1.imagePaste();
                });
            }
        });
    }

    function save(status) {
        let title = $("#title1").val();
        let content = editor1.getMarkdown();
        axios.post(baseUrl + '/article/save', {
            'id': articleId,
            'title': title,
            'content': content,
            'status': status
        }).then(res => {
            console.log(res);
            layer.closeAll('loading');
            if (res.data.status === 200) {
                // 弹窗事件
                layer.msg('保存成功', {
                    time: 500
                });
                setTimeout(() => {
                    window.location.href = 'detail.html?id=' + articleId;
                }, 200);
            }
        })
    }

})