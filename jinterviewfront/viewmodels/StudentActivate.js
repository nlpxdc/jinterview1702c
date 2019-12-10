var app = new Vue({
    el: '#app',
    data: {
        mobile: "",
        captcha: "",
        studentId: 1,
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

            axios.get('/student/getMobileCaptcha')
                .then(function (response) {
                    console.log(response);
                    alert('发送成功');
                })
                .catch(function (error) {
                    console.log(error);
                    alert('发送失败');
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
                    token: this.token

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
    }
});