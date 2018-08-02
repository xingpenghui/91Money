/**
 * 认证管理
 */
var vm = new Vue({
	el:'#dtApp',
	data:{
	},
	methods: {
		loadTable: function(){
			var columns = [
				{ title: 'ID', field: 'uid' },
				{ title: '真实名称', field: 'realname' },
				{ title: '性别', field: 'sex' },
				{ title: '身份证号', field: 'idNumber' },

                {
                    title: '正面', field: 'idimage1', formatter: function (value, row, index) {
                        return "<img src='"+value+"' width='100px' height='100px' alt='暂无身份证照片'/>";
                    }
                },
                {
                    title: '反面', field: 'idimage2', formatter: function (value, row, index) {
                        return "<img src='"+value+"' width='100px' height='100px' alt='暂无身份证照片'/>";
                    }
                },
                {
                    title: '审核状态', field: 'flag', formatter: function (value, row, index) {
                        var s;
                        switch (value){
                            case 0:s="未实名";break;
                            case 1:s="待审核";break;
                            case 2:s="审核通过";break;
                            case 3:s="审核拒绝";break;
                        }
                        return s;
                    }
                },
				{ title:'操作', field:'id', formatter: function(value,row,index){
					var edit ="<a href='javascript:sh(2,"+value+")' class='btn btn-success btn-xs'>通过</a><a href='javascript:sh(3,"+value+")' class='btn btn-success btn-xs'>拒绝</a>";
					return edit;
					}
				}
			];
			var option = T.btTableOption;
			var allColumns = option.columns.concat(columns);//合并列
			option.columns = allColumns; 
			option.url = 'list';
			$('#table').bootstrapTable(option);
		},
		deleteBatch: function(){
			T.deleteMoreItem('id');
		}
	}
});

function sh(f,id){
	$.get("/sys/auth/update.do","flag="+f+"&id="+id,function (obj) {
		layer.msg(obj.msg);
    })
}
vm.loadTable();