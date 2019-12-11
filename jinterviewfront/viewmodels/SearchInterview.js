var app = new Vue({
    el: '#app',
    data: {
        
        show:true,
        onlyme:false,
        keyword:'',
        lists:[],
        loading:false,
        finished:false,
        list:[]
    },
    methods: {
        onLoad(){
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
            
        },
        
       
        getInterviews(time){  
          
           axios.get("/interview/search",{params:
            {"keyword":this.keyword,"onlyme":this.onlyme,"time":time}})
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
        }
    },
    mounted () {
      
    }

});