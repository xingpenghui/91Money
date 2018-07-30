/**
 * 用户管理
 */
var vm = new Vue({
    el:'#dtApp',
    data:{
        title: null,
        job: {}
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
			var jobId = T.p('id');
			if(jobId == null){
				return;
			}
			$.get('info/'+jobId, function(r){
				vm.job = r.job;
            });
        },
        saveOrUpdate: function(){
        	
        	var url = vm.job.jobId == null ? 'save' : 'update';
        	
			$.ajax({
				type: 'POST',
			    url: url,
			    data: JSON.stringify(vm.job),
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