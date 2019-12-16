var app = new Vue({
    el: '#app',
    data: {
        studentId: '',
        nickname: '',
        realname: '',
        email: '',
        mobile: '',
        avatarUrl: '',
        IdCardPhotos: []

    },
    mounted() {
        console.log('view mounted');

        var url = new URL(location.href);
        this.studentId = url.searchParams.get("studentId");
        if (!this.studentId) {
            alert('studentId 不存在');
            return;
        }

        this.getstudent();
    },
    methods: {
        getstudent() {
            axios.get("/student/getBasicInfo", {
                params: {
                    studentId: this.studentId
                }
            })
                .then(response => {
                    console.log(response);
                    var student = response.data
                    this.avatarUrl = student.avatarUrl;
                    this.mobile = student.mobile;
                    this.email = student.email;
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
                })
                .catch(function (error) {
                    console.log(error);
                    alert(error.response.data.message);
                });
        }
    }

});