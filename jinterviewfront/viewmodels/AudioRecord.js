var app = new Vue({
    el: '#app',
    data() {
        return {
            
            listexam: [],
            keyword: "",
            //time:new Date().getTime().subString(0,10),


        };
    },
	mounted () {
        this.onSearch();
    },
    methods: {
        onSearch(){
           axios.get("http://localhost/audiorecord/search", {
            params: {
                keyword: this.keyword,
                // time: time,
            }
        }).then(function(response){

            alert(response.data)

            app.listexam=response.data;
        });
    

       
    }
});