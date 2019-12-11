var app = new Vue({
    el: '#app',
    data() {
        return {
            list: [],
            audiorecordId: "",
            aaa:1,
            
            
            
        };
    },

    mounted () {
        this.onSearch();
    },
    
    methods: {
        onSearch(){
           axios.get("http://localhost/audiorecord/getById", {
            params: {
                audiorecordId: this.aaa,
                // time: time,
            }
        }).then(function(response){

            alert(response.data)

            app.list=response.data;
        });
        },
       
    

      
    }
});