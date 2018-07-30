/**
 * 用户管理
 */
var vm = new Vue({
	el:'#dtApp',
	data:{
		
	},
	methods: {
		loadTable: function(){
			
			var columns = [
				{ title: '用户ID', field: 'userId'},
				{ title: '用户名', field: 'username'},
				{ title: '邮箱', field: 'email'},
				{ title: '手机号', field: 'mobile'},
				{ title: '状态', field: 'status', formatter: function(value, row, index){
					return value === 0 ? 
						'<span class="label label-danger">禁用</span>' : 
						'<span class="label label-success">正常</span>';
				}},
				{ title: '创建时间', field: 'createTime', index: "create_time"},
				{ title:'操作', field:'userId', formatter: function(value,row,index){
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
			T.deleteMoreItem('userId');
		}
	}
});

vm.loadTable();