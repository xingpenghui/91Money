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
				{ title: '任务ID', field: 'jobId' },
				{ title: 'bean名称', field: 'beanName' },
				{ title: '方法名称', field: 'methodName' },
				{ title: '参数', field: 'params' },
				{ title: 'cron表达式 ', field: 'cronExpression' },
				{ title: '备注 ', field: 'remark' },
				{ title: '状态', field: 'status', formatter: function(value,row,index){
					return value === 0 ? 
						'<span class="label label-success">正常</span>' : 
						'<span class="label label-danger">暂停</span>';
				}},
				{ title: '创建时间', field: 'createTime'},
				{ title:'操作', field:'jobId', formatter: function(value,row,index){
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
			T.deleteMoreItem('jobId');
		},
		
		pauseBatch: function(){
			T.doTask('jobId', 'pause');
		},
		
		resumeBatch: function(){
			T.doTask('jobId', 'resume');
		},
		
		runBatch: function(){
			T.doTask('jobId', 'run');
		}
	}
});

vm.loadTable();