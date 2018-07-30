/**
 * 用户管理
 */
var vm = new Vue({
    el:'#dtApp',
    data:{
        title: null,
        config: {}
    },
    methods: {
        init: function(){
        	
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
			var id = T.p('id');
			if(id == null){
				return;
			}
			$.get('info/'+id, function(r){
				vm.config = r.config;
            });
        },
        saveOrUpdate: function(){
        	
        	var url = vm.config.id == null ? 'save' : 'update';
        	
			$.ajax({
				type: 'POST',
			    url: url,
			    data: JSON.stringify(vm.config),
			    success: function(r){
			    	if(r.code === 0){
			    		layer.alert('操作成功', function(index){
			    			window.location.href = 'index';
						});
					}else{
						layer.alert(r.msg);
					}
				}
			});
        }
    }
});

vm.init();