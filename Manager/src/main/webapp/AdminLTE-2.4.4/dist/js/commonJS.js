<!-- common JS -->
//链接IP
var IP = "http://192.168.10.160:8080";
// var IP = "http://192.168.10.151";
//中文国际化
var language = {
    "sProcessing": "处理中...",
    "sLengthMenu": "每页 _MENU_ 项",
    "sZeroRecords": "没有匹配结果",
    "sInfo": "当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项。",
    "sInfoEmpty": "当前显示第 0 至 0 项，共 0 项",
    "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
    "sInfoPostFix": "",
    "sSearch": "搜索:",
    "sUrl": "",
    "sEmptyTable": "表中数据为空",
    "sLoadingRecords": "载入中...",
    "sInfoThousands": ",",
    "oPaginate": {
        "sFirst": "首页",
        "sPrevious": "上页",
        "sNext": "下页",
        "sLast": "末页",
        "sJump": "跳转"
    },
    "oAria": {
        "sSortAscending": ": 以升序排列此列",
        "sSortDescending": ": 以降序排列此列"
    }
};

/**
 *
 * 得到初始表头
 * @param tableName 表名（数据库）
 * @param tableChineseName  中文表名
 * @returns {*|jQuery|boolean|*|*|*}
 */
function getTableTh(tableName, tableChineseName) {
    return $.ajax({
        type: "GET",
        url: IP + "/system/table/queryNowData?tableName=" + tableName + "&pageSize=1&start=1&pageIndex=1",
        dataType: "json",
        success: function (result) {
            var columns = "[";
            var th = result.data.table.th[0];
            for (var i in th) {
                columns += "{\"data\":\"" + i + "\",\"title\":\"" + th[i] + "\",\"defaultContent\": \"\"},";
            }
            columns = JSON.parse(columns.substr(0, columns.length - 1) + "]");
            createTable(columns, tableName, tableChineseName);
        }
    });
}

/**
 *
 * 得到初始表头（带查询和操作）
 * @param tableName 表名（数据库）
 * @param tableChineseName  中文表名
 * @param search  查询参数
 * @returns {*|jQuery|boolean|*|*|*}
 */
function getTableThWithSearch(tableName, tableChineseName, search) {
    return $.ajax({
        type: "GET",
        url: IP + "/system/table/queryNowData?tableName=" + tableName + "&pageSize=1&start=1&pageIndex=1",
        dataType: "json",
        success: function (result) {
            var columns = "[";
            var th = result.data.table.th[0];
            for (var i in th) {
                columns += "{\"data\":\"" + i + "\",\"title\":\"" + th[i] + "\",\"defaultContent\": \"\"},";
            }
            columns = JSON.parse(columns.substr(0, columns.length - 1) + "]");
            createTableWithSearch(columns, tableName, tableChineseName, search);
        }
    });
}

/**
 * 生成Table
 * @param columns   初始表头
 * @param tableName 表名（数据库）
 * @param tableChineseName  中文表名
 * @returns {jQuery|*}
 */
function createTable(columns, tableName, tableChineseName) {
    return table = $('#data-table').DataTable({
        dom: '<"box-header"B><lf"box-body"rtip>',
        autoWidth: false,  //禁用自动调整列宽
        language: language,//国际化配置
        stripeClasses: ["odd", "even"],  //为奇偶行加上样式，兼容不支持CSS伪类的场合
        scrollY: 600,   //是否支持垂直滚动
        scrollCollapse: true,
        searching: false,  // 是否开启搜索功能
        ordering: false,  // 是否开启排序功能
        processing: true,  //隐藏加载提示,自行处理
        serverSide: true,  //启用服务器端分页
        orderMulti: true,  //启用多列排序
        order: [],  //取消默认排序查询,否则复选框一列会出现小箭头
        renderer: "bootstrap",  //渲染样式：Bootstrap和jquery-ui
        pagingType: "full_numbers",  //分页样式：simple,simple_numbers,full,full_numbers
        columnDefs: [{
            "targets": 'nosort',  //列的样式名
            "orderable": false    //包含上样式名‘nosort’的禁止排序
        }],
        buttons: [
            {
                extend: 'copy',
                text: '复制',
                className: 'btn btn-primary'
            },
            {
                extend: 'excel',
                text: '导出<u>EXCEL</u>',
                className: 'btn btn-primary',
                title: tableChineseName + getNowFormatDate()
            },
            {
                extend: 'csv',
                text: '导出<u>CSV</u>',
                className: 'btn btn-primary',
                title: tableChineseName + getNowFormatDate()
            }, {
                extend: 'print',
                text: '打印',
                className: 'btn btn-primary',
                title: tableChineseName
            }
        ],

        ajax: function (data, callback, settings) {
            //封装请求参数
            var param = {};
            param.tableName = tableName;//表名
            param.pageSize = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
            param.start = data.start;//开始的记录序号
            param.draw = data.draw;//开始的记录序号
            param.pageIndex = (data.start / data.length) + 1;//当前页码
            //ajax请求数据
            $.ajax({
                type: "POST",
                url: IP + "/system/table/queryNowData",
                cache: false,  //禁用缓存
                data: param,  //传入组装的参数
                dataType: "json",
                success: function (result) {
                    //封装返回数据
                    var returnData = {};
                    returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                    returnData.recordsTotal = result.data.page.totalSize;//返回数据全部记录
                    returnData.recordsFiltered = result.data.page.totalSize;//后台g不实现过滤功能，每次查询均视作全部结果
                    returnData.data = result.data.table.content;//返回的数据列表
                    //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                    //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                    callback(returnData);
                }
            });
        },
        //因为需要多次初始化，所以需要设置允许销毁实例
        destroy: true,
        //列的配置信息通过变量传进来
        columns: columns,
    });
}

/**
 * 生成Table（带查询和操作）
 * @param columns   初始表头
 * @param tableName 表名（数据库）
 * @param tableChineseName  中文表名
 * @param search  查询参数
 * @returns {jQuery|*}
 */
function createTableWithSearch(columns, tableName, tableChineseName, search) {
    return table = $('#data-table').DataTable({
        dom: '<"box-header"><lf"box-body"rtip>',
        autoWidth: false,  //禁用自动调整列宽
        language: language,//国际化配置
        stripeClasses: ["odd", "even"],  //为奇偶行加上样式，兼容不支持CSS伪类的场合
        scrollY: 600,   //是否支持垂直滚动
        scrollCollapse: true,
        searching: false,  // 是否开启搜索功能
        ordering: false,  // 是否开启排序功能
        processing: true,  //隐藏加载提示,自行处理
        serverSide: true,  //启用服务器端分页
        orderMulti: true,  //启用多列排序
        order: [],  //取消默认排序查询,否则复选框一列会出现小箭头
        renderer: "bootstrap",  //渲染样式：Bootstrap和jquery-ui
        pagingType: "full_numbers",  //分页样式：simple,simple_numbers,full,full_numbers
        columnDefs: [
            {
                "targets": 'nosort',  //列的样式名
                "orderable": false    //包含上样式名‘nosort’的禁止排序
                //倒数第一列
            },
            {
                "targets": 0,
                "bSortable": false,
                render: function (data, type, row) {
                    var html = '<button class="btn btn-xs btn-warning" value="' + row[0] + '" onclick="tickUser(this.value)">踢人</button>    '
                        + '<button class="btn btn-xs btn-danger" value="' + row[0] + '" onclick="banUser(this.value)">封号</button>    '
                        + '<button class="btn btn-xs btn-info" value="' + row[0] + '" onclick="sendUser(this.value)">发送邮件</button>';
                    return html;
                }
            }
        ],

        ajax: function (data, callback, settings) {
            //封装请求参数
            var param = {};
            param.tableName = tableName;//表名
            param.pageSize = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
            param.start = data.start;//开始的记录序号
            param.draw = data.draw;//开始的记录序号
            param.pageIndex = (data.start / data.length) + 1;//当前页码
            param.search = search;//查询参数
            //ajax请求数据
            $.ajax({
                type: "POST",
                url: IP + "/system/table/queryNowData",
                cache: false,  //禁用缓存
                data: param,  //传入组装的参数
                dataType: "json",
                success: function (result) {
                    //封装返回数据
                    var returnData = {};
                    returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                    returnData.recordsTotal = result.data.page.totalSize;//返回数据全部记录
                    returnData.recordsFiltered = result.data.page.totalSize;//后台g不实现过滤功能，每次查询均视作全部结果
                    returnData.data = result.data.table.content;//返回的数据列表
                    //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                    //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                    callback(returnData);
                }
            });
        },
        //因为需要多次初始化，所以需要设置允许销毁实例
        destroy: true,
        //列的配置信息通过变量传进来
        columns: columns,
    });
}

/**
 * 得到当前的日期
 * @returns {string}
 */
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = "\=\:";
    var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
    var strDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
    var currentDate = date.getFullYear() + seperator1 + month + seperator1 + strDate
        + " " + date.getHours() + seperator2 + date.getMinutes()
        + seperator2 + date.getSeconds();
    return currentDate;
}