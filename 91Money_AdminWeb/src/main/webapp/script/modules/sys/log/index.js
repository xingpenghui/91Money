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
				{ title: 'id', field: 'id' },
				{ title: '用户名', field: 'username' }, 			
				{ title: '用户操作', field: 'operation' }, 			
				{ title: '请求方法', field: 'method' }, 			
				{ title: '请求参数', field: 'params' }, 			
				{ title: 'IP地址', field: 'ip' }, 			
				{ title: '创建时间', field: 'createDate'}	
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