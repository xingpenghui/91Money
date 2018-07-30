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
				{ title: '身份证号', field: 'idnumber' },
                {
                    title: '审核状态', field: 'flag', formatter: function (value, row, index) {
                    	var s;
						switch (value){
							case 0:s="未审核";break;
							case 1:s="审核通过";break;
							case 2:s="审核拒绝";break;
						}
						return s;
                    }
                },
				{ title:'操作', field:'id', formatter: function(value,row,index){
					var edit = '<a href="form?edit&id=' + value + '" class="btn btn-success btn-xs">编辑</a>';
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

vm.loadTable();