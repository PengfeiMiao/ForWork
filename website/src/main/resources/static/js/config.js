const baseUrl = "http://49.235.229.66/website";
// const baseUrl = "http://localhost:8773/website";

const textSize = function (fontSize, text) {
    //fontSize:代表汉字的大小，英文字会自动按照默认值
    var span = document.createElement("span");
    var result = {};
    result.width = span.offsetWidth;
    result.height = span.offsetHeight;
    span.style.visibility = "hidden";
    span.style.fontSize = fontSize;
    document.body.appendChild(span);
    if (typeof span.textContent != "undefined")
        span.textContent = text;
    else span.innerText = text;
    result.width = span.offsetWidth - result.width;
    result.height = span.offsetHeight - result.height;
    span.parentNode.removeChild(span);
    return result;
}