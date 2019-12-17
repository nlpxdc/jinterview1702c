var app = new Vue({
    el: '#app',
    data() {
        return {
            list: [],
            audiorecordId: "",
           
            
            
            
        };
    },

    mounted () {

        
        var url=new URL(location.href);
        this.interviewId = url.searchParams.get("audiorecordId");
        if (!this.audiorecordId) {
        alert('audiorecordId 不存在');
        return;
        }
        this.onSearch();
    },
    
    methods: {
        onSearch(){
           axios.get("/audiorecord/getById", {
            params: {
                audiorecordId: this.aaa,
                // time: time,
            }
        }).then(function(response){

            alert(response.data)

            app.list=response.data;
        });
        },
        onClickLeft() {

        },
        onClickRight() {

        }
       
    

      
    }
});