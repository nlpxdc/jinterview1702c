var app = new Vue({
    el: '#app',
    data() {
        return {
            list: [],
            listexam: [],
            loading: false,
            finished: false,
            keyword: "",
            


        };
    },
    methods: {
        onSearch(){

            axios.get("/audiorecord/search",{params:
                {  keyword: this.keyword,
                    time: this.time,}})
               .then(res => {
                   console.log(res);
                        this.lists=res.data;
                        console.log(this.lists.length);
                        if(this.lists.length<2){
                            this.finished=true;
                        }
                        this.lists.forEach(element => {
                            this.list.push(element);
                        });
                        
                        
               })
               .catch(err => {
                   console.error(err); 
               })
        },

        onbutton(audiorecordId){},
           

        
        onClickLeft() {

        },
        onClickRight() {

        },
    

        onLoad() {
            // 异步更新数据
            setTimeout(() => {
               
                var time=null;
                if(this.list.length>0){
                    var longtime=this.list[this.list.length-1].time;
                     time=new Date(longtime).getTime();    
                }else{
                     time=new Date().getTime();
                }
               
                this.getInterviews(time);
                this.loading=false;
                
            },2000)
        }
    }
});