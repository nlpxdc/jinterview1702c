var app = new Vue({
    el: '#app',
    data: {
        studentname:"",
        show:true,
        onlyme:false,
        keyword:'',
       
        loading:false,
        finished:false,
        list:[]
    },
    methods: {
        onLoad(){
            setTimeout(() => {
                console.log(111);
                var time=null;
                if(this.list.length>0){
                    var longtime=this.list[this.list.length-1].time;
                     time=new Date(longtime).getTime();
                     console.log(time);
                  
                }else{
                     time=new Date().getTime();
                }
               
                this.getInterviews(time);
                this.loading=false;
                
            },2000)
            
        },
        onClickLeft(){
            
        },
        onClickRight(){

        },
        getInterviews(time){
        
            
           axios.get("/interview/search",{params:
            {"keyword":this.keyword,"onlyme":this.onlyme,"time":time}})
           .then(res => {
                    var lists=res.data;
                    if(lists.length<3){
                        this.finished=true;
                    }
                    lists.forEach(element => {
                        this.list.push(element);
                    });
                    
                    console.log(this.list);
           })
           .catch(err => {
               console.error(err); 
           })
        }
    },
    mounted () {
       
    }

});