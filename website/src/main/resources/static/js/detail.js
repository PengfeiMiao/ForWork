var articleId;
$(document).ready(function () {
    articleId = $.query.get('id');
    console.log('articleId', articleId);
    layui.use('layer', function() {
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
                $("#title2").val(res.data.data.title);
                convertToHtml(res.data.data.content); // res.data.data.content
            }
        });

        $('#edit2').on('click', () => {
            console.log('edit2');
            window.location.href = 'editor.html?id=' + articleId;
        })
    })
})
var editor2;

function convertToHtml(content) {
    editor2 = editormd.markdownToHTML("test-editor2", {
        markdown: content,
        htmlDecode: "style,script,iframe",
        emoji: true,
        taskList: true,
        tex: true, // 默认不解析
        flowChart: true, // 默认不解析
        sequenceDiagram: true, // 默认不解析
        codeFold: true,
    });
}