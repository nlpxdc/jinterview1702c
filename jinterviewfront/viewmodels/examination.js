var app = new Vue({
    el: '#app',
    data() {
        return {
            list: [],
            listexam: [],
            loading: false,
            finished: false,
            keyword: "",
            //time:new Date().getTime().subString(0,10),


        };
    },
    methods: {
        onSearch(){
            this.listexam=[];
           
           this.onLoad();
        },
        onClickLeft() {

        },
        onClickRight() {

        },
    

        onLoad() {
            // 异步更新数据
            setTimeout(() => {
               
                var time=null;
                console.log(this.listexam.length);
                if(this.listexam.length>0){
                   var times=((this.listexam[this.listexam.length-1].time));
                   time=  (new Date(times).getTime()+'').substring(0,10);              
                              
                }else{
                    time=(new Date().getTime()+'').substring(0,10);                  
                                             
                }
                console.log(time);
                axios.get("http://192.168.137.1:8080/exam/search", {
                    params: {
                        keyword: this.keyword,
                        time: time,
                    }
                })
                    .then(function (response) {
                        app.list=[];
                    
                        app.list = response.data;
                       
                      

                        app.list.forEach(list => {
                            app.listexam.push(list);
                        });
                        if (app.list.length<4) {
                            app.finished = true;
                        }
                       
                        console.log("111111")
                        console.log(app.listexam);
                        console.log("111111")

                    })
                    .catch(function (error) {
                        console.log(error);
                    });
                // 加载状态结束 
                
                
                this.loading = false;
                // if (app.list.length == 0) {
                //     app.finished = true;
                // }
            }, 2000);
        }
    }
});