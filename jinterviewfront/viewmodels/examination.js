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
        show(examId) {
            if(!examId){
                alert("没有试题");
                return ;
            }
            location.href = "/ExamShow.html?examId=" + examId;
        },

        onSearch() {
            this.listexam = [];

            this.onLoad();
        },
        onClickLeft() {

        },
        onClickRight() {

        },


        onLoad() {
            // 异步更新数据
            setTimeout(() => {

                var time = null;
                console.log(this.listexam.length);
                if (this.listexam.length > 0) {
                    var times = ((this.listexam[this.listexam.length - 1].time));
                    time = (new Date(times).getTime());

                } else {
                    time = (new Date().getTime());

                }
                console.log(time);
                axios.get("/exam/search", {
                    params: {
                        keyword: this.keyword,
                        time: time,
                    }
                })
                    .then(function (response) {
                        app.list = [];

                        app.list = response.data;



                        app.list.forEach(list => {
                            app.listexam.push(list);
                        });
                        if (app.list.length < 4) {
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