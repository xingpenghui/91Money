/**
 * 菜单管理-列表
 */
var vm = new Vue({
	el: '#dtApp',
	data:{
	},
	methods:{
		loadTable: function(){
			var columns = [
				{field:'menuId', title:'菜单ID'},
		        {field:'name', title:'菜单名称', formatter: function(value,row){
					if(row.type === 0){
						return value;
					}
					if(row.type === 1){
						return '<span class="m-l-md">├─ ' + value + '</span>';
					}
					if(row.type === 2){
						return '<span class="m-l-xl">├─ ' + value + '</span>';
					}
				}}, 
		        {field:'parentMenu.name', title:'上级菜单'},
		        {field:'icon', title:'菜单图标', formatter: function(value,row,index){
		        	return '<i class="' + value + ' fa-lg"></i>';
		        }},
		        {field:'url', title:'菜单URL'},
		        {field:'perms', title:'授权标识'},
		        {field:'type', title:'类型', formatter: function(value){
		        	switch (value) {
					case 0:
						return '<span class="label label-primary">目录</span>';
						break;
					case 1:
						return '<span class="label label-success">菜单</span>';
						break;
					case 2:
						return '<span class="label label-warning">按钮</span>';
						break;
					}
		        }},
		        {field:'orderNum', title:'排序号'},
				{field:'menuId',title:'操作', formatter: function(value,row,index){
						var edit = '<a href="form?edit&id=' + value + '" class="btn btn-info btn-xs">编辑</a>';
						return edit;
					}
				}
			];
			var option = T.btTableOption;
			var allColumns = option.columns.concat(columns);
			option.columns = allColumns;
			option.url='list';
			option.pageSize = 100;
			$('#table').bootstrapTable(option);
		},
		deleteBatch: function(){
			T.deleteMoreItem('menuId');
		}
	}
});


$(function(){
	vm.loadTable();
});

