var app = new Vue({
    el: '#app',
    data: {
        email: "",
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

            axios.get('/student/getMailCaptcha')
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

            axios.get('/student/submitMailCaptcha', {
                params: {
                    captcha: this.captcha
                }
            })
                .then(function (response) {
                    console.log(response);
                    alert("激活成功")
                })
                .catch(function (error) {
                    console.error(error);       
                    alert(error.response.data.message);
                });

        }
    }
});