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

            this.listexam = [];

            this.onLoad();
     
        },

        onbutton(audiorecordId){},
           

        
        onClickLeft() {

        },
        onClickRight() {

        },
    

        onLoad() {
            // 异步更新数据
            setTimeout(() => {
                // for (let i = 0; i < 10; i++) {
                //     this.list.push(this.list.length + 1);


                // }
                var time=null;
                console.log(this.listexam.length);
                if(this.listexam.length>0){
                   var times=((this.listexam[this.listexam.length-1].time));
                   time=  (new Date(times).getTime()+'');              
                    console.log(time);                  
                }else{
                    time=(new Date().getTime()+'').substring(0,10);                  
                    console.log(time);                           
                }
                axios.get("/audiorecord/search", {
                    params: {
                        keyword: this.keyword,
                        time: time,
                    }
                })
                    .then(function (response) {
                    
                        app.list = response.data;
                        console.log(app.list);
                        if (app.list.length == 0) {
                            app.finished = true;
                        }

                        app.list.forEach(list => {
                            app.listexam.push(list);
                        });
                       
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
            }, 1000);
        }
    }
});