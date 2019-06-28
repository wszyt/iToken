var App = function () {

    //iCheck
    var _masterCheckbox;
    var _checkbox;

    //用于存放ID的数组
    var _idArray;

    //默认的Dropzone参数
    var defaultDropzoneOpts = {
        url: "",
        paramName: "dropFile", // 传到后台的参数名称
        maxFiles: 1,// 一次性上传的文件数量上限
        maxFilesize: 2, // 文件大小，单位：MB
        acceptedFiles: ".jpg,.gif,.png,.jpeg", // 上传的类型
        addRemoveLinks: true,
        parallelUploads: 1,// 一次上传的文件数量
        //previewsContainer:"#preview", // 上传图片的预览窗口
        dictDefaultMessage: '拖动文件至此或者点击上传',
        dictMaxFilesExceeded: "您最多只能上传" + this.maxFiles + "个文件！",
        dictResponseError: '文件上传失败!',
        dictInvalidFileType: "文件类型只能是*.jpg,*.gif,*.png,*.jpeg。",
        dictFallbackMessage: "浏览器不受支持",
        dictFileTooBig: "文件过大上传文件最大支持.",
        dictRemoveLinks: "删除",
        dictCancelUpload: "取消",
    }

    /**
     * 私有方法，初始化iCHeck
     */
    var handlerInitCheckBox = function () {
        // 激活 iCheck
        $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
            checkboxClass: 'icheckbox_minimal-blue',
            radioClass   : 'iradio_minimal-blue'
        });

        //获取控制端 checkbox
        _masterCheckbox = $('input[type="checkbox"].minimal.icheck_master');

        //获取全部checkbox集合
        _checkbox = $('input[type="checkbox"].minimal');
    };

    /**
     * Checkbox 全选功能
     */
    var handlerCheckboxAll = function () {
        _masterCheckbox.on("ifClicked", function (e) {
            //返回true表示未选中
            if(e.target.checked) {
                _checkbox.iCheck("uncheck")
            }

            //选中状态
            else {
                _checkbox.iCheck("check")
            }
        });
    }

    var handlerDeleteSingle = function (url, id, msg) {
        //可选参数
        if (!msg) msg = null;

        //将ID放入数组中，以便和批量删除通用
        _idArray = new Array();
        _idArray.push(id);

        $("#modal-message").html(msg == null ? "是否确认删除所选用户" : msg);
        $("#modal-default").modal("show");

        $("#btnModalOK").bind("click", function () {
            handlerDeleteData(url);
        });
    };



    /**
     *批量删除
     */
    var handlerDeleteMulti = function (url) {
        _idArray = new Array();

        _checkbox.each(function () {
            var _id = $(this).attr("id");
            if (_id != null && _id != "undefine" && $(this).is(":checked")) {
                _idArray.push(_id);
            }
        });

        //点击删除按钮时弹出模态框

        $("#modal-default").modal("show");


        //判断用户是否选择数据项
        if (_idArray.length === 0) {
            $("#modal-message").html("您还没有选择任何一项数据项，请至少选择一项")
        }

        else {
            $("#modal-message").html("是否确认删除所选用户")
        }

        //如果选择了数据项则调用删除方法
        $("#btnModalOK").bind("click", function () {
            handlerDeleteData(url);
        });



        // /**
        //  * 当前私有函数的私有函数,删除数据
        //  */
        // function del() {
        //     $("#modal-default").modal("hide");
        //
        //
        //     //如果没有选择数据项的处理
        //     if (_idArray.length === 0 ) {
        //
        //     }
        //
        //     //删除操作
        //     else {
        //
        //         setTimeout(function () {
        //             $.ajax({
        //                 "url":url,
        //                 "type":"POST",
        //                 "data":{"ids" : _idArray.toString()},
        //                 "dataType":"JSON",
        //                 "success": function (data) {
        //                     //请求成功后无论是成功或者失败都需要弹出模态框进行提示，所以这里需要解绑原来的click时间
        //                     $("#btnModalOK").unbind();
        //                     //删除成功
        //                     if (data.status === 200) {
        //                         $("#btnModalOK").bind("click", function () {
        //                             //刷新页面
        //                             window.location.reload();
        //                         });
        //                     }
        //
        //                     //删除失败
        //                     else {
        //                         //确定按钮的时间改为隐藏模态框
        //                         $("#btnModalOK").bind("click", function () {
        //                             $("#modal-default").modal("hide");
        //                         });
        //                     }
        //
        //                     //因为无论如何都需要提示信息，所以这里的模态框是必须要调用的
        //                     $("#modal-message").html(data.message)
        //                     $("#modal-default").modal("show");
        //                 }
        //             });
        //         }, 300);
        //
        //     }
        // }
    }

    /**
     * AJAX异步删除数据
     */
    var handlerDeleteData = function (url) {
        $("#modal-default").modal("hide");


        //如果没有选择数据项的处理
        if (_idArray.length === 0 ) {

        }

        //删除操作
        else {

            setTimeout(function () {
                $.ajax({
                    "url":url,
                    "type":"POST",
                    "data":{"ids" : _idArray.toString()},
                    "dataType":"JSON",
                    "success": function (data) {
                        //请求成功后无论是成功或者失败都需要弹出模态框进行提示，所以这里需要解绑原来的click时间
                        $("#btnModalOK").unbind();
                        //删除成功
                        if (data.status === 200) {
                            $("#btnModalOK").bind("click", function () {
                                //刷新页面
                                window.location.reload();
                            });
                        }

                        //删除失败
                        else {
                            //确定按钮的时间改为隐藏模态框
                            $("#btnModalOK").bind("click", function () {
                                $("#modal-default").modal("hide");
                            });
                        }

                        //因为无论如何都需要提示信息，所以这里的模态框是必须要调用的
                        $("#modal-message").html(data.message)
                        $("#modal-default").modal("show");
                    }
                });
            }, 300);

        }
    }


    /**
     * 初始化DataTables
     */
    var handlerInitDataTables = function (url, columns) {
        var _dataTable = $("#dataTable").DataTable({
            "paging":true,
            "info":true,
            "lengthChange":false,
            "ordering":false,
            "processing":true,
            "searching":false,
            "serverSide":true,
            "deferRender":true,
            "ajax": {
                "url": url
            },
            "columns" : columns,
            "language": {
                "sProcessing": "处理中...",
                "sLengthMenu": "显示 _MENU_ 项结果",
                "sZeroRecords": "没有匹配结果",
                "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
                "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
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
                    "sLast": "末页"
                },
                "oAria": {
                    "sSortAscending": ": 以升序排列此列",
                    "sSortDescending": ": 以降序排列此列"
                }
            },

            "drawCallback" : function () {
                handlerInitCheckBox();
                handlerCheckboxAll();
            }
        });

        return _dataTable;
    };
    
    //初始化zTree
    var handlerInitZTree = function (url, autoParam, callback) {

        var setting = {
            view: {
                selectedMulti: false
            },
            async: {
                enable: true,
                url: url,
                autoParam: autoParam,
            }
        };

        $.fn.zTree.init($("#myTree"), setting);

        $("#btnModalOK").bind("click", function () {
            var zTree = $.fn.zTree.getZTreeObj("myTree");
            var nodes = zTree.getSelectedNodes();

                //未选择
                if(nodes.length == 0) {
                    alert("请选择一个节点")
                }

                //已选择
                else{
                    callback(nodes);
                }
            })
    }

    // $("#btnModalOK").bind("click", function () {
    //     var zTree = $.fn.zTree.getZTreeObj("myTree");
    //     var nodes = zTree.getSelectedNodes();
    //
    //     //未选择
    //     if(nodes.length == 0) {
    //         alert("请选择一个节点")
    //     }
    //
    //     //已选择
    //     else{
    //         var node = nodes[0];
    //         $("#categoryId").val(node.id);
    //         $("#categoryName").val(node.name);
    //         $("#modal-default").modal("hide");
    //     }
    // })

    /**
     * 初始化 Dropzone
     * @param opts
     */
    var handlerInitDropzone = function (opts) {
        //关闭Dropzone的自动发现功能
        Dropzone.autoDiscover = false;
        //继承
        $.extend(defaultDropzoneOpts, opts);

        new Dropzone(defaultDropzoneOpts.id, defaultDropzoneOpts);
        // {
        //
        //     init: function () {
        //         this.on("success", function (file, data) {
        //             defaultDropzoneOpts.success(file, data);
        //         });
        //     }
        // });
    };


    /**
     * 查看详细信息
     * @param url
     */
    var handlerShowDetail = function (url) {
        //通过ajax请求html的方式将jsp装入到模态框中
        $.ajax({
            url: url,
            type: "get",
            dataType : "html",
            success : function (data) {
                $("#modal-detail-body").html(data);

                $("#modal-detail").modal("show");
            }
        })
    }

    return {
        //初始化
        init:function() {


            handlerInitCheckBox();
            handlerCheckboxAll();
        },


        //删除单笔数据
        deleteSingle: function (url, id, msg) {
            handlerDeleteSingle(url, id, msg);
        },

        //批量删除
        deleteMulti: function (url) {
            handlerDeleteMulti(url)
        },

        //初始化dataTables
        initDataTables : function (url, columns) {
            return handlerInitDataTables(url, columns)
        },

        //初始化initZTree
        initZTree: function(url, autoParam, callback) {
            handlerInitZTree(url, autoParam, callback)
        },

        //初始化Dropzone
        initDropzone: function(opts) {
            handlerInitDropzone(opts)
        },

        //显示详情
        showDetail : function (url) {
            handlerShowDetail(url)
        }
    }


}();


$(document).ready(function () {
    App.init();
});