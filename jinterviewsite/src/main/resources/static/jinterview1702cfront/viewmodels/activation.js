var app = new Vue({
    el: '#app',
    data: {
        token:"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI0IiwiaXNzIjoiMTcwMmMiLCJleHAiOjE1NzU5NDIzNDgsImlhdCI6MTU3NTkzNTE0OCwic3RhdHVzIjoiMCJ9.KN7ZhDHhLoiqIaa5UdPxhd4PeotknLc28DU4H8yrqpo",
        mobile: "",
        captcha: "",
        studentId:1,


        showbtn: true,
        showbtnn: false,
        code_ts: '',
        sec: 60,

      
        




    },
    methods: {

        send() {
            var timer = setInterval(() => {

                this.sec = this.sec - 1
                this.code_ts = this.sec + 'S后重试'
                this.showbtn = false
                this.showbtnn = true
                if (this.sec === 0) {
                    clearInterval(timer)
                    this.sec = 60
                    this.code_ts = this.sec + 'S后重试'
                    this.showbtn = true
                    this.showbtnn = false

                }
            }, 1000)



            axios.get('http://192.168.137.1:80/student/getMobileCaptcha', {
                params: {
                
                    token:this.token
                    
                }
            })
                .then(function (response) {
                    console.log(response);

                })
                .catch(function (error) {
                    console.log(error);
                });



        },
        submit() {

            if (!this.captcha) {
                alert("请输入验证码");
                return;
            }

            axios.get('http://192.168.137.1:80/student/submitMobileCaptcha', {
                params: {
                    captcha: this.captcha,
                    token:this.token
                    
                }
            })
                .then(function (response) {
                    if (response.data) {
                        alert("激活成功")
                    } else {
                        alert("验证码有误，激活失败")
                    }

                })
                .catch(function (error) {
                    console.log(error);
                });

        }
    },
    mounted() {

    },


});