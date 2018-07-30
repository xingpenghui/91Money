/**
 * 
 */

var vm = new Vue({
	
	el: '#dtApp',
	
	data: {
		title: null,
		menu: {
			type: 1,
			parentMenu: {
				menuId: null,
				name: null
			}
		},
		
		ztree: null,
		setting: {
			data: {
				simpleData: {
					enable: true,//启用简单json
					idKey: 'menuId',//主键oid
					pIdKey: 'parentId',//关联的父记录的id
					rootPId: null//修正根节点的父节点的数据
				},
				key:{
					url: 'nourl'//数据库中有url属性，但是不想跳转
				}
			}
		},
		
		
	},
	
	methods: {
		
		init: function(){
			
			vm.getMenu();
			
			if(T.hasP('add')){
				vm.add();
			}
			if(T.hasP('edit')){
				vm.edit();
			}
		},
		
		add: function(){
			vm.title = '新增';
		},
		
		edit: function(){
			vm.title = '修改';
			
			var menuId = T.p('id');
			if(!menuId){
				return;
			}
			$.get('info/' + menuId, function(r){
				
				if(r.menu.parentMenu == null){
					r.menu.parentMenu = {
						menuId: null,
						name: null
					}
				}
				
				vm.menu = r.menu;
			});
		},
		
		saveOrUpdate: function(){
			
			var url = vm.menu.menuId == null ? 'save' : 'update';
		
			$.ajax({
				type: 'post',
				url: url,
				data: JSON.stringify(vm.menu),
				success: function(r){
					if(r.code == 0){
						layer.alert('操作成功', function(index){
							window.location.href = 'index';
						});
					}else{
						layer.alert(r.msg);
					}
				}
			});
		},
		
		menuTree: function(){
			
			//自定页
			layer.open({
			  type: 1,
			  skin: 'layui-layer-demo', //样式类名
			  closeBtn: 0, //不显示关闭按钮
			  area: ['300px', '450px'],
			  anim: 2,
			  shadeClose: false, //开启遮罩关闭
			  content: $('#menuLayer'),
			  btn: ['确定', '取消'],
			  btn1: function(index){
				  
				  console.log('确定');
				  //获取ztree中被选中的节点
				  var nodes = vm.ztree.getSelectedNodes();
				  vm.menu.parentMenu = {
						  menuId: nodes[0].menuId,
						  name: nodes[0].name
				  };
				  layer.close(index);
			  }
			});
		},
		
		getMenu: function(){
			console.log('getMenu')
			$.get('select', function(r){
				if(r.menuList.length > 0){
					FastJson.format(r);
				}
				//初始化ztree
				vm.ztree = $.fn.zTree.init($('#menuTree'), vm.setting, r.menuList);
			});
		}
	}
	
});

$(function(){
	vm.init();
});