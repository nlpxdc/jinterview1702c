var app = new Vue({
    el: '#app',
	data: {
		name:'上海站',
	},
	methods:{
		tiao(){
			location.href = "http://localhost:5000/address1?name="+this.name
		}
	},
});