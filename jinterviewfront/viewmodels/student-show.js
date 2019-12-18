var app = new Vue({
    el: '#app',
    data: {
        nickname: '',
        realname: '',
        email: '',
        emailVerified: false,
        emailCaptcha: '',
        mobile: '',
        avatarUrl: '',
        IdCardPhotos: []

    },
    mounted() {
        console.log('view mounted');
        this.getstudent();
    },
    methods: {
        getstudent() {
            axios.get("/student/getBasicInfo")
                .then(response => {
                    console.log(response);
                    var student = response.data
                    this.avatarUrl = student.avatarUrl;
                    this.mobile = student.mobile;
                    this.email = student.email;
                    this.emailVerified = student.emailVerified;
                    this.nickname = student.nickname;
                    this.realname = student.realname;
                })
                .catch(function (error) {
                    console.error(error);
                });
        },
        handleSubmitIdcardClick() {
            console.log('submit idcard click');
            if (!this.IdCardPhotos.length) {
                alert('请选择图片');
                return;
            }
            this.submitIdcard();
        },
        submitIdcard() {
            //todo redraw the photo
            var formData = new FormData();
            this.IdCardPhotos.forEach(file => {
                formData.append("photo", file.file);
            });
            axios.post('/student/submitIdcard', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            })
                .then(function (response) {
                    console.log(response);
                    alert('提交成功');
                    app.getstudent();
                    //todo loading
                })
                .catch(function (error) {
                    console.error(error);
                    alert(error.response.data.message);
                });
        },
        handleSendEmailCaptchaTouch() {
            console.log('send email captcha touch');
            this.sendEmailCaptcha();
        },
        handleSubmitEmailCaptchaTouch() {
            console.log('submit email captcha touch');
            this.submitEmailCaptcha();
        },
        sendEmailCaptcha() {
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
        submitEmailCaptcha() {
            if (!this.emailCaptcha) {
                alert("请输入邮箱验证码");
                return;
            }

            axios.get('/student/submitMailCaptcha', {
                params: {
                    captcha: this.emailCaptcha
                }
            })
                .then(function (response) {
                    console.log(response);
                    alert("邮箱验证成功");
                    location.reload();
                })
                .catch(function (error) {
                    console.error(error);
                    alert(error.response.data.message);
                });
        }
    }

});