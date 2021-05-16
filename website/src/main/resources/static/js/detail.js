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
                let content = res.data.data.content;
                // let result = textSize(16, content).height;
                // result += mdHeight(16, content)
                // console.log('flag', result);
                // $(this).height(result);
                convertToHtml(content);
            }
        });

        $('#edit2').on('click', () => {
            console.log('edit2');
            window.location.href = 'editor.html?id=' + articleId;
        });
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

function mdHeight(fontSize, text) {
    //fontSize:代表汉字的大小，英文字会自动按照默认值
    // let result = textSize(fontSize, text);
    // let H = result.height;
    // 开始统计图片高度
    let imgStart = text.split('#pic_center =x');
    let arr = [];
    let imgH = 0;
    imgStart.forEach(it => {
        if(text.indexOf(")") > -1) {
            let str = it.split(")")[0].replace(/(^\s*)/g, "");
            let index = 0;
            for(let i = 0; i < str.length; i++) {
                let num = Number(str[i]);
                if(num >=0 && num <=9){
                    index++;
                }else{
                    break;
                }
            }
            if(index > 0) {
                let size = Number(str.substr(0, index));
                arr.push(size);
                imgH += size;
            }

        }
    })
    console.log(imgH, arr);
    return imgH;
}