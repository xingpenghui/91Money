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
				{ title: '日志ID', field: 'logId' },
				{ title: '任务ID', field: 'jobId'},
				{ title: 'bean名称', field: 'beanName'},
				{ title: '方法名称', field: 'methodName' },
				{ title: '参数', field: 'params' },
				{ title: '状态', field: 'status', formatter: function(value, row, index){
					return value === 0 ? 
						'<span class="label label-success">成功</span>' :
						'<span class="label label-danger pointer" onclick="vm.showError('+row.logId+')">失败</span>';
				}},
				{ title: '耗时(单位：毫秒)', field: 'times'},
				{ title: '执行时间', field: 'createTime'}
			];

			var option = T.btTableOption;
			var allColumns = option.columns.concat(columns);//合并列
			option.columns = allColumns; 
			option.url = 'list'; 
			
			$('#table').bootstrapTable(option);
		},
		
		deleteBatch: function(){
			T.deleteMoreItem('jobId');
		}
	}
});

vm.loadTable();