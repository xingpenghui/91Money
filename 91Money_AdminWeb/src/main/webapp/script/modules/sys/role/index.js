/**
 * 菜单管理
 */
var vm = new Vue({
	el:'#dtApp',
	data:{
		
	},
	methods: {
		loadTable: function(){
			var columns = [
				{ title: '角色ID', field: 'roleId'},
				{ title: '角色名称', field: 'roleName'},
				{ title: '备注', field: 'remark'},
				{ title: '创建时间', field: 'createTime'},
				{ title:'操作', field:'roleId', formatter: function(value,row,index){
						var edit = '<a href="form?edit&id=' + value + '" class="btn btn-success btn-xs">编辑</a>';
						return edit;
					}
				}
			];

			var option = T.btTableOption;
			var allColumns = option.columns.concat(columns);
			option.columns = allColumns;
			option.url='list';
			$('#table').bootstrapTable(option);
		},
		
		deleteBatch: function(){
			T.deleteMoreItem('roleId');
		}
	}
});

vm.loadTable();