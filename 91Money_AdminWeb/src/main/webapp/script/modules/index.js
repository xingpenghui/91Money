Vue.component("menuItem", {
	props: ['item'],
    template: [
    	'<li>',
	        '<a href="#">',
	            '<i :class="item.icon"></i>',
	            '<span class="nav-label">{{item.name}}</span>',
	            '<span class="fa arrow"></span>',
	        '</a>',
	        '<ul class="nav nav-second-level">',
	        	'<li  v-for="child in item.children">',
	                '<a class="J_menuItem" :href="child.url">{{child.name}}</a>',
	            '</li>',
	        '</ul>',
	    '</li>',
	    ].join(''),
});

var vm = new Vue({
	el: '#wrapper',
	data: {
		menuList:{},
		sites: [
	          { text: 'Runoob' },
	          { text: 'Google' },
	          { text: 'Taobao' }
	        ]
	},
	created: function(){
		console.log("创建菜单");
		this.getMenuList();
	},
	methods:{
		getMenuList: function(){
			$.getJSON("sys/menu/menu", function(r){
				console.log("菜单："+r);
				FastJson.format(r);
				vm.menuList = r.menuList;
				console.log("菜单--》："+r.menuList);
				vm.loadMenu();
			});
		},
		loadMenu: function(){
			//等待vue组件渲染成功后执行
			this.$nextTick(function () {
				// MetsiMenu
                $('#side-menu').metisMenu();
				 //通过遍历给菜单项加上data-index属性
			    $(".J_menuItem").each(function (index) {
			        if (!$(this).attr('data-index')) {
			            $(this).attr('data-index', index);
			        }
			    });
			    $('.J_menuItem').on('click', menuItem);
			});
		}
	}
});