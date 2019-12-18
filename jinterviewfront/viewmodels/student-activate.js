var app = new Vue({
    el: '#app',
    data: {
        mobile: "",
        captcha: "",
        showbtn: true,
        showbtnn: false,
        code_ts: '',
        sec: 60,
    },
    mounted() {
        console.log('view mounted');
    },
    methods: {
        send() {
            // var timer = setInterval(() => {

            //     this.sec = this.sec - 1
            //     this.code_ts = this.sec + 'S后重试'
            //     this.showbtn = false
            //     this.showbtnn = true
            //     if (this.sec === 0) {
            //         clearInterval(timer)
            //         this.sec = 60
            //         this.code_ts = this.sec + 'S后重试'
            //         this.showbtn = true
            //         this.showbtnn = false

            //     }
            // }, 1000);

            axios.get('/student/getMobileCaptcha')
                .then(function (response) {
                    console.log(response);
                    alert('发送成功');
                })
                .catch(function (error) {
                    console.error(error);
                    alert(error.response.data.message);
                });
        },
        submit() {

            if (!this.captcha) {
                alert("请输入验证码");
                return;
            }

            axios.get('/student/submitMobileCaptcha', {
                params: {
                    captcha: this.captcha
                }
            })
                .then(function (response) {
                    console.log(response);
                    alert("激活成功");
                    localStorage['studentStatus'] = 1;
                    location.href = 'interview-index.html';
                })
                .catch(function (error) {
                    console.error(error);
                    alert(error.response.data.message);
                });

        }
    }
});