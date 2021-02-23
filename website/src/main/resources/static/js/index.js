$(document).ready(function () {
})


function test() {

    let columns = [], column = {}, param = {};
    column["cond"] = 0;
    column["name"] = "custom_code";
    column["value"] = "18629330003";
    columns.push(column);
    param["columns"] = columns;
    param["limit"] = 10;
    param["offset"] = 0;

    $.ajax({
        url: "http://49.235.229.66/server/v1/console/base/data/red_black_list_info/list",
        type: "post",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(param),
        timeout: 6000,
        success: function (res) {
            console.log(res);
        },
        error: function (res) {
            console.log("error: ", res);
        }
    })
}