$(document).ready(function(){
    $('#v0').css('display', 'block');
})

function show(name) {
    let arr = ['v0', 'v1', 'v2', 'v3'];
    arr.forEach(item => {
        if (item === name) {
            $('#' + item).css('display', 'block');
        } else {
            $('#' + item).css('display', 'none');
        }
    });
}