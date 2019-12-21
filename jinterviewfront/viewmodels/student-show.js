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
        IdCardPhotos: [],
        uploadIdcards: [],
        Idloading: false,
        Emailloading: false,

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
            this.Idloading = true;
            //todo redraw the photo
            var formData = new FormData();
            this.uploadIdcards.forEach(Idcard => {
                formData.append("Idcard", Idcard);
            });
            axios.post('/student/submitIdcard', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            })
                .then(function (response) {
                    console.log(response);
                    app.Idloading = false;
                    alert('提交成功');
                    app.getstudent();
                })
                .catch(function (error) {
                    console.error(error);
                    app.Idloading = false;
                    alert(error.response.data.message);
                });
        },
        handleSendEmailCaptchaTouch() {
            console.log('send email captcha touch');
            this.sendEmailCaptcha();
        },
        handleSubmitEmailCaptchaTouch() {
            console.log('submit email captcha touch');
            if (!this.emailCaptcha) {
                alert("请输入邮箱验证码");
                return;
            }
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
            this.Emailloading = true;
            axios.get('/student/submitMailCaptcha', {
                params: {
                    captcha: this.emailCaptcha
                }
            })
                .then(function (response) {
                    console.log(response);
                    app.Emailloading = false;
                    alert("邮箱验证成功");
                    location.reload();
                })
                .catch(function (error) {
                    console.error(error);
                    app.Emailloading = false;
                    alert(error.response.data.message);
                });
        },
        afterRead(Idcard) {
            console.log('after read Idcard', Idcard);

            var img = new Image();
            img.onload = function () {
                console.log('redraw loaded');

                const width = img.naturalWidth;
                const height = img.naturalHeight;
                const pixel = 360;

                if (width < height) {
                    if (width > pixel) {
                        var canvas = document.createElement('canvas');
                        canvas.width = pixel;
                        canvas.height = 1.0 * pixel * height / width;
                        var ctx = canvas.getContext('2d');
                        ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
                        canvas.toBlob(function (blob) {
                            const Idcard = new File([blob], "Idcard.jpg", {
                                type: "image/jpeg",
                            });
                            app.uploadIdcards.push(Idcard);
                            app.Idloading = false;
                        });
                    }
                } else {
                    if (height > pixel) {
                        var canvas = document.createElement('canvas');
                        canvas.width = 1.0 * pixel * width / height;
                        canvas.height = pixel;
                        var ctx = canvas.getContext('2d');
                        ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
                        canvas.toBlob(function (blob) {
                            const Idcard = new File([blob], "Idcard.jpg", {
                                type: "image/jpeg",
                            });
                            app.uploadIdcards.push(Idcard);
                            app.Idloading = false;
                        });
                    }
                }
            };
            img.src = Idcard.content;
            this.Idloading = true;

        }
    }

});